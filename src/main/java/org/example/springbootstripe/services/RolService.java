package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Rol;
import org.example.springbootstripe.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public List<Rol> getAllRols() {
        return rolRepository.findAll();
    }

    public Rol getRolById(Long id) {
        Optional<Rol> rol = rolRepository.findById(Math.toIntExact(id));
        return rol.orElse(null);
    }

    public Rol updateRol(Integer id, Rol updatedRol) {
        if (rolRepository.existsById(id)) {
            updatedRol.setId(id);
            return rolRepository.save(updatedRol);
        }
        return null;
    }

    public void deleteRol(Integer id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
        }
    }
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(Math.toIntExact(id));
    }

}
