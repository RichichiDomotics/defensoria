package mx.gob.segob.nsjp.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;

/**
 * CopiasAudioVideo entity. @author Asdrubal
 */
@Entity
@Table(name = "CopiasAudioVideo")
public class CopiasAudioVideo implements java.io.Serializable {

	// Fields
//	[Copiasav_id] [dbo].[campo llave] NOT NULL,
//	[Audiencia_id] [dbo].[campo llave] NOT NULL,
//	[Involucrado_id] [dbo].[campo llave] NOT NULL,
//	[Solicitud_id] [dbo].[campo llave] NOT NULL,
//	[NumeroCopias] [dbo].[int] NULL,
	
	
	private static final long serialVersionUID = -4439934496014628523L;
	private Long id;
	private long audiencia_id;
	private long involucrado_id;
	private long solicitud_id;
	private long numeroCopias;
	
	
	@Id
	@Column(name = "Copiasav_id")
	@GeneratedValue(strategy = IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(name = "Audiencia_id")
	public long getAudiencia_id() {
		return audiencia_id;
	}
	 
	@Column(name = "Involucrado_id")
	public long getInvolucrado_id() {
		return involucrado_id;
	}
	
	@Column(name = "Solicitud_id")
	public long getSolicitud_id() {
		return solicitud_id;
	}
	
	@Column(name = "NumeroCopias")
	public Long getNumeroCopias() {
		return numeroCopias;
	}

	public void setId(Long _id) {
		this.id = _id;
	}

	public void setAudiencia_id(long _audiencia_id) {
		audiencia_id = _audiencia_id;
	}

	public void setInvolucrado_id(long _involucrado_id) {
		involucrado_id = _involucrado_id;
	}

	public void setSolicitud_id(long _solicitud_id) {
		solicitud_id = _solicitud_id;
	}

	public void setNumeroCopias(long _numeroCopias) {
		numeroCopias = _numeroCopias;
	}
	

}