package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {

    List<User> getUsers();

    Optional<User> getUser(String userId);

    Optional<User> getUserByEmail(String email);

    boolean userExists(String userId);

    User insert(User user);

    User update(User user);

    void delete(User user);
}
