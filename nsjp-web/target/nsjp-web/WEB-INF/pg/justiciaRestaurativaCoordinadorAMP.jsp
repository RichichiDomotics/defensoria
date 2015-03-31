<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@page import="mx.gob.segob.nsjp.comun.enums.menu.EstatusMenuJAR" %>
		
<script type="text/javascript">
	
	var idWindowNuevaDenuncia=1;
	var idWindowDetalleSolicitud=1;
	var pantallaSolicitada = 2;
	
	$(document).ready(function() {
		var id=<%=EstatusMenuJAR.PORASIGNAR.getValorId()%>;
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
								url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&bandera=CERO&opcionSeleccionada=porAsignar&menu='+id, 
								datatype: "xml", 
								colNames:['Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus','Responsable'],  
								colModel:[ 	{name:'Detalle',index:'detalle', width:70},
								           	{name:'Tipo',index:'tipo', width:120, align:'center', hidden:true}, 
											{name:'Fecha',index:'fecha', width:70}, 
											{name:'Nombre',index:'nombre', width:70}, 
											{name:'Resumen',index:'Resumen', width:110},
											{name:'Origen',index:'origen', width:110},
											{name:'Estatus',index:'estatus', width:110},
											{name:'Responsable',index:'responsable', width:110}
										],
								pager: jQuery('#pager1'),
								rowNum:20,
								rowList:[20,50,100],
								autowidth: true,
								sortname: 'detalle',
								viewrecords: true,
								ondblClickRow: function(id) {
									nuevaDenuncia2(id);
									},
								sortorder: "desc"
							}).navGrid('#pager1',{edit:false,add:false,del:false});
									
		cargaOcupacion();
		cargaCasos();
		
		//cargo los datos del grid desde la BD
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&menu='+id, 
				datatype: "xml" });
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
			 
		restablecerPantallas();			 
			 
	});

	
	function nuevaDenuncia2(id) {
		
		var idExpediente="0";
		var numeroExpediente="0";
		var numeroExpedienteId="0";
		var numeroGeneralCaso="0";
		//var numExpAlter;
		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+<%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()%>+'&idExpediente='+id,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
    			numeroGeneralCaso=$(xml).find('expedienteDTO').find('numeroGeneralCaso').text();
    			/* numExpAlter=$(xml).find('expedienteDTO').find('esNuevo').text();
    			if(numExpAlter == ""){
    				numExpAlter=null;
        		} */
    		}
    		
    	});
		
    	if(numeroExpedienteId!="0"){
        	var pantallaSolicitada=2;
    		idWindowNuevaDenuncia++;
    		var ingresoDenuncia = true;
    		
    		customVentana("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,
    					  "Expediente: ","/BusquedaExpediente.do", 
    					  "?abreenPenal=abrPenal&ingresoDenuncia=" + ingresoDenuncia + 
    					  "&idExpedienteop=" + idExpediente + "&pantallaSolicitada=" + pantallaSolicitada + 
    					  "&numeroExpediente=" + numeroExpediente + "&idNumeroExpedienteop=" + numeroExpedienteId +   
    					  "&idNumeroExpediente="+ numeroExpedienteId +"&numeroGeneralCaso="+ numeroGeneralCaso);
 				    	  /*"&idNumeroExpediente="+ numeroExpedienteId +"&numeroGeneralCaso="+ numeroGeneralCaso+
				    	  "&numExpAlter="+ numExpAlter); */
        }
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
				
	function cargaOcupacion(){
		$.ajax({
			type: 'POST',
		    url: '<%= request.getContextPath()%>/ConsultarCatalogoOcupacion.do',
		    data: '',
		    dataType: 'xml',
		    async: false,
		    success: function(xml){
		    	var option;
		    	$(xml).find('ocupacion').each(function(){
		    		$('#consultaVictima').append('<li value="' + $(this).find('gcNombre').text() +  '" title="'+ $(this).find('gcDescripcion').text() + '"  style="background:#99C"  >'+ $(this).find('gcDescripcion').text() + '</li>');
		    	});
		    }
    	});
    }

	var casoAbierto = Array();
		
	function agregaExpediente (idCaso) {	
		
		if (casoAbierto[idCaso ] != true) {
			$.ajax({
				type: 'POST',
			    url: '<%= request.getContextPath()%>/BusquedaCaso.do',
			    data: 'idCaso=' + idCaso,
			    dataType: 'xml',
			    async: true,
			    success: function(xml){
			    	$(xml).find('expediente').each(function(){
			    		var branches = $("<ul><li id='" + $(this).find('expedienteId').text() + "EXP' onclick='verExpediente(" + $(this).find('expedienteId').text() + ",\"" + $(this).find('numeroExpediente').text() + "\")'><span class='file'>" + $(this).find('numeroExpediente').text() + "</span><ul></ul></li></ul>").appendTo("#" + idCaso + "CASO");
			    		$("#" + idCaso + "CASO").treeview({
			    			add: branches
			    		});
				    });
			    }		    		
			});
		}
		casoAbierto[idCaso] = true;
	}

	function cargaCasos(){
    	var branches = "";
    	$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/BusquedaInicialCaso.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var branches = "";
				$(xml).find('caso').each(function(){
					var casoId = $(this).find('casoId').text();
					var numeroGralCaso = $(this).find('numeroGeneralCaso').text();
    				branches ="<ul><li class='closed' id='" + casoId + "CASO' onclick='agregaExpediente(" + casoId + ")'><span class='folder'>" + numeroGralCaso + "</span><ul></ul></li></ul>";
					var casos = $(branches).appendTo("#casos");
					try{
						$("#seccion1tree").treeview({
	    					add: casos
	    				});
	    			} catch(e) {
	    			}
    			});
    		}
    	});
    }

	function restablecerPantallas() { 
		ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
	}
	
	/*
	*Listener del click para la redireccion a la valoracion de hechos
	*/
	function realizarValoracionHechos(){
		location.href='<%= request.getContextPath()%>/RealizarValoracionHechos.do';
	}	
	
	function cambiarResponsableExpediente() {
		customVentana("cambiarResponsableExpediente", "Cambiar Responsable A Expediente", "/cambiarResponsableExpediente.do");
	}
	
	function porAsignar(){
		
		var id=<%=EstatusMenuJAR.PORASIGNAR.getValorId()%>;
		//alert("porAsignar"+id);
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&opcionSeleccionada=porAsignar&menu='+id, 
				datatype: "xml",
				page:1});
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}
    
	function asignados(){
		var id=<%=EstatusMenuJAR.ASIGNADOS.getValorId()%>;
		//alert("asignados"+id);
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&opcionSeleccionada=asignados&menu='+id, 
				datatype: "xml",
				page:1});
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}
	
	function propios(){
		var id=<%=EstatusMenuJAR.PROPIOS.getValorId()%>;
		//alert("propios"+id);
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&opcionSeleccionada=propios&menu='+id, 
				datatype: "xml" ,
				page:1});
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}
	
</script>	

<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
				<div id="divGridSolicitudes">
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="pager1"></div>
				</div>
			</div>
		</div>
	</div>
</div>

