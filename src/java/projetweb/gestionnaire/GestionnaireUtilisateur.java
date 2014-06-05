/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetweb.gestionnaire;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projetweb.modeles.Abonnement;
import projetweb.modeles.Morceau;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@Stateless
public class GestionnaireUtilisateur {

    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

    public void creerUtilisateursDeTest() {
        creeUtilisateur("John", "Lennon", "jlennon", "mdp");
        creeUtilisateur("Paul", "Mac Cartney", "pmc", "mdp");
        creeUtilisateur("Ringo", "Starr", "rstarr", "mdp");
        creeUtilisateur("Georges", "Harisson", "georgesH", "mdp");
    }

    public Utilisateur creeUtilisateur(String nom, String prenom, String login, String mdp) {
        Utilisateur u = new Utilisateur(nom, prenom, login, mdp);
        u.setAbonnement(getAbonnementParNom("gratuit")); //Abonnement par défaut
        em.persist(u);
        this.ajouteMorceau(login, this.ajouteMorceauxGratuit()); //Ajoute les morceaux gratuit;
        return u;
    }

    public Collection<Utilisateur> getAllUsers() {
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select u from Utilisateur u");
        return q.getResultList();
    }

    /**
     * Cherche les utilisateurs pas leur login
     *
     * @param login login de l'utilisateur
     * @return la liste des utilisateurs ayant le même login
     */
    public List<Utilisateur> chercherParLogin(String login) {
        Query q = em.createQuery("select u from Utilisateur u where u.login = '" + login + "'");
        return q.getResultList();
    }

    public void modifierUtilisateur(String prenom, String nom, String login) {
        Query q = em.createQuery("update Utilisateur u "
                + "set u.firstname = '" + prenom + "' , " + "u.lastname = '" + nom + "' "
                + "where u.login = '" + login + "'");
        int numUpdates = q.executeUpdate();
    }

    /**
     * Supprimer un utilisateur en le selectionnant par son login
     *
     * @param login Le login de l'utilisateur à supprimer
     */
    public void supprimerUtilisateur(String login) {

        Utilisateur u = this.chercherParLogin(login).get(0); // on prend l'utilisateur qu'on recherche par le login
        em.remove(u); // on le supprime  

    }

    public Abonnement getAbonnementParNom(String nom) {
        Query q = em.createQuery("select a from Abonnement a where a.nom = '" + nom + "'");
        return (Abonnement) q.getResultList().get(0);
    }

    public Collection<Abonnement> getAllAbonnement() {
        Query q = em.createQuery("select a from Abonnement a");
        return q.getResultList();
    }

    /**
     * Methode de test qui sert à savoir s'il y a des abonnements dans la abse
     * de données
     *
     * @return true s'il y a des abonnements, false sinon
     */
    public boolean testAbonnement() {
        Collection<Abonnement> test = getAllAbonnement();
        if (!test.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Ajoute un abonnement à l'utilisateur
     *
     * @param login le login de l'utilisateur
     * @param idAbonnement L'id de l'abonnement
     * @return l'utilisateur abonné
     */
    public Utilisateur ajouteAbonnement(String login, String idAbonnement) {
        Abonnement a = getAbonnementParId(idAbonnement);
        Utilisateur u = chercherParLogin(login).get(0);
        u.setAbonnement(a);
        // u.setFinAbonnement(DateTime.now().plusDays(a.getDuree()).toDate()); //On ajoute la durée de l'abonnement       
        em.persist(u);
        return u;
    }

    public Abonnement getAbonnementParId(String id) {       
        return em.find(Abonnement.class, Long.valueOf(id));

    }


    public Utilisateur getUtilisateurParId(String id) {
        return em.find(Utilisateur.class, Integer.valueOf(id));
    }

    public int getIdUtilisateurParLogin(String login) {
        Query q = em.createQuery("select u.id from Utilisateur u where u.login = '" + login + "'");

        return (int) q.getResultList().get(0);
    }

    public void creerAbonnementsTest() {
        creeAbonnement("gratuit", 999, 0);
        creeAbonnement("duo", 2, 0);
        creeAbonnement("quinzaine", 15, 0);
        creeAbonnement("cent jours", 100, 0);
    }

    public Abonnement creeAbonnement(String nom, int duree, int prix) {
        Abonnement a = new Abonnement(nom, duree, prix);
        em.persist(a);
        return a;
    }

    /**
     * Verifie si un utilisateur existe dans la base de donnée Si on rentre
     * admin admin, on creer l'utilisateur admin s'il n'existe pas dans la base
     * de donnée
     *
     * @param login
     * @param mdp
     * @return
     */
    public boolean userExists(String login, String mdp) {
        Query q = em.createQuery("select u from Utilisateur u where u.login = '" + login + "' and u.mdp = '" + mdp + '"');
        if (q.getResultList().size() > 0) {
            return true;
        }
        if (login.equals("admin") && mdp.equals("admin")) {
            if (chercherParLogin("admin").size() == 0) {
                creeUtilisateur("admin", "admin", "admin", "admin");
            }
            return true;
        }
        return false;
    }

    public Abonnement getAbonnementUtilisateur(String login) {
        Query q = em.createQuery("select a from Abonnement a, Utilisateur u where u.login = '" + login + "'");
        return (Abonnement) q.getResultList().get(0);
    }

    /**
     *
     * @param login
     * @param listeMorceaux
     * @return
     */
    public Utilisateur ajouteMorceau(String login, Set<Morceau> listeMorceaux) {
        Utilisateur u = this.chercherParLogin(login).get(0);
        u.setAchats(listeMorceaux);
        em.merge(u);
        return u;
    }

    /**
     * Selectionne les morceaux gratuit
     *
     * @return les 3 morceaux gratuits
     */
    private Set<Morceau> ajouteMorceauxGratuit() {
        Query q = em.createQuery("select m from Morceau m where m.titre LIKE 'Highway To hell (live)' OR m.titre LIKE 'We Are The Champions' OR m.titre LIKE 'Paint It Black'");
        Collection<Morceau> m = q.getResultList();
        Set<Morceau> set = new HashSet<>();
        set.addAll(m);
        return set;
    }

    /**
     * Retourne la différence entre la date de prise d'abonnement et la date
     * d'aujourd'hui
     *
     * @param id l'id de l'utilisateur
     * @return True sur la date est dépassé
     */
    public boolean testAbonnementValide(String id) {
        Utilisateur u = this.getUtilisateurParId(id);
        Date now = new Date();
        if (now.compareTo(u.getFinAbonnement()) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Donne le temps restant à un utilisateur
     *
     * @param id ID de l'utilisateur
     * @return le temps restant
     */
    public String tempsRestant(String id) {
        Utilisateur u = this.getUtilisateurParId(id);
        String res = "";
        return res;

    }

}
