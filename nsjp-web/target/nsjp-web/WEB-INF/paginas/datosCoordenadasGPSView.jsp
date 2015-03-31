<script type="text/javascript">

	var idWindowMapa="";
	
	  var latitud='';
	  var longitud='';
	  var idFrameMapa='';
	  var direccionGoogle='';


  	/*
   *Funcion para recuperar los datos de Coordenadas GPS
   */
   function recuperaDatosCoordenadasGPS(idCalidad)
   {
	   var lsDatosCoordenadasGPS="";
	   		lsDatosCoordenadasGPS="longitudE="+$("#txtFldLongitudEste").val();
// 	   		lsDatosCoordenadasGPS+="&longitudGrados="+$("#txtFldLongitudGrados").val();
// 	   		lsDatosCoordenadasGPS+="&longitudMinutos="+$("#txtFldLongitudMinutos").val();
// 	   		lsDatosCoordenadasGPS+="&longitudSegundos="+$("#txtFldLongitudSegundos").val();
	   		lsDatosCoordenadasGPS+="&latitudN="+$("#txtFldLatitudNorte").val();
// 	   		lsDatosCoordenadasGPS+="&latitudGrados="+$("#txtFldLatitudGrados").val();
// 	   		lsDatosCoordenadasGPS+="&latitudMinutos="+$("#txtFldLatitudMinutos").val();
// 	   		lsDatosCoordenadasGPS+="&latitudSegundos="+$("#txtFldLatitudSegundos").val();
	   return lsDatosCoordenadasGPS;
   }

   /*
    *Funcion para pintar los datos mediante la recuperacion del xml del tipo de datos coordenadas GPS
    */
   function pintaDatosCoordenadasGPS(xml){
	   if($(xml).find('domicilio').find('longitudE').text()=="")
		   $('#txtFldLongitudEste').val($(xml).find('domicilioDTO').find('longitudE').text());
	   else
		  $('#txtFldLongitudEste').val($(xml).find('domicilio').find('longitudE').text());
// 	   if($(xml).find('domicilio').find('longitudGrados').text()=="")
// 	   		$('#txtFldLongitudGrados').val($(xml).find('domicilioDTO').find('longitudGrados').text());
// 	   else
// 		   $('#txtFldLongitudGrados').val($(xml).find('domicilio').find('longitudGrados').text());
// 	   if($(xml).find('domicilio').find('longitudMinutos').text()=="")
// 		   $('#txtFldLongitudMinutos').val($(xml).find('domicilioDTO').find('longitudMinutos').text());
// 	   else
// 	   		$('#txtFldLongitudMinutos').val($(xml).find('domicilio').find('longitudMinutos').text());
// 	   if($(xml).find('domicilio').find('longitudSegundos').text()=="")
// 		   $('#txtFldLongitudSegundos').val($(xml).find('domicilioDTO').find('longitudSegundos').text());
// 	   else
// 			$('#txtFldLongitudSegundos').val($(xml).find('domicilio').find('longitudSegundos').text());
	   if($(xml).find('domicilio').find('latitudN').text()=="")
		   $('#txtFldLatitudNorte').val($(xml).find('domicilioDTO').find('latitudN').text());
	   else
	   		$('#txtFldLatitudNorte').val($(xml).find('domicilio').find('latitudN').text());
// 	   if($(xml).find('domicilio').find('latitudGrados').text()=="")
// 		   $('#txtFldLatitudGrados').val($(xml).find('domicilioDTO').find('latitudGrados').text());
// 	   else
// 	   		$('#txtFldLatitudGrados').val($(xml).find('domicilio').find('latitudGrados').text());
// 	   if($(xml).find('domicilio').find('latitudMinutos').text()=="")
// 		   $('#txtFldLatitudMinutos').val($(xml).find('domicilioDTO').find('latitudMinutos').text());
// 	   else
// 	   		$('#txtFldLatitudMinutos').val($(xml).find('domicilio').find('latitudMinutos').text());
// 	   if($(xml).find('domicilio').find('latitudSegundos').text()=="")
// 		   $('#txtFldLatitudSegundos').val($(xml).find('domicilioDTO').find('latitudSegundos').text());
// 	   else
// 	   		$('#txtFldLatitudSegundos').val($(xml).find('domicilio').find('latitudSegundos').text());
	}
   
   function seteaDatosCoordenadasGPSHecho(xml)
   {
	   //$('#txtFldLongitud').val($(xml).find('hechoDTO').find('lugar').find('longitud').text());
	   //$('#txtFldLatitud2').val($(xml).find('hechoDTO').find('lugar').find('latitud').text());
	   $('#txtFldLongitudEste').val($(xml).find('lugar').find('longitudE').text());
// 	   $('#txtFldLongitudGrados').val($(xml).find('lugar').find('longitudGrados').text());
// 	   $('#txtFldLongitudMinutos').val($(xml).find('lugar').find('longitudMinutos').text());
// 	   $('#txtFldLongitudSegundos').val($(xml).find('lugar').find('longitudSegundos').text());
	   $('#txtFldLatitudNorte').val($(xml).find('lugar').find('latitudN').text());
// 	   $('#txtFldLatitudGrados').val($(xml).find('lugar').find('latitudGrados').text());
// 	   $('#txtFldLatitudMinutos').val($(xml).find('lugar').find('latitudMinutos').text());
// 	   $('#txtFldLatitudSegundos').val($(xml).find('lugar').find('latitudSegundos').text());
   }
   
   function bloqueaCamposCoordenadasGPSHecho(bandera)
   {
	   if(parseInt(bandera)==0)
	   {
		   $('#txtFldLongitudEste').attr("disabled","disabled");
// 		   $('#txtFldLongitudGrados').attr("disabled","disabled");
// 		   $('#txtFldLongitudMinutos').attr("disabled","disabled");
// 		   $('#txtFldLongitudSegundos').attr("disabled","disabled");
		   $('#txtFldLatitudNorte').attr("disabled","disabled");
// 		   $('#txtFldLatitudGrados').attr("disabled","disabled");
// 		   $('#txtFldLatitudMinutos').attr("disabled","disabled");
// 		   $('#txtFldLatitudSegundos').attr("disabled","disabled");
		   $('#txtFldLatitudSegundosNotif').attr("disabled","disabled");
		   /*se bloquean los botones para que no se puedan mostrar/*ByYolo Enable IT*/
			 $('#BuscarMap').attr("disabled","disabled");
			 $('#GuardarDireccionMap').attr("disabled","disabled");
			 modificarMapa="no";
	   }
	   else
	   {
		   $('#txtFldLongitudEste').attr("disabled","");
// 		   $('#txtFldLongitudGrados').attr("disabled","");
// 		   $('#txtFldLongitudMinutos').attr("disabled","");
// 		   $('#txtFldLongitudSegundos').attr("disabled","");
		   $('#txtFldLatitudNorte').attr("disabled","");
// 		   $('#txtFldLatitudGrados').attr("disabled","");
// 		   $('#txtFldLatitudMinutos').attr("disabled","");
// 		   $('#txtFldLatitudSegundos').attr("disabled","");
		   /*se bloquean los botones para que no se puedan mostrar/*ByYolo Enable IT*/
			 $('#BuscarMap').attr("disabled","");
			 $('#GuardarDireccionMap').attr("disabled","");			 
			 modificarMapa="si";
	   }
   }
	var patron = new Array(2,2,4)
	var patron2 = new Array(1,3,3,3,3)
	function mascara(d,sep,pat,nums){
		if(d.valant != d.value){
		val = d.value
   		largo = val.length
   		val = val.split(sep)
   		val2 = ''
   		for(r=0;r<val.length;r++){
   			val2 += val[r]	
   		}
   		if(nums){
   			for(z=0;z<val2.length;z++){
   				if(isNaN(val2.charAt(z))){
   					letra = new RegExp(val2.charAt(z),"g")
   					val2 = val2.replace(letra,"")
   				}
   			}
   		}
   		val = ''
   		val3 = new Array()
   		for(s=0; s<pat.length; s++){
   			val3[s] = val2.substring(0,pat[s])
   			val2 = val2.substr(pat[s])
   		}
   		for(q=0;q<val3.length; q++){
   			if(q ==0){
   				val = val3[q]
   			}
   			else{
   				if(val3[q] != ""){
   					val += sep + val3[q]
   				}
   			}
   		}
   		d.value = val
   		d.valant = val
   	}
  }


	function solonumeros(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		// if the key is backspace, tab, or numeric
		if (unicode == 8 || unicode == 9 || (unicode >= 48 && unicode <= 57)) {
		// we allow the key press
		return true;
		}
		else {
		// otherwise we don't
		return false;
		}
		}

	function soloLetras(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		// if the key is backspace, tab, or numeric
		//if (unicode == 8 || unicode == 9 || (unicode >= 65 && unicode <= 90)) { todas las letras en mayuscula
		if (unicode == 8 || unicode == 9 || unicode == 79 || unicode == 69 || unicode == 101 || unicode == 111 ) {// solo las letras utilizadas
		// we allow the key press
		return true;
		}
		else {
		// otherwise we don't
		return false;
		}
		}

	function soloLetrasNorte(e) {
		var unicode = e.charCode ? e.charCode : e.keyCode;

		// if the key is backspace, tab, or numeric
		if (unicode == 8 || unicode == 9 || unicode == 78 || unicode == 83 || unicode == 110 || unicode == 115  ) {
			
		// we allow the key press
		return true;
		}
		else {
		// otherwise we don't
		return false;
		}
		}
	
	function mayorMinLong() {
		var min=$("#txtFldLongitudMinutos").val();
		if(min>59 || min==0){
			$("#txtFldLongitudMinutos").val("");
		}
	}
	function mayorMinLat() {
		var min=$("#txtFldLatitudMinutos").val();
		if(min>59 || min==0){
			$("#txtFldLatitudMinutos").val("");
		}
	}
	function mayorSegLong() {
		var min=$("#txtFldLongitudSegundos").val();
		if(min>59 || min==0){
			$("#txtFldLongitudSegundos").val("");
		}
	}
	function mayorSegLat() {
		var min=$("#txtFldLatitudSegundos").val();
		if(min>59 || min==0){
			$("#txtFldLatitudSegundos").val("");
		}
	}
	   
	//funcion para limpiar los campos de las corrdenadas GPS
	 function limpiarDtsCrdndsGPS()
	   {
	   	   $('#txtFldLongitudEste').val("");
// 		   $('#txtFldLongitudGrados').val("");
// 		   $('#txtFldLongitudMinutos').val("");
// 		   $('#txtFldLongitudSegundos').val("");
		   $('#txtFldLatitudNorte').val("");
// 		   $('#txtFldLatitudGrados').val("");
// 		   $('#txtFldLatitudMinutos').val("");
// 		   $('#txtFldLatitudSegundos').val("");
		  }
	
	function abreMapa(){
		var direccion='';
		var pais = $('#cbxPais option:selected').text();
		var entFederativa=$('#cbxEntFederativa option:selected').text();
		var ciudad=$('#cbxCiudad option:selected').text();
		var delegacionMunicipio=$('#cbxDelegacionMunicipio option:selected').text();
		var asentamientoColonia=$('#cbxAsentamientoColonia option:selected').text();
		var calle=$('#areaCalle').val();;

		if(pais !=null && pais!='undefined' && pais!='-Seleccione-' ){
			direccion=direccion+' '+pais;
		}
		
		if(entFederativa !=null && entFederativa!='undefined' && entFederativa!='-Seleccione-' ){
			direccion=direccion+' '+entFederativa;
		}
		
		if(ciudad !=null && ciudad!='undefined' && ciudad!='-Seleccione-' ){
			direccion=direccion+' '+ciudad;
		}	

		if(delegacionMunicipio !=null && delegacionMunicipio!='undefined' && delegacionMunicipio!='-Seleccione-' ){
			direccion=direccion+' '+delegacionMunicipio;
		}	

		if(asentamientoColonia !=null && asentamientoColonia!='undefined' && asentamientoColonia!='-Seleccione-' ){
			direccion=direccion+' '+asentamientoColonia;
		}			
		
		if(calle !=null && calle!='undefined' ){
			direccion=direccion+' '+calle;
		}	
		
// 		idWindowMapa++;
		$.newWindow({
				id:"idWindowMapa", 
				statusBar: true, 
				posx:20,posy:5,
				width:1140, 
				height:520,
				title:"Mapa", 
				type:"iframe",
					onWindowClose: function(){
						
						if( modificarMapa==="si"){						
							$('#txtFldLongitudEste').val(longitud);						
						}
						if(modificarMapa==="si"){
							$('#txtFldLatitudNorte').val(latitud);
						}
						
						if(modificarMapa==="si"){
							$('#txtDireccionGoogle').val(direccionGoogle);
						}

					}
		});
		idFrameMapa="idWindowMapa";
		if($('#txtFldLongitudEste').val()!='' && $('#txtFldLatitudNorte').val()!=''){
			direccion='';
			direccion=$('#txtFldLatitudNorte').val()+' '+$('#txtFldLongitudEste').val();
		}
	    $.updateWindowContent("idWindowMapa",'<iframe src="<%= request.getContextPath() %>/abreMapa.do?direccion='+direccion+'&idFrameMapa='+idFrameMapa+'" width="1140" height="550" />');

	}
	
	function cierraMapa(idVentana){
		$.closeWindow(idVentana);
	}
	
	
