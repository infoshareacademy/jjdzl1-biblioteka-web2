package com.infoshare.logic.repository;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class UsersRepositoryDaoBean implements UsersRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<User> listOfUsers(String findUserByName) {

        String stringQuery = "select u from User u order by u.lastName";

        if (findUserByName != null || !findUserByName.isEmpty()) {
            stringQuery = "select u from User u where u.lastName like '%" + findUserByName + "%' order by u.lastName";
        }

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    public User getUserById(int id) {

        String stringQuery = "select u from User u where u.id=" + id;

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        User user = query.getSingleResult();
        return user;
    }

    public void addNewUser(User user) {

        if (user.getStatus() == null) user.setStatus("Aktywny");
        Hasher hasher = new PBKDF2Hasher();

        user.setPassword(hasher.hash(user.getPassword()));
        entityManager.persist(user);
    }

    public List<User> findUserByLogin(String login) {

        String stringQuery = "select u from User u where u.login='" + login + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    public List<User> findUserByEmail(String email) {

        String stringQuery = "select u from User u where u.email='" + email + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    public void updateUserAfterEdit(User user) {

        entityManager.merge(user);
    }
}
