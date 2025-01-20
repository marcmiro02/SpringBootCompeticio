package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Puntuacio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntuacioRepository extends JpaRepository<Puntuacio, Long> {
}