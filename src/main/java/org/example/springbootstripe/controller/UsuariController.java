package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService;

    @PostMapping
    public ResponseEntity<Usuari> createUsuari(@RequestBody Usuari usuari) {
        return ResponseEntity.ok(usuariService.saveUsuari(usuari));
    }

    @GetMapping
    public ResponseEntity<List<Usuari>> getAllUsuaris() {
        return ResponseEntity.ok(usuariService.getAllUsuaris());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuari> getUsuariById(@PathVariable Long id) {
        return ResponseEntity.ok(usuariService.getUsuariById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuari> updateUsuari(@PathVariable Long id, @RequestBody Usuari usuari) {
        return ResponseEntity.ok(usuariService.updateUsuari(id, usuari));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuari(@PathVariable Long id) {
        usuariService.deleteUsuari(id);
        return ResponseEntity.noContent().build();
    }
}
