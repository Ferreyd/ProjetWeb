/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetweb.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Nicolas
 */
@Entity
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nom;
    private String prenom;
    @OneToOne
    private Abonnement abonnement;
    private String login;
    private String mdp;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_MORCEAU",
            joinColumns = {
                @JoinColumn(name = "UTILISATEUR_PK", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "MORCEAU_PK", referencedColumnName = "id")})
    
    private Collection<Morceau> morceaux = new ArrayList<Morceau>();

    /*
     // Instruments qu'on retrouve dans ce morceau
     @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
     @JoinTable(name = "MORCEAU_INSTRUMENT",
     joinColumns = {
     @JoinColumn(name = "MORCEAU_PK", referencedColumnName = "id")},
     inverseJoinColumns = {
     @JoinColumn(name = "INSTRUMENT_PK", referencedColumnName = "id")})
     private Collection<Instrument> instruments = new ArrayList<Instrument>();
     */
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projetweb.modeles.Utilisateur[ id=" + id + " ]";
    }

    public Collection<Morceau> getMorceaux() {
        return morceaux;
    }

    public void setMorceaux(Collection<Morceau> morceaux) {
        this.morceaux = morceaux;
    }
    
    

}
