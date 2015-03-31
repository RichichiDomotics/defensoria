<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.EstatusEvidencia" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.TiposEslabon" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript">
		//variables globales
		var idEvidencia = parent.idParaConsultaEvidencia;
		var idAlmacen = parent.idAlmacenParaGestionarAlmacen;
		var numeroGeneralCaso = "";
		var cadenaDeCustodiaId = 0;
		var estatusEvidencia = 0;
		var tipoObjeto = 0;
		var idObjeto = 0;
		var idWindowObEvidencia=1;
		var idTipoEslabonPorDefault=0;
		var contextoPagina = "${pageContext.request.contextPath}";
		
		//variables para setear las fechas y horas maximas
		var fechaServidor="";
		var fechaMax="";
		var timeMax="";
		//Variable la cual permite adjuntar un documento asociado a un eslabon
		var idEslabonParaAdjuntarDocumento = 0;
		
		//Permite filtrar el combo en base al rol de los usuario
		var visualizaTipoEslabon = '<%= request.getParameter("visualizaTipoEslabon")%>';
		var tieneSolicitudPorAtender = false;
		
	$(document).ready(function() {
		//Inician Funciones para el registro de eslabon
		cargaCatalogoTipoEslabon();
		//Se configura un valor por default al Tipo de eslabon
		//$("#seccionTipoEslabon").hide();
		
		
		
		//vamos por la fecha actual al servidor
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServerHechos(fechaServidor);
		timeMax=getHoraMaximaServer(fechaServidor);
		
		
		$("#btnRegEslbnCadCus").click(registrarEslabonCadenaCustodia);
		
		$("#txtFechaRecepcionEslbn").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#txtFechaEntregaEslbn").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		//Calendarios para el lapso de prestamos en la cadena de custodia
		$("#txtFechaPrestamoEslbn").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true,
			maxDate: fechaMax,
		    onSelect: function(date) {
				//setter fecha minima al segunda calendario
				$( "#txtFechaPrestamoRecEslbn" ).datepicker( "option", "minDate", date );
			}	
		});
		
		$("#txtFechaPrestamoRecEslbn").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			minDate: fechaMax,
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		
		//llenamos los campos de quien recibe
		//llenaCamposQuienRecibe();
		$("#btnAdjuntarDocumento").attr("disabled","disabled");
		
		if(visualizaTipoEslabon != 'undefined' && visualizaTipoEslabon != null && visualizaTipoEslabon == "amp"){
			//Se agrega una clase de estilo al boton
			$("#btnRegEslbnCadCus").removeClass("btn_Generico");
			$("#btnRegEslbnCadCus").addClass("btn_grande");
			$("#btnRegEslbnCadCus").val("Solicitar extensión / cancelación de prestamo");
			$("#btnAdjuntarDocumento").hide();
			configuraValorTipoEslabon(<%= TiposEslabon.SOLICITUD.getValorId() %>);
			$("#seccionTipoEslabon").hide();
			$("#seccionInfoDeEvidencia").hide();
			//indica que la solicitut tiene una solicitud por atender
			tieneSolicitudPorAtender = true;
		}
		
		$(function(){
			   $('#txtHoraEntregaEslbn,#txtHoraPrestamoEslbn,#txtHoraRecepcionEslbn,#txtHoraPrestamoRepEslbn').timeEntry({timeSteps:[1,1,0],ampmPrefix: ' '});
			 });
		
	});
	
	function configuraValorTipoEslabon(idTipoEslabon){
		$("#cbxTipoEslabon option[value='"+idTipoEslabon+"']").attr("selected","selected");
	}
	
    function cargaCatalogoTipoEslabon()
    {
   	$('#cbxTipoEslabon').empty();
   	$('#cbxTipoEslabon').append('<option value="-1" selected="selected">-Seleccione-</option>');
   	
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTiposEslabon.do',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catEslabon').each(function(){
					
					
					//Si es el rol de agentemp o coordinadoramp
					
					if(visualizaTipoEslabon != null && visualizaTipoEslabon == "amp"){
						if($(this).find('clave').text() == '<%= TiposEslabon.ANALISIS_DE_EVIDENCIA.getValorId() %>'  ||
								$(this).find('clave').text() == '<%= TiposEslabon.CAMBIO_DE_ALMACEN.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.REGISTRO.getValorId() %>'  ||
								$(this).find('clave').text() == '<%= TiposEslabon.SOLICITUD.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.RESGUARDO.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.ENTREGA_RECEPCION.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.EVIDENCIA_ANALIZADA.getValorId() %>'){
							$('#cbxTipoEslabon').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
							$('#cbxTipoEslabonR').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');							
						}
					}else{
						//Si es el rol de almacenista
						if($(this).find('clave').text() == '<%= TiposEslabon.BAJA.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.ENTRADA.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.SALIDA_A_PRESTAMO.getValorId() %>' ||
								$(this).find('clave').text() == '<%= TiposEslabon.OTROS.getValorId() %>'){
							$('#cbxTipoEslabon').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						}
					}
					});
			}
		});
    }

    
	/*
	*Funcion que simula la funcion TRIM de otros lenguajes 
	*
	*/
	function trim(myString)
	{
		return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
	}
	

	function registrarEslabonCadenaCustodia()
	{
		if(validaCamposRegEslabonDeCadena())
		{
			
			var parametros=recuperaParametrosRegEslabonCadCus();
			parametros+="&generarDocumentoMovimento=true";
			
			parametros += "&tieneSolicitudPorAtender=" + tieneSolicitudPorAtender;
			
			//mandamos a llamar al action que guardara el eslabon de la cadena de custodia
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/guardarEslabonCadenaDeCustodia.do',
				data: parametros,
				dataType: 'xml',
				success: function(xml){
						
						if(parseInt($(xml).find('code').text())==0)
						{	
							var bandera=$(xml).find('exito').find('bandera').text();
							if(parseInt(bandera)==0 || $(xml).find('body').text() == "null")
							{
								customAlert("Ocurrió un error al guardar el eslabón de la cadena de custodia.");
							}
							else
							{
					        								
					        	customAlert("Se guardó exitosamente el eslabón de la cadena de custodia.");
					        	
					        	
								if (typeof consultarEslabonesXidEvidencia == 'function') {
									consultarEslabonesXidEvidencia(idEvidencia);
						        }

								//Se acualiza el estatus de la solicitud
								//Consulta el primer eslabón de tipo Registro
								switch(parseInt($('#cbxTipoEslabon option:selected').val())){
									case <%= TiposEslabon.REGISTRO.getValorId() %>: 
										 actualizaEstatusDeEvidencia('<%= EstatusEvidencia.NUEVA.getValorId() %>');
								     break;
									case <%= TiposEslabon.SALIDA_A_PRESTAMO.getValorId() %>: 
										 actualizaEstatusDeEvidencia('<%= EstatusEvidencia.EN_PRESTAMO.getValorId() %>');
									     break;
									case <%= TiposEslabon.ENTRADA.getValorId() %>: 
										 actualizaEstatusDeEvidencia('<%= EstatusEvidencia.EN_ALMACEN.getValorId() %>');
									     break;
									case <%= TiposEslabon.BAJA.getValorId() %>:
											actualizaEstatusDeEvidencia($('#cbxEstatusEvidencia option:selected').val());
									     break;
									case <%= TiposEslabon.OTROS.getValorId() %>: 
											actualizaEstatusDeEvidencia($('#cbxEstatusEvidencia option:selected').val());
									     break;
								}						
								
								if (typeof window.parent.gridEvidenciaAlmacen == 'function') {
						               window.parent.gridEvidenciaAlmacen(estatusEvidenciaDeLaUltimaConsulta, banderaSolicitudUltimaConsulta);
						        }
																
								idEslabonParaAdjuntarDocumento = parseInt(bandera);
								//Una vez registrado el eslabon ya permitira adjuntar documentos al eslabon
								//limpiaCamposEslabonCadCus();
								$("#btnAdjuntarDocumento").attr("disabled","");
								$("#btnRegEslbnCadCus").attr("disabled","disabled");								
							}
						}
						else
						{
							cadenaCustodia="";
							customAlert("Ocurrió un error al guardar el eslabón de la cadena de custodia.");
						}
				}
			});
		}
	}
	
	function validaCamposRegEslabonDeCadena()
	{
		//abriremos el popup del tipo de objeto seleccionado 
		var tipoEslabon= $('#cbxTipoEslabon option:selected').val();
		//revisamos la opcion seleccionada
		if(parseInt(tipoEslabon)==-1)
		{
			customAlert("Debe seleccionar el tipo de eslabón de entrega para poder continuar");
			return false;
		}
		//revisamos el nombre de la persona que entrega
		if($.trim($("#txtPersonaEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el nombre de la persona que entrega");
			return false;
		}
		
		//revisamos el apellido paterno de quien recibe
		if($.trim($("#txtPersonaApPatEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el apellido paterno de la persona que entrega");
			return false;
		}
		
    	//revisamos el apellido materno de quien recibe
		if($.trim($("#txtPersonaApMatEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el apellido materno de la persona que entrega");
			return false;
		}
		
		
		//revisamos la fecha de entrega
		if($.trim($("#txtFechaEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la fecha de la entrega");
			return false;
		}
		//revisamos la hora de entrega
		if($.trim($("#txtHoraEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la hora de la entrega");
			return false;
		}
		//revisamos la institucon de entrega
		if($.trim($("#txtInstitucionEslbnEntrega").val()).length==0)
    	{
			customAlert("Debe proporcionar la institución de entrega");
			return false;
    	}
		//revisamos el nombre de quien recibe
		if($.trim($("#txtPersonaRecibeEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el nombre de la persona que recibe");
			return false;
		}
		
    	//revisamos el apellido paterno de quien recibe
		if($.trim($("#txtPersonaApPatRecibeEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el apellido paterno de la persona que recibe");
			return false;
		}
		
    	//revisamos el apellido materno de quien recibe
		if($.trim($("#txtPersonaApMatRecibeEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar el apellido materno de la persona que recibe");
			return false;
		}
        	
		//revisamos la fecha de recepcion
		if($.trim($("#txtFechaRecepcionEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la fecha de la recepción");
			return false;
		}
		//revisamos la hora de recpcion
		if($.trim($("#txtHoraRecepcionEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la hora de la recepción");
			return false;
		}
		//revisamos la institucon de recepcion
		if($.trim($("#txtInstitucionEslbnRecepcion").val()).length==0)
    	{
			customAlert("Debe proporcionar la institución de recepción");
			return false;
    	}
		
		//Validaciones para los campos de fecha de prestamo
		//revisamos la fecha de inicio del periodo de prestamo
		if($.trim($("#txtFechaPrestamoEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la fecha de inicio del periodo de préstamo");
			return false;
		}
		//revisamos la hora de inicio del periodo de prestamo
		if($.trim($("#txtHoraEntregaEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la hora de inicio del periodo de préstamo");
			return false;
		}
		//revisamos la fecha de fin del periodo de prestamo
		if($.trim($("#txtFechaPrestamoRecEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la fecha final del periodo de préstamo");
			return false;
		}
		//revisamos la hora de fin del periodo de prestamo
		if($.trim($("#txtHoraPrestamoRepEslbn").val()).length==0)
		{
			customAlert("Debe proporcionar la hora del final del periodo de préstamo");
			return false;
		}
		
		switch(parseInt($('#cbxTipoEslabon option:selected').val())){		
			case <%= TiposEslabon.BAJA.getValorId() %>: 
				//Dado que puede haber varias estatus
				if($('#cbxEstatusEvidencia option:selected').val() == "-1"){
					customAlert("Debe proporcionar el estatus de la evidencia")
					return false;
				}
			     break;
			     
			case <%= TiposEslabon.OTROS.getValorId() %>: 
				//Dado que puede haber varias estatus
				if($('#cbxEstatusEvidencia option:selected').val() == "-1"){
					customAlert("Debe proporcionar el estatus de la evidencia")
					return false;
				}
			     break;
		}			
		
		return true;
	}
	
	
	/*
	*Funcion para limpiar los campos de captura de un eslabon de cadena de custodia
	*Los campos se limpian despues de agregar un eslabon a la cadena
	*/
	function limpiaCamposEslabonCadCus()
	{
		//Campos de entrega del eslabon
		$('#cbxTipoEslabon').find("option[value='-1']").attr("selected","selected");
		$("#txtPersonaEntregaEslbn").val("");
		$("#txtPersonaApPatEntregaEslbn").val("");
		$("#txtPersonaApMatEntregaEslbn").val("");
		$("#txtInstitucionEslbnEntrega").val("");
		$("#txtObsEntregaEslbn").val("");
		$("#txtFechaEntregaEslbn").val("");
		$("#txtHoraEntregaEslbn").timeEntry('destroy');
		$("#txtHoraEntregaEslbn").timeEntry({show24Hours: false,defaultTime: "7:00 AM",maxTime: null});
						
		
		
		//Campos de recepcion del eslabon
		$("#txtPersonaRecibeEslbn").val("");
		$("#txtPersonaApPatRecibeEslbn").val("");
		$("#txtPersonaApMatRecibeEslbn").val("");
		$("#txtInstitucionEslbnRecepcion").val("");
		$("#txtObsRecepcionEslbn").val("");
		$("#txtFechaRecepcionEslbn").val("");
		$("#txtHoraRecepcionEslbn").timeEntry('destroy');
		$("#txtHoraRecepcionEslbn").timeEntry({show24Hours: false,defaultTime: "7:00 AM",maxTime: null});
		
		$("#txtFechaPrestamoEslbn").val("");
		$("#txtHoraPrestamoEslbn").timeEntry('destroy');
		$("#txtHoraPrestamoEslbn").timeEntry({show24Hours: false,defaultTime: "7:00 AM",maxTime: null});
		
		$("#txtFechaPrestamoRecEslbn").val("");
		
		$("#txtHoraPrestamoRepEslbn").timeEntry('destroy');
		$("#txtHoraPrestamoRepEslbn").timeEntry({show24Hours: false,defaultTime: "7:00 AM",maxTime: null});
		
		//quitamos la seleccion de los combobox
		$(".chkObjEvdXCadCus").attr('checked', false);

		//Informacion de la evidencia
		$("#texUbicacion").val("");
		$("#txtPosicion").val("");


	}
	

	
	function recuperaParametrosRegEslabonCadCus()
	{
		var params="";
		//datos de entrega
		params+="tipoEslabon="+$('#cbxTipoEslabon option:selected').val();
		params+="&idsEvidencias="+idEvidencia;
		params+="&nombrePersonaEntrega="+$("#txtPersonaEntregaEslbn").val()+"-"+ $("#txtPersonaApPatEntregaEslbn").val() +"-"+ $("#txtPersonaApMatEntregaEslbn").val();
		params+="&fechaEntrega="+$("#txtFechaEntregaEslbn").val();
		params+="&horaEntrega="+$("#txtHoraEntregaEslbn").val();
		
		params+="&posicionEvidencia="+$("#txtPosicion").val();
		params+="&ubicacionFisica="+$("#texUbicacion").val();
		
		if($.trim($("#txtObsEntregaEslbn").val()).length>0)
		{
			params+="&obsEntrega="+$("#txtObsEntregaEslbn").val();
		}
		
		if($.trim($("#txtTiempoEntregaEslbn").val()).length>0)
    	{
			params+="&tiempoPrestamo="+$("#txtTiempoEntregaEslbn").val();
    	}
		//datos de recepcion 
		params+="&nombrePersonaRecepcion="+$("#txtPersonaRecibeEslbn").val()+"-"+ $("#txtPersonaApPatRecibeEslbn").val() +"-"+ $("#txtPersonaApMatRecibeEslbn").val();
		params+="&fechaRecepcion="+$("#txtFechaRecepcionEslbn").val();
		params+="&horaRecepcion="+$("#txtHoraRecepcionEslbn").val();
		if($.trim($("#txtObsRecepcionEslbn").val()).length>0)
		{
			params+="&obsRecepcion="+$("#txtObsRecepcionEslbn").val();
		}
		
		
		if($.trim($("#txtInstitucionEslbnEntrega").val()).length>0)
    	{
			params+="&institucionEnt="+$("#txtInstitucionEslbnEntrega").val();
    	}
		
		if($.trim($("#txtInstitucionEslbnRecepcion").val()).length>0)
    	{
			params+="&institucionRec="+$("#txtInstitucionEslbnRecepcion").val();
    	}

		
		//params para el lapso de prestamo
		params+="&fechaIniPres="+$("#txtFechaPrestamoEslbn").val();
		params+="&horaIniPres="+$("#txtHoraPrestamoEslbn").val();
		params+="&fechaFinPres="+$("#txtFechaPrestamoRecEslbn").val();
		params+="&horaFinPres="+$("#txtHoraPrestamoRepEslbn").val();

		
		//customAlert(params);
		return params;
	}				
	
	
	
	/*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
	*/
	function consultaFechaHoraMaximaServer()
	{
		var fecha="";
		   $.ajax({
			     type: 'POST',
			     url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
				 dataType: 'xml',
				 async: false,
				 success: function(xml){
					fecha= $(xml).find('fecha').text();
				  }
				});
		return fecha;
	}
	
	/*
	 * Funcion para regresar la fecha maxima obtenida desde el servidor
	 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
	 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
	 */
	function getFechaMaximaServerHechos(fechaCompleta)
	{
		var arrFechaHora=fechaCompleta.split(" ");
		var digitosFecha=arrFechaHora[0].split("-");
		return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
	}

	/*
	 * Funcion para regresar la hora maxima obtenida desde el servidor
	 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
	 * regresa una cadena con la hora en el formato : HH:MMAM o HH:MMPM
	 */
	function getHoraMaximaServer(fechaCompleta)
	{
		var arrFechaHora=fechaCompleta;
		var horaC=arrFechaHora[1].substring(0,5);
		var horaD=horaC.substring(0,2);
		var horaCorrecta="";
		if(parseInt(horaD)<13)
		{	
			horaCorrecta=horaC+'AM';
		}
		else
		{
			horaCorrecta=horaC+'PM';
		}
		return horaCorrecta;
	}
	
	function llenaCamposQuienRecibe()
	{
		//hacemos la consulta del usuario que esta firmado
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaFuncionarioFirmado.do',
			dataType: 'xml',
			success: function(xml){
					//revisamos la respuesta enviada
					if(parseInt($(xml).find('code').text())==0)
					{
						var bandera=$(xml).find('exito').find('bandera').text();
						if(bandera=='')
						{
							//removemos el funcionario en jefe
							var xmlEdit = $(xml);
							xmlEdit.find('funcionarioDTO').find('funcionarioJefe').remove();
							
							//si entra es xq todo fue exitoso
							$("#txtPersonaRecibeEslbn,#txtPersonaApPatRecibeEslbn,#txtPersonaApMatRecibeEslbn").empty();
							
							$("#txtPersonaRecibeEslbn").val($(xmlEdit).find('nombreFuncionario').text());
							$("#txtPersonaApPatRecibeEslbn").val($(xmlEdit).find('apellidoPaternoFuncionario').text());
							$("#txtPersonaApMatRecibeEslbn").val($(xmlEdit).find('apellidoMaternoFuncionario').text());
						}
					}
					else
					{
						customAlert("Ocurrió un error al recuperar su información");
					}
			}
		});
	}
	
	
	function abreVentanaAdjuntarDocumentoAEslabon(){
		var extensionesPermitidas = ".pdf,.jpg";
		$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
		$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idEslabon='+idEslabonParaAdjuntarDocumento+'" width="450" height="200" />');
	}
	
	function actualizaComboDeEstatus(){
		$('#idSeccionEstatusEvidencia').hide();
		$('#cbxEstatusEvidencia').empty();
	   	$('#cbxEstatusEvidencia').append('<option value="-1" selected="selected">-Seleccione-</option>');
				
		switch(parseInt($('#cbxTipoEslabon option:selected').val())){
			case <%= TiposEslabon.BAJA.getValorId() %>: 
			   	$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.BAJA.getValorId() %>  + '">Baja</option>');
			   	$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.DESTRUIDA.getValorId() %>  + '">Destruida</option>');
			   	$('#idSeccionEstatusEvidencia').show();
			     break;
			case <%= TiposEslabon.OTROS.getValorId() %>: 
			   	$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.BAJA.getValorId() %>  + '">Baja</option>');
		   		$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.DESTRUIDA.getValorId() %>  + '">Destruida</option>');
		   		$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.EN_ALMACEN.getValorId() %>  + '">En almacén</option>');
			   	$('#cbxEstatusEvidencia').append('<option value="' +   <%= EstatusEvidencia.EN_PRESTAMO.getValorId() %>  + '">En prestamo</option>');
			   	$('#idSeccionEstatusEvidencia').show();
			     break;    
		}	
		parseInt($('#cbxTipoEslabon option:selected').val())
	}

	
	</script>

	<div id="divRegistrarEslabon">
	  <table width="900px" class="tablaInsercion" id="tabalaInsercionEslabones">
        		<tr id="seccionTipoEslabon">
        			<td>
        				Tipo de Eslab&oacute;n : 
        				<select id="cbxTipoEslabon" onchange="actualizaComboDeEstatus()">
        					<option selected="selected" value="-1">-Seleccione-</option>
        				</select> 
        			</td>
           			<td id="idSeccionEstatusEvidencia" style="display: none">
        				Estatus de evidencia: 
        				<select id="cbxEstatusEvidencia">
        					<option selected="selected" value="-1">-Seleccione-</option>
        				</select> 
        			</td>
        			
        		</tr>
        		<tr>
        			<td>
        				<table width="50%">
        					<tr>
        						<td colspan="2" align="center">
        							<b>DATOS DE ENTREGA</b>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Nombre: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaEntregaEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Ap. Paterno: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaApPatEntregaEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Ap. Materno: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaApMatEntregaEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Fecha: 
        						</td>
        						<td>
        							<input type="text" id="txtFechaEntregaEslbn" maxlength="10" disabled="disabled"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Hora: 
        						</td>
        						<td>
        							<input type="text" id="txtHoraEntregaEslbn" class="timeRange" value="7:00 AM" size="10"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Instituci&oacute;n: 
        						</td>
        						<td>
        							<textarea rows="3" cols="45" id="txtInstitucionEslbnEntrega" maxlength="99"></textarea>
        						</td>
        					</tr>
        					
        					<tr>
        						<td colspan="2" align="left">
        							<b>Periodo de pr&eacute;stamo</b> 
        						</td>
        						<td>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Fecha incial: 
        						</td>
        						<td>
        							<input type="text" id="txtFechaPrestamoEslbn" maxlength="10" disabled="disabled"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Hora: 
        						</td>
        						<td>
        							<input type="text" id="txtHoraPrestamoEslbn" class="timeRange" value="7:00 AM" size="10"/>
        						</td>
        					</tr>
        					
        					<!-- <tr>
        						<td>
        							Observaciones: 
        						</td>
        						<td>
        							<textarea rows="4" cols="45" id="txtObsEntregaEslbn"></textarea>
        						</td>
        					</tr>-->
        				</table>
        			</td>
        			<td>
        				<table width="50%">
        					<tr>
        						<td colspan="2" align="center">
        							<b>DATOS DE RECEPCI&Oacute;N</b>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Nombre: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaRecibeEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Ap. Paterno: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaApPatRecibeEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Ap. Materno: 
        						</td>
        						<td>
        							<input type="text" id="txtPersonaApMatRecibeEslbn" maxlength="20" style="width: 150px;"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Fecha: 
        						</td>
        						<td>
        							<input type="text" id="txtFechaRecepcionEslbn" maxlength="10" disabled="disabled"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Hora: 
        						</td>
        						<td>
        							<input type="text" id="txtHoraRecepcionEslbn" class="timeRange" value="7:00 AM" size="10"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Instituci&oacute;n: 
        						</td>
        						<td>
        							<textarea rows="3" cols="45" id="txtInstitucionEslbnRecepcion" maxlength="99"></textarea>        							
        						</td>
        					</tr>
        					
        					
        					<tr>
        						<td>
        							 &nbsp;&nbsp;&nbsp;
        						</td>
        						<td>
        							&nbsp;&nbsp;&nbsp;
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Fecha final: 
        						</td>
        						<td>
        							<input type="text" id="txtFechaPrestamoRecEslbn" maxlength="10" disabled="disabled"/>
        						</td>
        					</tr>
        					<tr>
        						<td>
        							Hora: 
        						</td>
        						<td>
        							<input type="text" id="txtHoraPrestamoRepEslbn" class="timeRange" value="7:00 AM" size="10"/>
        						</td>
        					</tr>
        					
        					
        					<!-- <tr>
        						<td style="visibility: hidden;">
        							Observaciones: 
        						</td>
        						<td style="visibility: hidden;">
        							<textarea rows="4" cols="45" id="txtObsRecepcionEslbn"></textarea>
        						</td>
        					</tr>-->
        				</table>
        			</td>
        		</tr>
        	</table>
        	<table id="tablaObsGeneralesEslbnCadCus">
       		<tr>
       			<td>
							Observaciones: 
				</td>
       		</tr>
       		<tr>
	       		<td>
	       			<textarea rows="4" cols="150" id="txtObsEntregaEslbn" maxlength="299"></textarea>
	       		</td>
       		</tr>
       	</table>
        	       	  
    <br>
		<fieldset id="seccionInfoDeEvidencia">
			<legend>Informaci&oacute;n de la evidencia</legend>
			<table width="518" border="0">
			  <tr>
			    <td width="186">Ubicación Física:</td>
			    <td width="316"><textarea name="texUbicacion" id="texUbicacion" cols="45" rows="4"></textarea></td>
			  </tr>
			  <tr>
			    <td>Posición:</td>
			    <td><input type="text" name="txtPosicion" id="txtPosicion" size="48" /></td>
			  </tr>
			</table>
        </fieldset>	
        
        <br>
		<center>
			<input type="button" value="Limpiar campos" id="btnLimpiarCamposEslabon" class="btn_modificar" onclick="limpiaCamposEslabonCadCus()"/> &nbsp;&nbsp;
			<button value="Adjuntar documento" id="btnAdjuntarDocumento" class="btn_Generico" onclick="abreVentanaAdjuntarDocumentoAEslabon()">Adjuntar documento</button> &nbsp;&nbsp;
			<input type="button" value="Guardar" id="btnRegEslbnCadCus" class="btn_Generico"/>
		</center>
			
	</div>