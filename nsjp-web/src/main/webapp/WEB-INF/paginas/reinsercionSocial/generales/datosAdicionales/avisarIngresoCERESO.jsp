<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
	//Comienza funcion on ready del documento			

	$(document).ready(
		function() {
	
			jQuery( "#datosAdicionalesModal" ).dialog({
	
				title: "Ingreso a CERESO ",
				buttons: {
					"Continuar": function() {
						ingresarAlCERESO();
					},
					"Cancelar": function() {
						jQuery( this ).dialog( "close" );					
					}
				}
			});	

			$("#updt_Motivo").val('<bean:message key="sentencia.motivo.ingreso"/>');

		}
	);		
			 
	function ingresarAlCERESO(){
		actualizarSentencia();
	}
			 
			 
	function actualizarSentencia(){
		var estaArraigado =	parseInt($("input[name='esArraigado']:checked").val());
		var motivo = $("#updt_Motivo").val();
		
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/manejarSentencia.do',
			data: {
				sentenciaId		: sentenciaId,
				estaArraigado	: Boolean(estaArraigado),
				motivo	 		: motivo
			},
			dataType: 'xml',
			async: false,
			success: function(xml){
				enviarDatosFinales();
				jQuery( "#datosAdicionalesModal" ).dialog("close");				
			}
		});
		
		return true;
	}

	function enviarDocumentosAlJuez() {		
		bloquearPantalla();
		jsonParam = 
			{
				"destinatarios"				: obtenIdEInstitucionDestinatario(),
				"documentos" 				: obtenDocAdjuntos(), 
				"sentenciaId"				: sentenciaId,
		    	"institucionSolicitante" 	: "1",
			    "solicitante" 				: "",
			    "numeroExpediente" 			: numeroUnicoExpediente,
			    "idSolicitud"				: $('#idSolicitud').val(),
			    "idTipoSolicitud"			: idTipoSolicitud
			}
		;

		$.ajax({
			type: 'POST',
			url:  '<%=request.getContextPath()%>/enviarSolicitudOtrasInstituciones.do',
			async: false,
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(json){
				try {
					if (json.exito != undefined && json.exito != "undefined"){
						customAlert(json.exito, "Exito", cerrarCustomVentana);
					} else if (json.error != undefined && json.error != "undefined"){
						customAlert(json.error);
					}
				}catch(e){
					console.error(e);
				}
			}
		});
	}

	function obtenIdEInstitucionDestinatario(){					
		var lista = jQuery("#gridUsuarios").getDataIDs();
		var resultado =[];
		for(var i in lista ) {
			var ret = jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
			var destinatario = {
				idFuncionario	: ret.id, 
				instId			: ret.instId,
				areaDestinoId	: ret.areaDestinoId,
				esOtraInst		: ret.esOtraInst
				};
			resultado.push(destinatario); 
		}
		return resultado;
	}

	function obtenDocAdjuntos(){
		var docsAdjuntos = [];
		docsAdjuntos.push(documentoParcialGuardado);
		
		if (docsAdjuntos.length <=0){
			return false;
		}
		var documentos = [];
		for(var i in docsAdjuntos){
			var doc = {
				idDocumento : docsAdjuntos[i]
			};
			documentos.push(doc);
		}
		return documentos;
	}


</script>

<fieldset>
	<table width="99%" border="0">
		<tr>
			<td colspan="2">
				Los siguientes datos se actualizaran:
			</td>
		</tr>		
		<tr>
			<td align="right">
				<strong>¿Se encuentra físicamente en el CERESO?</strong>
			</td>
			<td align="left">
				<input type="radio" id="esArraigado" name="esArraigado" checked="checked" readonly="readonly" disabled="disabled" value ="1" > S&iacute;
			</td>
		</tr>	
		<tr>
			<td align="right">
				<strong>Motivo:</strong>
			</td>
			<td align="left">
				<input type="text" id="updt_Motivo" name="updt_Motivo" checked="checked" readonly="readonly" disabled="disabled" value ="1" > 
			</td>
		</tr>
		<tr>
			<td colspan="2">
				¿Desea continuar?
			</td>
		</tr>	
	</table>
</fieldset>
