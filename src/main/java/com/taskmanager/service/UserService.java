package com.taskmanager.service;

import com.taskmanager.entity.User;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */
public interface UserService {

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserByEmail(String email);

    boolean isEmailThereIs(String email);

    boolean isConfirm(String email);

    boolean isPasswordCorrect(User user);

    List<User> getAllUsers();
}
