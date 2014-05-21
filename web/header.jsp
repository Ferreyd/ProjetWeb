<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid"> 
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Brand</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:if test="${connecte!=null}">
                    <li> <a href="ServletUtilisateur?action=deconnexion">Deconnexion</a></li>
                    <li> <a href="ServletUtilisateur">Mon compte</a></li>
                    <li><a href="ServletMorceau">Morceaux</a></li>
                </c:if>
            </ul>  
        </div>
    </div> 
</nav>
