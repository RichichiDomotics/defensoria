<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusTurno"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!--COMIENZA CSS DEL DOCUMENTO-->
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	

<STYLE type=text/css>
DD P {
	LINE-HEIGHT: 120%
}
#iRepLegalAccordionPane {
	BORDER-BOTTOM: #b5c9e8 0px solid; BORDER-LEFT: #b5c9e8 0px solid; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px; WIDTH: 1000px; PADDING-RIGHT: 1px; BACKGROUND: #fff; HEIGHT: 355px; BORDER-TOP: #b5c9e8 0px solid; BORDER-RIGHT: #b5c9e8 0px solid; PADDING-TOP: 1px
}
#iRepLegalAccordionPane DL {
	WIDTH: 1000px; HEIGHT: 355px
}
#iRepLegalAccordionPane DT {
	TEXT-ALIGN: right;
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 44px;
	TEXT-TRANSFORM: uppercase;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 15px;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide-title-inactive-1.jpg) #fff no-repeat 0px 0px;
	LETTER-SPACING: 1px;
	HEIGHT: 46px;
	COLOR: #1c94c4;
	FONT-SIZE: 1.1em;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}
#iRepLegalAccordionPane DT.active {
	BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide-title-active-1.jpg) #fff no-repeat 0px 0px; COLOR: #e78f08; CURSOR: pointer
}
#iRepLegalAccordionPane DT.hover {
	COLOR: #e78f08
}
#iRepLegalAccordionPane DT.hover.active {
	COLOR: #1c94c4
}
#iRepLegalAccordionPane DD {
	BORDER-BOTTOM: #dbe9ea 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px; PADDING-RIGHT: 1px; BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide.jpg) repeat-x left bottom; BORDER-TOP: #dbe9ea 1px solid; MARGIN-RIGHT: 1px; BORDER-RIGHT: #dbe9ea 1px solid; PADDING-TOP: 1px
}
#iRepLegalAccordionPane .slide-number {
	COLOR: #68889b; FONT-WEIGHT: bold; LEFT: 10px
}
#iRepLegalAccordionPane .active .slide-number {
	COLOR: #fff
}
#iRepLegalAccordionPane A {
	COLOR: #68889b
}
#iRepLegalAccordionPane DD IMG {
	MARGIN: 0px; FLOAT: right
}
#iRepLegalAccordionPane H2 {
	MARGIN-TOP: 10px; FONT-SIZE: 2.5em
}
#iRepLegalAccordionPane .more {
	DISPLAY: block; PADDING-TOP: 10px
}
body {
	background-image: url(<%= request.getContextPath()%>/images/back_gral.jpg);
	background-repeat: repeat-x;
}
body,td,th {
	font-family: Arial, Helvetica, sans-serif;
}
</STYLE>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>
	
	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();

/**Variables para la ceja de Bandeja de Solicitudes en Audiencia******/
	
	//variable para controlar el grid de audiencias del dia
	var primeraVezGridSolProcesoHistorico = true;
	//Variable para controlar el id de las ventanas
	var idWindowVisorAudienciaPJENC=1;

	var primeraGridExpedientesDocumentoPJATP = true;
	var recargaPermisosAudiencias=0;
	var recargaBitacoraAudiencias=0;
	var estadoGlobal = 0;
	var estadoGlobalBitacora = 0;
	var idFrame="";
	
	//variable para controlar el cargado del gird de accion penal privada
	var cargaGridSolAccPenalPrivada = true;

/**Variables para la ceja de Mandamientos Judiciales******/
	
	//variable para controlar el grid de audiencias por fecha
	var primeraVezGridMandamientosJudiciales = true;	
	// Variable para controlar el id de las ventanas
	var idWindowVisorMedidasCautelaresPJENC=1;

/**Variables para la ceja de Estado Expediente******/
	var primeraVezGridEstadoExpediente = true;	
	var numCausa; 
	var numCaso;
	var idWindowVisorPermisoAudiencia=0;
	
	//variables para setear las fechas y horas maximas
	var fechaServidor="";
	var fechaMax="";
	
/********************************************************COMIENZA ON READY**************************************************************************************/
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);
				
		//seteamos la fecha del servidor
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServerHechos(fechaServidor);
		
		outerLayout = $("body").layout( layoutSettings_Outer );

		//Plug in date
		$("#buscaporfechaIni").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});

		//Plug in date
		$("#buscaporfechaFin").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		//generamos los calendarios
		$("#fechaInicio").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '-111:+0',
			maxDate: fechaMax,
			onSelect: function(date) {
				//setter fecha minima al segunda calendario
				$( "#fechaFin" ).datepicker( "option", "minDate", date );
			},
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#fechaFin").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '-111:+0',
			maxDate: fechaMax, 
			minDate: fechaMax,
			onSelect: function(date) {
				//revisaLongitudFechas();
			},
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#fechaInicio,#fechaFin").attr("disabled","disabled");

		$("#seccion34treePJEA").treeview();
//**Funcionalidad Comun*****/	
		//crea el acordeon
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		//crea el arbol de audiencias
		$("#seccion1treePJENC").treeview();
		//crea el arbol de mandamientos judiciales
		$("#seccion2treePJENC").treeview();
		//crea el sub arbol de audiencias
		$("#seccion3treePJENC").treeview();
		//crea el arbol de medidas cautelares
		$("#seccion4treePJENC").treeview();
		//crea el arbol de estado del expediente
		$("#seccion5treePJENC").treeview();
		//crea el arbol de permisos audiencias
		$("#seccion7treePJENC").treeview();
		//crea el arbol de bitacora de descargas
		$("#seccion8treePJENC").treeview();		
		//crea el arbol de años para sentencia
		$("#seccion6treePJENC,#seccion34treePJEA").treeview();
		$("#seccionAccPenaltreePJENC").treeview();
		
//**Funcionalidad para ceja de Bandeja de sol por audiencia****/
		consultaGeneralMedidaCautelar(1);
		consultaGeneralMandamientoJudicial(1);

		//Carga el grid de solicitudes en proceso, por default
		cargaGridSolProcesoHistoricoPJENC('<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.ABIERTA.getValorId()%>');

		$("#dialogoChat").dialog({ autoOpen: false, 
			modal: true, 
			title: 'Chat', 
			dialogClass: 'alert',
			modal: true,
			width: 500 ,
			maxWidth: 600,
			buttons: {"Cancelar":function() {
								$(this).dialog("close");
		 			 }
			} 
		});

//**Funcionalidad para ceja de Estado del Expediente*****/
		$( "#dialog-logout" ).dialog({
			autoOpen: false,
			resizable: false,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					document.location.href="<%= request.getContextPath()%>/Logout.do";
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});		
	
		/*******************************
		 ***  CUSTOM LAYOUT BUTTONS  ***
		 *******************************
		 *
		 * Add SPANs to the east/west panes for customer "close" and "pin" buttons
		 *
		 * COULD have hard-coded span, div, button, image, or any element to use as a 'button'...
		 * ... but instead am adding SPANs via script - THEN attaching the layout-events to them
		 *
		 * CSS will size and position the spans, as well as set the background-images
		 */

		// BIND events to hard-coded buttons in the NORTH toolbar
		outerLayout.addToggleBtn( "#tbarBtnHeaderZise", "north" );
		// save selector strings to vars so we don't have to repeat it
		// must prefix paneClass with "body > " to target ONLY the outerLayout panes
		var westSelector = "body > .ui-layout-west"; // outer-west pane
		var eastSelector = "body > .ui-layout-east"; // outer-east pane

		 // CREATE SPANs for pin-buttons - using a generic class as identifiers
		$("<span></span>").addClass("pin-button").prependTo( westSelector );
		$("<span></span>").addClass("pin-button").prependTo( eastSelector );
		// BIND events to pin-buttons to make them functional
		outerLayout.addPinBtn( westSelector +" .pin-button", "west");
		outerLayout.addPinBtn( eastSelector +" .pin-button", "east" );

		 // CREATE SPANs for close-buttons - using unique IDs as identifiers
		$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
		$("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
		// BIND layout events to close-buttons to make them functional
		outerLayout.addCloseBtn("#west-closer", "west");
		outerLayout.addCloseBtn("#east-closer", "east");
		$('#test').weatherfeed(['MXDF0132']);
		createInnerLayout ();
		
		//Carga el reloj
		muestraGadgets();
			
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		
		ajustarGridAlCentro($("#gridMedidasCautelares"));
		ajustarGridAlCentro($("#gridMandamientosJudiciales"));

	});
