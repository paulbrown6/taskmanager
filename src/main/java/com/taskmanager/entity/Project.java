package com.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectID;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userID;

    @ManyToMany
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @Column(name = "name", length = 256)
    private String name;

    public Project() {
    }

    public Project(User user, String name) {
        userID = user;
        this.name = name;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
