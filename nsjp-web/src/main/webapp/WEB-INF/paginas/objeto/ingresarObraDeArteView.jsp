<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
<%@ page import="org.omg.CORBA.Request"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Ingresar obra de arte</title>
        <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
        
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
        
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
        <script src="<%=request.getContextPath()%>/js/prettify.js"></script>
        <script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
        
        <script src="<%=request.getContextPath()%>/js/comun.js"></script>
        <script src="<%=request.getContextPath()%>/js/objetos.js"></script>
        
    <%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	%>
               
        <script type="text/javascript">
        
    	    var rolActivo = '<%=rolActivo%>';
    	    var entidadFedYuc = '<%=EntidadFederativa.YUCATAN.getValorId()%>';
	    	var idObraArte="";
	    	var tipoObjeto="";
	    	var numeroExpediente="";
	    	var cadenaCustodia="";
	    	var fechaLevCadCus="";
	    	var origenEvdCadCus="";
	    	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
	    	var ocultaAnularObjetoCadCus=null;
	    	
	    	var contextoPagina = "${pageContext.request.contextPath}";

	    	var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>';
    	
            jQuery().ready(
            function () {
            	//Instruccion pensada solo para el caso de policia ministerial
    			if(deshabilitarCampos == true){
    				$(":enabled").attr('disabled','disabled');
    			}	
            	
    			if(esModoConsulta != null && esModoConsulta == '1'){
    				$(":enabled").attr('disabled','disabled');
    				$('input[type="submit"]').hide();
    				$('input[type="button"]').hide();
    			}
            	
                numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
        		idObraArte='<%= request.getParameter("idObraArte")%>';
    			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
    			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
    			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
    			
    			if (idObraArte != null && idObraArte != 0)
    				$("#imgConFoto").attr("src",'<%=request.getContextPath()%>/obtenImagenDeElemento.do?elementoID=<%= request.getParameter("idObraArte")%>');
    			else{
    				$("#imgConFoto").attr("src","<%=request.getContextPath()%>/resources/images/Foto.JPG");
    			}
    			
    			if(idObraArte=='null')
    			{
    				idObraArte=0;	
    			}
    			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
    			
            	cargaTiposObraArte();
                cargaCondicion();
                cargaRelacionesHecho('<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getEntidadFederativaDespliegueId() %>');
                var num=parent.num;
                
              //lineas para ocultar la opcion de anular objeto alconsultar desde una cadena de custodia
    			//Viene con valor a 1 desde asentarRegCadenaCustodiaView.jsp
    			//No es setteado en el menu intermedio
    			ocultaAnularObjetoCadCus='<%= request.getParameter("anularConsultaCadCus")%>';
    			
    			if(parseInt( ocultaAnularObjetoCadCus ) ==1)
    			{
    				$("#anularElemento").hide("");
    				$("#anularInv").hide("");
        			if(idObraArte!=null && idObraArte!=0){
        				$(":enabled").attr('disabled','disabled');
        				$('input[type="submit"]').hide();
        				$('input[type="button"]').hide();
        			}
    			}
    			

    			//revisamos si es una consulta
    			if(idObraArte!=null && idObraArte!=0)
    			{
    				consultaObraArte();
    				//condicional para no mostrar el boton de anular objeto cuando entramos desde cadena de custodia
    				if(parseInt( ocultaAnularObjetoCadCus ) ==1)
    				{
    					$("#anularElemento").hide("");
    					$("#anularInv").hide("");
    				}
    				else
    				{
    					$("#anularElemento").show("");
    					$("#anularInv").show("");
    				}
    			}
    			else
    			{
	    			if(num!=null && num!="0"){
	    				$("#anularElemento").hide();
	    				$("#anularInv").hide();
	    			}
    			}
    			
    			if (rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
    				$(":enabled").attr('disabled','disabled');
    				$('input[type="submit"]').hide();
    				$('input[type="button"]').hide();
    			}

    		});

            /*
             *Funcion que realiza la carga del combo de tipos de obra de arte
             */
            function cargaTiposObraArte() {
		  
                $.ajax({
                    async: false,
                    type: 'POST',
                    url: '<%= request.getContextPath()%>/consultarTiposObraArte.do',
                    data: '',
                    dataType: 'xml',
                    success: function(xml){
                        $(xml).find('catTiposObraArte').each(function(){
                            $('#cbxTipoObraArte').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
                        });
                    }
                });
            }

	
            /*
             *Funcion que realiza la carga de la condicion de la obra de arte
             */
            function cargaCondicion() {
                $.ajax({
                    async: false,
                    type: 'POST',
                    url: '<%= request.getContextPath()%>/consultarCondicion.do',
                    data: '',
                    dataType: 'xml',
                    success: function(xml){
                        $(xml).find('catCondicion').each(function(){
                            $('#cbxCondicionObraArte').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
                        });
                    }
                });
            }

        	/*
        	*Funcion para mandar consultar el arma
        	*/
        	function consultaObraArte()
        	{
        		$.ajax({
            		type: 'POST',
            		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
            		data: 'idObjeto='+idObraArte,
            		dataType: 'xml',
            		async: false,
            		success: function(xml){
            			if(parseInt($(xml).find('code').text())==0)
        	    		{
            				//seteamos la informacion del hecho
            				$(xml).find('ObraArteDTO').each(function(){
            						seteaDatosObraArte($(this));
        		    	      });
        	    		}
            		}	
            	});
        	}

        	function seteaDatosObraArte(xml)
        	{
        		$('#cbxTipoObraArte').find("option[value='"+$(xml).find('tipoObraArte').find('idCampo').text()+"']").attr("selected","selected");
        		$('#txtCantidadObraArte').val($(xml).find('cantidad').text());
        		if($(xml).find('almacen'))
        			$(xml).find('almacen').remove();
        		$('#txtBoxDescObraArte').val($(xml).find('descripcion').text());
        		$('#cbxCondicionObraArte').find("option[value='"+$(xml).find('valorByCondicionFisicaVal').find('idCampo').text()+"']").attr("selected","selected");
        		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");
        	}
        	
        	/*Funcion que permite guardar una imagen
			* Se define en la forma:
			*	-id del elemento para relacionarlo a la imagen.
			*   -tipo de elemento para que, en el action, se difernecie sobre el tipo de elemento 
			*/
			function enviaImagenDeElemento(idElemento){
					document.frmImagenElemento.elementoID.value = idElemento;
					document.frmImagenElemento.tipoElementoId.value = '<%= TipoElemento.OBJETO.getValorId() %>';
			    	document.frmImagenElemento.submit();
			}	
        	
            //	COMIENZAN FUNCIONES PARA EL GUARDADO DE LOS DATOS
            function obtenerValoresObraDeArte(){		
                var paramObraArte = "idObraDeArte="+idObraArte;  
                paramObraArte += '&glTipoObraArteId=' + $("#cbxTipoObraArte option:selected").val();
                paramObraArte += '&gsCantidadObraArte=' + $('#txtCantidadObraArte').val();
                paramObraArte += '&glCondicionFisica=' + $("#cbxCondicionObraArte option:selected").val();
                paramObraArte += '&gcDescripcion=' + $("#txtBoxDescObraArte").val();
                paramObraArte += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
                
        		if(cadenaCustodia!='null')
        		{
        			paramObraArte += '&cadenaCustodia=' + cadenaCustodia;
        			paramObraArte += '&origenEvdCadCus=' + origenEvdCadCus;
        			paramObraArte += '&fechaLevCadCus=' + fechaLevCadCus;
        		}
                
                $.ajax({		
                    async: false,
                    type: 'POST',
                    url: '<%= request.getContextPath()%>/ingresarObraDeArte.do?numeroExpediente='+numeroExpediente +'',
                    data: paramObraArte,
                    dataType: 'xml',
                    success: function(xml){
                     var tipoObraArte = $("#cbxTipoObraArte option:selected").text();
      				  var id = $(xml).find('ObraArteForm').find('glTipoObraArteId').text();
      				  
      				  //Se ha agregado nuevo elemento
      				  if(idObraArte==0 && id>0)
      				  {
      					  //Se valida si se ha ingresado una imagen nueva
    					  if (document.frmImagenElemento.archivo.value != ''){
    						  //Para guardar la imagen
    						  enviaImagenDeElemento(id);  
    					  } 
    					  
    					  //Desde asentarRegCadenaCustodiaView.jsp 
    					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
    						  alertDinamico("Se guard� correctamente la informaci�n");
    						  regresarControlCadenaCustodia();
    						  //customAlert("Se guard� correctamente la informaci�n", '', regresarControlCadenaCustodia);
    					  }else //Desde el ingresarMenuIntermedio.jsp
    					  {
    						  window.parent.customAlert('Se guard� correctamente la informaci�n', '',regresarControl );
    					  }
      				  }
      				  else if(idObraArte==0 && id==0)
      				  {
      					window.parent.alertDinamico("Favor de revisar la informaci�n capturada");
      				  }
	      			  else  //Actualizacion del elemento - solo desde el ingresarMenuIntermedio.jsp 
	  				  {   
	  					  //Se valida si se ha ingresado una imagen nueva o a actualizar
	  					  if (document.frmImagenElemento.archivo.value != ''){
	  						  //Para guardar la imagen
	  						  enviaImagenDeElemento(id);  
	  					  } 
	  					  window.parent.customAlert('La informaci�n se actualiz� correctamente', '',regresarControl );
	  				  }
                    }
                });
            }
            
        	/**
        	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
        	* solo si viene desde asentarRegCadenaCustodia.sjp
        	*/
        	function regresarControlCadenaCustodia(){
        		window.parent.cargaObjetoCerrarVentana();
        	}
        	/**
        	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
        	* solo si viene desde ingresarMenuIntermedio.jsp
        	*/	
        	function regresarControl(){
        		window.parent.cargaObraDeArte();
        	}
        	
            function validaCamposObligatorios(){
            	var tipoObraArte = $("#cbxTipoObraArte option:selected").val();
                var condicionObraArte = $("#cbxCondicionObraArte option:selected").val();
                var mensaje = "";
        		//Primera validacion por cada campo obligatorio		
        		if(parseInt(tipoObraArte) == -1){
        			mensaje += "<br />- Tipo de la obra de arte";
        		}        		
        		if(parseInt(condicionObraArte) == -1){
        			mensaje += "<br />- Condici�n de la obra de arte";
        		}
        		//Comienza segunda validacion para validacion de consistencia de expresiones regulares
        		if(mensaje != ""){
        			//mensaje += "\n\nEs necesario...."; FUTURAS VALIDACIONES
        			if(parseInt( ocultaAnularObjetoCadCus ) ==1)
					{// alert especial cuando agregamos el objeto como evidencia en la cadena de custodia
						window.parent.alertDinamicoEvCadCus("Favor de revisar la informaci�n capturada");
					}
					else
					{
						window.parent.alertDinamico("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
					}
        		}else{			
        			obtenerValoresObraDeArte();
        		}                
            }
            
        	/*
        	*Funcion que manda a eliminar logicamente el objeto en la BD
        	*/
        	function anularObjeto(){
        		var paramObraArte="idObjeto="+idObraArte;
        		$.ajax({		
        			async: false,
        			type: 'POST',
        			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
        			data: paramObraArte,
        			dataType: 'xml',
        			success: function(xml){
        				//procederemos a tratar de eliminar la evidencia
        				if(parseInt($(xml).find('bandera').text())==1)
        				{
        					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
        					window.parent.cargaObraDeArte("","");
        					window.parent.cerrarVentanaObraDeArte();
        				}
        				if(cadenaCustodia=='null'){
        					//alert("cadCus::"+cadenaCustodia);
        					window.parent.alertDinamico($(xml).find('mensajeOp').text());
        				}
        			}
        		});

        	}
        	
        	/*
        	*Funcion para anular el objeto
        	*/
        	function solicitarAnlrObjeto(){
        		//revisamos si es una consulta
        		if(idObraArte!=null && idObraArte!=0)
        		{
        			//procederemos a tratar de eliminar la evidencia
        			customConfirm ("�Est� seguro que desea anular el objeto?", "", validarObjEvdncNoEslbns);
        		}
        	}
        	
        	/*
        	*Funcion que validara si el objeto es evidencia y NO tiene eslabones, de ser asi
        	*se debe confirmar que se desea eliminar dado que el objeto ya esta en una cadena de custdia
        	*/
        	function validarObjEvdncNoEslbns()
        	{
        		var paramObraArte="idObjeto="+idObraArte;
        		$.ajax({		
        			async: false,
        			type: 'POST',
        			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
        			data: paramObraArte,
        			dataType: 'xml',
        			success: function(xml){
        				//procederemos a tratar de eliminar la evidencia
        				if(parseInt($(xml).find('bandera').text())==1)
        				{
        					//debemos mostrar un confirm
        					customConfirm ("El objeto es ya una evidencia en alguna cadena de custodia <br/> �Est� seguro que desea anular el objeto?", "", anularObjeto);
        				}
        				else if(parseInt($(xml).find('bandera').text())==2)
        				{
        					//se puede eliminar el objeto sin problemas
        					anularObjeto();
        				}
        				else
        				{
        					window.parent.alertDinamico($(xml).find('mensajeOp').text());
        				}
        			}
        		});
        	}
        	
    		window.onload = function(){
    			var selects = document.getElementsByTagName("textarea");
    			for (var i = 0; i < selects.length; i++) { 
    				if(selects[i].getAttribute("maxlength") > 0){
    					selects[i].onkeydown = function(){
    		                            if (this.value.length > this.getAttribute("maxlength")) 
    		                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
    		                        }
    		                        selects[i].onblur = function(){
    		                            if (this.value.length > this.getAttribute("maxlength")) 
    		                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
    		                        }
    				}
    			}
    		}
        </script>
    </head>
    <body>
                    <table width="750px"  height="220px" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr><td>&nbsp;</td></tr>
                    	<tr height="12.5%">
                            <td width="20%"  align="right"" class="txt_gral_victima" id="anularInv"></td>
                            <td width="28%"></td>
                            <td width="26%">&nbsp;</td>
                            <td width="26%" align="right">
                            	<input type="button" value="Anular objeto" class="btn_Generico"id="anularElemento" onclick="solicitarAnlrObjeto()"/>
                            	<input type="button" id="btnGuardarObraDeArte" value="Guardar" onclick="validaCamposObligatorios();" class="btn_Generico" />
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>                       
                        <tr height="12.5%">
                            <td width="20%">&nbsp;</td>
                            <td width="28%">&nbsp;</td>
                            <td width="26%" align="center">Descripci&oacute;n:</td>
                            <td width="26%" align="center">Fotograf�a:</td>
                        </tr >
                        <tr height="12.5%">
                            <td width="20%" align="right">*Tipo:</td>
                            <td width="28%"><select id="cbxTipoObraArte" style="width:180px">
                                    <option value="-1">-Seleccione-</option>
                                </select></td>
                            <td width="26%" rowspan="6" align="center" valign="top">
                                <textarea cols="29" rows="9" id="txtBoxDescObraArte" maxlength="200"></textarea>
                            </td>
                            <td width="26%" rowspan="6" align="center" valign="middle">
                                <img id="imgConFoto" src="<%=request.getContextPath()%>/resources/images/Foto.JPG" alt="foto del objeto" width="185" height="185" />
                            </td>
                        </tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">Cantidad:</td>
                            <td width="28%"><input type="text" id="txtCantidadObraArte" maxlength="8" style="width:140px"/> Piezas</td>
                        </tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">&nbsp;</td>
                            <td width="28%">&nbsp;</td>
                        </tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">&nbsp;</td>
                            <td width="28%">&nbsp;</td>
                        </tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">&nbsp;</td>
                            <td width="28%">&nbsp;</td>
                        </tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">*Condici&oacute;n:</td>
                            <td width="28%"><select id="cbxCondicionObraArte" style="width:180px">
                                    <option value="-1">-Seleccione-</option>
                                </select></td>
                        </tr>
                        <tr id="trRelacionHecho" height="12.5%">
				    		<td align="right">Relaci&oacute;n del hecho: </td>
				    		<td >
				    			<select name="cbxRelacionHecho" id="cbxRelacionHecho" style="width:180px">
				    				<option value="-1">-Seleccione-</option>
				    			</select>
				    		</td>
				    	</tr>
                        <tr height="12.5%">
                            <td width="20%" align="right">&nbsp;</td>
                            <td width="28%">&nbsp;</td>
                            <td width="26%">&nbsp;</td>
                            <td width="26%">&nbsp;</td>
                        </tr>
                    </table>
        <table width="750px" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<form id="frmImagenElemento" name="frmImagenElemento" action="<%= request.getContextPath() %>/ingresarImagenDelElementoObraDeArte.do" method="post" enctype="multipart/form-data" align="left">
				         		<input type="hidden" name="elementoID"/>
				                <input type="file" name="archivo" id="uploadArchivo">
				                <input type="hidden" name="tipoElementoId"/>
	        	</form>
			</td>
		</tr>			
		</table>
    </body>
</html>