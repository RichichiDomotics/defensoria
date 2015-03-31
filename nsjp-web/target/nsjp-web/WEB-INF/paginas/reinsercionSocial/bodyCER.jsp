
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
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
var contextoPagina = "${pageContext.request.contextPath}";


var estatusNuevas = "";
var fechaini  = "";
var fechafin  = "";
var pp = "penaPrivativa";

estatusPorAtender = "<%=EstatusExpediente.POR_ATENDER.getValorId() %>";
estatusAtendidas  = "<%=EstatusExpediente.EN_PROCESO.getValorId()%>";
estatusInterno  = "<%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>";
estatusLibertad = "";

var pa ="penaAlternativa";

var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;

var estatusConsulta = "";

var gridExpedientesPorEstatus = { 
			url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
			datatype: "xml",
			page:1,
			postData: {}, 
			colNames:['Número De Caso','Número De Causa', 'Carpeta Ejecución', 'Nombre Sentenciado','Fecha De Creación', 'NumExpId'], 
			colModel:[ 	{name:'noCaso',index:'1', width:140}, 
						{name:'noCausa',index:'2', width:70}, 
						{name:'carpeta',index:'3', width:140}, 
						{name:'nombreSentenciado',index:'4', width:140},
						{name:'fechaCreacion',index:'5', width:70},
						{name:'numExpId',index:'5', width:70, hidden:true}
					],
	   		mtype: "POST",
			pager: "#pagerGridGeneral",
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autowidth: true,
			sortname: '',
			autowidth:true,
			shrinkToFit:true,
			viewrecords: true,
			sortorder: "asc",					
			ondblClickRow: function(id) {
				var datosExp = jQuery("#gridGeneral").jqGrid('getRowData',id);
				if (datosExp.id === undefined || datosExp.id === "undefined"){
					datosExp.id = id;
				}
				muestraPopupSeleccionarDestinatario(datosExp);
			},
			tipoGrid : "Expedientes"
		};


// Configuracion Grid Pra Solicitudes Generadas
var gridSolicitudesGeneradas = 	{ 
		url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do',
		datatype: "xml",
		page:1,
		postData :{},
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
   		mtype: "POST",
		pager: "#pagerGridGeneral",
		rowNum:10,
		rowList:[10,20,30,40,50,60,70,80,90,100],
		autowidth: true,
		sortname: '',
		autowidth:true,
		shrinkToFit:true,
		viewrecords: true,
		sortorder: "asc",				
		ondblClickRow: 
			function(rowid) {
				dblClickRowBandejaSolicitudesGen(rowid);
			},
		tipoGrid : "Solicitudes"
	};




$(document).ready(
	function() {
			jQuery("#gridGeneral").jqGrid(
				{
					pager: "#pagerGridGeneral"
				}
			).navGrid("#pagerGridGeneral",
				{
					edit:false,
					add:false,
					del:false
				}
			);

			//cargaGridExpedientesPorEstatus(<%=EstatusExpediente.EN_PROCESO.getValorId()%>);
			//cargaGridExpedientesPorEstatus(estatusPorAtender,fechaini,fechafin,pp);
			cargaGridExpedientesPorEstatus(estatusAtendidas,fechaini,fechafin);
			restablecerPantallas();	
	}
);

function restablecerPantallas() {
	ajustarGridAlCentro($("#gridGeneral"));
}