/********************************************************TERMINA ON READY**************************************************************************************/
	
	

	/*
	*#######################
	* INNER LAYOUT SETTINGS
	*#######################
	*
	* These settings are set in 'list format' - no nested data-structures
	* Default settings are specified with just their name, like: fxName:"slide"
	* Pane-specific settings are prefixed with the pane name + 2-underscores: north__fxName:"none"
	*/
	layoutSettings_Inner = {
		applyDefaultStyles:				false // basic styling for testing & demo purposes
	,	minSize:						50 // TESTING ONLY
	,	spacing_closed:					14
	,	north__spacing_closed:			8
	,	south__spacing_closed:			8
	,	north__togglerLength_closed:	-1 // = 100% - so cannot 'slide open'
	,	south__togglerLength_closed:	-1
	,	fxName:							"slide" // do not confuse with "slidable" option!
	,	fxSpeed_open:					1000
	,	fxSpeed_close:					2500
	,	fxSettings_open:				{ easing: "easeInQuint" }
	,	fxSettings_close:				{ easing: "easeOutQuint" }
	,	north__fxName:					"none"
	,	south__fxName:					"drop"
	,	south__fxSpeed_open:			500
	,	south__fxSpeed_close:			1000
	//,	initClosed:						true
	,	center__minWidth:				200
	,	center__minHeight:				200
	};

	
	/*
	*#######################
	* OUTER LAYOUT SETTINGS
	*#######################
	*
	* This configuration illustrates how extensively the layout can be customized
	* ALL SETTINGS ARE OPTIONAL - and there are more available than shown below
	*
	* These settings are set in 'sub-key format' - ALL data must be in a nested data-structures
	* All default settings (applied to all panes) go inside the defaults:{} key
	* Pane-specific settings go inside their keys: north:{}, south:{}, center:{}, etc
	*/
	var layoutSettings_Outer = {
		name: "outerLayout" // NO FUNCTIONAL USE, but could be used by custom code to 'identify' a layout
		// options.defaults apply to ALL PANES - but overridden by pane-specific settings
	,	defaults: {
			size:					"auto"
		,	minSize:				50
		,	paneClass:				"pane" 		// default = 'ui-layout-pane'
		,	resizerClass:			"resizer"	// default = 'ui-layout-resizer'
		,	togglerClass:			"toggler"	// default = 'ui-layout-toggler'
		,	buttonClass:			"button"	// default = 'ui-layout-button'
		,	contentSelector:		".content"	// inner div to auto-size so only it scrolls, not the entire pane!
		,	contentIgnoreSelector:	"span"		// 'paneSelector' for content to 'ignore' when measuring room for content
		,	togglerLength_open:		35			// WIDTH of toggler on north/south edges - HEIGHT on east/west edges
		,	togglerLength_closed:	35			// "100%" OR -1 = full height
		,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
		,	togglerTip_open:		"Close This Pane"
		,	togglerTip_closed:		"Open This Pane"
		,	resizerTip:				"Resize This Pane"
		//	effect defaults - overridden on some panes
		,	fxName:					"slide"		// none, slide, drop, scale
		,	fxSpeed_open:			750
		,	fxSpeed_close:			1500
		,	fxSettings_open:		{ easing: "easeInQuint" }
		,	fxSettings_close:		{ easing: "easeOutQuint" }
	}
	,	north: {
			spacing_open:			1			// cosmetic spacing
		,	togglerLength_open:		0			// HIDE the toggler button
		,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
		,	resizable: 				false
		,	slidable:				false
		//	override default effect
		,	fxName:					"none"
		}
	,	south: {
			maxSize:				200
		,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
		,	slidable:				false		// REFERENCE - cannot slide if spacing_closed = 0
		,	initClosed:				false
		}
	,	west: {
			size:					250
		,	spacing_closed:			21			// wider space when closed
		,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		,	togglerAlign_closed:	"top"		// align to top of resizer
		,	togglerLength_open:		0			// NONE - using custom togglers INSIDE west-pane
		,	togglerTip_open:		"Close West Pane"
		,	togglerTip_closed:		"Open West Pane"
		,	resizerTip_open:		"Resize West Pane"
		,	slideTrigger_open:		"click" 	// default
		,	initClosed:				false
		//	add 'bounce' option to default 'slide' effect
		,	fxSettings_open:		{ easing: "" }
		,	west__onresize:		function () { $("#accordionmenuprincipal").accordion("resize"); }
		}
	,	east: {
			size:					250
		,	spacing_closed:			21			// wider space when closed
		,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		,	togglerAlign_closed:	"top"		// align to top of resizer
		,	togglerLength_open:		0 			// NONE - using custom togglers INSIDE east-pane
		,	togglerTip_open:		"Close East Pane"
		,	togglerTip_closed:		"Open East Pane"
		,	resizerTip_open:		"Resize East Pane"
		,	slideTrigger_open:		"mouseover"
		,	initClosed:				false
		//	override default effect, speed, and settings
		,	fxName:					"drop"
		,	fxSpeed:				"normal"
		,	fxSettings:				{ easing: "" } // nullify default easing
		,	est__onresize:		function () { $("#accordionmenuderprincipal").accordion("resize"); }		
		}
	,	center: {
			paneSelector:			"#mainContent" 			// sample: use an ID to select pane instead of a class
		,	onresize:				"innerLayout.resizeAll"	// resize INNER LAYOUT when center pane resizes	
		,	minWidth:				200
		,	minHeight:				200
		,	onresize_end:			function () { 

			ajustarGridAlCentro($("#gridAudienciasPJENC"));
			ajustarGridAlCentro($("#gridEstadoExpedientePJENC"));
			ajustarGridAlCentro($("#gridSentenciasEnProcesoPJENC"));
			ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
			ajustarGridAlCentro($("#gridExpedientesDocumentoPJATP"));
			ajustarGridAlCentro($("#gridSolicitudesAccPenalPrivadaPJENC"));
			ajustarGridAlCentro($("#gridPermisosAudiencias"));
			ajustarGridAlCentro($("#gridBitacoraPermisosAudiencias"));				
			ajustarGridAlCentro($("#gridMedidasCautelares"));		
			ajustarGridAlCentro($("#gridMandamientosJudiciales"));		
			}		
		}
	};
		
