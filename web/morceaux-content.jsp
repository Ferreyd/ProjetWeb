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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="lib/jquery-1.11.0.js"></script>
        <link rel="stylesheet" href="resources/wikipedia.css">
        <title>Morceaux</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <jsp:include page="recherche.jsp"/>  
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ul class="nav nav-tabs">
                        <li><a href="ServletMorceau?action=afficherLesMorceauxEtPistes">Tous</a></li>
                            <c:forEach var="i" items="${requestScope['lesInstrus']}">
                            <li><a href="ServletMorceau?action=afficherParInstrument&instru=${i.nom}">${i.nom}</a></li>
                            </c:forEach>
                    </ul>
                    <br/>
                </div>  
            </div>
            <div class="row">
                <div class="col-md-10">

                    <!-- DEBUT RESULTAT DE RECHERCHE -->
                    <c:if test="${param['action'] == 'recherche'}" >
                        <!-- DEBUT RESULTAT DE RECHERCHE PAR TITRE -->
                        <c:if test="${param['typeRecherche'] == 'rechercheTitre'}">
                            <c:if test="${requestScope['nbResultatParMorceau'] == 0}" > 
                                <div class="alert alert-info">Aucun resultat trouvé pour "${param['champ_recherche']}"</div>
                            </c:if>
                            <c:if test="${requestScope['nbResultatParMorceau'] != 0}" >  
                                <h2>${requestScope['nbResultatParMorceau']} resultat(s) pour le titre "${param['champ_recherche']}"</h2>
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th><b>Titre</b></th>
                                            <th><b>Artiste</b></th>
                                            <th><b>Pistes</b></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="m" items="${requestScope['resultatsParMorceau']}">

                                            <tr>                                               
                                                <td>${m.titre}</td>
                                                <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}</a></td>
                                                <td>                                  
                                                    <c:forEach var="p" varStatus="compter" items="${m.pistes}">
                                                        <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                        <c:if test="${compter.last}" >  
                                                            ${compter.count}
                                                        </c:if>
                                                    </c:forEach>            
                                                </td>
                                                <td>
                                                    <a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter </a>
                                                    <button class="btn btn-info" id="showPistes${m.id}" onclick="javascript:show_hide('${m.id}')">Voir pistes</button>
                                                    <c:if test="${abonnement != 'gratuit'}" >
                                                        <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            <tr id="${m.id}" style="display:none; ">
                                                <td colspan="4"  >
                                                    <c:forEach var="p" items="${m.pistes}">

                                                        <ul>
                                                            <li>${p.nom}</li>
                                                        </ul>

                                                    </c:forEach>
                                                </td>
                                            </tr>                                         
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <c:if test="${requestScope['nbResultatParMorceau'] > 10 }">
                                    <ul class="pagination">
                                        <c:if test="${param['page'] == null}">
                                            <c:set var="after" value="1"/>

                                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                                <li <c:if test="${compter.first}">class="active"</c:if>><a href="ServletMorceau?action=recherche&typeRecherche=rechercheTitre&champ_recherche=${param['champ_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                                </c:forEach>
                                            <li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheTitre&champ_recherche=${param['champ_recherche']}&page=${after}">&raquo;</a></li> 
                                            </c:if> 
                                    </ul>
                                </c:if> 

                                <ul class="pagination">
                                    <c:if test="${param['page'] != null}">
                                        <c:set var="before" value="${param['page']-1}"/>
                                        <c:if test="${before < 0}"></c:if>
                                        <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheTitre&champ_recherche=${param['champ_recherche']}&page=${before}">&laquo;</a></li></c:if>
                                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                                            <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=recherche&typeRecherche=rechercheTitre&champ_recherche=${param['champ_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                            </c:forEach>                         
                                            <c:set var="after" value="${param['page']+1}"/>
                                            <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheTitre&champ_recherche=${param['champ_recherche']}&page=${after}">&raquo;</a></li></c:if>
                                            <c:if test="${after > requestScope['nbPages']}"></c:if>
                                        </c:if>
                                </ul>                           

                            </c:if>
                        </c:if>
                        <!-- FIN RESULTAT DE RECHERCHE PAR TITRE--> 
                        <!-- DEBUT RESULTAT DE RECHERCHE PAR ARTISTE -->
                        <c:if test="${param['typeRecherche'] == 'rechercheArtiste'}">
                            <c:if test="${requestScope['nbResultatParArtiste'] == 0}" > 
                                <div class="alert alert-info">Aucun resultat trouvé pour "${param['champ_recherche']}"</div>
                            </c:if>
                            <c:if test="${requestScope['nbResultatParArtiste'] != 0}" >  
                                <h2>${requestScope['nbResultatParArtiste']} resultat(s) pour l'artiste "${param['champ_recherche']}"</h2>
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th><b>Titre</b></th>
                                            <th><b>Artiste</b></th>
                                            <th><b>Pistes</b></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="m" items="${requestScope['resultatsParArtiste']}">

                                            <tr>                                               
                                                <td>${m.titre}</td>
                                                <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}</a></td>
                                                <td>
                                                    <c:forEach var="p" varStatus="compter" items="${m.pistes}">
                                                        <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                        <c:if test="${compter.last}" >  
                                                            ${compter.count}
                                                        </c:if>
                                                    </c:forEach>

                                                </td>
                                                <td>
                                                    <a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter </a>
                                                    <button class="btn btn-info" id="showPistes${m.id}" onclick="javascript:show_hide('${m.id}')">Voir pistes</button>
                                                    <c:if test="${abonnement != 'gratuit'}" >
                                                        <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                                    </c:if>
                                                </td>

                                            </tr>
                                            <tr id="${m.id}" style="display:none; ">
                                                <td colspan="4"  >
                                                    <c:forEach var="p" items="${m.pistes}">

                                                        <ul>
                                                            <li>${p.nom}</li>
                                                        </ul>

                                                    </c:forEach>
                                                </td>
                                            </tr>                                          
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <c:if test="${requestScope['nbResultatParArtiste'] > 10 }">
                                    <ul class="pagination">
                                        <c:if test="${param['page'] == null}">
                                            <c:set var="after" value="1"/>

                                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                                <li <c:if test="${compter.first}">class="active"</c:if>><a href="ServletMorceau?action=recherche&typeRecherche=rechercheArtiste&champ_recherche=${param['champ_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                                </c:forEach>
                                            <li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheArtiste&champ_recherche=${param['champ_recherche']}&page=${after}">&raquo;</a></li> 
                                            </c:if>   
                                    </ul>
                                </c:if> 

                                <ul class="pagination">
                                    <c:if test="${param['page'] != null}">
                                        <c:set var="before" value="${param['page']-1}"/>
                                        <c:if test="${before < 0}"></c:if>
                                        <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheArtiste&champ_recherche=${param['champ_recherche']}&page=${before}">&laquo;</a></li></c:if>
                                            <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                                            <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=recherche&typeRecherche=rechercheArtiste&champ_recherche=${param['champ_recherche']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                            </c:forEach>                         
                                            <c:set var="after" value="${param['page']+1}"/>
                                            <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=recherche&typeRecherche=rechercheArtiste&champ_recherche=${param['champ_recherche']}&page=${after}">&raquo;</a></li></c:if>
                                            <c:if test="${after > requestScope['nbPages']}"></c:if>
                                        </c:if>
                                </ul>                           

                            </c:if>
                        </c:if>
                        <!-- FIN RESULTAT DE RECHERCHE PAR ARTISTE-->
                    </c:if>                
                    <!-- FIN RESULTAT DE RECHERCHE --> 
                    <!-- DEBUT RESULTAT PAR INSTRU --> 
                    <c:if test="${param['action'] == 'afficherParInstrument'}" >               


                        <h2>${param['instru']}</h2>

                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <td><b>Titre</b></td>
                                    <td><b>Artiste</b></td>
                                    <td><b>Pistes</b></td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>


                                <c:forEach var="m" items="${requestScope['listeMorceaux']}">


                                    <tr>                                               
                                        <td>${m.titre}</td>
                                        <td><a href="ServletMorceau?action=ficheArtiste&artiste_id=${m.artiste.id}">${m.artiste.nom}

                                        </td>

                                        <td>
                                            <c:forEach var="p" varStatus="comptePiste" items="${m.pistes}">
                                                <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                <c:if test="${comptePiste.last}" >  
                                                    ${comptePiste.count}
                                                </c:if>
                                            </c:forEach>                                      
                                        </td>

                                        <td>
                                            <a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter </a>
                                            <button class="btn btn-info" id="showPistes${m.id}" onclick="javascript:show_hide('${m.id}')">Voir pistes</button>
                                            <c:if test="${abonnement != 'gratuit'}" >
                                                <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr id="${m.id}" style="display:none; ">
                                        <td colspan="4"  >
                                            <c:forEach var="p" items="${m.pistes}">

                                                <ul>
                                                    <li>${p.nom}</li>
                                                </ul>

                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <ul class="pagination">
                            <c:if test="${requestScope['nbResultatParInstru'] > 10 }">
                                <c:if test="${param['page'] == null}">
                                    <c:set var="after" value="1"/>

                                    <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                        <li <c:if test="${compter.first}">class="active"</c:if>><a href="ServletMorceau?action=afficherParInstrument&instru=${param['instru']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                        </c:forEach>
                                    <li><a href="ServletMorceau?action=afficherParInstrument&instru=${param['instru']}&page=${after}">&raquo;</a></li> 
                                    </c:if> 
                                </c:if>
                        </ul>
                        <ul class="pagination">
                            <c:if test="${param['page'] != null}">
                                <c:set var="before" value="${param['page']-1}"/>
                                <c:if test="${before < 0}"></c:if>
                                <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=afficherParInstrument&instru=${param['instru']}&page=${before}">&laquo;</a></li></c:if>
                                    <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                                    <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=afficherParInstrument&instru=${param['instru']}&page=${compter.index}">${compter.count}</a></li>                                                          
                                    </c:forEach>                         
                                    <c:set var="after" value="${param['page']+1}"/>
                                    <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=afficherParInstrument&instru=${param['instru']}&page=${after}">&raquo;</a></li></c:if>
                                    <c:if test="${after > requestScope['nbPages']}"></c:if>
                                </c:if>
                        </ul>    

                    </c:if>
                    <!-- FIN PAR INSTRU -->
                    <!-- DEBUT MORCEAUX ET PISTES -->          
                    <c:if test="${param['action'] == 'afficherLesMorceauxEtPistes'}" >               


                        <h2>Tableau des morceaux</h2>

                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <td><b>Titre</b></td>
                                    <td><b>Artiste</b></td>
                                    <td><b>Pistes</b></td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="nbPages" value="1"/>
                                <c:set var="nbLignes" value="1"/>  
                                <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                                    <tr>                                               
                                        <td>${m.titre}</td>
                                        <td><a data-toggle="modal" href="#artiste${m.artiste.id}" >${m.artiste.nom}</a>
                                            <div id="artiste{m.artiste.id}" class="modal hide fade in" style="display: none; ">
                                                <div class="modal-header">
                                                    <a class="close" data-dismiss="modal">×</a>
                                                    <h3>${m.artiste.nom}</h3>
                                                </div>
                                                <div class="modal-body">
                                                    <h4>Text in a modal</h4>
                                                    <p>You can add some text here.</p>		        
                                                </div>
                                                <div class="modal-footer">
                                                    <a href="#" class="btn btn-success">Call to action</a>
                                                    <a href="#" class="btn" data-dismiss="modal">Close</a>
                                                </div>
                                            </div>
                                        </td>

                                        <td>
                                            <c:forEach var="p" varStatus="comptePiste" items="${m.pistes}">
                                                <!-- NOMBRE DE PISTES DU MORCEAU -->
                                                <c:if test="${comptePiste.last}" >  
                                                    ${comptePiste.count}
                                                </c:if>
                                            </c:forEach>                                      
                                        </td>

                                        <td>
                                            <a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter </a>
                                            <button class="btn btn-info" id="showPistes${m.id}" onclick="javascript:show_hide('${m.id}')">Voir pistes</button>
                                            <c:if test="${abonnement != 'gratuit'}" >
                                                <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr id="${m.id}" style="display:none; ">
                                        <td colspan="4"  >
                                            <c:forEach var="p" items="${m.pistes}">

                                                <ul>
                                                    <li>${p.nom}</li>
                                                </ul>

                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <ul class="pagination">
                            <c:if test="${param['page'] == null}">
                                <c:set var="after" value="1"/>

                                <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">
                                    <li <c:if test="${compter.first}">class="active"</c:if>><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&page=${compter.index}">${compter.count}</a></li>                                                          
                                    </c:forEach>
                                <li><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&page=${after}">&raquo;</a></li> 
                                </c:if> 
                        </ul>
                        <ul class="pagination">
                            <c:if test="${param['page'] != null}">
                                <c:set var="before" value="${param['page']-1}"/>
                                <c:if test="${before < 0}"></c:if>
                                <c:if test="${before >= 0}"><li><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&page=${before}">&laquo;</a></li></c:if>
                                    <c:forEach var="m" varStatus="compter" begin="0" end="${requestScope['nbPages']}">                                     
                                    <li <c:if test="${compter.index == param['page']}">class="active"</c:if>><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&page=${compter.index}">${compter.count}</a></li>                                                          
                                    </c:forEach>                         
                                    <c:set var="after" value="${param['page']+1}"/>
                                    <c:if test="${after <= requestScope['nbPages']}"><li><a href="ServletMorceau?action=afficherLesMorceauxEtPistes&page=${after}">&raquo;</a></li></c:if>
                                    <c:if test="${after > requestScope['nbPages']}"></c:if>
                                </c:if>
                        </ul>    

                    </c:if>

                    <c:if test="${param['action'] == 'ficheArtiste'}" >

                        <h3>Morceaux présent sur le site</h3>
                        <table class="table table-bordered table-striped" border="1">
                            <tr>
                                <th>Titre</th>
                                <th>Genre</th>
                                <th>Annee</th>
                                <th></th>
                                    <c:forEach var="m" items="${requestScope['listeMorceaux']}">
                                <tr>                                               
                                    <td>${m.titre}</td>
                                    <td>${m.genre.nom}</td>
                                    <td>${m.annee}</td>
                                    <td> 
                                        <a class="btn btn-success" href="ServletMorceau?action=ajoutMorceauPanier&id=${m.id}">Ajouter </a>
                                        <button class="btn btn-info" id="showPistes${m.id}" onclick="javascript:show_hide('${m.id}')">Voir pistes</button>
                                        <c:if test="${abonnement != 'gratuit'}" >
                                            <a class="btn btn-primary" href="http://mt5demo.gexsoft.com/music/${m.titre}">Ecouter</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <h2>${requestScope['artiste']}</h2>
                        <c:set var="artiste" value="${artiste}"/>

                        <h3>Information sur l'artiste</h3>
                        <img style="text-align: center" src="${imageArtiste}">
                        <div id="wikiInfo" class="col-lg-12">&nbsp;</div>


                    </c:if>
                    <!-- FIN DETAIL PISTES --> 
                </div>
            </div>


        </div>

    </body>
</html>
<script>
    function show_hide(id)
    {
        if (document.getElementById(id).style.display == 'none')
        {
            document.getElementById(id).style.display = "";
            document.getElementById('showPistes' + id).innerHTML = 'Cacher pistes';
        }
        else
        {
            document.getElementById(id).style.display = "none";
            document.getElementById('showPistes' + id).innerHTML = 'Voir pistes';
        }
        return true;
    }

    var url = "http://fr.wikipedia.org/w/api.php?action=parse&format=json&callback=?";
    var page = "${artiste}";
    $.getJSON(url, {
        page: page,
        prop: "text|images",
        section: 0,
        uselang: "fr"
    }, function(json) {
        $('#wikiInfo').html(json.parse.text["*"]);
        $("#wikiInfo").find("a:not(.references a)").attr("href", function() {
            return "http://fr.wikipedia.org" + $(this).attr("href"); //Pour les liens wikipedia
        });
        $("#wikiInfo").find("div.infobox_v3").addClass("col-md-6").hide();
        $("#wikiInfo").find("table").hide();
        $("#wikiInfo").find("a").attr("target", "_blank");
        $("#wikiInfo").find("div.homonymie").hide();
        $("#wikiInfo").find(".icone_de_titre").hide();
        $("#wikiInfo").find(".entete icon musique").hide();
        $("#wikiInfo").find(".thumbcaption").hide();
        $("#wikiInfo").find("p.navbar").hide();
        $("#wikiInfo").find("sup.reference").hide();
        $("#wikiInfo").find("strong.error").hide();
    });
</script>




