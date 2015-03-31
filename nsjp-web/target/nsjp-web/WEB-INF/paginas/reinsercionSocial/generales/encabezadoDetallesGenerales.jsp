
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
		<fieldset style="height: 100%; min-height: 173px;">
			<table border="0">
				<tr>
					<td colspan="2">
						<table border="0">
							<tr>
								<td align="right"><strong>Caso:</strong></td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="caso" readonly="true" styleId="caso" styleClass="texto" />
								</td>
							</tr>
							<tr>
								<td align="right"><strong>Causa:</strong></td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="causa" readonly="true" styleId="causa" styleClass="texto" />
								</td>
							</tr>
							<tr>
								<td align="right"><strong>Carpeta:</strong></td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="carpeta" readonly="true" styleId="carpeta" styleClass="texto" />
								</td>
							</tr>
						</table>
					</td>
					<td colspan="1">
						<table>
							<tr>
								<td align="right">Nombre(s):</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="nombre" readonly="true" size="30"/>
								</td>
							</tr>
							<tr>
								<td align="right">Apellido Paterno:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="apellidoPaterno" readonly="true" size="30"/>
								</td>
							</tr>
							<tr>
								<td align="right">Apellido Materno:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="apellidoMaterno" readonly="true" size="30" />
								</td>
							</tr>
						</table>
					</td>
					<td colspan="2">
						<table>
							<tr>
								<td align="right">Número Único de Sentenciado (NUS):</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="nus" readonly="true" styleId="carpeta" styleClass="texto"/>
								</td>
							</tr>
							<tr>
								<td align="right">Edad biológica:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="edad" readonly="true" size="10" styleId="edad"/>
								</td>
							</tr>
							<tr>
								<td align="right">¿Present&oacute; lesiones?</td>
								<td align="left">
									S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="lesionado" title="Sí" value="1" disabled="true" /> 
									No <html:radio name="DatosGeneralesReinsercionForm" property="lesionado" title="No" value="0" disabled="true" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<hr/>
					</td>
				</tr>
			</table>
		</fieldset>
		
<html:hidden name="DatosGeneralesReinsercionForm" property="expedienteId" styleId="expedienteId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="numeroExpedienteId" styleId="numeroExpedienteId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="sentenciaId" styleId="sentenciaId" />