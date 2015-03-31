<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Elaborar Solicitud</title>
	
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0"></iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<script type="text/javascript">
	
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"Sin Resolutivo"%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"Sin Audiencia"%>';
	var numExpIdGlobal=0;
	var idWindowIngresarDenunciante = 1;
	var idWindowIngresarVictima = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarTestigo = 1;
	var idWindowIngresarTraductor = 1;
	var idWindowIngresarQuienDetuvo = 1;
	
	var idTipoSolicitud=0;
	var documentoParcialGuardado="";			
	
		jQuery().ready(function () {
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#imprimirNarraTiva').click(crearPdf);
			$('#guardadoParcialNarrativa').click(guardadoParcial);
			$('#vistaPreliminarNarrativa').click(elaborarVistaPreliminar);			
			cargarDocumento();
			cargarDatosExpediente();
						
			//Cambios para la pantalla de seleccionar destinatario
			cargaComboInstitucion();		//Funcion que carga el combo de las Instituciones
			$('#instituciones').change(enSeleccionInstitucion);
			cargaCompoDepartamentos();
			$('#areas').change(cargaCompoFuncionarios);
			$('#areas').change(cargaCompoDepartamentos);
			$('#departamentos').change(cargaCompoFuncionarios);
			
			//$('#seleccionarDestinatario').hide();
			$('#menuSeleccionarDestinatario').click($('#dialog-confirmSeleccionarDestinatario').show());
			//$('#tblUsuarioExterno').hide();
			//$('#tblUsuarioSistema').hide();
			$('#divGridUsuariosExt').hide();
			$('#divGridUsuarios').hide();
			cargaGridUsuarios();
			cargaGridUsuariosExternos();
			$('#relacionarElementos').click(abreVentanaRelacionarElementos);
		    var numeroExpedienteId=<%= request.getAttribute("numeroExpedienteId")%>;
		    idTipoSolicitud=<%= request.getParameter("idTipoSolicitud")%>;
			numExpIdGlobal=numeroExpedienteId;
			
			jQuery("#btnEliminarInterno").button().click(
				function (){
					var id = jQuery("#gridUsuarios").jqGrid('getGridParam','selrow');
					jQuery("#gridUsuarios").jqGrid('delRowData',id);
					jQuery("#btnEliminarInterno").button( "option", "disabled", true );
				});
			
			jQuery("#btnEliminarExterno").button().click(
				function(){
					var id = jQuery("#gridUsuariosExt").jqGrid('getGridParam','selrow');
					jQuery("#gridUsuariosExt").jqGrid('delRowData',id);
					jQuery("#btnEliminarExterno").button( "option", "disabled", true );
				});
			
			cargaDatosEncabezado();
			consultarTipoTamanioPapel();
		});
	
		
		function guardadoParcial(){
		
			var recuperaTexto=$('.jquery_ckeditor').val();
			var nuevaActividad='<%=request.getParameter("nuevaActividad")%>';
			var numeroOficio=$('#iNumeroOficio').val();
			var documentoIdParcial='<%=request.getParameter("documentoId")%>';
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){documentoIdParcial=="";}
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
		    	data: 'parcial=true&formaId=<%=request.getParameter("formaId") %>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    	'&documentoId='+documentoIdParcial+
		    	'&iNumeroOficio='+numeroOficio+
		    	'&nuevaActividad='+nuevaActividad+
		    	'&texto='+escape(recuperaTexto)+
		    	'&seleccionTamanioPapel='+seleccionTamPapel,
		    	dataType: 'xml',
		    	success: function(xml){
		    		var valorNumDocParcial = $(xml).find('long').first().text();
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('horaActual').text();
	    			}
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('fechaActual').text();
	    			}	    			
	    			if(valorNumDocParcial == ""){
	    				valorNumDocParcial = $(xml).find('response').find('body').find('string').text();
	    			}
	    			
	    		   var idsDocParcial = valorNumDocParcial.split(',');

	    		   if(idsDocParcial.length==2){
	    			   $('#iNumeroOficio').val(idsDocParcial[1]);
	    			   documentoParcialGuardado=idsDocParcial[0];
	    			}

	    		   try{window.parent.documentos();}catch(e){}
	    		   customAlert("El documento se ha generado exitosamente");
		    	},
		    	error: function(xml){
		    	}
			});
			validadDatosSolicitud();
		}
		function cargarDocumento(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do?idAudiencia='+idAudiencia+'&idResolutivo='+idResolutivo+'',
		    	data: 'formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    	'&documentoId=<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    		$("#iNumeroOficio").val( $(xml).find('<%=ConstantesGenerales.NUMERO_FOLIO_TAG_BUSQUEDA%>').text());
		    	}
			});
		}
		
		function cargarDatosExpediente(){
			
			if(!esconderArbol){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/CargarArbolExpedienteEnDocumento.do',
			    	data: 'numeroUnicoExpediente='+numeroUnicoExpediente,
			    	dataType: 'xml',
			    	success: function(xml){
			    		//Obtener Parametros de Configuracion
			    		var probableResponsableArbolTituloSeccion = '<bean:message key="probableResponsableArbolTituloSeccion"/>';
			    		var probableResponsableArbolTitulo = '<bean:message key="probableResponsableArbolTitulo"/>';
			    		
			    		contenido = escribirDatosGenerales(xml);			
			    		contenido += escribirInvolucrados(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo);
			    		contenido += escribirUnidadesEspecializadas(xml);
			    		contenido += escribirListaObjetos(xml);
			    		contenido += escribirDelitos(xml);
			    		//contenido += escribirHechos(xml);
			    		
			    		$("#accordionDatosExpediente").append(contenido);
			    		$("#accordionDatosExpediente").treeview();
			    	}
				});
			}else{
				$("#marcoArbolExpediente").css('display','none');
				$("#idExpedientes").css('display','none');
				$("#tdArbolExp").css('width','1px');
			}
		}
		
		function refresca(){
			$("#accordionDatosExpediente").empty();
			cargarDatosExpediente();
		}
		
		/*
		*Funcion que recuepera el texto del editor, y lo envia como una nueva peticion 
		*para que se imprima con formato PDF
		*/
		function crearPdf(){
			if(confirm("¿Est\u00E1 seguro que quiere guardarlo definitivamente?")) {
				if(validadDatosSolicitud() == 1){
					//mostramos los divs en el padre de la pestaña de Acciones.
					try{window.parent.muestraDIVSCanalizacion();}catch(e){}
					var recuperaTexto=$('.jquery_ckeditor').val();
					document.frmDoc.parcial.value = "";
					document.frmDoc.texto.value = recuperaTexto;
					document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel;
					document.frmDoc.action ="<%= request.getContextPath() %>/GenerarDocumento.do";
					if(document.frmDoc.documentoId.value==""){
						document.frmDoc.documentoId.value = documentoParcialGuardado;
						if(document.frmDoc.documentoId.value == null || document.frmDoc.documentoId.value == "null"){
							document.frmDoc.documentoId.value = "";
						}
					}
					document.frmDoc.submit();
					customAlert("El documento se ha generado exitosamente");
				}				
			}
		}
		
		/*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos() {
	
			$('#idDelitosCaso').empty();
		    $.ajax({
				type: 'POST',
		   	  	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		   	  	data: '',
		   	 	dataType: 'xml',
		   	  	success: function(xml){
					$('#idDelitosCaso').empty();
					$('#idDelitosCaso').append('<option value="-1">- Seleccione -</option>');
					$(xml).find('catCatalogo').each(function(){
						$('#idDelitosCaso').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				   	});
		   	  	}
		   	});
		}
	
		
	   /*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos2() {
	
			$('#idFormasParticipacion').empty();
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
					$('#idFormasParticipacionv').empty();
			    	$('#idFormasParticipacion').append('<option value="-1">- Seleccione -</option>');
		    		$(xml).find('catCatalogo').each(function(){
						$('#idFormasParticipacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
	
	   
	   /*
		*Funcion que carga la fecha actual del sistema y la agrega en la pantalla al campo fecha
		*/
		function cargaFechaCaptura(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('#generarDocumentoCmpFechaIngreso').val($(xml).find('fechaActual').text());
		    	}
			});
		}
	
	   
	   /*
		*Funcion que carga la hora actual del sistema y la agrega en la pantalla al campo hora
		*/
		function cargaHoraCaptura(){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/ConsultarHoraCaptura.do',
	    	    data: '',
	    	    dataType: 'xml',
	    	    success: function(xml){
	    			$('#idHoraDate').val($(xml).find('horaActual').text());
	    		}
			});
	    }

		function imprime(){
			
			var texto=$('.jquery_ckeditor').val();
			
			$.ajax({
				async: false,
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/GenerarDocumento.do',
	    	    data: 'texto='+$('.jquery_ckeditor').val(),
	    	    dataType: 'xml',
	    	    success: function(xml){
	    	    	customAlert("El documento se ha generado exitosamente");
	    		}
			});
		}
		
		//Inician cambios de RGG
		function muestraPopupSeleccionarDestinatario()
		{
				$( "#dialog-confirmSeleccionarDestinatario" ).dialog({
					resizable: false,
					width:650,
					height:450,					
					modal: true,
					buttons: {
						"Agregar": function() {
							var principal = "";
							var copia = "";									
							/**if($(':radio[name=TipoUsuario]:checked').val()==2){//Usuarios Externos
								var idPO = jQuery('#gridUsuariosExt').jqGrid('getGridParam','records') + 1;
								  var nombre = $("#nombre").val()+ " " + $("#apaterno").val() + " " + $("#amaterno").val();
								  var puesto = $("#puesto").val();				  
								  var correo = $("#correo").val();
								  
								  if(nombre !="" && puesto != ""){
									  if($(':radio[name=rbUsuarioExt]:checked').val()==1){
										  principal = "SI";
										  copia = "";	
									  }else{
										  principal = "";
										  copia = "SI";	
									  }
									  var direccion = $("#direccion").val();
									nuevoDestExt = new usuarioExterno(idPO,nombre, puesto, correo, principal, copia, direccion);
									agregarDestinatarioExterno(nuevoDestExt);
									//limpiaControles
									limpiaControles();
									$( this ).dialog( "close" );
									$( "#dialog:ui-dialog" ).dialog( "destroy" );  
								  }else{
									  customAlert("El nombre y el puesto son obligatorios");
								  }
								  
							}else{**/
								  //Usuarios Internos								  
								  var idPO = parseInt($("#funcionarios option:selected").val());
								  if(idPO != -1){
									  var infoFuncionario =  $("#funcionarios option:selected").text();
									  datos = infoFuncionario.split(',');
									  var nombre = datos[0];
									  var puesto = datos[1];			  
									  var direccion = datos[2];
									  
									  if($(':radio[name=rbUsuario]:checked').val()==1){
										  principal = "SI";
										  copia = "";	
									  }else{
										  principal = "";
										  copia = "SI";	
									  }			  							  
									  nuevoDestInt = new usuarioInterno(idPO,nombre, puesto, direccion, principal, copia);
									agregarDestinatarioInterno(nuevoDestInt);
									limpiaControles();
									$( this ).dialog( "close" );
									$( "#dialog:ui-dialog" ).dialog( "destroy" );
								  }else{
									  customAlert("Debes de seleccionar un funcionario");
								  }								  
							//}
	
						},
						"Terminar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
		}	
		
		//Permite llenar el combo de instituciones
		function cargaComboInstitucion() {
	     $.ajax({
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
	    	  data: '',
	    	  async: false,
	    	  dataType: 'xml',
	    	  success: function(xml){
	    	  	//INICIA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
		    	 	$(xml).find('instituciones').each(function(){
		    	 		<%
		    	 			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		    	 			if (usuario != null && usuario.getInstitucion() != null) {
		    	 		%>
		    	 		if ( $(this).find('clave').text() == '<%=usuario.getInstitucion().getConfInstitucionId()%>') {
							$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						}
						<% } %>
						
					});
					enSeleccionInstitucion();
				//TERMINA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
				}
	    	});
	     }
		
		/*
		* Permite llenar el combo de Areas
		* Funcion para deshabilitar combo areas
		* Permite hacer cargar las Areas por Id de la Institucion
		*/
		function enSeleccionInstitucion() {
		
		  	var selected = $("#instituciones option:selected");
	         $.ajax({
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/consultarCatalogoAreasDependiente.do',
		    	  data: 'idInstitucion=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
		    	  async: false,
		    	  dataType: 'xml',
		    	  success: function(xml){
		    		  	$('#areas').empty();
		    			$('#areas').append( '<option value="1">-Seleccione-</option>');
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');
		    		  if(idTipoSolicitud == <%= TiposSolicitudes.POLICIA_MINISTERIAL.getValorId() %>){
		    			  $(xml).find('areas').each(function(){
		    				  if($(this).find('clave').text() == '<%= Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong() %>'){
								$('#areas').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    				  }
							});
		    		  }else{
			    	 	$(xml).find('areas').each(function(){
						$('#areas').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						   });
		    		  }
		    	  }
		    	});
				
		}

		/*
		*Permite llenar el combo de departamentos: Funcion que dispara el Action para consultar departamentos
		*/	
		function cargaCompoDepartamentos() {
			var selected = $("#areas option:selected");
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoDepartamentosDependiente.do',
 	    	    data: 'idArea=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
				dataType: 'xml',
				success: function(xml){
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
				
					$(xml).find('departamentos').each(function(){
						$('#departamentos').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		
		//permite llenar el combo de funcionarios
		function cargaCompoFuncionarios() {
			  var institucion = $("#instituciones option:selected").val();
			  var area = $("#areas option:selected").val();
			  var departamento = $("#departamentos option:selected").val();			
				$.ajax({
					async: false,
					type: 'POST',
					url:'<%=request.getContextPath()%>/consultarPersonalOperativoAction.do?institucion='+ institucion +'&area='+ area +'&departamento='+ departamento +'', 
					dataType: 'xml',
					success: function(xml){
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');
					
						$(xml).find('row').each(function(){
							$('#funcionarios').append('<option value="' + $(this).attr('id') + '">' + $(this).find('nombre').text()+ ", " + $(this).find('puesto').text()+ ", " + $(this).find('email').text() + '</option>');
						});
					}
				});
		}  
				
		//INICIA GARGA DE GRID PARA USUARIO EXTERNOS
		function cargaGridUsuariosExternos(){
			jQuery("#gridUsuariosExt").jqGrid({
				datatype: "local",
				height: 110,
				colNames:['ID','Nombre','Puesto', 'Correo','Principal','Copia','Dirección'],
				colModel:[	
						  {name:'id',index:'id', width:60, sorttype:"int",hidden:true},
				          {name:'nombre',index:'nombre', width:200},
				          {name:'puesto',index:'puesto', width:200},
				          {name:'correo',index:'correo', width:200,hidden:true},
				          {name:'principal',index:'principal', width:60},
				          {name:'copia',index:'copia', width:60},
				          {name:'direccion',index:'direccion', width:260}
			    ],
			    onSelectRow: function (id){
			    	jQuery("#btnEliminarExterno").button( "option", "disabled", false );
			    },
			    rowNum:10,
				autowidth: false,
				rowList:[10,20,30],
				pager: jQuery('#pager2'),
				viewrecords: true,								
				multiselect: false,
				hiddengrid: false,
			    caption: "Destinatario(s) Externo(s)" 
		    }); 
			var mydata = [];
			for(var i=0;i<=mydata.length;i++)
				jQuery("#gridUsuariosExt").jqGrid('addRowData',i+1,mydata[i]); 
		}
		
		//INICIA GARGA DE GRID PARA USUARIO INTERNOS
		function cargaGridUsuarios(){
			jQuery("#gridUsuarios").jqGrid({
				datatype: "local",
				height: 110,
				colNames:['ID','Nombre','Puesto', 'Correo','Principal','Copia'],
				colModel:[	
						  {name:'id',index:'id', width:60, sorttype:"int"},
				          {name:'nombre',index:'nombre', width:200},
				          {name:'puesto',index:'puesto', width:200},
				          {name:'direccion',index:'direccion', width:200},
				          {name:'principal',index:'principal', width:60},
				          {name:'copia',index:'copia', width:60}
			    ],
			    onSelectRow: function (id){
			    	jQuery("#btnEliminarInterno").button("option","disabled",false);
			    },
			    rowNum:10,
				autowidth: false,
				rowList:[10,20,30],
				pager: jQuery('#pager1'),
				viewrecords: true,								
				multiselect: false,
				hiddengrid: false,
			    caption: "Destinatario(s) Usuarios del sistema" 
		    }); 			
		}
		
		//Se define la clase para un usuario Externo
		function usuarioInterno(id,nombre, puesto, direccion,principal,copia){
			this.id= id
		    this.nombre = nombre
		    this.puesto = puesto
		    this.direccion = direccion
		    this.principal = principal
		    this.copia = copia
		} 		
		function agregarDestinatarioInterno(nuevoDesInt){
			var mydata = [
						  {id:nuevoDesInt.id,nombre:nuevoDesInt.nombre,puesto:nuevoDesInt.puesto,direccion:nuevoDesInt.direccion,
							  principal:nuevoDesInt.principal,copia:nuevoDesInt.copia}						  
		              ];
			for(var i=0;i<=mydata.length;i++)
				jQuery("#gridUsuarios").jqGrid('addRowData',nuevoDesInt.id,mydata[i]);
			$('#divGridUsuarios').show();
			jQuery("#btnEliminarInterno").button( "option", "disabled", true );
		}
		
		//Se define la clase para un usuario Externo
		function usuarioExterno(id,nombre, puesto, correo,principal,copia,direccion){
			this.id= id
		    this.nombre = nombre
		    this.puesto = puesto
		    this.correo = correo
		    this.principal = principal
		    this.copia = copia
		    this.direccion = direccion
		} 		
		
		function agregarDestinatarioExterno(nuevoDesExt){
			var mydata = [
						  {id:nuevoDesExt.id,nombre:nuevoDesExt.nombre,puesto:nuevoDesExt.puesto,correo:nuevoDesExt.correo,
							  principal:nuevoDesExt.principal,copia:nuevoDesExt.copia,direccion:nuevoDesExt.direccion}						  
		              ];
			for(var i=0;i<=mydata.length;i++)
				jQuery("#gridUsuariosExt").jqGrid('addRowData',i+1,mydata[i]);
			$('#divGridUsuariosExt').show();
			jQuery("#btnEliminarExterno").button( "option", "disabled", true );
		}
		
		function limpiaControles(){
			  $( "#instituciones" ).attr('selectedIndex',0);
			  $( "#departamentos" ).attr('selectedIndex',0);
			  limpiaCombo("#areas");
			  limpiaCombo("#funcionarios");
			  //$( "#rbUsuario" ).attr('cheked')= false;
			  //$( "#rbUsuarioExt" ).attr('cheked')= false;
			  //$( "#rbUsuario" ).attr('cheked')= false;			  
			  $("#nombre").val("");				  
			  $("#apaterno").val("");				  
			  $("#amaterno").val("");				  
			  $("#puesto").val("");				  
			  $("#direccion").val("");
			  $("#correo").val("");
		}
		
		
		function abreVentanaRelacionarElementos(){
		    $.newWindow({id:"iframewindowRelacionarElementos", statusBar: true, posx:20,posy:5,width:850,height:480,title:"Relacionar elementos", type:"iframe"});
            $.updateWindowContent("iframewindowRelacionarElementos",'<iframe src="<%= request.getContextPath() %>/mostrarPantallaRelacionarElementos.do" width="850" height="480" />');
		}
		
		function validadDatosSolicitud(){
			totalDestinartario = jQuery('#gridUsuariosExt').jqGrid('getGridParam','records') + jQuery('#gridUsuarios').jqGrid('getGridParam','records');
			if(totalDestinartario >0 ){
				enviaDatosSolicitud()
				return 1;
			}else{
				customAlert('Debe de agregar al menos un destinatario');
				return 0;
			}					
		}

		function enviaDatosSolicitud(){
		    	var params = '&institucionSolicitante=' + 1;
			    params += '&solicitante=' + "";
			    params += '&numeroExpediente=' + numeroUnicoExpediente;
			    params += '&idsFuncionariosSolicitantes=' + obtenIdsDestinatariosInternos();
			    params += '&idSolicitud=' + $('#idSolicitud').val();
			    params += '&idTipoSolicitud=' + idTipoSolicitud;
			    
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registrarSolicitud.do',
				data: params, 
				async: false,
				dataType: 'xml',
				success: function(xml){										
					 if(parseInt($(xml).find('SolicitudDTO').find('documentoId').text())>0){
						 if($('#idSolicitud').val() == 0)
							 customAlert("La solicitud se envi\u00F3 correctamente");
						 else
							 customAlert("La solicitud se actualiz\u00F3 correctamente");
						 $('#idSolicitud').val(parseInt($(xml).find('SolicitudDTO').find('documentoId').text()));
					  }else
						  customAlert('Error al intentar guardar la solictud, inténtelo mas tarde');
				}
			});			   		
		}

		
		/**
		* Funcion que permite limpiar un combo e ingresa la opcion de seleccione
		*/
		function limpiaCombo(idCombo){
			$(idCombo).empty();
			$(idCombo).append('<option value="-1">-Seleccione-</option>');			
		}
		
		function obtenIdsDestinatariosInternos(){		
			var ids = "";
			var lista = jQuery("#gridUsuarios").getDataIDs();
			for(i=0;i<lista.length;i++){
					rowData= jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
					ids = ids + rowData.id + ",";
			 }
			return ids;			
		}		
		
		function inspeccionar(obj)
		{
		  var msg = '';		
		  for (var property in obj)
		  {
			if(typeof obj[property] == 'function')
			{
			  var inicio = obj[property].toString().indexOf('function');
			  var fin = obj[property].toString().indexOf(')')+1;
			  var propertyValue=obj[property].toString().substring(inicio,fin);
			  msg +=(typeof obj[property])+' '+property+' : '+propertyValue+' ;\n';
			}
			else if (typeof obj[property] == 'unknown')
			{
			  msg += 'unknown '+property+' : unknown ;\n';
			}
			else
			{
			  msg +=(typeof obj[property])+' '+property+' : '+obj[property]+' ;\n';
			}
		  }
		  return msg;
	}		
	</script>
</head>



<body>
	<!-- ETIQUETAS NECESARIAS PARA LOS CAMPOS DEL ENCABEZADO -->
	<table align="center" border="0" width="855px" height="50%">
		<tr><!-- MENU -->
			<td>
				<ul class="toolbar" id="menu_head">
                		<li id="menuSeleccionarDestinatario"  onclick="muestraPopupSeleccionarDestinatario()">Seleccionar destinatario</li>
                        <li id="relacionarElementos"  style="display:none">Relacionar elementos</li>
						<li id="guardadoParcialNarrativa" class="first">Guardado parcial</li>
						<li id="imprimirNarraTiva"><span></span>Guardado definitivo</li>
						<li id="vistaPreliminarNarrativa"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tamaño de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
				</ul>
			</td>
	  	</tr>
	</table>	
	<table align="center" border="0" width="855px" height="50%">
		<tr>
			<td width="20%">Nombre Servidor P&uacute;blico:</td>
			<td width=""><input type="text" title="Nombre del Servidor Publico" size="40" id="iNombreServidorPublico" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Hora de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="idHoraDate" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">Cargo:</td>
			<td width=""><input type="text" title="Puesto" size="40" id="iPuesto" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Fecha de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="generarDocumentoCmpFechaIngreso" name="generarDocumentoCmpFechaIngreso" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td>&Aacute;rea Administrativa:</td>
			<td><input type="text" title="Area Administrativa" size="40" id="iAreaAdministrativa" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td>Estado:</td>
			<td><input type="text" title="Estado" size="30" id="iEstado" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">N&uacute;mero de Oficio:</td>
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio"  /></td>
			<!--  
			<td>Ciudad:</td>
			<td><input type="text" title="Ciudad" size="30" id="iCiudad" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			-->
		</tr>
	</table>
		
	<!-- ETIQUETAS PARA LA SECCION DE LOS ELEMENTOS DEL EXPEDIENTE -->	
	<table align="center" width="1024px" border="0">
		<tr>			
			<td width="300px" valign="top" id="tdArbolExp">
				<h3><a href="#" id="idExpedientes">Elementos del Expediente</a></h3>
				
				<div style="height: 800px; 
						width: 300px;
						overflow: auto;
						border: 1px solid #666;
						padding: 0px;" id="marcoArbolExpediente">
						<ul id="accordionDatosExpediente" class="filetree"></ul>
				</div>
			</td>
			<td width="800px" valign="top" align="center">
				<div id="divGridUsuarios">				
					<table align="center" id="gridUsuarios" width="800px"></table>
					<div id="pager1"></div>
					<table align="center" id="botonesInternos" width="800px">
						<tr>
							<td align="right"><button id="btnEliminarInterno" type="button" class="btn_buscar">Eliminar</button></td>
						</tr>
					</table>				
					<br>
				</div>
				<div id="divGridUsuariosExt">
					<table align="center" id="gridUsuariosExt" width="800px"></table>
					<div id="pager2"></div>
					<table align="center" id="botonesExternos" width="800px">
						<tr>
							<td align="right"><button id="btnEliminarExterno" type="button" class="btn_buscar">Eliminar</button></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto">
				<br>	
						
				<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				
				</div>
				<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
					<input type="hidden" name="texto" value=""/>
					<input type="hidden" name="parcial" value=""/>
					<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
					<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
					<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
					<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
					<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
					<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
					<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
					<input type="hidden" name="nuevaActividad" value="<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>"/>
				</form>				
			</td>
		</tr>
	</table>
	
	<!-- ETIQUETAS PARA SELECCIONAR DESTINATARIO -->
	<div id="dialog-confirmSeleccionarDestinatario" title="Seleccionar Destinatario">
<!-- 	  <div id="seleccionarDestinatario"> -->
<!-- 	    <label>
	      <input type="radio" name="TipoUsuario" value="1" id="TipoUsuario_0">
	      Usuario del sistema</label>
	    <label>
	      <input type="radio" name="TipoUsuario" value="2" id="TipoUsuario_1">
	      Externo</label> -->
	    <table width="342" cellspacing="0" cellpadding="0" id="tblUsuarioSistema">
	      <tr>
	      	<td>&nbsp;</td>
	      </tr>
	      <tr>
	      	<td colspan="2" align="center">
	      		<font FACE="arial" SIZE=2>
					<b>Usuario del Sistema</b>
				</font>
	      	</td>
	      </tr>
	      <tr>
	      	<td>&nbsp;</td>
	      </tr>	      
	      <tr>
	        <td>Instituci&oacute;n:</td>
	        <td><select name="instituciones" id="instituciones">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td width="108">&Aacute;rea:</td>
	        <td width="232"><select name="area" id="areas">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td>Departamento:</td>
	        <td><select name="departamentos" id="departamentos">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td>* Funcionario:</td>
	        <td><select name="funcionarios" id="funcionarios" >
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>	      
	      <tr>
	        <td>&nbsp;</td>
          </tr>
	      <tr>
	        <td colspan="2" align="left"><label>
	          <input type="radio" name="rbUsuario" value="1" id="RadioGroup1_2" >
	          Destinatario principal</label>
	          <label>
	            <input type="radio" name="rbUsuario" value="2" id="RadioGroup1_3">
	            Destinatario copia</label></td>
          </tr>
        </table>
	    <!-- <table width="449" cellspacing="0" cellpadding="0" id="tblUsuarioExterno">
	      <tr>
	        <td>* Nombre:</td>
	        <td><input type="text" size="20px" id="nombre" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
          </tr>
	      <tr>
	        <td>Apellido Paterno:</td>
	        <td><input type="text" size="20px" id="apaterno" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
          </tr>
	      <tr>
	        <td>Apellido Materno:</td>
	        <td><input type="text" size="20px" id="amaterno" onkeypress="return soloLetrasNPunto(event,this.id);"/></td>
          </tr>
	      <tr>
	        <td width="133">* Cargo / Puesto</td>
	        <td width="314"><input type="text" size="20px" id="puesto"/></td>
          </tr>
          <tr>
	        <td>Email:</td>
	        <td><input type="text" size="20px" id="correo"/></td>
          </tr>
            <tr>
	        <td width="133">Dirección:</td>
	        <td width="314"><textarea id="direccion" COLS=45 ROWS=2></textarea></td>
          </tr>

	      <tr>
	        <td colspan="2" align="left">
	          <label>
	            <input type="radio" name="rbUsuarioExt" value="1" id="RadioGroup1_0">
	            Destinatario principal</label>
	          <label>
	            <input type="radio" name="rbUsuarioExt" value="2" id="RadioGroup1_1">
	            Destinatario copia</label>
	          <br>
	        </td>
          </tr>
        </table> -->
        <input type="hidden" size="20px" id="idSolicitud" value="0"/>
      <!-- </div> -->
	</div>
</body>
<script type="text/javascript">
$( "#dialog-confirmSeleccionarDestinatario" ).dialog();
$( "#dialog-confirmSeleccionarDestinatario" ).dialog( "destroy" );
</script>
</html>