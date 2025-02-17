package org.example.springbootstripe.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("userId", userId);
        return "profile";  // Retorna a la vista del perfil
    }
}
