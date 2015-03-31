
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>

<script type="text/javascript">

//Variable para controlar la carga del grid de expedientes
var cargaPrimeraExpPorEstatus = true;
var contextoPagina = "${pageContext.request.contextPath}";


var estatusNuevas = "";
var fechaini  = "";
var fechafin  = "";
var pp = "penaPrivativa";

estatusPorAtender  = "<%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>";
estatusAtendidas  = "";
estatusLibertad = "";

var pa ="penaAlternativa";



$(document).ready(
	function() {
			cargaGridExpedientesPorEstatus(estatusPorAtender);
			restablecerPantallas();	
	}
);

function restablecerPantallas(){
	ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
}

/*
*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
*/

function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
	if(cargaPrimeraExpPorEstatus == true){
		jQuery("#gridExpedientesPorEstatus").jqGrid({ 
			url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
			postData: {
				estatusExpediente: estatus
			},
			datatype: "xml", 
    		mtype: "POST",
			colNames:['Número De Caso','Número De Causa', 'Carpeta Ejecución', 'Nombre Sentenciado','Fecha De Creación'], 
			colModel:[ 	{name:'noCaso',index:'1', width:140}, 
						{name:'noCausa',index:'2', width:70}, 
						{name:'carpeta',index:'3', width:140}, 
						{name:'nombreSentenciado',index:'4', width:140},
						{name:'fechaCreacion',index:'5', width:70},
					],
			pager: jQuery('#pagerGridExpedientesPorEstatus'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			height:360,
			sortname: 'Sentencia_id',
			caption:"Sentencias",
			autowidth:true,
			shrinkToFit:true,
			viewrecords: true,
			onSelectRow: function(id){

				},
			ondblClickRow: function(id) {
				consultarDatosGenerales(id);
			},
			sortorder: "asc"
		}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});
	
		cargaPrimeraExpPorEstatus = false;
	}else{
		jQuery("#gridExpedientesPorEstatus").jqGrid(
			'setGridParam', {
				url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
				postData: {
					estatusExpediente: estatus
				},
				datatype: "xml" 
			}
		);
		$("#gridExpedientesPorEstatus").trigger("reloadGrid");
	}

}

function consultarDatosGenerales(id){
	customVentana("idVentanaDatosGenerales", "Datos Generales", "/consultarDatosGeneralesRS.do", "?sentenciaId="+id);
}


function regresaGrid(){

}

function creaVentanaInventarioPertenencias() {
	
	jQuery("#dialog-validarSentenciaIdIP").dialog({
		autoOpen: false,
		height: 'auto',
		width:'auto',
		modal: true,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
				$( "#dialog:ui-dialog" ).dialog( "destroy" );
			}
		},
		resizable: false
	});
	
	var sentenciaId = jQuery("#gridExpedientesPorEstatus").jqGrid('getGridParam','selrow'); 
	if (sentenciaId) { 
		customVentana("iframewindowExpedietesPenaPrivativa", "Inventario de pertenencias", "/inventarioPertenencias.do", "?sentenciaId="+sentenciaId);
	} else { 
		jQuery("#dialog-validarSentenciaIdIP" ).dialog("open");
	}
}


</script>
	
	<div id="mainContent">
			<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					<div id="divGridExpedientePorEstatus">
						<table id="gridExpedientesPorEstatus"></table>
						<div id="pagerGridExpedientesPorEstatus"
							style="vertical-align: top;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="dialog-validarSentenciaIdIP" title="Error de validaci&oacute;n">
		<p>
		Para consultar el inventario de pertenencias de una sentencia, es necesario seleccionar <br/> 
		la sentencia de la cual se consultar&aacute; el inventario de pertenencias asociado. <br/>
		Por favor seleccione un registro de la tabla de sentencias.
		</p>
	</div>