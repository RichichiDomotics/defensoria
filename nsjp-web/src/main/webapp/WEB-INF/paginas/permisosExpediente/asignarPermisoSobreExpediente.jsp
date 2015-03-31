<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.richtext.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.alerts.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.colorpicker.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css"/>

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ingresarMediosContacto.js"></script>

   	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>

	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/permisosExpediente/cmpAsignarPermisosSobreExpediente.js"></script>

	<!-- Enable JC Nuevo JS para compartir solicitudes en UAVD -->
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/permisosSolicitud/cmpAsignarPermisosSobreSolicitud.js"></script>
	<!-- Enable JC Funciones para UAVD -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/jsEnableIT/comunesUAVD.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript">
	<%
	UsuarioDTO usuario =((UsuarioDTO)request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"));
	FuncionarioDTO funcionario =usuario.getFuncionario();
	Long claveFuncionario = funcionario.getClaveFuncionario();
	%>
	var contextPath = "<%= request.getContextPath()%>";
	var areaUsuario = <%=usuario.getAreaActual().getAreaId()%>;
	var idsTiposSolicitudes = "";
	var estatusSol = "";
	var cadenaArea = "";
	var idArea = "";
	$(document).ready(function() {
		$("#fechaVencimiento").datepicker({
			changeMonth: true,
			changeYear: true
		});
		$("#modFechaVencimiento").datepicker({
			changeMonth: true,
			changeYear: true
		});

		if(areaUsuario == 12){//Coordinador UAVD
			estatusSol = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
			estatusSol += ",";
			estatusSol += <%=EstatusSolicitud.EN_PROCESO.getValorId()%>;
			idsTiposSolicitudes = "<%=request.getParameter("idsTiposSolicitudes")%>";

			initAsignarPermisosSobreSolicitudCoordinador(areaUsuario, <%=claveFuncionario%>, estatusSol, idsTiposSolicitudes);
		}else if(areaUsuario == 56){//TSocial
			idArea = "<%=Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()%>";
			estatusSol = "<%=EstatusSolicitud.EN_PROCESO.getValorId()%>";
			idsTiposSolicitudes = "<%=TiposSolicitudes.TRABAJO_SOCIAL.getValorId()%>";
			cadenaArea = "TRABSOC";

			initAsignarPermisosSobreSolicitudSubAreas(areaUsuario, <%=claveFuncionario%>, idArea, estatusSol, idsTiposSolicitudes, cadenaArea);
		}else if(areaUsuario == 57){//Psicologica
			idArea = "<%=Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()%>";
			estatusSol = "<%=EstatusSolicitud.EN_PROCESO.getValorId()%>";
			idsTiposSolicitudes = "<%=TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>";
			cadenaArea = "PSICO";

			initAsignarPermisosSobreSolicitudSubAreas(areaUsuario, <%=claveFuncionario%>, idArea, estatusSol, idsTiposSolicitudes, cadenaArea);
		}else if(areaUsuario == 58){//Juridica
			idArea = "<%=Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()%>";
			estatusSol = "<%=EstatusSolicitud.EN_PROCESO.getValorId()%>";
			idsTiposSolicitudes = "<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>";
			cadenaArea = "ATNJUR";

			initAsignarPermisosSobreSolicitudSubAreas(areaUsuario, <%=claveFuncionario%>, idArea, estatusSol, idsTiposSolicitudes, cadenaArea);
		}else{
			initAsignarPermisosSobreExpediente(<%=claveFuncionario%>);
		}
	});
	</script>

</head>
<body>
<table width="100%" height="100%">
<tr height="25%">
	<td colspan="3">
		<label>Expedientes Propios</label>
		<div id="divExpedientesPropios">
			<table id="gridExpedientesPropios"></table>
			<div id="pagerExpedientesPropios"></div>
		</div>
	</td>
</tr>
<tr height="25%">
	<td colspan="3">
		<label>Funcionarios</label>
		<div id="divSubordinados">
			<table id="gridSubordinados"></table>
			<div id="pagerSubordinados"></div>
		</div>

	</td>
</tr>
<tr id="filaFormaAsignarPermisos" height="10%">
<form name="formaAgregar" id="formaAgregar">
	<td width="30%">
		<input id="lectura" type="checkbox" disabled="disabled" checked="checked"><strong>Lectura</strong>
		<input id="escritura" type="checkbox" value="Escritura"><strong>Escritura</strong>
	</td>
	<td width="30%">
		<strong>Fecha Vencimiento:</strong><input type="text" id="fechaVencimiento"/>
	</td>
	<td width="40%">
		<input type="button" onclick="asignarPermisos()" value="Asignar permiso" class="btn_Generico">
	</td>
</form>
</tr>
<tr id="filaFormaModificarPermisos" height="10%">
<form name="formaModificar" id="formaModificar">
	<td width="30%">
		<input id="morLectura" type="checkbox" disabled="disabled" checked="checked"><strong>Lectura</strong>
		<input id="modEscritura" type="checkbox" value="Escritura"><strong>Escritura</strong>
	</td>
	<td width="30%">
		<strong>Fecha Vencimiento:</strong><input type="text" id="modFechaVencimiento"/>
	</td>
	<td width="30%">
		<input type="button" onclick="modificarPermisos()" value="Actualizar permiso" class="btn_Generico">
		<input type="button" onclick="eliminarPermisos()" value="Eliminar permiso" class="btn_Generico">
	</td>
</form>
</tr>
<tr height="25%">
	<td colspan="3">
		<label>Expedientes a los que tiene permisos el funcionario</label>
		<div id="divExpedientesFuncionario">
			<table id="gridExpedientesFuncionario"></table>
			<div id="pagerExpedientesFuncionario"></div>
		</div>
	</td>
</tr>
</table>
</body>
</html>