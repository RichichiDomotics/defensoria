/**
 * Enable JC. Funciones necesarias para compartir solicitudes en UAVD
 */

var apseFuncionarioId = 0;
var gridAPSEGenerado = false;
var areaActualFunc = 0;

//Función para alertDinamico
function alertDinamico(textoAlert, id){
    $("#divAlertTexto").html(textoAlert);
    $( "#dialog-Alert" ).dialog({
		autoOpen: true,
		resizable: false,
		//height:290,
		//width:300,
		modal: true,
		buttons: {

			"Aceptar": function() {
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");
			}
		}
	});
 }

function initAsignarPermisosSobreSolicitudCoordinador(areaActual, idFuncionario, estatusSolicitud, tiposSolicitudes){
	areaActualFunc = areaActual;
	apseFuncionarioId = idFuncionario;
	crearGridSubordinadosUAVD();
	crearGridSolicitudesPropiasCoordinador(estatusSolicitud, tiposSolicitudes);
	toggleFormaAsignarPermisosSol();
}

function initAsignarPermisosSobreSolicitudSubAreas(areaActual, idFuncionario, idArea, estatusSol, idsTiposSolicitudes, cadenaArea){
	areaActualFunc = areaActual;
	apseFuncionarioId = idFuncionario;
	crearGridSubordinadosUAVD();
	crearGridSolicitudesPropiasPsicJurSoc(idArea, estatusSol, idsTiposSolicitudes, cadenaArea);
	toggleFormaAsignarPermisosSol();
}

/**
 * Carga el grid de solicitudes propias del COORDINADOR UAVD.
 * Tales solicitudes estan disponibles para compartir.
 * Incluye solicitudes que le llegan de otras areas y las solicitudes que le fueron compartidas.
 * @param estatusSolicitud
 * @param tiposSolicitudes
 */
function crearGridSolicitudesPropiasCoordinador(estatusSolicitud, tiposSolicitudes){
	var gridName = "expedientesPropios";
	var idsTiposSolicitudes = tiposSolicitudes;
	var liga = contextPath+'/CargarGridTodasSolicitudesCoordinador.do?tipoSoliciutd='+idsTiposSolicitudes+'&idArea=0&estatus='+estatusSolicitud;

	var idGridTable = "gridExpedientesPropios";
	var idPagerDiv = "pagerExpedientesPropios";

	var headers = ['Solicitud','Fecha de Solicitud', 'Hora de Solicitud','V&iacute;ctima','Delito','&Aacute;rea que Solicita'];

	var columnas =[{name:'Caso',index:'Caso', width:80,align:'center'},
                   {name:'Fecha',index:'fecha', width:120,align:'center'},
                   {name:'Hora',index:'hora', width:100,align:'center'},
                   {name:'Prob',index:'prob', width:200,align:'center'},
                   {name:'Delito',index:'delito', width:200,align:'center'},
                   {name:'Quien',index:'quien', width:250,align:'center'}];
	crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 6, toggleFormaAsignarPermisosSol);
}

function crearGridSolicitudesPropiasPsicJurSoc(idArea, estatusSolicitud, idsTiposSolicitudes, cadenaArea){
	var gridName = "expedientesPropios";
	var liga = contextPath+'/CargarGridTodasSolicitudesSubAreas.do?idArea='+idArea+'&estatus='+estatusSolicitud+'&tipoSoliciutd='+idsTiposSolicitudes+'&cadenaArea='+cadenaArea;

	var idGridTable = "gridExpedientesPropios";
	var idPagerDiv = "pagerExpedientesPropios";

	var headers = ['Expediente','Fecha', 'Víctima','Delito'];

	var columnas =[{name:'Expediente',index:'1', width:200, align:'center'},
					{name:'Fecha',index:'2', width:120, align:'center'},
					{name:'Denunciante',index:'3', width:260, align:'center'},
					{name:'Delito',index:'4', width:250, align:'center'}];
	crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 6, toggleFormaAsignarPermisosSol);
}

