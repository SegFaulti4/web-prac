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
import ru.msu.cmc.webprac.models.ForumMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.msu.cmc.webprac.DAO.ForumPartitionDAOTest.forumPartitionSetUpList;
import static ru.msu.cmc.webprac.DAO.ForumUserDAOTest.forumUserSetUpList;
import static ru.msu.cmc.webprac.DAO.ThreadDAOTest.threadSetUpList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ForumMessageDAOTest {

    @Autowired
    private ForumMessageDAO forumMessageDAO;

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ForumPartitionDAO forumPartitionDAO;

    @Autowired
    private ForumUserDAO forumUserDAO;

    @Autowired
    private SessionFactory sessionFactory;

    static public @NonNull List<ForumMessage> forumMessageSetUpList(List<ForumUser> forumUsers,
                                                                    List<Thread> threads) {
        List<ForumMessage> l = new ArrayList<>();
        for (Thread t : threads) {
            l.add(new ForumMessage(t, null, forumUsers.get(1), "End of passion play"));
            l.add(new ForumMessage(t, l.get(l.size() - 1), forumUsers.get(2), "Crumbling away"));
            l.add(new ForumMessage(t, l.get(l.size() - 1), forumUsers.get(4), "I'm your source of self-destruction"));

            l.add(new ForumMessage(t, null, forumUsers.get(1), "Veins that pump with fear"));
            l.add(new ForumMessage(t, l.get(l.size() - 1), forumUsers.get(2), "Sucking darkness clear"));
            l.add(new ForumMessage(t, l.get(l.size() - 1), forumUsers.get(4), "Leading on your death's construction"));
        }
        return l;
    }

    @BeforeEach
    void setUp() {
        List<ForumUser> lu = forumUserSetUpList();
        List<ForumPartition> lp = forumPartitionSetUpList(lu);
        List<Thread> lt = threadSetUpList(lu, lp);
        List<ForumMessage> lm = forumMessageSetUpList(lu, lt);

        forumUserDAO.saveCollection(lu);
        forumPartitionDAO.saveCollection(lp);
        threadDAO.saveCollection(lt);
        forumMessageDAO.saveCollection(lm);
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE forum_message CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE forum_message_message_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("TRUNCATE thread CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE forum_partition CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE forum_user CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void getAll() {
        List<ForumMessage> l = (List<ForumMessage>) forumMessageDAO.getAll();
        assertEquals(54, l.size());
    }

    @Test
    void getByID() {
        ForumMessage m = forumMessageDAO.getByID(1L);
        assertEquals(1L, m.getId());

        ForumMessage nm = forumMessageDAO.getByID(1337L);
        assertNull(nm);
    }

    @Test
    void save() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.MODERATOR));
        ForumUser u = forumUserDAO.getByID("user7");
        threadDAO.save(new Thread("part1", "thread4", u));
        Thread t = threadDAO.getByID("part1", "thread4");

        forumMessageDAO.save(new ForumMessage(t, null, u, "MASTER"));
        ForumMessage m = forumMessageDAO.getByID(55L);
        assertEquals(55L, m.getId());

        forumMessageDAO.save(new ForumMessage(t, m, u, "MASTER"));
        ForumMessage rm = forumMessageDAO.getByID(56L);
        assertEquals(56L, rm.getId());
    }

    @Test
    void update() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.MODERATOR));
        ForumUser u = forumUserDAO.getByID("user7");
        threadDAO.save(new Thread("part1", "thread4", u));
        Thread t = threadDAO.getByID("part1", "thread4");

        forumMessageDAO.save(new ForumMessage(t, null, u, "MASTER"));
        ForumMessage m = forumMessageDAO.getByID(55L);
        m.setMessage("MASTER OF PUPPETS");
        forumMessageDAO.update(m);

        ForumMessage sm = forumMessageDAO.getByID(55L);
        assertEquals("MASTER OF PUPPETS", sm.getMessage());
    }

    @Test
    void delete() {
        ForumMessage m = forumMessageDAO.getByID(1L);
        forumMessageDAO.delete(m);

        ForumMessage sm = forumMessageDAO.getByID(1L);
        assertNull(sm);
    }

}
