  
function escribirDatosGenerales(xml){
	resultado = "<li class='opened'><span class='folder'>Datos Generales</span>" +
	"<ul><span class='nike' title='Número de Expediente'>"+$(xml).find('expedienteResumenDTO').find('numeroExpediente').first().text()+"</span></ul>"+
	"<ul><span  class='nike' title='Ciudad de Expedición'>"+$(xml).find('expedienteResumenDTO').find('estado').text()+"</span></ul>"+
	//"<ul><span  class='nike' title='Estado de Expedición'>Yucatán</span></ul>"+
	"<ul><span  class='nike' title='Hora Apertura'>"+$(xml).find('expedienteResumenDTO').find('strHoraActual').first().text()+"</span></ul>"+
	"<ul><span  class='nike' title='Fecha Apertura'>"+$(xml).find('expedienteResumenDTO').find('strFechaActual').first().text()+"</span></ul>"+
	"<ul><span  class='nike' title='Delito Principal'>"+$(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("nombre").first().text()+"</span></ul>"+
	"</li>";
	return resultado;
}

function escribirInvolucrados(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo){
	resultado = "<li><span class='folder'>Involucrados</span>"+
				"<ul>";			
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,"Denunciantes","denunciantes","Denunciante");	
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,"Víctimas Persona","victimasPersona","Víctima Persona");
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml, probableResponsableArbolTituloSeccion, "probablesResponsablesPersona", probableResponsableArbolTitulo);
//	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,'<bean:message key="probableResponsableArbolTituloSeccion"/>', "probablesResponsablesPersona",'<bean:message key="probableResponsableArbolTitulo"/>');
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,"Testigos","testigos","Testigo");
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,"Traductores","traductores","Traductor");
	resultado +=escribeHtmlSeccionTipoInvolucrado(xml,"Quienes Detuvieron","quienDetuvo","Quien Detuvo");

	//escribir Organizaciones
	resultado += "<li><span class='folder'>Organización</span>"+
					"<ul>";
		resultado += escribeHtmlOrganizacion(xml,"Denunciante","denunciantesOrganizacion");		
		resultado += escribeHtmlOrganizacion(xml,"Víctima","victimasOrganizacion");		
		resultado += escribeHtmlOrganizacion(xml, probableResponsableArbolTitulo,"probablesResponsablesOrganizacion");
		resultado += "</ul></li>";
		
	resultado+="</ul></li>";
	return resultado;
}


function escribeHtmlSeccionTipoInvolucrado(xml,tituloSeccion,idInvolucrado,tituloInvolucrado){
	resultado = "<li  class='opened'><span class='folder'>"+tituloInvolucrado+"</span>";
	
	$(xml).find(idInvolucrado).find("involucradoDTO").each(function (){
		resultado += escribirHtmlInvolucradto(this,tituloInvolucrado);
	});		
	
	resultado += "</li>";
	return resultado;
}

/**
* Funcion que invoca a las funciones para escribir los datos del involucrado
* considerando el nombre completo del involucrado.
*/
function escribirHtmlInvolucradto(xml,tipoInvolucrado){
	htmlInvolucrado = "";
	
	//Nombre demografico
	objNombre = $(xml).find("nombresDemograficoDTO").find("nombreDemograficoDTO").first();
	
	htmlInvolucrado = 
				"<ul><span class='nike' title='Nombre Completo'>"+$(objNombre).find("nombre").text()+ " " + 
				$(objNombre).find("apellidoPaterno").text() + " " + $(objNombre).find("apellidoMaterno").text()+"</span>";
	
	//Datos Generales del Involucrado
	htmlInvolucrado += "<li  class='closed'><span class='folder'>Datos Generales: </span>";
	
		htmlInvolucrado += "<ul><span class='nike' title='Nombre'>"+$(objNombre).find("nombre").text()+"</span></ul>";
		htmlInvolucrado += "<ul><span class='nike' title='Apellido Paterno'>"+$(objNombre).find("apellidoPaterno").text()+"</span></ul>";
		htmlInvolucrado += "<ul><span class='nike' title='Apellido Materno'>"+$(objNombre).find("apellidoMaterno").text()+"</span></ul>";
			
		htmlInvolucrado += "<ul>";
		htmlInvolucrado += escribirDatosInvolucrado($(xml));
		htmlInvolucrado += "</ul>";
		
	htmlInvolucrado += "</li></ul>";
	return htmlInvolucrado;
}

