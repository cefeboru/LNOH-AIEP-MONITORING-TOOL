<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/bbNG" prefix="bbNG"   %>
<%@taglib uri="/bbUI" prefix="bbUI"   %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<html>
		<head>
			<title>Control de Estudiantes SP</title>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.core.css" />
			<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.default.css" />
			<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/bootstrap.min.css"/>
			<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/jquery.bootgrid.min.css"/>
			<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/DatePicker/jquery-ui.css"/>
		</head>
		<body>
			<form id="AdminForm" enctype="application/x-www-form-urlencoded" action="null" method="POST">
			<table id="Info" class='table table-condensed table-hover table-striped'>
				<tbody>
					<tr>
						<td>ESTUDIANTE</td>
						<td>&nbsp;</td>
						<td id="Name"></td>
					</tr>
					<tr>
						<td>FECHA</td>
						<td>&nbsp;</td>
						<td>
							<span id="fecha" class="textfieldValidState">
				            <label>
				              <input type="text" id="StuDateSent">
				            </label>
				        </span>
						</td>
					</tr>
					<tr>
						<td>ESTADO</td>
						<td>&nbsp;</td>
						<td id="Status"></td>
					</tr>
					<tr>
						<td>GESTION</td>
						<td>&nbsp;</td>
						<td>
							<input id="StuC" type="radio" name="gestion" value="C" checked> Coordinador &nbsp; <input id="StuE" type="radio" name="gestion" value="E"> Estudiante
						</td>
					</tr>
					<tr>
						<td>VIA DE CONTACTO</td>
						<td>&nbsp;</td>
						<td>
							<select name="via_contacto" class="celdaColumna" id="StuContact_Channel">
			             	 	<option value="EMAIL" selected>EMAIL</option>
				              	<option value="TELEFONO">TELEFONO</option>
			              		<option value="SALA">EN SALA</option>
				            </select>
						</td>
					</tr>
					<tr>
						<td>RESPUESTA ESTUDIANTE</td>
						<td>&nbsp;</td>
						<td>
				            <select name="resp" class="celdaColumna" id="StuStudent_Response" style="display:none;">
				            	<option value="SIN RESPUESTA" selected>SIN RESPUESTA</option>
				              	<option value="RETRACTO INFORMAL">RETRACTO INFORMAL</option>
								<option value="RETRACTO FORMAL">RETRACTO FORMAL</option>
								<option value="CAMBIO DE MODALIDAD">CAMBIO DE MODALIDAD</option>
								<option value="HOMOLOGO MODULO">HOMOLOGÓ MÓDULO</option>
								<option value="LICENCIA MEDICA">LICENCIA MÉDICA</option>
								<option value="NO CONTACTAR">NO VOLVER A CONTACTAR</option>
				            </select>
				            <p id="NA" style="display:block;">N/A<p>
						</td>
					</tr>
					<tr>
						<td>OBSERVACIONES</td>
						<td>&nbsp;</td>
						<td>
							<textarea name="mensaje_enviado" id="StuObservations" cols="70" rows="7" autocomplete="off"></textarea>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<input id="SendQ" name="SendQ" type="button" value="Registrar Gestion">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	
			<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
			<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/DatePicker/jquery-ui.js"></script>
			<script src="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.min.js"></script>
              <script>
              
              $(function() {
                $("#StuDateSent").datepicker({ 
                  autoSize: true,
                  dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
                  dayNamesMin: ['Dom', 'Lu', 'Ma', 'Mi', 'Je', 'Vi', 'Sa'],
                  firstDay: 1,
                  monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
                  monthNamesShort: ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
                  dateFormat: 'dd/mm/yy',
                  changeMonth: true,
                  changeYear: true,
                  yearRange: "-90:+0",
                });
              });
              
              var Inputs = {};
              Inputs.Type = "C";

              $("[id^=Stu]").each(function(){

              	this.onclick = function(){this.style.backgroundColor = "";};
              	
				if(this.id == "StuC" || this.id == "StuE"){
              		
					
              		this.onclick = function(){Inputs.Type = this.value; Toggle(this);};
              	}
				else{
					
					Inputs[this.id.replace("Stu","")] = this;
				}
              });
              
              Inputs.IsValid = function(){
            		 
            		var Valid = true;

            		for(key in Inputs){

            			if(Inputs[key].value == ""){

            				Inputs[key].style.backgroundColor = "indianred";
            				Valid = false;
            			}
            		};

            		if(!Valid){

            			alertify.error("Complete los campos en rojo",1500);
            		}

            		return Valid;
            	};
            	
            	function Toggle(e){
            		
            		var Response = document.getElementById("StuStudent_Response");
            		var NotApplicable = document.getElementById("NA");
            		
            		if(e.id == "StuC"){
            			
						Response.style.display = "none";
						NotApplicable.style.display = "block";
            		}
					if(e.id == "StuE"){
            			
						Response.style.display = "block";
						NotApplicable.style.display = "none";
            		}
            	}
            </script>
		</body>
	
</html>