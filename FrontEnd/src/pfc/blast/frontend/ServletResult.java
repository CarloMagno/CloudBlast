package pfc.blast.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServletResult extends HttpServlet {
    
    private static final String CONTENT_TYPE_HTML = "text/html";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String HEADER_TO_BODY = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" /><title>BLAST Results</title></head><body>";
    private static final String PAGE_TOP = "<div id=\"container\">\n" + 
    "        <div id=\"header\">\n" + 
    "            <h1>CLOUD<span class=\"off\">BLAST</span></h1>\n" + 
    "            <h2>Juan Carlos Ruiz Rico</h2>\n" + 
    "        </div>\n" + 
    "        <div id=\"menu\">\n" + 
    "            <ul>\n" + 
    "                   <li class=\"menuitem\"><a href=\"index.jsp\">Home</a></li>\n" + 
    "                   <li class=\"menuitem\"><a href=\"workers.jsp\">Workers</a></li>\n" +
    "                   <li class=\"menuitem\"><a href=\"about.jsp\">About</a></li>\n" + 
    "                   <li class=\"menuitem\"><a href=\"contact.jsp\">Contact</a></li>\n" + 
    "            </ul>\n" + 
    "        </div>\n" + 
    "        ";
    private static final String BEGIN_CONTENT = "<div id=\"content\">\n" + 
    "        <div id=\"content_top\"></div>\n" + 
    "        <div id=\"content_main\">";
    
    private static final String END_CONTENT = "</div><div id=\"content_bottom\"></div>        \n" +
    "        <div id=\"footer\"></div>\n" + 
    "    </div>";
    
    
    private WorkersList workers;
    private long numProteins = 0;
    private static int BLOCK_SIZE = 100;
    private static final String WS_URL = "/BlastWS";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        workers = WorkersList.getInstance();        
        InputStream propertiesStream = getServletContext().getResourceAsStream("/db/data.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(propertiesStream));
        try {
            this.numProteins = Long.parseLong(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        //process(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        process(request, response);

    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {       
        
        response.setContentType(CONTENT_TYPE_HTML);
        OutputStream outStr = response.getOutputStream();
        PrintStream out = new PrintStream(outStr);

        String query = request.getParameter("sequence");
        if (query == null) {
            response.sendRedirect("index.jsp?error=2");    
        }
        // Number of workers input.
        int numWorkers = 0;
        
        try{
            if(request.getParameter("numWorkers") == null){
                numWorkers = workers.size();
            }else{
                numWorkers = Integer.valueOf(request.getParameter("numWorkers"));
            }
            
            if (numWorkers <= 0) {
                response.sendRedirect("index.jsp?error=1");
            } else if (numWorkers > workers.size()) {
                numWorkers = workers.size();
            }
        }catch (Exception e){
            response.sendRedirect("index.jsp?error=1");    
        }        
        
        out.println(HEADER_TO_BODY);
        out.println(PAGE_TOP);
        out.println(BEGIN_CONTENT);

        out.println("<h1>Results for " + query + "</h1>");
        out.println("<br></br>");
        
        long t1 = 0;
        try {
            /*
            // Sequential running.
            String inputQuery = request.getParameter("sequence");
            float percentage = (float)1.0;
            BlastRunnerSeq.execute("p", this.getServletContext(), percentage, inputQuery, out);
            */
                        
            // Create data structures.
            List<String> responses = Collections.synchronizedList(new ArrayList<String>());
            List<WSRequest> hilos = new LinkedList<WSRequest>();
            
            // Tipo de paralelismo.
            RangeCreator rangeCreator = null;
            String parallelChoice = request.getParameter("parallel");
            
            if (parallelChoice.equals("seq")) {
                rangeCreator = new RangeCreatorSequential(this.numProteins);
            } else if (parallelChoice.equals("dynamParFix")) {
                int blockSize = Integer.valueOf(request.getParameter("blockSize"));
                rangeCreator = new RangeCreatorStaticLoad(this.numProteins, blockSize);    
            } else if (parallelChoice.equals("staticPar")) {
                rangeCreator = new RangeCreatorFixedLoad(this.numProteins, numWorkers);
            } else if (parallelChoice.equals("dynamParDyn")) {
                rangeCreator = new RangeCreatorDynamicLoad(this.numProteins, numWorkers);
            }
                        
            for(int i=0; i<numWorkers; i++){
                String url = formWebServiceURL(workers.get(i),query);
                hilos.add( new WSRequest(url,rangeCreator,responses) );
            }
            
            t1 = System.currentTimeMillis();
            
            // Creates all threads.
            for(WSRequest hilo: hilos){
                hilo.start();
            }
        
            // Waiting all threads to finish.
            for(WSRequest hilo: hilos){
                hilo.join();
            }
            
            long t2 = System.currentTimeMillis();
            out.printf("<h2>Running Time: <b>%d msec</b> using <b>%d nodes </b>%n (%d max & %s)</h2>", t2-t1, numWorkers, workers.size(),parallelChoice);
            out.println("<br></br>");
            
            // Collecting data and print them.
            for(String resp: responses){
                JSONObject jsonResp = new JSONObject(resp);
                JSONArray jsonNodes = jsonResp.getJSONArray("data");
                for(int i = 0; i < jsonNodes.length(); i++){
                    AlignmentPrinter.printDetails(jsonNodes.getJSONObject(i), out);                    
                }
            }
            
        } catch( Exception e ){
            e.printStackTrace(out);
        }
        
        out.println(END_CONTENT);
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
            
    /**
     *
     * @param url
     * @param query
     * @return
     */
    private String formWebServiceURL(String url, String query) {
        return url + WS_URL + "/" + query;
    }
}
