package com.taskmanager.dao.impl;

import com.taskmanager.dao.UserDAO;
import com.taskmanager.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * Created by Paul Brown on 10.01.2018.
 */

@Repository("userDAOImp")
@Transactional
public class UserDAOImpl implements UserDAO {


    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    @Override
    public User createUser(User user) {
        sessionFactory.getCurrentSession().save(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        User mergUser = (User) sessionFactory.getCurrentSession().merge(user);
        sessionFactory.getCurrentSession().update(mergUser);
    }

    @Override
    public void deleteUser(User user) {
        User mergUser = (User) sessionFactory.getCurrentSession().merge(user);
        sessionFactory.getCurrentSession().delete(mergUser);
    }

    @Override
    public User getUserByEmail(String email) {
        String userHQL = "FROM User WHERE email = :email";
        Query query = sessionFactory.getCurrentSession().createQuery(userHQL);
        query.setParameter("email", email);
        return (User) query.uniqueResult();
    }

    @Override
    public boolean isEmailThereIs(String email) {
        String userHQL = "FROM User WHERE email = :email";
        Query query = sessionFactory.getCurrentSession().createQuery(userHQL);
        query.setParameter("email", email);
        System.out.println(email + " this String");
        System.out.println(query.getParameter("email") + " this Query");
        return query.list().size() > 0;
    }
    
    @Override
    public boolean isConfirm(String email){
        String userHQL = "FROM User WHERE email = :email";
        Query query = sessionFactory.getCurrentSession().createQuery(userHQL);
        query.setParameter("email", email);
        System.out.println(email + " this String");
        User user = (User) query.list().get(0);
        return user.isConfirm();
    }

    @Override
    public boolean isPasswordCorrect(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        String userHQL = "FROM User WHERE password=:password AND email=:email";
        Query query = sessionFactory.getCurrentSession().createQuery(userHQL);
        query.setParameter("password", password);
        query.setParameter("email", email);
        System.out.println(password + " this String");
        System.out.println(query.getParameter("password") + " this Query");
        List users = query.list();

        return users.size() > 0;
    }

    @Override
    public List<User> getAllUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }
}
