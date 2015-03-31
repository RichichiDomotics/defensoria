<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>



<script type="text/javascript">
	//URG - 005 
	var outerLayout, innerLayout;
	var idWindowNuevaDenuncia=1;
	/*variables atendiendo/*ByYolo*/
	var firstGridAsignacionesRecibidasEnProceso = true;
	var idExpedienteDoc;
	var numidExpedienteDoc;
	var numExpediente;
	var documentoID="";
	
	var idWindowVisorAntencionSolPer = 1;
	
	
	$(document).ready(function() {		
		$("#idExpedientes").css({ color: "#1C94C4"});
		$("#anio1").css({ color: "#1C94C4"});
		$("#A1").css({ color: "#1C94C4"});
		$("#A2").css({ color: "#1C94C4"});
		$("#A3").css({ color: "#1C94C4"});
		$("#anio2").css({ color: "#1C94C4"});
		$("#B1").css({ color: "#1C94C4"});
		$("#B2").css({ color: "#1C94C4"});
		$("#B3").css({ color: "#1C94C4"});
		
		
		$("#tbarBtnAsignarPermisosASubordinados").click(asigarPermisos);
				
		$("#justiciaAlterna-restaurativa").css({ color: "#1C94C4"});
		
		$("#unidad-imvestigacion").css({ color: "#1C94C4"});
		
		$("#controlAgenda").click(creaAgenda);
		
		$( "#tabsAtencionTempranaPenal" ).tabs();

		$("#exp0001").click(seleccionFila);	
		$("#exp0002").click(seleccionFila2);
		$("#restaura").click(gridRestaurativa);
		$("#unidadInvestiga").click(gridUnidadInvestigacion);
		
		///aqui va el grid principal
			jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarSolicitudesDirectorPericiales.do?estatusSolicitud=abierto', 
				datatype: "xml", 
				colNames:['Número Expediente','Número de Caso','Fecha de Solicitud','Fecha Última Modificación','Estatus','idExp','numExpid'],
				colModel:[ 	{name:'numeroExpediente',index:'1', width:220,align:'center'}, 
				           	{name:'numeroCaso',index:'2', width:220,align:'center'}, 
							{name:'fechaLimite',index:'3', width:150,align:'center'},
							{name:'fechaUltimaModificacion',index:'4', width:150,align:'center', hidden:true},
							{name:'documentoCreado',index:'5', width:160,align:'center'},
							{name:'idExpediente',index:'6', width:160,align:'center',hidden:true},
							{name:'numidExpediente',index:'7', width:160,align:'center',hidden:true}
							
						],
				pager: jQuery('#pager1'),
				rowNum:10,
				rowList:[10,20,30],
				width:767,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid){
//						detEvi(id);
 					var ret2 = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',rowid);
 					idExpedienteDoc=ret2.idExpediente;
 					numidExpedienteDoc=ret2.numidExpediente;
 					numExpediente = ret2.numeroExpediente;
 					mostrarVisorAtencionSolicitudesPericiales(rowid);
				},
				sortorder: "desc"
			}).navGrid('#pager1',{edit:false,add:false,del:false});	
		
			/*Inicio funciones/*ByYolo*/		
			function mostrarVisorAtencionSolicitudesPericiales(rowId){

				params = rowId.split(",");
				documentoId = params[1];
				solicitudId = params[0];

				idWindowVisorAntencionSolPer++;
				$.newWindow({id:"iframewindowVisorAtnSolPer"+idWindowVisorAntencionSolPer, statusBar: true, posx:150,posy:30,width:1200,height:600,title:"Atención de solicitudes periciales", type:"iframe"});
				$.updateWindowContent("iframewindowVisorAtnSolPer"+idWindowVisorAntencionSolPer,'<iframe src="<%= request.getContextPath() %>/visorSolicitudesDirectorPeritos.do?solicitudId='+solicitudId+'&documentoId='+documentoId+'" width="1200" height="600" />');				

			    $("#" +"iframewindowVisorAtnSolPer"+idWindowVisorAntencionSolPer + " .window-maximizeButton").click();	
		   }
		   
			
	/*Fin funciones/*ByYolo*/
		
		$("#divGridSolsXAtndr").hide();
		$("#divGridSolsGeneradas").hide();
		 $("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridDetalleExpediente .ui-jqgrid-bdiv").css('height', '410px');
		 $("#gview_gridSolsGeneradas .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridSolsXAtndr .ui-jqgrid-bdiv").css('height', '400px');
		 $("#gview_gridExpCompartidos .ui-jqgrid-bdiv").css('height', '400px');
		 $('#test').weatherfeed(['MXDF0132']);
		 	 
		restablecerPantallas();		 
	});
	
	/*Fin del funcion Ready/**/
	
	
		function restablecerPantallas(){
	 		ajustarGridAlCentro($("#gridDetalleFrmPrincipal")); 
			ajustarGridAlCentro($("#gridDetalleExpediente"));
			ajustarGridAlCentro($("#gridSolsXAtndr"));
			ajustarGridAlCentro($("#gridSolsGeneradas"));
		}

    var iframewindowAPSE = 0;
    
    function asigarPermisos(){
		$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="<%= request.getContextPath() %>/asigarPermisosExpediente.do" width="1430" height="670" />');
		$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
		iframewindowAPSE++;
    }
	
	//crea una nueva ventana para la agenda	
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();	
	}


    function seleccionFila(){

		$("#1").css({ color: "#FFFFFF", background: "#FBCB09" }); 
		$("#2").css({ background: "#dafafc", color: "#008000" });
			  
	}

	function seleccionFila2(){

		$("#2").css({ color: "#FFFFFF", background: "#FBCB09" }); 
		$("#1").css({ background: "#dafafc", color: "#FFA500" });
		   
	}


	function detEvi(id) {
	 	var pantallaSolicitada=61;
		idWindowNuevaDenuncia++;
		 var ingresoDenuncia = true;
		 var idIframe = "iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia;
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+id+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'" width="1430" height="670" />');
		//var numExpConsul='<%= request.getSession().getAttribute("numExpConsul")%>';
		//$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+numExpConsul);
		 $("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();
		}




	//Funcion ke rellena los datos del grid con los expedientes ke se encuentran en restaurativa
	function gridRestaurativa(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
			{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=ATENDER_', 
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}

	//Funcion ke rellena los datos del grid con los expedientes ke se encuentran en unidad de investigacion
	function gridUnidadInvestigacion(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
			{url:'<%=request.getContextPath()%>/BusquedaCanalizadosUnidadInvestigacion.do', 
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	
	 

	function solonumeros(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		// if the key is backspace, tab, or numeric
		if (unicode == 8 || unicode == 9 || (unicode >= 48 && unicode <= 57)) {
		// we allow the key press
		return true;
		}
		else {
		// otherwise we don't
		return false;
		}
		}
	
	/*************  FUNCION PARA ABRIR LA VENTANA DE CAMBIO DE PASSWORD *************/
	function abreVentanaModificarContrasena()
	{
		$.newWindow({id:"iframewindowModificarPwdUsuario", statusBar: true, posx:400,posy:90,width:380,height:280,title:"Modificar Contraseña", type:"iframe"});
	    $.updateWindowContent("iframewindowModificarPwdUsuario",'<iframe src="<%=request.getContextPath()%>/cambiarContrasena.do?administrador=4" width="380" height="280" />');
	}
	
	function cerrarVentanaModificarContrasena()
	{
		var pantalla ="iframewindowModificarPwdUsuario";
		$.closeWindow(pantalla);
	}
	function activaExpediente() {
		//$("#expedientePrincipal").css("display", "block");
		//$("#turnoPrincipal").css("display", "none");
		//jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
		//	{url:'<%=request.getContextPath()%>/BusquedaInicialTurnosGrid.do', 
		//	datatype: "xml" });
		 //$("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
		 //ocultaMuestraGrids("turnoPrincipal");
	}
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
	/*Modificando el comportamiento de arbol izquierdo/*ByYolo*/
	function busquedaExpedientesGridCoorAT(idTipoBusqueda){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
			{url:'<%=request.getContextPath()%>/consultarSolicitudesDirectorPericiales.do?tipoBusqueda='+idTipoBusqueda, 
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	
	function busquedaExpedientesGridCoorATAgencia(idTipoBusqueda,idAgencia){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
			{url:'<%=request.getContextPath()%>/consultarSolicitudesDirectorPericiales.do?tipoBusqueda='+idTipoBusqueda+'&idAgencia='+idAgencia, 
			datatype: "xml" });
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	/*Modificando el comportamiento de arbol izquierdo/*ByYolo*/
	function busquedaExpedientesGridCoorAT(idTipoBusqueda,idFuncionario){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
			{url:'<%=request.getContextPath()%>/consultarSolicitudesDirectorPericiales.do?tipoBusqueda='+idTipoBusqueda+'&idFuncionario='+idFuncionario, 
			datatype: "xml" });				
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	function popupAgencia(idTipoBusqueda){
		$('#cbxAgencias').empty();
		$('#cbxAgencias').append('<option value="-1">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoAgenciasPGJ.do',
			data: {
		    	tipoDiscriminante:"agencia"
		    },
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('listaCatalogo').find('catDiscriminante').each(function(){
					$('#cbxAgencias').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">'+ $(this).find('clave').first().text() +"-"+ $(this).find('nombre').first().text() +'</option>');
				});
				muestraPopupAgencia(idTipoBusqueda);
			}
		});
	}
	
	function muestraPopupAgencia(idTipoBusqueda){
		$( "#dialog-confirmAgencia" ).dialog({
			resizable: false,
			height:200,
			width:300,
			modal: true,
			buttons: {
				"Buscar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					var idAgencia=$("#cbxAgencias option:selected").val();
					if(idAgencia===-1){
						customAlert ("Seleccione una agencia.", "Aviso");
					}else{
						busquedaExpedientesGridCoorATAgencia(idTipoBusqueda,idAgencia);
					}				
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					//cancelarTurnoPRCAN();
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();		
	}
	
	function popupUsuario(idTipoBusqueda){
		var area=0;
		if(idTipoBusqueda===13){
			area="<%=Areas.SERVICIOS_PERICIALES_PG.parseLong()%>";
		}else{
			area="<%=Areas.SERVICIOS_PERICIALES_PG.parseLong()%>"
		}
		$('#cbxFuncionarios').empty();
		$('#cbxFuncionarios').append('<option value="-1">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarFuncionariosXArea.do',
			data: {
				areaId:area
			},
			dataType: 'xml',
			async: false,
			success: function(xml){
		    	$(xml).find('listaFuncionarios').find('funcionario').each(function(){

					var nombre="";
					var apPat="";
					var apMat="";
					
				    nombre = $(this).find('nombreFuncionario').first().text();
				    apPat = $(this).find('apellidoPaternoFuncionario').first().text();
				    apMat = $(this).find('apellidoMaternoFuncionario').first().text();
				    
			    	$('#cbxFuncionarios').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+nombre+" "+apPat+" "+apMat+'</option>');
		    	});
		    	muestraPopupUsuario(idTipoBusqueda);
			}
		});
	}
	
	function muestraPopupUsuario(idTipoBusqueda){
		$( "#dialog-confirmUsuarios" ).dialog({
			resizable: false,
			height:200,
			width:300,
			modal: true,
			buttons: {
				"Buscar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					var idFuncionario=$("#cbxFuncionarios option:selected").val();
					if(idFuncionario===-1){
						customAlert ("Seleccione un funcionario.", "Aviso");
					}else{
						busquedaExpedientesGridCoorAT(idTipoBusqueda,idFuncionario);	
					}
					
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					//cancelarTurnoPRCAN();
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		
	}
	
	/************* fin  FUNCION PARA ABRIR LA VENTANA DE CAMBIO DE PASSWORD *************/
</script>
<div id="dialog-confirmAgencia" title="Agencias: ">
		<p align="center">
			<span style="font-size: 25px;" id="tipoAgencia">Agencias</span><br/>
			<select id="cbxAgencias" style="width:200px">
				<option value="-1">-Seleccione-</option>
			</select>
		</p>
</div>

<div id="dialog-confirmUsuarios" title="Agencias: ">
		<p align="center">
			<span style="font-size: 25px;" id="tipoAgencia">Funcionarios</span><br/>
			<select id="cbxFuncionarios" style="width:200px">
				<option value="-1">-Seleccione-</option>
			</select>
		</p>
</div>	
<div id="mainContent">	
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div id="turnoPrincipal">
				<table id="gridDetalleFrmPrincipal"></table>
				<div id="pager1"></div>
			</div>
		<div>
	</div>
</div>
</div>
</div>