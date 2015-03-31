<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.funcionario.Puestos" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Atención a solicitudes periciales</title>

	<!--Se importan las css necesarias-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css" />

	<!--Se importan los scripts necesarios-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>

	<script type="text/javascript">
	/*ENable It contexto*/
	var contextoPagina = "${pageContext.request.contextPath}";
	var idNumeroExpediente = 0;
	var idExpediente = 0;
	var idAudiencia;
	var numeroGeneralCaso;
	var idEvento = parent.audienciasid;
	var visorDoc = 1;
	var estatusAudiencia=null;
	var idEventoParcial=<%= request.getSession().getAttribute("idEvento")%>;
	if(idEventoParcial!="undefined"){
		idEvento=idEventoParcial;
	}
	idAudiencia=idEvento;
	// Variables Generales recuperadas de la peticion
	var numeroCausa = ('<%=request.getParameter("numCausa")%>' == null ? '<%=request.getSession().getAttribute("numCausa")%>' : parent.numCausa);
	var numCaso = ( parent.numCaso != undefined ? parent.numCaso : "");

	var numExpedienteId  = '<%=request.getParameter("numExpedienteId")%>';
	if(numExpedienteId == "null"){
		numExpedienteId = '<%=request.getSession().getAttribute("numExpedienteId")%>';
	}

	var flujoMedCautelar = '<%=request.getParameter("flujoMedCautelar")%>';
	if(flujoMedCautelar == "null"){
		flujoMedCautelar = '<%=request.getSession().getAttribute("flujoMedCautelar")%>';
	}
	// Si es Encargado Causa, se habilita la pestana de Medidas Cautelares
	var pestanaMedidaCautelar = '<%=request.getParameter("tabMedCautelar")%>';
	if(pestanaMedidaCautelar == "null"){
		pestanaMedidaCautelar = '<%=request.getSession().getAttribute("tabMedCautelar")%>';
	}


	var idWindowMostrarMedidaCautelar = 1;
	var banderaGridInvolucradosMedidas = true;
	var banderaGridAgregarMedidaCautelar = true;
	var banderaGridDocumentosDigitalesPropios = true;

		$(document).ready(function() {

		$("#tabsPrincipal").tabs();

		// Llamadas para la ceja documentos propios
		cargaGridDocumentosDigitalesPropios();
		pintaDetalle();

		if(pestanaMedidaCautelar == "true"){
			$('#mandamientosJudiciales').show();
			cargaGridInvolucradosCausaPJENC(numeroCausa);
			if(flujoMedCautelar!="dePJaSSPyPGJ"){
				//Ocultar Periodicidad
				jQuery("#gridInvolucradosCausa").jqGrid('hideCol',"periodicidad");
			}


			/**********************************
			*LLamdas para la ceja Pruebas
			************************************/
			$('#pruebas').show();
			$( "#tabschildPruebasPJENS" ).tabs();

			//LLamadas para la sub ceja Datos de Pruebas
			cargaGridDatosPrueba();
			$("#tipoDatoPruebaPJENS").change(controlDatoDePrueba);
			$("#tipoMediaPruebaPJENS").change(controlMedioDePrueba);

			//LLamadas para la sub ceja de Medios de Pruebas
			cargaGridMediosDePrueba();

			//Llamadas para la sub ceja de Prueba
			cargaGridPrueba();
		}
		else{
			$('#mandamientosJudiciales').hide();
			$('#pruebas').hide();
		}
		
		consultaDetalleEvento(idEvento);
		/* enable IT: carga actuaciones */
		cargaActuaciones();
		/*
		*LLamdas para la ceja Adiministrar Actuaciones de Audiencia
		*/
			$("#finalAudienciaOpcion").change(onSelectChangeTipo);
		
		/*
		*Llamada para la ceja de Documentos
		*/
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			
			url:'<%=request.getContextPath()%>/consultarDocumentos.do?numidExpedienteDoc='+numeroCausa+'&visorDoc='+visorDoc,
			datatype: "xml",
			colNames:['Área del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
			colModel:[ 	{name:'area',index:'da', width:200, align:'center'},
						{name:'FechaActividad',index:'fechaActividad', width:170, align:'center', hidden:true},							
						{name:'NombreActividad',index:'nombreActividad', width:400, align:'center', hidden:true},
			           	{name:'Tipo',index:'tipo', width:155, align:'center'}, 
						{name:'Nombre',index:'nombre', width:255, align:'center'},
			           	{name:'Fecha',index:'fecha', width:170, align:'center'},
			           	{name:'Documento',index:'documento', width:170, align:'center'},
			           	{name:'EsParcial',index:'esParcial', align:'center', hidden:true}
						],
			pager: jQuery('#pager1Documentos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			autowidth: false,
			width:1100,
			sortname: 'turno',
			viewrecords: true,
			id: 'divgrid',
			ondblClickRow: function(id){
				var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
				if(retd.EsParcial){
					noEsParcial = retd.EsParcial.indexOf('false');
					if(noEsParcial > 0){
						consultaPDF(id);
					}
					else{
						numExp = $("#numCausaDetalle").val();
		    		    $.newWindow({id:"iframewindowGenerarActaAudiencia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:retd.Nombre, type:"iframe"});
		    		    $.updateWindowContent("iframewindowGenerarActaAudiencia",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numExp+'&esconderArbol=1&idAudiencia='+idAudiencia+'" width="1140" height="400" />');
					}
				}
				
			},
			sortorder: "desc"
		}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
		
		/*Carga Actuaciones */
		$("#cbxAccionesTab").change(function(e){
			seleccionaActuacion();
		});
		
		
	});//FIN ONREADY
	
    /*Actuaciones parametricas*/
    function cargaActuaciones() {
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?numeroExpediente='+numeroCausa,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTab').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
    /* ENABLE IT: Se agregan actuaciones paramétricas */
    function seleccionaActuacion(){
		var selected = $("#cbxAccionesTab option:selected");
		var confActividadId=selected.val();
		var actividad=0;
		var formaID=4;
		var titulo="op";
		var usaeditor="";
		var estatusId="";
		var nombreActividad="";

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
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

		ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId);
	}
	
    function ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId){
    	if(usaeditor== "true"){
    		registrarActividadExpediente(actividad,estatusId,0);
	     	$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe"});
	    	$.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroCausa+'&idAudiencia='+idAudiencia+'" width="1140" height="400" />');
	    	$("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
		}
	}
    
    function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje){
    	//Cambia la actividad del expediente
    	$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+idExpediente+'&actuacion='+actuacionID+'&estatus='+estatusId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					customAlert("Actividad nueva registrada");
				}
			}
		});
	}
    
	function consultaPDF(id){
		document.frmDococumentos.documentoId.value = id;
		document.frmDococumentos.submit();
	}
	
	//**********************************************************FUNCIONALIDAD DE LA CEJA DETALLES DE AUDIENCIA**********************************************************\

	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultaDetalleEvento(idEvento){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/detalleAudienciasDelDiaPJENS.do',
			data: 'idEvento='+ idEvento, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
    				//Se configuran variables para registrar un amparo
    				limpiaDatosDetalleEvento();
    				idExpediente = $(xml).find('expedienteId').first().text();
    				idNumeroExpediente = $(xml).find('numeroExpedienteId').first().text();

    			}
			}
		});
	}
	
	
	// Funcion que abre la ventana de medidas cautelares
	function mostrarVentanaMedidasCautelares(rowid){

		idWindowMostrarMedidaCautelar++;
		$.newWindow({id:"iframewindowMedidasCautelares"+idWindowMostrarMedidaCautelar, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Consultar Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowMedidasCautelares"+idWindowMostrarMedidaCautelar,'<iframe src="<%=request.getContextPath()%>/ingresarMedidaCautelarPJENC.do?numeroExpedienteId='+numeroCausa+'&rowid='+rowid+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA" width="880" height="360" />');
	}

	// Funcion que consulta los involucrados por causa
	function cargaGridInvolucradosCausaPJENC(numeroCausa){

		if(banderaGridInvolucradosMedidas == true){

			jQuery("#gridInvolucradosCausa").jqGrid(
					{ url:'<%= request.getContextPath() %>/consultarMedidasCautelaresPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'',
						datatype: "xml",
						mtype: 'POST',
						colNames:['Nombre','Medida Cautelar', 'Descripci&oacute;n','Periodo de Imposici&oacute;n','Periodicidad','Encargado Seguimiento', 'Estado'],
						colModel:[ 	{name:'nombre',index:'nombre', width:200},
									{name:'medidaCautelar',index:'medidaCautelar', width:100},
									{name:'descripcionMedida',index:'descripcionMedida', width:150},
									{name:'periodoAplicacion',index:'periodoAplicacion', width:150},
									{name:'periodicidad',index:'periodicidad', width:150},
									{name:'encargadoSeguimiento',index:'encargadoSeguimiento', width:150,hidden: true},
									{name:'estadoMedida',index:'estadoMedida', width:150 },
								],
							autowidth: false,
							width:924,
							height: 200,
							pager: jQuery('#paginadorInvolucradosCausa'),
							rowNum:10,
							rowList:[10,20,30],
							sortname: 'nombre',
							rownumbers: false,
							gridview: false,
							viewrecords: true,
							sortorder: "desc",
							caption:"Administrar medidas cautelares",
							ondblClickRow: function(rowid) {
								mostrarVentanaMedidasCautelares(rowid);
							}
					}).navGrid('#paginadorInvolucradosCausa',{edit:false,add:false,del:false});
			banderaGridInvolucradosMedidas=false;
		}
		else{
			jQuery("#gridInvolucradosCausa").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarMedidasCautelaresPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridInvolucradosCausa").trigger("reloadGrid");
		}
	}

	// Funcion que abre el PDF para ver los documentos asociados al numero de causa
	function abrirDocsDigAsociadosASol(documentoId){
		if(documentoId != null && documentoId != ""){
			$("#visorDocsPropiosFrame").attr("src","<%= request.getContextPath()%>/consultarArchivoDigitalIframe.do?documentoId="+documentoId+"&inFrame=true");
		}
		else{
			customAlert("El documento no puede ser mostrado");
		}
	}

	// Funcion que carga el grid con los nombre de los documentos digitales asociados al id de la solicitud de serv. periciales
	function cargaGridDocumentosDigitalesPropios(){
		if(banderaGridDocumentosDigitalesPropios == true){
			jQuery("#gridDocumentosDigitalesPropios").jqGrid({
				url:'<%=request.getContextPath()%>/ConsultaExpedientesDocumento.do?numeroExpedienteId='+numExpedienteId+'',
				datatype: "xml",
				colNames:['Nombre de Documento'],
				colModel:[	{name:'nombre',index:'nombre', width:255},

							],
				pager: jQuery('#pagerGridDocumentosDigitalesPropios'),
				rowNum:20,
				rowList:[10,20,30],
				width:250,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					if (rowid) {
						abrirDocsDigAsociadosASol(rowid);
					}
				},
				loadComplete: function(){
					jQuery("#gridDocumentosDigitalesPropios").jqGrid('hideCol',["area","fechaActividad","nombreActividad","tipo","fecha"]);
				}
			}).navGrid('#pagerGridDocumentosDigitalesPropios',{edit:false,add:false,del:false});
			$("#gview_gridDocumentosDigitalesPropios .ui-jqgrid-bdiv").css('height', '455px');
			banderaGridDocumentosDigitalesPropios= false;
		}
		else{
			jQuery("#gridDocumentosDigitalesPropios").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?numeroExpedienteId='+numExpedienteId+'',datatype: "xml" });
			$("#gridDocumentosDigitalesPropios").trigger("reloadGrid");
		}
	}

	function pintaDetalle(){
		 $.ajax({
		      type: 'POST',
	    	  url: '<%= request.getContextPath()%>/visorDocumentoDetalle.do',
	    	  data: 'numExpediente='+numeroCausa + '&numCaso=' + numCaso,
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
	    		    $('#numCausaDetalle').val($(xml).find('expedienteDto').find('numeroExpediente').first().text());
			    	$('#numCasoDetalle').val($(xml).find('expedienteDto').find('casoDTO').find('numeroGeneralCaso').first().text());
			    	$('#fCreacionDetalle').val($(xml).find('expedienteDto').find('casoDTO').first().find('strfechaApertura').text());
			    	$('#horaCreacionDetalle').val($(xml).find('expedienteDto').find('casoDTO').first().find('strHoraFechaApertura').text());
			    	$(xml).find('expedienteDto').find('delitos').find('delitoDto').each(function(){
	    				$('#delitosDetalle').append('<option>'+ $(this).find('catDelitoDTO').find('nombre').text() + '</option>');
	    			});
			    	$(xml).find('expedienteDto').find('involucradosDTO').find('involucradoDTO').find('nombresDemograficoDTO').find('nombreDemograficoDTO').each(function(){
						$('#imputadoDetalle').append('<option>'+ $(this).find('nombre').text() +" "+ $(this).find('apellidoPaterno').text() +" "+ $(this).find('apellidoMaterno').text() + '</option>');
	    			});
			    	$('#txtEstatusExpediente').val($(xml).find('expedienteDto').find('estatus').last().find('valor').text());
	    	  }
	   	});
	}

	// Funcion que abre la modal para agregar una medida cautelar a un imputado
	function agregarMedidaCautelar(){

		cargaGridAgregarMedidaCautelar();
		$("#divAgregarMedidaCautelar").dialog("open");
		$("#divAgregarMedidaCautelar").dialog({ autoOpen: true, modal: true,
			title: 'Agregar medida caultelar',
			dialogClass: 'alert', position: [250,50],
			width: 800, height:480, maxWidth: 800, maxHeight:550,
			buttons:{
				"Agregar":function() {
					validaSeleccion();
				},
				"Cancelar":function() {
					confirmar=confirm("¿Realmente desea salir?");
					if (confirmar){
							$(this).dialog("close");
					}
				}
			}
		});
	}

	// Funcion que carga el grid con los imputados del expediente
	function cargaGridAgregarMedidaCautelar(){

		if(banderaGridAgregarMedidaCautelar == true){
			jQuery("#gridAgregarMedidaCautelar").jqGrid({
				url:'<%=request.getContextPath()%>/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',
				data:'',
				datatype: "xml",
				colNames:['Nombre del Imputado'],
				colModel:[
				           	{name:'nombreImputado',index:'nombreImputado',width:350, align:'center'},
						],
				pager: jQuery('#pagerGridAgregarMedidaCautelar'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[25,50,100],
				sortname: 'nombreImputado',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados"
			}).navGrid('#gridAgregarMedidaCautelar',{edit:false,add:false,del:false});
			banderaGridAgregarMedidaCautelar = false;
		}
		else{
			jQuery("#gridAgregarMedidaCautelar").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridAgregarMedidaCautelar").trigger("reloadGrid");
		}
	}

	function validaSeleccion(){

		var rowid = jQuery("#gridAgregarMedidaCautelar").jqGrid('getGridParam','selrow');

		if (rowid) {
			$("#divAgregarMedidaCautelar").dialog("close");
			nuevaVentanaMedidasCautelares(rowid);
		}else{
			customAlert("Por favor seleccione un renglón");
		}
	}

	// Funcion para ingresar una nueva medida cautelar
	function nuevaVentanaMedidasCautelares(idInvolucrado){

		idWindowMostrarMedidaCautelar++;
		idVentana = "iframewindowMedidasCautelares"+idWindowMostrarMedidaCautelar;

		var rowid= idInvolucrado+',';
		$.newWindow({id:"iframewindowMedidasCautelares"+idWindowMostrarMedidaCautelar, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Ingresar/Modificar Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowMedidasCautelares"+idWindowMostrarMedidaCautelar,'<iframe src="<%=request.getContextPath()%>/ingresarMedidaCautelarPJENC.do?numeroExpedienteId='+numeroCausa+'&rowid='+rowid+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=INGRESAR&idVentana='+idVentana+'" width="880" height="360" />');
	}

//*************************************** FUNCIONALIDAD PARA LA CEJA DE PRUEBAS*********************************************************************/
//////////////////////FUNCIONALIDAD PARA LA CEJA DATOS DE PRUEBA

	var banderaDatosPrueba = true;
	/**
	*Funcion que carga el grid datos de Prueba
	*/
	function cargaGridDatosPrueba(){

		if(banderaDatosPrueba == true){
			jQuery("#gridDatosDePruebaPJENS").jqGrid({
				url:'<%=request.getContextPath()%>/consultarDatosDePruebaPorFiltro.do?numeroExpediente='+numeroCausa+'',
				datatype: "xml",
				colNames:['Nombre del Dato','Número de Identificación', 'Reg. de Cadena de Custodia','Registrado','Aceptado','Desechado'],
				colModel:[
				           	{name:'nombreDato',index:'nombreDato', width:100, align:'left'},
				           	{name:'numIdentificacion',index:'numIdentificacion', width:80, align:'left'},
				        	{name:'rcc',index:'rcc', width:100, align:'left'},
				           	{name:'registrado',index:'registrado', width:50, align:'center'},
				           	{name:'aceptado',index:'aceptado', width:50, align:'center'},
				           	{name:'rechazado',index:'rechazado', width:50, align:'center'},
						],
				pager: jQuery('#pagerGridDatosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:800,
				rowList:[10,20,30],
				sortname: 'NombreDato',
				viewrecords: true,
				sortorder: "desc",
				caption:"Datos de Prueba",
				toolbar: [true,"top"],
				ondblClickRow: function(rowid) {
					consultarDatoDePrueba(rowid);
				}
			}).navGrid('#pagerGridDatosDePruebaPJENS',{edit:false,add:false,del:false});
			$("#gview_gridDatosDePruebaPJENS .ui-jqgrid-bdiv").css('height', '200px');

			banderaDatosPrueba=false;

			$("#t_gridDatosDePruebaPJENS").append("<input type='button' id='btnAgregarDatoPrueba' class='btn_Generico' value='Agregar' style='height:20px;width:60px;font-size:-3'/>"+" "+"<input type='button' id='btnRelacionarDatoPrueba' class='btn_Generico' value='Relacionar' style='height:20px;width:70px;font-size:-3'/>");

			$("#btnAgregarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				agregarDatoDePrueba();
			});

			$("#btnRelacionarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				var rowid = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
				if(rowid){
					var renglon = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',rowid);
					//verifica si la sub string checked se encuentra en la cadena input,
					//de NO ser asi devuelve -1
					if(renglon.aceptado.indexOf("checked") != -1){

						relacionarMediosDePrueba(rowid);
					}
					else{
						alertDinamico("El dato de prueba debe ser aceptado para \n relacionarlo con un medio de prueba");
					}
				}
				else{
					alertDinamico("Seleccione un dato de prueba a relacionar");
				}
			});

			//Originalmente la funcionalidad de asociar paria de este grid
			$("#btnAsociarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				var rowid = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
				if(rowid){
					var renglon = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',rowid);
					//verifica si la sub string checked se encuentra en la cadena input,
					//de NO ser asi devuelve -1
					if(renglon.aceptado.indexOf("checked") != -1){
						asociarDatoPrueba(rowid);
					}
					else{
						alertDinamico("El dato de prueba debe ser aceptado para \n asociarlo con un imputado");
					}
				}
				else{
					alertDinamico("Seleccione un dato de prueba a asociar");
				}
			});
		}
		else{
			jQuery("#gridDatosDePruebaPJENS").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDatosDePruebaPorFiltro.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridDatosDePruebaPJENS").trigger("reloadGrid");
		}
	}


	/*
	*Funcion para cambiar el estado del dato de prueba
	*/
	function rechazarDatoPrueba(datoPruebaId){
		$.ajax({
	   		type: 'POST',
	   		url: '<%= request.getContextPath()%>/actualizarEstadoDatoPrueba.do?esAceptado=false&numeroExpediente='+numeroCausa+'',
	   		data:'datoPruebaId='+datoPruebaId,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
	   			cargaGridDatosPrueba();
	   			cargaGridPrueba();
	   			alertDinamico("El dato de prueba ha sido rechazado");
				//fata el motivo del rechazo
	   			//$(this).dialog("close");
	   			//$("#motivoRechazoDatoPrueba").val("");
	   		}
		});
	}

	/*
	*Funcion para cambiar el estado del dato de prueba
	*/
	function aceptarDatoPrueba(datoPruebaId){
		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/actualizarEstadoDatoPrueba.do?esAceptado=true&numeroExpediente='+numeroCausa+'',
    		data:'datoPruebaId='+datoPruebaId,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			alertDinamico("El dato de prueba ha sido aceptado");
    			cargaGridDatosPrueba();
    			cargaGridPrueba();
    		}
		});
	}



	//Funcion usada para asignarle id a la ventana de consultar datos de prueba
	var consultarDatoPruebaWindow = 1;
	/*
	*Funcion para consultar datos de prueba vs medios de prueba
	*/
	function consultarDatoDePrueba(idDatoPrueba){
		consultarDatoPruebaWindow++;
		$.newWindow({id:"iframewindowConsultarDatoPrueba"+consultarDatoPruebaWindow, statusBar: true, posx:80,posy:60,width:1240,height:420,title:"Consultar Dato de Prueba", type:"iframe"});
    	$.updateWindowContent("iframewindowConsultarDatoPrueba"+consultarDatoPruebaWindow,'<iframe src="<%= request.getContextPath() %>/consultarDetalleDatoPruebaPJENS.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'" width="1240" height="420" />');
	}



	/*
	*Funcion que abre la modal para relacionar los datos de prueba
	*con los medios de prueba
	*/
	function relacionarMediosDePrueba(idDatoPrueba){

		cargaGridMediosPrueba(idDatoPrueba);
		$("#divRelacionarMediosDePruebaPJENS").dialog("open");
		$("#divRelacionarMediosDePruebaPJENS").dialog({ autoOpen: true, modal: true,
		title: 'Relacionar medios de prueba',
		dialogClass: 'alert', position: [350,50],
		width: 800, height:480, maxWidth: 800, maxHeight:550,
		buttons:{
			"Relacionar":function() {

				var iDsMediosPrueba="";
				iDsMediosPrueba = jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid('getGridParam','selarrrow');
				guardarRelacionMediosDePruebaVsDatoPrueba(idDatoPrueba,iDsMediosPrueba);
			   	$(this).dialog("close");
			},
			"Cancelar":function() {

				customConfirm("¿Realmente desea salir?", "", cancelarRelacionarMediosDePrueba);
			}
		}
		});
	}

	function cancelarRelacionarMediosDePrueba(){
  		$("#divRelacionarMediosDePruebaPJENS").dialog("close");
	}



	/**
	*Funcion que guarda la relacion entre los medios de prueba
	*recibe un arreglo con los ids de medios de prueba y el id del dato de prueba
	*con el que se relacionaran
	*/
	function guardarRelacionMediosDePruebaVsDatoPrueba(idDatoPrueba,iDsMediosPrueba){

		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/relacionarMedioPruebaConDatoPrueba.do?iDsMediosPrueba='+iDsMediosPrueba+'&idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'',
    		data:'',
    		dataType:'xml',
    		async:false,
    		success: function(xml){
	   			if(parseInt($(xml).find('code').text())==0)
	    		  {
	   				alertDinamico("La relación ha sido guardada");
					//Limpiar los ids seleccionados
	   	    	}
	  			else{
	  				alertDinamico("Ocurrió un error durante la relación");
	      		}
    		}
		});
	}


	//Variable para controlar si se carga por primera vez el grid
	var banderaRelacionarMediosPrueba = true;

	/**
	*Funcion que carga el grid medios de Prueba
	*/
	function cargaGridMediosPrueba(idDatoPrueba){

		if(banderaRelacionarMediosPrueba == true){

			jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid({
				url:'<%=request.getContextPath()%>/consultarMediosPruebaXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'',
				datatype: "xml",
				colNames:['Nombre del Medio','Número de Identificación'],
				colModel:[
				           	{name:'nombreDato',index:'nombreDato', width:350, align:'center'},
				           	{name:'numIdentificacion',index:'numIdentificacion', width:350, align:'center'},
						],
				pager: jQuery('#pagerGridRelacionarMediosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[10,20,30],
				sortname: 'NombreDato',
				viewrecords: true,
				sortorder: "desc",
				caption:"Medios de Prueba",
				toolbar: [true,"top"],
				multiselect:true
			}).navGrid('#gridRelacionarMediosDePruebaPJENS',{edit:false,add:false,del:false});
			banderaRelacionarMediosPrueba = false;

			$("#t_gridRelacionarMediosDePruebaPJENS").append("<input type='button' class='btn_Generico' value='Agregar' style='height:20px;width:60px;font-size:-3'/>");
			$("input","#t_gridRelacionarMediosDePruebaPJENS").click(function(){
				agregarMedioDePrueba();
			});
		}
		else{
			jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarMediosPruebaXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridRelacionarMediosDePruebaPJENS").trigger("reloadGrid");
		}
	}


	/*
	*Funcion que abre la modal para relacionar los datos de prueba
	*con los medios de prueba
	*/
	function asociarDatoPrueba(idDatoPrueba){

		cargaGridImputadosAsociar(idDatoPrueba);
		$("#divAsociarDatoDePruebaPJENS").dialog("open");
		$("#divAsociarDatoDePruebaPJENS").dialog({ autoOpen: true, modal: true,
		title: 'Asociar imputados al dato de prueba',
		dialogClass: 'alert', position: [350,50],
		width: 800, height:480, maxWidth: 800, maxHeight:550,
		buttons:{
			"Asociar":function() {

				var iDsImputados="";
				iDsImputados = jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid('getGridParam','selarrrow');
				guardarAsociacionDatoPrueba(idDatoPrueba,iDsImputados);
			   	$(this).dialog("close");
			},
			"Cancelar":function() {
				customConfirm("¿Realmente desea salir?", "", salir);
			}
		}
		});
	}

	function salir(){
  		$("#divAsociarDatoDePruebaPJENS").dialog("close");
	}




	//bandera para controlar el cargado de la asociación de imputados
	banderaAsociarImputado=true;

	/**
	*Funcion que carga el grid con los imputados a asociar
	*/
	function cargaGridImputadosAsociar(idDatoPrueba){

		if(banderaAsociarImputado == true){


			jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid({
				url:'<%=request.getContextPath()%>/consultarImputadosXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'',
				datatype: "xml",
				colNames:['Nombre del Imputado'],
				colModel:[
				           	{name:'nombreImputado',index:'nombreImputado', width:350, align:'center'},
						],

				pager: jQuery('#pagerGridAsociarDatoDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[10,20,30],
				sortname: 'nombreImputado',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados",
				toolbar: [true,"top"],
				multiselect:true
			})

			banderaAsociarImputado = false;
		}
		else{
			jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadosXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridAsociarDatoDePruebaPJENS").trigger("reloadGrid");
		}
	}


	/**
	*Funcion que guarda la asociacion entre los datos de prueba
	*recibe un arreglo con los ids de los imputados y el id del dato de prueba
	*con el que se asociaran
	*/
	function guardarAsociacionDatoPrueba(idDatoPrueba,iDsImputados){

		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/asociarImputadoConDatoPrueba.do?iDsImputados='+iDsImputados+'&idDatoPrueba='+idDatoPrueba+'',
    		data:'',
    		dataType:'xml',
    		async:false,
    		success: function(xml){
	   			if(parseInt($(xml).find('code').text())==0)
	    		  {
	   				alertDinamico("La asociación ha sido guardada");
					//Limpiar los ids seleccionados
	   	    	}
	  			else{
	  				alertDinamico("Ocurrió un error durante la asociación");
	      		}
	      		cargaGridPrueba();

    		}
		});
	}


	/*
	*Funcion que despliega la ventana para agregar un dato de prueba
	*/
	function agregarDatoDePrueba(){

		seleccioneTipoDatoPrueba();
		$('#tipoDatoPruebaPJENS').attr('selectedIndex', 0);
		$("#divAgregarDatoDePrueba").dialog("open");
		$("#divAgregarDatoDePrueba").dialog({ autoOpen: true, modal: true,
		title: 'Agregar Dato de Prueba',
		dialogClass: 'alert', position: [500,50],
		width: 595, height: 450, maxWidth: 595,
		buttons:{
			"Agregar":function() {
				guardarDatoPrueba(null);
			   	//$(this).dialog("close");
			},
			"Cancelar":function() {

				customConfirm("¿Realmente desea salir del registro del dato de prueba?", "", cancelarAgregarDatoDePrueba);
			}
		}
		});
	}

	function cancelarAgregarDatoDePrueba(){
		limpiarYSalirDatoDePrueba();
  		$("#divAgregarDatoDePrueba").dialog("close");
	}



	/*
	*Funciones para agregar medios de prueba dependiendo de la seleccion
	*/
	function guardarDatoPrueba(datoPruebaId){

		var seleccion = $("#tipoDatoPruebaPJENS option:selected").val();
		var validacion=false;
		var paramDatoPrueba="";

		if(seleccion == "0"){
			validacion=false;
			alertDinamico("Seleccione un tipo de dato de prueba");
		}
		//Validacion de documentos
		if(seleccion == "1"){
			if($('#txtNombreDatoPruebaPJENS').val() == ""){
				alertDinamico("Ingrese un nombre");
			}
			else{
				if($('#txtNumeroIdeDatoPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un número de identificación");
				}
				else{

					if($('#txtAreaDescripcionDatoPruebaPJENS').val() == ""){
						alertDinamico("Ingrese una descripción");
					}
					else{
						validacion=true;
						//paramDatoPrueba += 'audienciaId=' + audienciaId;
						paramDatoPrueba += '&tipoDatoPrueba=' + "documento";
						paramDatoPrueba += '&nombreDato=' + $('#txtNombreDatoPruebaPJENS').val();
						paramDatoPrueba += '&numIdDato=' + $('#txtNumeroIdeDatoPruebaPJENS').val();
						paramDatoPrueba += '&rccDato=' + $('#txtRccDatoPruebaPJENS').val();
						paramDatoPrueba += '&descDato=' + $('#txtAreaDescripcionDatoPruebaPJENS').val();
						paramDatoPrueba += '&numeroExpediente=' + numeroCausa;
						paramDatoPrueba += '&datoPruebaId=' + datoPruebaId;
					}
				}
			}
		}

		if(seleccion == "2"){

			if($("#cbxTipoObjetoDatoPruebaPJENS option:selected").val() == "0" ){
				alertDinamico("Seleccione un tipo de objeto");
			}
			else{
				if($('#txtNombreDatoPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un nombre");
				}
				else{
					if($('#txtNumeroIdeDatoPruebaPJENS').val() == ""){
						alertDinamico("Ingrese un número de identificación");
					}
					else{

						if($('#txtAreaDescripcionDatoPruebaPJENS').val() == ""){
							alertDinamico("Ingrese una descripción");
						}
						else{
							validacion=true;
							//paramDatoPrueba += 'audienciaId=' + audienciaId;
							paramDatoPrueba += '&tipoDatoPrueba=' + "objeto";
							paramDatoPrueba += '&tipoObjetoDatoPrueba=' + $("#cbxTipoObjetoDatoPruebaPJENS option:selected").index();
							paramDatoPrueba += '&nombreDato=' + $('#txtNombreDatoPruebaPJENS').val();
							paramDatoPrueba += '&numIdDato=' + $('#txtNumeroIdeDatoPruebaPJENS').val();
							paramDatoPrueba += '&rccDato=' + $('#txtRccDatoPruebaPJENS').val();
							paramDatoPrueba += '&descDato=' + $('#txtAreaDescripcionDatoPruebaPJENS').val();
							paramDatoPrueba += '&numeroExpediente=' + numeroCausa;
							paramDatoPrueba += '&datoPruebaId=' + datoPruebaId;
						}
					}
				}
			}
		}

		if(validacion==true){
			$.ajax({
	    		type: 'POST',
	    		url: '<%= request.getContextPath()%>/agregarDatoPrueba.do',
	    		data:paramDatoPrueba,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			alertDinamico("Dato de prueba registrado correctamente");
	    			$("#divAgregarDatoDePrueba").dialog("close");
	    			limpiarYSalirDatoDePrueba();
	    			cargaGridDatosPrueba();
	    		}
			});
		}
	}


	/*
	*Funcion que controla el mostrar los campos dependiendo de la seleccion
	*del usuario
	*/
	function controlDatoDePrueba(){

		seleccioneTipoDatoPrueba();
		limpiaTipoDatoDePrueba();

		var seleccionTipoDato = $("#tipoDatoPruebaPJENS option:selected").val();

		if(seleccionTipoDato == "0"){
			seleccioneTipoDatoPrueba();
		}
		if(seleccionTipoDato == "1"){

			//Documentos
			seleccioneTipoDatoPrueba();
			$('#etiNombreDatoPruebaPJENS').show();
			$('#divTxtNombreDatoPruebaPJENS').show();
			$('#etiNumIdeDatoPruebaPJENS').show();
			$('#divTxtNumeroIdeDatoPruebaPJENS').show();
			$('#etiRccDatoPruebaPJENS').show();
			$('#divTxtRccDatoPruebaPJENS').show();
			$('#etiDescripcionPJENS').show();
			$('#divTxtAreaDescripcionDatoPruebaPJENS').show();
		}
		if(seleccionTipoDato == "2"){

			//Objetos
			seleccioneTipoDatoPrueba();
			consultarCatalogoDeObjetosDatoDePrueba();
			$('#etiTipoObjetoDatoPruebaPJENS').show();
			$('#divCbxTipoObjetoDatoPruebaPJENS').show();
			$('#etiNombreDatoPruebaPJENS').show();
			$('#divTxtNombreDatoPruebaPJENS').show();
			$('#etiNumIdeDatoPruebaPJENS').show();
			$('#divTxtNumeroIdeDatoPruebaPJENS').show();
			$('#etiRccDatoPruebaPJENS').show();
			$('#divTxtRccDatoPruebaPJENS').show();
			$('#etiDescripcionPJENS').show();
			$('#divTxtAreaDescripcionDatoPruebaPJENS').show();

		}
	}

	/*
	*Oculta todos los div de todos los objetos
	*/
	function seleccioneTipoDatoPrueba(){

		$('#etiTipoObjetoDatoPruebaPJENS').hide();
		$('#divCbxTipoObjetoDatoPruebaPJENS').hide();

		$('#etiNombreDatoPruebaPJENS').hide();
		$('#divTxtNombreDatoPruebaPJENS').hide();

		$('#etiNumIdeDatoPruebaPJENS').hide();
		$('#divTxtNumeroIdeDatoPruebaPJENS').hide();

		$('#etiRccDatoPruebaPJENS').hide();
		$('#divTxtRccDatoPruebaPJENS').hide();

		$('#etiDescripcionPJENS').hide();
		$('#divTxtAreaDescripcionDatoPruebaPJENS').hide();
	}

	/*
	*Funcion que carga los combos de las de objetos
	*/
	function consultarCatalogoDeObjetosDatoDePrueba() {
     $.ajax({
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/consultarTiposObjeto.do',
    	  data: '',
    	  async: false,
    	  dataType: 'xml',
    	  success: function(xml){
	    	  $('#cbxTipoObjetoDatoPruebaPJENS').empty();
	    	  $('#cbxTipoObjetoDatoPruebaPJENS').append('<option value="0">- Seleccione -</option>');
	    	  $(xml).find('catTipoObjetos').each(function(){
				$('#cbxTipoObjetoDatoPruebaPJENS').append('<option value="' + $(this).find('valor').text() + '">' + $(this).find('valor').text() + '</option>');
				});
    	  }
    	});
	}


	/*
	*Limpia todos los campos de todos los objetos
	*/
	function limpiaTipoDatoDePrueba(){

		$('#txtAreaDescripcionDatoPruebaPJENS').val("");
		$('#txtRccDatoPruebaPJENS').val("");
		$('#txtNumeroIdeDatoPruebaPJENS').val("");
		$('#txtNombreDatoPruebaPJENS').val("");
		$('#cbxTipoObjetoDatoPruebaPJENS').attr('selectedIndex', 0);
	}

	/*
	*Funcion que limpia todos los campos al salir de la ventana
	*y setea el valor seleccione por default
	*/
	function limpiarYSalirDatoDePrueba(){
		limpiaTipoDatoDePrueba();
		$('#tipoDatoPruebaPJENS').attr('selectedIndex',0);

	}


	/*
	*Funcion que despliega la ventana para agregar un Medio de prueba
	*/
	function agregarMedioDePrueba(){

		seleccioneTipoMedioPrueba();
		$('#tipoMediaPruebaPJENS').attr('selectedIndex', 0);
		controlMedioDePrueba();
		$("#divAgregarMedioDePrueba").dialog("open");
		$("#divAgregarMedioDePrueba").dialog({ autoOpen: true, modal: true,
		title: 'Medio de Prueba',
		dialogClass: 'alert', position: [500,50],
		width: 495, height: 400, maxWidth: 495,
		buttons:{
			"Agregar":function() {
				guardarMedioDePrueba();
				//limpiaTipoMediaPrueba();
			   	//$(this).dialog("close");
			},
			"Cancelar":function() {

				customConfirm("¿Realmente desea salir del registro del medio de prueba?", "", cancelarAgregarMedioDePrueba);
			}
		}
		});
	}

	function cancelarAgregarMedioDePrueba(){
		limpiarYSalirMedioPrueba();
  		$("#divAgregarMedioDePrueba").dialog("close");
	}



	/*
	*Funciones para agregar medios de prueba dependiendo de la seleccion
	*/
	function guardarMedioDePrueba(){

		var seleccion = $("#tipoMediaPruebaPJENS option:selected").val();
		var paramMedioPrueba="";

		if(seleccion == "0"){

			alertDinamico("Seleccione un tipo de medio de prueba");
		}
		//Validacion de documentos
		if(seleccion == "1"){
			if($("#cbxSubTipoDocumentoMedioPruebaPJENS option:selected").val() == "0"){
				alertDinamico("Ingrese un subtipo de documento");
			}
			else{
				if($('#txtNombreDocumentoMedioPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un nombre");
				}
				else{
					if($('#txtNumeroIdeDocumentoMedioPruebaPJENS').val() == ""){
						alertDinamico("Ingrese un número de identificación");
					}
					else{
						if($('#archivoPorSubir').val() == ""){
							alertDinamico("Adjunte un archivo");
						}
						else{
							if($('#txtAreaDescripcionDocMedioPruebaPJENS').val() == ""){
								alertDinamico("Ingrese una descripción");
							}
							else{

								guardarMedioPruebaConDocDig();
								alertDinamico("El medio de prueba ha sido guardado \n y relacionado al dato");
								$("#divAgregarMedioDePrueba").dialog("close");
								$("#divRelacionarMediosDePruebaPJENS").dialog("close");
								cargaGridMediosDePrueba();
							}
					}
				}
			}
		}
		}

		if(seleccion == "2"){
			$("#divAgregarMedioDePrueba").dialog("close");
			creaNuevoIndividuo(<%=Calidades.TESTIGO.getValorId()%>);
		}
	}

	//funcion que guarda las solicitudes de audiencia, de transcripcion de audiencia y  de audio/video
	function guardarMedioPruebaConDocDig(){


			forma = document.medioPruebaForm;

			forma.datoPruebaId.value= jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
			forma.audienciaId.value = null;
			forma.tipoMedioPrueba.value = "documento";
			forma.subTipoMedioPrueba.value = $("#cbxSubTipoDocumentoMedioPruebaPJENS option:selected").index();
			forma.nombreDoc.value = $('#txtNombreDocumentoMedioPruebaPJENS').val();
			forma.numIdDoc.value = $('#txtNumeroIdeDocumentoMedioPruebaPJENS').val();
			forma.refUbicacionFisica.value = $('#txtRefUbicacionFisicaMedioPruebaPJENS').val();
			forma.descDocumento.value = $('#txtAreaDescripcionDocMedioPruebaPJENS').val();
			forma.numeroExpediente.value = numeroCausa;
			forma.numExpedienteId.value = numExpedienteId;
			forma.flujoMedCautelar.value = flujoMedCautelar;
			forma.pestanaMedidaCautelar.value = pestanaMedidaCautelar;
			forma.submit();
	}

	//Variable para controlar el id de las ventanas
	var idWindowIngresarIndividuoMedioPrueba=1;

	/*
	*Crea una nueva ventana para ingresar individuos
	*/
	function creaNuevoIndividuo(calidad) {

		$("#divRelacionarMediosDePruebaPJENS").dialog("close");
		var stringCalidad = "";
		var datoPrueba;

		var idDatoPrueba = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
		if(idDatoPrueba){

			datoPrueba = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',idDatoPrueba);
		}

		switch(parseInt(calidad)){

			case <%=Calidades.TESTIGO.getValorId()%>:
				stringCalidad="Testigo";
			break;

			case <%=Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()%>:
				stringCalidad="Probable Responsable";
			break;

			case 3:
				stringCalidad="Perito";
			break;

			case 4:
				stringCalidad="Policía ministerial";
			break;

			default: alertDinamico("Error, calidad no encontrada");
		}

		idWindowIngresarIndividuoMedioPrueba++;

		var idWindow = "iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba;

		$.newWindow({id:"iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar: "+stringCalidad+" - Relacionado a: "+datoPrueba.nombreDato, type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba,'<iframe src="<%= request.getContextPath() %>/IngresarTestigoPJENS.do?numeroExpediente='+numeroCausa +'&calidad='+calidad+'&idDatoPrueba='+idDatoPrueba+'&idWindow='+idWindow+'" width="1050" height="600" />');
	    $("#iframewindowIngresarIndividuo"+idWindowIngresarIndividuoMedioPrueba+".window-closeButton").fadeIn();
	    //$("#divRelacionarMediosDePruebaPJENS").dialog("open");
	}

	/*
	*Funcion que controla el mostrar los campos dependiendo de la seleccion
	*del usuario
	*/
	function controlMedioDePrueba(){

		var seleccionTipoMedio = $("#tipoMediaPruebaPJENS option:selected").val();

		limpiaTipoMediaPrueba();

		if(seleccionTipoMedio == "0"){
			seleccioneTipoMedioPrueba();
		}
		if(seleccionTipoMedio == "1"){
			seleccioneTipoMedioPrueba();
			//Documentos
			$('#etiSubTipoDocumentoMedioPruebaPJENS').show();
			$('#etiNombreDocumentoMedioPruebaPJENS').show();
			$('#etiNumeroIdeDocumentoMedioPruebaPJENS').show();
			$('#etiAdjuntarDocumentoMedioPruebaPJENS').show();
			$('#etiRefUbicacionDocumentoMedioPruebaPJENS').show();
			$('#etiDescripcionDocMedioPruebaPJENS').show();
			$('#divSubTipoDocumentoMedioPruebaPJENS').show();
			$('#divTxtNombreDocumentoMedioPruebaPJENS').show();
			$('#divNumeroIdeDocumentoMedioPruebaPJENS').show();
			$('#divTxtAdjuntarDocumentoMedioPruebaPJENS').show();
			$('#divTxtRefUbicacionFisicaMedioPruebaPJENS').show();
			$('#divTxtAreaDescripcionDocMedioPruebaPJENS').show();
		}
		if(seleccionTipoMedio == "2"){
			seleccioneTipoMedioPrueba();
			//Persona
			//$('#etiCalidadPersonaMedioPruebaPJENS').show();
			//$('#divCbxCalidadPersonaMedioPruebaPJENS').show();
		}
	}


	/*
	*Oculta todos los div de todos los objetos
	*/
	function seleccioneTipoMedioPrueba(){
		//Documentos
		$('#etiSubTipoDocumentoMedioPruebaPJENS').hide();
		$('#etiNombreDocumentoMedioPruebaPJENS').hide();
		$('#etiNumeroIdeDocumentoMedioPruebaPJENS').hide();
		$('#etiAdjuntarDocumentoMedioPruebaPJENS').hide();
		$('#etiRefUbicacionDocumentoMedioPruebaPJENS').hide();
		$('#etiDescripcionDocMedioPruebaPJENS').hide();
		$('#divSubTipoDocumentoMedioPruebaPJENS').hide();
		$('#divTxtNombreDocumentoMedioPruebaPJENS').hide();
		$('#divNumeroIdeDocumentoMedioPruebaPJENS').hide();
		$('#divTxtAdjuntarDocumentoMedioPruebaPJENS').hide();
		$('#divTxtRefUbicacionFisicaMedioPruebaPJENS').hide();
		$('#divTxtAreaDescripcionDocMedioPruebaPJENS').hide();
		//Persona
		//$('#etiCalidadPersonaMedioPruebaPJENS').hide();
		//$('#divCbxCalidadPersonaMedioPruebaPJENS').hide();
	}


	/*
	*Limpia todos los campos de todos los objetos
	*/
	function limpiaTipoMediaPrueba(){

		//DOCUMENTOS
		$('#cbxSubTipoDocumentoMedioPruebaPJENS').attr('selectedIndex', 0);
		$('#txtNombreDocumentoMedioPruebaPJENS').val("");
		$('#txtNumeroIdeDocumentoMedioPruebaPJENS').val("");
		//resetea la forma
		document.medioPruebaForm.reset();
		$('#txtRefUbicacionFisicaMedioPruebaPJENS').val("");
		$('#txtAreaDescripcionDocMedioPruebaPJENS').val("");

		//PERSONA
		$('#cbxCalidadPersonaMedioPruebaPJENS').attr('selectedIndex', 0);


	}

	/*
	*Funcion que limpia todos los campos al salir de la ventana
	*y setea el valor seleccione por default
	*/
	function limpiarYSalirMedioPrueba(){
		limpiaTipoMediaPrueba();
		$('#tipoMediaPruebaPJENS').attr('selectedIndex',0);

	}

	//Funcion que carga los combos de las de objetos
	function consultarCatalogoDeObjetos() {
     $.ajax({
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/consultarTiposObjeto.do',
    	  data: '',
    	  async: false,
    	  dataType: 'xml',
    	  success: function(xml){
	    	  $('#cbxTipoObjetoMedioPruebaPJENS').empty();
	    	  $('#cbxTipoObjetoMedioPruebaPJENS').append('<option value="0">- Seleccione -</option>');
	    	  $(xml).find('catTipoObjetos').each(function(){
				$('#cbxTipoObjetoMedioPruebaPJENS').append('<option value="' + $(this).find('valor').text() + '">' + $(this).find('valor').text() + '</option>');
				});
    	  }
    	});
	}
	
/*************************************************************FUNCIONALIDAD PARA LA CEJA ADMINISTRAR ACTUACIONES DE AUDIENCIA*************************************/
	
	/*
  	* Funcion para llamar la funcion de habilitar los elementos de la pantalla
  	*/
  	function onSelectChangeTipo() {
    	var selected = $("#finalAudienciaOpcion option:selected");		
  		enableControls(selected.val());
  	}
	
	/*
	*Funcion que habilita o deshabilita los elementos de la pagina dependiento del tipo 
	* de busqueda
	*/
	function enableControls(tipo){
 		
		$("#divGernerarActa").hide();
		$("#divSolicitarCopia").hide();
		$("#divSolicitarTranscripcion").hide();
		$("#divSolicitarTraslado").hide();
		
		numExp = $("#numCausaDetalle").val();
		
  		if (parseInt(tipo) == 1){


  			$("#divGernerarActa").show();
  			
  				$.newWindow({id:"iframewindowGenerarActaAudiencia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Acta de Audiencia", type:"iframe"});
  			    $.updateWindowContent("iframewindowGenerarActaAudiencia",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?numeroUnicoExpediente='+numExp+'&formaId=<%=mx.gob.segob.nsjp.comun.enums.forma.Formas.AUDIENCIA.getValorId() %>&esconderArbol=1&idAudiencia='+idAudiencia+'" width="1140" height="400" />');
  				  			
  		}
	  		else{if (parseInt(tipo) == 2){


				$("#divSolicitarCopia").show();
				registraSolicitud(<%= TiposSolicitudes.AUDIO_VIDEO.getValorId() %>);	
	  		}
				else{if (parseInt(tipo) == 3){


						$("#divSolicitarTranscripcion").show();
						registraSolicitud(<%= TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId() %>);
					}
			
				   	else{if (parseInt(tipo) == 4){


				    	$("#divSolicitarTraslado").show();
							   // $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId=13&esconderArbol=1&idAudiencia='+idEvento+'" width="1140" height="400" />');
				  		}else{
				  			if(parseInt(tipo) == 5){
				  		        $.newWindow({id:"iframewindowRegistrarAmparo", statusBar: true, posx:20,posy:20,width:450,height:500,title:"Registrar Amparo", type:"iframe"});
				                $.updateWindowContent("iframewindowRegistrarAmparo",
				                		'<iframe src="<%= request.getContextPath() %>/registrarAmparo.jsp?idNumeroExpediente=' + idNumeroExpediente + '&idExpedienteSoli='+ idExpediente + '&numeroGeneralCaso=' +numeroGeneralCaso +'"    width="450" height="500" />');
				  			}
				  		}  							
				  	}
			  	}
		  	}
	}
	
	/*se le avisa cuando ya esta finalizada la audiencia/*Enable IT ByYolo*/
	function validaAudiencia(){
		consultaEstatusAudiencia(idEvento);
		if(estatusAudiencia!=<%=EstatusAudiencia.FINALIZADA.getValorId()%> && 
				estatusAudiencia!=<%=EstatusAudiencia.CANCELADA.getValorId()%>){
			finalizarAudiencia();
		}else{
			alertDinamico('La Audiencia esta en estatus finalizada.');
		}
	}
	
	function consultaEstatusAudiencia(idEvento){

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/detalleAudienciasDelDiaPJENS.do',
			data: 'idEvento='+ idEvento,
			async: false,
			dataType: 'xml',
			success: function(xml){
    					estatusAudiencia=$(xml).find('estatusAudiencia').find('idCampo').first().text();
					
    			}
		});
	}
	
	/**
	*Funcion para guardar la programacion de la audiencia y finalizar la audiencia
	*/
	function finalizarAudiencia(){
		
		var parametros = 'idAudiencia='+idAudiencia;
		
        $.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/finalizarAudienciaPJENS.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(xml){
				
				if($(xml).find('mensaje').text() == "exito"){
					alertDinamico("Finalización de audiencia completada");
				}else{
					alertDinamico("No se puede finalizar la audiencia si no se ha generado el acta de la audiencia primero");
				}
			}
		});
	}

	 function documentos(){
		 jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
					{url:'<%= request.getContextPath()%>/consultarDocumentos.do?numidExpedienteDoc='+numeroCausa+'&visorDoc='+visorDoc, 
					datatype: "xml" ,
					loadComplete: function(){
						rows = jQuery("#gridDetalleFrmPrincipal").jqGrid('getGridParam','records')
						if (rows<=0)
							customAlert("No hay documentos asociados para la audiencia");
					},
					success: function(xml){
						alertDinamico('<%= request.getSession().getAttribute("totalRegistrosDocumentos")%>');
					 } 
					});
				 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}


	//////////////////////FUNCIONALIDAD PARA LA SUB CEJA MEDIOS DE PRUEBA

	var banderaMediosPrueba = true;
	/*
	*Funcion que carga el grid de medios de prueba
	*/
	function cargaGridMediosDePrueba(){

		if(banderaMediosPrueba == true){

			jQuery("#gridMediosDePruebaPJENS").jqGrid({

				url:'<%=request.getContextPath()%>/consultarMediosPruebaAsociadosAlExpediente.do?idDatoPrueba=null&numeroExpediente='+numeroCausa+'',
				datatype: "xml",
				colNames:['Nombre del Medio','Número de Identificación'],
				colModel:[
				           	{name:'nombreMedio',index:'nombreMedio', width:350, align:'center'},
				           	{name:'numIdentificacion',index:'numIdentificacion', width:350, align:'center'},
						],
				pager: jQuery('#pagerGridMediosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				//height:250,
				autoheight:true,
				rowList:[10,20,30],
				sortname: 'nombreMedio',
				viewrecords: true,
				sortorder: "desc",
				caption:"Resumen de medios de prueba",
				ondblClickRow: function(rowid) {
					consultarMedioDePrueba(rowid);
				}
			})

			banderaMediosPrueba = false;
		}
		else{
			jQuery("#gridMediosDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarMediosPruebaAsociadosAlExpediente.do?idDatoPrueba=null&numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridMediosDePruebaPJENS").trigger("reloadGrid");
		}
	}


	//variable usada para asignarle id a la ventana de consultar datos de prueba
	var consultarMedioDePruebaWindow = 1;

	/*
	*Funcion para consultar datos de prueba vs medios de prueba
	*/
	function consultarMedioDePrueba(idMedioPrueba){

		consultarMedioDePruebaWindow++;
		$.newWindow({id:"iframewindowConsultarMedioDePrueba"+consultarMedioDePruebaWindow, statusBar: true, posx:180,posy:60,width:1140,height:420,title:"Consultar Medio de Prueba", type:"iframe"});
    	$.updateWindowContent("iframewindowConsultarMedioDePrueba"+consultarMedioDePruebaWindow,'<iframe src="<%= request.getContextPath() %>/consultarDetalleMedioPruebaPJENS.do?idMedioPrueba='+idMedioPrueba+'" width="1140" height="420" />');
	}

	//////////////////////FUNCIONALIDAD PARA LA SUB CEJA DE PRUEBA


	var banderaPrueba = true;
	/*
	*Funcion que carga el grid de medios de prueba
	*/
	function cargaGridPrueba(){

		if(banderaPrueba == true){

			jQuery("#gridPruebaPJENS").jqGrid({
				url:'<%=request.getContextPath()%>/consultarPruebasPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'',
				datatype: "xml",
				colNames:['Prueba Aceptada','Número de Identificación','Reg. de Cadena de Custodia', 'No. de Imputados Asociados', 'Imputados'],
				colModel:[
				           	{name:'nombrePrueba',index:'nombrePrueba', width:200, align:'center'},
				           	{name:'numIdentificacion',index:'numIdentificacion', width:180, align:'center'},
				        	{name:'rcc',index:'rcc', width:200, align:'center'},
				           	{name:'noProbRespAsociados',index:'numIdentificacion', width:100, align:'center'},
				        	{name:'probablesResponsables',index:'rcc', width:200, align:'center'}
						],
				pager: jQuery('#pagerGridPruebaPJENS'),
				rowNum:10,
				autoWidth:true,
				width:'auto',
				shrinkToFit:true,
				altRows : true,
				height:250,
				rowList:[10,20,30],
				sortname: 'nombreMedio',
				viewrecords: true,
				sortorder: "desc",
				caption:"Resumen de medios de prueba",
				toolbar: [true,"top"]
			}).navGrid('#pagerGridPruebaPJENS',{edit:false,add:false,del:false});

			$("#gview_gridPruebaPJENS .ui-jqgrid-bdiv").css('height', '200px');

			banderaPrueba = false;

			//Boton para asociar las pruebas a involucrados
			$("#t_gridPruebaPJENS").append("<input type='button' id='btnAsociarPrueba' class='btn_Generico' value='Asociar' style='height:20px;width:60px;font-size:-3'/>");

			//Funcionalidad para al boton asociar
			$("#btnAsociarPrueba","#t_gridPruebaPJENS").click(function(){

				var rowid = jQuery("#gridPruebaPJENS").jqGrid('getGridParam','selrow');

				if(rowid){

					asociarDatoPrueba(rowid);
				}
				else{
					alertDinamico("Seleccione una prueba a asociar");
				}

			});
		}
		else{
			jQuery("#gridPruebaPJENS").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarPruebasPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridPruebaPJENS").trigger("reloadGrid");
		}
	}


	function ismaxlength(obj,size){
		if (obj.value.length>size)
			obj.value=obj.value.substring(0,size);
	}



	/*************************************************************************
		ENable IT agregando funcionalidad para adjuntar documentos al expediente
		1 Si desea adjuntar documeno
		2 Si desea generar un documento
	**************************************************************************/
	 function adjuntardoc( valor ){
		//alert("Valor :: "+valor);
		if (valor == '1' ){
			var extensionesPermitidas = ".pdf";

			if(numExpedienteId != "undefined" && numExpedienteId != "null" && numExpedienteId != ""){
				$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:70,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
				$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idNumeroExpediente='+numExpedienteId+'" width="450" height="200" />');
			}else{
				alert("No se obtuvo el numero de expediente");
			}
		} else if (valor == '2'){

			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:50,posy:50,width:550,height:500,title:"Generar documento", type:"iframe"});
			$.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="' + contextoPagina + '/generarDocumentoSinCaso.do?&numeroUnicoExpediente='+numeroCausa+'&actividadId=3555&cambiarEstatus=false&ocultarGuardadoParcial=true&formaId=4&esconderArbol=true" width="550" height="500" />');

		}

 	}

