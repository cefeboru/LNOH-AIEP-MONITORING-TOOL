<%@ page language="java" contentType="text/html;UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib uri="/bbUI" prefix="bbUI"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<bbNG:learningSystemPage ctxId="bbContext">
	<bbNG:breadcrumbBar environment="CTRL_PANEL">
		<bbNG:breadcrumb>${ReportName}</bbNG:breadcrumb> 
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="${ReportName}">${ReportName}</bbNG:pageTitleBar>
		</bbNG:pageHeader>
	</bbNG:breadcrumbBar>
	
	<bbNG:form id="AdminForm" method="POST" action="${actionUrl}"
		enctype="application/x-www-form-urlencoded">
		<bbNG:dataCollection>

			<bbNG:step id="Sedes" title="Filtros"
				instructions="Seleccione una Sede">
					Modalidad: ${HTMLModalidades} Sede: ${HTMLHeadquarters}<input
					type="button" id="Filter" value="Filtrar">
			</bbNG:step>


			<bbNG:stepSubmit showCancelButton="False">
				<bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton>
			</bbNG:stepSubmit>

			<bbNG:step id="StudentReport" title="Control de Estudiantes"
				instructions=".">
				<div id="StudentData"></div>

				<div id="SendEmail" style="display: none;">
					<img src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/xls.png"
						style="width: 2%;">&nbsp;<label id="DownloadReport"
						style="font-size: 84%; font-Weight: bold; cursor: pointer;">Descargar
						Listado</label>&nbsp;&nbsp;&nbsp;&nbsp; <select id="EmailFormat">
						<option value="1">Sin Acceso</option>
						<option value="2">Sin Acceso en la última semana</option>
						<option value="3">Sin Acceso en las últimas dos semanas</option>
						<option value="4" selected>Bienvenida</option>
					</select> <input type="button" id="Emailbtn" value="Enviar">
				</div>
				<div id="dvData" style="display: none;"></div>
			</bbNG:step>
			<bbNG:stepSubmit showCancelButton="False">
				<bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton>
			</bbNG:stepSubmit>

		</bbNG:dataCollection>
	</bbNG:form>

	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.min.js"></script>
	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery-2.1.3.min.js"></script>
	<script
		src="/webapps/lnoh-AIEPMTOOL-BBLEARN/Jquery/jquery.bootgrid.min.js"></script>
	<script type="text/javascript">
	DownloadReport.onclick = function(){
		window.location = "DownloadReport?Modalidades="
				+document.getElementById("Modalidades").value+"&Headquarters="
				+document.getElementById("Headquarters").value+"&Report=Management";
	}
		var jqueryAlias = jQuery.noConflict();
		function selectPage(page) {
			document.getElementById("StudentData").innerHTML = "Loading.....";
			jqueryAlias.ajax({
						type : 'GET',
						url : 'StudentsInfo',
						data : {
							currentPage : page,
							Sede : document.getElementById("Headquarters").value,
							Modalidad : document.getElementById("Modalidades").value,
						},
						datatype : 'text',
						success : function(result) {

							document.getElementById("StudentData").innerHTML = result;
							if (result.indexOf("<table ") != -1) {

								document.getElementById("SendEmail").style.display = "block";
							}
						},
						error : function(jqXHR, exception) {

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

							document.getElementById("StudentData").innerHTML += "</br>"
									+ msg;
						}
					});
		}
		
		function studentRecord(e) {

		    var RecordWindow = null;
		    var Id = e.id.replace("History", "");

		    var Email = document.getElementById("Email" + Id).innerHTML;

		    var Name = document
		        .getElementById("First" + Id).innerHTML + " " + document.getElementById("Last" + Id).innerHTML;

		    var LastAccess = document
		        .getElementById("LastAccess" + Id).innerHTML;

		    RecordWindow = window.open(
		        "/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentRecord?Id=" + Id, "_blank");

		    RecordWindow.opener = self;

		    var Interval1 = setInterval(
		        function() {

		            if (RecordWindow.document
		                .getElementById("StuName") != null && RecordWindow.document
		                .getElementById("StuRut") != null && RecordWindow.document
		                .getElementById("StuPhone") != null && RecordWindow.document
		                .getElementById("StuEmail") != null && RecordWindow.document
		                .getElementById("StuHeadquarter") != null && RecordWindow.document
		                .getElementById("StuLastAccess") != null) {

		                clearInterval(Interval1);

		                RecordWindow.document
		                    .getElementById("StuName").innerHTML = Name;
		                RecordWindow.document
		                    .getElementById("StuRut").innerHTML = "";
		                RecordWindow.document
		                    .getElementById("StuPhone").innerHTML = "";
		                RecordWindow.document
		                    .getElementById("StuEmail").innerHTML = Email;
		                RecordWindow.document
		                    .getElementById("StuHeadquarter").innerHTML = document
		                    .getElementById("Headquarters").value;
		                RecordWindow.document
		                    .getElementById("StuLastAccess").innerHTML = LastAccess;

		                RecordWindow.document
		                    .getElementById("DownloadReport").onclick = function() {

		                        var StuId = Id;

		                        jqueryAlias
		                            .ajax({
		                                type: 'GET',
		                                url: 'DownloadReport',
		                                data: {

		                                    Report: "Record",
		                                    pk1: StuId,
		                                },
		                                datatype: 'text',
		                                success: function(
		                                    result) {

		                                    if (result
		                                        .indexOf("<table") != -1) {

		                                        RecordWindow.document
		                                            .getElementById("dvData").innerHTML = result;

		                                        var jqueryAliastable = RecordWindow
		                                            .jqueryAlias('#dvData>table');
		                                        var jqueryAliasrows = jqueryAliastable
		                                            .find('tr:has(td)');

		                                        // Temporary delimiter characters unlikely to be typed by keyboard
		                                        // This is to avoid accidentally splitting the actual contents
		                                        tmpColDelim = String
		                                            .fromCharCode(11); // vertical tab character
		                                        tmpRowDelim = String
		                                            .fromCharCode(0); // null character

		                                        // actual delimiter characters for CSV format
		                                        colDelim = '","';
		                                        rowDelim = '"\r\n"';

		                                        // Grab text from table into CSV formatted string
		                                        csv = '"' + jqueryAliasrows
		                                            .map(
		                                                function(
		                                                    i,
		                                                    row) {
		                                                    var jqueryAliasrow = jqueryAlias(row);
		                                                    jqueryAliascols = jqueryAliasrow
		                                                        .find('td');

		                                                    return jqueryAliascols
		                                                        .map(
		                                                            function(
		                                                                j,
		                                                                col) {
		                                                                var jqueryAliascol = jqueryAlias(col);
		                                                                text = jqueryAliascol
		                                                                    .text();

		                                                                return text
		                                                                    .replace(
		                                                                        /"/g,
		                                                                        '""'); // escape double quotes

		                                                            })
		                                                        .get()
		                                                        .join(
		                                                            tmpColDelim);

		                                                })
		                                            .get()
		                                            .join(
		                                                tmpRowDelim)
		                                            .split(
		                                                tmpRowDelim)
		                                            .join(
		                                                rowDelim)
		                                            .split(
		                                                tmpColDelim)
		                                            .join(
		                                                colDelim) + '"',

		                                            // Data URI
		                                            csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

		                                        var link = RecordWindow.document
		                                            .createElement("a");
		                                        link
		                                            .setAttribute(
		                                                "href",
		                                                csvData);
		                                        link
		                                            .setAttribute(
		                                                "download",
		                                                "InformeGestion-" + RecordWindow.document
		                                                .getElementById("StuName").innerHTML
		                                                .replace(
		                                                    / /gm,
		                                                    "_") + ".csv");
		                                        link
		                                            .click();

		                                        RecordWindow.document
		                                            .getElementById("dvData").innerHTML = "";
		                                    } else {

		                                        alertify
		                                            .error(
		                                                "Hubo un error al crear el reporte, porfavor intente de nuevo.",
		                                                1500);
		                                    }
		                                },
		                                error: function(
		                                    jqXHR,
		                                    exception) {

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
		                                    var p = RecordWindow.document
		                                        .createElement("p");
		                                    p = "</br>" + msg;
		                                    RecordWindow.document.body
		                                        .appendChild(p);
		                                }
		                            });
		                    };
		            }
		        }, 100);

		}
		
		var emailWindow = "";
		
		jqueryAlias(document)
				.ready(
						function() {

							jqueryAlias('head')
									.append(
											'<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.core.css" />');
							jqueryAlias('head')
									.append(
											'<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/AlertifyJS/alertify.default.css" />');
							jqueryAlias('head')
									.append(
											'<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/bootstrap.min.css"/>');
							jqueryAlias('head')
									.append(
											'<link rel="stylesheet" href="/webapps/lnoh-AIEPMTOOL-BBLEARN/css/jquery.bootgrid.min.css"/>');

							jqueryAlias(".stepHelp").remove();

							var ManageWindow = null;

							var Top_Button = document
									.getElementById("top_SubmitButton");
							var Bottom_Button = document
									.getElementById("bottom_SubmitButton");

							Top_Button.style.display = "none";
							Bottom_Button.style.display = "none";

							var form = document.getElementsByTagName("form")[0];
							form.onsubmit = function() {
								return false;
							};

							document.getElementsByClassName("submitStepBottom")[0]
									.remove();
							document
									.getElementById("content")
									.appendChild(
											document
													.getElementById("dataCollectionContainer"));
							document.getElementById("content").appendChild(
									document.getElementById("containerdiv"));
							document.getElementById("dataCollectionContainer").id = "TableData";

							document.getElementById("Filter").onclick = function() {

								var SelectedHeadquarter = document
										.getElementById("Headquarters").value;
								var SelectedModality = document
										.getElementById("Modalidades").value;
								document.getElementById("StudentData").innerHTML = "Loading...";
								document.getElementById("SendEmail").style.display = "none";

								jqueryAlias
										.ajax({
											type : 'GET',
											url : 'StudentsInfo',
											data : {
												Sede : SelectedHeadquarter,
												Modalidad : SelectedModality,
											},
											datatype : 'text',
											success : function(result) {

												document
														.getElementById("StudentData").innerHTML = result;

												if (result.indexOf("<table ") != -1) {

													document.getElementById("SendEmail").style.display = "block";
					
												}
											},
											error : function(jqXHR, exception) {

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
													msg = 'Uncaught Error.\n'
															+ jqXHR.responseText;
												}

												document
														.getElementById("StudentData").innerHTML += "</br>"
														+ msg;
											}
										});
							};
							
							document.getElementById("Emailbtn").onclick = function() {

								var SelectedFormat = document
										.getElementById("EmailFormat").value;
								var Emails = "";
								var Ids = "";
								var Names = "";
								var Status = "";

								jqueryAlias(':checkbox:checked')
										.each(
												function() {
													Emails += this.value + ",";
													Ids += this.id.replace(
															"Chk", "")
															+ ",";
													Names += (document
															.getElementById("First" + this.id.replace(
																					"Chk",
																					"")).innerHTML
															+ " "
															+ document
																	.getElementById("Last"
																			+ this.id
																					.replace(
																							"Chk",
																							"")).innerHTML + ",");
													Status += document
															.getElementById("Status"
																	+ this.id
																			.replace(
																					"Chk",
																					"")).children[0].title
															+ ",";
												});

								if (Emails != "") {
									Emails = Emails.substring(0,
											Emails.length - 1);
									Ids = Ids.substring(0, Ids.length - 1);
									Names = Names
											.substring(0, Names.length - 1);
									Status = Status.substring(0,
											Status.length - 1);
									
									var parametros = "Emails="+Emails;
									parametros += "&Format="+SelectedFormat;
									parametros += "&Ids="+Ids;
									parametros += "&Names="+Names;
									parametros += "&Status="+Status;
									emailWindow = window.open("SendEmail?"+parametros,'SendEmail','toolbar=no,status=no,menubar=no,scrollbars=no,location=no,width=600,height=690',false);
								} else {

									alertify
											.error(
													"Seleccione al menos un estudiante.",
													3000);
								}
							};							
						});
	</script>

	<script>
	var ManageWindow = null;
	
	function Management(e) {

        var width = 755;
        var height = 405;
		
        var top,left; 
        if(window.screenTop && window.screenLeft){
        	top = screenTop / 2;
        	left = screenLeft / 2;
        } else {
        	top = screenY / 2;
        	left = screenX/2;
        }

        if (ManageWindow != null) {

            ManageWindow.close();
            ManageWindow = null;
        }

        ManageWindow = window
            .open(
                "/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentManagement",
                'course_browse',
                'width=' +
                width +
                ',height=' +
                height +
                ',resizable=yes,scrollbars=yes,status=yes,top=' +
                top + ',left=' + left);

        ManageWindow.opener = self;

        var Id = e.id.replace("Management", "");

        var Email = document.getElementById("Email" +
            Id).innerHTML;

        var Name = document
            .getElementById("First" + Id).innerHTML +
            " " +
            document.getElementById("Last" + Id).innerHTML;

        var Today = new Date();
        Today = Today.toLocaleDateString();

        var Status = document.getElementById("Status" +
            Id).innerHTML.replace("60", "35");

        Status = Status +
            "<label id='StatusText'>" +
            document
            .getElementById("Status" + Id).children[0].title +
            "</label>";

        var Interval1 = setInterval(
            function() {

                if (ManageWindow.document
                    .getElementById("Name") != null &&
                    ManageWindow.document
                    .getElementById("StuDateSent") != null &&
                    ManageWindow.document
                    .getElementById("Status") != null &&
                    ManageWindow.document
                    .getElementById("SendQ") != null &&
                    ManageWindow.Inputs != null) {

                    clearInterval(Interval1);

                    ManageWindow.document
                        .getElementById("Name").innerHTML = Name;
                    ManageWindow.document
                        .getElementById("StuDateSent").value = Today;
                    ManageWindow.document
                        .getElementById("Status").innerHTML = Status;

                    ManageWindow.document
                        .getElementById("SendQ").onclick = function() {

                            if (ManageWindow.Inputs
                                .IsValid()) {

                                var a = ManageWindow.document
                                    .createElement("a");

                                if (ManageWindow.Inputs.Type == "C") {

                                    ManageWindow.Inputs.Student_Response.value = "";
                                }

                                var Temp = ManageWindow.Inputs.DateSent.value
                                    .split("/");
                                var DateSent = Temp[2] +
                                    "/" + Temp[1] +
                                    "/" + Temp[0];
                                var Status = ManageWindow.document
                                    .getElementById("StatusText").innerHTML;

                                var href = "/webapps/lnoh-AIEPMTOOL-BBLEARN/app/ConfirmManagement?" +
                                    "Email=" +
                                    Email +
                                    "&Id=" +
                                    Id +
                                    "&Date=" +
                                    DateSent +
                                    "&Status=" +
                                    Status +
                                    "&Type=" +
                                    ManageWindow.Inputs.Type +
                                    "&Contact_Channel=" +
                                    ManageWindow.Inputs.Contact_Channel.value +
                                    "&Student_Response=" +
                                    ManageWindow.Inputs.Student_Response.value +
                                    "&Observations=" +
                                    ManageWindow.Inputs.Observations.value;
                                a.href = href;

                                ManageWindow.document.body
                                    .appendChild(a);
                                a.click();

                                a.remove();
                            }
                        }
                }
            }, 100);
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
#StudentData {
	font-size: 13px;
}

#StudentData tr, #StudentData td, #StudentData th {
	border: 1px solid #DDDDDD;
}
</style>
	
</bbNG:learningSystemPage>