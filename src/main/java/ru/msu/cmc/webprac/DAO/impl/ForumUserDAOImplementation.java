package ru.msu.cmc.webprac.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.List;

@Repository
public class ForumUserDAOImplementation extends CommonDAOImplementation<ForumUser, String> implements ForumUserDAO {

    public ForumUserDAOImplementation(){
        super(ForumUser.class);
    }

    @Override
    public List<ForumUser> getForumUsersByThreadID(ThreadID t_id) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumUser> query = session.createQuery(
                    "select u " +
                            "from ForumUser u join u.threads t " +
                            "where t.id.partition_name=:partition_name " +
                            "and t.id.thread_name=:thread_name", ForumUser.class
            ).setParameter(
                    "partition_name", t_id.getPartition_name()
            ).setParameter(
                    "thread_name", t_id.getThread_name()
            );
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<ForumUser> getForumUsersByThreadID(String part_name, String thd_name) {
        return getForumUsersByThreadID(new ThreadID(part_name, thd_name));
    }

}
