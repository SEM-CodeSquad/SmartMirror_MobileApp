package postApp.MqTTHandler;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Created by adinH on 2016-10-27.
 */
public class Retrievedata extends AsyncTask<String, Void, String> {
    String Returnthis;
    String topic;
    @Override
    protected String doInBackground(String... args) {
        try{
            JSONObject json = new JSONObject();
            // Please use this format when passing around a JSON obj
            //just for testing purposes
            JSONArray array = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("Color", args[0]);
            item.put("Text", args[1]);
            array.add(item);

            json.put("Postit", array);

            topic = args[2] + "/" + args[3];
            System.out.println(topic);

            String messagestring = json.toJSONString();
            HttpRequestSender post = new HttpRequestSender("codehigh.ddns.me","new client", topic, messagestring );

            String myUrl = "http://codehigh.ddns.me:5000/";
            //String myUrl = "http://localhost:5600/";

            post.executePost(myUrl);
            System.out.println(post.getHttpResponse());
            Returnthis = post.getHttpResponse();
            return post.getHttpResponse();

        }
        catch(Exception e)
        {
            return "Warning: did not publish";
        }

    }

    @Override
    protected void onPostExecute(String result) {

    }
}