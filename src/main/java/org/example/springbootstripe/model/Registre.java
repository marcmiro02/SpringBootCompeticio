package org.example.springbootstripe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "registres")
public class Registre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_usuari", nullable = false)
    private Long idUsuari;

    @Column(name = "id_competicio", nullable = false)
    private Long idCompeticio;

    @Column(name = "id_equip")
    private Long idEquip;

    @Column(name = "id_pagament")
    private Long idPagament;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(Long idUsuari) {
        this.idUsuari = idUsuari;
    }

    public Long getIdCompeticio() {
        return idCompeticio;
    }

    public void setIdCompeticio(Long idCompeticio) {
        this.idCompeticio = idCompeticio;
    }

    public Long getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
    }

    public Long getIdPagament() {
        return idPagament;
    }

    public void setIdPagament(Long idPagament) {
        this.idPagament = idPagament;
    }
}

