package org.example.springbootstripe.dto;

public class CompeticioDTO {
    private Long id;
    private String nom;
    private String categoria;
    private String dataInici;
    private String dataFi;
    private String fotoPortada; // Aquí guardaremos la imagen en Base64
    private String ubicacio;
    private String poblacio;
    private String provincia;
    private String descripcio;
    private Integer capacitat;
    private Double preu;

    // Getters y Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDataInici() { return dataInici; }
    public void setDataInici(String dataInici) { this.dataInici = dataInici; }

    public String getFotoPortada() { return fotoPortada; }
    public void setFotoPortada(String fotoPortada) { this.fotoPortada = fotoPortada; }
    public String getDataFi() { return dataFi; }
    public void setDataFi(String dataFi) { this.dataFi = dataFi; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUbicacio() { return ubicacio; }
    public void setUbicacio(String ubicacio) { this.ubicacio = ubicacio; }

    public String getPoblacio() { return poblacio; }
    public void setPoblacio(String poblacio) { this.poblacio = poblacio; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDescripcio() { return descripcio; }
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }

    public Integer getCapacitat() { return capacitat; }
    public void setCapacitat(Integer capacitat) { this.capacitat = capacitat; }

    public Double getPreu() { return preu; }
    public void setPreu(Double preu) { this.preu = preu; }


}
