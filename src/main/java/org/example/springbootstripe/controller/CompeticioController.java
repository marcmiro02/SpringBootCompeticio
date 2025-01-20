package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.services.CompeticioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competicions")
public class CompeticioController {

    @Autowired
    private CompeticioService competicioService;

    @PostMapping
    public ResponseEntity<Competicio> createCompeticio(@RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.saveCompeticio(competicio));
    }

    @GetMapping
    public ResponseEntity<List<Competicio>> getAllCompeticions() {
        return ResponseEntity.ok(competicioService.getAllCompeticions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competicio> getCompeticioById(@PathVariable Long id) {
        return ResponseEntity.ok(competicioService.getCompeticioById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competicio> updateCompeticio(@PathVariable Long id, @RequestBody Competicio competicio) {
        return ResponseEntity.ok(competicioService.updateCompeticio(id, competicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompeticio(@PathVariable Long id) {
        competicioService.deleteCompeticio(id);
        return ResponseEntity.noContent().build();
    }
}