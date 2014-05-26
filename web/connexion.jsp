<%-- 
    Document   : connexion
    Created on : 24 mars 2014, 15:23:51
    Author     : Nicolas
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="lib/bootstrap-3.1.1-dist/css/signin.css" rel="stylesheet">           
        <link rel="stylesheet" href="lib/bootstrap-3.1.1-dist/css/cover.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <h1>${message}</h1>
        <div class="container">
            <div  class="row">
                <div class="col-lg-12">
                    <div class="col-lg-4">
                  
                        <form class="form-signin" role="form" action="ServletUtilisateur" method="post" >
                            <h2 class="form-signin-heading">Sinon, inscrivez-vous</h2>
                           
                            <input class="form-control"placeholder="Login" type="text" name="log">
                            
                            <input class="form-control"placeholder="Nom" type="text" name="nom">
                          
                            <input class="form-control"placeholder="Prénom" type="text" name="prenom">
                            
                            <input class="form-control"placeholder="Mot de passe" type="password" name="mdp"><br/>    
                            <input type="hidden" name="action" value="inscription">    
                            <input type="submit" class="btn btn-lg btn-primary btn-block" name="submit" value="Inscription">
                        </form>
                         
                </div>
                 </div>
             </div>
         </div>
        



    </body>
</html>
