package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.DAO.ThreadDAO;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;

import java.util.List;
import java.util.Objects;

@Controller
public class ForumPartitionController {

    @Autowired
    ForumUserDAO forumUserDAO;

    @Autowired
    ForumPartitionDAO forumPartitionDAO;

    @Autowired
    ThreadDAO threadDAO;

    @GetMapping("/forum/{partition}")
    public String forumPartition(@PathVariable(name = "partition") String forumPartitionId,
                                 @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                 Model model, Authentication auth) {
        bindAuth(model, auth);
        ForumPartition p = forumPartitionDAO.getByID(forumPartitionId);
        assert p != null;
        assert p.getGeneral_access() || auth != null && Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        List<Thread> threads;
        if (Objects.equals(pattern, "")) {
            threads = threadDAO.getPartitionThreads(p);
        } else {
            threads = threadDAO.getPartitionThreads(p, pattern);
        }
        assert threads != null;
        model.addAttribute("partition", p);
        model.addAttribute("threads", threads);
        model.addAttribute("pattern", pattern);
        return "forumPartition";
    }

    @GetMapping("/forum/{partition}/conceal")
    public String concealPartition(@PathVariable(name = "partition") String partition_name,
                                   @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                   Model model, Authentication auth) {
        assert auth != null && Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        ForumPartition p = forumPartitionDAO.getByID(partition_name);
        assert p != null;
        p.setGeneral_access(false);
        forumPartitionDAO.update(p);
        return forumPartition(partition_name, pattern, model, auth);
    }

    @PostMapping("/forum/{partition}/post")
    public String createThread(@PathVariable(name = "partition") String partition_name,
                               @RequestParam(name = "thread") String thread_name,
                               Model model, Authentication auth) {
        assert auth != null;
        ForumPartition p = forumPartitionDAO.getByID(partition_name);
        ForumUser u = forumUserDAO.getByID(auth.getName());
        assert p != null;
        assert p.getGeneral_access() || Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        assert u != null;
        threadDAO.save(new Thread(partition_name, thread_name, u));
        return forumPartition(partition_name, "", model, auth);
    }

    @GetMapping("/forum/{partition}/delete")
    public String deleteThread(@PathVariable(name = "partition") String partition_name,
                               @RequestParam(name = "thread") String thread_name,
                               Model model, Authentication auth) {
        Thread t = threadDAO.getByID(partition_name, thread_name);
        assert t != null;
        assert t.getPartition().getGeneral_access() || auth != null && Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        assert auth != null && (Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator") || Objects.equals(auth.getName(), t.getCreated_by().getId()));
        threadDAO.delete(t);
        return forumPartition(partition_name, "", model, auth);
    }

}
