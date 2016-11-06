package postApp.logic.MqTTHandler;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 7); // Adding 5 days
            String output = dateFormat.format(c.getTime());

            JSONObject sendthis = new JSONObject();
            sendthis.put("messageFrom", "test");
            sendthis.put("timestamp", "12");

            // Please use this format when passing around a JSON obj
            // 2 diffrent outcomes if its a postit we publish to a diffrent topic if its a config we publish to a dif topic with a dif jsonobj
            if(args[1] == "postit") {
                JSONObject item = new JSONObject();
                sendthis.put("contentType", "post-it");
                item.put("postItID", args[6]);
                item.put("body", args[2]);
                item.put("senderStyle", args[3]);
                item.put("important", args[4]);
                item.put("expiresAt", "12");
                JSONArray jArray = new JSONArray();
                jArray.add(0, item);
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                sendthis.put("content", jArray);
                String messagestring = sendthis.toJSONString();
                System.out.println(messagestring);
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, messagestring);
            }
            else if(args[1] == "config"){
                sendthis.put("contentType", "settings");

                //item.put("News", args[3]);
                //item.put("Weather", args[4]);

                topic = "dit029/SmartMirror/" + args[0] + "/settings";
                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();

                test.put("busStop", args[2]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                System.out.println(message);
                System.out.println(topic);
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, message);

            }
            else if(args[1] == "pairing")
            {
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                sendthis.put("contentType", "pairing");

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("ClientID", args[2]);
                jArray.add(test);
                //String results = jArray.toJSONString().replaceAll("\\\\", "");
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                System.out.println("message");
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, message);
            }
            else if(args[1] == "postIt action")
            {
                topic = "dit029/SmartMirror/" + args[0] + "/postit"   ;
                sendthis.put("contentType", args[1]);

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("postItID", args[2]);
                test.put("action", args[3]);
                test.put("modification", args[4]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                System.out.println(message);
                System.out.println(topic);
                post = new HttpRequestSender("codehigh.ddns.me", "new client", topic, message);
            }
            post.executePost(myUrl);
            System.out.println(post.getHttpResponse());
            Returnthis = post.getHttpResponse(); //execute a post http request with httprequestsenderclass
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