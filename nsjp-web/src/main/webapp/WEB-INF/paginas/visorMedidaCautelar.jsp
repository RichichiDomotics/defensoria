<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
	 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Visor Medida Cautelar</title>

	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>	
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/medidas.js"></script>
	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

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
	
	var ocultarAdd = 	   '<%= request.getParameter("ocultarAdd")%>';
	var numeroCausa = 	   '<%= request.getParameter("numeroCausa")%>';
	var numeroExpedienteId = 	   '<%= request.getParameter("numeroExpedienteId")%>';
	var flujoMedCautelar = '<%= request.getParameter("flujoMedCautelar")%>';
	var rolActivo = '<%=rolActivo%>';
	var contextoPagina = "${pageContext.request.contextPath}";
	
	$(document).ready(function() {
		
		//Carga el grid  de Involucrados por Causa
		cargaGridInvolucradosCausaPJENC(numeroCausa);
		$("#tabsprincipalconsulta" ).tabs();
		
		if(ocultarAdd == "true" || 
			// flujoMedCautelar define de donde a donde va a viajar la medida cautelar, si no va asignado el valor, no se debe mostrar el botón
			// agregar. Checar en ingresarMedidasCautelaresPJENC los flujos que se han definido.
			(flujoMedCautelar=="" || flujoMedCautelar==null || flujoMedCautelar=="null")){
			jQuery("#divBotonNuevaMedidaCautelar").hide();
		}
		
		// Este servicio tiene como parámetro de acción en ingresarMedidasCautelaresPJENC.jsp
		// a flujoMedCautelar
		if(flujoMedCautelar=="dePJaSSPyPGJ"){
			//Ocultar Periodicidad
			jQuery("#gridInvolucradosCausaPJENC").jqGrid('hideCol',"periodicidad");
		}
		
		if (rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
			$(":enabled").attr('disabled','disabled');
			$('input[type="submit"]').hide();
			$('input[type="button"]').hide();
		}
	});
	

	var cargaGridInvolucradosMedidas=true;
	/**
	*Funcion que consulta los involucrados por causa
	*/
	function cargaGridInvolucradosCausaPJENC(numeroCausa){

		if(cargaGridInvolucradosMedidas == true){

			jQuery("#gridInvolucradosCausaPJENC").jqGrid(
					{ url:'<%= request.getContextPath() %>/consultarMedidasCautelaresPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'', 						
						datatype: "xml",
						mtype: 'POST',
		    			colNames:['Nombre','Medida Cautelar','Fecha de creación', 'Fecha de inicio', 'Fecha de fin','Periodicidad','Descripci&oacute;n', 'Estado','Encargado Seguimiento'], 
						colModel:[ 	{name:'nombre',index:'nombre', width:200, sortable:false}, 
									{name:'medidaCautelar',index:'4', width:200, sortable:true},
									{name:'fechaCreacion',index:'6', width:110, sortable:true}, 
									{name:'fechaInicio',index:'1', width:100, sortable:true},
									{name:'fechaFin',index:'2', width:100, sortable:true},
									{name:'periodicidad',index:'3', width:150 , sortable:true},
									{name:'descripcion',index:'descripcion', width:150 , sortable:false},
									{name:'estadoMedida',index:'5', width:150 , sortable:true},
									{name:'encargadoSeguimiento',index:'encargadoSeguimiento', width:150,hidden: true},

								],		
							autowidth: false,
							width:924,
							height: 200,
							pager: jQuery('#paginadorInvolucradosCausa'), 
							rowNum:10,
							rowList:[10,20,30],
							sortname: '6', 
							rownumbers: false,
							gridview: false, 
							viewrecords: true, 
							sortorder: "desc",
							caption:"Administrar medidas cautelares",
							ondblClickRow: function(rowid) {
								mostrarVentanaMedidasCautelares(rowid);
							} 
					}).navGrid('#paginadorInvolucradosCausa',{edit:false,add:false,del:false});			
			cargaGridInvolucradosMedidas=false;
		}
		else{
			jQuery("#gridInvolucradosCausaPJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarMedidasCautelaresPorNumeroExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridInvolucradosCausaPJENC").trigger("reloadGrid");
		}
		$('#divGridInvolucradosCausaPJENC').show();
	}

	
	var mostrarMedidaCautelarPJENC=1;
	/*
	*Funcion que abre la ventana de medidas cautelares
	* 
	*/
	function mostrarVentanaMedidasCautelares(rowid){
		mostrarMedidaCautelarPJENC++;
		
		idVentana = "iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC;
		
		$.newWindow({id:"iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Consultar Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC,'<iframe src="<%=request.getContextPath()%>/ingresarMedidaCautelarPJENC.do?numeroExpediente='+numeroCausa+'&rowid='+rowid+'&numeroExpedienteId='+numeroExpedienteId+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA&idVentana='+idVentana+'" width="880" height="360" />');
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

	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Medidas Cautelares</a></li>
		</ul>
		<div id="tabsconsultaprincipal-1">
			<div id="divGridInvolucradosCausaPJENC">
				<table id="gridInvolucradosCausaPJENC"></table>
				<div id="paginadorInvolucradosCausa"></div>
			</div>
			<div id="divBotonNuevaMedidaCautelar">
				<input type="button" align="right" value="Agregar Medida Cautelar" id="crearMedida" onclick="agregarMedidaCautelar();" class="btn_Generico"/>
			</div>
		</div>
	</div>
	
	<!-- Comienza div Relacionar Medios de Prueba-->
	<div id="divAgregarMedidaCautelar" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
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
	<!-- Termina div Relacionar Medios de Prueba-->
	
</body>
</html>