package postApp.DataHandlers.AppCommons.Vasttrafik;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;


/**
 * Async task that travels by search with the firs argument being the authorization code, and the second the name.
 */
   public class TravelBySearch extends AsyncTask<String, Void, String> {

    /**
     * Async task for travelling by search
     * @param args args[0] = auth code, args[1] = search
     * @return the result as json
     */
        protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        URL url;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}