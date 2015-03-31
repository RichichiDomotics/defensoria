<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
<script type="text/javascript">
	
		//Llena el grid con los resultados de la busqueda, pasa como parametros el No de expediente
		  function llenaGridExpediente(){
			  var noExpediente = $("#noExpediente").val();				//Numero de Expediente
			  var opcion=$("#cmbTipoBusqueda option:selected").val();// para verificar la opcion de agentemp
						if (validaExpediente==true){
							if (reloadGridExpediente) {
								  jQuery("#tablaBuscarPorNumeroDeExpediente").jqGrid('setGridParam', {postData:{noExpediente: noExpediente,opcion:opcion}});
								  $("#tablaBuscarPorNumeroDeExpediente").trigger("reloadGrid"); 
							  } else {
								  reloadGridExpediente = true;
								  jQuery("#tablaBuscarPorNumeroDeExpediente").jqGrid(
											{ url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpediente.do', 						
												data: {
													noExpediente:noExpediente,
													opcion:opcion
												},
												datatype: "xml", 
												mtype: 'POST',						
												postData: {
												noExpediente: function()     { return noExpediente; },
												opcion		: function()     { return opcion; }													
												},
												colNames:['No. Caso','involucradoId','idNumeroExpediente','No. Expediente','Responsable','UIE'], 
												colModel:[ {name:'caso', index:'caso',width:50, sortable:false},
															{name:'involucradoId', index:'involucradoId',width:50, sortable:false, hidden:true},
															{name:'idNumeroExpediente', index:'idNumeroExpediente',width:50, sortable:false, hidden:true},															
															{name:'expediente', index:'expediente',width:50, sortable:false},
															{name:'Responsable', index:'Responsable',width:50, sortable:false},
															{name:'UIE', index:'UIE',width:50, sortable:false}
														], 
													autowidth: true, 
													pager: jQuery('#pagerNoExpediente'), 
													sortname: 'id', 
													rownumbers: true,
													gridview: true, 
													viewrecords: true, 
													sortorder: "desc", 
													height: "60%",
													onSelectRow: function(id){
														var ret = jQuery("#tablaBuscarPorNumeroDeExpediente").jqGrid('getRowData',id);
														var numExp = ret.idNumeroExpediente;
														var casoId=ret.caso;
														var involucradoId=ret.involucradoId;
														if(id==='-1'){
															customAlert("El expediente no ha sido asignado a la unidad.","");
														}else if(id==='-2'){
															customAlert("Usted no es el dueño del expediente,<br> y no cuenta con permisos para consultarlo.","");
														}else{
//															detEvi(id, numExp);
															detEviInvo(id, numExp, involucradoId, casoId);
															
														}
														
														},
													ondblClickRow: function(id) {
														if(tipoOrigen == 1){
															var ret = jQuery("#tablaBuscarPorNumeroDeExpediente").jqGrid('getRowData',id);
															var numExp = ret.expediente;
															var idExp = id;
															parent.cerrarEtapa(idExp,numExp);															
														}else if(tipoOrigen > 2){
															if(id==='-1'){
																customAlert("El expediente no ha sido asignado a la unidad.","");
															}else if(id==='-2'){
																customAlert("Usted no es el dueño del expediente,<br> y no cuenta con permisos para consultarlo.","");
															}else{
																detExp(id);	
															}
															
														}	
														
													},
													caption:"Resultado de la B&uacute;squeda" 
											});
							 			 }
							}			  
					   
				  }
	  

function detExp(id){
$.newWindow({id:"iframewindowDetalleExp", statusBar: true, posx:200,posy:50,width:806,height:500,title:"Detalle de Caso", type:"iframe"});
$.updateWindowContent("iframewindowDetalleExp",'<iframe src="<%= request.getContextPath() %>/generarDetalleDeBusquedaDeExpediente.do?idRow='+ id + '"  width="806" height="500" />');		

} 
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposExpediente(){

			if ($('#noExpediente').val()==''){
				if($('#noExpediente').val()==''){
					customAlert("Favor de ingresar un Número de Expediente");
					validaExpediente=false;
				}
					}else {
				validaExpediente=true;
					}						
												
		}		

		// Estas funciones hay que echarles ojo
		
</script>


<table width="650" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="306" >
    </td>
  </tr>
  <tr>
    <td height="10" colspan="2">&nbsp;
      
    </td>
    </tr>
  <tr>
    <td height="12" colspan="2" align="center">&nbsp;</td>
  </tr>
  <tr>
    <td height="13" colspan="2" align="center">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" colspan="2" align="center"><span id="etiquetaNumExp">Número Expediente</span>:
		<input type="text" name="noExpediente" id="noExpediente" size="50" maxlength="50" />&nbsp;&nbsp;</td>
    </tr>
  
  <tr id="espacios" >
    <td height="25" colspan="2" align="center">* Sólo se permiten n&uacute;meros, letras y los caracteres especiales: "-","/" y "Y"</td>
    </tr>

  <tr >
    <td height="25" colspan="2" align="center">&nbsp;</td>
    </tr>

	<tr >
    <td height="25" colspan="2" align="center"><input type="button" name="buscarExpediente" value="Buscar" id="buscarExpediente" class="btn_Generico"/>
    </tr>
    <tr>
     <td align="center">
       <table align="center" id="tablaBuscarPorNumeroDeExpediente"></table><div id="pagerNoExpediente"></div>
	 </td>

</table>

