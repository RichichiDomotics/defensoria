/**
 * Nombre del Programa : ObjetoTransformer.java
 * Autor                            : Emigdio Hernández
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13/06/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de transformación para un Objeto del Expediente.
 * Esta clase tranforma un Objeto a su DTO correspondiente considerando el tipo de objeto
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
package mx.gob.segob.nsjp.service.objeto.impl.transform;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.elemento.Elementos;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.InmuebleDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.model.Aeronave;
import mx.gob.segob.nsjp.model.Almacen;
import mx.gob.segob.nsjp.model.Animal;
import mx.gob.segob.nsjp.model.Arma;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.DocumentoOficial;
import mx.gob.segob.nsjp.model.Embarcacion;
import mx.gob.segob.nsjp.model.EquipoComputo;
import mx.gob.segob.nsjp.model.Explosivo;
import mx.gob.segob.nsjp.model.Inmueble;
import mx.gob.segob.nsjp.model.Joya;
import mx.gob.segob.nsjp.model.Numerario;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.model.ObraArte;
import mx.gob.segob.nsjp.model.Sustancia;
import mx.gob.segob.nsjp.model.Telefonia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.model.Vegetal;
import mx.gob.segob.nsjp.model.Vehiculo;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.ws.cliente.avisodetencion.ObjetoWSDTO;

/**
 * Clase de transformación para un Objeto del Expediente. Esta clase tranforma
 * un Objeto a su DTO correspondiente considerando el tipo de objeto
 * 
 * @version 1.0
 * @author Emigdio Hernández
 * 
 */
public class ObjetoTransformer {

