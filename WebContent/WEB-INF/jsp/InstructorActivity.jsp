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

				<bbNG:step id="CourseReport" title="Seguimiento Docentes SP" instructions= ".">
					<div id="CourseData">
						${HTMLInstructors}
					</div>
				</bbNG:step>
				<bbNG:stepSubmit showCancelButton= "False"><bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton></bbNG:stepSubmit>
				
			</bbNG:dataCollection>
		</bbNG:form>

<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
<script type="text/javascript">

$( document ).ready( function() {
	
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
</style>

	</bbNG:genericPage>
	
	