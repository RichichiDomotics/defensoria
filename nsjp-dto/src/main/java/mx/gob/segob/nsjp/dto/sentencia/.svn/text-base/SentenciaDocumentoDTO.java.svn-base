/**
 * Nombre del Programa : SentenciaDocumentoDTO.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/07/2012
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
package mx.gob.segob.nsjp.dto.sentencia;

import java.text.SimpleDateFormat;
import java.util.List;

import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatCursoDTO;
import mx.gob.segob.nsjp.dto.programas.CatProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatTrabajoDTO;
import mx.gob.segob.nsjp.dto.programas.CursoDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.TrabajoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
public class SentenciaDocumentoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3086295204165985596L;

	private SentenciaDTO sentenciaDTO;

	/**
	 * M&eacute;todo de acceso al campo sentenciaDTO.
	 * 
	 * @return El valor del campo sentenciaDTO
	 */
	public SentenciaDTO getSentenciaDTO() {
		return sentenciaDTO;
	}

	/**
	 * Asigna el valor al campo sentenciaDTO.
	 * 
	 * @param sentenciaDTO
	 *            el valor sentenciaDTO a asignar
	 */
	public void setSentenciaDTO(SentenciaDTO sentenciaDTO) {
		this.sentenciaDTO = sentenciaDTO;
	}

	public String getLstProgramasDTO() {
		StringBuffer resultado = new StringBuffer();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (this.sentenciaDTO != null) {
			if (this.sentenciaDTO.getAsignacionProgramas() != null) {
				for (AsignacionProgramaDTO asignacionProgramaDTO : this.sentenciaDTO
						.getAsignacionProgramas()) {
					if (asignacionProgramaDTO.getProgramaDTO() != null) {
						ProgramaDTO programaDTO = asignacionProgramaDTO
								.getProgramaDTO();
						CatProgramaDTO catProgramaDTO = programaDTO
								.getCatProgramaDTO();
						List<CursoDTO> lstCursos = programaDTO.getCursos();
						List<TrabajoDTO> lstTrabajos = programaDTO
								.getTrabajos();
						resultado.append("<fieldset>");
						resultado.append("<table border='0' width='100%'>");
						resultado.append("<tr>");
						resultado.append("<td align='right'>Nombre:</td>");
						resultado.append("<td align='left' colspan='3'>");
						resultado.append(catProgramaDTO.getNombre() != null ? catProgramaDTO.getNombre():"&nbsp;");
						resultado.append("</td>");
						resultado.append("</tr>");
						resultado.append("<tr>");
						resultado.append("<td align='right'>Tipo de Programa:</td>");
						resultado.append("<td align='left' colspan='3'>");
						if(catProgramaDTO.getCatTipoProgramaDTO()!= null) {
							resultado.append(catProgramaDTO.getCatTipoProgramaDTO().getDescripcion()!= null ? catProgramaDTO.getCatTipoProgramaDTO().getDescripcion():"&nbsp;");
						} else {
							resultado.append("&nbsp;");
						}
						resultado.append("</td>");
						resultado.append("</tr>");
						resultado.append("<tr>");
						resultado.append("<td align='right'>Descripci&oacute;n:</td>");
						resultado.append("<td align='left' colspan='3'>");
						resultado.append(catProgramaDTO.getDescripcion()!= null ? catProgramaDTO.getDescripcion():"&nbsp;");
						resultado.append("</td>");
						resultado.append("</tr>");
						resultado.append("<tr>");
						resultado.append("<td align='right'>Total De Puntos:</td>");
						resultado.append("<td align='left' colspan='3'>");
						if(catProgramaDTO.getTotalPuntosCursos() != null){
							resultado.append(catProgramaDTO.getTotalPuntosPrograma()!= null ? catProgramaDTO.getTotalPuntosPrograma():"&nbsp;");
						} else {
							resultado.append("&nbsp;");
						}
						resultado.append("</td>");
						resultado.append("</tr>");
						resultado.append("<tr>");
						resultado.append("<td align='right'>Fecha de Inicio:</td>");
						resultado.append("<td align='left'>");
						resultado.append(programaDTO.getDfechaIngreso() != null ? simpleDateFormat.format(programaDTO.getDfechaIngreso()): "&nbsp;");
						resultado.append("</td>");
						resultado.append("<td align='right'>Fecha de Termino:</td>");
						resultado.append("<td align='left'>");
						resultado.append(programaDTO.getDfechaTermino() != null ? simpleDateFormat.format(programaDTO.getDfechaTermino()): "&nbsp;");
						resultado.append("</td>");
						resultado.append("</tr>");
						resultado.append("</table>");
						resultado.append("</fieldset>");
						if (lstCursos != null && !lstCursos.isEmpty()) {
							resultado.append("<fieldset>");
							resultado.append("<legend>Cursos Asignados</legend>");
							resultado.append("<table border='0' width='100%'>");
							for (CursoDTO cursoDTO : lstCursos) {
								CatCursoDTO catCursoDTO = cursoDTO.getCatCursoDTO();
								if (catCursoDTO != null) {
									resultado.append("<tr>");
										resultado.append("<td>");
											resultado.append("<table border='0' width='100%'>");
												resultado.append("<tr>");
													resultado.append("<td align='right'>Nombre:</td>");
													resultado.append("<td align='left' colspan='3'>");
														resultado.append(catCursoDTO.getCnombre()!= null ?catCursoDTO.getCnombre():"&nbsp;");
													resultado.append("</td>");
												resultado.append("</tr>");
												resultado.append("<tr>");
													resultado.append("<td align='right'>Descripci&oacute;n:</td>");
													resultado.append("<td align='left' colspan='3'>");
														resultado.append(catCursoDTO.getCdescripcion()!= null ? catCursoDTO.getCdescripcion() :"&nbsp;");
													resultado.append("</td>");
												resultado.append("</tr>");
												resultado.append("<tr>");
													resultado.append("<td align='right'>Tipo:</td>");
													resultado.append("<td align='left' colspan='3'>");
														if(catCursoDTO.getCatTipoCursoDTO()!= null){
															resultado.append(catCursoDTO.getCatTipoCursoDTO().getDescripcion() != null ? catCursoDTO.getCatTipoCursoDTO().getDescripcion() : "&nbsp;");
														}else{
															resultado.append("&nbsp;");
														}
													resultado.append("</td>");
												resultado.append("</tr>");
												resultado.append("<tr>");
												resultado.append("<td align='right'>Categor&iacute;a:</td>");
												resultado.append("<td align='left' colspan='3'>");
													if(catCursoDTO.getCatCategoriaCursoDTO()!= null){
														resultado.append(catCursoDTO.getCatCategoriaCursoDTO().getDescripcion() != null ? catCursoDTO.getCatCategoriaCursoDTO().getDescripcion() : "&nbsp;");
													}else{
														resultado.append("&nbsp;");
													}
												resultado.append("</td>");
											resultado.append("</tr>");
											resultado.append("<tr>");
												resultado.append("<td align='right'>Nivel Acad&eacute;mico:</td>");
												resultado.append("<td align='left' colspan='3'>");
													if(catCursoDTO.getCatTipoNivelAcademicoDTO() != null){
														resultado.append(catCursoDTO.getCatTipoNivelAcademicoDTO().getDescripcion() != null ? catCursoDTO.getCatTipoNivelAcademicoDTO().getDescripcion() : "&nbsp;");
													}else{
														resultado.append("&nbsp;");
													}
												resultado.append("</td>");
											resultado.append("</tr>");
											resultado.append("<tr>");
											resultado.append("<td align='right'>N&uacute;mero De Puntos</td>");
											resultado.append("<td align='left' colspan='3'>");
												if(catCursoDTO.getIpuntos() != null){
													resultado.append(catCursoDTO.getIpuntos() != null ? catCursoDTO.getIpuntos() : "&nbsp;");
												}else{
													resultado.append("&nbsp;");
												}
											resultado.append("</td>");
											resultado.append("</tr>");
											if(cursoDTO.getIpuntosObtenidos() != null && cursoDTO.getIpuntosObtenidos() > 0){
												resultado.append("<tr>");
												resultado.append("<td align='right'>N&uacute;mero De Puntos Obtenidos</td>");
												resultado.append("<td align='left' colspan='3'>");
														resultado.append(cursoDTO.getIpuntosObtenidos());												
												resultado.append("</td>");
												resultado.append("</tr>");
											}
											
												resultado.append("<tr>");
												resultado.append("<td align='right'>Duraci&oacute;n</td>");
												resultado.append("<td align='left' colspan='3'>");
													if(catCursoDTO.getCduracion() != null){
														resultado.append(catCursoDTO.getCduracion() != null ? catCursoDTO.getCduracion() : "&nbsp;");
													}else{
														resultado.append("&nbsp;");
													}
												resultado.append("</td>");
											resultado.append("</tr>");											
											resultado.append("</table>");
											if(lstCursos.size() != 1){
												resultado.append("<hr />");
											}
										resultado.append("</td>");
									resultado.append("</tr>");
								}
							}
							resultado.append("</table>");
							resultado.append("</fieldset>");
						}
						if (lstTrabajos != null && !lstTrabajos.isEmpty()) {
							resultado.append("<fieldset>");
							resultado.append("<legend>Trabajos Asignados</legend>	");
							resultado.append("");
							resultado.append("<table border='0' width='100%'>");
							for (TrabajoDTO trabajoDTO : lstTrabajos) {
								CatTrabajoDTO catTrabajoDTO = trabajoDTO.getCatTrabajoDTO();
								if (catTrabajoDTO != null) {
									resultado.append("<tr>");
									resultado.append("<td>");
										resultado.append("<table border='0' width='100%'>");
											resultado.append("<tr>");
												resultado.append("<td align='right'>Nombre:</td>");
												resultado.append("<td align='left' colspan='3'>");
													resultado.append(catTrabajoDTO!= null ? catTrabajoDTO.getCnombre():"&nbsp;");
												resultado.append("</td>");
											resultado.append("</tr>");
											resultado.append("<tr>");
												resultado.append("<td align='right'>Descripci&oacute;n:</td>");
												resultado.append("<td align='left' colspan='3'>");
													resultado.append(catTrabajoDTO.getCdescripcion()!= null ? catTrabajoDTO.getCdescripcion() :"&nbsp;");
												resultado.append("</td>");
											resultado.append("</tr>");
											resultado.append("<tr>");
												resultado.append("<td align='right'>Tipo:</td>");
												resultado.append("<td align='left' colspan='3'>");
													if(catTrabajoDTO.getBesExterno()!= null){
														if(catTrabajoDTO.getBesExterno()){
															resultado.append("Externo");
															resultado.append("</td>");
															resultado.append("</tr>");
															resultado.append("<tr>");
															resultado.append("<td align='right'>Trabajo:</td>");
															resultado.append("<td align='left' colspan='3'>");
																if(catTrabajoDTO.getCatTipoTrabajoExterno() != null){
																	resultado.append(catTrabajoDTO.getCatTipoTrabajoExterno().getDescripcion() != null ? catTrabajoDTO.getCatTipoTrabajoExterno().getDescripcion() : "&nbsp;");
																}else{
																	resultado.append("&nbsp;");
																}
																resultado.append("</td>");
																resultado.append("</tr>");
																resultado.append("<tr>");
																resultado.append("<td align='right'>N&uacute;mero De Convenio:</td>");
																resultado.append("<td align='left' colspan='3'>");
																resultado.append(catTrabajoDTO.getCnumeroConvenio() != null ? catTrabajoDTO.getCnumeroConvenio() : "&nbsp;");
														} else {
															resultado.append("Interno");	
														}
													}else{
														resultado.append("&nbsp;");
													}
												resultado.append("</td>");
											resultado.append("</tr>");
												
										resultado.append("<tr>");
										resultado.append("<td align='right'>N&uacute;mero De Puntos</td>");
										resultado.append("<td align='left' colspan='3'>");
											if(catTrabajoDTO.getIpuntos() != null){
												resultado.append(catTrabajoDTO.getIpuntos() != null ? catTrabajoDTO.getIpuntos() : "&nbsp;");
											}else{
												resultado.append("&nbsp;");
											}
										resultado.append("</td>");
										resultado.append("</tr>");
										if(trabajoDTO.getIpuntosObtenidos() != null && trabajoDTO.getIpuntosObtenidos() > 0){
											resultado.append("<tr>");
											resultado.append("<td align='right'>N&uacute;mero De Puntos Obtenidos</td>");
											resultado.append("<td align='left' colspan='3'>");
													resultado.append(trabajoDTO.getIpuntosObtenidos());
											resultado.append("</td>");
											resultado.append("</tr>");
										}										
										resultado.append("</table>");
										if(lstTrabajos.size() != 1){
											resultado.append("<hr />");
										}
									resultado.append("</td>");
								resultado.append("</tr>");
								}
							}
							resultado.append("</table>");
							resultado.append("</fieldset>");
						}
					}
				}
			}
		}
		return resultado.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n del c&oacute;digo en 
	 * HTML para la representaci&oacute;n del inventario de pertenencias en la
	 * plantilla correspondiente.
	 * @return String - Cadena con el HTML para representar la tabla.
	 */
	public String getInventarioPertenencias(){
		StringBuilder tabla = new StringBuilder("");
		if (sentenciaDTO.getInventarioPertenenciaDTO() != null){
			tabla.append("<fieldset>");
				tabla.append("<table border='0' width='100%'>");
					tabla.append("<tr>");
						tabla.append("<td align='center'>Tipo</td>");
						tabla.append("<td align='center'>Descripci&oacute;n</td>");
						tabla.append("<td align='center'>Cantidad</td>");
						tabla.append("<td align='center'>Unidad de Medida</td>");
					tabla.append("</tr>");
			for (PertenenciaDTO pertenencia : sentenciaDTO.getInventarioPertenenciaDTO().getPertenenciasDTO()){
					tabla.append("<tr>");
						tabla.append("<td>");
							tabla.append(pertenencia.getTipoPertenencia() != null ? 
									pertenencia.getTipoPertenencia().getValor() != null ? 
											pertenencia.getTipoPertenencia().getValor() : "&nbsp;"
											: "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getDescripcion() != null ? pertenencia.getDescripcion() : "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getCantidad() != null ? pertenencia.getCantidad() : "&nbsp;");
						tabla.append("</td>");
						tabla.append("<td>");
							tabla.append(pertenencia.getUnidadMedida() != null ? 
									pertenencia.getUnidadMedida().getValor() != null ? 
											pertenencia.getUnidadMedida().getValor() : "Pieza" 
												: "Pieza");
						tabla.append("</td>");
					tabla.append("</tr>");
			}
				tabla.append("</table>");
			tabla.append("</fieldset>");
		}
		return tabla.toString();
	}
}
