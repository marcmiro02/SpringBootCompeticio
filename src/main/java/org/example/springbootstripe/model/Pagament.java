package org.example.springbootstripe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagaments")
public class Pagament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_usuari", nullable = false)
    private Long idUsuari;

    @Column(name = "quantitat", nullable = false)
    private BigDecimal quantitat;

    @Column(name = "estat", nullable = false)
    private String estat;

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

    public BigDecimal getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(BigDecimal quantitat) {
        this.quantitat = quantitat;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }
}

