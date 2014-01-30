package pfc.blast.backend.algorithm;

import java.io.PrintStream;

/**
 * Interface AlignmentStats specifies the interface for an object that computes
 * statistics of an {@linkplain Alignment}. Methods are provided to compute the
 * raw score, the bit score, and the E-value. The formulas for these statistics 
 * depend on the procedure used to produce the alignment.
 *
 * @author  Alan Kaminsky
 * @version 01-Jul-2008
 */
public interface AlignmentStats {

    // Exported operations.

    /**
     * Returns the raw score for the given alignment. A larger raw score
     * signifies a greater degree of similarity between the query sequence and
     * subject sequence that were aligned.
     *
     * @param  alignment  Alignment.
     *
     * @return  Raw score.
     */
    public double rawScore(Alignment alignment);

    /**
     * Returns the bit score for the given alignment. A larger bit score
     * signifies a greater degree of similarity between the query sequence and
     * subject sequence that were aligned.
     * <P>
     * The bit score is the raw score normalized to units of "bits." Bit scores
     * for different alignment procedures may be compared, whereas raw
     * (unnormalized) scores for different alignment procedures may not be
     * compared.
     *
     * @param  alignment  Alignment.
     *
     * @return  Bit score.
     */
    public double bitScore(Alignment alignment);

    /**
     * Returns the <I>E</I>-value (expect value) for the given alignment. A
     * smaller <I>E</I>-value signifies a more statistically significant degree
     * of similarity between the query sequence and subject sequence that were
     * aligned.
     * <P>
     * The <I>E</I>-value is the expected number of alignments with a score
     * greater than or equal to the <TT>alignment</TT>'s score when a
     * randomly-chosen query of the same length as the query that produced the
     * <TT>alignment</TT> is matched against the database.
     *
     * @param  alignment  Alignment.
     *
     * @return  Bit score.
     */
    public double eValue(Alignment alignment);

    /**
     * Print information about this alignment statistics object on the given
     * print stream.
     *
     * @param  out  Print stream.
     */
    public void print(PrintStream out);

}
