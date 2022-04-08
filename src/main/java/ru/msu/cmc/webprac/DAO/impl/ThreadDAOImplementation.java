package ru.msu.cmc.webprac.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ThreadDAO;
import ru.msu.cmc.webprac.DAO.impl.CommonDAOImplementation;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

@Repository
public class ThreadDAOImplementation extends CommonDAOImplementation<Thread, ThreadID> implements ThreadDAO {

    public ThreadDAOImplementation(){
        super(Thread.class);
    }

    @Override
    public Thread getByID(String part_name, String thread_name) {
        return getByID(new ThreadID(part_name, thread_name));
    }

}
