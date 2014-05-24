/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import projetweb.modeles.Utilisateur;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ServletPanier", urlPatterns = {"/ServletPanier"})
public class ServletPanier extends HttpServlet {

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

        String forwardTo = "panier.jsp";
        String action = request.getParameter("action");
        String form = request.getParameter("form");
        String message = "";

        //Object connecte = null;
        HttpSession session = request.getSession();

        if (session == null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
            System.out.println("LOGIN ====>" + session.getAttribute("login") + " " + request.getAttribute("log"));
            request.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin((String) session.getAttribute("login")));
        }
        if (action != null) {
            if (action.equals("affiche")) {
                System.out.println("session : " + session.getAttribute("login") + " " + session.getAttribute("idUtilisateur") + " cast : " + session.getAttribute("idUtilisateur").toString());
                Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());

                Collection<Morceau> morceaux = new ArrayList<Morceau>();

                String valeur = session.getAttribute("panier").toString();
                System.out.println("JE SUIS LA " + valeur);
                String[] tokens = valeur.split("[;]"); // on parse les données du cookie
                if (valeur != "") {
                    for (String s : tokens) {
                        Morceau m = gestionnaireMorceau.getMorceauByIdReturnAsMorceau(s);
                        System.out.println("MORCEAU : " + m.toString());
                        morceaux.add(m);
                    }
                }

                System.out.println("ET LA AUSSI");
                request.setAttribute("morceaux", morceaux);
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
