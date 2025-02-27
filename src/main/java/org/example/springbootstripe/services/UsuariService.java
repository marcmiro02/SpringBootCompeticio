package org.example.springbootstripe.services;

import java.util.List;
import java.util.Optional;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Usuari> existingUsuari = usuariRepository.findById(id);
        if (existingUsuari.isPresent()) {
            updatedUsuari.setId(id);
            return usuariRepository.save(updatedUsuari); // Guarda los cambios
        }
        return null;
    }

    public void deleteUsuari(Long id) {
        if (usuariRepository.existsById(id)) {
            usuariRepository.deleteById(id);
        }
    }

    public Usuari findByNomUsuari(String username) {
        return usuariRepository.findByNomUsuari(username);
    }

    public void updatePassword(Usuari usuari, String newPassword) {
        usuari.setContrasenya(newPassword);
        usuariRepository.save(usuari);
    }
}