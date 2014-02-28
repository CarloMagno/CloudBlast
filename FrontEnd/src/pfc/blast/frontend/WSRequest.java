package pfc.blast.frontend;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.List;


public class WSRequest extends Thread {
    
    private String url;
    private RangeCreator rangeCreator;
    private List<String> myResponse;
    
    public WSRequest(String url, RangeCreator rangeCreator, List<String> resp) {
        this.url = url;
        this.rangeCreator = rangeCreator;
        this.myResponse = resp;
    }

    public void run() {
        while(!rangeCreator.isCompleted()){
            try {                
                URL wsUrl = new URL(this.url+rangeCreator.getNextRange());
                URLConnection conn = wsUrl.openConnection();
                BufferedReader in =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                String res = "";
                while ((inputLine = in.readLine()) != null) {
                    res = res.concat(inputLine);
                }
                in.close();
                myResponse.add(res);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
