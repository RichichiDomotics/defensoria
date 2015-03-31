<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Atender Notificación</title>
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--Hojas de Estilo	-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<!--Scripts-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
		
	<script type="text/javascript">
	var x=0;
	var eventoDTOID=parent.eventoDTOID;
	//variable para almacenar el id del evento
	var idEvento;
	//Id del expediente que es consultado en el metodo consultaDetalleEvento(idEvento)
	//que posteriormente es usado para agregar notificacion
	var idExpediente;
	//Bandera para recargar el grid
	var primeraConsulta="true";
	var idnotificacion;
	var idinvolucrado;
	var docID;
	var archivoDigitalId;
	var documentoId;
	var idDocumentoGlobal;

	var numeroExpediente;
	var numeroExpedienteId;
	/**
	* Variables globales para controlar los datos del destinatario
	*/
	var lstDestinatarios = new Array();
	var idDestinatario = 0;
	
	//Permite ocultar/mostrar los botones relacionados a la Notificacion
	var desactivarBotonesEnNotificaciones = false;

	var estatusConsulta = 0;
	
	$(document).ready(function() {

		//Se crean las tabs
		$("#tabsprincipalconsulta" ).tabs();
		//Se agrega la funcion agregarDestinatario al dar click en el botn agregar destinatario
		$('#agregarDestinatarioBoton').bind('click', agregarDestinatario);
		//Se oculta el domicilio notificaciones de la JSP de ingresarDomicilioView
		$("#liDomNotif").hide();
		//Oculta la ceja de la tab Ingresar Domicilio, para que no se vea el JSP include 
		$("#liDom").hide();
		//Oculta completamente la tab de domicilio notificaciones
		$("#tabs-2").hide();
		//Oculta el renglon donde se encuentra la ceja de domicilio notificaciones
		$("#rowDomicilioNotif").hide();		
		
		//Id del evento que viene desde el gird de la bandeja de notificaciones
		idEvento=<%= request.getAttribute("idEvento")%>;
		//numeroExpediente="<%= request.getParameter("numeroExpediente")%>";
		//LLamada aconsultar el detalle del evento para llenar los campos de la Tab Detalle evento
		consultaDetalleEventoNotif(idEvento);

		estatusConsulta = "<%= request.getParameter("estatusConsulta")%>";
	});//FIN ON READY

	/*
	*Funcion que limpia las cajas de texto
	*antes de llenarlas con los datos de la
	*nueva consulta
	*/	
	function limpiaDatosDetalleEvento(){
		
		$("#numCasoNotifDetalle").val("");
		$("#numExpedienteNotifDetalle").val("");
		$("#tipoEventoNotifDetalle").val("");
		$("#eventoNotifDetalle").val("");
		$("#fechaEventoNotifDetalle").val("");
		$("#horaEventoNotifDetalle").val("");
		$("#lugarEventoNotifDetalle").val("");
		$("#direccionEventoNotifDetalle").val("");
		$('#formaDeNotificacion').find("option[value='0']").attr("selected","selected");
	}


	/*
	*Funcion que abre el frame de detalle de notificaciones por persona 
	*/
	function dblClickRowDetalleNotificacionesPersona(rowID){
		$.newWindow({id:"iframewindowDetalleNotificacionesPersona", statusBar: true, posx:200,posy:50,width:700,height:400,title:"Detalle de Notificaciones de Persona", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleNotificacionesPersona",'<iframe src="<%=request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do" width="700" height="400" />'); 
	}
		
	//Crea una nueva ventana para crear un nuevo documento
	function generarDocumentoView() {
	    $.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Solicitud de Estudio", type:"iframe"});
        //$.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId="+docID+"&esconderArbol=1&formaId=88&numeroUnicoExpediente="+numeroExpediente+"' width='1140' height='400' />");
	    $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId="+docID+"&esconderArbol=1&formaId="+formaId+"&numeroUnicoExpediente="+numeroExpediente+"' width='1140' height='400' />");
	}
	//Funcion que agrega un destinatario
	  function agregarDestinatario(){
		  
		$("#agregarDestinatario").dialog("open");
	  	$("#agregarDestinatario").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Agregar Destinatario', 
		  	dialogClass: 'alert',
		  	position: [312,40],
		  	width: 810,
		  	height: 540,
		  	maxWidth: 1000,
		  	buttons:{"Agregar":function() {		  			

		  		//Agrega el nuevo destinatario al grid
		  		nombreCompleto = ($('#nombre').val()+" "+$('#apPat').val()+" "+$('#apMat').val());
		  		institucion = $('#cmbInstitucion option:selected').val();
		  		
		  		
		  		var destinatario = new Destinatario(nombreCompleto,institucion);
		  		lstDestinatarios[idDestinatario] = destinatario;
		  		idDestinatario++;
		  		
		  		//jQuery("#gridDetalleNotificaciones").jqGrid('addRowData',idDestinatario,destinatario);
		  		//jQuery("#gridDetalleNotificaciones").trigger("updateGrid");
		  		
		  		//agregamos todos los parametros que vamos a enviar al action para el guardado
		  		var parametrosDomicilio = obtenerParametrosDomicilio();
				var nuevoDestinatario="";
				
				nuevoDestinatario +='idExpediente='+idExpediente;
				nuevoDestinatario +='&nombreDest='+ $('#nombre').val();
				nuevoDestinatario +='&apPatDest='+ $('#apPat').val();
				nuevoDestinatario +='&apMatDest='+ $('#apMat').val();
				nuevoDestinatario +='&institucionDest=' + $("#cmbInstitucion option:selected").val();
				nuevoDestinatario +='&dirElectDest=' + $('#correo').val();
				nuevoDestinatario +='&domicilioDest='+parametrosDomicilio;
				//Agregamos el nuevo destinatario
				 agregarDestinatarioNotificacion(nuevoDestinatario);				 
		  		$(this).dialog("close");
		  		limpiaAgregarNuevoDestinatario();		  	
		  	},
		  	"Cancelar":function() {	
		  			$(this).dialog("close");
		  			limpiaAgregarNuevoDestinatario();
		  		}
		  	}
		});	  	
	}	

	/**
	*Funcion que funciona como constructor para la estrucutra destinatario
	*/
	function Destinatario(involucrado, institucion){
		this.involucrado = involucrado;
		this.institucion = institucion;			
	} 

	/**
	*Funcion que realiza el paso de parametros para el guardado de la
	*informacion del destinatario. 
	*/
	function agregarDestinatarioNotificacion(nuevoDestinatario) {

		
		$.ajax({
  			type: 'POST',
  			url: '<%= request.getContextPath()%>/agregarDestinatario.do?idEvento='+idEvento+'',
  			data: nuevoDestinatario,
  			dataType: 'xml',
  			async: false,
  			success: function(xml){
  	  			
  				consultaDetalleEventoNotif(idEvento);
  				//jQuery("#gridDetalleNotificaciones").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=1'+'',datatype: "xml" });
  				//$("#gridDetalleNotificaciones").trigger("reloadGrid");
  				
  				jQuery("#gridDetalleNotificacionesInvolucrado").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=0'+'',datatype: "xml" });
  				$("#gridDetalleNotificacionesInvolucrado").trigger("reloadGrid");
  				
  			}
		});
 	}

	/**
	*Funcion que limpia todos los datos de la ventana de agregar nuevo destinatario 
	*/
	function limpiaAgregarNuevoDestinatario() {
		//Limpia los campos del destinatario
		$("#nombre").val("");
		$("#apPat").val("");
		$("#apMat").val("");
		$("#correo").val("");
		$("#cmbInstitucion").attr('selectedIndex', 0);
		//Limpia la direccion fisica
		seleccionaMexico();
		$('#codigoPostal').val("");
		 $('#entidadFederativa').val("");
		 $('#areaCiudad').val("");
		 $('#areaDelegacionMunicipio').val("");
		 $('#areaColonia').val("");
		 $('#areaAsentamiento').val("");
		 $('#areaTipoCalle').val("");
		 $('#areaCalle').val("");
		 $('#areaNumeroExterior').val("");
		 $('#areaNumeroInterior').val("");
		 $('#areaReferencias').val("");
		 $('#areaEntreCalle').val("");
		 $('#areaYCalle').val("");
		 $('#areaAlias').val("");
		 $('#areaEdificio').val("");
		//Limpia los datos de coordenadas
		$('#txtFldLongitudEste').val("");
		$('#txtFldLongitudGrados').val("");
		$('#txtFldLongitudMinutos').val("");
		$('#txtFldLongitudSegundos').val("");
		$('#txtFldLatitudNorte').val("");
		$('#txtFldLatitudGrados').val("");
		$('#txtFldLatitudMinutos').val("");
		$('#txtFldLatitudSegundos').val("");
	}
	
	function generarDocumentoDirecto(){
		var param ="";
		//param += "formaId=<%=Formas.ACTA.getValorId()%>";
		param += "formaId=<%=Formas.NOTIFICACION_DE_AUDIENCIA.getValorId()%>";
		param += "&documentoId="+documentoId;
		param += "&tipoDocumento=<%=TipoDocumento.NOTIFICACION.getValorId()%>";
		param += "&numeroUnicoExpediente="+numeroExpediente;
		param += "&returnDocument=0";
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/GenerarDocumentoDirecto.do',
			data: param, 
			async: false,
			dataType: 'xml',
			success: function(xml){}
		});
		
	}

	

	function enviarNotificacionWS(){
		var rowID = jQuery("#gridDetalleNotificaciones").jqGrid('getGridParam','selrow');		
		var ret = jQuery("#gridDetalleNotificaciones").jqGrid('getRowData',rowID);
		var param ="";
		param += "id="+eventoDTOID;
		param += "&documentoId="+documentoId;
		param += "&nombre="+ret.involucrado;
		param += "&institucion="+ret.idInstitucion;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/enviarNotificacionByWebService.do',
			data: param, 
			async: false,
			dataType: 'xml',
			success: function(xml){}
		});
	}
	
	function abrirPDF(){
		//document.frmDoc.archivoDigitalId.value = archivoDigitalId;
		//document.frmDoc.documentoId.value = documentoId;
		//document.frmDoc.submit();
		
	}

	//Variable para controlar el action para consultar pdf's
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

	/*
	*Funcion que aplica submit a la forma para abrir el documento solicitado
	*id= id del documento seleccionado en el grid de documentos
	*Así se obtenia anteriormente:
	*	
	*document.frmDoc.documentoId.value = id;
	*document.frmDoc.submit();
	*/
	function abreDocumento(documentoId,esParcial){
		
		if(esParcial != null ){

			if(esParcial===false){
				//Fue guardado definitivo	
				document.frmDoc.action=accionConsultarPdf+"?documentoId="+documentoId;
				document.frmDoc.submit();
			}else{
				//Fue guardado parcial
				$("#ventanaDetallePersona").dialog("close");
				titulo="Documento de Notificaci&oacute;n";
				$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe"});
    		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+documentoId+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
			}
		}
	}

	/*
	*Funcion para cerrar la ventana de generarDocumentoSinCaso.jsp
	*llamada desde esta pantalla
	*/
	function cerrarVentanaDocumento(){
		var pantalla ="iframewindowGenerarDocumento";
		$.closeWindow(pantalla);
	}

	/*
	*Generar documento llamado despues de guardar la solicitud
	*/
	function generarDocumento() {
		
		titulo="Documento de Notificaci&oacute;n";
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+documentoId+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
	    $("#ventanaDetallePersona").dialog("close");
	 }
	</script>
