package pfc.blast.frontend;

import java.io.PrintStream;

import org.json.JSONException;
import org.json.JSONObject;

public class AlignmentPrinter {

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
