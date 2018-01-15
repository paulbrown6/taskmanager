package com.taskmanager.dao.impl;

import com.taskmanager.dao.CommentDAO;
import com.taskmanager.entity.Comment;
import com.taskmanager.entity.Project;
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

@Repository("commentDAOImp")
@Transactional
public class CommentDAOImpl implements CommentDAO {


    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Override
    public Comment createComment(Comment comment) {
        sessionFactory.getCurrentSession().save(comment);
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        Comment mergComm = (Comment) sessionFactory.getCurrentSession().merge(comment);
        sessionFactory.getCurrentSession().delete(mergComm);
    }

    @Override
    public Comment getCommentByUserID(Long id) {
        return sessionFactory.getCurrentSession().get(Comment.class, id);
    }

    @Override
    public List<Comment> getCommentsByTaskID(Long id) {
        String commentHQL = "FROM Comment WHERE task_id = :task_id";
        Query query = sessionFactory.getCurrentSession().createQuery(commentHQL);
        query.setParameter("task_id", id);
        System.out.println(id + " this String");
        return query.list();
    }
}
