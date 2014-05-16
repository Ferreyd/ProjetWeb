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
import javax.servlet.ServletException;
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
public class ServletPanier extends HttpServlet {

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

        if (session == null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
            System.out.println("LOGIN ====>" + session.getAttribute("login") + " " + request.getAttribute("log"));
            request.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin((String)session.getAttribute("login")));
        }else
        {
            System.out.println("session : " + session.getAttribute("login") + " " + session.getAttribute("idUtilisateur") + " cast : " + session.getAttribute("idUtilisateur").toString());
            Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());
            
            Collection<Morceau> morceaux = u.getMorceaux();
            
            request.setAttribute("morceaux", morceaux);
        }
            
            
            
        
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