</script>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    	<td>&nbsp;&nbsp;Longitud:E/O:</td>
  		<td valign="top" colspan="4"><input type="text" name="txtFldLatitudNorte" id="txtFldLongitudEste"  onKeyPress="return solonumeros(event);"  tabindex="18"/></td>    	
<!--   		<td valign="top"><input type="text" name="txtFldLatitudNorte" id="txtFldLongitudEste" size="1" maxlength="1" onKeyPress="return soloLetras(event);" onKeyUp="this.value = this.value.toUpperCase();" tabindex="18"/></td> -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudGrados" id="txtFldLongitudGrados" size="1" maxlength="2" onKeyPress="return solonumeros(event);"  tabindex="20"/>&nbsp;°&nbsp;</td> -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudMinutos" id="txtFldLongitudMinutos" size="1" maxlength="2" onKeyPress="return solonumeros(event);" onKeyUp="mayorMinLong()" tabindex="22"/>&nbsp;&nbsp;'&nbsp;&nbsp;</td> -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudSegundos" id="txtFldLongitudSegundos" size="1" maxlength="2" onKeyPress="return solonumeros(event);" onKeyUp="mayorSegLong()" tabindex="24"/>&nbsp;"</td> -->
  </tr>
  <tr>
  		<td>&nbsp;&nbsp;Latitud:N/S:</td>
  		<td valign="top" colspan="4"><input type="text" name="txtFldLatitudNorte" id="txtFldLatitudNorte"  onKeyPress="return solonumeros(event);"  tabindex="19" /></td>
