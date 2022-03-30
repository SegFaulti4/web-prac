package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ForumPartition;

import java.util.List;
import java.util.Set;

public interface ForumUserDAO {

    List<ForumUser> getAllForumUserByThreadActivity(Thread thread);
    ForumUser getForumUserByUsername(String username);

}
