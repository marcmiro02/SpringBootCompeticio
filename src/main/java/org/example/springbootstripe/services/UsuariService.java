package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    public Usuari saveUsuari(Usuari usuari) {
        return usuariRepository.save(usuari);
    }

    public List<Usuari> getAllUsuaris() {
        return usuariRepository.findAll();
    }

    public Usuari getUsuariById(Long id) {
        Optional<Usuari> usuari = usuariRepository.findById(id);
        return usuari.orElse(null);
    }

    public Usuari updateUsuari(Long id, Usuari updatedUsuari) {
        if (usuariRepository.existsById(id)) {
            updatedUsuari.setId(id);
            return usuariRepository.save(updatedUsuari);
        }
        return null;
    }

    public void deleteUsuari(Long id) {
        if (usuariRepository.existsById(id)) {
            usuariRepository.deleteById(id);
        }
    }
}
