package org.example.springbootstripe.dto;

public class CompeticioDTO {
    private String nom;
    private String categoria;
    private String dataInici;
    private String fotoPortada; // Aquí guardaremos la imagen en Base64

    // Getters y Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDataInici() { return dataInici; }
    public void setDataInici(String dataInici) { this.dataInici = dataInici; }

    public String getFotoPortada() { return fotoPortada; }
    public void setFotoPortada(String fotoPortada) { this.fotoPortada = fotoPortada; }
}
