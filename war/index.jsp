<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta property="og:title" content="Inspector de Intereses"/>
		<meta property="og:description" content="¡Estoy fiscalizando! El inspector de @votainteligente te muestra posibles conflictos de intereses."/>
		<meta property="og:image" content="http://app.inspectordeintereses.cl/Inspector/images/thumb_inspector.png"/>
		<meta name="gwt:property" content="locale=es_CL">
		<title>Inspector</title>
		<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-26017381-1']);
			_gaq.push(['_setDomainName', 'app.inspectordeintereses.cl']);

			(function() {
				var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			})();
		</script>
		<link type="text/css" rel="stylesheet" href="inspector.css">
		<link rel="shortcut icon" href="images/favicon.ico">
		<script type="text/javascript" src="inspector/inspector.nocache.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/highcharts.js"></script>
		<script type="text/javascript" src="js/highcharts-gwt-adapter.js"></script>
		<script type="text/javascript" src="js/recaptcha_ajax.js"></script>
	</head>

	<body class="body">
		<div id="headerLine">
			<div id="header">
				<a id="headLogo" href="http://www.inspectordeintereses.cl"></a>
				<a id="etiquetaFCI" href="http://www.ciudadanointeligente.cl">
					<img alt="Ciudadano Inteligente" src="images/logo-fci-tag.png" />
				</a>
			</div>
		</div>
		<div id="container">
			<div id="applicationContent"></div>
		</div>
		<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
		<noscript>
			<div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
				Your web browser must have JavaScript enabled
				in order for this application to display correctly.
			</div>
		</noscript>
	</body>
</html>
