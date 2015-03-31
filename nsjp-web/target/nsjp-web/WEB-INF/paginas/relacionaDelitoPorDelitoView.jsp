<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	ConfInstitucionDTO institucion = usuario.getInstitucion();
	long valorInstitucion = institucion.getConfInstitucionId();
%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>	
	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
           // $.jgrid.useJSON = true;

    	//var contDelitosGravesDos=0;
    	var muestraCombosDelitoPersona;
    	var rolActivo = '<%=rolActivo%>';

    	/***** Ejecutamos funciones para setear la pagina*****/
		$(document).ready(function(){
						
			$( "#dialogTres:ui-dialog" ).dialog( "destroy" );
			
			$("#btnRelacionarRDPD").hide();
			$("#btnAnulaRelacionDelDel").hide();

			if (rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
			    <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
				$("#btnRelacionarRDPD").hide();
				$("#btnAnulaRelacionDelDel").hide();					
			}
		});
    	
		function validaSeleccion(){			
			if($("#cbxDelitosRDPD option:selected").val()!=-1){
				if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
				   <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){									
	    			$("#btnRelacionarRDPD").hide();
	    			$("#btnAnulaRelacionDelDel").hide();
				}
				else{
	    			$("#btnRelacionarRDPD").show();
				}
    		}
    		else{
    			$("#btnRelacionarRDPD").hide();
    			$("#btnAnulaRelacionDelDel").hide();
    		}
			consultaDelitosPRRDPD();
    	}


		/*Funcion para lanzar el popup que relacionara 
		*un delito por delito a un probable responsable
		*/
		function abrePopupRelacionarDelitosPRRDPD()
		{
			$( "#dialogTres:ui-dialog" ).dialog();
			$( "#dialogTres:ui-dialog" ).dialog( "destroy" );
			
			//cargamos el combo con los probables responsables
			cargaComboPRExpedientesRDPD();
			
			//RE USO - cargamos el combo con las formas de participacion del involucrado
			cargaComboFormaParticipacionRDPD();
			//RE USO - cargamos el combo de victimas del expediente
			cargaComboVictimasRDPD();
			
			//cargamos el combo de clasificacion delito
			cargaComboClasificacionDelitoRDPD();
			//cargamos el combo del lugar de delito
			cargaComboLugarDelitoRDPD();
			//cargamos el combo de modalidad delito
			cargaComboModalidadDelitoRDPD();
			//cargamos el combo de modus delito
			cargaComboModusDelitoRDPD();
			//cargamos el combo de causa delito
			cargaComboCausaDelitoRDPD();
			
			if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
				ocultaCombosInnecesariosDelitoPersonaPorDelito();
			}
			
			//generamos el Dialogo
			$( "#dialogTres-confirm" ).dialog({
				resizable: false,
				height:425,
				width:820,
				modal: true,
				buttons: {
					"Guardar": function() {
						$( this ).dialog( "close" );
						$( "#dialogTres:ui-dialog" ).dialog( "destroy" );
						RelacionarDelitoRDPD();
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialogTres:ui-dialog" ).dialog( "destroy" );
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
		
		//cargamos el combo de victimas del expediente
		function cargaComboVictimasRDPD()
		{
			$("#cbxVictimasExpRDPD").empty();
			$('#cbxVictimasExpRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=VICTIMA',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('involucradoDTO').each(function(){
						var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('nombresDemograficoDTO').find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('nombresDemograficoDTO').find('apellidoMaterno').text()+" ";
						if(nombreCompleto==null || nombreCompleto=="null" || nombreCompleto=="" || nombreCompleto=="   "){
							nombreCompleto="Anónimo";
						}
						$('#cbxVictimasExpRDPD').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
					});
				}
			});		
		}
		
		//cargamos el combo con las formas de participacion del involucrado
		function cargaComboFormaParticipacionRDPD()
		{
			$("#cbxFormasParticipacionRDPD").empty();
			$('#cbxFormasParticipacionRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModoParticipacionDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxFormasParticipacionRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}
		
		/*
		*Funcion para cargar el combo con los probables responsables del Expediente
		*/
		function cargaComboPRExpedientesRDPD()
		{
			$('#cbxProbableResponsableExpRDPD').empty();
			$('#cbxProbableResponsableExpRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('involucradoDTO').each(function(){
						var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('apellidoMaterno').text()+" ";
						$('#cbxProbableResponsableExpRDPD').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
					});
				}
			});		
		}
		
		//cargamos el combo con los delitos del expediente
		function cargaComboDelitosAAsociarRDPD()
		{			
			$("#btnRelacionarRDPD").hide();
			$("#btnAnulaRelacionDelDel").hide();
			

			$('#cbxDelitosRDPD').empty();
			//seteamos el listener para el boton de relacionar los delitos
			$("#btnRelacionarRDPD").click(abrePopupRelacionarDelitosPRRDPD);
			$("#trGridDelitosPRRDPD").hide();
			
			$( "#dialogTres:ui-dialog" ).dialog( "destroy" );			
			$('#cbxDelitosRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaDelitoPorExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp,
				dataType: 'xml',
				async: false,
				success: function(xml){
					var contaDelitos=0;
					$(xml).find('DelitoDTO').each(function(){
						$('#cbxDelitosRDPD').append('<option value="' + $(this).find('delitoId').text() + '">' + $(this).find('nombreDelito').text() + '</option>');
						contaDelitos++;
					});
					if(contaDelitos==0)
					{
						alertDinamico("Necesita delitos para poderlos relacionar.");
					}
				}
			});	
		}
		var banderaRDPD=0;
		//funcion para mandar consultar los delitos de un probable responsable 
        function consultaDelitosPRRDPD()
        {
        	var idDelito=$("#cbxDelitosRDPD option:selected").val();
        	//revisamos que se haya seleccionado un probable responsable
        	if(idDelito!=-1)
        	{
        		$("#trGridDelitosPRRDPD").show();
        		if(banderaRDPD==0)
        		{
        			var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';
	        		//var params="idPR="+idPR+"&idExpediente="+idExpedienteop;
	        		//cargamos el grid con los delitos del PR
		        	jQuery("#gridCatDelitosRDPD").jqGrid({ 
						url:'<%= request.getContextPath()%>/CargarProbRespsAsociadosAlDelito.do?idDelito='+idDelito +'&idExpediente='+idExpedienteop +'', 
						datatype: "xml",
						colNames:[probableResponsableProp,'Forma de Participación','Víctima','Ids'], 
						colModel:[ 	{name:'ProbableResp',index:'probableresp', width:250}, 
									{name:'FormaParticipacion',index:'formaParticipacion',width:300},
									{name:'Victima',index:'victima',width:250},
									{name:'DelitoPersonaId',index:'idPersonaDelito',width:250,hidden: true}
								],
						//pager: jQuery('#pagerADPD'),
						rowNum:0,
						rowList:[0,0,0],
						autowidth: true,
						caption:"LISTA DE RELACIONES",
						sortname: 'Clave',
						multiselect: true,
						viewrecords: true,
						id: 'divgridIzq',
						sortorder: "desc",
						loadComplete: function(){		        		

							var longitudTabla = jQuery("#gridCatDelitosRDPD").getDataIDs();
							var elementosListaDelitos = longitudTabla.length;							
							
				       		if(elementosListaDelitos>0){ 
						    	if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
						       	  <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
									$("#btnRelacionarRDPD").hide();
					    			$("#btnAnulaRelacionDelDel").hide();
						    	}
						    	else{
									$("#btnAnulaRelacionDelDel").show();
									$("#btnRelacionarRDPD").show();
						    	}
							}
							else{
				    			$("#btnAnulaRelacionDelDel").hide();
							}			       		
						}
					}).navGrid('#pagerADPD',{edit:false,add:false,del:false});
		        	$("#gridCatDelitosRDPD").trigger("reloadGrid");
		        	banderaRDPD=1;
        		}
        		else
        		{
        			jQuery("#gridCatDelitosRDPD").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/CargarProbRespsAsociadosAlDelito.do?idDelito='+idDelito +'&idExpediente='+idExpedienteop +'',datatype: "xml" });
    				$("#gridCatDelitosRDPD").trigger("reloadGrid");
        		}
        	}
        	else
        	{
        		//ocultamos el grid cuando no se selecciono un PR
        		$("#trGridDelitosPRRDPD").hide();
        	}
        }
		
        /*
		*Funcion que hara el llamado a BD para guardar el nuevo delito relacionado
		*por persona a BD y posteriormente agregara un renglon al grid
		*/
		function RelacionarDelitoRDPD()
		{
			var idPR=$("#cbxProbableResponsableExpRDPD option:selected").val();
			var idDelito=$("#cbxDelitosRDPD option:selected").val();
			var idVictima=$("#cbxVictimasExpRDPD option:selected").val();
			var idFormaP=$("#cbxFormasParticipacionRDPD option:selected").val();
			
			var idClasificacion=$("#cbxClasificacionRDPD option:selected").val();
			var idLugar=$("#cbxLugarRDPD option:selected").val();
			var idModalidad=$("#cbxModalidadRDPD option:selected").val();
			var idModus=$("#cbxModusRDPD option:selected").val();
			var idCausa=$("#cbxCausaRDPD option:selected").val();
			
			var esValida = false;
			
			if (muestraCombosDelitoPersona == "0"){
				if (parseInt(idPR)!=-1 && parseInt(idDelito)!=-1 && parseInt(idVictima)!=-1){
					esValida = true;
				}else{
					esValida = false;
				}
			}else{
				if(parseInt(idPR)!=-1 && parseInt(idDelito)!=-1 && parseInt(idVictima)!=-1 && parseInt(idClasificacion)!=-1  ){
					esValida = true;
				}else{
					esValida = false;
				}
			}
			
			if(esValida === true)
			{
				//mandamos guardar a BD
				<%-- $.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/ExisteRelacionProbableResponsableVictimaDelito.do',//verifica si existe la relacion en BD
					data: 'idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP,
					dataType: 'xml',
					async: true,
					success: function(xml){
						if(parseInt($(xml).find('bandera').text())==0)
			    		{ --%>
							//mandamos guardar a BD
							$.ajax({
								type: 'POST',
								url: '<%= request.getContextPath()%>/AsociarDelitoProbableResponsable.do',//guardar a BD la nueva relacion
									data: 'idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP+'&idAsociacion=0'+'&idClasificacion='+idClasificacion+'&idLugar='+idLugar+'&idModalidad='+idModalidad+'&idModus='+idModus+'&idCausa='+idCausa,
								dataType: 'xml',
								async: false,
								success: function(xml){
									if(parseInt($(xml).find('code').text())==0)
						    		{
										$(xml).find('Asociacion').each(function(){
											var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
											alertDinamico("Se asocio exitosamente el "+probableResponsableProp+" al delito.");
											//$('#cbxDelitosExpRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
											
										});
										consultaDelitosPRRDPPV();
										consultaDelitosPRRDPD();
						    		}
								}
							});	
			    		/* }
						else {
							alertDinamico("Ya existe esta relación, favor de verificar.");
						}
					}
				}); */
			}
			else
			{
				var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
				if(muestraCombosDelitoPersona == "0"){
					alertDinamico("Debe seleccionar un "+probableResponsableProp+" y una v&iacute;ctima.");
				}else{
					alertDinamico("Debe seleccionar un "+probableResponsableProp+", una víctima y clasificaci&oacute;n.");
				}
			}
		}
        
		//-----------------------------------------------
		//cargamos el combo de clasificacion del delito
		function cargaComboClasificacionDelitoRDPD()
		{
			$("#cbxClasificacionRDPD").empty();
			$('#cbxClasificacionRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoClasificacionDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxClasificacionRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de lugar del delito
		function cargaComboLugarDelitoRDPD()
		{
			$("#cbxLugarRDPD").empty();
			$('#cbxLugarRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoLugarDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxLugarRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Modalidad del delito
		function cargaComboModalidadDelitoRDPD()
		{
			$("#cbxModalidadRDPD").empty();
			$('#cbxModalidadRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModalidadDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxModalidadRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Modus del delito
		function cargaComboModusDelitoRDPD()
		{
			$("#cbxModusRDPD").empty();
			$('#cbxModusRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModusDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxModusRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Causa del delito
		function cargaComboCausaDelitoRDPD()
		{
			$("#cbxCausaRDPD").empty();
			$('#cbxCausaRDPD').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoCausaDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxCausaRDPD').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}
        
		function anularRelacionDelitoPersona()
        {
			var idsRelacionesSeleccionados = jQuery("#gridCatDelitosRDPD").jqGrid('getGridParam','selarrrow');
			if(idsRelacionesSeleccionados.length>0)
			{
			//Guardamos la relacion
				$.ajax({
					type: 'POST',		
		    		url: '<%= request.getContextPath()%>/anularRelacionDelitoPersona.do?idsRelacionesSeleccionados=' + idsRelacionesSeleccionados + '',
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						if(parseInt($(xml).find('code').text())==0 && parseInt($(xml).find('bandera').text())==1){
							alertDinamico("Se anularon con éxito la(s) relaci\u00F3n(es)");
							consultaDelitosPRRDPD();
							consultaDelitosPRRDPPV();
						}				    		
			    		else
			    			alertDinamico("No se logró anular la(s) relaci\u00F3n(es)")
					}
				});
			}
        }
		
		function ocultaCombosInnecesariosDelitoPersonaPorDelito(){
			var idParametro = '<%=Parametros.MUESTRA_COMBOS_DELITO_PERSONA.ordinal()%>';
	
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarParametro.do',
				data: 'idParametro='+ idParametro, 
				async: false,
				dataType: 'xml',
				success: function(xml){					
					muestraCombosDelitoPersona = $(xml).find('body').find('respuesta').text();
					if(muestraCombosDelitoPersona == "0"){
						$("#trFormasParcticipacionRDPD").hide();
						$("#trModalidadRDPD").hide();
						$("#trModusRDPD").hide();
						$("#trCausaRDPD").hide();
					}
				}
			});
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

	<table border="0"  width="1050px" id="tblRelacionarDelitoPorDelito">
		<tr>
			<td height="20" colspan="4" align="left" >
				Seleccione el Delito : 				
				<select id="cbxDelitosRDPD" onchange="validaSeleccion();">
					<option value="-1" selected="selected">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr id="trGridDelitosPRRDPD">
			<td colspan="3">
				<table id="gridCatDelitosRDPD"></table>
				<div id="pagerADPD"></div>
				<br/>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>			
			<td align="left" valign="top">
				<input type="button" value="Relacionar" id="btnRelacionarRDPD" class="btn_modificar"/>
			</td>
			<td align="left" valign="top">
				<input type="button" id="btnAnulaRelacionDelDel" value="Anular relación Delito - Persona" onclick="anularRelacionDelitoPersona();" class="btn_grande"/>
			</td>
		</tr>
	</table>
	

