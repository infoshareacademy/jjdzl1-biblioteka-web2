package com.infoshare.repository;

import com.infoshare.logic.domain.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface UsersRepositoryDao {
    List<User> listOfUsers(String findUserByName) throws SQLException, ClassNotFoundException;
    User getUserById(int id) throws SQLException, ClassNotFoundException;
    void addNewUser(User user);
    List<User> findUserByLogin(String login);
    List<User> findUserByEmail(String email);
    void updateUserAfterEdit(User user);
}
