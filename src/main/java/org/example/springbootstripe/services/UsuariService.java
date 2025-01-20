package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    public List<Usuari> getAllUsuaris() {
        return usuariRepository.findAll();
    }

    public Usuari getUsuariById(Long id) {
        return usuariRepository.findById(id).orElse(null);
    }

    public Usuari saveUsuari(Usuari usuari) {
        return usuariRepository.save(usuari);
    }

    public Usuari updateUsuari(Long id, Usuari usuari) {
        Usuari usuariToUpdate = usuariRepository.findById(id).orElse(null);
        if (usuariToUpdate != null) {
            usuariToUpdate.setNom(usuari.getNom());
            usuariToUpdate.setCognoms(usuari.getCognoms());
            usuariToUpdate.setNomUsuari(usuari.getNomUsuari());
            usuariToUpdate.setEmail(usuari.getEmail());
            usuariToUpdate.setContrasenya(usuari.getContrasenya());
            usuariToUpdate.setIdRol(usuari.getIdRol());
            return usuariRepository.save(usuariToUpdate);
        }
        return null;
    }

    public void deleteUsuari(Long id) {
        usuariRepository.deleteById(id);
    }
}