//**************************************************************FUNCIONALIDAD ACORDEON BANDEJA DE SOLICITUDES POR AUDIENCIA****************************************/
	/*
	*Funcion que carga el grid, por default con las audiencias del dia
	*/
	function cargaGridSolProcesoHistoricoPJENC(estados){

		if(primeraVezGridSolProcesoHistorico == true){

			jQuery("#gridAudienciasPJENC").jqGrid({
				url:'<%= request.getContextPath()%>/consultarAudienciasBandejaInicialEncargadoCausa.do?estadoSolicitud='+estados,
				datatype: "xml", 
				colNames:['N&uacute;mero de Caso','N&uacute;mero de Causa','Carácter','Tipo de Audiencia','Fecha de Audiencia','Hora de Audiencia','Sala'], 
				colModel:[ 	{name:'numeroCaso',index:'numeroCaso', width:130, align:'center'},
							{name:'expediente',index:'expediente', width:110, align:'center'}, 
							{name:'caracter',index:'caracter', width:50, align:'center'}, 
							{name:'tipoAudiencia',index:'tipoAudiencia', width:60, align:'center'}, 
							{name:'fechaAudiencia',index:'fechaAudiencia', width:80, align:'center'},	
							{name:'horaAudiencia',index:'horaAudiencia', width:80, align:'center'},											
							{name:'sala',index:'sala', width:80, align:'center'}
																		
						],
				pager: jQuery('#pagerAudienciasPJENC'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				width:767,
				sortname: 'expediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					mostrarVisorAudienciaPJENC(rowid);
				}
			}).navGrid('#pagerAudienciasPJENC',{edit:false,add:false,del:false});
			$("#gview_gridAudienciasPJENC .ui-jqgrid-bdiv").css('height', '450px');
			primeraVezGridSolProcesoHistorico=false;
			ocultaMuestraGrids("procesoHistorico");
		}
		else{
			jQuery("#gridAudienciasPJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarAudienciasBandejaInicialEncargadoCausa.do?estadoSolicitud='+estados,datatype: "xml" });
			$("#gridAudienciasPJENC").trigger("reloadGrid");
			ocultaMuestraGrids("procesoHistorico");
		}
		ajustarGridAlCentro($("#gridAudienciasPJENC"));
	}

	/*
	*Funcion que abre el visor de audiencias
	*/
	function mostrarVisorAudienciaPJENC(rowID){

		idWindowVisorAudienciaPJENC++;
		$.newWindow({id:"iframewindowVisorAudiencia"+idWindowVisorAudienciaPJENC, statusBar: true, posx:255,posy:111,width:1140,height:450,title:"Atenci&oacute;n de solicitudes por audiencia", type:"iframe"});
    	$.updateWindowContent("iframewindowVisorAudiencia"+idWindowVisorAudienciaPJENC,'<iframe src="<%=request.getContextPath()%>/visorAudienciaPJENC.do?idAudiencia=' + rowID +'" width="1140" height="450" />'); 
        $("#" +"iframewindowVisorAudiencia"+idWindowVisorAudienciaPJENC+ " .window-maximizeButton").click();
    }

	/*
	* Función que abre el visor de medidas cautelares 
	*/
	function mostrarVentanaInvolucradosMedida(numeroCausa){
		idWindowVisorMedidasCautelares++;
		$.newWindow({id:"iFrameWindowVisorMedidasCautelares"+idWindowVisorMedidasCautelares, statusBar: true, posx:200,posy:50,width:970,height:480,title:"Visor de Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iFrameWindowVisorMedidasCautelares"+idWindowVisorMedidasCautelares,'<iframe src="<%=request.getContextPath()%>/visorMedidaCautelar.do?numeroCausa='+numeroCausa+'" width="970" height="480" />');
	}
	
	/*
	* Función que abre el visor de mandamientos judiciales 
	*/
	function mostrarVentanaInvolucradosMandamiento(numeroCausa){
		idWindowVisorMandamientosJud++;
		$.newWindow({id:"iFrameWindowVisorMandamientosJud"+idWindowVisorMandamientosJud, statusBar: true, posx:200,posy:50,width:970,height:480,title:"Visor de Mandamientos Judiciales", type:"iframe"});
    	$.updateWindowContent("iFrameWindowVisorMandamientosJud"+idWindowVisorMandamientosJud,'<iframe src="<%=request.getContextPath()%>/visorMandamientoJudicial.do?numeroCausa=' + numeroCausa +'&op=1&origen='+<%=Instituciones.PJ.getValorId()%>+'&ocultarAdd='+false+'" width="970" height="480" />');
	}

	/**
	*Funcion que abre la ventana modal para introducir el número de audiencia
	*/
	function popUpTipoBusquedaModalBitacoraAudiencia(){
		
		$("#datoAudiencia").val("");
		$("#divAudiencia").dialog("open");
	  	$("#divAudiencia").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Consulta de bitácora de descargas por audiencia', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 350,
		  	height: 200,
		  	maxWidth: 350,
		  	buttons:{"Realizar búsqueda":function() {
		  		var numeroAudiencia = $("#datoAudiencia").val();
		  		gridBitacoraPorAudiencia(numeroAudiencia);
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}

//**************************************************************FUNCIONALIDAD ACORDEON ESTADO DEL EXPEDIENTE*******************************************************/
	
	/**
	*Funcion que abre la ventana modal para introducir el numero de causa
	*para consultar el estado de un expediente 
	*/
	function abreModalCausaEstadoExpediente(){
		
		$("#datoCausaExpediente").val("");
		$("#divCausaExpediente").dialog("open");
	  	$("#divCausaExpediente").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Estado del Expediente', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 350,
		  	height: 260,
		  	maxWidth: 350,
		  	buttons:{"Realizar búsqueda":function() {
		  		var numeroCausa = $("#datoCausaExpediente").val();
		  		cargaGridEstadoExpediente(numeroCausa);
		  		
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}


	/*
	*Funcion que carga el grid con el estado del expediente
	*/
	function cargaGridEstadoExpediente(numeroCausa){
													  
		if(primeraVezGridEstadoExpediente == true){
			  jQuery("#gridEstadoExpedientePJENC").jqGrid({ 
					url:'<%= request.getContextPath() %>/buscarInvolucradosAudienciaPorCausa.do?numCausa='+numeroCausa,datatype: "xml",
								
					datatype: "xml",

					colNames:['Nombre Probable Responsable','Nombre Víctima','Delito','Calidad Actual','Nueva Calidad'], 
						colModel:[ 	{name:'nombreProbResp',index:'nombreProbResp',width:250, align:'center'},
									{name:'victima',index:'victima', width:250, align:'center'}, 
									{name:'delito',index:'delito',width:150, align:'center'},								
									{name:'CalidadActual',index:'calidadActual', width:150, align:'center'},
					           		{name:'NuevaCalidad',index:'NuevaCalidad', width:200, align:'center'},
							],
						autowidth: false,
						width:767, 
						pager: jQuery('#pagerGridEstadoExpedientePJENC'),
						rowNum:10,
						rowList:[25,50,100],
						sortname: 'nombreProResp',
						sortorder: "desc", 
						viewrecords: true,
						//caption:"NUMERO DE CAUSA:"+numeroCausa,
						toolbar: [true,"top"],
						
						ondblClickRow: function(rowid) {
							mostrarbuscarInvolucradosAudienciaPorCausa(rowid);
						} 
				
				}).navGrid('#pagerGridEstadoExpedientePJENC',{edit:false,add:false,del:false}); 
			  jQuery("#gridEstadoExpedientePJENC").jqGrid('navGrid','#pagerGridEstadoExpedientePJENC',{edit:false,add:false,del:false});
			  $("#gview_gridEstadoExpedientePJENC .ui-jqgrid-bdiv").css('height', '450px');
			  $("#t_gridEstadoExpedientePJENC").append("<input type='button' value='Guardar' style='height:20px;font-size:-3'/>");
			  $("input","#t_gridEstadoExpedientePJENC").click(function(){
		
			  	actualizarSituacionJuridica();
			  });
			  				
				$("#gview_gridEstadoExpedientePJENC .ui-jqgrid-bdiv").css('height', '480px');
				primeraVezGridEstadoExpediente = false;
				//Resize del grid de estado expediente
				$("#gridEstadoExpedientePJENC").setGridWidth($("#mainContent").width() - 5, true);
				ocultaMuestraGrids("estadoExpediente");				
		}
		else{
			
			jQuery("#gridEstadoExpedientePJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/buscarInvolucradosAudienciaPorCausa.do?numCausa='+numeroCausa,datatype: "xml" });
			$("#gridEstadoExpedientePJENC").trigger("reloadGrid");
			ocultaMuestraGrids("estadoExpediente");				  
		}
		ajustarGridAlCentro($("#gridEstadoExpedientePJENC"));

	}

	/*
	*Funcion que abre el visor de medidas cautelares 
	*/
	function mostrarbuscarInvolucradosAudienciaPorCausa(numeroCausa){

		idWindowVisorMedidasCautelaresPJENC++;
		$.newWindow({id:"iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC, statusBar: true, posx:255,posy:111,width:970,height:480,title:"Visor de Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC,'<iframe src="<%=request.getContextPath()%>/visorbuscarInvolucradosAudienciaPorCausa.do?numeroCausa=' + numeroCausa +'" width="970" height="480" />'); 
	}
//**************************************************************FUNCIONALIDAD ACORDEON SENTENCIAS*******************************************************/

	/*
	*Funcion que agrega los anios dinamicamente 
	*/
	function cargaHistoricoSentenciasPJENC(){
    	
    	$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/BusquedaInicialCaso.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var branches = "";
				$(xml).find('anio').each(function(){
					var anioId = $(this).find('anioId').text();
					var anio = $(this).find('anio').text();
    				branches ="<ul><li class='closed' id='" + anioId+ "' onclick='agregaMesesPJENC(" + anioId + ")'><span class='folder'>" + anio + "</span><ul></ul></li></ul>";
					var sentenciasPJENC = $(branches).appendTo("#historicoSentencias");
					$("#seccion6treePJEA").treeview({
    					add: sentenciasPJENC
    				});
    			});
    		}
    		
    	});
    }


//COMENTADO	13/07/2012 AGA*****************************************************************************************************************************
	var primeraVezGridSentencias = true 

	/*
	*Funcion que carga el grid con el historico del estado del expediente
	*/
	function cargaGridSentencias(estados){
													  
		if(primeraVezGridSentencias == true){
						
			  jQuery("#gridSentenciasEnProcesoPJENC").jqGrid({ 
					url:'<%= request.getContextPath()%>/consultarSentenciasPorEstatus.do?estadoSentencia='+estados+'',
					data:'',
					datatype: "xml",
					colNames:['Imputado','Número de Caso','Número de Causa','Carpeta de Ejecución','Fecha Creación'], 
					colModel:[ 	{name:'imputado',index:'imputado',width:250, align:'center'},
								{name:'numeroCaso',index:'numeroCaso',width:150, align:'center'}, 
								{name:'numeroCausa',index:'numeroCausa', width:150, align:'center'}, 
								{name:'carpetaEjecucion',index:'audiencia',width:50, align:'center'},
								{name:'fechaCreacion',index:'fecha',width:100, align:'center'},
							],
						autowidth: false,
						width:767, 
						pager: jQuery('#pagerGridSentenciasEnProcesoPJENC'),
						rowNum:10,
						rowList:[25,50,100],
						sortname: 'fechaDocumento',
						sortorder: "desc", 
						viewrecords: true
				}).navGrid('#pagerGridSentenciasEnProcesoPJENC',{edit:false,add:false,del:false}); 
				$("#gview_gridSentenciasEnProcesoPJENC .ui-jqgrid-bdiv").css('height', '450px');

				primeraVezGridSentencias = false;

				//Resize del grid
				$("#gridSentenciasEnProcesoPJENC").setGridWidth($("#mainContent").width() - 5, true);
		}
		else{
			jQuery("#gridSentenciasEnProcesoPJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSentenciasPorEstatus.do?estadoSentencia='+estados+'',datatype: "xml" });
			$("#gridSentenciasEnProcesoPJENC").trigger("reloadGrid");
		}

		ocultaMuestraGrids("sentencias");
		ajustarGridAlCentro($("#gridSentenciasEnProcesoPJENC"));
	}
//COMENTADO	13/07/2012 AGA*****************************************************************************************************************************


//Variable para controlar la carga del grid de expedientes
var cargaPrimeraExpPorEstatus = true;



/*
*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
*usado para mostrar las sentemcias 
*/
function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
	if(cargaPrimeraExpPorEstatus == true){
		
		jQuery("#gridExpedientesPorEstatus").jqGrid({ 
			url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do', 
			datatype: "xml", 
			colNames:['Número De Caso','Número De Causa', 'Carpeta Ejecución', 'Nombre Sentenciado','Fecha De Creación'], 
			colModel:[ 	{name:'noCaso',index:'1', width:230,align:'center'}, 
						{name:'noCausa',index:'2', width:70,align:'center', hidden:true}, 
						{name:'carpeta',index:'3', width:200,align:'center'}, 
						{name:'nombreSentenciado',index:'4', width:250,align:'center'},
						{name:'fechaCreacion',index:'5', width:150,align:'center'},
					],
			pager: jQuery('#pagerGridExpedientesPorEstatus'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			height:360,
			sortname: 'Sentencia_id',
			caption:"Sentencias",
			//autowidth:true,
			//shrinkToFit:true,
			viewrecords: true,
			onSelectRow: function(id){

				},
			ondblClickRow: function(id) {
				consultarDatosGenerales(id);
				
			},
			sortorder: "asc"
		}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});
	
	}else{
		jQuery("#gridExpedientesPorEstatus").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',datatype: "xml" });
		$("#gridExpedientesPorEstatus").trigger("reloadGrid");
	}
	ocultaMuestraGrids("sentencias");	
	ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
}


function consultarDatosGenerales(id){
	customVentana("idVentanaDatosGenerales", "Datos Generales", "/consultarDatosGeneralesSentencia.do", "?sentenciaId="+id);
}




//**************************************************************************FUNCIONALIDAD COMUN ****************************************************************//

	function ocultaMuestraGrids(grid){
	
		$('#divGridAudienciasProcesoHistorico').hide();
		$('#divGridMandamientosJudiciales').hide();
		$('#divGridEstadoExpedientePJENC').hide();
		$('#divGridSentenciasEnProcesoPJENC').hide();
		$('#divGridExpedientesDocumentoPJATP').hide();
		$('#divGridSolicitudesAccPenalPrivadaPJENC').hide();
		$('#divGridPermisosAudiencias').hide();
		$('#divGridBitacoraPermisosAudiencias').hide();
		$('#divGridMedidasCautelares').hide();		

		switch (grid){
		case "accPenalPrivada":
			$('#divGridSolicitudesAccPenalPrivadaPJENC').show();
			break;
		case "procesoHistorico":
			$('#divGridAudienciasProcesoHistorico').show();
    		break;			
        case "estadoExpediente":
        	$('#divGridEstadoExpedientePJENC').show();
    	    break;
        case "sentencias":
        	$('#divGridSentenciasEnProcesoPJENC').show();
    	    break;
        case "expedientesDocumentoPJATP":
        	$('#divGridExpedientesDocumentoPJATP').show();
    	    break;
        case "permisosAudiencias":
        	$('#divGridPermisosAudiencias').show();
    	    break;
        case "bitacoraAudiencias":
        	$('#divGridBitacoraPermisosAudiencias').show();
    	    break;
        case "gridMedidasCautelares":
        	$('#divGridMedidasCautelares').show();
    	    break;
		case "gridMandamientosJudiciales":
			$('#divGridMandamientosJudiciales').show();
    	    break;
		}
	}

	/*
	*Funcion que llama a la funcionalidad para crear la agenda 
	*/	
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
	}
	
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();
	}


	function visorLeyesCodigos() {
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:255,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="809" height="468" />');
	}

	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
	*/
	function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:63,posy:111,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="400" />');
	    		
	}	


	function actualizarSituacionJuridica(){

		var parametros = delitosPersonaAOrdenar();

		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/actualizarSituacionJuridica.do',
			data: 'parametros='+parametros,
			dataType: 'xml',
			success: function(xml){			
			}
		});
	}

	/*
	*Funcion para obtener los ids del 
	*DELITO
	*INVOLUCRADO
	*NUEVA CALIDAD
	*/
	function delitosPersonaAOrdenar() {
		var solicitudesAOrdenar = "";
		var i=0;
		var arrayIds = new Array();
		
		arrayIds = jQuery("#gridEstadoExpedientePJENC").getDataIDs();
		$('select[id^="delito_"]').each(function(){
			id = $(this).attr("id");
			solicitudesAOrdenar += arrayIds[i]+"_"+id.split("_")[1]+ "_" + $(this).val()+ ",";
			i++;
		});
		return solicitudesAOrdenar; 
	}


	/*
	*Funcion que realiza las validaciones para la carga el grid de consulta por fechas y expedientes
	*/
	function controlCargaGridExpedientes(){

		var fechaIni = $("#buscaporfechaIni").val();
		var fechaFin = $("#buscaporfechaFin").val();
		var numeroExpedienteId =$("#buscapornumexp").val();

		if(numeroExpedienteId == null || numeroExpedienteId == "" || numeroExpedienteId == "undefined"){

			var validacion = validaCamposFecha(fechaIni,fechaFin);
			
			if(validacion == true){
				cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId);
			}			
		}else{
			
			cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId);
		}
	}

	
	/*
	*Funcion que carga el grid de consulta por fechas y numero de expediente
	*/
	function cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId){
												  
		if(primeraGridExpedientesDocumentoPJATP == true){

			jQuery("#gridExpedientesDocumentoPJATP").jqGrid({ 
				url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpedienteParaDocumentos.do?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&numeroExpedienteId='+numeroExpedienteId+'', 
				datatype: "xml",
				mtype: 'POST', 
				colNames:['<bean:message key="numeroDeCausa" />',
							'<bean:message key="numeroDeCaso" />',
							'<bean:message key="fechaDeCreacion" />',
							'<bean:message key="fechaDeModificacion" />',
							'<bean:message key="documentos" />',
				], 
				colModel:[ 	
							{name:'expediente',index:'numeroCaso',width:70,align:'center'},
							{name:'numeroCaso',index:'expediente',width:70,align:'center'}, 
							{name:'caracter',index:'caracter',width:25,align:'center'},
							//se oculta porque no se muestran datos debido a la bitacora 
							{name:'tipoAudiencia',index:'tipoAudiencia', width:40,hidden:true},
							{name:'documento',index:'documento', width:40,hidden:true},
						],
				pager: jQuery('#paginadorExpedientesDocumentoPJATP'),
				rowNum:10,
				rowList:[10,20,30],
				width:830,
				height:410,
				viewrecords: true,
				ondblClickRow: function(rowid) {
					var ret2 = jQuery("#gridExpedientesDocumentoPJATP").jqGrid('getRowData',rowid);
					numCausa= ret2.expediente;
					numCaso = ret2.numeroCaso;
					dblClickRowvisorDocumentosExpediente(rowid);
				},
				sortorder: "desc"
			});
			$("#gview_GridExpedientesDocumentoPJATP .ui-jqgrid-bdiv").css('height', '450px');
			//cambia la bandera a false para que solo ejecute el reload
		  	primeraGridExpedientesDocumentoPJATP = false;
		}
		else{
			jQuery("#gridExpedientesDocumentoPJATP").jqGrid('setGridParam',{url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpedienteParaDocumentos.do?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&numeroExpedienteId='+numeroExpedienteId+'',datatype:"xml" });
			$("#gridExpedientesDocumentoPJATP").trigger("reloadGrid");				  
		}
		
		ocultaMuestraGrids("expedientesDocumentoPJATP");
		ajustarGridAlCentro($("#gridExpedientesDocumentoPJATP"));
	}

	
	/*
	*Funcion para desplegar el poppoup de tipo de busqueda
	*/
	function  popUpTipoBusqueda(tipo){
	
		var titulo="";
		
		$("#buscapornumexp").val("");  
		$("#buscaporfechaIni").val("");
		$("#buscaporfechaFin").val("");
		
		if(tipo=="expediente"){
			$("#tiposBusquedaExpediente").css("display","block");
			$("#tiposBusquedafecha").css("display","none");
			titulo="Buscar por número de causa";
		}else{
			$("#tiposBusquedaExpediente").css("display","none");
			$("#tiposBusquedafecha").css("display","block");
			titulo="Buscar causa por fecha";
		}
		  
		$( "#tiposBusquedaExpedienteid" ).dialog({
			title:titulo, 
			autoOpen: true,
			resizable: false,
			modal: true,
			height:'auto',
			width:'auto',
			buttons: {
				"Aceptar": function() {
					controlCargaGridExpedientes();
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					
				}
			}
		});	
	}

	// Búsqueda de permisos de audiencia por condición y filtrado de fechas
	function popUpTipoBusquedaModal(estatus){
		
		var titulo="";
		
		$("#buscaporfechaIni").val("");
		$("#buscaporfechaFin").val("");
		
		$("#tiposBusquedaExpediente").css("display","none");
		$("#tiposBusquedafecha").css("display","block");
		titulo="Buscar permisos por fecha de solicitud";
		  
		$( "#tiposBusquedaExpedienteid" ).dialog({
			title:titulo, 
			autoOpen: true,
			resizable: false,
			modal: true,
			height:'auto',
			width:'auto',
			buttons: {
				"Aceptar": function() {								
					estadoGlobal=estatus;
					gridPermisosAudiencias();
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );					
				}
			}
		});	
	}
		
	// Búsqueda de bitácora de audiencias por fecha
	function popUpTipoBusquedaModalBitacoraFecha(){
		
		var titulo="";
		
		$("#buscaporfechaIni").val("");
		$("#buscaporfechaFin").val("");
		
		$("#tiposBusquedaExpediente").css("display","none");
		$("#tiposBusquedafecha").css("display","block");
		
		titulo="Bitacora de descargas de permisos por fecha";	
		  
		$( "#tiposBusquedaExpedienteid" ).dialog({
			title:titulo, 
			autoOpen: true,
			resizable: false,
			modal: true,
			height:'auto',
			width:'auto',
			buttons: {
				"Aceptar": function() {								
					gridBitacoraPermisos();
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );					
				}
			}
		});	
	}

	/*
	*Funcion que crea el visor de  audiencia
	*/		
	function dblClickRowvisorDocumentosExpediente(idRow){
		var tabMedCautelar="true";
		// Este servicio tiene como parámetro de acción en ingresarMedidasCautelaresPJENC.jsp
		// a flujoMedCautelar
		var flujoMedCautelar = "dePJaSSPyPGJ";
		$.newWindow({id:"iframewindowVisorEncargadoAdmonAudiencias", statusBar: true, posx:50,posy:111,width:1200,height:700,title:"Numero de Causa:"+numCausa+" "+"Numero de Caso:"+numCaso, type:"iframe"});
		$.updateWindowContent("iframewindowVisorEncargadoAdmonAudiencias",'<iframe src="<%=request.getContextPath()%>/visorDocumentos.do?numExpedienteId='+idRow+'&tabMedCautelar='+tabMedCautelar+'&numCausa='+numCausa+'&flujoMedCautelar='+flujoMedCautelar+'" width="1200" height="700" />'); 
	}


