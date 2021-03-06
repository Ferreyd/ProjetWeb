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
        <title>Page du compte</title>
    </head>
    <body>

        <div class="col-lg-12">
            <div class="container content">

                <h1>
                    Ma page d'administration
                </h1>
                <div class="col-lg-12">
                    <h2>
                        Vos informations
                    </h2>
                    <div class="col-xs-6">
                        <h3>
                            Nom :
                        </h3>
                        <h3>
                            Prenom :
                        </h3>
                        <h3>
                            Abonnement :
                        </h3>
                        <br/>
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
                            ${abonnementUtilisateur} , Abonnement jusqu'au : ${finAbonnement}.
                        </h3>  
                        <br/>
                        <h3>
                            ${utilisateur.login}
                        </h3>
                        <h3>
                            ${utilisateur.mdp}
                        </h3>
                    </div>
                    <br/>
                    <h2>
                        Si vous souhaitez changer votre abonnement, veuillez selectionner ce dernier dans la liste
                    </h2>
                    <form action="ServletCompte" method="post">
                        <select name="choixAbo">
                            <c:forEach var="a" items="${abonnements}">
                                <c:choose>
                                    <c:when test="${utilisateur.abonnement.id == a.id}">
                                        <option value="${a.id}" selected>Nom : ${a.nom} + Prix : ${a.prix},Duree : ${a.duree}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${a.id}">Nom : ${a.nom} + Prix : ${a.prix},Duree : ${a.duree}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="action" value="changerAbo">
                        <input type="submit" class="btn btn-success"/>
                    </form>




                </div>
            </div>
        </div>
    </body>
</html>