</script>
</head>
<body>
	<div id="tabsPrincipal">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Detalle</a></li>
			<!-- <li><a href="#tabsconsultaprincipal-2" >Documentos</a></li> -->
			<li class="tabTabsDocs">
				<a href="#tabs-12" onclick="documentos()">Documentos</a>
			</li>
			<li id="finalAudienciaPes">
				<a href="#tabsDetalleAudiencias-7">Actuaciones</a>
			</li>
			<li id="mandamientosJudiciales"><a href="#tabsconsultaprincipal-3">Medidas Cautelares</a></li>
			<li id="pruebas"><a href="#tabsconsultaprincipal-4">Pruebas</a></li>
		</ul>

		<div id="tabsconsultaprincipal-1">
		<br>
			<center>
				<fieldset style="width:90%" >
				  <legend>Detalle de la causa</legend>
				  <table width="90%" border="0">
				    <tr>
				      <td>N&uacute;mero de Causa:</td>
				      <td><input type="text" name="numCausaDetalle" id="numCausaDetalle" disabled="disabled" size="30"/></td>
				      <td>N&uacute;mero de Caso:</td>
				      <td><input type="text" name="numCasoDetalle" id="numCasoDetalle" disabled="disabled" size="30"/></td>
				    </tr>
				    <tr>
				      <td>Fecha de Creaci&oacute;n:</td>
				      <td><input type="text" name="fCreacionDetalle" id="fCreacionDetalle"  disabled="disabled" size="30"/></td>
				      <td>Hora de Creaci&oacute;n:</td>
				      <td><input type="text" name="horaCreacionDetalle" id="horaCreacionDetalle"  disabled="disabled" size="30"/></td>
				    </tr>
				    <tr>
				      <td>Estatus del Expediente:</td>
				      <td><input type="text" name="txtEstatusExpediente" id="txtEstatusExpediente"  disabled="disabled" size="30"/></td>
				      <td>&nbsp;</td>
				      <td>&nbsp;</td>
				    </tr>
				     <tr>
				      <td colspan="4">&nbsp;</td>
				    </tr>
				    <tr>
				      <td>Imputados:</td>
				      <td><select name="select" id="imputadoDetalle" multiple="multiple" disabled="disabled" size="4" style="width:210px">
				      </select></td>
				      <td>Delitos:</td>
				      <td><select name="select2" id="delitosDetalle" multiple="multiple" disabled="disabled" size="4" style="width:210px">
				      </select></td>
				    </tr>
				  </table>
	           </fieldset>
			</center>
		</div>
		
		<div id="tabs-12" class="tabTabsDocs">
			<br>
				<table id="gridDetalleFrmPrincipal"></table>
				<div id="pager1Documentos"></div>
				<form name="frmDoc3" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
				</form>
				<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
					<input type="hidden" name="formaId" />
					<input type="hidden" name="numeroUnicoExpediente" />
				</form>
				<table id="tableDocs">
					<tr>
						<td>Total de documentos:</td>
						<td id="idTotalDocumentos">&nbsp;</td>
					</tr>
				</table>
		</div>
		
		
	<!--	Comienza Actuaciones-->
	<div id="tabsDetalleAudiencias-7">
	
		<table width="720" cellspacing="0" cellpadding="0">
			<tr>
				<td width="31">&nbsp;</td>
				<td width="314">&nbsp;</td>
				<td>&nbsp;</td>
				<td width="32">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">
					<strong>Seleccionar la opción deseada:</strong>
				</td>
				<td>
					<select id="cbxAccionesTab">
						<option value="-1">-Seleccione-</option>
					</select>
				</td>
				<td>
					<input type="button" class="btn_Generico" value="Finalizar Audiencia" onclick="validaAudiencia();">
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
 			<tr>
    			<td>&nbsp;</td>
				<td colspan="2">
			    	<div id="divGernerarActa" style="display: none;">
			    		<table>
			    			<tr>
			    				<td></td>

			    			</tr>
			    		</table>
			    	</div>
			    	<div id="divSolicitarCopia" style="display: none;">
			    		<table>
			    			<tr>
								<td></td>

			    			</tr>
			    		</table>
			    	</div>
			    	<div id="divSolicitarTranscripcion" style="display: none;">
			    		<table>
			    			<tr>
			    				<td>solicitar Transcripcion</td>
			    			</tr>
			    		</table>
			    	</div>
			    	<div id="divSolicitarTraslado" style="display: none;">
			    		<table>
			    			<tr>
			    				<td>
			    					<table width="720" border="0" align="center" cellpadding="0" cellspacing="5">
										<tr>
											<td colspan="4" align="right">
												<table id="gridTrasladoPJA"></table>
												<div  id="pager2"></div>
											</td>
										</tr>
										<tr>
											<td colspan="4" align="right">&nbsp;</td>
										</tr>
										<tr>
											<td colspan="2" align="center">
										  		<input type="button" value="Enviar" id="trasladoImputado" class="btn_Generico"/> 
											</td>
										    <td colspan="2" align="center">
										    	<input type="button" value="Cancelar" id="trasladoImputado" class="btn_Generico"/> 
											</td>
										</tr>
										<tr>
											<td colspan="4" align="right">&nbsp;</td>
										</tr>
									</table>
									
            						<div id="poponTraslados" style="display: none;">
										<table width="745" border="0">
            								<tr>
											    <td colspan="2" align="right">Nombre de la Persona a Trasladar:</td>
											    <td colspan="2">&nbsp;&nbsp;
											    	<input name="textfield7" type="text" disabled="disabled" id="textfield7" />
											    </td>
											</tr>
											<tr align="right">
												<td width="187">Lugar de Origen:</td>
											    <td width="176">
											    	<label>
											      		<input name="textfield" type="text" disabled="disabled" id="textfield" />
											    	</label>
											    </td>
											    <td width="183">Lugar Destino:</td>
											    <td width="181">
											    	<input name="textfield2" type="text" disabled="disabled" id="textfield2" />
											    </td>
  											</tr>
											<tr align="right">
												<td>Funcionario que Autoriza:</td>
												<td>
													<input name="textfield3" type="text" disabled="disabled" id="textfield3" />
												</td>
												<td>Fecha de Presentacion:</td>
												<td>
													<input name="textfield4" type="text" disabled="disabled" id="textfield4" />
												</td>
											</tr>
											<tr align="right">
												<td>Hora de Presentacion:</td>
												<td>
													<input name="textfield5" type="text" disabled="disabled" id="textfield5" />
												</td>
												<td>Duracion de Presentacion:</td>
												<td>
													<input name="textfield6" type="text" disabled="disabled" id="textfield6" />
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</td>
    			<td>&nbsp;</td>
  			</tr>
		</table>
		
	</div>
	<!--	Termina Actuaciones-->

