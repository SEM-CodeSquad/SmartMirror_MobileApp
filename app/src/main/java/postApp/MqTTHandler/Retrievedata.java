package postApp.MqTTHandler;

import android.os.AsyncTask;

import org.json.simple.JSONObject;


/**
 * Created by adinH on 2016-10-27.
 */
public class Retrievedata extends AsyncTask<String, Void, String> {
    String Returnthis;
    String topic;
    HttpRequestSender post;
    String myUrl = "http://codehigh.ddns.me:5000/";
    @Override
    protected String doInBackground(String... args) {
        try{
            // Please use this format when passing around a JSON obj
            // 2 diffrent outcomes if its a postit we publish to a diffrent topic if its a config we publish to a dif topic with a dif jsonobj
            if(args[1] == "postit") {
                JSONObject item = new JSONObject();
                item.put("Color", args[2]);
                item.put("Text", args[3]);
                item.put("UUID", args[4]);


                topic = "SmartMirror/" + args[0] + "/" + args[1];
                System.out.println(topic);

                String messagestring = item.toJSONString();
                System.out.println(messagestring);
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, messagestring);
            }
            else if(args[1] == "config"){
                JSONObject item = new JSONObject();
                item.put("Bus", args[2]);
                item.put("News", args[3]);
                item.put("weather", args[4]);
                item.put("UUID", args[5]);


                topic = "SmartMirror/" + args[0] + "/" + args[1];
                System.out.println(topic);
                String messagestring = item.toJSONString();
                System.out.println(messagestring);
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, messagestring);

            }
            post.executePost(myUrl);
            System.out.println(post.getHttpResponse());
            Returnthis = post.getHttpResponse(); //execute a post http request with httprequestsenderclass
            return post.getHttpResponse();

        }// catches exceptions
        catch(Exception e)
        {
            return "Warning: did not publish";
        }

    }

    @Override
    protected void onPostExecute(String result) {

    }
}