//********************************************************************COMIENZA SECCI0N PARA SENTENCIA************************************************************/


	//TODO Se tiene que agregar este visor para que se abra cuando se de click como el grid 
	/*
	* Función que genera un Mandamiento y después abre la pantalla de mandamiento judicial
	*
	*/
	function generarMandamiento(){
		resolutivo = jQuery("#gridSentenciasEnProcesoPJENC").jqGrid('getGridParam','selrow');
		
		//limpiaDivTipoMandamiento();
		//agregarCalendarios();

		$("#divTipoMandamiento").dialog("open");
	  	$("#divTipoMandamiento").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Tipo de Mandamiento', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 350,
		  	maxWidth: 450,
		  	maxHeigth:350,
		  	buttons:{"Aceptar":function() {
			  	
		  		//if(validarDatosMandamiento() == false || validaCamposFechaSentencia() == false){
				//	return;
			  	//}else{
			  	//	enviarMandamiento();	
			  	//	$(this).dialog("close");
				//}
		  	  },
				"Cancelar" : function() {	
					$(this).dialog("close");
				}
		  	}
		});	 
	}

//******************************************************************ACCION PENAL PRIVADA***********************************************************************************/

	
 	//variable para verificar que grid fue el seleccionado
 	var seleccion; 
 	
 	/*
 	* Carga el grid con las solicitudes de accion penal privada
 	*
 	*/
	function cargaGirdSolicitudesAccPenalPrivadaPJENC(estado){
		
 		var status;
 		
 		if(estado == "porAtender"){
 			status='<%=EstatusTurno.ESPERA.getValorId()%>';
 			seleccion="porAtender";
 		}
 			
 		else if(estado =="atendidas"){
 			status='<%=EstatusTurno.ATENDIDO.getValorId()%>';
 			seleccion="atendidas";
 		}
 		
		if(cargaGridSolAccPenalPrivada == true){
			/*Enable IT agregando columna de número de causa a Solicitud Externa*/
			jQuery("#gridSolicitudesAccPenalPrivadaPJENC").jqGrid({
				url:'<%=request.getContextPath()%>/consultarTurnosAccPenalPrivada.do?estado='+status+'', 
				datatype: "xml", 
				colNames:['Fecha de la Solicitud','Hora de la Solicitud','Nombre del Solicitante','Número Expediente'], 
				colModel:[ 
							{name:'FechaSolicitud',index:'fechaSolicitud',align:"center", width:100},
							{name:'HoraSolicitud',index:'horaSolicitud',align:"center", width:100}, 
							{name:'NombreSolicitante',index:'nombreSolicitante',align:"center", width:100},
							{name:'NumeroExpediente',index:'numExpediente',align:"center", width:100},
						],
				pager: jQuery('#pagerGridSolicitudesAccPenalPrivadaPJENC'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				width:760,
				sortname: 'fechaSolicitud',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					
					var turnoId = rowid.split("+")[0];
					var numeroExpRelacion = rowid.split("+")[1];
					nuevaDenunciaUI(turnoId,numeroExpRelacion);
					}
				}).navGrid('#pagerGridSolicitudesAccPenalPrivadaPJENC',{edit:false,add:false,del:false});
			
			$("#gview_gridSolicitudesAccPenalPrivadaPJENC .ui-jqgrid-bdiv").css('height', '355px');
			cargaGridSolAccPenalPrivada = false;
			ocultaMuestraGrids("accPenalPrivada");
		}
		else{
			jQuery("#gridSolicitudesAccPenalPrivadaPJENC").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarTurnosAccPenalPrivada.do?estado='+status+'',datatype: "xml" });
			$("#gridSolicitudesAccPenalPrivadaPJENC").trigger("reloadGrid");
			ocultaMuestraGrids("accPenalPrivada");
		}
		ajustarGridAlCentro($("#gridSolicitudesAccPenalPrivadaPJENC"));

	}
	

	//variable para controlar el id del visor
	var idWindowNuevaDenuncia=1; 

	/*
	*Funcion que crea una nueva denuncia
	*/
	function nuevaDenunciaUI(turnoId,numeroExpRelacion) {
		
	    var idExpediente="";
	    var numeroExpediente="";
	    var numeroExpedienteId="";
	    var numeroCasoNuevo="";
		//ya estaba
	    var idNuevaDenuncia = 1;
	 	//variable que indica si es un ingreso o una consulta
	    var ingresoDenuncia = false;
	 	
	 	if(numeroExpRelacion == "null"){
	 		
	 		$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/nuevoExpedienteUI.do',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					var option;
					idExpediente=$(xml).find('expedienteDTO').find('expedienteId').first().text();
					
					numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').first().text();
					
					numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').first().text();
					
					numeroCasoNuevo=$(xml).find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').first().text();
					
				}
				
			});	 		
	 	}
	 	else{
	 		
	 		$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarExpedienteByIdPJENC.do',
				data: 'expedienteId='+numeroExpRelacion,
				dataType: 'xml',
				async: false,
				success: function(xml){
				
					idExpediente=$(xml).find('expedienteDTO').find('expedienteId').first().text();
					
					numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').first().text();
					
					numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').first().text();
					
					numeroCasoNuevo=$(xml).find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').first().text();
					
				}
				
			});
	 	}
	   	
	   	
	   	var pantallaSolicitada=3;
		idWindowNuevaDenuncia++;
		isWindowOpen = true;
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente+" - No. Caso: "+numeroCasoNuevo, type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedioPJENC.do?numeroGeneralCaso='+numeroCasoNuevo+'&abreenPenal=abrPenal&idNuevaDenuncia='+idNuevaDenuncia +'&ingresoDenuncia='+ingresoDenuncia +'&numeroExpediente='+numeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idNumeroExpedienteop='+numeroExpedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&idExpedienteop='+idExpediente+'&idNumeroExpedienteConsul='+numeroExpedienteId+'&idExpediente='+idExpediente+'&turnoId='+turnoId+'" width="1430" height="670" />');
		
		if(seleccion=="porAtender"){
			cargaGirdSolicitudesAccPenalPrivadaPJENC('porAtender');	
		}
	}   

	
	/************************************************* FUNCIONES PARA LAS FECHAS DE LA BUSQUEDA DE MADNAMIENTOS *****************/
	/*
			*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
			*/
			function consultaFechaHoraMaximaServer()
			{
				var fecha="";
				   $.ajax({
					     type: 'POST',
					     url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
						 dataType: 'xml',
						 async: false,
						 success: function(xml){
							fecha= $(xml).find('fecha').text();
						  }
						});
				return fecha;
			}
			
			/*
			 * Funcion para regresar la fecha maxima obtenida desde el servidor
			 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
			 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
			 */
			function getFechaMaximaServerHechos(fechaCompleta)
			{
				var arrFechaHora=fechaCompleta.split(" ");
				var digitosFecha=arrFechaHora[0].split("-");
				return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
			}
			/****************************************** FIN  FUNCIONES PARA LAS FECHAS DE LA BUSQUEDA DE MADNAMIENTOS *****************/
			/*
	 *Funcion para consultar los roles extras de cada usuario y
	 * construlle el arbol dinamico de los tipos de rol en el menu derecho
	 */
	function consultarTiposRol()
	{
		//limpiamos el menu de los tipos de solicitud
		$("#tableRolMenu").empty();
		//lanzamos la consulta del tipo de solicitudes
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaMenuRol.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('RolDTO').each(function(){
					var rolnuevo=$(this).find("nombreRol").text();
					var rolDesc=$(this).find("descripcionRol").text();
					var trTabla = "<tr>";
					trTabla = trTabla + "<td><span><img src='<%=request.getContextPath()%>/resources/css/check.png' width='16' height='16' />"+
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
		document.frmRol2.rolname.value = rolNuevo;
		document.frmRol2.submit();

	}	
	
	// Función que realiza el llenado del grid de permisos audiencias referente al desarrollo JAVS
	function gridPermisosAudiencias(){
				
		var fechaIni = $('#buscaporfechaIni').val();
		var fechaFin = $('#buscaporfechaFin').val();	

		if(recargaPermisosAudiencias==0){				
			jQuery("#gridPermisosAudiencias").jqGrid({ 
				url:'<%=request.getContextPath()%>/filtradoPermisosAudiencias.do?estado='+estadoGlobal+'&inicio='+fechaIni+'&fin='+fechaFin+'', 
				datatype: "xml", 
				colNames:['Institucion','Audiencia','Puesto', 'Nombre completo del funcionario', 'Fecha de solicitud','Fecha de asignación','Usuario Asignador'], 
				colModel:[ 	{name:'Institucion',		index:'1', width:140}, 
				           	{name:'Audiencia',			index:'2', width:65},
							{name:'Puesto',				index:'3', width:140}, 
							{name:'Nombre',				index:'4', width:230}, 
							{name:'FechaSolicitud',		index:'5', width:140},
							{name:'FechaAsignacion',	index:'6', width:140},
							{name:'UsuarioAsignador',	index:'7', width:230}
				],
				pager: jQuery('#paginadorPermisosAudiencias'),
				rowNum:10,
				rowList:[10,15,20],
				autowidth: true,
				sortname: 'Audiencia',
				viewrecords: true,
				height:410,				
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					
					var row = jQuery("#gridPermisosAudiencias").jqGrid('getRowData',rowid);
					
					dblClickRowBandejaPermisosAudiencias(rowid, row);
				}			
			}).navGrid('#paginadorPermisosAudiencias',{edit:false,add:false,del:false});
			recargaPermisosAudiencias=recargaPermisosAudiencias+1;	
		}
		else{
			jQuery("#gridPermisosAudiencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/filtradoPermisosAudiencias.do?estado='+estadoGlobal+'&inicio='+fechaIni+'&fin='+fechaFin+'',
			datatype: "xml" });
			$("#gridPermisosAudiencias").trigger("reloadGrid");			
		}
		ocultaMuestraGrids("permisosAudiencias");
		ajustarGridAlCentro($("#gridPermisosAudiencias"));

	}
	
	// Función que realiza el llenado del grid de bitácora de descargas referente al desarrollo JAVS
	function gridBitacoraPermisos(){
		
		var fechaIni = $('#buscaporfechaIni').val();
		var fechaFin = $('#buscaporfechaFin').val();
		
		if(recargaBitacoraAudiencias==0){				
			jQuery("#gridBitacoraPermisosAudiencias").jqGrid({ 
				url:'<%=request.getContextPath()%>/filtradoBitacoraPermisosAudiencias.do?inicio='+fechaIni+'&fin='+fechaFin+'', 
				datatype: "xml", 
				colNames:['Institucion','Audiencia','Puesto', 'Nombre completo del funcionario','Fecha de descarga','Usuario Asignador'], 
				colModel:[ 	{name:'Institucion',		index:'1', width:140}, 
				           	{name:'Audiencia',			index:'2', width:65},
							{name:'Puesto',				index:'3', width:140}, 
							{name:'Nombre',				index:'4', width:230}, 
							{name:'FechaDescarga',		index:'5', width:140},
							{name:'UsuarioAsignador',	index:'7', width:230}
				],
				pager: jQuery('#paginadorBitacoraPermisosAudiencias'),
				rowNum:10,
				rowList:[10,15,20],
				autowidth: true,
				sortname: 'Audiencia',
				viewrecords: true,
				height:410,				
				sortorder: "desc"		
			}).navGrid('#paginadorBitacoraPermisosAudiencias',{edit:false,add:false,del:false});
			recargaBitacoraAudiencias=recargaBitacoraAudiencias+1;	
		}
		else{
			jQuery("#gridBitacoraPermisosAudiencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/filtradoBitacoraPermisosAudiencias.do?estado='+estadoGlobal+'&inicio='+fechaIni+'&fin='+fechaFin+'',
			datatype: "xml" });
			$("#gridBitacoraPermisosAudiencias").trigger("reloadGrid");			
		}
		ocultaMuestraGrids("bitacoraAudiencias");
		ajustarGridAlCentro($("#gridBitacoraPermisosAudiencias"));
	}

	// Llenado del grid de bitácora de descargas, a petición de la audiencia -- desarrollo referente a JAVS --
	function gridBitacoraPorAudiencia(numeroAudiencia){
		
		if(recargaBitacoraAudiencias==0){				
			jQuery("#gridBitacoraPermisosAudiencias").jqGrid({ 
				url:'<%=request.getContextPath()%>/filtradoBitacoraPermisosAudiencias.do?audiencia='+numeroAudiencia+'', 
				datatype: "xml", 
				colNames:['Institucion','Audiencia','Puesto', 'Nombre completo del funcionario','Fecha de descarga','Usuario Asignador'], 
				colModel:[ 	{name:'Institucion',		index:'1', width:140}, 
				           	{name:'Audiencia',			index:'2', width:65},
							{name:'Puesto',				index:'3', width:140}, 
							{name:'Nombre',				index:'4', width:230}, 
							{name:'FechaDescarga',		index:'5', width:140},
							{name:'UsuarioAsignador',	index:'7', width:230}
				],
				pager: jQuery('#paginadorBitacoraPermisosAudiencias'),
				rowNum:10,
				rowList:[10,15,20],
				autowidth: true,
				sortname: 'Audiencia',
				viewrecords: true,
				height:410,				
				sortorder: "desc"		
			}).navGrid('#paginadorBitacoraPermisosAudiencias',{edit:false,add:false,del:false});
			recargaBitacoraAudiencias=recargaBitacoraAudiencias+1;	
		}
		else{
			jQuery("#gridBitacoraPermisosAudiencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/filtradoBitacoraPermisosAudiencias.do?audiencia='+numeroAudiencia+'',
			datatype: "xml" });
			$("#gridBitacoraPermisosAudiencias").trigger("reloadGrid");			
		}
		ocultaMuestraGrids("bitacoraAudiencias");
		ajustarGridAlCentro($("#gridBitacoraPermisosAudiencias"));

	}
	
	function cerrarVentanaDetallePermisoAudiencia(){
		$.closeWindow(idFrame);
		gridPermisosAudiencias(estadoGlobal);			
	}
		 
 	function dblClickRowBandejaPermisosAudiencias(rowid, row){
 		perAud   = rowid;
		inst     = row.Institucion;
		aud      = row.Audiencia;
		puesto   = row.Puesto;
		nomFun   = row.Nombre;
		fechSol  = row.FechaSolicitud;		
		fechAsig = row.FechaAsignacion;
		usrAsig  = row.UsuarioAsignador;
		est 	 = estadoGlobal;
		
		idWindowVisorPermisoAudiencia++;
		idFrame = "idWindowVisorAudiencia"+ idWindowVisorPermisoAudiencia;
		$.newWindow({id:"idWindowVisorAudiencia"+ idWindowVisorPermisoAudiencia, statusBar: true, posx:50,posy:111,width:1130,height:500,title:"Atenci&oacute;n de Permisos de Audiencias", type:"iframe"});
	    $.updateWindowContent("idWindowVisorAudiencia"+ idWindowVisorPermisoAudiencia,
	    '<iframe src="<%=request.getContextPath()%>/visorDetallePermisoAudiencia.do?perAud='+perAud+'&inst='+inst+'&est='+est+'&aud='+aud+'&puesto='+puesto+'&usrAsig='+usrAsig+'&nomFun='+nomFun+'&fechSol='+fechSol+'&fechAsig='+fechAsig+'" width="1130" height="500"/>'); 
	}
 	
 	
	/*
	*Funcion para cerrar la ventana de medida cautelar
	*/
	function cerrarVentanaMedidaCautelar(idVentana){
		$.closeWindow(idVentana);
	}

