package pfc.test;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;

import pfc.blast.backend.algorithm.Alignment;
import pfc.blast.backend.algorithm.AlignmentPrinter;
import pfc.blast.backend.algorithm.BLAST;
import pfc.blast.backend.algorithm.BLASTP;
import pfc.blast.backend.algorithm.DefaultAlignmentStats;
import pfc.blast.backend.algorithm.ProteinDatabase;
import pfc.blast.backend.algorithm.ProteinSequence;
import pfc.blast.backend.algorithm.Sequence;


public class BLASTTest {
    private final static String PATH = "C:\\JDeveloper\\mywork\\CloudBlast\\BackEnd\\public_html\\db\\";
    public BLASTTest() {
        super();
    }
    
    public static void main(String[] args) throws IOException, JSONException {
        /* This should be 'execute' method in BlastRunnerSeq */
        //int cores = Runtime.getRuntime().availableProcessors(); System.out.println(cores);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Starting...");
        
        ProteinDatabase pd = new ProteinDatabase(PATH);
        //ProteinDatabase pd = new ProteinDatabase( new FileInputStream(new File(PATH+"yeast.aa")) , new FileInputStream(new File(PATH+"yeast.aa.indexs")) , new FileInputStream(PATH+"yeast.aa.length") );
        //ProteinDatabase pd = new ProteinDatabase( new File(PATH+"yeast.aa") , new File(PATH+"yeast.aa.indexs2") );

        BLAST aligner = new BLASTP(pd.getProteinCount());
        Sequence query = new ProteinSequence(">Test","GGYEYAYGEYGE");
        AlignmentPrinter out;

        JSONArray jsonNodeArray = new JSONArray();

        for(long i=0; i < pd.getProteinCount(); i++){
            Alignment[] tmp = aligner.align( query, pd.getProteinSequence( i ) );
            // Print the alignments
            for(int j=0; j<tmp.length;j++){
                out = new AlignmentPrinter(System.out,new DefaultAlignmentStats(tmp[j].getSubjectLength()));
                //System.out.println(pd.getProteinSequence(i));
                jsonNodeArray.put(out.printDetailsJSON(tmp[j], query, pd.getProteinSequence(i)));
            }
        }
        
        //System.out.println(jsonNodeArray);
        System.out.println("...end!");
        //System.out.println("> "+jsonNodeArray.getJSONObject(0).getString("alignment"));
    
        /*
        long res = 0;
        for (long i = 0; i<1200000000; i++){
            res += i;    
        }
        System.out.println(res);
        */
    }
}
