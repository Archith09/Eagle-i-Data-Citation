<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eagle-i Citation</title>
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" type="text/css"  href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="fonts/font-awesome/css/font-awesome.css">
<link href="css/owl.carousel.css" rel="stylesheet" media="screen">
<link href="css/owl.theme.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"  href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/animate.min.css">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<script src="https://code.jquery.com/jquery-2.2.2.min.js"></script>

<style>
h1.capitalize {
	text-transform: capitalize;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('#submit').click(function(e){
			e.preventDefault();
			var resource = $('#resource').val();
			$.ajax({
				type:'GET',
				data:{resource: resource},
				url:'EagleServlet',
				success:function(result){
					var win = window.open();
					var text = '<h1>Eagle-i Citation</h1>'+ 
					'<div style="position: absolute; top: 100px; left: 10px; right: 10px"><TABLE style="border:1px dashed black;" SIZE=600 HEIGHT=100 bgcolor="#D3D3D3"><TR><TD>'+result+'</TD></TR></TABLE></div>' +
					'<div style="position: absolute; top: 230px; left:500px"><p>Citation is also available in below formats.</p></div>' +
					'<div style="position: absolute; top: 280px; left:500px"><p><ul class="list-inline"><a href="http://dblp.uni-trier.de/pers/hb/c/Crawford:Diane" target="_blank"><img alt="" src="http://dblp.uni-trier.de/img/bibtex.dark.16x16.png" class="icon">BibTeX</a> | <a href="file:///Users/archith/Desktop/outfilename.xml"><img alt="" src="http://dblp.uni-trier.de/img/xml.dark.16x16.png" class="icon">XML</a> | <a href="http://dblp.uni-trier.de/rec/ris/conf/a4cloud/2014.ris"><img alt="" src="http://dblp.uni-trier.de/img/endnote.dark.16x16.png" class="icon">RIS</a></ul></p></div>';
					win.document.write(text)
				}
			});
		});
	});
</script>
</head>

<body>
<div id="preloader">
  <div id="status"> <img src="img/preloader.gif" height="64" width="64" alt=""> </div>
</div>

<nav id="menu" class="navbar navbar-default navbar-fixed-top">
  <div class="container"> 
    <div class="navbar-header">
      <img src="img/penn.jpg" alt="" style="margin:30px 0px 0px 10px;" width="100" height="40" align="left">
    </div>
    
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="#home" class="page-scroll" style="color: grey;">Home</a></li>
        <li><a href="#about-section" class="page-scroll" style="color: grey;">About</a></li>
      </ul>
    </div>
  </div>
</nav>

<header class="text-center" name="home">
  <div class="intro-text">
    <div style="position: absolute; top: 100px; left: 130px">
    <h1 class="wow fadeInDown">Welcome to <span class="color"> <a href="https://www.eagle-i.net/" class="color">EAGLE-I</a></span> Citation</h1>
    </div>
    <h3 class="wow fadeInDown">Please enter Eagle-ID<strong><span class="color"></span></strong></h3>
    <!--a href="#works-section" class="btn btn-default btn-lg page-scroll wow fadeInUp" data-wow-delay="200ms">Start</a--> 
  
  
            <form action="EagleServlet" > 
              <div class="form-group">
                     <input type="text" name="resource" id="resource">
              </div>
              <div class="custom-wrapper">
    	          <button type="submit" class="btn btn-default" id="submit">Submit</button>
              </div>      
          	</form>

			<div style="position: absolute; bottom: -80px; right: 10px">
                <ul>
                    <li><h6>Team: Susan Davidson, Leshang Chen, Archith Shivanagere</h6></li>
                    <li><h6>This work in part supported by NSF IIS 1302212</h6></li>                
                </ul>
        	</div>
  </div> 
</header>

<!-- About Section -->
<div id="about-section">
  <div class="container">
    <div class="section-title text-center wow fadeInDown">
    <div class="row">
      <div class="col-md-4 wow fadeInLeft" style="top: 100px"> 
      	<h4>Resources at Universities We Can Cite</h4>
          <div class="space"></div><div class="list-style">
            <div class="row">
              <div class="col-lg-6 col-sm-6 col-xs-12" style="position: absolute; left: 80px">
                <ul>
                  <p>UPenn</p>
                  <p>Harvard</p>   
                  <p>Dartmouth</p>                 
                  <p>Howard</p>               
                </ul>
              </div>
            </div>
          </div>
       </div>
      <div class="col-md-6 wow fadeInRight" style="left: 180px">
          <div class="space"></div>
                    <p style="font-size:120%;" align="justify">Much scientific publishing now involves data stored in a database, contributed by members of the scientific community, and extracted using queries. The goal of the research supported by NSF IIS 1302212 is to develop a framework for data citation which takes into account the large number of possible queries and therefore different citations; the need for citations to be both human and machine readable; and the need for citations to conform to various specifications and standards.</p> 
					<p style="font-size:120%;" align="justify">This project implements a simple framework for Eagle-i citations as specified <a href="https://www.eagle-i.net/get-involved/for-researchers/citing-an-eagle-i-resource/" class="color">here</a>.  RDF datasets have been downloaded from Eagle-i and are queried using SPARQL to generate citations.</p>
					<p style="font-size:120%;" align="justify">The work is currently in progress: only a subset of the institutions are currently included, and since Eagle-i does not export archives resources may change from the time that they were cited.</p>
	      </div>
    </div>
  	</div>
</div>
</div>

<script type="text/javascript" src="js/jquery.1.11.1.js"></script> 
<script type="text/javascript" src="js/bootstrap.js"></script> 
<script type="text/javascript" src="js/SmoothScroll.js"></script> 
<script type="text/javascript" src="js/wow.min.js"></script> 
<script type="text/javascript" src="js/jquery.isotope.js"></script> 
<script type="text/javascript" src="js/jqBootstrapValidation.js"></script> 
<script type="text/javascript" src="js/contact_me.js"></script> 
<script type="text/javascript" src="js/owl.carousel.js"></script> 
<script type="text/javascript" src="js/FileSaver.js"></script> 
<script type="text/javascript" src="js/main.js"></script>
</body>
</html>