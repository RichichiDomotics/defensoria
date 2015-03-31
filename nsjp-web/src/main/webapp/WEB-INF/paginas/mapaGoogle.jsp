<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<title>Mapa</title>
</head>
<body class="page page-id-1877 page-template page-template-tpl_googlemaps-php">

<style type="text/css">

div#map {
    position: relative;
  width:100%;
  height:400px;
  margin-top: 80px;
  margin-bottom:40px;
  border-top: 4px solid #333;
  border-bottom: 4px solid #333;
}

div#map_canvas {
  width:100%;
  height:400px;
}

div#map .zoom{
  display: none;
}

div#map .form{
  position: absolute;
  top: -54px;
  left: 50%;
  width:980px;
  height:20px;
  margin:0 0 0 -490px;
  text-align: center;
  line-height: 50px;
  -webkit-border-bottom-right-radius: 2px;
  -webkit-border-bottom-left-radius: 2px;
  -moz-border-radius-bottomright: 2px;
  -moz-border-radius-bottomleft: 2px;
  border-bottom-right-radius: 2px;
  border-bottom-left-radius: 2px;
  z-index: 1;
}

div#map .form .google{
  position: absolute;
  top: 7px;
  left: 14px;
  height: 30px;
  z-index: 1;
}
div#map .coordinates{
  position: absolute;
  bottom: 20px;
  left: 50%;
  width:499px;
  height:40px;
  margin:0 0 0 -250px;
  text-align: center;
  line-height: 50px;
  color: #fff;
  z-index: 1;
}
div#map .coordinates em{
  position: absolute;
  top: -20px;
  width: 249px;
  height: 20px;
  background: #6BAB96;
  color: #000;
  font-style: normal;
  letter-spacing: 1px;
  font-size: 10px;
  line-height: 20px;
  text-transform: uppercase;
  font-weight: normal;
}
div#map .coordinates em.lat{
  left: 0;
}
div#map .coordinates em.lon{
  right: 0;
}
div#map .coordinates span{
  display: block;
  float: left;
  width: 249px;
  font-size: 18px;
  line-height: 40px;
  background: #333;
}
div#map .coordinates span#lng{
  float: right;
}
div#map .coordinates span:hover{
  background: #111;
}

div#map .address{
  position: absolute;
  bottom: -44px;
  left: 0;
  width:100%;
  height:40px;
  text-align: center;
  line-height: 40px;
  font-weight: bold;
}

div#crosshair {
    display: block;
    position: absolute;
    top: 50%;
  left: 50%;
    height: 17px;
    width: 16px;
    margin-left: -8px;
  margin-top: -8px;
    background-image: url(<%= request.getContextPath() %>/resources/images/bg-sprite.png);
    background-position: 0 -23px;
    background-repeat: no-repeat;
}

.logohtml5 {
  display: none;
}
</style>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
  var map;
  var geocoder;
  var centerChangedLast;
  var reverseGeocodedLast;
  var currentReverseGeocodeResponse;
  var idFrameMapa;

  
  function initialize() {
    var direccion='<%= request.getParameter("direccion")%>';   
	idFrameMapa='<%= request.getParameter("idFrameMapa")%>';  
    var latlng = new google.maps.LatLng(23.945963820559726,-99.50504753749999);
    var myOptions = {
      zoom: 4,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    geocoder = new google.maps.Geocoder();
    setupEvents();
    centerChanged();
	
	if(direccion!=null && direccion!='undefined'){
		$('#address').val(direccion);
		geocode();		
	}
	
  }

  function setupEvents() {
    reverseGeocodedLast = new Date();
    centerChangedLast = new Date();

    setInterval(function() {
      if((new Date()).getSeconds() - centerChangedLast.getSeconds() > 1) {
        if(reverseGeocodedLast.getTime() < centerChangedLast.getTime())
          reverseGeocode();
      }
    }, 1000);

//     google.maps.event.addListener(map, 'zoom_changed', function() {
//       document.getElementById("zoom_level").innerHTML = map.getZoom();
//     });

    google.maps.event.addListener(map, 'center_changed', centerChanged);

    google.maps.event.addDomListener(document.getElementById('crosshair'),'dblclick', function() {
       map.setZoom(map.getZoom() + 1);
    });

  }

  function getCenterLatLngText() {
    return '(' + map.getCenter().lat() +', '+ map.getCenter().lng() +')';
  }

  function centerChanged() {
    centerChangedLast = new Date();
    var latlng = getCenterLatLngText();
    var lat = map.getCenter().lat();
    var lng = map.getCenter().lng();
    document.getElementById('lat').innerHTML = lat;
    document.getElementById('lng').innerHTML = lng;
    document.getElementById('formatedAddress').innerHTML = '';
    currentReverseGeocodeResponse = null;
	//alert(lat);
	//alert(lng);
  }

  function reverseGeocode() {
    reverseGeocodedLast = new Date();
    geocoder.geocode({latLng:map.getCenter()},reverseGeocodeResult);
  }

  function reverseGeocodeResult(results, status) {
    currentReverseGeocodeResponse = results;
    if(status == 'OK') {
      if(results.length == 0) {
        document.getElementById('formatedAddress').innerHTML = 'None';
      } else {
        document.getElementById('formatedAddress').innerHTML = results[0].formatted_address;
		
      }
    } else {
      document.getElementById('formatedAddress').innerHTML = 'Error';
    }
  }

  function geocode() {
    var address = document.getElementById("address").value;
    geocoder.geocode({
      'address': address,
      'partialmatch': true}, geocodeResult);
  }

  function geocodeResult(results, status) {
    if (status == 'OK' && results.length > 0) {
      map.fitBounds(results[0].geometry.viewport);
    } else {
      alert("No se tuvo éxito por la siguiente razón: " + status);
    }
  }

  function addMarkerAtCenter() {
    var marker = new google.maps.Marker({
        position: map.getCenter(),
        map: map
    });

    var text = 'Lat/Lng: ' + getCenterLatLngText();
    if(currentReverseGeocodeResponse) {
      var addr = '';
      if(currentReverseGeocodeResponse.size == 0) {
        addr = 'None';
      } else {
        addr = currentReverseGeocodeResponse[0].formatted_address;
      }
      text = text + '<br>' + 'Dirección: <br>' + addr;
    }

    var infowindow = new google.maps.InfoWindow({ content: text });

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.open(map,marker);
    });
  }
  
