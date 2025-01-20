package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Pagament;
import org.example.springbootstripe.services.PagamentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagaments")
public class PagamentController {

    @Autowired
    private PagamentService pagamentService;

    @PostMapping
    public ResponseEntity<Pagament> createPagament(@RequestBody Pagament pagament) {
        return ResponseEntity.ok(pagamentService.savePagament(pagament));
    }

    @GetMapping
    public ResponseEntity<List<Pagament>> getAllPagaments() {
        return ResponseEntity.ok(pagamentService.getAllPagaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagament> getPagamentById(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentService.getPagamentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagament(@PathVariable Long id) {
        pagamentService.deletePagament(id);
        return ResponseEntity.noContent().build();
    }
}