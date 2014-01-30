package pfc.blast.backend.algorithm;

/**
 * Clase que representa una tupla de enteros. En BLAST se usa
 * para relacionar una palabra en una lista con su indice en
 * la consulta.
 * 
 * This is a utility class that can represent an integer tuple
 * In the BLAST implementation, it is used to relate a word 
 * in a list with its index in the query
 * 
 * @author Juan Carlos Ruiz Rico
 */

public class HSP {

    private int qPos;
    private int sPos;
    
    public HSP(int q, int s) {
        this.qPos = q;
        this.sPos = s;
    }

    public int getQPos() {
        return qPos;
    }

    public int getSPos() {
        return sPos;
    }

    public void setQPos(int qPos) {
        this.qPos = qPos;
    }

    public void setSPos(int sPos) {
        this.sPos = sPos;
    }
}
