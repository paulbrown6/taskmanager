package com.taskmanager.dao;

import com.taskmanager.entity.Comment;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */
public interface CommentDAO {

    Comment createComment(Comment comment);

    void deleteComment(Comment comment);

    List getCommentsByTaskID(Long id);

    Comment getCommentByUserID(Long id);
}
