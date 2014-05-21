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
import java.io.*;

/**
 *
 * @author Nicolas
 */
@Stateless
public class GestionnaireMorceau {

    @PersistenceContext
    private EntityManager em;
    
    public Morceau creerMorceau(String titre, Artiste artiste){
        Morceau m = new Morceau(titre);
        m.setArtiste(artiste);
        em.persist(m);
        return m;
    }
    
    public Piste creerPiste(String nom, Morceau m){
        Piste p = new Piste(nom);
        p.setMorceau(m);
        em.persist(p);
        return p;
    }
  
    public Artiste creerArtiste(String nom){
        Artiste a = new Artiste(nom);
        em.persist(a);
        return a;
    }
    public boolean artisteExiste(String nom){
        Query q = em.createQuery("select a from Artiste a where a.nom ='"+nom+"'");
        if(q.getResultList().isEmpty()) return false;
        else return true;
    }
    
    public Artiste getArtiste(String nom){
        /*if(artisteExiste(nom)==false){
           Artiste a = new Artiste(nom);
           return a;
        }else{
           Query q = em.createQuery("select a from Artiste a where a.nom ="+nom+"");
           return q.getResultList();
           return a;
        }*/
        Query q = em.createQuery("select a from Artiste a where a.nom ='"+nom+"'");
        if(q.getResultList().isEmpty()){
            Artiste a = new Artiste(nom);
            System.out.println("EXISTE PAS:" + a.getNom());
            return a;
        }
        else{
            Artiste a = (Artiste)q.getSingleResult();
            System.out.println("EXISTE:" + a.getNom());
            return a;
        }       
    }
    public void ajouterMorceauAvecPistes() throws Exception{
 
        /*Artiste a1 = creerArtiste("Ac-Dc");
        Artiste a2 = creerArtiste("Queen");
        
        Morceau m1 = creerMorceau("Highway To Hell", 1979, creerArtiste("Ac-Dc"));
        Morceau m2 = creerMorceau("We Are The Champions", 1977, creerArtiste("Queen"));
        
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
        creerPiste("Voix.mp3", m2);*/
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
        
        String data = "C:\\Users\\Jeje\\Documents\\NetBeansProjects\\ProjetWeb\\web\\resources\\liste.txt";
        FileInputStream fis = null;
        BufferedReader br = null;
        try{
            String ligne;
            fis = new FileInputStream(data);
            br = new BufferedReader(new InputStreamReader(fis));
            
            Morceau m = null;
            Artiste a = null;
            //int countInstru = 0;
            
            while((ligne = br.readLine())!= null){
                
                if(ligne.startsWith("./")){
                    //System.out.println(ligne);
                    String strar[] = ligne.split("./");
                   // System.out.println(strar[1]);
                    String musique[] = strar[1].split(" - ");
                   
                    String artiste = musique[0];
                    String titre[] = musique[1].split(":");
                    String morceau = titre[0];
                    m = new Morceau(morceau);
                    //m.setTitre(morceau);
                    a = getArtiste(artiste);
                    m.setArtiste(a);

                }
                    em.persist(a);
                    em.persist(m);
            }

        }
        catch(FileNotFoundException exc)
        {
          System.out.println("Erreur d'ouverture");
        }
 
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
    public Artiste getInfosArtiste(String id){
        Query q = em.createQuery("select a from Artiste a where a.id = "+id+"");
        return (Artiste)q.getSingleResult();
    }
    
    // POUR LA RECHERCHE
    public Collection<Morceau> getMorceauByArtiste(String nom){
        Query q = em.createQuery("select m from Morceau m where m.nom = "+nom+"");
        return q.getResultList();
    }
 

 
}