<!--Comentado para actuaciones
		<div id="tabsconsultaprincipal-2">
			<table width="1150"  height="530" border="0" cellspacing="0" cellpadding="0">
				<tr><td>&nbsp;</td></tr>
		    	<tr>
		        	<td width="250" align="center" valign="top">
	                	<table id="gridDocumentosDigitalesPropios"></table>
	                    <div id="pagerGridDocumentosDigitalesPropios"></div>
					</td>
		            <td width="900" align="center" valign="top">
						<iframe id='visorDocsPropiosFrame' width="900" height="500" src="" scrolling="auto" style="display: ;"></iframe>
	                </td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
                   	<td>
                  		<input type="button" align="left" value="Adjuntar Documento" id="adjuntardoc" onclick="adjuntardoc('1');" class="btn_Generico"/>
					</td>
					<td>
						<input type="button" align="left" value="Generar Documento" id="generardoc" onclick="adjuntardoc('2');" class="btn_Generico"/>
					</td>
				</tr>
			</table>
		</div> -->

		<div id="tabsconsultaprincipal-3">
			<table>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
			</table>
			<div id="divGridInvolucradosCausa">
				<table id="gridInvolucradosCausa"></table>
				<div id="paginadorInvolucradosCausa"></div>
			</div>
			<div id="divBotonNuevaMedidaCautelar">
				<input type="button" align="right" value="Agregar Medida Cautelar" id="crearMedida" onclick="agregarMedidaCautelar();" class="btn_Generico"/>
			</div>
		</div>

		<!--Comienza Tab de Pruebas-->
		<div id="tabsconsultaprincipal-4">

				<!--Comienzan sub cejas de pruebas-->
				<div id="tabschildPruebasPJENS" class="tabs-bottom">

					<ul>
						<li><a href="#tabschildPruebasPJENS-1">Datos de Prueba</a></li>
						<li><a href="#tabschildPruebasPJENS-2">Medios de Prueba</a></li>
						<li><a href="#tabschildPruebasPJENS-3">Prueba</a></li>
					</ul>


								<!--Comienza Sub Tab Datos de prueba  -->
								<div id="tabschildPruebasPJENS-1">

									<table width="1000" border="0" cellspacing="0" cellpadding="0">
									  <tr>
									    <td width="100">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="100">&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td colspan="2" rowspan="11">
											<table  id="gridDatosDePruebaPJENS" width="100%"></table>
											<div id="pagerGridDatosDePruebaPJENS"></div>
										</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									</table>

								</div>
								<!--Termina Sub Tab Datos de prueba  -->


								<!--Comienza Sub Tab Medios de prueba  -->
								<div id="tabschildPruebasPJENS-2">

									<table width="1000" border="0" cellspacing="0" cellpadding="0">
									  <tr>
									    <td width="100">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="100">&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td colspan="2" rowspan="11">
											<table  id="gridMediosDePruebaPJENS" width="100%"></table>
											<div id="pagerGridMediosDePruebaPJENS"></div>
										</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									</table>

								</div>
								<!--Termina Sub Tab Medios de prueba  -->

								<!--Comienza Sub Tab prueba  -->

								<div id="tabschildPruebasPJENS-3">

									<table width="1000" border="0" cellspacing="0" cellpadding="0">
									  <tr>
									    <td width="100">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="400">&nbsp;</td>
									    <td width="100">&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td colspan="2" rowspan="11">
											<table  id="gridPruebaPJENS" width="100%"></table>
											<div id="pagerGridPruebaPJENS"></div>
										</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									</table>

								</div>
								<!--Termina Sub Tab prueba  -->

				</div>
				<!--Terminan sub cejas de pruebas-->


		</div>
		<!--Termina Tab de Pruebas-->

	</div>

	<div id="divAgregarMedidaCautelar" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="190">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridAgregarMedidaCautelar" width="100%"></table>
				<div id="pagerGridAgregarMedidaCautelar"></div>
		    </td>
		  </tr>
		</table>
	</div>


