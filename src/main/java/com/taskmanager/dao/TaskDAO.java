package com.taskmanager.dao;

import com.taskmanager.entity.Task;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */
public interface TaskDAO {

    Task createTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    List getTasksByProjectID(Long id);

    Task getTaskByID(Long id);
}
