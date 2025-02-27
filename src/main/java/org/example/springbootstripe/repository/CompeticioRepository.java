package org.example.springbootstripe.repository;

import org.example.springbootstripe.model.Competicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CompeticioRepository extends JpaRepository<Competicio, Long> {
    List<Competicio> findByDataFiAfter(LocalDate date);

    List<Competicio> findTop8ByDataIniciAfterOrderByDataInici(LocalDate date);

    List<Competicio> findByDataFiBeforeOrderByDataFiDesc(LocalDate date);

    List<Competicio> findByDataFiAfterOrderByDataIniciAsc(LocalDate date);


    List<Competicio> findByIdUsuari(Long userId);

    @Query(value = "SELECT c.* FROM competicions c JOIN registres r ON c.id = r.id_competicio WHERE r.id_usuari = :userId", nativeQuery = true)
    List<Competicio> getCompeticionsByUserId(@Param("userId") Long userId);



}