</head>
<body>
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center">
					<span id="divAlertTexto"></span>
				</td>
			</tr>
		</table>	
	</div> 

<div id="tabsprincipalconsulta">
	<ul>
		<li><a href="#tabsprincipalconsulta-1">Detalle Evento</a></li>
		<li><a href="#tabsprincipalconsulta-2">Notificaciones</a></li>
  	</ul>
  	<div id="tabsprincipalconsulta-1" style="height: 500px">
  		<table width="720" border="0" align="center" cellpadding="0" cellspacing="5">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<fieldset>  		
						<table width="720" border="0" align="center" cellpadding="0" cellspacing="5">

							<tr>
								<td width="30%" align="right"><strong>Numero de Caso:</strong></td>
								<td width="70%"><input type="text" id="numCasoNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right"><strong>Numero de Expediente:</strong></td>
								<td><input type="text" id="numExpedienteNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
						</table>
					</fieldset>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<fieldset>
	  					<legend>Direcci&oacute;n del Evento</legend>
		  				<table width="720" border="0" align="center" cellpadding="0" cellspacing="5">
							<tr>
								<td align="right"><strong>Dirección de Evento:</strong></td>
								<td><input type="text" id="direccionEventoNotifDetalle" style="width:500px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right"><strong>Lugar de Evento:</strong></td>
								<td><input type="text" id="lugarEventoNotifDetalle" style="width:500px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right"><strong>Fecha de Evento:</strong></td>
								<td><input type="text" id="fechaEventoNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right"><strong>Hora de Evento:</strong></td>
								<td><input type="text" id="horaEventoNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>Evento</legend>
						<table width="720" border="0" align="center" cellpadding="0" cellspacing="5">
							<tr>
								<td align="right"><strong>Tipo de Evento: </strong></td>
								<td><input type="text" id="tipoEventoNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right"><strong>Evento:</strong></td>
								<td><input type="text" id="eventoNotifDetalle" style="width:200px; border: 0; background:#DDD;" readonly="readonly"/></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tabsprincipalconsulta-2" style="height: 500px">
			<jsp:include page="/WEB-INF/paginas/NotificacionesIncludeView.jsp" flush="true"></jsp:include>
	</div>
	
