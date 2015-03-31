<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.EstatusEvidencia" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	 
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>				
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"  />	
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"  />
   	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
    
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>	
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Defensa en Integración</title>
	
	<script type="text/javascript">
      
	var idAlmacen= parent.idalmacen;
	var cargaGridInv=false;
	var idFuncionario;
	
	$(document).ready(function() {
		
		$("#tabsPrincipal").tabs();
		$("#hrefIngDom,#liDomNotif,#rowDomicilioNotif").css("display","none");
	
		horaActual();
		cargarCatalogoDeFuncionarios();

		if(idAlmacen>0 && idAlmacen!=null && idAlmacen!=""){
			consultaAlmacen();
			$("#btnModificar1").css("display","");
			$("#btnModificar2").css("display","");
			$("#btnGuardar1").css("display","none");		
			$("#btnGuardar2").css("display","none");
			gridEncargados();
		}else{
			$("#btnModificar1").css("display","none");
			$("#btnModificar2").css("display","none");
			$("#tabsPrincipal").tabs("option", "disabled", [2]);
		}
		
		/*Ocultando botones que modifican datos/*ByYolo*/
		$("#btnModificar1").hide();
		$("#btnModificar2").hide();
		$("#btnGuardar1").hide();
		$("#btnGuardar2").hide();
		/*Fin de ocultamiento/*ByYolo*/
		
	});

	function modificaEvidencia(){
		$("#btnModificar1").css("display","none");
		$("#btnModificar2").css("display","none");
		$("#btnGuardar1").css("display","");
		$("#btnGuardar2").css("display","");
		$('#nombreAlmacen').attr("disabled","");
		$('#RadioEsVirtual_0').attr("disabled","");
		$('#RadioEsVirtual_1').attr("disabled","");
		$('#descripcionAlmacen').attr("disabled","");
		$('#RadioResguardaEV_0').attr("disabled","");
		$('#RadioResguardaEV_1').attr("disabled","");
		$('#fechaAlta').attr("disabled","");
		$('#codigoPostalButton').show();
		avilitarDatosDomicilio();		
	}

	function consultaAlmacen(){
		var params;
		params="idAlmacen="+idAlmacen;   
		$.ajax({								
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/consultarAlmacenXID.do',
    	  data: params,				
    	  dataType: 'xml',
    	  success: function(xml){
    		
    		$("#idIdentificador").val($(xml).find('almacenDTO').find('identificadorAlmacen').text());
    		$("#nombreAlmacen").val($(xml).find('almacenDTO').find('nombreAlmacen').text());

    		if($(xml).find('almacenDTO').find('resguardaEvidencias').text()=="true"){
    			$('#RadioResguardaEV_0').attr('checked','checked');
	    	}else{
	   			$('#RadioResguardaEV_1').attr('checked','checked');
		    }

    		if($(xml).find('almacenDTO').find('esVirtual').text()=="true"){
    			$('#RadioEsVirtual_0').attr('checked','checked');
	   		}else{
	   			$('#RadioEsVirtual_1').attr('checked','checked');
	   		}
    		var fechaActual=$(xml).find('almacenDTO').find('fechaAlta').text();					    	
 	    	var conformato	= fechaActual.split(" ");
		    $("#fechaAlta").val(conformato[0]);
    		$("#descripcionAlmacen").val($(xml).find('almacenDTO').find('descripcion').text());
    		
			$("#btnGuardar1").css("display","none");
			$("#btnGuardar2").css("display","none");
			pintaDatosDomicilio(xml);
			//pinta Datos de Coordenadas Geograficas.
			var longitud=$(xml).find('domicilio').find('longitud').text();
			var latitud=$(xml).find('domicilio').find('latitud').text();
			
			$('#txtFldLongitudEste').val(longitud.substring(0,1));
			$('#txtFldLongitudGrados').val(longitud.substring(1,3));
		    $('#txtFldLongitudMinutos').val(longitud.substring(4,6));
		    $('#txtFldLongitudSegundos').val(longitud.substring(7,9));
		    $('#txtFldLatitudNorte').val(latitud.substring(0,1));
		    $('#txtFldLatitudGrados').val(latitud.substring(1,3));
		    $('#txtFldLatitudMinutos').val(latitud.substring(4,6));
		    $('#txtFldLatitudSegundos').val(latitud.substring(7,9));
			
		    deshabilitaCampos();
    	  }
    	});
	}

	function deshabilitaCampos(){
		desavilitarDatosDomicilio();
		//Se deshabilita datos de la evidencia
	 	$('#nombreAlmacen').attr("disabled","disabled");
	 	$('#RadioEsVirtual_0').attr("disabled","disabled");
	 	$('#RadioEsVirtual_1').attr("disabled","disabled");
	 	$('#descripcionAlmacen').attr("disabled","disabled");
	 	$('#RadioResguardaEV_0').attr("disabled","disabled");
	 	$('#RadioResguardaEV_1').attr("disabled","disabled");
	 	$('#fechaAlta').attr("disabled","disabled");
	}	
		
	function guardarAlmacen(){
		if($("#nombreAlmacen").val()==""){
			customAlert("Favor de capturar el nombre del Almac\u00E9n");
		}
		else{
			var params;
			
			params="nombreAlmacen="+$("#nombreAlmacen").val();      
			params +="&fechaAlta="+$("#fechaAlta").val();
			params +="&descripcionAlmacen="+$("#descripcionAlmacen").val();
			params +="&radioEsVirtual="+$(':radio[name=RadioEsVirtual]:checked').val();
			params +="&radioResguardaEV="+$(':radio[name=RadioResguardaEV]:checked').val();
			params +="&idAlmacen=" + idAlmacen;
			//Domicilio 
			datosPestania = obtenerParametrosDomicilio();
			params += datosPestania;
			$.ajax({								
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/guardarAlmacen.do',
		    	  data: params,				
		    	  dataType: 'xml',
		    	  success: function(xml){		    		 
					if(idAlmacen>0 && idAlmacen!=null && idAlmacen!=""){
						deshabilitaCampos();
						$("#btnModificar1").css("display","");
		    			$("#btnModificar2").css("display","");
		    			$("#btnGuardar1").css("display","none");		
		    			$("#btnGuardar2").css("display","none");
		    			customAlert("El Almac\u00E9n se actualizó de forma correcta");	
					}
					else{
						idAlmacen = $(xml).find('long').text();
						deshabilitaCampos();
		    			gridEncargados();
		    			$("#tabEncargado").removeClass("ui-state-disabled");
		    			$("#btnModificar1").css("display","");
		    			$("#btnModificar2").css("display","");
		    			$("#btnGuardar1").css("display","none");		
		    			$("#btnGuardar2").css("display","none");
		    			customAlert("El Almac\u00E9n se registró de forma correcta");
					}
		    		parent.consultarAlmacenesPorTipo();
		    	  }
	    	});
		}
	}

	function horaActual(){
		$.ajax({								
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/horaActual.do',
	    	  data: "",	
	    	  async: false,			
	    	  dataType: 'xml',
	    	  success: function(xml){
	    		 var fechaActual=$(xml).find('fechaActual').text();	    	
	    		 var conformato	= fechaActual.split(",");	    	
	    		 $("#fechaAlta").val(conformato[0]);
	    	  }
	    });
	}	

	function gridEncargados(){
		if (cargaGridInv==false){
			cargaGridInv=true;
            jQuery("#gridEncargadosAlmacen").jqGrid({
                url:'<%=request.getContextPath()%>/consultargridEncargadosAlmacen.do?idAlmacen='+idAlmacen,
                datatype: "xml",
                colNames:['Tipo de Encargado','Nombre del Encargado'],
                colModel:[
                    {name:'tipoEncargado',index:'Index1',  viewable:false, hidden:false, width:250},
                    {name:'NombreEncargado',index:'Index1',  viewable:false, hidden:false, width:250}
                ],
                pager: jQuery('#pagerEncargadoAlmacen'),
                rowNum:10,
                rowList:[10,20,30],
                autowidth: true,
                autoheight:true,
               
                height:300,
                sortname: 'NombreEncargado',
                viewrecords: true,
                sortorder: "desc"
            }).navGrid('#pagerEncargadoAlmacen',{edit:false,add:false,del:false});           
		}else{
			 jQuery("#gridEncargadosAlmacen").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>consultargridEncargadosAlmacen.do?idAlmacen='+idAlmacen,datatype: "xml" });
			 $("#gridEncargadosAlmacen").trigger("reloadGrid");
	    }
	}	

	function quitarEncargadoAlmacen(){			
		var params;
    	idFuncionario=jQuery("#gridEncargadosAlmacen").jqGrid('getGridParam','selrow');		    			    	
    	if(idFuncionario>=0){
			params="idAlmacen="+idAlmacen;     
			
			params +="&idFuncionario="+jQuery("#gridEncargadosAlmacen").jqGrid('getGridParam','selrow');
				
			$.ajax({								
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/removerFuncionarioAlmacen.do',
		    	  data: params,				
		    	  dataType: 'xml',
		    	  success: function(xml){
		    		customAlert("Encargado Removido");
		    		cargarCatalogoDeFuncionarios();
		    		$("#gridEncargadosAlmacen").trigger("reloadGrid");			    		
		    		
		    	  }
	    	});
    	}
	   	else{
	   		customAlert("Selecciona un Encargado");
	    }
	}

	function agregarEncargadoAlmacen(){
		 selectorDeCaptura();
		 $("#dialog-Encargados" ).dialog({
    			autoOpen: true,
    			resizable: true,
    			height:250,
    			width:550,
    			modal: true,
    			buttons: {
    				"Aceptar": function() {
    			   		if($(':radio[name=RadioEsRespInterno]:checked').val()=="true"){
        					var checa=$('#selectFuncionarioaAlmacen option:selected').val();
        					if(checa>=0){
        						asociaFuncionarioAlmacen();
        					$( this ).dialog( "close" );
        					$('#selectFuncionarioaAlmacen').attr('selectedIndex',0);
        					cargarCatalogoDeFuncionarios();
        					}else{customAlert("Selecciona un Funcionario");}
    		    		}
    		    		else{
    		    			if($("#nombreResponsable").val()=="" && apPatResponsable=="" && apMatResponsable==""){
    		    				customAlert("Selecciona un Funcionario");
    		    			}
    		    			else{
        						asociaFuncionarioAlmacen();
            					$( this ).dialog( "close" );
            					$('#selectFuncionarioaAlmacen').attr('selectedIndex',0);
            					cargarCatalogoDeFuncionarios();     		    				
    		    			}
    		    		}
    				},
    				"Cancelar": function() {
    					$( this ).dialog( "close" );
    				}
    			}
    	});		
	}

	 function cargarCatalogoDeFuncionarios(){
		 $('#selectFuncionarioaAlmacen').html('');
		 $('#selectFuncionarioaAlmacen').append("<option value='-1'>- Seleccionar -</option>");
		 $.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoFuncionariosAlmacen.do',
				dataType: 'xml',
				success: function(xml){
					$(xml).find('listaFuncionarioDTOs').find('funcionarioDTO').each(function(){
						$('#selectFuncionarioaAlmacen').append('<option value="' + $(this).find('claveFuncionario').text() + '">' + $(this).find('nombreFuncionario').text() +" "+ $(this).find('apellidoPaternoFuncionario').text() +" "+ $(this).find('apellidoMaternoFuncionario').text() + '</option>');
					});
				}
			});
	    }

    function asociaFuncionarioAlmacen(){

    	var params;
		params="idAlmacen="+idAlmacen;     
		
		if($(':radio[name=RadioEsRespInterno]:checked').val()=="true"){
   			params +="&esInterno=" + "si";		   			
   		}
		else{
			params +="&esInterno=" + "no";
			params +="&nombreRespExt=" + $("#nombreReponsableExt").val();
			params +="&apPatRespExt=" + $("#apPatResponsableExt").val();
			params +="&apMatRespExt=" + $("#apMatResponsableExt").val();
		}				
		
		params +="&idFuncionario="+$('#selectFuncionarioaAlmacen option:selected').val();
		
		$.ajax({								
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/asociaFuncionarioAlmacen.do',
	    	  data: params,				
	    	  dataType: 'xml',
	    	  success: function(xml){
	    		 $("#gridEncargadosAlmacen").trigger("reloadGrid");
	    		 customAlert("Encargado Guardado");
	    		 $("#nombreReponsableExt").val("");
				 $("#apPatResponsableExt").val("");
				 $("#apMatResponsableExt").val("");
	    	  }
    	});
    }
	    
    function selectorDeCaptura(){
   		if($(':radio[name=RadioEsRespInterno]:checked').val()=="true"){
    			$("#encargadoInterno").show();
    			$("#encargadoExterno").hide();    		
   		}
   		else{    		
    			$("#encargadoInterno").hide();
    			$("#encargadoExterno").show();    		
   		}
    }

	</script>
	
	<!--ESTILOS A PARA LAS TABS-->
	
	<style>
	#tabs { height: 100% } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 550px; overflow: visible; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	.tabEstilo  { height: 350px; overflow: auto; }
	</style>
	
