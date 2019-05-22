package com.infoshare.logic.repository;

import com.infoshare.logic.domain.User;
import com.infoshare.logic.domain.UserStatus;
import com.infoshare.logic.utils.Hasher;
import com.infoshare.logic.utils.PBKDF2Hasher;
import com.infoshare.logic.utils.RecordPerPage;
import com.infoshare.logic.validation.UserValidator;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;


@Stateless
public class UsersRepositoryDaoBean implements UsersRepositoryDao {

    @PersistenceContext(name = "librarydb")
    private EntityManager entityManager;

    @EJB
    UserValidator validator;

    @Override
    public List<User> listOfUsers(String findUserByName, Integer page) {

        Integer recordPerPage = null;
        Integer offset = null;

        try {
            recordPerPage = RecordPerPage.readProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (page == null) page = 1;
        offset = recordPerPage * page - recordPerPage;


        String stringQuery = "select u from User u order by u.lastName";

        if (findUserByName == null) findUserByName = "";

        if (findUserByName != null || !findUserByName.isEmpty()) {
            stringQuery = "select u from User u where u.lastName like '%" + findUserByName + "%' order by u.lastName";
        }

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query
                .setMaxResults(recordPerPage)
                .setFirstResult(offset)
                .getResultList();

        return userList;
    }

    @Override
    public User getUserById(int id) {

        String stringQuery = "select u from User u where u.id=" + id;

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> user = query.getResultList();

        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public void addNewUser(User user) {

        if (user.getStatus() == null) user.setStatus("Aktywny");
        Hasher hasher = new PBKDF2Hasher();

        user.setPassword(hasher.hash(user.getPassword()));
        entityManager.persist(user);
    }

    public void deleteUser(int id) {

        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> findUserByLogin(String login) {

        String stringQuery = "select u from User u where u.login='" + login + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    @Override
    public List<User> findUserByEmail(String email) {

        String stringQuery = "select u from User u where u.email='" + email + "'";

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);
        List<User> userList = query.getResultList();
        return userList;
    }

    @Override
    public void updateUserAfterEdit(User user) {

        entityManager.merge(user);
    }

    @Override
    public User createUserFromForm(HttpServletRequest req) {

        return User.builder()
                .login(req.getParameter("login"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .password(req.getParameter("password"))
                .email(req.getParameter("e-mail"))
                .admin(isChecked(req, "admin") ? UserStatus.ADMIN : UserStatus.USER)
                .build();
    }


    private boolean isChecked(HttpServletRequest req, String fieldname) {

        String[] value = req.getParameterValues(fieldname);
        return value != null ? value[0].equals("on") : false;
    }

    @Override
    public List<String> validate(User user, HttpServletRequest req) {

        validator.userValidation(user);

        String password = user.getPassword();
        if (password != null && !password.equals(req.getParameter("password2"))) {
            validator.validationResult.add("Hasła są różne !");
        }

        return validator.validationResult;
    }
}
