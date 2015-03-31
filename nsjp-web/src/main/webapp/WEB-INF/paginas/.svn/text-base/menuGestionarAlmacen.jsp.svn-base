<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.EstatusEvidencia" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.evidencia.TiposEslabon" %>
<%@ page import=" mx.gob.segob.nsjp.comun.enums.objeto.Objetos"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>		
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js"></script>
	
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Gestión de Almacén</title>
	
	<script type="text/javascript">
      
		//variables globales
		var idEvidencia = parent.idParaConsultaEvidencia;
		var idAlmacen = parent.idAlmacenParaGestionarAlmacen;
		var numeroGeneralCaso = "";
		var cadenaDeCustodiaId = 0;
		var estatusEvidencia = 0;
		var tipoObjeto = 0;
		var idObjeto = 0;
		var opcionSeleccionadaEnElMenu = parent.opcionSeleccionadaEnElMenu;
	    var tipoEslabonDetalleSolicitud = 0;
	    var idEslabon = 0;
		var idEslabonDetalleSolicitud = 0;
		//Unicamente sirve para saber cual fue el ultimo estatus y recargar el grid
		var estatusEvidenciaDeLaUltimaConsulta = parent.estatusEvidenciaDeLaUltimaConsulta;
		var banderaSolicitudUltimaConsulta = parent.banderaSolicitudUltimaConsulta;
		//Permite saber que tipo de eslabon consultar en la pestania de Detalle Solicitud
		var estatusEvidenciaInicial = 0;
		
		//Permite filtrar el combo en base al rol de los usuario
		var visualizaTipoEslabon = '<%=request.getParameter("visualizaTipoEslabon")%>';
		
	$(document).ready(function() {

	$("#tabsprincipalconsulta").tabs();
	$("#tabsPrincipal").tabs();	
	

		seteaInfoDeEvidencia();		
		cargaDatosEvidencia(idEvidencia);
		
		//EN BASE AL ESTATUS DE LA EVIDENCIA SE DEBE DE CONFIGURAR DE FORMA INICIAL EL TIPO DE ESLABON A CONSULTAR
		//(Sirve para la pestania de Detalle de la Solitud)
		switch(parseInt(estatusEvidenciaInicial)){
			case <%= EstatusEvidencia.NUEVA.getValorId() %>: 
				 tipoEslabonDetalleSolicitud = '<%= TiposEslabon.REGISTRO.getValorId() %>';
			break;
			case <%= EstatusEvidencia.EN_ALMACEN.getValorId() %>: 
				 tipoEslabonDetalleSolicitud = '<%= TiposEslabon.SOLICITUD.getValorId() %>';
			break;			
			case <%= EstatusEvidencia.EN_PRESTAMO.getValorId() %>: 
				 tipoEslabonDetalleSolicitud = '<%= TiposEslabon.SOLICITUD.getValorId() %>';
			break;
			case <%= EstatusEvidencia.CON_RETRASO.getValorId() %>: 
				 tipoEslabonDetalleSolicitud = '<%= TiposEslabon.SOLICITUD.getValorId() %>';
			break;
		}
		
		
		
		consultarEslabonesXidEvidencia(idEvidencia);
		
		
		//Permite consultar el ultimo eslabon de una evidencia por tipo
		if(banderaSolicitudUltimaConsulta == true){
			idEslabonDetalleSolicitud = consultaIdEslabonPorTipo(idEvidencia , tipoEslabonDetalleSolicitud);
			gridDocumento();
		}
		else{
			// No tiene solicitudes pendientes -> no se muestra detalle de la solicitud
			ocultaDetalleSolicitud();
		}
		
		//Si se trata del rol agnetemp o del rol cooridnadoramp
		if(visualizaTipoEslabon != null && visualizaTipoEslabon == "amp"){
			$("#tabsprincipalconsulta").tabs("option", "selected", 1);
			$("#buttonGenerarReporte").hide();
			$("#tabEslabones").hide();	
			$("#tabES").hide();	
			estatusEvidenciaDeLaUltimaConsulta = '<%=EstatusEvidencia.CON_RETRASO.getValorId()%>';
		}
		
		
	});
	
    function cargaCatalogoTipoEslabon()
    {
   	$('#cbxTipoEslabon').empty();
   	$('#cbxTipoEslabon').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTiposEslabon.do',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catEslabon').each(function(){
					$('#cbxTipoEslabon').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
    }
	
			
	function seteaInfoDeEvidencia(){
		$("#txtNumEvi").val(idEvidencia);		
	}

	
   	//Llena el grid con los resultados de la busqueda, pasa como parametros el No general del caso
	  var banderaCargarORecargar=0;

   	function consultaEvidencias(){
			if(banderaCargarORecargar==0){
				jQuery("#tablaInventarioDeEvidenciasPorCaso").jqGrid({
					url:'<%=request.getContextPath()%>/generaInventarioDeAlmacen.do?numeroGeneralCaso='+numeroGeneralCaso+'&idAlmacen='+idAlmacen, 
					datatype: "xml",  							
					colNames:['#Caso','#Cadena','#Evidencia','Nombre','Codigo','Cantidad','Estatus','Fecha de devolución'], 
					colModel:[  {name:'Caso',index:'1', sortable:true, width:100},
					            {name:'Cadena',index:'3', sortable:true, width:100},
								{name:'Evidencia',index:'6', sortable:true, width:100},
								{name:'NombreEvi',index:'nombreEvi', width:80}, 
								{name:'Codigo',index:'codigo', width:80}, 
								{name:'Cantidad',index:'cantidad', width:80},
								{name:'Estatus',index:'2',sortable:true, width:100},
								{name:'Fecha',index:'fecha', width:100}
							],
							pager: jQuery('#pager1'), 
							rowNum:10,
							rowList:[10,20,30],
		                    autowidth: true,
		                    autoheight:true,
		                  	height:220,
							sortname: '6', 
							viewrecords: true,
		                    sortorder: "desc"
				 }).navGrid('#paginadorDetalle',{edit:false,add:false,del:false});
	        	$("#tablaInventarioDeEvidenciasPorCaso").trigger("reloadGrid");
	        	banderaCargarORecargar=1;
	    }else{
	    	jQuery("#tablaInventarioDeEvidenciasPorCaso").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/generaInventarioDeAlmacen.do?numeroGeneralCaso='+numeroGeneralCaso+'&idAlmacen='+idAlmacen,datatype: "xml" });
			$("#tablaInventarioDeEvidenciasPorCaso").trigger("reloadGrid");		
	    }	            	
		//Fin grid
		  
	}//Cierra consultaEvidencias

			 
	//Permite consultar el documento de solicitud en la pestania de "Detalle de la solicitud"
	function gridDocumento() {
	  	  jQuery("#gridDocumentos").jqGrid({
						url:'<%=request.getContextPath()%>/consultarDocumentosXIdEslabon.do?idEslabon='+idEslabonDetalleSolicitud, 
						datatype: "xml", 						
						colNames:['Área del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
						colModel:[ 	{name:'area',index:'area', width:200, hidden:true},
									{name:'FechaActividad',index:'fechaActividad', width:170, hidden:true},							
									{name:'NombreActividad',index:'nombreActividad', width:400, hidden:true},
						           	{name:'Tipo',index:'tipo', width:155}, 
									{name:'Nombre',index:'nombre', width:255},
						           	{name:'Fecha',index:'fecha', width:170, align:'center'}
									],
			pager: jQuery('#paginadorDocumentos'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			autoheight:true,
			sortname: 'Fecha',
			viewrecords: true,
			sortorder: "desc",
			onSelectRow: function(id) {
					consultaPDF(id);
				}
		}).navGrid('#paginadorDocumentos',{edit:false,add:false,del:false});	 
	}


	
	function consultaPDF(id){					
		document.frmDoc.documentoId.value = id;
		document.frmDoc.submit();
	}
	
	function cargaDatosEvidencia(evidenciaId){
		$.ajax({
	    	  type: 'POST',
	    	  url:  '<%= request.getContextPath()%>/consultarEvidenciaPorId.do',
	    	  data: 'evidenciaId='+evidenciaId,
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
	    		  pintaDatosEvidencia(xml);
	    		  
	    		  //Asigna el valor inicial del estatus de la evidencia
	    		  if($(xml).find('evidenciaDTO').find('estatus').last().find('idCampo') != null){
	    			  estatusEvidenciaInicial = $(xml).find('evidenciaDTO').find('estatus').last().find('idCampo').text();
	    	       }
	    		   
			  }
	    });
    }
	
    function pintaDatosEvidencia(xml){
		 //Numero de caso
    	if($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso') != null){
 		   $('#txtNumCaso').val($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').text());
 		  numeroGeneralCaso = $(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').text();
        }
    	//Numero de expediente
        if($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('expedienteDTO').find('numeroExpediente') != null){
    		   $('#txtNumExpediente').val($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('expedienteDTO').find('numeroExpediente').text());
           }
    	
      //Numero de cadena
        if($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('folio') != null){
    		   $('#txtNumCadena').val($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('folio').text());
           }
        //Cadena de custodia
        if($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('cadenaDeCustodiaId') != null){
    		   $('#txtNombreCadena').val($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('cadenaDeCustodiaId').text());
    		   cadenaDeCustodiaId = $(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('cadenaDeCustodiaId').text();
         }
		//Numero de evidencia
        if($(xml).find('evidenciaDTO').find('evidenciaId') != null){
 		   $('#txtNumEvi').val($(xml).find('evidenciaDTO').find('evidenciaId').text());
        }
		//Nombre de la evidencia  
        if($(xml).find('evidenciaDTO').find('tipoObjeto')!= null){
 		   $('#txtNombreEvi').val($(xml).find('evidenciaDTO').find('tipoObjeto').text());
 		  tipoObjeto = $(xml).find('evidenciaDTO').find('idTipoObjeto').text();
        }
        
		if($(xml).find('evidenciaDTO').find('objeto').find('elementoId')!= null){
  		   idObjeto = $(xml).find('evidenciaDTO').find('objeto').find('elementoId').first().text();
        }
		
      //Nombre de la evidencia  
        if($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('quienRecibe')!= null){
 		   $('#txtCodigo').val($(xml).find('evidenciaDTO').find('cadenaDeCustodia').find('quienRecibe').text());
        }
        
        //Codigo de la evidencia
        if($(xml).find('evidenciaDTO').find('codigoBarras') != null){
 		   $('#txtCodigo').val($(xml).find('evidenciaDTO').find('codigoBarras').text());
        }
            
      //Nombre del servidor publico
        if($(xml).find('evidenciaDTO').find('funcionario').find('nombreFuncionario')!= null){
 		   $('#solDePericialNombre').val($(xml).find('evidenciaDTO').find('funcionario').find('nombreFuncionario').text() + " " +
 				  $(xml).find('evidenciaDTO').find('funcionario').find('apellidoPaternoFuncionario').text() + " " +
 				 $(xml).find('evidenciaDTO').find('funcionario').find('apellidoMaternoFuncionario').text());
        }
        
        //Area del responsable 
        if($(xml).find('evidenciaDTO').find('funcionario').find('area').find('nombre')!= null){
 		   $('#solDePericialAreaAdmin').val($(xml).find('evidenciaDTO').find('funcionario').find('area').find('nombre').text());
        }
        
      //Puesto del responsable 
        if($(xml).find('evidenciaDTO').find('funcionario').find('puesto').find('valor')!= null){
 		   $('#solDePericialPuesto').val($(xml).find('evidenciaDTO').find('funcionario').find('puesto').find('valor').text());
        }
		
        

    }

	/* Funcion que permite acutalizar el estatus de una Evidencia
	*  En Almacen o Baja
	*/
	function actualizaEstatusDeEvidencia(estatusId)
	{
			var parametros="";
			parametros+="&estatusId="+estatusId;
			parametros+="&evidenciaId="+idEvidencia;
			
			//mandamos a llamar al action que actualiza el estatus de una solicitud
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/actualizarEstatusEvidencia.do',
				data: parametros,
				dataType: 'xml',
				success: function(xml){						
						if(parseInt($(xml).find('code').text())==0)
						{	
							var bandera=$(xml).find('boolean').text();
							if(bandera=="true")
							{
								//customAlert("Se actualizo exitosamente el estatus de la Evidencia");
								//Recargar el grid de evidencias
								consultaEvidencias();
							}
							else
							{
								customAlert("Ocurrio un error al actualizar el estatus de la Evidencia");
							}							
						}
						else
						{
							cadenaCustodia="";
							customAlert("Ocurrió un error al actualizar el estatus de la evidencia");
						}
				}
			});
	}		
	
	/*
	*Funcion que simula la funcion TRIM de otros lenguajes 
	*
	*/
	
	function trim(myString)
	{
		return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
	}

	
	/*
	* Funcion para abrir la ventana de los objetos desde la cadena de custodia y poder ver la informacion de detalle
	*/
	function abreVentanaConsultaObjeto()
	{
		//customAlert("idObjeto " + idObjeto )
		//customAlert("tipoObjeto " + tipoObjeto)
		
		idWindowObEvidencia = 1;
		numeroExpediente = "";
		idWindowObEvidencia++;
		
		//lanzamos la pantalla que depende del objeto
		if(parseInt(tipoObjeto)=='<%= Objetos.VEHICULO.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:570, height:455,title:"Consultar Veh&iacute;culo", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?tipoObjeto=VEHICULO&idVehiculo='+idObjeto+'&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.EQUIPO_DE_COMPUTO.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:230,title:"Consultar Equipo De C&oacute;mputo", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoDeComputo.do?idEquipoComputo='+idObjeto+'&tipoObjeto=EQUIPO_COMPUTO&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.EQUIPO_TELEFONICO.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:230,title:"Consultar Equipo Telef&oacute;nico", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoTelefonico.do?idEquipoTelefonico='+idObjeto+'&tipoObjeto=EQUIPO_TELEFONICO&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.ARMA.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:230,title:"Consultar Arma", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarArma.do?idArma='+idObjeto+'&tipoObjeto=ARMA&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.EXPLOSIVO.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Explosivo", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?idExplosivo='+idObjeto+'&tipoObjeto=EXPLOSIVO&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.SUSTANCIA.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Sustancia", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?idSustancia='+idObjeto+'&tipoObjeto=SUSTANCIA&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.ANIMAL.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Animal", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarAnimal.do?idAnimal='+idObjeto+'&tipoObjeto=ANIMAL&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.AERONAVE.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:570, height:455,title:"Consultar Aeronave", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?tipoObjeto=AERONAVE&idAeronave='+idObjeto+'&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');			
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.EMBARCACION.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:570, height:455,title:"Consultar Embarcaci&oacute;n", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?tipoObjeto=EMBARCACION&idEmbarcacion='+idObjeto+'&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');			
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.NUMERARIO.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Numerario", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?idNumerario='+idObjeto+'&tipoObjeto=NUMERARIO&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.VEGETAL.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Vegetal", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarVegetal.do?idVegetal='+idObjeto+'&tipoObjeto=VEGETAL&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.DOCUMENTO_OFICIAL.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Documento Oficial", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarDocumentoOficial.do?idDocOfic='+idObjeto+'&tipoObjeto=DOCUMENTO_OFICIAL&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.JOYA.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Joya", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarJoya.do?idJoya='+idObjeto+'&tipoObjeto=JOYA&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}
		else if(parseInt(tipoObjeto)=='<%= Objetos.OBRA_DE_ARTE.getValorId() %>')
		{
			$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Obra de Arte", type:"iframe",modal: true});
		    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarObraDeArte.do?idObraArte='+idObjeto+'&tipoObjeto=OBRA_DE_ARTE&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
		}else{
			if(parseInt(tipoObjeto)=='<%= Objetos.OTRO.getValorId() %>')
			{
				$.newWindow({id:"iframewindowIngresarEvidencia" + idWindowObEvidencia, statusBar: true, posx:200,posy:50,width:756,height:330,title:"Consultar Otros", type:"iframe",modal: true});
			    $.updateWindowContent("iframewindowIngresarEvidencia" + idWindowObEvidencia,'<iframe src="<%= request.getContextPath() %>/IngresarOtros.do?idOtros='+idObjeto+'&tipoObjeto=OTRO&anularConsultaCadCus=1&esModoConsulta=1" width="1050" height="600" />');
			}
		}
	}
	
	function consultarEslabonesXidEvidencia(idEvidencia){
           jQuery("#gridEslabon").jqGrid({
                url:'<%=request.getContextPath()%>'+'/consultarEslabonesEvidenciaParaGrid.do?idEvidencia=' + idEvidencia,
                datatype: "xml",
                colNames:['ID Eslab&oacute;n', 'Tipo de Eslab&oacute;n','Fecha de inicio de movimiento','Fecha de fin de movimiento', 'N&uacute;mero de Eslab&oacute;n' ],
                colModel:[
					{name:'idEslabon',index:'1',  sortable:true, width:120},
                    {name:'tipoEslabon',index:'2',  sortable:true, width:100},
                    {name:'fechaInicio',index:'3',  sortable:true, width:200},
                    {name:'fechaFin',index:'4',  sortable:true, width:200},
                    {name:'numeroDeEslabon',index:'5',  sortable:true, width:120},
                ],
                pager: jQuery('#paginadorEslabon'),
                rowNum:10,
                rowList:[10,20,30],
                autowidth: true,
                autoheight:true,
                height:150,
                sortname: '1',
                viewrecords: true,
                sortorder: "desc",
    			onSelectRow: function(rowid) {
                	gridDocumentosEnPestaniaEslabones(rowid);
                	//alert(rowid);
                	//Muestra el detalle del Eslabon
                	$.newWindow({id:"iframewindowDetalleEslabon", statusBar: true, posx:15,posy:50,width:756,height:250,title:"Consultar detalle de Eslab&oacute;n", type:"iframe",modal: true});
        		    $.updateWindowContent("iframewindowDetalleEslabon", '<iframe src="<%=request.getContextPath()%>/consultaEslabon.do?idEslabon='+ rowid +'" width="756" height="250" />');
        		    
                }
            }).navGrid('#paginadorEslabon',{edit:false,add:false,del:false});
			$("#gridEslabon").trigger("reloadGrid");	
			//Finaliza grid para la consulta de Almacenes
	}
	
	
    var banderaCargarORecargarGridDocumentosEnPestaniaEslabones=0;
	function gridDocumentosEnPestaniaEslabones(idEslabon) {
		if(banderaCargarORecargarGridDocumentosEnPestaniaEslabones==0){
 			jQuery("#gridDocumentosEnPestaniaEslabones").jqGrid({
							url:'<%=request.getContextPath()%>/consultarDocumentosXIdEslabon.do?idEslabon='+idEslabon, 
							datatype: "xml", 						
							colNames:['Área del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
							colModel:[ 	{name:'area',index:'area', width:200, hidden:true},
										{name:'FechaActividad',index:'fechaActividad', width:170, hidden:true},							
										{name:'NombreActividad',index:'nombreActividad', width:400, hidden:true},
							           	{name:'Tipo',index:'tipo', width:155}, 
										{name:'Nombre',index:'nombre', width:255},
							           	{name:'Fecha',index:'fecha', width:170, align:'center'}
										],
				pager: jQuery('#paginadorDocumentosEnPestaniaEslabones'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'Fecha',
				viewrecords: true,
				sortorder: "desc",
				onSelectRow: function(id) {
						consultaPDF(id);
					}
			}).navGrid('#paginadorDocumentosEnPestaniaEslabones',{edit:false,add:false,del:false});
 			banderaCargarORecargarGridDocumentosEnPestaniaEslabones=1;
		}else{
	    	jQuery("#gridDocumentosEnPestaniaEslabones").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosXIdEslabon.do?idEslabon='+idEslabon,datatype: "xml" });
			$("#gridDocumentosEnPestaniaEslabones").trigger("reloadGrid");		
	    }
	 }

	function consultaIdEslabonPorTipo(idEvidencia, tipoEslabon){
    	var parametros="";
		parametros+="&idEvidencia="+idEvidencia;
		parametros+="&tipoEslabon="+tipoEslabon;
		var idEslabon = 0
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaUltimoEslabonXEvidenciaYTipo.do',
			data: parametros,
			dataType: 'xml',
			success: function(xml){
					if((parseInt($(xml).find('code').text())==0) && ($(xml).find('error').text()!=1))
					{	
						//Identificador del Eslabon 
				        if($(xml).find('eslabonDTO').find('eslabonId') != null){
				        	idEslabon = $(xml).find('eslabonDTO').find('eslabonId').first().text();
				        }							
					}
			}
		});
		return idEslabon; 
    }
	
    function ocultaDetalleSolicitud(){
    	$("#tabES").hide();	
    	$("#tabsconsultaprincipal-1").hide();  
    	$("#tabsprincipalconsulta").tabs('select', 1);
    }
					
	</script>
	
	<!--ESTILOS PARA LAS TABS-->
	
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
	<div id="tabsprincipalconsulta">
        <ul>
            <li id="tabES" ><a href="#tabsconsultaprincipal-1" onclick="gridDocumento()">Detalle de la solicitud</a></li>
            <li id="tabEslabon" ><a href="#tabsconsultaprincipal-2">Registrar Movimiento</a></li>		
            <li id="tabEvidencias" ><a href="#tabsconsultaprincipal-3">Evidencia</a></li>
            <li id="tabEslabones" ><a href="#tabsconsultaprincipal-4">Eslabones</a></li>
        </ul>
			
			<div id="tabsconsultaprincipal-1">
				
				<jsp:include page="/WEB-INF/paginas/consultaEslabonInclueView.jsp" flush="true"></jsp:include>
				
				
				<p>&nbsp;</p>
				<!-- Inicio seccion de documento -->	
				<div id="divGridDocumentos">
					<table id="gridDocumentos"></table>
					<div id="paginadorDocumentos"></div>
				</div>
				
				<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
				<input type="hidden" name="documentoId" />
				</form>
				<!-- Fin seccion de documento -->					
			</div>
           

        
		<div id="tabsconsultaprincipal-2">
			<jsp:include page="/WEB-INF/paginas/eslabonView.jsp" flush="true"></jsp:include>			
		</div>
            
            
            
            <div id="tabsconsultaprincipal-3">		
                <table width="571" border="0">
                      <tr>
                        <td width="169">Numero de caso:</td>
                        <td width="386"><input type="text" name="txtNumCaso" id="txtNumCaso" size="30" disabled="disabled"/></td>
                      </tr>
                      <tr>
                        <td width="169">Numero de expediente:</td>
                        <td width="386"><input type="text" name="txtNumExpediente" id="txtNumExpediente" size="30" disabled="disabled" /></td>
                      </tr>
                      <tr>
                        <td width="169">Numero de cadena:</td>
                        <td width="386"><input type="text" name="txtNumCadena" id="txtNumCadena" size="30"  disabled="disabled"/></td>
                      </tr>			      
                      <tr>
                        <td width="169">Cadena de custodia:</td>
                        <td width="386"><input type="text" name="txtNombreCadena" id="txtNombreCadena" size="30"  disabled="disabled"/></td>
                      </tr>                    
                      <tr>
                        <td>Numero de evidencia:</td>
                        <td><input type="text" name="txtNumEvi" id="txtNumEvi" size="30" disabled="disabled"/></td>
                      </tr>
                      <tr>
                        <td>Nombre de evidencia:</td>
                        <td><input type="text" name="txtNombreEvi" id="txtNombreEvi" size="30" disabled="disabled"/></td>
                      </tr>
                      <tr style="display:none">
                        <td>Código:</td>
                        <td><input type="text" name="txtCodigo" id="txtCodigo" size="30" disabled="disabled"/></td>
                      </tr>	
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>			  
                    </table>
                    <br>
                    <input type="button" name="button" id="buttonGenerarReporte" value="Generar Reporte" onclick="consultaEvidencias()" class="btn_modificar"/>
                    <input type="button" name="button" id="btnVerObjeto" value="Ver Objeto" onclick="abreVentanaConsultaObjeto();" class="btn_modificar"/>
                    <br/>
                    <br>
                    <div id="divRerporteGeneral">
                                <table align="center" id="tablaInventarioDeEvidenciasPorCaso" width="800px"></table>
                                <div id="pager1"></div>
                    </div>
			</div>
			
			
			<!-- Inicia seccion de eslabones -->
			<div id="tabsconsultaprincipal-4">

	           <div id="divGridEslabon"> 
                     <table id="gridEslabon"></table>
                     <div id="paginadorEslabon"></div>
               </div>
				
			   <br>
			   <div id="divGridDocumentosEnPestaniaEslabones">
					<table id="gridDocumentosEnPestaniaEslabones"></table>
					<div id="paginadorDocumentosEnPestaniaEslabones"></div>
				</div>
				
				
			</div>
			<!-- Finaliza seccion de eslabones -->
			
	</div>
</body>
</html>