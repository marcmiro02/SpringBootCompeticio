package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNom(String nom);
}