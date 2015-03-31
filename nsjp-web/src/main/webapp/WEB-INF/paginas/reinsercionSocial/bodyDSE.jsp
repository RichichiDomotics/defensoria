
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

estatusPorAtender  = "<%=EstatusExpediente.POR_ATENDER.getValorId()%>";
estatusAtendidas  = "<%=EstatusExpediente.EN_PROCESO.getValorId()%>";
estatusLibertad = "";
estatusInterno = "<%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>";

var pa ="penaAlternativa";



$(document).ready(
	function() {
			cargaGridExpedientesPorEstatus();
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
			url:'', 
			datatype: "local", 
			postData: {
				estatusExpediente: <%=EstatusExpediente.POR_ATENDER.getValorId()%>
			},
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

function creaVentanaDetenidos(expediente) {
	$.newWindow({id:"iframewindowDetenidos", statusBar: true, posx:0,posy:00,width:$(document).width(),height:$(document).height(),title:"Reinserci&oacute;n Social", type:"iframe"});
	$.maximizeWindow("iframewindowDetenidos");
    $.updateWindowContent("iframewindowDetenidos",'<iframe src="<%= request.getContextPath() %>/reinsercionSocial.do" width="100%" height="100%" />');
}

function creaVentanaExpedietesPenaPrivativa(expediente) {
	$.newWindow({id:"iframewindowExpedietesPenaPrivativa", statusBar: true, posx:0,posy:00,width:$(document).width(),height:$(document).height(),title:"Reinserci&oacute;n Social", type:"iframe"});
	$.maximizeWindow("iframewindowExpedietesPenaPrivativa");
    $.updateWindowContent("iframewindowExpedietesPenaPrivativa",'<iframe src="<%= request.getContextPath() %>/consultarRS.do" width="100%" height="100%" />');
	//$.updateWindowContent("iframewindowExpedietesPenaPrivativa",'<iframe src="<%= request.getContextPath() %>/consultarDatosGeneralesRS.do" width="100%" height="100%" />');
}

function creaVentanaAsignacionPrograma() {
	
	jQuery("#dialog-validarSentenciaId").dialog({
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
	var ret = jQuery("#gridExpedientesPorEstatus").jqGrid('getRowData',sentenciaId); 
	if (sentenciaId) { 
	
	var cNumeroExpediente =ret.carpeta;
	
	 if(cNumeroExpediente.indexOf("div") > -1){
	 	var from = cNumeroExpediente.indexOf(">", cNumeroExpediente.indexOf("div"));
		var to = cNumeroExpediente.indexOf("<", from);
		cNumeroExpediente = cNumeroExpediente.substring(from+1, to);
	}
		customVentana(	"iframewindowExpedietesPenaPrivativa", 
						"Asignar Programa de Reinserci&oacute;n Social", 
						"/asignarProgramaRS.do", 
						"?sentenciaId="+sentenciaId+"&cNumeroExpediente=" + cNumeroExpediente );
	} else { 
		jQuery("#dialog-validarSentenciaId" ).dialog("open");
	}
}

function regresaGrid(){

}

function asignarExpedientes(){
	customVentana("cambiarResponsableExpediente", "Asignar Responsable A Expediente", "/cambiarResponsableExpediente.do");
}


/************************************************Enable IT GB************************************************/
 function registrarSentencia(){
	alertDinamicoDosBotones("¿Cuentas con Número de Caso?",1);
}

function alertDinamicoDosBotones(textoAlert, id){
    $("#divAlertTexto").html(textoAlert);
    $( "#dialog-Alert" ).dialog({
		autoOpen: true,
		resizable: false,
		//height:290,
		//width:300,
		modal: true,
		buttons: {				
			"Si": function() {	
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");					
				registrarSentenciaView('1');
			},
			"No": function() {
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");
				registrarSentenciaView('2');
			}				
		}
	});
}

function registrarSentenciaView(valor){

	customVentana("idRegistrarSentenciaview", "Registrar Sentencia", "/registrarSentencia.do", "?val="+valor);

}
 
 /*********************************************************/


</script>

	<div id="dialog-validarSentenciaId" title="Error de validaci&oacute;n">
		<p>
		Para poder asignar un programa a un sentenciado, es necesario seleccionar la sentencia a la cual se asociar&aacute; el programa. <br/>
		Por favor seleccione un registro de la tabla de sentencias disponibles.
		</p>
	</div>
	
	
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
	