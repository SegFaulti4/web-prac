package ru.msu.cmc.webprac.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ForumMessageDAO;
import ru.msu.cmc.webprac.DAO.impl.CommonDAOImplementation;
import ru.msu.cmc.webprac.models.ForumMessage;

@Repository
public class ForumMessageDAOImplementation extends CommonDAOImplementation<ForumMessage, Long> implements ForumMessageDAO {

    public ForumMessageDAOImplementation(){
        super(ForumMessage.class);
    }

}
