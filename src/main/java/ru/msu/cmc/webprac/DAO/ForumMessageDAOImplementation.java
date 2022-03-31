package ru.msu.cmc.webprac.DAO;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.models.ForumMessage;

@Repository
public class ForumMessageDAOImplementation extends CommonDAOImplementation<ForumMessage, Long> implements ForumMessageDAO {

    public ForumMessageDAOImplementation(){
        super(ForumMessage.class);
    }

}
