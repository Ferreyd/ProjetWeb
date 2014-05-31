/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import projetweb.gestionnaire.GestionnaireUtilisateur;
import projetweb.modeles.Morceau;
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ServletCompte", urlPatterns = {"/ServletCompte"})
public class ServletCompte extends HttpServlet {

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
        System.out.println("ACTION = " + action);
        String form = request.getParameter("form");
        String forwardTo = "compte.jsp";
        String message = "";

        HttpSession session = request.getSession();
        if (session.getAttribute("log") != null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
            System.out.println("LOGIN ====>" + session.getAttribute("login") + " " + request.getAttribute("log"));
            request.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin((String) session.getAttribute("login")));
        }
        //On prend l'utilisateur courant
        Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());

        if (action != null) {
            if (action.equals("changerAbo")) {
                //Ajout d'un abonnement à l'utilisateur
                System.out.println("Abonnement avant = " + u.getAbonnement().getNom() + " et dans la variable de sessions = " + session.getAttribute("abonnementUtilisateur"));
                u = gestionnaireUtilisateur.ajouteAbonnement(u.getLogin(),request.getParameter("choixAbo").toString());
                session.setAttribute("abonnementUtilisateur", u.getAbonnement().getNom());
                message = "Abonnement ajoute";
                forwardTo += "?action=affiche";
                System.out.println("Abonnement après = " + u.getAbonnement().getNom() + " et dans la variable de sessions = " + session.getAttribute("abonnementUtilisateur"));
                System.out.println("action, apres forwarTO : " + forwardTo);
            }
            if (action.equals("affiche")) {
                System.out.println("affiche");

            }
        } else {
            forwardTo += "?action=affiche";
        }
        request.setAttribute("utilisateur", u);
        request.setAttribute("abonnements", gestionnaireUtilisateur.getAllAbonnement());
        forwardTo += "?action=affiche";
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
