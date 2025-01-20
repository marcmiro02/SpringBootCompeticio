package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Competicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompeticioRepository extends JpaRepository<Competicio, Long> {
}