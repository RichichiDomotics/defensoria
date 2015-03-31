<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>


<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<html>
<head>

<!--COMIENZA CSS DEL DOCUMENTO-->
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<!--css para el estilos de jquery-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	
	<!--css para estilo de los arboles-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	
	<!--estilo ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	
	<!--estilo del grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	
	
<!--COMIENZAN SCRIPTS-->
	
	<!--jquery-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	
	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	
	<!--para creacion de arboles-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	
	<style type="text/css">
		.ui-jqgrid tr.jqgrow td {
				    white-space: normal;
				    height:auto;
				    vertical-align:text-middle;
				    text-align:center;
				    padding-top:2px;
				}
								
		.ui-jqgrid .ui-jqgrid-htable th.ui-th-column {
				    white-space: normal;
				    height:auto;
				    vertical-align:text-middle;
				    text-align:center;
				    padding-top:2px;
				}		
				
		#gview_girdSolicitudAudienciaPJEA .ui-jqgrid-htable TH DIV {
				    height:auto;
				}				
		#gview_gridAudienciaDelDiaPJENA .ui-jqgrid-htable TH DIV {
				    height:auto;
				}		
	</style>

<script type="text/javascript">
	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();

	var outerLayout, innerLayout;
	/*
	*variable para controlar los id´s del visor de detalle de audiencia
	*cuando se da click el audiencias -> del dia o audiencias -> por fecha
	*/		
	var idWindowVisorAudiencia = 1;
	/*
	*variable para controlar los id´s del visor de solicitud de audiencia
	*cuando se da click en solicitudes -> Audiencia -> Dbl click en el grid
	*/
	var idWindowVisorSolicitudesAudienciaPJENA = 1;
	/*
	*variable para controlar los id´s del visor de solicitud de audiencia
	*cuando se da click en solicitudes -> Otras -> Dbl click en el grid
	*/
	var idWindowVisorSolicitudesOtrasPJENA = 1;

	//Variable para controlar el id del visor de codigos y leyes
	var idWindowVisorLeyesYCodigos = 1;

	//Variable para las gráficas y reportes
	var idWindowGraficasView=1;
	var idWindowVisorAudiencia=0;
		
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);
		
		
		$("#complejidadAudiencia").css("display", "none");
		$("#presentarInvolucrado").css("display", "none");
		
		
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
		
		
		$('#imageViewer').click(generaVisorGraficaView);
		
		outerLayout = $("body").layout( layoutSettings_Outer );

		//crea el acordeon
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		//crea el arbol de eventos
		$("#seccion1treePJEA").treeview();
		//crea el arbol de casos
		$("#seccion2treePJEA").treeview();
		//crea el arbol de audiencias
		$("#seccion3treePJEA,#seccion34treePJEA,#seccionAudienciaCanceladasPJEA").treeview();
				
		//Carga el arbol de casos
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
		
		$( "#dialog-logout" ).dialog({ 
			autoOpen: false,
			resizable: false,
			modal: true,
//			height:'auto',
//			width:'auto',
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
		
		//manda a llamar la pantalla de complejidad de audiencia
		
		$('#complejidadAudiencia').click(ventanaComplejidad);
		$('#presentarInvolucrado').click(ventanaPresentacion);

		/*
		*variable para controlar si el grid de consultar audiencias agendadas por dia
		*se carga por primera vez
		*/ 
		primeraVezGridDia = true;

		/*
		*variable para controlar si el grid de consultar audiencias agendadas por fecha
		*se carga por primera vez
		*/ 
		primeraVezGridFecha = true;
		
		/*
		* variable para controlar si el grid de consultar audiencias canceladas
		*/
		primeraVezGridAudienciasCanceladas = true;


		primeraGridExpedientesDocumentoPJATP = true;
		/*
		*variable que controla si la fecha se ingreso de manera correcta
		*por default es false
		*/
		validaFecha = false;
		
		/*
		*Funcion que carga el grid, por default con las nuevas solicitudes de AUDIENCIA 
		*/
		jQuery("#girdSolicitudAudienciaPJEA").jqGrid({
			
			url:'<%= request.getContextPath()%>/consultarSolicitudesDeAudienciaPJENA.do',
			datatype: "xml",
			autowidth: true, 
			colNames:[
				'<bean:message key="numeroDeCaso" />',
				'<bean:message key="numeroDeCausa" />',
				'<bean:message key="caracter" />',
				'<bean:message key="tipoDeAudiencia" />',
				'<bean:message key="fechaHoraSolicitud" />',
				'<bean:message key="institucionSolicitante" />',
				'<bean:message key="solicitante" />',
				'<bean:message key="fechaHoraLimite" />'
			], 
			colModel:[ 	{name:'caso',index:'caso', align:"center",width:180},
						{name:'expediente',index:'expediente', align:"center",width:220}, 
						{name:'caracter',index:'caracter', align:"center",width:80}, 
						{name:'tipoAudiencia',index:'tipoAudiencia', align:"center",width:180}, 
						{name:'fechaHoraSolicitud',index:'fechaHoraSolicitud', align:"center",width:150},
						{name:'institucionSolicitante',index:'institucionSolicitante', align:"center"},
						{name:'solicitante',index:'solicitante', align:"center"},											
						{name:'fechaHoraLimite',index:'fechaHoraLimite', align:"center"}
																	
					],
			pager: jQuery('#pager1'),
			rowNum:10,
			rowList:[10,20,30],
			width:870,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					mostrarVisorSolicitudAudienciaPJENA(rowid);
					}
		}).navGrid('#pager1',{edit:false,add:false,del:false});	
		$("#gview_girdSolicitudAudienciaPJEA .ui-jqgrid-bdiv").css('height', '450px');

		//carga el reloj
		muestraGadgets();
	
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


		//llama la funcion que crea la ventana de buscar expediente	
		$("#buscarExpediente").click(buscarExpediente);
					
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
	});
	//Fin OnReady

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
		,	onresize_end:			function () { $("#girdSolicitudAudienciaPJEA").setGridWidth($("#mainContent").width() - 5, true);
												  $("#gridSolicitudOtrosPJEA").setGridWidth($("#mainContent").width() - 5, true);
												  $("#gridExpedientesPJENA").setGridWidth($("#mainContent").width() - 5, true);
												  $("#gridAudienciaDelDiaPJENA").setGridWidth($("#mainContent").width() - 5, true);
												  $("#gridFechaPJATP").setGridWidth($("#mainContent").width() - 5, true);
												}		
		}
	};


	/*
	*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function mostrarVisorSolicitudAudienciaPJENA(rowID){
		//Variable para controlar el id de las ventanas
		idWindowVisorSolicitudesAudienciaPJENA++;
		$.newWindow({id:"iframewindowAtenderSolicitudAudiencia"+idWindowVisorSolicitudesAudienciaPJENA, statusBar: true, posx:255,posy:111,width:1150,height:900,title:"Atender solicitud de audiencia", type:"iframe"});
    	$.updateWindowContent("iframewindowAtenderSolicitudAudiencia"+idWindowVisorSolicitudesAudienciaPJENA,'<iframe src="<%=request.getContextPath()%>/visorSolicitudAudienciaPJENA.do?idEvento=' + rowID +'" width="1150" height="450" />'); 
    	 $("#" +"iframewindowAtenderSolicitudAudiencia"+idWindowVisorSolicitudesAudienciaPJENA+ " .window-maximizeButton").click();
    }
    

	/*
	*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function visorSolicitudPJENA(rowID){
		idWindowVisorSolicitudesOtrasPJENA++;
		$.newWindow({id:"iframewindowSolicitudPJENA"+idWindowVisorSolicitudesOtrasPJENA, statusBar: true, posx:255,posy:111,width:1024,height:443,title:"Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowSolicitudPJENA"+idWindowVisorSolicitudesOtrasPJENA,'<iframe src="<%=request.getContextPath()%>/visorSolicitudPJENA.do?idEvento=' + rowID +'" width="1024" height="443" />'); 
	}
	
	
	/*
	*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function visorExpedientesPJENA(rowID){
		$.newWindow({id:"iframewindowExpedientesPJENA", statusBar: true, posx:255,posy:111,width:1140,height:400,title:"Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowExpedientesPJENA",'<iframe src="<%=request.getContextPath()%>/visorExpedientesPJENA.do?idEvento=' + rowID +'" width="1140" height="400" />'); 
	}	
	
	
	/*
	*Funcion que llama a la funcionalidad para crear la agenda 
	*/
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();
	}

	/*
	*Funcion que llama a la funcionalidad para configurar complejidad 
	*/
	function ventanaComplejidad() {
		$.newWindow({id:"iframewindowGeneraComplejidad", statusBar: true, posx:255,posy:111,width:405,height:400,title:"Configurar Complejidad de Audiencia", type:"iframe"});
	    $.updateWindowContent("iframewindowGeneraComplejidad",'<iframe src="<%=request.getContextPath()%>/generarComplejidadAudiencia.do" width="1140" height="400" />');	
	}

	
	/*
	*Funcion que llama a la funcionalidad para configurar complejidad 
	*/
	function ventanaPresentacion() {
	    customVentana("iframePresentacion", "Presentaci&oacute;n", "/presentarInvolucrado.do");
	}

	/*
	*Funcion que crea el visor buscar Expediente
	*/						
	function buscarExpediente() {
		$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Causa", type:"iframe"});
    	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do?rolUsuario=admonPJ" width="653" height="400" />');		
	}


	/*
	*Funcion que agrega los casos dinamicamente 
	*/
	function cargaCasosPJENA(){
		
    	var branches = "";
    	
    	$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/BusquedaInicialCaso.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var branches = "";
				$(xml).find('caso').each(function(){
					var casoId = $(this).find('casoId').text();
					var numeroGralCaso = $(this).find('numeroGeneralCaso').text();
    				branches ="<ul><li class='closed' id='" + casoId + "CASO' onclick='agregaExpedientePJENA(" + casoId + ")'><span class='folder'>" + numeroGralCaso + "</span><ul></ul></li></ul>";
					var casosPJEA = $(branches).appendTo("#casosPJEA");
					$("#seccion2treePJEA").treeview({
    					add: casosPJEA
    				});
    			});
    		}
    		
    	});
    }

	//variable que indica si el caso esta abierto
	var casoAbierto = Array();

	/*
	*Funcion que agrega los expedientes dinamicamente 
	*al arbol de casos, de acuerdo al caso seleccionado
	*y le agrega la funcion verEventosPorExpediente
	*/
	function agregaExpedientePJENA(idCaso) {	
				
		if (casoAbierto[idCaso ] != true) {
			$.ajax({
	    		type: 'POST',
	    		url: '<%= request.getContextPath()%>/BusquedaCaso.do',
	    		data: 'idCaso=' + idCaso,
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	    			$(xml).find('expediente').each(function(){
	    				var branches = $("<ul><li id='" + $(this).find('numeroExpedienteId').text() + "EXP' onclick='cargaGridSolicitudesExpedientePJENA(" + $(this).find('numeroExpedienteId').text() + ",\"\")'><span class='file'>" + $(this).find('numeroExpediente').text() + "</span><ul></ul></li></ul>").appendTo("#" + idCaso + "CASO");
	    				$("#" + idCaso + "CASO").treeview({
	    					add: branches
	    				});
		    		});
	    		}		    		
	    	});
		}
		casoAbierto[idCaso] = true;
	}


	/*
	*Funcion que recarga el grid de audiencia
	*es llamada en la hoja audiencia del arbol
	*derecho de solicitudes
	*/
	function recargarGridAudienciaPJENA(){
		//Recarga el grid
		$("#girdSolicitudAudienciaPJEA").trigger("reloadGrid");
		//oculta los otros grids
		$("#divGridExpedientesPJEA").hide();
		$("#divGridSolicitudOtroPJEA").hide();
		$('#divGridAudienciaDelDiaPJENA').hide();
		$('#divGridFechasPJATP').hide();
		//muestra el grid de solicitud de audiencia
		$("#divGridSolicitudAudienciaPJEA").show();		
		$('#divGridExpedientesDocumentoPJATP').hide();
		$('#divGridAudienciasCancenladasPJATP').hide();
	}

	
	/*
	*Funcion que carga el grid con los datos de la consulta de solicitud de otros
	*/
	function cargaGridOtrasSolicitudesPJENA(){
		
		jQuery("#gridSolicitudOtrosPJEA").jqGrid({
			
			url:'<%= request.getContextPath()%>/consultarSolicitudesOtroPJENA.do', 
			datatype: "xml", 
			colNames:[
				'<bean:message key="numeroDeCaso" />',
				'<bean:message key="numeroDeCausa" />',
				'<bean:message key="solicitud" />',
				'<bean:message key="fechaHoraSolicitud" />',
				'<bean:message key="institucionSolicitante" />',
				'<bean:message key="solicitante" />',
				'<bean:message key="estado" />',		
			], 
			colModel:[ 	{name:'caso',index:'caso',align:"center",width:180},
						{name:'expediente',index:'expediente',align:"center",width:220}, 
						{name:'solicitud',index:'solicitud',align:"center",width:180}, 
						{name:'fechaHoraSolicitud',index:'fechaHoraSolicitud',align:"center",width:180},
						{name:'institucionSolicitante',index:'institucionSolicitante',align:"center",width:150},
						{name:'solicitante',index:'solicitante',align:"center",width:130},											
						{name:'estado',index:'estado',align:"center",width:110}
						],
			pager: jQuery('#pager2'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			width:870,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					visorSolicitudPJENA(rowid);
				}
		}).navGrid('#pager2',{edit:false,add:false,del:false});	
		$("#gview_gridSolicitudOtrosPJEA .ui-jqgrid-bdiv").css('height', '450px');

		//oculta el grid de solicitud de audiencias
		$('#divGridSolicitudAudienciaPJEA').hide();		
		//oculta el grid de expedientes
		$('#divGridExpedientesPJEA').hide();
		$('#divGridAudienciaDelDiaPJENA').hide();
		$('#divGridFechasPJATP').hide();
		//Muestra el grid de otras solicitudes
		$('#divGridSolicitudOtroPJEA').show();
		$('#divGridExpedientesDocumentoPJATP').hide();
		$('#divGridAudienciasCancenladasPJATP').hide();
	}

	
	//CHECAR FUNCIONAMIENTO CUANDO SE ENCUENTRE LISTO EL SERVICIO
	/*
	*Funcion que recarga el grid de acuerdo a la consulta de un expediente
	*es decir, consulta el historico de eventos del expedienta seleccionado
	*/
	function cargaGridSolicitudesExpedientePJENA(numeroExpedienteId){
		
		jQuery("#gridExpedientesPJENA").jqGrid({

			url:'<%=request.getContextPath()%>/consultaEventosPorExpedientePJATP.do?numeroExpedienteId='+numeroExpedienteId +'', 
			datatype: "xml", 
			colNames:[		
				'<bean:message key="numeroDeCausa" />',
				'<bean:message key="solicitud" />',
				'<bean:message key="fechaHoraSolicitud" />',
				'<bean:message key="institucionSolicitante" />',
				'<bean:message key="solicitante" />',
				'<bean:message key="estado" />'
			], 
			colModel:[ 
						{name:'expediente',index:'expediente', width:127}, 
						{name:'solicitud',index:'solicitud', width:127}, 
						{name:'fechaHoraSolicitud',index:'fechaHoraSolicitud', width:127},
						{name:'institucionSolicitante',index:'institucionSolicitante', width:127},
						{name:'solicitante',index:'solicitante', width:127},											
						{name:'estado',index:'estado', width:127}
																	
					],
			pager: jQuery('#pager3'),
			rowNum:10,
			rowList:[25,50,100],
			autowidth: false,
			width:767,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					visorExpedientesPJENA(rowid);
					}
			}).navGrid('#pager3',{edit:false,add:false,del:false});	
		$("#gview_gridExpedientesPJENA .ui-jqgrid-bdiv").css('height', '450px');	
		
		//oculta el grid de otras solicitudes
		$('#divGridSolicitudOtroPJEA').hide();
		//oculta el grid de solicitud de audiencias
		$('#divGridSolicitudAudienciaPJEA').hide();
		$('#divGridAudienciaDelDiaPJENA').hide();
		$('#divGridFechasPJATP').hide();
		//muestra el grid de expedientes
		$('#divGridExpedientesPJEA').show();
		$('#divGridExpedientesDocumentoPJATP').hide();
		$('#divGridAudienciasCancenladasPJATP').hide();
		
	}

	///////////////////COMIENZA FUNCIONALIDAD PARA CONSULTAR AUDIENCIAS AGENDADAS///////////////////////////////////////////
	/*
	*Funcion que carga el grid de audiencias agendadas del dia
	*/
	function cargaGridDiaPJATP(){

			if(primeraVezGridDia == true){
				
				jQuery("#gridAudienciaDelDiaPJENA").jqGrid({ 
					url:'<%= request.getContextPath()%>/consultarAudienciaDelDiaPJENS.do',
					datatype: "xml",
					autowidth: false,
					colNames:[
						'<bean:message key="numeroDeCaso" />',
						'<bean:message key="numeroDeCausa" />',
						'<bean:message key="caracter" />',
						'<bean:message key="tipoDeAudiencia" />',
						'<bean:message key="fechaDeAudiencia" />',
						'<bean:message key="horaDeAudiencia" />',
						'<bean:message key="sala" />',
						'<bean:message key="estado" />'
					], 
					colModel:[ 	{name:'numeroCaso',index:'numeroCaso', width:180,align:'center'},
								{name:'expediente',index:'expediente', width:180,align:'center'}, 
								{name:'caracter',index:'caracter', width:80,align:'center'}, 
								{name:'tipoAudiencia',index:'tipoAudiencia', width:100,align:'center'},
								{name:'fechaAudiencia',index:'fechaAudiencia', width:80,align:'center'}, 
								{name:'horaAudiencia',index:'horaAudiencia', width:150,align:'center'},
								{name:'sala',index:'sala', width:100,align:'center'},
								{name:'estatus',index:'estatus', width:100,align:'center'}
							],
					pager: jQuery('#pager4'),
					rowNum:10,
					rowList:[10,20,30],
					width:860,
					sortname: 'detalle',
					viewrecords: true,
					sortorder: "desc",
					ondblClickRow: function(idRow) {
						dblClickRowBandejaAudiencias(idRow);
					}
				}).navGrid('#pager4',{edit:false,add:false,del:false});

				$("#gview_gridAudienciaDelDiaPJENA .ui-jqgrid-bdiv").css('height', '450px');
				primeraVezGridDia=false;
			}
			else{
				jQuery("#gridAudienciaDelDiaPJENA").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarAudienciaDelDiaPJENS.do',datatype: "xml" });
				$("#gridAudienciaDelDiaPJENA").trigger("reloadGrid");
			}
			
			$('#divGridSolicitudAudienciaPJEA').hide();
			$('#divGridSolicitudOtroPJEA').hide();
			$('#divGridExpedientesPJEA').hide();
			$('#divGridFechasPJATP').hide();
			$('#divGridAudienciaDelDiaPJENA').show();
			$('#divGridExpedientesDocumentoPJATP').hide();
			$('#divGridAudienciasCancenladasPJATP').hide();
	}

	/*
	*Funcion que carga el grid de consulta por fechas
	*/
	function cargaGridFechaPJATP(){

		 
		  var fechaInicio = $('#fechaInicio').val();
		  var fechaFin = $("#fechaFin").val();				  

		  if (validaFecha==true){
															  
			  if(primeraVezGridFecha == true){
				  
				  jQuery("#gridFechaPJATP").jqGrid({ url:'<%= request.getContextPath() %>/buscarAudienciasPorFecha.do', 						
						datatype: "xml", 
						mtype: 'POST',						
						postData: {fechaInicio: function()  {return fechaInicio;},
						           fechaFin: function()		{return fechaFin;}																										
						},
						colNames:[
							'<bean:message key="numeroDeCaso" />',
							'<bean:message key="numeroDeCausa" />',
							'<bean:message key="caracter" />',
							'<bean:message key="tipoDeAudiencia" />',
							'<bean:message key="fechaDeAudiencia" />',
							'<bean:message key="horaDeAudiencia" />',
							'<bean:message key="sala" />',
							'<bean:message key="estado" />'
						], 
						colModel:[ 	{name:'numeroCaso',index:'numeroCaso',width:180,align:'center'},
									{name:'expediente',index:'expediente', width:220,align:'center'}, 
									{name:'caracter',index:'caracter', width:80,align:'center'}, 
									{name:'tipoAudiencia',index:'tipoAudiencia', width:150,align:'center'},
									{name:'fechaAudiencia',index:'fechaAudiencia', width:80,align:'center'}, 
									{name:'horaAudiencia',index:'horaAudiencia', width:80,align:'center'},
									{name:'sala',index:'sala', width:100,align:'center'},
									{name:'estatus',index:'estatus', width:100,align:'center'}
								],
							width:870, 
							pager: jQuery('#paginadorFechaPJATP'), 
							sortname: 'id', 
							rowNum:10,
							rowList:[10,20,30], 
							sortorder: "desc",
							ondblClickRow: function(rowid) {
								dblClickRowBandejaAudiencias(rowid);
							} 
					}).navGrid('#paginadorFechaPJATP',{edit:false,add:false,del:false});

				  	$("#gview_gridFechaPJATP .ui-jqgrid-bdiv").css('height', '420px');
				  	//cambia la bandera a false para que solo ejecute el reload
				  	primeraVezGridFecha = false;
				}
			  else{
				  
				  jQuery("#gridFechaPJATP").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/buscarAudienciasPorFecha.do?fechaInicio='+fechaInicio+'&fechaFin='+fechaFin+'',datatype: "xml" });
					$("#gridFechaPJATP").trigger("reloadGrid");				  
				}
			  		  	
		  	$('#divGridSolicitudAudienciaPJEA').hide();
			$('#divGridSolicitudOtroPJEA').hide();
			$('#divGridExpedientesPJEA').hide();
			$('#divGridAudienciaDelDiaPJENA').hide();
			$('#divGridFechasPJATP').show();
			$('#divGridExpedientesDocumentoPJATP').hide();
			$('#divGridAudienciasCancenladasPJATP').hide();			
	 
		}			  		   
	}			


	/*
	*Funcion que carga la ventana modal para seleccionar el intervalo de fechas entre las cuales
	*se desea consultar las audiencias agendadas
	*/
	function modalFecha(){

		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		/**
		* Carga los escuchadores de eventos para los combo box's para 
		* el domicilio 
		*/
		$("#fechaInicio").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});

		$("#fechaFin").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		//abre la ventana de detalle de la persona
		$("#busquedaFecha").dialog("open");
		$("#busquedaFecha").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Buscar por Fecha', 
	  		dialogClass: 'alert',
			resizable: false,
			height:'auto',
			width:'auto',
	  		buttons:{"Aceptar":function() {
		  				validaCamposFecha($('#fechaInicio').val(), $('#fechaFin').val());
		  				cargaGridFechaPJATP();
		  				if(validaFecha==true){
		  					$(this).dialog("close");
				  		}
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});
				
	}

	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
	}

	
	/*
	*Funcion que crea el visor de  audiencia
	*/		
	function dblClickRowBandejaAudiencias(idRow){
		idWindowVisorAudiencia++;
       	$.newWindow({id:"iframewindowVisorEncargadoAdmonAudiencias"+ idWindowVisorAudiencia, statusBar: true, posx:50,posy:50,width:1180,height:800,title:"Atenci&oacute;n de Audiencias", type:"iframe"});
   	    $.updateWindowContent("iframewindowVisorEncargadoAdmonAudiencias"+ idWindowVisorAudiencia,'<iframe src="<%=request.getContextPath()%>/visorAtnAlPublicoAudiencias.do?idEvento=' + idRow +'" width="1180" height="800" />');
	}
	
	function dblClickRowBandejaAudienciasCaceladas(idRow){		
       	$.newWindow({id:"iframewindowVisorEncargadoAdmonAudiencias"+ idWindowVisorAudiencia, statusBar: true, posx:50,posy:50,width:1180,height:800,title:"Atenci&oacute;n de Audiencias", type:"iframe"});
   	    $.updateWindowContent("iframewindowVisorEncargadoAdmonAudiencias"+ idWindowVisorAudiencia,'<iframe src="<%=request.getContextPath()%>/visorAtnAlPublicoAudiencias.do?idEvento=' + idRow +'&visorDeSoloLectura=1'+'" width="1180" height="800" />');	
	}



	function visorLeyesCodigos() {
		idWindowVisorLeyesYCodigos++;
		$.newWindow({id:"iframewindowRestaurativa"+idWindowVisorLeyesYCodigos, statusBar: true, posx:254,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa"+idWindowVisorLeyesYCodigos,'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="800" height="500" />');
	    		
	}
	

	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
	*/
	function generaVisorGraficaView() {
		idWindowGraficasView++;
		$.newWindow({id:"iframewindowWindowImageViewer"+idWindowGraficasView, statusBar: true, posx:63,posy:111,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowImageViewer"+idWindowGraficasView,'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="400" />');
	    		
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
				height:420,
				viewrecords: true,
				ondblClickRow: function(rowid) {
					var ret2 = jQuery("#gridExpedientesDocumentoPJATP").jqGrid('getRowData',rowid);
					numCausa= ret2.expediente;
					numCaso = ret2.numeroCaso;
					dblClickRowvisorDocumentosExpediente(rowid);
				},
				sortorder: "desc"
			});
			$("#gview_gridExpedientesDocumentoPJATP .ui-jqgrid-bdiv").css('height', '420px');
			//cambia la bandera a false para que solo ejecute el reload
		  	primeraGridExpedientesDocumentoPJATP = false;
		}
		else{
			jQuery("#gridExpedientesDocumentoPJATP").jqGrid('setGridParam',{url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpedienteParaDocumentos.do?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&numeroExpedienteId='+numeroExpedienteId+'',datatype:"xml" });
			$("#gridExpedientesDocumentoPJATP").trigger("reloadGrid");				  
		}

		$('#divGridSolicitudAudienciaPJEA').hide();
		$('#divGridSolicitudOtroPJEA').hide();
		$('#divGridExpedientesPJEA').hide();
		$('#divGridAudienciaDelDiaPJENA').hide();
		$('#divGridExpedientesDocumentoPJATP').show();
		$('#divGridFechasPJATP').hide();
		$('#divGridAudienciasCancenladasPJATP').hide();
	}

	/*
	*Funcion para desplegar el poppoup de tipo de busqueda
	*/
	function  poppopTipoBusqueda(tipo){
	
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

	 
	/*
	*Funcion que crea el visor de  audiencia
	*/		
	function dblClickRowvisorDocumentosExpediente(idRow){
		$.newWindow({id:"iframewindowVisorEncargadoAdmonAudiencias", statusBar: true, posx:50,posy:111,width:1200,height:700,title:"Numero de Causa:"+numCausa+" "+"Numero de Caso:"+numCaso, type:"iframe"});
	    $.updateWindowContent("iframewindowVisorEncargadoAdmonAudiencias",'<iframe src="<%=request.getContextPath()%>/visorDocumentos.do?numExpedienteId='+idRow+'" width="1200" height="700" />');
	}


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
		document.frmRol2.rolname.value = rolNuevo;
		document.frmRol2.submit();
	}	
	

	/*
	*Funcion que carga el grid de consulta por fechas audiencias Canceladas
	*/
	function cargaGridAudienciasCanceladasPJATP(){
	      //Recupera parametros
	      var params;

	      
	      params="idsEstatusAudiencia="+<%=EstatusAudiencia.CANCELADA.getValorId()%>;
	      /*Enable tanto solicitudes como audiencias canceladas */
	      params+="&idsEstatusSolicitud=" + <%=EstatusSolicitud.CANCELADA.getValorId()%>;
		  if(primeraVezGridAudienciasCanceladas == true){
			  
			  jQuery("#gridAudienciasCanceladasPJATP").jqGrid({ url:'<%= request.getContextPath() %>/buscarAudienciasPorFecha.do?'+params, 						
					datatype: "xml", 
					mtype: 'POST',
					colNames:[
						'<bean:message key="numeroDeCaso" />',
						'<bean:message key="numeroDeCausa" />',
						'<bean:message key="caracter" />',
						'<bean:message key="tipoDeAudiencia" />',
						'<bean:message key="fechaDeAudiencia" />',
						'<bean:message key="horaDeAudiencia" />',
						'<bean:message key="sala" />',
						'<bean:message key="estado" />'
					], 
					colModel:[ 	{name:'numeroCaso',index:'numeroCaso',width:180,align:'center'},
								{name:'expediente',index:'expediente', width:220,align:'center'}, 
								{name:'caracter',index:'caracter', width:80,align:'center'}, 
								{name:'tipoAudiencia',index:'tipoAudiencia', width:150,align:'center'},
								{name:'fechaAudiencia',index:'fechaAudiencia', width:80,align:'center'}, 
								{name:'horaAudiencia',index:'horaAudiencia', width:80,align:'center'},
								{name:'sala',index:'sala', width:100,align:'center'},
								 {name:'estatus',index:'estatus', width:100,align:'center',hidden:'true'} 
							],
						width:870, 
						pager: jQuery('#paginadorAudienciasCanceladasPJATP'), 
						sortname: 'id', 
						rowNum:10,
						rowList:[10,20,30], 
						sortorder: "desc",
						ondblClickRow: function(rowid) {
							dblClickRowBandejaAudienciasCaceladas(rowid)
						} 
				}).navGrid('#paginadorAudienciasCanceladasPJATP',{edit:false,add:false,del:false});

			  	$("#gview_gridAudienciasCanceladasPJATP .ui-jqgrid-bdiv").css('height', '420px');
			  	//cambia la bandera a false para que solo ejecute el reload
			  	primeraVezGridAudienciasCanceladas = false;
			}
		  else{
			  
			  jQuery("#gridAudienciasCanceladasPJATP").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/buscarAudienciasPorFecha.do?'+params,datatype: "xml" });
				$("#gridAudienciasCanceladasPJATP").trigger("reloadGrid");				  
			}
			  		  	
		  	$('#divGridSolicitudAudienciaPJEA').hide();
			$('#divGridSolicitudOtroPJEA').hide();
			$('#divGridExpedientesPJEA').hide();
			$('#divGridAudienciaDelDiaPJENA').hide();
			$('#divGridFechasPJATP').hide();
			$('#divGridExpedientesDocumentoPJATP').hide();
			$('#divGridAudienciasCancenladasPJATP').show();				  		   
	}			
	
	</script>
</head>

<body>

	<!--Comienza ui-layout-west-->
	<div class="ui-layout-west">
		<div class="header">&nbsp;</div>
		<div class="content">
			<div id="accordionmenuprincipal">
				<h3 ><a id="evento" href="#" ><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes</a></h3>
				<div>			
					<ul id="seccion1treePJEA" class="filetree">
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="recargarGridAudienciaPJENA()">Audiencia</a></span></li>
						<li><span class="file" id="otras" style="cursor: pointer;" onclick="cargaGridOtrasSolicitudesPJENA()">Otras</span></li>
					</ul>		
				</div>
				<h3 ><a id="evento" href="#" ><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Audiencias</a></h3>
				<div>			
					<ul id="seccion3treePJEA" class="filetree">
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="cargaGridDiaPJATP()">Del D&iacute;a</a></span></li>
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="modalFecha()">Por Fecha</a></span></li>
					</ul>		
				</div>
				
				<h3 ><a id="evento" href="#" ><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Audiencias Canceladas</a></h3>
				<div>			
					<ul id="seccionAudienciaCanceladasPJEA" class="filetree">
						<li><span class="file"><a id="audienciaaCaneladas" style="cursor: pointer;" onclick="cargaGridAudienciasCanceladasPJATP()">Todas</a></span></li>
					</ul>		
				</div>
				
				
				<h3 ><a id="even" href="#" ><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Causas</a></h3>
				<div>			
					<ul id="seccion34treePJEA" class="filetree">
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="poppopTipoBusqueda('expediente')">N&uacute;mero de Causa</a></span></li>
						<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="poppopTipoBusqueda('fecha')">Por Fecha</a></span></li>
					</ul>		
				</div>
				
<!-- 			<h3 ><a id="imageViewer" href="#" onclick=""><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gráficas y Reportes</a></h3>
				<div>		
					
				</div> -->
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
				<div>
					
				</div>

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
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg" height="100%" >
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
					            	<!-- 
					              <td width="53" class="txt_menu_top">Buscar</td>
					              <td width="147"><img src="<%=request.getContextPath()%>/resources/images/icn_buscar.png" width="22" height="23" border="0"></td>
					               -->
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
					                <TD width=128 align=right valign="middle"><table width="141" border="0" cellspacing="0" cellpadding="0">
					                  <tr>
					                    <td width="107" align="right" valign="middle" class="txt_menu_top">&nbsp;</td>
					                  </tr>
					                </table></TD>
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
				<li id="complejidadAudiencia" ><span></span>Configurar Complejidad de Audiencia&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_confwrite.png" width="15" height="16"></li>
				<li id="presentarInvolucrado" ><span></span>Presentaci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_confwrite.png" width="15" height="16"></li>
				
			</div>
			<div id="menu_config">
				<li id="buscarExpediente"><span></span>Buscar Causa&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
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
					<div id="divGridSolicitudAudienciaPJEA">
						<table id="girdSolicitudAudienciaPJEA" ></table>
						<div id="pager1"></div>
					</div>
					<div id="divGridSolicitudOtroPJEA">
						<table id="gridSolicitudOtrosPJEA" ></table>
						<div id="pager2"></div>
					</div>	
					<div id="divGridExpedientesPJEA">
						<table id="gridExpedientesPJENA" ></table>
						<div id="pager3"></div>
					</div>
					<div id="divGridAudienciaDelDiaPJENA">
						<table id="gridAudienciaDelDiaPJENA" ></table>
						<div id="pager4"></div>
					</div>
					<div id="divGridFechasPJATP">
						<table id="gridFechaPJATP"></table>
						<div id="paginadorFechaPJATP"></div>
					</div>
					
					<div id="divGridExpedientesDocumentoPJATP">
						<table id="gridExpedientesDocumentoPJATP"></table>
						<div id="paginadorExpedientesDocumentoPJATP"></div>
					</div>
					
					<div id="divGridAudienciasCancenladasPJATP">
						<table id="gridAudienciasCanceladasPJATP"></table>
						<div id="paginadorAudienciasCanceladasPJATP"></div>
					</div>
				</div>	
			</div>
		</div>
	</div>
	<!--Termina main content-->
	
	<!--div para la ventana modal de buscar audiencias agendadas por fecha-->
	<div id="busquedaFecha" style="display: none">
		<table cellspacing="0" cellpadding="0" >
			<tr>
				<td width="153">&nbsp;</td>
				<td width="153">&nbsp;</td>
			</tr>
			<tr>
			  <td colspan="2" align="center">
			  	<strong>Fecha Inicio:</strong>
			  	<input type="text" id="fechaInicio" size="20" />		  
			  </td>
		    </tr>
			<tr>
			  <td align="center">&nbsp;</td>
			  <td align="center">&nbsp;</td>
	  		</tr>
			<tr>
			  <td colspan="2" align="center">
			  	<strong>Fecha Fin:&nbsp;&nbsp;</strong>&nbsp;
		      	<input type="text" id="fechaFin" size="20" /></td>
	  		</tr>
			<tr>
			  <td align="center">&nbsp;</td>
			  <td align="center">&nbsp;</td>
	  		</tr>
		</table>
	</div>
	
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
