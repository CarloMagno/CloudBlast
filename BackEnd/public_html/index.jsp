<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WorkerInfo</title>
  </head>
  <body>
  <%
    out.print("<h2>Cloud BLAST Worker</h2>");
    out.print("<br></br>");
    out.print("Application Server: " + application.getServerInfo());
    out.print("<br></br>");
    out.print("Cores available: " + Runtime.getRuntime().availableProcessors());
  %>
  </body>
</html>