package org.example.springbootstripe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuaris")
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "cognoms", nullable = false)
    private String cognoms;

    @Column(name = "nom_usuari", nullable = false)
    private String nomUsuari;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contrasenya", nullable = false)
    private String contrasenya;

    // En la base de datos almacenas solo la ID, así que el tipo puede ser int o long
    @Column(name = "id_rol", nullable = false)
    private int idRol;  // o Long, dependiendo de la base de datos


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

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    public String getNomComplet(){
        return this.nom + " " + this.cognoms;
    }


}
