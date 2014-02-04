package pfc.blast.frontend;

import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    
    private static volatile WorkersList instance = new WorkersList();
    private List<String> workers = null;
    
    private WorkersList() {
        workers = new LinkedList<String>();
        //workers.add("http://192.168.1.130:8080/backend");
        //workers.add("http://pfcblast-pfcblast.rhcloud.com/webapp1");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/backend");
        workers.add("http://cloudblast-backend.herokuapp.com");
    }
    
    public static synchronized WorkersList getInstance(){
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
