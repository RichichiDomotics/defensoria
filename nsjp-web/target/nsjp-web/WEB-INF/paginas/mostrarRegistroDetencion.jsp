<%@page import="mx.gob.segob.nsjp.comun.enums.involucrado.TipoEvento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mostrar Registro de Detención</title>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
		
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
		
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>	

	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	.tabEstilo  { height: 350px; overflow: auto; }
	</style>
    
<script type="text/javascript">

	var expedienteId = '<%=request.getParameter("expedienteId")%>';
	var numeroExpediente = '<%=request.getParameter("numeroExpediente")%>';
	var registroSinHecho = '<%=request.getParameter("registroSinHecho")%>';
	var hechoId = '<%=request.getParameter("hechoId")%>';
	var consultaDetencion = '<%=request.getParameter("consultaDetencion")%>';
	
	//Variable que controla si el grid se carga por primera vez
	var firstGridProbableResponsable = true;
	var firstGridPertenencias = true;

	var idWindowGenerarDetencion = 1;
	var idWindowIngresarPertenencias = 1;
	var idWindowMostrarReincidencias = 1;

	var gridLength = 0;

	var detencionTxtNombreSP = '';
	var detencionTxtCargo = '';
	var detencionTxtAreaAdm = '';
	var detencionTxtFechaElaboracion = '';
	var detencionTxtCoordinadorAMP = '';
	var detencionTxtCoordinadorDP = '';
	var idAvisoHechoDelitivo = 0;
	
	$(document).ready(function() {
		$('#liDom').hide();
		$('#liDom').addClass("tabEstilo");
		
		$( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
			.removeClass( "ui-corner-all ui-corner-top" )
			.addClass( "ui-corner-bottom" );

		$("#fechaInicioLapso").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});

		//Se crean las tabs principales
		$("#tabsconsultaprincipal" ).tabs();

		//Para seleccionar tab por default
		$('#tabsconsultaprincipal').tabs({ selected: 0 });

		$('#btnGenerarDetencion').click(generarDetencion);
		$('#btnAgregarProbableResponsable').click(agregarProbableResponsable);
		$('#btnEliminarProbableResponsable').click(eliminarProbableResponsable);
		$("#estaDetenido").click(habilitaFechaHoraDetencion);		
		$('#btnAgregarPertenencia').click(agregarPertenencia);
		$('#btnCancelarPertenencia').click(eliminarPertenencia);
		$('#btnEnviarUnidadCaptura').click(enviarAUnidadDeCaptura);
		$('#btnMostrarIPH').click(mostrarIPH);

		consultarCatalogoTipoPertenencia();
		consultarCatalogoCondicionFisica();
		cargaGridProbableResponsable();		
		cargaGridPertenencias();
		iniciarEditorTexto();
		cargaActuaciones();
		documentos();
			
		/*
		*Entramos a esta parte de codigo cuando generamos el expediente
		*unicamente con Hecho y sin avisoHechoDelictivo 
		*/
		if(registroSinHecho == "true"){
			consultaHecho();
			$('#btnMostrarIPH').hide();
			$('#guardarDescripcionHecho').show();
			
		}else{
			
			consultarRegistroDeDetencionPorExpedienteId();
			$('#btnMostrarIPH').hide();
			$('#guardarDescripcionHecho').hide();
			consultaAviso(idAvisoHechoDelitivo);
			
			CKEDITOR.on("instanceReady", function (ev) {
				var bodyelement = ev.editor.document.$.body;
				bodyelement.setAttribute("contenteditable", false);
				});
			CKEDITOR.replace('hechosRegistroDetencion');
		}
	});

	/**
	** Llena de manera dinámica los subtipos de eventos asociados al tipo de evento seleccionado.
	**/
	function buscaSubTipoEvento(){
		var selected = $('#detencionCbxTipoEvento option:selected').val();
		$('#detencionCbxSubtipoEvento').attr('selectedIndex',0);
		$('#detencionCbxSubtipoEvento').empty();
		$('#detencionCbxSubtipoEvento').append('<option value="-1">-Seleccione-</option>');
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarSubtipoEvento.do?tipoEvento='+selected+'',
			dataType: 'xml',
			success: function(xml){
				if(selected == '1'){
					$(xml).find('delito').each(function(){
						$('#detencionCbxSubtipoEvento').append('<option value="' + $(this).find('claveDelito').text() + '">' + $(this).find('nombre').text() + '</option>');
					});
				}else if(selected == '2'){
					$(xml).find('falta').each(function(){
						$('#detencionCbxSubtipoEvento').append('<option value="' + $(this).find('catFaltaAdministrativaId').text() + '">' + $(this).find('nombreFalta').text() + '</option>');
					});
				}
			}
		});
	}

	/**
	**Funcion que carga el grid con los probables responsables
	**/
	function cargaGridProbableResponsable(){

		if(firstGridProbableResponsable == true){
			
			jQuery("#gridProbableResponsable").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarProbableResponsablePorNumeroExpediente.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',
				datatype: "xml", 
				colNames:['Nombre','Apellido Paterno','Apellido Materno','¿Detenido?','Fecha de Detención','¿Menor de Edad?','ID Tipo Evento','Tipo Evento','ID Subtipo Evento','Subtipo de Evento'], 
				colModel:[ 	{name:'Nombre',index:'4', sortable:false, width:160},
				           	{name:'ApellidoPaterno',index:'2', sortable:false, width:140},
				           	{name:'ApellidoMaterno',index:'3', sortable:false, width:140},
				           	{name:'Detencion',index:'1', sortable:true, width:90,align:"center"},
				           	{name:'FechaDetencion',index:'5', sortable:false, width:160},
				           	{name:'MenorDeEdad',index:'6', sortable:false, width:120,align:"center"},
				           	{name:'idTipoEvento',index:'7', sortable:false, width:10,hidden:true},
				           	{name:'TipoEvento',index:'8', sortable:false, width:170},
				           	{name:'idSubTipoEvento',index:'9', sortable:false, width:10,hidden:true},
				           	{name:'SubTipoEvento',index:'10', sortable:false, width:170}
						],
				pager: jQuery('#pagerGridProbableResponsable'),
				rowNum:10,
				rowList:[10,20,30],
				//autowidth: true,
				//autoheight:true,
				width:900,
				height:115,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				
				ondblClickRow: function(rowid) {
					var probableResSeleccionado = jQuery("#gridProbableResponsable").jqGrid('getRowData',rowid);					
					//Solo en el caso de que el probable participe este detenido se invoca la pantalla de "Generar Detención"
					if(probableResSeleccionado.Detencion == 'Si'){
						if(probableResSeleccionado.TipoEvento == '<%=TipoEvento.FALTA_ADMINISTRATIVA.getDescripcion()%>'){
							generarDetencion(rowid,true);
						}
						else{
							generarDetencion(rowid,false);
						}
					}
				}
				
			}).navGrid('#pagerGridProbableResponsable',{edit:false,add:false,del:false});
			

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridProbableResponsable=false;
		}
		else{
			jQuery("#gridProbableResponsable").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarProbableResponsablePorNumeroExpediente.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',datatype: 'xml'});
			$("#gridProbableResponsable").trigger("reloadGrid");			
		}
		
		setTimeout('armaComboProbableResponsable()',3000);
	}

    function generarDetencion(idProbableParticipe,noAviso){

    	//Se recuperan los identificadores del Grid
    	var probableResSeleccionado = jQuery("#gridProbableResponsable").jqGrid('getRowData',idProbableParticipe);		
    	var tipoEvento = probableResSeleccionado.idTipoEvento;
    	var subtipoDeEvento = probableResSeleccionado.idSubTipoEvento;
    	
	   		idWindowGenerarDetencion++;
	   		$.newWindow({id:"iframewindowGenerarDetencion" + idWindowGenerarDetencion, statusBar: true, posx:30,posy:10,width:950,height:500,title:"Generar Detención", type:"iframe"});
	   		$.updateWindowContent("iframewindowGenerarDetencion" + idWindowGenerarDetencion,'<iframe src="<%= request.getContextPath() %>/mostrarGenerarDetencion.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&idProbableParticipe='+idProbableParticipe+'&tipoEvento='+tipoEvento+'&subtipoDeEvento='+subtipoDeEvento+'&noAviso='+noAviso+'" width="950" height="500" />');
	
    }

	/**
	**Funcion que carga el grid con las pertenencias de los probables responsables
	**/
	function cargaGridPertenencias(){
		var idInvolucrado = $("#detencionCbxProbableResponsable option:selected").val();
		var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';

			if(firstGridPertenencias == true){		
				jQuery("#gridPertenencias").jqGrid({ 
					url:'<%= request.getContextPath()%>/consultarPertenenciasPorDetencionId.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&idInvolucrado='+idInvolucrado+'', 
					datatype: "xml", 
					colNames:[probableResponsableProp,'Cantidad','Tipo','Condición Física','Descripción' ], 
					colModel:[ 	{name:'ProbableResponsable',index:'4',sortable:false,  width:200,hidden:true},
					           	{name:'Cantidad',index:'2', sortable:true ,  width:60, align:"center"},
					           	{name:'Tipo', index:'1', sortable:true, width:150},
					           	{name:'CondicionFisica', sortable:true, index:'3', width:150},
					           	{name:'Descripcion', sortable:false, index:'5', width:200}
							],
					pager: jQuery('#pagerGridPertenencias'),
					rowNum:10,
					rowList:[10,20,30],
					//autowidth: true,
					//autoheight:true,
					width:780,
					height:220,
					sortname: '1',
					viewrecords: true,
					sortorder: "desc"
				}).navGrid('#pagerGridPertenencias',{edit:false,add:false,del:false});
				//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
				firstGridPertenencias=false;
			}
			else{
				jQuery("#gridPertenencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarPertenenciasPorDetencionId.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&idInvolucrado='+idInvolucrado+'',datatype: "xml" });
				$("#gridPertenencias").trigger("reloadGrid");			
			}

	}

    function customRange(input) {
  	  return {minTime: (input.id == 'idHoraDateLapsoFin' ?
  		$('#idHoraDateLapsoInicio').timeEntry('getTime') : null),
  		maxTime: (input.id == 'idHoraDateLapsoInicio' ?
  		$('#idHoraDateLapsoFin').timeEntry('getTime') : null)};
      }
      $(function(){
        $('.timeRange').timeEntry({beforeShow: customRange,timeSteps:[1,1,0],ampmPrefix: ' '});
      });

      function agregarProbableResponsable(){
    	  var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
  		if( ( !$("#esDesconocido").is(':checked') 
  		  		&& ($('#nombre').val() == '' || $('#apellidoPaterno').val() == '' || $('#apellidoMaterno').val() == '' ) )
  	  		 || ( $("#estaDetenido").is(':checked') 
  	    	  	&& ($('#fechaInicioLapso').val() == '' || $('#horaInicioLapso').val() == '') ) 
			 || ( $('#detencionCbxTipoEvento option:selected').val() == -1 || $('#detencionCbxSubtipoEvento option:selected').val() == -1 ) ){
				alertDinamico('Debe seleccionar todos los datos del '+probableResponsableProp+'.');
		}else{	          
			  var nombre = $('#nombre').val();
			  var apellidoPaterno = $('#apellidoPaterno').val(); 
			  var apellidoMaterno = $('#apellidoMaterno').val();
			  var detencion = $("#estaDetenido").is(':checked') ? 'Si' : 'No';
			  var fechaDetencion = $('#fechaInicioLapso').val();
			  var horarioDetencion = $('#horaInicioLapso').val();
			  var menorDeEdad = $("#esMenorDeEdad").is(':checked') ? 'Si' : 'No';
			  var desconocido = 'No';
			  var tipoEvento = $('#detencionCbxTipoEvento').val();
			  var subtipoDeEvento = $('#detencionCbxSubtipoEvento').val();			  
			  
			  
			  if($("#esDesconocido").is(':checked')){
				  desconocido = 'Si';
				  nombre = 'Desconocido';
				  apellidoPaterno = '';
				  apellidoMaterno = '';
			  }

			  if(!$("#estaDetenido").is(':checked')){
				  fechaDetencion = '';
			  }

			  var params = '&nombre=' + nombre + '&apellidoPaterno=' + apellidoPaterno + '&apellidoMaterno=' + apellidoMaterno
			  			 + '&detencion=' + detencion + '&fechaDetencion=' + fechaDetencion + '&horarioDetencion=' + horarioDetencion
			  			 + '&desconocido=' + desconocido + '&tipoEvento=' + tipoEvento + '&subtipoDeEvento=' + subtipoDeEvento;

	          $('#nombre').val('');
			  $('#apellidoPaterno').val('');
			  $('#apellidoMaterno').val('');
			  $('#horaInicioLapso').val('07:00 AM');
			  $("#estaDetenido").attr('checked','');
			  $("#chkMayorEdad").attr('checked','');
			  $("#esDesconocido").attr('checked','');
			  deshabilitaFechaHoraDetencion();
			
			  
			  idCatDelito = $("#detencionCbxSubtipoEvento").val();
			  
			  $.ajax({
					async: false,
					type: 'POST',
					url: '<%= request.getContextPath()%>/agregarProbableResponsable.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&idCatDelito='+idCatDelito+'',
					data: params,
					dataType: 'xml',
					success: function(xml){
						 var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
						alertDinamico('El '+probableResponsableProp+' se agregó de manera correcta.');
						//jQuery("#gridProbableResponsable").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarProbableResponsablePorExpedienteId.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
						//$("#gridProbableResponsable").trigger("reloadGrid");
						cargaGridProbableResponsable();
					}
			  });

			  //gridLength++;
						   
		}  
      }

      function eliminarProbableResponsable(){
    	  var rowid = jQuery("#gridProbableResponsable").jqGrid('getGridParam','selrow');		

  		  if (rowid) { 
  			 $.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/eliminarProbableResponsable.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&involucradoId='+rowid+'',
				dataType: 'xml',
				success: function(xml){
					var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
					alertDinamico('El '+probableResponsableProp+' se eliminó de manera correcta.');
					//jQuery("#gridProbableResponsable").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarProbableResponsable.do?',datatype: "xml" });
					//$("#gridProbableResponsable").trigger("reloadGrid");
					cargaGridProbableResponsable();
				}
		  	 });

  			//gridLength--;
  		  }else{
  			  var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
  			  alertDinamico("Por favor seleccione un "+probableResponsableProp);
  		  } 	
      }

      function habilitaFechaHoraDetencion(){
    	if($("#estaDetenido").is(':checked')){
  			var today = new Date();
  			var fechaActual = (today.getDate() < 10 ? '0' + today.getDate() : today.getDate()) + '/' 
  		  				   + (today.getMonth() < 9 ? '0' + (today.getMonth() + 1) : (today.getMonth() + 1)) + '/' 
  		  				   + today.getFullYear();
  			$("#fechaInicioLapso").val(fechaActual);
  			$("#horaInicioLapso").attr('disabled','');
  			$("#horaInicioLapso").val('07:00 AM');
  		}else{
  			deshabilitaFechaHoraDetencion();
  		}
  	}

  	function deshabilitaFechaHoraDetencion(){
  		$("#fechaInicioLapso").val('');
  		$("#horaInicioLapso").attr('disabled','disabled');
  		$("#horaInicioLapso").val('');
  	}

	function agregarPertenencia(){
		if($('#detencionCbxProbableResponsable option:selected').val() == -1 
				|| $('#detencionCbxTipoPertenencia option:selected').val() == -1
				|| $('#detencionCbxCondicionFisica option:selected').val() == -1
				|| $('#detencionTxtCantidad').val() == ''
				|| $('#detencionTxtDescripcion').val() == ''){
				alertDinamico('Debe seleccionar todos los parámetros de pertenencia.');
		}else{
		  	  //var arrayIds = jQuery("#gridPertenencias").getDataIDs();
			  //var id = arrayIds.length + 1;

			  var probableResponsableId = $('#detencionCbxProbableResponsable option:selected').val();
			  var probableResponsable = $('#detencionCbxProbableResponsable option:selected').text();
			  var cantidad = $('#detencionTxtCantidad').val(); 
			  var tipoId = $('#detencionCbxTipoPertenencia option:selected').val();			  
			  var tipo = $('#detencionCbxTipoPertenencia option:selected').text();
			  var condicionFisicaId = $('#detencionCbxCondicionFisica option:selected').val();
			  var condicionFisica = $('#detencionCbxCondicionFisica option:selected').text();
			  var descripcion = $('#detencionTxtDescripcion').val();
			  
			  var params = '&probableResponsableId=' + probableResponsableId + '&cantidad=' + cantidad + '&tipoId=' + tipoId 
			  			 + '&condicionFisicaId=' + condicionFisicaId + '&descripcion=' + descripcion;
				  										  
		      //$('#detencionCbxProbableResponsable').val('-1');
			  $('#detencionCbxTipoPertenencia').val('-1');
			  $('#detencionCbxCondicionFisica').val('-1');
			  $('#detencionTxtCantidad').val('');
			  $('#detencionTxtDescripcion').val('');

			  $.ajax({
					async: false,
					type: 'POST',
					url: '<%= request.getContextPath()%>/agregarPertenencia.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',
					data: params,
					dataType: 'xml',
					success: function(xml){
						alertDinamico('La pertenencia se agregó de manera correcta');
						//jQuery("#gridPertenencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarPertenencia.do?',datatype: "xml" });
						//$("#gridPertenencia").trigger("reloadGrid");
						cargaGridPertenencias();
					}
			  });
			  
		}
	}

	function eliminarPertenencia(){
		var rowid = jQuery("#gridPertenencias").jqGrid('getGridParam','selrow');		

	    if (rowid) { 
			//jQuery("#gridPertenencias").jqGrid('delRowData',row);
			//var rowCount = $(".gridPertenencias tr").length;
 			 $.ajax({
 				async: false,
 				type: 'POST',
 				url: '<%= request.getContextPath()%>/eliminarPertenencia.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'&pertenenciaId='+rowid+'',
 				dataType: 'xml',
 				success: function(xml){
 					alertDinamico('La pertenencia se eliminó de manera correcta.');
 					//jQuery("#gridProbableResponsable").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarProbableResponsable.do?',datatype: "xml" });
 					//$("#gridProbableResponsable").trigger("reloadGrid");
 					cargaGridPertenencias();
 				}
 		  	 });			
		}else{
			var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
			alertDinamico("Por favor seleccione una pertenencia del "+probableResponsableProp);
		}	
	}

	/**Funcion que consulta el catalogo de tipos de pertenencia*/
	function consultarCatalogoTipoPertenencia(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarTiposDePertenencia.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catTipoDePertenencia').each(function(){
					$('#detencionCbxTipoPertenencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
	}
	
	/**Funcion que consulta el catalogo de condición física del objeto*/
	function consultarCatalogoCondicionFisica(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCondicion.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catCondicion').each(function(){
					$('#detencionCbxCondicionFisica').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
	}

	function consultarRegistroDeDetencionPorExpedienteId(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerAvisoPorIdExpediente.do?expedienteId='+expedienteId+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if($(xml).find('tipoPertenencia').text() != null && $(xml).find('tipoPertenencia').text() != ''){
					$("#detencionCbxTipoPertenencia").val($(xml).find('tipoPertenencia').text()); //combo
				}
				buscaSubTipoEvento();
				if($(xml).find('condicionFisica').text() != null && $(xml).find('condicionFisica').text() != ''){
					$("#detencionCbxCondicionFisica").val($(xml).find('condicionFisica').text()); //combo
				}

				if($(xml).find('avisoHechoDelictivoDTO').text() != null && $(xml).find('avisoHechoDelictivoDTO').text() != ''){
					detencionTxtNombreSP =
						$(xml).find('usuario').find('funcionario').find('nombreFuncionario').text() + ' ' 
						+ $(xml).find('usuario').find('funcionario').find('apellidoPaternoFuncionario').text() + ' '
						+ $(xml).find('usuario').find('funcionario').find('apellidoMaternoFuncionario').text(); 

					detencionTxtCargo = $(xml).find('usuario').find('funcionario').find('puesto').find('valor').text();
					detencionTxtAreaAdm = $(xml).find('usuario').find('areaActual').find('nombre').text();
					detencionTxtFechaElaboracion = $(xml).find('fechaCreacion').text();
					$("#detencionTxtCoordinadorAMP").val($(xml).find('coordinadorAMP').text());
					$("#detencionTxtCoordinadorDP").val($(xml).find('coordinadorDP').text());
					idAvisoHechoDelitivo = $(xml).find('documentoId').first().text();
				}
										
			}
		});		
	}

	function armaComboProbableResponsable(){
		$('#detencionCbxProbableResponsable').attr('selectedIndex',0);
		$('#detencionCbxProbableResponsable').empty();
		$('#detencionCbxProbableResponsable').append('<option value="-1">-Seleccione-</option>');
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarProbablesResponsablesDetenidosCombo.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('involucradoDTO').each(function(){
					$('#detencionCbxProbableResponsable').append('<option value="' + $(this).find('elementoId').first().text() + '">' + $(this).find('nombre').first().text() + " " 
							+ $(this).find('apellidoPaterno').first().text() + " " + $(this).find('apellidoMaterno').first().text()
							+ '</option>');
					});
			}
		});
		
		//Recargar grid de pertenencias
		cargaGridPertenencias();
	}

	function cambiaNombreDesconocido(){
		if($("#esDesconocido").is(':checked')){
			$('#nombre').val('Desconocido');
			$('#apellidoPaterno').val('');
			$('#apellidoMaterno').val('');
			$('#nombre').attr('disabled','disabled');
			$('#apellidoPaterno').attr('disabled','disabled');
			$('#apellidoMaterno').attr('disabled','disabled');			
		}else{
			$('#nombre').val('');
			$('#nombre').attr('disabled','');
			$('#apellidoPaterno').attr('disabled','');
			$('#apellidoMaterno').attr('disabled','');
		}
	}

	function enviarAUnidadDeCaptura(){
		/*$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/enviarAUnidadDeCaptura.do?numeroExpediente='+numeroExpediente+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				alertDinamico("Informe Policial Homologado generado de manera correcta");
			}
		});*/
		$('#btnEnviarUnidadCaptura').hide();
		$('#btnMostrarIPH').show();				
	}

	function mostrarIPH(){
	}
	

	/*
	*Consulta el aviso hecho delictivo con el que se creo el expediente
	*/
	function consultaAviso(avisoId){
		  $.ajax({
			type: 'POST',
			url:  '<%= request.getContextPath()%>/consultarAvisoAuxilioXIdSSPPolicia.do?avisoId='+avisoId+'',
			dataType: 'xml',
			async: false,
			success: function(xml){				
			    $('.jquery_ckeditor').val($(xml).find('avisoHechoDTO').find('hechoDTO').find('descNarrativa').text());
		    }
	    });
	}



	