<!--   		<td valign="top"><input type="text" name="txtFldLatitudNorte" id="txtFldLatitudNorte" size="1" maxlength="1" onKeyPress="return soloLetrasNorte(event);" onKeyUp="this.value = this.value.toUpperCase();" tabindex="19" /></td>  		 -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudGrados" id="txtFldLatitudGrados" size="1" maxlength="2"  onKeyPress="return solonumeros(event);" tabindex="21"/>&nbsp;°&nbsp;</td> -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudMinutos" id="txtFldLatitudMinutos" size="1" maxlength="2" onKeyPress="return solonumeros(event);" onKeyUp="mayorMinLat()" tabindex="23"/>&nbsp;&nbsp;'&nbsp;&nbsp;</td> -->
<!--   		<td valign="top"><input type="text" name="txtFldLatitudSegundos" id="txtFldLatitudSegundos" size="1" maxlength="2" onKeyPress="return solonumeros(event);" onKeyUp="mayorSegLat()" tabindex="25"/>&nbsp;"</td> -->
  </tr>
  <tr>
		<td>&nbsp;&nbsp;Dirección en &nbsp;&nbsp;Mapa: </td>
		<td><textarea rows="2" cols="35" id="txtDireccionGoogle" maxlength="500"></textarea></td>
		
  </tr>

</table><!-- <label id="btnVerMapa"><input type="button" onclick="abreMapa()" value="Ver Mapa" class="btn_Generico"></label> -->

