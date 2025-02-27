package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariRepository extends JpaRepository<Usuari, Long> {
    Usuari findByNomUsuari(String nomUsuari);
    Usuari findByEmail(String email);
    


}