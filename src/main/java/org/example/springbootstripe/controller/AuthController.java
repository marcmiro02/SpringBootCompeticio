package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nom, @RequestParam String email, @RequestParam String contrasenya) {
        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setEmail(email);
        usuari.setContrasenya(passwordEncoder.encode(contrasenya));
        usuariRepository.save(usuari);
        return "redirect:/login";
    }
}