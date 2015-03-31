<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.medida.TipoMedida"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento"%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ingresar Mandamiento Judicial</title>

		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen"/>
		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css"/>
		<link rel="stylesheet" type="text/css"  media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen"/>
		<link type="text/css"  rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>			
					
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
				
		<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
		<script	src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>
		
		
		<%
		String rolActivo = "";
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
		if (usuario != null 
				&& usuario.getRolACtivo() != null 
				&& usuario.getRolACtivo().getRol() != null
				&& usuario.getRolACtivo().getRol().getRolId() != null){
			rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
		}
		%>	

		<script type="text/javascript">

		var idInvolucrado             = "";
		
		//idResolutivo
		var rowid =			   '<%=request.getParameter("rowid")%>';
		var resolutivoId =	   '<%=request.getParameter("resolutivoId")%>';
		var idVentana =		   '<%=request.getParameter("idVentana")%>';
		var operacion =		   '<%=request.getParameter("operacion")%>';
		var numexpediente =  '<%=request.getParameter("numeroExpediente")%>';
		var numeroDeCaso =  '<%=request.getParameter("numeroDeCaso")%>';
		var numexpedienteid =  '<%=request.getParameter("numeroExpedienteId")%>';
		var rolActivo = '<%=rolActivo%>';
		
		var contextoPagina = "${pageContext.request.contextPath}";
		
		//Nuevos atributos desde el padre
		var idAudiencia = <%=request.getParameter("idAudiencia")%>;
		var idInvolucradoSeleccionado = '<%=request.getParameter("idInvolucrado")%>';
		//Id estatus mandamiento judicial
		var estatusActualMandamiento = "";

		var mandamientoJudicialId = "";
		
		jQuery().ready(function () {
			
			$( "#tabsprincipalconsulta" ).tabs();
						
			//Codigo para obtener los datos de la pantalla
			$("#guardarMandamientoJudicial").click(guardarMandamientoJudicial);

			$('#iMandamientoJudicialWorkSheet').show();
			
			$("#mandamientoJudicialFechaEjecucion").attr("disabled","disabled");

			
			cargaTipoMandamiento();
			cargaTipoSentencia();
			controlTipoMandamiento();
			$('#tipoMandamiento').change(controlTipoMandamiento);
			agregarCalendarios();
			
			if(operacion=='CONSULTA'){
				$('#iMandamientoJudicialWorkSheet').hide();
				obtenerDatosMandamientoJudicial(rowid);
				
				consultarDocumentosDeMandamientoJudicialPorExpediente();
				$("#cbxTipoConsultaDocumentos").change(consultarDocumentosDeMandamientoJudicialPorExpediente);
				
				if (rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
					//Muestra seccion para el cambio de estatus
					if(estatusActualMandamiento == '<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>' || estatusActualMandamiento == '<%=EstatusMandamiento.EN_PROCESO.getValorId()%>'
							|| estatusActualMandamiento == '<%=EstatusMandamiento.CANCELADO.getValorId()%>'){
						$("#seccionCambiarEstatusMandamiento").show();
					}
					//Muestra seccion para adjuntar documentos
					if(estatusActualMandamiento == '<%=EstatusMandamiento.ATENDIDO.getValorId()%>' || estatusActualMandamiento == '<%=EstatusMandamiento.CANCELADO.getValorId()%>'
							|| estatusActualMandamiento == '<%=EstatusMandamiento.CONCLUIDO.getValorId()%>'){
						$("#seccionAdjuntarDocumentoAMandamiento").show();
					}
				}else{//Ajustes necesarios al momento de consultar el Rol de agenteMP					
				    $("#cbxTipoConsultaDocumentos").find("option[value='<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>']").remove();
					$("#idEtiquetaNumCausa").html("N&uacute;mero de Expediente:");
				}
				
			}else{
				consultaNombreInvolucrado(idInvolucradoSeleccionado);
				$("#cmpEstado").val("No atendido");
				$('#txtNumCausa').val(numexpediente);
				if(numeroDeCaso != "")
					$('#txtNumCaso').val(numeroDeCaso);
				else
					$('#txtNumCaso').val("-");

				consultarDocumentosDeMandamientoJudicialPorExpediente();
				$('#cbxTipoConsultaDocumentos').attr('disabled',true);
			}
						
			
			//Permite cargar el grid con las medidas cautelares relacionadas a un expediente
			consultaGeneralMandamientoJudicial(4, '', numexpediente);
			//Esta funcion se debe llamar despues de consultar el detalle
			consultarCatalogoEstatusMandamiento();
			
			if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>'){
				jQuery("#gridMandamientosJudiciales").jqGrid('setLabel', 'numeroCausa', 'N&uacute;mero de Expediente');				
			}
		});

		function obtenerDatosMandamientoJudicial(rowid){
			
			mandamientoJudicialId = rowid.split(",")[1];
									
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultaMandamientoJudicialPJENC.do',
	    		data: 'idMandamientoJudicial='+mandamientoJudicialId,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			deshabilitaCampos();
	    			idInvolucrado=$(xml).find('mandamientoJudicial').find('involucrado').find('elementoId').text();
				
					var nombre=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('nombre').first().text();
					document.getElementById('iMCNombre').value=nombre;
					var apellidoPaterno=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('apellidoPaterno').first().text();
					document.getElementById('iMCApellidoPaterno').value=apellidoPaterno;
					var apellidoMaterno=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('apellidoMaterno').first().text();
					document.getElementById('iMCApellidoMaterno').value=apellidoMaterno;

					var estadoMedida =  $(xml).find('mandamientoJudicial').find('estatus').find('valor').last().text();
					$('#cmpEstado').val(estadoMedida);
					
					estatusActualMandamiento = $(xml).find('mandamientoJudicial').find('estatus').find('idCampo').last().text();

					
					var descripcion = $(xml).find('mandamientoJudicial').find('descripcion').text();
					$('#descripcionMandamiento').val(descripcion);
					
					var tipoMandamiento = $(xml).find('mandamientoJudicial').find('tipoMandamiento').find('idCampo').text()
					
					if(tipoMandamiento != '')
						$("#tipoMandamiento").val(tipoMandamiento);

					var tipoSentencia = $(xml).find('mandamientoJudicial').find('tipoSentencia').find('idCampo').text()
					
					if(tipoSentencia != ''){

						$("#tipoSentencia").val(tipoSentencia);
						$('#divEtiTipoSentencia').show();
						$('#divCbxTipoSentencia').show();
						$('#divEtiFechaInicioSentencia').show();
						$('#divFechaInicioSentencia').show();
						$('#divEtiFechaFinSentencia').show();
						$('#divFechaFinSentencia').show();
						

						var fechaInicio=$(xml).find('mandamientoJudicial').find('fechaInicioStr').text();
						if(fechaInicio != '' && fechaInicio != null){
							$("#fechaInicioSentencia").val(fechaInicio);
						}
						
						var fechaFin=$(xml).find('mandamientoJudicial').find('fechaFinStr').text();
						if(fechaFin != '' && fechaFin != null){
							$("#fechaFinSentencia").val(fechaFin);
						}
					}

					var fechaEjecucion=$(xml).find('mandamientoJudicial').find('fechaEjecucionStr').text();
					if(fechaEjecucion != '' && fechaEjecucion != null){
						$("#mandamientoJudicialFechaEjecucion").val(fechaEjecucion);
					}
					
					var numCausa = $(xml).find('mandamientoJudicial').find('numeroExpediente').first().text();
					$('#txtNumCausa').val(numCausa);
					numexpediente = numCausa; 

					numexpedienteid = $(xml).find('mandamientoJudicial').find('numeroExpedienteId').first().text();
					
					var numCaso = $(xml).find('mandamientoJudicial').find('numeroGeneralCaso').first().text();
					$('#txtNumCaso').val(numCaso);
					
					if(numCaso != "")
						$('#txtNumCaso').val(numCaso);
					else
						$('#txtNumCaso').val("-");
					
					numeroDeCaso = numCaso; 
					
					$('#iMandamientoJudicialWorkSheet').hide();
	    		}	
	    	});
		}
		


		/*
		*Funcion para guardar la medida cautelar
		*/
		function guardarMandamientoJudicial(){

			if(validaParametrosDeGuardado() == true){
	
				var params = '';
				params += recuperaDatosMandamientoJudicial();
				//TODO: Cambiar el tipo de forma al momento de guardar el documento
				var formaId = '<%=Formas.MEDIDA_CAUTELAR.getValorId()%>'; 
				var numeroUnicoExpediente = numexpediente;
				
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/crearMandamientoJudicial.do?idInvolucrado='+idInvolucradoSeleccionado+'',
			    	  data: params,				
			    	  dataType: 'xml',
			    	  async: false,
			    	  success: function(xml){
			    		  //Si no tenía mandamiento
			    		  
			    			if($(xml).find("archivoDigitalId").first().text()!=""){
								//el documento ya está generado, consultarlo
								document.verMandamientoForm.documentoId.value = $(xml).find("documentoId").first().text();
								document.verMandamientoForm.submit();
							}else{
								
								mandamientoJudicialId = $(xml).find("documentoId").first().text();
								numExp = $(xml).find("numeroExpediente").first().text();
								
								//mandamiento recién creado, mostrar el editor
								$.newWindow({id:"iframewindowGenerarMandamientoJudicial", 
									statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Mandamiento", 
									type:"iframe"});
						        $.updateWindowContent("iframewindowGenerarMandamientoJudicial",
						        		"<iframe src='<%=request.getContextPath()%>/generarDocumentoSinCaso.do?documentoId="+mandamientoJudicialId+"&numeroUnicoExpediente="+numExp+"&ocultarNumeroOficio=true' width='1140' height='400' />");
							}
			    			
				    		  //Despues de guardar el mandamiento judicial se oculta el boton, mosotrando el botón de cancelar
				    		  $("#guardarMandamientoJudicial").hide();
			    		  
			    		  
			    	 }
			    });
			}
		}
			
		/*
		*Funcion que valida que se hayan ingresado todos los campos correctamente
		*/
		function validaParametrosDeGuardado(){
			
			var validacion = false
			var tipoMandamiento = $('#tipoMandamiento option:selected').val();
			var tipoSentencia = $('#tipoSentencia option:selected').val();
			
				if( tipoMandamiento == "-1"){
					customAlert("Seleccione un tipo de mandamiento");
				}
				else if($('#mandamientoJudicialFechaEjecucion').val() == ""){
					customAlert("Seleccione una fecha de ejecución");
				}
				else{
				  		if(tipoMandamiento == <%=TipoMandamiento.SENTENCIA.getValorId()%>){
				  			if( tipoSentencia == "-1"){
								customAlert("Seleccione un tipo de sentencia");
							}else{
								if(validaCamposFechaSentencia() == true)
				  					validacion = true;
							}
					  	}else{
	  						validacion = true;
					  	}
				}
		    return validacion;		    
		}
		
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposFechaSentencia() {

			if ($('#fechaInicioSentencia').val() == '' || $('#fechaFinSentencia').val() == '') {
				customAlert("Debe ingresar la fecha de inicio y fin de sentencia");
				validaFecha=false;
			}else{

				var fechaIniVal = $('#fechaInicioSentencia').val();
				var fechaFinVal = $('#fechaFinSentencia').val();

				var inicio = fechaIniVal.split("/");
				var fin = fechaFinVal.split("/");

				if(fin[2]>inicio[2]){
					validaFecha=true;
				}
				else{
					if(fin[2]<inicio[2]){
						validaFecha=false;
					}
					else{
						if(fin[1]>inicio[1]){
							validaFecha=true;
						}	
						else{
							if(fin[1]<inicio[1]){
								validaFecha=false;
							}
							else{
								if(fin[0]>=inicio[0]){
									validaFecha=true;
								}
								else{
									validaFecha=false;
								}
							}
						}
					}
				}
					
				if(validaFecha==false){	
					customAlert("La fecha final debe de ser mayor ó igual a la fecha inicial");
				}
				
			}
			return validaFecha;
		}



		function recuperaDatosMandamientoJudicial(){
	        var parametros = '&resolutivoId=' + resolutivoId;
	        parametros += '&tipoMandamiento=' +  $('#tipoMandamiento option:selected').val();
	        parametros += '&tipoSentencia=' +  $('#tipoSentencia option:selected').val();
	        parametros += '&idImputado=' + idInvolucradoSeleccionado;
	        parametros += '&fechaInicio=' + $('#fechaInicioSentencia').val();        
	        parametros += '&fechaFin=' + $('#fechaFinSentencia').val(); 
	        parametros += '&fechaEjecuacion=' + $('#mandamientoJudicialFechaEjecucion').val(); 
	        parametros += '&descripcionMandamiento='+  $('#descripcionMandamiento').val();
	        parametros += '&numeroExpedienteId='+numexpedienteid;
	        parametros += '&idAudiencia='+idAudiencia;
			return parametros;
		}

		function habilitaCampos(){
	        $('#tipoMandamiento').attr("disabled","");
	        $('#tipoMandamiento').attr("disabled","");
	        $('#mandamientoJudicialFechaEjecucion').attr("disabled","");        
	        $('#tipoSentencia').attr("disabled","");        
	        $('#fechaInicioSentencia').attr("disabled","");        
	        $('#fechaFinSentencia').attr("disabled","");        
	        $('#descripcionMandamiento').attr("disabled",""); 
		}

		function deshabilitaCampos(){
	        $('#tipoMandamiento').attr("disabled","disabled");
	        $('#mandamientoJudicialFechaEjecucion').attr("disabled","disabled");        
	        $('#tipoSentencia').attr("disabled","disabled");        
	        $('#fechaInicioSentencia').attr("disabled","disabled");        
	        $('#fechaFinSentencia').attr("disabled","disabled");        
	        $('#descripcionMandamiento').attr("disabled","disabled"); 
		}
		
