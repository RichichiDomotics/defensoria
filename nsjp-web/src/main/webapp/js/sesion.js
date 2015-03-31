/**
 * 
 */

var tituloInicial = "";

function estaSesionActiva(){
	if(sesionActiva=="false"){
		bloquearPantalla(true, "Cerrando Sesión");
		window.location.href = contextoPagina + "/Logout.do";
	}
}

function  reiniciar(){
	$.idleTimeout.options.onResume.call($.idleTimeout('#dialogBlok', 'div.ui-dialog-buttonpane button:first'));
	$('#password').val("");
	$('#scaptcha').val("");
	$('#imgcaptcha').hide().attr("src", contextoPagina + "/kaptcha.jpg?" + (new Date().getTime()) ).fadeIn();
	$(document).attr("title", tituloIncial);
}

function validaContra(){
	var pass=$('#password').val();
	var capcha=$('#scaptcha').val();

	var error = "";
	var sinError = true; 
	if( $("#password").val().length === 0 ) {
		error += "El campo <b>Contraseña</b> es requerido<br/>";
		sinError = false;
	}
	if( $("#scaptcha").val().length === 0 ) {
		error += "El  <b>Código Captcha</b> es requerido<br/>";
		sinError = false;
	}
	if(!sinError){
		error+="Por favor, verifique que los campos esten completos.";
		customAlert(error, "Validación De Datos");
	}else{
		$.ajax({
	   		type: 'POST',
	    		url: contextoPagina +  '/consultarAutenticidad.do',
	    		data: {password:pass, captcha:capcha, noCache: new Date().getTime()},
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			var op=$(xml).find('usuarioDTO').find('datosIncorrectos').text();
	    			if(op=="false"){
	    				var mensaje = $(xml).find('usuarioDTO').find('idSesion').text();	
	    				customAlert(mensaje, "Error");
	    				$('#imgcaptcha').hide().attr("src", contextoPagina + "/kaptcha.jpg?" + (new Date().getTime()) ).fadeIn();
	    				$('#scaptcha').val("");
	    			}else{
	    				$("#dialog-bloqueo").dialog( "close" );
	    				reiniciar();
	    			}
				}
	   	});
	}
}

function bloqueoSesion(){
	$('#password').width(195);
	$('#scaptcha').width(195);

	$( "#dialog-bloqueo" ).dialog({
		resizable: false,
		height:'auto',
		width:'auto',
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {
				validaContra();
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				bloquearPantalla(true, "Cerrando Sesión");
				window.location.href = contextoPagina + "/Logout.do";
				return false;
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}

function iniciarBloqueoSesion(){
	$("#dialogBlok").dialog({
		autoOpen: false,
		modal: true,
		width: 400,
		height: 200,
		closeOnEscape: false,
		draggable: false,
		resizable: false,
		buttons: {
			'Continuar Trabajando': function(){
				// fire whatever the configured onTimeout callback is.
				// using .call(this) keeps the default behavior of "this" being the warning
				// element (the dialog in this case) inside the callback.
				$(this).dialog('close');
				//bloqueoSesion();
			}
		}
		
	});
	
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
	
	var $countdown = $("#dialog-countdown");
	// start the idle timer plugin
	$.idleTimeout('#dialogBlok', 'div.ui-dialog-buttonpane button:first', {
		idleAfter: tiempoActiva, //variable que vive en el JSP ya que trae la configuración de la BD
		titleMessage: '¡Advertencia!: Tiene %s segundos para volver a iniciar sesión.',
//		pollingInterval: 2,
//		serverResponseEquals: 'OK',
		onTimeout: function(){
			$(this).dialog('close');
			$(document).attr("title", tituloIncial);
			bloqueoSesion();
		},
		onIdle: function(){
				$(this).dialog("open");
		},
		onCountdown: function(counter){
			$countdown.html(counter); // update the counter
		},
		onResume: function(){
			$(document).attr("title", tituloIncial);
		}
	});	
	//Fin
}

function customLogout(){
	
	if (jQuery('#dialog-logout').length == 0){
		jQuery("body").append("<div id=\"dialog-logout\" title=\"Cerrar Sesi&oacute;n\"><p align=\"center\"><span id=\"logout\">¿Desea cerrar su sesi&oacute;n?</span></p></div>");
	}

	jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );

	$( "#dialog-logout" ).dialog({
		autoOpen: false,
		resizable: false,
		//height:290,
		//width:300,
		modal: true,
		buttons: {
			"Aceptar": function() {
				$( this ).dialog( "close" );
				//$( "#dialog:ui-dialog" ).dialog( "destroy" );
				bloquearPantalla(true, "Cerrando Sesión");
				window.location.href = contextoPagina + "/Logout.do";
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				$( "#dialog:ui-dialog" ).dialog( "destroy" );
			}
		}
	});											
}

/*
 *Funcion para consultar los roles extras de cada usuario y
 * construlle el arbol dinamico de los tipos de rol en el menu derecho
 */
function consultarTiposRol() {
	//limpiamos el menu de los tipos de solicitud
	$("#tableRolMenu").empty();
	//lanzamos la consulta del tipo de solicitudes
	$.ajax({
		type: 'POST',
		url: contextoPagina + '/consultaMenuRol.do',
		data: '',
		dataType: 'xml',
		async: false,
		success: function(xml){
			
			var rolesOrdenados = $(xml).find('RolDTO').sort(
					function(a,b){
						var at = $(a).find("descripcionRol").text();
						var bt = $(b).find("descripcionRol").text();
						return (at < bt) ? -1 : 1;
					}
			);
			
			rolesOrdenados.each(function(){
				var rolnuevo=$(this).find("nombreRol").text();
				var rolDesc=$(this).find("descripcionRol").text();
				var trTabla = "<tr>";
				trTabla = trTabla + "<td><span><img src='" + contextoPagina +"/resources/css/check.png' width='16' height='16' />"+
					 					"<a  onclick=\"cargaRolNuevo('"+rolnuevo+"');\">" + rolDesc +
					 					"</a></span></td>";
				trTabla = trTabla + "</tr>";
	
					$('#tableRolMenu').append(trTabla);
			});
		}
		
	});
}

function cargaRolNuevo(rolNuevo){
	///rolRedirec
	//alert(rolNuevo);
	document.frmRol2.rolname.value = rolNuevo;
	document.frmRol2.submit();

}

$(document).ready(function() {
	iniciarBloqueoSesion();
	customLogout();
	//$(this).attr("title", "Nuevo Sistema De Justicia Penal");
	tituloIncial = $(this).attr("title");
});	





