package org.example.springbootstripe.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "competicions")
public class Competicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "capacitat", nullable = false)
    private Integer capacitat;

    @Column(name = "capacitat_equip")
    private Integer capacitatEquip;  // Campo opcional para equipos

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "data_inici", nullable = false)
    private LocalDate dataInici;

    @Column(name = "data_fi", nullable = false)
    private LocalDate dataFi;

    @Column(name = "edat_min")
    private Integer edatMin;

    @Column(name = "edat_max")
    private Integer edatMax;

    @Lob
    @Column(name = "foto_portada")
    private byte[] fotoPortada;

    @Column(name = "preu", nullable = false)
    private Double preu;

    @Column(name = "ubicacio")
    private String ubicacio;

    @Column(name = "poblacio", nullable = false)
    private String poblacio;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    // Nueva propiedad tipus
    @Enumerated(EnumType.STRING)
    @Column(name = "tipus", nullable = false)
    private Tipus tipus;  // El tipo de inscripción (INDIVIDUAL o EQUIP)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Integer getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(Integer capacitat) {
        this.capacitat = capacitat;
    }

    public Integer getCapacitatEquip() {
        return capacitatEquip;
    }

    public void setCapacitatEquip(Integer capacitatEquip) {
        this.capacitatEquip = capacitatEquip;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataInici() {
        return dataInici;
    }

    public void setDataInici(LocalDate dataInici) {
        this.dataInici = dataInici;
    }

    public LocalDate getDataFi() {
        return dataFi;
    }

    public void setDataFi(LocalDate dataFi) {
        this.dataFi = dataFi;
    }

    public Integer getEdatMin() {
        return edatMin;
    }

    public void setEdatMin(Integer edatMin) {
        this.edatMin = edatMin;
    }

    public Integer getEdatMax() {
        return edatMax;
    }

    public void setEdatMax(Integer edatMax) {
        this.edatMax = edatMax;
    }

    public byte[] getFotoPortada() {
        return fotoPortada;
    }

    public void setFotoPortada(byte[] fotoPortada) {
        this.fotoPortada = fotoPortada;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Tipus getTipus() {
        return tipus;
    }

    public void setTipus(Tipus tipus) {
        this.tipus = tipus;
    }
}

