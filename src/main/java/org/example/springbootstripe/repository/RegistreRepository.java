package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Registre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistreRepository extends JpaRepository<Registre, Long> {
}