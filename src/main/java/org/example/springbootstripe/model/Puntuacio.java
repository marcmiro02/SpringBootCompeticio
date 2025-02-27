package org.example.springbootstripe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "puntuacions")
public class Puntuacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "puntuacio", nullable = false)
    private String puntuacio;

    @ManyToOne
    @JoinColumn(name = "id_competicio", nullable = false)
    private Competicio competicio;

    @ManyToOne
    @JoinColumn(name = "id_equip", nullable = false)
    private Equip equip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(String puntuacio) {
        this.puntuacio = puntuacio;
    }

    public Competicio getCompeticio() {
        return competicio;
    }

    public void setCompeticio(Competicio competicio) {
        this.competicio = competicio;
    }

    public Equip getEquip() {
        return equip;
    }

    public void setEquip(Equip equip) {
        this.equip = equip;
    }
}