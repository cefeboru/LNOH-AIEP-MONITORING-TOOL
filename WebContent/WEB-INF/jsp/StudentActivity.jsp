<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/bbNG" prefix="bbNG"   %>
<%@taglib uri="/bbUI" prefix="bbUI"   %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  		<bbNG:genericPage title="${ReportName}">
	<bbNG:pageHeader>
		<bbNG:pageTitleBar title="${ReportName}"/>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</bbNG:pageHeader>
	
		<bbNG:form id="AdminForm" method = "POST" action = "${actionUrl}" enctype="application/x-www-form-urlencoded">
			<bbNG:dataCollection>

				<bbNG:step id="StudentReport" title="${Name}" instructions= ".">
					<table width="100%" border="0" cellspacing="2" cellpadding="1" class="tablaHistDatos">
					    <tbody>
							<tr>
					          <td width="15%" class="titleCurso" align="right">Nombre Curso : </td>
					          <td width="85%"><a target="_blank" href="/webapps/blackboard/execute/courseMain?course_id=_${Id}_1">${Name}</a></td>
					        </tr>
					        <tr>
					          <td width="15%" class="titleCurso" align="right">Estado : </td>
					          <td width="85%">${Status}</td>
						    </tr>
					  </tbody>
					</table>
					<div id="StudentData">
						${HTMLStuActivity}
					</div>
					<div id="DownloadReport" style="display:block;">
					<img src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/xls.png" style="width: 2%;">&nbsp;<label id="DownloadReport" style="font-size: 84%;font-Weight: bold;cursor: pointer;">Descargar Listado</label>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</bbNG:step>
				<bbNG:stepSubmit showCancelButton= "False"><bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton></bbNG:stepSubmit>
				
			</bbNG:dataCollection>
		</bbNG:form>

<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.min.js"></script>
<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
<script type="text/javascript">

