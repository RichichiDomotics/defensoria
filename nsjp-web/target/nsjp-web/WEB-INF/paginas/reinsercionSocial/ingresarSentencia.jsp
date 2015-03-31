<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="logic" uri="/WEB-INF/tld/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar Sentencias</title>

<!--Se importan las css necesarias-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css" />


<!--Se importan los scripts necesarios-->
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>




<script type="text/javascript">


	var contextoPagina = "${pageContext.request.contextPath}";
	/* Enable IT variables */

	var involucradoId;
	var etiquetaProbableProp = '<bean:message key="probableResponsableTitulo"/>';
	var msjProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';

	$("#btnNuevoProbResponsable").val(etiquetaProbableProp);
	/*si respuesta=1 , existe en SIGI ,
	* si respuesta=2 , no existe en SIGI.
	*/
	var respuesta='<%= request.getParameter("caso")%>';
	
	//var numeroExpedienteId;
	var casoNuevo;
	var idCaso="";
	var numerocaso=""; 
	var idExpediente=""; 
	var numeroExpedienteId="";
	var tipoSentenciaVal="";
	var tipoPena="";
	var seleccionados = new Array();
	var expedienteNuevo; 
	var delitos;
	
	var validaInvolucrados=true;
	var firstGridDelitos=true;
	
	
	
	var ListaCasos= new Array();
	var ListaExpedientes = new Array();
	var exiteCaso=false;
	var existeExpediente=false;
	
	$(document).ready(function() {
		
		$("#tabsprincipalconsulta" ).tabs();
		cargaListaCasos();
		cargaDatePickerFechaLimite();
		muestraDelitos();
		
		/*Existe en sigi*/
		if(respuesta=='1'){
			
			$("#CasoNuevo").hide();
			$("#expedienteNuevo").hide();
			$("#tblProbableResponsable").hide(); //tabla con boton para agregar probable responsable
			$("#delitos").hide();
			$("#etiquetadel").hide();
			$("#tblProbableResponsable").hide();
			$("#btnGenerarCaso").hide();
			$("#Expedientes").hide();//no existen aun
			$("#labelExpediente").hide();//no existen aun
			
			
		}else if (respuesta==2){
			$("#solicitudPJATP").hide();
			$("#Expedientes").hide();
			$("#btnGuardarSentencia").hide();
			muestraPenas();
		}

		
		$("#nuevoProbResponsable").click(creaNuevoProbResponsable);
		
		
			$("#solicitudPJATP").change(function(e){
			 	idCaso= $("#solicitudPJATP option:selected").val();
				numerocaso= $("#solicitudPJATP option:selected").text();
				
				/* $('#Expedientes').empty();
				$('#Expedientes').append('<option value="-1">Seleccione</option>'); */
				$('#Penas').empty();
				$('#Penas').append('<option value="-1">Seleccione</option>');
				cargaListaExpediente(numerocaso);
				cargarGridSeleccionInvolucradosAudiencia();
				obtenerNumeroExpedienteId();
				
				
			});
			
			$("#Expedientes").change(function(e){
				idExpediente= $("#Expedientes option:selected").val();
				cargarGridSeleccionInvolucradosAudiencia();
				obtenerNumeroExpedienteId();

			});

			$("#Penas").change(function(e){
				tipoSentenciaVal= $("#Penas option:selected").val();
			});
			
			$("#delitos").change(function(e){
				delitos= $("#delitos option:selected").val();
			});
			
 		
	});
	//Fin On Ready	
	
		
		function creaNuevoProbResponsable() {
			
			//var casoNuevo;
			casoNuevo= $("#CasoNuevo").val();
			casoNuevo+=" - Externo";
			
			expedienteNuevo= $("#expedienteNuevo").val();
			
			if(casoNuevo!=null && casoNuevo != "" && expedienteNuevo!=null && expedienteNuevo!= "" && delitos>0 ){

				
					/*Genero el caso y el expediente.... cuando es nuevo */
				generarCasoExpediente();
					
					if(!existeCaso){	
						var sistemaTrad = false;
						var idWindowIngresarProbResponsable=1;
						var sistemaTrad = false;
						var muestraDetenido =true;
						var idDefensor="";
						numeroExpediente=$("#expedienteNuevo").val();
						var titulo = "Ingresar " + etiquetaProbableProp;
						$.newWindow({id:"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
						$.updateWindowContent("iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?numeroExpediente='+idExpediente+'&calidadInv=PROBABLE_RESPONSABLE&idDefensor='+idDefensor+'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'" width="1100" height="530" />');
						$("#btnNuevoProbResponsable" +"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable + " .window-maximizeButton").click();
						
					}
				
				
			}else{
				
				alert("Se Debe ingresar un Numero de Caso, Expediente  y un Delito de la Lista");
			}
			
		}
		
		 function cargaProbableResponsable(nombre,id){

			 involucradoId=id;
			 
			 if(involucradoId != null && involucradoId != ""){
				 cargarRelacion();
			}else{
				 alert("No se registro el Probrable Responsable");
			 }
			 
				//var row=$('#btnNuevoProbResponsable'+id);
				//$(row).remove();
				var row=$('#'+id);
				$(row).remove();
			
				nombre=nombre+" - "+'<bean:message key="indiciado" />';
				$('#tblProbableResponsable').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarProbableResponsable" onclick="modificaProbResponsable('+id+')">'+nombre+'</a></td></tr>');

		 }
		 
		 function cargarRelacion(){
			 
			 var relacion;
			 var delito=$("#delitos").val();
		 	 
			 $.ajax({
					type: 'GET',
					url: '<%= request.getContextPath()%>/crearRelacionDelPer.do?idExpediente='+idExpediente+'&delito='+delito+'&involucradoId='+involucradoId,
					data: '',
					async: false,
					dataType: 'xml',
					success: function(xml){
						relacion=$(xml).find('expedienteDTO').find('delitoPrincipal').find('delitoId').text();
						  if(relacion !=null && relacion != "" && relacion != 'undefinied'){
							  $("#btnGuardarSentencia").show();
					  	}
					}	
				});  
		 }
		 
		 function muestraDelitos(){

			 $("#delitos").show();
				
	 			$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/CargarDelito.do?sentencia=true',
					data: '',
					async: false,
					dataType: 'xml',
					success: function(xml){
					
						$(xml).find('catDelitoDTO').each(function(){
							$('#delitos').append('<option value="' + $(this).find('catDelitoId').text() + '">'
							+ $(this).find('nombre').text()+'</option>');
							
						});
						 	 
					}
					
				});
	 
		 }
		 
		 function muestraPenas(){
			 $("#Penas").show();
				
	 			$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/cargarTiposPenas.do',
					data: '',
					async: false,
					dataType: 'xml',
					success: function(xml){
					
						$(xml).find('penasDTO').each(function(){
							$('#Penas').append('<option value="' + $(this).find('idCampo').text() + '">'+
							$(this).find('valor').text()+'</option>');
						});
						 	 
					}
					
				});
		 }

	/*****************************************************************************/
	/*****************************************************************************/
	/*****************************************************************************/
	
	function obtenerNumeroExpedienteId(){
		
		if(idExpediente != null && idExpediente!=="" && idExpediente!== " "){
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerNumeroExpedienteId.do?idExpediente='+idExpediente,
				data: '',
				async: false,
				dataType: 'xml',
				success: function(xml){
					 	 numeroExpedienteId=$(xml).find('neid').text();
				}	
			});
			
		}
		
	}
	
	
	function cargaListaCasos(){
		var contador=0;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargaCasos.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
					
					$(xml).find('caso').each(function(){
						$('#solicitudPJATP').append('<option value="' + $(this).find('casoId').text() + '">'
						+ $(this).find('numeroGeneralCaso').text()+'</option>');
						
						ListaCasos[contador] =$(this).find('numeroGeneralCaso').text();
						contador++;
					});
					
					cargaListaExpediente($(xml).find('caso').find('numeroGeneralCaso').text());
					
			}	
		});
	} 
		
	
		
	function cargaListaExpediente(numerocaso){
		
		var contador=0;
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarExpedientesAsociadosaCaso.do?numerocaso='+numerocaso,
			data:'',				
			async: false,
			dataType: 'xml',
			success: function(xml){

				$(xml).find('penasDTO').each(function(){
					$('#Penas').append('<option value="' + $(this).find('idCampo').text() + '">'+
					$(this).find('valor').text()+'</option>');
				});
				
				for( var i = 0; i< $('#Expedientes').length; i++ ){
					$(xml).find('ExpedienteDTO').each(function(){
						//if(!existeCaso){
						cargarGridSeleccionInvolucradosAudiencia($(this).find('expedienteId').text());
						//}
					});	
				}
					
	/* 		if(existeCaso){
				$(xml).find('ExpedienteDTO').each(function(){
					$('#Expedientes').append('<option value="' + $(this).find('expedienteId').text() + '">'+
					$(this).find('numeroExpediente').text()+'</option>');
					ListaExpedientes[contador] = $(this).find('numeroExpediente').text();
				contador++;
				});
			} */
			}	
		});
	}
	

	function cargaDatePickerFechaLimite(){

		$("#fechaInicialSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true
		});
		
		$("#fechaFinalSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true
		});

	}
	
		
	function limpiarGridInvolucradosAudiencia(){
		numExpediente="";
		validaInvolucrados=false;
		jQuery("#gridInvolucradosAudiencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?numExpediente='+numExpediente+'&limpiar=true',datatype: "xml" });
		$("#gridInvolucradosAudiencia").trigger("reloadGrid");
	}
	
	
	function alertDinamico(textoAlert,id){
		$("#divAlertTexto").html(textoAlert);
	    $( "#dialog-Alert" ).dialog({
			autoOpen: true,
			resizable: false,
			modal: true,
			buttons: {				
				"Aceptar": function() {
					$( this ).dialog( "close" );
					$("#divAlertTexto").html("");					
				}				
			}
		});    
	 }

	//funcion que guarda las solicitudes de audiencia, de transcripcion de audiencia y  de audio/video
	function guardarSentencia(){
		
		var datosCorrectos=true;
		 
		 var tipoPena=$("#Penas option:selected").val();
		 var numeroExpediente;
		 var fechaInicio=$("#fechaInicialSentencia").val();
		 var fechaFinal=$("#fechaFinalSentencia").val();
		 var nombre=$('#nombreSolicitantePJATP').val();
		 var apaterno=$('#apPatSolicitantePJATP').val();
		 var delito=$("#delitos option:selected").val();
		 var campoNumeroUnicoSentencia=$("#cNUS").val();
		
		 var fiscal=$('#nombreSolicitantePJATP').val()+" "+$('#apPatSolicitantePJATP').val()+" "+$('#apMatSolicitantePJATP').val();
		
		/*para cuando existe en sigi*/
		if (respuesta=='1'){
		
			involucrados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','selarrrow');
			//numeroExpediente=$("#Expedientes option:selected").text();
			
			if(seleccionados <= 0  ){
				alert("Por favor seleccione al menos un involucrado",2);
				datosCorrectos=false;
			}else if(seleccionados < 2){
				alert("Por favor seleccione solo un involucrado",2);
				datosCorrectos=false;
			}else
			if(involucrados.length <= 0){
				alert("Por favor seleccione al menos un involucrado",2);
				datosCorrectos=false;
			}
		}
		
		/************************para cuando se ingresa desde fuera*********************/
		if (respuesta=='2'){
			numeroExpediente=$('#expedienteNuevo').val();
			var involucrados=involucradoId;
			//involucrados=involucradoId;
			
			if(involucradoId == null || involucradoId=="" ){
				alert("No se ha agregado a un involucrado",2);
				datosCorrectos = false;
			}else if (delitos <= 0){
				alert("No se ha seleccionado un Delito",2);
				datosCorrectos = false;
			}
		}
		
		if(tipoPena == null || tipoPena <= 0 ){
			alert("Seleccione un Tipo de Sentencia",2);
			datosCorrectos= false;
		}else 
		if( (nombre == null && apaterno == null) || (nombre == "" && apaterno == "")){
			alert("Ingrese los datos del Fiscal",2);
			datosCorrectos= false;
			var fiscal=$('#nombreSolicitantePJATP').val()+" "+$('#apPatSolicitantePJATP').val()+" "+$('#apMatSolicitantePJATP').val();
		}else
		if(fechaInicio == null || fechaInicio == "" ){
			alert("Seleccione una Fecha Inicial de la Sentencia",2);
			datosCorrectos= false;
		}else	
		if(fechaFinal == null || fechaFinal == "" ){
			alert("Seleccione una Fecha Final de la Sentencia",2);
			datosCorrectos= false;
		}else
		if(campoNumeroUnicoSentencia == null || campoNumeroUnicoSentencia == ""){
			alert("El campo Numero Unico de Sentencia es Obligatorio",2);
			datosCorrectos= false;
		}
		
		if(datosCorrectos){
		
			var caso=numerocaso;			
			var tipoPena=$("#Penas option:selected").val();
			var fechaInicio=$("#fechaInicialSentencia").val();
			var fechaFinal=$("#fechaFinalSentencia").val();
			var nombre=$('#nombreSolicitantePJATP').val();
			var apaterno=$('#apPatSolicitantePJATP').val();
			var campoNumeroUnicoSentencia=$("#cNUS").val();
			var fiscal=$('#nombreSolicitantePJATP').val()+" "+$('#apPatSolicitantePJATP').val()+" "+$('#apMatSolicitantePJATP').val();
			var delito=$('#delitos option:selected').val();
			
			
			if(respuesta=='1'){//expediente dentro de sigi necesita el caso 
				var parametros="involucrados="+involucrados+"&tipoPenaval="+tipoPena+"&fechaInicio="+fechaInicio+"&fechaFin="+fechaInicio+"&cNUS="+campoNumeroUnicoSentencia+"&numeroCaso="+caso+"&respuesta="+respuesta;	
				guardaResponse(parametros);
			}
			if(respuesta=='2'){//expediente por fuera , hay q crear el caso y expediente para dar de alta la sentencia 
				var parametros="numeroCaso="+casoNuevo+"&numeroExpedienteId="+numeroExpedienteId+"&numeroExpediente="+numeroExpediente+"&involucrados="+involucrados+"&tipoPenaval="+tipoPena+"&fechaInicio="+fechaInicio+"&fechaFin="+fechaInicio+"&cNUS="+campoNumeroUnicoSentencia+"&respuesta="+respuesta;
				guardaResponse(parametros);
			}
			
			if(sentenciaId != null && sentenciaId > 0){
				alertDinamico("Sentencia Creada Correctamente",1);
				cerrarCustomVentana();
			}else{
				alertDinamico("Error al crear la sentencia.",1);	
			}
			
		}

	}

	var sentenciaId;
	
	function guardaResponse(parametros){
		
			$.ajax({
				type: 'GET',
				url: '<%= request.getContextPath()%>/guardarSentenciaEna.do',
				data: parametros,
				async: false,
				dataType: 'xml',
				success: function(xml){
					/*variable para corroborar que la sentencia se haya creado correctamente*/
					sentenciaId=$(xml).find('SentenciaDTO').find('sentenciaId').text();
				}	
			}); 
		
	}
	
	/********************************************************/
	/**
	* Carga el grid para seleccionar involucrados en la audiencia con las víctimas y probables responsables
	* del expediente seleccionado en los pasos anteriores
	*/
	var primeraConsultaInvolucrados = true;
	var validaInvolucrados=true;
	function cargarGridSeleccionInvolucradosAudiencia(idExpedienteaux){
		
		var numExpediente = $('#numCausaPJATP').val();
		var numInvolucrados;

		if(primeraConsultaInvolucrados){
			//Se llena el gird de involucrados VICTIMAS Y PROBABLES RESPONSABLES
			jQuery("#gridInvolucradosAudiencia").jqGrid({
				url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?sentencia=true&expedienteId='+idExpedienteaux+'', 
				datatype: "xml", 
				colNames:['Nombre','Calidad'], 
				colModel:[ 	{name:'nombre',index:'nombre', width:200, align:"left"},
				           	{name:'carga',index:'carga', width:120, align:"center"}  
						],
				pager: jQuery('#pagerGridInvolucrados'),
				rowNum:10,
				rowList:[25,50,100],
				autowidth: true,
				sortname: 'carga',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true,
				onSelectRow: function(ids) {
					numInvolucrados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','records');
					seleccionados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','selarrrow');
					if((numInvolucrados-seleccionados.length) == 0){
						jQuery("#cb_gridInvolucradosAudiencia").attr('checked','checked');
					}else if((numInvolucrados-seleccionados.length) < numInvolucrados && jQuery("#cb_gridInvolucradosAudiencia").is(":checked")){
						jQuery("#cb_gridInvolucradosAudiencia").removeAttr("checked");
					}
				},
				gridComplete: function(){
					if (validaInvolucrados == true)	{
						numInvolucrados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','records');
						if (numInvolucrados == 0){				
							alertDinamico("Los intervinientes de la causa seleccionada ya cuentan con sentencia o no se cuenta con intervinientes, \n Por favor seleccione una causa que tenga intervinientes asociados",1);
						}
					}
				}
				
			}).navGrid('#gridInvolucradosAudiencia',{edit:false,add:false,del:false});
			primeraConsultaInvolucrados=false;
		}
		else{
			jQuery("#gridInvolucradosAudiencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?sentencia=true&expedienteId='+idExpedienteaux+'',datatype: "xml" });
			$("#gridInvolucradosAudiencia").trigger("reloadGrid");
		}
	}
	
	function validarInvolucradosGrid(numInvolucrados){
		if (numInvolucrados == '0' || numInvolucrados <= 0){
			alertDinamico("Los intervinientes de la causa seleccionada ya cuentan con sentencia o no se cuenta con intervinientes, <br/>"
					+"Por favor seleccione una causa que tenga intervinientes asociados");
		}	
	}
	
	
	

	function generarCasoExpediente(){
		
		casoNuevo= $("#CasoNuevo").val();
		casoNuevo+=" - Externo";

		expedienteNuevo= $("#expedienteNuevo").val();

		existeCasoYexpediente(casoNuevo,expedienteNuevo);
		
		/*Sino existe el caso lo creo, ni el expediente lo creo*/
		//if((existeCaso == false || existeCaso==true) && existeExpediente==false){
		
			$.ajax({
				type: 'GET',
				url: '<%= request.getContextPath()%>/generarCasoExpediente.do?casoNuevo='+casoNuevo+'&numeroExpediente='+expedienteNuevo,
				data: '',
				async: false,
				dataType: 'xml',
				success: function(xml){
					
					<%-- <%=idExpediente%> --%>
					idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
					 <%-- <%=numeroExpedienteId%> --%>
					 numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
					<%-- alert("numero expediente id ::"+<%=numeroExpedienteId%>+"\nexpediente ID :: "+<%=idExpediente%>); --%>
					$('#btnNuevoProbResponsable').hide();
					
				}	
			}); 
		//}//fin del if
		//else{
			//sino si existe el caso y el expediente no existe ligado al caso creo el #expediente y expediente ya que son nuevos
			//ligados al caso existente
		//	alertDinamico("El caso ya existe favor de verificar tu información.");
			
		//}
	}
	
	
	function existeCasoYexpediente(casoN,expedienteN){

		cargaListaCasos();
		
		for(var i=0;i<=ListaCasos.length;i++){
			if(casoN === (ListaCasos[i])){
				
				existeCaso=true;
				
				cargaListaExpediente(casoN);
			 	
				for(var j=0;j<=ListaExpedientes.length;j++){
					if(ListaExpedientes[j] === (expedienteN)){
						existeExpediente=true;
						alertDinamico("El caso"+casoN+" ya existe con Número de Expediente"+expedienteN+" , favor de verificar los datos.");
						
					}	 
					
				}
				break;
			}else{
				existeCaso=false;
			}
		}
		
	}

 	function patronDiseno(e,valor) { 

		var tecla = (document.all) ? e.keyCode : e.which; 
  		var te = String.fromCharCode(tecla);
  		if(valor == 0){
  			var patron =/\w/;//alfanumericos	
  		}else{
  			var patron =/\d/;//digitos
  		}
		 		
 	    if(te.match(patron) || tecla == 8 || tecla == 45 || tecla==47 ){ 
 	    	
 	    	return true;	
 	    }else{
 	    	return false;
 	    }
 	} 
	/*****************************************************************************/
	
	function modificaProbResponsable(id){
		modificarProbResponsable(id);
		}

	function modificarProbResponsable(id) {
		var idWindowIngresarProbResponsable=1;
		var muestraDetenido = true;
		var elementoNvo="no";
		var titulo = "Modificar " + etiquetaProbableProp;
		$.newWindow({id:"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
		$.updateWindowContent("iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente='+expedienteNuevo +'&detenido='+muestraDetenido+'&elemento='+elementoNvo+'" width="1100" height="530" />');
	<%-- $.updateWindowContent("iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id+'&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente='+expedienteNuevo +'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'&elemento=si" width="1100" height="530" />'); --%>
		$("#" +"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable + " .window-maximizeButton").click();
	}
	
	
</script>
</head>

<!--Comienza cuerpo del documento-->
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
	<!--Comienza tab principal-->
	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Datos de la Sentencia</a></li>
		</ul>
		<!--Comienza tab detalle de solicitud-->
		<div id="tabsconsultaprincipal-1" align="left">
		
		<table width="100%" cellpadding="5" id="General" valign="top" border=0 align="left">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<td>
			<table width="80%" cellpadding="5" id="Resumen" valign="top" border=0 align="right">
				
				<tr>
					<td colspan="2" width="125" align="left">
						<strong>Caso(s)</strong>
					</td>									
		        </tr>
				<tr>
				
					<td colspan="2" width="125" align="center">	
						<div>
							<select id="solicitudPJATP"	style="width: 200px;" tabindex="1"><option value="-1">Seleccione</option></select>
						</div>
						<div>
			            	<input type="text" id="CasoNuevo" onkeypress="return patronDiseno(event,0);"  style="width:200px;" tabindex="7"/>
			        	</div>
					</td>
				</tr>

				<tr>
					<td colspan="2" id="labelExpediente" width="125" align="left"><strong>Expediente :</strong> 
					</td> 
				</tr>
				<tr>
					<td colspan="2" width="125" align="center">	
					
						<!-- <select id="numeroExpedienteId" style="width: 200px;" tabindex="1"><option value="-1">Seleccione</option></select> -->
							<select id="Expedientes" style="width: 200px;" tabindex="1"><option value="-1">Seleccione</option></select>
							
						<div>
			            	<input type="text" id="expedienteNuevo" onkeypress="return patronDiseno(event,1);" style="width: 200px;"  tabindex="7"/>
			        	</div>
		        	</td>
				</tr>

				<tr>
					<td colspan="2" width="125" align="left"><strong>Tipos de Sentencias:</strong></td> 
					</tr>
					<tr>
						<td colspan="2" width="125" align="center">
						<select id="Penas" style="width: 200px;" tabindex="1">
								<option value="-1">Seleccione</option>
						</select></td>
				</tr>
				
				
				<tr>
					<td  id="etiquetadel" colspan="2" width="125" align="left"><strong>Delito(s):</strong></td> 
					</tr>
					<tr>
						<td colspan="2" width="125" align="center">
						<select id="delitos" style="width: 200px;" tabindex="1">
								<option value="-1">Seleccione</option>
						</select></td>
				</tr>
				
				
			</table>
		</td>
		
		
		<td>	
			<table width="80%" cellpadding="5" id="DatosFiscal" valign="top" border=0 align="center" >
			
				<tr>
						<td colspan="2" align="center">
				            <div id="divDatosSolicitante">
				                <strong>Datos del Fiscal</strong>
				            </div>
				        </td>
			        </tr>
			        
			        <tr>
				        <td align="right">
				            <div id="divNombreSol"><strong>Nombre(s):</strong></div>
				            
				        </td>
				        <td>
				        	<div>
				            <input type="text" id="nombreSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="7"/>
				        	</div>
				        </td>
			        </tr>
			        
			        <tr>
				        <td align="right">
				            <div id="divApellidoPat"><strong>Apellido Paterno:</strong></div>
				        </td>
				        <td><input type="text" id="apPatSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="8"/></td>
				        
					</tr>
					
					<tr>
				        <td align="right">
				            <div id="divApellidoMat"><strong>Apellido Materno:</strong></div>
				        </td>
				        <td>
				            <input type="text" id="apMatSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="9"/>
				        </td>
			        </tr>
		      
	    </table>
	    </td>
	    <td>
		
			<table width="100%" cellpadding="10" id="DatosFiscal" valign="top" border=0 align="center">
					<tr>				
						<td width="100%" align="center">
				            <div id="fechaIniSen">
				                <strong>Fecha Inicial Sentencia: </strong>
				            </div>
				        </td>
				        <td width="209">
				            <input type="text" id="fechaInicialSentencia" align="middle" style="width:78px;" tabindex="12"/>
				        </td>
			        </tr>
			        <tr>
						<td width="100%" align="center">
				            <div id="fechaFinSen">
				                <strong>Fecha Final Sentencia: </strong>
				            </div>
				        </td>
				        <td width="209">
				            <input type="text" id="fechaFinalSentencia" align="middle" style="width:78px;" tabindex="12"/>
				        </td>  
					        <tr>
						        <td width="100%" align="center">
						        	<div id="etiquetacNUS"><strong> Número Unico de Sentencia:</strong>
						        	</div>
						        </td>
						        <td>
						        <input type="text" id="cNUS" style="width: 78px;" onkeypress="return solonumeros(event);" tabindex="9"/>
						        </td>
					        </tr>
					</tr>
			</table>
				<tr>
				 <!-- <td colspan="2">&nbsp;
				 <input type="button" name="btnGenerarCaso" id="btnGenerarCaso" value="Generar Caso/Expediente" onclick="generarCasoExpediente();" align="middle" tabindex="4" class="btn_Generico">
				 </td> --> 
		    	<td width="209">
		    		<input type="button" name="btnGuardar" id="btnGuardarSentencia" value="Guardar Sentencia" onclick="guardarSentencia();" align="middle" tabindex="4" class="btn_Generico">
		        </td>
		        	 <td colspan="2">&nbsp;</td>
		        </tr>
			</td>
		</table>
	    
	     	
			<tr>
			<td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td>
				<td colspan="2" align="center"><span id="divEtiquetaInvolucrados"><strong>Seleccione los involucrados</strong></span></td>
				<td>
					<table width="50%" cellpadding="10"
						id="gridInvolucradosAudienciaContenedor" valign="top">
						<tr>
							<td><table id="gridInvolucradosAudiencia"></table></td>
						</tr>
					</table>
				 	
				 	<table width="25%" cellpadding="0" cellspacing="0" id="tblProbableResponsable" style="padding: .5cm;">
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoProbResponsable"><input type="button" class="btn_Generico" id="btnNuevoProbResponsable" value="Ingresar Probable Responsable"/></a></td>
							</tr>
						</table>

                    		 
					</table> 
				</td>
				<td colspan="2">&nbsp;</td><td colspan="2">&nbsp;</td>
			</tr>
						<!--Termina tabla con datos de la solicitud-->
			
		</div>
		<!--Termina tab detalle de solicitud-->

	</div>
	

	<!--Termina Tab principal-->
	
	<!-- div para el alert dinamico -->
	
</body>
</html>

