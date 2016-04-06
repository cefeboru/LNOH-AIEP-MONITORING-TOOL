<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<% 
	response.setContentType("application/csv");
	response.setHeader("content-disposition","filename=ControlDocentes.xls"); // Filename
%>
<html> 
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
		${HTMLTable}
	</body>
</html>