</script>
</head>

<body>

	<!--Comienza ui-layout-west-->
	<div class="ui-layout-west">
		<div class="header">&nbsp;</div>
		<div class="content">
			<div id="accordionmenuprincipal">
			
				<h3 ><a id="evento" href="#" ><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes generadas<br/>en audiencia</a></h3>
				<div>			
					<ul id="seccion1treePJENC" class="filetree">
						<li>
							<span class="file">
								<a onclick="javascript:cargaGridSolProcesoHistoricoPJENC('<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.ABIERTA.getValorId()%>')" style="cursor: pointer;">En proceso</a>
							</span>
						</li>
						<li>
							<span class="file" style="cursor: pointer;" onclick="javascript:cargaGridSolProcesoHistoricoPJENC('<%=EstatusSolicitud.CERRADA.getValorId()%>')" >Hist&oacute;rico</span>
						</li>
					</ul>		
				</div>
				
				<!--  Ojo: las funciones ligadas a mandamientos judiciales, están en el js funcionesComunMandJudYMedCautelares -->
				<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Mandamientos judiciales</a></h3>
				<div>
					<ul id="seccion2treePJENC" class="filetree">
						
							
						<li class="closed">
								<span class="folder" onclick="consultaGeneralMandamientoJudicial(1)">Todos</span>
									<ul>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMandamientoJudicial(1,'<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>')">No atendido</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMandamientoJudicial(1,'<%=EstatusMandamiento.EN_PROCESO.getValorId()%>')">En proceso</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMandamientoJudicial(1,'<%=EstatusMandamiento.CONCLUIDO.getValorId()%>')">Concluido</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMandamientoJudicial(1,'<%=EstatusMandamiento.CANCELADO.getValorId()%>')">Cancelado</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMandamientoJudicial(1,'<%=EstatusMandamiento.ATENDIDO.getValorId()%>')">Atendido</a></span></li>
									</ul>
						</li>
						
						<li class="closed">
								<span class="folder">Por fecha</span>
									<ul>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(1,'<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>')">No atendido</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(1,'<%=EstatusMandamiento.EN_PROCESO.getValorId()%>')">En proceso</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(1,'<%=EstatusMandamiento.CONCLUIDO.getValorId()%>')">Concluido</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(1,'<%=EstatusMandamiento.CANCELADO.getValorId()%>')">Cancelado</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(1,'<%=EstatusMandamiento.ATENDIDO.getValorId()%>')">Atendido</a></span></li>
									</ul>
						</li>
						
						
						<li><span class="file"> <a id="medCautelarXExp"	style="cursor: pointer;" onclick="popUpTipoBusquedaModalXExpediente(1)">Por Causa</a></span></li>
					</ul>
					
				</div>

				<!--  Ojo: las funciones ligadas a medidas cautelares, están en el js funcionesComunMandJudYMedCautelares -->
				<h3>
					<a href="#"><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Medidas cautelares</a>
				</h3>
				<div>
					<ul id="seccion4treePJENC" class="filetree">
						<li class="closed">
								<span class="folder" onclick="consultaGeneralMedidaCautelar(1)">Todas</span>
									<ul>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMedidaCautelar(1,'<%=EstatusMedida.NO_ATENDIDA.getValorId()%>')">No atendida</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMedidaCautelar(1,'<%=EstatusMedida.EN_PROCESO.getValorId()%>')">En proceso</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMedidaCautelar(1,'<%=EstatusMedida.CONCLUIDA.getValorId()%>')">Concluida</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMedidaCautelar(1,'<%=EstatusMedida.CANCELADA.getValorId()%>')">Cancelada</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="consultaGeneralMedidaCautelar(1,'<%=EstatusMedida.ATENDIDA.getValorId()%>')">Atendida</a></span></li>
									</ul>
						</li>
						
						<li class="closed">
								<span class="folder">Por fecha</span>
									<ul>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(2,'<%=EstatusMedida.NO_ATENDIDA.getValorId()%>')">No atendida</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(2,'<%=EstatusMedida.EN_PROCESO.getValorId()%>')">En proceso</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(2,'<%=EstatusMedida.CONCLUIDA.getValorId()%>')">Concluida</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(2,'<%=EstatusMedida.CANCELADA.getValorId()%>')">Cancelada</a></span></li>
										<li><span class="file"><a style="cursor: pointer;" onclick="modalFechaDeMandamientoYMedidas(2,'<%=EstatusMedida.ATENDIDA.getValorId()%>')">Atendida</a></span></li>
									</ul>
						</li>
						
						
						<li><span class="file"> <a id="medCautelarXExp"	style="cursor: pointer;" onclick="popUpTipoBusquedaModalXExpediente(2)">Por	Expediente/Causa</a></span></li>
						
					</ul>
				</div>
				
				<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Permisos de audiencias</a></h3>
				<div>			
					<ul id="seccion7treePJEA" class="filetree">					
						<li>
							<span class="file">
								<a id="perAudConsignado" style="cursor: pointer;" onclick="popUpTipoBusquedaModal('<%=EstatusPermisosAudiencia.CONCEDIDO.getValorId()%>')">Concedido</a>
							</span>
						</li>
						<li>
							<span class="file">
								<a id="perAudCancelado" style="cursor: pointer;"  onclick="popUpTipoBusquedaModal('<%=EstatusPermisosAudiencia.CANCELADO.getValorId()%>')">Cancelado</a>
							</span>
						</li>
						<li>
							<span class="file">
								<a id="perAudSinAsignar" style="cursor: pointer;" onclick="popUpTipoBusquedaModal('<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>')">Sin asignar</a>
							</span>
						</li>
					</ul>						
				</div>
				
				<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Bitacora de descargas</a></h3>
				<div>			
					<ul id="seccion8treePJEA" class="filetree">					
						<li>
							<span class="file">
								<a id="bitAudFecha" style="cursor: pointer;" onclick="popUpTipoBusquedaModalBitacoraFecha()">Por fecha</a>
							</span>
						</li>
						<li>
							<span class="file">
								<a id="bitAud" style="cursor: pointer;" onclick="popUpTipoBusquedaModalBitacoraAudiencia();">Por audiencia</a>
							</span>
						</li>						
					</ul>						
				</div>
