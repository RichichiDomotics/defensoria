/**
* Nombre del Programa : VehiculoTransformer.java                                    
* Autor                            : Tattva-IT                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 3 May 2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : tranformer de los objetos vehiculo                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.service.objeto.impl.transform;

import java.util.Date;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.model.Vehiculo;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.ws.cliente.avisodetencion.VehiculoWSDTO;

import org.apache.log4j.Logger;

/**
 * tranformer de los objetos vehiculo
 * 
 * @version 1.0
 * @author Tattva-IT
 *
 */
public class VehiculoTransformer {
	
	private final static Logger logger = Logger.getLogger(VehiculoTransformer.class);
	
	public static Vehiculo transformarVehiculo(VehiculoDTO vehiculoDto){
		Vehiculo vehiculo = new Vehiculo();
		vehiculo = (Vehiculo)ObjetoTransformer.transformarObjeto(vehiculoDto,vehiculo);
		
		if(vehiculoDto.getElementoId()!=null)
			vehiculo.setElementoId(vehiculoDto.getElementoId());
		
		if(vehiculoDto.getValorByPaisOrigenVal()!=null && vehiculoDto.getValorByPaisOrigenVal().getIdCampo()!=null)
			vehiculo.setValorByPaisOrigenVal(new Valor(vehiculoDto.getValorByPaisOrigenVal().getIdCampo()));
		
		if(vehiculoDto.getValorByColorVal()!=null && vehiculoDto.getValorByColorVal().getIdCampo()!=null)
			vehiculo.setValorByColorVal(new Valor(vehiculoDto.getValorByColorVal().getIdCampo()));
		
		if(vehiculoDto.getValorByMarcaVal()!=null && vehiculoDto.getValorByMarcaVal().getIdCampo()!=null)
			vehiculo.setValorByMarcaVal(new Valor(vehiculoDto.getValorByMarcaVal().getIdCampo()));

		if(vehiculoDto.getValorBySubmarcaVal()!=null && vehiculoDto.getValorBySubmarcaVal().getIdCampo()!=null)
			vehiculo.setValorBySubmarcaVal(new Valor(vehiculoDto.getValorBySubmarcaVal().getIdCampo()));

		if(vehiculoDto.getValorByTipoVehiculo()!=null && vehiculoDto.getValorByTipoVehiculo().getIdCampo()!=null)
			vehiculo.setValorByTipoVehiculo(new Valor(vehiculoDto.getValorByTipoVehiculo().getIdCampo()));
		
		if(vehiculoDto.getValorByCondicionFisicaVal()!=null && vehiculoDto.getValorByCondicionFisicaVal().getIdCampo()!=null)
			vehiculo.setValorByCondicionFisicaVal(new Valor(vehiculoDto.getValorByCondicionFisicaVal().getIdCampo()));
		vehiculo.setFolioElemento(vehiculoDto.getFolioElemento());
		vehiculo.setModelo(vehiculoDto.getModelo());
		vehiculo.setPlaca(vehiculoDto.getPlaca());
		vehiculo.setNoSerie(vehiculoDto.getNoSerie());
		vehiculo.setNoMotor(vehiculoDto.getNoMotor());
		vehiculo.setNrfv(vehiculoDto.getNrfv());
		vehiculo.setNoPuertas(vehiculoDto.getNoPuertas());
		vehiculo.setNoCilindros(vehiculoDto.getNoCilindros());
		vehiculo.setEsBlindado(vehiculoDto.getEsBlindado());
		vehiculo.setDescripcion(vehiculoDto.getDescripcion());
		vehiculo.setPropietario(vehiculoDto.getPropietario());
		vehiculo.setEsNumMotorAlterado(vehiculoDto.getEsNumMotorAlterado());
		vehiculo.setEsNumSerieAlterado(vehiculoDto.getEsNumSerieAlterado());
		
		
		if(vehiculoDto.getExpedienteDTO()!=null){
			vehiculo.setExpediente(new Expediente(vehiculoDto.getExpedienteDTO().getExpedienteId()));
			vehiculo.setFechaCreacionElemento(new Date());
		}	
		else
			 logger.debug("Con expediente en vehiculoDTO");
		
		//Permite guardar la foto de un elemento
		vehiculo.setArchivoDigital(ArchivoDigitalTransformer.transformarArchivoDigitalDTO(vehiculoDto.getFotoDelElemento()));		
		vehiculo.setEsActivo(vehiculoDto.getEsActivo());

		return vehiculo;
	}
	
