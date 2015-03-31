<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.medida.TipoMedida"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMedida"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ingresar Medidas Cautelares</title>

		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen"/>
		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css"/>
		<link rel="stylesheet" type="text/css"  media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen"/>
		<link type="text/css"  rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>

		<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
		<script	src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>


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

		var idInvolucrado             = "";
		var origen                    = "";
		var institucionDestino        = "";
		var institucionDestinoSegunda = "";

		var rowid =			   '<%=request.getParameter("rowid")%>';
		var idVentana =		   '<%=request.getParameter("idVentana")%>';
		var operacion =		   '<%=request.getParameter("operacion")%>';
		var numexpedienteid =  '<%=request.getParameter("numeroExpedienteId")%>';
		var flujoMedCautelar = '<%=request.getParameter("flujoMedCautelar")%>';
		var numeroExpediente =  '<%=request.getParameter("numeroExpediente")%>';
		//Enable JC. Validacion para evitar que el String del numero de expediente llegue nulo
		if(numeroExpediente == 'null'){
			numeroExpediente = '<%=request.getParameter("numeroExpedienteId")%>';
		}
		var rolActivo = '<%=rolActivo%>';
		var contextoPagina = "${pageContext.request.contextPath}";
		var medidaCautelarId  = "";

		//Id estatus medida cautelar
		var estatusActualMedidaCautelar = "";
		var numeroGeneralCaso = "";


		jQuery().ready(function () {

			origen = '<%=Instituciones.PJ.getValorId()%>';
			institucionDestino = '<%=Instituciones.PGJ.getValorId()%>';

			$( "#tabsprincipalconsulta" ).tabs();
			$("#medidaCautelarCmpFechaInicio").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true
			});
			$("#medidaCautelarCmpFechaFin").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true
			});

			cargarMedidas();
			cargarEncargadoSeguimiento();
			obtenerCatalogoPeriodicidad();

			//Codigo para obtener los datos de la pantalla
			$("#guardarMedida").click(guardarMedidaCautelar);
			$('#iMedidaCautelarWorkSheet').show();

			$("#medidaCautelarCmpFechaInicio").attr("disabled","disabled");
			$("#medidaCautelarCmpFechaFin").attr("disabled","disabled");

			obtenerDatosMedidaCautelar(rowid);

			if(operacion=='CONSULTA'){
				$('#iMedidaCautelarWorkSheet').hide();
				consultarCatalogoEstatusMedidaCautelar();
				$("#cbxTipoConsultaDocumentos").change(consultarDocumentosDeMedidasCautelaresPorExpediente);

//Se agrego el Rol de AgenteMP para poder darle seguimiento a Medidas Cautelares
				if (rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>' || rolActivo == '<%=Roles.AGENTEMP.getValorId() %>'){
					//Muestra seccion para el cambio de estatus
					if(estatusActualMedidaCautelar == '<%=EstatusMedida.NO_ATENDIDA.getValorId()%>' || estatusActualMedidaCautelar == '<%=EstatusMedida.EN_PROCESO.getValorId()%>'
							|| estatusActualMedidaCautelar == '<%=EstatusMedida.CANCELADA.getValorId()%>' || estatusActualMedidaCautelar == '<%=EstatusMedida.SUSPENDIDA.getValorId()%>'){
						$("#seccionCambiarEstatusMedidaCautelar").show();
					}
					//Muestra seccion para adjuntar documentos
					if(estatusActualMedidaCautelar == '<%=EstatusMedida.ATENDIDA.getValorId()%>' || estatusActualMedidaCautelar == '<%=EstatusMedida.CANCELADA.getValorId()%>'
							|| estatusActualMedidaCautelar == '<%=EstatusMedida.CONCLUIDA.getValorId()%>'){
						$("#seccionAdjuntarDocumentoAMedidaCautelar").show();
					}
				}else{
				    $("#cbxTipoConsultaDocumentos").find("option[value='<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>']").remove();
				}
			}else{
				$("#cmpEstado").val("No atendida");
				$('#cbxTipoConsultaDocumentos').attr('disabled',true);
			}
			consultarDocumentosDeMedidasCautelaresPorExpediente();
			//Permite cargar el grid con las medidas cautelares relacionadas a un expediente
			consultaGeneralMedidaCautelar(4,'',numeroExpediente);

		});

		/*
		* Función que carga el catálogo de periodicidad en el combo-box correspondiente cbxPeriodicidad
		*
		*/
		function obtenerCatalogoPeriodicidad(){
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoPeriodicidad.do',
				data: '',
				dataType: 'xml',
				success: function(xml){
					$(xml).find('catPeriodicidad').each(function(){
						$('#cbxPeriodicidad').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		/*
		*Funcion que realiza la carga del combo de Medidas Cautelares
		*/
		function cargarMedidas() {
				$.ajax({
					async: false,
					type: 'POST',
					url: '<%= request.getContextPath()%>/ConsultarCatalogoMedidasCautelares.do',
					data: '',
					dataType: 'xml',
					success: function(xml){
						$(xml).find('medidasCautelares').each(function(){
							$('#cbxMedidaCautelar').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						});
					}
				});
		}

		/*
		*Funcion que realiza la carga del combo de Encargados de seguimiento
		*/
		function cargarEncargadoSeguimiento() {

			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoEncargadosSeguimiento.do',
				data: '',
				dataType: 'xml',
				success: function(xml){
					$(xml).find('encargadoSeguimiento').each(function(){
						$('#cbxEncargado').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}

		function obtenerDatosMedidaCautelar(rowid){
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/ConsultarMedidasCautelaresInvolucradoPJENC.do',
	    		data: 'rowid='+rowid,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){

	    			idInvolucrado=$(xml).find('medidaCautelar').find('involucrado').find('elementoId').text();
	    			medidaCautelarId = $(xml).find('medidaCautelar').find('documentoId').first().text();

					var nombre=$(xml).find('medidaCautelar').find('involucrado').find('nombresDemograficoDTO').find('nombre').first().text();
					document.getElementById('iMCNombre').value=nombre;
					var apellidoPaterno=$(xml).find('medidaCautelar').find('involucrado').find('nombresDemograficoDTO').find('apellidoPaterno').first().text();
					document.getElementById('iMCApellidoPaterno').value=apellidoPaterno;
					var apellidoMaterno=$(xml).find('medidaCautelar').find('involucrado').find('nombresDemograficoDTO').find('apellidoMaterno').first().text();
					document.getElementById('iMCApellidoMaterno').value=apellidoMaterno;

					var medidaCautelar = $(xml).find('medidaCautelar').find('valorTipoMedida').find('idCampo').text();
					tipoPeriodicidad = $(xml).find('medidaCautelar').find('valorPeriodicidad').find('idCampo').text();

					var estadoMedida =  $(xml).find('medidaCautelar').find('estatus').find('valor').last().text();
					$('#cmpEstado').val(estadoMedida);

					estatusActualMedidaCautelar = $(xml).find('medidaCautelar').find('estatus').find('idCampo').last().text();

					var descripcion = $(xml).find('medidaCautelar').find('descripcionMedida').text();
					$('#descripcionMedidaCautelar').val(descripcion);

					$('#cbxPeriodicidad').find("option[value='"+tipoPeriodicidad+"']").attr("selected","selected");
					if(medidaCautelar != ''  && medidaCautelar != null){
						$('#cbxMedidaCautelar').find("option[value='"+medidaCautelar+"']").attr("selected","selected");

						var guardaDef = $(xml).find('medidaCautelar').find('guardadoDefinitivo').text();
						deshabilitaCampos();
						if(guardaDef == 'true'){
							$('#iMedidaCautelarWorkSheet').hide();
						}
					}

					var seguimiento=$(xml).find('medidaCautelar').find('seguimiento').text();
					if(seguimiento != '' && seguimiento != null){
						$('#cmpSeguimiento').val(seguimiento);
					}

					var fechaInicio=$(xml).find('medidaCautelar').find('strFechaInicio').text();
					if(fechaInicio != '' && fechaInicio != null){
						$("#medidaCautelarCmpFechaInicio").val(fechaInicio);
					}

					var fechaFin=$(xml).find('medidaCautelar').find('strFechaFin').text();
					if(fechaFin != '' && fechaFin != null){
						$("#medidaCautelarCmpFechaFin").val(fechaFin);
					}

					numexpedienteid = $(xml).find('numeroExpedienteId').first().text();

					numeroGeneralCaso = $(xml).find('numeroCaso').last().text();
	    		}
	    	});
		}



		/*
		*Funcion para guardar la medida cautelar
		*/
		function guardarMedidaCautelar(){

			if(validaParametrosDeGuardado() == true){

				var params = '';

				params += recuperoDatosMedidaCautelar();

				$.ajax({
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/guardarMedidaCautelar.do',
			    	  data: params,
			    	  dataType: 'xml',
			    	  async: false,
			    	  success: function(xml){
			    		  //Si no tenía medida cautelar

			    		  window.parent.cargaGridInvolucradosCausaPJENC(numeroExpediente);

			    		  if(rowid.split(",")[1] == ""){
			    				customAlert("Se guardó con éxito la medida cautelar");

			    				medidaCautelarId=$(xml).find('medidaCautelarForm').find('medidaCautelarId').text();

								$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:5,posy:5,width:1140,height:400,title:"Generar Medida Cautelar", type:"iframe"});
						        $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId="+medidaCautelarId+"&numeroUnicoExpediente="+numeroExpediente+"&ocultarNumeroOficio=true' width='1140' height='400' />");
			    		  }
			    		  //Si ya tenia medida cautelar
			    		  else{
			    			  medidaCautelarId=rowid.split(",")[1];

			    			  $.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:5,posy:5,width:1140,height:400,title:"Generar Medida Cautelar", type:"iframe"});
						      $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId="+medidaCautelarId+"&numeroUnicoExpediente="+numeroExpediente+"&ocultarNumeroOficio=true' width='1140' height='400' />");
				    	  }

			    		  $("#guardarMedida").hide();
			    	 }
			    });
			}
		}


		function fecha1EsMayor(fecha1, fecha2){

			var xDia=fecha1.substring(0, 2);
			var xMes=fecha1.substring(3, 5);
			var xAnio=fecha1.substring(6);

			var yDia=fecha2.substring(0, 2);
			var yMes=fecha2.substring(3, 5);
			var yAnio=fecha2.substring(6);

			if (xAnio > yAnio){
				return(true);
			}else{
				if (xAnio < yAnio){
					return(false);
				}
				else{
					if(xMes > yMes){
						return(true);
					}
					else{
						if(xMes<yMes){
							return(false);
						}
						else{
							if(xDia<=yDia){
								return(false);
							}
							else{
								return(true);
							}
						}
					}
				}
			}
		}

		/*
		*Funcion que valida que se hayan ingresado todos los campos correctamente
		*/
		function validaParametrosDeGuardado(){

			var validacion = false
				if( $('#cbxMedidaCautelar option:selected').val() == "-1"){
					customAlert("Seleccione un tipo de medida cautelar");
				}
				else if($('#medidaCautelarCmpFechaInicio').val() == ""){
					customAlert("Seleccione una fecha de inicio");
				}
				//Falta validar las coherencia de las fechas
				else if($('#medidaCautelarCmpFechaFin').val() == ""){
					customAlert("Seleccione una fecha de fin");
				}
				else if(fecha1EsMayor($('#medidaCautelarCmpFechaInicio').val(),$('#medidaCautelarCmpFechaFin').val())==true){
					customAlert("La fecha inicial debe ser menor o igual a la fecha final");
				}
				else if( origen != '<%=Instituciones.PGJ.getValorId()%>' && $('#cbxPeriodicidad option:selected').val() == "-1"){
					customAlert("Seleccione una periodicidad");
				}
				else{
					validacion = true;
				}
		    return validacion;
		}

		function recuperoDatosMedidaCautelar(){
	        //Lugar de nacimiento esta pendiente ya que es un campo en BD pero en la pantalla vienen 3 campos, pais, estado y municipio
	        var parametros = '&medidaCautelar=' + $('#cbxMedidaCautelar option:selected').val();
	        parametros += '&seguimiento=' + $('#cmpSeguimiento').val();
	        parametros += '&fechaInicio=' + $('#medidaCautelarCmpFechaInicio').val();
	        parametros += '&fechaFin=' + $('#medidaCautelarCmpFechaFin').val();

	        var activo = $(':radio[name=rbtMCDesactivar]:checked').val();
	        parametros += '&activo=' + activo;
	        parametros += '&descripcionMedidaCautelar='+  $('#descripcionMedidaCautelar').val();
	        parametros += '&periodicidad=' + $('#cbxPeriodicidad option:selected').val();
	        parametros += '&rowid='+rowid;
	        parametros += '&numeroExpediente='+numeroExpediente;
	        parametros += '&idInvolucrado='+idInvolucrado;
	        parametros += '&medidaCautelarId='+rowid.split(",")[1];

			return parametros;
		}

		function habilitaCampos(){
	        $('#cbxMedidaCautelar').attr("disabled","");
	        $('#cmpSeguimiento').attr("disabled","");
	        $('#medidaCautelarCmpFechaInicio').attr("disabled","");
	        $('#medidaCautelarCmpFechaFin').attr("disabled","");
	        $('#cbxPeriodicidad').attr("disabled","");
	        $('#descripcionMedidaCautelar').attr("disabled","");
		}

		function deshabilitaCampos(){
	        $('#cbxMedidaCautelar').attr("disabled","disabled");
	        $('#cmpSeguimiento').attr("disabled","disabled");
	        $('#medidaCautelarCmpFechaInicio').attr("disabled","disabled");
	        $('#medidaCautelarCmpFechaFin').attr("disabled","disabled");
	        $('#cbxPeriodicidad').attr("disabled","disabled");
	        $('#descripcionMedidaCautelar').attr("disabled","disabled");
		}

	/**
	* Función que es invocada cuando se termina la creación del archivo digital de la medida
	*/
	function replicarMedidaCautelarAPGR(){
		var contextoPagina = "${pageContext.request.contextPath}";
		$.ajax({
			type: 'POST',
			url:contextoPagina +'/enviarMedidaCautelarInstitucion.do?medidaCautelarId='+medidaCautelarId+'&origen='+origen+'&institucionDestino='+institucionDestino+'',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				actualizarDatosCerrar(documentoGeneradoId);
			}
		});
	}


	function actualizarDatosCerrar(documentoGeneradoId){
		consultaDocumento(documentoGeneradoId);
		$.closeWindow("iframewindowGenerarDocumento");
	}

	/************ FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
	function ocultaMuestraTabVisor(claseTab,bandera)
	{
		if(parseInt(bandera)==0)//oculta
		{
			$("."+claseTab).hide();
		}
		else///muestra
		{
			$("."+claseTab).show();
		}
	}


	/* Inician funciones para realizar el cambio de estatus de una medida cautelar */

		 /*
		 * Funcion que consulta el catalogo del estatus de medida cautelar
		 */
			function consultarCatalogoEstatusMedidaCautelar(){

				$.ajax({
					async: true,
					type: 'POST',
					url: '<%=request.getContextPath()%>/consultarCatalogoEstatusMedidaCautelar.do',
					data: 'estatusMedidaCautelar=' + estatusActualMedidaCautelar,
					dataType: 'xml',
					success: function(xml){
						$(xml).find('estatusMedida').each(function(){
								$('#cbxEstatusMedidaCautelar').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						});
					}
				});
			}

		/**
		 * Funcion para invocar al editor de texto y cambiar el estatus del
		 * medidaCautelar, al presionar el boton "guardado definitivo"
		 */
		function generarDocumentoDeCambioEstatusDeMedidaCautelar(){

			if(validarNuevoEstatusMedidaCautelarJud() == true){

				var titulo="Cambio de estatus de medida Cautelar";

				var nuevoEstatusMedidaCautelar = $('#cbxEstatusMedidaCautelar option:selected').val();

				var parametros = obtenerParametrosActividadDocumento();

				parametros += '&medidaCautelarId=' + medidaCautelarId;
				parametros += '&nuevoEstatusMedidaCautelar=' + nuevoEstatusMedidaCautelar;
				parametros += '&esDocumentoDeMedidaCautelar=' + true;

				$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe"});
				$.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'&ocultarNumeroOficio=true" width="1140" height="400" />');
			}
		}


		/**
		 *Funcion para obtener los siguientes parametros de la tabla confActividadDocumento:
		 *
		 *actividadId
		 *formaId
		 *tipoDocumento
		 *nombreDocumento
		 *usaEditor
		 *estatusId (No importa, ya que no cambiara el estatus del expediente)
		 *nombreActividad
		 *
		 *Nota se requiere el parametro:numeroUnicoExpediente, que corresponde al numeroExpediente (String), como
		 *obligatorio, ya que se estara generando una actividad
		 */
		function obtenerParametrosActividadDocumento(){

			var parametros="";

			var actividad=0;
			var formaID=0;
			var titulo="";
			var usaeditor="";
			var estatusId="";
			var nombreActividad="";

			var confActividadDocumentoId = '<%=ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MEDIDA_CAUTELAR.getValorId()%>';

			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadDocumentoId+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
					formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
					titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
					usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
					estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
					nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				}
			});

			parametros = '&nuevaActividad=' + actividad;
			parametros += '&numeroUnicoExpediente='+numeroExpediente;
			parametros += '&formaId=' + formaID;
			parametros += '&titulo=' + titulo;
			parametros += '&nombreActividad=' + nombreActividad;

			return parametros;
		}


		/*
		 * Funcion para validar que se haya seleccionado un estatus para el medida cautelar
		 */
		function validarNuevoEstatusMedidaCautelarJud(){

			if($('#cbxEstatusMedidaCautelar option:selected').val() == "0"){
				customAlert("Selccione el nuevo estatus");
				return false;
			}else{
				return true
			}
		}


		/*
		 * Funcion que permite actualizar/cambiar el estatus de la medida cautelar
		 */
		function cambiarEstatusMedidaCautelar(medidaCautelarId, nuevoEstatusMedidaCautelar){
			$.ajax({
		   		type: 'POST',
		   		url: '<%=request.getContextPath()%>/cambiarEstatusMedidaCautelar.do?medidaId='+medidaCautelarId+'&estatus='+nuevoEstatusMedidaCautelar+'&numeroExpedienteId='+numexpedienteid+'&numeroExpediente='+numeroExpediente+'&numeroGeneralCaso='+numeroGeneralCaso+'',
		   		data: '',
		   		dataType: 'xml',
		   		async: false,
		   		success: function(jsonObject){
					customAlert("El estatus del medida cautelar fue actualizado de manera correcta");
					//Refresca el grid de medidas en el mismo visor pestania "En expediente"
					consultaGeneralMedidaCautelar(4,'',numeroExpediente);

					//Permite recargar el grid de la bandeja principal con el nuevo estatus
					recargarBandejaPrincipalMedidaCautelarsXEstatus(nuevoEstatusMedidaCautelar);
				}
		    });
		}



		/*
		*Funcion para cerrar la ventana de generarDocumentoSinCaso.jsp
		*llamada desde esta pantalla
		*/
		function cerrarVentanaDocumento(){

			var pantalla ="iframewindowGenerarDocumento";

			if(operacion=='INGRESAINGRESARR'){
				replicarMedidaCautelarAPGR();
			}

			$.closeWindow(pantalla);

			if (typeof window.parent.cerrarVentanaMedidaCautelar == 'function') {
				window.parent.cerrarVentanaMedidaCautelar(idVentana);
	        }
		}

		function recargarBandejaPrincipalMedidaCautelarsXEstatus(idEstatusMedidaCautelar){
	         if (typeof window.parent.consultaGeneralMedidaCautelar == 'function') {
				window.parent.consultaGeneralMedidaCautelar(1,idEstatusMedidaCautelar)
	          }
		}


	/* Finalizan funciones para realizar el cambio de estatus de una medida cautelar */


	/*************************************************FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/

		//Variable para controlar la carga del grid
		var primeraVezDocumentosRelacionados = true;

		/*
		*Funcion para consultar los doocumentos de la medida cautelar de acuerdo
		*al tipo de documento seleccionado por el usuario:
		*	- Creacion
		*	- Cambio de estatus
		*	- Adjuntos
		*/

		function consultarDocumentosDeMedidasCautelaresPorExpediente(){
			var tipoDocumento = $('#cbxTipoConsultaDocumentos option:selected').val();

			if(primeraVezDocumentosRelacionados == true){

				jQuery("#gridDocumentos").jqGrid({
					url:'<%=request.getContextPath()%>/consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo.do?medidaCautelarlId='+medidaCautelarId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'',
					datatype: "xml",
					colNames:['Fecha de Elaboracion','Nombre'],
					colModel:[
					           	{name:'fechaElab',index:'2', width:150, align:'center'},
					           	{name:'Nombre',index:'Nombre', width:150, align:'center'},
							],
					pager: jQuery('#pagerGridDocumentos'),
					rowNum:10,
					autoWidth:false,
					width:700,
					rowList:[10,20,30],
					sortname: '2',
					viewrecords: true,
					sortorder: "desc",
					ondblClickRow: function(rowid) {
						consultaDocumento(rowid);
					}
				});

				primeraVezDocumentosRelacionados=false;

			}else{
				jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo.do?medidaCautelarlId='+medidaCautelarId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
				$("#gridDocumentos").trigger("reloadGrid");
			}

			$("#gview_pagerGridDocumentos.ui-jqgrid-bdiv").css('height', '150px'); //Espacio en el punto.....
		}

		function consultaDocumento(idDocumento){
			document.frmDocumento.documentoId.value = idDocumento;
			document.frmDocumento.submit();
		}

		function abrirPDF(rowid){
			document.frmDoc.archivoDigitalId.value = rowid;
			document.frmDoc.submit();
		}
	/***********************************************TERMINA FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/



	//**********************************************FUNCIONES PARA ADJUNTAR DOCUMENTO A MEDIDA *************************************************/

	/*
	*Funcion para abirir la ventana para adjuntar un documento al medida cautelar
	*/
	function abreVentanaAdjuntarDocumentoAMedidaCautelar(){

		var extensionesPermitidas = ".pdf,.jpg";

		if(typeof(medidaCautelarId) != "undefined" && typeof(medidaCautelarId) != "null" && medidaCautelarId != ""){
			$.newWindow({id:"iframewindowAdjuntarDocumentoAMedidaCautelar", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento a medida cautelar", type:"iframe"});
			$.updateWindowContent("iframewindowAdjuntarDocumentoAMedidaCautelar",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAMedida.jsp?extensionesPermitidas=' + extensionesPermitidas + '&medidaCautelarId='+medidaCautelarId+'" width="450" height="200" />');
		}else{
			customAlert("Imposible adjuntar!");
		}
	}

	/*
	*Funcion que recarga el grid de adjuntar documento, si esa opcion esta seleccionada
	*esta funcion se invoca en adjuntarDocumentoAMedidaCautelar.jsp
	*/
	function recargaGridAdjuntarDocumento(){

		var tipoMandamientoSeleccionado = $('#cbxTipoConsultaDocumentos option:selected').val();

		if(tipoMandamientoSeleccionado == '<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>'){
			jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo.do?medidaCautelarlId='+medidaCautelarId+'&tipoDocumento='+tipoMandamientoSeleccionado+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
			$("#gridDocumentos").trigger("reloadGrid");
		}
	}




		</script>
	</head>
<body>
<div id="tabsprincipalconsulta">
	<ul>
		<li><a href="#tabsconsultaprincipal-1" id="labelPestaniaMedida">Medidas cautelares</a></li>
		<li class="tabsconsultaprincipal-2"><a href="#tabsconsultaprincipal-2">Documentos</a></li>
		<li class="tabsconsultaprincipal-3"><a href="#tabsconsultaprincipal-3">En el expediente</a></li>
	</ul>

	<div id="tabsconsultaprincipal-1">
		<input type="hidden" name="xml" id="xml" />

		<table border="0">
			<tr valign="top">
				<td width="50%">
					<table id="iMedidaCautelarViewHeader" width="100%" border="0">
						<tr>
							<td>
								<table border="0">
									<tr valign="top">
										<td>
											<table style="border: 0; background:#DDD;" width="100%" height="143" cellpadding="0"  cellspacing="0" class="celda2">
					                        	<tr>
							                    	<td width="60%" height="30" align="right">Nombre:</td>
						                        	<td width="29%">
						                        		<input type="text" value="" readonly="readonly" title="Escribir nombre" size="50" maxlength="40" id="iMCNombre" style="background:#DDD;border: 0;" readonly="readonly"/>
						                        	</td>
					                        	</tr>
					                        	<tr>
							                    	<td width="60%" height="28" align="right">Apellido paterno:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" value="" readonly="readonly" readonly="readonly" size="50" maxlength="40" id="iMCApellidoPaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
							                    	</td>
							                	</tr>
							                	<tr>
					                          		<td width="60%"  height="35" align="right">Apellido materno:</td>
						                    		<td height="35">
						                    			<input type="text" value="" readonly="readonly"  readonly="readonly" size="50" maxlength="40" id="iMCApellidoMaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
						                    		</td>
					                        	</tr>
					                    	</table>
										</td>
									</tr>
								</table>


								<!-- Comienza tabla cambio estatus -->
								<table border="0" >
									<tr id="seccionCambiarEstatusMedidaCautelar" style="display:none">
										<td>
											<strong>Estado:</strong>
											<select id="cbxEstatusMedidaCautelar" style="width:150px">
												<option value="0">-Seleccione-</option>
											</select>
										</td>
									    <td>
											<input type="button" value= "Guardar" id="btnGuardarEstatusMedidaCautelarJud" class="btn_Generico" onclick="generarDocumentoDeCambioEstatusDeMedidaCautelar();"/>
										</td>
									</tr>

									<tr id="seccionAdjuntarDocumentoAMedidaCautelar" style="display:none">
										<td>&nbsp;

										</td>
									    <td>
											<input type="button" value= "Adjuntar documento" id="btnAdjuntarDocAMedidaCautelarJud" class="btn_Generico" onclick="abreVentanaAdjuntarDocumentoAMedidaCautelar();"/>
										</td>
									</tr>

								</table>
								<!-- Termina tabla cambio estatus -->



							</td>
						</tr>
					</table>
				</td>
				<td width="50%">
					<table border="0">
						<tr>
							<td width="20%" align="right">Medida cautelar:</td>
							<td width="20%">
						        <select id="cbxMedidaCautelar">
									<option value="-1">-Seleccione-</option>
						    	</select>
							</td>
						</tr>
						<tr id="estadoTR">
							<td width="20%" align="right">Estado:</td>
							<td>
								<input type="text" id="cmpEstado" size="40" style="width: 200px;" disabled="disabled">
							</td>
						</tr>
						<tr>
							<td width="20%" align="right" id="cmpSeguimientolbl">
								<span style="display: none;">Encargado de seguimiento:</span>
							</td>
							<td width="20%">
								<input type="text" id="cmpSeguimiento" size="40" style="display: none;">
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">Fecha de inicio:</td>
							<td><input type="text" id="medidaCautelarCmpFechaInicio" style="width: 180px;" /></td>
						</tr>
						<tr>
							<td width="20%" align="right">Fecha de fin:</td>
							<td><input type="text" id="medidaCautelarCmpFechaFin" style="width: 180px;" /></td>
						</tr>
						<tr id="periodicidadTR">
							<td width="20%" align="right">Periodicidad:</td>
							<td width="20%">
								<select id="cbxPeriodicidad">
									<option value="-1">-Seleccione-</option>
						    	</select>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">Descripci&oacute;n:</td>
							<td><textarea style="width: 200px;" id="descripcionMedidaCautelar"></textarea></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="top">
				<td>
					<table id="iMedidaCautelarWorkSheet" width="100%"  border="0">
						<tr valign="top">
							<td align="center">
								<input type="button" value="Generar medida cautelar" id="guardarMedida" class="btn_Generico"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<form name="frmDocumento" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
	</div>

	<div id="tabsconsultaprincipal-2">
		<table width="700" border="0">
				<tr>
					<td>
						<strong>Tipos de documentos:</strong>
						<select id="cbxTipoConsultaDocumentos">
							<option value="<%=TipoDocumento.MEDIDA_CAUTELAR.getValorId()%>">Documentos de creacion</option>
							<option value="<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MEDIDA_CAUTELAR.getValorId()%>">Documentos por cambio de estatus</option>
							<option value="<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>">Documentos Adjuntos</option>
						</select>
					</td>
				</tr>

				<tr>
					<td>
						<div>
							<table id="gridDocumentos" ></table>
							<div id="pagerGridDocumentos" style="width: 300"></div>
						</div>
					</td>
				</tr>
			</table>
	</div>

	<div id="tabsconsultaprincipal-3">

		<table border="0">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<div>
						<table id="gridMedidasCautelares" ></table>
						<div id="pagerGridMedidasCautelares"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>

<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	<input type="hidden" name="archivoDigitalId" value=""/>
</form>

</body>
</html>