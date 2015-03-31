<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.EstatusEvidencia" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.TiposEslabon" %>

<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="org.springframework.web.bind.support.SessionAttributeStore"%>
<%@page import="org.springframework.web.bind.annotation.SessionAttributes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="org.apache.cxf.common.security.UsernameToken"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" session="false" %>

<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
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
        <link type="text/css" rel="stylesheet"
              href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/resources/css/layout_complex.css"
              media="screen" />

        <!--css para el estilos de jquery-->
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />

        <!--css para estilo de los arboles-->
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />

        <!--estilo ultrasist-->
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/resources/css/estilos.css"
              media="screen" />

        <!--estilo del grid-->
        <link rel="stylesheet" type="text/css" media="screen"
              href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
        <link rel="stylesheet" type="text/css"
              href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />

        <!--COMIENZAN SCRIPTS-->

        <!--jquery-->
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script
        src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>

        <!--para controlar las ventanas-->
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

        <!--para creacion de arboles-->
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/reloj.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
        <script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	
		<script type="text/javascript">
			var opcionSeleccionadaEnElMenu = 0;
			var contextoPagina = "${pageContext.request.contextPath}";
			var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
			var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
			estaSesionActiva();
			
            var CONTEXT_ROOT = '<%= request.getContextPath()%>';
			var cargaGridInv=false;
			var idParaConsultaEvidencia;
			var idalmacen;
			var idAlmacenParaGestionarAlmacen;
			
			//Permite buscar evidencias en base al numero de caso
			var numeroGeneralCaso;
			
			//Permite saber la ultima consulta que se hizo
			var estatusEvidenciaDeLaUltimaConsulta = '';
			var banderaSolicitudUltimaConsulta = '';
			
			//Permite consultar Evidencias con o sin solicitud
			var tieneSolicitudPorAtender = true;
			
            $(document).ready(function() {
            	
				jQuery(document).ajaxStop(jQuery.unblockUI);            	
            	//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
        		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
        		setInterval(muestraAlerta, tiempo);
				
                outerLayout = $("body").layout( layoutSettings_Outer );

                $("#accordionmenuprincipal").accordion({  fillSpace: true });
                $("#accordionmenuderprincipal").accordion({ fillSpace: true});

                $("#seccionSolicitudes").treeview();
                $("#seccionEvidenciasConSolicitud").treeview();
                $("#seccionEvidenciasSinSolicitud").treeview();

                //agreaga el evento para crear la agenda
                $("#controlAgenda").click(creaAgenda);
                //Carga el arbol de casos
		
                /*
                 *Funcion que carga el grid, por default con las nuevas notificaciones
                 */

                //div contenedor divGridDetalleResolutivaAudiencia
		

			

		 	

			

		
	
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
                createInnerLayout ();

               //Permite consultar los alamacenes  	
               consultarAlmacenesPorTipo();
               //Permite consultar las solicitudes nuevas asociadas al funcionario firmado en sesion
               gridEvidenciaAlmacen('<%=EstatusEvidencia.NUEVA.getValorId()%>',tieneSolicitudPorAtender);


                                        $( "#dialog-logout" ).dialog({
                                			autoOpen: false,
                                			resizable: false,
                                			//height:290,
                                			//width:300,
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
                                		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
                                		if(ambiente == undefined || ambiente == "undefined"){
                                			ambiente = "";
                                		}
                                		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
            });
            //FIN ON READY
	
	

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
						ajustarGridAlCentro($("#gridEvidenciaAlmacenExpediente"));
						ajustarGridAlCentro($("#gridAlmacen"));
					}
                }
            };

            /////////////////////////////////////////////// COMIENZA FUNCIONALIDAD COMUN //////////////////////////////////////////////////////////////////
            /*
             *Funcion que abre el visor de audiencias
             *(Por el momento no acarrea el ID solo abre el visor)
             */
	
            /*
             *Funcion que llama a la funcionalidad para crear la agenda
             */
            function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
                $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');
            }


            /*
             *Funcion que llama a la funcionalidad para generar un documento
             */
	
            function lanzaMenuSalidasAlmacen() {
                abreNuevoFrame("iframewindowGenerarDocumento", "/salidaAlmacen.do", 255, 130, 454, 219, "Salida de Almacén");
            }

            function lanzaMenuEntradasAlmacen() {
                var idAlmacenSeleccionado = jQuery('#gridAlmacen').jqGrid('getGridParam','selrow');
                if (!idAlmacenSeleccionado) {
                    alert("Seleccione un almacén para registrar entradas");
                }else{
                    $.newWindow({id:"iframewindowEntradasAlmacen", statusBar: true, posx:255,posy:133,width:803,height:445,title:"Entrada de Almacen", type:"iframe"});
                    $.updateWindowContent("iframewindowEntradasAlmacen",'<iframe src="<%=request.getContextPath()%>/entradasAlmacen.do?identificadorAlmacen="' + idAlmacenSeleccionado + ' width="650" height="600" />');
                }

            }

	
	
            function lanzaMenuGestionarAlmacen(idEvidencia) {
                idParaConsultaEvidencia=idEvidencia;
                idAlmacenParaGestionarAlmacen = $('#identificadorAlmacen').val();
		        $.newWindow({id:"iframewindowEntradasAlmacen", statusBar: true, posx:255,posy:133,width:803,height:760,title:"Gestionar Almac&eacute;n", type:"iframe"});
                $.updateWindowContent("iframewindowEntradasAlmacen",'<iframe src="<%=request.getContextPath()%>/menuGestionarAlmacen.do?opcionSeleccionadaEnElMenu="' + opcionSeleccionadaEnElMenu + ' width="803" height="760" />');
	    		
            }

	
            function funcionMuestraGrids(gridAMostrar){
            	$("#divGridDetalleAlmacen").css("display","none");
 				$("#divGridDetalleEvidencia").css("display","none");
            	
            	if(gridAMostrar == "detalleAlmacen"){
            		$("#divGridDetalleAlmacen").css("display","");
            	}
            	if(gridAMostrar == "detalleEvidencia"){
            		$("#divGridDetalleEvidencia").css("display","");
            	}
            }

           function crearAlmacen(){ 
        	   idalmacen=0;
        	   $.newWindow({id:"iframewindowNuevoAlmacen", statusBar: true, posx:255,posy:133,width:850,height:490,title:"Nuevo Almac&eacute;n", type:"iframe"});
               $.updateWindowContent("iframewindowNuevoAlmacen",'<iframe src="<%=request.getContextPath()%>/crearAlmacen.do" width="850" height="490" />');
           }

           function consultaAlmacen(idrow){ 
        	   idalmacen=idrow;
        	   $.newWindow({id:"iframewindowConsultaAlmacen", statusBar: true, posx:255,posy:133,width:850,height:490,title:"Consulta Almac&eacute;n", type:"iframe"});
               $.updateWindowContent("iframewindowConsultaAlmacen",'<iframe src="<%=request.getContextPath()%>/crearAlmacen.do" width="850" height="490" />');
           }

           /**
            * Permite consultar Evidencias asociadas al Almacen del cual es responsable 
            * el usuario firmado en seción. Permite consultar evidencias en base al
            * estatus de la evidencia.
            **/
 		   function gridEvidenciaAlmacen(estatusEvidencia, tieneSolicitudPorAtender){
        	  //Permite almacenar el valor del estatus asociado a la consulta con el fin 
        	  //de poder recargar el grid con el ultimo estatus de la consulta
 			  estatusEvidenciaDeLaUltimaConsulta = estatusEvidencia;
 			  banderaSolicitudUltimaConsulta = tieneSolicitudPorAtender;
        	   
        	   
         	   //Muestra el grid respectivo
 				funcionMuestraGrids('detalleEvidencia')
 				
 				//Recupera parametros
 				var params;
 					params="idAlmacen="+$('#identificadorAlmacen').val();      
 					params+="&estatusEvidencia="+estatusEvidencia;
 					params+="&numeroGeneralCaso="+numeroGeneralCaso;
 					params+="&tieneSolicitudPorAtender="+tieneSolicitudPorAtender;
 					
 					//alert(params);
 				
 				//Permite recargar el grid de forma automatica
 				if (cargaGridInv==false){
 	                jQuery("#gridEvidenciaAlmacenExpediente").jqGrid({
 	                    url:'<%=request.getContextPath()%>/consultarEvidenciaPorAlmacenoExpediente.do?'+params,
 	                    datatype: "xml",
 	                    colNames:["Caso",'Expediente','Folio CC','# Evidencia','Descripcion','Estatus'],
 	                    colModel:[
 	                        {name:'Caso',index:'1',  viewable:false, width:135},
 	                        {name:'Expediente',index:'4',  sortable:false, width:110},
 	                        {name:'Folio',index:'3',  sortable:true, width:100},
 	                        {name:'Evidencia',index:'6',  sortable:true, width:60},
 	                        {name:'Descripcion',index:'5',  sortable:false, width:120},
 	                        {name:'estatus',index:'2',  sortable:true, width:80},
 	                    ],
 	                    pager: jQuery('#paginadorEvidenciaAlmacenExpediente'), 
 	                    rowNum:10,
 	                    rowList:[10,20,30],
 	                    autowidth: true,
 	                    autoheight:true,
 	                    height:350,
 	                    sortname: '6',
 	                    viewrecords: true,
 	                    sortorder: "desc",
 	                    ondblClickRow: function(rowid) {
 	                    	lanzaMenuGestionarAlmacen(rowid);
 	                    }
 	
 	                }).navGrid('#paginadorEvidenciaAlmacenExpediente',{edit:false,add:false,del:false});
 	                	cargaGridInv=true;
 				 }else{					 
 					 jQuery("#gridEvidenciaAlmacenExpediente").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarEvidenciaPorAlmacenoExpediente.do?'+params,datatype: "xml" });
 					 $("#gridEvidenciaAlmacenExpediente").trigger("reloadGrid");
 				 }
 				ajustarGridAlCentro($("#gridEvidenciaAlmacenExpediente"));
 			}


 		  function agregarEncargadoAlmacen(tipo){
				$('#identificadorExpediente').val("");  
				$('#identificadorAlmacen').val(""); 
				if(tipo==1){//Busqueda por numero de caso
					$('#buscandoporAlmacen').css("display","none");
					$('#buscandoporExpedienteAlmacen').css("display","");
				}else{//Busqueda por id de almacen
					$('#buscandoporExpedienteAlmacen').css("display","none");
					$('#buscandoporAlmacen').css("display","");
				}
				 $( "#dialog-BusquedaXEoA" ).dialog({
	     			autoOpen: true,
	     			resizable: true,
	     			height:150,
	     			width:400,
	     			modal: true,
	     			buttons: {
	     				"Aceptar": function() {
	     					
	     					numeroGeneralCaso = $('#identificadorExpediente').val();
	     					gridEvidenciaAlmacen(estatusEvidenciaDeLaUltimaConsulta, banderaSolicitudUltimaConsulta);
     						$( this ).dialog( "close" );
     						//Una vez generada la consulta se limpia la variable
     						numeroGeneralCaso = "";
     						

	     				},
	     				"Cancelar": function() {
	     					$( this ).dialog( "close" );
	     					
	     				}
	     			}
	     		});		

			}



			
			/******************************************************    FUNCIONES PARA LAS ALARMAS      ***************************************************/
			function muestraAlerta(){
				var op="";
				var idAlerta="";

				$.ajax({
		   		type: 'POST',
		    		url: '<%=request.getContextPath()%>/consultarAlarmas.do',
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
		    			//alert($(xml).find('alertaDTO').find('nombre').text());
		    			//alert('la primera op:'+op);
		    			
		    			//alert('la xml op:'+$(xml).find('alertaDTO').find('esAplaza').text());
		    			//alert('la segunda op:'+op);
		   		}
		   	});
				
				

			}

			function cambiarEstausAlarma(estatus,tiempo,id,unidad){
				$.ajax({
			   		type: 'POST',
			    		url: '<%=request.getContextPath()%>/actualizarAlarma.do?idAlerta='+id+'&estatus='+estatus+'&tiempo='+tiempo+'&unidad='+unidad,
			    		data: '',
			    		dataType: 'xml',
			    		async: false,
			    		success: function(xml){
			    			//alert($(xml).find('alertaDTO').find('nombre').text());
			    			if(estatus=="posponer")
			    			{
			    				alert("Alarma pospuesta.");
			    			}
			    			else if(estatus=="cancelar")
			    			{
			    				alert("Alarma cancelada");
			    			}
			    			else
			    			{
			    				alert("Alarma aceptada.");
			    			}
			   		}
				});
			}

			function llamaraCambia(op,idAlerta){
				//alert('la segunda op:'+op);
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
							//cambiarEstausAlarma("cancelar","0",idAlerta);
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
					
			}
			/******************************************************  FIN  FUNCIONES PARA LAS ALARMAS      ***************************************************/
			
			function consultarAlmacenesPorTipo(){
				//Incia grid para la consulta de Almacenes 
	               jQuery("#gridAlmacen").jqGrid({
	                    url:'<%=request.getContextPath()%>'+'/consultarAlmacenesPorTipo.do',
	                    datatype: "xml",
	                    colNames:['Identificador','Tipo de Almacén','Nombre del Almacén','Dirección','Descripción' ],
	                    colModel:[
							{name:'identificador',index:'4',  sortable:true, width:40,align:'center'},
	                        {name:'tipoDeAlmacen',index:'1',  sortable:true, widih:40},
	                        {name:'nombreAlmacen',index:'2',  sortable:true, width:90},
	                        {name:'direccion',index:'5',  sortable:false, width:120},
	                        {name:'descripcion',index:'3',  sortable:true, width:200},
	                    ],
	                    pager: jQuery('#paginadorDetalle'),
	                    rowNum:10,
	                    rowList:[10,20,30],
	                    autowidth: true,
	                    autoheight:true,
	                    height:400,
	                    sortname: '4',
	                    viewrecords: true,
	                    sortorder: "desc",
	                    ondblClickRow: function(rowid) {
	                    	consultaAlmacen(rowid);
	                    }

	                }).navGrid('#paginadorDetalle',{edit:false,add:false,del:false});
					$("#gridAlmacen").trigger("reloadGrid");	
					//Finaliza grid para la consulta de Almacenes
	 				ajustarGridAlCentro($("#gridAlmacen"));

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
				///rolRedirec
				//alert(rolNuevo);
				document.frmRol2.rolname.value = rolNuevo;
				document.frmRol2.submit();

			}
        </script>
    </head>

    <body>

        <!--Comienza ui-layout-west-->
        <div class="ui-layout-west">
            <div class="header">&nbsp;</div>
            <div class="content">
                <div id="accordionmenuprincipal">
                                   
           			<h3><a id="menuSolicitudes" href="#" onclick="">Solicitudes</a></h3>
                    <div>
                    	<ul id="seccionSolicitudes" class="filetree">
                            <li><span class="folder"><a style="cursor: pointer;" onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.NUEVA.getValorId()%>',true);opcionSeleccionadaEnElMenu=1">Entradas al almac&eacute;n</a></span></li>
						</ul>	
                    </div>
                                                  
                    <h3><a id="menuEvidenciasConSolicitud" href="#" onclick="">Evidencias con Solicitud</a></h3>
                    <div>
                    	<ul id="seccionEvidenciasConSolicitud" class="filetree">
							<li><span class="folder"><a style="cursor: pointer;" onclick="gridEvidenciaAlmacen('',true);opcionSeleccionadaEnElMenu=1">Todas</a></span></li>
							<li><span class="folder"><a style="cursor: pointer;" onclick="agregarEncargadoAlmacen('1')">Por n&uacute;mero de Caso<br></a></span></li>
													
							<li class="closed">
								<span class="folder">Estatus</span>
									<ul>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.EN_ALMACEN.getValorId()%>', true);opcionSeleccionadaEnElMenu=1">En almac&eacute;n</a></span></li>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.EN_PRESTAMO.getValorId()%>', true);opcionSeleccionadaEnElMenu=1">En pr&eacute;stamo</a></span></li>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.CON_RETRASO.getValorId()%>', true);opcionSeleccionadaEnElMenu=1">Con retraso</a></span></li>
									</ul>
							</li>
						</ul>	
					</div>
					
					
					<h3><a id="menuEvidenciasSinSolicitud" href="#" onclick="">Evidencias sin Solicitud</a></h3>
                    <div>
                    	<ul id="seccionEvidenciasSinSolicitud" class="filetree">
							<li><span class="folder"><a style="cursor: pointer;" onclick="gridEvidenciaAlmacen('',false);opcionSeleccionadaEnElMenu=1;banderaSolicitudUltimaConsulta=false;">Todas</a></span></li>
							<li><span class="folder"><a style="cursor: pointer;" onclick="agregarEncargadoAlmacen('1')">Por n&uacute;mero de Caso<br></a></span></li>
													
							<li class="closed">
								<span class="folder">Estatus</span>
									<ul>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.EN_ALMACEN.getValorId()%>', false);opcionSeleccionadaEnElMenu=1">En almac&eacute;n</a></span></li>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.EN_PRESTAMO.getValorId()%>', false);opcionSeleccionadaEnElMenu=1">En pr&eacute;stamo</a></span></li>
										<li><span class="file"><a onclick="gridEvidenciaAlmacen('<%=EstatusEvidencia.CON_RETRASO.getValorId()%>', false);opcionSeleccionadaEnElMenu=1">Con retraso</a></span></li>
									</ul>
							</li>
						</ul>	
					</div>
					
					
                    <h3><a id="menuGenerarReporte" href="#" onclick="funcionMuestraGrids('detalleAlmacen')">Generar Reporte de Almac&eacute;n</a></h3>
                    <div></div>
                    
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
                    <!-- <h4><a href="#">Calendario</a></h4>
                    <div>
                        <center><a href="#"> <img
                                    src="<%=request.getContextPath()%>/resources/images/calendario.JPG"
                                    width="130" height="104"> </a></center>
                    </div>-->
                    <h6><a href="#">Agenda</a></h6>
                    <div>
                        <center>
                        	<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
                        </center>
                        <br />
                    </div>
                   <!--   <h6><a href="#" id="">Consultar Leyes y C&oacute;digos</a></h6>
                    <div>
                        <table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0"
                               cellpadding="0" bgcolor="#EEEEEE" bordercolorlight="#FFFFFF"
                               style="cursor: pointer">
                            <tr>
                                <td width="100%" id="leyes"><img
                                        src="<%=request.getContextPath()%>/resources/css/check.png"
                                        width="16" height="16" />Leyes</td>
                            </tr>
                            <tr>
                                <td id="codigos">&nbsp;<img
                                        src="<%=request.getContextPath()%>/resources/css/check.png"
                                        width="16" height="16" />Codigos</td>
                            </tr>
                            <tr>
                                <td id="manuales">&nbsp;<img
                                        src="<%=request.getContextPath()%>/resources/css/check.png"
                                        width="16" height="16" />Manuales</td>
                            </tr>
                        </table>
                    </div>-->
                    <h1><a href="#">Chat</a></h1>
                    <div></div>
