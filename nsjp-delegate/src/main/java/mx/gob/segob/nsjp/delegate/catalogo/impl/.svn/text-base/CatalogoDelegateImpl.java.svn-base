/**
 *
 * Nombre del Programa : CatalogoDelegateImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 05/04/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Integración
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
package mx.gob.segob.nsjp.delegate.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatFaltaAdministrativaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.DiaInhabilDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.FuncionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.audiencia.AdministrarSalaService;
import mx.gob.segob.nsjp.service.catalogo.AdministrarCatalogoService;
import mx.gob.segob.nsjp.service.catalogo.CatalogoService;
import mx.gob.segob.nsjp.service.catalogo.DiaInhabilService;
import mx.gob.segob.nsjp.service.institucion.JerarquiaOrganizacionalService;
import mx.gob.segob.nsjp.service.usuario.ConsultarFuncionService;
import mx.gob.segob.nsjp.service.usuario.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vaguirre
 * @version 1.0
 * 
 */
@Service("catalogoDelegate")
public class CatalogoDelegateImpl implements CatalogoDelegate {

    @Autowired
    private CatalogoService service;
    @Autowired
    private DiaInhabilService diaInhabilService;
    @Autowired
    private RolService rolService;
    @Autowired
    private ConsultarFuncionService consultarFuncionService;
    @Autowired
    private JerarquiaOrganizacionalService jerarquiaOrganizacionalService;
    @Autowired 
    private AdministrarSalaService administrarSalaService;
    @Autowired 
    private AdministrarCatalogoService administrarCatalogoService;    
    
    
    /*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate#recuperarCatalogo
     * (mx.gob.segob.nsjp.comun.enums.catalogo.Catalogo)
     */
    @Override
    public List<CatalogoDTO> recuperarCatalogo(Catalogos cat)
            throws NSJPNegocioException {
        return service.recuperarCatalogo(cat);
    }

    @Override
    public List<CatalogoDTO> recuperarCatalogoDependiente(Catalogos catHijo,
            Long idValorPadre) throws NSJPNegocioException {
        return service.recuperarCatalogoDependiente(catHijo, idValorPadre);
    }

    @Override
    public List<CatalogoDTO> consultarAsentamiento(Long idMpio, Long idCiudad,
            Long idTipoAsentamiento) throws NSJPNegocioException {
        return this.service.consultarAsentamiento(idMpio, idCiudad, idTipoAsentamiento);
    }

    @Override
    public List<CatalogoDTO> recuperarCatalogoCompleto(Catalogos cat)
            throws NSJPNegocioException {
        return this.service.recuperarCatalogoCompleto(cat);
    }

    @Override
    public List<CatDelitoDTO> consultarDelito() throws NSJPNegocioException {
        return this.service.consultarDelito();
    }

	public Long guardarDiaInhabil(DiaInhabilDTO dto)
			throws NSJPNegocioException {
		return diaInhabilService.guardarDiaInhabil(dto);
	}

	public void eliminarDiaInhabil(DiaInhabilDTO dto)
			throws NSJPNegocioException {
		diaInhabilService.eliminarDiaInhabil(dto);
	}

	public List<DiaInhabilDTO> consultarDiasInhabiles()
			throws NSJPNegocioException {
		return diaInhabilService.consultarDiasInhabiles();
	}
	
	public List<DiaInhabilDTO> consultarDiasInhabilesPorMes(Short mes)
			throws NSJPNegocioException {
		return diaInhabilService.consultarDiasInhabilesPorMes(mes);
	}

	public List<RolDTO> consultarRoles(FiltroRolesDTO filtroRolesDTO) throws NSJPNegocioException{
		return rolService.consultarRoles(filtroRolesDTO);		
	}

	@Override
	public List<FuncionDTO> consultarFunciones() throws NSJPNegocioException {		
		return consultarFuncionService.consultarFunciones();
	}

	@Override
	public List<CatFaltaAdministrativaDTO> consultarCatalogoFaltaAdministrativa()
			throws NSJPNegocioException {
		return service.consultarCatalogoFaltaAdministrativa();
	}
	
