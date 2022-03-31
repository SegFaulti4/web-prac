package ru.msu.cmc.webprac.DAO;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

@Repository
public class ThreadDAOImplementation extends CommonDAOImplementation<Thread, ThreadID> implements ThreadDAO {

    public ThreadDAOImplementation(){
        super(Thread.class);
    }

}
