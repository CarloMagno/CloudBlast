package pfc.blast.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

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
            StringTokenizer stok = new StringTokenizer(request.getPathInfo().substring(1),"/");
            String inputQuery = stok.nextToken();
            int initIndex = Integer.valueOf(stok.nextToken());
            int endIndex = Integer.valueOf(stok.nextToken());
                        
            JSONObject jsonResp = new JSONObject();
            JSONArray jsonNodeArray = new JSONArray();
                        
            int cores = Runtime.getRuntime().availableProcessors();
            //int cores = 1; // Para nube de Oracle.
            
            List<JSONArray> results = Collections.synchronizedList(new LinkedList<JSONArray>());
            List<BlastProcessorThread> threads = new LinkedList<BlastProcessorThread>();
            RangeCreator rangeCreator = new RangeCreatorFixedLoad(initIndex, endIndex, cores);
            for(int i = 0; i < cores; i++){
                threads.add(new BlastProcessorThread(rangeCreator, 
                                                     this.getServletContext(), 
                                                     inputQuery, 
                                                     new PrintStream(outStr), 
                                                     results));
            }
            
            long t1 = System.currentTimeMillis();
            
            // Lanzar threads.
            for(int i = 0; i < cores; i++){
                threads.get(i).start();
            }
            
            // Esperar a que terminen.
            for(int i = 0; i < cores; i++){
                threads.get(i).join();
            }
            
            // Recolectar datos.
            for (JSONArray array: results) {
                for(int i=0; i<array.length(); i++){
                    jsonNodeArray.put(array.get(i));
                }
            }
            
            long t2 = System.currentTimeMillis();
            jsonResp.put("data", jsonNodeArray);
            jsonResp.put("runningTime", t2-t1);
            out.println(jsonResp.toString());
            out.close();
            
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

}
