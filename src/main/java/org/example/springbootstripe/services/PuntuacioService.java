package org.example.springbootstripe.services;

import java.util.List;
import java.util.Optional;

import org.example.springbootstripe.model.Puntuacio;
import org.example.springbootstripe.repository.PuntuacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuntuacioService {

    @Autowired
    private PuntuacioRepository puntuacioRepository;

    public Puntuacio savePuntuacio(Puntuacio puntuacio) {
        return puntuacioRepository.save(puntuacio);
    }

    public List<Puntuacio> getAllPuntuacions() {
        return puntuacioRepository.findAll();
    }

    public Puntuacio getPuntuacioById(Long id) {
        Optional<Puntuacio> puntuacio = puntuacioRepository.findById(id);
        return puntuacio.orElse(null);
    }

    public void deletePuntuacio(Long id) {
        if (puntuacioRepository.existsById(id)) {
            puntuacioRepository.deleteById(id);
        }
    }

    public Optional<Puntuacio> findByCompeticioIdAndEquipIdUsuari(Long competicioId, Long idUsuari) {
        return puntuacioRepository.findByCompeticioIdAndEquipIdUsuari(competicioId, idUsuari);
    }
}