<!---------------------------------------------------Comienzan divs para ventanas modales------------------------------------------------------------------>

	<!--Comienza div agregar dato de prueba-->
	<div  id="divAgregarDatoDePrueba" style="display: none">

		<table width="440" cellspacing="0" cellpadding="0" border="0">
	      <tr>
	        <td width="5">&nbsp;</td>
	        <td width="230" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td width="5">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	            <strong>Tipo de dato de prueba:</strong>
	        </td>
	        <td>
	            <select id="tipoDatoPruebaPJENS" style="width:200px">
	              <option value="0">-Seleccione-</option>
	              <option value="1">Documento</option>
	              <option value="2">Objeto</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	            <div id="etiTipoObjetoDatoPruebaPJENS">
	                <strong>Tipo de Objeto:</strong>
	            </div>
	        </td>
	        <td>
	            <div id="divCbxTipoObjetoDatoPruebaPJENS">
	                <select id="cbxTipoObjetoDatoPruebaPJENS" style="width:200px">
	              	</select>
	            </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNombreDatoPruebaPJENS">
	        		<strong>Nombre del dato:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divTxtNombreDatoPruebaPJENS">
	        		<input type="text" id="txtNombreDatoPruebaPJENS"  maxlength="30" size="30" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNumIdeDatoPruebaPJENS">
	            	<strong>N&uacute;mero de identificaci&oacute;n:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divTxtNumeroIdeDatoPruebaPJENS">
	        		<input type="text" id="txtNumeroIdeDatoPruebaPJENS" maxlength="20" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiRccDatoPruebaPJENS">
	            	<strong>Reg. Cadena de custodia:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divTxtRccDatoPruebaPJENS">
	        		<input type="text" id="txtRccDatoPruebaPJENS" maxlength="30" size="30" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiDescripcionPJENS">
	        		<strong>Descripción:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divTxtAreaDescripcionDatoPruebaPJENS">
	        		<textarea cols="33" rows="6" id="txtAreaDescripcionDatoPruebaPJENS" onkeyup="return ismaxlength(this,200)" style="width:215px"></textarea>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>

	</div>
	<!--Termina div agregar dato de prueba-->

	<!--Comienza div rechazar dato de prueba-->
	<div id="divRechazarDatoDePruebaPJENS" style="display: none">
		<table width="400" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="14">&nbsp;</td>
	        <td width="374">&nbsp;</td>
	        <td width="12">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td height="200" align="center" valign="middle">
	        	<textarea id="motivoRechazoDatoPrueba" cols="60" rows="12"></textarea>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	</div>
	<!--Termina div rechazar dato de prueba-->


	<!--Comienza div Relacionar Medios de Prueba-->
	<div id="divRelacionarMediosDePruebaPJENS" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridRelacionarMediosDePruebaPJENS" width="100%"></table>
				<div id="pagerGridRelacionarMediosDePruebaPJENS"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->

	<!--Comienza div Relacionar Medios de Prueba-->
	<div id="divAsociarDatoDePruebaPJENS" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridAsociarDatoDePruebaPJENS" width="100%"></table>
				<div id="pagerGridAsociarDatoDePruebaPJENS"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->

	<!--Comienza div agregar Medio de prueba-->
	<div  id="divAgregarMedioDePrueba" style="display: none">

		<table width="440" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="5">&nbsp;</td>
	        <td width="230" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td width="5">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Tipo de Medio de Prueba:</strong>
	        </td>
	        <td>
	            <select name="tipoMediaPruebaPJENS" id="tipoMediaPruebaPJENS" style="width:200px">
	              <option value="0">-Seleccione-</option>
	              <option value="1">Documento</option>
	              <option value="2">Persona</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiSubTipoDocumentoMedioPruebaPJENS">
			    	<strong>*Subtipo del documento:</strong>
		        </div>
