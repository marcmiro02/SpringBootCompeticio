package org.example.springbootstripe.controller;

import java.util.Optional;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
                               @RequestParam String contrasenya) {



        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);
        usuari.setNomUsuari(nom_usuari);
        usuari.setEmail(email);
        usuari.setContrasenya(contrasenya);
        usuari.setIdRol(2);

        usuariRepository.save(usuari);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request) {
        Usuari usuari = usuariRepository.findByEmail(username);

        if (usuari != null && password.equals(usuari.getContrasenya())) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", usuari);
            session.setAttribute("userId", usuari.getId());
            session.setAttribute("username", usuari.getNomUsuari());
            session.setAttribute("nom", usuari.getNom());
            session.setAttribute("cognoms", usuari.getCognoms());
            session.setAttribute("email", usuari.getEmail());

            // Retrieve the role name based on idRol and set it in the session
            Optional<Rol> optionalRol = rolRepository.findById(usuari.getIdRol());
            if (optionalRol.isPresent()) {
                Rol rol = optionalRol.get();
                session.setAttribute("roleName", rol.getNom());
            }

            return "redirect:competicions/all";
        } else {
            return "redirect:/login?error=invalidCredentials";
        }
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
