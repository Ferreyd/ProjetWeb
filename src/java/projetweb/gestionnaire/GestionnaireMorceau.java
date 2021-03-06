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

    public Morceau creerMorceau(String titre, Artiste artiste) {
        Morceau m = new Morceau(titre);
        m.setArtiste(artiste);
        em.persist(m);
        return m;
    }

    public Piste creerPiste(String nom, Morceau m) {
        Piste p = new Piste(nom);
        p.setMorceau(m);
        em.persist(p);
        return p;
    }

    public boolean artisteExiste(String nom) {
        Query q = em.createQuery("select a from Artiste a where a.nom ='" + nom + "'");
        if (q.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private Artiste getArtiste(String nom, String image) {
        Query q = em.createQuery("select a from Artiste a where a.nom ='" + nom + "'");
        if (q.getResultList().isEmpty()) {
            Artiste a = new Artiste(nom, image);
            return a;
        } else {
            Artiste a = (Artiste) q.getSingleResult();
            return a;
        }
    }

    private Genre getGenre(String nom) {
        Query q = em.createQuery("select g from Genre g where g.nom ='" + nom + "'");
        if (q.getResultList().isEmpty()) {
            Genre g = new Genre(nom);
            return g;
        } else {
            Genre g = (Genre) q.getSingleResult();
            return g;
        }
    }

    private Instrument getInstrument(String nom) {
        Query q = em.createQuery("select i from Instrument i where i.nom ='" + nom + "'");
        if (q.getResultList().isEmpty()) {
            Instrument i = new Instrument(nom);
            return i;
        } else {
            Instrument i = (Instrument) q.getSingleResult();
            return i;
        }
    }

    public void ajouterMorceauAvecPistes() throws Exception {

        //String data = "C:\\Users\\Jeje\\Documents\\NetBeansProjects\\ProjetWeb\\web\\resources\\liste.txt";
        String data = "C:\\Users\\Nicolas\\Documents\\NetBeansProjects\\ProjetWeb\\web\\resources\\liste.txt";
        File f = new File(data);
        System.out.println("f = " + f.getAbsolutePath() +" " +f.getName());
        FileInputStream fis = null;
        BufferedReader br = null;
        try {
            String ligne;
            fis = new FileInputStream(f.getAbsolutePath());
            br = new BufferedReader(new InputStreamReader(fis));

            Morceau m = null;
            Artiste a = null;

            Piste p = null;
            Genre g = null;
            Instrument in = null;
            //int countInstru = 0;

            while ((ligne = br.readLine()) != null) {

                if (ligne.startsWith("./")) {

                    String firstline[] = ligne.split("(\\./)");

                    String elements[] = firstline[1].split(" - ");

                    //ARTISTE;IMAGE
                    String artiste_image[] = elements[0].split(";");
                    String artiste = artiste_image[0];
                    String image = artiste_image[1];

                    //TITRE;GENRE;ANNEE
                    String titre_genre_annee[] = elements[1].split(";");
                    String morceau = titre_genre_annee[0];//
                    String genre = titre_genre_annee[1];//
                    String annee = titre_genre_annee[2];//

                    //INSTRU
                    String instruments[] = elements[2].split(":")[0].split(";");

                    m = new Morceau(morceau);
                    Collection<Instrument> mi = m.getInstruments();
                    m.setAnnee((Integer.parseInt(annee)));
                    g = getGenre(genre);    // POUR VERIFIER DOUBLONS
                    m.setGenre(g);
                    a = getArtiste(artiste, image); // POUR VERIFIER DOUBLONS
                    m.setArtiste(a);
                    em.persist(a);
                    em.persist(g);
                    for (int i = 0; i < instruments.length; i++) {
                        in = getInstrument(instruments[i]);//VERIF DOUBLONS

                        mi.add(in);
                        in.getMorceaux().add(m);

                    }

                } else {
                    if (!ligne.isEmpty()) {//Pour les sauts de ligne
                        if (ligne.endsWith(".mp3") || ligne.endsWith(".ogg")) {

                            p = new Piste(ligne);
                            p.setMorceau(m);
                            m.getPistes().add(p);
                            em.persist(p);

                        }
                    }
                }
  
                em.persist(m);

            }

        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        }

    }

    //OK
    public int compteAllMorceaux() {
        Query q = em.createQuery("select m from Morceau m");
        return q.getResultList().size();
    }

    public Collection<Morceau> getAllMorceaux(int index) {
        Query q = em.createQuery("select m from Morceau m");
        q.setFirstResult(index);
        q.setMaxResults(10);
        return q.getResultList();
    }

    //OK
    public Collection<Morceau> rechercheParMorceau(String titre, int index) {
        Query q = em.createQuery("select m from Morceau m where m.titre LIKE '%" + titre + "%'");
        q.setFirstResult(index);
        q.setMaxResults(10);
        return q.getResultList();
    }

    public int compteRechercheParMorceaux(String titre) {
        Query q = em.createQuery("select m from Morceau m where m.titre LIKE '%" + titre + "%'");
        return q.getResultList().size();
    }

    public Collection<Morceau> rechercheParArtiste(String nom, int index) {
        Query q = em.createQuery("select m from Morceau m where m.artiste.nom LIKE '%" + nom + "%'");
        q.setFirstResult(index);
        q.setMaxResults(10);
        return q.getResultList();
    }

    public Collection<Morceau> rechercheParArtiste(String nom) {
        Query q = em.createQuery("select m from Morceau m where m.artiste.nom LIKE '%" + nom + "%'");
        return q.getResultList();
    }

    public int compteRechercheParArtiste(String nom) {
        Query q = em.createQuery("select m from Morceau m where m.artiste.nom LIKE '%" + nom + "%'");
        return q.getResultList().size();
    }

    //OK
    public Collection<Piste> getPistesByMorceau(String morceau_id) {
        Morceau m = em.find(Morceau.class, Long.valueOf(morceau_id));
        return m.getPistes();
    }

    //OK
    public String getMorceauById(String morceau_id) {
        Morceau m = em.find(Morceau.class, Long.valueOf(morceau_id));
        return m.getTitre();
    }

    public Morceau getMorceauByIdReturnAsMorceau(String morceau_id) {
        return em.find(Morceau.class, Integer.valueOf(morceau_id));
    }

    public Artiste getInfosArtiste(String id) {
        return em.find(Artiste.class, Integer.valueOf(id));
    }

    // POUR LA RECHERCHE
    public Collection<Morceau> getMorceauByArtiste(String nom) {
        Query q = em.createQuery("select m from Morceau m where m.nom = " + nom + "");
        return q.getResultList();
    }

    public Collection<Instrument> getAllInstrus() {
        Query q = em.createQuery("select i from Instrument i");
        return q.getResultList();
    }

    public Collection<Morceau> getMorceauxByInstru(String nomInstru, int index) {
        Query q = em.createQuery("select m from Morceau m where m.instruments in (select i from Instrument i where i.nom='" + nomInstru + "')");
        q.setFirstResult(index);
        q.setMaxResults(10);
        return q.getResultList();
    }

    public int compteMorceauxByInstru(String nomInstru) {
        Query q = em.createQuery("select m from Morceau m where m.instruments in (select i from Instrument i where i.nom='" + nomInstru + "')");
        return q.getResultList().size();
    }

}
