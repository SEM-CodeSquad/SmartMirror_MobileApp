package postApp.MqTTHandler;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Created by adinH on 2016-10-27.
 */
public class Retrievedata extends AsyncTask<String, Void, String> {
    String Returnthis;
    @Override
    protected String doInBackground(String... args) {
        try{
            JSONObject json = new JSONObject();
            // Please use this format when passing around a JSON obj
            //just for testing purposes
            JSONArray array = new JSONArray();
            JSONObject item = new JSONObject();
            System.out.println(args[0]);
            item.put("Color", args[0]);
            item.put("Title", args[1]);
            item.put("Text", args[2]);
            array.add(item);

            json.put("Postit", array);

            String messagestring = json.toJSONString();
            HttpRequestSender post = new HttpRequestSender("codehigh.ddns.me","new client", "test", messagestring );

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