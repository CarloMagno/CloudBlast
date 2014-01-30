package pfc.blast.backend.algorithm;

/**
 * Class Blosum62 provides the BLOSUM-62 substitution matrix for protein
 * sequence alignment. The expression Blosum62.matrix[x][y] is the
 * score when amino acid x is aligned with amino acid y, where
 * x and y are amino acids in the range 0..27 from a
 * ProteinSequence.
 * 
 * The BLOSUM-62 substitution matrix is taken from
 * http://www.ncbi.nlm.nih.gov/Class/BLAST/BLOSUM62.txt
 *
 * @author  Alan Kaminsky
 * @version 01-Jul-2008
 */
public class Blosum62 {
    private Blosum62() { }
    
    /**
     * The BLOSUM-62 protein substitution matrix.
     */
    public static final int[][] MATRIX = new int[][]{
          
          /*A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z  *  -*/
    /*A*/ { 4,-2, 0,-2,-1,-2, 0,-2,-1,-4,-1,-1,-1,-2,-4,-1,-1,-1, 1, 0, 0, 0,-3, 0,-2,-1,-4,-4},
    /*B*/ {-2, 4,-3, 4, 1,-3,-1, 0,-3,-4, 0,-4,-3, 3,-4,-2, 0,-1, 0,-1,-3,-3,-4,-1,-3, 1,-4,-4},
    /*C*/ { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3,-4,-4},
    /*D*/ {-2, 4,-3, 6, 2,-3,-1,-1,-3,-4,-1,-4,-3, 1,-4,-1, 0,-2, 0,-1,-3,-3,-4,-1,-3, 1,-4,-4},
    /*E*/ {-1, 1,-4, 2, 5,-3,-2, 0,-3,-4, 1,-3,-2, 0,-4,-1, 2, 0, 0,-1,-4,-2,-3,-1,-2, 4,-4,-4},
    /*F*/ {-2,-3,-2,-3,-3, 6,-3,-1, 0,-4,-3, 0, 0,-3,-4,-4,-3,-3,-2,-2,-2,-1, 1,-1, 3,-3,-4,-4},
    /*G*/ { 0,-1,-3,-1,-2,-3, 6,-2,-4,-4,-2,-4,-3, 0,-4,-2,-2,-2, 0,-2,-3,-3,-2,-1,-3,-2,-4,-4},
    /*H*/ {-2, 0,-3,-1, 0,-1,-2, 8,-3,-4,-1,-3,-2, 1,-4,-2, 0, 0,-1,-2,-3,-3,-2,-1, 2, 0,-4,-4},
    /*I*/ {-1,-3,-1,-3,-3, 0,-4,-3, 4,-4,-3, 2, 1,-3,-4,-3,-3,-3,-2,-1,-1, 3,-3,-1,-1,-3,-4,-4},
    /*J*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
    /*K*/ {-1, 0,-3,-1, 1,-3,-2,-1,-3,-4, 5,-2,-1, 0,-4,-1, 1, 2, 0,-1,-3,-2,-3,-1,-2, 1,-4,-4},
    /*L*/ {-1,-4,-1,-4,-3, 0,-4,-3, 2,-4,-2, 4, 2,-3,-4,-3,-2,-2,-2,-1,-1, 1,-2,-1,-1,-3,-4,-4},
    /*M*/ {-1,-3,-1,-3,-2, 0,-3,-2, 1,-4,-1, 2, 5,-2,-4,-2, 0,-1,-1,-1,-1, 1,-1,-1,-1,-1,-4,-4},
    /*N*/ {-2, 3,-3, 1, 0,-3, 0, 1,-3,-4, 0,-3,-2, 6,-4,-2, 0, 0, 1, 0,-3,-3,-4,-1,-2, 0,-4,-4},
    /*O*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
    /*P*/ {-1,-2,-3,-1,-1,-4,-2,-2,-3,-4,-1,-3,-2,-2,-4, 7,-1,-2,-1,-1,-3,-2,-4,-2,-3,-1,-4,-4},
    /*Q*/ {-1, 0,-3, 0, 2,-3,-2, 0,-3,-4, 1,-2, 0, 0,-4,-1, 5, 1, 0,-1,-3,-2,-2,-1,-1, 3,-4,-4},
    /*R*/ {-1,-1,-3,-2, 0,-3,-2, 0,-3,-4, 2,-2,-1, 0,-4,-2, 1, 5,-1,-1,-3,-3,-3,-1,-2, 0,-4,-4},
    /*S*/ { 1, 0,-1, 0, 0,-2, 0,-1,-2,-4, 0,-2,-1, 1,-4,-1, 0,-1, 4, 1,-1,-2,-3, 0,-2, 0,-4,-4},
    /*T*/ { 0,-1,-1,-1,-1,-2,-2,-2,-1,-4,-1,-1,-1, 0,-4,-1,-1,-1, 1, 5,-1, 0,-2, 0,-2,-1,-4,-4},
    /*U*/ { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3,-4,-4},
    /*V*/ { 0,-3,-1,-3,-2,-1,-3,-3, 3,-4,-2, 1, 1,-3,-4,-2,-2,-3,-2, 0,-1, 4,-3,-1,-1,-2,-4,-4},
    /*W*/ {-3,-4,-2,-4,-3, 1,-2,-2,-3,-4,-3,-2,-1,-4,-4,-4,-2,-3,-3,-2,-2,-3,11,-2, 2,-3,-4,-4},
    /*X*/ { 0,-1,-2,-1,-1,-1,-1,-1,-1,-4,-1,-1,-1,-1,-4,-2,-1,-1, 0, 0,-2,-1,-2,-1,-1,-1,-4,-4},
    /*Y*/ {-2,-3,-2,-3,-2, 3,-3, 2,-1,-4,-2,-1,-1,-2,-4,-3,-1,-2,-2,-2,-2,-1, 2,-1, 7,-2,-4,-4},
    /*Z*/ {-1, 1,-3, 1, 4,-3,-2, 0,-3,-4, 1,-3,-1, 0,-4,-1, 3, 0, 0,-1,-3,-2,-3,-1,-2, 4,-4,-4},
    /***/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4},
    /*-*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1},
        
    };
}
