package ru.msu.cmc.webprac.DAO;

import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;
import ru.msu.cmc.webprac.models.ThreadID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.msu.cmc.webprac.DAO.ForumPartitionDAOTest.forumPartitionSetUpList;
import static ru.msu.cmc.webprac.DAO.ThreadDAOTest.threadSetUpList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
class ForumUserDAOTest {

    @Autowired
    private ForumUserDAO forumUserDAO;

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ForumPartitionDAO forumPartitionDAO;

    @Autowired
    private SessionFactory sessionFactory;

    static public @NonNull List<ForumUser> forumUserSetUpList() {
        List<ForumUser> l = new ArrayList<>();
        l.add(new ForumUser("user1", "123", "dead", ForumUser.UserRole.BANNED));
        l.add(new ForumUser("user2", "123", "alive", ForumUser.UserRole.COMMON));
        l.add(new ForumUser("user3", "123", "hungry", ForumUser.UserRole.MODERATOR));
        l.add(new ForumUser("user4", "123", null, ForumUser.UserRole.BANNED));
        l.add(new ForumUser("user5", "123", null, ForumUser.UserRole.COMMON));
        l.add(new ForumUser("user6", "123", null, ForumUser.UserRole.MODERATOR));
        return l;
    }

    @BeforeEach
    void setUp() {
        List<ForumUser> lu = forumUserSetUpList();
        List<ForumPartition> lp = forumPartitionSetUpList(lu);
        List<Thread> lt = threadSetUpList(lu, lp);

        forumUserDAO.saveCollection(lu);
        forumPartitionDAO.saveCollection(lp);
        threadDAO.saveCollection(lt);

        /*for (Thread t : lt) {
            lu.get(0).addThread(t);
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ForumUser u = session.get(ForumUser.class, lu.get(0).getId());
            Thread t = session.get(Thread.class, lt.get(0).getId());
            u.addThread(t);
            session.getTransaction().commit();
        }*/
        lu.get(0).addThread(lt.get(0));
        forumUserDAO.update(lu.get(0));
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE forum_user CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void getAll() {
        List<ForumUser> l = (List<ForumUser>) forumUserDAO.getAll();
        assertEquals(6, l.size());
    }

    @Test
    void getByID() {
        ForumUser u = forumUserDAO.getByID("user1");
        assertEquals("user1", u.getId());

        ForumUser nu = forumUserDAO.getByID("random");
        assertNull(nu);
    }

    @Test
    void save() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.COMMON));
        ForumUser u = forumUserDAO.getByID("user7");
        assertEquals("user7", u.getId());
    }

    @Test
    void update() {
        ForumUser u = forumUserDAO.getByID("user1");
        u.setPasswd("321");
        u.setUserrole(ForumUser.UserRole.COMMON);
        forumUserDAO.update(u);

        ForumUser su = forumUserDAO.getByID("user1");
        assertEquals("321", su.getPasswd());
        assertEquals(ForumUser.UserRole.COMMON, su.getUserrole());
    }

    @Test
    void delete() {
        ForumUser u = forumUserDAO.getByID("user1");
        forumUserDAO.delete(u);

        ForumUser su = forumUserDAO.getByID("user1");
        assertNull(su);
    }

    @Test
    void getForumUsersByThread() {
        List<ForumUser> lu = forumUserDAO.getForumUsersByThreadID("part1", "thread1");
        assertEquals(1, lu.size());
    }
}