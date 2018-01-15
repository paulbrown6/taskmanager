package com.taskmanager.service;

import com.taskmanager.entity.Project;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */
public interface ProjectService {

    Project createProject(Project project);

    void updateProject(Project project);

    void deleteProject(Project project);

    List getProjectsByUserID(Long id);

    Project getProjectByID(Long id);

    List<Project> getAllProjects();
}
