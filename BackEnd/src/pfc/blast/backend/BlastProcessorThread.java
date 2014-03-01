package pfc.blast.backend;

import java.io.PrintStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import org.json.JSONArray;
import org.json.JSONException;

import pfc.blast.backend.algorithm.BlastRunnerSeq;

public class BlastProcessorThread extends Thread {

    private ServletContext context;
    private PrintStream psOut;
    private String query;
    private float inputPercentage = (float)1.0;
    private RangeCreator rangeCreator;
    private List<JSONArray> results;

    public BlastProcessorThread(RangeCreator rangeCreator, ServletContext context, String query,
                         PrintStream psOut, List<JSONArray> res) {
        this.rangeCreator = rangeCreator;
        this.context = context;
        this.query = query;
        this.psOut = psOut;
        this.results = res;
    }


    @Override
    public void run() {
        JSONArray res = null;
        String range = rangeCreator.getNextRange();
        StringTokenizer st = new StringTokenizer(range,"/");
        int initIndex = Integer.valueOf(st.nextToken());
        int endIndex = Integer.valueOf(st.nextToken());
        try {
            res = BlastRunnerSeq.executeJSON("p", context, inputPercentage, query, initIndex, endIndex, psOut);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        results.add(res);
    }
}