/**
* Funcion que invoca a las funciones necesarias para escribir los datos
* del involucrado.
*/
function escribirDatosInvolucrado(xml){
	var resultado = "";
	//Nombre demografico
	objNombre = $(xml).find("nombresDemograficoDTO").find("nombreDemograficoDTO").first();
	//Estado Civil
	resultado += "<ul><span class='nike' title='Estado Civil'>"+$(xml).find("valorIdEstadoCivil").find("valor").text()+"</span></ul>";
	//Edad
	resultado += "<ul><span class='nike' title='Edad'>"+objNombre.find("edadAproximada").text()+"</span></ul>";
	/*Enable IT @Autor ByYolo
	 * se agrega campos peticion Coauhila	
		*/
	resultado += "<ul><span class='nike' title='Ocupación'>"+$(xml).find("valorIdOcupacion").find("valor").text()+"</span></ul>";
	resultado += "<ul><span class='nike' title='Fecha Nacimiento'>"+objNombre.find("fechaNacimiento").text()+"</span></ul>";
	/*Enable IT @Autor ByYolo
	 * fin de campos
		*/
	//Escolaridad
	resultado += "<ul><span class='nike' title='Escolaridad'>"+$(xml).find("valorIdEscolaridad").find("valor").text()+"</span></ul>";
	//Nacionalidades
	resultado += escribirHtmlNacionalidades(xml);
	//Telefonos
	resultado += escribirHTMLTelefonos(xml);
	//Datos de Nacimiento
	resultado += escribirHTMLDatosNacimiento(objNombre);
	//Domicilio
	resultado += escribirHTMLDomicilio($(xml).find("domicilio"));
	//Documento de Identificacion
	resultado += escribirHTMLDocumentoIdentificacion($(xml));
	return resultado;
}

/*
* Funcion que recupera las nacionalidades del involucrado
* y las muestra como en lista.
*/
function escribirHtmlNacionalidades(xml){
	var nacionalidades = "<li class='closed'><span class='folder'> Nacionalidad(es): </span>";
	
	$(xml).find("valorIdNacionalidad").find("ValorDTO").each(function (){
		nacionalidades +="<ul><span class='nike' title='Nacionalidad'>" +
						$(this).find("valor").text() + 
						"</span></ul>";
	});
	
	nacionalidades += "</li>";
	return nacionalidades;
}

/*
* Funcion que recupera los telefonos del involucrado
* y los muestra como en lista.
*/
function escribirHTMLTelefonos(xml){
	var telefonos = "<li  class='closed'><span class='folder'> Teléfono(s): </span>";
	$(xml).find("telefonosDTO").find("telefonoDTO").each(function (){
		telefonos += "<ul><span class='nike' title='Telefono'>" +
			$(this).find("valorTipoTelefono").find("valor").text() + ": "+ 
			$(this).find("codigoPais").text() + " ("+
			$(this).find("codigoArea").text()  +") "+
			$(this).find("numeroTelefonico").text() +
			"</span></ul>";
	});
	telefonos += "</li>";
	return telefonos;
}

/**
* Funcion que escribe los datos de nacimiento del involucrado.
*/
function escribirHTMLDatosNacimiento(xml){
	var datosNacimiento = "<li  class='closed'><span class='folder'> Datos de Nacimiento:  </span>";
	//Pais
	datosNacimiento += "<ul><span class='nike' title='Pais'>"+$(xml).find("paisValorDTO").find("valor").text()+"</span></ul>";
	//Estado
	datosNacimiento += "<ul><span class='nike' title='Estado'>"+$(xml).find("entidadFederativaDTO").find("nombreEntidad").text()+"</span></ul>";
	//Municipio
	datosNacimiento += "<ul><span class='nike' title='Municipio'>"+$(xml).find("municipioDTO").find("nombreMunicipio").text()+"</span></ul>";
	datosNacimiento += "</li>";
	return datosNacimiento;
}

/**
* Funcion que escribe los datos del domicilio del involucrado.
*/
function escribirHTMLDomicilio(xml){
	var domicilio = "<li  class='closed'><span class='folder'> Domicilio de Residencia:  </span>";
	//Calle
	domicilio += "<ul><span class='nike' title='Calle'>"+$(xml).find("calle").text()+"</span></ul>";
	//Numero
	domicilio += "<ul><span class='nike' title='Numero Exterior'>"+$(xml).find("numeroExterior").text()+"</span></ul>";
	//Colonia
	var asentamiento =  $(xml).find("asentamientoDTO");
	domicilio += "<ul><span class='nike' title='Colonia'>"+asentamiento.find("nombreAsentamiento").text()+"</span></ul>";
	//Codigo Postal
	domicilio += "<ul><span class='nike' title='Codigo Postal'>"+asentamiento.find("codigoPostal").text()+"</span></ul>";
	//Ciudad
	domicilio += "<ul><span class='nike' title='Ciudad'>"+$(xml).find("ciudadDTO").last().find("nombreCiudad").text()+"</span></ul>";
	//Municipio
	domicilio += "<ul><span class='nike' title='Municipio'>"+$(xml).find("municipioDTO").last().find("nombreMunicipio").text()+"</span></ul>";
	//Estado
	domicilio += "<ul><span class='nike' title='Estado'>"+$(xml).find("ciudadDTO").last().find("entidadFederativaDTO").find("nombreEntidad").text()+"</span></ul>";
	domicilio += "</li>";
	return domicilio;
}

