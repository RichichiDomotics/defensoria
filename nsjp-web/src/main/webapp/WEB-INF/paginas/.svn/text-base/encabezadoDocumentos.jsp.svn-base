<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.pdf.PDFPropiedad" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
   	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
   
	String nombreFuncionario = usuario.getFuncionario().getNombreCompleto();
   
   	String areaFuncionario = "";
   	if (usuario.getAreaActual() != null && usuario.getAreaActual().getNombre() != null){
   		areaFuncionario = usuario.getAreaActual().getNombre();
   	}
   
   	String puestoFuncionario = "";
   	if (usuario.getFuncionario().getPuesto() != null && usuario.getFuncionario().getPuesto().getValor() != null){
   		puestoFuncionario = usuario.getFuncionario().getPuesto().getValor();
   	}
   	
   	String entidadDespliegue = "";
   	ConfiguracionDTO cfg = (ConfiguracionDTO) request.getSession().getAttribute("KEY_SESSION_CONFIGURACION_GLOBAL");
   	if (cfg != null && cfg.getNombreEntidadFederativaDespliegue() != null){
   		entidadDespliegue = cfg.getNombreEntidadFederativaDespliegue();
   	}
%>
<script type="text/javascript">
	var nombreServidorPublico = '<%=nombreFuncionario%>';
	var cargoServidorPublico = '<%=puestoFuncionario%>';	
	var areaAdministrativa = '<%=areaFuncionario%>';
	var entidadDespliegue = '<%=entidadDespliegue%>';
	
	var accionVistaPreliminarPdf="<%= request.getContextPath() %>/generarVistaPreliminar.do";
	
	/** 
	* Parametro que define el tamano de la hoja del editor visual
	* Utilizada en el jsp de encabezadoDocumento
	*/
	var seleccionTamPapel = 0;

	function cargaDatosEncabezado(){
		$('#iNombreServidorPublico').val(nombreServidorPublico);
		$('#iPuesto').val(cargoServidorPublico);
		$('#iAreaAdministrativa').val(areaAdministrativa);
		$('#iEstado').val(entidadDespliegue);
	}

	/**
	* Funcion que se encarga de recuperar el texto parcial, del editor, para generar el pdf.
	* requiere de la forma y el texto modificado en el editor. 
	**/
	function elaborarVistaPreliminar(){
		//var recuperaTexto=escape($('.jquery_ckeditor').val());
		var recuperaTexto=$('.jquery_ckeditor').val();
		var formaIdentificador  = <%=request.getParameter("formaId")%>
		
		//Surge el problema de "Request URI is too large.""
		//document.frmVistaPreliminar.action = accionVistaPreliminarPdf+"?texto="+recuperaTexto+"&formaId="+formaIdentificador;
		document.frmDoc.action = accionVistaPreliminarPdf;
		document.frmDoc.texto.value = recuperaTexto;
		document.frmDoc.formaId.value = formaIdentificador;
		document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel; 
		document.frmDoc.submit();
	}
		
	function consultarTipoTamanioPapel(){
		<%
			PDFPropiedad valores[] = PDFPropiedad.values();
			for(int i=0;i<valores.length;i++){
		%>	
				var valor = <%=valores[i].getValorId()%>;
				var descripcion = '<%=valores[i].getNombre()%>';
				$("#cbxTamanioPapel").append('<option value="' + valor+ '">' + descripcion+'</option>');
		<%
			}
		%>
		seleccionTamPapel = '<%=PDFPropiedad.Oficio.getValorId()%>';
		$("#cbxTamanioPapel option[value='"+seleccionTamPapel+"']").attr("selected","selected");
	}
	
	function recuperarTamanioPapel(){
		seleccionTamPapel = parseInt($("#cbxTamanioPapel option:selected").val());
	}
</script>