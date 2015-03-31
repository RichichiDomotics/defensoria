<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Adjuntar documento a expediente</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js"></script>
        
        <script type="text/javascript">
       
         var CONTEXT_ROOT = '<%= request.getContextPath()%>';
         var idExpediente='<%= request.getParameter("idExpediente")%>';
         var idNumeroExpediente='<%= request.getParameter("idNumeroExpediente")%>';
         var numeroGeneralCaso='<%= request.getParameter("numeroGeneralCaso")%>';
         
         //Permite adjuntar el documento al expediente  y ademas a un eslabon
         var idEslabon='<%= request.getParameter("idEslabon")%>';
         
         //Permite restringir los tipos de archivos a subir en el servidor
         //Los tipo de archivos(.ext) de deben separar por comas, en minusculas y sin espacios por ejemplo: 
         //.doc,.jpg,.pdf
         var extensionesPermitidas='<%= request.getParameter("extensionesPermitidas")%>';

         var success='<%= request.getParameter("success")%>';
         
         $(document).ready(function() {
         	//Permite mandar un mensaje despues de registrar un amparo dado que el action redirige de nuevo a este mismo jsp
         	
         	if(extensionesPermitidas != null)
         		$('#tiposArchivos').html(extensionesPermitidas.replace(/,/g," ó "))
         	
         	if(success != "null"){
         		if(parseInt(success) == 1)	
         			alertSincronoNombre("El documento se adjuntó de forma correcta")
             	else{
             		alertSincronoNombre("Error al adjuntar el documento");             	    
             	}
             		    		
         	}         	         	
         	$("#btnGuardar").click(adjuntarDocumento);
         });
         
         function adjuntarDocumento(){        	 
        	if(datosObligatoriosCorrectos()){
          		if(validaExtensionDeArchivo($('#fileAdjuntarArchivo').val().trim(),extensionesPermitidas))
        			adjuntarArchivoActuaciones();
        	}        	
         }
         
         function datosObligatoriosCorrectos(){
      		 if($('#fileAdjuntarArchivo').val().trim() == ""){
         		 msjDeError = "Es necesario seleccionar un archivo\n";
         		 customAlert(msjDeError);
        		 return false;
        	 }else
        		 return true;
         }
         
 		function validaExtensionDeArchivo(archivo,iType) {
 			esExtensionPermitida = false; 
 			if (!archivo) return;
			ext = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();

 			arregloDeExtensionesPermitidas = extensionesPermitidas.split(",");
 			for (var i = 0; i < arregloDeExtensionesPermitidas.length; i++) { 
	 			if (arregloDeExtensionesPermitidas[i] == ext) { 
		 			esExtensionPermitida = true; 
		 			break; 
	 			} 
 			} 
 			
 			if (!esExtensionPermitida) {
	 			customAlert("Usted sólo puede subir archivos con extensiones (" + extensionesPermitidas.replace(/,/g," ó ")+ ")");
 			}
 			return esExtensionPermitida;
 	   }
        
		function adjuntarArchivoActuaciones(){
				$("#btnGuardar").attr('disabled','-1');
				document.adjuntarDocForm.tipo.value = 1;
				document.adjuntarDocForm.expedienteId.value = idExpediente;
				document.adjuntarDocForm.idNumeroExpediente.value = idNumeroExpediente;
				document.adjuntarDocForm.numeroGeneralCaso.value = numeroGeneralCaso;
				document.adjuntarDocForm.idEslabon.value = idEslabon;
		 		forma = document.adjuntarDocForm;
				forma.submit();
		}
		
		/*
		*Funcion que despliega una ventana modal para indicar que el nombre
		*del area ya existe
		*/
		function alertSincronoNombre(mensaje){
			$('#spanAlert').html(mensaje);

			$("#alertSincro").dialog("open");
			$("#alertSincro").dialog({
			autoOpen: true,
			modal: true,
			title: '',
			dialogClass: 'alert',
			position: [10,10],
			width: 400,
			height: 150,
			maxWidth: 300,
			maxHeigth:120,
				buttons: {"Aceptar":function() {
					cerrarCustomVentana();
					}
				},
				close:function() {
					cerrarCustomVentana();
				}
			});

			
		}

        
         		
        </script>
    </head>
<body>

<table width="436" border="0" summary="Tabla para registrar un amparo">
   <tr>
     <td align="left">&nbsp;</td>
   </tr>
   <tr>
     <td align="center">Seleccione el documento a adjuntar (<label id="tiposArchivos"></label>)</td>
   </tr>
   <tr>
     <td align="center">
       <label for="fileAdjuntarArchivo"></label>
       
       <form id="adjuntarDocForm" name="adjuntarDocForm" action="<%= request.getContextPath() %>/adjuntarDocumentoAExpediente.do" method="post" enctype="multipart/form-data" >
         * Archivo adjunto:
         <input id="fileAdjuntarArchivo" type="file" name="archivoAdjunto" value="" >
         <input type="hidden" name="expedienteId" />
         <input type="hidden" name="idNumeroExpediente" />
         <input type="hidden" name="tipo" />
         <input type="hidden" name="folio" />
         <input type="hidden" name="descripcion" />
         <input type="hidden" name="idsSeleccionados" />
         <input type="hidden" name="numeroGeneralCaso" />		
         <input type="hidden" name="idEslabon" />		
         </form>     
     </td>
   </tr>
   <tr>
     <td align="center">
	   
	</td>
   </tr>
   <tr>
     <td align="center">       
     	<br/>
     	<input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" tabindex="4" class="btn_Generico">
     </td>
   </tr>
</table>

<div id="alertSincro" style="display: none">
	<table>
		<tr>
			<td>
			<label id=spanAlert></label>
			</td>
		</tr>
	</table>
</div>

</body>
</html>