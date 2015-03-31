   <script type="text/javascript">
	
	function consultar(){
	if(primeraConsultaJueces=="true"){
		
		//Se llena el gird de jueces
		jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid({
			url:'<%= request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudienciaSiguiente+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimadaAudiencia+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia, 
			datatype: "xml", 
			colNames:['Nombre','Carga de trabajo','Paridad',], 
			colModel:[ 	{name:'nombre',index:'nombre', width:100, align:"center"},
			           	{name:'carga',index:'carga', width:150, align:"center"},  
						{name:'paridad',index:'paridad', width:50, align:"center"},
						
					],
			pager: jQuery('#pagerGridJueces'),
			rowNum:10,
			loadComplete: function(){
				if(automatico== true)
					//Deshabilita el multi select
					jQuery("#gridSolicitudDeAudienciaJuecesPJENA").setGridParam({multiselect:false}).hideCol('cb');
				}, 				
			rowList:[25,50,100],
			autowidth: false,
			sortname: 'carga',
			viewrecords: true,
			sortorder: "desc",
			multiselect: true
		}).navGrid('#gridSolicitudDeAudienciaJuecesPJENA',{edit:false,add:false,del:false});

		primeraConsultaJueces="false";
		deshabilitarHabilitarComponentes("juez");
		verificarTipoAudiencia(automatico);
	}
	else{
		jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudienciaSiguiente+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimada+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia,datatype: "xml" });
		$("#gridSolicitudDeAudienciaJuecesPJENA").trigger("reloadGrid");
		deshabilitarHabilitarComponentes("juez");
		verificarTipoAudiencia(automatico);
	}
	}
	
   </script>   	
	<table border="0" summary="Consulta información de amparos">
	  <tr>
	    <td>
			Asigncación de Permisos para la Audiencia #
	    </td>
	  </tr>
	  <tr>
		<td>
			<table id="gridAsignacionPermisosAudiencia"></table>
		</td>
     </tr>
	</table>