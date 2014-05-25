/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetweb.modeles;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author Nicolas
 */
@Entity
public class Artiste implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    // Nom de l'artiste (ou du groupe)
    private String nom;
    //private String resume;


    private String image;
    /*@ManyToMany
    @JoinTable(name = "MORCEAU_ARTISTE",
            joinColumns = {
                @JoinColumn(name = "MORCEAU_PK", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "ARTISTE_PK", referencedColumnName = "id")})*/

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    // Morceaux joués par l'artiste
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER, mappedBy="artiste")
    private Collection<Morceau> morceaux;
    

    
    public Artiste(){}
    // Constructeur
    public Artiste(String nom, String image) {
        this.nom = nom;
        this.image=image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
   /* public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }*/
    
    public Collection<Morceau> getMorceaux() {
        return morceaux;
    }

    public void setMorceaux(Collection<Morceau> morceaux) {
        this.morceaux = morceaux;
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
        if (!(object instanceof Artiste)) {
            return false;
        }
        Artiste other = (Artiste) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projetweb.modeles.Artiste[ id=" + id + " ]";
    }
    
}
