/**
 * Nombre del Programa : Indicadores.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeracion que lista los distintas consultas para generar los indicadores 
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.comun.indicador;

import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.PGJ;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.PJ;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.DEF;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.SSP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;

/**
 * Enumeracion que lista los distintas consultas para generar los indicadores
 * 
 * @author GustavoBP
 */
public enum Indicadores {
	INDICADOR_1(1L,
			"Incidencia de delitos estatal",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("ClaveDelito","Delito", "Total"),
			"DECLARE @cClaveDelito [cadena 10]"+
			"DECLARE @query [cadena 300]"+
			"SELECT @cClaveDelito =''"+
			"Select @query ='"+
			" SELECT CD.cClaveDelito, CD.cNombre AS Delito, COUNT (*) as Total " +
			" FROM Expediente E, Delito D, CatDelito CD" +
			" WHERE E.Expediente_id =D.Expediente_id  " +
			" AND D.CatDelito_id = CD.CatDelito_id' " +
			"  " +
			" IF (@cClaveDelito>0) BEGIN " +
			" SELECT  @query = @query + ' AND CD.cClaveDelito = '''+@cClaveDelito+'''' END"+
			" SELECT  @query = @query +' AND E.dFechaCreacion between '''+:fechaIncio+''' AND  DATEADD(day, 1,'''+:fechaFin+''')' " +
			" SELECT  @query = @query +' group by  CD.cClaveDelito,CD.cNombre' exec (@query)"),
	INDICADOR_2(2L,
			"Incidencia de delitos distrital",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("ClaveDelito","Delito","Distrito","Total"),
			"DECLARE @cClaveDelito [cadena 10]"+
			"DECLARE @query nvarchar(max)"+
			"SELECT @cClaveDelito =''"+
			"Select @query ='"+
			" SELECT CD.cClaveDelito,CD.cNombre as Delito,Dist.cNombre as Region, COUNT(*) Total " +
			" FROM Expediente E,Delito D, CatDelito CD, CatDiscriminante Dis, CatDistrito Dist" +
			" WHERE E.Expediente_id =D.Expediente_id  " +
			" AND D.CatDelito_id = CD.CatDelito_id " +
			" AND E.catDiscriminante_id = Dis.catDiscriminante_id" +
			" AND Dis.catDistrito_id = Dist.catDistrito_id ' " +
			" IF (@cClaveDelito>0) BEGIN " +
			" SELECT  @query = @query + ' AND CD.cClaveDelito = '''+@cClaveDelito+'''' " +
			" END"+
			" SELECT  @query = @query +' AND E.dFechaCreacion between '''+:fechaIncio+''' AND  DATEADD(day, 1,'''+:fechaFin+''')' " +
			" SELECT  @query = @query +' group by CD.cClaveDelito,CD.cNombre, Dist.cNombre' exec (@query)"),
	INDICADOR_4(4L,
			"Avance de investigaciones",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Funcionario","Estatus", "Total"),
			" SELECT F.cNombreFuncionario+'-'+F.cApellidoPaternoFuncionario+'-'+F.cApellidoMaternoFuncionario AS 'Funcionario',V.cValor AS 'Estatus',COUNT (*) as 'Total' " +
			" FROM Expediente E, NumeroExpediente NE, Funcionario F, Valor V " +
			" WHERE E.Expediente_id =NE.Expediente_id  " +
			" AND NE.JerarquiaOrganizacional_id = 10 " +
			" AND NE.iClaveFuncionario = F.iClaveFuncionario " +
			" AND NE.Estatus_val = V.Valor_id "+
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " +
			" GROUP BY F.cNombreFuncionario,F.cApellidoPaternoFuncionario,F.cApellidoMaternoFuncionario,V.cValor "),
	
