/**
* Nombre del Programa 		: BitacoraDescarga.java
* Autor 					: AAAV
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 10 Apr 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Clase de persistencia para la tabla BitacoraDescarga
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity para BitacoraDescarga.
 * @version 1.0
 * @author AAAV
 */
@Entity
@Table(name = "BitacoraDescarga")
public class BitacoraDescarga implements java.io.Serializable {

	private static final long serialVersionUID = 5101310278485561491L;
	private Long bitacoraDescargaId;
	private PermisoAudiencia permisoAudiencia;
	private Date fechaDescarga;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BitacoraDescarga_id", unique = true, nullable = false, precision = 18, scale = 0)    				
	public Long getBitacoraDescargaId() {
		return bitacoraDescargaId;
	}
	public void setBitacoraDescargaId(Long bitacoraDescargaId) {
		this.bitacoraDescargaId = bitacoraDescargaId;
	}
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PermisoAudiencia_id")
	public PermisoAudiencia getPermisoAudiencia() {
		return permisoAudiencia;
	}
	public void setPermisoAudiencia(PermisoAudiencia permisoAudiencia) {
		this.permisoAudiencia = permisoAudiencia;
	}
	
	@Column(name = "dFechaDescarga", length = 23)
	public Date getFechaDescarga() {
		return fechaDescarga;
	}
	public void setFechaDescarga(Date fechaDescarga) {
		this.fechaDescarga = fechaDescarga;
	}		
}
