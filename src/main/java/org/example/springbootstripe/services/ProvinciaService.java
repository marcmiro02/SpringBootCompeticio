package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Provincia;
import org.example.springbootstripe.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Provincia> getAllProvincias() {
        return provinciaRepository.findAll();
    }
}
