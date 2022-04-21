package ru.msu.cmc.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.DAO.impl.ForumUserDAOImplementation;
import ru.msu.cmc.webprac.models.ForumUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class ForumUserController {
    @Autowired
    private final ForumUserDAO forumUserDAO = new ForumUserDAOImplementation();

    @GetMapping("/forumUsers")
    public String forumUsersList(Model model) {
        List<ForumUser> users = (List<ForumUser>) forumUserDAO.getAll();
        model.addAttribute("users", users);
        return "forumUsers";
    }

    @GetMapping("/forumUser")
    public String forumUserPage(@RequestParam(name = "id") String forumUserId, Model model) {
        ForumUser u = forumUserDAO.getByID(forumUserId);

        if (u == null) {
            model.addAttribute("error_msg", "No forum user with id " + forumUserId);
        }

        model.addAttribute("forumUser", u);
        return "forumUser";
    }

}
