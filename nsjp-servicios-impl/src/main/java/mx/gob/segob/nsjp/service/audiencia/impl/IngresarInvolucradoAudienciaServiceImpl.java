/**
* Nombre del Programa : IngresarInvolucradoAudienciaServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 21/09/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.InvolucradoAudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.OrganizacionAudienciaDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.CatRelacion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.model.InvolucradoAudienciaId;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.model.OrganizacionAudiencia;
import mx.gob.segob.nsjp.model.OrganizacionAudienciaId;
import mx.gob.segob.nsjp.model.Relacion;
import mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.organizacion.impl.transform.OrganizacionTransformer;

/**
 * Implementaci&oacute;n del servicio de negocio para asociar un involucrado a una audiencia
 * @version 1.0
 * @author Emigdio Hern&aacute;ndez
 *
 */
@Service
@Transactional
public class IngresarInvolucradoAudienciaServiceImpl implements
		IngresarInvolucradoAudienciaService {
	@Autowired
	InvolucradoAudienciaDAO involucradoAudienciaDAO;

	@Autowired
	OrganizacionAudienciaDAO organizacionAudienciaDAO;

	@Autowired
	OrganizacionDAO organizacionDAO;

	@Autowired
	InvolucradoDAO involucradoDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService
	 * #asociarInvolucradoAAudiencia(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asociarInvolucradoAAudiencia(Long involucradoId,
			Long audienciaId) throws NSJPNegocioException {

		boolean isPersonaFisica = true;
		if (involucradoId == null && audienciaId == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		/*
		 * Enable JC. Se agrega bloque de codigo para validar que el involucrado tenga una organizacion asociada,
		 * de ser asi, se trata de una personal moral y debe ser asociada a la audiencia por medio de la tabla
		 * OrganizacionAudiencia
		 */
		Involucrado inv = involucradoDAO.read(involucradoId);
		if(inv.getRelacionsForSujetoId() != null && inv.getRelacionsForSujetoId().size() > 0){
			Relacion[] relaciones = inv.getRelacionsForSujetoId().toArray(new Relacion[0]);
			for(Relacion relBD : relaciones){
				if(relBD != null && relBD.getCatRelacion() != null && relBD.getCatRelacion().getCatRelacionId() != null
						&& relBD.getCatRelacion().getCatRelacionId() == 59){//Relacion de Elemento-Organizacion
					isPersonaFisica = false;
					OrganizacionAudienciaId orgAudID = new OrganizacionAudienciaId(audienciaId, relBD.getElementoByComplementoId().getElementoId());
					if(organizacionAudienciaDAO.read(orgAudID) == null){
						OrganizacionAudiencia orgAud = new OrganizacionAudiencia();
						orgAud.setId(orgAudID);
						organizacionAudienciaDAO.create(orgAud);
					}
				}
			}

		}
		/*Enable JC. Termina bloque*/

		if(isPersonaFisica){
			// si no est&aacute; asociado a una audiencia, asociarlo
			InvolucradoAudienciaId identificador = new InvolucradoAudienciaId(
					audienciaId, involucradoId);
			if (involucradoAudienciaDAO.read(identificador) == null) {
				InvolucradoAudiencia involucradoAud = new InvolucradoAudiencia();
				involucradoAud.setId(identificador);
				involucradoAud.setEsPresente(true);
				involucradoAudienciaDAO.create(involucradoAud);
			}
			//En otro caso, el invoulcrado ya fue asociado
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService
	 * #asociarInvolucradoAAudiencia(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asociarOrganizacionAAudiencia(Long organizacionId,
			Long audienciaId) throws NSJPNegocioException {

		if ((organizacionId == null || organizacionId < 1L)
				&& (audienciaId == null || audienciaId < 1L)) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// si no est&aacute; asociada a una audiencia, asociarla
		OrganizacionAudienciaId identificador = new OrganizacionAudienciaId(
				audienciaId, organizacionId);
		if (organizacionAudienciaDAO.read(identificador) == null) {
			OrganizacionAudiencia organizacionAud = new OrganizacionAudiencia();
			organizacionAud.setId(identificador);
			organizacionAudienciaDAO.create(organizacionAud);
		}
		//En otro caso, la organizacion ya fue asociada
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService#consultarOrganizacionesAudiencia(mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public List<OrganizacionDTO> consultarOrganizacionesAudiencia(
			AudienciaDTO audiencia) throws NSJPNegocioException {

		if (audiencia == null || audiencia.getId() == null
				|| audiencia.getId() < 1){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Audiencia aud = new Audiencia();
		aud.setAudienciaId(audiencia.getId());
		List<Organizacion> organizaciones = organizacionDAO.consultarOrganizacionesAudiencia(aud);

		List<OrganizacionDTO> organizacionesDTO = new ArrayList<OrganizacionDTO>();
		for (Organizacion orgEntity : organizaciones){
			organizacionesDTO.add(OrganizacionTransformer.transformarOrganizacion(orgEntity));
		}
		return organizacionesDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService#obtenerInvolucradoByRelacion(mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO, mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO)
	 */
	@Override
	public InvolucradoDTO obtenerInvolucradoByRelacion(
			OrganizacionDTO organizacionDTO, CatRelacionDTO catRelacionDTO)
			throws NSJPNegocioException {

		if ((organizacionDTO == null || organizacionDTO.getElementoId() < 1L)
				&&(catRelacionDTO == null || catRelacionDTO.getCatRelacionId() < 1L)){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Organizacion organizacion = new Organizacion();
		organizacion.setElementoId(organizacionDTO.getElementoId());

		CatRelacion catRelacion = new CatRelacion();
		catRelacion.setCatRelacionId(catRelacionDTO.getCatRelacionId());

		Involucrado invl = organizacionDAO.obtenerInvolucradoByRelacion(organizacion, catRelacion);

		return InvolucradoTransformer.transformarInvolucradoBasico(invl);
	}

}
