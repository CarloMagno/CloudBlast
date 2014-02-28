package pfc.blast.frontend.testing;

import java.util.LinkedList;
import java.util.List;

import pfc.blast.frontend.RangeCreator;
import pfc.blast.frontend.RangeCreatorDynamicLoad;
import pfc.blast.frontend.RangeCreatorStaticLoad;

public class RangeTest {

    public static List<String> workers = new LinkedList<String>();
    private static String WS_URL = "/BlastWS";

    public static String getRange(int index, int numWorkers) {
        String res = "";
        
        double minim = 130;
        double maxim = 250;
        double total = maxim - minim + 1.0;
        double lengthRange = total/numWorkers;
        long min = (long)Math.floor(lengthRange*index + minim);
        long max = (long)Math.floor(lengthRange*index + minim + lengthRange)-1;
        res = res.concat("/"+ min +"/"+ max);
        
        return res;
    }
    
    private static String formURL(int index, String query) {
        return workers.get(index) + WS_URL + "/" + query + getRange(index, workers.size());
    }
    
    /**
     *
     * @param args
     */
    public static void main(String args[]){
        /*
        System.out.println("**********************");
        System.out.println("******* TEST 1 *******");
        System.out.println("**********************");
        workers.add("http://192.168.1.130:8080/backend");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/webapp1");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/backend");
        workers.add("http://pfc-blast.herokuapp.com");
        
        int N = workers.size();
        for (int i = 0; i<N; i++){
            System.out.println(formURL(i,"GYEGYE"));
        }
        System.out.println("**********************");
        System.out.println("******* TEST 2 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTest = new RangeCreatorStaticLoad(6298,100);
        while(!rangeTest.isCompleted()){
            System.out.println(rangeTest.getNextRange());    
        }
        */
        System.out.println("**********************");
        System.out.println("******* TEST 3 *******");
        System.out.println("**********************");
        
        RangeCreator rangeTestFinal = new RangeCreatorDynamicLoad(6298, 4);
        while(!rangeTestFinal.isCompleted()){
            System.out.println(rangeTestFinal.getNextRange());    
        }
        
    }
}
