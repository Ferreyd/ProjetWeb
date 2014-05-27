<%-- 
    Document   : compte-content
    Created on : 21 mai 2014, 12:40:17
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
        <title>JSP Page</title>
    </head>
    <body>

        <div class="col-lg-12">
            <h1>
                Votre page d'admnistration
            </h1>
            <div class="col-xs-3">
                <h3>
                    Nom :
                </h3>
                <h3>
                    Prenom :
                </h3>
                <h3>
                    Abonnement :
                </h3>
                <h3>
                    Login :
                </h3>
                <h3>
                    Mot de passe :
                </h3>
            </div>
            <div class="col-xs-6">
                <h3>
                    ${utilisateur.nom}
                </h3>
                <h3>
                    ${utilisateur.prenom}
                </h3>
                <h3>
                    ${abonnementUtilisateur}
                </h3>
                <h3>
                    ${utilisateur.login}
                </h3>
                <h3>
                    ${utilisateur.mdp}
                </h3>
            </div>
            <form action="ServletCompte" method="post">
                <select name="choixAbo">
                    <c:forEach var="a" items="${abonnements}">
                        <option value="${a.id}">Nom : ${a.nom} + Prix : ${a.prix},Duree : ${a.duree}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="action" value="changerAbo">
                <input type="submit" class="btn btn-success"/>
            </form>





        </div>
        <div class="col-lg-12">
            <table class="table-bordered" border="1">
                <tr>
                    <th>Titre</th>
                    <th>Genre</th>
                    <th>Annee</th>
                    <th>Artiste</th>
                    <th>Prix</th>
                        <c:forEach var="m" items="${requestScope['listeAchats']}">
                    <tr>                                               
                        <td>${m.titre}</td>
                        <td>
                            ${m.genre.nom}
                        </td>
                        <td>${m.annee}</td>
                        <td>${m.artiste.nom}</td>
                        <td>0.99€</td>

                    </tr>
                </c:forEach>
            </table>
            <c:if test="${param['achats'] != null}" >

            </c:if>
        </div>





    </body>
</html>