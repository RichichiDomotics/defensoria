<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<div class="ui-layout-north">
	<div class="content">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"  height="100%">
				  <TBODY style="background: #fff;">
					  <TR>
					    <TD width=100 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_nsjph.jpg"></TD>
					    <TD width=150 align=left valign="middle" style="color:#51a651;">ATENCION TEMPRANA</TD>
					    <TD width=126 align=left valign="top"><!--SPAN></SPAN--><img src="<%=request.getContextPath()%>/resources/images/ejecutivo.png" width="100px"></TD>
					    <TD width=272 align=center valign="top"><img id="logoPagina" src="<%=request.getContextPath()%>/resources/images/sis_penal.jpg" width="300px"></TD>
					    <TD width=28 align=middle>&nbsp;</TD><td width="150" align="center"><img src="<%=request.getContextPath()%>/resources/images/escudo.png" width="100px"></td>
					    <TD width="150" align="right"  valign="middle">
										<br>
										<br>
										<br>
										<DIV id=liveclock style="visibility:hidden;"></DIV>
										<a href="#" title="Salir" onclick='$("#dialog-logout").dialog( "open" );'><img src="<%=request.getContextPath()%>/resources/images/cerrar.jpg" width="50" height="50" border="0" style="box-shadow: 2px 2px 5px #999;"/></a>
										<!--a href="#" title="Ayuda"><img src="<%=request.getContextPath()%>/resources/images/Help.png" width="26" height="26" border="0"></a>
										<IMG alt="Icono reloj" src="<%=request.getContextPath()%>/resources/images/clock.png" width=26 height=25-->
									</TD>
						
					  </TD>
					  </TR>
				  </TBODY>
			  </TABLE>
		</div>
	
	<ul class="toolbar">
		<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_SUPERIOR">
			<logic:iterate name="KEY_SESSION_MENU_DINAMICO_SUPERIOR" id="elementoMenuSuperior" >
				<div id="<bean:write name="elementoMenuSuperior" property="cIdHTML" />">
					<logic:notEmpty name="elementoMenuSuperior" property="elementoMenuHijosDTO">
						<logic:iterate name="elementoMenuSuperior" property="elementoMenuHijosDTO" id="elementoMenuBarra" >
							<li 
								id="<bean:write name="elementoMenuBarra" property="cIdHTML" />" 
								class="<bean:write name="elementoMenuBarra" property="cClassHTML" />" 
								onclick="<bean:write name="elementoMenuBarra" property="cComando" />" 
							>									
								<span></span>
								<bean:write name="elementoMenuBarra" property="cNombre" />
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</div>
			</logic:iterate>					
		</logic:notEmpty>
		<logic:empty name="KEY_SESSION_MENU_DINAMICO_SUPERIOR">
			<div id="menu_head">	
				<li id="tbarBtnHeaderZise" class="first"><span></span></li>
			</div>
			<div id="menu_config" >
			</div>
		</logic:empty>			
	</ul>
</div>