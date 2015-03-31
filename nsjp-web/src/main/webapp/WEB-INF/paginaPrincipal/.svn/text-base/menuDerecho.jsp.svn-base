<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<script type="text/javascript">
	$(document).ready(function() {			
		$("#accordionmenuderprincipal").accordion(
			{
				fillSpace: true,
			   	changestart: function(event, ui) {
      					ui.newContent.css('overflow' , 'hidden');
  					},
  					change: function(event, ui) { 
    					ui.newContent.css('overflow' , 'auto');
  					}
			}
		);
		
		$('#test').weatherfeed(['MXDF0132']);
	});
</script>
<div class="ui-layout-east">
	<div class="header">Bienvenido</div>
	<div class="content">
		<div id="accordionmenuderprincipal">
			<h4><a href="javascript:void(0);">Datos Personales</a></h4>
			<div>			
					<center>
						<jsp:include page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
					</center>
			</div>
			<!-- <h4><a href="javascript:void(0);">Calendario</a></h4>
			<div>
					<center>
						<a href="javascript:void(0);"><img src="<%=request.getContextPath()%>/resources/images/img_calendario.png" width="201" height="318"></a>
					</center>
			</div>-->
			<h6><a href="javascript:void(0);">Agenda</a></h6>
			<div>
				<center>
					<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
				</center>
				<br/>
				<!-- <ul id="seccion2tree" class="filetree">
					<li class="closed"><span class="folder" id="agenda1">Agenda de Funcionario</span>
						<ul>
							<li class="closed"><span class="file">Mitzi</span>
							<li class="closed"><span class="file">Rodrigo</span>
						</ul>
					</li>
					<li class="closed"><span class="folder" id="agenda2">Administrar Agendas</span></li>
				</ul>-->
				<!-- <table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" style="cursor:pointer">
						<tr>
						   <td width="100%" id="agenda-funcionario"><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Agenda de Funcionario</td>
						</tr>
						<tr>
						   <td width="100%" id="funcionario1"><img src="<%=request.getContextPath()%>/resources/css/flecha.png" width="16" height="16" />Mitzi</td>
						</tr>
						<tr>
						   <td width="100%" id="funcionario2"><img src="<%=request.getContextPath()%>/resources/css/flecha.png" width="16" height="16" />Rodrigo</td>
						</tr>
						<tr>
						   <td width="100%" id="admin-agendas"><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Administrar Agendas</td>
						</tr>
				</table>	-->
			</div>
			<h6><a href="javascript:void(0);" id="" onclick="visorLeyesCodigos()">Consultar Leyes y C&oacute;digos</a></h6>
				<div>
					<!--  <table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" bordercolorlight="#FFFFFF" style="cursor:pointer">
						<tr>
							<td width="100%" id="leyes"><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Leyes</td>
						</tr>
						<tr>
							<td id="codigos">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Codigos</td>
						</tr>
						<tr>
							<td id="manuales">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Manuales</td>
						</tr>
					</table>-->
				</div>
			<h1><a href="javascript:void(0);">Chat</a></h1>
			<div>
				<div id="dialogoChat" title="Chat" align="center">
					<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
				</div>
				<center>
					<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
				</center>
			</div>
<!--			<h1><a href="javascript:void(0);">Clima</a></h1>-->
<!--				<div align="left">-->
<!--					<div align="left" id="test"></div>-->
<!--				</div>-->
			<h1><a href="javascript:void(0);" onclick="consultarTiposRol();">Facultades</a></h1>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
		</div>
	</div>
</div>