//*******************************************************COMIENZA FUNCIONALIDAD DE LA CEJA HECHOS****************************************************/

	/*
	*Guarda la descripcion del hecho, cuando se genera el expediente desde el boton de Detencion
	*SIN aviso hecho delictivo
	*/
	function guardarDescripcionDelHecho(){

		var descripcionDelHecho = escape($('.jquery_ckeditor').val());
			 
		$.ajax({
			type: 'POST',
			url:  '<%= request.getContextPath()%>/modificarHecho.do?idHecho='+hechoId+'&gcDescripcionHecho='+descripcionDelHecho+'&expedienteId='+expedienteId+'',
			dataType: 'xml',
			async: false,
			success: function(xml){

				errorCode=$(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){				
					$('.jquery_ckeditor').val($(xml).find('hechoDTO').find('descNarrativa').text());
					customAlert("La descripci\u00f3n del hecho, fue guardada correctamente");
    			}else{
    				customAlert("No fue posible guardar la descripci\u00f3n del hecho");
            	}
			}
		});
	}


	/*
	*Consulta el HECHO delictivo con el que se creo el expediente
	*/
	function consultaHecho(){
		  $.ajax({
			type: 'POST',
			url:  '<%= request.getContextPath()%>/consultarHecho.do?&expedienteId='+expedienteId+'',
			dataType: 'xml',
			async: false,
			success: function(xml){				
				errorCode=$(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){				
					$('.jquery_ckeditor').val($(xml).find('hechoDTO').find('descNarrativa').text());
					hechoId = $(xml).find('hechoDTO').find('hechoId').first().text();

					
						detencionTxtNombreSP =
							$(xml).find('usuario').find('funcionario').find('nombreFuncionario').text() + ' ' 
							+ $(xml).find('usuario').find('funcionario').find('apellidoPaternoFuncionario').text() + ' '
							+ $(xml).find('usuario').find('funcionario').find('apellidoMaternoFuncionario').text(); 

						detencionTxtCargo = $(xml).find('usuario').find('funcionario').find('puesto').find('valor').text();
						detencionTxtAreaAdm = $(xml).find('usuario').find('areaActual').find('nombre').text();
						detencionTxtFechaElaboracion = "";
    			}else{
    				//customAlert("No fue posible cargar el hecho");
            	}
		    }
	    });
	}
	

	/*
	*Funcion que inicializa el editor de texto
	*/
	function iniciarEditorTexto(){

		var config = {toolbar:
			[
				['Source','-','Cut','Copy','Paste','-','Undo','Redo','-','Find','Replace','-','RemoveFormat'],
				['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
				['NumberedList','BulletedList','-','Outdent','Indent'],
				['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
				['Table','HorizontalRule'],
				'/',
				['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize']
			],
			height:'350px'
		};

		$('.jquery_ckeditor').ckeditor(config);
	}

	//*******************************************************TERMINA FUNCIONALIDAD DE LA CEJA HECHOS****************************************************/
	
	
	//*******************************************************COMIENZA FUNCIONALIDAD DE LA CEJA ACTUACIONES**********************************************/
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones() {
		
		var id=null;
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+numeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#actuacionesDetencion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});            
			}
		});
	}
	
	
	
	
	
	
	//*******************************************************TERMINA FUNCIONALIDAD DE LA CEJA ACTUACIONES**********************************************/
	
	
	//*******************************************************COMIENZA FUNCIONALIDAD DE LA CEJA DOCUMENTOS**********************************************/
	
	//Variable para la carga de documentos
	var primeraVezDocumentos = true;

	/*
	*Funcion para consultar los documentos asociados al expediente
	*/
	function documentos(){
		if(primeraVezDocumentos == true){
			//Lanzamos la consulta de los documentos
			jQuery("#gridDocumentosExpediente").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarDocumentos.do?expedienteId='+expedienteId, 
				datatype: "xml", 
				colNames:['Folio','&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento','Documento','Es Parcial'],
				colModel:[ 	{name:'folio',index:'folio', width:80},
				           	{name:'area',index:'area', width:200,hidden:true},
							{name:'FechaActividad',index:'fechaActividad', width:170},							
							{name:'NombreActividad',index:'nombreActividad', width:400},
				           	{name:'Tipo',index:'tipo', width:155}, 
							{name:'Nombre',index:'nombre', width:255},
				           	{name:'Fecha',index:'fecha', width:170},
					     	{name:'Documento',index:'documento', hidden:true},
				           	{name:'EsParcial',index:'esParcial', hidden:true}
						],
				pager: jQuery('#pagerGridDocumentosExpediente'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				width:800,
				height:250,
				sortname: 'Nombre',
				viewrecords: true,
				id: 'divgrid',
				onSelectRow: function(id){
					
					var retd = jQuery("#gridDocumentosExpediente").jqGrid('getRowData',id);
					var titulo = retd.Nombre;
					if(retd.EsParcial){
						noEsParcial = retd.EsParcial.indexOf('false');
						if(noEsParcial > 0){//"No es parcial"
							consultaPDF(id);
						}
						else{//"Es parcial"
							abreVisorRegistroDetencion();			     			
						}
					 }

				},
				sortorder: "desc"
			}).navGrid('#pagerGridDocumentosExpediente',{edit:false,add:false,del:false});
			primeraVezDocumentos = false;
		}else{
			jQuery("#gridDocumentosExpediente").jqGrid('setGridParam',
					{url:'<%=request.getContextPath()%>/consultarDocumentos.do?expedienteId='+expedienteId, 
			datatype: "xml" });
			$("#gridDocumentosExpediente").trigger("reloadGrid");
		}
	}

	/*
	*Consulta los pdf del documentos
	*/
	function consultaPDF(id){
		document.frmDoc.documentoId.value = id;
		document.frmDoc.submit();
	}
	
	
	/*
	 *Para enviar el registro de detencion
	 */
	function abreVisorRegistroDetencion(){	
		
		//Se registra actividad asociada al expediente
		registrarActividadExpediente(<%= Actividades.SOLICITAR_DEFENSOR.getValorId()%>,"",0);
		
		var tituloVentanaGenerarAvisoDetencion = "Generar aviso de detención";
		var formaID = <%=Formas.SOLICITUD_DEFENSOR.getValorId()%>;		
		
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:20,posy:20,width:1140,height:400,title:""+tituloVentanaGenerarAvisoDetencion, type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&enviarAvisoDetencionSSP=true&idInvolucrado='+idInvolucrado+'" width="1140" height="400" />');		
	}

	/*
	*Registra la actividad de expediente
	*/
	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje,funcionario){
		
		var idFuncionario;
		if ( funcionario === undefined ) {
			idFuncionario = '';
		}else{
			idFuncionario=funcionario;
		}
		
		var idNumeroExpedienteOp = 0;
		
		//Cambia la actividad del expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+expedienteId+'&idNumeroExpediente='+idNumeroExpedienteOp+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+numeroExpediente+'&cveFuncionarioAsignado='+idFuncionario,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					alertDinamico("Actividad nueva registrada");	
				}
			}
		});
	}
	
	
	
	
	//*******************************************************COMIENZA FUNCIONALIDAD DE LA CEJA DOCUMENTOS**********************************************/

