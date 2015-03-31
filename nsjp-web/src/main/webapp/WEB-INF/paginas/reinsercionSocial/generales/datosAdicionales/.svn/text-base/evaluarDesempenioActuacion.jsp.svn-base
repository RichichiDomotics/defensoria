<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.AsignarProgramaReinsercionAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">

	var datosPrograma = {
		id:"", Tipo:"", Nombre:"", FechaInicio:"", FechaTermino:"", Puntos:""
	};
	var sentenciaId = <%=request.getParameter("sentenciaId")%>;

	function verPuntosAsignados(){	
			
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/asignacionDePuntos.do',
				data: {
					asignacionProgramaId : datosPrograma.id
				}, 
				async: false,
				dataType: 'html',
				success: function(responseText){
					customConfirm(responseText, "Asignar Puntos");
					cambiarBotonesDialog("asignarPuntos");		
				}
			});		
	}

	jQuery().ready(
		function () {
				jQuery("#gridProgramas").jqGrid({
					datatype: "xml", 
					rowNum:10,
					rowList:[10,20,30,40,50,60,70,80,90,100],
					//autowidth: true,
					height:'auto',
					minWidth: 500,
					width: 800, 
					minHeight: 200,						
					caption:"Programas Asignados",
					viewrecords: true,
					onSelectRow: function(id){
						var programaSeleccionado = jQuery("#gridProgramas").jqGrid('getRowData',id); 
						copiarDatosPrograma(programaSeleccionado, id);
					},
					sortorder: "asc",		           		
					autowidth:true,
	    			shrinkToFit:true,
					url:'<%=request.getContextPath()%>/llenarGridAsignacionPrograma.do',
					mType : "POST",
					postData : {
						gridID : "asignacionDePuntos",
						sentenciaId : sentenciaId
					},
					colNames:['Tipo','Nombre','Fecha Inicio','Fecha Termino', 'Puntos', 'asignacionProgramaId'], 
					colModel:[ 	{name:'Tipo',index:'1' }, 
								{name:'Nombre',index:'2'}, 
								{name:'FechaInicio',index:'3'}, 
								{name:'FechaTermino',index:'4'},
								{name:'Puntos',index:'5'},
								{name:'asignacionProgramaId',index:'6', hidden:true}
							],
					pager: jQuery('#pagerProgramas'),
					gridComplete: function(){
						cambiarBotonesDialog("grid");
					}
				}).navGrid('#pagerProgramas',{edit:false,add:false,del:false});
				
				
		}
	);
	

	
	function cambiarBotonesDialog(opcion) {
		var botones = {};	
		switch(opcion){
			case "grid" :
				botones = [
					    	{
						        text: "Aceptar",
						        id:"btn_ccAceptar",
						        click: function() {
							        if(datosPrograma.id != undefined 
							        	&& datosPrograma.id != "undefined" 
							        	&& datosPrograma.id != ""){ 
								       		$(this).dialog("close");
							        		verPuntosAsignados();
							        } else {
							        	customAlert("Se debe de seleccionar un programa.");
							        }
						        }
						    },
						    {
						        text: "Cancelar",
						        id:"btn_ccCancelar",
						        click: function() { 
						        			$(this).dialog("close");
						        			datosPrograma = {};
						        }
						    }			    
						];
				break;
			case "asignarPuntos" :
				botones = [
					    	{
						        text: "Aceptar",
						        id:"btn_ccAceptar",
						        click: function() {
							        if( validarAsignacion() ){							        	
										asignarPuntosAlPrograma();				        
							        } else {
							        	customAlert("Por favor verifique que los puntos otorgados no rebasen su valor m&aacute;ximo.");
							        }
						        }
						    },
						    {
						        text: "Cancelar",
						        id:"btn_ccCancelar",
						        click: function() { 
						        			$(this).dialog("close");
						        			datosPrograma = {};
						        }
						    }			    
						];			
				break;
		}
		
		jQuery("#customConfirm").dialog('option', 'buttons', botones);
	}

	function copiarDatosPrograma(datosSeleccionados, id){
		datosPrograma.id = getValorDelGrid(datosSeleccionados.asignacionProgramaId);
		datosPrograma.Tipo = getValorDelGrid(datosSeleccionados.Tipo);
		datosPrograma.Nombre = getValorDelGrid(datosSeleccionados.Nombre);
		datosPrograma.FechaInicio = getValorDelGrid(datosSeleccionados.FechaInicio);
		datosPrograma.FechaTermino = getValorDelGrid(datosSeleccionados.FechaTermino);
		datosPrograma.Puntos = 0;
	}

	function getValorDelGrid(valor){
		if(valor != undefined && valor != "undefined"){
		 	if(valor.indexOf("div") > -1){
		 		var from = valor.indexOf(">", valor.indexOf("div"));
				var to = valor.indexOf("<", from);
				if(from != -1 && to != -1) {
					valor = valor	.substring(from+1, to);
				}
			}
		}
		return valor;
	}


	function asignarPuntosAlPrograma() {

		var jsonParam = [];
				
		$("#customConfirm :input").each(function(index) {
				jsonParam.push({
					"objetoId"    : $(this).attr("objetoId"),
					"objetoTipo"  : $(this).attr("objetoTipo"),
					"objetoValor" : $(this).val(),
					"objetoFinal" : true
				});		
		});

		bloquearPantalla();		
	    $.ajax({
			url: '<%= request.getContextPath()%>/asignacionDePuntos.do',
			type: 'POST',
			async: false,
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(json){
				try {
					if (json.exito != undefined && json.exito != "undefined"){
						jQuery("#customConfirm").dialog("close");
						customAlert(json.exito, "Asignaci&oacute;n de Puntos", seleccionaActuacion("&asignacionProgramaId="+datosPrograma.id));
					} else if (json.error != undefined && json.error != "undefined"){
						customAlert(json.error);
					}
				}catch(e){
					console.error(e);				
				}
			}
		});		

	}	

	function validarAsignacion(){
		var resultado = true;
	
		$("#customConfirm :input").each(function(index) {
   			if ($(this).val() > parseInt($(this).attr("maxpuntos"))) {
				resultado = false;
				$(this).css("color","red");
			} else {
					$(this).css("color","black");
			}
		});
		
		return resultado;
	}

	function validarMaximoPuntos(objeto) {
		var maximo = parseInt($("#"+objeto.id).attr("maxpuntos"));
		if ($("#"+objeto.id).val() > maximo){
			customAlert("El m&aacute;ximo de puntos asignable es: " + maximo, "Error");
			$("#"+objeto.id).css("color","red");
		} else {
			$("#"+objeto.id).css("color","black");
		}
	}


</script>

<fieldset>
	<table width="99%" border="0">
		<tr>
			<td align="right">
				<table id="gridProgramas"></table>
				<div id="pagerProgramas" style="vertical-align: top;"></div>
			</td>
		</tr>	
	</table>
</fieldset>
