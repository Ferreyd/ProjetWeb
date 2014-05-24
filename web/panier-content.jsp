<%-- 
    Document   : panier
    Created on : 16 mai 2014, 15:25:47
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                <td><b>Titre</b></td>
            </tr>

            <c:forEach var="m" items="${requestScope['morceaux']}">
                <tr>                                               
                    <td>${m.titre}</td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
