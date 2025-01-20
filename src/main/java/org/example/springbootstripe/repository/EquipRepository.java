package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Equip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipRepository extends JpaRepository<Equip, Long> {
}