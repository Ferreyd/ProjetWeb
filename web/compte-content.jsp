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
        <h1>Bienvenue ${u.nom}</h1>
        <h2> Votre abonnement : ${abonnementUtilisateur}</h2>
        <!-- Message qui s'affiche lorsque la page est appelé avec un paramètre http message -->  
        <c:if test="${!empty param['message']}">  
            <h4>Reçu message : ${param.message}</h4>  
        </c:if>  
    
            <h2>
                Nom : ${utilisateur.nom} <br/>
                Prenom : ${utilisateur.prenom}<br/>
                Abonnement : ${utilisateur.abonnement}<br/>
                Login : ${utilisateur.login}<br/>
                MDP : ${utilisateur.mdp}<br/>
            </h2>
        
        <form action="ServletCompte" method="post">
            <select name="choixAbo">
                <c:forEach var="a" items="${abonnements}">
                    <option value="${a.id}">Nom : ${a.nom} + Prix : ${a.prix},Duree : ${a.duree}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="action" value="changerAbo">
            <input type="submit" class="btn btn-success"/>
        </form>



    </body>
</html>