package pfc.blast.frontend;

public class RangeCreatorStaticLoad implements RangeCreator {

    private long minRange;
    private long maxRange;
    private int blockSize;
    private long current;
    private boolean completed;

    public RangeCreatorStaticLoad(long minRange, long maxRange,
                                  int blockSize) throws IllegalArgumentException {
        if (minRange >= maxRange) {
            throw new IllegalArgumentException("Minimum cannot exceed maximum.");
        }
        
        if (minRange < 0) {
            throw new IllegalArgumentException("Minimum cannot be negative.");
        }
        
        if (maxRange <= 0) {
            throw new IllegalArgumentException("Maximum range cannot be negative.");
        }

        if (blockSize <= 0) {
            throw new IllegalArgumentException("Block size cannot be negative.");
        }
        
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.blockSize = blockSize;
        this.current = minRange;
        this.completed = false;
    }
    
    public RangeCreatorStaticLoad(long maxRange,
                                  int blockSize) throws IllegalArgumentException {
        if (maxRange <= 0) {
            throw new IllegalArgumentException("Maximum range cannot be negative.");
        }

        if (blockSize <= 0) {
            throw new IllegalArgumentException("Block size cannot be negative.");
        }
        this.minRange = 0;
        this.maxRange = maxRange;
        this.blockSize = blockSize;
        this.current = 0;
        this.completed = false;
    }

    public synchronized boolean isCompleted() {
        return this.completed;
    }

    public synchronized void reset() {
        this.current = this.minRange;
        this.completed = false;
    }

    public synchronized String getNextRange() {
        String min;
        String max;
        
        if (this.minRange == this.current){
            min = String.valueOf(current);
        } else {
            min = String.valueOf(current + 1);
        }
        
        if (current + blockSize >= maxRange) {
            max = String.valueOf(maxRange);
            completed = true;
        } else {
            max = String.valueOf(current + blockSize);
            current += blockSize;
        }
        return "/" + min + "/" + max;
    }
}