<!--                    <h1><a href="#">Clima</a></h1>-->
<!--                    <div></div>-->
                    <h1><a href="#" onclick="consultarTiposRol();">Facultades</a></h1>
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
					    <TD width=301 align=left valign="middle">
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
                    <li  onclick="crearAlmacen()"><span></span>Nuevo Almac&eacute;n</li>
                </div>
                <div id="menu_config">
                    <li id="generarDocumento"><span></span>Generar Documento</li>
                    <li><span></span>Adjuntar documento</li>
                </div>
            </ul>
            <!--termina barra de herramientas--></div>
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
                    
                         <div id="divGridDetalleAlmacen"> 
                            <table id="gridAlmacen"></table>
                            <div id="paginadorDetalle"></div>
                        </div>


						<div id="divGridDetalleEvidencia">
                            <table id="gridEvidenciaAlmacenExpediente"></table>
                            <div id="paginadorEvidenciaAlmacenExpediente"></div>
                        </div>
						 

                    </div>
                </div>
            </div>
        </div>
        <!--Termina main content-->

        <div id="cambiaEstatusConfirm" style="display: none">

            <table cellspacing="0" cellpadding="0">
                <tr>
                    <td >Estas Seguro de Actualizar el Estatus</td>

                </tr>
            </table>

        </div>
        
        <div id="dialog-logout" title="Logout">
		<p align="center">
			<span id="logout">¿Desea cerrar su sesi&oacute;n?</span>
		</p>
	</div>
	
	
	<div id="dialog-BusquedaXEoA">
	<table id="buscandoporAlmacen"><tr> 
