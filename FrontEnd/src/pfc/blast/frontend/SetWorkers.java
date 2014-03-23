package pfc.blast.frontend;

import java.io.IOException;

import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.http.*;

public class SetWorkers extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        process(request, response);
    }
    
    public void process(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                                                             IOException{  
        WorkersList wl = WorkersList.getInstance(getServletContext());
        
        // Leer urls.
        String workersFile = request.getParameter("urls");
        
        // Limpiar lista.
        wl.clear();
        
        // Agregar todas las urls.
        StringTokenizer stok = new StringTokenizer(workersFile,";");
        while(stok.hasMoreElements()){
            wl.addWorker(stok.nextToken());    
        }
        
        // Redirect.
        response.sendRedirect("workers.jsp");
        
    }
}
