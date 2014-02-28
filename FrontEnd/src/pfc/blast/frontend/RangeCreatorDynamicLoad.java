package pfc.blast.frontend;

public class RangeCreatorDynamicLoad implements RangeCreator {
    
    private long current;
    private boolean completed;
    private long total;
    private int numWorkers;
    private long remaining;
    
    public RangeCreatorDynamicLoad(long total, int numWorkers) {
        super();
        this.total = total;
        this.remaining = total;
        this.numWorkers = numWorkers;
        this.completed = false;
        this.current = 0;
    }

    public synchronized boolean isCompleted() {
        return this.completed;
    }

    public synchronized void reset() {
        this.current = 0;
        this.completed = false;
    }

    public synchronized String getNextRange() {
        String res = "";
        
        double rem = (double)this.remaining;
        long block = (long)Math.ceil(rem/this.numWorkers);
        this.remaining -= block;
        
        long min;
        long max;
        
        min = this.current++;
        this.current += block;
        
        if (current >= this.total) {
            max = this.total;
            this.completed = true;
        } else {
            max = min + block;
        }

        res = res.concat("/"+ min +"/"+ max);        
        return res;
    }
}

