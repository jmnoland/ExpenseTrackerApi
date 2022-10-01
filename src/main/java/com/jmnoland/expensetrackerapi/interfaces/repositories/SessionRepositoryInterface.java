package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Session;

import java.util.Optional;

public interface SessionRepositoryInterface {

    Optional<Session> getSession(String sessionId);

    Optional<Session> getSessionByUserId(String userId);

    Session insert(String userId);

    Session update(Session session);

    void delete(Session session);
}
