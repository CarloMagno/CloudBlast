package pfc.blast.frontend.testing;

import java.util.LinkedList;
import java.util.List;

import pfc.blast.frontend.RangeCreator;
import pfc.blast.frontend.WSRequest;
import pfc.blast.frontend.WSThread;

public class DynamicRangeTest {
    
    //Pool de threads.
    
    private List<String> workers = new LinkedList<String>();
    private static String WS_URL = "/BlastWS";
    private List<WSRequest> thWorkers = new LinkedList<WSRequest>();
    
    
    public DynamicRangeTest() {
        workers.add("http://192.168.1.130:8080/backend");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/webapp1");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/backend");
        workers.add("http://pfc-blast.herokuapp.com");
        
        RangeCreator rangeCreator = new RangeCreator(6298, 50);
        
        for(String urlWorker: workers){
            thWorkers.add(new WSRequest(formURL(urlWorker,"QUERY"), rangeCreator, new LinkedList<String>()));
        }
        
    }
    
    public void start() throws InterruptedException {
        for(WSRequest worker: thWorkers){
            worker.start();
        }
        
        for(WSRequest worker: thWorkers){
            worker.join();
        }    
    }
    
    private String getRange(int index, int numWorkers) {
        /*
        String res = "";
        
        double total = 6298;
        double lengthRange = total/numWorkers;
        long min = (long)Math.floor(lengthRange*index);
        long max = (long)Math.floor(lengthRange*index + lengthRange)-1;
        res = res.concat("/"+ min +"/"+ max);
        
        return res;
        */
        String res = "";
        
        
        return res;
    }

    private String formURL(String url, String query) {
        return url + WS_URL + "/" + query;
    }
    
    public static void main(String args[]){
        DynamicRangeTest test = new DynamicRangeTest();
        System.out.println("-------START");
        try {
            test.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------END");
    }
    
}
