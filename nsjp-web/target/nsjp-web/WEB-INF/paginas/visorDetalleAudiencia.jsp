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
	
	var audiencia= "";
	/*Se modifica la informacion a presentar sobre las audiencias*/
	$(document).ready(function(){
		audiencia=<%= request.getParameter("audiencia")%>;
		var caso	 ="<%= request.getParameter("caso")%>";
		var noExpediente = "<%= request.getParameter("noExpediente")%>";
		var numeroCausa ="<%= request.getParameter("numeroCausa")%>";
		var tipo	 ="<%= request.getParameter("tipo")%>";
		var sala	 ="<%= request.getParameter("sala")%>";
		var juzgado	 ="<%= request.getParameter("juzgado")%>";
		var fecha	 ="<%= request.getParameter("fecha")%>";
		
		$('#numeroAudiencia').val(audiencia);
		$('#casoAudiencia').val(caso);
		$('#noExpediente').val(noExpediente);
		$('#numeroCausa').val(numeroCausa);
		$('#tipoAudiencia').val(tipo);
		$('#salaAudiencia').val(sala);
		$('#juzgadoAudiencia').val(juzgado);
		$('#fechaAudiencia').val(fecha);	
	});
	
	function invocacionPermisoJAVS(){
		esExterno="true";
		$.ajax({
			type: 'POST',
	    	url: '<%=request.getContextPath()%>/verificarSolicitudPermiso.do?audienciaId='+audiencia+'&esExterno='+esExterno,
	    	data: '',
	    	dataType: 'xml',
	    	async: false,
	    	success: function(xml){	    						    		
				var resultado=$(xml).find('long').text();
				if(resultado==""){
					resultado=$(xml).find('fechaActual').text();
				}
					
				S_Mensaje = mensajeEstadoJAVSConsulta();
				if(S_Mensaje ==""){
					leyendaVerificarSolicitudPermisoExternoAudiencia(resultado);
				}
				else{
					customAlert(S_Mensaje);
					if(resultado=="<%=ConstantesGenerales.AUDIENCIA_ACTUALIZADA%>"){
						invocacionPantallaApplet(audiencia);
					}
				}
			}				    	
		});							
	}
	
	function invocacionPantallaApplet(Audiencia){
		idApplet++;
	
		$.newWindow({id:"iframeWindowInvocacionApplet" + idApplet, statusBar: true, posx:0,posy:0,width:1300,height:1300,title:"Transferencia de videos", type:"iframe"});
		$.updateWindowContent("iframeWindowInvocacionApplet" + idApplet,'<iframe src="<%= request.getContextPath() %>/invocacionApplet.do?Audiencia='+Audiencia+'" width="1300" height="1300" />');
	}
		
	function leyendaVerificarSolicitudPermisoExternoAudiencia(resultado){
		if(resultado=="<%=EstatusPermisosAudiencia.NO_ES_JAVS.getValorId()%>"){
			customAlert("La audiencia no se ha llevado a cabo en una sala JAVS.");															
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.FALLO.getValorId()%>"){
			customAlert("Fallo en la verificación de permisos de la audiencia.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>"){
			customAlert("No se ha atendido su solicitud de permisos de la audiencia.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.CANCELADO.getValorId()%>"){
			customAlert("Usted no tiene permisos de descarga, favor de generar su solicitud de permiso nuevamente.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.NO_HAY_SOLICITUD.getValorId()%>"){
			customAlert("Usted no tiene una solicitud de permiso de descarga, favor de generarla.");
		}
		else{
			//customAlert("Fallo al conectar con el Web Service.");
			customAlert("Fallo al verificar la solicitud de permiso");
		}
	}
	
	function mensajeEstadoJAVSConsulta(idEvento){
		var S_Mensaje="";
		switch (idEvento){
			case "<%=ConstantesGenerales.AUDIENCIA_NO_ACTIVA%>":
        		S_Mensaje = "La audiencia no se ha llevado a cabo aún";
            	break;
			case "<%=ConstantesGenerales.NO_ES_JAVS%>":
        		S_Mensaje = "La audiencia no se llevo a cabo en una sala JAVS";
            	break;			
			case "<%=ConstantesGenerales.RESOLUTIVOS_ACTUALIZADOS%>":
                S_Mensaje = "Se han actualizado las notas a este momento, la audiencia sigue en proceso";
                break;            
            case "<%=ConstantesGenerales.AUDIENCIA_ACTUALIZADA%>":
                S_Mensaje = "Se han actualizados las notas y los datos de la audiencia.</br>La audiencia se ha llevado a cabo";
                break;
            case "<%=ConstantesGenerales.FALLO_GENERAL%>":
                S_Mensaje = "Fallo al conectar con el servidor JAVS. Vuelva a intentarlo más tarde";
                break;
            case "<%=ConstantesGenerales.FALLO_GENERAL_JAVS%>":
            	S_Mensaje = "Fallo al conectar con el servidor JAVS. Vuelva a intentarlo más tarde";
                break;
            case "<%=ConstantesGenerales.ERROR_CREDENCIALES_CONSULTA%>":
            	S_Mensaje = "Fallo al conectar con el servidor JAVS, credenciales incorrectas.</br> Vuelva a intentarlo más tarde";
                break;
            case "<%=ConstantesGenerales.NO_HAY_AUDIENCIAS%>":
                S_Mensaje = "La audiencia no esta agendada en Sala JAVS.";
                break;        
        }		
        return S_Mensaje;
	}
	
	function invocacionSolicitudJAVS(){
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/solicitarPermisoExternoAudienciaJAVS.do?audienciaId='+audiencia,
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
		    						    		
						var resultado=$(xml).find('long').text();

						if(resultado=="<%=EstatusPermisosAudiencia.NO_ES_JAVS.getValorId()%>"){
							customAlert("La audiencia no se ha llevado a cabo en una sala JAVS, no es posible solicitar un permiso.");															
						}
						else if(resultado=="<%=EstatusPermisosAudiencia.FALLO.getValorId()%>"){
							customAlert("Fallo en el servicio de solicitud de Permisos de audiencia.");
						}
						else if(resultado=="<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>"){
							customAlert("Ya se realizó una solicitud anteriormente para esta audiencia, aún no ha sido atendida.");
						}
						else if(resultado=="<%=EstatusPermisosAudiencia.CONCEDIDO.getValorId()%>"){
							customAlert("Ya se realizó una solicitud anteriormente para esta audiencia, usted ya cuenta con permisos.");
						}
						else if(resultado=="<%=EstatusPermisosAudiencia.NUEVA_SOLICITUD.getValorId()%>"){
							customAlert("Se generó correctamente la solicitud.");
						}
				}				    	
			});					
	}
	
	</script>