<!-- 					se oculta pestaña Estado del Expediente a peticion  /*ByYolo*/				 -->
<%-- 				<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Estado del expediente</a></h3> --%>
<!-- 				<div> -->
<!-- 					<ul id="seccion5treePJENC" class="filetree"> -->
<!-- 						<li> -->
<!-- 							<span class="file"> -->
<!-- 								<a id="estadoExpediente" style="cursor: pointer;" onclick="abreModalCausaEstadoExpediente();">Por causa</a> -->
<!-- 							</span> -->
<!-- 						</li> -->
<!-- 					</ul>	 -->
<!-- 				</div> -->
				
				<h3><a href="#"><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Sentencia</a></h3>
				<div>
					<ul id="seccion6treePJENC" class="filetree">
						<li>
							<span class="file">
								<a id="sentenciaEnProceso" style="cursor: pointer;" onclick='cargaGridExpedientesPorEstatus("estatus","fechaini","fechafin")'>Consultar Sentencias</a>
								<!--<a id="sentenciaEnProceso" style="cursor: pointer;" onclick="javascript:cargaGridSentencias('<%=EstatusExpediente.ABIERTO.getValorId()%>')">En proceso</a>-->
							</span>
							<!--<span class="file">-->
								<!--<a id="sentenciaFueraDeProceso" style="cursor: pointer;" onclick="javascript:cargaGridSentencias('<%=EstatusExpediente.CERRADO.getValorId()%>,<%=EstatusExpediente.NO_ATENDIDO.getValorId()%>')">Fuera de proceso</a>-->
							<!--</span>-->
						</li>				
					</ul>	
				</div>
				
				<h3 ><a id="even" href="#" ><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes</a></h3>
				<div>			
					<ul id="seccion34treePJEA" class="filetree">
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="popUpTipoBusqueda('expediente')">N&uacute;mero de expediente</a></span></li>
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="popUpTipoBusqueda('fecha')">Por fecha</a></span></li>
					</ul>		
				</div>
				<!-- Enable IT cambio de etiqueta Solicitud Accion Penal Privada por Solicitud Externa -->
				<h3 ><a id="evem" href="#" ><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="imgAccPenal" width="15" height="15">Solicitud Externa</a></h3>
				<div>			
					<ul id="seccionAccPenaltreePJENC" class="filetree">
						<li><span class="file"><a id="accPenalPrivadaPorAtender" style="cursor: pointer;" onclick="cargaGirdSolicitudesAccPenalPrivadaPJENC('porAtender');">Por atender</a></span></li>
						<li><span class="file"><a id="accPenalPrivadaAtendida" style="cursor: pointer;" onclick="cargaGirdSolicitudesAccPenalPrivadaPJENC('atendidas');">Atendidas</a></span></li>
					</ul>		
				</div>
