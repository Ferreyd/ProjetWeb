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
                        <div class="col-lg-12">
                            <p>Cliquez sur une chanson pour tester notre service !</p>
                            <div class="col-lg-12">
                                <a href="http://mt5demo.gexsoft.com/music/Highway To hell (live)"><img src="http://rgcred.files.wordpress.com/2012/07/ac-dc-highway-to-hell-stencil.jpg" width="200" height="150"></a>
                                <a href="http://mt5demo.gexsoft.com/music/We Are The Champions"><img src="http://991.com/NewGallery/Queen-We-Are-The-Champi-83729.jpg"width="200" height="150"></a>
                                <a href="http://mt5demo.gexsoft.com/music/Paint It Black"><img src="http://3.bp.blogspot.com/-KwOTlHHmDnc/Tud2RUBBfFI/AAAAAAAABdI/4xh0jLGCfT0/s1600/paintitblack.jpg"width="200" height="150"></a>
                            </div>
                        </div>
                        </p>
                    </div>
                </div>
            </div>
        </div>




    </body>
</html>
