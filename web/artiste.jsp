<!--
//working url:
http://en.wikipedia.org/w/api.php?action=query&prop=revisions&titles=Threadless&rvprop=content&format=json&rvsection=0&rvparse=1

//Explanation
//Base Url:
http://en.wikipedia.org/w/api.php?action=query

//tell it to get revisions:
&prop=revisions

//define page titles separated by pipes. In the example i used t-shirt company threadless
&titles=whatever|the|title|is

//specify that we want the page content
&rvprop=content

//I want my data in JSON, default is XML
&format=json

//lets you choose which section you want. 0 is the first one.
&rvsection=0

//tell wikipedia to parse it into html for you
&rvparse=1


Using Extracts (better/easier for what i'm doing)

//working url:
http://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=Threadless&format=json&exintro=1

//only explaining new parameters
//instead of revisions, we'll set prop=extracts
&prop=extracts

//if we just want the intro, we can use exintro. Otherwise it shows all sections
&exintro=1


http://en.wikipedia.org/w/api.php
-->

<div id="example" class="modal hide fade in" style="display: none; ">
            <div class="modal-header">
              <a class="close" data-dismiss="modal">×</a>
              <h3>This is a Modal Heading</h3>
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