package mx.gob.segob.nsjp.converters;

import mx.gob.segob.nsjp.dto.solicitud.DocumentoWSMichDTO;
import mx.gob.segob.nsjp.dto.solicitud.ImpDelOfendidoDTO;
import mx.gob.segob.nsjp.dto.solicitud.ImputadoDTO;
import mx.gob.segob.nsjp.dto.solicitud.ImputadoDelitoDTO;
import mx.gob.segob.nsjp.dto.solicitud.OfendidoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMichDTO;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SolicitudMichConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		
		return clazz.equals(SolicitudMichDTO.class);
	}


	@Override
	public void marshal(Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		SolicitudMichDTO solicitudMichDTO = (SolicitudMichDTO) valor;
		writer.addAttribute("xmlns", "http://tempuri.org/");
		writer.addAttribute("xmlns:org", "http://tempuri.org/");
		writer.startNode("org:xSolicitud");
		writer.startNode("Caso");
		writer.addAttribute("xmlns", "");
		writer.startNode("Solicitud");
		writer.addAttribute("fec_envio", solicitudMichDTO.getFec_envio());
		writer.addAttribute("fec_inicio", solicitudMichDTO.getFec_inicio());
		writer.addAttribute("materia", solicitudMichDTO.getMateria());
		writer.addAttribute("mp", ""+solicitudMichDTO.getMp());
		writer.addAttribute("nombre", solicitudMichDTO.getNombre());
		writer.addAttribute("nuc", solicitudMichDTO.getNuc());
		writer.addAttribute("prioridad", solicitudMichDTO.getPrioridad());
		writer.addAttribute("promovente", solicitudMichDTO.getPromovente());
		writer.addAttribute("solicitudId", solicitudMichDTO.getSolicitudId());
		writer.addAttribute("tipo_despacho", solicitudMichDTO.getTipo_despacho());
		writer.addAttribute("tipo_ini", solicitudMichDTO.getTipo_ini());
		writer.addAttribute("tipo_promocion", solicitudMichDTO.getTipo_promocion());
		writer.addAttribute("tipo_solicitud", solicitudMichDTO.getTipo_solicitud());
		writer.addAttribute("unidad_inv", solicitudMichDTO.getUnidad_inv());
		
		for (ImputadoDTO imputadoDTO : solicitudMichDTO.getImputados()) {
			writer.startNode("Imputado");
			writer.addAttribute("ap_mater", imputadoDTO.getAp_mater());
			writer.addAttribute("ap_pater", imputadoDTO.getAp_pater());
			writer.addAttribute("consec_imp", imputadoDTO.getConsec_imp());
			writer.addAttribute("edad", imputadoDTO.getEdad());
			writer.addAttribute("fec_registro", imputadoDTO.getFec_registro());
			if (solicitudMichDTO.getTipo_ini().equals("1")){
//				writer.addAttribute("fec_nac", imputadoDTO.getFec_nac());
				writer.addAttribute("fec_detencion", imputadoDTO.getFec_detencion());
				writer.addAttribute("fecha_presmp", imputadoDTO.getFecha_presmp());
				writer.addAttribute("id_defensor", imputadoDTO.getId_defensor());
				writer.addAttribute("tipo_defensor", imputadoDTO.getTipo_defensor());
				if (solicitudMichDTO.getTipo_promocion().equals("3")){
					writer.addAttribute("colonia_id_imp", imputadoDTO.getColonia_id_imp());
					writer.addAttribute("colonia_localidad_imp", imputadoDTO.getColonia_localidad_imp());
					writer.addAttribute("colonia_municipio_imp", imputadoDTO.getColonia_municipio_imp());
					writer.addAttribute("colonia_zona_imp", imputadoDTO.getColonia_zona_imp());
					writer.addAttribute("colonia_estado_imp", imputadoDTO.getColonia_estado_imp());
					writer.addAttribute("domicilio_imp", imputadoDTO.getDomicilio_imp());
				}
			}
			writer.addAttribute("nombre", imputadoDTO.getNombre());
			writer.addAttribute("sexo", imputadoDTO.getSexo());
			writer.addAttribute("situacion", imputadoDTO.getSituacion());
			if (imputadoDTO.getFec_disposicion_pj() != null)
				writer.addAttribute("fec_disposicion_pj", imputadoDTO.getFec_disposicion_pj());
			writer.endNode();
		}
		for (ImputadoDelitoDTO imputadoDelitoDTO : solicitudMichDTO.getImputadosDelitos()){
			writer.startNode("Imputado_Delito");
			writer.addAttribute("consec_imp", imputadoDelitoDTO.getConsec_imp());
			writer.addAttribute("delito", imputadoDelitoDTO.getDelito());
			writer.addAttribute("estado_psicofisico", imputadoDelitoDTO.getEstado_psicofisico());
			writer.addAttribute("fecha_delito", imputadoDelitoDTO.getFecha_delito());
			writer.addAttribute("grado_consumacion", imputadoDelitoDTO.getGrado_consumacion());
			writer.addAttribute("intencionalidad", imputadoDelitoDTO.getIntencionalidad());
			writer.endNode();
		}
		for (OfendidoDTO ofendidoDTO : solicitudMichDTO.getOfendidos()){
			writer.startNode("Ofendido");
			writer.addAttribute("ap_mater", ofendidoDTO.getAp_mater());
			writer.addAttribute("ap_pater", ofendidoDTO.getAp_pater());
			writer.addAttribute("nombre", ofendidoDTO.getNombre());
			writer.addAttribute("ofendido", ofendidoDTO.getOfendido());
			writer.addAttribute("sexo", ofendidoDTO.getSexo());
			writer.addAttribute("tipo_parte", ofendidoDTO.getTipo_parte());
			writer.addAttribute("tipo_persona", ofendidoDTO.getTipo_persona());
			writer.endNode();
		}
		for (ImpDelOfendidoDTO impDelOfendidoDTO : solicitudMichDTO.getImpDeOfendidos()){
			writer.startNode("Imp_Del_Ofend");
			writer.addAttribute("consec_imp", impDelOfendidoDTO.getConsec_imp());
			writer.addAttribute("delito", impDelOfendidoDTO.getDelito());
			writer.addAttribute("ofendido", impDelOfendidoDTO.getOfendido());
			writer.endNode();
		}
		for (DocumentoWSMichDTO documentoWsMichDTO : solicitudMichDTO.getDocumentos()){
			writer.startNode("Documento");
			writer.addAttribute("archivo", documentoWsMichDTO.getArchivo());
			writer.addAttribute("consec_doc", documentoWsMichDTO.getConsec_doc());
			writer.addAttribute("formato", documentoWsMichDTO.getFormato());
			writer.addAttribute("nombre", documentoWsMichDTO.getNombre());
			writer.endNode();
		}
		
		writer.endNode();
		writer.endNode();
		writer.endNode();
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		SolicitudMichDTO solicitudMichDTO = new SolicitudMichDTO();
		solicitudMichDTO.setFec_envio(reader.getAttribute("fec_envio"));
		solicitudMichDTO.setFec_inicio(reader.getAttribute("fec_inicio"));
		solicitudMichDTO.setMateria(reader.getAttribute("materia"));
		solicitudMichDTO.setMp(reader.getAttribute("mp"));
		solicitudMichDTO.setNombre(reader.getAttribute("nombre"));
		solicitudMichDTO.setNuc(reader.getAttribute("nuc"));
		solicitudMichDTO.setPrioridad(reader.getAttribute("prioridad"));
		solicitudMichDTO.setPromovente(reader.getAttribute("promovente"));
		solicitudMichDTO.setSolicitudId(reader.getAttribute("solicitudId"));
		solicitudMichDTO.setTipo_despacho(reader.getAttribute("tipo_despacho"));
		solicitudMichDTO.setTipo_ini(reader.getAttribute("tipo_ini"));
		solicitudMichDTO.setTipo_promocion(reader.getAttribute("tipo_promocion"));
		solicitudMichDTO.setTipo_solicitud(reader.getAttribute("tipo_solicitud"));
		solicitudMichDTO.setUnidad_inv(reader.getAttribute("unidad_inv"));
		
		for (ImputadoDTO imputadoDTO : solicitudMichDTO.getImputados()) {
			imputadoDTO.setAp_mater("ap_mater");
			imputadoDTO.setAp_pater("ap_pater");
			imputadoDTO.setConsec_imp("consec_imp");
			imputadoDTO.setEdad("edad");
			imputadoDTO.setFec_registro("fec_registro");
			if (solicitudMichDTO.getTipo_ini().equals("1")){
//				imputadoDTO.setFec_nac("fec_nac");
				imputadoDTO.setFec_detencion("fec_detencion");
				imputadoDTO.setFecha_presmp("fecha_presmp");
				imputadoDTO.setId_defensor("id_defensor");
				imputadoDTO.setTipo_defensor("tipo_defensor");
				if (solicitudMichDTO.getTipo_promocion().equals("3")){
					imputadoDTO.setColonia_id_imp("colonia_id_imp");
					imputadoDTO.setColonia_localidad_imp("colonia_localidad_imp");
					imputadoDTO.setColonia_municipio_imp("colonia_municipio_imp");
					imputadoDTO.setColonia_zona_imp("colonia_zona_imp");
					imputadoDTO.setColonia_estado_imp("colonia_estado_imp");
					imputadoDTO.setDomicilio_imp("domicilio_imp");
				}
			}
			imputadoDTO.setNombre("nombre");
			imputadoDTO.setSexo("sexo");
			imputadoDTO.setSituacion("situacion");
		}
		
		for (ImputadoDelitoDTO imputadoDelitoDTO : solicitudMichDTO.getImputadosDelitos()){
			imputadoDelitoDTO.setConsec_imp("consec_imp");
			imputadoDelitoDTO.setConsec_imp("delito");
			imputadoDelitoDTO.setConsec_imp("estado_psicofisico");
			imputadoDelitoDTO.setConsec_imp("fecha_delito");
			imputadoDelitoDTO.setConsec_imp("grado_consumacion");
			imputadoDelitoDTO.setConsec_imp("intencionalidad");
		}
		for (OfendidoDTO ofendidoDTO : solicitudMichDTO.getOfendidos()){
			ofendidoDTO.setAp_mater("ap_mater");
			ofendidoDTO.setAp_pater("ap_pater");
			ofendidoDTO.setNombre("nombre");
			ofendidoDTO.setOfendido("ofendido");
			ofendidoDTO.setSexo("sexo");
			ofendidoDTO.setTipo_parte("tipo_parte");
			ofendidoDTO.setTipo_persona("tipo_persona");
		}
		for (ImpDelOfendidoDTO impDelOfendidoDTO : solicitudMichDTO.getImpDeOfendidos()){
			impDelOfendidoDTO.setConsec_imp("consec_imp");
			impDelOfendidoDTO.setConsec_imp("delito");
			impDelOfendidoDTO.setConsec_imp("ofendido");
		}
		for (DocumentoWSMichDTO documentoWsMichDTO : solicitudMichDTO.getDocumentos()){
			documentoWsMichDTO.setArchivo("archivo");
			documentoWsMichDTO.setConsec_doc("consec_doc");
			documentoWsMichDTO.setFormato("formato");
			documentoWsMichDTO.setNombre("nombre");
		}
		
		return solicitudMichDTO;
	}

}
