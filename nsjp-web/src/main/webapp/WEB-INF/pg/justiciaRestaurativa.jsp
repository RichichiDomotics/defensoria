<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@page import="java.util.Date"%>z
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page
	import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>

<script type="text/javascript">

	var idWindowNuevaDenuncia=1;
	var idWindowDetalleSolicitud=1;
	
	$(document).ready(function() {
		
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
								url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=ATENDER_CANALIZACION', 
								datatype: "xml", 
								colNames:['Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus'],  
								colModel:[ 	{name:'Detalle',index:'detalle', width:70},
								           	{name:'Tipo',index:'tipo', width:120, align:'center', hidden:true}, 
											{name:'Fecha',index:'fecha', width:70}, 
											{name:'Nombre',index:'nombre', width:70}, 
											{name:'Resumen',index:'Resumen', width:110},
											{name:'Origen',index:'origen', width:110},
											{name:'Estatus',index:'estatus', width:110}
										],
								pager: jQuery('#pager1'),
								rowNum:10,
								rowList:[10,20,30,40,50, 60, 70, 80, 90, 100],
								autowidth: true,
								sortname: 'detalle',
								viewrecords: true,
								onSelectRow: function(id){
									//muestraAcordeon();
									},
								ondblClickRow: function(id) {
									nuevaDenuncia(id);
									},
								sortorder: "desc"
							}).navGrid('#pager1',{edit:false,add:false,del:false});	

		
		//Ejemplo2.xml    |      |      |          |            |         

		
		jQuery("#gridDetalleFrmSecundario").jqGrid({ 
//			url:'<%=request.getContextPath()%>/.do', 
			datatype: "xml", 
			colNames:['Expediente','Fecha remitido','Denunciante', 'Delito principal', 'Origen','Estatus'], 
			colModel:[ 	{name:'Caso',index:'detalle', width:130},
			           	{name:'Expediente',index:'detalle', width:130}, 
						{name:'Fecha',index:'fecha', width:120}, 
						{name:'Denunciante',index:'nombre', width:210}, 
						{name:'Denunciante2',index:'nombre2', width:200}, 
						{name:'Delito',index:'Resumen', width:200}
			
					],
			pager: jQuery('#pager2'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			onSelectRow: function(id){
				nuevaDenuncia(id);
				},
			sortorder: "desc"
		}).navGrid('#pager2',{edit:false,add:false,del:false});


		//Ejemplo3.xml UNIDAD DE INVESTIGACION
		jQuery("#gridPorUnidadInvestigacion").jqGrid({ 
			url:'<%=request.getContextPath()%>/BusquedaExpPorAtPenalyUInvestigacion.do?area=<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>',
			datatype: "xml", 
			colNames:['Expediente','Fecha Remitido', 'Denunciante', 'Delito'], 
			colModel:[ 	{name:'Expediente',index:'detalle', width:155},
			           	{name:'Fecha',index:'fecha', width:155}, 
						{name:'Denunciante',index:'nombre', width:145}, 
						{name:'Delito',index:'Resumen', width:145}
			
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

		//Ejemplo4.xml ATENCION PENAL
		jQuery("#divGridAtencionTempranaPenal").jqGrid({ 
			url:'<%=request.getContextPath()%>/BusquedaExpPorAtPenalyUInvestigacion.do?area=<%=Areas.ATENCION_TEMPRANA_PG_PENAL.ordinal()%>',
			datatype: "xml", 
			colNames:['Expediente','Fecha Remitido', 'Denunciante', 'Delito'], 
			colModel:[ 	{name:'Expediente',index:'detalle', width:145},
			           	{name:'Fecha',index:'fecha', width:175}, 
						{name:'Denunciante',index:'nombre', width:245}, 
						{name:'Delito',index:'Resumen', width:245}
			
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

		//Grid de Solicitudes generadas
		jQuery("#gridSolsGeneradas").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd=0&idArea=0&estatus=0', 
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:150},
			           	{name:'expediente',index:'expediente', width:130}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100},
						{name:'fechaLimite',index:'fechaLimite', width:100},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsGeneradas'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudesGen(rowid);
					}
		}).navGrid('#pagerGridSolsGeneradas',{edit:false,add:false,del:false});
				
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
				nuevaDenuncia(id);
			},
			sortorder: "desc"
		}).navGrid('#pagerExpCompartidos',{edit:false,add:false,del:false});
		//FIN GRID Expedientes Compartidos
		
		
		//agregamos el click para redireccionar a la valoracion de hechos
			
		$("#datosDenuncia").click(nuevaDenuncia);
		$("#divGridSolsGeneradas").hide();

		//cargo los datos del grid desde la BD
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=ATENDER_CANALIZACION', 
				datatype: "xml" });
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
			 
			restablecerPantallas();			 
			 
		});


	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		$.newWindow({id:"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Detalle Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,'<iframe src="<%=request.getContextPath()%>/consultarDetalleSolicitudBandejaGen.do?idSolicitud=' +rowID +'&tipoUsuario=0 " width="1140" height="450" />'); 
	}
	
	function seleccionFila(){
		$("#gridDetalleFrmPrincipal").find("tr:nth-child(2)").css({ color: "#FFFFFF", background: "#FF0000" });
		//$("#gridDetalleFrmPrincipalDos").find("tr:nth-child(2)").css({ color: "#FFFFFF", background: "#FF0000" });
	}

	function nuevaDenuncia(id) {
		var pantallaSolicitada=5;
		idWindowNuevaDenuncia++;
		 var ingresoDenuncia = true;
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+id+'&pantallaSolicitada='+pantallaSolicitada+'" width="1430" height="670" />');
		
	}

	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
			

		var casoAbierto = Array();
		
		function agregaExpediente (idCaso) {	
			//alert("agregarExpediente:" + idCaso)	
			if (casoAbierto[idCaso ] != true) {
				$.ajax({
		    		type: 'POST',
		    		url: '<%= request.getContextPath()%>/BusquedaCaso.do',
		    		data: 'idCaso=' + idCaso,
		    		dataType: 'xml',
		    		async: true,
		    		success: function(xml){
		    			$(xml).find('expediente').each(function(){
		    				var branches = $("<ul><li id='" + $(this).find('expedienteId').text() + "EXP' onclick='verExpediente(" + $(this).find('expedienteId').text() + ",\"" + $(this).find('numeroExpediente').text() + "\")'><span class='file'>" + $(this).find('numeroExpediente').text() + "</span><ul></ul></li></ul>").appendTo("#" + idCaso + "CASO");
		    				$("#" + idCaso + "CASO").treeview({
		    					add: branches
		    				});
			    		});
		    		}		    		
		    	});
			}
			casoAbierto[idCaso] = true;
		}

	function restablecerPantallas(){
	 	ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
		ajustarGridAlCentro($("#gridDetalleFrmSecundario"));
		ajustarGridAlCentro($("#gridPorUnidadInvestigacion"));
		ajustarGridAlCentro($("#divGridAtencionTempranaPenal")); 
		ajustarGridAlCentro($("#divGridAtencionTemprana"));

									
		$("#accordionmenuprincipal").accordion("resize");
		$("#accordionmenuderprincipal").accordion("resize");										
	}
	
	
	function ocultaMuestraGridsAlertas(idGrid)
	{
		if(parseInt(idGrid)==1)
		{
			$("#divGridSolicitudes").css("display", "block");
			$("#divGridAudiencia").css("display", "none");
			$("#divGridUnidadInvestigacion").css("display", "none");
			$("#divGridSolsGeneradas").hide();
		}
		else
		{
			$("#divGridSolicitudes").css("display", "none");
			$("#divGridAudiencia").css("display", "block");
			$("#divGridUnidadInvestigacion").css("display", "none");
			$("#divGridSolsGeneradas").hide();
			
		}
		$("#divGridExpCompartidos").css("display", "none");
	}

	function activaGridSolsGeneradas()
	{
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesSecundario .ui-jqgrid-bdiv").css('height', '337px');
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "none");
		$("#divGridAtencionTemprana").css("display", "none");
		$("#divGridSolsGeneradas").show();
		$("#divGridExpCompartidos").css("display", "none");
	}
	
	function activaSecundario() {
		
		$("#divGridSolicitudesSecundario").css("display", "block");
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesSecundario .ui-jqgrid-bdiv").css('height', '337px');
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "none");
		$("#divGridAtencionTemprana").css("display", "none");
		$("#divGridSolsGeneradas").hide();
		$("#divGridExpCompartidos").css("display", "none");
	}

	function activaPrincipal() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "block");
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "none");
		$("#divGridAtencionTemprana").css("display", "none");
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
		$("#divGridSolsGeneradas").hide();
		$("#divGridExpCompartidos").css("display", "none");
	}
	///UNIDAD DE INVESTIGACION
	function activaUno() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "block");
		$("#divGridUnidadInvestigacion .ui-jqgrid-bdiv").css('height', '730px');
		$("#divGridAtencionTemprana").css("display", "none");
		$("#divGridSolsGeneradas").hide();
		$("#divGridExpCompartidos").css("display", "none");
		
	}
	///ATENCION TEMPRANA
	function activaDos() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "none");
		$("#divGridAtencionTemprana").css("display", "block");
		$("#divGridAtencionTemprana .ui-jqgrid-bdiv").css('height', '730px');
		$("#divGridSolsGeneradas").hide();
		$("#divGridExpCompartidos").css("display", "none");
	}
	

	function activaCompartidos() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridUnidadInvestigacion").css("display", "none");
		$("#divGridAtencionTemprana").css("display", "none");
		$("#divGridExpCompartidos").css("display", "block");
		//$("#divGridAtencionTemprana .ui-jqgrid-bdiv").css('height', '337px');
		$("#divGridSolsGeneradas").hide();
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea)
	{
		jQuery("#gridSolsGeneradas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>',datatype: "xml" });
		$("#gridSolsGeneradas").trigger("reloadGrid");
		activaGridSolsGeneradas();
	}
	
	
	/*
	*Funcion para recargar el grid de expedientes compartidos desde el arbol izquierdo
	*/
	function activaExpCompartidos(){
		jQuery("#gridExpCompartidos").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaExpCompartidosFuncionarioLog.do', 
				datatype: "xml" });
			 $("#gridExpCompartidos").trigger("reloadGrid"); 
			 //ocultaMuestraGrids("expCompartidos");
			 activaCompartidos();
			$("#gridExpCompartidos").setGridWidth($("#mainContent").width() - 5, true);
			ajustarGridAlCentro($("#gridExpCompartidos"));
	}
	
	function porUnidadDeInvestigacion(){
		jQuery("#gridPorUnidadInvestigacion").jqGrid('setGridParam',
				{url: '<%= request.getContextPath()%>/BusquedaExpPorAtPenalyUInvestigacion.do?area=<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>',
			datatype: "xml"});
			$("#gridPorUnidadInvestigacion").trigger("reloadGrid");
			activaUno();
			$("#gridPorUnidadInvestigacion").setGridWidth($("#mainContent").width() - 5, true);
			ajustarGridAlCentro($("#gridPorUnidadInvestigacion"));
	}
	
	function porAtencionTempranaPenal(){
		jQuery("#divGridAtencionTempranaPenal").jqGrid('setGridParam',
				{url: '<%= request.getContextPath()%>/BusquedaExpPorAtPenalyUInvestigacion.do?area=<%=Areas.ATENCION_TEMPRANA_PG_PENAL.ordinal()%>',
			datatype: "xml"});
		$("#divGridAtencionTempranaPenal").trigger("reloadGrid");
		activaDos();
		$("#divGridAtencionTempranaPenal").setGridWidth($("#mainContent").width() - 5, true);
		ajustarGridAlCentro($("#divGridAtencionTempranaPenal"));
	}
	
</script>

<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
				<div id="divGridSolicitudes">
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="pager1"></div>
				</div>
				<div id="divGridSolicitudesSecundario" style="display: none;">
					<table id="gridDetalleFrmSecundario"></table>
					<div id="pager2"></div>
				</div>
				<div id="divGridUnidadInvestigacion" style="display: none;">
					<table id="gridPorUnidadInvestigacion"></table>
					<div id="pager3"></div>
				</div>
				<div id="divGridAtencionTemprana" style="display: none;">
					<table id="divGridAtencionTempranaPenal"></table>
					<div id="pager4"></div>
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
			</div>
		</div>
	</div>
</div>
