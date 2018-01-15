package com.taskmanager.controllers;


import com.taskmanager.entity.Comment;
import com.taskmanager.entity.Project;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.mail.JMail;
import com.taskmanager.service.CommentService;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MyController {

    @Autowired
    UserService userServ;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model, HttpSession session, ModelAndView modelAndView){
        if (session.getAttribute("idpro") == null){
            model.addAttribute("idpro", "");
            session.setAttribute("idpro", "");
        }
        if (session.getAttribute("filter") == null){
            model.addAttribute("filter", "all");
            session.setAttribute("filter", "all");
        }
        return isUserToPage(session, modelAndView);
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager(@ModelAttribute("email") String email, @ModelAttribute("password") String password,
                                @ModelAttribute("mode") String mode, @ModelAttribute("firstname") String firstname,
                                @ModelAttribute("lastname") String lastname, @ModelAttribute("idpro") String idproject,
                                @ModelAttribute("delete") String delete, @ModelAttribute("adduser") String adduser,
                                @ModelAttribute("filter") String filter,
                                HttpServletRequest request, Model model,
                                HttpSession session, ModelAndView modelAndView){
        if (session.getAttribute("idpro") == null){
            model.addAttribute("idpro", "");
            session.setAttribute("idpro", "");
        }
        if (session.getAttribute("filter") == null){
            model.addAttribute("filter", "all");
            session.setAttribute("filter", "all");
        }
        if (!filter.equals("")) {
            session.setAttribute("filter", filter);
        }
        if (!idproject.equals("")) {
            session.setAttribute("idpro", idproject);
        }
        if (!delete.equals("")){
            return delete(delete, session, modelAndView);
        }
        if (!email.equals("")){
            if (!mode.equals("")){
                return registration(mode, email, password, firstname, lastname, request, modelAndView).getViewName();
            } else return signin(email, password, session, modelAndView).getViewName();
        }
        if (!adduser.equals("")){
            return addUserInProject(adduser, session, modelAndView).getViewName();
        }
        return isUserToPage(session, modelAndView);
    }

    private String delete(String delete, HttpSession session, ModelAndView modelAndView) {
        System.out.println(delete);
        System.out.println(Long.parseLong(delete.substring(1)));
        if (delete.charAt(0) == 'p'){
            List<Task> tasks = taskService.getTasksByProjectID(Long.parseLong(delete.substring(1)));
            for (Task task: tasks) {
                List<Comment> comments = commentService.getCommentsByTaskID(task.getTaskID());
                for (Comment comment: comments) {
                    commentService.deleteComment(comment);
                }
                taskService.deleteTask(task);
            }
            projectService.deleteProject(projectService.getProjectByID(Long.parseLong(delete.substring(1))));
            session.setAttribute("tasks", null);
            session.setAttribute("idpro", "");
            session.setAttribute("prousers", "");
            session.setAttribute("freeusers", "");
        } else {
            if (delete.charAt(0) == 't') {
                List<Comment> comments = commentService.getCommentsByTaskID(taskService.getTaskByID(Long.parseLong(delete.substring(1))).getTaskID());
                for (Comment comment: comments) {
                    commentService.deleteComment(comment);
                }
                taskService.deleteTask(taskService.getTaskByID(Long.parseLong(delete.substring(1))));
            } else {
                commentService.deleteComment(commentService.getCommentByUserID(Long.parseLong(delete.substring(1))));
                Task task = (Task) session.getAttribute("task");
                return task(task.getTaskID().toString(), session);
            }
        }
        return isUserToPage(session, modelAndView);
    }

    @RequestMapping(value = "/manager/add", method = RequestMethod.GET)
    public String add(@ModelAttribute("type") String type, @ModelAttribute("name") String name,
                      @ModelAttribute("title") String title,
                      HttpSession session, ModelAndView modelAndView){
        if (!type.equals("")){
            if (!name.equals("")) return addObject(type, name, title, session, modelAndView).getViewName();
            else return "add";
        }
        return isUserToPage(session, modelAndView);
    }

    private ModelAndView addObject(String type, String name, String title, HttpSession session, ModelAndView modelAndView){
        if (type.equals("project")) {
            return addProject(name, session, modelAndView);
        }
        else {
            if (!session.getAttribute("idpro").equals("")) {
                return addTask(name, title, session, modelAndView);
            } else {
                modelAndView.setViewName(isUserToPage(session, modelAndView));
                return modelAndView;
            }
        }
    }

    private ModelAndView addProject(String name, HttpSession session, ModelAndView modelAndView){
        Project project = new Project((User) session.getAttribute("user"), name);
        projectService.createProject(project);
        modelAndView.setViewName(isUserToPage(session, modelAndView));
        return modelAndView;
    }

    private ModelAndView addTask(String name, String title, HttpSession session, ModelAndView modelAndView){
        Task task = new Task(projectService.getProjectByID(Long.parseLong((String) session.getAttribute("idpro"))),
                name, title, "waiting");
        User user = (User) session.getAttribute("user");
        if (user.getMode().equals("dev")){
            List<User> users = task.getUsers();
            if (users == null){
                users = new ArrayList<>();
            }
            users.add(user);
            task.setUsers(users);
        }
        taskService.createTask(task);
        modelAndView.setViewName(isUserToPage(session, modelAndView));
        return modelAndView;
    }

    private ModelAndView addUserInProject(String email, HttpSession session, ModelAndView modelAndView){
        Project project = projectService.getProjectByID(Long.parseLong((String) session.getAttribute("idpro")));
        List<User> users = project.getUsers();
        users.add(userServ.getUserByEmail(email));
        projectService.updateProject(project);
        modelAndView.setViewName(isUserToPage(session, modelAndView));
        return modelAndView;
    }

    private ModelAndView signin(String email, String pass, HttpSession session, ModelAndView modelAndView){
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);
        if(userServ.isEmailThereIs(email) && userServ.isPasswordCorrect(user)) {
            user = userServ.getUserByEmail(email);
            session.setAttribute("user", user);
            modelAndView.setViewName(isConfirmToPage(email, session, modelAndView));
            return modelAndView;
        } else return errorAuth("errorSign", modelAndView);
    }

    private ModelAndView registration(String mode, String email, String pass, String firstname, String lastname,
                                     HttpServletRequest req, ModelAndView modelAndView){
        HttpSession session = req.getSession(true);
        if(validation(email, pass)) {
            User user = new User(email, pass, firstname, lastname, mode);
            userServ.createUser(user);
            session.setAttribute("user", user);
            sendMessage(email, req);
            modelAndView.setViewName("errorconfirm");
            return modelAndView;
        }
        else {
            return errorAuth("errorReg", modelAndView);
        }
    }

    private ModelAndView errorAuth(String type, ModelAndView modelAndView){
        switch (type){
            case "errorSign":
                modelAndView.addObject("errorSign", "Вы не правильно ввели логин или пароль!");
                break;
            case "errorReg":
                modelAndView.addObject("errorReg", "Ошибка при регистрации!");
                break;
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }

    private ModelAndView showproject(HttpSession session, ModelAndView modelAndView){
        User user = (User) session.getAttribute("user");
        List<Project> projects;
        if (user.getMode().equals("manager")) {
            projects = projectService.getProjectsByUserID(user.getId());
        } else {
            projects = user.getProjects();
        }
        session.setAttribute("projects", projects);
        modelAndView.setViewName("manager");
        return modelAndView;
    }

    private ModelAndView showtask(String idproject, HttpSession session, ModelAndView modelAndView){
        List<Task> tasks = new ArrayList<>();
        if (session.getAttribute("filter").equals("all")) {
            tasks = taskService.getTasksByProjectID(Long.parseLong(idproject));
        } else {
            User user = (User) session.getAttribute("user");
            tasks = user.getTasks();
        }
        session.setAttribute("tasks", tasks);
        modelAndView.setViewName("manager");
        return modelAndView;
    }

    private ModelAndView showusers(String idproject, HttpSession session, ModelAndView modelAndView){
        List<User> usersinproject = projectService.getProjectByID(Long.parseLong(idproject)).getUsers();
        List<User> users = usersNotObject(userServ.getAllUsers(), usersinproject);
        session.setAttribute("prousers", usersinproject);
        session.setAttribute("freeusers", users);
        modelAndView.setViewName("manager");
        return modelAndView;
    }

    private List<User> usersNotObject(List<User> all, List<User> inproject){
        List<User> notpro = new ArrayList<>();
        if (inproject.size() == 0){
            for (User user: all) {
                if (user.getMode().equals("dev")) {
                    notpro.add(user);
                }
            }
        } else {
            for (User user : all) {
                for (User anInproject : inproject) {
                    if (!user.getId().equals(anInproject.getId()) && user.getMode().equals("dev")) {
                        notpro.add(user);
                    }
                }
            }
        }
        return notpro;
    }

    @RequestMapping(value = "/resend", method = RequestMethod.GET)
    public String resend(HttpServletRequest request, HttpSession session, ModelAndView modelAndView){
        User user = (User) session.getAttribute("user");
        sendMessage(user.getEmail(), request);
        return isUserToPage(session, modelAndView);
    }

    @RequestMapping(value = "/manager/task", method = RequestMethod.GET)
    public String taskpage(@ModelAttribute("idtask") String idtask, @ModelAttribute("devtask") String devtask,
                           @ModelAttribute("stat") String status, @ModelAttribute("comment") String comment,
                           @ModelAttribute("delete") String delete, HttpSession session,
                           ModelAndView modelAndView){
        if (isUserToPage(session, modelAndView).equals("manager")) {
            if (!idtask.equals("")) {
                return task(idtask, session);
            }
            if (!devtask.equals("")){
                return addUserToTask(devtask, session);
            }
            if (!status.equals("")){
                return taskStatusChange(status, session);
            }
            if (!comment.equals("")){
                return addComment(comment, session);
            }
            if (!delete.equals("")){
                return delete(delete, session, modelAndView);
            }
        }
        return isUserToPage(session, modelAndView);
    }

    private String task(String id, HttpSession session){
        Task task = taskService.getTaskByID(Long.parseLong(id));
        List<User> userstask = task.getUsers();
        List<User> users = usersNotObject(userServ.getAllUsers(), userstask);
        List<Comment> comments = commentService.getCommentsByTaskID(task.getTaskID());
        session.setAttribute("task", task);
        session.setAttribute("taskusers", userstask);
        session.setAttribute("usersnottask", users);
        session.setAttribute("comments", comments);
        return "task";
    }

    private String addUserToTask(String email, HttpSession session){
        Task task = (Task) session.getAttribute("task");
        List<User> users = task.getUsers();
        users.add(userServ.getUserByEmail(email));
        task.setUsers(users);
        taskService.updateTask(task);
        return task(task.getTaskID().toString(), session);
    }

    private String taskStatusChange(String status, HttpSession session){
        Task task = (Task) session.getAttribute("task");
        task.setStatus(status);
        taskService.updateTask(task);
        return "task";
    }

    private String addComment(String textcomment, HttpSession session){
        User user = (User) session.getAttribute("user");
        Task task = (Task) session.getAttribute("task");
        String username = user.getFirstname() + " " + user.getLastname();
        Comment comment = new Comment(user, task, username, textcomment);
        commentService.createComment(comment);
        List<Comment> comments = commentService.getCommentsByTaskID(task.getTaskID());
        session.setAttribute("comments", comments);
        return "task";
    }

    private boolean validation(String email, String pass) {
        return validEmail(email) && validPassword(pass) && !userServ.isEmailThereIs(email);
    }

    private boolean validEmail(String email){
        Pattern pt = Pattern.compile("([а-яa-z1-9._])+@([а-яa-z.])+.([a-zа-я]{2,6})");
        Matcher mt = pt.matcher(email);
        System.out.println(mt.matches());
        return mt.matches();
    }

    private boolean validPassword(String pass){
        return pass.length() > 7;
    }

    private void sendMessage(String email, HttpServletRequest request){
        Random random = new Random();
        String body = "Для подтверждения своего email пройдите по ссылке! http://localhost:8080" +
                request.getContextPath() + "/isconfirm?path=" + random.nextInt(9)  + random.nextInt(9) +
                random.nextInt(9) + random.nextInt(9) +  userServ.getUserByEmail(email).getId();
        JMail jmail = new JMail("drek6@mail.ru", "Task Manager", email,
                userServ.getUserByEmail(email).getFirstname(), body);
        jmail.sendMail();
        System.out.println("Письмо отправлено!");
    }

    @RequestMapping(value = "/isconfirm", method = RequestMethod.GET)
    public String confirm(@ModelAttribute("path") String path, HttpSession session, ModelAndView modelAndView){
        User user = (User) session.getAttribute("user");
        if (path.substring(4).equals(user.getId().toString())){
            user.setConfirm(true);
            userServ.updateUser(user);
        }
        return isUserToPage(session, modelAndView);
    }

    private String isUserToPage(HttpSession session, ModelAndView modelAndView){
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "index";
        } else {
            return isConfirmToPage(user.getEmail(), session, modelAndView);
        }
    }

    private String isConfirmToPage(String email, HttpSession session, ModelAndView modelAndView){
        if (!userServ.isConfirm(email)){
            return "errorconfirm";
        }
        else {
            if (!session.getAttribute("idpro").equals("")) {
                showtask((String) session.getAttribute("idpro"), session, modelAndView);
                showusers((String) session.getAttribute("idpro"), session, modelAndView);
            }
            return showproject(session, modelAndView).getViewName();
        }
    }
}