    /**
     * Transforma un objeto del tipo
     * <code>Objeto</objeto> a su DTO correspondiente pero teniendo en cuenta
     * el tipo de objeto, por ejemplo, si el tipo de objeto es un arma se realiza un casto y luego otra
     * transoformación y se retorna como un <code>ObjetoDTO</code>
     * 
     * @param src
     *            Objeto fuente
     * @return Objeto transformado
     */
    public static ObjetoDTO transformarObjeto(Objeto src) {
        ObjetoDTO destino = new ObjetoDTO();
        
        if(src.getAlmacen() != null){
        	destino.setAlmacen(AlmacenTransformer.transformarAlmacen(src.getAlmacen()));
        }
        if (src != null) {
        	if (src instanceof Vehiculo) {
                destino = VehiculoTransformer.transformarVehiculo((Vehiculo) src);
            } else if (src instanceof EquipoComputo) {
            	destino = EquipoComputoTransformer.transformarEquipoComputo((EquipoComputo) src);
            } else if (src instanceof Telefonia) {
                destino = TelefoniaTransformer.transformarTelefonia((Telefonia) src);
            } else if (src instanceof Arma) {
            	destino = ArmaTransformer.transformarArma((Arma) src);
            } else if (src instanceof Explosivo) {
            	destino = ExplosivoTransformer.transformarExplosivo((Explosivo) src);
            } else if (src instanceof Aeronave) {
            	destino = AeronaveTransformer.transformarAeronave((Aeronave) src);
            } else if (src instanceof Animal) {
            	destino = AnimalTransformer.transformarAnimal((Animal) src);
            } else if (src instanceof DocumentoOficial) {
            	destino = DocumentoOficialTransformer.transformarDocumentoOficial((DocumentoOficial) src);
            } else if (src instanceof Embarcacion) {
            	destino = EmbarcacionTransformer.transformarEmbarcacion((Embarcacion) src);
            } else if (src instanceof Inmueble) {
            	destino = InmuebleTransformer.transformarInmueble((Inmueble) src);
            } else if (src instanceof Joya) {
            	destino = JoyaTransformer.transformarJoya((Joya) src);
            } else if (src instanceof Numerario) {
            	destino = NumerarioTransformer.transformarNumerario((Numerario) src);
            } else if (src instanceof ObraArte) {
                destino = ObraArteTransformer.transformarObraArte((ObraArte) src);
            } else if (src instanceof Sustancia) {
                destino = SustanciaTransformer.transformarSustancia((Sustancia) src);
            } else if (src instanceof Vegetal) {
                destino = VegetalTransformer.transformarVegetal((Vegetal) src);
            } else {
            	destino = new ObjetoDTO();
            }
            destino.setDescripcion(src.getDescripcion());

            if(src.getValorByCondicionFisicaVal()!=null){
            	destino.setValorByCondicionFisicaVal(new ValorDTO(src.getValorByCondicionFisicaVal().getValorId(), src.getValorByCondicionFisicaVal().getValor()));
            }
            
            destino.setElementoId(src.getElementoId());
            destino.setFolioElemento(src.getFolioElemento());
            destino.setTipoObjeto(Objetos.getByValor(src.getValorByTipoObjetoVal().getValorId()));
            destino.setFechaCreacionElemento(src.getFechaCreacionElemento());
            if (src.getCalidad() != null && src.getCalidad().getTipoCalidad() != null) {
                Calidad calidad = src.getCalidad();
                CalidadDTO calidadDTO = new CalidadDTO();
                calidadDTO.setCalidadId(calidad.getCalidadId());
                calidadDTO.setValorIdCalidad(new ValorDTO(calidad.getTipoCalidad().getValorId(), calidad.getTipoCalidad().getValor()));
                calidadDTO.setDescripcionEstadoFisico(calidad.getDescripcionEstadoFisico());
                calidadDTO.setCalidades(Calidades.getByValor(calidad.getTipoCalidad().getValorId()));
                destino.setCalidadDTO(calidadDTO);
            }
            
            // Jacob Transformamos el almacen en caso que src lo tenga.
            if (src.getAlmacen() != null) {
                destino.setAlmacen(AlmacenTransformer.transformarAlmacen(src.getAlmacen()));
            }
            if(src.getArchivoDigital() != null){
        		destino.setFotoDelElemento(ArchivoDigitalTransformer.transformarArchivoDigital(src.getArchivoDigital()));
            }
            if(src.getInstitucionPresenta()!= null){
            	destino.setInstitucionPresenta( ConfInstitucionTransformer.transformarInstitucion(src.getInstitucionPresenta()));
            }
            if(src.getNombreObjeto() != null){
                destino.setNombreObjeto(src.getNombreObjeto());
            }
            destino.setEsActivo(src.getEsActivo());
            if(src.getRelacionHechoVal() != null){
            	destino.setRelacionHechoVal(new ValorDTO(src.getRelacionHechoVal().getValorId(),src.getRelacionHechoVal().getValor()));
    		}
        }
        return destino;
    }

    public static Objeto transformarObjeto(ObjetoDTO objeto) {

        Objeto object = new Objeto();
        object.setElementoId(objeto.getElementoId());

        object.setDescripcion(objeto.getDescripcion());
        if (objeto.getValorByCondicionFisicaVal() != null) {
            object.setValorByCondicionFisicaVal(new Valor(objeto
                    .getValorByCondicionFisicaVal().getIdCampo()));
        }
        if (objeto.getTipoObjeto() != null) {
            object.setValorByTipoObjetoVal(new Valor(objeto.getTipoObjeto()
                    .getValorId()));
        }
        object.setTipoElemento(new Valor(Elementos.OBJETO.getValorId()));
        object.setFechaCreacionElemento(objeto.getFechaCreacionElemento());
        if(objeto.getExpedienteDTO()!=null)
        object.setExpediente(ExpedienteTransformer.transformarExpediente(objeto
                .getExpedienteDTO()));
        object.setEsActivo(objeto.getEsActivo());
        
        if(objeto.getNombreObjeto()!=null)
        	object.setNombreObjeto(objeto.getNombreObjeto());
        
        if(objeto.getRelacionHechoVal() != null && objeto.getRelacionHechoVal().getIdCampo() != null){
        	object.setRelacionHechoVal(new Valor(objeto.getRelacionHechoVal().getIdCampo()));
        }
        	
        return object;
    }
 
