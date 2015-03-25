/**
 * Nombre del Programa : CatEstadoPermiso.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01 Jun 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Objeto de Transferencia para el catálogo de estados permisos
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
package mx.gob.segob.nsjp.dto.audiencia;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Objeto de Transferencia para el catalogo estado permisos.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class CatEstadoPermisoDTO extends GenericDTO {

	private static final long serialVersionUID = -1027008789242489553L;
	
	private Long catEstadoPermisoId;
	private String estatus;

	public Long getCatEstadoPermisoId() {
		return catEstadoPermisoId;
	}
	public void setCatEstadoPermisoId(Long catEstadoPermisoId) {
		this.catEstadoPermisoId = catEstadoPermisoId;
	}
	
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}	
}