/*************************************************FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/

		//Variable para controlar la carga del grid
		var primeraVezDocumentosRelacionados = true;

		/*
		*Funcion para consultar los doocumentos del mandamiento judicial de acuerdo
		*al tipo de documento seleccionado por el usuario:
		*	- Creacion
		*	- Cambio de estatus
		*	- Adjuntos
		*/
		function consultarDocumentosDeMandamientoJudicialPorExpediente(){

			var tipoDocumento = $('#cbxTipoConsultaDocumentos option:selected').val();
			
			if(primeraVezDocumentosRelacionados == true){
				
				jQuery("#gridDocumentos").jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'', 
					datatype: "xml", 
					colNames:['Fecha de Elaboracion','Nombre'], 
					colModel:[ 					
					           	{name:'fechaElab',index:'2', width:150, align:'center'}, 
					           	{name:'Nombre',index:'Nombre', width:150, align:'center'}, 
							],
					pager: jQuery('#pagerGridDocumentos'),
					rowNum:10,
					autoWidth:false,
					width:700,
					rowList:[10,20,30],
					sortname: '2',
					viewrecords: true,
					sortorder: "desc",
					ondblClickRow: function(rowid) {
						consultaDocumento(rowid);
					} 
				});
				
				primeraVezDocumentosRelacionados=false;
				
			}else{				
				jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
				$("#gridDocumentos").trigger("reloadGrid");
			}
			
			$("#gview_pagerGridDocumentos .ui-jqgrid-bdiv").css('height', '150px');
		}
		


		function consultaDocumento(idDocumento){
			document.frmDocumento.documentoId.value = idDocumento;
			document.frmDocumento.submit();
		}


		
		function abrirPDF(rowid){
			document.frmDoc.archivoDigitalId.value = rowid;
			document.frmDoc.submit();
		}
