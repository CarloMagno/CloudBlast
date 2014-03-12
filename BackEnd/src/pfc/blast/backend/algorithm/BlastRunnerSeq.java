package pfc.blast.backend.algorithm;

/**
 * Sequential Version of BLAST - 
 *
 * @author Alexander Dean
 **/
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URI;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONException;

public class BlastRunnerSeq {
    
    public static JSONArray executeJSON(String searchType, ServletContext context,
                        float inputPercentage, String inputQuery, int initIndex,
                        int endIndex, PrintStream psOut) throws JSONException {
        
        JSONArray jsonNodeArray = null;
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( context );
            float percentage = inputPercentage;
            Sequence query;
            BLAST aligner;
            
            if(searchType.toLowerCase().startsWith("p")){
                query = new ProteinSequence(">Search Query", inputQuery);
                aligner = new BLASTP(pd.getProteinCount());
            }else{
                query = new NucleotideSequence(">Search Query", inputQuery);
                aligner = new BLASTN(pd.getProteinCount());
            }
            
            jsonNodeArray = new JSONArray();
            
            for(long i=initIndex; i < endIndex; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                for(int j=0; j<tmp.length;j++){
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i))); //nuevo
                }
            }            
                        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonNodeArray;
    }
    
}
