package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Equip;
import org.example.springbootstripe.services.EquipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equips")
public class EquipController {

    @Autowired
    private EquipService equipService;

    @PostMapping
    public ResponseEntity<Equip> createEquip(@RequestBody Equip equip) {
        return ResponseEntity.ok(equipService.saveEquip(equip));
    }

    @GetMapping
    public ResponseEntity<List<Equip>> getAllEquips() {
        return ResponseEntity.ok(equipService.getAllEquips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equip> getEquipById(@PathVariable Long id) {
        return ResponseEntity.ok(equipService.getEquipById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquip(@PathVariable Long id) {
        equipService.deleteEquip(id);
        return ResponseEntity.noContent().build();
    }
}