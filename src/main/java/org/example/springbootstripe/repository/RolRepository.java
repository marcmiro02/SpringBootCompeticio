package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}