    /**
     * 
     * @param input
     * @return
     */
    public static ObjetoDTO tranformarComoEvidenciaBasico(Objeto input) {
        ObjetoDTO resp 				= new ObjetoDTO();
        EvidenciaDTO evDto 			= new EvidenciaDTO();
        CadenaDeCustodiaDTO cadDto 	= new CadenaDeCustodiaDTO();
        if( input.getEvidencia() != null ){
            cadDto.setFolio(input.getEvidencia().getCadenaDeCustodia().getFolio());	
            evDto.setNumeroEvidencia(input.getEvidencia().getNumeroEvidencia());
            evDto.setCadenaDeCustodia(cadDto);
            resp.setEvidencia(evDto);
        }
        if(input.getNombreObjeto()!=null){
        	resp.setNombreObjeto(input.getNombreObjeto());
        }
        
        resp.setElementoId(input.getElementoId());
        if(input.getAlmacen()!=null)
        	resp.setAlmacen(AlmacenTransformer.transformarAlmacen(input.getAlmacen()));
        
        if(input.getRelacionHechoVal() != null){
        	resp.setRelacionHechoVal(new ValorDTO(input.getRelacionHechoVal().getValorId(),input.getRelacionHechoVal().getValor()));
        }
        return resp;
    }
    
    public static Objeto transformarObjeto(ObjetoDTO objetoDTO, Objeto object) {

        object.setElementoId(objetoDTO.getElementoId());

        object.setDescripcion(objetoDTO.getDescripcion());
        if (objetoDTO.getValorByCondicionFisicaVal() != null) {
            object.setValorByCondicionFisicaVal(new Valor(objetoDTO
                    .getValorByCondicionFisicaVal().getIdCampo()));
        }
        if (objetoDTO.getTipoObjeto() != null) {
            object.setValorByTipoObjetoVal(new Valor(objetoDTO.getTipoObjeto().getValorId()));
        }
        object.setTipoElemento(new Valor(Elementos.OBJETO.getValorId()));
        object.setFechaCreacionElemento(objetoDTO.getFechaCreacionElemento());
        if(objetoDTO.getExpedienteDTO()!=null)
        object.setExpediente(ExpedienteTransformer.transformarExpediente(objetoDTO.getExpedienteDTO()));
        object.setEsActivo(objetoDTO.getEsActivo());
        
        if(objetoDTO.getNombreObjeto()!=null)
        	object.setNombreObjeto(objetoDTO.getNombreObjeto());
        
        if(objetoDTO.getRelacionHechoVal() != null && objetoDTO.getRelacionHechoVal().getIdCampo() != null){
        	object.setRelacionHechoVal(new Valor(objetoDTO.getRelacionHechoVal().getIdCampo()));
        }
        	
        return object;
    }
    
