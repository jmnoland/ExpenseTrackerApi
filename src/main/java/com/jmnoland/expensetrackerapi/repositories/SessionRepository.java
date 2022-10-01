package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.SessionDAO;
import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.SessionRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public class SessionRepository implements SessionRepositoryInterface {

    private final SessionDAO sessionDAO;
    private final DateProviderInterface dateProvider;

    @Autowired
    public SessionRepository(SessionDAO sessionDAO, DateProviderInterface dateProvider) {
        this.sessionDAO = sessionDAO;
        this.dateProvider = dateProvider;
    }

    public Optional<Session> getSession(String sessionId) {
        return this.sessionDAO.findById(sessionId);
    }

    public Optional<Session> getSessionByUserId(String userId) {
        return this.sessionDAO.findByUserId(userId);
    }

    public Session insert(String userId) {
        Date dateNow = this.dateProvider.getDateNow().getTime();
        Session session = new Session(null, userId, dateNow, dateNow);
        this.sessionDAO.insert(session);
        return session;
    }

    public Session update(Session session) {
        this.sessionDAO.save(session);
        return session;
    }

    public void delete(Session session) {
        this.sessionDAO.delete(session);
    }
}
