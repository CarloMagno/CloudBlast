<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="pfc.blast.frontend.WorkersList"%>
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
            <h2>Please, enter your sequence to compare with our database.</h2>
            <br></br>
            <form name="inputSequence" method="post" action="ServletResult">
             <div style="float:left;display:inline-block;">
                <table>
                    <tr>
                        <td>
                            Input Sequence <input type="text" name="sequence"/> 
                            &nbsp;
                            Workers
                            <% 
                                WorkersList wl = WorkersList.getInstance(getServletConfig().getServletContext());
                                out.print("("+wl.size()+" max)");
                            %> 
                            <input type="text" name="numWorkers" size="5" value=<%=wl.size()%> />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <br>Parallelization:<br>
                            <input type="radio" name="parallel" value="seq"/>Sequential<br>
                            <input type="radio" name="parallel" value="staticPar"/>Static parallel<br>
                            <input type="radio" name="parallel" value="dynamParFix" checked/>Dynamic parallel (fixed size) &nbsp; Block size <input type="text" name="blockSize" size=3 value="50"/><br>
                            <input type="radio" name="parallel" value="dynamParDyn" />Dynamic parallel (dynamic size)<br>
                        </td>
                    </tr>
                </table>
              <p>
                <input type="submit" name="Send" value="Submit" onclick="showLoading()"/>
              </p>
            </div>
            <div style="margin-left:300px;">
              <p>
                <img id="loadingImage" src="images/ajax-loader.gif" style="visibility:hidden;padding-top:25%"
                     height="100" width="100" />
              </p>
              <br></br>
              <br></br>
              <br></br>
              <br></br>
              <br></br>

              <% 
                String error = request.getParameter("error");
                if ( error != null ) {
                    int errorCode = Integer.valueOf(error);
                    String errorMsg = "";
                    
                    switch(errorCode){
                        case 1:  errorMsg = "Wrong number of workers.";
                                 break;
                        case 2:  errorMsg = "Input sequence not valid.";
                                 break;
                        default: break;
                    }
                    out.print("<h3><font color=\"red\">"+errorMsg+"</font></h3>");
                }
              %>
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