package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
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
        List<ForumMessage> messages = forumMessageDAO.getThreadMessages(t);
        assert messages != null;
        model.addAttribute("thread", t);
        model.addAttribute("messages", messages);
        return "thread";
    }

    @PostMapping("/forum/{partition}/{thread}/post")
    public RedirectView postMessage(@PathVariable(name = "partition") String partition_name,
                                    @PathVariable(name = "thread") String thread_name,
                                    @RequestParam(name = "message_text") String msg_txt,
                                    @RequestParam(name = "reply_to", defaultValue = "") String reply_to,
                                    Model model, Authentication auth, RedirectAttributes attributes) {
        Thread t = threadDAO.getByID(partition_name, thread_name);
        ForumUser u = forumUserDAO.getByID(auth.getName());
        assert t != null;
        assert u != null;
        ForumMessage replied;
        if (Objects.equals(reply_to, "")) {
            System.out.println("Reply not provided");
            replied = null;
        } else {
            System.out.println("Reply provided");
            replied = forumMessageDAO.getByID(Long.valueOf(reply_to));
        }

        /*System.out.println(t.getId().getPartition_name());
        System.out.println(t.getId().getThread_name());
        System.out.println(replied == null ? false : replied.getId());
        System.out.println(u.getId());
        System.out.println(msg_txt);
        System.out.println("");*/

        forumMessageDAO.save(new ForumMessage(t, replied, u, msg_txt));
        attributes.addAttribute("partition", partition_name);
        attributes.addAttribute("thread", thread_name);
        return new RedirectView("/forum/{partition}/{thread}");
    }

    @GetMapping("/forum/{partition}/{thread}/delete")
    public RedirectView deleteMessage(@PathVariable(name = "partition") String partition_name,
                                @PathVariable(name = "thread") String thread_name,
                                @RequestParam(name = "message") Long message_id,
                                Model model, Authentication auth, RedirectAttributes attributes) {
        Thread t = threadDAO.getByID(partition_name, thread_name);
        assert t != null;
        ForumMessage m = forumMessageDAO.getByID(message_id);
        assert m != null;
        forumMessageDAO.delete(m);
        attributes.addAttribute("partition", partition_name);
        attributes.addAttribute("thread", thread_name);
        return new RedirectView("/forum/{partition}/{thread}");
    }

}
