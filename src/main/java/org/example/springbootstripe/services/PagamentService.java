package org.example.springbootstripe.services;

import org.example.springbootstripe.model.Pagament;
import org.example.springbootstripe.repository.PagamentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentService {

    @Autowired
    private PagamentRepository pagamentRepository;

    public Pagament savePagament(Pagament pagament) {
        return pagamentRepository.save(pagament);
    }

    public List<Pagament> getAllPagaments() {
        return pagamentRepository.findAll();
    }

    public Pagament getPagamentById(Long id) {
        Optional<Pagament> pagament = pagamentRepository.findById(id);
        return pagament.orElse(null);
    }

    public void deletePagament(Long id) {
        if (pagamentRepository.existsById(id)) {
            pagamentRepository.deleteById(id);
        }
    }
}