	INDICADOR_6(6L,
			"Aumento de denuncias",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeDenuncias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeDenuncias' " +
			" FROM Expediente E, CatDiscriminante cD, CatDistrito D " +
			" WHERE origen_val = 2078  " +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id " +
			" AND cD.catDiscriminante_id = D.catDistrito_id " +
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " +
			" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_7(7L,
			"Cantidad de Delitos cometidos en cada uno de los distritos, ordenado de mayor a menor incidencia.",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Clave Delito","Delito", "Distrito", "Total"),
			" SELECT CD.cClaveDelito,CD.cNombre,Dist.cNombre as Region, COUNT (NDM.cSexo)  as Total" +
			" FROM Delito D, CatDelito CD, DelitoPersona DPM, Involucrado IM,NombreDemografico NDM,Expediente E, " +
			" CatDiscriminante Dis, CatDistrito DisT" +
			" WHERE D.CatDelito_id = CD.CatDelito_id" +
			" AND D.Delito_id =DPM.Delito_id" +
			" AND DPM.ProbableResponsable_id=IM.Involucrado_id" +
			" AND IM.Involucrado_id = NDM.Persona_id" +
			" AND E.Expediente_id = D.Expediente_id" +
			" AND E.catDiscriminante_id = Dis.catDiscriminante_id" +
			" AND Dis.catDistrito_id = DisT.catDistrito_id" +
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " +
			" GROUP BY  Dist.cNombre,CD.cClaveDelito,CD.cNombre" +
			" ORDER BY  Dist.cNombre ,Total Desc"),
	INDICADOR_8(8L,
			"Mayor número de sentencias condenatorias",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia\\Tribunal", "#NumeroDeSentencias"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia\\Tribunal', COUNT (*) AS '#NumeroDeSentencias'  "+
					" FROM  Sentencia S,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE TipoSentencia_val in (323,324) "+
					" AND S.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND cD.catDiscriminante_id = E.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.dFechaInicioPena between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_9(9L,
			"Actas levantadas en el modulo de Atención por edificios",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Edificio/Agecia","Total Actas"),
			" SELECT Dis.cNombre as 'Edificio/Agecia', COUNT (*) As 'totalActasAtencionTemprana'" +
			" FROM Expediente E, NumeroExpediente NE, CatDiscriminante cD, CatDistrito D, CatDiscriminante Dis" +
			" WHERE E.Expediente_id= NE.Expediente_id" +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id" +
			" AND cD.catDistrito_id = D.catDistrito_id" +
			" AND NE.JerarquiaOrganizacional_id = 45" +
			" AND Dis.catDiscriminante_id = E.catDiscriminante_id" +
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " +
			" GROUP BY Dis.cNombre"),
	INDICADOR_10(10L,
			"Percepción de justicia",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeConvenios"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#DeConvenios' "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id  "+
					" AND NE.Expediente_id = E.Expediente_id  "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaFin  between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
			
	INDICADOR_11(11L,
			"Aplicación correcta de Mecanismos Alternativos de Solución de Controversias",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeConvenios"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#DeConvenios' "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id  "+
					" AND NE.Expediente_id = E.Expediente_id  "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaFin  between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_12(12L,
			"Cantidad de casos atendidos en el modulo ",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Modulo","Total"),
			" SELECT 'Atención en CJR' as Modulo, COUNT (*) as Total" +
			" FROM  NumeroExpediente NE ,Expediente E" +
			" WHERE NE.Expediente_id = E.Expediente_id" +
			" AND NE.JerarquiaOrganizacional_id = 7" +
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " ),
	INDICADOR_13(13L,
			"Cantidad de personas que acudieron al modulo de atencion temprana en general",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Modulo","IndiceDeAtencion"),
			" SELECT  'Atención  Temprana' as Modulo,COUNT (*) As 'IndiceDeAtencion'" +
			" FROM Expediente E, NumeroExpediente NE, CatDiscriminante cD, CatDistrito D, CatDiscriminante Dis" +
			" WHERE E.Expediente_id= NE.Expediente_id" +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id" +
			" AND cD.catDistrito_id = D.catDistrito_id" +
			" AND NE.JerarquiaOrganizacional_id = 45" +
			" AND Dis.catDiscriminante_id = E.catDiscriminante_id" +
			" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) " ),
	INDICADOR_14(14L,
			"Difusión del principio de publicidad del NSJP",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "FechaDeSolicitud", "AdienciasPublicas"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#AdienciasPublicas' "+
					" FROM  Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND A.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND A.TipoAudiencia_val not in (1718,1717,292) "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_17(17L,
			"Atender puntualmente todas las solicitudes de audiencia",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "FechaDeSolicitud", "FechaDeAtencion"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', "+
					" a.Audiencia_id,Doc.dFechaCreacion as 'FechaDeSolicitud',A.dFechaAudiencia AS 'FechaDeAtencion' "+
					" FROM SolicitudAudiencia SA, Solicitud S, Audiencia A,Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE SA.SolicitudAudiencia_id = S.Solicitud_id "+
					" AND A.Audiencia_id = SA.Audiencia_id "+
					" AND Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND A.dFechaAudiencia  is not null "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val = 1771 "+
					" AND cD.catDistrito_id = D.catDistrito_id "),
					
	INDICADOR_18(18L,
			"Dictar autos a la totalidad de las solicitudes y promociones formuladas al Poder Judicial",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusCerrado"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+'-'+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusCerrado' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val <> 1771 "+
					" AND S.Estatus_val = 1776  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor "),
	INDICADOR_24(24L,
			"Atención a Víctimas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusEnProceso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+'-'+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusEnProceso' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val in (2809,2810,2811) "+
					" AND S.Estatus_val = 2096  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor "),
	INDICADOR_25(25L,
			"Ejecución del módulo de Atención a Víctimas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusCerrado"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+'-'+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusCerrado' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val in (2809,2810,2811)  "+
					" AND S.Estatus_val = 1776 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor "),
	INDICADOR_26(26L,
			"Ejecución del módulo de acción penal privada",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeDenuncias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeDenuncias' "+
					" FROM Expediente E, CatDiscriminante cD, CatDistrito D, Turno T "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDiscriminante_id = D.catDistrito_id "+
					" AND E.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" AND T.Expediente_id = E.Expediente_id "+
					" AND T.cTipoTurno ='JUDICIAL' "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_29(29L,
			"Justicia Alternativa",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ExpedientesCanalizadosJAR"),
			" SELECT  cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ExpedientesCanalizadosJAR' "+
					" FROM NumeroExpediente NE ,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND NE.JerarquiaOrganizacional_id = 7 "+
					" AND NE.dFechaApertura  between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_30(30L,
			"Ejecución del Módulo de Justicia Alternativa Restaurativa (mediación, conciliación y justicia restaurativa)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ExpedientesCanalizadosJAR"),
			" SELECT  cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ExpedientesCanalizadosJAR' "+
					" FROM NumeroExpediente NE ,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND NE.JerarquiaOrganizacional_id = 11 "+
					" AND NE.dFechaApertura  between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_32(32L,
			"Protección al inocente",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#SolicitudDefensor"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) AS '#SolicitudDefensor' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val <> 1772 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_33(33L,
			"Dar vista oportuna al Defensor Público en caso de detención del imputado",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#SolicitudDefensor"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) AS '#SolicitudDefensor' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val <> 1772 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_34(34L,
			"Notificación de fecha, hora, lugar y juez de audiencia al defensor público",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#Notificaciones"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#Notificaciones' "+
					" FROM Notificacion N, Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE N.Audiencia_id = A.Audiencia_id "+
					" AND A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND dFechaCitado between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_36(36L,
			"Evitar impunidad",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "Tipo", "#Total"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal','Convenio' AS 'Tipo',COUNT (*) as '#Total'   "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaInicio between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "+
					" UNION "+
					" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal','Medida Cautelar' AS 'Tipo',COUNT (*) as '#Total' "+
					" FROM Medida M , MedidaCautelar MC, NumeroExpediente NE , Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE M.Medida_id = MC.MedidaCautelar_id "+
					" AND M.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND M.dFechaInicio between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_37(37L,
			"Ejecutar módulo de acuerdos reparatorios",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "ConveniosCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConveniosCumplidos'   "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaInicio between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_38(38L,
			"Ejecutar módulo de medidas cautelares",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Tribunal", "#MedidasCautelares"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#MedidasCautelares' "+
					" FROM Medida M , MedidaCautelar MC, NumeroExpediente NE , Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE M.Medida_id = MC.MedidaCautelar_id "+
					" AND M.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND M.dFechaInicio between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_39(39L,
			"Sustentación del Fiscal Investigador de la Teoría del Caso",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#TeoriaDelCaso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#TeoriaDelCaso'  "+
					" FROM OficioEstructurado OE,Documento Doc, Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE OE.TipoOficio_val = 2382 "+
					" AND OE.Documento_id = Doc.Documento_id "+
					" AND Doc.Documento_id = A.Documento_id "+
					" AND A.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_42(42L,
			"Esclarecimiento de los hechos",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#TeoriaDelCaso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#TeoriaDelCaso'  "+
					" FROM OficioEstructurado OE,Documento Doc, Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE OE.TipoOficio_val = 2382 "+
					" AND OE.Documento_id = Doc.Documento_id "+
					" AND Doc.Documento_id = A.Documento_id "+
					" AND A.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND Doc.dFechaCreacion between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_47(47L,
			"Reparación del daño",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ConvenioCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConvenioCumplidos'   "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaFin between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_48(48L,
			"Reclamación de reparación del daño del fiscal investigador al imputado, en la formulación de imputación y acusación",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ConveniosCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Region',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConveniosCumplidos'   "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND C.dFechaInicio between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_51(51L,
			"Ejecución de audiencia pública",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#Audiencias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',V.cValor as 'TiposAudiencia',COUNT (*) as '#Audiencias' "+
					" FROM Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D, Valor V "+
					" WHERE A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND A.dFechaAudiencia  between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" AND V.Valor_id = A.TipoAudiencia_val "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,cValor "),
	INDICADOR_52(52L,
			"Solicitud al Poder Judicial, de señalamiento de fecha,  hora y lugar de audiencias para dar impulso al proceso penal",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#SolicitudesAuidiencia"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) as '#SolicitudesAuidiencia' "+
					" FROM SolicitudAudiencia SA, Solicitud S, Audiencia A,Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE SA.SolicitudAudiencia_id = S.Solicitud_id "+
					" AND A.Audiencia_id = SA.Audiencia_id "+
					" AND Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND A.dFechaAudiencia  is not null "+
					" AND Doc.dFechaCreacion between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" AND S.TipoSolicitud_val = 1771 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_53(53L,
			"Ejecución del principio de igualdad entre las partes",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Tribunal", "#Notificaciones"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#Notificaciones' "+
					" FROM Notificacion N, Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE N.Audiencia_id = A.Audiencia_id "+
					" AND A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND dFechaCitado between :fechaIncio AND  DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_55(5L,
			"Ejecución de sanciones",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#NumeroDeSentencias"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#NumeroDeSentencias'  "+
					" FROM  Sentencia S,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE TipoSentencia_val in (323,324) "+
					" AND S.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND cD.catDiscriminante_id = E.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.dFechaInicioPena between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_56(56L,
			"Restaurar la armonía social",
			SSP,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#EjecucionRS"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#EjecucionRS'   "+
					" FROM NumeroExpediente NE, Expediente E,CatDiscriminante, CatDiscriminante cD,CatDistrito D "+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND NE.JerarquiaOrganizacional_id = 60 "+
					" AND NE.dFechaApertura between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre "),
	INDICADOR_57(57L,
			"Número de audiencias programadas, reprogramadas y canceladas",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","estadoProgramada","estadoReprogramada","estadoCancelada"),
			Arrays.asList("Estado audiencia","#Audiencias"),
			"SELECT v.cValor as estadoAudiencia, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.Estatus_val = v.Valor_id "+
					" WHERE v.Valor_id in (:estadoProgramada, :estadoReprogramada, :estadoCancelada) " +
					" AND a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin)"+
					" GROUP BY v.cValor "+
					" ORDER BY v.cValor "),
	INDICADOR_58(58L,
			"Número de audiencias por causa",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Número de causa","#Audiencias"),
			"SELECT ne.cNumeroExpediente, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN NumeroExpediente ne "+
					" ON a.NumeroExpediente_id = ne.NumeroExpediente_id "+
					" WHERE a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY ne.cNumeroExpediente "+
					" ORDER BY ne.cNumeroExpediente "),
	INDICADOR_59(59L,
			"Número de audiencias programadas vs realizadas",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","estadoProgramada","estadoFinalizada"),
			Arrays.asList("Estado audiencia","#Audiencias"),
			"SELECT v.cValor as EstadoAudiencia, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.Estatus_val = v.Valor_id "+
					" WHERE v.Valor_id in (:estadoProgramada, :estadoFinalizada) " +
					" AND a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY v.cValor "+
					" ORDER BY v.cValor "),
	INDICADOR_60(60L,
			"Tiempo programado vs tiempo real de audiencia",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Folio audiencia", "Fecha inicio de audiencia", "Fecha fin de audiencia", "Duración estimada", "Duración real"),
			"SELECT ISNULL(cFolioAudiencia,'Sin folio') as folioAudiencia, dFechaAudiencia, dFechaHoraFin, " +
					" ISNULL(iDuracionEstimada,0) as duracionEstimada, DATEDIFF(minute,dFechaAudiencia,dFechaHoraFin) as duracionReal "+
					" FROM Audiencia a " +
					" WHERE a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" ORDER BY ISNULL(cFolioAudiencia,'Sin folio')"),
	INDICADOR_61(61L,
			"Número de audiencias por juez",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","rolIdJuez"),
			Arrays.asList("Juez","#Audiencias"),
			"SELECT f.cNombreFuncionario+' '+f.cApellidoPaternoFuncionario+' '+" +
					"f.cApellidoMaternoFuncionario as nombreCompleto, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN FuncionarioAudiencia fa "+
					" ON a.Audiencia_id = fa.Audiencia_id "+
					" INNER JOIN Funcionario f "+
					" ON f.iClaveFuncionario = fa.iClaveFuncionario "+
					" INNER JOIN Usuario u "+
					" ON f.iClaveFuncionario = u.iClaveFuncionario "+
					" INNER JOIN UsuarioRol ur "+
					" ON u.Usuario_id = ur.Usuario_id "+
					" WHERE ur.Rol_id = :rolIdJuez " +
					" AND a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY f.cNombreFuncionario+' '+f.cApellidoPaternoFuncionario+' '+f.cApellidoMaternoFuncionario"),
	INDICADOR_62(62L,
			"Número de audiencias por tipo",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Tipo de audiencia","#Audiencias"),
			"SELECT v.cValor, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.TipoAudiencia_val = v.Valor_id "+
					" WHERE a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY v.cValor"),
	INDICADOR_63(63L,
			"Número de audiencias por sala",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Nombre de sala","#Audiencias"),
			"SELECT ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) as nombreSala, "+
					" count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" LEFT JOIN SalaAudiencia sa "+
					" ON a.SalaAudiencia_id = sa.SalaAudiencia_id "+
					" LEFT JOIN Sala s "+
					" ON s.Sala_id = s.Sala_id "+
					" LEFT JOIN SalaTemporal st "+
					" ON a.SalaTemporal_id = st.SalaTemporal_id "+
					" WHERE a.dFechaAudiencia between :fechaIncio AND DATEADD(day, 1,:fechaFin) "+
					" GROUP BY ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) "+
					" ORDER BY ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) ");
	
	private Long indicadorId;
	private String titulo;
	private Instituciones institucion;
	private List<String>  nombreColumnas;
	private List<String>  parametros;
	private String consulta;
	
	private final static Map<Long, Indicadores> hash = new HashMap<Long, Indicadores>();
    static {
        Indicadores[] objs = Indicadores.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getIndicadorId(), objs[pos]);
            pos++;
        }
    }
	
	/**
	 * @param indicadorId
	 * @param titulo
	 * @param institucion
	 * @param nombreColumnas
	 * @param parametros
	 * @param consulta
	 */
	private Indicadores(Long indicadorId, String titulo,
			Instituciones institucion, List<String> parametros,
			List<String> nombreColumnas, String consulta) {
		this.indicadorId = indicadorId;
		this.titulo = titulo;
		this.institucion = institucion;
		this.nombreColumnas = nombreColumnas;
		this.parametros = parametros;
		this.consulta = consulta;
	}
	
	 /**
     * 
     * @param valorIdPredefinido
     * @return
     */
    public static Indicadores getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }
    
	/**
	 * @return the indicadorId
	 */
	public Long getIndicadorId() {
		return indicadorId;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @return the institucion
	 */
	public Instituciones getInstitucion() {
		return institucion;
	}
	/**
	 * @return the nombreColumnas
	 */
	public List<String> getNombreColumnas() {
		return nombreColumnas;
	}
	/**
	 * @return the parametros
	 */
	public List<String> getParametros() {
		return parametros;
	}
	/**
	 * @return the consulta
	 */
	public String getConsulta() {
		return consulta;
	}
}
