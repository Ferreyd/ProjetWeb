/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetweb.gestionnaire;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@Stateless
public class GestionnaireUtilisateur {

    @PersistenceContext
    private EntityManager em;


    
    public void creerUtilisateursDeTest() {  
        creeUtilisateur("John", "Lennon", "jlennon","mdp");  
        creeUtilisateur("Paul", "Mac Cartney", "pmc","mdp");  
        creeUtilisateur("Ringo", "Starr", "rstarr","mdp");  
        creeUtilisateur("Georges", "Harisson", "georgesH","mdp");  
    } 
    
    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String mdp)
    {
        Utilisateur u = new Utilisateur(nom, prenom, login, mdp);
        em.persist(u);
        return u;
    }
    
     public Collection<Utilisateur> getAllUsers() {  
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select u from Utilisateur u");  
        return q.getResultList();  
    }  
     
         // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
