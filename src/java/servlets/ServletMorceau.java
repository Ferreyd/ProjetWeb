/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import projetweb.gestionnaire.GestionnaireMorceau;
import projetweb.gestionnaire.GestionnaireUtilisateur;
import projetweb.modeles.Morceau;
import projetweb.modeles.Piste;
import projetweb.modeles.Artiste;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ServletMorceau", urlPatterns = {"/ServletMorceau"})
public class ServletMorceau extends HttpServlet {

    @EJB
    private GestionnaireMorceau gestionnaireMorceau;
    @EJB
    private GestionnaireUtilisateur gestionnaireUtilisateur;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forwardTo = "morceaux.jsp";
        String action = request.getParameter("action");
        String morceau_id = /*Integer.parseInt(*/ request.getParameter("id")/*)*/;
        String artist_id = request.getParameter("artiste_id");
        String instru = request.getParameter("instru");
        String index = request.getParameter("page");
        HttpSession session = request.getSession();
        request.setAttribute("lesInstrus", gestionnaireMorceau.getAllInstrus());
        
        if (action != null) {
            if(action.equals("afficherParInstrument")){
                Collection<Morceau> morceaux;
                double res = Math.ceil(gestionnaireMorceau.compteMorceauxByInstru(instru) / 10);
                int nbPages = (int) res;
                if (index == null) {
                    morceaux = gestionnaireMorceau.getMorceauxByInstru(instru, 0);
                } else {
                    morceaux = gestionnaireMorceau.getMorceauxByInstru(instru, (Integer.valueOf(10)) * Integer.parseInt(index));

                }
                
                request.setAttribute("listeMorceaux", morceaux);
                request.setAttribute("index", index);
                request.setAttribute("nbPages", nbPages); 
                request.setAttribute("nbResultatParInstru", gestionnaireMorceau.compteMorceauxByInstru(instru));
            }
            if (action.equals("afficherLesMorceauxEtPistes")) {
                Collection<Morceau> morceaux;
                double res = Math.ceil(gestionnaireMorceau.compteAllMorceaux() / 10);
                int nbPages = (int) res;
                if (index == null) {
                    morceaux = gestionnaireMorceau.getAllMorceaux(0);
                } else {
                    morceaux = gestionnaireMorceau.getAllMorceaux((Integer.valueOf(10)) * Integer.parseInt(index));

                }
                
                request.setAttribute("listeMorceaux", morceaux);
                request.setAttribute("index", index);
                request.setAttribute("nbPages", nbPages);
                
            }
            if (action.equals("ajouterMorceauxAvecPistes")) {
                try {
                    gestionnaireMorceau.ajouterMorceauAvecPistes();
                    Collection<Morceau> morceaux;
                    double res = Math.ceil(gestionnaireMorceau.compteAllMorceaux() / 10);
                    int nbPages = (int) res;
                    if (index == null) {
                        morceaux = gestionnaireMorceau.getAllMorceaux(0);
                    } else {
                        morceaux = gestionnaireMorceau.getAllMorceaux((Integer.valueOf(10)) * Integer.parseInt(index));

                    }
                    request.setAttribute("listeMorceaux", morceaux);
                    request.setAttribute("index", index);
                    request.setAttribute("nbPages", nbPages);                   
                    forwardTo="morceaux.jsp?action=afficherLesMorceaux";
                } catch (Exception e) {
                    e.getMessage();
                }             
            }
            
            
            if (action.equals("recherche")) {
                Collection<Morceau> resultat;
                String nom = request.getParameter("champ_recherche");
                String typeRecherche = request.getParameter("typeRecherche");
                
                if(typeRecherche.equals("rechercheTitre")){
                    
                    double res = Math.ceil(gestionnaireMorceau.compteRechercheParMorceaux(nom)/10);
                    int nbPages = (int) res;
                    if(index == null){
                        resultat = gestionnaireMorceau.rechercheParMorceau(nom, 0);
                    }
                    else{
                        resultat = gestionnaireMorceau.rechercheParMorceau(nom, (Integer.valueOf(10))*Integer.parseInt(index));
                    }
                    request.setAttribute("resultatsParMorceau", resultat);
                    request.setAttribute("nbResultatParMorceau", gestionnaireMorceau.compteRechercheParMorceaux(nom));
                    request.setAttribute("index", index);
                    request.setAttribute("nbPages", nbPages);
                  forwardTo="morceaux.jsp?action=recherche";
                }
               if(typeRecherche.equals("rechercheArtiste")){
                   
                    double res = Math.ceil(gestionnaireMorceau.compteRechercheParArtiste(nom)/10);
                    int nbPages = (int) res;
                    if(index == null){
                        resultat = gestionnaireMorceau.rechercheParArtiste(nom, 0);
                    }
                    else{
                        resultat = gestionnaireMorceau.rechercheParArtiste(nom, (Integer.valueOf(10))*Integer.parseInt(index));
                    }
                    request.setAttribute("resultatsParArtiste", resultat);
                    request.setAttribute("nbResultatParArtiste", gestionnaireMorceau.compteRechercheParArtiste(nom));
                    request.setAttribute("index", index);
                    request.setAttribute("nbPages", nbPages);
                    forwardTo="morceaux.jsp?action=recherche";
                }
               
            }
            if (action.equals("ficheArtiste")) {
                Artiste a = gestionnaireMorceau.getInfosArtiste(artist_id);
                String nomArtiste = a.getNom();
                String imgArtiste = a.getImage();
                Collection<Morceau> listeMorceaux = a.getMorceaux();
                request.setAttribute("nomArtiste", nomArtiste);
                request.setAttribute("imgArtiste", imgArtiste);
                request.setAttribute("listeMorceaux", listeMorceaux);
            }
            if (action.equals("ajoutMorceauPanier")) {
                if (session.getAttribute("panier") != null) {
                    String valeur = session.getAttribute("panier").toString();
                    System.out.println("Ancienne valeur de panier = " + valeur);
                    valeur += morceau_id + ";";
                    session.setAttribute("panier", valeur);
                    System.out.println("Nouvelle valeur de panier = " + valeur);
                }else
                {
                    session.setAttribute("panier","");                  
                    String valeur = morceau_id + ";";
                    session.setAttribute("panier", valeur);
                    System.out.println("Nouvelle valeur de panier = " + valeur);  
                }

                forwardTo = "morceaux.jsp?action=rechercheParTitre";
            }
        }
        else{
            Collection<Morceau> morceaux;
            double res = Math.ceil(gestionnaireMorceau.compteAllMorceaux() / 10);
            int nbPages = (int) res;
            if (index == null) {
                morceaux = gestionnaireMorceau.getAllMorceaux(0);
            } else {
                morceaux = gestionnaireMorceau.getAllMorceaux((Integer.valueOf(10)) * Integer.parseInt(index));

            }

            request.setAttribute("listeMorceaux", morceaux);
            request.setAttribute("index", index);
            request.setAttribute("nbPages", nbPages);
        }
        RequestDispatcher dp = request.getRequestDispatcher(forwardTo);

        dp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
