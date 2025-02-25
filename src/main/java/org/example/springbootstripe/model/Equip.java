package org.example.springbootstripe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equips")
public class Equip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

