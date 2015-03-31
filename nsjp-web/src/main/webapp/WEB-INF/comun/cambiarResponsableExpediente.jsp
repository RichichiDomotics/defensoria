<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page import="mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	FuncionarioDTO funcionarioDTO = usuarioDTO.getFuncionario();
	CatDiscriminanteDTO catDiscriminanteDTO = funcionarioDTO.getDiscriminante();
	RolDTO rolDTO = usuarioDTO.getRolACtivo().getRol();
	JerarquiaOrganizacionalDTO jerarquiaOrganizacionalDTO = rolDTO.getJerarquiaOrganizacionalDTO();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>Cambiar Responsable A Expedientes</title>
	
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
		<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />				
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
			
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>			
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
		

		<script type="text/javascript">
			var contextoPagina = "${pageContext.request.contextPath}";
			var esCordinadorGeneralAMP = <%=request.getAttribute("ampGeneral")%>;
		//INCIA READY
			$(document).ready(function() {
				
				
				$( "#tabs" ).tabs();
				$( "#tabs" ).tabs('select',2);
				$( "#tabs" ).tabs({disabled: [1]});
				cargarGrid(1);
				cargarGrid(2);
				cargarHistorico();
	            
				$("#cmbTipoBusqueda").multiselect({ 
					multiple: false, 
					minWidth: 200,
					header: "Seleccione", 
					position: { 
						my: 'top', 
						at: 'top' 
					},
					selectedList: 1, 
					close: 	function (event, ui){
								onChangeTipoBusqueda();
							}
				});	    
				
				consultarFuncionarios();
	                    
			});
				
		//TERMINA READY
		
			function consultarFuncionarios(){
				
				$.ajax({
	   				type: 'POST',
	   				url: '<%=request.getContextPath()%>/consultarFuncionariosFiltro.do',
	   				data: {
	   						esCordinadorGeneralAMP:esCordinadorGeneralAMP,	
	   						esGrid: false,
								"funcionarioDTO.buscarPorJerarquiasHijas": true,
	   							<%if (Roles.getByValor(rolDTO.getRolId()) == Roles.DSE) {%>
	   								"idRol": '<%=Roles.ASE.getValorId()%>'
	   							<%} else { %>
									//"funcionarioDTO.claveFuncionario": 1,
									"funcionarioDTO.discriminante.catDiscriminanteId": <%=catDiscriminanteDTO.getCatDiscriminanteId()%>,
									"funcionarioDTO.jerarquiaOrganizacional.jerarquiaOrganizacionalId": <%=jerarquiaOrganizacionalDTO.getJerarquiaOrganizacionalId()%>
								<% } %>
	   				},
	   				dataType: 'xml',
	   				async: false,
	   				success: function(xml){
	   					$(xml).find('FuncionarioDTO').each(function(){

							var opt = 	$('<option />', {
								value: 	$(this).find('claveFuncionario').text(),
								text: 	$(this).find('nombreFuncionario').text() + " " + 
										$(this).find('apellidoPaternoFuncionario').text()+ " " + 
										$(this).find('apellidoMaternoFuncionario').text() + " - " +
										$(this).find('descripcionRol').text()
										
							}); 	   						
							
							$("#cmbFuncionarios").append(opt);
	   						
	   					});
	   									
						$("#cmbFuncionarios").multiselect({ 
							multiple: false, 
							minWidth: 300,
							header: "Seleccione", 
							position: { 
								my: 'top', 
								at: 'top' 
							},
							selectedList: 1, 
							close: 	function (event, ui){
							}
						});			   					
	   				}				
	            });
			}
		
		
		
			function onChangeTipoBusqueda() {
				
			  	var seleccion = $("#cmbTipoBusqueda option:selected").val();		

				$("#porExpediente").hide();
				$("#porExpediente").val('');
				$("#porFuncionario").hide();
				$("#btnBuscar").hide();	
											
				switch(seleccion){
					case "1":
							$("#porExpediente").show();
							$("#btnBuscar").show();
							break;
					case "2":
							$("#porFuncionario").show();
							$("#btnBuscar").show();	
							break;
				}
				
				
			}		

		
		// INICIAL VARIABLES DE CONFIGURACION DE LOS GRID
			var gridExpedientes = { 
				url:"",
				datatype: "local", 
				postData: {},
				mtype: 'POST',
				colNames:[	'Caso',
							'Expediente',
							<%if (Roles.getByValor(rolDTO.getRolId()) == Roles.DSE) { %>
								'NUS',
	   							'Sentenciado',
	   						<% } %>
							 
							'Estatus',
							'Id Funcionario', 
							'Nombre Del Responsable', 
							'Delito Principal', 
							'Historial'], 
				colModel:[
							{name:'Detalle',index:'1'},
							{name:'Expediente',index:'2'},
							
							<%if (Roles.getByValor(rolDTO.getRolId()) == Roles.DSE) { %>
								{name:'NUS',index:'2.1'},
	   							{name:'Sentenciado',index:'2.2'},
	   						<% } %>
							{name:'Estatus',index:'3'},
							{name:'idFuncionario', index:'4', hidden: true},
							{name:'NombreResponsable',index:'5'}, 
							{name:'Delito',index:'6'},
							{name:'Historial',index:'7', align:"center", sortable:false, width:100},
						],
				autowidth: true,
				pager: "#pagerGridCambiarResponsable",
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: true,
				sortname: '1',
				viewrecords: true,
				onSelectRow: function(id){},
				ondblClickRow: function(id) {
					detalleExpediente(id);
				},
				caption: "Seleccione Los Expediente A Reasignar",
				sortorder: "desc",
				multiselect: true,
				gridComplete: function () {
					seleccionarItems($(this));
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}
			};
		
			var gridFuncionarios = { 
				url:"<%=request.getContextPath()%>/consultarFuncionariosFiltro.do",
				postData: 	{
								esCordinadorGeneralAMP:esCordinadorGeneralAMP,
								esGrid: true,
								"funcionarioDTO.buscarPorJerarquiasHijas": true,
	   							<%if (Roles.getByValor(rolDTO.getRolId()) == Roles.DSE) {%>
	   								"idRol": '<%=Roles.ASE.getValorId()%>'
	   							<%} else { %>
									//"funcionarioDTO.claveFuncionario": 1,
									"funcionarioDTO.discriminante.catDiscriminanteId": <%=catDiscriminanteDTO.getCatDiscriminanteId()%>,
									"funcionarioDTO.jerarquiaOrganizacional.jerarquiaOrganizacionalId": <%=jerarquiaOrganizacionalDTO.getJerarquiaOrganizacionalId()%>
								<% } %>
							},
				datatype: "xml",
				mtype: "POST",
				colNames:['Id Funcionario','Nombre Del Funcionario', 'Rol Principal', 'Área Del Funcionario'], 
				colModel:[
							{name:'Detalle',index:'1', width:100},
							{name:'Estatus',index:'2'},
							{name:'NombreResponsable', index:'3'}, 
							{name:'AreaResponsable', index:'4'}
						],
				autowidth: true,
				shrinkToFit:true,
				pager: "#pagerGridNuevoResponsable",
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: true,
				sortname: '1',
				viewrecords: true,
				onSelectRow: function(id){},
				ondblClickRow: function(id) {
					cargarGrid(3);
				},
				caption: "Seleccione Al Nuevo Responsable: ",
				sortorder: "desc"			
			};
			
			var gridHistorico = { 
				url:"",
				datatype: "local",
				colNames:['N&uacute;mero Expediente','Id Responsable', 'Nombre Del Responsable', 'Id Asigna', 'Nombre De Quien Asigna', 'Id Revocado', 'Nombre de Quien Fue Revocado', 'Fecha Inicio', 'Fecha Fin'], 
				colModel:[
							{name:'NumeroExpediente',index:'NumeroExpediente'},
							{name:'IdResponsable',index:'FuncionarioActual', sortable:false, width:80}, 
							{name:'NombreResponsable',index:'FuncionarioActual'},
							{name:'IdAsigna',index:'FuncionarioAsiigna', sortable:false, width:50}, 
							{name:'NombreAsigna',index:'FuncionarioAsigna'},
							{name:'IdAnterior',index:'FuncionarioAnterior', sortable:false, width:70}, 
							{name:'NombreAnterior',index:'FuncionarioAnterior'},							
							{name:'FechaInicio',index:'FechaInicio', align:"center", width:100},
							{name:'FechaFin',index:'FechaFin', align:"center", width:100}
						],
				autowidth: true,
				shrinkToFit:true,
				pager: "#pagerGridHistorico",
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: true,
				sortname: 'NumeroExpediente',
				viewrecords: true,
				onSelectRow: function(id){},
				ondblClickRow: function(id) {
					cargarGrid(1);
				},
				caption: "Histórico De Responsables De Un Expediente ",
				sortorder: "desc"			
			};
			
			
		
		// TERMINA VARIABLES DE CONFIGURACION DE LOS GRID
		
			function cargarGrid(opcion) {
				opcion = parseInt(opcion, 10);
			
				switch(opcion){
					case 1 :
						$("#gridCambiarResponsable").GridUnload();
						$("#gridCambiarResponsable").jqGrid(gridExpedientes)
							.navGrid("#pagerGridCambiarResponsable",{edit:false,add:false,del:false});	 				
						break;
					case 2 :
						$("#gridNuevoResponsable").GridUnload();
					 	$("#gridNuevoResponsable").jqGrid(gridFuncionarios)
							.navGrid("#pagerGridNuevoResponsable",{edit:false,add:false,del:false});
					 	break;
				}
				
				try {
					ajustarGridAlCentro($("#gridCambiarResponsable"));
					ajustarGridAlCentro($("#gridNuevoResponsable"));
				}catch(e){}
			}

			function cargarHistorico() {
				$("#gridHistorico").GridUnload();
			 	$("#gridHistorico").jqGrid(gridHistorico)
							.navGrid("#pagerGridHistorico",{edit:false,add:false,del:false});
				try {
					ajustarGridAlCentro($("#gridHistorico"));
				}catch(e){}
			}
	
			function buscar(){
				var seleccion = $("#cmbTipoBusqueda option:selected").val();
				
					switch(seleccion){
						case "1":
							gridExpedientes.url = "<%=request.getContextPath()%>/buscarExpedientePorNumeroDeExpediente.do";
							gridExpedientes.postData = {
									esCordinadorGeneralAMP:esCordinadorGeneralAMP,
									noExpediente: $("#textoBuscar").val(), 
									reasignarExpediente: true,
									verHistorico: true
								};
							gridExpedientes.datatype = "xml";						
							break;
						case "2":
							gridExpedientes.url = "<%=request.getContextPath()%>/consultarExpedientesPorFuncionario.do";
							gridExpedientes.postData = {
									"funcionarioDTO.discriminante.catDiscriminanteId": <%=catDiscriminanteDTO.getCatDiscriminanteId()%>,
									"funcionarioDTO.claveFuncionario": $("#cmbFuncionarios option:selected").val(),
									"funcionarioDTO.jerarquiaOrganizacional.jerarquiaOrganizacionalId": <%=jerarquiaOrganizacionalDTO.getJerarquiaOrganizacionalId()%>,
									reasignarExpediente: true,
									verHistorico: true
								};
							gridExpedientes.datatype = "xml";						
							break;
					}
				 cargarGrid(1);
			}
			
			function verHistorial(id){
				$("#tabs").tabs('select', "tabs-2");
				$("#tabs").tabs( "option", "disabled", false );
				$("#tabs").tabs('select', "tabs-2");
				gridHistorico.url = "<%=request.getContextPath()%>/consultarHistoricoExpediente.do";
				gridHistorico.postData = {
								numeroExpediente: id
					};
				gridHistorico.datatype = "xml";			
				cargarHistorico();	
			}

			function  seleccionarItems(grid){
				var currentPage = grid.getGridParam('page').toString();
			
				//retrieve any previously stored rows for this page and re-select them
				var retrieveSelectedRows = grid.data(currentPage);
				if (retrieveSelectedRows) {
			         $.each(retrieveSelectedRows, function (index, value) {
			             grid.setSelection(value, false);
					});
				}
			}
			
			
			
			function guardarItemsSeleccionados(grid) {
				var pagerId = grid.getGridParam('pager').toString();
				if(pagerId.indexOf("#") != -1){
					pagerId = pagerId.substring(1, pagerId.length);
				}
				// ger paper id like "pager" 
				var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
				var saveSelectedRows = grid.getGridParam('selarrrow'); 
				//Store any selected rows 
				grid.data(pageValue.toString(), saveSelectedRows); 
			}

			function obtenerSeleccionados(grid){
				guardarItemsSeleccionados(grid);
			
			    var retrieveSelectedRows = grid.data();       
			    var arr_values = new Array();
			
			    if (retrieveSelectedRows) {
			        $.each(retrieveSelectedRows, function (index, value) {
			            $.each(value, function (index, sub_value) {
			                if(typeof(sub_value)=='string')
			                arr_values.push(sub_value);
			            });
			        });
			    }
			
			    return arr_values;
			}
			
			function eliminarItemsSeleccionados(grid){
				guardarItemsSeleccionados(grid);
				grid.jqGrid('resetSelection');
			    var retrieveSelectedRows = grid.data();       
			    if (retrieveSelectedRows) {
			        $.each(retrieveSelectedRows, function (index, value) {
			            $.each(value, function (sub_index, sub_value) {
			                if(typeof(sub_value)=='string'){
			                	grid.data(index, "");
			                }
			            });
			        });
			    }
			    
			}			
				
			function obtenerIdFilaSeleccionada(grid){
				var id = grid.jqGrid('getGridParam','selrow');
				if(id) {
					return id;
				} else {
					return false;
				}
			}
			
			function validarNuevoFuncionario() {
				var numerosExpedientes = obtenerSeleccionados($("#gridCambiarResponsable"));
				
				var funcionario = obtenerIdFilaSeleccionada($("#gridNuevoResponsable"));
				
				for (var nExp in numerosExpedientes) {
					var ret = $("#gridCambiarResponsable").jqGrid('getRowData',numerosExpedientes[nExp]);
					if(ret.idFuncionario == funcionario) {
						return false;
					}
				}
				return true;
			}
					
			function reasignarAFuncionario(){
				var numerosExpedientes = obtenerSeleccionados($("#gridCambiarResponsable"));
				
				var funcionario = obtenerIdFilaSeleccionada($("#gridNuevoResponsable"));				
				
				if (numerosExpedientes.length <=0){
					customAlert("Seleccione al menos un n&uacute;mero de expediente", "Error");
					return false;
				}

				if (funcionario == false){
					customAlert("Seleccione un funcionario de la lista", "Error");
					return false;
				}
				
				if (!validarNuevoFuncionario()) {
					customAlert("Uno o m&#x00E1;s Expedientes se est&#x00E1;n reasignando al responsable que tienen actualmente.<br />" +
							"Por favor verifique que el responsable actual sea diferente al nuevo responsable.", "Error");
					return false;							
				}
				
				$.ajax({
					type: 'POST',
					//traditional: true,
					url:  contextoPagina + '/reasignarFuncionarioAExpedinte.do',
					traditional :true,
					data: 	{ 
								"claveFuncionario" : funcionario,
								"numerosExpedientes" : numerosExpedientes
							},
					dataType: 'xml',
					async: false,
					success: function(xml){
						cancelarReasignacion();
						cargarGrid(1);		
						customAlert($(xml).find("body").text(), "Reasignar Expedientes");
						
					}
				});
			}
			
			function cancelarReasignacion (){
				eliminarItemsSeleccionados($("#gridCambiarResponsable"));
				
				var funcionario = obtenerIdFilaSeleccionada($("#gridNuevoResponsable"));
				
				if(funcionario != false){
					$("#gridNuevoResponsable").jqGrid('setSelection', funcionario);
					$("#gridNuevoResponsable").jqGrid('resetSelection');
				}				
			}

			function detalleExpediente(id){				
				customVentana("iframewindowDetalleExp","Detalle de Caso", "/BusquedaExpediente.do","?abreenPenal=abrPenal&pantallaSolicitada=4&flagIndexProcView=1&ingresoDenuncia=false&idNumeroExpediente="+id);
			}
			
			function regresaGrid(){
			}			

		</script>
	</head>
	<body>
		<table border="0" width="100%">
			<tr>
				<td align="right" width="20%" valign="top">Tipo de b&uacute;squeda:</td>
				<td align="left" width="25%" valign="top">
					<select name="cmbTipoBusqueda" id="cmbTipoBusqueda" style="width: 'auto';" >
				        <option value="-1" >-Seleccione-</option>
				        <option value="1" >Por Expediente</option>
				        <option value="2" >Por Funcionario</option>
				    </select>	
				</td>
				<td width="35%" valign="top">
					<span id="porExpediente" style="display: none;"> 
						Criterio:&nbsp;
						<input type="text" name="textoBuscar" id="textoBuscar" style="width: 80%; display: inline;"/>
						<div>* Sólo se permiten números, letras y los caracteres especiales: "-","/" y "Y"</div>
					</span>
					<span id="porFuncionario" style="display: none;"> 
						Funcionario:&nbsp;
						<select name="cmbFuncionarios" id="cmbFuncionarios"></select>
					</span>
				</td>
				<td width="20%" valign="top">
					<input type="button" name="btnBuscar" id="btnBuscar" value="Buscar" onclick="buscar();" class="btn_Generico" style="margin-left: 20px; display: none;" />				
				</td>
			</tr>
		</table>
	
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">Expedientes</a></li>
				<li><a href="#tabs-2">Historicos</a></li>
			</ul>
			<div id="tabs-1">
					<table id="gridCambiarResponsable"></table>
					<div id="pagerGridCambiarResponsable"></div>
					<table id="gridNuevoResponsable"></table>
					<div id="pagerGridNuevoResponsable"></div>
					<div style="width: 100%; text-align: center; padding-top: 10px; padding-bottom: 10px;">
						<input type="button" name="btnReasinar" id="btnReasignar" value="Reasignar A Funcionario" onclick="reasignarAFuncionario();" class="btn_Generico" />
						<input type="button" name="btnCancelar" id="btnCancelar" value="Cancelar" onclick="cancelarReasignacion();" class="btn_Generico" />
					</div>					
			</div>
			<div id="tabs-2">
				<table id="gridHistorico"></table>
				<div id="pagerGridHistorico"></div>
			</div>
			
		</div>
	</body>
</html>