package pfc.blast.backend.algorithm;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.Formatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class AlignmentPrinter was modified to remove the use of the traceback
 * array in Alignment, as this project only uses ungapped alignments
 *
 * @editor Sean McGroty
 */

/**
 * Class AlignmentPrinter provides an object that prints an {@linkplain
 * Alignment}.
 *
 * @author  Alan Kaminsky
 * @version 01-Jul-2008
 */
public class AlignmentPrinter {

    // Hidden data members.

    private PrintStream out;
    private AlignmentStats stats;
    private int[][] delta = Blosum62.MATRIX;

    // Exported constructors.

    /**
     * Construct a new alignment printer.
     *
     * @param  out    Print stream on which to print (e.g.,
     *                <TT>System.out</TT>).
     * @param  stats  Object for calculating alignment statistics.
     */
    public AlignmentPrinter(PrintStream out, AlignmentStats stats) {
        this.out = out;
        this.stats = stats;
    }

    // Exported operations.

    /**
     * Set the substitution matrix. If not set, the default is the BLOSUM-62
     * substitution matrix.
     * <P>
     * The expression <TT>matrix[x][y]</TT> is the score when sequence element
     * <TT>x</TT> is aligned with sequence element <TT>y</TT>.
     *
     * @param  matrix  Substition matrix.
     */
    public void setSubstitutionMatrix(int[][] matrix) {
        this.delta = matrix;
    }

    /**
     * Print a summary of the given alignment. The printout consists of:
     * <UL>
     * <LI>
     * Subject sequence description (columns 1-60).
     * <LI>
     * Bit score (columns 63-67).
     * <LI>
     * <I>E</I>-value (columns 70-75).
     * </UL>
     *
     * @param  alignment  Alignment.
     * @param  subject    Subject sequence.
     */
    public void printSummary(Alignment alignment, Sequence subject) {
        String desc = subject.description();
        if (desc.length() > 60)
            desc = desc.substring(0, 57) + "...";
        double bitScore = stats.bitScore(alignment);
        double eValue = stats.eValue(alignment);
        out.format("%-60s  %5s  %s%n", desc, formatBitScore(bitScore),
                   formatEValue(eValue));
    }

    /**
     * Print details of the given alignment. The printout consists of:
     * <UL>
     * <LI>
     * Subject sequence description and length.
     * <LI>
     * Bit score, raw score, and <I>E</I>-value.
     * <LI>
     * Number and percent of identities, positives, and gaps.
     * <LI>
     * The aligned query and subject sequences.
     * </UL>
     *
     * @param  alignment  Alignment.
     * @param  query      Query sequence.
     * @param  subject    Subject sequence.
     */
    public void printDetails(Alignment alignment, Sequence query,
                             Sequence subject) {
        // Print subject sequence description and length.
        out.println(subject.description());
        out.print("<br>");

        out.format("Length = %d%n", subject.length());
        out.println();
        out.print("<br>");

        // Print bit score, raw score, and <I>E</I>-value.
        double bitScore = stats.bitScore(alignment);
        double rawScore = stats.rawScore(alignment);
        double eValue = stats.eValue(alignment);
        out.format("Score = %s bits (%.0f), Expect = %s%n",
                   formatBitScore(bitScore), rawScore, formatEValue(eValue));
        out.print("<br>");
        
        // Count identities, positives, and gaps.
        int identities = 0;
        int positives = 0;
        int qi = alignment.myQueryStart;
        int si = alignment.mySubjectStart;
        int n = alignment.myQueryFinish - alignment.myQueryStart;
        for (int i = 0; i < n; ++i) {

            byte query_qi = query.mySequence[qi];
            byte subject_si = subject.mySequence[si];
            if (query_qi == subject_si) {
                ++identities;
                ++positives;
            } else if (delta[query_qi][subject_si] > 0) {
                ++positives;
            }
            ++qi;
            ++si;
        }

        // Print identities, positives, and gaps.
        out.format("Identities = %d/%d (%.0f%%), ", identities, n,
                   ((double)identities) / ((double)n) * 100.0);
        out.format("Positives = %d/%d (%.0f%%)", positives, n,
                   ((double)positives) / ((double)n) * 100.0);

        out.println();
        out.print("<br>");

        // Print aligned sequences.
        qi = alignment.myQueryStart;
        si = alignment.mySubjectStart;
        int i = 0;
        int j;
        int qj;
        int sj;
        while (i < n) {
            qj = qi;
            out.print("<table>");
            out.print("<tr>");
                out.print("<td>");
                    out.format("Query%6d  ", qj);
                out.print("</td>");
                out.print("<td>");
                    j = 0;
                    while (j < 60 && i + j < n) {
                        out.print("<td>"+query.charAt(qj)+"</td>");
                        ++qj;
                        ++j;
                    }
                out.print("</td>");
                out.print("<td>");
                    out.format("%6d%n", qj - 1);
                out.print("</td>");
                //out.print("<br>");
                qj = qi;
                sj = si;
            out.print("</tr>");
            out.print("<tr>");
                //out.print("             ");
                out.print("<td></td>");
                j = 0;            
                
                out.print("<td>");
                    while (j < 60 && i + j < n) {
                        byte query_qj = query.mySequence[qj];
                        byte subject_sj = subject.mySequence[sj];
                        if (query_qj == subject_sj) {
                            out.print("<td>"+query.charAt(qj)+"</td>");
                        } else if (delta[query_qj][subject_sj] > 0) {
                            //out.print('+');
                            out.print("<td>+</td>");
                        } else {
                            out.print("<td> </td>");
                        }
                        ++qj;
                        ++sj;
                        ++j;
                    }
                out.print("</td>");
                out.print("<td></td>");
            out.println();
            out.print("</tr>");
            out.print("<tr>");
                //out.print("<br>");
                qj = qi;
                sj = si;
                out.print("<td>");
                    out.format("Sbjct%6d  ", sj);
                out.print("</td>");
                j = 0;
                out.print("<td>");
                    while (j < 60 && i + j < n) {
                        out.print("<td>"+subject.charAt(sj)+"</td>");
                        ++qj;
                        ++sj;
                        ++j;
                    }
                out.print("</td>");
                out.print("<td>");
                    out.format("%6d%n", sj - 1);
                out.print("</td>");
            out.print("</tr>");
            out.println();
            out.print("</table>");
            out.print("<br>");
            out.print("<br>");
            qi = qj;
            si = sj;
            i += j;
        }
    }

