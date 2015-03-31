<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar narrativa</title>
	   
   	   <!--Scripts necesarios para la ejecucion del editor-->
	   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js"></script>
	   	   
<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	
	Long rolId = 0L;
	Boolean esCoordinadorAmpGeneral = false;

	if (usuario != null && usuario.getRolACtivo() != null
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null) {
		rolId = usuario.getRolACtivo().getRol().getRolId();
	}

	if (rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())) {
		esCoordinadorAmpGeneral = true;
	}

%>   	   
	   <script type="text/javascript">
			
			var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>; 

			jQuery().ready(function(){						
						if(esCoordinadorAmpGeneral===true){
		    				$(":enabled").attr('disabled','disabled');
		    				$('input[type="submit"]').hide();
		    				$('input[type="button"]').hide();
		    				$("#editor1").attr('disabled','disabled');
						}
			});

			function guarda() {
				var recuperaTextoNarrativa=$('.jquery_ckeditor').val();
				window.parent.$("#textNarrativa").val(recuperaTextoNarrativa);
			}
			
			function recuperaNarrativa()
			{
				return $('.jquery_ckeditor').val();
			}
			
			function recuperaNarrativaBD()
			{
				var lsNarrativa="";
				lsNarrativa="gsNarrativa="+$('.jquery_ckeditor').val();
	  			return lsNarrativa;
			}
			
			//Funcion que refleja los datos de textoNarrativa a la ventana padre
		 	function espejoDatos(){
		     	
		 		var valorCampoTextoNarrativa;
		 		
		 		valorCampoTextoNarrativa=$("#txtDescripcion").val();
		 		        		
		 		//llama a la funcion que escribe los datos en la ventana padre
		 		window.parent.imprimeDatosPadreNarrativa(valorCampoTextoNarrativa);
		 	}    
				
			/*
			*Funcion que habilita los botones
			*/
			function onSelectChangeRdbs()
			{
			  if($("#rdbGenerarFicha").attr('checked'))
			  {
			  	$("#btnIdentificarElem").attr('disabled','disabled');
			  	$("#btnGenerarFicha").attr('disabled','');
			  }
			  else if($("#rdbIdentificarElem").attr('checked'))
			  {
			     $("#btnIdentificarElem").attr('disabled','');
			  	 $("#btnGenerarFicha").attr('disabled','disabled');
			  }
			}
					
			/*
			* Funcion que manda llamar el action que atiende el guardado de una Narrativa
			*/
			function guardarNarrativa() {
				$.ajax({
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/GuardarNarrativa.do',
			    	  data: 'tipoNarrativa='+$("#cbxTipoNarrativa option:selected").val()+'&texto='+$("#txtDescripcion").val()+'&numeroExpediente=000001',
			    	  dataType: 'xml',
			    	  success: function(xml){
				    	  var option;
			    		  $(xml).find('tipos').each(function(){
							$('#cbxTipoNarrativa').append('<option value="' + $(this).find('cNombre').text() + '">' + $(this).find('cDescripcion').text() + '</option>');
						   });
			    	  }
			    	});
			}
				
		
			
		    /**
		    *Funcion que obtiene el texto seleccionado del textarea de descripcion
		    */    
		    function getTextSub() 
		    { 
		       var textoSeleccionado="";
		       //IE support
				if (document.selection)
				{
				   $("#txtDescripcion").focus();
				   sel = document.selection.createRange();
				   textoSeleccionado=sel.text;
			    }
		        //MOZILLA/NETSCAPE support
		        else if ($("#txtDescripcion")[0].selectionStart || $("#txtDescripcion")[0].selectionStart == "0")
		        {
		 		    var startPos = $("#txtDescripcion")[0].selectionStart;
		 			var endPos = $("#txtDescripcion")[0].selectionEnd;
					textoSeleccionado=$("#txtDescripcion").val().substr(startPos, endPos - startPos);
			    }
			    if(textoSeleccionado!="")
			    {
				   customAlert(textoSeleccionado);
				}
		    }  

		    function closeIt(){
		        window.close();
		        }
		</script>
		
		<!-- Estilo para el cuerpo del documento-->
		<style>
		   body{
		       font-size: 10px;
		   }
		</style>
</head>
<body>
	<br/><br/>
      <textarea class="jquery_ckeditor" cols="150" id="editor1" name="editor1" rows="10" ></textarea>
   <br/>
	<input type="button" id="guardarNarraTiva" name="guardar" value="Guardar" class="btn_Generico"/>
</body>
</html>

<script type="text/javascript">
var config = {					
		toolbar:
			[
	 			['Source','-','Cut','Copy','Paste','-','Undo','Redo','-','Find','Replace','-','RemoveFormat','SelectAll'],
				['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
				['NumberedList','BulletedList','-','Outdent','Indent'],
				['Table','HorizontalRule'],
				'/',
				['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
				['Styles','Format','Font','FontSize','TextColor','BGColor','-','Image','PageBreak','Maximize', 'ShowBlocks']
/*
[ 'Source', '-', 'Save', 'NewPage', 'Preview', '-', 'Templates' ],
[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print', 'SpellChecker','Scayt' ],
[ 'Undo', 'Redo', '-', 'Find', 	'Replace', '-',	'SelectAll', 'RemoveFormat' ],
[ 'Form', 'Checkbox', 'Radio', 	'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',	'HiddenField' ],
'/',
[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript' ],
[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent',	'Blockquote', 'CreateDiv' ],
[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
[ 'BidiLtr', 'BidiRtl' ],
[ 'Link', 'Unlink', 'Anchor' ],
[ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ],
'/',
[ 'Styles', 'Format', 'Font','FontSize' ],
[ 'TextColor', 'BGColor' ],
[ 'Maximize', 'ShowBlocks', '-', 'About' ] */
			], 
			//Oficio
			//height:'355.6mm',
			//width:'215.9mm'
	};			
	if(window.parent.banderaNarrativa==1)
		{
			$("#editor1").val('<%= request.getSession().getAttribute("narrativa")%>');
		}
	
		//cargaTiposNarrativa();
    	//enableControls('0');
    	config.width = '742px';
        config.height = '1050px';
		$('.jquery_ckeditor').ckeditor(config);
		
		$('#guardarNarraTiva').click(guarda);	
		// $("#cbxTipoNarrativa").change(onSelectChangeTipo);
	    $("#rdbIdentificarElem").change(onSelectChangeRdbs);
	    $("#rdbGenerarFicha").change(onSelectChangeRdbs);
	    $("#btnIdentificarElem").click(getTextSub);
	    $("#btnIdentificarElem").attr('disabled', 'disabled');
	    $("#btnGenerarFicha").attr('disabled', 'disabled');
	    $("#btnGuardarNarrativa").click(guardarNarrativa);
</script>