</head>
<body>
<div id="tabsPrincipal">
	<ul>
		<li id="tabDetalle" ><a href="#tabsconsultaprincipal-1">Detalle</a></li>
		<li id="tabDireccion" ><a href="#tabsconsultaprincipal-2">Domicilio</a></li>
		<li id="tabEncargado"><a href="#tabsconsultaprincipal-3">Encargados</a></li>		
	</ul>
	<div id="tabsconsultaprincipal-1">
		<input type="button" value="Guardar" onclick="guardarAlmacen()" id="btnGuardar1" class="btn_modificar">
		<input type="button" value="Modificar" onclick="modificaEvidencia()" id="btnModificar1" class="btn_modificar">
		<table width="80%" border="0">
			<tr><td></td></tr>
			<tr><td></td></tr>
			<tr>
			    <td width="18%" align="right">Identificador:</td>
			    <td width="26%"><input type="text" name="idIdentificador" id="idIdentificador" readonly="readonly" disabled="disabled"/></td>
			    <td width="15%" align="right">Fecha Alta:</td>
			    <td width="41%"><input type="text" name="fechaAlta" id="fechaAlta" readonly="readonly" value=""/></td>
			</tr>
  		  	<tr>
			    <td align="right">Nombre:</td>
			    <td><input type="text" name="nombreAlmacen" id="nombreAlmacen" maxlength="20" size="20"/></td> 
			    <td colspan="2">Descripci&oacute;n:</td>
			</tr>
  			<tr>
    			<td align="right">Es Virtual:</td>
    			<td>
    				<table width="100%">
      					<tr>
        					<td>
        						<label>          Sí
          							<input type="radio" name="RadioEsVirtual" value="true" id="RadioEsVirtual_0" checked="checked" />
								</label>
							</td>
 							<td>
 								<label>No
									<input type="radio" name="RadioEsVirtual" value="false" id="RadioEsVirtual_1" />
          						</label>
          					</td>
      					</tr>  
    				</table>
    			</td>
    			<td colspan="2" rowspan="2">
    				<textarea name="textarea" id="descripcionAlmacen" cols="60" rows="5"></textarea>
    			</td>
  			</tr>
  			<tr>
    			<td valign="top" align="right">Resguardar EV:</td>
    			<td valign="top">
    				<table width="100%">
      					<tr>
        					<td>
        						<label>          Sí
          							<input type="radio" name="RadioResguardaEV" value="true" id="RadioResguardaEV_0" checked="checked"/>
								</label>
							</td>
 							<td>
 								<label>No
          							<input type="radio" name="RadioResguardaEV" value="false" id="RadioResguardaEV_1" />
          						</label>
          					</td>
      					</tr>
    				</table>
    			</td>
  			</tr>
		</table>		
	</div>
	<div id="tabsconsultaprincipal-2">
		<input type="button" value="Guardar" onclick="guardarAlmacen()" id="btnGuardar2" class="btn_modificar">
		<input type="button" value="Modificar" onclick="modificaEvidencia()" id="btnModificar2" class="btn_modificar">		
		<jsp:include page="ingresarDomicilioView.jsp"></jsp:include>			
	</div>	
	<div id="tabsconsultaprincipal-3">
		<table id="gridEncargadosAlmacen" width="700"></table> 
		<div id="pagerEncargadoAlmacen"></div>
		<table>
			<tr>
				<td>
