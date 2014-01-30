package pfc.blast.frontend;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.List;

/**
 * This class consumes the web service and store the response for its following
 * parse to HTML.
 *
 * @author Juan Carlos Ruiz Rico
 *
 */
public class WSThread extends Thread {

    private String url;
    private List<String> myResponse;

    public WSThread(String url, List<String> resp) {
        this.url = url;
        this.myResponse = resp;
    }

    @Override
    public void run() {
        try {
            URL wsUrl = new URL(this.url);
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

        }
    }

}
