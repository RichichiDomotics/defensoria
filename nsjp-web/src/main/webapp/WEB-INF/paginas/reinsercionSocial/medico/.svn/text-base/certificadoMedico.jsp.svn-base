<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
	//Comienza funcion on ready del documento			

	$(document).ready(
		function() {
			
			$("#cbxAccionesTab").change(
				function(e){
					seleccionaActuacion();
				}
			);
			
			<%
				EstatusSolicitud estatusSolicitud = EstatusSolicitud
							.getByValor(NumberUtils.toLong(request.getParameter("estatus"), 0L));
			
				if (estatusSolicitud == EstatusSolicitud.CERRADA) {
			%>
					$( "#tabsContenido").tabs({disabled: [2]});
					$( "#liActuacionesRS").hide();
			<%	
				}				
			%>			
			
			copiarValores();
			cargaActuaciones(1);
		}
	);		
	
	
	function copiarValores(){
			$("#edadBiologica").val($("#edad").val());

			$("input:radio[name='estaLesionado']")
				.filter("[value='" + 
					$("input[name='lesionado']:checked")
					.val() +
				"']").attr('checked', true);					
		
	}
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones(id) {
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+cNumeroExpediente+'&numeroExpedienteId='+numeroExpedienteId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$('#cbxAccionesTab').append('<option value="-1">--Seleccione una opci&oacute;n--</option>');
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTab').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});            
			}
		});
	}	
	 
	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje){
		//Cambia la actividad del expediente
		var expedienteId = $("#expedienteId").val();
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+expedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+cNumeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					customAlert("Actividad nueva registrada");
				}
			}
		});
	}	 
	 
	 
	function seleccionaActuacion(){
		var selected = $("#cbxAccionesTab option:selected");
		var confActividadId=selected.val();
		var actividad=0;
		var formaID=4;
		var titulo="op";
		var usaeditor="";
		var estatusId="";
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
				formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
				titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
				usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
				estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
			}
		});
		actuacion=actividad;

		if(usaeditor == "true"){			
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&actividadId=" + actividad + "&sentenciaId=" + sentenciaId;
			 
			registrarActividadExpediente(actividad,estatusId,0);
			customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros, function (){
				$('#cbxAccionesTab').empty();
				cargaActuaciones("");
				llenarGridDocumentos(listaDesplegada);	
			});		
		}
	}	 

	function modificarDatos(opcion){
		$("#edadBiologica").datepicker("destroy");
		$("#edadBiologica").attr("readonly","readonly");		
		$("input[name='estaLesionado']").each(
			function(i) {
            	$(this).attr('disabled', 'disabled');
        	}
        );
		
		$("#btn_modificar").hide();
		$("#btn_guardar").hide();
		$("#btn_cancelar").hide();
	
	
		switch(opcion){					
			case 'modificar':
				$("#btn_guardar").show();
				$("#btn_cancelar").show();
				camposParaModificar();
			break;
			
			case 'guardar':
				if(actualizarSentencia() == false){
					$("#btn_guardar").show();
					$("#btn_cancelar").show();
					camposParaModificar();				
				} else {
					$("#btn_modificar").show();
				}
			break;
			
			case 'cancelar':
			 	$("#btn_modificar").show();
			 	copiarValores();
			break;
		}
	}

	function camposParaModificar(){
		$("#edadBiologica").attr("readonly", false).val("");		
		$("input[name='estaLesionado']").each(
			function(i) {
	           	$(this).attr('disabled', false);
	       	}
	    );
	    $("#edadBiologica").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:'+ new Date().getFullYear(),
				changeMonth: true,
				changeYear: true,
				maxDate: "+0D",
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
		});	
	}

	function actualizarSentencia(){
		var estaLesionado =	parseInt($("input[name='estaLesionado']:checked").val());
		var fechaNacimiento = $("#edadBiologica").val();
		
		if($.trim( fechaNacimiento) ==  ""){
			customAlert("Por favor ingrese una fecha valida.", "Error");
			return false;
		}
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/manejarSentencia.do',
			data: {
				sentenciaId		: sentenciaId,
				estaLesionado	: Boolean(estaLesionado),
				fechaNacimiento : fechaNacimiento
			},
			dataType: 'xml',
			async: false,
			success: function(xml){
				var edad = $(xml).find('datosGralesRF').find('edad').text();
				var lesionado = $(xml).find('datosGralesRF').find('lesionado').text();
				$("#edadBiologica").val(edad);

				$("input:radio[name='estaLesionado']")
					.filter("[value='" +
					 lesionado
					+
					"']").attr('checked', true);
					
				$("#edad").val(edad);

				$("input:radio[name='lesionado']")
					.filter("[value='" +
					 lesionado
					+
					"']").attr('checked', true);
				
				customAlert("Los datos se han actualizado correctamente.")
										
			}
		});
		
		return true;
	}

	function actualizarEstatusSolicitud(estatus){
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/actualizaEstatusSolicitud.do',
			dataType: 'xml',
        	data: {
        		solicitudId : <%=request.getParameter("solicitudId") != null 
        							? request.getParameter("solicitudId") 
        							: "0"%>,
        		estatus : estatus
        	},
			success: function(xml){
				customAlert($(xml).find('body').text());
			}
		});

}


</script>

<fieldset>
	<legend>Datos del individuo: </legend>
	<table border="0">
		<tr>
			<td align="right">Edad biológica:</td>
			<td align="left">
				<input type="text" id="edadBiologica" readonly="readonly" size="10" />
			</td>
		</tr>					
		<tr>
			<td align="right">¿Presenta lesiones?</td>
			<td align="left">
				S&iacute; <input type="radio" name="estaLesionado" title="S&iacute;" value="1" disabled="disabled"/>
				No <input type="radio" name="estaLesionado" title="No;" value="0" disabled="disabled"/>
			</td>
		</tr>
			<tr>
				<td align="right">
					<input type="button" id="btn_modificar" value="Modificar" class="btn_Generico" onclick="modificarDatos('modificar');" />
					<input type="button" id="btn_guardar" value="Guardar" class="btn_Generico" onclick="modificarDatos('guardar');" style="display: none;" />
				</td>
				<td align="left">									
					<input type="button" id="btn_cancelar" value="Cancelar" class="btn_Generico" onclick="modificarDatos('cancelar');" style="display: none;" />
				</td>
			</tr>
	</table>
</fieldset>				

<fieldset>
	<legend>Certificados:</legend>
	<table border="0">
		<tr>
			<td>
			Tipo: <html:select name="DatosGeneralesReinsercionForm" property="actuacionSeleccionada"
									   styleId="cbxAccionesTab">
						 	<!--  option value="-1">--Seleccione una opci&oacute;n--</option -->
						 	<!-- html : optionsCollection name="DatosGeneralesReinsercionForm" property="actuaciones" label="nombre" value="programaId" /-->
						 </html:select>
			</td>
		</tr>
	</table>
</fieldset>				

<fieldset>
	<legend>Finalizar Solicitud:</legend>
	<table border="0">
		<tr>
			<td>
				<input type="button" name="finalizarSolicitud" id="finalizarSolicitud" value="Finalizar Solicitud" class="btn_Generico" onClick="actualizarEstatusSolicitud(<%=EstatusSolicitud.CERRADA.getValorId()%>);" />
			</td>
		</tr>
	</table>
</fieldset>				
