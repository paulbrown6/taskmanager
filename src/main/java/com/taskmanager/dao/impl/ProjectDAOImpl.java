package com.taskmanager.dao.impl;

import com.taskmanager.dao.ProjectDAO;
import com.taskmanager.entity.Project;
import com.taskmanager.entity.User;
import org.hibernate.Criteria;
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

@Repository("projectDAOImp")
@Transactional
public class ProjectDAOImpl implements ProjectDAO {


    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Override
    public Project createProject(Project project) {
        sessionFactory.getCurrentSession().save(project);
        return project;
    }

    @Override
    public void updateProject(Project project) {
        Project mergPro = (Project) sessionFactory.getCurrentSession().merge(project);
        sessionFactory.getCurrentSession().update(mergPro);
    }

    @Override
    public void deleteProject(Project project) {
        Project mergProj = (Project) sessionFactory.getCurrentSession().merge(project);
        sessionFactory.getCurrentSession().delete(mergProj);
    }

    @Override
    public List<Project> getProjectsByUserID(Long id) {
        String projectHQL = "FROM Project WHERE user_id = :user_id";
        Query query = sessionFactory.getCurrentSession().createQuery(projectHQL);
        query.setParameter("user_id", id);
        System.out.println(id + " this String");
        return query.list();
    }

    @Override
    public Project getProjectByID(Long id) {
        return sessionFactory.getCurrentSession().get(Project.class, id);
    }

    @Override
    public List<Project> getAllProjects() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
        return criteria.list();
    }
}
