package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.DAO.ThreadDAO;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;

import java.util.Comparator;
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
        List<Thread> threads;
        if (Objects.equals(pattern, "")) {
            threads = threadDAO.getPartitionThreads(p);
        } else {
            threads = threadDAO.getPartitionThreads(p, pattern);
        }
        assert threads != null;

        threads.sort(Comparator.comparing(t -> t.getId().getThread_name()));

        model.addAttribute("partition", p);
        model.addAttribute("threads", threads);
        model.addAttribute("pattern", pattern);
        return "forumPartition";
    }

    private void changePartitionAccess(String partition_name, Boolean access) {
        ForumPartition p = forumPartitionDAO.getByID(partition_name);
        assert p != null;
        p.setGeneral_access(access);
        forumPartitionDAO.update(p);
    }

    @GetMapping("/forum/{partition}/conceal")
    public RedirectView concealPartition(@PathVariable(name = "partition") String partition_name,
                                         @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                         Model model, Authentication auth, RedirectAttributes attributes) {
        changePartitionAccess(partition_name, false);

        attributes.addAttribute("partition", partition_name);
        if (!Objects.equals(pattern, "")) {
            attributes.addAttribute("pattern", pattern);
        }
        return new RedirectView("/forum/{partition}");
    }

    @GetMapping("/forum/{partition}/open")
    public RedirectView openPartition(@PathVariable(name = "partition") String partition_name,
                                @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                Model model, Authentication auth, RedirectAttributes attributes) {
        changePartitionAccess(partition_name, true);

        attributes.addAttribute("partition", partition_name);
        if (!Objects.equals(pattern, "")) {
            attributes.addAttribute("pattern", pattern);
        }
        return new RedirectView("/forum/{partition}");
    }

    @PostMapping("/forum/{partition}/post")
    public RedirectView createThread(@PathVariable(name = "partition") String partition_name,
                               @RequestParam(name = "thread") String thread_name,
                               Model model, Authentication auth, RedirectAttributes attributes) {
        ForumUser u = forumUserDAO.getByID(auth.getName());
        assert u != null;
        threadDAO.save(new Thread(partition_name, thread_name, u));

        attributes.addAttribute("partition", partition_name);
        return new RedirectView("/forum/{partition}");
    }

    @GetMapping("/forum/{partition}/delete")
    public RedirectView deleteThread(@PathVariable(name = "partition") String partition_name,
                               @RequestParam(name = "thread") String thread_name,
                               Model model, Authentication auth, RedirectAttributes attributes) {
        Thread t = threadDAO.getByID(partition_name, thread_name);
        assert t != null;
        threadDAO.delete(t);

        attributes.addAttribute("partition", partition_name);
        return new RedirectView("/forum/{partition}");
    }

}
