<%@ page import="java.util.Date"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>

<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
<script	src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>

<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");

	Boolean tieneRolAgentemp = false;

	// Condicional realizada para validar que el usuario coordinadorAmp cuente con el rol de
	// agentemp. En caso contrario, el flujo es normal.
	if(usuario!=null && usuario.getUsuarioRoles()!=null){
		
		for (UsuarioRolDTO usuarioRolDTO : usuario.getUsuarioRoles()) {
			if(usuarioRolDTO.getRol().getRolId()!=null){
				if(usuarioRolDTO.getRol().getRolId() == Roles.AGENTEMP.getValorId()){
					tieneRolAgentemp = true;
				}
			}
		}
		
	}
%>

<script type="text/javascript">

	var tipoSolicitudAJR = '<%= TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>';
	var tipoSolicitudAP  = '<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>';
	var tipoSolicitudTS  = '<%= TiposSolicitudes.TRABAJO_SOCIAL.getValorId()%>';
	var tipoSolicitudCI  = '<%= TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId()%>';
	var idAreaPorAtender = '<%= Areas.UNIDAD_INVESTIGACION.ordinal()%>';
	var idAreaGeneradas	 = 0;
	var idDeptoExp = '<%= Areas.UNIDAD_INVESTIGACION.ordinal()%>';
	var idEstatusA = '<%= EstatusExpediente.ABIERTO.getValorId()%>';
	var idEstatusAD = '<%= EstatusExpediente.ARCHIVO_DEFINITIVO.getValorId()%>';
	var idEstatusAT = '<%= EstatusExpediente.ARCHIVO_TEMPORAL.getValorId()%>';
	var idEstatusTC = '2808';
	var idEstatusST = '<%= EstatusExpediente.SUSPENDIDO_TEMPORALMENTE.getValorId()%>';
	var idEstatusPA = '<%= EstatusExpediente.POR_ATENDER.getValorId()%>';
	var idEstatusCC = '2540';
	var idEstatusC = '<%= EstatusExpediente.CERRADO.getValorId()%>';
	var idEstatusCCMC = '2945';
	
	var menuExpedienteAsignado = false;	
	var tur;
	var idWindowNuevaDenuncia=1;
	var idWindowAMP=1;
	//Variable para controlar el id de las ventanas
	var idWindowVisorMedidasCautelaresPJENC=1;
	//variable para guardar el ID del area del usuario logueado
	var idAreaUserLogged='<%=Areas.COORDINACION_UNIDAD_INVESTIGACION.ordinal()%>';
	var idWindowDetalleSolicitud=1;
	var gridRecargaExpediente=true;
	var visualizaMenuAsignarExps = <%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAutoAsignarExpedientes()%>;
	var tieneRolAgentemp = <%=tieneRolAgentemp%>;

	//Al consultar Evidencias con retraso el combo solo habilita los tipos de Eslabon asosciados a un amp
	var visualizaTipoEslabon = 'amp';

	$(document).ready(function() {
		
		$('#remisionesIPH').click(cargaGridInformePolicial);
				
		$("#divGridSolsXAtndr").hide();
		
		//Mandamos consultar los tipos de solicitud dependiendo del Area del Funcionario
		consultarTiposSolicitudPorAreaDelFuncionarioGen('tableSolsGeneradas',"0");
		consultarTiposSolicitudPorAreaDelFuncionario('tableSolsXAtender',idAreaUserLogged);	
			
		//Grid de Solicitudes por atender
		jQuery("#gridSolsXAtndr").jqGrid({ 
			datatype: "xml", 
			autowidth: true,
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'], 
			colModel:[ 	{name:'caso',index:'caso', width:180,hidden:true},
			           	{name:'expediente',index:'expediente', width:180}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:90},
						{name:'fechaLimite',index:'fechaLimite', width:80,hidden:true},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsXAtndr'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
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
			autowidth: true,
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:180,hidden:true},
			           	{name:'expediente',index:'expediente', width:180}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:90},
						{name:'fechaLimite',index:'fechaLimite', width:80,hidden:true},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsGeneradas'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudesGen(rowid);
			}
		}).navGrid('#pagerGridSolsGeneradas',{edit:false,add:false,del:false});
		
		//Grid de Mandamientos Judiciales
		jQuery("#gridMandamientosJudiciales").jqGrid({ 
			datatype: "xml", 
			colNames:['Caso','Tipo', 'Persona (s)','Fecha'], 
			colModel:[ 	{name:'caso',index:'caso', width:250},
			           	{name:'tipo',index:'tipo', width:200}, 
						{name:'persona',index:'persona', width:200}, 
						{name:'fecha',index:'fecha', width:150}
			],
			pager: jQuery('#pagerGridMandamientosJudiciales'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pagerGridMandamientosJudiciales',{edit:false,add:false,del:false});
		
		//Ejemplo3.xml
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

		//Ejemplo4.xml
		jQuery("#gridDetalleFrmDos").jqGrid({ 
			datatype: "xml", 
			colNames:['Caso','Fecha Remitido', 'Denunciante', 'Delito'], 
			colModel:[ 	{name:'Caso',index:'detalle', width:125},
			           	{name:'Fecha',index:'fecha', width:125}, 
						{name:'Denunciante',index:'nombre', width:425}, 
						{name:'Delito',index:'Resumen', width:225}
			
					],
			pager: jQuery('#pager4'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			onSelectRow: function(id){
				nuevaDenuncia(id);
			},
			sortorder: "desc"
		}).navGrid('#pager4',{edit:false,add:false,del:false});	
					 		
		/**Queja Ciudadana - Pendiente**/
		jQuery("#gridQuejaPendiente").jqGrid({
			url : '<%= request.getContextPath()%>/consultaGridQuejasPEndientes.do', 
			datatype: "xml", 
			colNames:['Folio de Queja','Nombre de Quejoso','Calidad del Afectado','Nombre del Funcionario','Tipo de Queja'], 
			colModel:[ 	
			        	{name:'FolioQueja',index:'1', sortable:false,width: 150},
			           	{name:'NombreQuejoso',index:'2', sortable:false, width: 250},
			           	{name:'CalidadQuejoso',index:'3', sortable:false, width: 150}, 
			           	{name:'NombreFuncionario',index:'4', sortable:false, width: 250}, 
			           	{name:'TipoQueja',index:'5', sortable:false, width: 200}, 							
			
			],
			autowidth: true,
			pager: jQuery('#paginadorgridQuejaPendiente'),
			rowNum:10,
			rowList:[10,20,30],
			sortname: '1',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
				consultaQuejaCiudadana(rowid);
			} 
		}).navGrid('#paginadorgridQuejaPendiente',{edit:false,add:false,del:false});
		
		/**Queja Ciudadana - Concluida**/
		jQuery("#gridQuejaConcluida").jqGrid({
			url : '<%= request.getContextPath()%>/consultaGridQuejasConcluidas.do', 
			datatype: "xml", 
			colNames:['Folio de Queja','Nombre de Quejoso','Calidad del Afectado','Nombre del Funcionario','Motivo de Rechazo'], 
			colModel:[ 	
			        	{name:'FolioQueja',index:'1', sortable:false,width: 150},
			           	{name:'NombreQuejoso',index:'2', sortable:false, width: 250},
			           	{name:'CalidadQuejoso',index:'3', sortable:false, width: 150}, 
			           	{name:'NombreFuncionario',index:'4', sortable:false, width: 250}, 
			           	{name:'MotivoRechazo',index:'5', sortable:false, width: 200}, 							
			
			],
			autowidth: true,
			pager: jQuery('#paginadorgridQuejaConcluida'),
			rowNum:10,
			rowList:[10,20,30],
			sortname: '1',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
				quejaCiudadanaConcluida(rowid);
			} 
		}).navGrid('#paginadorgridQuejaConcluida',{edit:false,add:false,del:false});
		
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridQuejaPendiente").hide();
		$("#divGridQuejaConcluida").hide();

		generaMenuExpedientesDelArea();
		restablecerPantallas();
		consultaGeneralExpedientes();
	});
		//termina funcion onready

	function restablecerPantallas(){
	 	ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
		ajustarGridAlCentro($("#gridDetalleFrmUno"));
		ajustarGridAlCentro($("#gridDetalleFrmDos"));
		ajustarGridAlCentro($("#gridSolicitudes"));
		ajustarGridAlCentro($("#gridSolicitudesUno"));
		ajustarGridAlCentro($("#gridSolicitudesDos"));
		ajustarGridAlCentro($("#gridSolsXAtndr"));
		ajustarGridAlCentro($("#gridSolsGeneradas"));
		ajustarGridAlCentro($("#gridMandamientosJudiciales"));
		ajustarGridAlCentro($("#gridAudiencias"));
		ajustarGridAlCentro($("#gridInformePolicial"));
		ajustarGridAlCentro($("#gridQuejaPendiente"));
		ajustarGridAlCentro($("#gridQuejaConcluida"));
		ajustarGridAlCentro($("#gridEvidenciaAlmacenExpediente"));
		var params = {"titulo": true};
		ajustarGridAlCentro($("#gridCambiarResponsable"), params);
	}
		
		/*se agregan funciones para visualizacion por fecha/*ByYolo*/
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
		/*fin/*ByYolo*/
	
	function nuevaVentanita(numeroCasoNuevo,idNuevaDenuncia,ingresoDenuncia,numeroExpediente,pantallaSolicitada,numeroExpedienteId,idExpediente) {
		var pantallaSolicitada=3;
		idWindowNuevaDenuncia++;
		var ingresoDenuncia = true;
		customVentana(	"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,
						"Carpeta de investigación: ", 
						"/BusquedaExpediente.do",
						"?abreenPenal=abrPenal&ingresoDenuncia="+ingresoDenuncia +
						"&idNumeroExpediente="+numeroExpedienteId+"&pantallaSolicitada="+pantallaSolicitada);
	}
	
	function cierraVentanaAdjuntarDenuncia(){
		var pantalla ="iframewindowRDE";	
		$.closeWindow(pantalla);
	}
	
	/*
	*Funcion para realizar la consulta del grid de visitadores
	*/
	function cargaGridVisitadores(idDepartamento,idEstatus) {
		gIdDepartamento=idDepartamento;
		gIdEstatus=idEstatus;
		var activaExpedienteId = true;
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/busquedaCanalizadosRestaurativaStatus.do?estatus='+idEstatus+'&activaExpedienteId='+activaExpedienteId+'',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}
	
	function regresaGrid(){
		menuExpedienteAsignado = false;
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=RECIBIR_CANALIZACION&expedientesAsignados=false',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		ocultaMuestraGrids("gridDetalleFrmPrincipal");
	}

	function recargaExpedientesAsignados(){
		menuExpedienteAsignado = true;
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=RECIBIR_CANALIZACION&expedientesAsignados=true&menuUI=1',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		ocultaMuestraGrids("gridDetalleFrmPrincipal");		
	}

	//var numExpAlter = null;
	
	//Función que genera un nuevo número de expediente para la UI en el mismo expediente
	function nuevoNumeroExpediente(id){
			
		var idExpediente="0";
		var numeroExpediente="0";
		var numeroExpedienteId="0";
		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>+'&idExpediente='+id+'',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
    			/* numExpAlter=$(xml).find('expedienteDTO').find('esNuevo').text();
    			if(numExpAlter == ""){
    				numExpAlter=null;
        		} */
    		}
    		
    	});
		
    	if(numeroExpedienteId != "0"){
    		nuevaDenuncia(numeroExpedienteId);
        }
	}


    function nuevaDenuncia(id) {
    	var pantallaSolicitada=3;
		idWindowNuevaDenuncia++;
		 var ingresoDenuncia = true;
		customVentana(	"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, 
						"Carpeta de investigación: ", 
						"/BusquedaExpediente.do", 
						"?abreenPenal=abrPenal&ingresoDenuncia="+ingresoDenuncia +
						"&idNumeroExpediente="+id+"&pantallaSolicitada="+pantallaSolicitada);
						/* "&idNumeroExpediente="+id+"&pantallaSolicitada="+pantallaSolicitada +
						"&numExpAlter="+numExpAlter); */
	}
    
    function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Carpeta de investigación: "+num);
	}
	
	function activaPrincipal() {
		$("#divGridSolicitudes").css("display", "block");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridQuejaPendiente").hide();
		$("#divGridQuejaConcluida").hide();
	}

	function activaUno() {
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "block");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
	}
	
	function activaDos() {
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "block");
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
	}
	
	function asignarCancelarAMP() {
		idWindowAMP++;
		customVentana(	"iframewindowAsignaCancelaAMP" + idWindowAMP,
						 "Asignar / Cancelar AMP",
						 "/asignarCancelarAMP.do");
	}
	
	function actualizaGrid() {
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		activaPrincipal();
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsXAtndr(tipoSolicitud,idArea) {
		jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsXAtnder.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.CERRADA.getValorId()%>',datatype: "xml" });
		$("#gridSolsXAtndr").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsXAtndr");
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea) {
		jQuery("#gridSolsGeneradas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>,<%=EstatusSolicitud.CERRADA.getValorId()%>',datatype: "xml" });
		$("#gridSolsGeneradas").trigger("reloadGrid");
		ocultaMuestraGrids("gridSolsGeneradas");
	}
	
	/*
	 *Funcion para consultar los tipos de solicitud y generar 
	 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
	 * param - nombre del elemento en el que se construira de manera dinamica
	 * los tipos de solicutd
	 */
	function consultarTiposSolicitudPorAreaDelFuncionario(idDivArbol,idArea) {
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
					var trTabla = '<tr>';
					trTabla = trTabla + '<td><span><img src="<%=request.getContextPath()%>/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsXAtndr('+$(this).find("idCampo").text()+','+idArea+')">'+$(this).find("valor").text()+'</a></span></td>';
					trTabla = trTabla + '</tr>';
					$('#'+idDivArbol).append(trTabla);
				});
			}
			
		});
	}
	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudes(rowID) {
		idWindowDetalleSolicitud++;
    	customVentana(	"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,
    					"Detalle Solicitud", 
    					"/consultarDetalleSolicitudBandeja.do",
    					"?idSolicitud=" + rowID + "&tipoUsuario=0"); 
	}
	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudesGen(rowID) {
		idWindowDetalleSolicitud++;
		customVentana(	"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,
						"Detalle Solicitud",
						"/consultarDetalleSolicitudBandejaGen.do",
						"?idSolicitud=" + rowID + "&tipoUsuario=0");
	}
	
	/*
	*Funcion que oculta o muestra los grids, recibe como parametro
	*el nombre del grid que va a mostrar, y todos los demas, se 
	*ocultaran
	*/ 
	function ocultaMuestraGrids(nombreGrid) {
		var divGrid = "#";

		$("#botonesExpedientes").hide();

		if(nombreGrid == "gridDetalleFrmPrincipal") {
			divGrid += "divGridSolicitudes";
			if(visualizaMenuAsignarExps == 1 && tieneRolAgentemp===true){
				// Muestra el botón de asignación de expedientes
				if(menuExpedienteAsignado==false){
					jQuery("#gridDetalleFrmPrincipal").setGridParam({ multiboxonly: true }).showCol('cb');
    				ajustarGridPrincipalAlCentro($("#gridDetalleFrmPrincipal"));
    				$("#botonesExpedientes").show();
				}
				else{
				// Oculta el botón de asignación de expedientes
				// si la consulta es por expedientes asignados
					jQuery("#gridDetalleFrmPrincipal").setGridParam({ multiboxonly: true }).hideCol('cb');
					restablecerPantallas();
					$("#botonesExpedientes").hide();
				}
    		}
    	} else if (nombreGrid == "gridSolsXAtndr") {
			divGrid += "divGridSolsXAtndr";
		}else if (nombreGrid == "gridSolsGeneradas") {
			divGrid += "divGridSolsGeneradas";
		} else if (nombreGrid == "gridMandamientosJudiciales") {
			divGrid += "divGridMandamientosJudiciales";
		} else if (nombreGrid == "gridAudiencias") {
			divGrid += "divGridAudiencias";
		} else if (nombreGrid == "informePolicial") {
			divGrid += "divGridInformePolicial";
		} else if (nombreGrid == "gridQuejaPendiente") {
			divGrid += "divGridQuejaPendiente";
		} else if(nombreGrid == "gridQuejaConcluida") {
			divGrid += "divGridQuejaConcluida";
		} else if(nombreGrid == "gridEvidenciaAlmacenExpediente") {
			divGrid += "divGridDetalleEvidencia";
		} else {
			divGrid +=nombreGrid;
		}
		
		$("#divGridSolicitudes").hide();
		$("#divGridSolicitudesUno").hide();
		$("#divGridSolicitudesDos").hide();
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		$("#divGridMandamientosJudiciales").hide();
		$("#divGridAudiencias").hide();
		$("#divGridInformePolicial").hide();
		$("#divGridQuejaPendiente").hide();
		$("#divGridQuejaConcluida").hide();
		$("#divGridDetalleEvidencia").hide();	
		$(divGrid).hide();
		$(divGrid).show();
		
	}
	
	/*
	 *Funcion para consultar los tipos de solicitud y generar 
	 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
	 * param - nombre del elemento en el que se construira de manera dinamica
	 * los tipos de solicutd
	 */
	function consultarTiposSolicitudPorAreaDelFuncionarioGen(idDivArbol,idArea) {
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
					var trTabla = '<tr>';
					trTabla = trTabla + '<td><span><img src="<%=request.getContextPath()%>/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsGeneradas('+$(this).find("idCampo").text()+',0)">'+$(this).find("valor").text()+'</a></span></td>';
					trTabla = trTabla + '</tr>';
					$('#'+idDivArbol).append(trTabla);
				});
			}
			
		});
	}

	//**************************************************************FUNCIONALIDAD ACORDEON ADMINISTRAR MEDIDAS CAUTELARES*********************************************/
	/**
	*Funcion que abre la ventana modal para introducir el numero de causa
	*/
	function abreModalCausa(){
		
		$("#datoCausa").val("");
		$("#divCausa").dialog("open");
	  	$("#divCausa").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Administrar medidas cautelares por n&uacute;mero de causa', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 350,
		  	height: 260,
		  	maxWidth: 350,
		  	buttons:{"Realizar Busqueda":function() {
		  		var numeroCausa = $("#datoCausa").val();
		  		mostrarVentanaInvolucradosCausa(numeroCausa);
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}

	/*Funcion que abre el visor de medidas cautelares 
	*/
	function mostrarVentanaInvolucradosCausa(numeroCausa){
		idWindowVisorMedidasCautelaresPJENC++;
    	customVentana(	"iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC,
    					"Visor de Medidas Cautelares",
    					"/visorMedidaCautelar.do",
    					"?numeroCausa=" + numeroCausa); 
	}

	/*
	*Funcion para generar dinamicamente el menu izquierdo para la opcion de 
	*expedientes del area
	*/
	function generaMenuExpedientesDelArea()
	{
		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/consultarEstadosPorDepartamentoDinamico.do',
    		data: 'area='+<%= Areas.UNIDAD_INVESTIGACION.ordinal()%>,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
				$(xml).find('estatus').each(function(){
					//barremos el grid para generar la cadena de IDs de los delitos normales
					//$("#seccion1tree").append("<li><span class='file'><a onclick='cargaGridVisitadores("+<%= Areas.UNIDAD_INVESTIGACION.ordinal()%>+","+$(this).find('idCampo').text()+")'>"+$(this).find('valor').text()+"</a></span></li>");
    			});
    		}
    		
    	});
	}
	
	
	 function gridAudiencias()
	 {
		 ///Grid de Audiencias
		 jQuery("#gridAudiencias").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarAudienciasDefensor.do', 
				datatype: "xml", 
				colNames:['Caso','Carácter','Tipo de Audiencia','Fecha de Audiencia','Sala','Defensor de Oficio'], 
				colModel:[{name:'caso',	 	index:'2002', 		width:200, align:"let"},
				          {name:'caracter',	index:'2037', 	width:100, align:"center"},
				          {name:'tipo',	 	index:'2017', 	    width:120, align:"center"},
				          {name:'fechaHora',index:'2018',	width:200, align:"center"},
				          {name:'sala' ,	index:'2029', 		width:110, align:"center"},
				          {name:'defensor' ,index:'2030', 	width:150, align:"center"}
						],
				
				pager: jQuery('#pagerGridAudiencias'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					var ret = jQuery("#gridAudiencias").jqGrid('getRowData',rowid);
					caso = ret.caso;
					detalleAudiencia(rowid, caso);
				}
				
			}).navGrid('#pagerGridAudiencias',{edit:false,add:false,del:false});
			ocultaMuestraGrids('divGridAudiencias');
			jQuery("#gridAudiencias").trigger('reloadGrid');
			$("#gview_gridAudiencias .ui-jqgrid-bdiv").css('height', '450px');
	 }
	 
	 //Funcion para consultar el detalle de una Audiencia
	 function detalleAudiencia(rowId, caso) {
		    customVentana(	"iframewindowDetalleAudiencia", 
		    				"Caso: " + caso,
		    				"/detalleAudienciaDefensoria.do",
		    				"?idAudiencia="+rowId);
	}
	 
	 function muestraGridAudiencias()
	 {
		 gridAudiencias();
		 ocultaMuestraGrids('gridAudiencias');
	 }
	
	
	/******************************************************FUNCIONES PARA REMISIONES DE IPH***********************************************************************************/
	
	//variable para controlar la carga del grid de informe policial
	var primeraVezGridInformePolicial=true;
	
	/*
	*Funcion que carga el grid de consulta por fechas
	*/
	function cargaGridInformePolicial(){

		if(primeraVezGridInformePolicial == true){
			
			jQuery("#gridInformePolicial").jqGrid({ 
				url:'<%=request.getContextPath()%>/remisionesIPH.do', 
				datatype: "xml", 
				autowidth: true,
				colNames:['Caso','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
				colModel:[ 	{name:'Detalle',index:'1', width:140}, 
							{name:'Fecha',index:'2', width:55}, 
							{name:'Nombre',index:'3'}, 
							{name:'Resumen',index:'4', width:80},
							{name:'Origen',index:'5', width:50},
							{name:'Estatus',index:'6'}
						],
				pager: jQuery('#pagergridInformePolicial'),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				height:420,
				sortname: 'detalle',
				viewrecords: true,
				ondblClickRow: function(id) {
					 nuevaDenuncia(id);
				},
				sortorder: "desc"
			}).navGrid('#pagergridInformePolicial',{edit:false,add:false,del:false});	 
			//Resize del grid
			$("#gridInformePolicial").setGridWidth($("#mainContent").width() - 5, true);
		    ocultaMuestraGrids("informePolicial");
			primeraVezGridInformePolicial=false;
		}
		else{
			jQuery("#gridInformePolicial").jqGrid('setGridParam', { datatype: "xml" });
			$("#gridInformePolicial").trigger("reloadGrid");
			ocultaMuestraGrids("informePolicial");				  
		}
	}
	
	/****QUEJA CIUDADANA**/
	function muestraQuejaCiudadana(){
		
		$.newWindow({id:"iframewindowQuejaCiudadana", statusBar: true, posx:255,posy:111,width:1023,height:473,title:"Queja Ciudadana", type:"iframe"});
	    $.updateWindowContent("iframewindowQuejaCiudadana",'<iframe src="<%=request.getContextPath()%>/quejaCiudadana.do" width="1023" height="473" />'); 
	}
	
	//Ventana de captura de queja ciudadana
	function consultaQuejaCiudadana(rowid){
		$.newWindow({id:"iframewindowConsultaQuejaCiudadana", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Consulta de Queja Ciudadana", type:"iframe"});
	    $.updateWindowContent("iframewindowConsultaQuejaCiudadana",'<iframe src="<%=request.getContextPath()%>/consultaQuejaCiudadana.do?idQueja='+rowid+'" width="850" height="350" />'); 
	}
	
	function cerrarVentanaConsultaQueja(){
		var pantalla ="iframewindowConsultaQuejaCiudadana";
		$.closeWindow(pantalla);
		muestraGridQuejaPendiente();
	}
	
	//Ventana de queja ciudadana concluida
	function quejaCiudadanaConcluida(rowid){
		$.newWindow({id:"iframewindowQuejaCiudadanaConcluida", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Queja Ciudadana Concluida", type:"iframe"});
	    $.updateWindowContent("iframewindowQuejaCiudadanaConcluida",'<iframe src="<%=request.getContextPath()%>/quejaCiudadanaConcluida.do?idQueja='+rowid+'" width="850" height="350" />'); 
	}
	
	function cerrarVentanaQuejaConcluida(){
		var pantalla ="iframewindowQuejaCiudadanaConcluida";
		$.closeWindow(pantalla);
	}
	
	function cerrarVentanaQueja(){
		var pantalla ="iframewindowQuejaCiudadana";
		$.closeWindow(pantalla);
	}
	
	/*
	*Funcion para realizar la consulta del grid de Quejas Pendientes
	*/
	function muestraGridQuejaPendiente() {
		jQuery("#gridQuejaPendiente").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaGridQuejasPEndientes.do',datatype: "xml" });
		$("#gridQuejaPendiente").trigger("reloadGrid");
		ocultaMuestraGrids("gridQuejaPendiente");
	}

	/*
	*Funcion para realizar la consulta del grid de Quejas Concluidas
	*/
	function muestraGridQuejaConcluida() {
		jQuery("#gridQuejaConcluida").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaGridQuejasConcluidas.do',datatype: "xml" });
		$("#gridQuejaConcluida").trigger("reloadGrid");
		ocultaMuestraGrids("gridQuejaConcluida");
	}
	
	function cambiarResponsableExpediente() {
		customVentana("cambiarResponsableExpediente", "Cambiar Responsable A Expediente", "/cambiarResponsableExpediente.do");
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
			
			ajustarGridAlCentro($("#gridEvidenciaAlmacenExpediente"));
		}
	   
       function lanzaMenuGestionarAlmacen(idEvidencia) {
           idParaConsultaEvidencia=idEvidencia;
            opcionSeleccionadaEnElMenu = 1;
	        $.newWindow({id:"iframewindowEntradasAlmacen", statusBar: true, posx:255,posy:133,width:803,height:760,title:"Evidencia con retraso", type:"iframe"});
            $.updateWindowContent("iframewindowEntradasAlmacen",'<iframe src="<%= request.getContextPath() %>/menuGestionarAlmacen.do?opcionSeleccionadaEnElMenu='+opcionSeleccionadaEnElMenu+'&visualizaTipoEslabon='+visualizaTipoEslabon+'"    width="803" height="760" />');
       }

	// Consulta de expedientes sin asignar
    function consultaGeneralExpedientes(){
    		
   		if(gridRecargaExpediente==true){
       		
       		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
       			url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=RECIBIR_CANALIZACION&expedientesAsignados=false', 
       			datatype: "xml", 
       			colNames:['Número de Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
       			colModel:[ 	{name:'Detalle',index:'1',width:100},
       			           	{name:'Tipo',index:'7', width:120, align:'center', hidden:true}, 
       						{name:'Fecha',index:'2',width:55}, 
       						{name:'Nombre',index:'3'}, 
       						{name:'Resumen',index:'4'},
       						{name:'Origen',index:'5',width:50},
       						{name:'Estatus',index:'6',width:50}
       					],
       			pager: jQuery('#divGridDetalleFrmPrincipal'),
       			rowNum:10,
       			rowList:[10,20,30,40,50,60,70,80,90,100],
       			autowidth: true,
       			shrinkToFit: true,
       			sortname: 'Fecha',
				multiselect: true,
				height: 500,
       			viewrecords: true,
       			ondblClickRow: function(id) {
       				// Muestra el detalle del número de expediente, si el coordinadorAmp
       				// no tiene el rol de agentemp y la bandera esta apagada
       				if(!(visualizaMenuAsignarExps == 1 && tieneRolAgentemp===true)){
	       				nuevoNumeroExpediente(id);
       				}
       			},
       			sortorder: "desc"
       		}).navGrid('#divGridDetalleFrmPrincipal',{edit:false,add:false,del:false});

       		gridRecargaExpediente=false;
       		
    		if(visualizaMenuAsignarExps == 1 && tieneRolAgentemp===true ){
    			ajustarGridPrincipalAlCentro($("#gridDetalleFrmPrincipal"));
    			$("#botonesExpedientes").show();
    		}
    		else{
    			restablecerPantallas();
    			// Remplazar instrucción:
    			// jQuery("#gridDetalleFrmPrincipal").setGridParam({multiselect:false}).hideCol('cb');
    			// Por:
    			jQuery("#gridDetalleFrmPrincipal").setGridParam({ multiboxonly: true }).hideCol('cb');
    		}
   		}
   		else{			
   			jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=UI&actividad=RECIBIR_CANALIZACION&expedientesAsignados=false',
   					datatype: "xml" });
   			$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
   		}
   	
   		ocultaMuestraGrids("gridDetalleFrmPrincipal");
   	}
    
    // Ajusta el grid al centro y dispone un espacio para mostrar el botón de
    // asignación de expedientes
    function ajustarGridPrincipalAlCentro(grid, params){
    	var height = 0;
    	grid.setGridWidth($("#mainContent").width() - 5, true);
    	
    	if (params === undefined){
    		height = (($("#mainContent").height() - $("#ui-layout-south").height()) - 60);
    	} else {
    		try {
    			if(params.titulo){
    				height = (($("#mainContent").height() - $("#ui-layout-south").height()) - 80);
    			}
    		}catch(e){
    			height = (($("#mainContent").height() - $("#ui-layout-south").height()) - 60);	
    		}
    	}
    	height=height-200;
    	grid.setGridHeight(height, true);
    	
    }

	// Recolección de los números de expedientes a asignar
    function asignarExps(){
    	
    	var longitudTabla = jQuery("#gridDetalleFrmPrincipal").getDataIDs();
		var numeroExpedientes = longitudTabla.length;
		var i=0;
		var idsExpedientes="";
		
		if(jQuery("#gridDetalleFrmPrincipal").jqGrid('getGridParam','selarrrow') != ""){
			//Recolecta los ids. En este caso, los ids hacen referencia al Expediente_id 
			idsExpedientes = jQuery("#gridDetalleFrmPrincipal").jqGrid('getGridParam','selarrrow');
			nuevosNumerosDeExpedientes(idsExpedientes);
		}
		else{
			customAlert("Favor de seleccionar al menos un registro, para que se pueda realizar la asignación de expedientes");
		}
    }
    
    // Generación de los nuevos números de expedientes
	function nuevosNumerosDeExpedientes(idsExpedientes){
		
		var idExpediente="0";
		var numeroExpediente="0";
		var numeroExpedienteId="0";
		var idArea='<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>';
		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevosNumerosDeExpedientes.do?idArea='+idArea+'&idsExpedientes='+idsExpedientes+'',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){    		
    			regresaGrid();
    			var resultado=$(xml).find('boolean').text();
    			if(resultado===true || resultado=="true"){        		
    				customAlert("Se realizó de manera correcta la asignación de carpetas de investigación");
    			}
    			else{
    				customAlert("La asignación de carpetas de investigación no se realizó de manera correcta");
    			}
    		}    		
    	});
	}
	
