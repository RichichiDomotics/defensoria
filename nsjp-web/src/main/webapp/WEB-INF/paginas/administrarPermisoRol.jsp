<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
<script type="text/javascript">
var idRoles=0;
var rolSelect=0;
$(document).ready(function() {
hideTable();
idRoles=10;
gridRoles();
gridInitModulos();

});
function gridRoles(){
jQuery("#list1").jqGrid({ 
		url:'<%=request.getContextPath()%>/consultarCatalogoRoles.do', 
		datatype: "xml", 
		colNames:['Id No','Nombre', 'Descripción'], 
		colModel:[ {name:'id',index:'id', width:15}, 
		           {name:'nombre',index:'nombre', width:30}, 
		           {name:'desc',index:'desc', width:50}], 
		rowNum:10, 
		autowidth: true,
		width: 100,
		rowList:[10,20,30], 
		pager: jQuery('#pager1'), 
		sortname: 'id', 
		viewrecords: true, 
		onSelectRow: function(id){
			rolSelect=id;
			gridModulos(id);
		},
		sortorder: "desc", 
		caption:"Roles en Sistema" 
}).navGrid('#pager1',{edit:false,add:false,del:false}); 
}
function gridInitModulos(){
	jQuery("#list2").jqGrid({ 
		//url:'local', 
		datatype: "xml", 
		colNames:['Id No','Nombre', 'Descripción','esSeleccionado'], 
		colModel:[ {name:'id',index:'id', width:15}, 
		           {name:'nombre',index:'nombre', width:30}, 
		           {name:'desc',index:'desc', width:50},
		           {name:'esSel',index:'esSel', width:50, hidden:true}],
		loadComplete: function(){
		 	var ids=jQuery("#list2").jqGrid('getDataIDs');
		 	for (var i=0;i < ids.length;i++){
		 		var cl = ids[i]; 
		 		var ret = jQuery("#list2").jqGrid('getRowData',cl);
		 		if(ret.esSel=='true'){
		 			jQuery("#list2").jqGrid('setSelection',cl);
		 		}
		 	}			
		},
		rowNum:10, 
		autowidth: true,
		width: 100, 
		rowList:[10,20,30], 
		pager: jQuery('#pager2'), 
		sortname: 'id', 
		viewrecords: true, 
		sortorder: "desc",
		multiselect: true,
		caption:"Modulos"
}).navGrid('#pager2',{edit:false,add:false,del:false}); 
	jQuery("#m1").click( function() { var s; s = jQuery("#list2").jqGrid('getGridParam','selarrrow'); alert(s); });
}

function saveValues(){
	var s; 
	s = jQuery("#list2").jqGrid('getGridParam','selarrrow'); 
	var params = 'idsMod=' +s;
	params+= '&idRol='+idRoles
	
	
	//---
	$.ajax({
   		type: 'POST',
    		url: '<%=request.getContextPath()%>/guardarModulosRol.do',
    		data: params,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    		op=$(xml).find('boolean').text();
    		if(op='true'){
    			alert('Cambios guardados correctamente');
    		}
    	}
   	});
	
	
	//---
}


function gridModulos(id){
		idRoles=id;
	 	jQuery("#list2").jqGrid('setGridParam', {
	 		url:'<%=request.getContextPath()%>/consultarCatalogoModulos.do?idRol=' + id,datatype: "xml" });
	 	$("#list2").trigger("reloadGrid");	
	 	showTable();
	 	
}
function showTable(){
	document.getElementById('btnGuardar').style.visibility = "visible";		
}
function hideTable(){
	document.getElementById('btnGuardar').style.visibility = "hidden";
}


</script>
</head>
<body>
	<table id="list1"></table>
	<div id="pager1"></div>
	
<br>
	<table id="list2"></table>
	<div id="pager2"></div>
	<table id="tblButtons" align="center">
		<tr>
			<td align="center" width="50%">
				<input id="btnGuardar" class="btn_Generico" type="button" onclick="saveValues()" value="Guardar">
			</td>
		</tr>	
	</table>

</body>
</html>