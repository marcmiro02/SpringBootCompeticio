package org.example.springbootstripe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equips")
public class Equip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom_equip", nullable = false)
    private String nomEquip;
    @Column(name = "nom_participant", nullable = false)
    private String nomParticipant;

    @Column(name = "id_equip")
    private Long idEquip;

    @Column(name = "id_usuari", nullable = false)
    private Long idUsuari;

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

    public Long getIdEquip() {
        return idEquip;
    }
    public void setIdEquip(Long idEquip) {
        this.idEquip = idEquip;
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
}

