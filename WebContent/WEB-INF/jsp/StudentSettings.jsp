<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@taglib uri="/bbNG" prefix="bbNG"   %>
<%@taglib uri="/bbUI" prefix="bbUI"   %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.util.*"%>

<bbNG:learningSystemPage ctxId="bbContext">
	<bbNG:breadcrumbBar environment="CTRL_PANEL ">
		<bbNG:breadcrumb>Estados Control de Estudiantes</bbNG:breadcrumb>
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="Estados Control de Estudiantes">Estados Control de Estudiantes</bbNG:pageTitleBar>
		</bbNG:pageHeader>
	</bbNG:breadcrumbBar>
	<form method="post">
		<div class="divider">
			Estado Verde <img class="imageLabel" src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/verde.png" width="25"><b>:</b>
			<input type="number" id="diasVerde" name="diasVerde" value="${estadoVerde }"> días
		</div>
		<div class="divider">
			Estado Amarillo <img class="imageLabel" src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/amarillo.png" width="25"><b>:</b>
			<input type="number" id="diasAmarillo" name="diasAmarillo" value="${estadoAmarillo }" min="${estadoVerde }"> días
		</div>
		
		<div class="divider">
			Estado Naranja <img class="imageLabel" src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/naranja.png" width="25"> <b>></b>
			<input type="number" id="diasNaranja" name="diasNaranja" value="${estadoNaranja }" min="${estadoAmarillo}" >	días
		</div>
		<input type="submit" value="Guardar">
	</form>
	<style>
		.divider {
			padding-top:15px;
			padding-bottom:15px;
		}
		.divider > input {
			margin-left:10px;
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
	</style>
<script>
	diasVerde.onchange = function() {
		diasAmarillo.min = diasVerde.valueAsNumber + 1;
	}

	diasAmarillo.onchange = function() {
		diasNaranja.min = diasAmarillo.value;
	}
</script>
</bbNG:learningSystemPage>