</script>	
<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class=ui-layout-north>
				<div id="divGridSolicitudes">
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="divGridDetalleFrmPrincipal"></div>
				</div>
				<div id="divGridSolicitudesUno" style="display: none;">
					<table id="gridDetalleFrmUno"></table>
					<div id="pager3"></div>
				</div>
				<div id="divGridSolicitudesDos" style="display: none;">
					<table id="gridDetalleFrmDos"></table>
					<div id="pager4"></div>
				</div>
				<div id="divGridSolsXAtndr" style="display: none;">
					 	<table id="gridSolsXAtndr" width="100%" height="100%"></table>
						<div id="pagerGridSolsXAtndr"></div>
					</div>
				<div id="divGridSolsGeneradas" style="display: none;">
					 	<table id="gridSolsGeneradas" width="100%" height="100%"></table>
						<div id="pagerGridSolsGeneradas"></div>
				</div>	
				
				<div id="divGridMandamientosJudiciales" style="display: none;">
					 	<table id="gridMandamientosJudiciales" width="100%" height="100%"></table>
						<div id="pagerGridMandamientosJudiciales"></div>
				</div>
				<div id="divGridAudiencias">
					<table id="gridAudiencias"></table>
					<div id="pagerAudiencias"></div>
				</div>
				<div id="divGridInformePolicial">
					<table id="gridInformePolicial"></table>
					<div id="pagergridInformePolicial"></div>
				</div>
				<div id="divGridQuejaPendiente" >
					<table id="gridQuejaPendiente" ></table>
					<div id="paginadorgridQuejaPendiente"></div>
				</div>
				
				<div id="divGridQuejaConcluida" >
					<table id="gridQuejaConcluida" ></table>
					<div id="paginadorgridQuejaConcluida"></div>
				</div>
				
				<div id="divGridDetalleEvidencia">
                     <table id="gridEvidenciaAlmacenExpediente"></table>
                     <div id="paginadorEvidenciaAlmacenExpediente"></div>
                </div>
                
                <div id="botonesExpedientes">
					<table id="botonesExp" width="100%">
						<tr><td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td align="center">
								<input id="btnAsignar" class="btn_Generico" type="button" value="Autoasignar Expedientes" onclick="asignarExps();">
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<!--Comienza div para mostrar la ventana para ingresar el numero de causa-->	
	<div id="divCausa" style="display: none">
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
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="datoCausa"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>