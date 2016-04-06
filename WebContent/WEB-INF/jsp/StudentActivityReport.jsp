<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<% 
	response.setContentType("application/csv");
	response.setHeader("content-disposition","filename=ActividadEstudiantes.xls"); // Filename
%>
<html><body>${HTMLTable}</body></html>