    public static ObjetoWSDTO transformarObjetoWSDTO(ObjetoDTO objeto) {
    	ObjetoWSDTO wsdto 		= new ObjetoWSDTO();
    	if(objeto.getDescripcion() != null ){
    		wsdto.setDescripcion(objeto.getDescripcion());
    	}
    	if(objeto.getAlmacen() != null ){        	
        	wsdto.setiIdentificadorAlmacen(objeto.getAlmacen().getIdentificadorAlmacen());
    	}
    	if(objeto.getValorByCondicionFisicaVal() != null ){
        	wsdto.setValorByCondicionFisicaVal(objeto.getValorByCondicionFisicaVal().getIdCampo());
    	}
    	if(objeto.getInstitucionPresenta() != null ){
        	wsdto.setConfInstitucionId(objeto.getInstitucionPresenta().getConfInstitucionId());
    	}
    	if(objeto.getNombreObjeto() != null ){
        	wsdto.setNombreObjeto(objeto.getNombreObjeto());
    	}
    	if(objeto.getRelacionHechoVal() != null ){
    		wsdto.setRelacionHechoVal(objeto.getRelacionHechoVal().getIdCampo());
    	}
    	if(objeto.getNombreObjeto() != null ){
        	wsdto.setNombreObjeto(objeto.getNombreObjeto());
    	}
    	if(objeto.getIdTipoObjeto() != null ){
        	wsdto.setIdTipoObjeto(objeto.getIdTipoObjeto());
    	}

    	if(objeto.getTipoObjeto() != null ){
        	///Transformacion Dependiendo de objeto
    		if (objeto instanceof VehiculoDTO) {
    			wsdto = VehiculoTransformer.transformarVehiculoDTO((VehiculoDTO) objeto);
            } else if (objeto instanceof EquipoComputoDTO) {
            	wsdto = EquipoComputoTransformer.transformarEquipoComputoDTO((EquipoComputoDTO) objeto);
            } else if (objeto instanceof TelefoniaDTO) {
            	wsdto = TelefoniaTransformer.transformarTelefoniaDTO((TelefoniaDTO) objeto);
            } else if (objeto instanceof ArmaDTO) {
            	wsdto = ArmaTransformer.transformarArmaDTO((ArmaDTO) objeto);
            } else if (objeto instanceof ExplosivoDTO) {
            	wsdto = ExplosivoTransformer.transformarExplosivoDTO((ExplosivoDTO) objeto);
            } else if (objeto instanceof AeronaveDTO) {
            	wsdto = AeronaveTransformer.transformarAeronaveDTO((AeronaveDTO) objeto);
            } else if (objeto instanceof AnimalDTO) {
            	wsdto = AnimalTransformer.transformarAnimalDTO((AnimalDTO) objeto);
            } else if (objeto instanceof DocumentoOficialDTO) {
            	wsdto = DocumentoOficialTransformer.transformarDocumentoOficialDTO((DocumentoOficialDTO) objeto);
            } else if (objeto instanceof EmbarcacionDTO) {
            	wsdto = EmbarcacionTransformer.transformarEmbarcacionDTO((EmbarcacionDTO) objeto);
            } else if (objeto instanceof InmuebleDTO) {
            	wsdto = InmuebleTransformer.transformarInmuebleDTO((InmuebleDTO) objeto);
            } else if (objeto instanceof JoyaDTO) {
            	wsdto = JoyaTransformer.transformarJoyaDTO((JoyaDTO) objeto);
            } else if (objeto instanceof NumerarioDTO) {
            	wsdto = NumerarioTransformer.transformarNumerarioDTO((NumerarioDTO) objeto);
            } else if (objeto instanceof ObraArteDTO) {
            	wsdto = ObraArteTransformer.transformarObraArteDTO((ObraArteDTO) objeto);
            } else if (objeto instanceof SustanciaDTO) {
            	wsdto = SustanciaTransformer.transformarSustanciaDTO((SustanciaDTO) objeto);
            } else if (objeto instanceof VegetalDTO) {
            	wsdto = VegetalTransformer.transformarVegetalDTO((VegetalDTO) objeto);
            }
    		
    	}
        return wsdto;
    }
    
