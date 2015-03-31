<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Canalización a la Unidad de Fiscales Investigadores Especializados</title>
	
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0"></iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>	
	<script type="text/javascript">
	
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"Sin Resolutivo"%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"Sin Audiencia"%>';
	var idWindowIngresarDenunciante = 1;
	var idWindowIngresarVictima = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarTestigo = 1;
	var idWindowIngresarTraductor = 1;
	var idWindowIngresarQuienDetuvo = 1;
	var numeroExpedienteId=<%= request.getParameter("numeroExpedienteId")%>;
	var idDistritoUsuario = 0;
	var idAgenciaUsuario = 0;
	var unidadInvEsp = 0;
	
		jQuery().ready(function () {
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#imprimirNarraTiva').click(crearPdf);
			//$('#guardadoParcialNarrativa').click(guardadoParcial);
			$('#vistaPreliminar').click(elaborarVistaPreliminar);
			cargarDocumento();
			cargarDatosExpediente();

			$('#seleccionarDestinatario').hide();
			
			//Se selecciona de forma automatica el distrito y agencia del usuario
			consultarDistritos();
			$("#cbxDistrito option[value='"+idDistritoUsuario+"']").attr("selected","selected");
			//$("#cbxDistrito").val(idDistritoUsuario);
			consultarAgenciasXDistrito(idDistritoUsuario);
			$("#cbxAgencia option[value='"+idAgenciaUsuario+"']").attr("selected","selected");
			
			consultarTipoTamanioPapel();
		});
		
		
		function guardadoParcial(){
			var recuperaTexto=$('.jquery_ckeditor').val();
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
		    	data: 'parcial=true&formaId=<%=request.getParameter("formaId") %>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    	'&documentoId=<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>&texto='+
		    			escape(recuperaTexto)+'&seleccionTamanioPapel='+seleccionTamPapel,
		    	dataType: 'xml'
			});
		}

		
		function cargarDocumento(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do?idAudiencia='+idAudiencia+'&idResolutivo='+idResolutivo+'',
		    	data: 'formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    	'&documentoId=<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    	}
			});
		}
		
		function cargarDatosExpediente(){
			if(!esconderArbol){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/CargarArbolExpedienteEnDocumento.do',
			    	data: 'numeroUnicoExpediente='+numeroUnicoExpediente,
			    	dataType: 'xml',
			    	success: function(xml){
			    		//Obtener Parametros de Configuracion
			    		var probableResponsableArbolTituloSeccion = '<bean:message key="probableResponsableArbolTituloSeccion"/>';
			    		var probableResponsableArbolTitulo = '<bean:message key="probableResponsableArbolTitulo"/>';
			    		
			    		contenido = escribirDatosGenerales(xml);			
			    		contenido += escribirInvolucrados(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo);
			    		contenido += escribirUnidadesEspecializadas(xml);
			    		contenido += escribirListaObjetos(xml);
			    		contenido += escribirDelitos(xml);
			    		//contenido += escribirHechos(xml);
			    		
			    		//Se configura el id del distrito y la agencia del usuario firmado en sesion
			    		idDistritoUsuario = $(xml).find('expedienteResumenDTO').find('usuario').find("funcionario").find("discriminante").find("distrito").find("catDistritoId").first().text();
			    		idAgenciaUsuario  = $(xml).find('expedienteResumenDTO').find('usuario').find("funcionario").find("discriminante").find("catDiscriminanteId").first().text();
			    		//Muestra información del delito principal y de la UIE
			    		if($(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("unidadIEspecializada") != null){
			    			$.ajax({
						    	type: 'POST',
						    	url: '<%=request.getContextPath()%>/cargarUIE.do',
						    	data: '',
						    	dataType: 'xml',
						    	success: function(xml){
						    		$(xml).find('CatUIEspecializadaDTO').each(function (){
					    				$('#unidadInvEsp').append('<option value="' + $(this).find('catUIEId').text() + '">' + $(this).find('nombreUIE').text() + '</option>');
					    			});						    		
						    	}
			    			});
			    		}
				    	if($(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("nombre") != null)
				    		$("#nombreDelito").val($(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("nombre").first().text());
				    	if($(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("unidadIEspecializada").find("catUIEId") != null)
				    		unidadInvEsp = $(xml).find('expedienteResumenDTO').find('delitoPrincipal').find("catDelitoDTO").find("unidadIEspecializada").find("catUIEId").first().text();
				    		
			    		$("#accordionDatosExpediente").append(contenido);
			    		$("#accordionDatosExpediente").treeview();
			    	}
				});
				
			}else{
				$("#marcoArbolExpediente").css('display','none');
				$("#idExpedientes").css('display','none');
				$("#tdArbolExp").css('width','1px');
						
			}
			
			
		}

		function refresca(){
			$("#accordionDatosExpediente").empty();
			cargarDatosExpediente();
		}
		
		/*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos() {
	
			$('#idDelitosCaso').empty();
		    $.ajax({
				type: 'POST',
		   	  	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		   	  	data: '',
		   	 	dataType: 'xml',
		   	  	success: function(xml){
					$('#idDelitosCaso').empty();
					$('#idDelitosCaso').append('<option value="-1">- Seleccione -</option>');
					$(xml).find('catCatalogo').each(function(){
						$('#idDelitosCaso').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				   	});
		   	  	}
		   	});
		}
	
		
	   /*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos2() {
	
			$('#idFormasParticipacion').empty();
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
					$('#idFormasParticipacionv').empty();
			    	$('#idFormasParticipacion').append('<option value="-1">- Seleccione -</option>');
		    		$(xml).find('catCatalogo').each(function(){
						$('#idFormasParticipacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
	
	   
	   /*
		*Funcion que carga la fecha actual del sistema y la agrega en la pantalla al campo fecha
		*/
		function cargaFechaCaptura(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('#generarDocumentoCmpFechaIngreso').val($(xml).find('fechaActual').text());
		    	}
			});
		}
	
	   
	   /*
		*Funcion que carga la hora actual del sistema y la agrega en la pantalla al campo hora
		*/
		function cargaHoraCaptura(){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/ConsultarHoraCaptura.do',
	    	    data: '',
	    	    dataType: 'xml',
	    	    success: function(xml){
	    			$('#idHoraDate').val($(xml).find('horaActual').text());
	    		}
			});
	    }

		function imprime(){
			
			var texto=$('.jquery_ckeditor').val();

			$.ajax({
				async: false,
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/GenerarDocumento.do',
	    	    data: 'texto='+$('.jquery_ckeditor').val(),
	    	    dataType: 'xml'
			});
		}
		
		
		/*
		*Funcion que recuepera el texto del editor, y lo envia como una nueva peticion 
		*para que se imprima con formato PDF
		*/
		function crearPdf(){

			if(validadDatosSolicitud() == true){
				
				var params = 'catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
				params += '&numeroExpedienteId=' + numeroExpedienteId;
				params += '&catUIE=' +  $("#unidadInvEsp option:selected").val();

				var resultado = "";
				//Cambiar catDiscriminanteId del area seleccionada al expediente
				$.ajax({
					type: 'POST',
				    url: '<%=request.getContextPath()%>/actualizarCatDiscriminanteDeExpediente.do',
				    data: params,
				    dataType: 'xml',
				    async: false,
				    success: function(xml){
				    	resultado = $(xml).find('boolean').text();		
				    	if(resultado=="false"){
				    		alertSincrono("No existe el expediente ó la agencia es incorrecta");
						}else{
							alertSincrono("Solicitud enviada a la unidad de fiscales investigadores.");
							//alertSincrono("La canalización se realizó correctamente");
							try{
								
								//generar documento
								setTimeout("crearPdfSincrono()",100);
								setTimeout("window.parent.cerrarVentanaUFI()",8000);
								}catch(e){}
						}
					}
				});
			}
		}
		/*
		*Funcion que genera el pdf
		*/
		function crearPdfSincrono(){
			var recuperaTexto=$('.jquery_ckeditor').val();
			document.frmDoc.parcial.value = "";
			document.frmDoc.texto.value = recuperaTexto;
			document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel;
			document.frmDoc.action ="<%= request.getContextPath() %>/GenerarDocumento.do";
			document.frmDoc.submit();
		}

		/*
		*Funcion que despliega una ventana modal para indicar el mensaje
		*que recibe como parametro
		*/
		function alertSincrono(mensaje){

			$('#spanAlert').html(mensaje);
			
			$("#alertSincro").dialog("open");
			$("#alertSincro").dialog({
				autoOpen: true, 
				modal: true, 
				title: '', 
				dialogClass: 'alert',
				position: [250,100],
			  	width: 300,
			  	height: 180,
			  	maxWidth: 300,
			  	maxHeigth:180,
				buttons: {"Aceptar":function() {
							$(this).dialog("close");
					     }
					}
			});
		}

		/*
		*Valida que existan coordinadores AMP en la agencia seleccionada
		*/
		function validadDatosSolicitud(){
			var idRolAMP = '<%=Roles.COORDINADORAMP.getValorId()%>';
			
			var totalDestinartario = 0;
			//buscar si existen Coordinadores en el área seleccionada
			var params = 'catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
			params += '&idUIE=' + unidadInvEsp;
			params += '&idRol=' + idRolAMP;
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarFuncionariosCoordinadoresXDicriminante.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
			    	$(xml).find('funcionarioDTO').each(function(){
			    		totalDestinartario++;
					});					
				}
			});
			
			if(parseInt(totalDestinartario) > 0 ){
				return true;
			}else{
				alertSincrono('No existen coordinadores en la agencia seleccionada. Seleccione otra agencia');
				return false;
			}			
		}

		
		/*
		 *Funcion que consulta Distritos
		 */
		function consultarDistritos(){		
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDistritos.do',
			    data: '',
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
				    	$(xml).find('listaCatalogo').find('catDistritoDTO').each(function(){
							$('#cbxDistrito').append('<option value="' + $(this).find('catDistritoId').text() + '">' + $(this).find('claveDistrito').text()+"-"+ $(this).find('nombreDist').text() + '</option>');
						});					
				}
			});
		}
		
		/*
		 *Funcion que consulta Agencias dependiento del Distrito seleccionado
		 */
		function consultarAgenciasXDistrito(distritoId){
			$('#cbxAgencia').empty();
			$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDiscriminantesXDistrito.do?distritoId='+distritoId+'',
			    data: '',
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
				    	var contAgencias=0;
				    	$(xml).find('CatDiscriminanteDTO').find('catDiscriminanteDTO').each(function(){
							$('#cbxAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('clave').text()+"-"+ $(this).find('nombre').text() + '</option>');
							contAgencias++;
						});
						if(contAgencias == 0){
							alertDinamico("No existen agencias asignadas a este distrito");
						}
				}
			});
		}
		
				
		/*
		*Actualiza el combo de agencias dependiendo de la seleccion del distrito
		*/
		function actualizaComboAgencias(){
			distritoId = parseInt($("#cbxDistrito option:selected").val());
			if(distritoId > 0)
				consultarAgenciasXDistrito(distritoId);
			else{
				$('#cbxAgencia').empty();
				$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
			}
		}
		
		function actualizaUnidadInvEsp(){
			unidadInvEsp = parseInt($("#unidadInvEsp option:selected").val());
		}
		
		</script>
