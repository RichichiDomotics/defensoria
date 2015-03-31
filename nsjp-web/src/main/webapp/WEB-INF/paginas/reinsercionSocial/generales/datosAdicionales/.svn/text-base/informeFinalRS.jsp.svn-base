<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
var banderaGridDocumentosDigitalesPropios = true;
	jQuery().ready(function () {
		cargaGridDocumentosDigitalesPropios();
	});

	// Funcion que carga el grid con los nombre de los documentos digitales asociados al id de la solicitud de serv. periciales
	function cargaGridDocumentosDigitalesPropios(){ 

		if(banderaGridDocumentosDigitalesPropios == true){
			jQuery("#gridDocumentosDigitalesPropios").jqGrid({
				url:'<%=request.getContextPath()%>/ConsultaExpedientesDocumento.do',
				datatype: "xml", 
				postData:{
					numeroExpedienteId : numExpIdGlobal				
				},
				colNames:['Nombre de Documento', 'tieneArchivoDigital'],
				colModel:[	
							{name:'nombre',index:'nombre', width:255},
							{name:'tieneArchivoDigital',index:'tAD', width:255, hidden:true},
						 ],
				pager:"#pagerGridDocumentosDigitalesPropios",
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				width:600,
				height:300,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					if (rowid) {
						var ret = jQuery("#gridDocumentosDigitalesPropios").jqGrid('getRowData',id);
						if(ret.tieneArchivoDigital || ret.tieneArchivoDigital == "true"){
							consultaPDF(rowid);						
						}
					}
				},
				multiselect: true,
				gridComplete: function () {
					cambiarBotonesDialog();
					seleccionarItems($(this));
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}
				
			}).navGrid("#pagerGridDocumentosDigitalesPropios", {edit:false,add:false,del:false});
			banderaGridDocumentosDigitalesPropios= false;
		} else {
			jQuery("#gridDocumentosDigitalesPropios").jqGrid('setGridParam', 
						{
							url			:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do',
							postData	: 	{
												numeroExpedienteId : numExpedienteId
											},
							datatype	: "xml" });
			$("#gridDocumentosDigitalesPropios").trigger("reloadGrid");
		}
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



		//Variable para controlar el action para consultar pdf's
		var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

		/*
		*Funcion que aplica submit a la forma para abrir el documento solicitado
		*id= id del documento seleccionado en el grid de documentos
		*Así se obtenia anteriormente:
		*	
		*document.frmPDF.documentoId.value = id;
		*document.frmPDF.submit();
		*/
		function consultaPDF(id) {
			document.frmPDF.action=accionConsultarPdf+"?documentoId="+id;
			$("#frmPDF").submit();
		}

	function cambiarBotonesDialog() {
		var botones = [
	    	{
		        text: "Continuar",
		        click: function() {
					enviarDatosFinales();
			    }
		    },
		    {
		        text: "Cancelar",
		        click: function() { 
		        			$(this).dialog("close");
		        }
		    }			    
		];		
		jQuery("#datosAdicionalesModal").dialog('option', 'buttons', botones);
	}

	function enviarDocumentosAlJuez() {		
		bloquearPantalla();
		jsonParam = 
			{
				"destinatarios"				: obtenIdEInstitucionDestinatario(),
				"documentos" 				: obtenDocAdjuntos(), 
				"sentenciaId"				: sentenciaId,
		    	"institucionSolicitante" 	: "1",
			    "solicitante" 				: "",
			    "numeroExpediente" 			: numeroUnicoExpediente,
			    "idSolicitud"				: $('#idSolicitud').val(),
			    "idTipoSolicitud"			: idTipoSolicitud
			}
		;

		$.ajax({
			type: 'POST',
			url:  '<%=request.getContextPath()%>/enviarSolicitudOtrasInstituciones.do',
			async: false,
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(json){
				try {
					if (json.exito != undefined && json.exito != "undefined"){
						customAlert(json.exito, "Exito", cerrarCustomVentana);
					} else if (json.error != undefined && json.error != "undefined"){
						customAlert(json.error);
					}
				}catch(e){
					console.error(e);
				}
			}
		});
	}

		function obtenIdEInstitucionDestinatario(){					
			var lista = jQuery("#gridUsuarios").getDataIDs();
			var resultado =[];
			for(var i in lista ) {
				var ret = jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
				var destinatario = {
					idFuncionario	: ret.id, 
					instId			: ret.instId,
					areaDestinoId	: ret.areaDestinoId,
					esOtraInst		: ret.esOtraInst};
				resultado.push(destinatario); 
			}
			return resultado;
		}

	function obtenDocAdjuntos(){
		var docsAdjuntos = obtenerSeleccionados($("#gridDocumentosDigitalesPropios"));
				
		docsAdjuntos.push(documentoParcialGuardado);
		
		if (docsAdjuntos.length <=0){
			return false;
		}
		var documentos = [];
		for(var i in docsAdjuntos){
			var doc = {
				idDocumento : docsAdjuntos[i]
			};
			documentos.push(doc);
		}
		return documentos;
	}

</script>

<fieldset>
	<form 	name="frmPDF"
			id="frmPDF" 
			action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<!--<input type="hidden" name="documentoId" />-->
	</form>

	<table width="99%" border="0">
		<tr>
			<td>
				<fieldset>
					<legend> Adjuntar Documentos: </legend>
	               	<table id="gridDocumentosDigitalesPropios"></table>
	                <div id="pagerGridDocumentosDigitalesPropios"></div>
	                
                </fieldset>	
			</td>
		</tr>	
	</table>
</fieldset>
