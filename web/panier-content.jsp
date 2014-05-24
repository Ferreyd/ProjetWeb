<%-- 
    Document   : panier
    Created on : 16 mai 2014, 15:25:47
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Votre panier</title>
    </head>
    <body>

        <h2>Tableau des morceaux</h2>
        <table border="1">
            <tr>
                <th>Titre</th>
                <th>Genre</th>
                <th>Annee</th>
                <th>Artiste</th>
                <th>Prix</th>
                <th></th>
            </tr>

            <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                <tr>                                               
                    <td>${m.titre}</td>
                    <td>
                        <c:forEach var="g" items="${requestScope['m.genre']}">
                            ${g}
                        </c:forEach>
                    </td>
                    <td>${m.annee}</td>
                    <td>
                        <c:forEach var="a" items="${requestScope['m.artiste']}">
                            ${a}
                        </c:forEach>
                    </td>
                    <td>0.99€</td>
                    <td><a class="btn btn-danger" href="ServletPanier?action=supprimerDuPanier&id=${m.id}">Supprimer du panier</a></td>
                </tr>
            </c:forEach>
        </table>
        
        <a href="ServletPanier?action=achat"class="btn btn-success">ACHAT</a>

    </body>
</html>
