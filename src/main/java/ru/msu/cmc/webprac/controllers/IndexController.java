package ru.msu.cmc.webprac.controllers;

import static ru.msu.cmc.webprac.controllers.ModelAuthBinder.bindAuth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

@Controller
public class IndexController {

    @RequestMapping(value = { "/", "/index"})
    public String index(Model model, Authentication auth) {
        bindAuth(model, auth);
        return "index";
    }

}
