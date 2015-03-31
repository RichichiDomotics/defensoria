<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>

<% 
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();

	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
 %>

<script type="text/javascript" >
	var idWindowVisorAudiencia=0;
	var tur;
	var gridAud=0;
	var idWindowNuevaDenuncia=1;
	var idWindowSolicitarEvidencia = 1;
	var selector;
	var validaFecha;
	var idWindowDetalleSolicitud=1;
	
	var numerocaso;
	var firstGridEvidenciasNuevas = true;
	var firstGridEvidenciasPendientes = true;
	var firstGridEvidenciasConcluidas = true;

	//variable que controla si se carga el grid de evaluacion de documentos por primera vez
	var firstGridEvaluarDocumentos = true;
	
	var idVentanaAvisoPosHechoDel=1;
	
	//Variable para los identificadores de las ventanas
	var idWindowDetalleNotificacion = 1;
	
	//variable para guardar el ID del area del usuario logueado
	var idAreaUserLogged='<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>';
	
	var idWindowSolicitudTranscripcion=1;

	// OJO: CHECAR PORQUE pantallaSolicitada != pantallaSolicitadaCD 
	var pantallaSolicitada   = 3;
	var	pantallaSolicitadaCD = 4;
	
	//Al consultar Evidencias con retraso el combo solo habilita los tipos de Eslabon asosciados a un amp
	var visualizaTipoEslabon = 'amp';
	var rolActivo = '<%=rolActivo%>';
		
	$(document).ready(function() {
		//Carga los mandamientos judiciales
		consultaGeneralMandamientoJudicial(1);
		
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		$("#tbarBtnAsignar").click(asigarPermisos);
						
		//Mandamos consultar los tipos de solicitud dependiendo del Area del Funcionario
		consultarTiposSolicitudPorAreaDelFuncionarioGen('tableSolsGeneradas',"0");
		consultarTiposSolicitudPorAreaDelFuncionario('tableSolsXAtender',idAreaUserLogged);

		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			url: contextoPagina + '/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=ATENDER_CANALIZACION', 
			datatype: "xml", 
			colNames:['N\u00FAmero de Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
			colModel:[ 	{name:'Detalle',index:'NumeroExpediente', width:120}, 
			           	{name:'Tipo',index:'Tipo', width:120, align:'center', hidden:true},
						{name:'Fecha',index:'Fecha', width:70}, 
						{name:'Nombre', sortable: false , width:70}, 
						{name:'Resumen',sortable: false , width:110},
						{name:'Origen',index:'Origen', width:110},
						{name:'Estatus',index:'Estatus', width:110}
					],
			pager: jQuery('#pager1'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			shrinkToFit: true,
			sortname: 'Fecha',
			viewrecords: true,
			ondblClickRow: function(id) {
				consultaDenuncia(id);
			},
			sortorder: "desc"
		}).navGrid('#pager1',{edit:false,add:false,del:false});
		
		//GRID Expedientes Compartidos
		jQuery("#gridExpCompartidos").jqGrid({ 
			url: contextoPagina + '/BusquedaExpCompartidosFuncionarioLogAMP.do', 
			datatype: "xml", 
			colNames:['Caso','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
			colModel:[ 	{name:'Detalle',index:'1', width:70}, 
						{name:'Fecha',index:'2', width:70}, 
						{name:'Nombre',index:'3', width:70}, 
						{name:'Resumen',index:'4', width:110},
						{name:'Origen',index:'5', width:110},
						{name:'Estatus',index:'6', width:110}
					],
			pager: jQuery('#pagerExpCompartidos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			shrinkToFit: true,
			sortname: '1',
			viewrecords: true,
			ondblClickRow: function(id) {
				consultaDenuncia(id);
			},
			sortorder: "desc"
		}).navGrid('#pagerExpCompartidos',{edit:false,add:false,del:false});
		//FIN GRID Expedientes Compartidos
		
		regresaGrid();
		
		//Grid de Notificaciones
		jQuery("#gridNotificacionEventos").jqGrid({ 
			url: contextoPagina + '/ConsultaNuevasNotificaciones.do', 
			datatype: "xml", 
			colNames:['Expediente','Tipo Evento', 'Evento','Fecha-Hora Solicitud','Fecha-Hora Evento'], 
			colModel:[ 	{name:'expediente',index:'expediente', width:250}, 
						{name:'tipoEvento',index:'tipoEvento', width:260}, 
						{name:'evento',index:'evento', width:260}, 
						{name:'fechaHoraSolicitud',index:'fechaHoraSolicitud', width:270},
						{name:'fechaHoraEvento',index:'fechaHoraEvento', width:270}
																	
					],
			pager: jQuery('#pagerGridNotificacionEventos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			shrinkToFit: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaAudiencias(rowid);
					}
		}).navGrid('#pagerGridNotificacionEventos',{edit:false,add:false,del:false});	
		
		//Grid de Solicitudes por atender
		jQuery("#gridSolsXAtndr").jqGrid({ 
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'], 
			colModel:[ 	{name:'caso',index:'caso', width:180,hidden:true},
			           	{name:'expediente',index:'expediente', width:180}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100},
						{name:'fechaLimite',index:'fechaLimite', width:100,hidden:true},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsXAtndr'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			shrinkToFit: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudes(rowid);
					}
		}).navGrid('#pagerGridSolsXAtndr',{edit:false,add:false,del:false});	
		
		//Grid de Solicitudes generadas
		jQuery("#gridSolsGeneradas").jqGrid({ 
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
			           	{name:'expediente',index:'expediente', width:180}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100},
						{name:'fechaLimite',index:'fechaLimite', width:100,hidden:true},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsGeneradas'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			shrinkToFit: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudesGen(rowid);
					}
		}).navGrid('#pagerGridSolsGeneradas',{edit:false,add:false,del:false});

		$("#gview_gridNotificacionEventos .ui-jqgrid-bdiv").css('height', '400px');
		$("#gview_gridSolsXAtndr .ui-jqgrid-bdiv").css('height', '400px');
		$("#gview_gridSolsGeneradas .ui-jqgrid-bdiv").css('height', '400px');
		$("#gview_gridExpCompartidos .ui-jqgrid-bdiv").css('height', '400px');
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridMedidasCautelares").hide();
		
		$('#test').weatherfeed(['MXDF0132']);
				
		restablecerPantallas();		
		
		$( "#dialog-alarm" ).dialog();
		$( "#dialog-alarmPos" ).dialog();
		$( "#dialog-alarm" ).dialog( "destroy" );
		$( "#dialog-alarmPos" ).dialog( "destroy" );
		
		if(rolActivo == '<%=Roles.AGENTEMP.getValorId()%>'){
			jQuery("#gridMandamientosJudiciales").jqGrid('setLabel', 'numeroCausa', 'N&uacute;mero de Expediente');				
		}
		
	});
	//fin funcion onready

    /*
	*Funcion para realizar la consulta del grid de visitadores
	*/
	function cargaGridVisitadores(idDepartamento,idEstatus) {
		gIdDepartamento=idDepartamento;
		gIdEstatus=idEstatus;
		
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url: contextoPagina + '/busquedaCanalizadosRestaurativaStatus.do?estatus='+idEstatus+'',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");		
	}
	
	// Esta función es invocada también desde el menú intermedio, para recargar el grid
	function regresaGrid(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url: contextoPagina + '/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=ATENDER_CANALIZACION',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		ocultaMuestraGrids("gridDetalleFrmPrincipal");
	}
    
    function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Carpeta de investigación: "+num);
	}

	
	function restablecerPantallas(){
		ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
		ajustarGridAlCentro($("#gridDetalleSolServPericiales"));
		ajustarGridAlCentro($("#gridEvidenciasNuevas"));
		ajustarGridAlCentro($("#gridEvidenciasPendientes"));
		ajustarGridAlCentro($("#gridEvidenciasConcluidas"));
		ajustarGridAlCentro($("#gridNuevoAvisoPosHechoDel"));
		ajustarGridAlCentro($("#gridAvisoAtnddPosHechoDel"));
		ajustarGridAlCentro($("#gridEvaluarDocumentos"));	
		ajustarGridAlCentro($("#gridDetalleFrmUno"));
		ajustarGridAlCentro($("#gridNotificacionEventos"));
		ajustarGridAlCentro($("#gridDetalleSolAvisosDetencion"));
		ajustarGridAlCentro($("#gridAudiencias"));
		ajustarGridAlCentro($("#gridMandamientosJudiciales"));
		ajustarGridAlCentro($("#gridMedidasCautelares"));
		ajustarGridAlCentro($("#gridEvidenciaAlmacenExpediente"));
	}

	function ocultaMuestraGridsAlertas(idGrid)
	{
		if(parseInt(idGrid)==1){
			$("#divCasos").css("display", "block");
			$("#divGridSolServPericiales").css("display", "none");						
		} 	//Agregado Grid para servicios periciales
		else if(parseInt(idGrid)==2){
			$("#divCasos").css("display", "none");
			$("#divGridSolServPericiales").css("display", "block");
			gridSolServPericiales();
		}
		
		// Agrupando funcionalidades comúnes
		// ¿Existen más casos?
		if(parseInt(idGrid)==1 || parseInt(idGrid)==2){
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").hide();
			$("#divGridEvaluarDocumentos").hide();
			$("#divGridNotificaciones").hide();
			$("#divGridSolsXAtndr").hide();
			$("#divGridSolsGeneradas").hide();
			$("#divGridMedidasCautelares").hide();
			$("#divGridMandamientosJudiciales").hide();
		}
	}

	/*
	*Funcion que oculta o muestra los grids, recibe como parametro
	*el nombre del grid que va a mostrar, y todos los demas, se 
	*ocultaran
	*/ 
	function ocultaMuestraGrids(nombreGrid){
		
		$("#divGridEvidenciasNuevas").hide();
		$("#divGridEvidenciasPendientes").hide();
		$("#divGridEvidenciasConcluidas").hide();
		$("#divDetalleEvidencia").hide();
		$("#divCasos").hide();
		$("#divGridSolServPericiales").hide();
		$("#divNuevoAvisoPosHechoDel").hide();
		$("#divAvisoAtnddPosHechoDel").hide();
		$("#divGridEvaluarDocumentos").hide();
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridAudiencias").hide();
		$("#divGridExpCompartidos").hide();
		$("#divGridDetalleSolAvisosDetencion").hide();
		$("#divGridMedidasCautelares").hide();
		$("#divGridMandamientosJudiciales").hide();		
		$("#divGridDetalleEvidencia").hide();		
		
		switch (nombreGrid){
			case "divGridAvisosDetencion":
				$("#divGridDetalleSolAvisosDetencion").show();
        		break;
			case "gridDetalleFrmPrincipal":
				$("#divCasos").show();
        		break;			
			case "gridEvidenciasNuevas":
				$("#divGridEvidenciasNuevas").show();
        	    break;            
	        case "gridEvidenciasPendientes":
    	    	$("#divGridEvidenciasPendientes").show();
        	    break;
	        case "gridEvidenciasConcluidas":
    	    	$("#divGridEvidenciasConcluidas").show();
        	    break;
	        case "gridDetalleEvidencia":
    	    	$("#divDetalleEvidencia").show();
        	    break;
	        case "gridNuevoAvisoPosHechoDel":
    	    	$("#divNuevoAvisoPosHechoDel").show();
        	    break;
	        case "gridAvisoAtnddPosHechoDel":
    	    	$("#divAvisoAtnddPosHechoDel").show();
        	    break;
	        case "gridEvaluarDocumentos":
    	    	$("#divGridEvaluarDocumentos").show();
        		break;
        	case "gridNotificaciones":
        		$("#divGridNotificaciones").show();
        		break;
        	case "gridSolsXAtndr":
        		$("#divGridSolsXAtndr").show();
        		break;
	        case "gridSolsGeneradas":
    	    	$("#divGridSolsGeneradas").show();
        		break;
	        case "gridAudiencias":
    	    	$("#divGridAudiencias").show();
        		break;
	        case "expCompartidos":
    	    	$("#divGridExpCompartidos").show();
        		break;
	        case "gridMandamientosJudiciales":
	        	$("#divGridMandamientosJudiciales").show();
    	    	break;
        	case "gridMedidasCautelares":
        		$("#divGridMedidasCautelares").show();
        		break;
        	case "gridEvidenciaAlmacenExpediente":
        		$("#divGridDetalleEvidencia").show();
        		break;
	    }		

		restablecerPantallas();
	}
	
	function gridSolServPericiales(){
				jQuery("#gridDetalleSolServPericiales").jqGrid({ 
					url: contextoPagina + '/.do', 
					datatype: "xml", 
					colNames:['Expediente',' Fecha Remitido','Denunciante', 'Delito Principal', 'Origen','Estatus'], 
					colModel:[ 	{name:'NumeroExpediente',index:'numeroExpediente', width:40},
					           	{name:'NombreSolicitante',index:'nombreSolicitante', width:40}, 
								{name:'FechaElaboracion',index:'fechaElaboracion', width:30}, 
								{name:'FechaVencimiento',index:'fechaVencimiento', width:30},
								{name:'Especialidad',index:'Especialidad', width:40},
								{name:'Especialidad',index:'Especialidad', width:40}, 
							],
					pager: jQuery('#pagerSolServPericiales'),
					rowNum:10,
					rowList:[10,20,30],
					autowidth: true,
					autoheight:true,
					height:400,
					sortname: 'numeroExpediente',
					viewrecords: true,
					sortorder: "desc"
			}).navGrid('#pagerSolServPericiales',{edit:false,add:false,del:false});
		}
	
	function detEvi(){
		  $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}


	/*
	*Funcion que carga el grid de evidencias nuevas
	*/
	function cargaGridEvidenciasNuevas(){
		if(firstGridEvidenciasNuevas == true){
			
			jQuery("#gridEvidenciasNuevas").jqGrid({ 
				url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=1&areaSolicitante=3', 
				datatype: "xml", 
				colNames:['Folio','Número de caso','Número de expediente','Nombre del solicitante','Cadena de custodia','Objetos solicitados','Inicio de préstamo','Fin de préstamo','Fecha límite'], 
				colModel:[ 	{name:'Folio',index:'folio', width:100},
				           	{name:'NumeroCaso',index:'numeroCaso', width:100},
				           	{name:'NumeroExpediente',index:'numeroExpediente', width:100},
				           	{name:'NombreSolicitante',index:'nombreSolicitante', width:100},
				           	{name:'CadenaCustodia',index:'cadenaCustodia', width:100},
				           	{name:'NumeroObjetosSolicitados',index:'numeroObjetosSolicitados', width:100},
							{name:'FechaInicioPrestamo',index:'fechaInicioPrestamo', width:100},
							{name:'FechaFinPrestamo',index:'fechaFinPrestamo', width:100},
							{name:'FechaLimite',index:'fechaLimite', width:100}
						],
				pager: jQuery('#pagerEvidenciasNuevas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					autorizaEvidencia(rowid);
				}
			}).navGrid('#pagerGridEvidenciasNuevas',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasNuevas=false;
		}
		else{
			jQuery("#gridEvidenciasNuevas").jqGrid('setGridParam', {url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=1&areaSolicitante=3',datatype: "xml" });
			$("#gridEvidenciasNuevas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasNuevas');
	}

	/*
	*Funcion que carga el grid de evidencias pendientes
	*/
	function cargaGridEvidenciasPendientes(){
		if(firstGridEvidenciasPendientes == true){
			
			jQuery("#gridEvidenciasPendientes").jqGrid({ 
				url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=2&areaSolicitante=3', 
				datatype: "xml", 
				colNames:['Folio','Número de caso','Número de expediente','Nombre del solicitante','Cadena de custodia','Objetos solicitados','Inicio de préstamo','Fin de préstamo','Fecha límite','Ultima modificación'], 
				colModel:[ 	{name:'Folio',index:'folio', width:100},
				           	{name:'NumeroCaso',index:'numeroCaso', width:100},
							{name:'NumeroExpediente',index:'numeroExpediente', width:100},
				           	{name:'NombreSolicitante',index:'nombreSolicitante', width:100},
				           	{name:'CadenaCustodia',index:'cadenaCustodia', width:100},
				           	{name:'NumeroObjetosSolicitados',index:'numeroObjetosSolicitados', width:100},
							{name:'FechaInicioPrestamo',index:'fechaInicioPrestamo', width:100},
							{name:'FechaFinPrestamo',index:'fechaFinPrestamo', width:100},
							{name:'FechaLimite',index:'fechaLimite', width:100},
				           	{name:'FechaUltimaModificacion',index:'fechaUltimaModificacion', width:100, hidden:true} 
						],
				pager: jQuery('#pagerEvidenciasPendientes'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridEvidenciasPendientes',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasPendientes=false;
		}
		else{
			jQuery("#gridEvidenciasPendientes").jqGrid('setGridParam', {url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=2&areaSolicitante=3',datatype: "xml" });
			$("#gridEvidenciasPendientes").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasPendientes');
	}
	
	/*
	*Funcion que carga el grid de evidencias concluidas
	*/
	function cargaGridEvidenciasConcluidas(){
		if(firstGridEvidenciasConcluidas == true){
			
			jQuery("#gridEvidenciasConcluidas").jqGrid({ 
				url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=3&areaSolicitante=3', 
				datatype: "xml", 
				colNames:['Folio','Número de caso','Número de expediente','Nombre del solicitante','Cadena de custodia','Objetos solicitados','Inicio de préstamo','Fin de préstamo','Fecha límite','Fecha de cierre' ], 
				colModel:[ 	{name:'Folio',index:'folio', width:100},
				           	{name:'NumeroCaso',index:'numeroCaso', width:100},
							{name:'NumeroExpediente',index:'numeroExpediente', width:100},
				           	{name:'NombreSolicitante',index:'nombreSolicitante', width:100},
				           	{name:'CadenaCustodia',index:'cadenaCustodia', width:100},
				           	{name:'NumeroObjetosSolicitados',index:'numeroObjetosSolicitados', width:100},
							{name:'FechaInicioPrestamo',index:'fechaInicioPrestamo', width:100},
							{name:'FechaFinPrestamo',index:'fechaFinPrestamo', width:100},
							{name:'FechaLimite',index:'fechaLimite', width:100},
				           	{name:'FechaCierre',index:'fechaCierre', width:100} 
						],
				pager: jQuery('#pagerEvidenciasConcluidas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridEvidenciasConcluidas',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasConcluidas=false;
		}
		else{
			jQuery("#gridEvidenciasConcluidas").jqGrid('setGridParam', {url: contextoPagina + '/solicitudEvidenciasPorEstatus.do?estatus=3&areaSolicitante=3',datatype: "xml" });
			$("#gridEvidenciasConcluidas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasConcluidas');
	}
	
	function activaNuevoAviso()
	{
		jQuery("#gridNuevoAvisoPosHechoDel").jqGrid({ 
			url: contextoPagina + '/consultarAvisosNuevosPosiblesHechosDel.do?estatus=NO_ATENDIDA', 
			datatype: "xml", 
			colNames:['Lugar de los hechos','Tipo de delito', 'Fecha y Hora envío'], 
			colModel:[ 	{name:'Lugar',index:'lugar', width:425},
			           	{name:'Tipodelito',index:'tipodelito', width:125}, 
						{name:'Fecha',index:'fecha', width:125}
					],
			pager: jQuery('#pagerGridNuevoAvisoPosHechoDel'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			onSelectRow: function(id){
				consultaAvisoPosHechoDel(id,0);
				},
			sortorder: "desc"
		}).navGrid('#pagerGridNuevoAvisoPosHechoDel',{edit:false,add:false,del:false});
		$("#gridNuevoAvisoPosHechoDel").trigger("reloadGrid"); 
		 
		$("#gview_gridNuevoAvisoPosHechoDel .ui-jqgrid-bdiv").css('height', '410px');	
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridNuevoAvisoPosHechoDel');
	}
	
	function activaAvisoAtendido()
	{
		jQuery("#gridAvisoAtnddPosHechoDel").jqGrid({ 
			url: contextoPagina + '/consultarAvisosNuevosPosiblesHechosDel.do?estatus=ATENDIDA', 
			datatype: "xml", 
			colNames:['Lugar de los hechos','Tipo de delito', 'Fecha y Hora envío'], 
			colModel:[ 		{name:'Lugar',index:'lugar', width:425},
				           	{name:'Tipodelito',index:'tipodelito', width:205}, 
							{name:'Fecha',index:'fecha', width:195}
					],
			pager: jQuery('#pagerGridAvisoAtnddPosHechoDel'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			onSelectRow: function(id){
				consultaAvisoPosHechoDel(id,1);
				},
			sortorder: "desc"
		}).navGrid('#pagerGridAvisoAtnddPosHechoDel',{edit:false,add:false,del:false});
		$("#gridAvisoAtnddPosHechoDel").trigger("reloadGrid"); 
		$("#gview_gridAvisoAtnddPosHechoDel .ui-jqgrid-bdiv").css('height', '410px');	
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridAvisoAtnddPosHechoDel');
	}

	
	/*
	*Funcion que carga el grid de evaluar documentos
	*/
	function cargaGridEvaluarDocumentos(tipoDocumento){
		tipo = tipoDocumento=='Dictamen'?<%=Formas.DICTAMEN_PERICIAL.getValorId()%>:<%=Formas.INFORME_PERICIAL.getValorId()%>;
		
		if(firstGridEvaluarDocumentos == true){
			
			jQuery("#gridEvaluarDocumentos").jqGrid({ 
				url: contextoPagina + '/consultarDocumentosRecibidos.do?formaId='+tipo, 
				datatype: "xml", 
				colNames:['Número de Expediente','Número de Caso','Nombre del Perito','Especialidad','Fecha de Recepci&oacute;n'], 
				colModel:[ 	{name:'NumeroExpediente',index:'numeroExpediente', width:200},
				           	{name:'NumeroCaso',index:'numeroCaso', width:200},
				           	{name:'NombrePerito',index:'nombrePerito', width:200},
				           	{name:'EspecialidadPerito',index:'especialidadPerito', width:200},
				           	{name:'fechaRecepcion',index:'fechaRecepcion', width:150}
						],
				pager: jQuery('#pagerGridEvaluarDocumentos'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				height:440,
				sortname: 'fechaRecepcion',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridEvaluarDocumentos',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvaluarDocumentos=false;
		}
		else{
			jQuery("#gridEvaluarDocumentos").jqGrid('setGridParam', {url: contextoPagina + '/consultarDocumentosRecibidos.do?formaId='+tipo,datatype: "xml" });
			$("#gridEvaluarDocumentos").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvaluarDocumentos');
	}
	
	function consultaAvisoPosHechoDel(idAviso,bandEstatus)
	{
		idVentanaAvisoPosHechoDel++;
		$.newWindow({id:"iframewindowAviPosHechoDel_"+idVentanaAvisoPosHechoDel, statusBar: true, posx:200,posy:50,width:850,height:450,title:"Aviso Posible Hecho Delictivo", type:"iframe"});
		var rowd;
		var retd;
		
		if(parseInt(bandEstatus)==0)
		{
			rowd = jQuery("#gridNuevoAvisoPosHechoDel").jqGrid('getGridParam','selrow');
			retd = jQuery("#gridNuevoAvisoPosHechoDel").jqGrid('getRowData',rowd);
		}
		else
		{
			rowd = jQuery("#gridAvisoAtnddPosHechoDel").jqGrid('getGridParam','selrow');
			retd = jQuery("#gridAvisoAtnddPosHechoDel").jqGrid('getRowData',rowd);
		}
		var aux=retd.Tipodelito;
		var posicion1=aux.indexOf('>',0);
		var posicion2=aux.indexOf('<',1);
		var tipoDelito=aux.substring(posicion1+1,posicion2);
		
		aux=retd.Fecha;
		posicion1=aux.indexOf('>',0);
		posicion2=aux.indexOf('<',1);
		var fecha=aux.substring(posicion1+1,posicion2);
		
		if(parseInt(bandEstatus)==0)//No atendidas
		{
			$.updateWindowContent("iframewindowAviPosHechoDel_"+idVentanaAvisoPosHechoDel,'<iframe src="<%= request.getContextPath() %>/consultaPosAvisoHechoDel.do?idAviso='+idAviso+'&estatus=NO_ATENDIDA&tipo='+tipoDelito+'&fecha='+fecha+' " width="850" height="450" />');			
		}
		else //atendidas
		{
			$.updateWindowContent("iframewindowAviPosHechoDel_"+idVentanaAvisoPosHechoDel,'<iframe src="<%= request.getContextPath() %>/consultaPosAvisoHechoDel.do?idAviso='+idAviso+'&estatus=ATENDIDA&tipo='+tipoDelito+'&fecha='+fecha+' " width="850" height="450" />');			
		}	    
	}
	
	function recargaGridAvisosNoAtendidos()
	{
		jQuery("#gridNuevoAvisoPosHechoDel").jqGrid('setGridParam', {url: contextoPagina + '/consultarAvisosNuevosPosiblesHechosDel.do?estatus=NO_ATENDIDA',datatype: "xml" });
		$("#gridNuevoAvisoPosHechoDel").trigger("reloadGrid");
	}
	function activaPrincipal() {
		$("#divCasos").css("display", "block");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");

		$("#divGridAudiencias").hide();
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridEvidenciasNuevas").hide();
		$("#divGridEvidenciasPendientes").hide();
		$("#divGridEvidenciasConcluidas").hide();
		$("#divDetalleEvidencia").hide();
		$("#divGridEvaluarDocumentos").hide();
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridMedidasCautelares").hide();
	}

	function activaUno() {
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "block");
		$("#divCasos").css("display", "none");
		jQuery("#gridDetalleFrmUno").jqGrid({ 
			datatype: "xml", 
			colNames:['Expediente','Fecha Remitido', 'Denunciante', 'Delito principal','Origen','Estatus'], 
			colModel:[ 	{name:'Caso',index:'detalle', width:125},
			           	{name:'Fecha',index:'fecha', width:125}, 
						{name:'Denunciante',index:'nombre', width:125}, 
						{name:'Fecha',index:'fecha', width:125}, 
						{name:'Denunciante',index:'nombre', width:125}, 
						{name:'Delito',index:'Resumen', width:125}
			
					],
			pager: jQuery('#pager3'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			onSelectRow: function(id){
				nuevaDenuncia(id);
				},
			sortorder: "desc"
		}).navGrid('#pager3',{edit:false,add:false,del:false});
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridMedidasCautelares").hide();
	}
	function activaDos() {
		$("#divCasos").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolServPericiales").css("display", "block");
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridMedidasCautelares").hide();
		$("#divGridAudiencias").hide();		
		$("#divGridEvidenciasNuevas").hide();
		$("#divGridEvidenciasPendientes").hide();
		$("#divGridEvidenciasConcluidas").hide();
		$("#divDetalleEvidencia").hide();
		$("#divGridEvaluarDocumentos").hide();
		$("#divGridNotificaciones").hide();
		$("#divGridSolsXAtndr").hide();
		gridSolServPericiales();
	}
	
	function consultaNotificaciones()
	{
		ocultaMuestraGrids('gridNotificaciones');
		jQuery("#gridNotificacionEventos").jqGrid('setGridParam', {url: contextoPagina + '/consultaEventosPorExpediente.do?numeroExpedienteId='+numeroExpedienteId +'',datatype: "xml" });
		  $("#gridNotificacionEventos").trigger("reloadGrid");
	}
	
	/*
	*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaAudiencias(rowID){
		idWindowDetalleNotificacion++;
		customVentana("iframewindowDetalleNotificacion"+idWindowDetalleNotificacion, "Atender Notificación", "/acarrearIdEvento.do", "?idEvento=" +rowID);	
	}
	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudes(rowID){
		idWindowDetalleSolicitud++;
		customVentana("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, "Detalle Solicitud", "/consultarDetalleSolicitudBandeja.do", "?idSolicitud=" + rowID + "&tipoUsuario=1");    	 
	}
	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		customVentana("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, "Detalle Solicitud", "/consultarDetalleSolicitudBandejaGen.do", "?idSolicitud=" +rowID + "&tipoUsuario=0");
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsXAtndr(tipoSolicitud,idArea)
	{
		jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url: contextoPagina + '/consultaSolsXAtnder.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.CERRADA.getValorId()%>',datatype: "xml" });
		$("#gridSolsXAtndr").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsXAtndr");
	}
	
	/*
	 *Funcion para consultar los tipos de solicitud y generar 
	 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
	 * param - nombre del elemento en el que se construira de manera dinamica
	 * los tipos de solicutd
	 */
	function consultarTiposSolicitudPorAreaDelFuncionario(idDivArbol,idArea)
	{
		//limpiamos el menu de los tipos de solicitud
		$("#"+idDivArbol).empty();
		//lanzamos la consulta del tipo de solicitudes
		$.ajax({
			type: 'POST',
			url:  contextoPagina + '/consultaTiposSolsXArea.do',
			data: 'idArea='+idArea,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ValorDTO').each(function(){

					var tipoSol = $(this).find("idCampo").text();
					
					//Aplica filtro de solicitides
					if(tipoSol == '<%=TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId()%>' || 
							tipoSol == '<%=TiposSolicitudes.TRABAJO_SOCIAL.getValorId()%>' || 
								tipoSol == '<%=TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>' ||
									tipoSol == '<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>'){

						var trTabla = '<tr>';
						trTabla = trTabla + '<td><span><img src="' + contextoPagina + '/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsXAtndr('+$(this).find("idCampo").text()+','+idArea+')">'+$(this).find("valor").text()+'</a></span></td>';
						trTabla = trTabla + '</tr>';
						$('#'+idDivArbol).append(trTabla);
					}
				});
			}
			
		});
	}
	
	/*
	 *Funcion para consultar los tipos de solicitud y generar 
	 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
	 * param - nombre del elemento en el que se construira de manera dinamica
	 * los tipos de solicutd
	 */
	function consultarTiposSolicitudPorAreaDelFuncionarioGen(idDivArbol,idArea)
	{
		//limpiamos el menu de los tipos de solicitud
		$("#"+idDivArbol).empty();
		//lanzamos la consulta del tipo de solicitudes
		$.ajax({
			type: 'POST',
			url:  contextoPagina + '/consultaTiposSolsXArea.do',
			data: 'idArea='+idArea,
			dataType: 'xml',
			async: false,
			success: function(xml){

				var trTabla = '<tr>';
				trTabla = trTabla + '<td><span><img src="' + contextoPagina + '/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="gridSolAvisosDetencion()">Avisos de Detenido</a></span></td>';
				trTabla = trTabla + '</tr>';
				$('#'+idDivArbol).append(trTabla);
				
				$(xml).find('ValorDTO').each(function(){
					
					var tipoSol = $(this).find("idCampo").text();
					
					//Aplica filtro de solicitides
					if(tipoSol == '<%=TiposSolicitudes.AUDIENCIA.getValorId()%>' || 
							tipoSol == '<%=TiposSolicitudes.TRABAJO_SOCIAL.getValorId()%>' || 
								tipoSol == '<%=TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>' ||
									tipoSol == '<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>'){
						
						var trTabla = '<tr>';
						trTabla = trTabla + '<td><span><img src="' + contextoPagina + '/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsGeneradas('+$(this).find("idCampo").text()+',0)">'+$(this).find("valor").text()+'</a></span></td>';
						trTabla = trTabla + '</tr>';
						$('#'+idDivArbol).append(trTabla);
					}
				});
			}
			
		});
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea)
	{
		jQuery("#gridSolsGeneradas").jqGrid('setGridParam', {url: contextoPagina + '/consultaSolsGeneradas.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.CERRADA.getValorId()%>',datatype: "xml" });
		$("#gridSolsGeneradas").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsGeneradas");
	}


	var firstAvisoDetencion = true;
	/**
	*Funcion que llena el grid de Avisos de detencion 
	*/
	function gridSolAvisosDetencion(){

		if(firstAvisoDetencion == true){

			jQuery("#gridDetalleSolAvisosDetencion").jqGrid({ 
				url: contextoPagina + '/SolicitudesNoAtendidas.do', 
				datatype: "xml", 
				colNames:['Folio','Caso','Imputado:','Delito(s)','Agencia del MP','Fecha-Hora de detención','Fecha-Hora de aviso'], 
				colModel:[ 	{name:'folio',index:'2008', width:150, align:"center"},
				           	{name:'caso',index:'2002', width:180, align:"center"},
				           	{name:'imputado',index:'2009', width:200, align:"center"},
				           	{name:'delito',index:'2004', width:200, align:"center"},
				           	{name:'mp',index:'2010', width:150, align:"center"},
				           	{name:'fechaHoraDetencion',index:'2011', width:150, align:"center"},
				           	{name:'fechaHoraAviso',index:'2012', width:150, align:"center"}
				           
				           	
						],
				pager: jQuery('#pagerDetalleSolAvisosDetencion'),
				rowNum:30,
				rowList:[10,20,30,40,50,60],
				autowidth: false,
				autoheight:true,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerDetalleSolAvisosDetencion',{edit:false,add:false,del:false});
			$("#gview_gridDetalleSolAvisosDetencion .ui-jqgrid-bdiv").css('height', '450px');

			firstAvisoDetencion = false;
		}
		else{
			$('#gridDetalleSolAvisosDetencion').trigger('reloadGrid');
		}
		
		ocultaMuestraGrids('divGridAvisosDetencion');
	}
		
	/**
	*  La variable desarrolloJAVS se utiliza para mostrar la columna Audiencia en el grid, si no se requiere mostrar esta columna,
	*  basta con eliminar las referencias en el JSP a la variable desarrolloJAVS, y eliminar de la estructura del grid, la columna "Audiencia"
	**/
	function gridAudiencias(){
		
		var fechaInicio=$('#fechaInicio').val();
		var fechaFin=$('#fechaFin').val();
		var desarrolloJAVS="TRUE";
		
		if(selector == 1){
			fechaInicio="";
			fechaFin="";
		}
		/*Se modifica la informacion a presentar sobre las audiencias*/
		if(gridAud==0){
    		jQuery("#gridAudiencias").jqGrid({ 
    			url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'', 	   		
    			datatype: "xml", 
    			colNames:['Audiencia','Número de Expediente', 'No. Caso','Número de Causa','Tipo de Audiencia','Fecha de Audiencia','Sala','Juzgado' , 'Estatus'],  
				colModel:[{name:'audiencia',	 index:'audiencia', 		width:60,hidden:true },
				          {name:'numeroExpediente',	 index:'numeroExpediente', 		width:160},
				          {name:'caso',	 index:'caso', 		width:160, hidden:true},
				          {name:'numeroCausa',	 index:'numeroCausa', 		width:100},
			          {name:'tipo',index:'tipo', 	width:230},
			          {name:'fechaHora',	 index:'fechaHora', 	    width:120},
			          {name:'sala',index:'sala',width:50},
			          {name:'juzgado' ,index:'juzgado', 		width:100} ,
			          {name:'estatus', index:'estatus', width:80}
					],			
				pager: jQuery('#pagerAudiencias'),
				rowNum:20,
				rowList:[20,50,100,200],
				autowidth: true,
				autoheight:true,
				height:400,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					
					//rowid = row.audiencia -> apuntan al mismo valor
					
					var row = jQuery("#gridAudiencias").jqGrid('getRowData',rowid);
					dblClickRowBandejaAudiencias(row);
				}
			}).navGrid('#pagerAudiencias',{edit:false,add:false,del:false});
    		
			gridAud=1;
		}
		else{
			
			jQuery("#gridAudiencias").jqGrid('setGridParam', {url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'',
					datatype: "xml",
					page:1});
			$("#gridAudiencias").trigger("reloadGrid");
			$("#gview_gridAudiencias .ui-jqgrid-bdiv").css('height', '450px');
		}
	}
	
	function dblClickRowBandejaAudiencias(row){
		audiencia= row.audiencia;
		caso=	   row.caso;
		numeroCausa=  row.numeroCausa;
		tipo=	   row.tipo;
		sala=	   row.sala;	
		juzgado =  row.juzgado;
		fecha= 	   row.fechaHora;
		noExpediente = row.numeroExpediente;
		
		idWindowVisorAudiencia++;
		$.newWindow({id:"idWindowVisorAudiencia"+ idWindowVisorAudiencia, statusBar: true, posx:150,posy:111,width:1130,height:400,title:"Atenci&oacute;n de Audiencias", type:"iframe"});
	    $.updateWindowContent("idWindowVisorAudiencia"+ idWindowVisorAudiencia,'<iframe src="<%=request.getContextPath()%>/visorDetalleAudiencia.do?audiencia='+audiencia+'&caso='+caso+'&noExpediente='+noExpediente+'&numeroCausa='+numeroCausa+'&tipo='+tipo+'&sala='+sala+'&juzgado='+juzgado+'&fecha='+fecha+'" width="1130" height="400"/>'); 
	}
	    
    function muestraGridAudiencias()
	 {
		 selector=1;
		 gridAudiencias();
		 ocultaMuestraGrids('gridAudiencias');
	 }
	
	/******************************************************    FUNCIONES PARA LAS ALARMAS      ***************************************************/
	function muestraAlerta(){
		var op="";
		var idAlerta="";

		$.ajax({
   		type: 'POST',
    		url:  contextoPagina + '/consultarAlarmas.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			$(xml).find('alertaDTO').each(function(){
    				op=$(this).find('esAplaza').text();
    				idAlerta=$(this).find('alertaId').text();
    				var nombre=$(this).find('nombre').text();
    				$("#mensajeAlarma").html(nombre);
    				
        			llamaraCambia(op,idAlerta);
				});
	   		}
   		});
	}

	function cambiarEstausAlarma(estatus,tiempo,id,unidad){
		$.ajax({
	   		type: 'POST',
	    		url:  contextoPagina + '/actualizarAlarma.do?idAlerta='+id+'&estatus='+estatus+'&tiempo='+tiempo+'&unidad='+unidad,
	    		data: '',
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			if(estatus=="posponer")
	    			{
	    				customAlert("Alarma pospuesta");
	    			}
	    			else if(estatus=="cancelar")
	    			{
	    				customAlert("Alarma cancelada");
	    			}
	    			else
	    			{
	    				customAlert("Alarma aceptada");
	    			}
	   		}
		});
	}
	
	/**	
	* Función que abre el visor de medidas cautelares 
	**/
	function mostrarVentanaInvolucradosMedida(numeroExpediente){
		idWindowVisorMedidasCautelares++;
		$.newWindow({id:"iFrameWindowVisorMedidasCautelares"+idWindowVisorMedidasCautelares, statusBar: true, posx:200,posy:50,width:970,height:480,title:"Visor de Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iFrameWindowVisorMedidasCautelares"+idWindowVisorMedidasCautelares,'<iframe src="<%=request.getContextPath()%>/visorMedidaCautelar.do?numeroCausa='+numeroExpediente+'&ocultarAdd='+true+'" width="970" height="480" />'); 
	}

	/**	
	* Función que abre el visor de mandamientos judiciales
	**/	
	function mostrarVentanaInvolucradosMandamiento(numeroExpediente){
		// Ojo -> Esta función se invoca desde el js funcionesComunMandJudYMedCautelares
		// Ya se desarrollo para encargado causa, falta replicar cambios aquí
	}
	
	function llamaraCambia(op,idAlerta){
		if(op!="false"){		
			$( "#dialog-alarm" ).dialog({
				resizable: false,
				height:150,
				width:300,
				modal: true,
				buttons: {
					"Aceptar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						cambiarEstausAlarma("aceptar","0",idAlerta,"0");
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						cambiarEstausAlarma("cancelar","0",idAlerta,"0");
					},
					"Posponer": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						nuevoPoppupAlarma(idAlerta);
						
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}else if(op=="false"){
			$( "#dialog-alarm" ).dialog({
				resizable: false,
				height:150,
				width:300,
				modal: true,
				buttons: {
					"Aceptar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						cambiarEstausAlarma("aceptar","0",idAlerta,"0");
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						cambiarEstausAlarma("cancelar","0",idAlerta,"0");
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
	}
	
	function modalFecha(){

		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		var dates =	$("#fechaInicio, #fechaFin").datepicker(
			{
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				numberOfMonths: 1,
				onSelect: function( selectedDate ) {
					var option = this.id == "fechaInicio" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );
				},
				showOn: "button",
				buttonImage:  contextoPagina + "/resources/images/date.png",
				buttonImageOnly: true			
			}
		);
		
		//abre la ventana de detalle de la persona
		$("#busquedaFecha").dialog("open");
		$("#busquedaFecha").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Buscar por Fecha', 
	  		dialogClass: 'alert',
	  		width: 380,
	  		height: 260,
	  		maxWidth: 1000,
	  		buttons:{"Aceptar":function() {
	  				
	  					validaCamposFecha($("#fechaInicio").val(), $("#fechaFin").val());
	  					
	  					if(validaFecha==true){ 
		  					selector=2;
			  				gridAudiencias();
			  				ocultaMuestraGrids('gridAudiencias');
		  					$(this).dialog("close");
				  		}
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});
				
	}
	
	
    /**
     * Permite consultar Evidencias asociadas al Almacen del cual es responsable 
     * el usuario firmado en seción. Permite consultar evidencias en base al
     * estatus de la evidencia.
     **/
		var cargaGridEvidencias=false;
	   function gridEvidenciaAlmacen(estatusEvidencia){
			//Recupera parametros
			var params;
				params="estatusEvidencia="+estatusEvidencia;
				params+="&consultarEvidenciasSinImportarAlmacen=1";
			
			//Permite recargar el grid de forma automatica
			if (cargaGridEvidencias==false){
              jQuery("#gridEvidenciaAlmacenExpediente").jqGrid({
                  url:'<%=request.getContextPath()%>/consultarEvidenciaPorAlmacenoExpediente.do?'+params,
                  datatype: "xml",
                  colNames:["Caso",'Expediente','Folio CC','# Evidencia','Descripcion','Estatus'],
                  colModel:[
                      {name:'Caso',index:'4',  viewable:false, width:135},
                      {name:'Expediente',index:'3',  sortable:false, width:110},
                      {name:'Folio',index:'2',  sortable:true, width:100},
                      {name:'Evidencia',index:'1',  sortable:true, width:60},
                      {name:'Descripcion',index:'5',  sortable:false, width:120, hidden: true},
                      {name:'estatus',index:'6',  sortable:true, width:80},
                  ],
                  pager: jQuery('#paginadorEvidenciaAlmacenExpediente'), 
                  rowNum:10,
                  rowList:[10,20,30],
                  autowidth: true,
                  autoheight:true,
                  height:400,
                  sortname: '1',
                  viewrecords: true,
                  sortorder: "desc",
                  ondblClickRow: function(rowid) {
                  	lanzaMenuGestionarAlmacen(rowid);
                  }

              }).navGrid('#paginadorEvidenciaAlmacenExpediente',{edit:false,add:false,del:false});
              	cargaGridEvidencias=true;
			 }else{					 
				 jQuery("#gridEvidenciaAlmacenExpediente").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarEvidenciaPorAlmacenoExpediente.do?'+params,datatype: "xml" });
				 $("#gridEvidenciaAlmacenExpediente").trigger("reloadGrid");
			 }
			
			//muestra este grid y oculta los demas
			ocultaMuestraGrids('gridEvidenciaAlmacenExpediente');
		}
	   
       function lanzaMenuGestionarAlmacen(idEvidencia) {
           idParaConsultaEvidencia=idEvidencia;
            opcionSeleccionadaEnElMenu = 1;
	        $.newWindow({id:"iframewindowEntradasAlmacen", statusBar: true, posx:255,posy:133,width:803,height:760,title:"Evidencia con retraso", type:"iframe"});
            $.updateWindowContent("iframewindowEntradasAlmacen",'<iframe src="<%= request.getContextPath() %>/menuGestionarAlmacen.do?opcionSeleccionadaEnElMenu='+opcionSeleccionadaEnElMenu+'&visualizaTipoEslabon='+visualizaTipoEslabon+'"    width="803" height="760" />');
       }


	function nuevoPoppupAlarma(idAlerta)
	{
		$( "#dialog-alarmPos" ).dialog({
			resizable: false,
			height:200,
			width:500,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					var unidadTiempo=$( "#cbxTiempo" ).val();
					var tiempoAplazar=$( "#idTiempotex" ).val();
					cambiarEstausAlarma("posponer",tiempoAplazar,idAlerta,unidadTiempo);
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
			
	}
	/******************************************************  FIN  FUNCIONES PARA LAS ALARMAS      ***************************************************/
	
	/**************************************   FUNCIONES PARA EL GRID DE EXPEDIENTES COMPARTIDOS ********************************************/
	/*
	*Funcion para recargar el grid de expedientes compartidos desde el arbol izquierdo
	*/
	function activaExpCompartidos()
	{
		jQuery("#gridExpCompartidos").jqGrid('setGridParam',  
				{url: contextoPagina + '/BusquedaExpCompartidosFuncionarioLogAMP.do', 
				datatype: "xml" });
			 $("#gridExpCompartidos").trigger("reloadGrid"); 
			 ocultaMuestraGrids("expCompartidos");
			$("#gridExpCompartidos").setGridWidth($("#mainContent").width() - 5, true);
	}

</script>
<!-- Div para mostrar la ventana modal para elegir la fecha de consulta para mandamientos judiciales y medidas cautelares -->
<div id="busquedaFecha" style="display: none">
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td width="153">&nbsp;</td>
			<td width="153">&nbsp;</td>
		</tr>
		<tr>
		  <td colspan="2" align="center"><strong>Fecha Inicio:</strong><input type="text" id="fechaInicio" size="20" />		  
		  </td>
	    </tr>
		<tr>
		  <td align="center">&nbsp;</td>
		  <td align="center">&nbsp;</td>
  		</tr>
		<tr>
		  <td colspan="2" align="center"><strong>Fecha Fin:&nbsp;&nbsp;</strong>&nbsp;
	      <input type="text" id="fechaFin" size="20" /></td>
  		</tr>
	</table>
</div>
<!--Termina div fechas-->

	<!--Comienza div para mostrar la ventana para ingresar el número de expediente-->	
	<div id="divBusquedaExpediente" style="display: none">
		<table width="400" cellspacing="0" cellpadding="0" height="150">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="300">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><strong>Ingrese el n&uacute;mero de expediente: </strong></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="numeroExpediente"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>

<div id="mainContent">
	
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
					 <div id="divCasos">
						<table id="gridDetalleFrmPrincipal"></table>
						<div id="pager1" style=" vertical-align: top;"></div>
					</div>
					<div id="divGridSolServPericiales" style="display: none; ">
						<table id="gridDetalleSolServPericiales"></table>
						<div id="pagerSolServPericiales"></div>
					</div>
					<div id="divGridEvidenciasNuevas" style="display: none; ">
						<table id="gridEvidenciasNuevas"></table>
						<div id="pagerGridEvidenciasNuevas"></div>
					</div>					
					<div id="divGridEvidenciasPendientes" style="display: none; ">
						<table id="gridEvidenciasPendientes"></table>
						<div id="pagerGridEvidenciasPendientes"></div>
					</div>					
					<div id="divGridEvidenciasConcluidas" style="display: none; ">
						<table id="gridEvidenciasConcluidas"></table>
						<div id="pagerGridEvidenciasConcluidas"></div>
					</div>					
					<div id="divDetalleEvidencia" style="display: none; ">
						<table id="gridDetalleEvidenciasNuevas"></table>
						<div id="pagerGridDetalleEvidenciasNuevas"></div>
					</div>
					
					<div id="divNuevoAvisoPosHechoDel" style="display: none; ">
					 	<table id="gridNuevoAvisoPosHechoDel"></table>
						<div id="pagerGridNuevoAvisoPosHechoDel"></div>
					</div>
					
					<div id="divAvisoAtnddPosHechoDel" style="display: none; ">
					 	<table id="gridAvisoAtnddPosHechoDel"></table>
						<div id="pagerGridAvisoAtnddPosHechoDel"></div>
					</div>
					
					<div id="divGridEvaluarDocumentos" style="display: none; ">
					 	<table id="gridEvaluarDocumentos" width="100%" height="100%"></table>
						<div id="pagerGridEvaluarDocumentos"></div>
					</div>
					<div id="divGridSolicitudesUno" style="display: none;">
					<table id="gridDetalleFrmUno"></table>
					<div id="pager3"></div>
				</div>
					<div id="divGridNotificaciones" style="display: none;">
					 	<table id="gridNotificacionEventos"></table>
						<div id="pagerGridNotificacionEventos"></div>
					</div>
					<div id="divGridSolsXAtndr" style="display: none;">
					 	<table id="gridSolsXAtndr"></table>
						<div id="pagerGridSolsXAtndr"></div>
					</div>
					<div id="divGridSolsGeneradas" style="display: none;">
					 	<table id="gridSolsGeneradas"></table>
						<div id="pagerGridSolsGeneradas"></div>
					</div>					
					
					<!--  Ojo: las funciones ligadas a mandamientos judiciales, están en el js funcionesComunMandJudYMedCautelares -->
					<div id="divGridMandamientosJudiciales" style="display: none;">
					 	<table id="gridMandamientosJudiciales"></table>
						<div id="pagerGridMandamientosJudiciales"></div>
					</div>
					
					<!--  Ojo: las funciones ligadas a medidas cautelares, están en el js funcionesComunMandJudYMedCautelares -->
					<div id="divGridMedidasCautelares" style="display: none;">
					 	<table id="gridMedidasCautelares"></table>
						<div id="pagerGridMedidasCautelares"></div>
					</div>					
					
					<!-- GRID Expedientes compartidos -->
					<div id="divGridExpCompartidos" style="display: none;">
					 	<table id="gridExpCompartidos"></table>
						<div id="pagerExpCompartidos"></div>
					</div>
					
					<div id="divGridAudiencias">
						<table id="gridAudiencias"></table>
						<div id="pagerAudiencias"></div>
					</div>
				
					<div id="divGridDetalleSolAvisosDetencion">
						<table id="gridDetalleSolAvisosDetencion"></table>
						<div id="pagerDetalleSolAvisosDetencion"></div>
					</div>
					
					<div id="divGridDetalleEvidencia">
                         <table id="gridEvidenciaAlmacenExpediente"></table>
                         <div id="paginadorEvidenciaAlmacenExpediente"></div>
                    </div>
					
					<table width="100%">
					<tr valign="top">
						<td width="100%" valign="top">
							<div id="iRepLegalAccordionPane" style="display: none; width: 70%">
					            <dl>
					                <dt>Denuncia</dt>
					                <dd></dd>
					                <dt>Oficios</dt>
					                <dd></dd>
					                <dt>Solicitudes</dt>
					                <dd></dd>
					                <dt>Notificaciones</dt>
					                <dd></dd>
					                <dt>Documentos</dt>
					                <dd></dd>
					            </dl>
							</div>
						</td>
					</tr>
				</table>
			</div>		
		</div>
	</div>
</div>