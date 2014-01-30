package pfc.blast.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;

import pfc.blast.backend.algorithm.BlastRunnerSeq;

public class BlastWS extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        processJSON(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        processJSON(request, response);
    }

    private void processJSON(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        response.setContentType(CONTENT_TYPE);
        OutputStream outStr = response.getOutputStream();
        PrintStream out = new PrintStream(outStr);

        try {
            //String inputQuery = request.getPathInfo().substring(1);
            StringTokenizer stok = new StringTokenizer(request.getPathInfo().substring(1),"/");
            String inputQuery = stok.nextToken();
            int initIndex = Integer.valueOf(stok.nextToken());
            int endIndex = Integer.valueOf(stok.nextToken());
            
            float percentage = (float)1.0;
            BlastRunnerSeq.executeJSON("p", this.getServletContext(), percentage, inputQuery, initIndex, endIndex, out);
            out.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

}
