<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
	 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Visor Mandamiento Judicial</title>

	
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
	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

	<script type="text/javascript">
	
	var opOculta                  = '<%= request.getParameter("op")%>';
	var numeroCausa               = '<%= request.getParameter("numeroCausa")%>';
	var ocultarAdd                = '<%= request.getParameter("ocultarAdd")%>';
	var flujoMedCautelar 		  = '<%=request.getParameter("flujoMedCautelar")%>';
	
	var cargaGridInvolucradosMandamientos = true;
	var mostrarMandamientoJudicialPJENC=1;
	var ventanaMandamientoJudicialPJENC=1;
	var banderaImputados = true;
	
	$(document).ready(function() {
		
		$('#divGridInvolucradosCausaPJENC').show();
		
		//Carga el grid  de Involucrados por Causa
		cargaGridInvolucradosCausaPJENC(numeroCausa);
		$("#tabsprincipalconsulta" ).tabs();
		
		if(ocultarAdd == "true"){
			jQuery("#divBotonNuevaMandamientoJudicial").hide();
		}
	});
	
	function cargaGridInvolucradosCausaPJENC(numeroCausa){

		if(cargaGridInvolucradosMandamientos == true){

			jQuery("#gridInvolucradosCausaPJENC").jqGrid(
					{ url:'<%= request.getContextPath() %>/consultarMandamientosJudicialesPorCausa.do?numeroExpediente='+numeroCausa+'', 						
						datatype: "xml",
						mtype: 'POST',						
						colNames:['Nombre','Mandamiento Judicial', 'Fecha de creaci&oacute;n','Estado'], 
						colModel:[ 	{name:'nombre',index:'nombre', width:200}, 
									{name:'mandamientoJudicial',index:'mandamientoJudicial', width:100}, 
									{name:'fechaCreacion',		index:'fechaCreacion', width:100},
									{name:'estadoMandamiento',  index:'estadoMandamiento', width:150 },
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
							caption:"Administrar mandamientos judiciales",
							ondblClickRow: function(rowid) {
								mostrarVentanaMandamientosJudiciales(rowid);
							} 
					}).navGrid('#paginadorInvolucradosCausa',{edit:false,add:false,del:false});			
			cargaGridInvolucradosMandamientos=false;
		}
		else{
			jQuery("#gridInvolucradosCausaPJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarMandamientosJudicialesPorCausa.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridInvolucradosCausaPJENC").trigger("reloadGrid");
		}		
	}
	
	function mostrarVentanaMandamientosJudiciales(rowid){
		mostrarMandamientoJudicialPJENC++;
		
		$.newWindow({id:"iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Consultar Mandamientos Judiciales", type:"iframe"});
    	$.updateWindowContent("iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC,'<iframe src="<%=request.getContextPath()%>/ingresarMandamientoJudicialPJENC.do?numeroExpedienteId='+numeroCausa+'&rowid='+rowid +'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA" width="880" height="360" />');
	}

	function nuevaVentanaMandamientosJudiciales(idInvolucrado){
		
		ventanaMandamientoJudicialPJENC++;
		idVentana = "iframewindowMandamientosJudiciales"+ventanaMandamientoJudicialPJENC;
		
		var rowid= idInvolucrado+',';
		$.newWindow({id:"iframewindowMandamientosJudiciales"+ventanaMandamientoJudicialPJENC, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Ingresar/Modificar Mandamientos Judiciales", type:"iframe"});
    	$.updateWindowContent("iframewindowMandamientosJudiciales"+ventanaMandamientoJudicialPJENC,'<iframe src="<%=request.getContextPath()%>/ingresarMandamientoJudicialPJENC.do?numeroExpedienteId='+numeroCausa+'&rowid='+rowid +'&flujoMedCautelar='+flujoMedCautelar+'&operacion=INGRESAR&opOculta='+opOculta+'&idVentana='+idVentana+'" width="880" height="360" />');
	}
	
	
	function cerrarVentanaMandamientosJudiciales(idVentana){
		$.closeWindow(idVentana);
	}
	
	function validaSeleccion(){
		
		var rowid = jQuery("#gridAgregarMandamientoJudicial").jqGrid('getGridParam','selrow');
		
		if (rowid) {
			$("#divAgregarMandamientoJudicial").dialog("close");
			nuevaVentanaMandamientosJudiciales(rowid);
		}else{
			alertDinamico("Por favor seleccione un renglon");
		} 
	}

	function agregarMandamientoJudicial(){
		
		cargaGridImputados();
		$("#divAgregarMandamientoJudicial").dialog("open");
		$("#divAgregarMandamientoJudicial").dialog({ autoOpen: true, modal: true, 
		title: 'Agregar mandamiento caultelar', 
		dialogClass: 'alert', position: [350,50],
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

	function cargaGridImputados(){

		if(banderaImputados == true){
			jQuery("#gridAgregarMandamientoJudicial").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',
				data:'',
				datatype: "xml",
				colNames:['Nombre del Imputado'], 
				colModel:[ 					
				           	{name:'nombreImputado',index:'nombreImputado',width:350, align:'center'}, 
						],
				pager: jQuery('#pagerGridAgregarMandamientoJudicial'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[25,50,100],
				sortname: 'nombreImputado',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados"
			}).navGrid('#gridAgregarMandamientoJudicial',{edit:false,add:false,del:false});
			banderaImputados = false;
		}
		else{
			jQuery("#gridAgregarMandamientoJudicial").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridAgregarMandamientoJudicial").trigger("reloadGrid");
		}
	}

	
	</script>
</head>
<body>

	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Mandamientos Judiciales</a></li>
		</ul>
		<div id="tabsconsultaprincipal-1">
			<div id="divGridInvolucradosCausaPJENC">
				<table id="gridInvolucradosCausaPJENC"></table>
				<div id="paginadorInvolucradosCausa"></div>
			</div>
			<div id="divBotonNuevaMandamientoJudicial">
				<input type="button" align="right" value="Agregar Mandamiento Judicial" id="crearMandamiento" onclick="agregarMandamientoJudicial();" class="btn_Generico"/>
			</div>
		</div>
	</div>
	
	<!-- Comienza div Relacionar Medios de Prueba-->
	<div id="divAgregarMandamientoJudicial" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridAgregarMandamientoJudicial" width="100%"></table>
				<div id="pagerGridAgregarMandamientoJudicial"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!-- Termina div Relacionar Medios de Prueba-->
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>
	
</body>
</html>