package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Usuari;
import org.example.springbootstripe.repository.UsuariRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsuariRepository usuariRepository;

    public UserDetailsServiceImpl(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuari> optionalUsuari = Optional.ofNullable(usuariRepository.findByNomUsuari(username));

        if (!optionalUsuari.isPresent()) {
            throw new UsernameNotFoundException("Usuari no trobat");
        }

        Usuari usuari = optionalUsuari.get();

        // Verifica que el usuario tenga un rol
        if (usuari.getRol() == null) {
            throw new UsernameNotFoundException("El usuario no tiene un rol asignado.");
        }

        String[] roles = new String[]{usuari.getRol().getNom()}; // Asume que el Rol tiene un campo 'nom'

        // Construye y devuelve el UserDetails
        return User.builder()
                .username(usuari.getNomUsuari())
                .password(usuari.getContrasenya())
                .roles(roles) // Asigna el rol al usuario
                .build();
    }


}


