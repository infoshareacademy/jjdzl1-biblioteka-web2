package com.infoshare.repository;

import com.infoshare.domain.User;
import com.infoshare.query.UsersQuery;
import com.infoshare.utils.Hasher;
import com.infoshare.utils.PBKDF2Hasher;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Stateless
public class UsersRepositoryDaoBean implements UsersRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<User> listOfUsers(String findUserByName) {

        String stringQuery = "select u from User u order by u.lastName";


        if (findUserByName != null) {
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


    public User getUserByLogin(String login) throws SQLException, ClassNotFoundException {
        User user = new User();
        ResultSet rs = UsersQuery.findUserByLogin(login);
        while (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");
            String password = rs.getString("password");
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);
        }
        rs.close();
        return user;
    }

    public List<User> findUserByLogin(String login) {

        String stringQuery = "select u from User u where u.login='" + login + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    public List<User> findUserByEmailOrLogin(String email, String login) {

        String stringQuery = "select u from User u where u.email='" + email + "' or u.login='" + login + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }
}