</script>

</head>

<body>
<div id="dialog-Alert" style="display: none">
<table>
<tr>
<td>
<span id="divAlertTexto"></span>
</td>
</tr>
</table>	
</div>

<input type="button" value="Enviar a Unidad de Captura" id="btnEnviarUnidadCaptura" class="back_button" align="right"/>	
<p>				

	<div id="tabsconsultaprincipal">
		<ul>
			<li id="tabDetencion"><a href="#tabsconsultaprincipal-2">Detenci&oacute;n</a></li>
			<li id="tabRegistroPertenencias"><a href="#tabsconsultaprincipal-1">Registro de Pertenencias</a></li>
			<li id="tabHechos"><a href="#tabsconsultaprincipal-3">Descripción del Hecho</a></li>
			<li id="tabActuaciones"><a href="#tabsconsultaprincipal-4">Actuaciones</a></li>
			<li id="tabDocumentos"><a href="#tabsconsultaprincipal-5" onclick="documentos()">Documentos</a></li>
		</ul>
			
		<!--COMIENZA TAB DE REGISTRO DE PERTENENCIAS-->
		<div id="tabsconsultaprincipal-1" class="tabRegistroPertenencias">
			<fieldset style="width: 900px;">
				<legend>Pertenencias</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td align="right"><bean:message key="probableResponsable"/>:</td>
						<td align="left">						
							<select id="detencionCbxProbableResponsable" style="width: 180px;" tabindex="1" onchange="cargaGridPertenencias()">
								<option value="-1">- Seleccione -</option>
							</select>
						</td>
						<td align="right">Descripci&oacute;n:</td>
						<td style="width: 200px;"><input type="text" style="width: 180px;" maxlength="30" id="detencionTxtDescripcion" tabindex="4"/></td>						
					</tr>
					<tr>
						<td align="right" >Tipo de Pertenencia:</td>
						<td style="width: 200px;">
							<select id="detencionCbxTipoPertenencia" style="width: 180px;" tabindex="2">
								<option value="-1">- Seleccione -</option>
							</select>
						</td>					
						<td align="right">Cantidad:</td>
						<td style="width: 200px;"><input type="text" style="width: 50px;" maxlength="5" id="detencionTxtCantidad" tabindex="5"/></td>
					</tr>
					<tr>
						<td align="right">Condici&oacute;n F&iacute;sica:</td>
						<td style="width: 200px;">
							<select id="detencionCbxCondicionFisica" style="width: 180px;" tabindex="3">
								<option value="-1">- Seleccione -</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="4">
							<input type="button" value="Agregar Pertenencia" id="btnAgregarPertenencia" class="back_button" tabindex="6"/>
							&nbsp;
							<!--<input type="button" value="Cancelar Pertenencia" id="btnCancelarPertenencia" class="back_button" tabindex="7"/>-->
						</td>					
					</tr>		
					<tr>
						<td colspan="4"><br></td>
					</tr>
					<tr>
						<td colspan="4" align="center">
		            		<table id="gridPertenencias"></table>
		            		<div id="pagerGridPertenencias"></div>
		            	</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<!--TERMINA TAB DE REGISTRO DE PERTENENCIAS-->
		
		<!--COMIENZA TAB DE DETENCION-->
		<!--<form id="profileForm" class="actionForm" method="get">-->
		<div id="tabsconsultaprincipal-2" class="tabDetencion">
			<fieldset style="width: 900px;">
				<legend>Descripci&oacute;n</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td align="right" style="width: 200px;">Tipo de Evento:</td>
						<td>
							<select id="detencionCbxTipoEvento" onchange="buscaSubTipoEvento();" tabindex="1">
								<option value="-1">- Seleccione -</option>
								<option value="1">Delito</option>
								<option value="2">Falta Administrativa</option>
							</select>
						</td>
						<td align="right">Subtipo de Evento:</td>
						<td>
							<select id="detencionCbxSubtipoEvento" style="width: 180px;" tabindex="2">
								<option value="-1">- Seleccione -</option>
							</select>
						</td>							
					</tr>
				</table>
			</fieldset>
			<br>
			<fieldset style="width: 900px;">
				<legend><bean:message key="plProbalbeResponsableTitulo"/> Detenidos y No Detenidos</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td align="right">Nombre(s):</td>
						<td><input type="text" style="width: 120px;" maxlength="30" id="nombre" tabindex="3" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
						<td align="right">¿Es detenido?:</td>
						<td><input type="checkbox" id="estaDetenido" tabindex="6"/></td>
						<td align="right">¿Es menor de edad?:</td>
						<td><input type="checkbox" id="esMenorDeEdad" tabindex="9"/></td>						
					</tr>
					<tr>
						<td align="right">Apellido Paterno:</td>
						<td><input type="text" style="width: 120px;" maxlength="30" id="apellidoPaterno" tabindex="4" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>					
						<td align="right">Fecha de detenci&oacute;n:</td>
						<td><input type="text" style="width: 70px;" maxlength="8" id="fechaInicioLapso" class="dateRange" disabled="disabled" tabindex="7"/></td>
						<td align="right">¿No proporcion&oacute; nombre?:</td>
						<td><input type="checkbox" id="esDesconocido" tabindex="10" onclick="cambiaNombreDesconocido();"/></td>						
					</tr>
					<tr>
						<td align="right">Apellido Materno:</td>
						<td><input type="text" style="width: 120px;" maxlength="30" id="apellidoMaterno" tabindex="5" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
						<td align="right">Hora de detenci&oacute;n:</td>
						<td><input type="text" style="width: 70px;" maxlength="8" id="horaInicioLapso" class="timeRange" disabled="disabled" tabindex="8"/></td>						
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td align="right" colspan="2">
							<input type="button" value='<bean:message key="ingProbaleResponsable"/>' id="btnAgregarProbableResponsable" class="back_button" tabindex="11"/>
						</td>
						<td align="left" colspan="2">
							<input type="button" value='<bean:message key="elimProbaleResponsable"/>' id="btnEliminarProbableResponsable" class="back_button" tabindex="12"/>
						</td>
						<td>&nbsp;</td>						
					</tr>
					<tr>
						<td colspan="6"><br></td>
					</tr>
					<tr>
						<td colspan="6" align="center">
		            		<table id="gridProbableResponsable"></table>
		            		<div id="pagerGridProbableResponsable"></div>
		            	</td>
					</tr>
				</table>
			</fieldset>

			<table border="0" height="90%" style="width: 900px;">
				<tr>
					<td align="left" style="width: 200px;"><input type="button" value="Mostrar IPH" id="btnMostrarIPH" class="back_button" tabindex="14"/></td>
				</tr>
			</table>
		</div>
		<!--</form>-->
		<!--TERMINA TAB DE DETENCION-->		
		
		
		
		<!--INICIA TAB DE HECHOS-->
		<div id="tabsconsultaprincipal-3">		
			<table width="900px" height="350px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<input type="button" value="Guardar Descripci&oacute;n" id="guardarDescripcionHecho" class="back_button" onclick="guardarDescripcionDelHecho();"/>
					</td>
				</tr>
				<tr>
					<td>
						<textarea class="jquery_ckeditor" cols="160" rows="25" id="hechosRegistroDetencion" disabled="disabled"></textarea>
					</td>
				</tr>
			</table>			
		</div>
		<!--TERMINA TAB DE HECHOS-->
		
		<div id="tabsconsultaprincipal-4">
			<fieldset style="width: 650px;">
				<legend>Actuaciones</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right" style="width: 200px;">Actuaci&oacute;n:</td>
							<td>
								<select id="actuacionesDetencion" style="width: 180px;">
									<option value="">- Seleccione -</option>
								</select>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
			</fieldset>
		</div>
		<!--TERMINA TAB DE ACTUACIONES-->
		
		<!--INICIA TAB DOCUMENTOS-->
		<div id="tabsconsultaprincipal-5">		
			<table id="gridDocumentosExpediente" width="100%"></table>
			<div id="pagerGridDocumentosExpediente"></div>
			
			<form name="frmDoc"	action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do" method="post">
				<input type="hidden" name="documentoId" />
			</form>	
		</div>
		<!--TERMINA TAB DE DOCUMENTOS-->
		
				
	</div>
</body>
</html>