/***********************************************TERMINA FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/

	
	/************ FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
	function ocultaMuestraTabVisor(claseTab,bandera)
	{
		if(parseInt(bandera)==0)//oculta
		{
			$("."+claseTab).hide();
		}
		else///muestra
		{
			$("."+claseTab).show();
		}
	}
	
	/**
	* Funcion que realiza la carga del catálogo de tipos de mandamiento
	*/
	function cargaTipoMandamiento() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoMandamiento.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoMandamiento').each(function(){
					$('#tipoMandamiento').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/**
	* Funcion que realiza la carga del catálogo de tipos de sentencia
	*/
	function cargaTipoSentencia() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoSentencia.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoSentencia').each(function(){
					$('#tipoSentencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	/*
	*Funcion para mostrar u ocultar el tipo de sentencia dependiendo
	*de la seleccion del usuario
	*/
	function controlTipoMandamiento(){

		var tipoMandamiento = $('#tipoMandamiento option:selected').val();
		
		if(tipoMandamiento == 0){
			limpiaDivTipoMandamiento();
		}
		else if(tipoMandamiento == <%=TipoMandamiento.SENTENCIA.getValorId()%>){
			$('#divEtiTipoSentencia').show();
			$('#divCbxTipoSentencia').show();
			$('#divEtiFechaInicioSentencia').show();
			$('#divFechaInicioSentencia').show();
			$('#divEtiFechaFinSentencia').show();
			$('#divFechaFinSentencia').show();
		}else{
			$('#divEtiTipoSentencia').hide();
			$('#divCbxTipoSentencia').hide();
			$('#divEtiFechaInicioSentencia').hide();
			$('#divFechaInicioSentencia').hide();
			$('#divEtiFechaFinSentencia').hide();
			$('#divFechaFinSentencia').hide();
			
			//limpiaDatosSentencia()
			$('#tipoSentencia').attr('selectedIndex', 0);
			$('#fechaInicioSentencia').val("");
			$('#fechaFinSentencia').val("");
		}
	}
	
	/*
	*Limpia el Div de tipos de mandamiento
	*/
	function limpiaDivTipoMandamiento(){

		$('#tipoMandamiento').attr('selectedIndex', 0);
		$('#nombreDelImputado').attr('selectedIndex', 0);
		$('#tipoSentencia').attr('selectedIndex', 0);
		$('#fechaInicioSentencia').val("");
		$('#fechaFinSentencia').val("");
		//oculta el tipo de sentencia
		$('#divEtiTipoSentencia').hide();
		$('#divCbxTipoSentencia').hide();
		$('#divEtiFechaInicioSentencia').hide();
		$('#divFechaInicioSentencia').hide();
		$('#divEtiFechaFinSentencia').hide();
		$('#divFechaFinSentencia').hide();
	}

	/*
	*Funcion que agrega el calendario a los campos fecha de inicio y fecha de fin
	*/
	function agregarCalendarios(){

		$('#mandamientoJudicialFechaEjecucion').val('');
		$('#fechaInicioSentencia').val('');
		$('#fechaFinSentencia').val('');

		$("#mandamientoJudicialFechaEjecucion").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#fechaInicioSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	
		$("#fechaFinSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	}
	

	//********************************************************FUNCIONES PARA CAMBIO DE ESTATUS**********************************************************/

		/*
		 * Funcion que consulta el catalogo del estatus del mandamiento judicial
		 */
		function consultarCatalogoEstatusMandamiento(){
			
			$.ajax({
				async: true,
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarCatalogoEstatusMandamiento.do',
				data: 'estatusMandamiento=' + estatusActualMandamiento,
				dataType: 'xml',
				success: function(xml){
					$(xml).find('estatusMandamiento').each(function(){
							$('#cbxEstatusMendamientoJud').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}

		/**
		 * Funcion para invocar al editor de texto y cambiar el estatus del
		 * mandamiento judicial, al presionar el boton "guardado definitivo"
		 */
		function generarDocumentoDeCambioEstatusDeMandamientoJudicial(){
		
			if(validarNuevoEstatusMandamientoJud() == true){
		
				var titulo="Cambio de estatus de mandamiento judicial";
				
				var nuevoEstatusMandamientoJudicial = $('#cbxEstatusMendamientoJud option:selected').val();
				
				var parametros = obtenerParametrosActividadDocumento();
				
				parametros += '&mandamientoJudicialId=' + mandamientoJudicialId;
				parametros += '&nuevoEstatusMandamientoJudicial=' + nuevoEstatusMandamientoJudicial; 
				parametros += '&esDocumentoDeMandamientoJudicial=' + true;
				
				$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe"});
				$.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'&ocultarNumeroOficio=true" width="1140" height="400" />');
			}
		}


		/**
		 *Funcion para obtener los siguientes parametros de la tabla confActividadDocumento:
		 *
		 *actividadId
		 *formaId
		 *tipoDocumento
		 *nombreDocumento
		 *usaEditor
		 *estatusId (No importa, ya que no cambiara el estatus del expediente)
		 *nombreActividad
		 *
		 *Nota se requiere el parametro:numeroUnicoExpediente, que corresponde al numeroExpediente (String), como
		 *obligatorio, ya que se estara generando una actividad
		 */
		function obtenerParametrosActividadDocumento(){
		
			var parametros="";
			
			var actividad=0;
			var formaID=0;
			var titulo="";
			var usaeditor="";
			var estatusId="";
			var nombreActividad="";
			
			var confActividadDocumentoId = '<%=ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>';
		
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadDocumentoId+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
					formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
					titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
					usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
					estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
					nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				}
			});
		
			parametros = '&nuevaActividad=' + actividad; 
			parametros += '&numeroUnicoExpediente='+numexpediente;
			parametros += '&formaId=' + formaID;
			parametros += '&titulo=' + titulo;
			parametros += '&nombreActividad=' + nombreActividad;
		
			return parametros;
		}


		/*
		 * Funcion para validar que se haya seleccionado un estatus para el mandamiento judicial
		 */
		function validarNuevoEstatusMandamientoJud(){
		
			if($('#cbxEstatusMendamientoJud option:selected').val() == "0"){
				customAlert("Selccione el nuevo estatus");
				return false;
			}else{
				return true
			}
		}


		/*
		 * Funcion que permite actualizar/cambiar el estatus del mandamiento judicial
		 */
		function cambiarEstatusMandamientoJudicial(mandamientoJudicialId, nuevoEstatusMandamientoJudicial){

			
			$.ajax({
		   		type: 'POST',
		   		url: '<%=request.getContextPath()%>/actualizarMandamientoJudicial.do?mandamientoId='+mandamientoJudicialId+'&estatusMandamiento='+nuevoEstatusMandamientoJudicial+'&numeroExpedienteId='+numexpedienteid+'&numeroGeneralCaso='+numeroDeCaso+'&numeroExpediente='+numexpediente+'',
		   		data: '',
		   		dataType: 'xml',
		   		async: false,
		   		success: function(jsonObject){
					customAlert("El estatus del mandamiento judicial fue actualizado de manera correcta");
					//Permite recargar el grid de la bandeja principal con el nuevo estatus 
					recargarBandejaPrincipalMandamientosXEstatus(nuevoEstatusMandamientoJudicial);
				}
		    });
		}


		/*
		*Funcion para cerrar la ventana de generarDocumentoSinCaso.jsp
		*llamada desde esta pantalla
		*/
		function cerrarVentanaDocumento(){

			if(operacion=='INGRESAR'){
				replicarMandamientoAPGR();
			}
			 
			var pantalla ="iframewindowGenerarMandamientoJudicial";
			
			$.closeWindow(pantalla);
			
			if (typeof window.parent.cerrarVentanaMandamientoJudicial == 'function') {
				
				window.parent.cerrarVentanaMandamientoJudicial(idVentana);
	        }

			if (typeof window.parent.cerrarVentanaMandamientoJudicialJS == 'function') {
				
				window.parent.cerrarVentanaMandamientoJudicialJS(idVentana);
	        }
			
		}
		
		function recargarBandejaPrincipalMandamientosXEstatus(idEstatusMandamiento){			
	         if (typeof window.parent.consultaGeneralMandamientoJudicial == 'function') {
				window.parent.consultaGeneralMandamientoJudicial(1,idEstatusMandamiento)
	          }
		}
		

	//**********************************************FUNCIONES PARA ADJUNTAR DOCUMENTO A MANDAMIENTO JUDICIAL*************************************************/

		/*
		*Funcion para abirir la ventana para adjuntar un documento al mandamiento judicial
		*/
		function abreVentanaAdjuntarDocumentoAMandamiento(){
			
			var extensionesPermitidas = ".pdf,.jpg";
			
			if(typeof(mandamientoJudicialId) != "undefined" && typeof(mandamientoJudicialId) != "null" && mandamientoJudicialId != ""){
				$.newWindow({id:"iframewindowAdjuntarDocumentoAMandamientoJudicial", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento a mandamiento judicial", type:"iframe"});
				$.updateWindowContent("iframewindowAdjuntarDocumentoAMandamientoJudicial",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAMandamientoJudicial.jsp?extensionesPermitidas=' + extensionesPermitidas + '&mandamientoJudicialId='+mandamientoJudicialId+'" width="450" height="200" />');
			}else{
				customAlert("Imposible adjuntar!");
			}   
		}

		/*
		*Funcion que recarga el grid de adjuntar documento, si esa opcion esta seleccionada
		*esta funcion se invoca en adjuntarDocumentoAMandamientoJudicial.jsp
		*/
		function recargaGridAdjuntarDocumento(){

			var tipoMandamientoSeleccionado = $('#cbxTipoConsultaDocumentos option:selected').val();
			
			if(tipoMandamientoSeleccionado == '<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>'){
				jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoMandamientoSeleccionado+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
				$("#gridDocumentos").trigger("reloadGrid");
			}
		}

		
		function consultaNombreInvolucrado(idInvolucradoSeleccionado) {
		       
		      $.ajax({
			      type: 'POST',
		    	  url: '<%= request.getContextPath()%>/ConsultarPersonaDatos.do',
		    	  data:'idInvolucrado='+idInvolucradoSeleccionado,
		    	  dataType: 'xml',
		    	  success: function(xml){			    	
			    	  pintaDatosGenerales(xml);				
		    	  }
		    	});
		}		
		function pintaDatosGenerales(xml){
			   
			   $('#iMCNombre').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('nombre').first().text());
			   $('#iMCApellidoPaterno').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoPaterno').first().text());
			   $('#iMCApellidoMaterno').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoMaterno').first().text());
		 }
		
		/**
		* Función que es invocada cuando se termina la creación del archivo digital de la medida
		*/
		function replicarMandamientoAPGR(){
			$.ajax({
				async: true,
				type: 'POST',
				url:contextoPagina +'/enviarMandamientoJudicial.do',
				data: 'mandamientoId='+mandamientoJudicialId,
				dataType: 'xml',
				success: function(xml){
					//actualizarDatosCerrar(documentoGeneradoId);
					var mensaje = $(xml).find('body').find('respuesta').text();
					if(mensaje == "fallo_envio_de_mandamiento_judicial"){
						alertDinamico("Error al replicar mandamiento judicial !!!");
					}
					else{					
						alertDinamico("Mandamiento judicial enviado correctamente");
					}
				}

			});
		}
		

	
		</script>
	</head>
<body>
<div id="tabsprincipalconsulta">
	<ul>
		<li><a href="#tabsconsultaprincipal-1" id="labelPestaniaMedida">Mandamientos Judiciales</a></li>
		<li class="tabsconsultaprincipal-2"><a href="#tabsconsultaprincipal-2">Documentos</a></li>
		<li class="tabsconsultaprincipal-3"><a href="#tabsconsultaprincipal-3">Mandamientos Judiciales</a></li>
	</ul>
		
	<div id="tabsconsultaprincipal-1">
		<input type="hidden" name="xml" id="xml" />
			
		<table border="0">
			<tr valign="top">
				<td colspan="2">
					<table id="iMedidaCautelarViewHeader" width="100%" border="0">
						<tr>
							<td>
								<table border="0">
									<tr valign="top">
										<td>
											<table style="border: 0" width="100%" cellpadding="0"  cellspacing="0" class="celda2">
												<tr>
					                        		<td width="60%" height="28" align="right">N&uacute;mero de Caso:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" id="txtNumCaso" style="width: 190px;" disabled="disabled"/>
							                    	</td>
							                	</tr>
					                        	<tr>
					                        		<td width="60%" height="28" align="right" id="idEtiquetaNumCausa">N&uacute;mero de Causa:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" id="txtNumCausa" style="width: 190px;" disabled="disabled"/>
							                    	</td>
							                	</tr>
							           			<tr>
							                    	<td width="60%" height="30" align="right">&nbsp;</td>
						                        	<td width="29%">&nbsp;</td>
					                        	</tr>							               
					                    	</table>
										
											<table style="border: 0; background:#DDD;" width="100%" height="143" cellpadding="0"  cellspacing="0" class="celda2">
					                        	<tr>
							                    	<td width="60%" height="30" align="right">Nombre:</td>
						                        	<td width="29%">
						                        		<input type="text" value="" readonly="readonly" title="Escribir nombre" size="50" maxlength="40" id="iMCNombre" style="background:#DDD;border: 0;" readonly="readonly"/>
						                        	</td>
					                        	</tr>
					                        	<tr>
							                    	<td width="60%" height="28" align="right">Apellido paterno:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" value="" readonly="readonly" readonly="readonly" size="50" maxlength="40" id="iMCApellidoPaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
							                    	</td>
							                	</tr>
							                	<tr>
					                          		<td width="60%"  height="35" align="right">Apellido materno:</td>
						                    		<td height="35">
						                    			<input type="text" value="" readonly="readonly"  readonly="readonly" size="50" maxlength="40" id="iMCApellidoMaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
						                    		</td>
					                        	</tr>
					                    	</table>
										</td>
									</tr>
								</table>
								
								<!-- Comienza tabla cambio estatus -->
								<table border="0" >
									<tr id="seccionCambiarEstatusMandamiento" style="display:none">
										<td>
											<strong>Estado:</strong>
											<select id="cbxEstatusMendamientoJud" style="width:150px">
												<option value="0">-Seleccione-</option>
											</select>
										</td>
									    <td>
											<input type="button" value= "Guardar" id="btnGuardarEstatusMandamientoJud" class="btn_Generico" onclick="generarDocumentoDeCambioEstatusDeMandamientoJudicial();"/>
										</td>
									</tr>
										
									<tr id="seccionAdjuntarDocumentoAMandamiento" style="display:none">
										<td>&nbsp;
												
										</td>
									    <td>
											<input type="button" value= "adjuntar documento" id="btnAdjuntarDocAMandamientoJud" class="btn_Generico" onclick="abreVentanaAdjuntarDocumentoAMandamiento();"/>	
										</td>
									</tr>
									
								</table>
								<!-- Termina tabla cambio estatus -->
								
								
								
							</td>
						</tr>
					</table>
				</td>
				<td width="14%">
					<table border="0">
						<tr>
							<td width="20%" align="right">Tipo de Mandamiento:</td>
							<td width="20%">
						        <select id="tipoMandamiento">
									<option value="-1">-Seleccione-</option>
						    	</select>
							</td>
						</tr>		
										
						<tr id="estadoTR">
							<td width="20%" align="right">Estado:</td>
							<td>
								<input type="text" id="cmpEstado" size="40" style="width: 150px;" disabled="disabled">
							</td>
						</tr>
						
						<tr>
							<td width="20%" align="right">Fecha de ejecuci&oacute;n:</td>
							<td><input type="text" id="mandamientoJudicialFechaEjecucion" style="width: 100px;" /></td>
						</tr>
						
						<tr>
					        <td width="20%" align="right">
					        	<div id="divEtiTipoSentencia">
						        	<strong>Tipo de Sentencia:</strong>
					            </div>
					        </td>
					        <td width="20%">
					        	<div id="divCbxTipoSentencia">
					                <select id="tipoSentencia" style="width: 150px;">
					                    <option value="-1">-Seleccione-</option>
					                </select>
					        	</div>
					        </td>
					    </tr>
					    
					    <tr>
					        <td width="20%" align="right">
					        	<div id="divEtiFechaInicioSentencia">
					        		<strong>Fecha Inicio:</strong>
					        	</div>
					        </td>
					        <td width="20%" >
					        	<div id="divFechaInicioSentencia">
					        		<input type="text" id="fechaInicioSentencia" style="width: 100px;"/>
					        	</div>
					        </td>
					    </tr>
					    <tr>
					        <td width="20%" align="right">
					        	<div id="divEtiFechaFinSentencia">
					        		<strong>Fecha Fin:</strong>
					        	</div>
					       	</td>
					        <td width="20%">
					        	<div id="divFechaFinSentencia">
					        		<input type="text" id="fechaFinSentencia" style="width: 100px;"/>
					        	</div>
					        </td>
					    </tr>
					    
					    <tr>
							<td width="20%" align="right">Descripci&oacute;n:</td>
							<td><textarea style="width: 150px;" id="descripcionMandamiento"></textarea></td>
						</tr>
						
						
					</table>	
				</td>
			</tr>
			<tr valign="top">
				<td colspan="2">
					<table id="iMandamientoJudicialWorkSheet" width="100%"  border="0">
						<tr valign="top">
							<td align="center">
								<input type="button" value="Generar Mandamiento Judicial" id="guardarMandamientoJudicial" class="btn_Generico"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
		<form name="frmDocumento" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
	</div>

	<div id="tabsconsultaprincipal-2">
		<table width="700" border="0">
			<tr>
				<td>
					<strong>Tipos de documentos:</strong>
					<select id="cbxTipoConsultaDocumentos">
						<option value="<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>">Documentos de creacion</option>
						<option value="<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>">Documentos por cambio de estatus</option>
						<option value="<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>">Documentos Adjuntos</option>
					</select>
				</td>
			</tr>

			<tr>
				<td>
					<div>
						<table id="gridDocumentos" ></table>
						<div id="pagerGridDocumentos" style="width: 300"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tabsconsultaprincipal-3">
	    
		<table border="0">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<div>
						<table id="gridMandamientosJudiciales" ></table>
						<div id="pagerGridMandamientosJudiciales"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>

<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	<input type="hidden" name="archivoDigitalId" value=""/>
</form>

<form name="verMandamientoForm" id="verMandamientoForm"
	action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do"
	method="post">
	<input type="hidden" name="documentoId" />
</form>
					
</body>
</html>