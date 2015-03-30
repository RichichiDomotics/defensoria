package mx.gob.segob.nsjp.service.audiencia.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaAudienciaDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.CausaDAO;
import mx.gob.segob.nsjp.dto.audiencia.pjsiaj.AudienciaWSDTO;
import mx.gob.segob.nsjp.dto.audiencia.pjsiaj.NotificacionSIAJWSDTO;
import mx.gob.segob.nsjp.dto.audiencia.pjsiaj.RespuestaWSDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatAudiencia;
import mx.gob.segob.nsjp.model.Causa;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SalaAudiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.audiencia.NotificacionPJSIAJService;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("notificacionPJSIAJServiceImpl")
@Transactional
public class NotificacionPJSIAJServiceImpl implements NotificacionPJSIAJService {

	@Autowired
	private CasoDAO casoDao;
	@Autowired
	AudienciaDAO audienciaDAO;
	@Autowired
    private SalaAudienciaDAO salaDao;
	@Autowired
	private CausaDAO causaDao;
	@Autowired
	private ActividadDAO actividadDAO;
	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	
	@Override
	public RespuestaWSDTO recibeNotificacionAudiencia(NotificacionSIAJWSDTO notificacionWSDTO)
			throws NSJPNegocioException, ParseException {
		Caso caso = new Caso();
		Expediente exp = null;
		Audiencia audiencia = null;
		Actividad actividad = null;
		Documento documento = null;
		ArchivoDigital archdigital = null;
		NumeroExpediente numeroexp = null;
		Causa causa = null;
		RespuestaWSDTO respuesta = new RespuestaWSDTO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		caso = casoDao.consultarCasoPorNumeroCaso(notificacionWSDTO.getNuc());
		if (caso != null){
			exp = caso.getExpedientes().iterator().next();
			Iterator<NumeroExpediente> ne = exp.getNumeroExpedientes().iterator();
			while (ne.hasNext()){
				numeroexp = ne.next();
				if (numeroexp.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())
						|| numeroexp.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong())
						|| numeroexp.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.DEFENSORIA.parseLong()))
					break;
			}
			actividad = new Actividad();
			documento = new Documento();
			archdigital = new ArchivoDigital();
			causa = new Causa();
			long aud = 0L;
			if (notificacionWSDTO.getSolicitudDto().get(0)
					.getSolicitudRespuesta().equals("A")) {
				actividad.setTipoActividad(new Valor(1671L));
				audiencia = new Audiencia();
				audiencia.setNumeroExpediente(numeroexp);
				audiencia.setConsecutivo(buscarConsecutivo(numeroexp
						.getNumeroExpedienteId()));
				
				
				audiencia.setFechaAudiencia(formatter.parse(notificacionWSDTO.getAudiendiaDto()
						.get(0).getFechaInicio()));
				audiencia.setFechaAsignacionSala(new Date());
				// CAMBIAR POR EL VALOR CORRESPONDIENTE
				audiencia.setTipo(new Valor(292L));
				audiencia.setCatAudiencia(new CatAudiencia((long) notificacionWSDTO
						.getAudiendiaDto().get(0).getTipo()));
				audiencia
						.setDuracionEstimada(calcularDuracion(notificacionWSDTO
								.getAudiendiaDto().get(0)));
				
				causa.setcCausaPenalIdPJ(notificacionWSDTO.getCausaPenalIdPJ());
				
				Causa c = causaDao.buscarCausaByCCausaPenalIdPJ(notificacionWSDTO.getCausaPenalIdPJ());
				if (c != null && c.getCausaId() != null)
					audiencia.setCausa(c);
				else{
					causaDao.create(causa);
					audiencia.setCausa(causa);
				}
				
				try {
					audiencia.setSalaAudiencia(new SalaAudiencia(
							consultarIdSala(notificacionWSDTO.getAudiendiaDto()
									.get(0).getLugarSala())));

					audiencia.setEstatus(new Valor(
							obtenerEstatus(notificacionWSDTO.getAudiendiaDto()
									.get(0).getStatus())));
					audiencia.setFechaHoraFin(formatter.parse(notificacionWSDTO
							.getAudiendiaDto().get(0).getFechaTermino()));
					audiencia.setFolioAudiencia(notificacionWSDTO
							.getAudiendiaDto().get(0).getAudienciaIdPJ());
					aud = audienciaDAO.create(audiencia);
				} catch (NSJPNegocioException e) {
					respuesta.setCodigo(0);
					respuesta.setMensaje("No se localizó la SALA");
					respuesta.setFolio(null);
				}catch (DataIntegrityViolationException e) {
					respuesta.setCodigo(0);
					respuesta.setMensaje("No se localizó la SALA");
					respuesta.setFolio(null);
				}
			} else {
				actividad.setTipoActividad(new Valor(1674L));
			}
			actividad.setFuncionario(numeroexp.getFuncionario());
			actividad.setExpediente(exp);
			actividad.setFechaCreacion(new Date());
			documento.setNombreDocumento(notificacionWSDTO.getDocumentoDto().getNombre());
			documento.setFechaCreacion(new Date());
			documento.setTipoDocumento(new Valor(TipoDocumento.OFICIO.getValorId()));
			documento.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO.getValorId()));
			documento.setEsGuardadoParcial(false);
			archdigital.setNombreArchivo(notificacionWSDTO.getDocumentoDto().getNombre());
			archdigital.setTipoArchivo(notificacionWSDTO.getDocumentoDto().getFormato());
			Base64 base64 = new Base64();
			
			archdigital.setContenido(base64.decode(notificacionWSDTO.getDocumentoDto().getArchivo()));
			
			long archdig = archivoDigitalDAO.create(archdigital);
			documento.setArchivoDigital(new ArchivoDigital(archdig));
			Long idDocumento = documentoDAO.create(documento);
			Documento doc=new Documento();
			doc.setDocumentoId(idDocumento);
			actividad.setDocumento(doc);
			long act = actividadDAO.create(actividad);
			
			respuesta.setCodigo(1);
			respuesta.setMensaje("Notificación exitosa...");
			if(aud != 0L){
				respuesta.setFolio("10"+Long.toString(aud));
			} else {
				respuesta.setFolio("20"+Long.toString(act));
			}
			
		} else {
			respuesta.setCodigo(0);
			respuesta.setMensaje("No se localizó el NUC");
			respuesta.setFolio(null);
		}
		return respuesta;
	}
	
	private Short buscarConsecutivo (Long nexp) throws NSJPNegocioException {
		List<Audiencia> audiencias = audienciaDAO.consultarAudienciasporExpediente(nexp);
		Audiencia audiencia = null;
		Short consecutivo = 0;
		if (audiencias != null && audiencias.size() != 0){
			audiencia = audiencias.get(audiencias.size()-1);
			consecutivo = (short)(audiencia.getConsecutivo()+1);
		}
		else
			consecutivo = 1;
		return consecutivo;
	}
	
	private int calcularDuracion(AudienciaWSDTO audiencia) throws ParseException{
		int duracionM = 0;
		int duracionH = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm");
		Calendar fInicio = Calendar.getInstance();
		fInicio.setTime(formatter.parse(audiencia.getFechaInicio()));
		Calendar fFinal = Calendar.getInstance();
		fFinal.setTime(formatter.parse(audiencia.getFechaTermino()));
		duracionM = fFinal.get(Calendar.MINUTE) - fInicio.get(Calendar.MINUTE);
		duracionH = fFinal.get(Calendar.HOUR_OF_DAY) - fInicio.get(Calendar.HOUR_OF_DAY);
		return duracionM + (duracionH*60);
	}
	
	private Long consultarIdSala(String nombre) throws NSJPNegocioException{
		List<SalaAudiencia> salas = salaDao.consultarTodos();
		Long salaId = 0L;
		SalaAudiencia sal;
		for(int i= 0; i < salas.size(); i++){
			sal = salas.get(i);
			if (sal.getNombreSala().equals(nombre))
				salaId = sal.getSalaAudienciaId();
		}
		return salaId;
	}
	
	private Long obtenerEstatus(String status){
		if (status.equals("P"))
			return EstatusAudiencia.PROGRAMADA.getValorId();
		else if(status.equals("S"))
			return EstatusAudiencia.CANCELADA.getValorId();
		else if(status.equals("R"))
			return EstatusAudiencia.REPROGRAMADA.getValorId();
		return null;
	}

}
