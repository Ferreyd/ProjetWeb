/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetweb.modeles;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
@XmlRootElement
public class Instrument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    // Nom de l'instrument
    private String nom;

    // Morceaux dans lesquels on retrouve l'instrument
    @ManyToMany
    @JoinTable(name = "MORCEAU_INSTRUMENT",
            joinColumns = {
                @JoinColumn(name = "MORCEAU_PK", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "INSTRUMENT_PK", referencedColumnName = "id")})
    private Collection<Morceau> morceaux = new ArrayList<Morceau>();

    @XmlTransient
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

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
        if (!(object instanceof Instrument)) {
            return false;
        }
        Instrument other = (Instrument) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projetweb.modeles.Instrument[ id=" + id + " ]";
    }
    
}
