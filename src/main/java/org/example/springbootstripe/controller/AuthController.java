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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
        // Aquí podrías agregar otros servicios si es necesario, como obtener roles
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nom,
                               @RequestParam String cognoms,
                               @RequestParam String nom_usuari,
                               @RequestParam String email,
                               @RequestParam String contrasenya,
                               @RequestParam int id_rol) {  // Cambiado a int

        // Validar que el id_rol no sea nulo o inválido
        if (id_rol <= 0) {
            throw new IllegalArgumentException("El ID de rol no puede ser nulo o inválido");
        }

        Usuari usuari = new Usuari();
        usuari.setNom(nom);
        usuari.setCognoms(cognoms);
        usuari.setNomUsuari(nom_usuari);
        usuari.setEmail(email);

        // Aquí asignas directamente el idRol, ya que solo es la ID y no un objeto completo
        usuari.setIdRol(id_rol);

        // Establecer la contraseña
        usuari.setContrasenya(contrasenya);

        // Guardar el usuario en la base de datos
        usuariRepository.save(usuari);

        return "redirect:/login";
    }



    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        // Buscar al usuario por su correo electrónico
        Usuari usuari = usuariRepository.findByEmail(username)
                .orElse(null);

        // Verificar si el usuario existe y si la contraseña coincide
        if (usuari != null && password.equals(usuari.getContrasenya())) {
            // Si la autenticación es exitosa, redirigir al dashboard
            return "redirect:/homepage";
        } else {
            // Si la autenticación falla, redirigir al login con mensaje de error
            if (usuari == null) {
                return "redirect:/login?error=userNotFound";  // Usuario no encontrado
            } else {
                return "redirect:/login?error=wrongPassword";  // Contraseña incorrecta
            }
        }
    }

}
