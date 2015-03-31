<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jsTree/jquery.jstree.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
<script type="text/javascript">
var op=0;
var rolPadre=0;
$(document).ready(function() {
	gridRoles();
	jQuery("#tabsConfSubRol").tabs();

});

function gridRoles(){
	jQuery("#lstRoles").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarCatalogoRoles.do', 
			datatype: "xml", 
			colNames:['Id No','Nombre Rol', 'Descripción','Acciones'], 
			colModel:[ {name:'id',index:'id', width:5, hidden:true}, 
			           {name:'nombre',index:'nombre', width:10}, 
			           {name:'desc',index:'desc', width:10},
			           {name:'act',index:'act', width:5,sortable:false}],
			rowNum:20, 
			autowidth: true,
			width: "auto",
			height: "auto",
			rowList:[10,20,30], 
			pager: jQuery('#pagRoles'), 
			sortname: 'id', 
			viewrecords: true,
			sortorder: "desc",
			subGrid: true,
			caption:"Roles en Sistema",
			subGridRowExpanded: function(subgrid_id, row_id) { 
				var subgrid_table_id, pager_id; 
				subgrid_table_id = subgrid_id+"_t"; 
				pager_id = "p_"+subgrid_table_id; 
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				jQuery("#"+subgrid_table_id).jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarCatalogoSubRoles.do?idRol='+ row_id,
					datatype: "xml", 
					colNames:['Id No','Nombre SubRol', 'Descripción','Acciones'], 
					colModel:[ {name:'id',index:'id', width:15, hidden:true}, 
					           {name:'nombre',index:'nombre', width:30}, 
					           {name:'desc',index:'desc', width:30},
					           {name:'act',index:'act', width:10,sortable:false}], 
					rowNum:20, 
					autowidth: true,
					width: "auto",
					pager: pager_id, 
					sortname: 'id',
					ondblClickRow: function(id){
						rolPadre=row_id;
						editarSubRol(id);
					},
					sortorder: "asc",
					height: '100%' 
					}); 
				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false}) 
				}, 
				subGridRowColapsed: function(subgrid_id, row_id) { 
					}
	}).navGrid('#pagRoles',{edit:false,add:false,del:false}); 
	}
