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
import javax.persistence.Transient;


/**
 * HistoricoExpediente entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="HistoricoExpediente")

public class HistoricoExpediente implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -19780239960682945L;
	// Fields    

     private Long historicoExpedienteId;
     private Funcionario funcionarioAnterior;
     private Funcionario funcionarioAsigna;
     private NumeroExpediente numeroExpediente;
     private Funcionario funcionarioActual;
     private Date dFechaInicio;
     private Date dFechaFin;
     private Boolean consultarUltimo;

    // Constructors

    /** default constructor */
    public HistoricoExpediente() {
    }

	/** minimal constructor */
    public HistoricoExpediente(Long historicoExpedienteId, Funcionario funcionarioActual, Date dFechaInicio) {
        this.historicoExpedienteId = historicoExpedienteId;
        this.funcionarioActual = funcionarioActual;
        this.dFechaInicio = dFechaInicio;
    }
    
    /** full constructor */
    public HistoricoExpediente(Long historicoExpedienteId, Funcionario funcionarioAnterior, Funcionario funcionarioAsigna, NumeroExpediente numeroExpediente, Funcionario funcionarioActual, Date dFechaInicio, Date dFechaFin) {
        this.historicoExpedienteId = historicoExpedienteId;
        this.funcionarioAnterior = funcionarioAnterior;
        this.funcionarioAsigna = funcionarioAsigna;
        this.numeroExpediente = numeroExpediente;
        this.funcionarioActual = funcionarioActual;
        this.dFechaInicio = dFechaInicio;
        this.dFechaFin = dFechaFin;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HistoricoExpediente_id", unique=true, nullable=false, precision=18, scale=0)
    public Long getHistoricoExpedienteId() {
        return this.historicoExpedienteId;
    }
    
    public void setHistoricoExpedienteId(Long historicoExpedienteId) {
        this.historicoExpedienteId = historicoExpedienteId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="iFuncionarioAnterior")
    public Funcionario getFuncionarioAnterior() {
        return this.funcionarioAnterior;
    }
    
    public void setFuncionarioAnterior(Funcionario funcionarioAnterior) {
        this.funcionarioAnterior = funcionarioAnterior;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="iFuncionarioAsigna")
    public Funcionario getFuncionarioAsigna() {
        return this.funcionarioAsigna;
    }
    
    public void setFuncionarioAsigna(Funcionario funcionarioAsigna) {
        this.funcionarioAsigna = funcionarioAsigna;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="NumeroExpediente_id")
    public NumeroExpediente getNumeroExpediente() {
        return numeroExpediente;
    }
    
    public void setNumeroExpediente(NumeroExpediente numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="iFuncionarioActual", nullable=false)
    public Funcionario getFuncionarioActual() {
        return this.funcionarioActual;
    }
    
    public void setFuncionarioActual(Funcionario funcionarioActual) {
        this.funcionarioActual = funcionarioActual;
    }
    
    @Column(name="dFechaInicio", nullable=false, length=23)

    public Date getDFechaInicio() {
        return this.dFechaInicio;
    }
    
    public void setDFechaInicio(Date dFechaInicio) {
        this.dFechaInicio = dFechaInicio;
    }
    
    @Column(name="dFechaFin", length=23)

    public Date getDFechaFin() {
        return this.dFechaFin;
    }
    
    public void setDFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

	/**
	 * Método de acceso al campo consultarUltimo.
	 * @return El valor del campo consultarUltimo
	 */
    @Transient
	public Boolean isConsultarUltimo() {
		return consultarUltimo;
	}

	/**
	 * Asigna el valor al campo consultarUltimo.
	 * @param consultarUltimo el valor consultarUltimo a asignar
	 */
	public void setConsultarUltimo(Boolean consultarUltimo) {
		this.consultarUltimo = consultarUltimo;
	}
}