	/**
	 * Tranforma un objeto del modelo del tipo <code>Embarcacion</code> en su 
	 * correspondiente DTO
	 * @param src Datos de origen
	 * @return DTO transformado
	 */
	public static VehiculoDTO transformarVehiculo(Vehiculo src){
		VehiculoDTO dest = null;
		if(src != null){
			dest = new VehiculoDTO();
			
			if(src.getValorByPaisOrigenVal() != null){
				dest.setValorByPaisOrigenVal(new ValorDTO(src.getValorByPaisOrigenVal().getValorId(),src.getValorByPaisOrigenVal().getValor()));
			}
			
			if(src.getValorByColorVal() != null){
				dest.setValorByColorVal(new ValorDTO(src.getValorByColorVal().getValorId(),src.getValorByColorVal().getValor()));
			}

			if(src.getValorByMarcaVal() != null){
				dest.setValorByMarcaVal(new ValorDTO(src.getValorByMarcaVal().getValorId(),src.getValorByMarcaVal().getValor()));
			}

			if(src.getValorBySubmarcaVal() != null){
				dest.setValorBySubmarcaVal(new ValorDTO(src.getValorBySubmarcaVal().getValorId(),src.getValorBySubmarcaVal().getValor()));
			}
			if(src.getValorByTipoVehiculo() != null){
				dest.setValorByTipoVehiculo(new ValorDTO(src.getValorByTipoVehiculo().getValorId(),src.getValorByTipoVehiculo().getValor()));
			}
			dest.setModelo(src.getModelo());
			dest.setPlaca(src.getPlaca());
			dest.setNoSerie(src.getNoSerie());
			dest.setNoMotor(src.getNoMotor());
			dest.setNrfv(src.getNrfv());
			dest.setNoPuertas(src.getNoPuertas());
			dest.setNoCilindros(src.getNoCilindros());
			dest.setPropietario(src.getPropietario());
			dest.setEsBlindado(src.getEsBlindado());
            dest.setDescripcion(src.getDescripcion());
            dest.setEsActivo(src.getEsActivo());
            dest.setEsNumMotorAlterado(src.getEsNumMotorAlterado());
            dest.setEsNumSerieAlterado(src.getEsNumSerieAlterado());
		}
		return dest;
	}
	
	public static VehiculoWSDTO transformarVehiculoDTO(VehiculoDTO src){
		VehiculoWSDTO dest = null;
		if(src != null){
			dest = new VehiculoWSDTO();
			
			if(src.getValorByPaisOrigenVal() != null){
				dest.setValorByPaisOrigenVal( src.getValorByPaisOrigenVal().getIdCampo() );
			}
			
			if(src.getValorByColorVal() != null){
				dest.setValorByColorVal(src.getValorByColorVal().getIdCampo());
			}

			if(src.getValorByMarcaVal() != null){
				dest.setValorByMarcaVal(src.getValorByMarcaVal().getIdCampo());
			}

			if(src.getValorBySubmarcaVal() != null){
				dest.setValorBySubmarcaVal(src.getValorBySubmarcaVal().getIdCampo());
			}
			if(src.getValorByTipoVehiculo() != null){
				dest.setValorByTipoVehiculo(src.getValorByTipoVehiculo().getIdCampo());
			}
			dest.setModelo(src.getModelo());
			dest.setPlaca(src.getPlaca()); 
			dest.setNoSerie(src.getNoSerie());
			dest.setNoMotor(src.getNoMotor());
			dest.setNrfv(src.getNrfv());
			dest.setNoPuertas(src.getNoPuertas());
			dest.setNoCilindros(src.getNoCilindros());
			dest.setPropietario(src.getPropietario());
			dest.setEsBlindado(src.getEsBlindado());
            dest.setDescripcion(src.getDescripcion());
            dest.setEsActivo(src.getEsActivo());
            dest.setEsNumMotorAlterado(src.getEsNumMotorAlterado());
            dest.setEsNumSerieAlterado(src.getEsNumSerieAlterado());            
		}
		return dest;
	}
	
	
	
}