function toggleFormaAsignarPermisosSol(){
	$("#filaFormaAsignarPermisos").show();
	$("#filaFormaModificarPermisos").hide();
	document.formaAgregar.reset();
	jQuery("#gridExpedientesFuncionario").jqGrid('resetSelection');

}

function crearGridSubordinadosUAVD(){
	var gridName = "subordinados";
	var parametros = "claveFuncionario="+apseFuncionarioId;
	//var liga = contextPath+'/CargarGridSubordinados.do?'+parametros;
	var liga = contextPath+'/CargarGridSubordinadosUAVD.do?'+parametros;
	var idGridTable = "gridSubordinados";
	var idPagerDiv = "pagerSubordinados";
	var headers = ['Nombre del Funcionario', '\u00c1rea'];

	var columnas =[
	                {name:'nombre',		index:'2101', width:155, align:"center"},
					{name:'area',		index:'2106', width:200, align:"center"}
				  ];
	crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 6, crearGridSolicitudesFuncionarioClon);
}

function crearGridSolicitudesFuncionarioClon()
{
	var rowd = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	if (rowd) {
		var gridName = "expedientesFuncionario";
		var parametros = "claveFuncionario="+rowd;
		var liga = contextPath+'/CargarGridSolicitudesCompartidasSubordinado.do?'+parametros;
		var idGridTable = "gridExpedientesFuncionario";
		var idPagerDiv = "pagerExpedientesFuncionario";
		var headers = "";

		if(gridAPSEGenerado){
			jQuery("#"+idGridTable).jqGrid('setGridParam', {url:liga, datatype: "xml" });
			$("#"+idGridTable).trigger("reloadGrid");
		}else{
			if(areaActualFunc == 12){
				headers = ['Solicitud', 'Fecha de vencimiento', 'Lectura', 'Escritura'];
			}else{
				headers = ['Expediente', 'Fecha de vencimiento', 'Lectura', 'Escritura'];
			}

			var columnas = [
			                {name:'nExpediente',	index:'2102', width:150, align:"center"},
				           	{name:'vencimiento',	index:'2103', width:150, align:"center"},
							{name:'permiso_Lec',	index:'2104', width: 75, align:"center"},
							{name:'permiso_Esc',	index:'2105', width: 75, align:"center"}
						   ];
			crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 6, toggleFormaModificarPermisosSol);
			gridAPSEGenerado = true;
		}
	}
	else
	{
		alertDinamico("Favor de seleccionar un renglón.");
	}
}

function crearGridSolicitudesFuncionario(idRow){
	var gridName = "expedientesFuncionario";
	var parametros = "claveFuncionario="+idRow;
	var liga = contextPath+'/CargarGridSolicitudesCompartidasSubordinado.do?'+parametros;
	var idGridTable = "gridExpedientesFuncionario";
	var idPagerDiv = "pagerExpedientesFuncionario";
	var headers = "";

	if(gridAPSEGenerado){
		jQuery("#"+idGridTable).jqGrid('setGridParam', {url:liga, datatype: "xml" });
		$("#"+idGridTable).trigger("reloadGrid");
	}else{
		if(areaActualFunc == 12){
			headers = ['Solicitud', 'Fecha de vencimiento', 'Lectura', 'Escritura'];
		}else{
			headers = ['Expediente', 'Fecha de vencimiento', 'Lectura', 'Escritura'];
		}

		var columnas = [
		                {name:'nExpediente',	index:'2102', width:150, align:"center"},
			           	{name:'vencimiento',	index:'2103', width:150, align:"center"},
						{name:'permiso_Lec',	index:'2104', width: 75, align:"center"},
						{name:'permiso_Esc',	index:'2105', width: 75, align:"center"}
					   ];
		crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 6, toggleFormaModificarPermisosSol);
		gridAPSEGenerado = true;
	}
}

