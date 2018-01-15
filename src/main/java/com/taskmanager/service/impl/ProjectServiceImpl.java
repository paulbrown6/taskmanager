package com.taskmanager.service.impl;

import com.taskmanager.dao.ProjectDAO;
import com.taskmanager.entity.Project;
import com.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDAO projectDAO;

    @Override
    @Transactional
    public Project createProject(Project project) {
        return projectDAO.createProject(project);
    }

    @Override
    @Transactional
    public void updateProject(Project project) {
        projectDAO.updateProject(project);
    }

    @Override
    @Transactional
    public void deleteProject(Project project) {
        projectDAO.deleteProject(project);
    }

    @Override
    @Transactional
    public List getProjectsByUserID(Long id) {
        return projectDAO.getProjectsByUserID(id);
    }

    @Override
    @Transactional
    public Project getProjectByID(Long id) {
        return projectDAO.getProjectByID(id);
    }

    @Override
    @Transactional
    public List<Project> getAllProjects(){return projectDAO.getAllProjects();}
}
