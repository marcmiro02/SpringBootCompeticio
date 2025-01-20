package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Equip;
import org.example.springbootstripe.repository.EquipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipService {

    @Autowired
    private EquipRepository equipRepository;

    public Equip saveEquip(Equip equip) {
        return equipRepository.save(equip);
    }

    public List<Equip> getAllEquips() {
        return equipRepository.findAll();
    }

    public Equip getEquipById(Long id) {
        Optional<Equip> equip = equipRepository.findById(id);
        return equip.orElse(null);
    }

    public void deleteEquip(Long id) {
        if (equipRepository.existsById(id)) {
            equipRepository.deleteById(id);
        }
    }
}
