document.onkeydown = function disableF5BackSpace(e){

	evt = e || window.event;
	    	   
	//alert(evt.target.name); 
	//alert("-"+evt.target.type+"-"); //"text"
	  
	// keycode=8 => backspace, keycode=37 => <-,  keycode=39 => ->
	if(evt.target.type!="text" && evt.target.type!="textarea" && evt.target.type!="password"){		
		if(evt.keyCode == 8 || evt.keyCode==37 || evt.keyCode==39){
			evt.preventDefault();
		}
	}
	
	//Bloqueo de Teclas en general, independiente del componente donde se encuentra el cursor
	// keycode=116 -> F5,  keycode = 18 => alt,  e.altKey = true => si está precionada la tecla alt en combinación de cualquiera de <- y ->
	if(evt.keyCode == 116 || ( e.altKey && (evt.keyCode==37 || evt.keyCode==38))){
		evt.preventDefault();
	}
	
}

/*

document.onkeydown = function disableF5BackSpace(e){

evt = e || window.event;
    	   
//alert(evt.target.name); 
//alert("-"+evt.target.type+"-"); //"text"
   
if(evt.target.type!="text" && evt.keyCode == 8){	
	evt.preventDefault();
}

if(evt.keyCode == 116 || evt.keyCode==37 || evt.keyCode==39){
	evt.preventDefault();
}

}
*/

/**
 * Este script es para bloquear las funciones del clic del raton, 
 * dependiendo el tipo de elemento seleccionado
 */

function checkClickIE() {
	evt = window.event;
	if (navigator.appName == 'Microsoft Internet Explorer' && (event.button == "2" || event.button == "3")){
		//muestra el tipo de elemento
		//alert("type: " + evt.target.type + "-");
		//solo para los elementos DIFERENTES de text, se bloquea el menu contextual 
		if (evt.target.type != "text" && evt.target.type != "textarea") {
			document.oncontextmenu = new Function("return false");
		}else{
			document.oncontextmenu = null;
		}
		return false;
	}
}

function checkClickNS(e) {
	evt = e || window.event;
	if (document.layers || (document.getElementById && !document.all)) {
		if (e.which == "3" || e.which == "2") {
			//muestra el tipo de elemento
			//alert("type: " + evt.target.type + "-");
			//solo para los elementos DIFERENTES de texto, se bloquea el menu contextual 
			if (evt.target.type != "text" && evt.target.type != "textarea") {
				document.oncontextmenu = new Function("return false");
			}else{
				document.oncontextmenu = null;
			}
			return false;
		}
	}
}
document.onmousedown = checkClickIE;
document.onmouseup = checkClickNS;   