    // Hidden operations.

    /**
     * Format the given bit score.
     *
     * @param  bitScore  Bit score.
     *
     * @return  Formatted bit score.
     */
    private static String formatBitScore(double bitScore) {
        Formatter f = new Formatter();
        if (bitScore >= 100.0)
            f.format("%.0f", bitScore);
        else if (bitScore >= 10.0)
            f.format("%.1f", bitScore);
        else
            f.format("%.2f", bitScore);
        return f.toString();
    }

    /**
     * Format the given <I>E</I>-value.
     *
     * @param  eValue  <I>E</I>-value.
     *
     * @return  Formatted <I>E</I>-value.
     */
    private static String formatEValue(double eValue) {
        Formatter f = new Formatter();
        if (eValue < 1.0e-199)
            f.format("0.0");
        else if (eValue < 0.001)
            f.format("%.0e", eValue);
        else if (eValue < 0.1)
            f.format("%.3f", eValue);
        else if (eValue < 1.0)
            f.format("%.2f", eValue);
        else if (eValue < 10.0)
            f.format("%.1f", eValue);
        else
            f.format("%.0f", eValue);
        return f.toString();
    }


    /**
     * Print details of the given alignment. The printout consists of:
     * <UL>
     * <LI>
     * Subject sequence description and length.
     * <LI>
     * Bit score, raw score, and <I>E</I>-value.
     * <LI>
     * Number and percent of identities, positives, and gaps.
     * <LI>
     * The aligned query and subject sequences.
     * </UL>
     *
     * @param  alignment  Alignment.
     * @param  query      Query sequence.
     * @param  subject    Subject sequence.
     */
        /* void */
    public JSONObject printDetailsJSON(Alignment alignment, Sequence query,
                             Sequence subject) throws JSONException {
        
        JSONObject jsonNode = new JSONObject();
        
        jsonNode.put("header", subject.description());
        jsonNode.put("length", subject.length());
        
        // Print bit score, raw score, and <I>E</I>-value.
        double bitScore = stats.bitScore(alignment);
        double rawScore = stats.rawScore(alignment);
        double eValue = stats.eValue(alignment);
        
        jsonNode.put("score",formatBitScore(bitScore));
        jsonNode.put("rawScore",rawScore);
        jsonNode.put("expect",formatEValue(eValue));
        
        // Count identities, positives, and gaps.
        int identities = 0;
        int positives = 0;
        int qi = alignment.myQueryStart;
        int si = alignment.mySubjectStart;
        int n = alignment.myQueryFinish - alignment.myQueryStart;
        
        for (int i = 0; i < n; ++i) {
            byte query_qi = query.mySequence[qi];
            byte subject_si = subject.mySequence[si];
            if (query_qi == subject_si) {
                ++identities;
                ++positives;
            } else if (delta[query_qi][subject_si] > 0) {
                ++positives;
            }
            ++qi;
            ++si;
        }

        // Print identities, positives, and gaps.
        jsonNode.put("identities",identities+"/"+n);
        jsonNode.put("identitiesPercentage",((double)identities) / ((double)n) * 100.0);
        
        jsonNode.put("positives",positives+"/"+n);
        jsonNode.put("positivesPercentage",((double)positives) / ((double)n) * 100.0);

        // Print aligned sequences.
        qi = alignment.myQueryStart;
        si = alignment.mySubjectStart;
        int i = 0;
        int j;
        int qj;
        int sj;
        while (i < n) {
            qj = qi;  
            jsonNode.put("query_minindex",qj);
                     
            j = 0;
            String aux = "";
            while (j < 60 && i + j < n) {
                aux += (query.charAt(qj));
                ++qj;
                ++j;
            }
            
            jsonNode.put("query_segment",aux);
            jsonNode.put("query_maxindex", qj - 1);
            
            qj = qi;
            sj = si;
            
            String aux2 = "";
            j = 0;                
            while (j < 60 && i + j < n) {
                byte query_qj = query.mySequence[qj];
                byte subject_sj = subject.mySequence[sj];
                if (query_qj == subject_sj) {
                    aux2 += query.charAt(qj);
                } else if (delta[query_qj][subject_sj] > 0) {
                    aux2 += "+";
                } else {
                    aux2 += " ";
                }
                ++qj;
                ++sj;
                ++j;
            }
            
            jsonNode.put("alignment",aux2);
            
            qj = qi;
            sj = si;
            
            jsonNode.put("subject_minindex", sj);
            
            String aux3 = "";
            j = 0;
            while (j < 60 && i + j < n) {
                aux3 += subject.charAt(sj);
                ++qj;
                ++sj;
                ++j;
            }
            
            jsonNode.put("subject_segment",aux3);
            jsonNode.put("subject_maxindex", sj - 1);

            qi = qj;
            si = sj;
            i += j;
        }
    
        //out.println(jsonNode.toString());
        return jsonNode;
    }

