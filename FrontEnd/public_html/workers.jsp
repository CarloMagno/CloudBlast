<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="pfc.blast.frontend.WorkersList" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <title>BLAST on Cloud</title>
    <script type="text/javascript">
        function showLoading(){
            document.getElementById('loadingImage').style.visibility='visible';
        }
    </script>
  </head>
  <body>
  <div id="container">
        <div id="header">
            <h1>CLOUD<span class="off">BLAST</span></h1>
            <h2>Juan Carlos Ruiz Rico</h2>
        </div>
        <div id="menu">
            <ul>
            	<li class="menuitem"><a href="index.jsp">Home</a></li>
                <li class="menuitem"><a href="workers.jsp">Workers</a></li>
                <li class="menuitem"><a href="about.jsp">About</a></li>
                <li class="menuitem"><a href="contact.jsp">Contact</a></li>
            </ul>
        </div>
        
    <div id="content">
        <div id="content_top"></div>
        <div id="content_main">
            <h2>Workers</h2>

            <form name="uploadForm" method="post" action="SetWorkers">
            <div style="width:200px;float:left;display:inline-block;">
              <p>
                <textarea name="urls" cols="50" rows="5"></textarea>
              </p>
              <br></br>
              <p>
                <input type="submit" name="load" value="Load"/>
              </p>
            </div>
              <br></br>
              <br></br>
              <br></br>
              <br></br>
            <div>
              <p>
                <h3>Workers already set</h3>
                <%
                    WorkersList wl = WorkersList.getInstance(getServletConfig().getServletContext());
                    out.print("<br></br><h4>Number of nodes: "+wl.size()+"<br></br>");
                    for(int i = 0; i<wl.size(); i++){
                        out.print(wl.get(i));
                        out.print("<br></br>");
                    }
                %>
                <br></br>
              </p>
            </div>
            </form>
        </div>
    
    <div id="content_bottom"></div>        
        <!-- <div id="footer"><h3><a href="http://www.linkedin.com/profile/view?id=264815119&trk=nav_responsive_tab_profile">LinkedIn</a></h3></div> -->
        <div id="footer"></div>
    </div>
    
    </div>
  </body>
</html>