<%@page import="mx.gob.segob.nsjp.comun.enums.detencion.TipoCentroDetencion"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
<title>Mostrar Generar Detención</title>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	
		
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
		
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
    
<script type="text/javascript">

	//Este parametro corresponde al expedienteId
	var numeroExpediente = '<%=request.getParameter("numeroExpediente")%>';
	var idInvolucrado = '<%=request.getParameter("idProbableParticipe")%>';
	var expedienteId = '<%=request.getParameter("expedienteId")%>';
	var tipoEvento = '<%=request.getParameter("tipoEvento")%>';
	var subtipoDeEvento = '<%=request.getParameter("subtipoDeEvento")%>'
	//Cuando es falta administrativa no se permite enviar a defensoria
	var noAviso = '<%=request.getParameter("noAviso")%>';
	var lugarDetencionId = "";

	$(document).ready(function() {

		

		/*
		*LLamadas generales
		*/
		//Se crean las tabs principales
		$("#tabsconsultaprincipaldetencion" ).tabs();

		//Falta administrativa
		if(noAviso == "true"){
			//Ocultamos la ceja de envio a defensoria
			$('#tabAvisos').hide();		
		}
		
		consultarDetencionPorIdInvolucrado();
		
		//LLamadas de ceja lugar detencion
		deshabilitaFuncionalidadDomicilio();
		$("#btnGuardarDomicilioDetencion").click(guardarDatosLugarDetencion);

		//LLamadas de ceja aviso de detencion
		mostrarDatos();
		$("#btnRegistroDetencion").bind("click",abreVisorRegistroDetencion);		

		//LLamadas ceja actuaciones
		 cargaActuaciones();
		
	});


	/*
	*Funcion para consultar la detencion por involucradoId (si existe con anterioridad)
	*/
	function consultarDetencionPorIdInvolucrado(){
		
		$.ajax({								
		  	  type: 'POST',
		  	  url: '<%= request.getContextPath()%>/consultarDetencionByInvolucradoId.do?idInvolucrado='+idInvolucrado+'',
		  	  data:'',				
		  	  dataType: 'xml',
		  	  success: function(xml){
			  	  
		  		errorCode=$(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){
        			
    				lugarDetencionId=$(xml).find('lugarDetencionDTO').find('elementoId').first().text();
    				
    				if(lugarDetencionId != "" && lugarDetencionId != "null"  && lugarDetencionId != " "){
    					consultarLugarDetencionById();
        			}
    			}else{
    				
            	}		  		  			  
		  	  }
		 });
	}

//************************************************COMIENZA FUNCIONALIDAD PARA LA CEJA DE LUGAR DE DETENCION*******************************************/ 
/*
	*Funcion para ocultar los datos de domicilio que no necesitamos
	*/	
	function deshabilitaFuncionalidadDomicilio(){
		$('#liDom').hide();
		$('#liDom').addClass("tabEstilo");
		killDomicilioNotificaciones();
		killCoordenadasGeograficas();
	}


	/*
	*Funcion para guardar el lugar de la detencion
	*/
	function guardarDatosLugarDetencion(){
		
		var params = obtenerParametrosDomicilio();
		params += '&lugarDetencionId='+lugarDetencionId;
		
		$.ajax({								
		  	  type: 'POST',
		  	  url: '<%= request.getContextPath()%>/agregarLugarDeDetencion.do?numeroExpediente='+numeroExpediente+'&involucradoId='+idInvolucrado+'',
		  	  data: params,				
		  	  dataType: 'xml',
		  	  success: function(xml){
			  	  
				errorCode=$(xml).find('response').find('code').text();
				//Si errorCode=0 entonces continuamos con el flujo
				if(parseInt(errorCode)==0){
					lugarDetencionId = $(xml).find('lugarId').first().text();				
					customAlert('Lugar de detenci\u00f3n guardado de manera correcta');
				}else{
					customAlert('No es posible guardar el lugar de detencion');
			    }		 
		  	  }
		 });
	}


	/*
	*Funcion para guardar el lugar de la detencion
	*/
	function consultarLugarDetencionById(){
		
		$.ajax({								
		  	  type: 'POST',
		  	  url: '<%= request.getContextPath()%>/consultarLugarDetencionById.do?elementoId='+lugarDetencionId+'',
		  	  data: '',				
		  	  dataType: 'xml',
		  	  success: function(xml){
			  	  
				errorCode=$(xml).find('response').find('code').text();
				//Si errorCode=0 entonces continuamos con el flujo
				if(parseInt(errorCode)==0){
					pintaDatosDomicilio(xml);
				}else{
					//customAlert('No es posible consultar el lugar de detencion');
			    }		 
		  	  }
		 });
	}
	
	
//************************************************TERMINA FUNCIONALIDAD PARA LA CEJA DE LUGAR DE DETENCION*******************************************/


