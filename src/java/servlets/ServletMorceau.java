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
        String morceau_id = /*Integer.parseInt(*/request.getParameter("id")/*)*/;
        String artist_id = request.getParameter("artiste_id");
        String index = request.getParameter("page");       
        HttpSession session = request.getSession();
        
        if (action != null) {
            if (action.equals("afficherLesMorceaux"))
            {
                Collection<Morceau> morceaux;
                double res = Math.ceil(gestionnaireMorceau.compteAllMorceaux()/10);
                int nbPages = (int) res;
                if(index == null){
                    morceaux = gestionnaireMorceau.getAllMorceaux(0);
                }
                else{          
                    morceaux = gestionnaireMorceau.getAllMorceaux((Integer.valueOf(10))*Integer.parseInt(index));
  
                }
                request.setAttribute("listeMorceaux", morceaux);
                request.setAttribute("index", index);
                request.setAttribute("nbPages", nbPages);
                
            }
            if (action.equals("afficherLesMorceauxEtPistes")) {
                //STOCKER PAGINATION parameter
                // before et after pour les fleches
                
                
                //Collection<Morceau> morceaux = gestionnaireMorceau.getAllMorceaux((Integer.parseInt(index)));//param nbpagination
                
                
                Collection<Piste> pistes = gestionnaireMorceau.getPistesByMorceau(morceau_id);
                
                String titre = gestionnaireMorceau.getMorceauById(morceau_id);
                request.setAttribute("nbLignes", pistes.size());
                //request.setAttribute("listeMorceaux", morceaux);
                request.setAttribute("listePistes", pistes);
                request.setAttribute("titreMorceau", titre);
                
            }
            if (action.equals("ajouterMorceauxAvecPistes")) {
                try{
                    gestionnaireMorceau.ajouterMorceauAvecPistes();
                }
                catch(Exception e){
                    e.getMessage();
                }
                Collection<Morceau> morceaux = gestionnaireMorceau.getAllMorceaux((Integer.parseInt(index)));
                request.setAttribute("listeMorceaux", morceaux);       
            }
            
            
            if (action.equals("rechercheParTitre")) {
                Collection<Morceau> resultat;
                String nom = request.getParameter("titre_recherche");
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
                forwardTo="morceaux.jsp?action=rechercheParTitre";
            }
            if (action.equals("ficheArtiste")) {
                Artiste a = gestionnaireMorceau.getInfosArtiste(artist_id);
                String nomArtiste = a.getNom();
                
                Collection<Morceau> listeMorceaux = a.getMorceaux();
                request.setAttribute("nomArtiste", nomArtiste);
                request.setAttribute("listeMorceaux", listeMorceaux);
            }
            if(action.equals("ajoutMorceauPanier"))
            {
                Cookie[] cookies = request.getCookies();
                if(cookies != null) //Si il y a des cookies
                {
                    for(int i = 0; i< cookies.length; i++) // on cherche dans le tableau de cookies
                    {
                        if(cookies[i].equals("panier")) // S'il existe un cookie de nom panier
                        { //On récupere sa valeur et on ajoute l'id du morceau choisis dans le cookie
                            String valeur = cookies[i].getValue();
                            valeur += ";" + morceau_id;
                            cookies[i].setValue(valeur);
                            
                        }
                    }
                }else //sinon, on cree le cookie
                {
                    Cookie cookie = new Cookie("panier",morceau_id);
                    response.addCookie(cookie);                  
                } 
                
                forwardTo="morceaux.jsp?action=rechercheParTitre";
            }         
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
