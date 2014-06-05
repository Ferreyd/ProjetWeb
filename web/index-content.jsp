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
        <div class="page-header">
            <div class="container content">

                <h1>Bienvenue ${login} !</h1>
                <h2> </h2>
                <!-- Message qui s'affiche lorsque la page est appelé avec un paramètre http message -->   
                <div class="row">                       <div class="col-lg-3"></div>

                    <div class="col-lg-12">
                        <div class="col-lg-6"><jsp:include page="recherche.jsp"/> </div>
                        <div class="col-lg-3"></div>             
                    </div>
                </div>
                <div class="container">
                    <div class="col-lg-12">
                        <h2>
                            Vos achats
                        </h2>
                        <table class="table table-bordered" border="1">
                            <thead>
                            <th>Titre</th>
                            <th>Genre</th>
                            <th>Annee</th>
                            <th>Artiste</th>
                            <th></th>
                            </thead>
                            <c:forEach var="m" items="${requestScope['listeAchats']}">
                                <tr>                                               
                                    <td>${m.titre}</td>
                                    <td>${m.genre.nom}</td>
                                    <td>${m.annee}</td>
                                    <td>${m.artiste.nom}</td>
                                    <td>                                           
                                        <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
