<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<div>  
    <a href="${pageContext.request.contextPath}">  
        <img src="${pageContext.request.contextPath}/resources/logo.jpg"/>  
    </a>  
    <c:if test="${connecte!=null}">
        <a href="ServletUsers?action=deconnexion">Deconnexion</a>
    </c:if>
</div> 