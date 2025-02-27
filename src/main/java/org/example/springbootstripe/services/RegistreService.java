package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Registre;
import org.example.springbootstripe.repository.RegistreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistreService {

    @Autowired
    private RegistreRepository registreRepository;

    public Registre saveRegistre(Registre registre) {
        return registreRepository.save(registre);
    }

    public List<Registre> getAllRegistres() {
        return registreRepository.findAll();
    }

    public Registre getRegistreById(Long id) {
        Optional<Registre> registre = registreRepository.findById(id);
        return registre.orElse(null);
    }

    public void deleteRegistre(Long id) {
        if (registreRepository.existsById(id)) {
            registreRepository.deleteById(id);
        }
    }
    public boolean isUsuariRegistrat(Long idUsuari, Long idCompeticio) {
        return registreRepository.existsByIdUsuariAndIdCompeticio(idUsuari, idCompeticio);
    }
}
