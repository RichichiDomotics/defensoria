

/*
*Funcion que carga el combo Relaciones del hecho
*/
	function cargaRelacionesHecho(paramEntidad) {
		
		
		if(paramEntidad == entidadFedYuc){
			$('#trRelacionHecho').hide();
			return;
		}
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: contextoPagina + '/consultarRelacionesHecho.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catRelacionesHechos').each(function(){
					$('#cbxRelacionHecho').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}