</script>

<script>

// function kcoords(lat, lng) {
//     var x  = Math.abs(lat);
	
//     window.parent.latGrados = Math.floor(x);
//     window.parent.latMinutos = Math.floor((x - window.parent.latGrados)*60);
//     window.parent.latSegundos  = Math.floor(((x - window.parent.latGrados) - (window.parent.latMinutos/60))*3600);
    
// 	if (lat.charAt(0) === '-'){
// 		window.parent.noteSur='S';
// 	}else{
// 		window.parent.noteSur='N';
// 	}

//     var y  = Math.abs(lng);
//     window.parent.lngGrados = Math.floor(y);
//     window.parent.lngMinutos = Math.floor((y - window.parent.lngGrados)*60);
//     window.parent.lngSegundos = Math.floor(((y - window.parent.lngGrados) - (window.parent.lngMinutos/60))*3600);
	
//     if (lng.charAt(0) === '-'){
//     	window.parent.esteOeste='O';
// 	}else{
// 		window.parent.esteOeste='E';
// 	}
	
//     return (window.parent.noteSur + window.parent.latGrados + '°' + window.parent.latMinutos + 'min ' + window.parent.latSegundos  
//     	    + 'seg ' + window.parent.esteOeste +window.parent.lngGrados + '°' + window.parent.lngMinutos + 'min ' + 
//     	    window.parent.lngSegundos + 'seg');
// }

    function guardarCoordenadas(){

        	window.parent.latitud = $('#lat').text();
        	window.parent.longitud = $('#lng').text();   
        	window.parent.direccionGoogle = $('#formatedAddress').text();   	
			window.parent.cierraMapa(idFrameMapa);
			window.parent.modificarMapa='si';
    }


	
</script>

<div class="inner">

</div>

<div id="map">
    <div id="map_canvas"></div>
    <div id="crosshair"></div>
    <div class="form">
        <p>
			<input type="text" id="address" placeholder="Escribe aquí tu lugar..." value="" class="input"> 
			<input type="button" id="BuscarMap" value="Buscar" onclick="geocode()" class="btn_Generico"> 
<!-- 			<input type="button" value="Insertar marcador" onclick="addMarkerAtCenter()" class="btn_Generico"> -->
			<input type="button" id="GuardarDireccionMap" value="Guardar Direccion" onclick="guardarCoordenadas()" class="btn_Generico">
		</p>
    </div>
    <div class="coordinates">
      <em class="lat">Latitud</em>
      <em class="lon">Longitud</em>
      <span id="lat"></span>
      <span id="lng"></span>
    </div>
    <div class="address">
      <span id="formatedAddress">-</span>
    </div>
<!--     <span id="zoom_level"></span> -->
</div>

<footer id="footer">
    
    <div class="inner">
    
        
  

    </div>

</footer>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 

<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-9981803-1");
pageTracker._trackPageview();
} catch(err) {}
</script>


</body>
</html>

<script>
$(document).ready(function(){
  initialize();
});
</script>