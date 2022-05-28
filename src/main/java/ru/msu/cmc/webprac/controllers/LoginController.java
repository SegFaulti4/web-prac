package ru.msu.cmc.webprac.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

@Controller
public class LoginController {

    @RequestMapping(value = { "/login" })
    public String login(Model model, Authentication auth) {
        bindAuth(model, auth);
        return "login";
    }

    @RequestMapping(value = { "/loginError" })
    public String loginError(Model model, Authentication auth) {
        bindAuth(model, auth);
        return "loginError";
    }

}
