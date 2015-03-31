/**
 * Enable JC. Funciones comunes para el visor de compartir solicitudes en UAVD
 * AreaUsuario = 12 (CoordinadorUAVD)
 * AreaUsuario = 56 (Trabajo Social)
 * AreaUsuario = 57 (Atencion Psicologica)
 * AreaUsuario = 58 (Atencion Juridica)
 */

/**
 * Abre ventana para asignar permisos
 */
var iframewindowAPSE = 0;
function asignarPermisos(contextPath, idsTiposSolicitudes){
	$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
	$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="'+contextPath+'/asigarPermisosExpediente.do?idsTiposSolicitudes='+idsTiposSolicitudes+'" width="1430" height="670" />');
	$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
	iframewindowAPSE++;
}

/**
 * Asigna Permisos sobre un expediente (AMP) o sobre una solicitud (UAVD)
 */
function asignarPermisos(){
	if(areaUsuario == 12 || areaUsuario == 56 || areaUsuario == 57 || areaUsuario == 58){
		asignarPermisoSobreSolicitudes();
	}else{
		asignarPermisoSobreExpedientes();
	}
}

/**
 * Modifica Permisos sobre un expediente (AMP) o sobre una solicitud (UAVD)
 */
function modificarPermisos(){
	if(areaUsuario == 12 || areaUsuario == 56 || areaUsuario == 57 || areaUsuario == 58){
		modificarPermisoSobreSolicitudes();
	}else{
		modificarPermisoSobreExpedientes();
	}
}

/**
 * Elimina Permisos sobre un expediente (MP) o sobre una solicitud (UAVD)
 */
function eliminarPermisos(){
	if(areaUsuario == 12 || areaUsuario == 56 || areaUsuario == 57 || areaUsuario == 58){
		eliminarPermisoSobreSolicitudes();
	}else{
		eliminarPermisoSobreExpedientes();
	}
}