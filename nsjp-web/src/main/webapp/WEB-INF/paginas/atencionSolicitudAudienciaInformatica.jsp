<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Administracion de Audiencia Informatica</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	
	<script type="text/javascript">

	var solicitudId;
	
	$(document).ready(function() {
	
		$( "#tabsprincipalconsulta" ).tabs();
		$("#generarDocumento").click(generarDocumentoViwe);

		//obtenemos el id de la solicitud para recuperar el detalle y el documento
		solicitudId = "<%= request.getParameter("solicitudId")%>";
				//obtenemos el id de la solicitud para recuperar el detalle y el documento
		estatus = "<%= request.getParameter("solicitudId")%>";
		/*
		*Obtenemos el nombre del solicitante para saber si generamos el documento o 
		*o solo lo abrimos en modo de consulta
		*/
		var solicitante = "<%= request.getParameter("solicitante")%>";

		//Consultamos el detalle de la solicitud
		consultarDetalleSolicitudDeTranscripcion(solicitudId);
		
		/********************************************************/
		cargaNotificacionesFuncionario();
		cargaNotificacionesInvolucrado();

		/*****************************************************/
	});

	function generarDocumentoViwe() {
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Documento", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/generarDocumento.do" width="1140" height="400" />');
	    		
	}

	/**
	*Funcion que consulta el detalle de la solicitud de transcripcion
	*/
	function consultarDetalleSolicitudDeTranscripcion(solicitudId){
		

		if(solicitudId != null){
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarDetalleSolicitudTranscripcionAudioVideo.do',
				data: 'solicitudId='+ solicitudId, 
				async: false,
				dataType: 'xml',
				success: function(xml){
					var errorCode;
					errorCode=$(xml).find('response').find('code').text();
					if(parseInt(errorCode)==0){
		  
						limpiaDatosDetalleSolicitud();
	    				//Mostramos los campos con el detalle de la solicitud		
	    				$("#numCaso").val($(xml).find('numeroGeneralCaso').first().text());
	    				$("#numCausa").val($(xml).find('numeroExpediente').first().text());
	    				
						var fechaSol = $(xml).find('strFechaCreacion').first().text();
						var horaSol = $(xml).find('strHoraCreacion').first().text();
	    				
	    				$("#fechHoraSol").val(fechaSol+" "+horaSol);
	    				$("#nombreSolicitante").val($(xml).find('nombreSolicitante').first().text());
	    				$("#institucionSolicitante").val($(xml).find('solicitudTranscripcionAudienciaDTO').find('institucion').find('nombreInst').first().text());
	    				$("#tipoAudiencia").val($(xml).find('tipoAudiencia').find('valor').first().text());
	    				$("#audienciaId").val($(xml).find('audiencia').find('id').first().text());
	    				var fechaIniAud = $(xml).find('audiencia').find('strFechaInicio').first().text();
	    				var horaIniAud = $(xml).find('audiencia').find('strHoraInicio').first().text();
	    				
	    				$("#fechHoraInicioAudiencia").val(fechaIniAud+" "+horaIniAud);
	    				
	    				var fechaFinAud = $(xml).find('audiencia').find('strFechaFin').first().text();
	    				var horaFinAud = $(xml).find('audiencia').find('strHoraFin').first().text();
	    				
	    				$("#fechHoraFinAudiencia").val(fechaFinAud+" "+horaFinAud);	

	    				//estado de la solicitud
	    				estatusSolicitud = $(xml).find('solicitudTranscripcionAudienciaDTO').find('estatusSolicitud').find('idCampo').first().text();
	    				verificaEstatusSolicitud(estatusSolicitud);
	    			}
					else{
						//Mostrar mensaje de error
					}
				}
			});		
		}
	}

	/*
	*Funcion que limpia los datos de la solicitud
	*/
	function limpiaDatosDetalleSolicitud(){

		$("#numCaso").val("");
		$("#numCausa").val("");
		$("#fechHoraSol").val("");
		$("#nombreSolicitante").val("");
		$("#institucionSolicitante").val("");
		$("#tipoAudiencia").val("");
		$("#audienciaId").val("");
		$("#fechHoraInicioAudiencia").val("");
		$("#fechHoraFinAudiencia").val("");
	}

	/*
	*Funcion que verifica el estatus de la solicitud
	*/
	function verificaEstatusSolicitud(estatusSolicitud){
		
		if(estatusSolicitud != null && estatusSolicitud != ""){
			
			if( estatusSolicitud == <%=EstatusSolicitud.EN_PROCESO.getValorId()%> || estatusSolicitud == <%=EstatusSolicitud.ABIERTA.getValorId()%> ){
				$('#atendida').change(habilitaBtnGuardado);	
				$("#guardar").attr('disabled','disabled');				
			}
			else if(estatusSolicitud == <%=EstatusSolicitud.CERRADA.getValorId()%>){
				$('#atendida').attr('checked', true);
				$("#atendida").attr('disabled','disabled');
				$("#guardar").attr('disabled','disabled');
				$("#guardar").hide();
				$("#etiquetaSinAtender").hide();
		        $("#etiquetaAtendida").show();
			}
		}
		
	}

	/*
	*Funcion que habilita el boton de guardado si el checkbox 
	*de atendido es checado
	*/
	function habilitaBtnGuardado(){

		if($('#atendida').is(':checked')){
			$("#guardar").removeAttr('disabled');
		}
		else{
			$("#guardar").attr('disabled','disabled');
		}
	}

	/*
	*Funcion que guarda el -Marcado como atendido- y cambia el estado de la 
	*solicitud a cerrada
	*/
	
	
	/*Enable IT encargado informatica copias Audio y Video*/
	var arrayIds = new Array();
    var arrayIds2 = new Array();
    var idi="";
    var numeroCopiasFunc="";
    var numeroCopiasInvo="";
    var fun="",invo="";
	
	
    /*Enable IT encargado informatica copias Audio y Video*/
	function finalizarSolicitudDeAV() {
		/*	Nueva Funcionalidad Enable IT*/	
		numCp();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/finalizarSolicitudTranscripcionAudioVideo.do',
			data: 'solicitudId='+ solicitudId+'&audienciaId='+idi+'&invoFuncionarios='+fun+'&invoInvolucrados='+invo+'&copiasFun='+numeroCopiasFunc+'&copiasInvo='+numeroCopiasInvo,
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					alertDinamico("Guardado exitoso");
					cerrarVentana();
					parent.cargaGridSolicitudesDeAudioVideo("enProceso");
				}
				else{

				}
			}
		});
	}

	/*
	*Cerrar la ventana de la solicitud desde el padre
	*/
	function cerrarVentana(){
		parent.cerrarVentanaAtencionDeSolicitud();
	}
	
	
	/******************************************************************************************
	 * 
	 *Nueva Funcionalidad de Enable IT 
	 * 
	 ******************************************************************************************/

	var idinvolucrado;

		function cargaNotificacionesFuncionario(){
			
			var idi = $("#audienciaId").val();
			//alert("antes de enviar al action valor de solicitudId:: "+solicitudId);
			jQuery("#gridDetalleNotificaciones").jqGrid({ 
				
				url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?solicitudId='+solicitudId+'&tipoDeRespuesta=0&idEvento='+idi+'&esFuncionario=1&infromatica=1'+'', 
				//data:'solicitudId='+solicitudId,					
				datatype: "xml", 
				colNames:['Interveniente','idInstitucion','Instituci&oacute;n','Tipo Evento','&Uacute;ltima notificaci&oacute;n creada','&Uacute;ltima notificaci&oacute;n recibida','No. Copias','esFuncionario'], 
				colModel:[ 	{name:'involucrado',index:'involucrado', width:150,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
				           	{name:'idInstitucion',index:'idInstitucion', width:150, hidden:true},
							{name:'institucion',index:'institucion', width:120,align:'center'},
							{name:'tipoNotificacion',index:'tipoNotificacion', width:120,align:'center'},
							{name:'notificacionEnviada',index:'notificacionEnviada', width:160,align:'center',hidden:true},
							{name:'notificacionRecibida',index:'notificacionRecibida', width:160,align:'center',hidden:true},
							{name:'noCopias',index:'noNotificaciones', width:150,align:'center',disabled:true},
							{name:'esFuncionario',index:'esFuncionario', width:150, hidden:true}
						],
				pager: jQuery('#pagerNotificacionesFuncionarios'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				caption: "Funcionarios asociados a la audiencia",
				loadComplete: function(){
					
					rows = jQuery("#gridDetalleNotificaciones").jqGrid('getGridParam','records')
					
				},
				onSelectRow: function(rowid) {
					
					idinvolucrado=rowid;
					
				}
			}).navGrid('#pagerNotificacionesFuncionarios',{edit:false,add:false,del:false});
		}
		
		
		
		/*
		*Funcion que carga el grid, con el detalle de las notificaciones asociadas a un involucrado
		*/
		function cargaNotificacionesInvolucrado(){
			
			var idi = $("#audienciaId").val();
			//alert("antes de enviar al action valor de solicitudId:: "+solicitudId);
			jQuery("#gridDetalleNotificacionesInvolucrado").jqGrid({ 
				url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?solicitudId='+solicitudId+'&tipoDeRespuesta=0&idEvento='+idi+'&esFuncionario=0&infromatica=1'+'',
				datatype: "xml", 
				colNames:['Interveniente','Tipo Evento','Calidad','&Uacute;ltima notificaci&oacute;n creada','&Uacute;ltima notificaci&oacute;n recibida','No. de Copias','esFuncionario'], 
				colModel:[ 	{name:'involucrado',index:'involucrado', width:150,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
							{name:'tipoNotificacion',index:'tipoNotificacion', width:150,align:'center'},
							{name:'calidad',index:'calidad', width:150,align:'center'}, 
							{name:'notificacionEnviada',index:'notificacionEnviada', width:160,align:'center',hidden:true},
							{name:'notificacionRecibida',index:'notificacionRecibida', width:160,align:'center',hidden:true},
							{name:'noCopias',index:'noCopias', width:150,align:'center'},
							{name:'esFuncionario',index:'esFuncionario', width:150, hidden:true}
						],
				pager: jQuery('#pagerNotificacionesInvolucrado'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				caption: "Involucrados asociados a la audiencia",
				loadComplete: function(){
					
					rows = jQuery("#gridDetalleNotificacionesInvolucrado").jqGrid('getGridParam','records')
					
				},
				onSelectRow: function(rowid) {
					
					idinvolucrado=rowid;
									
				}
			}).navGrid('#pagerNotificacionesInvolucrado',{edit:false,add:false,del:false});
			
		}
		
			
		function numCp() {

	        var i=0;      
	        
			idi = $("#audienciaId").val();
	        
	        /*Obteniendo los Ids de los involucrados de la audiencia*/
	        invlucradosIdsFunc = jQuery("#gridDetalleNotificaciones").getDataIDs();
	        involucradosIds = jQuery("#gridDetalleNotificacionesInvolucrado").getDataIDs();
	                
	        /*Obteniendo numero de copias de los funcionarios*/
	        $('select[id^="numcp_"]').each(function(){
	               id = $(this).attr("id");
	               var datosRegistro = jQuery("#gridDetalleNotificaciones").jqGrid('getRowData', arrayIds[i]);
					/*Obteniendo el valor de los id´s del numero de copias*/
	                numeroCopiasFunc += $(this).val()+",";
			        i++;
	        });
	        
	       /*obteniendo numero de copias de los involucrados*/
	        $('select[id^="numcp2_"]').each(function(){
	            id2 = $(this).attr("id2");
	            var datosRegistro = jQuery("#gridDetalleNotificacionesInvolucrado").jqGrid('getRowData', arrayIds2[i]);
					/*Obteniendo el valor de los id´s del numero de copias*/
	             numeroCopiasInvo += $(this).val()+",";	                   
	            i++;
	   		});

	       /*Parseando los ids de los involucrados con ,*/
	        for(var i in involucradosIds){
	        	invo += involucradosIds[i]+",";
	        }

	       /*Parseando los ids de los funcionarios con ,*/
	        for(var i in invlucradosIdsFunc){
		       	fun += invlucradosIdsFunc[i]+",";
		   
	       }
	        
	  }
		
		
	/********************************************************************************************/

		
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

<div id="tabsprincipalconsulta">
	<ul>
		<li><a href="#tabsconsultaprincipal-1">Generales</a></li>		
	</ul>
	<div id="tabsconsultaprincipal-1" style="height: 400">
		<table width="858" border="0" cellspacing="5" cellpadding="0">
		    <tr>
		        <td width="22%" align="right"><strong>N&uacute;mero de Caso:</strong></td>
		      <td width="25%" align="left"><input type="text" id="numCaso" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		        <td width="26%" align="right"><strong>Institución:</strong></td>
		        <td width="27%"><input type="text" id="institucionSolicitante" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		    </tr>
		    <tr>
		        <td align="right"><strong>N&uacute;mero de Causa:</strong></td>
		      <td align="left"><input type="text" id="numCausa" style="width: 200px; border: 0; background: #DDD;" readonly="readonly"  /></td>
		        <td align="right"><strong>Tipo de Audiencia:</strong></td>
		        <td ><input type="text" id="tipoAudiencia" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		    </tr>
		    <tr>
		        <td align="right"><strong>Fecha/Hora Solicitud:</strong></td>
		      <td align="left"><input type="text" id="fechHoraSol" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		        <td align="right"><strong>Identificador Audiencia:</strong></td>
		        <td><input type="text" id="audienciaId" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		    </tr>
		    <tr>
		        <td align="right"><strong>Solicitante:</strong></td>
		      <td align="left"><input type="text" id="nombreSolicitante" style="width: 200px; border: 0; background: #DDD;" readonly="readonly"  /></td>
		        <td align="right"><strong>Fecha/Hora Inicio Audiencia:</strong></td>
		        <td><input type="text" id="fechHoraInicioAudiencia" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>        
		    </tr>
		    <tr>
		        <td align="right"><strong>
		        <span id="etiquetaSinAtender">Marcar como atendida:</span>
		        <span id="etiquetaAtendida" style="display: none;">Atendida:</span>
		        </strong></td>
		      <td align="left"><input type="checkbox" id="atendida" /></td>
		        <td align="right"><strong>Fecha/Hora Fin Audiencia:</strong></td>
		        <td><input type="text" id="fechHoraFinAudiencia" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" /></td>
		    </tr>
		    <tr>
		        <td align="center">&nbsp;</td>
		        <td align="center">&nbsp;</td>
		        <td align="center"><input type="submit" id="guardar" value="Guardar" onclick="finalizarSolicitudDeAV();" class="btn_Generico"/>
		            
		      </td>
		        <td align="center">&nbsp;</td>
		    </tr>
		  <tr>
				<td colspan="4" align="center">
					<table id="gridDetalleNotificaciones" >
	                	<tr>
	                		<td>&nbsp;</td>
	                	</tr>
					</table>
					<div id="pagerNotificacionesFuncionarios"></div>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" align="center">
					<table id="gridDetalleNotificacionesInvolucrado" >
	                	<tr>
	                		<td>&nbsp;</td>
	                	</tr>
					</table>
					<div id="pagerNotificacionesInvolucrado"></div>
				</td>
			</tr>
			
		
		</table>
		
		
		<table align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<table id="gridDetalleNotificaciones" >
	                	<tr>
	                		<td>&nbsp;</td>
	                	</tr>
					</table>
					<div id="pagerNotificacionesFuncionarios"></div>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			
			<tr>
				<td colspan="4" align="center">
					<table id="gridDetalleNotificacionesInvolucrado" >
	                	<tr>
	                		<td>&nbsp;</td>
	                	</tr>
					</table>
					<div id="pagerNotificacionesInvolucrado"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td width="150" align="center">
					<!--<input type="button" value="Guardar" id="guardar" />-->
				</td>
				<td width="149" align="center">&nbsp;</td>
			</tr>
		</table>
		
	</div>

</div>
</body>
</html>