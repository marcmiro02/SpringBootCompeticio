package org.example.springbootstripe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Autowired
    private UsuariService usuariService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("userId", userId);
        return "profile";  // Returns the profile view
    }

    @PostMapping("/perfil/change-password")
    public String changePassword(HttpSession session,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Usuari usuari = usuariService.getUsuariById(userId);

        if (usuari == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "redirect:/";
        }

        if (!usuari.getContrasenya().equals(currentPassword)) {
            model.addAttribute("error", "Current password is incorrect.");
            return "redirect:/";
        }

        usuariService.updatePassword(usuari, newPassword);
        model.addAttribute("success", "Password changed successfully.");
        return "redirect:/";
    }
}