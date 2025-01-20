package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rols")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.saveRol(rol));
    }

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRols() {
        return ResponseEntity.ok(rolService.getAllRols());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.updateRol(id, rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}
