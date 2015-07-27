package app.demo2.webservice2;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * Created by prakash on ??-??-????.
 */
public class HttpManager {
    public static String getData(String uri){
        BufferedReader reader = null;
        try {
               URL url= new URL(uri);
               HttpURLConnection con = (HttpURLConnection) url.openConnection();
               StringBuilder sb = new StringBuilder();
               reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
               String line;
               while ((line = reader.readLine())!=null){
                   sb.append(line+"\n");
               }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
              if (reader != null)
              {
                  try {
                      reader.close();
                  } catch (Exception e) {
                      e.printStackTrace();
                      return null;
                  }
              }
        }
    }








}


