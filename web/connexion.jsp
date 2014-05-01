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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>

        <div class="login">
            <h2>Veuillez entrez vos identifiants de connexion ci dessous :</h2>
            <form role="form" action="ServletUtilisateur" method="post" >
                <label for="log">Login</label>
                <input class="form-control"placeholder="Login" type="text" name="log">
                <label for="pass">Mot de passe</label>
                <input class="form-control"placeholder="Mot de passe" type="text" name="pass"><br/>    
                <input type="hidden" name="action" value="checkConnexion">    
                <input type="submit" class="btn btn-primary" name="submit" value="Connexion">
            </form>
            <h2>Si vous n'êtes pas inscrit, alors veuillez vous inscrire en remplissants le formulaire ci-dessous</h2>
            <form role="form" action="ServletUtilisateur" method="post" >
                <label for="log">Login</label>
                <input class="form-control"placeholder="Login" type="text" name="log">
                <label for="nom">Nom</label>
                <input class="form-control"placeholder="Nom" type="text" name="nom">
                <label for="prenom">Prénom</label>
                <input class="form-control"placeholder="Prénom" type="text" name="prenom">
                <label for="pass">Mot de passe</label>
                <input class="form-control"placeholder="Mot de passe" type="password" name="mdp"><br/>    
                <input type="hidden" name="action" value="inscription">    
                <input type="submit" class="btn btn-primary" name="submit" value="Inscription">
            </form>
        </div>



    </body>
</html>
