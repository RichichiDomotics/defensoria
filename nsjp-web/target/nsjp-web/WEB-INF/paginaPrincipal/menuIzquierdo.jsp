<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!-- SE INICIALIZAN LOS MENUS UNA VEZ QUE LA PAGINA HA TERMINADO DE CARGAR -->
	<script type="text/javascript">
	
	function restablecerAcordeones(){
		$("#accordionmenuprincipal").accordion("resize");
		$("#accordionmenuderprincipal").accordion("resize");	
	}
	
	
			<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO">
				<logic:iterate name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO" id="elementoMenuAcordeon" >
				
						var <bean:write name="elementoMenuAcordeon" property="cIdHTML" />Config = {
								"strings" : {
								            "loading"		: "Cargando...",
								            "new_node"	: "Nuevo Nodo",
								            "multiple_selection" : "Selección Multiple"
								},
					        	"themes" : {
						            "theme" : "classic",
						            "dots" : true,
						            "icons" : true
						        },						
								"html_data" : {
						            "ajax" : {
							            "url" : contextoPagina + '/obtenerMenu.do',
							            "cache": false,
	                					"data":	function(n){ 
					       					var id  = n.attr ? n.attr("id") : 0;
					       					var tipoMenu;
					       					var cForward;
											if (id == 0) {
												id = <bean:write name="elementoMenuAcordeon" property="elementoMenuId" />;
												tipoMenu = '<bean:write name="elementoMenuAcordeon" property="cIdHTML" />';
												cForward = '<bean:write name="elementoMenuAcordeon" property="cForward" />';
											}else{
												tipoMenu = n.find('a').attr("id");
												cForward = n.find('a').attr("cForward");
											}
											return {root: id, tipoMenu:tipoMenu, cForward: cForward};
	                					}
	                				}
						        },
						        "types" : {
						            "valid_children" : [ "root" ],
						            "types" : {
						                "turnos_leaf" : {
						                    "icon" : {
							                        "image" : contextoPagina + "/resources/images/icn_turno.png"
						                    }
						                },
						                "default" : {
							                "select_node": function(e) {
										    	this.toggle_node(e);
										    	return true;
										  	}
						                }
						            }
						        },
								// the `plugins` array allows you to configure the active plugins on this instance
								"plugins" : ["themes","html_data","ui","types"],
								// each plugin you have included can have its own config object
								"core" : {  }
								// it makes sense to configure a plugin only if overriding the defaults
							};					
				</logic:iterate>	
			</logic:notEmpty>		
	
	
		$(document).ready(function() {
			$("#accordionmenuprincipal").accordion(
				{
					fillSpace: true,
				   	changestart: function(event, ui) {
       					ui.newContent.css('overflow' , 'hidden');
   					},
   					change: function(event, ui) { 
     					ui.newContent.css('overflow' , 'auto');
   					}
				}
			);
			
			<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO">
				<logic:iterate name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO" id="elementoMenuAcordeon" >
						$("#<bean:write name="elementoMenuAcordeon" property="cIdHTML" />")
						.jstree(<bean:write name="elementoMenuAcordeon" property="cIdHTML" />Config);					
				</logic:iterate>	
			</logic:notEmpty>		
			
		});	
		
	</script>

<div class="ui-layout-west">
	<div class="header">&nbsp;</div>
	<div class="content">
		<div id="accordionmenuprincipal">
			<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO">
				<logic:iterate name="KEY_SESSION_MENU_DINAMICO_IZQUIERDO" id="elementoMenuAcordeon" >
					<h3 id="menu_<bean:write name="elementoMenuAcordeon" property="elementoMenuId" />">
						<a href="javascript:void(0);" onclick='<bean:write name="elementoMenuAcordeon" property="cComando" />'>						
							<img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">
							&nbsp;<bean:write name="elementoMenuAcordeon" property="cNombre" />
						</a>
					</h3>
					<div>
						<logic:notEmpty name="elementoMenuAcordeon" property="elementoMenuHijosDTO">
							<div id="<bean:write name="elementoMenuAcordeon" property="cIdHTML" />" class="demo">
							</div>
						</logic:notEmpty>
					</div>
				</logic:iterate>					
			</logic:notEmpty>
		</div>
	</div>
</div>