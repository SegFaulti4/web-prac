package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.models.ForumUser;

import java.util.List;
import java.util.Objects;

@Controller
public class ForumUserController {
    @Autowired
    ForumUserDAO forumUserDAO;

    private void addSearchAttributes(String partition_name, String thread_name, String pattern, Model model) {
        model.addAttribute("partition", partition_name);
        model.addAttribute("thread", thread_name);
        model.addAttribute("pattern", pattern);
    }

    private void changeUserRole(String username, ForumUser.UserRole role) {
        ForumUser u = forumUserDAO.getByID(username);
        assert u != null;
        u.setUserrole(role);
        forumUserDAO.update(u);
    }

    private void banUser(String username) {
        changeUserRole(username, ForumUser.UserRole.BANNED);
    }

    private void promoteUser(String username) {
        changeUserRole(username, ForumUser.UserRole.MODERATOR);
    }

    @GetMapping("/users")
    public String forumUsersList(@RequestParam(name = "partition", defaultValue = "") String partition_name,
                                 @RequestParam(name = "thread", defaultValue = "") String thread_name,
                                 @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                 Model model, Authentication auth) {
        bindAuth(model, auth);
        List<ForumUser> users;
        if (Objects.equals(partition_name, "") || Objects.equals(thread_name, "")) {
            if (Objects.equals(pattern, "")) {
                users = forumUserDAO.getAll();
            } else {
                users = forumUserDAO.getAll(pattern);
            }
        } else {
            if (Objects.equals(pattern, "")) {
                users = forumUserDAO.getForumUsersByThreadID(partition_name, thread_name);
            } else {
                users = forumUserDAO.getForumUsersByThreadID(partition_name, thread_name, pattern);
            }
        }
        if (auth == null || !Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator")) {
            users.removeIf(u -> u.getUserrole() == ForumUser.UserRole.BANNED);
        }
        model.addAttribute("users", users);
        addSearchAttributes(partition_name, thread_name, pattern, model);
        return "forumUsers";
    }

    @GetMapping("/users/ban")
    public String banUserFromUsers(@RequestParam(name = "partition", defaultValue = "") String partition_name,
                                   @RequestParam(name = "thread", defaultValue = "") String thread_name,
                                   @RequestParam(name = "pattern", defaultValue = "") String pattern,
                                   @RequestParam(name = "user") String username,
                                   Model model, Authentication auth) {
        banUser(username);
        return forumUsersList(partition_name, thread_name, pattern, model, auth);
    }

    @GetMapping("/users/{user}")
    public String forumUser(@PathVariable(name = "user") String username,
                            Model model, Authentication auth) {
        bindAuth(model, auth);
        ForumUser u = forumUserDAO.getByID(username);
        assert u != null;
        model.addAttribute("user", u);
        return "forumUser";
    }

    @GetMapping("/users/{user}/ban")
    public String banUser(@PathVariable(name = "user") String username,
                          Model model, Authentication auth) {
        banUser(username);
        return forumUser(username, model, auth);
    }

    @GetMapping("/users/{user}/promote")
    public String promoteUser(@PathVariable(name = "user") String username,
                          Model model, Authentication auth) {
        promoteUser(username);
        return forumUser(username, model, auth);
    }

}
