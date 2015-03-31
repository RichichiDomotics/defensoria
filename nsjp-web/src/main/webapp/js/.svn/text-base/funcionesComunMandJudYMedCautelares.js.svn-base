	var gridMedCau=0; 
	
	// Variable para controlar el id de las ventanas de medidas cautelares
	var idWindowVisorMedidasCautelares=1;
	
	// Variable para controlar el id de las ventanas de mandamientos judiciales
	var idWindowVisorMandamientosJud=1;	
	

	function consultaGeneralMedidaCautelar(selectorMedida, estatusDeFiltrado, numeroCausa){
		var fechaInicio="";
		var fechaFin="";
		var numeroExpediente = "";

		// Este valor se utiliza desde base de datos en la invocación del elementoMenu, en la columna de cComando
		// cuando selectorMedida==1, es la consulta general de medidas cautelares.
		// Se manipula que cuando selectorMedida=2 -> Búsqueda por fechas, selectorMedida=3 -> Búsqueda por número de expediente,
		// de necesitar más filtros, es necesario modificar las condicionales sobre selectorMedida.
		// consulta general -> fechaInicio = "", fechaFin = "", numeroExpediente = ""
		
		if(selectorMedida == 2){
			fechaInicio= $('#fechaInicio').val();
			fechaFin=    $('#fechaFin').val();
		}
		else if(selectorMedida == 3){
			numeroExpediente = $("#numeroExpediente").val();
		}
		else if(selectorMedida == 4){
			numeroExpediente = numeroCausa;
		}
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		if(gridMedCau==0){
    		jQuery("#gridMedidasCautelares").jqGrid({ 
    			url: contextoPagina + '/consultaMedidasCautelaresGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatusMedida='+estatusDeFiltrado+'', 	   		
    			datatype: "xml", 
    			colNames:['Nombre','Medida Cautelar','Fecha de creación', 'Fecha de inicio', 'Fecha de fin','Periodicidad','Descripci&oacute;n', 'Estado','No. Causa'], 
				colModel:[ 	{name:'nombre',index:'nombre', width:200, sortable:false}, 
							{name:'medidaCautelar',index:'4', width:200, sortable:true},
							{name:'fechaCreacion',index:'6', width:110, sortable:true,align:'center'},
							{name:'fechaInicio',index:'1', width:100, sortable:true,align:'center'}, 
							{name:'fechaFin',index:'2', width:100, sortable:true,align:'center'},
							{name:'periodicidad',index:'3', width:150 , sortable:true},
							{name:'descripcion',index:'descripcion', width:150 , sortable:false, hidden:true},
							{name:'estadoMedida',index:'5', width:150 , sortable:true,align:'center'},
							{name:'numeroCausa',index:'numeroCausa', width:150 , sortable:false, hidden:true}
						],		
				pager: jQuery('#pagerGridMedidasCautelares'),
				rowNum:30,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				height:210,
				sortname: '6',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					//Solo se habilita la opcion de mostrar el visor de medidas cautelares si y solo si se trata de una consulta desde la bandeja principal del usuario
					if(selectorMedida != 4){
						numeroCausa= jQuery("#gridMedidasCautelares").jqGrid('getRowData',rowid).numeroCausa;
						mostrarVentanaMedidasCautelares(rowid,numeroCausa);
					}
				}
			}).navGrid('#pagerGridMedidasCautelares',{edit:false,add:false,del:false});
    		
    		gridMedCau=1;
		}
		else{			
			jQuery("#gridMedidasCautelares").jqGrid('setGridParam', {url: contextoPagina + '/consultaMedidasCautelaresGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatusMedida='+estatusDeFiltrado+'',
					datatype: "xml" });
			$("#gridMedidasCautelares").trigger("reloadGrid");
		}
		
		if(typeof numeroCausa == "undefined")
			ocultaMuestraGrids('gridMedidasCautelares');	
	}
	
	//Permite controlar la carga/recarga del grid
	var gridMandJud = 0;
	function consultaGeneralMandamientoJudicial(selectorMandamiento, estatusDeFiltrado, numeroCausa){
		var fechaInicio="";
		var fechaFin="";
		var numeroExpediente = "";

		// Este valor se utiliza desde base de datos en la invocación del elementoMenu, en la columna de cComando
		// cuando selectorMandamiento==1, es la consulta general de mandamientos judiciales.
		// Se manipula que cuando selectorMandamiento=2 -> Búsqueda por fechas, selectorMandamiento=3 -> Búsqueda por número de expediente,
		// de necesitar más filtros, es necesario modificar las condicionales sobre selectorMandamiento.
		// consulta general -> fechaInicio = "", fechaFin = "", numeroExpediente = ""
		
		if(selectorMandamiento == 2){
			fechaInicio= $('#fechaInicio').val();
			fechaFin=    $('#fechaFin').val();
		}
		else if(selectorMandamiento == 3){
			numeroExpediente = $("#numeroExpediente").val();
		}
		else if(selectorMandamiento == 4){
			numeroExpediente = numeroCausa;
		}
		
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		
		if(gridMandJud==0){
    		jQuery("#gridMandamientosJudiciales").jqGrid({ 
    			url: contextoPagina + '/consultaMandamientosJudicialesGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatus='+estatusDeFiltrado+'', 	   		
    			datatype: "xml", 
    			colNames:['Número de Caso','Número de Causa','Nombre','Mandamiento Judicial','Fecha de creación','Tipo sentencia' ,'Fecha de inicio', 'Fecha de fin','Descripci&oacute;n','Estado','CalidadImputado'], 
				colModel:[ 	
				          	{name:'numeroCaso',index:'numeroCaso', width:230, sortable:false},
				            {name:'numeroCausa',index:'numeroCausa', width:240, sortable:false},
				           	{name:'nombre',index:'nombre', width:200, sortable:false}, 
							{name:'mandamientoJudicial',index:'3', width:200, sortable:true},
							{name:'fechaCreacion',index:'6', width:110, sortable:true,align:'center'},
							{name:'tipoSentencia',index:'4', width:200, sortable:true,hidden:true},
							{name:'fechaInicio',index:'1', width:110, sortable:true,align:'center',hidden:true}, 
							{name:'fechaFin',index:'2', width:100, sortable:true,align:'center',hidden:true},
							{name:'descripcion',index:'descripcion', width:150 , sortable:false,hidden:true},
							{name:'estadoMedida',index:'5', width:150 , sortable:true,align:'center'},
							{name:'calidadPR',index:'calidadPR', width:150 , sortable:false, hidden:true}
						],				
				pager: jQuery('#pagerGridMandamientosJudiciales'),
				rowNum:30,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				height:210,
				sortname: '6',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					//Solo se habilita la opcion de mostrar el visor de medidas cautelares si y solo si se trata de una consulta desde la bandeja principal del usuario
					if(selectorMandamiento != 4){
						numeroCausa= jQuery("#gridMandamientosJudiciales").jqGrid('getRowData',rowid).numeroCausa;
						mostrarVentanaMandamientosJudiciales(rowid,numeroCausa);
					}
				}
			}).navGrid('#pagerGridMandamientosJudiciales',{edit:false,add:false,del:false});
    		
    		gridMandJud=1;
		}
		else{
			
			jQuery("#gridMandamientosJudiciales").jqGrid('setGridParam', {url: contextoPagina + '/consultaMandamientosJudicialesGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatus='+estatusDeFiltrado+'',
					datatype: "xml" });
			$("#gridMandamientosJudiciales").trigger("reloadGrid");
		}
		
		if(typeof numeroCausa == "undefined")
			ocultaMuestraGrids('gridMandamientosJudiciales');
		
	}

	function popUpTipoBusquedaModalXExpediente(selector){
		// 1 -> Mandamiento Judicial
		// 2 -> Medidas Cautelares
		var titulo="";
		if(selector == 1){
			titulo = "Administrar Mandamientos Judiciales por número de expediente"
		}
		else if(selector==2){
			titulo = "Administrar Medidas Cautelares por número de expediente"
		}

		$("#numeroExpediente").val("");
		$("#divBusquedaExpediente").dialog("open");
	  	$("#divBusquedaExpediente").dialog({ autoOpen: true, 
			modal: true, 
		  	title: titulo, 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 300,
		  	maxWidth: 350,
		  	buttons:{"Realizar búsqueda":function() {
		  			if(selector == 1){
		  				consultaGeneralMandamientoJudicial(3);
		  			}
		  			else if(selector == 2){
		  				consultaGeneralMedidaCautelar(3);
		  			}
		  			$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}

	function modalFechaDeMandamientoYMedidas(opcion, estatusDeFiltrado){

		// opcion = 1  -> MandamientosJudiciales
		// opcion = 2  -> MedidasCautelares
		
		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		
		
		var dates =	$("#fechaInicio, #fechaFin").datepicker(
			{
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				numberOfMonths: 2,
				onSelect: function( selectedDate ) {
					var option = this.id == "fechaInicio" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );
				},
				showOn: "button",
				buttonImage:  contextoPagina + "/resources/images/date.png",
				buttonImageOnly: true			
			}
		);
		
		//abre la ventana de detalle de la persona
		$("#busquedaFecha").dialog("open");
		$("#busquedaFecha").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Buscar por Fecha', 
	  		dialogClass: 'alert',
	  		width: 380,
	  		height: 260,
	  		maxWidth: 1000,
	  		buttons:{"Aceptar":function() {
	  				
	  					validaCamposFecha($("#fechaInicio").val(), $("#fechaFin").val());
	  					
	  					if(validaFecha==true){
	  						if(opcion==1){
	  							selectorMandamiento=2;
	  							consultaGeneralMandamientoJudicial(selectorMandamiento,estatusDeFiltrado);
	  						}
	  						else if(opcion==2){
	  							selectorMedida=2;
	  							consultaGeneralMedidaCautelar(selectorMedida,estatusDeFiltrado);
	  						}
	  						$(this).dialog("close");
				  		}
	  					else{
	  						alert("Favor de verificar el rango seleccionado");
	  					}
	  					
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});				
	}
	
	/**
	* Función que abre la ventana modal para cambiar el estado del mandamiento judicial
	* 
	**/
	idMandamientoGlobal = "";
	function abreModalPdfMandamientoJudicial(idMandamiento,estatusActual){
		//colocar el estado actual
		idMandamientoGlobal = idMandamiento;
		$("#estadoActualMandamientoJud").val(estatusActual);
		$("#divPdfMandamientoJudicial").dialog("open");
	  	$("#divPdfMandamientoJudicial").dialog({ autoOpen: true, 
			modal: true, 
		  	title: '¿Cambiar el estado del mandamiento judicial?', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 260,
		  	maxWidth: 350,
		  	buttons:{"Aceptar":function() {
				var estado = $('#estadoMandamientoJudPJENC option:selected').val();
				actualizarEstadoMandamiento(idMandamientoGlobal,estado);		  		
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});	  	
	}	
	
	/**
	* Manda a actualizar el estado de un mandamiento judicial y actualiza el grid de mandamientos
	*
	*/
	function actualizarEstadoMandamiento(idMandamiento,estadoNuevo){
		
		$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/actualizarMandamientoJudicial.do?estatusMandamiento='+estadoNuevo+'&mandamientoId='+idMandamiento,
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){    			
    			cargaGridMandamientosJudiciales(numeroCausaGlobalGrid,estadoNuevo);
    		}
    		
    	});
	}
	
	var mostrarMedidaCautelarPJENC=1;
	/*
	*Funcion que abre la ventana de medidas cautelares
	* 
	*/
	function mostrarVentanaMedidasCautelares(rowid,numeroCausa){
		var flujoMedCautelar = "" ;		
		mostrarMedidaCautelarPJENC++;
		
		var idVentana = "iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC;
		
		$.newWindow({id:"iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC, statusBar: true, posx:70,posy:20,width:1100,height:400,title:"Consultar Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC,'<iframe src="' + contextoPagina + '/ingresarMedidaCautelarPJENC.do?numeroExpediente='+numeroCausa+'&rowid='+rowid+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA&idVentana='+idVentana+'" width="1100" height="400" />');
	}
	
	/*
	*Funcion que abre la ventana de medidas cautelares
	* 
	*/
	var mostrarMandamientoJudicialPJENC =1;
	function mostrarVentanaMandamientosJudiciales(rowid,numeroCausa){
		var flujoMedCautelar = "" ;
		mostrarMandamientoJudicialPJENC++;
		
		var idVentana = "iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC;

		$.newWindow({id:"iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC, statusBar: true, posx:70,posy:20,width:880,height:400,title:"Consultar Mandamientos Judiciales", type:"iframe"});
    	$.updateWindowContent("iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC,'<iframe src="' + contextoPagina + '/ingresarMandamientoJudicial.do?numeroExpediente='+numeroCausa+'&rowid='+rowid +'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA&idVentana='+idVentana+'" width="880" height="400" />');
	}
	
	/*
	*Funcion para cerrar la ventana de mandamiento judicial
	*/
	function cerrarVentanaMandamientoJudicialJS(idVentana){
		$.closeWindow(idVentana);
	}

