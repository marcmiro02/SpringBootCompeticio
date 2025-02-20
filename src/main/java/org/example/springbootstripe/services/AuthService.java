package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuariRepository usuarioRepository;

    public Usuari validateUser(String username, String password) {
        Usuari usuario = usuarioRepository.findByEmail(username);
        if (usuario != null && usuario.getContrasenya().equals(password)) {
            return usuario;
        }
        return null;
    }


    public boolean registerUser(Usuari usuario) {
        if (usuarioRepository.findByNomUsuari(usuario.getNomUsuari()) == null && usuarioRepository.findByEmail(usuario.getEmail()) == null) {
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}