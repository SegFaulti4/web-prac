package ru.msu.cmc.webprac.DAO;

import org.springframework.dao.DataAccessException;
import ru.msu.cmc.webprac.models.ForumMessage;
import ru.msu.cmc.webprac.models.Thread;

import java.util.List;

public interface ForumMessageDAO extends CommonDAO<ForumMessage, Long> {

    List<ForumMessage> getThreadMessages(Thread thread);

}
