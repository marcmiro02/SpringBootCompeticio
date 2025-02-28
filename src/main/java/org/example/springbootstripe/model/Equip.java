package org.example.springbootstripe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "equips")
public class Equip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom_equip", nullable = true)
    private String nomEquip;

    @Column(name = "nom_participant", nullable = false)
    private String nomParticipant;

    @Column(name = "id_usuari", nullable = false)
    private Long idUsuari;

    @Column(name = "email", nullable = true)
    private String email;

    @OneToMany(mappedBy = "equip")
    private List<Puntuacio> puntuacions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEquip() {
        return nomEquip;
    }

    public void setNomEquip(String nomEquip) {
        this.nomEquip = nomEquip;
    }

    public String getNomParticipant() {
        return nomParticipant;
    }

    public void setNomParticipant(String nomParticipant) {
        this.nomParticipant = nomParticipant;
    }

    public Long getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(Long idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Puntuacio> getPuntuacions() {
        return puntuacions;
    }

    public void setPuntuacions(List<Puntuacio> puntuacions) {
        this.puntuacions = puntuacions;
    }
}