<!-- 					<input type="button" value="Quitar" onclick="quitarEncargadoAlmacen()" class="btn_modificar"> -->
				</td>
				<td>
<!-- 					<input type="button" value="Agregar" onclick="agregarEncargadoAlmacen()" class="btn_modificar"> -->
				</td>
			</tr>
		</table>
	</div>			
</div>

<div id="dialog-Encargados" style="display: none;">
	<table id="selectorEncargado">
		<tr><td></td></tr>
		<tr>
    		<td align="center">
    			<table width="100%">
      				<tr>
      					<td align="right">Es Encargado Interno:</td>      				
        				<td> Sí </td>
        				<td>
        					<label>        				
          						<input type="radio" name="RadioEsRespInterno" value="true" id="RadioEsRespInterno_0" checked="checked" onchange="selectorDeCaptura();"/>
							</label>
						</td>
						<td> No </td>					
 						<td>
 							<label>
								<input type="radio" name="RadioEsRespInterno" value="false" id="RadioEsRespInterno_1" onchange="selectorDeCaptura();"/>
          					</label>
          				</td>
      				</tr>  
    			</table>
    		</td>		
		</tr>
	</table>
	<table>
		<tr>
			<td width="500">
				<table id="encargadoInterno" align="center" width="100%">
					<tr><td></td></tr>							
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr>
						<td align="center">Encargados: 
							<select id="selectFuncionarioaAlmacen"> 
								<option value="-1">- Seleccionar -</option>
							</select>
						</td>					
					</tr>				
				</table>
			</td>					 
		</tr>
		<tr>
			<td width="500">
				<table id="encargadoExterno" align="center" width="100%">
					<tr><td></td></tr>					
					<tr>
						<td colspan="2" align="center">
							<font FACE="arial" SIZE=2>
								<b>Información del Encargado Externo</b>
							</font>			
						</td>
					</tr>
					<tr><td></td></tr>
					<tr>
						<td align="right">Nombre (s):</td>
					    <td><input type="text" name="nombreReponsableExt" id="nombreReponsableExt" maxlength="50" size="50"/></td>
					</tr>
					<tr>
					    <td align="right">Apellido Paterno:</td>
					    <td><input type="text" name="apPatResponsableExt" id="apPatResponsableExt" maxlength="50" size="50"/></td>
					</tr>
					<tr> 
					    <td align="right">Apellido Materno:</td>
					    <td><input type="text" name="apMatResponsableExt" id="apMatResponsableExt" maxlength="50" size="50"/></td> 		   
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
	
</script>
</body>
</html>