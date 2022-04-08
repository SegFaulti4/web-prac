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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.msu.cmc.webprac.DAO.ForumUserDAOTest.forumUserSetUpList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ForumPartitionDAOTest {

    @Autowired
    private ForumPartitionDAO forumPartitionDAO;

    @Autowired
    private ForumUserDAO forumUserDAO;

    @Autowired
    private SessionFactory sessionFactory;

    static public @NonNull List<ForumPartition> forumPartitionSetUpList(List<ForumUser> forumUsers) {
        List<ForumPartition> l = new ArrayList<>();
        l.add(new ForumPartition("part1", forumUsers.get(2), true));
        l.add(new ForumPartition("part2", forumUsers.get(5), true));
        l.add(new ForumPartition("part3", forumUsers.get(2), true));
        return l;
    }

    @BeforeEach
    void setUp() {
        List<ForumUser> lu = forumUserSetUpList();
        List<ForumPartition> lp = forumPartitionSetUpList(lu);
        forumUserDAO.saveCollection(lu);
        forumPartitionDAO.saveCollection(lp);
    }

    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE forum_partition CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE forum_user CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void getAll() {
        List<ForumPartition> l = (List<ForumPartition>) forumPartitionDAO.getAll();
        assertEquals(3, l.size());
    }

    @Test
    void getByID() {
        ForumPartition p = forumPartitionDAO.getByID("part1");
        assertEquals("part1", p.getId());

        ForumPartition np = forumPartitionDAO.getByID("random");
        assertNull(np);
    }

    @Test
    void save() {
        forumUserDAO.save(new ForumUser("user7", "123", null, ForumUser.UserRole.MODERATOR));
        ForumUser u = forumUserDAO.getByID("user7");
        forumPartitionDAO.save(new ForumPartition("part4", u, true));

        ForumPartition p = forumPartitionDAO.getByID("part4");
        assertEquals("part4", p.getId());
    }

    @Test
    void update() {
        ForumPartition p = forumPartitionDAO.getByID("part1");
        p.setGeneral_access(false);
        forumPartitionDAO.update(p);

        ForumPartition sp = forumPartitionDAO.getByID("part1");
        assertEquals(false, sp.getGeneral_access());
    }

    @Test
    void delete() {
        ForumPartition p = forumPartitionDAO.getByID("part1");
        forumPartitionDAO.delete(p);

        ForumPartition sp = forumPartitionDAO.getByID("part1");
        assertNull(sp);
    }

}
