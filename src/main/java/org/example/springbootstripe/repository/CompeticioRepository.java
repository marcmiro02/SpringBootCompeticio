package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Competicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CompeticioRepository extends JpaRepository<Competicio, Long> {
    List<Competicio> findByDataFiAfter(LocalDate date);
}