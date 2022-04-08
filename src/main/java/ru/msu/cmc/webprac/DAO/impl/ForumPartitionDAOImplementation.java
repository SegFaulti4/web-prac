package ru.msu.cmc.webprac.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.DAO.impl.CommonDAOImplementation;
import ru.msu.cmc.webprac.models.ForumPartition;

@Repository
public class ForumPartitionDAOImplementation extends CommonDAOImplementation<ForumPartition, String> implements ForumPartitionDAO {

    public ForumPartitionDAOImplementation(){
        super(ForumPartition.class);
    }

}