	@Override
	public List<JerarquiaOrganizacionalDTO> consultarDepartamentosExceptoAreasYDepartamentos(Long jerarquiaResponsableId, 
			List<Long> idsAreasDescartar, List<Long> idsDepartamentoDescartar)	throws NSJPNegocioException {
		return jerarquiaOrganizacionalService
				.consultarDepartamentosExceptoAreasYDepartamentos(
						jerarquiaResponsableId, idsAreasDescartar,
						idsDepartamentoDescartar);
	}
	
	
	@Override
	public List<CatDelitoDTO> consultarCatDelitoPorFilro(CatDelitoDTO catDelitoFiltroDTO) throws NSJPNegocioException{
		return service.consultarCatDelitoPorFilro(catDelitoFiltroDTO); 
	}
	

    @Override
    public List<CatDelitoDTO> consultarDelitosSeleccionables(String idsGrid) throws NSJPNegocioException {
        return this.service.consultarDelitosSinIdsGrid(idsGrid);
    }
    
    @Override
    public List<CatDelitoDTO> consultarDelitoSeleccionado(String idsGrid) throws NSJPNegocioException {
        return this.service.consultarDelitoIdSeleccionados(idsGrid);
    }
    
    @Override
	public CatDelitoDTO consultarCatDelitoPorId(Long catDelitoId)
			throws NSJPNegocioException {
		return service.consultarCatDelitoPorId(catDelitoId);
    }
    
    @Override
	public CatAreasNegocioDTO consultarCatAreaNegocioPorId(Long catAreaNegId)
			throws NSJPNegocioException {
		return service.consultarCatAreaNegocioPorId(catAreaNegId);
    }

	@Override
	public CatDelitoDTO guardarActualizarCatDelito(CatDelitoDTO catDelitoDto)
			throws NSJPNegocioException {
		return service.guardarActualizarCatDelito(catDelitoDto);
	}
	
	@Override
	public CatAreasNegocioDTO guardarActualizarCatAreasNegocio(CatAreasNegocioDTO CatAreasNegocioDto)
			throws NSJPNegocioException {
		return service.guardarActualizarCatAreasNegocio(CatAreasNegocioDto);
	}

	@Override
	public Long eliminarCatDelito(Long catDelitoId)
			throws NSJPNegocioException {
		return service.eliminarCatDelito(catDelitoId);
	}
	
	@Override
	public Long eliminarCatAreasNegocio(Long catAreasNegocioId)
			throws NSJPNegocioException {
		return service.eliminarCatAreasNegocio(catAreasNegocioId);
	}

	@Override
	public SalaAudienciaDTO consultarSalaAudiencia(
			SalaAudienciaDTO salaAudienciaDTO) throws NSJPNegocioException {
		return administrarSalaService.consultarSalaAudiencia(salaAudienciaDTO);
	}

	@Override
	public SalaAudienciaDTO guardarActualizarSalaAudiencia(
			SalaAudienciaDTO salaAudienciaDTO) throws NSJPNegocioException {
		return administrarSalaService.guardarActualizarSalaAudiencia(salaAudienciaDTO);
	}

	@Override
	public List<CatAreasNegocioDTO> consultarAreasDeNegocio()
			throws NSJPNegocioException {
		return administrarCatalogoService.consultarAreasDeNegocio();
	}

	@Override
	public CatFaltaAdministrativaDTO consultarFaltaAdministrativa(
			Long idFaltaAdministrativa) throws NSJPNegocioException {
		return service.consultarFaltaAdministrativa(idFaltaAdministrativa);

	}

	@Override
	public CatDelitoDTO consultarDelito(Long idDelito)
			throws NSJPNegocioException {
		return service.consultarDelito(idDelito);
	}

	@Override
	public List<CatDistritoDTO> consultarDistritos(Instituciones target)
			throws NSJPNegocioException {
		return administrarCatalogoService.consultarDistritos(target);
	}
	
	public ConfInstitucionDTO consultarIntitucionPorClave(String clave) throws NSJPNegocioException{
		return service.consultarIntitucionPorClave(clave);
	}
}
