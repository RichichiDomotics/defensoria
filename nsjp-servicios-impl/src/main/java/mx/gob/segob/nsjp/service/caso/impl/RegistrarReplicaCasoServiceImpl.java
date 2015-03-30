/**
 * Nombre del Programa : RegistrarReplicaCasoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
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
package mx.gob.segob.nsjp.service.caso.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.caso.CasoWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoWSDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.service.caso.RegistrarReplicaCasoService;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("registrarReplicaCasoService")
@Transactional
public class RegistrarReplicaCasoServiceImpl
        implements
            RegistrarReplicaCasoService {
    /**
     * Logger
     */
    private final static Logger logger = Logger
            .getLogger(RegistrarReplicaCasoServiceImpl.class);
    @Autowired
    private IngresarIndividuoService individuoService;
    @Autowired
    private CasoDAO casoDao;
    @Autowired
    private ExpedienteDAO expedienteDao;
    @Autowired
    private DelitoDAO delitoDao;
    @Autowired
    private DelitoPersonaDAO delitoPerDao;

    @Override
    public void registraReplicaCaso(CasoWSDTO casoWS)
            throws NSJPNegocioException {
        logger.info("Inicia registraReplicaCaso(...)");
        final Caso rowExistente = this.casoDao
                .obtenerCasoByNumeroGeneral(casoWS.getNumeroGeneralCaso());
        if (rowExistente != null) {
            logger.info("Caso existente!!!!");
            return;
        }
        final Caso casoNuevo = new Caso();
        casoNuevo.setNumeroGeneralCaso(casoWS.getNumeroGeneralCaso());
        casoNuevo.setFechaApertura(casoWS.getFechaApertura());

        casoDao.create(casoNuevo);
        
        final Expediente expNuevo = new Expediente();
        expNuevo.setCaso(casoNuevo);
        expNuevo.setFechaCreacion(new Date());
        final Long expNuevoId = this.expedienteDao.create(expNuevo);
        expNuevo.setExpedienteId(expNuevoId);

        final ExpedienteDTO expDto = new ExpedienteDTO(expNuevoId);
        InvolucradoDTO invo = null;
        Long idInvoVict = null;
        Long idInvoProbRes = null;
//        Long idInvo = null;
        
        for (InvolucradoWSDTO dtoInv : casoWS.getInvolucradosDTO()) {
            invo = new InvolucradoDTO();
            invo.setFolioElemento(dtoInv.getFolioElemento());
            logger.info("RegistrarReplicaCaso  FolioElementoID Involucrado ------ " + dtoInv.getFolioElemento());
            logger.info("RegistrarReplicaCaso  Valor Calidad Id del Involucrado --- " + dtoInv.getCalidad().getValorIdCalidad());
            invo.setExpedienteDTO(expDto);

            final CalidadDTO calidad = new CalidadDTO();
            calidad.setValorIdCalidad(new ValorDTO(dtoInv.getCalidad()
                    .getValorIdCalidad()));
            calidad.setDescripcionEstadoFisico(dtoInv.getCalidad()
                    .getDescripcionEstadoFisico());
            calidad.setCalidades(Calidades.getByValor(dtoInv.getCalidad()
                    .getValorIdCalidad()));
            invo.setCalidadDTO(calidad);
            invo.setTipoPersona(dtoInv.getTipoPersona());
            invo.setDesconocido(dtoInv.getDesconocido());
            // se agrega Condicion Invo.. 
            invo.setCondicion(dtoInv.getCondicion());
            logger.info("Condicion de Involucrado ---- " + dtoInv.getCondicion());
            
            if (dtoInv.getNombresDemograficos() != null) {
                for (NombreDemograficoWSDTO nomWs : dtoInv
                        .getNombresDemograficos()) {
                	logger.info("Nombre Demografico Creado Defensoria Edad ------ " + nomWs.getEdadAproximada());
                	logger.info("Nombre Demografico Creado Defensoria Sexo ------ " + nomWs.getSexo());
                    invo.addNombreDemografico(new NombreDemograficoDTO(null,
                            nomWs.getNombre(), nomWs.getApellidoPaterno(),
                            nomWs.getApellidoMaterno(), nomWs.getEdadAproximada(), nomWs.getSexo()));
                }
            } // nombres demograficos
            if (dtoInv.getAliasInvolucrado() != null) {
                for (String unAlias : dtoInv.getAliasInvolucrado()) {
                    invo.addAlias(new AliasInvolucradoDTO(unAlias));
                }
            } // alias

            logger.info("Calidades Defensoria antes de Condicion --- " + invo.getCalidadDTO().getCalidades().getValorId());
            
//            if (dtoInv.getCalidad().getValorIdCalidad() == Calidades.DENUNCIANTE.getValorId()){
            if (invo.getCalidadDTO().getCalidades().getValorId() == Calidades.DENUNCIANTE.getValorId()){
            	idInvoVict= this.individuoService.ingresarIndividuo(invo);
            	logger.info("RegistraRecplicaCaso ---- id del Involucrado Victima ---- " + idInvoVict);
            }
            else if (invo.getCalidadDTO().getCalidades().getValorId() == Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()){
            	idInvoProbRes = this.individuoService.ingresarIndividuo(invo);
            	logger.info("RegistraRecplicaCaso ---- id del Involucrado Probable Responsable ---- " + idInvoProbRes);
            }
//            else
//            	idInvo = this.individuoService.ingresarIndividuo(invo);
            	
        }
            
        for (InvolucradoWSDTO dtoInv : casoWS.getInvolucradosDTO()) {    
        	
            Delito delitoPojo;
            DelitoPersona delPerPojo;
            // guaradamode delito cometidos
            if (dtoInv.getCalidad().getValorIdCalidad() == 213){
//            if (dtoInv.getCalidad().getValorIdCalidad() == Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()){
	            if (dtoInv.getDelitosCometidos() != null) {
	            	logger.debug("Delitos a guardar para el involucrado :: " + idInvoProbRes);
	                for (DelitoWSDTO deli : dtoInv.getDelitosCometidos()) {
	                    delitoPojo = new Delito();
	                    delitoPojo.setCatDelito(new CatDelito(deli.getIdCatDelito()));
	                    logger.debug("deli.getIdCatDelito() :: " + deli.getIdCatDelito());
	                    delitoPojo.setEsProbable(Boolean.TRUE);
	                    delitoPojo.setExpediente(expNuevo);
	                    delitoPojo.setEsPrincipal(deli.isEsPrincipal());
	                    delitoPojo.setDelitoId(this.delitoDao.create(delitoPojo));
	                    delPerPojo = new DelitoPersona();
	                    delPerPojo.setDelito(delitoPojo);
	                    //Se agrega la Situacion del Imputado by Dorian
	                    delPerPojo.setSituacionImputadoId(deli.getSituacionImputado());
	                    logger.info("Situacion Imputado ID Defensoria ---- " + deli.getSituacionImputado());
	                    delPerPojo.setCatDelitoClasificacionId(deli.getCatDelitoClasificacion());
	                    logger.info("CatDelitoClasificacion Defensoria ----- " + deli.getCatDelitoClasificacion());
	                    delPerPojo.setCatDelitoModusId(deli.getCatDelitoModus());
	                    logger.info("CatDelitoModus ------ " + deli.getCatDelitoModus());
	                    delPerPojo.setCatDelitoCausaId(deli.getCatDelitoCausa());
	                    logger.info("CatDelitoCausa ---- " + deli.getCatDelitoCausa());
	                    
	                    delPerPojo.setVictima(new Involucrado(idInvoVict));
	                    delPerPojo.setProbableResponsable(new Involucrado(idInvoProbRes));
	                    delPerPojo.setEsActivo(Boolean.TRUE);
	                    this.delitoPerDao.create(delPerPojo);
	                }
	                
	            }
            
            } // delito
        }
     logger.info("Fin registraReplicaCaso(...)");
    }
}
