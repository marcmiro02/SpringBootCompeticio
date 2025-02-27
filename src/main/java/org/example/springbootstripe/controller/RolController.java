package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rols")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/api")
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.saveRol(rol));
    }

    @GetMapping("/api")
    public ResponseEntity<List<Rol>> getAllRols() {
        return ResponseEntity.ok(rolService.getAllRols());
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.updateRol(Math.toIntExact(id), rol));
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(Math.toIntExact(id));
        return ResponseEntity.noContent().build();
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
        return "redirect:/rols";
    }
}