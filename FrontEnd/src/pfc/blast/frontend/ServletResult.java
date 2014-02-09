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
        
        out.println(HEADER_TO_BODY);
        out.println(PAGE_TOP);
        out.println(BEGIN_CONTENT);
        String query = request.getParameter("sequence");
        out.println("<h1>Results for "+ query +"</h1>");
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
            //List<WSThread> hilos = new LinkedList<WSThread>();
            List<WSRequest> hilos = new LinkedList<WSRequest>();

            RangeCreator rangeCreator = new RangeCreator(this.numProteins, BLOCK_SIZE);
            // Create all threads.
            for(int i=0; i<workers.size(); i++){
                String url = formURL(workers.get(i),query);
                //hilos.add(new WSThread(url, responses));
                hilos.add(new WSRequest(url, rangeCreator, responses));
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
            out.printf("<h2>Running Time: <b>%d msec</b> using <b>%d nodes.</b>%n</h2>", t2-t1, workers.size());
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
        
    private void processJSON(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {       
        
        response.setContentType(CONTENT_TYPE_JSON);
        OutputStream outStr = response.getOutputStream();
        PrintStream out = new PrintStream(outStr);

        try {
            String inputQuery = request.getParameter("sequence");
            float percentage = (float)1.0;
//            BlastRunnerSeq.executeJSON("p", this.getServletContext(), percentage, inputQuery, out);
            out.close();
        } catch( Exception e ){
            out.println(e.getMessage());
        }
    }

    /**
     * Aux method to help to form the URL of the worker. Set the range of 
     * proteins to compute for the worker 'index'.
     * 
     * @param index
     * @param size
     * @return
     */
    private String getRange(int index, int numWorkers) {
        String res = "";
        
        double total = numProteins;
        double lengthRange = total/numWorkers;
        long min = (long)Math.floor(lengthRange*index);
        long max = (long)Math.floor(lengthRange*index + lengthRange)-1;
        res = res.concat("/"+ min +"/"+ max);
        
        return res;
    }

    /**
     *
     * @param index
     * @return
     */
    private String formURL(String url, String query) {
        return url + WS_URL + "/" + query /*+ getRange(index, workers.size())*/;
    }
}
