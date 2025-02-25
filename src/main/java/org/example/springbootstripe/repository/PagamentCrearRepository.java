package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.PagamentCrear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentCrearRepository extends JpaRepository<PagamentCrear, Integer> {
}