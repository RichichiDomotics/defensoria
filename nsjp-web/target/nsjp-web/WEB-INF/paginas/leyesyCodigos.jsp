<%@page
	import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoDefensoria"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.leycodigo.TipoNorma"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/layout_complex.css"
	media="screen" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/estilos.css"
	media="screen" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
		
<style>
.transpa {
	background-color: transparent;
	border: 0;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">


	var idExpediente =""; 
	
	var reloadGrid=false;
	
	var reloadGrid2=false;

	$(document).ready(function() {
		abreCodigosLeyes();
	
	});

	/**
	*Funcion que muestra los pdfs disponibles
	*/
	function abreCodigosLeyes(tipo) {
	
		 tipoLey = tipo;
	
		 jQuery("#gridLeyesCodigosPJENS").jqGrid('setGridParam', {postData: {tipoLey: tipoLey}});
		  $("#gridLeyesCodigosPJENS").trigger("reloadGrid"); 
	
		jQuery("#gridLeyesCodigosPJENS").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarLeyesCodigos.do', 
			datatype: "xml", 
			colNames:['Consulta de Leyes y C&oacute;digos'], 
			colModel:[ 	{name:'ley',index:'ley', width:500,align:"center"} ],
			pager: jQuery('#pager10'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			height: 220,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			onCellSelect: function(id,fila,contenido){
				//abrirPDF(id,fila,contenido);
				}
			}).navGrid('#pager10',{edit:false,add:false,del:false});
	}

	/*
	*Funcion apara abrir el pdf de la ley seleccionada en el grid
	*/
	function abrirPDF(idLey,fila,contenido){
		document.frmDoc.idLey.value = idLey;
		document.frmDoc.nombreArchivo.value = contenido;
		document.frmDoc.submit();
	}

</script>

<title>Insert title here</title>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td width="25%">
				<input class="btn_mediano" type="button" id="consPolitica" onClick="abreCodigosLeyes('<%=TipoNorma.CONSTITUCION.getValorId()%>')" value="Constituci&oacute;n Pol&iacute;tica" />
			</td >
			<td width="75%" rowspan="8" valign="top" >
				<table id="gridLeyesCodigosPJENS" width="100%"></table>
				<div id="pager10"></div>
			</td>
		</tr>
		<tr>
			<td width="25%">
				<input class="btn_mediano" type="button" id="leyesGenerales" onClick="abreCodigosLeyes('<%=TipoNorma.LEYES.getValorId()%>')" value="Leyes Generales"/>
			</td>
		</tr>
		<tr>
			<td height="21" >
				<input class="btn_mediano" type="button" id="tratados" onClick="abreCodigosLeyes('<%=TipoNorma.TRATADOS.getValorId()%>')" value="Tratados Internacionales"/>
			</td>
		</tr>
		<tr>
			<td >
				<input class="btn_mediano" type="button" id="codigos" onClick="abreCodigosLeyes('<%=TipoNorma.CODIGOS.getValorId()%>')" value="C&oacute;digos" />
			</td>
		</tr>
		<tr>
			<td>
				<input class="btn_mediano" type="button" id="acuerdos" onClick="abreCodigosLeyes('<%=TipoNorma.ACUERDOS.getValorId()%>')" value="Acuerdos" />
			</td>
		</tr>
		<tr>
			<td>
				<input class="btn_mediano" type="button" id="circulares" onClick="abreCodigosLeyes('<%=TipoNorma.CIRCULARES.getValorId()%>')" value="Circulares" />
			</td>
		</tr>
		<tr>
			<td>
				<input class="btn_mediano" type="button" id="manuales" onClick="abreCodigosLeyes('<%=TipoNorma.MANUALES.getValorId()%>')" value="Manuales" />
			</td>
		</tr>
		<tr>
			<td>
				<input class="btn_mediano" type="button" id="instructivos" onClick="abreCodigosLeyes('<%=TipoNorma.INSTRUCTIVOS.getValorId()%>')" value="Instructivos" />
			</td>
		</tr>
	</table>
	
	<form name="frmDoc" action="<%= request.getContextPath() %>/abrirPDF.do" method="post">
		<input type="hidden" name="idLey" value=""/>
		<input type="hidden" name="nombreArchivo" value=""/>
	</form>
</body>
</html>