<td>Identificador de Almac&eacute;n</td><td>
<input type="text" id="identificadorAlmacen"> 

</td>
</tr>
</table>
<table id="buscandoporExpedienteAlmacen"><tr> 
<td>Numero de Caso</td><td>
<input type="text" id="identificadorExpediente" maxlength="27">

</td>
</tr>
</table>
	</div>
	
		<!-- dialogos para las alarmas -->
	<div id="dialog-alarm" title="Alarma ">
		<p align="center">
			<span id="mensajeAlarma">mensajeConfigurable</span>
		</p>
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
	
	<div id="dialog-alarmPos" title="Alarma ">
		<p align="center">
			<span id="mensaje">Tiempo deseado para aplazar la alerta</span><br/>
			<span id="tiempo"><input type="text" size="5" maxlength="2" id="idTiempotex" onKeyPress="return solonumeros(event);"/></span>
			<span id="cbxTiempoSpan">
				<select id="cbxTiempo">
					<option value="2">Horas</option>
					<option value="1">Minutos</option>
				</select>
			</span>
		</p>
	</div>
	<!-- FIN dialogos para las alarmas -->
    </body>
    <script type="text/javascript">
$( "#dialog-alarm" ).dialog();
$( "#dialog-alarmPos" ).dialog();
$( "#dialog-alarm" ).dialog( "destroy" );
$( "#dialog-alarmPos" ).dialog( "destroy" );	
</script>
</html>

