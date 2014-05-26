<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid"> 
        <div class="navbar-header">

          <a class="navbar-brand" href="#">Multitracksongs.com</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:if test="${connecte=='OK'}">
                    <li> <a href="ServletUtilisateur?action=deconnexion">Deconnexion</a></li>
                    <li> <a href="ServletUtilisateur?action=ok">Index</a></li>
                    <li> <a href="ServletCompte">Mon compte</a></li>
                    <li><a href="ServletMorceau">Morceaux</a></li>
                    <li><a href="ServletPanier?action=affiche">Votre Panier</a></li>
                </c:if>

            </ul> 
         <c:if test="${connecte!='OK'}">
            <form  class="navbar-form navbar-right" role="form" action="ServletUtilisateur" method="post" >
                <div class="form-group"> <input class="form-control"placeholder="Login" type="text" name="log"></div>

                <div class="form-group"> <input class="form-control"placeholder="Mot de passe" type="password" name="pass"></div>    
                <input type="hidden" name="action" value="checkConnexion">    
                <button type="submit" class="btn btn-success" name="submit" value="Connexion">Connexion</button>
            </form>  
         </c:if>
        </div>
    </div> 
</nav>
