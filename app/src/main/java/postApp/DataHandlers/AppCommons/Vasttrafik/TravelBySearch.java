package postApp.DataHandlers.AppCommons.Vasttrafik;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by adinH on 2016-11-02.
 */
   public class TravelBySearch extends AsyncTask<String, Void, String> {

    String loc;

        protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        URL url = null;
        try {

            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/location.name?input=" + args[1] + "&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty( "Authorization:", "Bearer " + args[0]);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result.toString());
        return result.toString();

    }

}