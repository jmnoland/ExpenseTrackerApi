package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserRepositoryInterface {

    List<User> getUsers();

    User getUser(String userId);

    User getUserByEmail(String email);

    boolean userExists(String userId);

    boolean usersExist();

    User insert(User user);

    User update(User user);

    void delete(User user);
}
