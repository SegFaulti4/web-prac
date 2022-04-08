package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.List;
import java.util.Set;

public interface ForumUserDAO extends CommonDAO<ForumUser, String> {

    List<ForumUser> getForumUsersByThreadID(ThreadID thread);
    List<ForumUser> getForumUsersByThreadID(String part_name, String thd_name);
    // List<ForumUser> getForumUsersByThreadSet(Set<Thread> thread);

}
