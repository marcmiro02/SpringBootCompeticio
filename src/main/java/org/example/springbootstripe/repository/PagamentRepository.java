package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Pagament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentRepository extends JpaRepository<Pagament, Long> {
}