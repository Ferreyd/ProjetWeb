<%-- 
    Document   : morceaux-content
    Author     : Jeje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
    "http://www.w3.org/TR/html4/loose.dtd">  

<!-- Ne pas oublier cette ligne sinon tous les tags de la JSTL seront ignorés ! -->  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>
    <head>
       

</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Morceaux</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-6">
                    <ul class="nav nav-pills">
                        <li><a href="ServletMorceau?action=afficherLesMorceaux">Afficher/Rafraichir la liste des morceaux</a></li>
                        <li><a href="ServletMorceau?action=ajouterMorceauxAvecPistes">Ajouter des morceaux</a></li>
                    </ul>
                    <br/>
                </div>  
                <div class="col-lg-6">
                    <form action="ServletMorceau" method="get">
                    <div class="input-group">                       
                            <input type="hidden" name="action" value="rechercheParTitre"/>
                            <input type="text"  class="form-control" name="titre_recherche"/>
                            
                            <span class="input-group-btn">
                                <button class="btn btn-block" type="submit" value="Rechercher" name="submit">Rechercher</button>
                            </span>
                        
                    </div></form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-10">
     <!-- DEBUT TOUS LES MORCEAUX -->        
                    <c:if test="${param['action'] == 'afficherLesMorceaux'}" >
                        
                         <h2>Tableau des morceaux</h2>

                         <table class="table table-bordered table-striped">
                             <thead>
                                <tr>
                                    <td><b>Titre</b></td>
                                    <td><b>Artiste</b></td>
                                     <td><b>Pistes</b></td>
                                     <td><b>Ajouter à votre panier</b></td>
                                </tr>
                             </thead>
                             <tbody>
                                
                                <c:set var="nbPages" value="1"/>
                                <c:set var="nbLignes" value="1"/>  
                                
                                <c:forEach var="m" items="${requestScope['listeMorceaux']}">


                                    <tr>                                               
                                        <td>${m.titre}</td>
                                        <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}</td>
                                        <td><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&id=${m.id}">
                                                <c:forEach var="p" varStatus="comptePiste" items="${m.pistes}">
                                                    <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                    <c:if test="${comptePiste.last}" >  
                                                        ${comptePiste.count}
                                                    </c:if>
                                                </c:forEach>
                                            </a>
                                        </td>
                                        <td><a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter</a></td>
                                    </tr>
                                </c:forEach>
                             </tbody>
                         </table>
                        <ul class="pagination">
                        <c:if test="${param['page'] == null}">
                             <c:set var="after" value="1"/>
                             <li class="disabled"><a>&laquo;</a></li>
                             <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                   <li><a href="ServletMorceau?action=afficherLesMorceaux&page=${compter.index}">${compter.count}</a></li>                                                          
                             </c:forEach>
                             <li><a href="ServletMorceau?action=afficherLesMorceaux&page=${after}">&raquo;</a></li> 
                        </c:if> 
                        </ul>
                        <ul class="pagination">
                        <c:if test="${param['page'] != null}">
                            <c:set var="before" value="${param['page']-1}"/>
                            <c:if test="${before < 0}"></c:if>
                            <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=afficherLesMorceaux&page=${before}">&laquo;</a></li></c:if>
                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                            <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=afficherLesMorceaux&page=${compter.index}">${compter.count}</a></li>                                                          
                            </c:forEach>                         
                            <c:set var="after" value="${param['page']+1}"/>
                            <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=afficherLesMorceaux&page=${after}">&raquo;</a></li></c:if>
                            <c:if test="${after > requestScope['nbPages']}"></c:if>
                        </c:if>
                        </ul>    
                     </c:if>
              <!-- FIN TOUS LES MORCEAUX -->                
             <!-- DEBUT RESULTAT DE RECHERCHE -->
                    <c:if test="${param['action'] == 'rechercheParTitre'}" >
                       <c:if test="${requestScope['nbResultatParMorceau'] == 0}" > 
                            <div class="alert alert-info">Aucun resultat trouvé pour "${param['titre_recherche']}"</div>
                       </c:if>
                      <c:if test="${requestScope['nbResultatParMorceau'] != 0}" >  
                         <h2>${requestScope['nbResultatParMorceau']} resultat(s) pour "${param['titre_recherche']}"</h2>
                         <table class="table table-bordered table-striped">
                             <thead>
                                <tr>
                                    <th><b>Titre</b></th>
                                    <th><b>Artiste</b></th>
                                     <th><b>Pistes</b></th>
                                </tr>
                             </thead>
                             <tbody>
                             <c:forEach var="m" items="${requestScope['resultatsParMorceau']}">
                                 
                                 <tr>                                               
                                     <td>${m.titre}</td>
                                     <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}</a></td>
                                     <td><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&id=${m.id}">
                                             <c:forEach var="p" varStatus="compter" items="${m.pistes}">
                                                 <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                 <c:if test="${compter.last}" >  
                                                     ${compter.count}
                                                 </c:if>
                                             </c:forEach>
                                         </a>
                                     </td>
                                 </tr>
                             </c:forEach>
                            </tbody>
                         </table>
                         <c:if test="${requestScope['nbResultatParMorceau'] > 10 }">
                         <ul class="pagination">
                            <c:if test="${param['page'] == null}">
                                    <c:set var="after" value="1"/>
                                    <li class="disabled"><a>&laquo;</a></li>
                                    <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                          <li><a href="ServletMorceau?action=rechercheParTitre&titre_recherche=${param['titre_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                    </c:forEach>
                                    <li><a href="ServletMorceau?action=rechercheParTitre&titre_recherche=${param['titre_recherche']}&page=${after}">&raquo;</a></li> 
                             </c:if>    
                        </c:if> 
                        </ul>
                        <ul class="pagination">
                        <c:if test="${param['page'] != null}">
                            <c:set var="before" value="${param['page']-1}"/>
                            <c:if test="${before < 0}"></c:if>
                            <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=rechercheParTitre&titre_recherche=${param['titre_recherche']}&page=${before}">&laquo;</a></li></c:if>
                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                            <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=rechercheParTitre&titre_recherche=${param['titre_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                            </c:forEach>                         
                            <c:set var="after" value="${param['page']+1}"/>
                            <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=rechercheParTitre&titre_recherche=${param['titre_recherche']}&page=${after}">&raquo;</a></li></c:if>
                            <c:if test="${after > requestScope['nbPages']}"></c:if>
                        </c:if>
                        </ul>                           
                         
                       </c:if>     
                     </c:if>                
              <!-- FIN RESULTAT DE RECHERCHE -->            
               <!-- DEBUT DETAIL PISTES -->          
                    <c:if test="${param['action'] == 'afficherLesMorceauxEtPistes'}" >
                        <a href="ServletMorceau?action=afficherLesMorceaux">Retour morceaux</a><br/>

                        <h2>Tableau des pistes</h2>

                        <table class="table table-striped">
                            <tr>
                                <td><b>Pistes de ${requestScope['titreMorceau']}</b></td>
                            </tr>

                            <c:forEach var="p" items="${requestScope['listePistes']}">
                                <tr>                                               
                                    <td>${p.nom}</td>                               
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>

                    <c:if test="${param['action'] == 'ficheArtiste'}" >
                        <h2>${requestScope['nomArtiste']}</h2>
                    </c:if>
   <!-- FIN DETAIL PISTES --> 
                </div>
            </div>
        </div>
    </body>
</html>  

