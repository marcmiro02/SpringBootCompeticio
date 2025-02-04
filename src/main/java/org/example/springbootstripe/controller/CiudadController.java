package org.example.springbootstripe.controller;

import org.example.springbootstripe.model.Ciudad;
import org.example.springbootstripe.services.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/ciudades")
    public List<Ciudad> getCiudades(@RequestParam Long provinciaId) {
        return ciudadService.getCiudadesByProvincia(provinciaId);
    }
}