<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>	
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	Boolean esCoordinadorAmpGeneral = false;
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

    	var contDelitosGraves=0;
    	var idProbResAviso;
    	var idDetencion;
    	var muestraCombosDelitoPersona;
    	var rolActivo='<%=rolActivo%>';

    	/***** Ejecutamos funciones para setear la pagina*****/
		$(document).ready(function(){		
			
			$("#dialogDos:ui-dialog").dialog( "destroy" );
			
			$("#btnRelacionarRDPPV").hide();
			$("#btnAnulaRelacionDelPer").hide();
			
			if (rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
				<%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
				$("#btnRelacionarRDPPV").hide();
				$("#btnAnulaRelacionDelPer").hide();					
				$("#btnSolicitarDefensor").hide();
			}
			
		});
    	
	
    	function validaSeleccion1(){
    		    		
    		if($("#cbxProbableResponsableRDPPV option:selected").val()!=-1){
    			$("#trGridDelitosPRRDPPV").show();
    			if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
    			   <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
    				$("#btnRelacionarRDPPV").hide();
    			}
    			else{
    				$("#btnRelacionarRDPPV").show();
    			}
    			consultaDelitosPRRDPPV();
    		}
    		else{    			
    			$("#trGridDelitosPRRDPPV").hide();
    			$("#btnRelacionarRDPPV").hide();
    			$("#btnAnulaRelacionDelPer").hide();
    			$("#muestraDatosDetencion1").hide();
    			$("#muestraDatosDetencion2").hide();
    		}
    	}

		/*Funcion para lanzar el popup que relacionara 
		*un delito al probable responsable seleccionado
		*/
		function abrePopupRelacionarDelitosPRRDPPV()
		{
			var bandeaDPP = false;
			
			//cargamos el combo de victimas del expediente
			bandeaDPP = cargaComboVictimasRDPPV();

			if(bandeaDPP == true ){
				return;
			}
			
			//cargamos el combo con los delitos del expediente
			cargaComboDelitosExpedientesRDPPV();
			//cargamos el combo con las formas de participacion del involucrado
			cargaComboFormaParticipacionRDPPV();
			//cargamos el combo de clasificacion delito
			cargaComboClasificacionDelitoRDPPV();
			//cargamos el combo del lugar de delito
			cargaComboLugarDelitoRDPPV();
			//cargamos el combo de modalidad delito
			cargaComboModalidadDelitoRDPPV();
			//cargamos el combo de modus delito
			cargaComboModusDelitoRDPPV();
			//cargamos el combo de causa delito
			cargaComboCausaDelitoRDPPV();
			//cargamos el combo de causa delito
			cargaComboSituacionImputadoRDPPV();
			
			
			if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
				ocultaCombosInnecesariosDelitoPersonaPorPersona();
			}
			
			//generamos el Dialogo
			$( "#dialogDos-confirm" ).dialog({
				resizable: false,
				height:425,
				width:1024,
				modal: true,
				buttons: {
					"Guardar": function() {
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
						RelacionarDelitoRDPPV();
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
		
		/*
		*Funcion que hara el llamado a BD para guardar el nuevo delito relacionado
		*por persona a BD y posteriormente agregara un renglon al grid
		*/
		function RelacionarDelitoRDPPV()
		{
			var idPR=$("#cbxProbableResponsableRDPPV option:selected").val();
			var idDelito=$("#cbxDelitosExpRDPPV option:selected").val();
			var idVictima=$("#cbxVictimasExpRDPPV option:selected").val();
			var idFormaP=$("#cbxFormasParticipacionRDPPV option:selected").val();
			
			var idClasificacion=$("#cbxClasificacionRDPPV option:selected").val();
			var idLugar=$("#cbxLugarRDPPV option:selected").val();
			var idModalidad=$("#cbxModalidadRDPPV option:selected").val();
			var idModus=$("#cbxModusRDPPV option:selected").val();
			var idCausa=$("#cbxCausaRDPPV option:selected").val();
			var idSituacion=$("#cbxSituacionRDPPV option:selected").val();
			
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
			    		{  --%>
							//mandamos guardar a BD
							$.ajax({
								type: 'POST',
								url: '<%= request.getContextPath()%>/AsociarDelitoProbableResponsable.do',//guardar a BD la nueva relacion
								data: 'idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP+'&idAsociacion=0'+'&idClasificacion='+idClasificacion+'&idLugar='+idLugar+'&idModalidad='+idModalidad+'&idModus='+idModus+'&idCausa='+idCausa+'&idSituacion='+idSituacion,
								dataType: 'xml',
								async: true,
								success: function(xml){
									if(parseInt($(xml).find('code').text())==0)
						    		{
										$(xml).find('Asociacion').each(function(){
											var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
											alertDinamico("Se asoció correctamente el delito al "+probableResponsableProp);
											//$('#cbxDelitosExpRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
											
										});
										consultaDelitosPRRDPPV();
										consultaDelitosPRRDPD();
						    		}
								}
							});	
			    		 /* }
						else {
							alertDinamico("Ya existe esta relación. Favor de verificar.");
						 }
					}
				});
				 */
			}
			else
			{
				if(muestraCombosDelitoPersona == "0"){
					alertDinamico("Debe seleccionar un delito y una v&iacute;ctima.");	
				}else{
					alertDinamico("Debe seleccionar un delito, una v&iacute;ctima y clasificaci&oacute;n.");
				}
			}
		}
		
		
		//cargamos el combo con los delitos del expediente
		function cargaComboProbableResponsableRDPPV()
		{
			//idNumeroExpedienteOp
			$('#cbxProbableResponsableRDPPV').empty();
			//seteamos el listener para el combo de PR en relacion de delitos por persona
	       // $("#cbxProbableResponsableRDPPV").change(consultaDelitosPRRDPPV);
	        //seteamos el listener para el boton de relacionar los delitos
			//$("#btnRelacionarRDPPV").click(abrePopupRelacionarDelitosPRRDPPV);
			$("#trGridDelitosPRRDPPV").hide();
			$("#muestraDatosDetencion1").hide();
			$("#muestraDatosDetencion2").hide();
			
			
			$( "#dialogDos:ui-dialog" ).dialog( "destroy" );			
			$('#cbxProbableResponsableRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
				dataType: 'xml',
				async: false,
				success: function(xml){
					var contaProbResps=0;
					$(xml).find('involucradoDTO').each(function(){
						var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').first().text()+" ";
						nombreCompleto+=$(this).find('apellidoPaterno').first().text()+" ";
						nombreCompleto+=$(this).find('apellidoMaterno').first().text()+" ";
						$('#cbxProbableResponsableRDPPV').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
						contaProbResps++;
												
					});
					if(contaProbResps==0)
					{
						var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
						alertDinamico("Necesita un "+probableResponsableProp+" para poder relacionar un delito.");
					}
				}
			});		
		}

		//cargamos el combo con los delitos del expediente
		function cargaComboProbableResponsableRDPPVDelito(){
			//oculta jsp que muestra los datos de detencion
			$('#muestraDatosDetencion1').hide();
			$('#muestraDatosDetencion2').hide();
			//idNumeroExpedienteOp
			$('#cbxProbableResponsableRDPPV').empty();
			
	        //seteamos el listener para el boton de relacionar los delitos
			//$("#btnRelacionarRDPPV").click(abrePopupRelacionarDelitosPRRDPPV);
			$("#trGridDelitosPRRDPPV").hide();
			//$("#muestraDatosDetencion1").hide();
			//$("#muestraDatosDetencion2").hide();
			
			$( "#dialogDos:ui-dialog" ).dialog( "destroy" );			
			$('#cbxProbableResponsableRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
				dataType: 'xml',
				async: false,
				success: function(xml){
					var contaProbResps=0;
					$(xml).find('involucradoDTO').each(function(){
						var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('apellidoMaterno').text()+" ";
						$('#cbxProbableResponsableRDPPV').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
						contaProbResps++;
					});
				}
			});		
		}
		
		//cargamos el combo con los delitos del expediente
		function cargaComboDelitosExpedientesRDPPV()
		{
			$("#cbxDelitosExpRDPPV").empty();
			$('#cbxDelitosExpRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaDelitoPorExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp,
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('DelitoDTO').each(function(){
						$('#cbxDelitosExpRDPPV').append('<option value="' + $(this).find('delitoId').text() + '">' + $(this).find('nombreDelito').text() + '</option>');
					});
				}
			});
		}
		//cargamos el combo con las formas de participacion del involucrado
		function cargaComboFormaParticipacionRDPPV()
		{
			$("#cbxFormasParticipacionRDPPV").empty();
			$('#cbxFormasParticipacionRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModoParticipacionDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxFormasParticipacionRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}
		
		//cargamos el combo de victimas del expediente
		function cargaComboVictimasRDPPV()
		{
			var banderaVictimas = false;
			$("#cbxVictimasExpRDPPV").empty();
			$('#cbxVictimasExpRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=VICTIMA',
				dataType: 'xml',
				async: false,
				success: function(xml){
					var contaProbResps=0;
					$(xml).find('involucradoDTO').each(function(){
						var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('nombresDemograficoDTO').find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('nombresDemograficoDTO').find('apellidoMaterno').text()+" ";
						if(nombreCompleto==null || nombreCompleto=="null" || nombreCompleto=="" || nombreCompleto=="   "){
							nombreCompleto="Anónimo";
						}
						$('#cbxVictimasExpRDPPV').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
						contaProbResps++;
					});
					if(contaProbResps==0)
					{
						alertDinamico("Necesita una víctima para poder relacionar un delito.");
						banderaVictimas = true;
					}
				}
			});		
			return banderaVictimas;
		}
		
		//-----------------------------------------------
		//cargamos el combo de clasificacion del delito
		function cargaComboClasificacionDelitoRDPPV()
		{
			$("#cbxClasificacionRDPPV").empty();
			$('#cbxClasificacionRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoClasificacionDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxClasificacionRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de lugar del delito
		function cargaComboLugarDelitoRDPPV()
		{
			$("#cbxLugarRDPPV").empty();
			$('#cbxLugarRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoLugarDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxLugarRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Modalidad del delito
		function cargaComboModalidadDelitoRDPPV()
		{
			$("#cbxModalidadRDPPV").empty();
			$('#cbxModalidadRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModalidadDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxModalidadRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Modus del delito
		function cargaComboModusDelitoRDPPV()
		{
			$("#cbxModusRDPPV").empty();
			$('#cbxModusRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoModusDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxModusRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}

		//cargamos el combo de Causa del delito
		function cargaComboCausaDelitoRDPPV()
		{
			$("#cbxCausaRDPPV").empty();
			$('#cbxCausaRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoCausaDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxCausaRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}
			
		//cargamos el combo de Situacion Delito
		function cargaComboSituacionImputadoRDPPV()
		{
			$("#cbxSituacionRDPPV").empty();
			$('#cbxSituacionRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarCatalogoSituacionDelito.do',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ModoParticipacionDelito').each(function(){
						$('#cbxSituacionRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						
					});
				}
			});
		}
		
		
		var banderaPV=0;
		//Validar si el PR es detenido con relacion a delitos 
		var esDetenidoConRelacion=0;
        //funcion para mandar consultar los delitos de un probable responsable 
        function consultaDelitosPRRDPPV(){
        	var idPRval=$("#cbxProbableResponsableRDPPV option:selected").val();        
        	//revisamos que se haya seleccionado un probable responsable
        	if(parseInt(idPRval)!=-1)
        	{
        		if(banderaPV==0)
        		{
        		$("#trGridDelitosPRRDPPV").show();
        		//var params="idPR="+idPR+"&idExpediente="+idExpedienteop;
        		//cargamos el grid con los delitos del PR
	        	jQuery("#gridCatDelitosRDPPV").jqGrid({ 
					url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idPRval +'&idExpediente='+idExpedienteop+'', 
					datatype: "xml",
					colNames:['Delito','Forma de Participación','Víctima'], 
					colModel:[ 	{name:'Delito',index:'delito', width:250}, 
								{name:'FormaParticipacion',index:'formaParticipacion',width:550},
								{name:'Victima',index:'victima',width:250},							
								
							],
					//pager: jQuery('#pager1'),
					rowNum:0,
					rowList:[0,0,0],
					autowidth: true,
					caption:"LISTA DE DELITOS",
					sortname: 'Clave',
					multiselect: true,
					viewrecords: true,
					id: 'divgridIzq',
					sortorder: "desc",
					loadComplete: function(){		        		

						var longitudTabla = jQuery("#gridCatDelitosRDPPV").getDataIDs();
						var elementosListaDelitos = longitudTabla.length;

			       		if(elementosListaDelitos>0){ 			       		
				       		if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
				       		   <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
								$("#btnRelacionarRDPPV").hide();
								$("#btnAnulaRelacionDelPer").hide();
								$("#btnSolicitarDefensor").hide();
								esDetenidoConRelacion = 0;
							}
							else{
								$("#btnAnulaRelacionDelPer").show();
								$("#btnRelacionarRDPPV").show();
								$("#btnSolicitarDefensor").show();
								esDetenidoConRelacion = 1;
							}	
						}
			       		else{
							$("#btnAnulaRelacionDelPer").hide();
							esDetenidoConRelacion = 0;
			       		}
			       	}
				}).navGrid('#pager1',{edit:false,add:false,del:false});
	        	$("#gridCatDelitosRDPPV").trigger("reloadGrid");
	        	banderaPV=1;
        		}
        		else
        		{
        			jQuery("#gridCatDelitosRDPPV").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idPRval +'&idExpediente='+idExpedienteop+'',datatype: "xml" });
    				$("#gridCatDelitosRDPPV").trigger("reloadGrid");
    				$("#trGridDelitosPRRDPPV").show();
        		}
        	}
        	else
        	{
        		//ocultamos el grid cuando no se selecciono un PR
        		$("#trGridDelitosPRRDPPV").hide();
        		$('#muestraDatosDetencion1').hide();
        		$('#muestraDatosDetencion2').hide();
        	}
        	consultaDetencion(idPRval);
        }

        function consultaDetencion(idPRval){
        	var avisoDetencion	= "";
        	var detImp			= false;
        	var fechaDet 		= "";
        	var fechaRecep		= "";
        	$('#muestraDatosDetencion1').hide();
        	$('#muestraDatosDetencion2').hide();
        	idProbResAviso = idPRval;
        		$.ajax({
        			type: 'POST',
    		    	url:  '<%= request.getContextPath()%>/consultarInvolucrado.do',
    		    	data: 'idInvolucrado='+idProbResAviso,
    		    	dataType: 'xml',
    		    	async: false,
    		    	success: function(xml){
				   		var arrayIds = jQuery("#gridCatDelitosRDPPV").getDataIDs();
						if(arrayIds.length>0){
							if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
								<%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
								$('#btnSolicitarDefensor').hide();
							}
							else{
								$('#btnSolicitarDefensor').show();
							}
						}
						
						idDetencion = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('detencionId').first().text();
						if( $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('avisoDetencion').find('documentoId').text() == '' ){
							detImp	= $(xml).find('involucradoDTO').find('esDetenido').first().text();
							fechaDet	= $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('strFechaInicioDetencion').first().text()
							fechaRecep	= $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('strFechaFinDetencion').first().text();
							avisoDetencion = false;
						}
						else{ avisoDetencion = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('avisoDetencion').find('documentoId').first().text()!= ""; }
						 
						//$(xml).find('involucradoDTO').find('nombresDemograficoDTO').find('').
	    		    	///if($(xml).find('involucradoDTO').find('esDetenido').first().text() && esDetenidoConRelacion){
	    		    	if(esDetenidoConRelacion){
	    		    		$('#btnSolicitarDefensor').attr("enabled","");
	    		    		pintaDatosTiempoLapso(xml);
	    		    		bloqueaCamposTiempoLapso(1);
	    		    		bloqueaCamposTiempoLapso(0);
	    		    		$('#muestraDatosDetencion1').show();  
	    		    		$('#muestraDatosDetencion2').show();
	    		    		if(avisoDetencion){
	        		    		$('#btnSolicitarDefensor').attr("enabled","enabled");
	        		    		var fecha = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('avisoDetencion').find('fechaCreacion').text();
	        		    		var strfecha = obtenerFecha(fecha);   
	        		    		var strhora = obtenerHora(fecha); 	
	        		    		$('#fechaSolicitud').show();
	        		    		$('#horaSolicitud').show(); 
	        		    		$('#fechaSolicitudDefensor').val(strfecha);
	        		    		$('#horaSolicitudDefensor').val(strhora);
	         		    	}
	    			    }
    		    	}
    		   });
	    		if(avisoDetencion==false){
	    			$('#fechaSolicitud').show();
		    		$('#horaSolicitud').show();
		    		if(detImp){
			    		$('#idFechaDateLapso').val(fechaDet.substring(0,10));
			    		$('#idHoraDateLapsoInicio').val(fechaDet.substring(11,16));
			    		$('#idFechaDateLapso2').val(fechaRecep.substring(0,10));
			    		$('#idHoraDateLapsoFin').val(fechaRecep.substring(11,16));
			    		$('#fechaSolicitudDefensor').val("");
			    		$('#horaSolicitudDefensor').val("");
		    		}
	    		}
	    		
	    		$("#idFechaDateLapso").attr('disabled','disabled');
	    		$("#idHoraDateLapsoInicio").attr('disabled','disabled');
	    		$("#idFechaDateLapso2").attr('disabled','disabled');
	    		$("#idHoraDateLapsoFin").attr('disabled','disabled');
	    		$("#fechaSolicitudDefensor").attr('disabled','disabled');
	    		$("#horaSolicitudDefensor").attr('disabled','disabled');
	    		$('#muestraDatosDetencion1').show();  
	    		$('#muestraDatosDetencion2').show();
       	}
    			
        
        function anularRelacionDelitoPersonaPersona()
        {
			var idsRelacionesSeleccionados = jQuery("#gridCatDelitosRDPPV").jqGrid('getGridParam','selarrrow');
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
							consultaDelitosPRRDPPV();
							consultaDelitosPRRDPD();
						}				    		
			    		else
			    			alertDinamico("No se logró anular la(s) relaci\u00F3n(es)");
					}
				});
			}
        }

        function enviaAvisoDetencion(){
			var params = '';
			params += 'idIndividuo='+idProbResAviso;
			params += '&numeroExpediente='+numeroExpediente;
			params += '&idDetencion='+ idDetencion;
			//Datos fechas
			datosPestania = recuperaDatosTiempoLapso();
			params += datosPestania;
			$.ajax({								
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/enviarAvisosDetencion.do',
		    	  data: params,				
		    	  dataType: 'xml',
		    	  success: function(xml){
		    		  	alertDinamico("Defensor solicitado");
						$('#btnSolicitarDefensor').attr("enabled","enabled");
		    	  }
		    	});
		}
        
        function enviaAvisoSinDetencion(){
			var params = '';
			params += 'idIndividuo='+idProbResAviso;
			params += '&numeroExpediente='+numeroExpediente;
			params += '&idDetencion='+ idDetencion;
			//Datos fechas
			datosPestania = recuperaDatosTiempoLapso();
			params += datosPestania;
			$.ajax({								
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/enviarAvisosSinDetencion.do',
		    	  data: params,				
		    	  dataType: 'xml',
		    	  success: function(xml){
		    		  	alertDinamico("Defensor solicitado");
						$('#btnSolicitarDefensor').attr("enabled","enabled");
		    	  }
		    	});
		}
        
        function consultarAvisosDetencion(){
        	
    		var idPRval=$("#cbxProbableResponsableRDPPV option:selected").val();
			var params = '';
			params += 'idIndividuo='+idProbResAviso;
			params += '&numeroExpediente='+numeroExpediente;
			params += '&idDetencion='+ idDetencion;
			
			if(parseInt(idPRval)!=-1){
				
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/consultarDenunciaDetencion.do',
			    	  data: params,				
			    	  dataType: 'xml',
			    	  success: function(xml){
			    		  if($(xml).find('aviso').text() == "true"){
				    			  $.ajax({
				    				  type: 'POST',
							    	  url: '<%= request.getContextPath()%>/consultarAvisosDetencion.do',
							    	  data: params,
							    	  dataType: 'xml',
							    	  success: function(xml){
							    		  if(idDetencion != ''){
							    				if($(xml).find('aviso').text() == "true"){
							    						customConfirm("Ya cuenta con una solicitud, desea enviar una nueva?", "Aviso", enviaAvisoDetencion);
							    		  			}else{
							    		  				customConfirm("Se realizará una solicitud de defensor con Detenido", "Aviso", enviaAvisoDetencion);
							    		  			}
							    		  		}else{
							    		  			if($(xml).find('aviso').text() == "true"){
							    		  				customConfirm("Ya cuenta con una solicitud, desea enviar una nueva?", "Aviso", enviaAvisoSinDetencion);
							    		  			}else{
							    		  				customConfirm("Se realizará una solicitud de defensor para persona Citada", "Aviso", enviaAvisoSinDetencion);
							    		  			}
							    		  		}
										$('#btnSolicitarDefensor').attr("enabled","enabled");
							    		
							    	  }
							    	});
			    		  }
			    		  else{
			    			  alertDinamico("Se requiere generar la denuncia");
			    		  }
			    		  
						$('#btnSolicitarDefensor').attr("enabled","enabled");
			    		
			    	  }
			    	});
				
			}
			else 
				alertDinamico("Favor de seleccionar un Probable Responsable");
			
        }
        
		function ocultaCombosInnecesariosDelitoPersonaPorPersona(){
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
						$("#trFormasParticipacionRDPPV").hide();
						$("#trModalidadRDPPV").hide();
						$("#trModusRDPPV").hide();
						$("#trCausaRDPPV").hide();
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

	<table border="0"  width="1050px" id="tblRelacionarDelitoPorPersona">
		<tr>
			<td height="20" colspan="5" align="left" >
				<!-- <input type="button" id="btnGuardarDelitosAg" value="Guardar" onclick="guardarDelitosAgraviadosExp();"/> -->				
					Seleccione el Imputado
					<!--<bean:message key="selProbableResponsable"/>: --> 
				<select id="cbxProbableResponsableRDPPV" onchange="validaSeleccion1();">
					<option value="-1" selected="selected">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr>
			<td id="trGridDelitosPRRDPPV" colspan="3">
				<table id="gridCatDelitosRDPPV"></table>
				<div id="pager1"></div>
				<br/>				
			</td>
			<!-- <td id="muestraDatosDetencion" style="display: none"> -->
		  		<td id="muestraDatosDetencion1" align="left" ><jsp:include page="ingresarTiempoLapsoDetencion.jsp"></jsp:include></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>	
			<td align="left" valign="top">
				<input type="button" value="Relacionar" id="btnRelacionarRDPPV" onclick="abrePopupRelacionarDelitosPRRDPPV();" class="btn_modificar"/>
			</td>
			<td align="left" valign="top">
				<input type="button" id="btnAnulaRelacionDelPer" value="Anular relación Delito - Persona" onclick="anularRelacionDelitoPersonaPersona();" class="btn_grande"/>
			</td>
			<td>&nbsp;</td>
			<td id="muestraDatosDetencion2" align="center" >
				<input type="button" id="btnSolicitarDefensor" value="Solicitar Defensor" style="display: none" class="btn_mediano"/>
			</td>
		</tr>
	</table>

	<script type="text/javascript">
	 $('#btnSolicitarDefensor').click(consultarAvisosDetencion); 	 
	</script>

