package com.taskmanager.dao.impl;

import com.taskmanager.dao.TaskDAO;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Repository("taskDAOImp")
@Transactional
public class TaskDAOImpl implements TaskDAO {


    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Override
    public Task createTask(Task task) {
        sessionFactory.getCurrentSession().save(task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        Task mergTask = (Task) sessionFactory.getCurrentSession().merge(task);
        sessionFactory.getCurrentSession().update(mergTask);
    }

    @Override
    public void deleteTask(Task task) {
        Task mergTask = (Task) sessionFactory.getCurrentSession().merge(task);
        sessionFactory.getCurrentSession().delete(mergTask);
    }

    @Override
    public List getTasksByProjectID(Long id) {
        String taskHQL = "FROM Task WHERE project_id = :project_id";
        Query query = sessionFactory.getCurrentSession().createQuery(taskHQL);
        query.setParameter("project_id", id);
        System.out.println(id + " this String");
        return query.list();
    }

    @Override
    public Task getTaskByID(Long id) {
        return sessionFactory.getCurrentSession().get(Task.class, id);
    }
}
