package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Registre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistreRepository extends JpaRepository<Registre, Long> {
    boolean existsByIdUsuariAndIdCompeticio(Long idUsuari, Long idCompeticio);
}