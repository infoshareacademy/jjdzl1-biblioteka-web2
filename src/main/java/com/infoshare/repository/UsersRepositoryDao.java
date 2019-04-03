package com.infoshare.repository;

import com.infoshare.domain.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.List;

@Local
public interface UsersRepositoryDao {
    List<User> listOfUsers(String findUserByName) throws SQLException, ClassNotFoundException;
    User getUserById(int id) throws SQLException, ClassNotFoundException;
    User getUserByLogin(String login) throws SQLException, ClassNotFoundException;
    void addNewUser(User user);
}