</div>

	
<div id="agregarDestinatario" style="display: none">
	
	<table width="780" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="196">&nbsp;</td>
        <td width="263">&nbsp;</td>
        <td width="164" align="right">&nbsp;</td>
        <td width="247">&nbsp;</td>
        
      </tr>
      <tr>
        <td align="right">Nombre:</td>
        <td><input type="text" size="30" id="nombre" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
        <td align="right">Institución:</td>
        <td>
            <select name="select" id="cmbInstitucion" style="width:165px;">
            <option value="Seleccione">-Seleccione-</option>
            <option value="Defensoria">Defensoria</option>
            <option value="Procuraduria">Procuraduria</option>
            </select>
        </td>
      </tr>
      <tr>
        <td align="right">Apellido Paterno:</td>
        <td><input type="text" size="30" id="apPat" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
        <td align="right">Dirección Electronica:</td>
        <td><input type="text" size="30" id="correo"/></td>
      </tr>
      <tr>
        <td align="right">Apellido Materno:</td>
        <td><input type="text" size="30" id="apMat" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="4" align="center" valign="top">Direcci&oacute;n Fis&iacute;ca:</td>
      </tr>
      <tr>
        <td colspan="4"><jsp:include page="ingresarDomicilioView.jsp"></jsp:include></td>
      </tr>
    </table>
	

</div>

<form name="formaDocumento" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
	<input type="hidden" value="" id="formaId" >
	<input type="hidden" value="" id="documentoId">
	<input type="hidden" value="" id="tipoDocumento">
	<input type="hidden" name="numeroUnicoExpediente" />
</form>

<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	<!--<input type="hidden" name="documentoId" />-->
</form>

</body>
</html>