package ru.msu.cmc.webprac.controllers;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class ModelAuthBinder {

    public static void bindAuth(@NonNull Model model, Authentication auth) {
        model.addAttribute("authenticated", auth != null);
        if (auth != null) {
            model.addAttribute("user_name", auth.getName());
            model.addAttribute("user_role", auth.getAuthorities().toArray()[0].toString());
        }
    }

}