//INICIO Funciones para EDITAR un SUB ROL
function editarSubRol (id){
	popupEditarSubRol(id);
}
function popupEditarSubRol(id){
	$( "#dialog-subRol" ).dialog({
		resizable: false,
		height:200,
		width:400,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function() {
				$( this ).dialog( "close" );
				op=2;
				guardarSubRol(id);
				
				
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				
				
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
//FIN Funciones para EDITAR un SUB ROL
//Inicio Funciones para Agregar un Sub Rol
function agregarNuevoRol(id){
	popupSubRol(id);
}
function popupSubRol(id){
	$( "#dialog-subRol" ).dialog({
		resizable: false,
		height:200,
		width:400,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function() {
				$( this ).dialog( "close" );
				op=1;
				guardarSubRol(id);
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				
				
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
function validaTexto (){
	var nameSubRol=$('#nameSubRol').val();
	var decSubRol=$('#decSubRol').val();
	var s = nameSubRol;
	var t = decSubRol;
	var resp=false;
	var mPsw=/^[A-Za-z0-9!@#$%^&*()_]{1,50}/;
	var mPsw2=/^[A-Za-z0-9]/;
	var mPsw3=/^[A-Za-z0-9!@#$%^&*()_]{1,200}/;
	if (mPsw.test(s) && mPsw3.test(t)){
		if (mPsw2.test(s) && mPsw2.test(t)){
			resp=true;	
		}else{
			customAlert("No se adminten caracteres especiales tales como !@#$%^&*()_, favor de verificar","Error");	
		}
	}else{
		customAlert ("El nombre y/o descripción del sub rol NO puede estar vacío y <br> El nombre NO puede ser mayor a 50 caracteres <br> La descripción NO puede ser mayor a 200 caracteres,<br>favor de verificar","Error");
	}
	return resp;
}
function guardarSubRol(id)
{
	var nameSubRol=$('#nameSubRol').val();
	var decSubRol=$('#decSubRol').val();
	if (validaTexto())
	{
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/guardarSubRol.do',
			data: 'nameSubRol='+nameSubRol+'&decSubRol='+decSubRol+'&rolSelect=' + id + '&op='+op,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var resp=$(xml).find('resp').text();
				if (resp=='exito'){
					if (op=='1'){
						jQuery("#lstRoles_"+ id +"_t").trigger("reloadGrid");
						customAlert("Sub rol agregado con éxito","Operación Exitosa");
						
					}else if (op=='2'){
						jQuery("#lstRoles_"+ rolPadre +"_t").trigger("reloadGrid");
						customAlert("Sub rol modificado con éxito","Operación Exitosa");
						
					}
					
				}else if (resp=='nombreVacio'){
					customAlert ("El nombre del sub rol NO puede estar vacío, favor de verificar","Error");	
				}else if (resp=='caracteresEspeciales'){
					customAlert("No se adminten caracteres especiales tales como !@#$%^&*()_, favor de verificar","Error");		
				}else if (resp=='nombreRepetido'){
					customAlert("El nombre del sub rol ya ha sido utilizado, favor de verificar","Error");
				}else if (resp=='error'){
					customAlert("El sub rol no fue agregado, favor de verificar","Error");	
				}
				
			destruirArboles();
			}// Cierra success
		});// Cierra Ajax
	}//Cierra if (validaNombre(nameSubRol))
	
}// Cierra función
// FIN Funciones para Agregar Nuevo Rol
// Inicio Funciones para configurar un sub rol
function configurarSubRol(id,idPadre){
	popupConfSubRol(id,idPadre);
}	
function popupConfSubRol(id,idPadre){
	$( "#dialog-confSubRol" ).dialog({
		resizable: false,
		height:600,
		width: 800,
		modal: true,
		position: "top",
		closeOnEscape: false,
		buttons: {
			"Guardar": function() {
				$( this ).dialog( "close" );
				guardarConfSubRol(id);
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				destruirArboles();
			}
		}
	});
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/obtenerConfMenu.do',
		data: 'idRol='+id +'&idRolPadre='+idPadre+'&posicion='+"0",
		dataType: 'html',
		async: false,
		cache: false,
		success: function(data){
			jQuery('#menuVestri').empty();
			jQuery('#menuVestri').html(data);
			$("#menuVestri").jstree({
				"strings":{
					"loading":  "Cargando..."
				},
	        	"themes" : {
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
                "plugins" : [ "themes", "html_data", "ui", "checkbox" ]
        });
			
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
	

	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/obtenerConfMenu.do',
		data: 'idRol='+id +'&idRolPadre='+idPadre+'&posicion='+"1",
		dataType: 'html',
		async: false,
		cache: false,
		success: function(data){
			jQuery('#menuNordri').empty();
			jQuery('#menuNordri').html(data);
			$("#menuNordri").jstree({
				"strings":{
					"loading":  "Cargando..."
				},
	        	"themes" : {
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
                "plugins" : [ "themes", "html_data", "ui", "checkbox" ]
        });
			
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
	$("#lstActuaciones").GridUnload();
	jQuery("#lstActuaciones").jqGrid({ 
		url:'<%=request.getContextPath()%>/consultarCatalogoActuacionesPorRol.do?idRol='+id, 
		data: '',
		datatype: "xml", 
		colNames:['Id No','Estado Inicial', 'Estado Final', 'Nombre Actuacion','esSeleccionado'], 
		colModel:[ {name:'id',index:'id', width:10, hidden:true}, 
		           {name:'edoIni',index:'edoIni', width:40}, 
		           {name:'edoFin',index:'edoFin', width:40},
		           {name:'nombre',index:'nombre', width:100},
		           {name:'esSel',index:'esSel', width:50, hidden:true}],
		loadComplete: function(){
			var ids=jQuery("#lstActuaciones").jqGrid('getDataIDs');
			for (var i=0;i < ids.length;i++){
				var cl = ids[i]; 
				var ret = jQuery("#lstActuaciones").jqGrid('getRowData',cl);
				if(ret.esSel=='true'){
					jQuery("#lstActuaciones").jqGrid('setSelection',cl);
				}
			
			}			
		},
		rowNum:20, 
		width: 750,
		height: "100%",
		rowList:[10,20,30], 
		pager: jQuery('#pagActuaciones'), 
		sortname: 'id', 
		viewrecords: true, 
		sortorder: "desc",
		multiselect: true,
		caption:"Actuaciones" 
}).navGrid('#pagActuaciones',{edit:false,add:false,del:false}); 
	
}
function destruirArboles(){
	$("#menuVestri").jstree("destroy");
	jQuery('#menuVestri').empty();
	$("#menuNordri").jstree("destroy");
	jQuery('#menuNordri').empty();
	$("#nameSubRol").val("");
	$("#decSubRol").val("");
	
	
}
function guardarConfSubRol(idRol){
	var checked_ids = []; 
	var s; 
	
	//Guarda los seleccionados y los padres PARCIALmente seleccionados
      $("#menuVestri").find(".jstree-undetermined").each(function(i,element){            
    	    checked_ids.push($(element).attr("id"));
    	    
    	    if ($(this).find(".jstree-undetermined").length == 0) {  
    	        $(this).find(".jstree-checked").each(function(i, element){
    	        	checked_ids.push($(element).attr("id"));
    	        });         
    	    }
    
    	});
      //Guarda todos los que fueron seleccionados
      $("#menuVestri").jstree("get_checked",null,true).each 
      (function () { 
          checked_ids.push(this.id); 
      });
    //Guarda los seleccionados y los padres PARCIALmente seleccionados
      $("#menuNordri").find(".jstree-undetermined").each(function(i,element){            
    	    checked_ids.push($(element).attr("id"));
    	    
    	    if ($(this).find(".jstree-undetermined").length == 0) {  
    	        $(this).find(".jstree-checked").each(function(i, element){
    	        	checked_ids.push($(element).attr("id"));
    	        });         
    	    }
    
    	});
      //Guarda todos los que fueron seleccionados
      $("#menuNordri").jstree("get_checked",null,true).each 
      (function () { 
          checked_ids.push(this.id); 
      });
      s = jQuery("#lstActuaciones").jqGrid('getGridParam','selarrrow');
      $.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/guardarConfiguracionSubRol.do',
			data: 'lstEM='+checked_ids + '&idRol='+idRol + '&lstAct='+s,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var op=$(xml).find('boolean').text();
				if (op=='true'){
				customAlert("Configuración guardada con éxito","Operación Exitosa");
				}else{
					customAlert("Error la configuración no fue guardada","Error");	
				}
				destruirArboles();
			}
		});
	}
//FIN Funciones para guardar configuración SubRol
	
</script>
</head>
<body>
<table id="lstRoles"></table>
<div id="pagRoles"></div>
</body>
<!-- Inicio dialogos para Sub Rol-->
<!-- Inicio Agregar Sub Rol -->
	<div id="dialog-subRol" title="Guardar Sub Rol"  style="display: none;">
		<p align="center">
			<table border="0">
				<tr>
					<td align="right"><label style="color:#4A5C68">Nombre del Sub Rol:</label></td>
					<td><input type="text" name="name" id="nameSubRol" value="" maxlength="50" size="20"></td>
				</tr>
				<tr>
					<td align="right"><label style="color:#4A5C68">Descripción del sub Rol:</label></td>
					<td><input type="text" name="dec" id="decSubRol" value="" maxlength="50" size="20"></td>
				</tr>
			</table>
		</p>
	</div>
<!-- Fin Agregar Sub Rol -->
<!-- Fin del Dialogos para Sub Rol -->
<!-- Inicio del Dialogo para configuración de Sub Rol -->
<div id="dialog-confSubRol" title="Configurar Sub Rol"  style="display: none;">
<div id="tabsConfSubRol">
	<ul>
		<li><a href="#tabs-1">Menú Lateral Izquierdo</a></li>
		<li><a href="#tabs-2">Menú Superior</a></li>
		<li><a href="#tabs-3">Actuaciones</a></li>
	</ul>
	<div id="tabs-1">
		<div id="menuVestri">
		</div>
	</div>
	<div id="tabs-2">
		<div id="menuNordri">
		</div>
	</div>
	<div id="tabs-3">
	<table id="lstActuaciones"></table>
<div id="pagActuaciones"></div>
	</div>
</div>
</div>
<!-- Fin del Dialogo para configuración de Sub Rol -->
</html>