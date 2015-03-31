<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoForma"%>
<%@page import="java.text.Normalizer.Form"%>
<%@page import="mx.gob.segob.nsjp.model.Forma"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Solicitar Permisos</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<!--Scrip para el idioma del calendario-->
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/currencyFormatMask.js"></script>  
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript">
	
	var idNumeroExpedienteOp;
	var numeroExpediente;
	var area;
	var idRenglon=1;
	var idWindowGenConvenio;
	var idConvenio;
	
	$(document).ready(function() {
		//guardamos las variables del paso de parametros
		numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
		idNumeroExpedienteOp='<%= request.getParameter("numeroExpedienteId")%>';
		area='<%= request.getParameter("area")%>';
		cargaGridEstadoExpediente();
	});
	//termina funcion onready

	function consultaPDF(id,esParcial){
		if( esParcial == "0"){
			document.frmDoc.documentoId.value = id;
			document.frmDoc.submit();
		}
		else{
			var activiDocu=0;
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/cargarActividadGuardadoParcial.do',
		    	async: false,
		    	data: {
		    		documento:id
		    	},
		    	dataType: 'xml',
		    	success: function(xml){
		    		activiDocu=$(xml).find('long').text();
		    	}
			});
				$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+"Generar Convenio", type:"iframe"});
    		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
		}
	}
	
	function cargaGridEstadoExpediente(){
	  jQuery("#gridConsultaPagos").jqGrid({ 
			url:'<%= request.getContextPath() %>/consultarConvenios.do?numeroExpediente='+numeroExpediente,			 				
			datatype: "xml",
			colNames:['#Convenio','Fecha Inicio','Fecha Termino','Cantidad Total a Pagar','Cantidad último Pago','Fecha último Cumplimiento','Convenio','Parcial'], 
			colModel:[ 	{name:'nombreProbResp',index:'nombreProbResp',width:150, align:'center'},
						{name:'delito',index:'delito',width:150, align:'center'},
						{name:'victima',index:'victima', width:150, align:'center'},
						{name:'CalidadActual',index:'calidadActual', width:250, align:'center'},
			           	{name:'NuevaCalidad',index:'NuevaCalidad', width:250, align:'center'},
			           	{name:'Observaciones',index:'Observaciones', width:265, align:'center'},
			        	{name:'convenio',index:'convenio', width:200, align:'center'},
			        	{name:'parcial',index:'parcial', width:200, align:'center', hidden: true}
					],
			autowidth: false,
			width:924,
			pager: jQuery('#pagerGridConsultaPagos'),
			rowNum:10,
			rowList:[10,20,30],
			sortname: 'nombreProResp',
			sortorder: "desc",
			viewrecords: true,
			gridComplete: function(){
				  var ids		= jQuery("#gridConsultaPagos").getDataIDs();
				  for(i=0;i < ids.length;i++){
					var cl = ids[i];
					var retd = jQuery("#gridConsultaPagos").jqGrid('getRowData',cl);
					cl = cl+","+retd.parcial;
					el = "<a href='#' title='Ver Documento' onclick=\"consultaPDF("+cl+");\">Ver documento</a>";
					jQuery("#gridConsultaPagos").jqGrid('setRowData',ids[i],{convenio:el});
				  }
		    },
			ondblClickRow: function(rowid) {
				idConvenio=rowid;
				detalleConvenio();
			}
		}).navGrid('#pagerGridConsultaPagos',{edit:false,add:false,del:false}); 
	}
	
	function ocultaboton(){
		 $("#btnGuardarConvenio").css("display", "none");
	}
	
	/*
	*Funcion para llenar los combobox de Probable Responsable y Victimas del expediente
	*/
	function consultarPrimerConvenio()
	{
		var param="numeroExpediente="+numeroExpediente;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarPrimerConvenio.do',
			data: param,
			dataType: 'xml',
			async: false
		});
	}

	function detalleConvenio(){
		$.newWindow({id:"iframewindowDetalleConvenio", statusBar: true, posx:150,posy:50,width:1000,height:420,title:"Detalle de Convenio", type:"iframe"});
	    $.updateWindowContent("iframewindowDetalleConvenio",'<iframe src="<%=request.getContextPath()%>/generarSeguimientoConvenioDetalle.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'" width="1000" height="420" />');
	}
	
	</script>
</head>
<body bgcolor="#CCCCCC">  
	<br>

	<div align="center">
		<table id="gridConsultaPagos" width="370px"></table>
		<div id="pagerGridConsultaPagos"></div>
	</div>

	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>

</body>

<script type="text/javascript">
	$( "#dialogDos-confirm" ).dialog();
	$( "#dialogDos-confirm" ).dialog( "destroy" );
</script>

</html>