    public static Objeto transformarObjetoDAO(mx.gob.segob.nsjp.dto.expediente.ObjetoWSDTO objetoWSDTO) {
    	Objeto objeto 		= new Objeto();
    	if(objetoWSDTO.getDescripcion() != null ){
    		objeto.setDescripcion(objetoWSDTO.getDescripcion());
    	}
    	if(objetoWSDTO.getAlmacen() != null ){        	
    		objeto.setAlmacen(new Almacen(objetoWSDTO.getiIdentificadorAlmacen()));
    	}
    	if(objetoWSDTO.getValorByCondicionFisicaVal() != null ){
    		objeto.setValorByCondicionFisicaVal(new Valor(objetoWSDTO.getValorByCondicionFisicaVal() ));
    	}
//    	if(objetoWSDTO.getInstitucion() != null ){
//    		objeto.setConfInstitucionId(objeto.getInstitucionPresenta().getConfInstitucionId());
//    	}
    	if(objetoWSDTO.getNombreObjeto() != null ){
    		objeto.setNombreObjeto(objetoWSDTO.getNombreObjeto());
    	}
    	if(objetoWSDTO.getRelacionHechoVal() != null ){
    		objeto.setRelacionHechoVal(new Valor(objetoWSDTO.getRelacionHechoVal()));
    	}
    	if(objetoWSDTO.getNombreObjeto() != null ){
    		objeto.setNombreObjeto(objetoWSDTO.getNombreObjeto());
    	}
//    	if(objetoWSDTO.getIdTipoObjeto() != null ){
//    		objeto.setIdTipoObjeto(objetoWSDTO.getIdTipoObjeto());
//    	}

//    	if(objetoWSDTO.getTipoObjeto() != null ){
        	///Transformacion Dependiendo de objeto
//    		if (objetoWSDTO instanceof VehiculoDTO) {
//    			objeto = VehiculoTransformer.transformarVehiculoDTO((VehiculoDTO) objeto);
//            } else if (objetoWSDTO instanceof EquipoComputoDTO) {
//            	objeto = EquipoComputoTransformer.transformarEquipoComputoDTO((EquipoComputoDTO) objeto);
//            } else if (objetoWSDTO instanceof TelefoniaDTO) {
//            	objeto = TelefoniaTransformer.transformarTelefoniaDTO((TelefoniaDTO) objeto);
//            } else if (objetoWSDTO instanceof ArmaDTO) {
//            	objeto = ArmaTransformer.transformarArmaDTO((ArmaDTO) objeto);
//            } else if (objetoWSDTO instanceof ExplosivoDTO) {
//            	objeto = ExplosivoTransformer.transformarExplosivoDTO((ExplosivoDTO) objeto);
//            } else if (objetoWSDTO instanceof AeronaveDTO) {
//            	objeto = AeronaveTransformer.transformarAeronaveDTO((AeronaveDTO) objeto);
//            } else if (objetoWSDTO instanceof AnimalDTO) {
//            	objeto = AnimalTransformer.transformarAnimalDTO((AnimalDTO) objeto);
//            } else if (objetoWSDTO instanceof DocumentoOficialDTO) {
//            	objeto = DocumentoOficialTransformer.transformarDocumentoOficialDTO((DocumentoOficialDTO) objeto);
//            } else if (objetoWSDTO instanceof EmbarcacionDTO) {
//            	objeto = EmbarcacionTransformer.transformarEmbarcacionDTO((EmbarcacionDTO) objeto);
//            } else if (objetoWSDTO instanceof InmuebleDTO) {
//            	objeto = InmuebleTransformer.transformarInmuebleDTO((InmuebleDTO) objeto);
//            } else if (objetoWSDTO instanceof JoyaDTO) {
//            	objeto = JoyaTransformer.transformarJoyaDTO((JoyaDTO) objeto);
//            } else if (objetoWSDTO instanceof NumerarioDTO) {
//            	objeto = NumerarioTransformer.transformarNumerarioDTO((NumerarioDTO) objeto);
//            } else if (objetoWSDTO instanceof ObraArteDTO) {
//            	objeto = ObraArteTransformer.transformarObraArteDTO((ObraArteDTO) objeto);
//            } else if (objetoWSDTO instanceof SustanciaDTO) {
//            	objeto = SustanciaTransformer.transformarSustanciaDTO((SustanciaDTO) objeto);
//            } else if (objetoWSDTO instanceof VegetalDTO) {
//            	objeto = VegetalTransformer.transformarVegetalDTO((VegetalDTO) objeto);
//            }
    		
//    	}
        return objeto;
    }
    
    

}
