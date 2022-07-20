package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.UserDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements UserRepositoryInterface {

    private final UserDAO userDAO;

    @Autowired
    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean userExists(String userId) {
        return false;
    }

    @Override
    public boolean usersExist() {
        return false;
    }

    @Override
    public User insert(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
