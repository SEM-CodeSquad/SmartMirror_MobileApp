package postApp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;

/**
 * Created by adinH on 2016-12-08.
 */

public class TestingFragment extends Fragment {
    View myView;
    int count = 0;
    String messagestring;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.testlayout, container, false);
        Button pubhttp = (Button)myView.findViewById(R.id.pubhtpp);


        pubhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newjson();
                HttpRequestSender kk = new HttpRequestSender("codehigh.ddns.me", "dit029/SmartMirror/" +((NavigationActivity) getActivity()).getMirror() +"/postit",messagestring, "0", "false");
                try {
                    System.out.println("Expecting count that is equal to: " + count);
                    System.out.println("Before execution");
                    System.out.println(kk.execute().get());
                    System.out.println("After execution");
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return myView;
    }

public void newjson(){
    JSONObject sendthis = new JSONObject();
    JSONArray jArray = new JSONArray();
    JSONObject item = new JSONObject();
    sendthis.put("messageFrom", "Adin");
    sendthis.put("timestamp", "213123");
    sendthis.put("contentType", "post-it");
    item.put("postItID", "HHHHHHH"+Integer.toString(count) +"HHHHHHH");
    item.put("body", "HHHHHHH"+Integer.toString(count)+"HHHHHHH");
    item.put("senderStyle", "HHHHHHH"+Integer.toString(count)+"HHHHHHH");
    item.put("expiresAt", count);
    jArray.add(0, item);
    sendthis.put("content", jArray);
    messagestring = sendthis.toJSONString();
}
}


 class HttpRequestSender extends AsyncTask <String, Void, String> {

    private String brokerHostname;
    private String topic;
    private String msg;
    private String qos;
    private String retain;
    private String httpResponse;

    /**
     * Constructor for the http sender.
     *
     * @param brokerHostname String hostname for the broker
     * @param topic          String topic to be published on the broker
     * @param msg            String message to be published in the broker
     */
    public HttpRequestSender(String brokerHostname, String topic, String msg, String qos, String retain) {
        this.brokerHostname = brokerHostname;
        this.topic = topic;
        this.msg = msg;
        this.qos = qos;
        this.retain = retain;
    }

    /**
     * This method connects to a web server and send a query string to the web server. The query string must contain
     * the necessary parameters otherwise the web server will not accept it.
     *
     * @param targetURL String containing the url and port to the web server
     */
    public void executePost(String targetURL) {

        HttpURLConnection connection = null;

        try {
            String encodedMsg = URLEncoder.encode(this.msg, "utf-8");
            String query = "?broker=" + this.brokerHostname + "&topic=" + this.topic + "&msg=" + encodedMsg
                    + "&qos=" + this.qos + "&retain=" + this.retain + "&password=CodeHigh_SmartMirror";

            //Create connection
            URL url = new URL(targetURL + query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);

            //Get Response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            this.httpResponse = response.toString();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

     @Override
     protected String doInBackground(String... args) {
         try {
             executePost("http://codehigh.ddns.me:8080/");
         } catch (Exception e) {
             return "Warning: did not publish";
         }
         return "HI";
     }




    /**
     * Method to get the String containing the response from the web server.
     *
     * @return String web server response
     */
    public String getHttpResponse() {
        return httpResponse;
    }
}