</head>
<body>
	<div id="tabsDetalleAudiencias-1">
		<table width="900" border="0" align="center" cellpadding="0" cellspacing="5">
			<tr><td width="10%">&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
	        	<td>&nbsp;</td>
	        	<td align="right"><strong>Número de Audiencia:</strong></td>
	            <td width="37%"><input type="text" id="numeroAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>	            	            	        
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Número de Caso:</strong></td>
	            <td width="37%"><input type="text" id="casoAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td width="21%" align="right"><strong>Número de Carpeta:</strong></td>
	            <td width="37%"><input type="text" id="noExpediente" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Causa:</strong></td>
	            <td><input type="text" id="numeroCausa" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
			<tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Sala:</strong></td>
	            <td><input type="text" id="salaAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
			<tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Juzgado:</strong></td>
	            <td><input type="text" id="juzgadoAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	        	<td align="right"><strong>Fecha de audiencia:</strong></td>
	            <td><input type="text" id="fechaAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>
	        </tr>	        
	        <tr>
	        	<td>&nbsp;</td>
	            <td align="right"><strong>Tipo de Audiencia:</strong></td>
	            <td width="37%"><input type="text" id="tipoAudiencia" style="width:230px; border: 0; background:#DDD;" readonly="readonly"/></td>	            
	        </tr>
	        
	        <tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>	        
			<!-- <tr> -->
				<!-- <td>&nbsp;</td>
				<td><input type="button" name="button" id="button" value="Solicitar permiso de consulta" onclick="invocacionSolicitudJAVS()" class="btn_grande"></td>
	            <td width="10%">&nbsp;</td>
	            <td><input type="button" name="button" id="button" value="Videos JAVS" onclick="invocacionPermisoJAVS()" class="btn_grande"></td>
	            <td>&nbsp;</td>	  -->           	            			            								
	  		<!-- </tr> -->
		</table> 
	</div>		

</body>
</html>