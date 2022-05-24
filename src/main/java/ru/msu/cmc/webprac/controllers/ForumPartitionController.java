package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.models.ForumPartition;

import java.util.List;

@Controller
public class ForumPartitionController {

    @Autowired
    ForumPartitionDAO forumPartitionDAO;

    @GetMapping("/forumPartitions")
    public String forumPartitionsList(Model model, Authentication auth) {
        bindAuth(model, auth);
        // TODO restrict for non moderators
        List<ForumPartition> partitions = (List<ForumPartition>) forumPartitionDAO.getAll();
        model.addAttribute("partitions", partitions);
        return "forumPartitions";
    }

    @GetMapping
    public String forumPartition(@RequestParam(name = "id") String forumPartitionId, Model model, Authentication auth) {
        bindAuth(model, auth);
        ForumPartition p = forumPartitionDAO.getByID(forumPartitionId);

        if (p == null) {
            model.addAttribute("error_msg", "No forum partition with id " + forumPartitionId);
        }

        model.addAttribute("partition", p);
        return "forumPartition";
    }


}