    public static void printDetails(JSONObject jsonResp, PrintStream outStr) throws JSONException {
        // Print subject sequence description and length.
        outStr.println(jsonResp.getString("header")); //out.println(subject.description());
        outStr.print("<br>");

        outStr.format("Length = %s%n",jsonResp.getInt("length")); //out.format("Length = %d%n", subject.length());
        
        outStr.println();
        outStr.print("<br>");

        outStr.format("Score = %s bits (%d), Expect = %s",jsonResp.getString("score"), 
                   jsonResp.getInt("rawScore"), jsonResp.getString("expect"));
        outStr.print("<br>");

        // Print identities, positives, and gaps.
        outStr.format("Identities = %s (%f%%), ", jsonResp.getString("identities"),
                   jsonResp.getDouble("identitiesPercentage"));
        outStr.format("Positives = %s (%f%%)", jsonResp.getString("positives"),
                   jsonResp.getDouble("positivesPercentage"));

        outStr.println();
        outStr.print("<br>");
            // Print alignments.
            outStr.print("<table>");
            outStr.print("<tr>");
                outStr.print("<td>");
                    outStr.format("Query%6d  ", jsonResp.getInt("query_minindex"));
                outStr.print("</td>");
                outStr.print("<td>");
                String query = jsonResp.getString("query_segment");
                for(int i = 0; i<query.length(); i++){
                    outStr.print("<td>"+query.charAt(i)+"</td>");
                }
                outStr.print("</td>");
                outStr.print("<td>");
                    outStr.format("%6d%n", jsonResp.getInt("query_maxindex"));
                outStr.print("</td>");
                outStr.print("</tr>");
                outStr.print("<tr>");
                outStr.print("<td></td>");
                outStr.print("<td>");
                    
                String alignment = jsonResp.getString("alignment");
                for(int i = 0; i<alignment.length(); i++) {
                    outStr.print("<td>"+alignment.charAt(i)+"</td>");
                }
                outStr.print("</td>");
                outStr.print("<td></td>");
                outStr.println();
                outStr.print("</tr>");
                outStr.print("<tr>");

                outStr.print("<td>");
                    outStr.format("Sbjct%6d  ", jsonResp.getInt("subject_minindex"));
                outStr.print("</td>");
                outStr.print("<td>");
                String subject = jsonResp.getString("subject_segment");
                    for (int i = 0; i<subject.length(); i++) {
                        outStr.print("<td>"+subject.charAt(i)+"</td>");
                    }
                outStr.print("</td>");
                outStr.print("<td>");
                    outStr.format("%6d%n", jsonResp.getInt("subject_maxindex"));
                outStr.print("</td>");
            outStr.print("</tr>");
            outStr.println();
            outStr.print("</table>");
            outStr.print("<br>");
            outStr.print("<br>");
    }

}
