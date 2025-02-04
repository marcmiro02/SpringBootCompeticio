package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
}