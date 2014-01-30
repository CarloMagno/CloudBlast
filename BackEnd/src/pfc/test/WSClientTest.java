package pfc.test;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;

public class WSClientTest {
    static String str = "https://java4-triala1xh.java.em1.oraclecloudapps.com/WSTest/GEYGEYGEYGEY";
    static String str2 = "http://www.google.com";
    static String str1 = "http://java4-triala1xh.java.em1.oraclecloudapps.com/main.jsp";
    
    public static void main1(String[] args){
        //Client c = Client.create();
        //WebResource resource = c.resource(str);
        //String response = resource.get(String.class);    
        //System.out.println(response);
    }

    public static void main(String[] args) {
        try {
            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " +
                                           conn.getResponseCode());
            }

            BufferedReader br =
                new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String apiOutput;
            while ((apiOutput = br.readLine()) != null) {
                System.out.println(apiOutput);
            }
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();    
        }
    }

    public static void main2(String[] args) {
        try {
            System.out.println("Creamos conexion.");
            URL url = new URL(str);
            System.out.println("Abrimos conexion.");
            URLConnection urlc = url.openConnection();
            System.out.println("Leemos conexion.");
            BufferedReader bfr =
                new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            System.out.println("Imprimimos datos.");
            String line, title, des;
            while ((line = bfr.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Fin conexion.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
