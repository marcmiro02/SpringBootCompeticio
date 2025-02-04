package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Ciudad;
import org.example.springbootstripe.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Ciudad> getCiudadesByProvincia(Long provinciaId) {
        return ciudadRepository.findByProvinciaId(provinciaId);
    }
}
