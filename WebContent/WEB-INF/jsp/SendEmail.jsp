<!DOCTYPE html>
<html>
<head>
<style>
.EmailMessage {
	width: 100%;
	height: 500px;
}

#EmailMessage_ifr {
	height: 300px;
}

[type=submit] {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	border-color: #8c8c8c;
}

[type=submit]:hover {
	color: #333;
	background-color: #e6e6e6;
	border-color: #8c8c8c;
}

table.mceLayout, input.EmailMessage {
	width: 100% !important;
}
</style>
<script
	src="/webapps/vtbe-tinymce/tiny_mce/tiny_mce.js?v=9.1.201410.160373-1_11.6.1173427"></script>
</head>
<body>
	<form id="formEmail" method="POST">
		<textarea id="EmailMessage" name="EmailMessage">${EmailMessage}</textarea>
		<div style="display: none" id="data">
			<input id="EmailsData" type="hidden" value="${Emails}" /> 
			<input id="IdsData" type="hidden" value="${Ids}" /> 
			<input id="NamesData" type="hidden" value="${Names}" /> 
			<input id="StatusData" type="hidden" value="${Status}" />
			<input id="testEmail" type="hidden" value="false" />
		</div>
		<input id="btnEnviar" type="submit" value="Enviar Correos(s)" />
	</form>
	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
	<script>
		//Crear el Editor de texto en el textarea
		tinymce.init({
			selector : '#EmailMessage',
			toolbar : 'undo redo | styleselect | bold italic | link image',
			height : "560px",
		});

		var jqueryAlias = jQuery.noConflict();
		
		var htmlMessage = "";
		
		var requestMethod = "${Method}";
		if (requestMethod == "POST") {
			document.getElementById("formEmail").innerHTML = '${Response}';
			var error = "${Error}";
			if(error == "true") {
				window.alert("Sucedio un Error al enviar el/los Correo(s), intente de nuevo.");
				//window.close();
			} else {
				window.alert("Correo(s) enviado(s) con exito!");
				//window.close();
			}
			
			
		}
	</script>
</body>
</html>