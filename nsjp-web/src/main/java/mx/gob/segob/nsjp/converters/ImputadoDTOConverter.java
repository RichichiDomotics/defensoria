package mx.gob.segob.nsjp.converters;

import mx.gob.segob.nsjp.dto.solicitud.ImputadoDTO;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ImputadoDTOConverter implements Converter{

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(ImputadoDTO.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ImputadoDTO imputadoDTO =(ImputadoDTO) source;
		writer.addAttribute("ap_pater", imputadoDTO.getAp_pater());
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		ImputadoDTO imputadoDTO = new ImputadoDTO();
		imputadoDTO.setAp_pater(reader.getAttribute("ap_pater"));
		return imputadoDTO;
	}

}