<!-- 			<h3 ><a id="" href="#" onclick="generaVisorGraficaView()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gráficas y reportes</a></h3>
				<div></div> -->
			</div>
		</div>
	</div>
	<!--Fin ui-layout-west-->

	<!--Comienza ui-layout-east-->
	<div class="ui-layout-east">
		<div class="header">Bienvenido</div>
		<div class="content">
			<div id="accordionmenuderprincipal">
				<h4><a href="#">Datos Personales</a></h4>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
					</center>
				</div>
				<h6><a href="#">Agenda</a></h6>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
					</center>
					<br />
				</div>
				<h6><a href="#" id="" onclick="visorLeyesCodigos()">Consultar Leyes y C&oacute;digos</a></h6>
				<div></div>
				<h4>
					<a href="#">Chat</a>
				</h4>
				<div align="center">
				
					<div id="dialogoChat" title="Chat" align="center">
						<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
					</div>
					<center>
						<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
					</center>
						<!--iframe src="http://gaby1:5280/web/jwchat/index.html" frameborder="0" width="200" height="200" scrolling="no"></iframe  -->
				</div>
				<h4><a href="#" onclick="consultarTiposRol();">Facultades</a></h4>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--Termina ui-layout-east-->

	<!--Comienza ui-layout-north-->
	<div class="ui-layout-north">
		<div class="content">
				<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg" height="100%">
				  <TBODY>
					  <TR>
					    <TD width=100 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_gral.png"></TD>
                        <TD width=301  align=left valign="middle">
                        	<div class='nombreInstitucion'><%=rolDTO.getDescripcionRol()%></div>
                        	<div class='versionCodigo'>${nsjp.version.codigo}</div>
                        </TD>
					    <TD width=126 align=left valign="top"><SPAN></SPAN></TD>
					    <TD width=272 align=center valign="top">&nbsp;</TD>
					    <TD width=28 align=middle>&nbsp;</TD><!--<td width="150" align="center"></td>-->
					    <TD width=380 align="right" valign="middle">
					      <table width="362" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <td width="165"><table width="141" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					              <td width="53" align="right" class="txt_menu_top">&nbsp;</td>
					              <td width="157">&nbsp;</td>
					            </tr>
					          </table></td>
					          <td width="103"><table width="89" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					            	<td width="107" class="txt_menu_top">Cerrar sesi&oacute;n</td>
					            	<td width="42" class="txt_menu_top"><a href="#" onclick='$("#dialog-logout").dialog( "open" );'><img src="<%=request.getContextPath()%>/resources/images/btn_cerrar.png" width="29" height="26" border="0"></a></td>
					              </tr>
					          </table>
					            <label for="textfield"></label></td>
					          <td width="204"><table width="89" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					              <td width="47">Ayuda</td>
					              <td width="42"><a href="#"><img src="<%=request.getContextPath()%>/resources/images/btn_ayuda.png" width="29" height="26" border="0"></a></td>
					            </tr>
					          </table></td>
					        </tr>
					      </table>
					      <table width="363" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <td width="363" align="right" valign="middle"><TABLE border=0 cellSpacing=0 cellPadding=0 width="300" height="100%">
					            <TBODY>
					              <TR>          
					              <TR vAlign=top>
					                <TD width=128 align=right valign="middle">&nbsp;</TD>
					                <TD width=150 align="right" valign="middle"><DIV id=liveclock></DIV></TD>
					                <TD width=10 align="right" valign="middle"><IMG alt="Icono reloj" src="<%=request.getContextPath()%>/resources/images/icn_reloj.png" width=26 height=25></TD>
					              </TR>
					            </TBODY>
					          </TABLE></td>
					        </tr>
					  </table>
					  </TD>
					  </TR>
				  </TBODY>
			  </TABLE>	
		</div>
	
		<!--comienza barra de herramientas-->
		<ul class="toolbar">
			<div id="menu_head">
				<li id="tbarBtnHeaderZise" class="first"><span></span></li>
				<!--<li id="accionPenalPrivada" ><span></span>Accion Penal Privada</li>-->
			</div>
			<div id="menu_config">
				<li id="generarDocumento"><span></span>Generar Documento&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctowri.png" width="15" height="16"></li>
				<li><span></span>Adjuntar documento&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctoadjun.png" width="10" height="16"></li>
			</div>
		</ul>
		<!--termina barra de herramientas-->
	</div>
	<!--Termina ui-layout-north-->

	<!--Comienza ui-layout-south-->
	<div class="ui-layout-south">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pleca_bottom">
		    <tr>
				<!-- <td height="15">&nbsp;</td>  -->
				<td height="15" align="center" style="border-left:#FFFFFF; border-top:#FFFFFF;">
					<label id="ambienteLb" style="color:#FBFBEF"></label>	
				</td>
		  </tr>
		</table>
		<div id="pie" align="center" style="BACKGROUND-COLOR: #e7eaeb; BACKGROUND-POSITION: center top; COLOR: #58595b">
			<div id="footer" style="PADDING-BOTTOM: 5px; PADDING-LEFT: 5px; WIDTH: 720px; PADDING-RIGHT: 5px; PADDING-TOP: 5px">
				Direcci&oacute;n de la Instituci&oacute;n
			</div>
		</div>
	</div>
	<!--Termina ui-layout-south-->

	<!--Comienza main content-->
	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					
					<!--Comienzan los divs para mostrar los diferentes Grids-->

					<div id="divGridMedidasCautelares" style="display: none;">
						<table id="gridMedidasCautelares"></table>
						<div id="pagerGridMedidasCautelares"></div>
					</div>

					<div id="divGridSolicitudesAccPenalPrivadaPJENC">
						<table id="gridSolicitudesAccPenalPrivadaPJENC"></table>
						<div id="pagerGridSolicitudesAccPenalPrivadaPJENC"></div>
					</div>

					<div id="divGridAudienciasProcesoHistorico">
						<table id="gridAudienciasPJENC"></table>
						<div id="pagerAudienciasPJENC"></div>
					</div>

					<div id="divGridMandamientosJudiciales" style="display: none;">
					 	<table id="gridMandamientosJudiciales"></table>
						<div id="pagerGridMandamientosJudiciales"></div>
					</div>

					<div id="divGridEstadoExpedientePJENC">
						<table id="gridEstadoExpedientePJENC"></table>
						<div id="pagerGridEstadoExpedientePJENC"></div>
					</div>

					<div id="divGridSentenciasEnProcesoPJENC">
