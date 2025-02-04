package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByProvinciaId(Long provinciaId);
}