/**
* Funcion que escribe los datos del documento de identificación.
*/
function escribirHTMLDocumentoIdentificacion(xml){
	var documentoIdentificacion="<li  class='closed'><span class='folder'> Documento de Identificación:  </span>";
	//Tipo de Documento de Identificacion
	documentoIdentificacion += "<ul><span class='nike' title='Tipo Identificacion'>"+$(xml).find("valorIdIdentificaion").find("valor").text()+"</span></ul>";
	//Folio de Documento de Identificacion
	documentoIdentificacion += "<ul><span class='nike' title='Folio Identificacion'>"+$(xml).find("folioIdentificacion").text()+"</span></ul>";
	documentoIdentificacion += "</li>";
	return documentoIdentificacion;
}

/** 
* Funcion que se encarga de escribir las Unidades Especializadas 
**/
function escribirUnidadesEspecializadas(xml){
	resultado = "<li class = 'closed' ><span class='folder'>Unidades Especializadas</span>";
	$(xml).find("listaCatalogo").find("CatUIEspecializadaDTO").each(function (){
		resultado += "<ul><span class='nike'>"+$(this).find("nombreUIE").text()+"</span></ul>";
	});	
	resultado += "</li>";
	return resultado;
}

/** 
* Funcion que se encarga de escribir la lista de objetos del expediente. 
**/
function escribirListaObjetos(xml){
	resultado ="<li class = 'closed' ><span class='folder'>Objetos</span>"+
					"<ul>";
	
	$(xml).find("grupoObjetosExpediente").find("grupoObjetosExpedienteDTO").each(function (){
		resultado+= "<li><span class='folder'>"+$(this).find("descripcionGrupo").text()+"</span>";
		$(this).find("objetos").find("objetoResumenDTO").each(function(){
			resultado+= "<ul><span class='nike'>"+$(this).find("descripcionResumen").text()+"</span></ul>";
		});
		resultado+="</li>";
	});
	resultado+="</ul></li>";
	return resultado;
}

/** 
* Funcion que se encarga de escribir los datos del involucrado como organizacion. 
**/
function escribeHtmlOrganizacion(xml,tituloSeccion,tagSeccion){
	resultado =  "<li><span class='folder'>"+tituloSeccion+"</span>"+
				"<ul>";
	
	$(xml).find(tagSeccion).find("involucradoDTO").each(function (){
		nombre = $(this).find("organizacionDTO").find("representanteLegal").find("nombresDemograficoDTO").first();
		resultado += "<li><span class='nike'>"+$(this).find("organizacionDTO").find("nombreOrganizacion").text()+"</span>"+
						"<ul><span class='nike'>"+
						nombre.find("nombre").text() + " " + nombre.find("apellidoPaterno").text() + " " + nombre.find("apellidoMaterno").text()+
						"<span></ul>" +
					"</li>";
	});	
	resultado += "</ul></li>";	
	return resultado;
}

/** 
* Funcion que se encarga de listar los delitos asociados al expediente. 
**/
function escribirDelitos(xml){
	resultado = "<li><span class='folder'>Delitos</span>";
	
	$(xml).find("expedienteResumenDTO").find("delitos").find("delitoDTO").each(function (){
		resultado += "<ul><span class='nike'>"+$(this).find("catDelitoDTO").find("nombre").text()+"</span></ul>";
	});	
	
	resultado += "</li>";
	return resultado;
}

/** 
* Funcion que se encarga de escribir el hecho. 
* Deprecated 
**/
function escribirHechos(xml){
	resultado = "<li><span class='folder'>Hechos</span>"+
				"<ul>" +		
				"<span class='nike'>"+$(xml).find("hechos").first().text()+"</span>"+
				"</ul>";
	return resultado;
}


/////Funciones con la posibilidad de crear los involucrados

