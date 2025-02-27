package org.example.springbootstripe.repository;

import java.util.List;

import org.example.springbootstripe.model.Equip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipRepository extends JpaRepository<Equip, Long> {
    List<Equip> findByIdUsuari(Long idUsuari);
}