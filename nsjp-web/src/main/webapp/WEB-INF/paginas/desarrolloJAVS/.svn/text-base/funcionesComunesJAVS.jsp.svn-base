<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	
	<script type="text/javascript">
	
	/**
	* Desarrollo para la solicitud de permisos usuarios internos a PJ
	**/
	function solicitarPermisoAudiencia(audienciaId){
		if(idEstatusAudiencia == <%=EstatusAudiencia.CANCELADA.getValorId()%>){
			customAlert("La audiencia ha sido cancelada, no es posible solicitar el permiso de audiencia");
		}else{
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/solicitarPermisoAudienciaJAVS.do?audienciaId='+audienciaId,
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
		    						    		
		    			// Problemas con el converter, no respeta el alias
						var resultado=$(xml).find('long').text();
						if(resultado==""){
							resultado=$(xml).find('fechaActual').text();
						}
						if(resultado==""){
							resultado=$(xml).find('horaActual').text();
						}

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
	}

	/**
	* Verifica el estatus de un permiso de audiencia para un usuario interno a PJ
	**/
	function verificarSolicitudPermisoAudiencia(audienciaId){
		$.ajax({
			type: 'POST',
	    	url: '<%=request.getContextPath()%>/verificarSolicitudPermiso.do?audienciaId='+audienciaId,
	    	data: '',
	    	dataType: 'xml',
	    	async: false,
	    	success: function(xml){	    					    			
					var resultado=$(xml).find('long').text();
					if(resultado==""){
						resultado=$(xml).find('fechaActual').text();
					}
					if(resultado==""){
						resultado=$(xml).find('horaActual').text();
					}
					leyendaVerificarSolicitudPermisoInternoAudiencia(resultado, audienciaId);
			}				    	
		});							
	}

	/**
	* Función para el control de leyendas para el verificación de permisos con los estados controlados
	* en EstatusPermisoAudiencia -- íntimamente ligados con los valores de retorno del WS-JAVS
	**/
	function leyendaVerificarSolicitudPermisoInternoAudiencia(resultado, audienciaId){
		if(resultado=="<%=EstatusPermisosAudiencia.NO_ES_JAVS.getValorId()%>"){
			customAlert("La audiencia no se ha llevado a cabo en una sala JAVS.");															
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.FALLO.getValorId()%>"){
			customAlert("Fallo en la verificación de permisos de la audiencia.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.SIN_ASIGNAR.getValorId()%>"){
			customAlert("No se ha atendido su solicitud de permisos de la audiencia.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.CONCEDIDO.getValorId()%>"){
			alertDinamicoConsulta("Verificación de permisos aceptada, se procede a conectarse con el WS.",audienciaId);
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.CANCELADO.getValorId()%>"){
			customAlert("Usted no tiene permisos de descarga, favor de generar su solicitud de permiso nuevamente.");
		}
		else if(resultado=="<%=EstatusPermisosAudiencia.NO_HAY_SOLICITUD.getValorId()%>"){
			customAlert("Usted no tiene una solicitud de permiso de descarga, favor de generarla.");
		}
	}
	
	/**
	* Alert dinamico que invoca la consulta del estus de audiencias en JAVS
	**/
	function alertDinamicoConsulta(textoAlert, audienciaId){
		$("#divAlertTextoCerrar").html(textoAlert);
	    $( "#dialog-AlertCerrar" ).dialog({
			autoOpen: true,
			resizable: false,
			modal: true,
			buttons: {							
				"Aceptar": function() {						
					$(this).dialog("close");
					$("#divAlertTextoCerrar").html("");
					consultarAudienciaJAVS(audienciaId);
				}				
			}
		});    
	}
	
	/**
	* Mapeado los valores de retorno del WS-JAVS en ConstantesGenerales
	**/
	function consultarAudienciaJAVS(audienciaId){
		$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/consultarAudienciaJavs.do?idAudiencia='+audienciaId+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var respuesta;
				respuesta=$(xml).find('response').find('body').find('long').text();
				var S_Mensaje=mensajeEstadoJAVSConsulta(respuesta);
				if(respuesta=="<%=ConstantesGenerales.AUDIENCIA_ACTUALIZADA%>"){
					alertDinamico("La audiencia ya se ha llevado a cabo, presione click para continuar con la descarga de videos");
					invocacionApplet(audienciaId);
				}
				else{
					if(S_Mensaje!=""){
						alertDinamico(S_Mensaje);
					}
					else{
						// Puede ser error de conexión WS
						alertDinamico("Fallo al consultar la audiencia");
					}
				}
				$("#gridAudienciasResolutivosPJENS").trigger("reloadGrid");
			}
		});
	}

	/**
	* Valores de retorno de la consulta de audiencias en JAVS
	**/
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

	function invocacionApplet(audienciaId){
		
		var Audiencia = audienciaId;

/*		$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/datosSalaJAVS.do?idAudiencia='+audienciaId+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var respuesta;
				respuesta=$(xml).find('response').find('body').find('respuesta').text();
				var dato = respuesta.split(",");
				
				if(dato.length==3){
					DirIP = dato[0];
					Password = dato[1];
					Usuario = dato[2];
				}
			}
		});*/
				
		/*$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/dirsJAVS.do?idAudiencia='+audienciaId+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				RutasDirs=$(xml).find('response').find('body').find('respuesta').text();
			}
		});*/
		
/*		$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/jvlJAVS.do?idAudiencia='+audienciaId+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				ArchivoJVL=$(xml).find('response').find('body').find('respuesta').text();
			}
		});*/
		
		$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/conglomeradoJAVS.do?idAudiencia='+audienciaId+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				// Problemas con el converter, no respeta el alias
				var resultado=$(xml).find('long').text();
				if(resultado==""){
					resultado=$(xml).find('fechaActual').text();
				}
				if(resultado==""){
					resultado=$(xml).find('horaActual').text();
				}
				if(resultado>0){
					invocacionPantallaApplet(Audiencia);
				}
			}
		});
				
		//invocacionPantallaApplet(Audiencia, Usuario, Password, DirIP, RutasDirs, ArchivoJVL);		
	}

	/**
	* Invocación del applet de descarga
	**/
	function invocacionPantallaApplet(Audiencia){
		idApplet++;
		var alto=1000;
		var ancho=1300;
		
		$.newWindow({id:"iframeWindowInvocacionApplet" + idApplet, statusBar: true, posx:0,posy:0,width:1300,height:1300,title:"Transferencia de videos", type:"iframe"});
//		$.updateWindowContent("iframeWindowInvocacionApplet" + idApplet,'<iframe src="<%= request.getContextPath() %>/invocacionApplet.do?ancho='+ancho+'&alto='+alto+'&Audiencia='+Audiencia+'&Usuario='+Usuario+'&Password='+Password+'&DirIP='+DirIP+'&RutaDirs='+RutaDirs+'" width="1300" height="1300" />');
		$.updateWindowContent("iframeWindowInvocacionApplet" + idApplet,'<iframe src="<%= request.getContextPath() %>/invocacionApplet.do?Audiencia='+Audiencia+'" width="1300" height="1300" />');
	}
	
	</script>	