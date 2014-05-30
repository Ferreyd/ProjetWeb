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
                    <!-- DIV INSCRIPTION -->
                    <div class="col-lg-4">

                        <form class="form-signin" role="form" action="ServletUtilisateur" method="post" >
                            <h3 class="form-signin-heading">Profitez de l'offre musicale !</h3>
                            <h4>Inscrivez-vous !</h4>

                            <input class="form-control"placeholder="Login" type="text" name="log"><br/>

                            <input class="form-control"placeholder="Nom" type="text" name="nom"><br/>

                            <input class="form-control"placeholder="Prénom" type="text" name="prenom"><br/>

                            <input class="form-control"placeholder="Mot de passe" type="password" name="mdp"><br/>

                            <input type="hidden" name="action" value="inscription">    
                            <input type="submit" class="btn btn-lg btn-primary btn-block" name="submit" value="Inscription">
                        </form>

                    </div>
                    <!-- DIV DESC-->
                    <div class="col-lg-8">
                        <h1>Ecoutez la musique autrement !</h1>
                        <p>Sur Multitracksongs, vous pouvez écouter, découvrir et même vous offrir un chanson parmi un catalogue, <b>instruments par instruments</b>!<br/>
                            <b>Multitracksongs</b>, votre meilleur partenaire musical !<br/>
                            Testez notre service révolutionnaire avec ces 3 chansons gratuite !

                        <div class="col-lg-12">
                            <div class="col-lg-12">
                                <h2>
                                    <a class="btn btn-large btn-primary " href="http://mt5demo.gexsoft.com/music/Highway to hell">Highway to hell</a>
                                </h2>
                            </div>
                            <div class="col-lg-12">
                                <h2>
                                    <a class="btn btn-large btn-primary " href="http://mt5demo.gexsoft.com/music/We Are The Championsl">We Are The Champions</a>
                                </h2>

                            </div>
                            <div class="col-lg-12">
                                <h2>
                                    <a class="btn btn-large btn-primary " href="http://mt5demo.gexsoft.com/music/ Paint It Black"> Paint It Black</a>
                                </h2>

                            </div>
                        </div>



                        </p>
                    </div>
                </div>
            </div>
        </div>




    </body>
</html>
