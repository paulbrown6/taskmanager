package com.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userID;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task taskID;

    @Column(name = "text", length = 256)
    private String text;

    private String username;

    public Comment(User userID, Task taskID, String username, String text) {
        this.userID = userID;
        this.taskID = taskID;
        this.username = username;
        this.text = text;
    }

    public Comment() {
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Task getTaskID() {
        return taskID;
    }

    public void setTaskID(Task taskID) {
        this.taskID = taskID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
