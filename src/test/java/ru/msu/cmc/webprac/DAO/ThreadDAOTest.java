package ru.msu.cmc.webprac.DAO;

import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.*;
import ru.msu.cmc.webprac.models.Thread;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.msu.cmc.webprac.DAO.ForumPartitionDAOTest.forumPartitionSetUpList;
import static ru.msu.cmc.webprac.DAO.ForumUserDAOTest.forumUserSetUpList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ThreadDAOTest {

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ForumPartitionDAO forumPartitionDAO;

    @Autowired
    private ForumUserDAO forumUserDAO;

    @Autowired
    private SessionFactory sessionFactory;

    static public @NonNull List<Thread> threadSetUpList(List<ForumUser> forumUsers, List<ForumPartition> forumPartitions) {
        List<Thread> l = new ArrayList<>();
        for (ForumPartition part : forumPartitions) {
            l.add(new Thread(part.getId(), "thread1", forumUsers.get(1)));
            l.add(new Thread(part.getId(), "thread2", forumUsers.get(2)));
            l.add(new Thread(part.getId(), "thread3", forumUsers.get(4)));
        }
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
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE thread CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE forum_partition CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE forum_user CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void getAll() {
        List<Thread> l = (List<Thread>) threadDAO.getAll();
        assertEquals(9, l.size());
    }

    @Test
    void getByID() {
        Thread t = threadDAO.getByID(new ThreadID("part1", "thread1"));
        assertEquals(new ThreadID("part1", "thread1"), t.getId());

        Thread nt = threadDAO.getByID(new ThreadID("Vna", "ture"));
        assertNull(nt);
    }

    @Test
    void save() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.MODERATOR));
        ForumUser u = forumUserDAO.getByID("user7");
        threadDAO.save(new Thread("part1", "thread4", u));

        Thread t = threadDAO.getByID("part1", "thread4");
        assertEquals(new ThreadID("part1", "thread4"), t.getId());
    }

    @Test
    void update() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.MODERATOR));
        ForumUser u = forumUserDAO.getByID("user7");
        Thread t = threadDAO.getByID("part1", "thread1");
        t.setCreated_by(u);
        threadDAO.update(t);

        Thread st = threadDAO.getByID("part1", "thread1");
        assertEquals(u.getId(), st.getCreated_by().getId());
    }

    @Test
    void delete() {
        ForumPartition p = forumPartitionDAO.getByID("part1");
        forumPartitionDAO.delete(p);

        ForumPartition sp = forumPartitionDAO.getByID("part1");
        assertNull(sp);
    }

}
