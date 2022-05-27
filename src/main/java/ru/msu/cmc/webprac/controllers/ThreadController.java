package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprac.DAO.ForumMessageDAO;
import ru.msu.cmc.webprac.DAO.ForumPartitionDAO;
import ru.msu.cmc.webprac.DAO.ForumUserDAO;
import ru.msu.cmc.webprac.DAO.ThreadDAO;
import ru.msu.cmc.webprac.models.ForumMessage;
import ru.msu.cmc.webprac.models.ForumPartition;
import ru.msu.cmc.webprac.models.ForumUser;
import ru.msu.cmc.webprac.models.Thread;

import java.util.List;
import java.util.Objects;

@Controller
public class ThreadController {

    @Autowired
    ForumUserDAO forumUserDAO;

    @Autowired
    ThreadDAO threadDAO;

    @Autowired
    ForumMessageDAO forumMessageDAO;

    @GetMapping("/forum/{partition}/{thread}")
    public String thread(@PathVariable(name = "partition") String partition_name,
                                 @PathVariable(name = "thread") String thread_name,
                                 Model model, Authentication auth) {
        bindAuth(model, auth);
        Thread t = threadDAO.getByID(partition_name, thread_name);
        assert t != null;
        assert t.getPartition().getGeneral_access() || auth != null && Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        List<ForumMessage> messages = forumMessageDAO.getThreadMessages(t);
        assert messages != null;
        model.addAttribute("thread", t);
        model.addAttribute("messages", messages);
        return "thread";
    }

    @PostMapping("/forum/{partition}/{thread}/post")
    public String postMessage(@PathVariable(name = "partition") String partition_name,
                              @PathVariable(name = "thread") String thread_name,
                              @RequestParam(name = "message_text") String msg_txt,
                              @RequestParam(name = "reply_to", defaultValue = "") String reply_to,
                              Model model, Authentication auth) {
        assert auth != null;
        Thread t = threadDAO.getByID(partition_name, thread_name);
        ForumUser u = forumUserDAO.getByID(auth.getName());
        assert t != null;
        assert t.getPartition().getGeneral_access() || Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        assert u != null;
        ForumMessage replied;
        if (Objects.equals(reply_to, "")) {
            replied = null;
        } else {
            replied = forumMessageDAO.getByID(Long.valueOf(reply_to));
            assert replied != null;
        }
        forumMessageDAO.save(new ForumMessage(t, replied, u, msg_txt));
        return thread(partition_name, thread_name, model, auth);
    }

    @GetMapping("/forum/{partition}/{thread}/delete")
    public String deleteMessage(@PathVariable(name = "partition") String partition_name,
                                @PathVariable(name = "thread") String thread_name,
                                @RequestParam(name = "message") Long message_id,
                                Model model, Authentication auth) {
        assert auth != null;
        Thread t = threadDAO.getByID(partition_name, thread_name);
        assert t != null;
        assert t.getPartition().getGeneral_access() || Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator");
        ForumMessage m = forumMessageDAO.getByID(message_id);
        assert m != null;
        assert Objects.equals(auth.getAuthorities().toArray()[0].toString(), "moderator") || Objects.equals(m.getCreated_by().getId(), auth.getName());
        forumMessageDAO.delete(m);
        return thread(partition_name, thread_name, model, auth);
    }

}
