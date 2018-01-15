package com.taskmanager.service.impl;

import com.taskmanager.dao.UserDAO;
import com.taskmanager.entity.User;
import com.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;


    @Override
    @Transactional
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    @Transactional
    public boolean isEmailThereIs(String email) {
        return userDAO.isEmailThereIs(email);
    }

    @Override
    @Transactional
    public boolean isConfirm(String email){ return userDAO.isConfirm(email);}

    @Override
    @Transactional
    public boolean isPasswordCorrect(User user) {
        return userDAO.isPasswordCorrect(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers(){return userDAO.getAllUsers();}
}
