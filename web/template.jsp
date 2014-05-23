<%@ page language="java" contentType="text/html; charset=UTF-8"  
         pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE HTML>  
<html>  
    <head>  
        <title>${param.title}</title>  
    </head>  
    <body> 


  <c:choose>
            <c:when test="${connecte=='OK'}">
                <jsp:include page="header.jsp"/> 

                <jsp:include page="menu.jsp"/>  

                <h1>${param.title}</h1>  

                <jsp:include page="${param.content}.jsp"/>  

                <jsp:include page="footer.jsp"/>  
            </c:when>
            <c:otherwise>
                <jsp:include page="connexion.jsp"/>
            </c:otherwise>
        </c:choose>
    </body> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
    <link rel="stylesheet" href="lib/bootstrap-3.1.1-dist/css/bootstrap-theme.css">
    <link rel="stylesheet" href="lib/bootstrap-3.1.1-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="lib/bootstrap-3.1.1-dist/css/bootstrap.css">
    <link rel="stylesheet" href="lib/bootstrap-3.1.1-dist/css/site.css">
    <link rel="stylesheet" href="resources/style.css">
    <script type="text/javascript" src="lib/bootstrap-3.1.1-dist/js/bootstrap.js"></script>
    <meta charset=utf-8 />
</html>  