/*
*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
*/
function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
	$("#gridGeneral").jqGrid("GridUnload");
	
	gridExpedientesPorEstatus.postData = {
		estatusExpediente: estatus 
	};

	if(estatus == estatusAtendidas) {
		gridExpedientesPorEstatus.ondblClickRow = function(id) {
				var datosExp = jQuery("#gridGeneral").jqGrid('getRowData',id);
				if (datosExp.id === undefined || datosExp.id === "undefined"){
					datosExp.id = id;
				}
				muestraPopupSeleccionarDestinatario(datosExp);
			};
	} else if(estatus == estatusInterno) {
		gridExpedientesPorEstatus.ondblClickRow = function(id) {
			consultarDatosGenerales(id);
		};
	}
	
	$("#gridGeneral").jqGrid(
		//'setGridParam', 
		gridExpedientesPorEstatus
	).navGrid("#pagerGridGeneral",
				{
					edit:false,
					add:false,
					del:false
				}
			);
	//$("#gridGeneral").trigger("reloadGrid");
	restablecerPantallas();
}

	var idTipoSolicitudCM = <%=TiposSolicitudes.CERTIFICADO_MEDICO.getValorId()%>; 

	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea, estatus) {
	
		estatusConsulta = estatus;
	
		$("#gridGeneral").jqGrid("GridUnload");
		
		gridSolicitudesGeneradas.postData = {
				tipoSoliciutd 	: tipoSolicitud,
				idArea 			: idArea,
				estatus			: estatus
		};
		
		if(estatus == estatusAbierto) {
			gridSolicitudesGeneradas.ondblClickRow = function(rowid) {
				dblClickRowBandejaSolicitudesGen(rowid);
			};
		} else if(estatus == estatusCerrado) {
			gridSolicitudesGeneradas.ondblClickRow = function(id) {
					var datosSol = jQuery("#gridGeneral").jqGrid('getRowData',id);
					if (datosSol.id === undefined || datosSol.id === "undefined"){
						datosSol.id = id;
					}
					dblClickRowBandejaSolicitudes(datosSol, estatusConsulta);
				};
		}
		
		jQuery("#gridGeneral").jqGrid(
			//'setGridParam', 
			gridSolicitudesGeneradas ).navGrid("#pagerGridGeneral",
				{
					edit:false,
					add:false,
					del:false
				}
			);
		//$("#gridGeneral").trigger("reloadGrid");
		restablecerPantallas();
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

	function prepararEnvio(datosExp, idDestinatario, infoDestinatario ) {
	
		var cNumeroExpediente = getValorDelGrid(datosExp.carpeta);
		var idSentencia = getValorDelGrid(datosExp.id);
		var numeroExpedienteId = getValorDelGrid(datosExp.numExpId);
	
		enviaDatosSolicitud(
			$("#instituciones option:selected").val(), //institucionSolicitante
			"",//solicitante
			cNumeroExpediente,
			idDestinatario,//idsFuncionariosSolicitantes
			"0",//idSolicitud
			"<%=TiposSolicitudes.CERTIFICADO_MEDICO.getValorId()%>",//idTipoSolicitud
			idSentencia,
			$("#areas option:selected").val(),//Area Destino
			numeroExpedienteId
			);
		}

	function enviaDatosSolicitud(
					institucionSolicitante,
					solicitante,
					numeroExpediente,
					idsFuncionariosSolicitantes,
					idSolicitud,
					idTipoSolicitud,
					idSentencia,
					areaDestino,
					numeroExpedienteId
					){
   
		 bloquearPantalla();
	    $.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrarSolicitud.do',
			data: {
					institucionSolicitante:institucionSolicitante,
					solicitante:solicitante,
					numeroExpediente:numeroExpediente,
					idsFuncionariosSolicitantes:idsFuncionariosSolicitantes,
					idSolicitud:idSolicitud,
					idTipoSolicitud:idTipoSolicitud,
					areaDestino:areaDestino
					
			}, 
			async: false,
			dataType: 'xml',
			success: function(xml){										
				if(parseInt($(xml).find('SolicitudDTO').find('documentoId').text())>0){
					if($('#idSolicitud').val() == 0) {
						customAlert("La solicitud se envi\u00F3 correctamente", 
						"", 
						function(){
							actualizarEstatus(numeroExpedienteId, <%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>);
							consultarDatosGenerales(idSentencia);
						});
					} else {
						customAlert("La solicitud se actualiz\u00F3 correctamente", 
						"",
						function(){
							actualizarEstatus(numeroExpedienteId, <%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>);
							consultarDatosGenerales(idSentencia);
						});
					}
					$('#idSolicitud').val(parseInt($(xml).find('SolicitudDTO').find('documentoId').text()));
				} else {
					customAlert('Error al intentar guardar la solicitud, inténtelo mas tarde');
				}
			}
		});			   		
	}

		function actualizarEstatus(numeroExpedienteId, estatus){
			//Cambia la actividad del expediente
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registraStatusExpediente.do',
				data: {
					idNumeroExpediente : numeroExpedienteId,
					estatus:estatus
				},
				dataType: 'xml',
				async: false,
				success: function(xml){
					cargaGridExpedientesPorEstatus(estatusAtendidas,fechaini,fechafin,pp);
				}
			});
		}