</head>



<body>
	
	<table align="center" border="0" width="820px" height="50%">
		<tr><!-- MENU -->
			<td colspan="4">
				<ul class="toolbar">
					<div id="menu_head">
<!--						<li id="guardadoParcialNarrativa" class="first"><span></span>Guardado Parcial</li>-->
						<li id="imprimirNarraTiva"><span></span>Enviar</li>
						<li id="vistaPreliminar"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tamaño de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
					</div>
				</ul>
			</td>
	  	</tr>
		<tr>
			<td width="">Fecha de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="generarDocumentoCmpFechaIngreso" name="generarDocumentoCmpFechaIngreso" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Hora de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="idHoraDate" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<!--agregados para la canalización-->
		<tr>
			<td width="20%">Nombre del Delito Principal:</td>
			<td width=""><input type="text" title="Nombre" size="30" id="nombreDelito" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td>Unidad de Investigación Especializada:</td>
			<td>
			<!-- input type="text" title="Unidad Investigacion Especializada" size="30" id="unidadInvEsp" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/-->
			<select id="unidadInvEsp" onchange="actualizaUnidadInvEsp()" style=" max-width:40ex;"><option  value="-1" selected="selected">-Seleccione-</option></select>
			</td>
		</tr>
		<tr>
			<td width="20%">Regi&oacute;n:</td>
			<td width=""><select id="cbxDistrito" onchange="actualizaComboAgencias()"><option  value="-1" selected="selected">-Seleccione-</option></select> </td>
			<td>Agencia:</td>
			<td><select id="cbxAgencia"><option  value="-1" selected="selected">-Seleccione-</option></select></td>
		</tr>
	</table>
		
	<!-- ETIQUETAS PARA LA SECCION DE LOS ELEMENTOS DEL EXPEDIENTE -->	
	<table align="center" width="1024px" border="0">
		<tr>			
			<td width="300px" valign="top" id="tdArbolExp">
				<h3><a href="#" id="idExpedientes">Elementos del Expediente</a></h3>
				
				<div style="height: 800px; 
						width: 300px;
						overflow: auto;
						border: 1px solid #666;
						padding: 0px;" id="marcoArbolExpediente">
						<ul id="accordionDatosExpediente" class="filetree"></ul>
				</div>
			</td>
			<td width="800px" valign="top" align="center">
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto">
					<br>		
					<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				</div>
				<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
					<input type="hidden" name="texto" value=""/>
					<input type="hidden" name="parcial" value=""/>
					<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
					<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
					<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
					<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
					<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
					<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
					<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
				</form>					
			</td>
		</tr>
	</table>
	
	<!-- div para el alert sincrono -->
	<div id="alertSincro" style="display: none">
		<table>
			<tr>
				<td>
					<label id=spanAlert></label>
				</td>
			</tr>
		</table>
	</div>   
</body>

</html>