package postApp.DataHandlers.AppCommons.Vasttrafik;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that generates a accesscode from vässtrafik
 */

public class GenerateAccessCode extends AsyncTask<String, Void, String> {

    /**
     * A curl command that request authorization with secret key and access key from västtrafik
     * @return the code that is gotten from västtrafik
     */
    protected String doInBackground(String... args) {
        StringBuilder result = new StringBuilder();

        URL url;
        try
        {
            String authParam = "Basic b3BPMlJKT3o3em9RaEhseE5VS0ozWXdoaVJNYTpuajR3bmc2R3dqUWxBRHk1aktRRFR1aHpFdGNh";
            url = new URL("https://api.vasttrafik.se:443/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authParam);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            wr.write("grant_type=client_credentials");
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                result.append(line);

            }
            rd.close();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result.substring(result.lastIndexOf(":") + 2, result.length() - 2);
    }
}
