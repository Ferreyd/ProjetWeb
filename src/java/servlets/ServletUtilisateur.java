/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import projetweb.gestionnaire.GestionnaireMorceau;
import projetweb.gestionnaire.GestionnaireUtilisateur;
import projetweb.modeles.Abonnement;
import projetweb.modeles.Morceau;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ServletUtilisateur", urlPatterns = {"/ServletUtilisateur"})
public class ServletUtilisateur extends HttpServlet {

    @EJB
    private GestionnaireUtilisateur gestionnaireUtilisateur;

    @EJB
    private GestionnaireMorceau gestionnaireMorceau;

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
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String form = request.getParameter("form");
        String forwardTo = "";
        String message = "";

        //Object connecte = null;
        HttpSession session = request.getSession();

        if (session.getAttribute("log") != null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
            request.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin((String) session.getAttribute("login")));

        }
        if (action != null) {
            if (gestionnaireUtilisateur.testAbonnement() == false) {
                gestionnaireUtilisateur.creerAbonnementsTest();
            } else {
                request.setAttribute("abonnements", gestionnaireUtilisateur.getAllAbonnement());
            }
            if (action.equals("affiche")) {
                Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());
                Collection<Morceau> achats = u.getAchats();
                request.setAttribute("listeAchats", achats);
            }
            if (action.equals("checkConnexion")) {
                //Si il n'y a pas de morceaux dans la base de données, méthode utile seulement pour le développement
                //Si le site était hébergé sur une base de donnée distante ce morceau de code n'existerait pas 
                //Il se trouve aussi dans checkConnexion car le compte admin est cree si on rentre admin admin dans le formulaire de connexion
                if (gestionnaireMorceau.getAllMorceaux(0).size() == 0) {
                    try {                     
                        gestionnaireMorceau.ajouterMorceauAvecPistes();
                    } catch (Exception ex) {
                        Logger.getLogger(ServletUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                boolean existe = gestionnaireUtilisateur.userExists(request.getParameter("log"), request.getParameter("pass"));
                if (existe) {
                    session.setAttribute("login", request.getParameter("log"));
                    session.setAttribute("mdp", request.getParameter("pass"));
                    session.setAttribute("abonnementUtilisateur", gestionnaireUtilisateur.getAbonnementUtilisateur(request.getParameter("log")).getNom());
                    session.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin(request.getParameter("log")));
                    session.setAttribute("connecte", "OK");
                    //connecte = true;
                    message = "Connexion reussie";

                    Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());
                    Collection<Morceau> achats = u.getAchats();
                    request.setAttribute("listeAchats", achats);

                    if (gestionnaireUtilisateur.testAbonnementValide(String.valueOf(u.getId())) == false) // Si l'abonnement est plus valide alors il repasse en gratuit
                    {
                        u.setAbonnement(gestionnaireUtilisateur.getAbonnementParNom("gratuit"));
                    }

                    forwardTo = "index.jsp?action=affiche";
                } else {
                    session.setAttribute("connecte", "KO");
                    message = "Vos identifiants de connexions ne sont pas correcte";
                    forwardTo = "index.jsp?action=ko";
                }
            } else if (action.equals("inscription")) { //On l'inscrit et il est automatiquement redirigé vers le site

                if (gestionnaireUtilisateur.chercherParLogin(request.getParameter("log")).isEmpty()) {

                    Utilisateur u = gestionnaireUtilisateur.creeUtilisateur(request.getParameter("log"), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("mdp"));
                    u.setAbonnement(gestionnaireUtilisateur.getAbonnementParNom("gratuit"));
                    //Si il n'y a pas de morceaux dans la base de données, méthode utile seulement pour le développement
                    //Si le site était hébergé sur une base de donnée distante ce morceau de code n'existerait pas                   String index = "";
                    if (gestionnaireMorceau.getAllMorceaux(0).size() == 0) {
                        try {
                            System.out.println(request.getServletContext().getContextPath() + "/web/resources/liste.txt");
                            gestionnaireMorceau.ajouterMorceauAvecPistes();
                        } catch (Exception ex) {
                            Logger.getLogger(ServletUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    session.setAttribute("login", request.getParameter("log"));
                    session.setAttribute("mdp", request.getParameter("pass"));
                    session.setAttribute("idUtilisateur", u.getId());

                    session.setAttribute("connecte", "OK");
                    message = "Connexion reussie";

                    forwardTo = "index.jsp?action=affiche";
                } else {
                    message = "Login déjà utilisé, veuillez en prendre un autre";
                    forwardTo = "index.jsp?action=ko";
                }

            } else if (action.equals("deconnexion")) {
                session.invalidate();
                message = "Deconnexion reussie";
                forwardTo = "index.jsp?action=bye";
            } else {
                forwardTo = "index.jsp?action=todo";
                message = "La fonctionnalité pour le paramètre " + action + " est à implémenter !\n";
            }

        }
        RequestDispatcher dp = request.getRequestDispatcher(forwardTo + "&message=" + message);

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
