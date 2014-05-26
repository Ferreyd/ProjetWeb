/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetweb.modeles;

import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
public class Morceau implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // Titre du morceau
    private String titre;

    // Instruments qu'on retrouve dans ce morceau
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "morceaux")
    @JoinTable(name = "MORCEAU_INSTRUMENT",
            joinColumns = {
                @JoinColumn(name = "MORCEAU_PK", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "INSTRUMENT_PK", referencedColumnName = "id")})
    private Collection<Instrument> instruments = new ArrayList<Instrument>();
    
    // Pistes qui composent le morceau
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "morceau")
    private Collection<Piste> pistes;

    @ManyToOne
    private Artiste artiste;

    @ManyToOne
    private Genre genre;

    // Année de sortie du morceau
    private int annee;

    public Morceau() {
    }

    public Morceau(String titre) {
        this.titre = titre;

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @XmlTransient
    public Collection<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Collection<Piste> getPistes() {
        return pistes;
    }

    public void setPistes(Collection<Piste> pistes) {
        this.pistes = pistes;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
        if (!(object instanceof Morceau)) {
            return false;
        }
        Morceau other = (Morceau) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projetweb.modeles.Morceau[ id=" + id + " ]";
    }

}
