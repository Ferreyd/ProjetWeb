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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import projetweb.gestionnaire.GestionnaireUtilisateur;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ServletUtilisateur", urlPatterns = {"/ServletUtilisateur"})
public class ServletUtilisateur extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String form = request.getParameter("form");
        String forwardTo = "";
        String message = "";

        //Object connecte = null;
        HttpSession session = request.getSession();

        if (session != null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
        }
        if (action != null) {
            if (gestionnaireUtilisateur.testAbonnement() == false) {
                gestionnaireUtilisateur.creerAbonnementsTest();
            } else {
                request.setAttribute("abonnements", gestionnaireUtilisateur.getAllAbonnement());
            }
            if (action.equals("creerUtilisateursDeTest")) {
                gestionnaireUtilisateur.creerUtilisateursDeTest();
                Collection<Utilisateur> liste = gestionnaireUtilisateur.getAllUsers();
                request.setAttribute("listeDesUsers", liste);
                forwardTo = "index.jsp?action=listerLesUtilisateurs";
                message = "Liste des utilisateurs";
            } else if (action.equals("checkConnexion")) {
                System.out.println("test");
                boolean existe = gestionnaireUtilisateur.userExists(request.getParameter("log"), request.getParameter("pass"));
                System.out.println("EXISTE : " + existe);
                //boolean existe = true;

                if (existe) {

                    session.setAttribute("login", request.getParameter("log"));
                    session.setAttribute("mdp", request.getParameter("pass"));
                    session.setAttribute("abonnementUtilisateur", gestionnaireUtilisateur.getAbonnementUtilisateur(request.getParameter("log")).getNom());
                    session.setAttribute("connecte", "OK");
                    //connecte = true;
                    message = "Connexion reussie";

                    forwardTo = "index.jsp?action=ok";
                } else {
                    session.setAttribute("connecte", "KO");
                    message = "Vos identifiants de connexions ne sont pas correcte";
                    forwardTo = "index.jsp?action=ko";
                }
            } else if (action.equals("inscription")) { //On l'inscrit et il est automatiquement redirigé vers le site
                
                if (gestionnaireUtilisateur.chercherParLogin(request.getParameter("log")).isEmpty()) {
                    
                    gestionnaireUtilisateur.creeUtilisateur(request.getParameter("log"), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("mdp"));
                    session.setAttribute("login", request.getParameter("log"));
                    session.setAttribute("mdp", request.getParameter("pass"));
                    
                    session.setAttribute("connecte", "OK");
                    message = "Connexion reussie";
                    forwardTo = "index.jsp?action=ok";
                } else {
                    message = "Login déjà utilisé, veuillez en prendre un autre";
                    forwardTo = "index.jsp?action=ko";
                }

            } else if (action.equals("deconnexion")) {
                session.setAttribute("connecte", "KO");
                //connecte = null;
                message = "Deconnexion reussie";
                forwardTo = "index.jsp?action=bye";
            } else {
                forwardTo = "index.jsp?action=todo";
                message = "La fonctionnalité pour le paramètre " + action + " est à implémenter !\n";
            }
            if (action.equals("changerAbo")) {
                Utilisateur u = gestionnaireUtilisateur.ajouteAbonnement((String) session.getAttribute("login"), (String) request.getParameter("choixAbo"));
                session.setAttribute("abonnementUtilisateur", u.getAbonnement().getNom());
                message = "Abonnement ajoute";
                forwardTo = "index.jsp?action=ok";
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
