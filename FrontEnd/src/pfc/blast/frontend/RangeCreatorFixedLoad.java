package pfc.blast.frontend;

public class RangeCreatorFixedLoad implements RangeCreator {
    
    private long maxRange;
    private int divisor;
    private int current;
    private boolean completed;
    
    public RangeCreatorFixedLoad(long maxRange, int divisor) {
        this.maxRange = maxRange;
        this.divisor = divisor;
        this.current = 0;
        this.completed = false;
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
        
        double total = (double)maxRange;
        double lengthRange = total/divisor;
        long min = (long)Math.floor(lengthRange*current);
        long max = (long)Math.floor(lengthRange*current + lengthRange)-1;
        
        if(max >= maxRange -1){
            max = maxRange;
            completed = true;
        }else{
            current++;    
        }
        res = res.concat("/"+ min +"/"+ max);
        
        return res;
    }
}
