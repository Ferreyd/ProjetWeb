/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

        String forwardTo = "panier.jsp?action=affiche";
        String action = request.getParameter("action");
        String form = request.getParameter("form");
        String id = request.getParameter("id");
        String message = "";

        //Object connecte = null;
        HttpSession session = request.getSession();

        if (session == null) //On met les informations intéréssante en attributs afin de les avoir affichés
        {
            request.setAttribute("log", session.getAttribute("login"));
            request.setAttribute("nom", session.getAttribute("nom"));
            request.setAttribute("prenom", session.getAttribute("prenom"));
            request.setAttribute("idUtilisateur", gestionnaireUtilisateur.getIdUtilisateurParLogin((String) session.getAttribute("login")));
        }
        if (action != null) {
            if (action.equals("affiche")) {

                Collection<Morceau> listeMorceaux = new ArrayList<Morceau>();
                if (session.getAttribute("panier") != null) {
                    String valeur = session.getAttribute("panier").toString();
                    System.out.println("JE SUIS LA " + valeur);
                    String[] tokens = valeur.split("[;]"); // on parse les données du cookie
                    /**
                     * Le code ci dessous permet de gerer les doublons en
                     * utilsant la structure de donné Set
                     */
                    ArrayList<String> list = new ArrayList();
                    for (String s : tokens) {
                        list.add(s);
                    }
                    Set set = new HashSet();
                    set.addAll(list);
                    ArrayList<String> panier = new ArrayList(set);

                    if (valeur != "") {
                        for (String s : panier) {
                            Morceau m = gestionnaireMorceau.getMorceauByIdReturnAsMorceau(s);
                            System.out.println("MORCEAU : " + m.getTitre() + " " + m.getGenre());
                            listeMorceaux.add(m);
                        }
                    }
                }
                System.out.println("ET LA AUSSI");
                request.setAttribute("listeMorceaux", listeMorceaux);
                forwardTo = "panier.jsp?action=affiche";
            }
            if (action.equals("supprimerDuPanier")) {
                String res = "";
                Collection<Morceau> listeMorceaux = new ArrayList<Morceau>();
                if (session.getAttribute("panier") != null) {
                    String valeur = session.getAttribute("panier").toString();
                    String[] tokens = valeur.split("[;]"); // on parse les données du cookie
                    /**
                     * Le code ci dessous permet de gerer les doublons en
                     * utilsant la structure de donné Set
                     */
                    ArrayList<String> list = new ArrayList();
                    for (String s : tokens) {
                        if (!id.equals(s)) {
                            list.add(s);
                        }
                    }
                    Set set = new HashSet();
                    set.addAll(list);
                    ArrayList<String> panier = new ArrayList(set);

                    for (String s : panier) {

                        res += s + ";";
                    }
                }
                System.out.println("RES = " + res);
                session.setAttribute("panier", res);
                forwardTo = "panier.jsp?action=affiche";
            }
            if (action.equals("achat")) {

                Utilisateur u = gestionnaireUtilisateur.getUtilisateurParId(session.getAttribute("idUtilisateur").toString());
                Set<Morceau> listeMorceaux = new HashSet<>();
                if (session.getAttribute("panier") != null) {
                    String valeur = session.getAttribute("panier").toString();
                    System.out.println("JE SUIS LA " + valeur);
                    String[] tokens = valeur.split("[;]"); // on parse les données du cookie
                    /**
                     * Le code ci dessous permet de gerer les doublons en
                     * utilsant la structure de donné Set
                     */
                    ArrayList<String> list = new ArrayList();
                    list.addAll(Arrays.asList(tokens));
                    Set set = new HashSet();
                    set.addAll(list);
                    ArrayList<String> panier = new ArrayList(set);

                    if (!"".equals(valeur)) {
                        for (String s : panier) {
                            Morceau m = gestionnaireMorceau.getMorceauByIdReturnAsMorceau(s);
                            System.out.println("MORCEAU : " + m.getTitre() + " " + m.getGenre());
                            listeMorceaux.add(m);
                        }
                    }
                    System.out.println("LOG = " + session.getAttribute("login"));
                    gestionnaireUtilisateur.ajouteMorceau(session.getAttribute("login").toString(), listeMorceaux);
                    listeMorceaux = null; // on vide le panier
                }
                request.setAttribute("listeMorceaux", listeMorceaux);
                forwardTo = "panier.jsp?action=affiche";
            }
        } else {

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