function escribirInvolucradosOpcionCrear(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo){
	resultado = "<li><span class='folder'>Involucrados</span>"+
				"<ul>";			
				var op=true;
				$(xml).find("denunciantes").find("involucradoDTO").each(function (){
	    			op=false;
	    		});	
	    		if(op){
	    			resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Denunciantes","denunciantes","Denunciante","crearDenunciante()");
			    }else{
			    	resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Denunciantes","denunciantes","Denunciante","");
				}			
				resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Víctimas Persona","victimasPersona","Víctima Persona","creaNuevaVictima()");
				//resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Probables Responsables Personas ","probablesResponsablesPersona","Probable Responsable","creaNuevoProbResponsable()");/*  */
				//resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,'<bean:message key="probableResponsableArbolTituloSeccion"/>', "probablesResponsablesPersona",'<bean:message key="probableResponsableArbolTitulo"/>',"creaNuevoProbResponsable()");
				resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,probableResponsableArbolTituloSeccion, "probablesResponsablesPersona", probableResponsableArbolTitulo, "creaNuevoProbResponsable()");
				resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Testigos","testigos","Testigo","creaNuevoTestigo()");
				resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Traductores","traductores","Traductor","creaNuevoTraductor()");
				resultado +=escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,"Quienes Detuvieron","quienDetuvo","Quien Detuvo","");
	
	    		//escribir Organizaciones
	    		resultado += "<li><span class='folder'>Organización</span>"+
	    						"<ul>";
					resultado += escribeHtmlOrganizacion(xml,"Denunciante","denunciantesOrganizacion");		
					resultado += escribeHtmlOrganizacion(xml,"Víctima","victimasOrganizacion");		
					//resultado += escribeHtmlOrganizacion(xml,"Probable Responsable","probablesResponsablesOrganizacion");
					resultado += escribeHtmlOrganizacion(xml, probableResponsableArbolTitulo,"probablesResponsablesOrganizacion");

				resultado += "</ul></li>";
		resultado+="</ul></li>";
	return resultado;
}

function escribeHtmlSeccionTipoInvolucradoOpcionCrear(xml,tituloSeccion,idInvolucrado,tituloInvolucrado,funcion){
	resultado = "<li  class='opened'><span class='folder'>"+tituloInvolucrado+"<img onclick='"+funcion+"' src='<%= request.getContextPath() %>/resources/images/add.png'></span>";
	$(xml).find(idInvolucrado).find("involucradoDTO").each(function (){
		resultado += escribirHtmlInvolucradto(this,tituloInvolucrado);
	});		
	
	resultado += "</li>";
	//resultado += "</li><input type='button' value='Ingresar'/>";
	return resultado;
}

function crearDenunciante(){
	idWindowIngresarDenunciante++;
	$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:150,posy:20,width:1040,height:570,title:"Ingresar Denunciante", type:"iframe"});
    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?numeroExpediente='+numeroUnicoExpediente +'&calidadInv=DENUNCIANTE&elemento=si" width="1040" height="570" />');		
}

function creaNuevaVictima() {
	idWindowIngresarVictima++;
	$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Víctima", type:"iframe"});
    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?numeroExpediente='+numeroUnicoExpediente +'&elemento=si" width="1050" height="600" />');		
}

function creaNuevoProbResponsable() {
	idWindowIngresarProbResponsable++;
	$.newWindow({id:"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:250,posy:150,width:1050,height:620,title:"Ingresar Probable Responsable", type:"iframe"});
	$.updateWindowContent("iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?numeroExpediente='+numeroUnicoExpediente +'&calidadInv=PROBABLE_RESPONSABLE&elemento=si" width="1050" height="620" />');		
}

//Crea una nueva ventana de testigo
function creaNuevoTestigo() {
	idWindowIngresarTestigo++;
	$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Testigo", type:"iframe"});
    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?numeroExpediente='+numeroUnicoExpediente +'&elemento=si" width="1050" height="600" />');		
}

function creaNuevoTraductor() {
	idWindowIngresarTraductor++;
	$.newWindow({id:"iframewindow" + idWindowIngresarTraductor, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Traductor", type:"iframe"});
	$.updateWindowContent("iframewindow" + idWindowIngresarTraductor,'<iframe src="<%= request.getContextPath() %>/IngresarTraductor.do?numeroExpediente='+numeroUnicoExpediente +'&elemento=si" width="1050" height="600" />');		
}

function creaQuienDetuvo() {
	idWindowIngresarQuienDetuvo++;
$.newWindow({id:"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Quién detuvo", type:"iframe"});
$.updateWindowContent("iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo,'<iframe src="<%= request.getContextPath() %>/IngresarQuienDetuvo.do?elemento='+0+'&numeroExpediente='+numeroUnicoExpediente+'" width="1050" height="600" />');
}	