//************************************************COMIENZA FUNCIONALIDAD PARA LA CEJA DE AVISO DE DETENCION*******************************************/
	
	/*
	*Funcion para mostrar los datos del usuario responsable en el envio de la detencion
	*/
	function mostrarDatos(){

		$("#detencionTxtNombreSP").val(parent.detencionTxtNombreSP);
		$("#detencionTxtCargo").val(parent.detencionTxtCargo);
		$("#detencionTxtAreaAdm").val(parent.detencionTxtAreaAdm);
		var fechaElaboracion = parent.detencionTxtFechaElaboracion;

		if(fechaElaboracion == ""){
			cargaFechaCaptura();
		}else{
			var fechaElaboracionFmt = fechaElaboracion.substring(8,10) + '/' + fechaElaboracion.substring(5,7) 
			+ '/' + fechaElaboracion.substring(0,4) + ' ' + fechaElaboracion.substring(11,16); 
			$("#detencionTxtFechaElaboracion").val(fechaElaboracionFmt);
		}
	}

	 /*
	*Funcion que carga la fecha actual del sistema y la agrega en la pantalla al campo fecha
	*/
	function cargaFechaCaptura(){
		$.ajax({
	    	type: 'POST',
	    	url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
	    	data: '',
	    	dataType: 'xml',
	    	success: function(xml){
	    		$('#detencionTxtFechaElaboracion').val($(xml).find('fechaActual').text());
	    	}
		});
	}


	 /*
	 *Para enviar el registro de detencion
	 */
	function abreVisorRegistroDetencion(){	
		
		//Se registra actividad asociada al expediente
		registrarActividadExpediente(<%= Actividades.SOLICITAR_DEFENSOR.getValorId() %>,"",0);
		
		var tituloVentanaGenerarAvisoDetencion = "Generar aviso de detención";
		var formaID = <%= Formas.SOLICITUD_DEFENSOR.getValorId() %>;		
		
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:20,posy:20,width:1140,height:400,title:""+tituloVentanaGenerarAvisoDetencion, type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&enviarAvisoDetencionSSP=true&idInvolucrado='+idInvolucrado+'" width="1140" height="400" />');		
	}

	/*
	*Registra la actividad de expediente
	*/
	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje,funcionario){
		
		var idFuncionario;
		if ( funcionario === undefined ) {
			idFuncionario = '';
		}else{
			idFuncionario=funcionario;
		}
		
		var idNumeroExpedienteOp = 0;
		
		//Cambia la actividad del expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+expedienteId+'&idNumeroExpediente='+idNumeroExpedienteOp+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+numeroExpediente+'&cveFuncionarioAsignado='+idFuncionario,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					alertDinamico("Actividad nueva registrada");	
				}
			}
		});
	}

	//************************************************TERMINA FUNCIONALIDAD PARA LA CEJA DE AVISO DE DETENCION*******************************************/
	
	
	
	//************************************************COMIENZA FUNCIONALIDAD PARA LA CEJA DE ACTUACIONES*******************************************/
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones() {
		var id=0;
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+numeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#actuacionesRegistroDetencionIndividual').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});            
			}
		});
	}

	//************************************************TERMINA FUNCIONALIDAD PARA LA CEJA DE ACTUACIONES*******************************************/
</script>

</head>

<body>
	<div id="tabsconsultaprincipaldetencion">
		<ul>
			<li id="tabDetencion"><a href="#tabsconsultadetencion-1">Lugar de Detenci&oacute;n</a></li>
			<li id="tabAvisos"><a href="#tabsconsultadetencion-2">Aviso de Detenci&oacute;n</a></li>
			<li id="tabActuaciones"><a href="#tabsconsultadetencion-3">Actuaciones</a></li>
		</ul>
		
		<!--COMIENZA TAB DE LUGAR DE DETENCION-->
		<div id="tabsconsultadetencion-1" class="tabDetencion">
			<table width="762px" height="313px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><input type="button" id="btnGuardarDomicilioDetencion" value="Guardar" class="back_button"></td>
				</tr>
				<tr>
					<td>
						<jsp:include page="ingresarDomicilioView.jsp"/>
					</td>
				</tr>
			</table>				
		</div>
		<!--TERMINA TAB DE LUGAR DE DETENCION-->
		
		<!--COMIENZA TAB DE AVISOS-->
		<div id="tabsconsultadetencion-2" class="tabAvisos">
			<fieldset style="width: 650px;">
				<legend>Responsable</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td align="right" style="width: 250px;">Nombre Servidor P&uacute;blico:</td>
						<td><input type="text" style="width: 180px;" maxlength="30" id="detencionTxtNombreSP" disabled="disabled"/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" style="width: 250px;">Cargo:</td>
						<td><input type="text" style="width: 180px;" maxlength="30" id="detencionTxtCargo" disabled="disabled"/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" style="width: 250px;">&Aacute;rea Administrativa:</td>
						<td><input type="text" style="width: 180px;" maxlength="30" id="detencionTxtAreaAdm" disabled="disabled"/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" style="width: 250px;">Fecha Elaboraci&oacute;n:</td>
						<td><input type="text" style="width: 180px;" maxlength="30" id="detencionTxtFechaElaboracion" disabled="disabled"/></td>
						<td>&nbsp;</td>
					</tr>					
					<tr>
						<td align="center" colspan="3">
							<input type=button class="back_button" id="btnRegistroDetencion" value="Generar aviso de detención">
						</td>
					</tr>
				</table>
			</fieldset>
					
		</div>
		<!--TERMINA TAB DE AVISOS-->
		
		<!--COMIENZA TAB DE ACTUACIONES-->
		<div id="tabsconsultadetencion-3" class="tabActuaciones">
			<fieldset style="width: 650px;">
				<legend>Actuaciones</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right" style="width: 200px;">Actuaci&oacute;n:</td>
							<td>
								<select id="actuacionesRegistroDetencionIndividual" style="width: 180px;">
									<option value="">- Seleccione -</option>
								</select>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
			</fieldset>
		</div>
		<!--TERMINA TAB DE ACTUACIONES-->
				
	</div>
</body>
</html>
