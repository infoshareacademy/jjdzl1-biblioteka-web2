package com.infoshare.logic.repository;

import com.infoshare.logic.domain.User;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@Local
public interface UsersRepositoryDao {
    List<User> listOfUsers(String findUserByName) throws SQLException, ClassNotFoundException;

    User getUserById(int id) throws SQLException, ClassNotFoundException;

    void addNewUser(User user);

    void deleteUser(int id);

    List<User> findUserByLogin(String login);

    List<User> findUserByEmail(String email);

    void updateUserAfterEdit(User user);

    User createUserFromForm(HttpServletRequest req);

    List<String> validate(User user, HttpServletRequest req);
}
