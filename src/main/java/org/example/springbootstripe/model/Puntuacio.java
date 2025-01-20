package org.example.springbootstripe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "puntuacions")
public class Puntuacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_registre", nullable = false)
    private Long idRegistre;

    @Column(name = "puntuacio", nullable = false)
    private Integer puntuacio;

    @Column(name = "id_usuari", nullable = false)
    private Long idUsuari;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRegistre() {
        return idRegistre;
    }

    public void setIdRegistre(Long idRegistre) {
        this.idRegistre = idRegistre;
    }

    public Integer getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(Integer puntuacio) {
        this.puntuacio = puntuacio;
    }

    public Long getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(Long idUsuari) {
        this.idUsuari = idUsuari;
    }
}

