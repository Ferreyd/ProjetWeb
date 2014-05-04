<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<div>  
    <a href="${pageContext.request.contextPath}">  
        <img src="${pageContext.request.contextPath}/resources/logo.jpg"/>  
    </a>  
    <c:if test="${connecte!=null}">
        <a href="ServletUtilisateur?action=deconnexion">Deconnexion</a>
        <a href="ServletMorceau">Morceaux</a>
    </c:if>
</div> 