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
        <div class="col-xs-2"></div>
        <div class="col-lg-8 content">
            <h2>Tableau des morceaux</h2>
            <table class="table table-bordered table-striped" border="1">
                <thead>
                <th>Titre</th>
                <th>Genre</th>
                <th>Annee</th>
                <th>Artiste</th>
                <th>Prix</th>
                <th></th>
                </thead>

                <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                    <tr>                                               
                        <td>${m.titre}</td>
                        <td>
                            ${m.genre.nom}
                        </td>
                        <td>${m.annee}</td>
                        <td>${m.artiste.nom}</td>
                        <td>3.99€</td>
                        <td><a class="btn btn-danger" href="ServletPanier?action=supprimerDuPanier&id=${m.id}">Supprimer du panier</a></td>
                    </tr>
                </c:forEach>
            </table>
            <h2>Vous avez ${taillePanier} article(s) dans votre panier, pour une valeur de ${prix}€</h2>
            <a href="ServletPanier?action=achat"class="btn btn-success" onclick="confirm('Confirmez vous vouloir acheter ces articles ?')">ACHAT</a>

        </div>



    </body>
</html>

