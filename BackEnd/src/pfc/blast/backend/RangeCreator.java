package pfc.blast.backend;

public interface RangeCreator {

    public boolean isCompleted();
    
    public void reset();
    
    public String getNextRange();
    
}
