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
import java.util.ArrayList;
import java.util.List;


@Stateless
public class UsersRepositoryDaoBean implements UsersRepositoryDao {

    List<User> listOfUsers = new ArrayList<>();

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @Override
    public List<User> listOfUsers(String findUserByName) {

        String stringQuery = "select u from User u order by u.lastName";

//        if (findUserByName != null) {
//            stringQuery = "select u from User u where u.lastName like '%" + findUserByName + "%' order by u.lastName";
//        }

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;


/*
        try (ResultSet rs = UsersQuery.listOfUsers("lastName", findUserByName)) {

            while (rs.next()) {
                int userID = rs.getInt("id");
                String login = rs.getString("login");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int admin = rs.getInt("admin");
                String status = rs.getString("status");

                User user = new User();
                user.setId(userID);
                user.setLogin(login);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(password);
                user.setEmail(email);
                if (admin == 1) user.setAdmin(UserStatus.ADMIN);
                else user.setAdmin(UserStatus.USER);
                user.setStatus(status);

                listOfUsers.add(user);
            }
            rs.close();
            return listOfUsers;
        }
*/
    }

    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        User user = new User();
        ResultSet rs = UsersQuery.findUserById(id);
        while (rs.next()) {
            String login = rs.getString("login");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String email = rs.getString("email");
            String password = rs.getString("password");
            user.setId(id);
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);
        }
        rs.close();
        return user;
    }

/*
    public void addNewUser(User user) {
        UsersQuery.addNewUser(user);
    }
*/


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