<!--						<table id="gridSentenciasEnProcesoPJENC"></table>-->
<!--						<div id="pagerGridSentenciasEnProcesoPJENC"></div>-->
						<table id="gridExpedientesPorEstatus"></table>
						<div id="pagerGridExpedientesPorEstatus"></div>
					</div>

					<div id="divGridExpedientesDocumentoPJATP">
						<table id="gridExpedientesDocumentoPJATP"></table>
						<div id="paginadorExpedientesDocumentoPJATP"></div>
					</div>

					<div id="divGridPermisosAudiencias">
						<table id="gridPermisosAudiencias"></table>
						<div id="paginadorPermisosAudiencias"></div>
					</div>

					<div id="divGridBitacoraPermisosAudiencias">
						<table id="gridBitacoraPermisosAudiencias"></table>
						<div id="paginadorBitacoraPermisosAudiencias"></div>
					</div>

					<!--Terminan los divs para mostrar los diferentes Grids-->
				</div>
			</div>
		</div>
	</div>
	<!--Termina main content-->

	<!--Comienza div para mostrar la ventana para ingresar las fechas-->
	<div id="busquedaFecha" style="display: none">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="153">&nbsp;</td>
				<td width="153">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><strong>Fecha Inicio:</strong> <input type="text" id="fechaInicio" size="20" /></td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><strong>Fecha Fin:&nbsp;&nbsp; </strong>&nbsp; <input type="text" id="fechaFin" size="20" />
				</td>
			</tr>
		</table>
	</div>
	<!--Termina div para mostrar la ventana para ingresar las fechas-->

	<!--Comienza div para mostrar la ventana para ingresar el numero de causa en mandamientos judiciales y medidas cautelares-->
	<div id="divBusquedaExpediente" style="display: none">
		<table width="400" cellspacing="0" cellpadding="0" height="150">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="300">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><strong>Ingrese el n&uacute;mero de causa: </strong></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<!--  numeroExpediente equals numeroCaso, se dejo así, porque se factorizaron funciones comúnes a agentemp y
					  encargadoCausa en funcionesComunMandJudYMedCautelares.js -->
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="numeroExpediente" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>

	<!--Termina div para mostrar la ventana para ingresar el numero de causa en mandamientos judiciales-->

	<!--Comienza div para mostrar la ventana para ingresar el numero de causa-->
	<div id="divCausa" style="display: none">
		<table width="300" cellspacing="0" cellpadding="0">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="308">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><strong>Ingrese el n&uacute;mero de causa: </strong></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="datoCausa"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>

	<!--Comienza div para mostrar la ventana para ingresar el número de audiencia-->
	<div id="divAudiencia" style="display: none">
		<table width="300" cellspacing="0" cellpadding="0">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="308">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><strong>Ingrese el n&uacute;mero de audiencia: </strong></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="datoAudiencia"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>

	<!--Termina div para mostrar la ventana para ingresar el numero de causa-->

	<!--Comienza div para mostrar la ventana modal para ver el pdf de mandamiento judicial-->
	<div id="divPdfMandamientoJudicial" style="display: none">
		<table width="400" cellspacing="0" cellpadding="0">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="308" align="center"><strong>Mandamiento Judicial</strong></td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><strong>Estado Actual:</strong></td>
				<td>&nbsp;</td>
			</tr>
            <tr>
			  <td>&nbsp;</td>
				<td align="center">
                	<input type="text" id="estadoActualMandamientoJud" style="width: 200px; border: 0; 
                	
                	background: #DDD;" readonly="readonly" />
                </td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="left"><strong> Nuevo  estado:</strong></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><select name="estadoMandamientoJudPJENC" id="estadoMandamientoJudPJENC" style="width:200px;">
				  <option value="0">-Seleccione-</option>
				  <option value="<%=EstatusMandamiento.CANCELADO.getValorId() %>">Cancelado</option>
				  <option value="<%=EstatusMandamiento.EJECUTADO.getValorId() %>">Ejecutado</option>
		      </select></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--Termina div para mostrar la ventana para ingresar el numero de causa en mandamientos judiciales-->

	<!--Comienza div para mostrar la ventana para ingresar el numero de causa-->
	<div id="divCausaExpediente" style="display: none">
		<table width="300" cellspacing="0" cellpadding="0">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="308">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="justify"><strong>Ingrese el n&uacute;mero de causa: </strong></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="datoCausaExpediente"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--Termina div para mostrar la ventana para ingresar el numero de causa-->
	<div id="dialog-logout" title="Logout">
		<p align="center">
			<span id="logout">¿Desea cerrar su sesi&oacute;n?</span>
		</p>
	</div>
	
	<!-- Cuadros de dialogo para buscar expedientes por numero de expediente y por fecha -->
	<div id="tiposBusquedaExpedienteid"  style="display: none;"> 
	
		<div id="tiposBusquedaExpediente"  style="display: none;"> 
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="120">
						<bean:message key="numeroDeCausa"/>
					</td>
					<td width="153">
						<input type="text" id="buscapornumexp" size="28" maxlength="32"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="tiposBusquedafecha"  style="display: none;">
		
			<table cellspacing="0" cellpadding="0" >
				<tr>
					<td width="153">&nbsp;</td>
					<td width="153">&nbsp;</td>
				</tr>
				<tr>
				  <td colspan="2" align="center">
				  	<strong>Fecha Inicio:</strong>
				  	<input type="text" id="buscaporfechaIni" size="15" readonly="readonly"/>
				  </td>
			    </tr>
				<tr>
				  <td align="center">&nbsp;</td>
				  <td align="center">&nbsp;</td>
		  		</tr>
				<tr>
				  <td colspan="2" align="center">
				  	<strong>Fecha Fin:&nbsp;&nbsp;</strong>&nbsp;
			      	<input type="text" id="buscaporfechaFin" size="15" readonly="readonly"/>
			      </td>
		  		</tr>
				<tr>
				  <td align="center">&nbsp;</td>
				  <td align="center">&nbsp;</td>
		  		</tr>
			</table>
			
		</div>
		
	</div>
		    
	<!--Div para ventana modal del tipo de mandamieto-->
	<div id="divTipoMandamiento" style="display: none">
	
		<table width="400" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20">&nbsp;</td>
	        <td colspan="2" align="center">
	        	<strong>Tipo de Mandamiento Judicial</strong>
	       	</td>
	        <td width="20">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td width="160" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Tipo de Mandamiento:</strong>
	       	</td>
	        <td>
	        	<select id="tipoMandamiento" style="width: 200px;">
	              <option value="0">-Seleccione-</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Nombre del Imputado:</strong>
	       	</td>
	        <td>
	        	<select id="nombreDelImputado" style="width: 200px;">
	          		<option value="0">-Seleccione-</option>
	        	</select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiTipoSentencia">
		        	<strong>Tipo de Sentencia:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divCbxTipoSentencia">
	                <select id="tipoSentencia" style="width: 200px;">
	                    <option value="0">-Seleccione-</option>
	                </select>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiFechaInicioSentencia">
	        		<strong>Fecha Inicio:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divFechaInicioSentencia">
	        		<input type="text" id="fechaInicioSentencia" style="width: 100px;"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiFechaFinSentencia">
	        		<strong>Fecha Fin:</strong>
	        	</div>
	       	</td>
	        <td>
	        	<div id="divFechaFinSentencia">
	        		<input type="text" id="fechaFinSentencia" style="width: 100px;"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	    
	</div>	    

	<!-- dialogos para Bloqueo de pantalla-->
	<div id="dialog-bloqueo" title="Bloqueo Sesi&oacute;n"  style="display: none;">
		<p align="center">
			<table border="0">
				<tr>
					<td colspan="2">La sesi&oacute;n se ha bloqueado, introduce tu contraseña para desbloquear.</td>
					
				</tr>
				<tr>
					<td align="right"><label style="color:#4A5C68">Contraseña:</label></td>
					<td><input type="password" name="password" id="password" value="" maxlength="15" size="20"></td>
				</tr>
				<tr id="captchaJPG" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
                    </td>
	                <td>
	                	<img id="imgcaptcha" src="<%=request.getContextPath()%>/kaptcha.jpg">
	                </td>
	            </tr>
	            <tr id="captchaTXT" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
	             	</td>
	                <td>
	                   	<input type="text" id="scaptcha" name="scaptcha" value="" maxlength="15" size="20">
	                   	<input type="hidden" name="captcha" value='<%= request.getAttribute("captcha")%>'>
	                </td>
	            </tr>
			</table>
		</p>
	</div>
	<div id="dialogBlok" title="¡Su sesi&oacute;n est&aacute; a punto de caducar!">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
				La sesi&oacute;n se cerrara en <span id="dialog-countdown" style="font-weight:bold"></span> segundos.
			</p>

			<p>¿Desea continuar con la sesi&oacute;n?</p>
	</div>
	<!-- FIN dialogos para las alarmas -->
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>
	
	<!-- INCLUDE PARA ALARMAS -->
	<jsp:include page="/WEB-INF/paginas/agenda/alarmas.jsp" flush="true"></jsp:include>
	 
	</body>
</html>