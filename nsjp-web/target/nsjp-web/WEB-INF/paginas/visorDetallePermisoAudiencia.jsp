<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Atención de Audiencias</title>	
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	
	
	<script type="text/javascript">
	
	var permisoAudiencia= "";
	
	$(document).ready(function(){
		
		permisoAudiencia   = <%= request.getParameter("perAud")%>;
		var institucion    ="<%= request.getParameter("inst")%>";
		var estado         = <%= request.getParameter("est")%>;
		var audiencia      = <%= request.getParameter("aud")%>;
		var puesto         ="<%= request.getParameter("puesto")%>";
		var asignador      ="<%= request.getParameter("usrAsig")%>";
		var funcionario    ="<%= request.getParameter("nomFun")%>";
		var fechaSolicitud ="<%= request.getParameter("fechSol")%>";
		var fechaAsignacion="<%= request.getParameter("fechAsig")%>";
		var est            ="";
		
		if(estado=="<%=EstatusPermisosAudiencia.CONCEDIDO.getValorId()%>"){
			est="Concedido";
			$('#asignar1').hide();
			$('#asignar2').hide();
			$('#sinAsignar1').show();
			$('#sinAsignar2').show();
			$('#cancelar1').show();
			$('#cancelar2').show();
		}
		else if(estado=="<%=EstatusPermisosAudiencia.CANCELADO.getValorId()%>"){
			est="Cancelado";		
			$('#asignar1').show();
			$('#asignar2').show();
			$('#sinAsignar1').show();
			$('#sinAsignar2').show();
			$('#cancelar1').hide();
			$('#cancelar2').hide();
		}
		if(estado=="<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>"){
			est="Sin Asignar";		
			$('#asignar1').show();
			$('#asignar2').show();
			$('#sinAsignar1').hide();
			$('#sinAsignar2').hide();
			$('#cancelar1').show();
			$('#cancelar2').show();
		}	
		
		$('#permisoAudiencia').val(permisoAudiencia);
		$('#institucion').val(institucion);
		$('#estado').val(est);
		$('#audiencia').val(audiencia);
		$('#puesto').val(puesto);
		$('#asignador').val(asignador);												
		$('#funcionario').val(funcionario);
		$('#fechaSolicitud').val(fechaSolicitud);
		$('#fechaAsignacion').val(fechaAsignacion);
	});	
	
	function guardarNuevoEstado(estadoUpdate){
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/updateEstadoPermisosAudiencias.do?estado='+estadoUpdate+'&seleccionado='+permisoAudiencia,
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
		    						    		
		    			var resultado=$(xml).find('long').text();
						
						if(resultado==0){
							alertDinamicoCerrar("Fallo al realizar el cambio de estado, vuelva a intentarlo");
						}
						else{
							alertDinamicoCerrar("El cambio de estado se realizó correctamente");
						}
				}				    	
			});					
	}	
	
	//Función para alertDinamicoCerrar
	function alertDinamicoCerrar(textoAlert){						
		$("#divAlertTextoCerrar").html(textoAlert);
	    $( "#dialog-AlertCerrar" ).dialog({
			autoOpen: true,
			resizable: false,
			modal: true,
			buttons: {				
				
				"Aceptar": function() {						
					parent.cerrarVentanaDetallePermisoAudiencia();
					$( this ).dialog( "close" );
					$("#divAlertTextoCerrar").html("");					
				}				
			}
		});    
	 }
	
	</script>
</head>
<body>
	<!-- div para el alert dinamico antes de cerrar ventana -->
	<div id="dialog-AlertCerrar" style="display: none">
		<table align="center">
			<tr>
        		<td align="center">
            		<span id="divAlertTextoCerrar"></span>
            	</td>
        	</tr>
     	</table>              
	</div>    
	<div id="tabsDetalleAudiencias-1">
		<table width="900" border="0" align="center" cellpadding="0" cellspacing="5">
			<tr><td width="10%">&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
	        	<td>&nbsp;</td>
	        	<td align="right"><strong>Número de Permiso de Audiencia:</strong></td>
	            <td width="37%"><input type="text" id="permisoAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>	            	            	        
	        </tr>
	        <tr><td>&nbsp;</td></tr>
	        <tr><td>&nbsp;</td></tr>			
			<tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Institución:</strong></td>
	            <td><input type="text" id="institucion" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
			<tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Estado del Permiso:</strong></td>
	            <td><input type="text" id="estado" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	        	<td align="right"><strong>Número de Audiencia:</strong></td>
	            <td><input type="text" id="audiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>	        
	        <tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Puesto del Funcionario:</strong></td>
	            <td width="37%"><input type="text" id="puesto" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>	            
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Usuario Asignador:</strong></td>
	            <td width="37%"><input type="text" id="asignador" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Nombre del Funcionario:</strong></td>
	            <td width="37%"><input type="text" id="funcionario" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Fecha de Solicitud:</strong></td>
	            <td width="37%"><input type="text" id="fechaSolicitud" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Fecha de Asignación:</strong></td>
	            <td width="37%"><input type="text" id="fechaAsignacion" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>	        
	        <tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>	        
			<tr>
				<td>&nbsp;</td>
				<td id="asignar1"><input type="button" name="button" id="button" value="Conceder Permiso" onclick="guardarNuevoEstado(<%=EstatusPermisosAudiencia.CONCEDIDO.getValorId()%>);" class="btn_grande"></td>
				<td id="asignar2">&nbsp;</td>
				<td id="sinAsignar1"><input type="button" name="button" id="button" value="Sin Asignar Permiso" onclick="guardarNuevoEstado(<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>);" class="btn_grande"></td>
				<td id="sinAsignar2">&nbsp;</td>
				<td id="cancelar1"><input type="button" name="button" id="button" value="Cancelar Permiso" onclick="guardarNuevoEstado(<%=EstatusPermisosAudiencia.CANCELADO.getValorId()%>);"  class="btn_grande"></td>
				<td id="cancelar2">&nbsp;</td>									            	            	            			            						
	  		</tr>
		</table> 
	</div>		
</body>
</html>