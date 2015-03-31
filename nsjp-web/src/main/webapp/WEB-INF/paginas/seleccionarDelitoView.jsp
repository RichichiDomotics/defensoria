<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>

<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Prueba Visor de elementos</title>

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
  
	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
            
            $.widget( "custom.combobox", {
            	_create: function() {
            		this.wrapper = $( "<span>" )
            		.addClass( "custom-combobox" )
            		.insertAfter( this.element );
            		this.element.hide();
            		this._createAutocomplete();
            		this._createShowAllButton();
            		},
            	_createAutocomplete: function() {
            		var selected = this.element.children( ":selected" ),
            		value = selected.val() ? selected.text() : "";
            		this.input = $( "<input id=\"txtDelitosAuto\" size=\"80\"  >" )
            		.appendTo( this.wrapper )
            		.val( value )
            		.attr( "title", "" )
            		.autocomplete({
            			delay: 0,
            			minLength: 0,
            			source: $.proxy( this, "_source" )
            		})

            		this._on( this.input, {
            			autocompleteselect: function( event, ui ) {
            				ui.item.option.selected = true;
            				this._trigger( "select", event, {
            					item: ui.item.option
            				});
            		},	
            		});
            	},
            	_createShowAllButton: function() {
            		var input = this.input,
            		wasOpen = false;
            		$( "<a>" )
            		.attr( "tabIndex", -1 )
            		.attr( "title", "Show All Items" )
            		.appendTo( this.wrapper )
            		.button({
            			icons: {
            				primary: "ui-icon-triangle-1-s"
            			},
            		text: false
            	})
            	.removeClass( "ui-corner-all" )
            	.mousedown(function() {
            		wasOpen = input.autocomplete( "widget" ).is( ":visible" );
            		})
            		.click(function() {
            			input.focus();
            			if ( wasOpen ) {
            				return;
            				}
            			input.autocomplete( "search", "" );
            			});
            		},
            		_source: function( request, response ) {
            			var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
            			response( this.element.children( "option" ).map(function() {
            				var text = $( this ).text();
            				if ( this.value && ( !request.term || matcher.test(text) ) )
            					return {
            					label: text,
            					value: text,
            					option: this
            				};
            			}) );
            		},
            	_destroy: function() {
                	this.wrapper.remove();
                	this.element.show();
                }
            });
            
	</script>
	
    <%
   		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		Long rolId = 0L;
		Boolean esCoordinadorAmpGeneral = false;
	
		if(usuario!=null &&
	   		usuario.getRolACtivo()!=null &&
	   		usuario.getRolACtivo().getRol()!=null &&
	   		usuario.getRolACtivo().getRol().getRolId()!=null){
			rolId=usuario.getRolACtivo().getRol().getRolId();
		}
   	
   		if(rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())){
   			esCoordinadorAmpGeneral = true;
   		}
	%>
       
    <script type="text/javascript">

    	var contDelitosGraves=0;
		var idExpedienteop="";
		var idsDelitos="";
    	var numeroExpedienteId=0;
    	var firstGridDelitos=true;
    	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
    	var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>; 
    	
		$(document).ready(function(){
			if(deshabilitarCampos == true){
				$("#btnGuardarDelitosAg").hide();
				$("#pasar").hide();
				$("#pasarD").hide();
			}
			idExpedienteop='<%= request.getParameter("idExpedienteop")%>';
			numeroExpedienteId='<%= request.getParameter("idNumeroExpediente")%>';
			
			if(numeroExpedienteId=='null'){
				numeroExpedienteId = '<%= request.getParameter("idNumeroExpedienteop") %>';
			}
			if(idExpedienteop=='null')
			{
				idExpedienteop='<%= request.getSession().getAttribute("idExpedienteConsulop")%>';
			}

			//asigna a expediente op
			if(idExpedienteop == 'null' && numeroExpedienteId == 'null'){
				idExpedienteop=idExpediente;
				numeroExpedienteId=numeroExpediente;
			}
			
			 $("#gview_gridDelitosAgraviados .ui-jqgrid-bdiv").css('width', '500px');
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos == true){
				$("#pasar").attr("disabled","");
				$("#pasarD").attr("disabled","");
				$("#btnGuardarDelitosAg").attr("disabled","");		
			}
			cargaGridDelitosAgraviados();
			cargaGridDelitos();
			$( "#cbxDelitosAuto").combobox();
			
			 $("#gview_gridCatDelitos .ui-jqgrid-bdiv").css('width', '400px');
    		muestraActividadesSugeridasEnConsultaExpediente();
			
			if(esCoordinadorAmpGeneral===true){
				$("#pasar").hide();
				$("#pasarD").hide();
				$("#btnGuardarDelitosAg").hide();
			}
		});

		function obtenerDelitosDenunciados(){
			var arrayIDs = new Array() ;
			var arrayIDs = jQuery("#gridDelitosAgraviados").getDataIDs();
			var arrayDelitosDenunciados;
			arrayDelitosDenunciados="";		
			
			for (i=0;i<arrayIDs.length;i++){				
				var row = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
				if(arrayDelitosDenunciados.length>0){					
					arrayDelitosDenunciados = arrayDelitosDenunciados + "," + row.DelitoId;
				}
				else{
					arrayDelitosDenunciados = row.DelitoId;
				}
			}
			return arrayDelitosDenunciados;
		}
		
		function cargaGridDelitosAgraviados(){
			jQuery("#gridDelitosAgraviados").jqGrid({ 
				url:'<%= request.getContextPath()%>/ConsultaDelitoPorExpedienteGrid.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',
				datatype: "xml",
				ajaxGridOptions : {
	                   async:false
	            },
				colNames:['Clave','Clave','Delito', '¿Prisión P. O.?','¿Prisión P. O.?','Delito Principal','Tipo','DelitoId', ' '], 
				colModel:[ 	{name:'Clave',index:'clave', width:50,hidden:true}, 
				           	{name:'ClaveBD',index:'claveDB', width:50},
				           	{name:'Delito',index:'delito', width:180}, 
							{name:'Gravedad',index:'gravedad',align:'center',formatter:'checkbox',hidden:true},
							{name:'GravedadFormateada',index:'gravedadFormateada',width:165,align:'center',sortable:false,hidden:true},
							{name:'Principal',index:'principal',width:100,align:'center',sortable:false},
							{name:'Tipo',index:'tipo',hidden: true,align:'center'},
							{name:'DelitoId',index:'delitoId', hidden: true,align:'center'},
							{name:'X',index:'x', align:'center', width:50}
						],
				rowNum:50,
				rowList:[10,20,30,40,50,60],
				caption:"LISTA DE DELITOS DENUNCIADOS",
				sortname: 'Clave',
				viewrecords: true,
				afterInsertRow:function(){
					idsDelitos=obtenerDelitosDenunciados();
					return true;					
				},				
				sortorder: "desc"
			});
		}
		
		function cargaGridDelitos(){
			$.ajax({
				type: 'POST',
            	url:'<%= request.getContextPath()%>/cargarDelitosFiltrados.do?idNumeroExpediente='+idExpedienteop+'&idsDelitos='+idsDelitos+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('catDelitoDTO').each(function(){
						$('#cbxDelitosAuto').append('<option value="' + $(this).find('catDelitoId').text() + '">' + $(this).find('nombre').text() + '</option>');
					});
				}
			});
		}
		
		function addRowRigthToLeft(){
			var delitoSeleccionado 	= $("#txtDelitosAuto").attr('value');			
			if( delitoSeleccionado != null && delitoSeleccionado != ''  ){
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/ConsultaDelitoSeleccionadoGrid.do?delitoSeleccionado='+delitoSeleccionado+'&numeroExpediente='+numeroExpediente+'',
					data: '',
					dataType: 'xml',
					async: false
				});
				cargaGridDelitos();
				jQuery("#gridDelitosAgraviados").jqGrid('setGridParam', {url:'<%=
						request.getContextPath()%>/ConsultaDelitoPorExpedienteGrid.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',datatype: "xml"});
				$("#gridDelitosAgraviados").trigger("reloadGrid");
			}else { alertDinamico("Por favor seleccione un delito");}
			idsDelitos=obtenerDelitosDenunciados();
		}

		function addRowLeftToRigth(delitoId ){
			if( delitoId != '' && delitoId != null){
				var retd = delitoId;
				if(validaRelacionDelitos(retd)){
					jQuery("#gridDelitosAgraviados").jqGrid('delRowData',retd); 
					jQuery("#gridDelitosAgraviados").jqGrid(idsDelitos=obtenerDelitosDenunciados(),cargaGridDelitos());
				}else{
					var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
					alertDinamico("Para eliminar este delito del expediente, es necesario eliminar la relación con el "+probableResponsableProp);
				}
				
			}else { alertDinamico("Por favor seleccione un delito");}
			idsDelitos=obtenerDelitosDenunciados();
			/* codigo de cuando eran 2 tablas y se hacia el paso
			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');
			if (rowd) { 
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);

				if(validaRelacionDelitos(retd.Clave)){
					jQuery("#gridDelitosAgraviados").jqGrid('delRowData',retd.DelitoId);
					jQuery("#gridDelitosAgraviados").jqGrid(idsDelitos=obtenerDelitosDenunciados(),cargaGridDelitos());
				}else{
					var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
					alertDinamico("Para eliminar este delito del expediente, es necesario eliminar la relación con el "+probableResponsableProp);
				}
			} else { alertDinamico("Por favor seleccione un renglón");}
			idsDelitos=obtenerDelitosDenunciados(); */
		} 

		function validaRelacionDelitos(delitoId){
			var relacion=true;
	    	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarRelacionProbRespConDelito.do',
	    		data: 'idExpediente='+idExpedienteop+'&idDelito='+delitoId,
            	async: false,
	    		dataType: 'xml',
	    		success: function(xml){
	    			var regreso="";
    				regreso=$(xml).find('respuesta').text();
	    			if(regreso != false && regreso != "false"){
		    			relacion=false;
	    			}
	    		}
	    	});
			return relacion;
		}
		
		function delitoPrincipal(){
			
			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');
			if (rowd) { 
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);
				 document.getElementById('cveDelitoPrincipal').value=retd.Clave;
				 document.getElementById('delitoPrincipal').value=retd.Delito;
				 if(retd.Gravedad == "Yes")
				 	document.getElementById('graveDelitoPrincipal').value="Si";
				 else
					 document.getElementById('graveDelitoPrincipal').value=retd.Gravedad;				
			}	
			else { 
				alertDinamico("Por favor seleccione un renglón");
			} 
		}

		function limpiar(){
			document.getElementById('cveDelitoPrincipal').value="";
			document.getElementById('delitoPrincipal').value="";
		 	document.getElementById('graveDelitoPrincipal').value="";	
		}

	</script>
	</head>
	<body>
	<div id="dialog-Alert" style="display: none">
	<table align="center">
		<tr>
			<td align="center">
				<span id="divAlertTexto"></span>
			</td>
		</tr>
	</table>	
	</div>
	<table border="0"  width="1500px">
		<tr>
			<td height="20" colspan="4" align="left" >
				<table width="75%">
					<tr>
						<td  align="left"><input type="button" id="btnGuardarDelitosAg" value="Guardar" onclick="guardarDelitosAgraviadosExp();" class="btn_Generico"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td valign="top">
				<label  >LISTA DE DELITOS:</label> 
				<select size="1" id="cbxDelitosAuto" style="width:200px" ></select>
				<input type="button" id="pasar" value="Agregar" onclick="addRowRigthToLeft();" class="btn_Generico">
				<table id="gridDelitosAgraviados" width="500px"></table>
				<b> <span id="leyendaUnDelitoGrave">Se debe canalizar a la Unidad de Investigadores</span> </b>
				<b> <span id="leyendaNingunDelitoGrave">Si no hay reincidencia por parte del <bean:message key="probableResponsableTitulo"/>,<br/>
					se debe canalizar a la <bean:message key="leyendaNingunDelitoGraveRestaurativa"/></span> </b>
			</td>
			
			<!--td valign="top">
				<table id="gridDelitosAgraviados" width="500px"></table>
					<b> <span id="leyendaUnDelitoGrave">Se debe canalizar a la Unidad de Investigadores</span> </b>
					<b> <span id="leyendaNingunDelitoGrave">Si no hay reincidencia por parte del <bean:message key="probableResponsableTitulo"/>,<br/> 
					se debe canalizar a la <bean:message key="leyendaNingunDelitoGraveRestaurativa"/></span> </b>
			</td-->
		</tr>
	</table>
</body>
</html>