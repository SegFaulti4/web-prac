package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.ForumUserID;
import ru.msu.cmc.webprac.models.Thread;

import java.util.List;
import java.util.Set;

public interface ForumUserDAO extends CommonDAO<ForumUser, ForumUserID> {

    List<ForumUser> getForumUsersByThread(Thread thread);
    // List<ForumUser> getForumUsersByThreadSet(Set<Thread> thread);

}
