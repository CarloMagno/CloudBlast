package pfc.blast.frontend;

import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    
    private static volatile WorkersList instance = new WorkersList();
    private List<String> workers = null;
    
    private WorkersList() {
        workers = new LinkedList<String>();
        workers.add("http://cloudblast-backend.herokuapp.com");
        workers.add("http://pfcblast-pfcblast.rhcloud.com/backend");   
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
