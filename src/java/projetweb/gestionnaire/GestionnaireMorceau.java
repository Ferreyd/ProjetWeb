/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetweb.gestionnaire;

import javax.ejb.Stateless;
import java.util.*;

import javax.persistence.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import projetweb.modeles.*;

/**
 *
 * @author Nicolas
 */
@Stateless
public class GestionnaireMorceau {

    @PersistenceContext
    private EntityManager em;
    
    public Morceau creerMorceau(String titre, int annee /*Collection<Piste>pistes*/){
        Morceau m = new Morceau(titre, annee);
        //m.setPistes(pistes);
        em.persist(m);
        return m;
    }
    
    public Piste creerPiste(String nom, Morceau m){
        Piste p = new Piste(nom);
        p.setMorceau(m);
        em.persist(p);
        return p;
    }
  
    public void ajouterMorceauAvecPistes(){
 
        Morceau m1 = creerMorceau("Highway To Hell", 1979);
        Morceau m2 = creerMorceau("We Are The Champions", 1977);
        
        creerPiste("Bass.mp3", m1);
        creerPiste("Drums 2 Bass drum.mp3", m1);
        creerPiste("Drums 3 snare.mp3", m1);
        creerPiste("Drums 4 snare.mp3", m1);
        creerPiste("Drums 5 hi-hat cymbals.mp3", m1);
        creerPiste("Drums 6 hi-hat cymbals.mp3", m1);
        creerPiste("Drums1 bass drum.mp3", m1);
        
        creerPiste("Bass.mp3", m2);
        creerPiste("Guitare.mp3", m2);
        creerPiste("Piano.mp3", m2);
        creerPiste("Voix.mp3", m2);
        /*p1.add(creerPiste("Bass.mp3"));
        p1.add(creerPiste("Drums 2 Bass drum.mp3"));
        p1.add(creerPiste("Drums 3 snare.mp3"));
        p1.add(creerPiste("Drums 4 snare.mp3"));
        p1.add(creerPiste("Drums 5 hi-hat cymbals.mp3"));
        p1.add(creerPiste("Drums 6 hi-hat cymbals.mp3"));
        p1.add(creerPiste("Drums1 bass drum.mp3"));
        
        p2.add(creerPiste("Bass.mp3"));
        p2.add(creerPiste("Guitare.mp3"));
        p2.add(creerPiste("Piano.mp3"));
        p2.add(creerPiste("Voix.mp3"));*/
        

    }  
    public Collection<Morceau> getAllMorceaux(){
        Query q = em.createQuery("select m from Morceau m");
        return q.getResultList();
    }
    
    public Collection<Piste> getPistesByMorceau(String morceau_id){
        Query q = em.createQuery("select m from Morceau m where m.id ="+morceau_id+"");
        Morceau m = (Morceau) q.getSingleResult();
        return m.getPistes();       
    }
    
    public String getMorceauById(String morceau_id){
        Query q = em.createQuery("select m.titre from Morceau m where m.id ="+morceau_id+"");
        return (String)q.getSingleResult();
    }
 
 
}
