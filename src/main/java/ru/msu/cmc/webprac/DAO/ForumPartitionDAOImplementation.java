package ru.msu.cmc.webprac.DAO;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumPartitionID;

@Repository
public class ForumPartitionDAOImplementation extends CommonDAOImplementation<ForumPartition, ForumPartitionID> implements ForumPartitionDAO {

    public ForumPartitionDAOImplementation(){
        super(ForumPartition.class);
    }

}
