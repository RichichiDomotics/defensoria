<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	
	String idAgenciaUsuario = "-1";
	if (usuario.getFuncionario() != null 
			&& usuario.getFuncionario().getDiscriminante() != null
			&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null){
		idAgenciaUsuario = usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId().toString();
	}
	
	String idDistritoUsuario = "-1";
	if (usuario.getFuncionario() != null 
			&& usuario.getFuncionario().getDiscriminante() != null
			&& usuario.getFuncionario().getDiscriminante().getDistrito() != null
			&& usuario.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() != null){
		idDistritoUsuario = usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId().toString();
	}
	
	Long rolId = 0L;
	Boolean esCoordinadorAmpGeneral = false;

	if (usuario != null && usuario.getRolACtivo() != null
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null) {
		rolId = usuario.getRolACtivo().getRol().getRolId();
	}

	if (rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())) {
		esCoordinadorAmpGeneral = true;
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Generar Documento</title>
	
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0">
	</iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<script type="text/javascript">
	
	var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>;
	var contextoPagina = "${pageContext.request.contextPath}";
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):""%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):""%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):""%>';
	var actividadId = '<%=request.getParameter("actividadId")!=null?request.getParameter("actividadId"):""%>';
	var idDistritoUsuario = '<%= idDistritoUsuario %>';
	var idAgenciaUsuario = '<%= idAgenciaUsuario %>';
	
	var enviarAvisoDetencionSSP = <%=request.getParameter("enviarAvisoDetencionSSP")!=null?"true":"false"%>;
