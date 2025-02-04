package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Provincia;
import org.example.springbootstripe.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping("/provincias")
    public List<Provincia> getProvincias() {
        return provinciaService.getAllProvincias();
    }
}