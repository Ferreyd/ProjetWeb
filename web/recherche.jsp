<form action="ServletMorceau" method="get">
    <div class="input-group recherche">                       
            <input type="hidden" name="action" value="recherche"/>
            <span class="input-group-addon">
                 <input checked type="radio" name="typeRecherche" value="rechercheArtiste" /> Artiste
                 <input type="radio" name="typeRecherche" value="rechercheTitre" /> Titre
            </span>                                
            <input type="text"  class="form-control" placeholder="Rechercher un artiste ou un titre"name="champ_recherche"/>

            <span class="input-group-btn">
                <button class="btn btn-block btn-info" type="submit" value="Rechercher" name="submit">Rechercher</button>
            </span>

    </div>
</form>