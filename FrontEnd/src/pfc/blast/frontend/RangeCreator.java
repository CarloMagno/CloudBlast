package pfc.blast.frontend;

public class RangeCreator {
    
    private long maxRange;
    private int blockSize;
    private long current;
    private boolean completed;
    
    public RangeCreator(long maxRange, int blockSize) throws IllegalArgumentException{
        if (maxRange <= 0){
            throw new IllegalArgumentException("Maximum range cannot be negative.");
        }
        
        if (blockSize <= 0){
            throw new IllegalArgumentException("Block size cannot be negative.");
        }
            
        this.maxRange = maxRange;
        this.blockSize = blockSize;
        this.current = 0;
        this.completed = false;
    }

    public synchronized boolean isCompleted(){
        return this.completed;
    }
    
    public synchronized void reset(){
        this.current = 0;
        this.completed = false;
    }
    
    public synchronized String getNextRange(){
        String min;
        String max;
        
        min = String.valueOf(current+1);
        if(current + blockSize >= maxRange){
            max = String.valueOf(maxRange);
            completed = true;
        }else{
            max = String.valueOf(current+blockSize);
            current += blockSize;
        }
        return "/" + min + "/" + max;
    }
}
