package pfc.blast.frontend;

public class RangeCreatorSequential implements RangeCreator {
    
    private long maxRange;
    private boolean completed;
    
    public RangeCreatorSequential(long maxRange) {
        this.maxRange = maxRange;
        this.completed = false;
    }

    public synchronized boolean isCompleted() {
        return this.completed;
    }

    public synchronized void reset() {
        this.completed = false;
    }

    public synchronized String getNextRange() {
        String res = "/1" + "/" + maxRange;
        this.completed = true;
        return res;
    }
}
