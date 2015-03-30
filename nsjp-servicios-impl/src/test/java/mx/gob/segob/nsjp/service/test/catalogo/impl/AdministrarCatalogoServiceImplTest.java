package mx.gob.segob.nsjp.service.test.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoCompletoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.service.catalogo.AdministrarCatalogoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class AdministrarCatalogoServiceImplTest
        extends
            BaseTestServicios<AdministrarCatalogoService> {
    public void testObtenerValor() {
        try {
            CatalogoDTO input = new CatalogoDTO();
            //ORGANIZACION_SECTOR_PUBLICO (57) Con valor de 1460
//            input.setIdCatalogo(new Long(Catalogos.ORGANIZACION_SECTOR_PUBLICO.ordinal()));
//            input.setClave(1460L);
            //TIPO_DEPENDENCIA (56) Con valor de 1466
//            input.setIdCatalogo(new Long(Catalogos.TIPO_DEPENDENCIA.ordinal()));
//            input.setClave(1446L);
            //NIVEL_DEPENDENCIA (55) Con valor de 1443
//            input.setIdCatalogo(new Long(Catalogos.NIVEL_DEPENDENCIA.ordinal()));
//            input.setClave(1443L);
            
            //ORGANIZACION_SECTOR_PUBLICO (57) Con valor de 1460
	        input.setIdCatalogo(new Long(Catalogos.ORGANIZACION_SECTOR_PUBLICO.ordinal()));
	        input.setClave(1460L);
            CatalogoDTO catalogo = super.service.obtenerValor(input);
            logger.info("ICatalogo:"+ catalogo.getCampoId());
            
            Long valorTipoDependenciaId = null;
            if(catalogo!= null && catalogo.getValoresExras()!=null && !catalogo.getValoresExras().isEmpty()){
	            for(ValorDTO valor : catalogo.getValoresExras()){
	            	if( valor.getValor()!=null && !valor.getValor().isEmpty()){
	            		valorTipoDependenciaId = Long.parseLong(valor.getValor());
		            	logger.debug(" valorTipoDependencia:"+valor.getIdCampo());
		            	logger.debug(" valorTipoDependencia:"+valor.getValor());
		            	logger.debug(" valorTipoDependencia***:"+valorTipoDependenciaId);
		            	}
	            }
            }
            
            //TIPO_DEPENDENCIA
            Long valorNivelDependenciaId = null;
            if(valorTipoDependenciaId!=null){
            	input.setIdCatalogo(new Long(Catalogos.TIPO_DEPENDENCIA.ordinal()));
    	        input.setClave(valorTipoDependenciaId);
            	
            	CatalogoDTO catalogoNivelDependencia = super.service.obtenerValor(input);
                
                if(catalogoNivelDependencia!= null && catalogoNivelDependencia.getValoresExras()!=null && !catalogoNivelDependencia.getValoresExras().isEmpty()){
    	            for(ValorDTO valor : catalogoNivelDependencia.getValoresExras()){
    	            	if( valor.getValor()!=null && !valor.getValor().isEmpty()){
    	            		valorNivelDependenciaId = Long.parseLong(valor.getValor());
    		            	logger.debug(" valorNivelDependencia:"+valor.getIdCampo());
    		            	logger.debug(" valorNivelDependencia:"+valor.getValor());
    		            	logger.debug(" valorNivelDependencia***:"+valorNivelDependenciaId);
    		            	}
    	            }
                }	
            }
            
            //DATOS DEL NIVEL_DEPENDENCIA
            if(valorNivelDependenciaId!=null){
            	input.setIdCatalogo(new Long(Catalogos.NIVEL_DEPENDENCIA.ordinal()));
    	        input.setClave(valorNivelDependenciaId);
    	        
            	CatalogoDTO catalogoNivelDependencia = super.service.obtenerValor(input);
                
                logger.info("ICatalogo:"+ catalogoNivelDependencia.getCampoId());
                logger.info("ICatalogo:"+ catalogoNivelDependencia.getValor());
                logger.info("ICatalogo:"+ catalogoNivelDependencia.getClave());
                logger.info("ICatalogo:"+ catalogoNivelDependencia.getIdCatalogo());
            }
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }

    public void testRegistrarValor() {
        try {
            CatalogoDTO niu = new CatalogoDTO();
            niu.setCampoId(171L);
            niu.setValor("F");
            ValorDTO cuota = new ValorDTO();
            cuota.setIdCampo(172L);
            cuota.setValor("20");
            niu.addValorExtra(cuota);
            super.service.registrarValor(niu);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }

    }

    public void testActualizarValor() {
        try {
            CatalogoDTO niu = new CatalogoDTO();
            niu.setClave(2798L);
            niu.setValor("EE");
            ValorDTO cuota = new ValorDTO();
            cuota.setIdCampo(172L);
            cuota.setValor("21");
            niu.addValorExtra(cuota);
            super.service.actualizarValor(niu);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }

    }

    public void testEliminarValor() {
        try {
            CatalogoDTO del =new CatalogoDTO();
            del.setClave(2798L);
            super.service.eliminarValor(del);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }

    }

    public void testObtenerCatalogo() {
        try {
            CatalogoCompletoDTO catCom = super.service.obtenerCatalogo(new Long(Catalogos.TIPO_UNIDAD_INVESTIGACION_ESPECIALIZADA.ordinal()));
            for (CatalogoDTO dto : catCom.getValores()) {
                logger.debug("dto :: " + dto.getClave() + "->" + dto.getValor());
                for (ValorDTO  ex: dto.getValoresExras()) {
                    logger.debug(" ex :: ["+ex.getIdCampo() +"] " + ex.getNombreCampo() + "->" + ex.getValor());
                }
            }
            logger.debug(catCom);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }

    }

    public void testObtenerListaCatalogos() {
        try {
            List<CatalogoDTO> data = super.service.obtenerListaCatalogos();
            for (CatalogoDTO row: data) {
                logger.debug("row :: "+ row);
            }
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }

    }
    
    public void testObtenerDefinicion(){
        try {
            CatalogoDTO row = super.service.obtenerDefinicion(new Long(Catalogos.DELITO.ordinal()));
            logger.debug(row);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    
        
    }
    
}
