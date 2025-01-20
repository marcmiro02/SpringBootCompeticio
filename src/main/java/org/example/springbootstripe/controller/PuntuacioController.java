package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Puntuacio;
import org.example.springbootstripe.services.PuntuacioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntuacions")
public class PuntuacioController {

    @Autowired
    private PuntuacioService puntuacioService;

    @PostMapping
    public ResponseEntity<Puntuacio> createPuntuacio(@RequestBody Puntuacio puntuacio) {
        return ResponseEntity.ok(puntuacioService.savePuntuacio(puntuacio));
    }

    @GetMapping
    public ResponseEntity<List<Puntuacio>> getAllPuntuacions() {
        return ResponseEntity.ok(puntuacioService.getAllPuntuacions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Puntuacio> getPuntuacioById(@PathVariable Long id) {
        return ResponseEntity.ok(puntuacioService.getPuntuacioById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuntuacio(@PathVariable Long id) {
        puntuacioService.deletePuntuacio(id);
        return ResponseEntity.noContent().build();
    }
}