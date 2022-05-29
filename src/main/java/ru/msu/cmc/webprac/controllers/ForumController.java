package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumUser;

import java.util.*;

@Controller
public class ForumController {

    @Autowired
    ForumUserDAO forumUserDAO;

    @Autowired
    ForumPartitionDAO forumPartitionDAO;


    @GetMapping("/forum")
    public String forum(Model model, Authentication auth) {
        bindAuth(model, auth);
        List<ForumPartition> partitions = forumPartitionDAO.getAll();
        if (auth == null || !Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator")) {
            partitions.removeIf(p -> !p.getGeneral_access());
        }
        partitions.sort(Comparator.comparing(ForumPartition::getId));
        model.addAttribute("partitions", partitions);
        return "forum";
    }

    @PostMapping("/forum/post")
    public String createPartition(@RequestParam(name = "partition") String partition_name,
                                  Model model, Authentication auth) {
        ForumUser u = forumUserDAO.getByID(auth.getName());
        assert u != null;
        forumPartitionDAO.save(new ForumPartition(partition_name, u, true));

        return "redirect:/forum";
    }

}
