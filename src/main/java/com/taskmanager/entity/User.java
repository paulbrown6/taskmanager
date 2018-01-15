package com.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "firstname", length = 64)
    private String firstname;

    @Column(name = "lastname", length = 64)
    private String lastname;

    @Column(name = "mode", length = 64)
    private String mode;

    @Column(name = "confirm")
    private boolean confirm;

    @ManyToMany
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @ManyToMany
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks;

    public User() {
        confirm = false;
    }

    public User(String email, String pass, String firstname, String lastname, String mode){
        this.email = email;
        this.password = pass;
        this.mode = mode;
        this.firstname = firstname;
        this.lastname = lastname;
        confirm = false;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mode='" + mode +
                '}';
    }

}
