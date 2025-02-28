package org.example.springbootstripe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rols")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/gestio")
    public String getAllRols(Model model, HttpSession session) {
        String userRol = (String) session.getAttribute("roleName");
        if ("ADMIN".equals(userRol)) {
            List<Rol> roles = rolService.getAllRols();
            model.addAttribute("roles", roles);
            return "/administrador/gestioRols";
        }else{
            return "redirect:/home";
        }

    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("rol", new Rol());
        return "createRol";
    }

    @PostMapping("/create")
    public String createRol(@RequestParam("name") String name) {
        Rol rol = new Rol();
        rol.setNom(name);
        rolService.saveRol(rol);
        return "redirect:/rols/gestio";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Rol rol = rolService.getRolById(id);
        model.addAttribute("rol", rol);
        return "administrador/editRol";
    }

    @PostMapping("/edit/{id}")
    public String updateRol(@PathVariable Long id, @RequestParam("name") String name) {
        Rol rol = rolService.getRolById(id);
        rol.setNom(name);
        rolService.updateRol(Math.toIntExact(id), rol);
        return "redirect:/rols/gestio";
    }

    @GetMapping("/delete/{id}")
    public String deleteRol(@PathVariable Long id) {
        rolService.deleteRol(Math.toIntExact(id));
        return "redirect:/rols/gestio";
    }
}