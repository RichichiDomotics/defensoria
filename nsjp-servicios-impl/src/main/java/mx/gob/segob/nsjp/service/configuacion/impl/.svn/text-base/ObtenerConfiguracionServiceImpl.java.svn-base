/**
 * Nombre del Programa : ObtenerConfiguracionServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 14 Oct 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute;n para obtener la configuraci&oacute;n general del sistema
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.configuacion.impl;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.domicilio.EntidadFederativaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.EntidadFederativa;
import mx.gob.segob.nsjp.service.configuracion.ObtenerConfiguracionService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci&oacute;n para obtener la configuraci&oacute;n general del sistema.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional
public class ObtenerConfiguracionServiceImpl implements
		ObtenerConfiguracionService {

	public static final Logger LOGGER = Logger
			.getLogger(ObtenerConfiguracionServiceImpl.class);

	@Autowired
    ParametroDAO paramDao;
    @Autowired
    private ConfInstitucionDAO institucionDAO;
    @Autowired
    private EntidadFederativaDAO entidadFederativaDAO;
    
    
	@Override
	public ConfiguracionDTO obtgenerConfiguracionGlobal() {
		final ConfiguracionDTO resp = new ConfiguracionDTO();

		resp.setTiempoRevisionAlarmas(this.paramDao.obtenerPorClave(
				Parametros.TIEMPO_REVISION_ALARMAS).getValorAsLong());
		resp.setUrlServidorChat(this.paramDao.obtenerPorClave(
				Parametros.URL_SERVIDOR_CHAT).getValor());
		resp.setTiempoBloqueoSesion(this.paramDao.obtenerPorClave(
				Parametros.TIEMPO_BLOQUEO_SESION).getValorAsLong());
		resp.setHabilitarTurno(this.paramDao.obtenerPorClave(
				Parametros.HABILITAR_TURNO).getValorAsLong());
		resp.setValidaDelitoGrave(this.paramDao.obtenerPorClave(
				Parametros.VALIDA_DELITO_GRAVE).getValorAsLong());
        resp.setUrlServidorImag(this.paramDao.obtenerPorClave(
        		Parametros.URL_SERVIDOR_IMAGENES).getValor());
        resp.setValidaTipoExpedienteReporte(this.paramDao.obtenerPorClave(
				Parametros.VALIDA_TIPO_EXPEDIENTE_REPORTE).getValorAsLong());
        resp.setValidaPestanasJar(this.paramDao.obtenerPorClave(
				Parametros.PESTANAS_JAR).getValorAsLong());
        resp.setAutoAsignarExpedientes(this.paramDao.obtenerPorClave(
				Parametros.AUTOASIGNAR_EXPEDIENTES).getValorAsLong());
		resp.setAmbiente(this.paramDao.obtenerPorClave(Parametros.AMBIENTE)
				.getValor());
		try {
			EntidadFederativa entidadFederativa = entidadFederativaDAO
					.read(this.paramDao.obtenerPorClave(
							Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE)
							.getValorAsLong());
			resp.setEntidadFederativaDespliegue(entidadFederativa
					.getAbreviacion());
			resp.setNombreEntidadFederativaDespliegue(entidadFederativa.getNombre());
			resp.setEntidadFederativaDespliegueId(entidadFederativa.getEntidadFederativaId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ConfInstitucionDTO consultarInstitucionActual()
			throws NSJPNegocioException {

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR LA INSTITUCION ACTUAL****/");

		ConfInstitucion institucion = institucionDAO
				.consultarIntitucionActual();

		return ConfInstitucionTransformer.transformarInstitucion(institucion);
	}

}
