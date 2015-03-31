<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
           // $.jgrid.useJSON = true;
        
		//cargamos el combo con los delitos del expediente
		function cargaGridConsultaDelitosTodos()
		{
			var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';
			//cargamos el grid con los delitos del PR
	    	jQuery("#gridCatDelitosRDPTodos").jqGrid({ 
				url:'<%= request.getContextPath()%>/ConsultarRelacionDelitosPorTodos.do?idExpediente='+idExpedienteop+'', 
				datatype: "xml",
				colNames:[probableResponsableProp,'Delito','Forma de Participación','Víctima'], 
				colModel:[ 	{name:'Probable',index:'probable', width:250},
				           	{name:'Delito',index:'delito', width:250}, 
							{name:'FormaParticipacion',index:'formaParticipacion',width:250},
							{name:'Victima',index:'victima',width:250},
						],
				pager: jQuery('#pagerGridCatDelitosRDPTodos'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autowidth: true,
				caption:"LISTA DE RELACIONES",
				sortname: 'Clave',
				viewrecords: true,
				id: 'divgridIzq',
				onSelectRow: function(id){
					idRelacionDelito=id;
				},
				sortorder: "desc"
			}).navGrid('#pagerGridCatDelitosRDPTodos',{edit:false,add:false,del:false});
	    	$("#gridCatDelitosRDPTodos").trigger("reloadGrid");	
		}
        </script>
</head>
<body>
	<table border="0"  width="900px" id="tblRelacionarDelitoPorTodos">
		<tr>
			<td>
				<table id="gridCatDelitosRDPTodos" width="400px"></table>
				<div id="pagerGridCatDelitosRDPTodos"></div>
			</td>
		</tr>
	</table>
	

