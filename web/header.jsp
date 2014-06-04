<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid"> 
        <div class="navbar-header">

            <a class="navbar-brand" href="#">Multitracksongs.com</a>
        </div>
        <c:if test="${connecte=='OK'}">    
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <a type="button" class="btn  btn-default btn-sm navbar-btn" href="ServletUtilisateur?action=affiche">Index</a>
                <a type="button" class="btn  btn-default btn-sm navbar-btn" href="ServletMorceau">Morceaux</a>
                <a type="button" class="btn  btn-default btn-sm navbar-btn" href="ServletCompte">Mon compte</a></li>                  
                <a type="button" class="btn  btn-default btn-sm navbar-btn" href="ServletPanier?action=affiche">Mon Panier</a>

                <a type="button" class="btn second-button btn-default btn-sm navbar-btn pull-right btn-danger" href="ServletUtilisateur?action=deconnexion">Deconnexion</a>
            </div>
        </c:if>
        <c:if test="${connecte!='OK'}">
            <form  class="navbar-form navbar-right" role="form" action="ServletUtilisateur?action=checkConnexion" method="post" >
                <div class="form-group"> <input class="form-control"placeholder="Login" type="text" name="log"></div>

                <div class="form-group"> <input class="form-control"placeholder="Mot de passe" type="password" name="pass"></div>    
                <input type="hidden" name="action" value="checkConnexion">    
                <button type="submit" class="btn btn-success" name="submit" value="Connexion">Connexion</button>
            </form>  
        </c:if>
    </div> 
</nav>
