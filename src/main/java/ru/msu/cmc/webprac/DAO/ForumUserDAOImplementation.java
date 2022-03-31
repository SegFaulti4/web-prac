package ru.msu.cmc.webprac.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.ForumUserID;
import ru.msu.cmc.webprac.models.Thread;

import java.util.List;

@Repository
public class ForumUserDAOImplementation extends CommonDAOImplementation<ForumUser, ForumUserID> implements ForumUserDAO {

    public ForumUserDAOImplementation(){
        super(ForumUser.class);
    }

    @Override
    public List<ForumUser> getForumUsersByThread(Thread thread) {
        try (Session session = sessionFactory.openSession()) {
            Query<ForumUser> query = session.createQuery(
                    "select u " +
                            "from ForumUser u join u.threads t " +
                            "where t.id.partition_id.partition_name=:partition_name " +
                            "and t.id.thread_name=:thread_name", ForumUser.class
            ).setParameter(
                    "partition_name", thread.getId().getPartition_id().getPartition_name()
            ).setParameter(
                    "thread_name", thread.getId().getThread_name()
            );
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

}
