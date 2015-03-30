/**
 * Nombre del Programa : FuncionarioTransformer.java                                    
 * Autor                            : Tattva-IT                                              
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 28/04/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Integraci&oacute;n xxxxxxxxxxx                      
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
package mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioWSDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaWSDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoWSDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.model.Funcionario;

import org.apache.log4j.Logger;

/**
 * Clase para la tranformacion de objetos funcionario a funcionariosDTO
 * 
 * @version 1.0
 * @author Tattva-IT
 * 
 */
public class FuncionarioWSDTOTransformer extends GenericWSDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4564315484896399088L;
	/**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(FuncionarioWSDTOTransformer.class);


    /**
     * Metodo para transformar el objeto funcionario a FuncionarioWSDTO de forma
     * muy basica (clave y nombres)
     * 
     * @param funcionario
     * @return
     */
    public static FuncionarioWSDTO transformarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            return null;
        }
        logger.debug("funcionario.getClaveFuncionario() :: "
                + funcionario.getClaveFuncionario());
        FuncionarioWSDTO funcionarioWSDTO = new FuncionarioWSDTO();
        
        funcionarioWSDTO.setClaveFuncionario(funcionario.getClaveFuncionario());
        funcionarioWSDTO.setNombre(funcionario.getNombreFuncionario());
        funcionarioWSDTO.setApellidoMaterno(funcionario
                .getApellidoMaternoFuncionario());
        funcionarioWSDTO.setApellidoPaterno(funcionario.getApellidoPaternoFuncionario());
        
        funcionarioWSDTO.setEmail(funcionario.getEmail());
        if (funcionario.getArea() != null){
        	funcionarioWSDTO.setNombreArea(funcionario.getArea().getNombre());        	
        }
        if (funcionario.getPuesto()!= null){
        	funcionarioWSDTO.setNombrePuesto(funcionario.getPuesto().getValor());
        }
        
        return funcionarioWSDTO;

    }
    
    public static FuncionarioDTO transformarFuncionario(mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO funcionarioWSDTO) {
        if (funcionarioWSDTO == null) {
            return null;
        }
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();        
        
        funcionarioDTO.setClaveFuncionario(funcionarioWSDTO.getClaveFuncionario());
        funcionarioDTO.setNombreFuncionario(funcionarioWSDTO.getNombre());
        funcionarioDTO.setApellidoMaternoFuncionario(funcionarioWSDTO.getApellidoMaterno());
        funcionarioDTO.setApellidoPaternoFuncionario(funcionarioWSDTO.getApellidoPaterno());        
        return funcionarioDTO;

    }
    
    /**
     * M&eacute;todo utilitario que lleva a cabo la transformaci&oacute;n del WSDTO 
     * regresado por el web service de consulta de funcionarios externos a un DTO normal.
     * @param funcionarioWSDTO - el FuncionarioWSDTO regresado por el web service.
     * @return funcionarioDTO - el FuncionarioDTO normal.
     */
    public static FuncionarioDTO transformarFuncionario(mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO funcionarioWSDTO) {
        if (funcionarioWSDTO == null) {
            return null;
        }
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();        
        
        funcionarioDTO.setClaveFuncionario(funcionarioWSDTO.getClaveFuncionario());
        funcionarioDTO.setNombreFuncionario(funcionarioWSDTO.getNombre());
        funcionarioDTO.setApellidoMaternoFuncionario(funcionarioWSDTO.getApellidoMaterno());
        funcionarioDTO.setApellidoPaternoFuncionario(funcionarioWSDTO.getApellidoPaterno());
        funcionarioDTO.setEmail(funcionarioWSDTO.getEmail());
        
        if (funcionarioWSDTO.getNombreArea() != null && !funcionarioWSDTO.getNombreArea().isEmpty()){
        	JerarquiaOrganizacionalDTO jerarquiaOrganizacional = new JerarquiaOrganizacionalDTO();
        	jerarquiaOrganizacional.setNombre(funcionarioWSDTO.getNombreArea());
        	funcionarioDTO.setJerarquiaOrganizacional(jerarquiaOrganizacional);        	
        }
        
        if (funcionarioWSDTO.getNombrePuesto() != null && !funcionarioWSDTO.getNombrePuesto().isEmpty()){
        	ValorDTO puesto = new ValorDTO();
        	puesto.setValor(funcionarioWSDTO.getNombrePuesto());
        	funcionarioDTO.setPuesto(puesto);        	
        }
        
        if (funcionarioWSDTO.getNombreDiscriminante() != null && !funcionarioWSDTO.getNombreDiscriminante().isEmpty()){
        	CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
        	discriminante.setNombre(funcionarioWSDTO.getNombreDiscriminante());
        	funcionarioDTO.setDiscriminante(discriminante);
        }
        
        if (funcionarioWSDTO.getDepartamento() != null){
        	DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        	departamentoDTO.setDepartamentoId(funcionarioWSDTO.getDepartamento().getDepartamentoId());
        	if(funcionarioWSDTO.getDepartamento().getArea() != null){
        		AreaDTO areaDTO = new AreaDTO();
        		areaDTO.setAreaId(funcionarioWSDTO.getDepartamento().getArea().getAreaId());        		
        		departamentoDTO.setArea(areaDTO);
        	}
        }
        
        return funcionarioDTO;

    }
    
    public static FuncionarioDTO transformarFuncionario(mx.gob.segob.nsjp.dto.funcionario.FuncionarioWSDTO funcionario) {
        if (funcionario == null) {
            return null;
        }
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();        
        
        funcionarioDTO.setClaveFuncionario(funcionario.getClaveFuncionario());
        funcionarioDTO.setNombreFuncionario(funcionario.getNombre());
        funcionarioDTO.setApellidoMaternoFuncionario(funcionario.getApellidoMaterno());
        funcionarioDTO.setApellidoPaternoFuncionario(funcionario.getApellidoPaterno());
        funcionarioDTO.setEmail(funcionario.getEmail());
        
        JerarquiaOrganizacionalDTO jerarquiaOrganizacional = new JerarquiaOrganizacionalDTO();
        jerarquiaOrganizacional.setNombre(funcionario.getNombreArea());
        funcionarioDTO.setJerarquiaOrganizacional(jerarquiaOrganizacional);
        
        ValorDTO puesto = new ValorDTO();
        puesto.setValor(funcionario.getNombrePuesto());
        funcionarioDTO.setPuesto(puesto);
        
        if (funcionario.getNombreDiscriminante() != null && !funcionario.getNombreDiscriminante().isEmpty()){
        	CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
        	discriminante.setNombre(funcionario.getNombreDiscriminante());
        	funcionarioDTO.setDiscriminante(discriminante);
        }
        
        if (funcionario.getDepartamento()!= null){
        	DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        	if(funcionario.getDepartamento().getArea()!= null){
        		AreaDTO areaDTO = new AreaDTO();
        		areaDTO.setAreaId(funcionario.getDepartamento().getArea().getAreaId());
        		departamentoDTO.setArea(areaDTO);
        	}
        	departamentoDTO.setDepartamentoId(funcionario.getDepartamento().getDepartamentoId());
        	funcionarioDTO.setDepartamento(departamentoDTO);
        }        
        
        return funcionarioDTO;

    }
    
    /**
     * Metodo para transformar el objeto funcionario a FuncionarioWSDTO de forma
     * muy basica (clave y nombres)
     * 
     * @param funcionario
     * @return
     */
    public static FuncionarioWSDTO transformarFuncionario(FuncionarioDTO funcionario) {
        if (funcionario == null) {
            return null;
        }
        logger.debug("funcionario.getClaveFuncionario() :: "
                + funcionario.getClaveFuncionario());
        FuncionarioWSDTO funcionarioWSDTO = new FuncionarioWSDTO();
        
        funcionarioWSDTO.setClaveFuncionario(funcionario.getClaveFuncionario());
        funcionarioWSDTO.setNombre(funcionario.getNombreFuncionario());
        funcionarioWSDTO.setApellidoMaterno(funcionario
                .getApellidoMaternoFuncionario());
        funcionarioWSDTO.setApellidoPaterno(funcionario.getApellidoPaternoFuncionario());
        
        funcionarioWSDTO.setEmail(funcionario.getEmail());
        if (funcionario.getJerarquiaOrganizacional() != null){
        	funcionarioWSDTO.setNombreArea(funcionario.getJerarquiaOrganizacional().getNombre());        	
        }
        if (funcionario.getPuesto()!= null){
        	funcionarioWSDTO.setNombrePuesto(funcionario.getPuesto().getValor());
        }

        if (funcionario.getDepartamento()!= null){
        	DepartamentoWSDTO departamentoWSDTO = new DepartamentoWSDTO();
        	if(funcionario.getDepartamento().getArea()!= null){
        		AreaWSDTO areaWSDTO = new AreaWSDTO();
        		areaWSDTO.setAreaId(funcionario.getDepartamento().getArea().getAreaId());
        		departamentoWSDTO.setArea(areaWSDTO);
        	}
        	departamentoWSDTO.setDepartamentoId(funcionario.getDepartamento().getDepartamentoId());
        	funcionarioWSDTO.setDepartamento(departamentoWSDTO);
        }
        
        
        return funcionarioWSDTO;

    }
    
    /**
     * M&eacute;todo para transformar el objeto FuncionarioDTO a un FuncionarioWSDTO 
     * con los datos m&iacute;nimos de retorno para la consulta de funcionarios externos
     * por criterios.
     * 
     * @param funcionario - FuncionarioDTO a transformar en un FuncionarioWSDTO
     * @return funcionarioWSDTO - FuncionarioWSDTO transformado.
     */
    public static mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO transformarWSDTOCliente(FuncionarioDTO funcionario) {
        if (funcionario == null) {
            return null;
        }
        logger.debug("funcionario.getClaveFuncionario() :: "
                + funcionario.getClaveFuncionario());
        mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO funcionarioWSDTO = 
        	new mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO();
        
        funcionarioWSDTO.setClaveFuncionario(funcionario.getClaveFuncionario());
        funcionarioWSDTO.setNombre(funcionario.getNombreFuncionario());
        funcionarioWSDTO.setApellidoMaterno(funcionario
                .getApellidoMaternoFuncionario());
        funcionarioWSDTO.setApellidoPaterno(funcionario.getApellidoPaternoFuncionario());
        
        funcionarioWSDTO.setEmail(funcionario.getEmail());
        if (funcionario.getJerarquiaOrganizacional() != null){
        	funcionarioWSDTO.setNombreArea(funcionario.getJerarquiaOrganizacional().getNombre());        	
        }
        if (funcionario.getPuesto()!= null){
        	funcionarioWSDTO.setNombrePuesto(funcionario.getPuesto().getValor());
        }
        if (funcionario.getDiscriminante() != null){
        	funcionarioWSDTO.setNombreDiscriminante(funcionario.getDiscriminante().getNombre());
        }
        
        if (funcionario.getDepartamento()!= null){
        	mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.DepartamentoWSDTO departamentoWSDTO = new mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.DepartamentoWSDTO();
        	if(funcionario.getDepartamento().getArea()!= null){
        		mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.AreaWSDTO areaWSDTO = new mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.AreaWSDTO();
        		areaWSDTO.setAreaId(funcionario.getDepartamento().getArea().getAreaId());
        		departamentoWSDTO.setArea(areaWSDTO);
        	}
        	departamentoWSDTO.setDepartamentoId(funcionario.getDepartamento().getDepartamentoId());
        	funcionarioWSDTO.setDepartamento(departamentoWSDTO);
        }
        
        return funcionarioWSDTO;

    }

}
