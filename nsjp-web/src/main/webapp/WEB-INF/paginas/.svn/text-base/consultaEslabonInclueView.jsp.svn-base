<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css"/>
        
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js"></script>
        

	<script type="text/javascript">
	var idEvidencia = parent.idParaConsultaEvidencia;	
	var tipoEslabonDetalleSolicitud = parent.tipoEslabonDetalleSolicitud;
	var banderaSolicitudUltimaConsulta = parent.banderaSolicitudUltimaConsulta;
	
	$(document).ready(function() {
		
		if(banderaSolicitudUltimaConsulta == true){
			consultaEslabonPorTipo(idEvidencia , tipoEslabonDetalleSolicitud);
		}
		
		//En caso de existir la fecha inicio y fin de prestamo se copian al registro de eslabon
		var fechaInicioPrestamo = $('#fechaInicioPrestamo').html(); 
		var fechaFinPrestamo = $('#fechaFinPrestamo').html();
		
		if(fechaInicioPrestamo != "" && fechaFinPrestamo != ""){
			$('#txtFechaPrestamoEslbn').val(fechaInicioPrestamo)
			$('#txtFechaPrestamoRecEslbn').val(fechaFinPrestamo)

			$('#txtFechaEntregaEslbn').val(fechaInicioPrestamo)
			$('#txtFechaRecepcionEslbn').val(fechaFinPrestamo)
		}
		
		
		//En caso de existir la hora inicio y fin de prestamo se copian al registro de eslabon
		var horaInicioPrestamo = $('#horaInicioPrestamo').html(); 
		var horaFinPrestamo = $('#horaFinPrestamo').html();
		
		if(horaInicioPrestamo != "" && horaFinPrestamo != ""){
			$('#txtHoraEntregaEslbn').val(horaInicioPrestamo)
			$('#txtHoraRecepcionEslbn').val(horaFinPrestamo)

			$('#txtHoraPrestamoEslbn').val(horaInicioPrestamo)
			$('#txtHoraPrestamoRepEslbn').val(horaFinPrestamo)
		}

		
	});
	
    function consultaEslabonPorTipo(idEvidencia, tipoEslabon){
    	var parametros="";
		parametros+="&idEvidencia="+idEvidencia;
		parametros+="&tipoEslabon="+tipoEslabon;
		
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaUltimoEslabonXEvidenciaYTipo.do',
			data: parametros,
			dataType: 'xml',
			success: function(xml){
					if((parseInt($(xml).find('code').text())==0) && ($(xml).find('error').text()!=1))
					{	
						pintaDatosDeEslabon(xml);								
					}
					else{
						// ocultaDetalleSolicitud, es una función del jsp menuGestionarAlmacen
						// No tiene solicitudes pendientes -> no se muestra detalle de la solicitud
						ocultaDetalleSolicitud();					
					}
			}
		});
    }
    
    function pintaDatosDeEslabon(xml){
    	
    	//Nombre de la persona que entrega
        if($(xml).find('eslabonDTO').find('quienEntrega') != null){
    		   $('#nombreDeQuienEntregaConsulta').html($(xml).find('eslabonDTO').find('quienEntrega').text());
        }
    	
      	//Fecha de entrega
        if($(xml).find('eslabonDTO').find('strFechaEntrega') != null){
    		   $('#fechaEntregaConsulta').html($(xml).find('eslabonDTO').find('strFechaEntrega').text());
        }
    	
        //Nombre de la persona que recibe
        if($(xml).find('eslabonDTO').find('quienRecibe') != null){
 		   $('#nombreDeQuienRecibeConsulta').html($(xml).find('eslabonDTO').find('quienRecibe').text());
    	}
        
        //Fecha de recepcion
        if($(xml).find('eslabonDTO').find('strFechaRecibe') != null){
 		   $('#fechaRecepcionConsulta').html($(xml).find('eslabonDTO').find('strFechaRecibe').text());
    	}
        
      	//Hora de entrega
        if($(xml).find('eslabonDTO').find('strHoraEntrega') != null){
    		   $('#horaEntregaConsulta').html($(xml).find('eslabonDTO').find('strHoraEntrega').text());
        }     	
        
      	//Hora de recepcion
        if($(xml).find('eslabonDTO').find('strHoraRecibe') != null){
    		   $('#horaRecepcionConsulta').html($(xml).find('eslabonDTO').find('strHoraRecibe').text());
        }
        
        //observacionesConsulta
        if($(xml).find('eslabonDTO').find('observacion') != null){
 		   $('#observacionesConsulta').html($(xml).find('eslabonDTO').find('observacion').text());
    	}
 	
        //Fecha de inicio de prestamo
        if($(xml).find('eslabonDTO').find('strFechaInicioPrestamoAlmacen') != null){
 		   $('#fechaInicioPrestamo').html($(xml).find('eslabonDTO').find('strFechaInicioPrestamoAlmacen').text());
    	}
        
      	//Hora de inicio de prestamo
        if($(xml).find('eslabonDTO').find('strHoraInicioPrestamo') != null){
    		   $('#horaInicioPrestamo').html($(xml).find('eslabonDTO').find('strHoraInicioPrestamo').text());
        }     	
      	
      	
      //Fecha de fin de prestamo
        if($(xml).find('eslabonDTO').find('strFechaFinPrestamoAlmacen') != null){
 		   $('#fechaFinPrestamo').html($(xml).find('eslabonDTO').find('strFechaFinPrestamoAlmacen').text());
    	}
        
      	//Hora de fin de prestamo
        if($(xml).find('eslabonDTO').find('strHoraFinPrestamo') != null){
    		   $('#horaFinPrestamo').html($(xml).find('eslabonDTO').find('strHoraFinPrestamo').text());
        }  
        
    }
    </script>
        </head>
    
	<table width="100%">
	  <tr>
	    <td colspan="2" align="left"><b>DATOS DE ENTREGA</b></td>
	    <td colspan="2" align="left"><b>DATOS DE RECEPCI&Oacute;N</b></td>
      </tr>
	  <tr>
	    <td>Nombre: </td>
	    <td><span id="nombreDeQuienEntregaConsulta"></span></td>
	    <td>Nombre:</td>
	    <td><span id="nombreDeQuienRecibeConsulta"></span></td>
      </tr>
	  <tr>
	    <td> Fecha: </td>
	    <td><span id="fechaEntregaConsulta"></span></td>
	    <td>Fecha:</td>
	    <td><span id="fechaRecepcionConsulta"></span></td>
      </tr>
	  <tr>
	    <td> Hora: </td>
	    <td><span id="horaEntregaConsulta"></span></td>
	    <td>Hora:</td>
	    <td><span id="horaRecepcionConsulta"></span></td>
      </tr>
	  <tr>
	    <td>Observaciones: </td>
	    <td colspan="2"><span id="observacionesConsulta"></span></td></td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td colspan="3"><strong>Periodo de pr&eacute;stamo</strong></td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td>Fecha inicial:</td>
	    <td><span id="fechaInicioPrestamo"></span></td>
	    <td>Fecha inicial:</td>
	    <td><span id="fechaFinPrestamo"></span></td>
      </tr>
	   <tr>
	    <td>Hora:</td>
	    <td><span id="horaInicioPrestamo"></span></td>
	    <td>Hora:</td>
	    <td><span id="horaFinPrestamo"></span></td>
      </tr>	  
    </table>

    </table>

</html>