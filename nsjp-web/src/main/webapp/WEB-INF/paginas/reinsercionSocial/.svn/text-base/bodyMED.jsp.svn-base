
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>
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
var cargaPrimeraSolsXAtndr = true;

var contextoPagina = "${pageContext.request.contextPath}";

$(document).ready(
	function() {
			cargaGridSolsXAtndr(idTipoSolicitudCM,idAreaPorAtender, estatusAbierto);
			restablecerPantallas();	
	}
);

function restablecerPantallas(){
	ajustarGridAlCentro($("#gridSolsXAtndr"));
}

/*
*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
*/

var idTipoSolicitudCM = <%=TiposSolicitudes.CERTIFICADO_MEDICO.getValorId()%>; 
var idAreaPorAtender = "0";
var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;
var estatusConsulta = "";

function cargaGridSolsXAtndr(tipoSolicitud, idArea, estatus) {
	estatusConsulta = estatus;
	if(cargaPrimeraSolsXAtndr == true){
		//Grid de Solicitudes por atender
		jQuery("#gridSolsXAtndr").jqGrid({ 
			url:"<%= request.getContextPath()%>/consultaSolsXAtnder.do",
			mType: "POST",
			postData :{
				tipoSoliciutd : tipoSolicitud,
				idArea : idArea,
				estatus : estatus
			},
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
			pager: jQuery("#pagerGridSolsXAtndr"),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(id) {
				var datosSol = jQuery("#gridSolsXAtndr").jqGrid('getRowData',id);
				if (datosSol.id === undefined || datosSol.id === "undefined"){
					datosSol.id = id;
				}
				dblClickRowBandejaSolicitudes(datosSol, estatusConsulta);
			}
		}).navGrid("#pagerGridSolsXAtndr",{edit:false,add:false,del:false});
	cargaPrimeraSolsXAtndr = false;
	}else{
		jQuery("#gridSolsXAtndr").jqGrid('setGridParam', { 
				url:"<%= request.getContextPath()%>/consultaSolsXAtnder.do",
				mType: "POST",
				postData :{
					tipoSoliciutd : tipoSolicitud,
					idArea : idArea,
					estatus : estatus			
				},
				datatype: "xml"
			}
		);		
		$("#gridSolsXAtndr").trigger("reloadGrid");
	}	
}

function dblClickRowBandejaSolicitudes(datosSol, estatus) {
	customVentana(	"idVentanaDatosGenerales", 
					"Certidicado M&eacute;dico", 
					"/consultarDatosGeneralesRS.do", 
					"?cNumeroExpediente="+ getValorDelGrid(datosSol.expediente) + 
					"&solicitudId="+ getValorDelGrid(datosSol.id) +
					"&estatus="+ estatus +
					"&enviarA=<%=DatosGeneralesReinsercionAction.CERTIFICADO_MEDICO%>" );
}

	function getValorDelGrid(valor){
		if(valor != undefined && valor != "undefined"){
		 	if(valor.indexOf("div") > -1){
		 		var from = valor.indexOf(">", valor.indexOf("div"));
				var to = valor.indexOf("<", from);
				if(from != -1 && to != -1) {
					valor = valor	.substring(from+1, to);
				}
			}
		}
		return valor;
	}

</script>

	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					<div id="divGridExpedientePorEstatus">
						<table id="gridSolsXAtndr"></table>
						<div id="pagerGridSolsXAtndr"
							style="vertical-align: top;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	