package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.RolRepository;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("roles", rolRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nom,
                               @RequestParam String cognoms,
                               @RequestParam String nom_usuari,
                               @RequestParam String email,
                               @RequestParam String contrasenya,
                               @RequestParam int id_rol) {

        if (id_rol <= 0) {
            throw new IllegalArgumentException("El ID de rol no puede ser nulo o inválido");
        }

        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);
        usuari.setNomUsuari(nom_usuari);
        usuari.setEmail(email);
        usuari.setContrasenya(contrasenya);
        usuari.setIdRol(id_rol);

        usuariRepository.save(usuari);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request) {
        Usuari usuari = usuariRepository.findByEmail(username).orElse(null);

        if (usuari != null && password.equals(usuari.getContrasenya())) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", usuari.getId());
            session.setAttribute("username", usuari.getNomUsuari());
            return "redirect:/homepage";
        } else {
            return "redirect:/login?error=invalidCredentials";
        }
    }

    @GetMapping("/somePage")
    public String somePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login?error=sessionExpired";
        }

        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");

        if (userId == null || username == null) {
            return "redirect:/login?error=sessionExpired";
        }

        model.addAttribute("userId", userId);
        model.addAttribute("username", username);

        return "somePage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}