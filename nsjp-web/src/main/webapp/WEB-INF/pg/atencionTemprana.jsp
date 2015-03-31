<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
		
	<script type="text/javascript">
	var defensoria=0;

	function ejecutarFuncionTurno(idTurno, numeroTurno){
	
	}
		
	function refrescarArbol(){
	<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO">
		<logic:iterate name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO" id="elementoMenuAcordeon" >
				$("#<bean:write name="elementoMenuAcordeon" property="cIdHTML" />")
				.jstree(<bean:write name="elementoMenuAcordeon" property="cIdHTML" />Config);
				
				$("#<bean:write name="elementoMenuAcordeon" property="cIdHTML" />")
				.bind("loaded.jstree", function (event, data) {
        			$("#<bean:write name="elementoMenuAcordeon" property="cIdHTML" />").jstree("open_all");
    			});
		</logic:iterate>	
	</logic:notEmpty>		
	}
		
	$(document).ready(function() {
				
	});
		

	
	</script>
		
	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					<jsp:include page="/vAdmonRecepcion.jsp" flush="true"></jsp:include>			
					<div id="pager1">
					</div>
				</div>
			</div>
		</div>
	</div>
