package pfc.blast.backend;

public class RangeCreatorFixedLoad implements RangeCreator {
    
    private long minRange;
    private long maxRange;
    private int divisor;
    private int current;
    private boolean completed;
    
    public RangeCreatorFixedLoad(long minRange, long maxRange, int divisor) {
        this.minRange = minRange;
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
        long min = (long)Math.floor(lengthRange*current + minRange);
        long max = (long)Math.floor(lengthRange*current + minRange + lengthRange)-1;
        
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
