package ru.msu.cmc.webprac.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.List;

@Repository
public class ForumUserDAOImplementation extends CommonDAOImplementation<ForumUser, String> implements ForumUserDAO {

    public ForumUserDAOImplementation(){
        super(ForumUser.class);
    }

    @Override
    public List<ForumUser> getForumUsersByThreadID(String part_name, String thd_name) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumUser> query = session.createQuery(
                    "select u " +
                            "from ForumUser u join Activity a on u.id=a.id.username " +
                            "where a.id.partition_name=:partition_name " +
                            "and a.id.thread_name=:thread_name", ForumUser.class
            ).setParameter(
                    "partition_name", part_name
            ).setParameter(
                    "thread_name", thd_name
            );
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<ForumUser> getForumUsersByThreadID(String part_name, String thd_name, String pattern) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumUser> query = session.createQuery(
                    "select u " +
                            "from ForumUser u join Activity a on u.id=a.id.username " +
                            "where a.id.partition_name=:partition_name " +
                            "and a.id.thread_name=:thread_name and a.id.username like :pattern", ForumUser.class
            ).setParameter(
                    "partition_name", part_name
            ).setParameter(
                    "thread_name", thd_name
            ).setParameter(
                    "pattern", pattern
            );
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<ForumUser> getAll(String pattern) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumUser> query = session.createQuery(
                    "select u " +
                            "from ForumUser u " +
                            "where u.id like :pattern", ForumUser.class
            ).setParameter(
                    "pattern", pattern
            );
            return query.getResultList();
        }
    }

}
