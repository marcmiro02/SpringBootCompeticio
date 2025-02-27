package org.example.springbootstripe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.RolRepository;
import org.example.springbootstripe.repository.UsuariRepository;
import org.example.springbootstripe.services.RolService;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService;
    @Autowired
    private RolService rolService;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuariRepository usuariRepository;

    @PostMapping
    public ResponseEntity<Usuari> createUsuari(@RequestBody Usuari usuari) {
        return ResponseEntity.ok(usuariService.saveUsuari(usuari));
    }

    @GetMapping
    public ResponseEntity<List<Usuari>> getAllUsuaris() {
        return ResponseEntity.ok(usuariService.getAllUsuaris());
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Usuari> getUsuariById(@PathVariable Long id) {
        return ResponseEntity.ok(usuariService.getUsuariById(id));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<Usuari> updateUsuari(@PathVariable Long id, @RequestBody Usuari usuari) {
        return ResponseEntity.ok(usuariService.updateUsuari(id, usuari));
    }


    @GetMapping("/all")
    public String getActiveCompeticions(Model model) {
        List<Usuari> usuaris = usuariService.getAllUsuaris();
        List<Map<String, Object>> usuarisWithRoles = usuaris.stream().map(usuari -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", usuari.getId());
            userMap.put("nom", usuari.getNom());
            userMap.put("cognoms", usuari.getCognoms());
            userMap.put("nomUsuari", usuari.getNomUsuari());
            userMap.put("email", usuari.getEmail());
            userMap.put("nomComplet", usuari.getNomComplet());
            userMap.put("contrasenya", usuari.getContrasenya());
            Optional<Rol> optionalRol = rolRepository.findById(usuari.getIdRol());
            optionalRol.ifPresent(rol -> userMap.put("nomRol", rol.getNom()));
            return userMap;
        }).collect(Collectors.toList());
        model.addAttribute("users", usuarisWithRoles);
        return "usuari/usuaris";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Usuari usuari = usuariService.getUsuariById(id);
        model.addAttribute("usuari", usuari);
        model.addAttribute("roles", rolRepository.findAll());

        return "usuari/usuari";
    }
    @PostMapping("/actualitzar")
    public String actualizarUsuario(@RequestParam("rol") Long rolId, @ModelAttribute Usuari usuari) {
        // Buscar el Rol por su ID (puede ser null, por eso se usa Optional)
        Optional<Rol> optionalRol = rolService.findById(rolId);

        if (optionalRol.isPresent()) {
            Rol rol = optionalRol.get();  // Obtener el Rol si está presente
            usuari.setIdRol(rol.getId());  // Asignar el ID del rol al usuario

            // Continuar con el proceso de actualización del usuario
            Usuari existingUsuari = usuariService.getUsuariById(usuari.getId());
            if (existingUsuari != null) {
                existingUsuari.setNom(usuari.getNom());
                existingUsuari.setCognoms(usuari.getCognoms());
                existingUsuari.setEmail(usuari.getEmail());
                existingUsuari.setNomUsuari(usuari.getNomUsuari());
                existingUsuari.setIdRol(usuari.getIdRol());

                // Si no se modifica la contraseña, mantener la original
                if (usuari.getContrasenya() != null && !usuari.getContrasenya().isEmpty()) {
                    existingUsuari.setContrasenya(usuari.getContrasenya());
                }

                // Guardar el usuario actualizado
                usuariService.saveUsuari(existingUsuari);
            }

            return "redirect:/usuaris/all";  // Redirigir a la lista de usuarios
        } else {
            // Si no se encuentra el rol, manejar el error
            return "error";  // Redirige o muestra un mensaje de error al usuario
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteUsuari(@PathVariable Long id) {
        usuariService.deleteUsuari(id);
        return "redirect:/usuaris/all";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("usuari", new Usuari());
        model.addAttribute("roles", rolRepository.findAll());
        return "usuari/crearUsuari";
    }

    @PostMapping("/create")
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

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        // Obtener el ID del usuario actual de la sesión
        Long userId = (Long) session.getAttribute("userId");

        // Verificar si el ID del usuario está presente en la sesión
        if (userId != null) {
            // Obtener los detalles del usuario desde la base de datos
            Usuari usuari = usuariService.getUsuariById(userId);

            // Agregar los detalles del usuario al modelo
            model.addAttribute("usuari", usuari);
        } else {
            // Manejar el caso en que el usuario no esté logueado
            return "redirect:/login";
        }

        return "/perfil/profile";
    }



}
