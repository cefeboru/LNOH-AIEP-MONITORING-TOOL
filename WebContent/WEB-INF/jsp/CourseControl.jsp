<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib uri="/bbUI" prefix="bbUI"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<bbNG:learningSystemPage ctxId="bbContext">
	<bbNG:breadcrumbBar environment="CTRL_PANEL ">
		<bbNG:breadcrumb>${ReportName}</bbNG:breadcrumb>
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="${ReportName}">${ReportName}</bbNG:pageTitleBar>

			<bbNG:form id="AdminForm" method="POST" action="${actionUrl}"
				enctype="application/x-www-form-urlencoded">
				<bbNG:dataCollection>
					<bbNG:step id="Filtros" title="Filtros" instructions=".">
					
						<label>Modalidad:</label> ${HTMLModalidades} &emsp;
						<label>Sede:</label> ${HTMLHeadquarters} &emsp;
						<label>Modulo:</label><select name="Modulo" id="Modulo">${HTMLModules}</select>&emsp;
						<label>Secci&oacute;n:</label><input type="text" id="Seccion">&emsp;
						<label>A&ntilde;o:</label><select name="Anio" id="Anio">${HTMLYears}</select>&emsp;
						<label>Semestre:</label><select name="Semestre" id="Semestre">${HTMLSemeters}</select>&emsp;
						<input type="button" id="Filter" value="Filtrar">
						<input id="PageSize" type = "hidden" value = "${PageSize}">
						<br>
						<div id="CourseInfo">
						
						</div>
					</bbNG:step>
					<bbNG:stepSubmit showCancelButton="False">
						<bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton>
					</bbNG:stepSubmit>

				</bbNG:dataCollection>
			</bbNG:form>

			<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
			<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
			<script type="text/javascript">
				$(document)
						.ready(
								function() {

									$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/bootstrap.min.css"/>');
									$('head').append('<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/jquery.bootgrid.min.css"/>');

									$(".stepHelp").remove();

									var Top_Button = document.getElementById("top_SubmitButton");
									var Bottom_Button = document.getElementById("bottom_SubmitButton");

									Top_Button.style.display = "none";
									Bottom_Button.style.display = "none";

									var form = document.getElementsByTagName("form")[0];
									form.onsubmit = function() {
										return false;
									};

									document.getElementsByClassName("submitStepBottom")[0].remove();
									document.getElementById("content").appendChild(document.getElementById("dataCollectionContainer"));
									document.getElementById("content").appendChild(document.getElementById("containerdiv"));
									document.getElementById("dataCollectionContainer").id = "TableData";
									
								    document.getElementById("Filter").onclick = function(){
								    	
								    	BtnFetchInfo(-1);
								    };
								});
				
				function BtnFetchInfo(currentPageVal){
					
					var SelectedHeadquarter = document.getElementById("Headquarters").value;
					var SelectedModalidad = document.getElementById("Modalidades").value;
					
					if(currentPageVal == -1){
						
						document.getElementById("CourseInfo").innerHTML = "";
					}
					else{
						
						document.getElementById("CourseData").remove();
					}
					
					$.ajax({
				           type:'GET',
				           url:'CourseControlInfo',
				           data:{
				        	   
				        	   Sede: SelectedHeadquarter,
				        	   Modalidad: SelectedModalidad,
				        	   CourseFilter: BuildCourseString(),
				        	   currentPage: currentPageVal,
				           },
				           datatype:'text',
				           success:function(result){
				               
				        	   document.getElementById("CourseInfo").innerHTML += result;
				        	   
				        	   if(currentPageVal != -1){
				        		   
				        		   document.getElementById("pageNumber").value = currentPageVal;
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
					       	    
				       	        document.getElementById("CourseInfo").innerHTML +=  "</br>" + msg;
				       	    }
				           });
				}
				
				function BuildCourseString(){
					
					var Modality = document.getElementById("Modalidades").value;
					var Module = document.getElementById("Modulo").value;
					var Section = document.getElementById("Seccion").value;
					var Year = document.getElementById("Anio").value;
					var Semester = document.getElementById("Semestre").value;

					var Filters = [Module,Section,Year,Semester,Modality];

					var FilterString = "";

					Filters.forEach(

					function(a){

						if(a == "" || a.toLowerCase() == "todo"){

							FilterString += "%";
						}
						else{

							FilterString += a;	
						}

						FilterString += "-";
					});

					FilterString = FilterString.substring(0,FilterString.length-1);

					if(FilterString == "%-%-%-%-%"){
						
						FilterString = "Todo";
					}
					
					return FilterString;
				}
			</script>
			<style type="text/css">
.bootgrid-table {
	table-layout: auto !important;
}

#TableData {
	overflow: hidden;
	overflow-y: hidden;
	margin-left: 20px;
	margin-right: 20px;
}

.dropdown-menu.pull-right {
	margin-top: 0px !important;
}

#CourseData tr, #CourseData td, #CourseData th {
	border: 1px solid #DDDDDD;
}

#CourseData {
	font-size: 12px !important;
}
</style>

		</bbNG:pageHeader>
	</bbNG:breadcrumbBar>
</bbNG:learningSystemPage>