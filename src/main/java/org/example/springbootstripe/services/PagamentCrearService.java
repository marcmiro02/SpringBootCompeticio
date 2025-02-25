package org.example.springbootstripe.services;

import org.example.springbootstripe.model.PagamentCrear;
import org.example.springbootstripe.repository.PagamentCrearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PagamentCrearService {

    @Autowired
    private PagamentCrearRepository pagamentCrearRepository;

    public PagamentCrear savePagament(PagamentCrear pagament) {
        return pagamentCrearRepository.save(pagament);
    }

    public Optional<PagamentCrear> getPagamentById(Integer id) {
        return pagamentCrearRepository.findById(id);
    }
}

