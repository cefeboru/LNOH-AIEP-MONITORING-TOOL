<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<% 
	//String Headquarter = (request.getParameter("Headquarter") == null ? "" : request.getParameter("Headquarter"));
	response.setContentType("application/csv");
	response.setHeader("content-disposition","filename=ControlEstudiantes.xls"); // Filename
%>
<html><body>${Results}</body></html>