$( document ).ready( function() {
	
	$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.core.css" />');
	$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.default.css" />');
	$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/bootstrap.min.css"/>');
	$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/jquery.bootgrid.min.css"/>');
	
	$(".stepHelp").remove();
	
	
	    var Top_Button = document.getElementById("top_SubmitButton");
	    var Bottom_Button = document.getElementById("bottom_SubmitButton");
	    
	    Top_Button.style.display = "none";
	    Bottom_Button.style.display = "none";
	    
	    var form = document.getElementsByTagName("form")[0];
	    form.onsubmit = function() { return false; };
	    
	    document.getElementsByClassName("submitStepBottom")[0].remove();
	    document.getElementById("content").appendChild(document.getElementById("dataCollectionContainer"));
	    document.getElementById("content").appendChild(document.getElementById("containerdiv"));
	    document.getElementById("dataCollectionContainer").id = "TableData";
	    
	    $("[id^=Management]").each(function(){this.onclick = function(){Management(this);};});
    	$("[id^=History]").each(function(){this.onclick = function(){Record(this);};});
    	
    	var ManageWindow = null;
    	
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

	    	var Id = e.id.replace("Management","");

	    	var Email = document.getElementById("Email" + Id).innerHTML;

	    	var Name = document.getElementById("First" + Id).innerHTML + " " + document.getElementById("Last" + Id).innerHTML;

	    	var Today = new Date();
	    	Today = Today.toLocaleDateString();
	    	
	    	var Options = document.getElementById("UnitsOptions").innerHTML;

	    	var Interval1 = setInterval(function(){

	    		if(ManageWindow.document.getElementById("Name") != null && ManageWindow.document.getElementById("StuDateSent") != null && ManageWindow.document.getElementById("Status") != null && ManageWindow.document.getElementById("SendQ") != null && ManageWindow.Inputs != null){

	    			clearInterval(Interval1);

	    			ManageWindow.document.getElementById("Name").innerHTML = Name;
	    			ManageWindow.document.getElementById("StuDateSent").value = Today;
	    			ManageWindow.document.getElementById("Status").parentNode.remove();
					ManageWindow.document.getElementById("StuE").click();
					ManageWindow.document.getElementById("StuStudent_Response").parentNode.parentNode.children[0].innerHTML = "UNIDAD";
					ManageWindow.document.getElementById("StuStudent_Response").innerHTML = Options;
					ManageWindow.Toggle = function(){return false;};
					
	    			ManageWindow.document.getElementById("SendQ").onclick = function(){
	    				
	    				if(ManageWindow.Inputs.IsValid()){

	    					var a = ManageWindow.document.createElement("a");
	    					
	    					if(ManageWindow.Inputs.Type == "C"){
	    						
	    						ManageWindow.Inputs.Student_Response.value = "";
	    					}
	    					
	    					var Temp = ManageWindow.Inputs.DateSent.value.split("/");
	    					var DateSent = Temp[2] + "/" + Temp[1] + "/" + Temp[0];
	    					var Status = ManageWindow.document.getElementById("StuStudent_Response").value;
							
	    					var href = "/webapps/lnoh-AIEPMTOOL-BBLEARN/app/ConfirmManagement?"
	    							+ "Email=" + Email
	    							+ "&Id=" + Id
	    							+ "&Date=" + DateSent
	    							+ "&Status=" + Status
	    							+ "&Type=" + ManageWindow.Inputs.Type
	    							+ "&Contact_Channel=" + ManageWindow.Inputs.Contact_Channel.value
	    							+ "&Student_Response=" + ManageWindow.Inputs.Student_Response.value
	    							+ "&Observations=" + ManageWindow.Inputs.Observations.value;
	    					a.href = href;

	    					ManageWindow.document.body.appendChild(a);
	    					a.click();

	    					a.remove();
	    				}
	    			}
	    		}
	    	},100);
	    }
    	
		function Record(e){
	    	
	    	var RecordWindow = null;
	    	var Id = e.id.replace("History","");
	    	
	    	var Email = document.getElementById("Email" + Id).innerHTML;

	    	var Name = document.getElementById("First" + Id).innerHTML + " " + document.getElementById("Last" + Id).innerHTML;
	    	
	    	var LastAccess = document.getElementById("LastAccess" + Id).innerHTML;
	    	
	    	var Rut = document.getElementById("Rut" + Id).innerHTML;
	    	
	    	RecordWindow = window.open("/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentRecord?Id=" + Id,"_blank");
	    	
	    	RecordWindow.opener = self;
	    	
	    	var Interval1 = setInterval(function(){
	    		
	    		if(RecordWindow.document.getElementById("StuName") != null &&
	    			RecordWindow.document.getElementById("StuRut") != null &&
	    			RecordWindow.document.getElementById("StuPhone") != null &&
	    			RecordWindow.document.getElementById("StuEmail") != null &&
	    			RecordWindow.document.getElementById("StuHeadquarter") != null &&
	    			RecordWindow.document.getElementById("StuLastAccess") != null){
	    			
	    			clearInterval(Interval1);
	    			
	    			RecordWindow.document.getElementById("StuName").innerHTML = Name;
	    			RecordWindow.document.getElementById("StuRut").innerHTML = Rut;
	    			RecordWindow.document.getElementById("StuPhone").innerHTML = "";
	    			RecordWindow.document.getElementById("StuEmail").innerHTML = Email;
	    			RecordWindow.document.getElementById("StuLastAccess").innerHTML = LastAccess;
	    			
	    			RecordWindow.document.getElementById("DownloadReport").onclick = function(){
	    		    	
	    		    	var StuId = Id;
	    		    	
	    		    	$.ajax({
	    			           type:'GET',
	    			           url:'DownloadReport',
	    			           data:{
	    			        	   
	    			        	   Report: "Record",
	    			        	   pk1: StuId,
	    			           },
	    			           datatype:'text',
	    			           success:function(result){
	    							
	    							if(result.indexOf("<table") != -1){
	    								
	    								RecordWindow.document.getElementById("dvData").innerHTML = result;
	    								
	    								var $table = RecordWindow.$('#dvData>table');
	    								var $rows = $table.find('tr:has(td)');

	    								// Temporary delimiter characters unlikely to be typed by keyboard
	    								// This is to avoid accidentally splitting the actual contents
	    								tmpColDelim = String.fromCharCode(11); // vertical tab character
	    								tmpRowDelim = String.fromCharCode(0); // null character

	    								// actual delimiter characters for CSV format
	    								colDelim = '","';
	    								rowDelim = '"\r\n"';

	    								// Grab text from table into CSV formatted string
	    								csv = '"' + $rows.map(function (i, row) {
	    								    var $row = $(row);
	    								        $cols = $row.find('td');

	    								    return $cols.map(function (j, col) {
	    								        var $col = $(col);
	    								            text = $col.text();

	    								        return text.replace(/"/g, '""'); // escape double quotes

	    								    }).get().join(tmpColDelim);

	    								}).get().join(tmpRowDelim)
	    								    .split(tmpRowDelim).join(rowDelim)
	    								    .split(tmpColDelim).join(colDelim) + '"',

	    								// Data URI
	    								csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

	    								var link = RecordWindow.document.createElement("a");
	    								link.setAttribute("href", csvData);
	    								link.setAttribute("download", "InformeGestion-"+ RecordWindow.document.getElementById("StuName").innerHTML.replace(/ /gm,"_") +".csv");
	    								link.click();
	    								
	    								RecordWindow.document.getElementById("dvData").innerHTML = "";
	    							}
	    							else{
	    								
	    								alertify.error("Hubo un error al crear el reporte, porfavor intente de nuevo.",1500);
	    							}
	    			       		},
	    			       		error: function (jqXHR, exception) {
	    			       			
	    			       	        var msg = '';
	    			       	        
	    			       	        if (jqXHR.status === 0) {
	    			       	            msg = 'Not connect.\n Verify Network.';
	    			       	        } else if (jqXHR.status == 404) {
	    			       	            msg = 'Requested page not found. [404]';
	    			       	        } else if (jqXHR.status == 500) {
	    			       	            msg = 'Internal Server Error [500].';
	    			       	        } else if (exception === 'parsererror') {
	    			       	            msg = 'Requested JSON parse failed.';
	    			       	        } else if (exception === 'timeout') {
	    			       	            msg = 'Time out error.';
	    			       	        } else if (exception === 'abort') {
	    			       	            msg = 'Ajax request aborted.';
	    			       	        } else {
	    			       	            msg = 'Uncaught Error.\n' + jqXHR.responseText;
	    			       	        }
	    				       	    var  p = RecordWindow.document.createElement("p");
	    				       	    p = "</br>" + msg;
	    			       	     	RecordWindow.document.body.appendChild(p);
	    			       	    }
	    			           });
	    		    };
	    		}
	    	},100);
	    	
	    }
		
		document.getElementById("DownloadReport").onclick = function(){
			
			//window.location = "https://"+ window.location.hostname +"/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentActivityReport?HTMLTable=" + encodeURIComponent(document.getElementById("ReportData").innerHTML);
			Array.prototype.slice.call(document.getElementById("ReportData").getElementsByTagName("table")).forEach(function(a){a.border = 1;});
			submitFORM('StudentActivityReport',{'HTMLTable':document.getElementById("ReportData").innerHTML},'POST');
	    };
	    
	    function submitFORM(path, params, method) {
	        method = method || "post"; 

	        var Removeform = document.getElementById("ReportForm");

	        if(Removeform != null){

	            Removeform.remove();
	        }
	     
	        var form = document.createElement("form");
	        form.id = "ReportForm";
	        form.setAttribute("method", method);
	        form.setAttribute("action", path);
	     
	        //Move the submit function to another variable
	        //so that it doesn't get overwritten.
	        form._submit_function_ = form.submit;
	     
	        for(var key in params) {
	            if(params.hasOwnProperty(key)) {
	                var hiddenField = document.createElement("input");
	                hiddenField.setAttribute("type", "hidden");
	                hiddenField.setAttribute("name", key);
	                hiddenField.setAttribute("value", params[key]);
	     
	                form.appendChild(hiddenField);
	             }
	        }
	     
	        document.body.appendChild(form);
	        form._submit_function_();
	    }
});

</script>
<style type="text/css">
		.bootgrid-table{
			table-layout: auto !important;
		}
		#TableData{
			overflow: hidden;
			overflow-y: hidden;
			margin-left: 20px;
  			margin-right: 20px;
		}
		td, th {
		  max-width: 500px !important;
		  word-wrap: break-word !important;
		  white-space: pre-wrap !important;
		}
		.dropdown-menu.pull-right {
		
		    margin-top: 0px !important;
		}
		#StudentData tr,#StudentData td,#StudentData th{

			border: 1px solid #DDDDDD;
		}
</style>

	</bbNG:genericPage>
	
	