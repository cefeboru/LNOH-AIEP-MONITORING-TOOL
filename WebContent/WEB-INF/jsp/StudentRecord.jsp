<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Historial</title>
	</head>
	<body>
		<div class="content">
	      <table width="99%" border="0" class="tablaHistDatos" align="center">
	        <tbody>
	          <tr>
	            <td class="titleColumna">ESTUDIANTE : </td>
	            <td id="StuName" width="35%"></td>
	            <td class="titleColumna">RUT : </td>
	            <td id="StuRut"></td>
	            <td class="titleColumna">FONO : </td>
	            <td id="StuPhone"></td>
	          </tr>
	          <tr>
	            <td class="titleColumna">EMAIL : </td>
	            <td id="StuEmail"></td>
	            <td class="titleColumna">SEDE : </td>
	            <td id="StuHeadquarter"></td>
	            <td class="titleColumna">Último Acc. : </td>
	            <td id="StuLastAccess"></td>
	          </tr>
	          <tr>
	            <td colspan="6" align="center" class="celdaColumna">
	                <img src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/xls.png" style="height: 30px;">&nbsp;<label id="DownloadReport" style="font-size: 110%;font-Weight: bold;cursor: pointer;">Descargar Listado</label>      
	            </td>
	          </tr>
	          </tbody>
	      </table>
	        <br>
			${HTMLData}
			<div id="dvData" style="display:none;"></div>
    	</div>
	</body>

	<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.min.js"></script>
	<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
	<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
	<script>
	
	var ManageWindow = null;
	
	$( document ).ready( function() {
		
		$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/estilo.css"/>');
		$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.core.css" />');
		$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.default.css" />');
		$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/bootstrap.min.css"/>');
		$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/jquery.bootgrid.min.css"/>');
		
		$("[id^=Edit]").each(function(){this.onclick = function(){Management(this);};});
		$("[id^=Status]").each(function(){this.innerHTML = this.innerHTML.replace("?","");});
	});
	
	function Management(e){

    	var width = 755;
    	var height = 405;

    	var top = screenTop/2;						
    	var left = screenLeft/2;
    	
    	if(ManageWindow != null){
    		
    		ManageWindow.close();
    		ManageWindow = null;
    	}

    	ManageWindow = window.open("/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentManagement",'course_browse','width='+width+',height='+height+',resizable=yes,scrollbars=yes,status=yes,top='+top+',left='+left);

    	ManageWindow.opener = self;

    	var Id = e.id.replace("Edit","");

    	var Email = "";

    	var Name = document.getElementById("StuName").innerHTML;

    	var Today = document.getElementById("DateSent" + Id).innerHTML;
    	
    	var Temp = Today.split("/");
    	
		var Today = Temp[2] + "/" + Temp[1] + "/" + Temp[0];

    	var Channel = document.getElementById("Channel" + Id).innerHTML;
    	
    	var Observations = document.getElementById("Observations" + Id).innerHTML; 
    	
    	var Status = document.getElementById("Status" + Id).innerHTML;
    	
    	var Type = e.title;

    	var Interval1 = setInterval(function(){

    		if(ManageWindow.document.getElementById("Name") != null && ManageWindow.document.getElementById("StuDateSent") != null && ManageWindow.document.getElementById("Status") != null && ManageWindow.document.getElementById("SendQ") != null && ManageWindow.Inputs != null && ManageWindow.document.getElementById("StuE") != null){

    			clearInterval(Interval1);

    			ManageWindow.document.getElementById("Name").innerHTML = Name;
    			ManageWindow.document.getElementById("StuDateSent").value = Today;
    			ManageWindow.document.getElementById("Status").innerHTML = Status;
    			ManageWindow.document.getElementById("StuContact_Channel").value = Channel;
    			ManageWindow.document.getElementById("StuObservations").value = Observations;
    			
    			
    			if(Type == "E"){
    				
    				ManageWindow.document.getElementById("StuE").click();
    				var StudentResponse = document.getElementById("StudentResponse" + Id).innerHTML;
    				ManageWindow.document.getElementById("StuStudent_Response").value = StudentResponse;
    			}

    			ManageWindow.document.getElementById("SendQ").onclick = function(){
    				
    				if(ManageWindow.Inputs.IsValid()){

    					var a = ManageWindow.document.createElement("a");
    					
    					if(ManageWindow.Inputs.Type == "C"){
    						
    						ManageWindow.Inputs.Student_Response.value = "";
    					}
    					
    					var Temp = ManageWindow.Inputs.DateSent.value.split("/");
    					var DateSent = Temp[2] + "/" + Temp[1] + "/" + Temp[0];
						var Status = ManageWindow.document.getElementById("StatusText").innerHTML;
						
    					var href = "/webapps/lnoh-AIEPMTOOL-BBLEARN/app/ConfirmManagement?"
    							+ "Email=" + Email
    							+ "&Id=" + Id
    							+ "&Date=" + DateSent
    							+ "&Status=" + Status
    							+ "&Type=" + ManageWindow.Inputs.Type
    							+ "&Contact_Channel=" + ManageWindow.Inputs.Contact_Channel.value
    							+ "&Student_Response=" + ManageWindow.Inputs.Student_Response.value
    							+ "&Observations=" + ManageWindow.Inputs.Observations.value
    							+ "&Edit=true";
    					a.href = href;

    					ManageWindow.document.body.appendChild(a);
    					a.click();

    					a.remove();
    				}
    				
    				var Interval2 = setInterval(function(){
    					
    					if(ManageWindow.location.hostname == null){
    						
    						clearInterval(Interval2);
    						$("[id^=Edit]").each(function(){this.onclick = function(){Management(this);};});
    						$("[id^=Status]").each(function(){this.innerHTML = this.innerHTML.replace("?","");});
    					}
    				},500);
    			};
    		}
    	},100);
    }
	</script>
</html>