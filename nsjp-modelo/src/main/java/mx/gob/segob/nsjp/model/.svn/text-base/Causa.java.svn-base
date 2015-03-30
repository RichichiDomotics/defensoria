package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Causa" )
public class Causa implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1412838336221575040L;
	private Long causaId;
	private String cCausaPenalIdPJ;
	private Set<Audiencia> audiencias = new HashSet<Audiencia>(0);
		
	
	public Causa() {
	}
	
	
	public Causa(long causaId) {
		this.causaId = causaId;
	}


	/**
	 * @return the causaId
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "causa_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getCausaId() {
		return causaId;
	}
	/**
	 * @param causaId the causaId to set
	 */
	public void setCausaId(Long causaId) {
		this.causaId = causaId;
	}
	/**
	 * @return the cCausaPenalIdPJ
	 */
	@Column(name = "cCausaPenalIdPJ", nullable = false, length = 12)
	public String getcCausaPenalIdPJ() {
		return cCausaPenalIdPJ;
	}
	/**
	 * @param cCausaPenalIdPJ the cCausaPenalIdPJ to set
	 */
	public void setcCausaPenalIdPJ(String cCausaPenalIdPJ) {
		this.cCausaPenalIdPJ = cCausaPenalIdPJ;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "causa")
    public Set<Audiencia> getAudiencias() {
        return this.audiencias;
    }

    public void setAudiencias(Set<Audiencia> audiencias) {
        this.audiencias = audiencias;
    }
	
}
