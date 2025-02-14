package org.example.springbootstripe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.example.springbootstripe.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolService roleService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<Rol> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nom, @RequestParam String cognoms, @RequestParam String nom_usuari,
                               @RequestParam String email, @RequestParam String contrasenya, @RequestParam Long id_rol) {
        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);
        usuari.setNomUsuari(nom_usuari);
        usuari.setEmail(email);
        usuari.setContrasenya(passwordEncoder.encode(contrasenya));
        usuari.setIdRol(id_rol);
        usuariRepository.save(usuari);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Usuari usuari = usuariRepository.findByNomUsuari(username)
                .orElse(null);

        if (usuari != null && passwordEncoder.matches(password, usuari.getContrasenya())) {
            HttpSession session = request.getSession();
            session.setAttribute("username", usuari.getNomUsuari());
            session.setAttribute("email", usuari.getEmail());
            session.setAttribute("role", usuari.getIdRol());
            return "redirect:/dashboard";
        } else {
            return "redirect:/login?error";
        }
    }
}