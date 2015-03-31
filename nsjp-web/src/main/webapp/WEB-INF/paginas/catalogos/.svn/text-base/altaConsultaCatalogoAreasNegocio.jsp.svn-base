<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.richtext.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.alerts.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.colorpicker.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css"/>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ingresarMediosContacto.js"></script>
    
   	<script type="text/javascript" src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>    
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	
	<script type="text/javascript">
	<%	
		boolean esNuevo = true;
	
		if(request.getAttribute("esNuevo") != null){
			esNuevo = (Boolean) request.getAttribute("esNuevo");
		}
		
		CatAreasNegocioDTO catAreasNegocio = new CatAreasNegocioDTO();
		
		Long catAreasNegocioId = 0L;
		String nombreCatAreaNegocio = "";
		Boolean esUIE = false;
		Long confInstitucionId = 0L;
		
		
		//Si es la consulta de un delito
		if (!esNuevo) {
			
			catAreasNegocio = (CatAreasNegocioDTO) request.getAttribute("catAreasNegocio");
			
			if(catAreasNegocio != null){
				
				if(catAreasNegocio.getCatAreasNegocioId() != null){
					catAreasNegocioId=catAreasNegocio.getCatAreasNegocioId();
				}
					
				if(catAreasNegocio.getNombreCatAreaNegocio() != null){
					nombreCatAreaNegocio = catAreasNegocio.getNombreCatAreaNegocio();	
				}
				if(catAreasNegocio.getConfInstitucion() != null && catAreasNegocio.getConfInstitucion().getConfInstitucionId() != null){
					confInstitucionId = catAreasNegocio.getConfInstitucion().getConfInstitucionId();	
				}
				if(catAreasNegocio.getEsUIE() != null){
					esUIE = catAreasNegocio.getEsUIE();
				}	
			}
	%>
			$(document).ready(function() {
				//CCONSULTA
				$("#confInstitucion").attr('disabled','disabled');
				cargaComboInstitucion();
				consultaYMuestraCatalogo();
				bloquearTodosLosElementos();
			});
	<%
		//Si es un catAreasNegocio nuevo
		}else{%>
					
			$(document).ready(function() {
				//NUEVO
				$("#confInstitucion").attr('disabled','disabled');
				cargaComboInstitucion();
				consultaInstitucionActual();
				botonesEsNuevo();
			});
	<%}%>

	//********************************************FUNCIONES PARA AGREGAR CAT AREAS DE NEGOCIO*********************************************/
	
	/*
	*Funcion que consulta la instalacion actual
	*/
	function consultaInstitucionActual(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarInstitucionActual.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				var institucionActualId = $(xml).find('institucionActual').find('confInstitucionId').text();
				$('#confInstitucion').val(institucionActualId);
			}
		});
	}

	
	//Funcion que carga el combo de instituciones
	function cargaComboInstitucion() {

		$('#confInstitucion').empty();
		$('#confInstitucion').append('<option value="nop">- Seleccione -</option>');
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				$(xml).find('instituciones').each(function(){
					$('#confInstitucion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		
				});
			}
		});
	}

	/*
	*Funcion que oculta el boto de modificar
	*/
	function botonesEsNuevo(){
		 $("#modificarAreaNegocioBtn").hide();
		 $("#guardarAreaNegocioBtn").show();
		 $("#eliminarAreaNegocioBtn").hide();
	}

	/*
	*Funcion para validar los datos generales de la area de negocio
	*/
	function validaDatosAreasNegocio(){

		if($('#nombreAreaNegocio').val() == ''){	
			customAlert("Ingrese un nombre del area de negocio");
			return false;
		}
		else if($("#confInstitucion option:selected").val() == "nop"){
			customAlert("Seleccione una institucion");
			return false;
		}
		else if($("input[name='esEspecializada']:checked").val() === undefined){
			
			customAlert("Seleccione si el area de negocio es especializada");
			return false;
		}
		else{
			return true;
		}
	}
	
	/*
	*Funcion para guardar el area de negocio despues de las validaciones
	*/
	function guardarCatAreaNegocio(){

		if(validaDatosAreasNegocio()){
				
			var params = '';
			params += "&catAreasNegocioId="+'<%=catAreasNegocioId%>';
			params += "&nombreAreaNegocio="+$('#nombreAreaNegocio').val();
			params += "&esEspecializada="+$("input[name='esEspecializada']:checked").val();
			params += "&confInstitucion="+$("#confInstitucion option:selected").val();
			
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/guardarValorEnCatalogoAreasNegocio.do',
				data: 'params='+params,
				dataType: 'xml',
				async: false,
				success: function(xml){
					respuestaIntentarGuardar(xml);
				}
			});
		}
	}

	/*
	*Funcion que se invocara despues de intentar guardar 
	*/
	function respuestaIntentarGuardar(xml){
		var codigo = $(xml).find('code').text();
		if(codigo == 0){
			var cuerpo = $(xml).find('body').text();
			if(cuerpo == "fallo"){
				alertSincronoNombre("El nombre del area de negocio ya existe");
			}
			if(cuerpo == "exito"){
				alertSincronoGuardado("El area de negocio fue guardado correctamente");
			}
		}
		else{
			//Error inesperado
		}
	}

	/*
	*Funcion que despliega una ventana modal para indicar 
	*que area de negocio fue guardada exitosamente, e invocar a la 
	*funcion del padre(administrarCatalogos.jsp) que cierra la
	*ventana y recarga el grid de delitos 
	*/
	function alertSincronoGuardado(mensaje){

		$('#spanAlert').html(mensaje);
		
		$("#alertSincro").dialog("open");
		$("#alertSincro").dialog({
			autoOpen: true, 
			modal: true, 
			title: '', 
			dialogClass: 'alert',
			position: [250,100],
		  	width: 300,
		  	height: 120,
		  	maxWidth: 300,
		  	maxHeigth:120,
			buttons: {"Aceptar":function() {
							parent.cerrarVentanaVerValor();
						}
				},
			close:function() {
					parent.cerrarVentanaVerValor();
				} 
		});
	}

	/*
	*Funcion que despliega una ventana modal para indicar que el nombre
	*del area ya  existe
	*/
	function alertSincronoNombre(mensaje){

		$('#spanAlert').html(mensaje);
		
		$("#alertSincro").dialog("open");
		$("#alertSincro").dialog({
			autoOpen: true, 
			modal: true, 
			title: '', 
			dialogClass: 'alert',
			position: [250,100],
		  	width: 300,
		  	height: 120,
		  	maxWidth: 300,
		  	maxHeigth:120,
			buttons: {"Aceptar":function() {
						$(this).dialog("close");
				     }
				}
		});
	}


	/******************************************************************FUNCIONES PARA CONSULTAS*********************************************************/
	 
	 /*
	 *Funcion que muestra la informacion de consulta en la pantalla
	 */
 	function consultaYMuestraCatalogo(){

 		$('#nombreAreaNegocio').val('<%=nombreCatAreaNegocio%>');
 		$('#confInstitucion').val('<%=confInstitucionId%>');
 		

 		//Checamos si el delito es grave
 		<%if (esUIE) {%>
 			$('input[name=esEspecializada]:eq(0)').attr('checked','checked');
 		<%} else {%>
 			$('input[name=esEspecializada]:eq(1)').attr('checked','checked');
 	 	<%}%>
 	 }


 	/*
 	*Funcion que deshabilita todos los campos de la ventana para el modo consulta
 	*/
	 function bloquearTodosLosElementos(){

		 $("#nombreAreaNegocio").attr('disabled','disabled');
		 $("#esEspecializadaSiRbtn").attr('disabled','disabled');
		 $("#esEspecializadaNoRbtn").attr('disabled','disabled');

		 $("#guardarAreaNegocioBtn").hide();
		 $("#modificarAreaNegocioBtn").show();
	}

	 /*
	*Funcion que deshabilita todos los campos de la ventana para el modo consulta
	*/
	function desbloquearTodosLosElementos(){
	
		 $("#nombreAreaNegocio").attr('disabled',false);
		 $("#esEspecializadaSiRbtn").attr('disabled',false);
		 $("#esEspecializadaNoRbtn").attr('disabled',false);

		 $("#guardarAreaNegocioBtn").show();
		 $("#modificarAreaNegocioBtn").hide();
		 $("#eliminarAreaNegocioBtn").hide();
	}

	//********************************************************FUNVCIONES PARA ELIMINAR UN CAT AREA DE NEGOCIO*****************************************/

	/*
	*Funcion que elimina el area de negocio que se consultó
	*/
	function eliminarCatAreaNegocio(){

		var params = '';
		params += "&catAreasNegocioId="+'<%=catAreasNegocioId%>';
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/eliminarValorEnCatalogoAreasNegocio.do',
			data: 'params='+params,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var valorRetorno=$(xml).find("long").text();
				respuestaIntentarEliminar(valorRetorno);
			}
		});
	}

	/*
	*Funcion que se invocara despues de intentar eliminar el objeto
	*/
	function respuestaIntentarEliminar(resultado){
		
		if(resultado == -1){
			alertSincronoNoExisteOusado("El area de negocio no se puede eliminar porque tiene relacion con funcionarios existentes");
		}
		else if(resultado == 2){
			alertSincronoNoExisteOusado("El area de negocio no se logro eliminar porque no se encontro en la base de datos");
		}
		else if(resultado == 1){
			alertSincronoNoExisteOusado("El area de negocio fue eliminada correctamente");				
		}
 	}

	/*
	*Funcion que despliega una ventana modal para indicar que el delito no existe 
	*o esta siendo usado 
	*/
	function alertSincronoNoExisteOusado(mensaje){

		$('#spanAlert').html(mensaje);
		
		$("#alertSincro").dialog("open");
		$("#alertSincro").dialog({
			autoOpen: true, 
			modal: true, 
			title: '', 
			dialogClass: 'alert',
			position: [250,100],
		  	width: 300,
		  	height: 150,
		  	maxWidth: 300,
		  	maxHeigth:150,
			buttons: {"Aceptar":function() {
						$( this ).dialog( "close" );$("#spanAlert").html("");
						parent.cerrarVentanaVerValor();
				     }
				}
		});
	}
	
	</script>
