package ru.msu.cmc.webprac.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ForumMessageDAO;
import ru.msu.cmc.webprac.DAO.impl.CommonDAOImplementation;
import ru.msu.cmc.webprac.models.Activity;
import ru.msu.cmc.webprac.models.ForumMessage;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.List;

@Repository
public class ForumMessageDAOImplementation extends CommonDAOImplementation<ForumMessage, Long> implements ForumMessageDAO {

    public ForumMessageDAOImplementation() {
        super(ForumMessage.class);
    }

    @Override
    public List<ForumMessage> getThreadMessages(Thread thread) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumMessage> query = session.createQuery(
                    "select m " +
                            "from ForumMessage m " +
                            "where m.thread.id=:thread_id", ForumMessage.class
            ).setParameter(
                    "thread_id", thread.getId()
            );
            return query.getResultList();
        }
    }

    private void addActivity(ForumMessage entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String part_name = entity.getThread().getId().getPartition_name();
            String thread_name = entity.getThread().getId().getThread_name();
            String username = entity.getCreated_by().getId();
            session.saveOrUpdate(new Activity(part_name, thread_name, username));
            session.getTransaction().commit();
        }
    }

    @Override
    public void save(ForumMessage entity) {
        super.save(entity);
        addActivity(entity);
    }

}
