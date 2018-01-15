package com.taskmanager.service.impl;

import com.taskmanager.dao.CommentDAO;
import com.taskmanager.entity.Comment;
import com.taskmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Override
    public Comment createComment(Comment comment) {
        return commentDAO.createComment(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentDAO.deleteComment(comment);
    }

    @Override
    public List getCommentsByTaskID(Long id) {
        return commentDAO.getCommentsByTaskID(id);
    }

    @Override
    public Comment getCommentByUserID(Long id) {
        return commentDAO.getCommentByUserID(id);
    }
}
