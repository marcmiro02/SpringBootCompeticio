package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuaris")
public class UsuariController {

    @Autowired
    private UsuariService usuariService;

    @GetMapping
    public List<Usuari> getAllUsuaris() {
        return usuariService.getAllUsuaris();
    }

    @GetMapping("/{id}")
    public Usuari getUsuariById(@PathVariable Long id) {
        return usuariService.getUsuariById(id);
    }

    @PostMapping
    public Usuari createUsuari(@RequestBody Usuari usuari) {
        return usuariService.saveUsuari(usuari);
    }

    @PutMapping("/{id}")
    public Usuari updateUsuari(@PathVariable Long id, @RequestBody Usuari usuari) {
        Usuari existingUsuari = usuariService.getUsuariById(id);
        if (existingUsuari != null) {
            existingUsuari.setNom(usuari.getNom());
            existingUsuari.setEmail(usuari.getEmail());
            existingUsuari.setContrasenya(usuari.getContrasenya());
            return usuariService.saveUsuari(existingUsuari);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUsuari(@PathVariable Long id) {
        usuariService.deleteUsuari(id);
    }
}