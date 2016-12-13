package postApp.DataHandlers.AppCommons.Vasttrafik;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that Uses västtrafiks api to get the currents locations closest stop, using the authorization bearer we got from
 * generateaccesscode

 */
public class TravelByLoc extends AsyncTask<String, Void, String> {

    /**
     * Asynctask that gets the closest stop
     * @param args args[0] is generateaccess code, args[1] is lat cordinate, third is args[2] coordinate.
     * @return the closest stop as a string
     */
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        URL url;
        try {

            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/location.nearbystops?originCoordLat=" + args[1] + "&originCoordLong=" + args[2] + "&maxNo=1&format=json");
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