package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.UserDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserRepositoryInterface {

    private final UserDAO userDAO;

    @Autowired
    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getUsers() {
        return this.userDAO.findAll();
    }

    public Optional<User> getUser(String userId) {
        return this.userDAO.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userDAO.findByEmail(email);
    }

    public boolean userExists(String userId) {
        return this.userDAO.existsById(userId);
    }

    public User insert(User user) {
        this.userDAO.insert(user);
        return user;
    }

    public User update(User user) {
        this.userDAO.save(user);
        return user;
    }

    public void delete(User user) {
        this.userDAO.delete(user);
    }
}
