package pfc.blast.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

public class WorkersList {
    
    private static volatile WorkersList instance = null;
    private List<String> workers = new LinkedList<String>();

/*    
    private WorkersList() {
        workers = new LinkedList<String>();
        workers.add("http://cloudblast-backend.herokuapp.com");
        workers.add("https://java-trialba2a.java.em1.oraclecloudapps.com");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/backend");   
    }
*/

    private WorkersList(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String url;
            while(( url = br.readLine() ) != null){
                workers.add(url);
            }
            br.close();    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized WorkersList getInstance(ServletContext context){
        if(instance == null){
            instance = new WorkersList(context.getResourceAsStream("/db/workers.url"));
        }
        return instance;
    }
    
    public synchronized void clear(){
        workers.clear();
    }
    
    public synchronized void addWorker(String workerUrl){
        workers.add(workerUrl);
    }
    
    public synchronized int size(){
        return workers.size();    
    }
    
    public synchronized String get(int index){
        return workers.get(index);    
    }
}