function consultarDatosGenerales(id) {
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


function regresaGrid(){

}

function creaVentanaAcumulacionPuntos() {
	var tipoGrid = jQuery('#gridGeneral').jqGrid('getGridParam','tipoGrid');	
	if(tipoGrid != "Expedientes"){
		customAlert("Debe seleccionar una sentencia.", "Error al seleccionar una sentencia.");
		return false;
	}
	
	jQuery("#dialog-validarSentenciaIdAP").dialog({
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
	
	var sentenciaId = jQuery("#gridGeneral").jqGrid('getGridParam','selrow'); 
	if (sentenciaId) { 
		customVentana("iframewindowExpedietesPenaPrivativa", "Acumulaci&oacute;n de puntos", "/administracionAcumulacionPuntos.do", "?sentenciaId="+sentenciaId);
	} else { 
		jQuery("#dialog-validarSentenciaIdAP" ).dialog("open");
	}
}



		function muestraPopupSeleccionarDestinatario(datosExp) {
				$('#tblUsuarioExterno').hide();
				$('#tblUsuarioSistema').show(); 
				cargaComboInstitucion(false);
				$( "#dialog-confirmSeleccionarDestinatario" ).dialog({
					resizable: false,
					height: 'auto',
					width:'auto',
					modal: true,
					title:"Seleccionar Certificado M&eacute;dico: ",
					buttons: {
						"Agregar": function() {

							  var idPO = parseInt($("#funcionarios option:selected").val());
							  if(idPO != -1){
								  	jQuery("#confInstitucion").val(jQuery("#instituciones option:selected").val());
								  	var idDestinatario =  $("#funcionarios option:selected").val();
									var infoDestinatario =  $("#funcionarios option:selected").text();

									prepararEnvio(datosExp, idDestinatario, infoDestinatario );
									$( this ).dialog( "close" );
									$( "#dialog:ui-dialog" ).dialog( "destroy" );

							  }else{
								  customAlert("Se debe seleccionar un funcionario");
							  }
						},
						"Cancelar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
		}	
		
		//Permite llenar el combo de instituciones
		function cargaComboInstitucion(esExterno) {
	     $.ajax({
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
	    	  data: '',
	    	  async: false,
	    	  dataType: 'xml',
	    	  success: function(xml){
				//INICIA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
				$('#instituciones').empty();
				if(esExterno) {
					$('#instituciones').append( '<option value="1">-Seleccione-</option>');
				}
			    $(xml).find('instituciones').each(function(){
			    	<%
		    			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		    			if (usuario != null && usuario.getInstitucion() != null) {
			    	%>   
		    	  	if(esExterno) {
		    	  		if ( $(this).find('clave').text() != '<%=usuario.getInstitucion().getConfInstitucionId()%>') {
		    	  			$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    	  		}
		    	  	} else {
		    	 		if ( $(this).find('clave').text() == '<%=usuario.getInstitucion().getConfInstitucionId()%>') {
							$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						}
					}
					<% } %>
				});
				enSeleccionInstitucion();
				//TERMINA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
			
	    	  }
	    	});
	     }
		
		/*
		* Permite llenar el combo de Areas
		* Funcion para deshabilitar combo areas
		* Permite hacer cargar las Areas por Id de la Institucion
		*/
		function enSeleccionInstitucion() {
		  	var selected = $("#instituciones option:selected");
 			$('#areas').empty();
   			$('#areas').append( '<option value="1">-Seleccione-</option>');
   			$('#departamentos').empty();
   			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
   			$('#funcionarios').empty();
   			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');
 	
	         $.ajax({
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/consultarCatalogoAreasDependiente.do',
		    	  data: 'idInstitucion=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
		    	  async: false,
		    	  dataType: 'xml',
		    	  success: function(xml){
			    	 	$(xml).find('areas').each(
			    	 		function(){
			    	 			//SOlo Selecciona el Area Del Medico RS
			    	 			if($(this).find('clave').text() == <%=Areas.MED_DE_REINSERCION.ordinal()%>) {
									$('#areas').append('<option value="' + $(this).find('clave').text() + '" selected="selected">' + $(this).find('valor').text() + '</option>');
									cargaCompoDepartamentos();
									cargaCompoFuncionarios();									
								}
						   }
						);
		    	  }
		    	});
				
		}

		/*
		*Permite llenar el combo de departamentos: Funcion que dispara el Action para consultar departamentos
		*/	
		function cargaCompoDepartamentos() {
			var selected = $("#areas option:selected");
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoDepartamentosDependiente.do',
 	    	    data: 'idArea=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
				dataType: 'xml',
				success: function(xml){
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
					$(xml).find('departamentos').each(function(){
						$('#departamentos').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		cargaCompoDepartamentos()
		//permite llenar el combo de funcionarios
		function cargaCompoFuncionarios() {
			  var institucion = $("#instituciones option:selected").val();
			  var area = $("#areas option:selected").val();
			  var departamento = $("#departamentos option:selected").val();			
				$.ajax({
					async: false,
					type: 'POST',
					url:'<%=request.getContextPath()%>/consultarPersonalOperativoAction.do?institucion='+ institucion +'&area='+ area +'&departamento='+ departamento +'', 
					dataType: 'xml',
					success: function(xml){
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');					
						$(xml).find('row').each(function(){
							$('#funcionarios').append('<option value="' + $(this).attr('id') + '">' + $(this).find('nombre').text()+ ", " + $(this).find('puesto').text()+ ", " + $(this).find('email').text() + '</option>');
						});
					}
				});
		}  

var idWindowDetalleSolicitud= 0;
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		customVentana(	"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,
						"Detalle Solicitud", 
						"/consultarDetalleSolicitudBandejaGen.do"
						,"?idSolicitud="+rowID + "&tipoUsuario=1");    	 
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

</script>
	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					<div id="divGridExpedientePorEstatus">
						<table id="gridGeneral"></table>
						<div id="pagerGridGeneral"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="dialog-validarSentenciaIdAP" title="Error de validaci&oacute;n"  style ="display: none;">
		<p>
		Para acumular los actos de buena conducta de una sentencia,<br /> es necesario seleccionar la sentencia de la cual se acumular&aacute;n <br/> 
		los actos de buena conducta asociados. <br/><br/>
		Por favor seleccione un registro de la tabla de sentencias disponibles.
		</p>
	</div>
	<div id="dialog-confirmSeleccionarDestinatario" style ="display: none;">
	  <div id="seleccionarDestinatario">
	    <table width="342" cellspacing="0" cellpadding="0" id="tblUsuarioSistema">
	      <tr style = "display: none;">
	        <td>Instituci&oacute;n:</td>
	        <td><select name="instituciones" id="instituciones" style="width: 500px;">
	          <!--option value="-1" selected="selected">-Seleccione-</option-->
	          </select></td>
          </tr>
	      <tr>
	        <td width="108">&Aacute;rea:</td>
	        <td width="232"><select name="area" id="areas" style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td>Departamento:</td>
	        <td><select name="departamentos" id="departamentos" style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td>* Funcionario:</td>
	        <td><select name="funcionarios" id="funcionarios"  style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>	      
        </table>
        <input type="hidden" size="20px" id="idSolicitud" value="0"/>
      </div>
	</div>
		