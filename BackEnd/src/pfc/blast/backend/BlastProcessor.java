package pfc.blast.backend;

import java.io.PrintStream;

import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pfc.blast.backend.algorithm.BlastRunnerSeq;


public class BlastProcessor implements Callable<JSONArray> {
    
    ServletContext context;
    PrintStream psOut;
    String inputQuery;
    float inputPercentage = (float)1.0;
    RangeCreator rangeCreator;
    
    public BlastProcessor(RangeCreator rangeCreator, ServletContext context, String inputQuery, PrintStream psOut) {
        super();
        this.rangeCreator = rangeCreator;
        this.context = context;
        this.inputQuery = inputQuery;
        this.psOut = psOut;
    }

    public JSONArray call() {
        JSONArray res = null;
        String range = rangeCreator.getNextRange();
        StringTokenizer st = new StringTokenizer(range,"/");
        int initIndex = Integer.valueOf(st.nextToken());
        int endIndex = Integer.valueOf(st.nextToken());
        try {
            res = BlastRunnerSeq.executeJSON("p", context, inputPercentage, inputQuery, initIndex, endIndex, psOut);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}
