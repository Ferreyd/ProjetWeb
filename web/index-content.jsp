<%-- 
    Document   : index-content
    Created on : 30 avr. 2014, 16:04:07
    Author     : Nicolas
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
        <h1>Bienvenue ${login}</h1>
        <h2> Votre abonnement : ${abonnementUtilisateur}</h2>
        <!-- Message qui s'affiche lorsque la page est appelé avec un paramètre http message -->  
        <c:if test="${!empty param['message']}">  
            <h4>Reçu message : ${param.message}</h4>  
        </c:if>  
        <jsp:include page="recherche.jsp"/>  



    </body>
</html>
