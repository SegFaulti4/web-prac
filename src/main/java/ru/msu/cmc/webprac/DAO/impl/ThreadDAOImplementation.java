package ru.msu.cmc.webprac.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ThreadDAO;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.List;

@Repository
public class ThreadDAOImplementation extends CommonDAOImplementation<Thread, ThreadID> implements ThreadDAO {

    public ThreadDAOImplementation(){
        super(Thread.class);
    }

    @Override
    public Thread getByID(String part_name, String thread_name) {
        return getByID(new ThreadID(part_name, thread_name));
    }

    @Override
    public List<Thread> getPartitionThreads(ForumPartition partition, String pattern) {
        try (Session session = sessionFactory.openSession()) {
            Query<Thread> query = session.createQuery(
                    "select t " +
                            "from Thread t " +
                            "where t.id.partition_name=:partition_name and t.id.thread_name like :pattern", Thread.class
            ).setParameter(
                    "partition_name", partition.getId()
            ).setParameter(
                    "pattern", pattern
            );
            return query.getResultList();
        }
    }

    @Override
    public List<Thread> getPartitionThreads(ForumPartition partition) {
        try (Session session = sessionFactory.openSession()) {
            Query<Thread> query = session.createQuery(
                    "select t " +
                            "from Thread t " +
                            "where t.id.partition_name=:partition_name", Thread.class
            ).setParameter(
                    "partition_name", partition.getId()
            );
            return query.getResultList();
        }
    }

}
