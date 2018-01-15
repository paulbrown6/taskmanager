package com.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskID;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project projectID;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "title", length = 256)
    private String title;

    @Column(name = "status", length = 256)
    private String status;

    @ManyToMany
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Task() {
    }

    public Task(Project projectID, String name, String title, String status) {
        this.projectID = projectID;
        this.name = name;
        this.title = title;
        this.status = status;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Project getProjectID() {
        return projectID;
    }

    public void setProjectID(Project projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
