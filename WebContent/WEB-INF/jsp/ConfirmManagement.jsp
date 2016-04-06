<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Management Submit</title>
</head>
<body>
${Sent}
<br>
${Results}
<script>
var Sent = '${Sent}';
var Id = '${Id}';
var Type = '${Type}';
var Img = "";
var Edit = "${Edit}";
var Results = "${Results}";
var Student_Response = "${Student_Response}";

	if(Sent == "true"){
		
		if(Edit == "false"){
			
			if(Type == "C"){
				
				Img = "Sent";
			}
			else{
				
				Img = "Response";
			}
			
			if(Student_Response == "NO CONTACTAR"){
				
				opener.document.getElementById("Status" + Id).children[0].src = '/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/gris.png';
				opener.document.getElementById("Chk" + Id).remove();
			}
			
			if(opener.document.getElementById(Img + Id) != null){
				
				opener.document.getElementById(Img + Id).src = '/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png';				
			}
			opener.alertify.success("Se ha registrado la gestion",1500);
		}
		else{
			
			opener.alertify.success("Se ha editado la gestion",1500);
			
			var PrevTable = opener.document.getElementById("TableH" + Id).cloneNode(true);
			document.importNode(PrevTable);
			
			var temp = document.createElement("div");
			temp.appendChild(PrevTable);
			opener.document.body.innerHTML = opener.document.body.innerHTML.replace(temp.innerHTML,Results);
		}
		
		window.close();
	}
	else{
		opener.console.log(Results);
		opener.alertify.error("Hubo un error al registrar la gestion, intente de nuevo",1500);
	}
</script>
</body>
</html>