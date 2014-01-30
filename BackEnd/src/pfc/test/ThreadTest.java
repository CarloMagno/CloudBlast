package pfc.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class ThreadTest extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML><BODY>");
        out.println(this + ": <br>");
        int counter = 0;
        for (int c = 0; c < 10; c++)
        {   
            out.println("Counter = " + counter + "<BR>");
              try
              {
                Thread th = new Thread();
                th.start();
                Thread.currentThread().sleep((long) Math.random() * 1000);
                counter++;
              }
              catch (InterruptedException exc) { }
        }
            out.println("</BODY></HTML>");
    }
}
