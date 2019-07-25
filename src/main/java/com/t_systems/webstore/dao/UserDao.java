package com.t_systems.webstore.dao;

import com.t_systems.webstore.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDao extends AbstractDao{

    public List<User> getAllUsers() {

        return em.createQuery("FROM User", User.class).getResultList();
    }

    public void addUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public User getUserByEmail(String email) {
        try {
            return em.createQuery("FROM User u WHERE u.email=:email", User.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getUser(String username) {
        try {
            return em.createQuery("FROM User u WHERE u.username=:username", User.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