function toggleFormaModificarPermisosSol(){
	$("#filaFormaAsignarPermisos").hide();
	$("#filaFormaModificarPermisos").show();
	document.formaModificar.reset();
	jQuery("#gridExpedientesPropios").jqGrid('resetSelection');

	var idSubordinado = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	var idNExpediente = jQuery("#gridExpedientesFuncionario").jqGrid('getGridParam','selrow');
	if(!isEmpty(idSubordinado) && !isEmpty(idNExpediente)){
		var expediente = jQuery("#gridExpedientesFuncionario").jqGrid('getRowData',idNExpediente);
		if(expediente.permiso_Esc=="SI"){
			$("#modEscritura").attr("checked","true");
		}else{
			$("#modEscritura").attr("checked","false");
		}
		$("#modFechaVencimiento").val(expediente.vencimiento);

	}

}

function asignarPermisoSobreSolicitudes(){
	var idSubordinado = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	var idSolicitud = jQuery("#gridExpedientesPropios").jqGrid('getGridParam','selrow');
	if(!isEmpty(idSubordinado) && !isEmpty(idSolicitud)){
		var param = "";
		param += "claveFuncionario="+idSubordinado;
		param += "&solicitudId="+idSolicitud;
		param += "&escritura="+$("#escritura").attr("checked");
		if(!isEmpty($("#fechaVencimiento").val())){
			param += '&fechaVencimiento=' + $("#fechaVencimiento").val();
			var accion = contextPath+'/AsignarPermisosSobreSolicitud.do';
			peticionSincronaAjaxXML(accion, param, apseSUCCESSSol);
		}else{
			alertDinamico("Debe de seleccionar una fecha de vencimiento para el permiso.");
		}
	}else{
		alertDinamico("Se debe seleccionar tanto un funcionario como un expediente propio para esta operación.");
	}
}

function modificarPermisoSobreSolicitudes(){
	var idSubordinado = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	var idSolicitud = jQuery("#gridExpedientesFuncionario").jqGrid('getGridParam','selrow');
	if(!isEmpty(idSubordinado) && !isEmpty(idSolicitud)){
		var param = "";
		param += "claveFuncionario="+idSubordinado;
		param += "&solicitudId="+idSolicitud;
		param += "&escritura="+$("#modEscritura").attr("checked");
		if(!isEmpty($("#modFechaVencimiento").val())){
			param += '&fechaVencimiento=' + $("#modFechaVencimiento").val();
			var accion = contextPath+'/AsignarPermisosSobreSolicitud.do';
			peticionSincronaAjaxXML(accion, param, apseSUCCESSSol);
		}else{
			alertDinamico("Debe de seleccionar una fecha de vencimiento para el permiso.");
		}
	}else{
		alertDinamico("Se debe seleccionar tanto un funcionario como un expediente asignado para esta operación.");
	}
}

function eliminarPermisoSobreSolicitudes(){
	var idSubordinado = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	var idSolicitud = jQuery("#gridExpedientesFuncionario").jqGrid('getGridParam','selrow');
	if(!isEmpty(idSubordinado) && !isEmpty(idSolicitud)){
		var param = "";
		param += "claveFuncionario="+idSubordinado;
		param += "&solicitudId="+idSolicitud;
		var accion = contextPath+'/EliminarPermisosSobreSolicitud.do';
		peticionSincronaAjaxXML(accion, param, apseSUCCESSSol);
	}else{
		alertDinamico("Se debe seleccionar tanto un funcionario como un expediente asignado para esta operación.");
	}
}

function apseSUCCESSSol(xml){
	alert($(xml).find("body").text());
	document.formaAgregar.reset();
	document.formaModificar.reset();
	var idSubordinado = jQuery("#gridSubordinados").jqGrid('getGridParam','selrow');
	crearGridSolicitudesFuncionario(idSubordinado);
}