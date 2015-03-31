	//Permite saber si el funcionalidad en comun es para registrar:
    // - Mandamiento Judicial
    // - Medida cautelar
	var esMandamientoJudicial = 0;
	

	/*
	*Funcion que abre la modal para agregar una medida cautelar a un imputado
	*/
	function agregarMedidaCautelar(){
		
		cargaGridImputados();
		$("#divAgregarMedidaCautelar").dialog("open");
		$("#divAgregarMedidaCautelar").dialog({ autoOpen: true, modal: true, 
			title: 'Agregar medida caultelar',
			dialogClass: 'alert', position: [350,50],
			width: 800, height:480, maxWidth: 800, maxHeight:550,
			buttons:{
				"Agregar":function() {
					validaSeleccion();
				},
				"Cancelar":function() {
					
					customConfirm("¿Realmente desea salir?", "", cancelarAgregarInvolucrado);			
				}
			}
		});
	}
	
	function cancelarAgregarInvolucrado(){
  		$("#divAgregarMedidaCautelar").dialog("close");
	}

	
	
	var banderaImputados = true;
	/**
	*Funcion que carga el grid con los imputados del expediente
	*/
	function cargaGridImputados(){
		if(banderaImputados == true){
			jQuery("#gridAgregarMedidaCautelar").jqGrid({ 
				url: contextoPagina +'/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',
				data:'',
				datatype: "xml",
				colNames:['Nombre del Imputado'], 
				colModel:[ 					
				           	{name:'nombreImputado',index:'nombreImputado',width:350, align:'center'}, 
						],
				pager: jQuery('#pagerGridAgregarMedidaCautelar'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[10,20,30],
				sortname: 'nombreImputado',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados"
			}).navGrid('#gridAgregarMedidaCautelar',{edit:false,add:false,del:false});
			banderaImputados = false;
		}
		else{
			jQuery("#gridAgregarMedidaCautelar").jqGrid('setGridParam', {url: contextoPagina +'/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridAgregarMedidaCautelar").trigger("reloadGrid");
		}
	}
	
	
	function validaSeleccion(){
		
		var rowid = jQuery("#gridAgregarMedidaCautelar").jqGrid('getGridParam','selrow');
		
		if (rowid) {
			$("#divAgregarMedidaCautelar").dialog("close");
			nuevaVentanaMedidasCautelares(rowid);
		}else{
			customAlert("Por favor seleccione un renglón");
		} 
	}
	
	//Variable para controlar el id de la ventana
	var ventanaMedidaCautelarPJENC=1;
	/*
	*Funcion para ingresar una nueva medida cautelar
	*/
	function nuevaVentanaMedidasCautelares(idInvolucrado){
		
		ventanaMedidaCautelarPJENC++;
		idVentana = "iframewindowMedidasCautelares"+ventanaMedidaCautelarPJENC;
		var rowid= idInvolucrado+',';

		$.newWindow({id:"iframewindowMedidasCautelares"+ventanaMedidaCautelarPJENC, statusBar: true, posx:70,posy:20,width:880,height:360,title:"Ingresar/Modificar Medidas Cautelares", type:"iframe"});
	    $.updateWindowContent("iframewindowMedidasCautelares"+ventanaMedidaCautelarPJENC,'<iframe src="' + contextoPagina + '/ingresarMedidaCautelarPJENC.do?numeroExpediente='+numeroCausa+'&rowid='+rowid+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=INGRESAR&idVentana='+idVentana+'&numeroExpedienteId='+numeroExpedienteId+'" width="880" height="360" />');
				
		
	}
	
	