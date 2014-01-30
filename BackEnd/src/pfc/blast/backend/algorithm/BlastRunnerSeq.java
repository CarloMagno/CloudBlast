package pfc.blast.backend.algorithm;

/**
 * Sequential Version of BLAST - 
 *
 * @author Alexander Dean
 **/
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.net.URI;

import javax.servlet.ServletContext;

import javax.sound.midi.Sequence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class
 */
public class BlastRunnerSeq {
    
    public static void executeJSON(String searchType, ServletContext context,
                        float inputPercentage, String inputQuery, PrintStream psOut) throws JSONException {
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

            JSONArray jsonNodeArray = new JSONArray();
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                for(int j=0; j<tmp.length;j++){
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i))); //nuevo
                    //out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i));
                }
            }            
            
            psOut.println(jsonNodeArray.toString()); // nuevo
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // ESTE ES EL BUENO AL QUE SE LLAMA.
    public static void executeJSON(String searchType, ServletContext context,
                        float inputPercentage, String inputQuery, int initIndex,
                        int endIndex, PrintStream psOut) throws JSONException {
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
            
            JSONObject jsonResp = new JSONObject();
            JSONArray jsonNodeArray = new JSONArray();
            jsonResp.put("data", jsonNodeArray);
            
            long t1 = System.currentTimeMillis();
            for(long i=initIndex; i < endIndex; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                for(int j=0; j<tmp.length;j++){
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i))); //nuevo
                }
            }            
            long t2 = System.currentTimeMillis();
            jsonResp.put("runningTime", t2-t1);
            psOut.println(jsonResp.toString()); // nuevo
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void execute(String searchType, ServletContext context, float inputPercentage, 
                               String inputQuery, PrintStream psOut){
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
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                // Print the alignments
                for(int j=0; j<tmp.length;j++){
                    //out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out.printDetails(tmp[j], query, pd.getProteinSequence(i));
                }
            }
            long t2 = System.currentTimeMillis();
            
            psOut.printf("Running Time: %d msec%n", t2-t1);
        } catch (IOException e) {
            psOut.println(e.getMessage());
        }
    }
    
    
    
    public static void execute(String searchType, String databasePath, 
                               String indexPath, float inputPercentage, 
                               String inputQuery, PrintStream psOut){
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( new File(databasePath) , new File(indexPath) );
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
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                // Print the alignments
                for(int j=0; j<tmp.length;j++){
                    //out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out.printDetails(tmp[j], query, pd.getProteinSequence(i));
                }
            }
            long t2 = System.currentTimeMillis();
            
            psOut.printf("Running Time: %d msec%n", t2-t1);
        } catch (IOException e) {
            psOut.println(e.getMessage());
        }
    }
    
    /*
    public static void execute(String searchType, InputStream databaseStream, 
                               InputStream indexStream, float inputPercentage, 
                               String inputQuery, PrintStream psOut){
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( databaseStream , indexStream );
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
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                // Print the alignments
                for(int j=0; j<tmp.length;j++){
                    //out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out.printDetails(tmp[j], query, pd.getProteinSequence(i));
                }
            }
            long t2 = System.currentTimeMillis();
            
            psOut.printf("Running Time: %d msec%n", t2-t1);
        } catch (IOException e) {
            psOut.println(e.getMessage());
        }
    }
    */
    public static void execute(String searchType, File databaseStream, 
                               File indexStream, float inputPercentage, 
                               String inputQuery, PrintStream psOut){
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( databaseStream , indexStream );
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
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                // Print the alignments
                for(int j=0; j<tmp.length;j++){
                    //out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out.printDetails(tmp[j], query, pd.getProteinSequence(i));
                }
            }
            long t2 = System.currentTimeMillis();
            
            psOut.printf("Running Time: %d msec%n", t2-t1);
        } catch (IOException e) {
            psOut.println(e.getMessage());
        }
    }
    
    public static void main( String[] args ) throws Exception{
        //Comm.init(args);
         
        if( args.length != 5 ){ usage(); }

        AlignmentPrinter out;
        ProteinDatabase pd = new ProteinDatabase( new File( args[1] ) , new File(args[2]) );
        float percentage = (new Float(args[3])).floatValue();
        
        Sequence query;
        BLAST aligner;
        if(args[0].toLowerCase().startsWith("p")){
            query = new ProteinSequence(">Search Query", args[4]);
            aligner = new BLASTP(pd.getProteinCount());
        }else{
            query = new NucleotideSequence(">Search Query", args[4]);
            aligner = new BLASTN(pd.getProteinCount());
        }
        
        long t1 = System.currentTimeMillis();
        for(long i=0; i < pd.getProteinCount()*percentage; i++){
            Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
            // Print the alignments
            for(int j=0; j<tmp.length;j++){
                out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                out.printDetails(tmp[j], query, pd.getProteinSequence(i));
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("fin");
        //System.out.printf("Running Time: %d msec%n", t2-t1);
    }

    private static void usage(){
        System.out.println( "Usage: java BlastRunnerSeq [p/n] <input database> <index file> <search coverage percentage> <query seqence>\n"+
                            "\t[p/n] - p if proteins, n if nucleotides.\n"+
                            "\t<database indexs> - Path to index file for input database.\n"+
                            "\t<input database> - Path to a file with FASTA style sequences.\n" +
                            "\t<search coverage percentage> - A number 0.0-1.0, represents how much of the database to search\n"+
                            "\t<query seqence> - The sequence to search for in the input db.");
        //System.exit(-1);
    }

    public static void execute(String searchType, URI databasePath, URI indexPath,
                        float inputPercentage, String inputQuery, PrintStream psOut) {
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( new File(databasePath) , new File(indexPath) );
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
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                // Print the alignments
                for(int j=0; j<tmp.length;j++){
                    //out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    out.printDetails(tmp[j], query, pd.getProteinSequence(i));
                }
            }
            long t2 = System.currentTimeMillis();
            
            psOut.printf("Running Time: %d msec%n", t2-t1);
        } catch (IOException e) {
            psOut.println(e.getMessage());
        }
    }
    
    public static void executeJSON(String searchType, URI databasePath, URI indexPath,
                        float inputPercentage, String inputQuery, PrintStream psOut) throws JSONException {
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( new File(databasePath) , new File(indexPath) );
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
            
            JSONArray jsonNodeArray = new JSONArray();
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                for(int j=0; j<tmp.length;j++){
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i))); //nuevo
                    //out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i));
                }
            }            
            
            psOut.println(jsonNodeArray.toString()); // nuevo
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
    public static void executeJSON(String searchType, InputStream databaseStream, InputStream indexStream,
                        float inputPercentage, String inputQuery, PrintStream psOut) throws JSONException {
        try {        
            AlignmentPrinter out;
            ProteinDatabase pd = new ProteinDatabase( databaseStream , indexStream );
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
            
            JSONArray jsonNodeArray = new JSONArray();
            
            long t1 = System.currentTimeMillis();
            for(long i=0; i < pd.getProteinCount()*percentage; i++){
                Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
                for(int j=0; j<tmp.length;j++){
                    out = new AlignmentPrinter(psOut,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                    jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i))); //nuevo
                    //out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i));
                }
            }            
            
            psOut.println(jsonNodeArray.toString()); // nuevo
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
