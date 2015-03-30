/**
* Nombre del Programa : MandamientoJudicialTransformer.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/08/2011
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
package mx.gob.segob.nsjp.service.documento.impl.tranform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.ResolutivoTransformer;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatalogoTransformer;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;
import mx.gob.segob.nsjp.service.medida.impl.transform.MedidaTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;

/**
 * Clase de transformaciones para el mandamiento judicial
 * @version 1.0
 * @author Emigdio
 *
 */
public class MandamientoJudicialTransformer {
	/**
	 * Tranforma un objeto del tipo MandamientoDTO en su equivalente
	 * objeto del modelo de entidades de BD
	 * @param src
	 * @return
	 */
	public static Mandamiento transformarMandamientoDTO(MandamientoDTO src){
		Mandamiento dest = null;
		if(src != null){
			dest = new Mandamiento();
			
			dest.setFechaInicial(src.getFechaInicial());
			dest.setFechaFinal(src.getFechaFinal());
			
			dest.setDocumentoId(src.getDocumentoId());
			dest.setForma(FormaTransformer.transformarFormaDTO(src.getFormaDTO()));
			dest.setNombreDocumento(src.getNombreDocumento());
			dest.setTipoDocumento(CatalogoTransformer.transformValor(src.getTipoDocumentoDTO()));
			dest.setFechaCreacion(src.getFechaCreacion());
			dest.setEstatus(CatalogoTransformer.transformValor(src.getEstatus()));
			dest.setTipoMandamiento(CatalogoTransformer.transformValor(src.getTipoMandamiento()));
			dest.setTipoSentencia(CatalogoTransformer.transformValor(src.getTipoSentencia()));
			dest.setTextoParcial(src.getTextoParcial());
			if(src.getResolutivo()!=null){
				dest.setResolutivo(new Resolutivo());
				dest.getResolutivo().setResolutivoId(src.getResolutivo().getResolutivoId());
			}
			dest.setFechaEjecuacion(src.getFechaEjecuacion());

		}
		return dest;
	}
	/**
	 * Transforma un objeto del model de entidades de BD a su respectivo equivalente
	 * DTO
	 * @param src Datos fuente
	 * @return DTO equivalente
	 */
	public static MandamientoDTO transformarMandamiento(Mandamiento src){
		MandamientoDTO dest = null;
		if(src!=null){
			
			dest = new MandamientoDTO();
			dest.setDocumentoId(src.getDocumentoId());
			dest.setFormaDTO(FormaTransformer.transformarForma(src.getForma()));
			dest.setNombreDocumento(src.getNombreDocumento());
			dest.setFolioDocumento(src.getFolioDocumento());
			dest.setFechaCreacion(src.getFechaCreacion());
			dest.setEstatus(CatalogoTransformer.transformValor(src.getEstatus()));
			dest.setArchivoDigital(ArchivoDigitalTransformer.transformarArchivoDigitalBasico(src.getArchivoDigital()));
			if(src.getResolutivo() != null){
				dest.setResolutivo(ResolutivoTransformer.transformarResolutivo(src.getResolutivo()));
			}
			dest.setTipoMandamiento(CatalogoTransformer.transformValor(src.getTipoMandamiento()));
			dest.setTipoSentencia(CatalogoTransformer.transformValor(src.getTipoSentencia()));
			dest.setMedida(MedidaTransformer.transformarMedida(src.getMedida()));
			dest.setFechaEjecuacion(src.getFechaEjecuacion());
			dest.setFechaInicial(src.getFechaInicial());
			dest.setFechaFinal(src.getFechaFinal());
			dest.setDescripcion(src.getDescripcion());

			//Se consulta unicamente el id y el nombre completo del Involucrado
			Involucrado involucrado = src.getInvolucrado();
			InvolucradoDTO involucradoDto = new InvolucradoDTO();
			involucradoDto.setElementoId(involucrado.getElementoId());
			
			CalidadDTO calidadDto = new CalidadDTO();
			Calidad calidad = new Calidad();

			if (involucrado.getCalidad() != null
					&& involucrado.getCalidad().getTipoCalidad() != null) {
				calidad = involucrado.getCalidad();
				calidadDto.setCalidadId(calidad.getCalidadId());
				calidadDto.setValorIdCalidad(new ValorDTO(calidad.getTipoCalidad()
						.getValorId(), calidad.getTipoCalidad().getValor()));
				calidadDto.setCalidades(Calidades.getByValor(calidad.getTipoCalidad()
						.getValorId()));
				calidadDto.setDescripcionEstadoFisico(calidad
						.getDescripcionEstadoFisico());
			}
			involucradoDto.setCalidadDTO(calidadDto);			
			
			
			if (involucrado.getNombreDemograficos() != null
					&& !involucrado.getNombreDemograficos().isEmpty()) {
				involucradoDto
						.setNombresDemograficoDTO(new ArrayList<NombreDemograficoDTO>());
				for (NombreDemografico nombre : involucrado.getNombreDemograficos()) {
					NombreDemograficoDTO nombreDTO = NombreDemograficoTransformer
							.transformarNombreDemografico(nombre);
					involucradoDto.getNombresDemograficoDTO().add(nombreDTO);
				}

			}			
			dest.setInvolucrado(involucradoDto);			
			
		}
		
		return dest;
	}
	
	public static List<MandamientoDTO> transformarMandamientos(List<Mandamiento> src){
		List<MandamientoDTO> loMandamientos = new ArrayList<MandamientoDTO>();
		if(src == null)
			return null;
		else{
			for (Mandamiento loMandamiento : src) {
				loMandamientos.add(transformarMandamiento(loMandamiento));
			}
			return loMandamientos;
		}
	}


}
