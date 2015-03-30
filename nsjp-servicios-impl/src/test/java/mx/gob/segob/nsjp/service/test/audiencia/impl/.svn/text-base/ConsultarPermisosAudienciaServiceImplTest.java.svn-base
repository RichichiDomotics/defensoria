/**
 * Nombre del Programa : ConsultarPermisoAudienciaServiceImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Jun 2011
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
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.service.audiencia.ConsultarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class ConsultarPermisosAudienciaServiceImplTest
        extends
        	BaseTestServicios<ConsultarPermisosAudienciaService> {

    public void testBuscarAudiencias() {
        try {
            List<PermisoAudienciaDTO> resp = service.buscarPermisosAudienciaPorEstado(1L,null,null,null);
            if(resp!=null){
        		for(PermisoAudienciaDTO PA : resp){
        			logger.info("---------------------------------------------------------");
        			logger.info("PA.getPermisoAudiencia_id():"+PA.getPermisoAudienciaId());
        			logger.info("PA.getEsExterno():"+PA.getEsExterno());
        			logger.info("PA.getEstadoPermiso().getEstatus():"+PA.getCatEstadoPermiso().getEstatus());
        			logger.info("PA.getEsVigente():"+PA.getEsVigente());
        			logger.info("PA.getFechaAsignacion():"+PA.getFechaAsignacion());        		
        			logger.info("PA.getFechaSolicitud():"+PA.getFechaSolicitud());
        			
        			if(PA.getUsuario()!=null){
        				logger.info("PA.getUsuario().getIdUsuario():"+PA.getUsuario().getIdUsuario());
        			}
        			else{
        				logger.info("Sin usuario interno");
        			}
        		
        			if(PA.getUsuarioAsignador()!=null){
        				logger.info("PA.getUsuarioAsignador().getIdUsuario():"+PA.getUsuarioAsignador().getIdUsuario());
    				}
    				else{
    					logger.info("Sin usuario asignador");
    				}

            		if(PA.getFuncionarioExterno()!=null){
            			logger.info("PA.getFuncionarioExterno().getApellidoMaterno():"+PA.getFuncionarioExterno().getApellidoMaterno());
            			logger.info("PA.getFuncionarioExterno().getApellidoPaterno():"+PA.getFuncionarioExterno().getApellidoPaterno());
            			logger.info("PA.getFuncionarioExterno().getNombre():"+PA.getFuncionarioExterno().getNombre());
            			
            			if(PA.getFuncionarioExterno().getConfInstitucionDTO()!=null){
            				logger.info("PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst():" + PA.getFuncionarioExterno().getConfInstitucionDTO().getNombreInst());
            			}
            			else{
            				logger.info("Sin confInstitucion");
            			}
   					}	
        			else{
        				logger.info("Sin usuario externo");
        			}

        			logger.info("---------------------------------------------------------");
        			logger.info("PA.getAudiencia().getId():"+PA.getAudiencia().getId());
        		}
            }
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }
 }
