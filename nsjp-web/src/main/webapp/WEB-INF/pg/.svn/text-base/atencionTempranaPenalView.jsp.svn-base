<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>

<script type="text/javascript">
	//URG - 005
	var habilitarTurno='<%= (request.getSession().getAttribute("KEY_SESSION_HABILITAR_TURNO"))%>';
	var abreenPenal;
	var tur;
	var outerLayout, innerLayout;
	var idWindowNuevaDenuncia=1;
	var grid=0;
	var idSiguienteTurno=0;
	var idTurno;
	var numerocaso;
	var idWindowDetalleSolicitud=1;
	/*es ultimo turno y estatus/*ByYolo*/
	var esUltimoTurno;
	var estatusTurno;
	obtenerUltimoTurno();
	$(document).ready(function() {
		$("#idExpedientes").css({ color: "#1C94C4"});
		$("#anio1").css({ color: "#1C94C4"});
		$("#A1").css({ color: "#1C94C4"});
		$("#A2").css({ color: "#1C94C4"});
		$("#A3").css({ color: "#1C94C4"});
		$("#anio2").css({ color: "#1C94C4"});
		$("#B1").css({ color: "#1C94C4"});
		$("#B2").css({ color: "#1C94C4"});
		$("#B3").css({ color: "#1C94C4"});

		/*se muestran ambos botones y se obtiene el estatus del ultimo turno/*ByYolo*/
			$("#tbarBtnConsultarTurnoAtencion").show();
			$("#tbarBtnConsultarTurnoAtencionDesh").show();

		//ocultamos el boton de turno o no URG - 005 /*Se deshabilita esta condicion y se implementa un desarrollo para la validacion de botones "llamar turno" y "Registrar denuncia"/*ByYolo*/
// 		if(habilitarTurno==null || habilitarTurno=='null' || habilitarTurno==1)
// 		{
// 			$("#tbarBtnConsultarTurnoAtencion").show();
// 			$("#tbarBtnConsultarTurnoAtencionDesh").hide();
// 		}
// 		else
// 		{
// 			$("#tbarBtnConsultarTurnoAtencion").hide();
// 			$("#tbarBtnConsultarTurnoAtencionDesh").show();
// 		}
		//funciones para el popup interno del turno
		$( "#dialog:ui-dialog" ).dialog( "destroy" );

		$("#tbarBtnConsultarTurnoAtencion").click(obtenTurnoPopup);

		/*se deja deshabilitado ya que esta cargado en en el evento click de <li></li>/*ByYolo*/
// 		$("#tbarBtnConsultarTurnoAtencionDesh").click(obtenTurnoSinPopup);//URG - 005

		$("#tbarBtnAsignarPermisosASubordinados").click(asigarPermisos);

		$("#justiciaAlterna-restaurativa").css({ color: "#1C94C4"});

		$("#unidad-imvestigacion").css({ color: "#1C94C4"});

		$("#controlAgenda").click(creaAgenda);

		$( "#tabsAtencionTempranaPenal" ).tabs();

		$("#exp0001").click(seleccionFila);
		$("#exp0002").click(seleccionFila2);
		$("#restaura").click(gridRestaurativa);
		$("#unidadInvestiga").click(gridUnidadInvestigacion);

		outerLayout = $("body").layout( layoutSettings_Outer );
		$("#botonGuarda").click(guardaNombre);
		$("#cancelTurno").click(cancelar);
		$("#idTurnos").click(activaTurno);
		$("#delDia").click(activaExpediente);
		$("#delTodos").click(activaExpedienteSinFecha);
		$("#expCompartidos").click(activaExpCompartidos);

		$("#divDatos").css("display", "none");

		jQuery("#gridDetalleFrmPrincipal").jqGrid({
										url:'<%=request.getContextPath()%>/BusquedaInicialTurnosGrid.do',
										datatype: "xml",
										colNames:['Expediente','Tipo','Fecha', 'Denunciante','Delito'],
										colModel:[ 	{name:'Expediente',index:'1', width:200, align:'center'},
										           	{name:'Tipo',index:'5', width:120, align:'center'},
													{name:'Fecha',index:'2', width:100, align:'center'},
													{name:'Denunciante',index:'3', width:250, align:'center'},
													{name:'Delito',index:'4', width:205, align:'center'}
												],
										pager: jQuery('#pager1'),
										rowNum:10,
										rowList:[10,20,30,40,50,60,70,80,90,100],
										autoheight: true,
										sortname: '1',
										viewrecords: true,
										id: 'pager1',
										onSelectRow: function(id){
											detEvi(id);
										},
										sortorder: "desc"
									}).navGrid('#pager1',{edit:false,add:false,del:false});

		//GRID Expedientes Compartidos
		jQuery("#gridExpCompartidos").jqGrid({
			url:'<%=request.getContextPath()%>/BusquedaExpCompartidosFuncionarioLog.do',
			datatype: "xml",
			colNames:['Expediente','Tipo','Fecha', 'Denunciante','Delito'],
			colModel:[ 	{name:'Expediente',index:'1', width:30},
			           	{name:'Tipo',index:'5', width:120, align:'center'},
						{name:'Fecha',index:'2', width:25},
						{name:'Denunciante',index:'3', width:90},
						{name:'Delito',index:'4', width:45}
					],
			pager: jQuery('#pagerExpCompartidos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			sortname: '1',
			viewrecords: true,
			onSelectRow: function(id){
				detEvi(id);
			},
			sortorder: "desc"
		}).navGrid('#pagerExpCompartidos',{edit:false,add:false,del:false});
		//FIN GRID Expedientes Compartidos

		jQuery("#gridDetalleExpediente").jqGrid({
			url:'<%=request.getContextPath()%>/BusquedaInicialExpedientesGrid.do',
			datatype: "xml",
			colNames:['Expediente','Fecha','Denunciante','Delito'],
			colModel:[ 	{name:'Expediente',index:'expediente', width:200},
			           	{name:'Fecha',index:'fecha', width:100},
			           	{name:'Denunciante',index:'denunciante', width:290},
						{name:'Delito',index:'delito', width:190}

					],
			pager: jQuery('#pagerExpediente'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pagerExpediente',{edit:false,add:false,del:false});

		//Grid de Solicitudes por atender
		jQuery("#gridSolsXAtndr").jqGrid({
			datatype: "xml",
			colNames:['No. Caso','Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'],
			colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
			           	{name:'expediente',index:'expediente', width:180,align:'center'},
						{name:'folio',index:'folio', width:100,align:'center'},
						{name:'estatus',index:'estatus', width:100,align:'center'},
						{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
						{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
						{name:'institucion',index:'institucion', width:100,align:'center'},
						{name:'remitente',index:'remitente', width:200,align:'center'}
					],
			pager: jQuery('#pagerGridSolsXAtndr'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
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
			colNames:['No. Caso','Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Destinatario'],
			colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
			           	{name:'expediente',index:'expediente', width:180,align:'center'},
						{name:'folio',index:'folio', width:100,align:'center'},
						{name:'estatus',index:'estatus', width:100,align:'center'},
						{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
						{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
						{name:'institucion',index:'institucion', width:100,align:'center'},
						{name:'remitente',index:'remitente', width:200,align:'center'}
					],
			pager: jQuery('#pagerGridSolsGeneradas'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudesGen(rowid);
					}
		}).navGrid('#pagerGridSolsGeneradas',{edit:false,add:false,del:false});

		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		 $("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridDetalleExpediente .ui-jqgrid-bdiv").css('height', '410px');
		 $("#gview_gridSolsGeneradas .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridSolsXAtndr .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridExpCompartidos .ui-jqgrid-bdiv").css('height', '400px');
		 $('#test').weatherfeed(['MXDF0132']);

		restablecerPantallas();
	});

		function restablecerPantallas(){
	 		ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
			ajustarGridAlCentro($("#gridDetalleExpediente"));
			ajustarGridAlCentro($("#gridSolsXAtndr"));
			ajustarGridAlCentro($("#gridSolsGeneradas"));
		}

    var iframewindowAPSE = 0;

    function asigarPermisos(){
		$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="<%= request.getContextPath() %>/asigarPermisosExpediente.do" width="1430" height="670" />');
		$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
		iframewindowAPSE++;
    }

	//crea una nueva ventana para la agenda
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();
	}
	/************************************************    FUNCION PARA REGISTRAR DENUNCIA SIN TURNO        **************************************/

	function obtenTurnoSinPopup(){
		 /*se condicionan los botones/*ByYolo*/
		 obtenerUltimoTurno();
		 if(estatusTurno!='ESPERA'){
			 genConsultarTurnoAtencion();
		 }else{
			 alert("Hay turnos en espera. Favor de llamar turno");
		 }
	}
	 /*se mete la funcion original a otra para poderla condicionar /*ByYolo*/
	function genConsultarTurnoAtencion(){
		//generamos el turno
		var params ='tipoTurno=0&esUrgente=false';

		 $.ajax({
		      url: '<%=request.getContextPath()%>/generarConsultarTurnoAtencion.do',
	    	  dataType: 'xml',
	    	  Type: 'POST',
	    	  data:params,
	    	  async: false,
	    	  success: function(xml){
	        	  //revisamos si se genero el turno
	    		  if($(xml).find('turnoDTO').find('numeroTurno')){
	    			  idSiguienteTurno=$(xml).find('numeroTurno').text();
	    			  idTurno=$(xml).find('turnoId').text();
	    			  if(idSiguienteTurno!=null && idSiguienteTurno!=""){
	    				  nuevaDenuncia();
	    			  }
	    		  }else{
	    			  customAlert("Ocurrió un error al intentar registrar la denuncia");
	    		  }
			  }
	    	});
	}
	/************************************************   FIN FUNCION PARA REGISTRAR DENUNCIA SIN TURNO        **************************************/
	/*Validar que action puede consultar si hay turnos e invocarlo/*byYolo*/
	function obtenerUltimoTurno(){
		estatusTurno='';
		var params ='tipoTurno=0&esUrgente=false';
	    $.ajax({

	    	  url: '<%= request.getContextPath()%>/obtenerUltimoTurnoAtencion.do',
	    	  dataType: 'xml',
	    	  Type: 'POST',
	    	  data:params,
	    	  async: false,
	    	  success: function(xml){
	    		  //llamar(xml);
	    		  $('#xml').val(xml);
	    				esUltimoTurno=$(xml).find('numeroTurno').text();
	    				estatusTurno=$(xml).find('estado').text();
			  }
	    	});

	}
	/*fin/*byYolo*/



	function obtenTurnoPopup()
	{
		/*se condicionan los botones/*ByYolo*/
		 obtenerUltimoTurno();
		 if(estatusTurno!='ESPERA'){
			 alert("No hay turnos. Favor de registrar denuncia");
		 }else{
			 busSiguienteTurno();
		 }
		 /*se mete la funcion original a otra para poderla condicionar /*ByYolo*/
			function busSiguienteTurno(){
				//Vamos a BD por el turno penal siguiente
				$.ajax({
		    		type: 'POST',
		    		url: '<%=request.getContextPath()%>/BusquedaSiguienteTurno.do',
		    		data: '',
		    		dataType: 'xml',
		    		async: false,
		    		success: function(xml){
		    			var errorCode;
		    			errorCode=$(xml).find('response').find('code').text();
		    			//Si errorCode=0 entonces continuamos con el flujo
		    			if(parseInt(errorCode)==0)
		    			{
		    				//asignamos el numero de turno al span del popup
		    				$("#spanNumTurno").html(''+$(xml).find('numeroTurno').text());
		    				var tipoTurno="";
		    				if($(xml).find('tipoTurno').text()=='PENAL')
		    				{
		    					tipoTurno="TURNO  PENAL  ";
		    				}
		    				else
		    				{
		    					tipoTurno="TURNO  NO PENAL";
		    				}
		    				if($(xml).find('esUrgente').text()=='true')
		    				{
		    					tipoTurno=tipoTurno+"URGENTE";
		    				}
		    				$("#spanTipoTurno").html(tipoTurno);
		    				idSiguienteTurno=$(xml).find('numeroTurno').text();
		    				idTurno=$(xml).find('turnoId').text();
		    				//Generamos el popup
		    				muestraPopupSiguienteTurno();
		    			}
		    		}
		    	});
			}

	}

	var isWindowOpen = false;

	function muestraPopupSiguienteTurno(){

				if(idSiguienteTurno==null||idSiguienteTurno==""){
					customAlert("No hay turnos por atender");
				}else{
			$( "#dialog-confirm" ).dialog({
				resizable: false,
				height:290,
				width:300,
				modal: true,
				buttons: {
					"Atender": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						nuevaDenuncia();
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						cancelarTurnoPRCAN();
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();

		}
	}

	function cancelarTurnoPRCAN(){
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/CancelarTurno.do?turno='+idTurno,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			customAlert("Turno Cancelado");
    			/*se valida despues de cancelar un turno principalmente para los urgentes/*ByYolo*/
    			obtenerUltimoTurno();
    		}
    	});
	}

    function nuevaDenuncia() {

        var idExpediente;
        var numeroExpediente;
        var numeroExpedienteId;
        var numeroCasoNuevo;
        var idNuevaDenuncia = 1;
      //variable que indica si es un ingreso o una consulta
        var ingresoDenuncia = false;
       	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoExpedienteDenuncia.do?turno='+idTurno,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			var option;
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
    			numeroCasoNuevo=$(xml).find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').text();
    		}

    	});
       	var pantallaSolicitada=1;
    	idWindowNuevaDenuncia++;
			if(parseInt(idSiguienteTurno)>0){
//  				if(habilitarTurno==null || habilitarTurno=='null' || habilitarTurno==1)
				if(<%=request.getSession().getAttribute("KEY_SESSION_HABILITAR_TURNO")%> == 0 || <%=request.getSession().getAttribute("KEY_SESSION_HABILITAR_TURNO")%> == "0"){
					$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente+" - Turno: "+idSiguienteTurno+" - No. Caso: "+numeroCasoNuevo, type:"iframe"});
				}
				else{
					$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente+" - No. Caso: "+numeroCasoNuevo, type:"iframe"});
				}
			}
			else{
				isWindowOpen = true;
				$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente+" - No. Caso: "+numeroCasoNuevo, type:"iframe"});
			}
			var idIframe = "iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia;
			$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do?numeroGeneralCaso='+numeroCasoNuevo+'&abreenPenal=abrPenal&idNuevaDenuncia='+idNuevaDenuncia +'&ingresoDenuncia='+ingresoDenuncia +'&numeroExpediente='+numeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idNumeroExpedienteop='+numeroExpedienteId+'&idExpedienteop='+idExpediente+'&idIframe='+idIframe+'" width="1430" height="670" />');
			<%--  $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do?numeroGeneralCaso='+numeroCasoNuevo+'&abreenPenal=abrPenal&idNuevaDenuncia='+idNuevaDenuncia +'&ingresoDenuncia='+ingresoDenuncia +'&numeroExpediente='+numeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idNumeroExpedienteop='+numeroExpedienteId+'&idExpedienteop='+idExpediente+'&idIframe='+idIframe+'&numExpAlter='+true+'" width="1430" height="670" />'); --%>
    }

	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle
	*/
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		$.newWindow({id:"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Detalle Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,'<iframe src="<%=request.getContextPath()%>/consultarDetalleSolicitudBandejaGen.do?idSolicitud=' +rowID +'&tipoUsuario=0 " width="1140" height="450" />');
	}

    function generarDocumento() {
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Documento", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/generarDocumento.do" width="1140" height="400" />');

	}



    function seleccionFila(){

		$("#1").css({ color: "#FFFFFF", background: "#FBCB09" });
		$("#2").css({ background: "#dafafc", color: "#008000" });

	}

	function seleccionFila2(){

		$("#2").css({ color: "#FFFFFF", background: "#FBCB09" });
		$("#1").css({ background: "#dafafc", color: "#FFA500" });

	}

	//este acction se tiene ke colocar dentro del ke obtiene el expediente
	function generarTurno(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{url:'<%=request.getContextPath()%>/BusquedaGenerarTurnosGrid.do',
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}

		function guardaNombre(){
			jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
					{url:'<%=request.getContextPath()%>/EjemploAtencionTempranaNuevolleno.xml',
					datatype: "xml" });
			  $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		}
		function cancelar(){
			jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
					{url:'<%=request.getContextPath()%>/CancelarTurnosGrid.do',
	datatype : "xml"
		});
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}

	function detEvi(id) {
	 	var pantallaSolicitada=1;
		idWindowNuevaDenuncia++;
		 var ingresoDenuncia = true;
		 var idIframe = "iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia;
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+id+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'" width="1430" height="670" />');
		//var numExpConsul='<%= request.getSession().getAttribute("numExpConsul")%>';
		//$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+numExpConsul);
		 $("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();
		}

	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}

	// Función invocada también desde el menu intermedio para recargar el grid principal
	function activaExpediente() {
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{url:'<%=request.getContextPath()%>/BusquedaInicialTurnosGrid.do',
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		 ocultaMuestraGrids("turnoPrincipal");
	}

	/*
	*Funcion para recargar el grid de expedientes compartidos desde el arbol izquierdo
	*/
	function activaExpCompartidos()
	{
		jQuery("#gridExpCompartidos").jqGrid('setGridParam',
				{url:'<%=request.getContextPath()%>/BusquedaExpCompartidosFuncionarioLog.do',
				datatype: "xml" });
			 $("#gridExpCompartidos").trigger("reloadGrid");
			 ocultaMuestraGrids("expCompartidos");
			$("#gridExpCompartidos").setGridWidth($("#mainContent").width() - 5, true);
			$("#gview_gridExpCompartidos .ui-jqgrid-bdiv").css('width', '900px');
	}

	function activaExpedienteSinFecha() {
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{url:'<%=request.getContextPath()%>/BusquedaInicialTurnosGridSinFecha.do',
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		 ocultaMuestraGrids("turnoPrincipal");
	}

	function activaTurno() {
		$("#expedientePrincipal").css("display", "none");
		$("#turnoPrincipal").css("display", "block");
		$("#divGridSolsGeneradas").hide();
		$("#divGridSolsXAtndr").hide();
	}

	function buscaExpedientes(num){
		if(num=='A1'){
			$("#A1").css({ color: "#E78F08"});
			$("#A2").css({ color: "#1C94C4"});
			$("#A3").css({ color: "#1C94C4"});

			jQuery("#gridDetalleExpediente").jqGrid('setGridParam',
					{url:'<%=request.getContextPath()%>/BusquedaExpedientesMesGrid.do',
					datatype: "xml" });
				 $("#gridDetalleExpediente").trigger("reloadGrid");
		}else if(num=='A2'){
			$("#A1").css({ color: "#1C94C4"});
			$("#A2").css({ color: "#E78F08"});
			$("#A3").css({ color: "#1C94C4"});
		}else  if(num=='A3'){
			$("#A1").css({ color: "#1C94C4"});
			$("#A2").css({ color: "#1C94C4"});
			$("#A3").css({ color: "#E78F08"});
		}

		if(num=='B1'){
			$("#B1").css({ color: "#E78F08"});
			$("#B2").css({ color: "#1C94C4"});
			$("#B3").css({ color: "#1C94C4"});

			jQuery("#gridDetalleExpediente").jqGrid('setGridParam',
					{url:'<%=request.getContextPath()%>/BusquedaExpedientesMesGrid.do',
					datatype: "xml" });
				 $("#gridDetalleExpediente").trigger("reloadGrid");
		}else if(num=='B2'){
			$("#B1").css({ color: "#1C94C4"});
			$("#B2").css({ color: "#E78F08"});
			$("#B3").css({ color: "#1C94C4"});
		}else  if(num=='B3'){
			$("#B1").css({ color: "#1C94C4"});
			$("#B2").css({ color: "#1C94C4"});
			$("#B3").css({ color: "#E78F08"});
		}

	}

	//Funcion ke rellena los datos del grid con los expedientes ke se encuentran en restaurativa
	function gridRestaurativa(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=ATENDER_',
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}

	//Funcion ke rellena los datos del grid con los expedientes ke se encuentran en unidad de investigacion
	function gridUnidadInvestigacion(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{url:'<%=request.getContextPath()%>/BusquedaCanalizadosUnidadInvestigacion.do',
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}

	function ocultaMuestraGrids(idDivGrid)
	{
		if(idDivGrid == "turnoPrincipal"){
			$("#turnoPrincipal").show();
			$("#expedientePrincipal").hide();
			$("#divGridSolsGeneradas").hide();
			$("#divGridExpCompartidos").hide();
			$("#divGridSolsXAtndr").hide();
		}
		if(idDivGrid == "expedientePrincipal"){
			$("#turnoPrincipal").hide();
			$("#expedientePrincipal").show();
			$("#divGridSolsGeneradas").hide();
			$("#divGridExpCompartidos").hide();
			$("#divGridSolsXAtndr").hide();
		}
		if(idDivGrid == "gridSolsXAtndr"){
			$("#divGridSolsXAtndr").show();
			$("#turnoPrincipal").hide();
			$("#expedientePrincipal").hide();
			$("#divGridSolsGeneradas").hide();
			$("#divGridExpCompartidos").hide();
		}
		if(idDivGrid == "gridSolsGeneradas"){
			$("#turnoPrincipal").hide();
			$("#expedientePrincipal").hide();
			$("#divGridSolsGeneradas").show();
			$("#divGridExpCompartidos").hide();
			$("#divGridSolsXAtndr").hide();
		}
		if(idDivGrid == "expCompartidos"){
			$("#turnoPrincipal").hide();
			$("#expedientePrincipal").hide();
			$("#divGridSolsGeneradas").hide();
			$("#divGridExpCompartidos").show();
			$("#divGridSolsXAtndr").hide();
		}

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
			url: '<%= request.getContextPath()%>/consultaTiposSolsXArea.do',
			data: 'idArea='+idArea,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ValorDTO').each(function(){
					var idCampo = $(this).find("idCampo").text();
					var trTabla = '<li class="closed">';
					trTabla = trTabla + '<span class="folder">'+$(this).find("valor").text()+'</a></span>';
					trTabla = trTabla + '<ul>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsXAtndr('+idCampo+','+idArea+','+<%=EstatusSolicitud.ABIERTA.getValorId()%>+')">Abierta</a></span></li>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsXAtndr('+idCampo+','+idArea+','+<%=EstatusSolicitud.CERRADA.getValorId()%>+')">Cerrada</a></span></li>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsXAtndr('+idCampo+','+idArea+','+<%=EstatusSolicitud.EN_PROCESO.getValorId()%>+')">En proceso</a></span></li>';
					trTabla = trTabla + '</ul>';
					trTabla = trTabla + '</li>';
					$('#'+idDivArbol).append(trTabla);
				});
			}

		});
	}

	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsXAtndr(tipoSolicitud,idArea,estatus)
	{
		jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsXAtnder.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus='+estatus+'',datatype: "xml" });
		$("#gridSolsXAtndr").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsXAtndr");
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
			url: '<%= request.getContextPath()%>/consultaTiposSolsXArea.do',
			data: 'idArea='+idArea,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ValorDTO').each(function(){
					var idCampo = $(this).find("idCampo").text();
					var trTabla = '<li class="closed">';
					trTabla = trTabla + '<span class="folder">'+$(this).find("valor").text()+'</a></span>';
					trTabla = trTabla + '<ul>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsGeneradas('+idCampo+','+idArea+','+<%=EstatusSolicitud.ABIERTA.getValorId()%>+')">Abierta</a></span></li>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsGeneradas('+idCampo+','+idArea+','+<%=EstatusSolicitud.CERRADA.getValorId()%>+')">Cerrada</a></span></li>';
					trTabla = trTabla + '<li><span class="file"><a onclick="cargaGridSolsGeneradas('+idCampo+','+idArea+','+<%=EstatusSolicitud.EN_PROCESO.getValorId()%>+')">En proceso</a></span></li>';
					trTabla = trTabla + '</ul>';
					trTabla = trTabla + '</li>';
					$('#'+idDivArbol).append(trTabla);
				});
		}

		});
		}

	var idWindowDetalleSolicitud=1;

	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle
	*/
	function dblClickRowBandejaSolicitudes(rowID){
		idWindowDetalleSolicitud++;
		$.newWindow({id:"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Detalle Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,'<iframe src="<%=request.getContextPath()%>/consultarDetalleSolicitudBandeja.do?idSolicitud=' +rowID +'&tipoUsuario=1 " width="1140" height="450" />');
		}

	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle
	*/
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		$.newWindow({id:"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Detalle Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,'<iframe src="<%=request.getContextPath()%>/consultarDetalleSolicitudBandejaGen.do?idSolicitud=' +rowID +'&tipoUsuario=0 " width="1140" height="450" />');
		}


	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea)
	{
		jQuery("#gridSolsGeneradas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.CERRADA.getValorId()%>',datatype: "xml" });
		$("#gridSolsGeneradas").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsGeneradas");
	}

	function solonumeros(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		// if the key is backspace, tab, or numeric
		if (unicode == 8 || unicode == 9 || (unicode >= 48 && unicode <= 57)) {
		// we allow the key press
		return true;
		}
		else {
		// otherwise we don't
		return false;
		}
		}

	/*************  FUNCION PARA ABRIR LA VENTANA DE CAMBIO DE PASSWORD *************/
	function abreVentanaModificarContrasena()
	{
		$.newWindow({id:"iframewindowModificarPwdUsuario", statusBar: true, posx:400,posy:90,width:380,height:280,title:"Modificar Contraseña", type:"iframe"});
	    $.updateWindowContent("iframewindowModificarPwdUsuario",'<iframe src="<%=request.getContextPath()%>/cambiarContrasena.do?administrador=4" width="380" height="280" />');
	}

	function cerrarVentanaModificarContrasena()
	{
		var pantalla ="iframewindowModificarPwdUsuario";
		$.closeWindow(pantalla);
	}
	/************* fin  FUNCION PARA ABRIR LA VENTANA DE CAMBIO DE PASSWORD *************/
</script>

<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div id="turnoPrincipal">
				<table id="gridDetalleFrmPrincipal"></table>
				<div id="pager1"></div>
			</div>
			<div id="expedientePrincipal" style="display: none;" >
				<table id="gridDetalleExpediente" ></table>
				<div id="pagerExpediente"></div>
			</div>
			<div id="divGridSolsXAtndr" style="display: none;">
			 	<table id="gridSolsXAtndr"></table>
				<div id="pagerGridSolsXAtndr"></div>
			</div>
			<div id="divGridSolsGeneradas" style="display: none;">
			 	<table id="gridSolsGeneradas"></table>
				<div id="pagerGridSolsGeneradas"></div>
			</div>
			<!-- GRID Expedientes compartidos -->
			<div id="divGridExpCompartidos" style="display: none;">
			 	<table id="gridExpCompartidos"></table>
				<div id="pagerExpCompartidos"></div>
			</div>
		<div>
	</div>
</div>
</div>
</div>
<div id="dialog-confirm" title="Turno: ">
	<p align="center"><span style="font-size: 25px;" id="spanTipoTurno">.</span><br/><span style="font-size: 115px;" id="spanNumTurno">15</span></p>
</div>