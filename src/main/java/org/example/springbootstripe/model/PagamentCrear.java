package org.example.springbootstripe.model;

import jakarta.persistence.*;
import org.example.springbootstripe.model.Competicio;
import org.example.springbootstripe.model.Usuari;

@Entity
@Table(name = "pagaments_crear")
public class PagamentCrear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagament", nullable = false)
    private Integer idPagament;

    @Column(name = "preu")
    private Double preu;

    @ManyToOne
    @JoinColumn(name = "id_competicio", referencedColumnName = "id")
    private Competicio competicio;

    @ManyToOne
    @JoinColumn(name = "id_usuari", referencedColumnName = "id")
    private Usuari usuari;

    // Constructor vacío
    public PagamentCrear() {}

    // Getters y Setters
    public Integer getIdPagament() {
        return idPagament;
    }

    public void setIdPagament(Integer idPagament) {
        this.idPagament = idPagament;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public Competicio getCompeticio() {
        return competicio;
    }

    public void setCompeticio(Competicio competicio) {
        this.competicio = competicio;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}