<!--	            <div id="etiCalidadPersonaMedioPruebaPJENS">-->
<!--			    	<strong>Calidad:</strong>-->
<!--			    </div>-->
	        </td>
	        <td>
	        	<div id="divSubTipoDocumentoMedioPruebaPJENS">
	                <select id="cbxSubTipoDocumentoMedioPruebaPJENS" style="width:200px">
	                  <option value="0">-Seleccione-</option>
	                  <option value="2">Archivo de audio</option>
	                  <option value="1">Archivo de texto</option>
	                  <option value="3">Archivo de video</option>
	                  <option value="4">Imágenes/Fotografías</option>
	                </select>
	            </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNombreDocumentoMedioPruebaPJENS">
			    	<strong>*Nombre del documento:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtNombreDocumentoMedioPruebaPJENS">
			    	<input type="text" id="txtNombreDocumentoMedioPruebaPJENS" maxlength="20" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	       		<div id="etiNumeroIdeDocumentoMedioPruebaPJENS">
			    	<strong>*N&uacute;mero de identificaci&oacute;n:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divNumeroIdeDocumentoMedioPruebaPJENS">
			    	<input type="text" id="txtNumeroIdeDocumentoMedioPruebaPJENS"  maxlength="20" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiAdjuntarDocumentoMedioPruebaPJENS">
			    	<span class="au av ra rc ta" ><strong>*Adjuntar:</strong></span>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtAdjuntarDocumentoMedioPruebaPJENS">
					<div id="divAdjuntarDoc" class="au av ra rc ta">

				        	<form id="medioPruebaForm" name="medioPruebaForm"
				        	action="<%= request.getContextPath() %>/agregarMedioPrueba.do" method="post" enctype="multipart/form-data">
								<input type="file" name="archivoAdjunto" id="archivoPorSubir">
								<input type="hidden" name="datoPruebaId"/>
								<input type="hidden" name="audienciaId"/>
								<input type="hidden" name="tipoMedioPrueba"/>
								<input type="hidden" name="subTipoMedioPrueba"/>
								<input type="hidden" name="nombreDoc"/>
								<input type="hidden" name="numIdDoc"/>
								<input type="hidden" name="refUbicacionFisica"/>
								<input type="hidden" name="descDocumento"/>
								<input type="hidden" name="numeroExpediente"/>

								<input type="hidden" name="numExpedienteId"/>
								<input type="hidden" name="flujoMedCautelar"/>
								<input type="hidden" name="pestanaMedidaCautelar"/>
							</form>
					</div>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiRefUbicacionDocumentoMedioPruebaPJENS">
			    	<strong>Ref. ubicación física:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtRefUbicacionFisicaMedioPruebaPJENS">
			    	<input type="text" id="txtRefUbicacionFisicaMedioPruebaPJENS" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiDescripcionDocMedioPruebaPJENS">
			    	<strong>*Descripción:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtAreaDescripcionDocMedioPruebaPJENS">
			    	<textarea cols="31" rows="5" id="txtAreaDescripcionDocMedioPruebaPJENS"></textarea>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>

	</div>
	<!--Termina div agregar Medio de prueba Documento-->

