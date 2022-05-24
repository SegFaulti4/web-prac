package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.models.ForumUser;

import java.util.List;

@Controller
public class ForumUserController {
    @Autowired
    ForumUserDAO forumUserDAO;

    @GetMapping("/forumUsers")
    public String forumUsersList(Model model, Authentication auth) {
        bindAuth(model, auth);
        // TODO restrict for non moderators
        List<ForumUser> users = (List<ForumUser>) forumUserDAO.getAll();
        model.addAttribute("users", users);
        return "forumUsers";
    }

    @GetMapping("/forumUser")
    public String forumUser(@RequestParam(name = "id") String forumUserId, Model model, Authentication auth) {
        bindAuth(model, auth);
        ForumUser u = forumUserDAO.getByID(forumUserId);

        if (u == null) {
            model.addAttribute("error_msg", "No forum user with id " + forumUserId);
        }

        model.addAttribute("forumUser", u);
        return "forumUser";
    }

    @RequestMapping(value = { "/myProfile" })
    public String myProfile(Model model, Authentication auth) {
        return forumUser(auth.getName(), model, auth);
    }

}
