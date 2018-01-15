package com.taskmanager.service.impl;

import com.taskmanager.dao.TaskDAO;
import com.taskmanager.entity.Task;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDAO taskDAO;

    @Override
    @Transactional
    public Task createTask(Task task) {
        return taskDAO.createTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskDAO.updateTask(task);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskDAO.deleteTask(task);
    }

    @Override
    @Transactional
    public List getTasksByProjectID(Long id) {
        return taskDAO.getTasksByProjectID(id);
    }

    @Override
    @Transactional
    public Task getTaskByID(Long id) {
        return taskDAO.getTaskByID(id);
    }
}