<%-- 	var ocultarGuardadoParcial = <%=request.getParameter("ocultarGuardadoParcial")!=null?"true":"false"%>; --%>
	var ocultarGuardadoParcial = <%=request.getParameter("ocultarGuardadoParcial")%>;
	var ocultarNumeroOficio = <%=request.getParameter("ocultarNumeroOficio")!=null?"true":"false"%>;
	var idInvolucrado= '<%=request.getParameter("idInvolucrado")!=null?request.getParameter("idInvolucrado"):"0"%>';

	/*
	*Parametros para el envio de a coordinador de cosignacion del sistema tradicional
	*/
	var enviarCoordConsignacion = <%=request.getParameter("enviarCoordConsignacion")!=null?"true":"false"%>;
	var cveFuncCoordConsignacion = '<%=request.getParameter("cveFuncionario")!=null?request.getParameter("cveFuncionario"):""%>';
	var cambiarEstatus = <%=request.getParameter("cambiarEstatus")!=null?"true":"false"%>;
	var actividad = '<%=request.getParameter("actividad")!=null?request.getParameter("actividad"):""%>';
	
	
	/*
	*Parametros necesarios para manejar el cambio de estatus en las Medidas catutelares
	*/
	var esDocumentoDeMedidaCautelar = <%=request.getParameter("esDocumentoDeMedidaCautelar")!=null?"true":"false"%>;
	var medidaCautelarId= '<%=request.getParameter("medidaCautelarId")!=null?request.getParameter("medidaCautelarId"):"0"%>';
	var nuevoEstatusMedidaCautelar= '<%=request.getParameter("nuevoEstatusMedidaCautelar")!=null?request.getParameter("nuevoEstatusMedidaCautelar"):"0"%>';
	var documentoParcialGuardado="";

	/**
	*Parametros necesarios para manejar el cambio de estatus en los Mandamientos Judiciales
	*/
	var esDocumentoDeMandamientoJudicial = <%=request.getParameter("esDocumentoDeMandamientoJudicial")!=null?"true":"false"%>;
	var mandamientoJudicialId = '<%=request.getParameter("mandamientoJudicialId")!=null?request.getParameter("mandamientoJudicialId"):"0"%>';
	var nuevoEstatusMandamientoJudicial= '<%=request.getParameter("nuevoEstatusMandamientoJudicial")!=null?request.getParameter("nuevoEstatusMandamientoJudicial"):"0"%>';

	
	
	/**
	* Parametro para identificar cuando se envia a Controversias (JAR) y mostrar en 
	* el combo de agencias, solo aquellas que tengan un coordinadorJAR
	*/
	var buscarCoordJAR = false;
	
		jQuery().ready(function () {
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#imprimirNarraTiva').click(crearPdf);
			$('#guardadoParcialNarrativa').click(guardadoParcial);
			$('#vistaPreliminar').click(elaborarVistaPreliminar);
			
			cargarDocumento();
			cargarDatosExpediente();
			cargaDatosEncabezado();
			$("#seccionCbxDistrito").hide();
			$("#etiquetaDistrito").hide();
			$("#etiquetaAgencia").hide();
			$("#seccionCbxAgencia").hide();
			$("#seccionSolicitarDefensor").hide();
			if(enviarAvisoDetencionSSP == true){
				$('#guardadoParcialNarrativa').hide();
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#seccionSolicitarDefensor").show();
				consultarDistritos('<%=Instituciones.DEF.getValorId()%>');
			}
			
			

			if(ocultarNumeroOficio == true){
				$('#iNumeroOficio').hide();
				$('#labelNumeroOficio').hide();
			}
			
			$("#btnRegistroDetencion").bind("click",registraDetencion);
			if(actividadId===null || actividadId===undefined || actividadId===""){
				var temp='<%=request.getParameter("documentoId")%>';
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/cargarActividadGuardadoParcial.do',
			    	async: false,
			    	data: {
			    		documento:temp
			    	},
			    	dataType: 'xml',
			    	success: function(xml){
			    		actividadId=$(xml).find('long').text();
			    	}
				});
				
			}
			if (actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>' ){
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#etiquetaAgencia").show();
				$("#seccionCbxAgencia").show();
				buscarCoordJAR = true;
				consultarDistritos();
				$("#cbxDistrito option[value='"+idDistritoUsuario+"']").attr("selected","selected");
				//$("#cbxDistrito").val(idDistritoUsuario);
				consultarAgenciasXDistrito(idDistritoUsuario);
				
				$("#cbxAgencia option[value='"+idAgenciaUsuario+"']").attr("selected","selected");	
			}
			
			consultarTipoTamanioPapel();
			if(esCoordinadorAmpGeneral===true){
				$("guardadoParcialNarrativa").hide();				
				$("imprimirNarraTiva").hide();
				$("vistaPreliminar").hide();
				$('#imprimirNarraTiva').unbind();
				$('#guardadoParcialNarrativa').unbind();
				$('#vistaPreliminar').unbind();
			}
			if(ocultarGuardadoParcial == false && 
					actividadId == '<%=Actividades.RECIBIR_CANALIZACION_JAR.getValorId() %>'){
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#etiquetaAgencia").show();
				$("#seccionCbxAgencia").show();
				consultarDistritos();
			}
			
			/*se habilita guardado parcial/*ByYolo*/
			if(ocultarGuardadoParcial == true || ocultarGuardadoParcial == "true"){
				$('#guardadoParcialNarrativa').hide();
			}else {
				$('#guardadoParcialNarrativa').show();
			}
			
		});

		function guardadoParcial(){ 
			var textoParcial	= $('.jquery_ckeditor').val();
			var recuperaTexto=escape($('.jquery_ckeditor').val());
			var nuevaActividad='<%=request.getParameter("nuevaActividad")%>';
			var numeroOficio=$('#iNumeroOficio').val();
			var documentoIdParcial='<%=request.getParameter("documentoId")%>';
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){documentoIdParcial=="";}
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
    			data:'parcial=true&formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+'&audienciaId='+idAudiencia+'&documentoId='+documentoIdParcial+'&texto='+recuperaTexto+'&iNumeroOficio='+numeroOficio+'&seleccionTamanioPapel='+seleccionTamPapel+'&nuevaActividad='+nuevaActividad+'',		    					    	
		    	dataType: 'xml',
		    	success: function(xml){
	    			var valorNumDocParcial = $(xml).find('long').first().text();
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('horaActual').text();
	    			}
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('fechaActual').text();
	    			}	    			
	    			if(valorNumDocParcial == ""){
	    				valorNumDocParcial = $(xml).find('response').find('body').find('string').text();
	    			}
	    			
	    		   var idsDocParcial = valorNumDocParcial.split(',');

	    		   if(idsDocParcial.length==2){
	    			   $('#iNumeroOficio').val(idsDocParcial[1]);
	    			   documentoParcialGuardado=idsDocParcial[0];
	    			}

					try{window.parent.documentos();}catch(e){}
					pintaChecksTipoAtencion();

					$('#iNumeroOficio').attr('disabled',true);
		    		alertDinamico("Guardado exitoso");
		    		
		    		var formaIDD = <%=request.getParameter("formaId")%>;
					if( formaIDD == '7'   ){
						parent.actualizaEstatusSolicitudDefensoria(textoParcial);
					}
					if( formaIDD == '33'   ){
						parent.actualizarEstatusSolicitudAsesoria(textoParcial);
					}


		    	},
		    	error: function(xml){		    		
		    	}
			});
		}

		function cargarDocumento(){
			
			parametros ="";
			
			<%for(Object llave:request.getParameterMap().keySet()){%>
				parametros +=  '<%=llave.toString()%>=<%=request.getParameter(llave.toString())%>&';
			<%}%>
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do',
		    	data: parametros,
		    	dataType: 'xml',
		    	success: function(xml){
		    		
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    		$("#iNumeroOficio").val( $(xml).find('<%=ConstantesGenerales.NUMERO_FOLIO_TAG_BUSQUEDA%>').text());
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
		
		/*
		*Funcion que recuepera el texto del editor, y lo envia como una nueva peticion 
		*para que se imprima con formato PDF
		*/
		function crearPdf(){
			var textoParcial	= $('.jquery_ckeditor').val();
			if (actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>'){
				if(validadDatosSolicitud() == true){
					var params = 'catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
					params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;

					//Cambiar catDiscriminanteId del area seleccionada al expediente
					$.ajax({
						type: 'POST',
					    url: '<%=request.getContextPath()%>/actualizarCatDiscriminanteDeExpediente.do',
					    data: params,
					    dataType: 'xml',
					    async: false,
					    success: function(xml){
					    	var resultado = $(xml).find('boolean').text();		
					    	if(resultado=="false"){
					    		customAlert('La agencia seleccionada es incorrecta', 'Error');
							}else{
								customAlert('La canalizaci&oacute;n se realiz&oacute; correctamente','&Eacute;xito', generaDocumento);
							}
						}
					});
				}
			}else{
				if(confirm("¿Est\u00E1 seguro que quiere guardarlo definitivamente?")) {
					var formaIDD = <%=request.getParameter("formaId")%>;
					if( formaIDD == '7' ){
						parent.actualizaEstatusSolicitudDefensoria(textoParcial);
						generaDocumento();
					}
					if( formaIDD == '33' ){
						parent.actualizarEstatusSolicitudAsesoria(textoParcial);
						generaDocumento();
					}
					else{
						generaDocumento();
					}
				}
			}	
		}
		
		function generaDocumento(){

			//mostramos los divs en el padre de la pestaña de Acciones.
			try{window.parent.muestraDIVSCanalizacion();}catch(e){}
			
			try{
				if (typeof window.parent.documentoGenerado == 'function' )
					setTimeout("window.parent.documentoGenerado()",5000);
				setTimeout("deshabilitarBotonesGuardado()",500);
			}catch(e){}
				
			var recuperaTexto=$('.jquery_ckeditor').val();
			
			document.frmDoc.parcial.value = "";
			document.frmDoc.texto.value = recuperaTexto;
			document.frmDoc.audienciaId.value = idAudiencia;
			document.frmDoc.iNumeroOficio.value = $("#iNumeroOficio").val();				
			document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel;
			document.frmDoc.action ="<%= request.getContextPath() %>/GenerarDocumento.do";
			if(document.frmDoc.documentoId.value==""){
				document.frmDoc.documentoId.value = documentoParcialGuardado;
				if(document.frmDoc.documentoId.value == null || document.frmDoc.documentoId.value == "null"){
					document.frmDoc.documentoId.value = "";
				}
			}
			document.frmDoc.submit();
			//para sistema tradicional
			actualizarEstatusExpedienteSistemaTradicional();
			
			//Para medidas cautelares
			if(esDocumentoDeMedidaCautelar == true && parseInt(nuevoEstatusMedidaCautelar) > 0){
				cambiarEstatusMedidaCautelarPJ(medidaCautelarId,nuevoEstatusMedidaCautelar);
			}
			
			//Para mandamiento judicial
			if(esDocumentoDeMandamientoJudicial == true && parseInt(mandamientoJudicialId) > 0){
				cambiarEstatusMandamientoJudicial(mandamientoJudicialId,nuevoEstatusMandamientoJudicial);
			}

			try{window.parent.documentos();}catch(e){}
			pintaChecksTipoAtencion();

			//Funcion para replicar a PG y SSP
			//try{window.parent.documentoGeneradoSincrono($(xml).find("long").first().text());}catch(e){}
			customAlert("El documento se ha generado exitosamente.", "", cerrarVentaDocumentoActualizarGrid);

		}

		/*
		*Funcion que controla el cambio de estatus tanto de los numeros de expediente
		*como del expediente, ademas de llamar a la generacion de numeros de expediente
		*/
		function actualizarEstatusExpedienteSistemaTradicional(){

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL AGENTE MP ST
			
			if(enviarCoordConsignacion == true && cveFuncCoordConsignacion != ""){
				//Funciones agregadas para sistema tradicional ingresarMenuIntermedioSistemaTradicional.jsp
								
				//Aplica para la actuacion la primera vez que aplica la actuacion cierre de investigacion
				window.parent.generarNumeroExpedienteConsignacion();
			}
			
			if (cambiarEstatus == true){
				//Funciones agregadas para sistema tradicional ingresarMenuIntermedioSistemaTradicional.jsp
				
				//Funcion que es invocada cuando el agenteMpSt requiere cambiar el estatus del expediente del consignador
				//Aplica cuando ejecuta nuevamente la actuacion de cierre de investigacion 
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>","<%=EstatusExpediente.EN_PROCESO.getValorId()%>","");
				//Cambiamos tambien el estatus del expediente del agenteMpSt
				window.parent.cambiaEstatusNumeroExpediente(<%=EstatusExpediente.CONSIGNADO.getValorId()%>);
				window.parent.habilitaDeshabilitaComboActuaciones();
			}

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL CONSIGNADOR
			
			if( actividad =='<%= Actividades.GENERAR_OFICIO_DE_CONSIGNACION.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.CONSIGNADO.getValorId()%>","<%=EstatusExpediente.CONSIGNADO.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad =='<%= Actividades.DETERMINAR_NEAP.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.NEAP.getValorId()%>","<%=EstatusExpediente.NEAP.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}			
			if( actividad =='<%= Actividades.DETERMINAR_LA_FALTA_DE_DILIGENCIAS.getValorId() %>' ||
			    actividad=='<%= Actividades.DETERMINAR_LA_FALTA_DE_RESPUESTAS_A_PARTICULARES.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.DEVUELTO.getValorId()%>","<%=EstatusExpediente.DEVUELTO.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad=='<%= Actividades.COMPARECENCIA_PARA_OTORGAR_PERDON_18_A.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.CONCLUIDO_POR_PERDON.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad=='<%= Actividades.ARCHIVO_DEFINITIVO_18_B.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.ARCHIVO_DEFINITIVO.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			} 
			if( actividad=='<%= Actividades.OFICIO_PARA_CANALIZAR_A_MEDIACION.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.MEDIACION_CONCILIACION.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			
		}
		
		function cerrarVentaDocumentoActualizarGrid(){
			if (typeof window.parent.documentos == 'function' ){
				window.parent.documentos();
			}
			if (typeof window.parent.cerrarVentanaDocumento == 'function' ){
				window.parent.cerrarVentanaDocumento();
			}
		}
		
		function pintaChecksTipoAtencion(){
			pintaTiposAtencion='<%=request.getParameter("pintaTiposAtencion")%>';
			if(pintaTiposAtencion != null && pintaTiposAtencion>0 ){
				try{window.parent.pintaCheckTipoAtencion(pintaTiposAtencion);}catch(e){}
			}
		}
		
		/**
		* Deshabilitar los botones de guardado parcial y guardado definitivo, esta función se invoca 
		* después del guardado parcial
		*/
		function deshabilitarBotonesGuardado(){
			
			$('#imprimirNarraTiva').unbind();
			$('#guardadoParcialNarrativa').unbind();
			
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

		/*
		 *Funcion que consulta Distritos
		 */
		function consultarDistritos(institucionDestino){
			var parametros = '';
			if (institucionDestino != undefined && institucionDestino != ''){
				parametros = 'institucionId='+institucionDestino;
			}
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDistritos.do',
			    data: parametros,
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
			
			var params = 'distritoId='+distritoId;
			if(buscarCoordJAR){
				params += '&buscarCoordJAR='+buscarCoordJAR;
			}
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDiscriminantesXDistrito.do?',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
				    	var contAgencias=0;
				    	$(xml).find('CatDiscriminanteDTO').find('catDiscriminanteDTO').each(function(){
							$('#cbxAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('clave').text()+"-"+ $(this).find('nombre').text() + '</option>');
							contAgencias++;
						});
						if(contAgencias == 0){
							alertDinamico("No existen agencias asignadas a esta regi&oacute;n");
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
		
		/*
		*Valida que existan coordinadores AMP en la agencia seleccionada
		*/
		function validadDatosSolicitud(){
			var idRolJAR = '<%=Roles.COORDINADORJAR.getValorId()%>';
			
			var totalDestinartario = 0;
			//buscar si existen Coordinadores en el área seleccionada
			var params = 'catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
			params += '&idRol=' + idRolJAR;
			
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
				customAlert('No existen coordinadores en la agencia seleccionada. Seleccione otra agencia', 'Agencia inv&aacute;lida');
				return false;
			}			
		}
		
		function registraDetencion(){
 		    idDistrito = $("#cbxDistrito").val();
			if(idDistrito != "-1"){
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/enviarAvisosDetencionIPH.do?idInvolucrado='+idInvolucrado+'&numeroExpediente='+numeroUnicoExpediente+'&idDistrito='+idDistrito+'',
			    	  data: '',				
			    	  dataType: 'xml',
			    	  success: function(xml){
			  			customAlert("Registro realizado correctamente.");
			    	  }
			    	});			
			}else{
				customAlert("Debe de seleccionar un distrito.");
			}

		}
		
		/*
		*Funcion que controla el cambio de estatus sobre las madidas cautelares
		*/
		function controlarCambioDeEstatusDeMedidaCautelar(){

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL sspEPRS
			if( actividad=='<%= Actividades.ARCHIVO_DEFINITIVO_18_B.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.ARCHIVO_DEFINITIVO.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			} 
			if( actividad=='<%= Actividades.OFICIO_PARA_CANALIZAR_A_MEDIACION.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.MEDIACION_CONCILIACION.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			
		}
		
		
		
		/*
		*Funcion que controla el estatus de una medida cautelar
		*/
		function cambiarEstatusMedidaCautelar(medidaCautelarId, nuevoEstatusMedidaCautelar){
			window.parent.cambiarEstatusMedidaCautelar(medidaCautelarId,nuevoEstatusMedidaCautelar);
			//Permite actualizar la variable del estatus inicial al actual
			estatus = nuevoEstatusMedidaCautelar; 
			window.parent.estatus = nuevoEstatusMedidaCautelar;
			//Actualizar el grid del estatus incial
			window.parent.cargaGrid(estatus);
			//Cambia vista de la lista de Actuaciones
			window.parent.mostrarActuaciones();
			//Ocultar la pestania de actuaciones dependiendo del estatus de la MC.
			window.parent.ocultarPestaniaActuaciones();
		}

		/**
		*Funcion exclusiva de PJ para cambiar el estatus de medida cautelar, la funcion de arriba
		*ya se encontraba aqui, y es para ssp, en especial para el usuario sspEPRS, esta funcion
		*se llama en la jsp ingresarMedidasCautelaresPJENC.jsp, llamada en el usuario encargadoCausa
		*/
		function cambiarEstatusMedidaCautelarPJ(medidaCautelarId, nuevoEstatusMedidaCautelar){
			window.parent.cambiarEstatusMedidaCautelar(medidaCautelarId, nuevoEstatusMedidaCautelar);

		}
		
		/*
		*Funcion que permite actualizar el estatus del mandamiento judicial
		*/
		function cambiarEstatusMandamientoJudicial(mandamientoJudicialId,nuevoEstatusMandamientoJudicial){
			//LLama a la funcion en el padre, para actualizar el estatus del mandamiento judicial
			window.parent.cambiarEstatusMandamientoJudicial(mandamientoJudicialId, nuevoEstatusMandamientoJudicial);


		}
		
	</script>
</head>
<body>
<!-- div para el alert dinamico -->
<div id="dialog-Alert" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTexto"></span>
            </td>
        </tr>
  </table>              
</div> 
	<table align="center" border="0" width="820px" height="50%">
		<tr>
			<td colspan="5">
				<ul class="toolbar">
					<div id="menu_head">
						<li id="guardadoParcialNarrativa" class="first"><span></span>Guardado Parcial</li>
						<li id="imprimirNarraTiva"><span></span>Guardado Definitivo</li>
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
			<td width="20%">Nombre Servidor P&uacute;blico:</td>
			<td width=""><input type="text" title="Nombre del Servidor Publico" size="40" id="iNombreServidorPublico" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Hora de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="idHoraDate" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">Cargo:</td>
			<td width=""><input type="text" title="Cargo" size="40" id="iPuesto" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Fecha de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="generarDocumentoCmpFechaIngreso" name="generarDocumentoCmpFechaIngreso" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td>&Aacute;rea Administrativa:</td>
			<td><input type="text" title="Area Administrativa" size="40" id="iAreaAdministrativa" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td>Estado:</td>
			<td><input type="text" title="Estado" size="30" id="iEstado" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>			
		</tr>
		<tr>
			<td width="20%"><span id="labelNumeroOficio">N&uacute;mero de Oficio:</span></td>
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio"  /></td>
		</tr>
		<tr id="trDistritos">
			<td id="etiquetaDistrito">Regi&oacute;n:</td>
			<td id="seccionCbxDistrito">
				<select name="cbxDistrito" id="cbxDistrito" onchange="actualizaComboAgencias()" style="width: 180px;">
			  		<option value="-1">- Seleccione -</option>
		    	</select>
	    	</td>
			<td id="etiquetaAgencia">Agencia:</td>
			<td id="seccionCbxAgencia">
				<select id="cbxAgencia">
					<option  value="-1" selected="selected">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr id="seccionSolicitarDefensor">
			<td align="center" colspan="4">
					<input type=button class="back_button" id="btnRegistroDetencion" value="Solicitar Defensor">
			</td>
		</tr>
	</table>
	
	
		
	<table align="center" width="1024px" >
		<tr>
			
			<td width="300px" valign="top" id="tdArbolExp">
				<h3><a href="#" id="idExpedientes">Elementos del Expediente</a></h3>
				<div style="height: 800px; 
						width: 300px;
						overflow: auto;
						border: 1px solid #666;
						padding: 0px;" id="marcoArbolExpediente">
						<ul id="accordionDatosExpediente" class="filetree">
						</ul>
			</div>
			</td>
			<td width="1000px" valign="top" align="center">
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto"  >
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
					<input type="hidden" name="iNumeroOficio" value="<%=request.getParameter("iNumeroOficio")!=null?request.getParameter("iNumeroOficio"):"" %>"/>
					<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
					<input type="hidden" name="audienciaId" value="<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"" %>"/>					                               
					<input type="hidden" name="nuevaActividad" value="<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>"/>
					<input type="hidden" name="esDocumentoDeMedidaCautelar" value="<%=request.getParameter("esDocumentoDeMedidaCautelar")%>"/>
				</form>
				
			</td>
		</tr>
	</table>
	
	
</body>
</html>