<!--Terminan divs para ventanas modales de pruebas-->
	<form name="frmDococumentos" action="<%=request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>

	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>

	<!-- div para el alert dinamico antes de cerrar ventana -->
	<div id="dialog-AlertApplet" style="display: none">
		<table align="center">
			<tr>
	  			<td>
		    		<div align="center">Introduzca la ruta donde se alojaran los videos:</div>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td>
	    			<div align="center"><input type="text" id="dir" style="width:300px" tabindex="1" maxlength="300"/></div>
	    		</td>
	    	</tr>
     	</table>
	</div>

	<!--Comienza div Relacionar Involucrados a la audiencia siguiente-->
	<div id="divRelacionarInvolucradosPorCalidadAudienciaSig" style="display: none" align="center">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td height="250" colspan="4" align="center">
				<table  id="gridRelacionarInvolucradosPorCalidadAudienciaSig" width="90%"></table>
				<div id="pagerGridRelacionarInvolucradosPorCalidadAudienciaSig"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->


	<form id="frmRecargaVisor"
		  name="frmRecargaVisor" action="<%=request.getContextPath()%>/recargaVisor.do"
		  method="post" enctype="multipart/form-data">

		<input type="hidden" name="idAudiencia" />
		<input type="hidden" name="idVisor" />
		<input type="hidden" name="idAudienciaSiguiente" />
	</form>
</body>
</html>