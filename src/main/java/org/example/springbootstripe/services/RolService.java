package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRols() {
        return rolRepository.findAll();
    }

    public Rol getRolById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol saveRol(Rol usuari) {
        return rolRepository.save(usuari);
    }

    public Rol updateRol(Long id, Rol usuari) {
        Rol rolToUpdate = rolRepository.findById(id).orElse(null);
        if (rolToUpdate != null) {
            rolToUpdate.setNom(usuari.getNom());
            return rolRepository.save(rolToUpdate);
        }
        return null;
    }

    public void deleteRol(Long id) {
        rolRepository.deleteById(id);
    }
}