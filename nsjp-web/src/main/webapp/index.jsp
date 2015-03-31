<html>
	<head>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fakeLoader.css">
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js"></script>
		<script>
            /*window.onload = detectarCarga;
            function detectarCarga(){
                document.getElementById("imgLOAD").style.display="none";
            }*/

            var ventanaPrincipal;
            function loadVentanaPrincipal() {
                //ventanaPrincipal=window.open('<%= request.getContextPath() %>/cargarLogin.do','ventanaPrincipal','toolbar=no,location=no,directories=no,status=no, menubar=no,scrollbars=no,resizable=no,width=' + window.screen.width + ',height=' + window.screen.height);
                ventanaPrincipal=window.open('<%= request.getContextPath() %>/cargarLogin.do','_self');
            }
		</script>
        <style>
            #circular3dG{
                position:relative;
                width:128px;
                height:128px;
                align: center;
                valign: center;
            }

            .circular3dG{
                position:absolute;
                background-color:#51a651/*#FFFFFF*/;
                width:36px;
                height:36px;
                -moz-border-radius:38px;
                -moz-animation-name:bounce_circular3dG;
                -moz-animation-duration:1.04s;
                -moz-animation-iteration-count:infinite;
                -moz-animation-direction:linear;
                -webkit-border-radius:38px;
                -webkit-animation-name:bounce_circular3dG;
                -webkit-animation-duration:1.04s;
                -webkit-animation-iteration-count:infinite;
                -webkit-animation-direction:linear;
                -ms-border-radius:38px;
                -ms-animation-name:bounce_circular3dG;
                -ms-animation-duration:1.04s;
                -ms-animation-iteration-count:infinite;
                -ms-animation-direction:linear;
                -o-border-radius:38px;
                -o-animation-name:bounce_circular3dG;
                -o-animation-duration:1.04s;
                -o-animation-iteration-count:infinite;
                -o-animation-direction:linear;
                border-radius:38px;
                animation-name:bounce_circular3dG;
                animation-duration:1.04s;
                animation-iteration-count:infinite;
                animation-direction:linear;
            }

            #circular3d_1G{
                left:52px;
                top:8px;
                -moz-animation-delay:0.39s;
                -webkit-animation-delay:0.39s;
                -ms-animation-delay:0.39s;
                -o-animation-delay:0.39s;
                animation-delay:0.39s;
            }

            #circular3d_2G{
                left:78px;
                top:30px;
                -moz-animation-delay:0.52s;
                -webkit-animation-delay:0.52s;
                -ms-animation-delay:0.52s;
                -o-animation-delay:0.52s;
                animation-delay:0.52s;
            }

            #circular3d_3G{
                left:94px;
                top:58px;
                -moz-animation-delay:0.65s;
                -webkit-animation-delay:0.65s;
                -ms-animation-delay:0.65s;
                -o-animation-delay:0.65s;
                animation-delay:0.65s;
            }

            #circular3d_4G{
                left:88px;
                top:86px;
                -moz-animation-delay:0.78s;
                -webkit-animation-delay:0.78s;
                -ms-animation-delay:0.78s;
                -o-animation-delay:0.78s;
                animation-delay:0.78s;
            }

            #circular3d_5G{
                left:54px;
                top:94px;
                -moz-animation-delay:0.9099999999999999s;
                -webkit-animation-delay:0.9099999999999999s;
                -ms-animation-delay:0.9099999999999999s;
                -o-animation-delay:0.9099999999999999s;
                animation-delay:0.9099999999999999s;
            }

            #circular3d_6G{
                left:10px;
                top:62px;
                -moz-animation-delay:1.04s;
                -webkit-animation-delay:1.04s;
                -ms-animation-delay:1.04s;
                -o-animation-delay:1.04s;
                animation-delay:1.04s;
            }

            #circular3d_7G{
                left:0px;
                top:18px;
                -moz-animation-delay:1.1700000000000002s;
                -webkit-animation-delay:1.1700000000000002s;
                -ms-animation-delay:1.1700000000000002s;
                -o-animation-delay:1.1700000000000002s;
                animation-delay:1.1700000000000002s;
            }

            #circular3d_8G{
                left:22px;
                top:0px;
                -moz-animation-delay:1.3s;
                -webkit-animation-delay:1.3s;
                -ms-animation-delay:1.3s;
                -o-animation-delay:1.3s;
                animation-delay:1.3s;
            }

            @-moz-keyframes bounce_circular3dG{
                0%{
                    -moz-transform:scale(1)}

                100%{
                    -moz-transform:scale(.3)}

            }

            @-webkit-keyframes bounce_circular3dG{
                0%{
                    -webkit-transform:scale(1)}

                100%{
                    -webkit-transform:scale(.3)}

            }

            @-ms-keyframes bounce_circular3dG{
                0%{
                    -ms-transform:scale(1)}

                100%{
                    -ms-transform:scale(.3)}

            }

            @-o-keyframes bounce_circular3dG{
                0%{
                    -o-transform:scale(1)}

                100%{
                    -o-transform:scale(.3)}

            }

            @keyframes bounce_circular3dG{
                0%{
                    transform:scale(1)}

                100%{
                    transform:scale(.3)}

            }
        </style>
	</head>
<body class="marcadeagua" onLoad="javascript:loadVentanaPrincipal();" style="background-color: #FFFFFF/*#51a651*/">
<!--Cargando Aplicacion .....-->
<!--div id="imgLOAD" style="text-align:center;">
    <img src="<%=request.getContextPath()%>/resources/images/logo_nsjph.jpg" />
    <div id="waiting4"></div>
    <br>
    <b>Cargando...</b>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-waiting.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $('#waiting4').waiting({
                className: 'waiting-circles',
                elements: 8,
                radius: 20,
                auto: true
            });
        });
    </script>
</div-->
<div style="position: absolute; top: 50%; left: 50%; margin: -99px 0 0 -66px;">
    <div id="circular3dG">
        <div id="circular3d_1G" class="circular3dG">
        </div>
        <div id="circular3d_2G" class="circular3dG">
        </div>
        <div id="circular3d_3G" class="circular3dG">
        </div>
        <div id="circular3d_4G" class="circular3dG">
        </div>
        <div id="circular3d_5G" class="circular3dG">
        </div>
        <div id="circular3d_6G" class="circular3dG">
        </div>
        <div id="circular3d_7G" class="circular3dG">
        </div>
        <div id="circular3d_8G" class="circular3dG">
        </div>
    </div>
    <br>
    <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cargando...</b>
</div>
</body>
</html>
 