</head>

<body>

	<table width="500" border="0">
	  <tr>
	    <td width="15">&nbsp;</td>
	    <td colspan="5"><div align="center"><strong>DATOS DEL AREA DE NEGOCIO </strong></div></td>
	    <td width="16">&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td width="141">
			<div align="right">Nombre: </div>	</td>
	    <td colspan="4">
			<input type="text" id="nombreAreaNegocio" style="width:300px" tabindex="1" maxlength="150"/>	</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>
			<div align="right">Instituci&oacute;n:</div>	</td>
	    <td colspan="4">
			<select id="confInstitucion" style="width:300px" tabindex="2"></select>	
		</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>
	    	<div align="right">Es especializada:</div>
	    </td>
	    <td width="49">
	    	<div align="right">Si:</div>
	    </td>
	    <td width="28">
			<input name="esEspecializada" id="esEspecializadaSiRbtn" type="radio" value="true" tabindex="3"/>
		</td>
	    <td width="43">
			<div align="right">No:</div>
		</td>
	    <td width="178">
			<input name="esEspecializada"  id="esEspecializadaNoRbtn" type="radio" value="false" tabindex="4" />
		</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td colspan="4">&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td colspan="5">
			<input type="submit" id="guardarAreaNegocioBtn" value="Guardar" onclick="guardarCatAreaNegocio();" class="btn_Generico"/>
		    <input type="submit" id="modificarAreaNegocioBtn" value="Modificar" onclick="desbloquearTodosLosElementos();" class="btn_Generico"/>
		    <input type="submit" id="eliminarAreaNegocioBtn" value="Eliminar" onclick="eliminarCatAreaNegocio();" class="btn_Generico"/>	
		</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
	
	<!-- div para el alert sincrono -->
	<div id="alertSincro" style="display: none">
		<table>
			<tr>
				<td>
					<label id=spanAlert></label>
				</td>
			</tr>
		</table>
	</div>
	
</body>

</html>