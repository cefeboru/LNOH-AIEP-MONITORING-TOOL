<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib uri="/bbUI" prefix="bbUI"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="blackboard.platform.plugin.PlugInUtil"%>
<%@ page pageEncoding="UTF-8" import="java.util.*"%>

<bbNG:genericPage title="Monitoring Tool Menu">
	<bbNG:pageHeader>
		<bbNG:pageTitleBar title="Monitoring Tool Menu" />
	</bbNG:pageHeader>

	<bbNG:form id="AdminForm" method="POST" name="Admin">
		<bbNG:dataCollection>
			<bbNG:step title="Opciones" instructions="Seleccione un reporte para visualizar.">
				<bbNG:dataElement label="Opción 1">
					<a target='_blank' href="/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentControl">Control de Estudiantes</a>
				</bbNG:dataElement>
				<bbNG:dataElement label="Opción 2">
					<a target='_blank' href="/webapps/lnoh-AIEPMTOOL-BBLEARN/app/CourseControl?page=1">Control de Actividad de Estudiantes</a>
				</bbNG:dataElement>
				<bbNG:dataElement label="Opción 3">
					<a target='_blank' href="/webapps/lnoh-AIEPMTOOL-BBLEARN/app/InstructorControl">Control de Docentes</a>
				</bbNG:dataElement>
				<bbNG:dataElement label="Opción 4">
					<a target='_blank' href="/webapps/lnoh-AIEPMTOOL-BBLEARN/app/InstructorAccess">Control de Acceso de Docentes</a>
				</bbNG:dataElement>
				<br><br><br>
				<a target="_blank" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentSettings">Estados Control de Estudiantes</a>
			</bbNG:step>
			<bbNG:stepSubmit showCancelButton="False">
				<bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton>
			</bbNG:stepSubmit>
		</bbNG:dataCollection>
	</bbNG:form>


	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
	<script type="text/javascript">
		$(function() {

			var Bottom_Button = document.getElementById("bottom_SubmitButton");
			var Top_Button = document.getElementById("top_SubmitButton");

			$(document).ready(function() {

				Bottom_Button.type = "hidden";
				Top_Button.type = "hidden";
			});
		});
	</script>
</bbNG:genericPage>