<%-- 
    Document   : morceaux-content
    Author     : Jeje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
    "http://www.w3.org/TR/html4/loose.dtd">  

<!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Morceaux</title>
    </head>
    <body>
        <a href="ServletMorceau?action=afficherLesMorceaux">Afficher/Rafraichir la liste des morceaux</a><br/>
        <a href="ServletMorceau?action=ajouterMorceauxAvecPistes">Ajouter des morceaux</a><br/>
       
           <c:if test="${param['action'] == 'afficherLesMorceaux'}" >
                <h2>Tableau des morceaux</h2>
                <table border="1">
                    <tr>
                        <td><b>Titre</b></td>
                    </tr>

                    <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                        <tr>                                               
                            <td>${m.titre}</td>
                            <td><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&id=${m.id}">Pistes</a>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>      
            
            <c:if test="${param['action'] == 'afficherLesMorceauxEtPistes'}" >
                <a href="ServletMorceau?action=afficherLesMorceaux">Retour morceaux</a><br/>
                
                <h2>Tableau des morceaux</h2>
                <table border="1">
                    <tr>
                        <td><b>Titre</b></td>
                        <td><b>Artiste</b></td>
                        <td></td>
                    </tr>

                    <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                        <tr>                                               
                            <td>${m.titre}</td>
                            <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}</td>
                            <td><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&id=${m.id}">Pistes</a>
                        </tr>
                    </c:forEach>
                </table>
                <h2>Tableau des pistes</h2>

                <table border="1">
                    <tr>
                        <td><b>Pistes de ${requestScope['titreMorceau']}</b></td>
                    </tr>

                    <c:forEach var="p" items="${requestScope['listePistes']}">
                        <tr>                                               
                            <td>${p.nom}</td>                               
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
                
            <c:if test="${param['action'] == 'ficheArtiste'}" >
                <h2>${requestScope['nomArtiste']}</h2>
            </c:if>
            
 
    </body>
</html>
