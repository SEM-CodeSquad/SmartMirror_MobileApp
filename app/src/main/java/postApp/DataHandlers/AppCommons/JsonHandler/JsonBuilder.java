package postApp.DataHandlers.AppCommons.JsonHandler;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import postApp.DataHandlers.MqTTHandler.HttpRequestSender;


public class JsonBuilder extends AsyncTask<String, Void, String> {
    private String topic;
    private HttpRequestSender post;
    private String myUrl = "http://codehigh.ddns.me:8080/";

    @Override
    protected String doInBackground(String... args) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 5); // Adding 5 days
            long timestamp = c.getTimeInMillis() / 1000;

            // Please use this format when passing around a JSON obj
            // 2 different outcomes if its a postit we publish to a different topic if its a config we publish to a dif topic with a dif jsonobj
            if (args[1].equals("postit")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                JSONArray jArray = new JSONArray();
                JSONObject item = new JSONObject();
                sendthis.put("messageFrom", args[6]);
                sendthis.put("timestamp", Long.toString(timestamp));
                sendthis.put("contentType", "post-it");
                item.put("postItID", args[5]);
                item.put("body", args[2]);
                item.put("senderStyle", args[3]);
                item.put("expiresAt", args[4]);
                jArray.add(0, item);
                sendthis.put("content", jArray);
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                String messagestring = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, messagestring, "0", "false");
            } else if (args[1].equals("config")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                sendthis.put("contentType", "settings");

                topic = "dit029/SmartMirror/" + args[0] + "/settings";
                JSONArray jArray = new JSONArray();
                JSONObject jOBJ = new JSONObject();
                if(args[3].equals("newsedit")){
                    jOBJ.put("news", args[4]);
                }
                if(args[3].equals("busedit")){
                    jOBJ.put("busStop", args[4]);
                }
                else if(args[3].equals("weatheredit")){
                    jOBJ.put("weather", args[4]);
                }

                jArray.add(jOBJ);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");

            } else if (args[1].equals("pairing")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                sendthis.put("contentType", "pairing");

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("ClientID", args[2]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            } else if (args[1].equals("postIt action")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[5]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/postit";
                sendthis.put("contentType", args[1]);

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("postItID", args[2]);
                test.put("action", args[3]);
                test.put("modification", args[4]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();

                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            } else if (args[1].equals("preferences")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/preferences";
                sendthis.put("contentType", args[1]);

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("news", args[3]);
                test.put("bus", args[4]);
                test.put("weather", args[5]);
                test.put("clock", args[6]);
                test.put("device", args[7]);
                test.put("greetings", args[8]);
                test.put("postits", args[9]);
                test.put("shoppinglist", args[10]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            } else if (args[1].equals("preferencesHide")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/preferences";
                sendthis.put("contentType", "preferences");

                JSONArray jArray = new JSONArray();
                JSONObject test = new JSONObject();
                test.put("showOnly", args[3]);
                jArray.add(test);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            } else if (args[0].equals("shoppinglist")) {
                String send = "";
                if (args.length==3) {
                    send = "{\"client_id\":\"" + args[1]+ "\"," +
                            "\"list\":" + "\"SmartMirror Shopping list\"" + "," +
                            "\"request\":\"" + args[2]+"\"}";
                }
                else  if (!(args[3] == null)) {

                    send = "{\"client_id\":\"" + args[1]+ "\"," +
                            "\"list\":" + "\"SmartMirror Shopping list\"" + "," +
                            "\"request\":\"" + args[2]+"\"," +
                            "\"data\":{\"item\":\"" + args[3] + "\"}}";
                }
//                sendthis.put("client-id", args[1]);
//                sendthis.put("list", "SmartMirror Shopping List");
//                sendthis.put("request", args[2]);
//                if(!(args[3]==null)) {
//                    JSONObject item = new JSONObject();
//                    JSONArray jArray = new JSONArray();
//                    String tmp = args[4];
//                    for (int i = 0; i < Integer.parseInt(args[3]); i++) {
//                        item.put("item"+i, tmp.substring(0,tmp.indexOf(",")));
//                        tmp = tmp.substring(tmp.indexOf(",")+1);
//                    }
//                    jArray.add(0, item);
//                    sendthis.put("content", jArray);
//                }
                topic = "Gro/" + args[1];
                //String messageString = sendthis.toJSONString();
                //TODO the following part, codehigh.ddns.me needs to be changed
                post = new HttpRequestSender("54.154.153.243", topic, send, "1", "false");

            } else if (args[0].equals("SPLToMirror")) {

                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[1]);
                sendthis.put("timestamp", args[2]);
                sendthis.put("contentType", "shoppingList");

                //Check how long the argument is and add item accordingly

                if (!(args[3] == null)) {

                    JSONObject item = new JSONObject();
                    JSONArray jArray = new JSONArray();
                    String tmp = args[4];
                    for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                        item.put("item" + i, tmp.substring(0, tmp.indexOf(",")));
                        tmp = tmp.substring(tmp.indexOf(",") + 1);
                    }
                    jArray.add(0, item);
                    sendthis.put("content", jArray);
                } else if (args[3] == null) {
                    JSONObject item = new JSONObject();
                    JSONArray jArray = new JSONArray();
                    item.put("item", "empty");
                    jArray.add(0, item);
                    sendthis.put("content", jArray);
                }

                topic = "dit029/SmartMirror/" + args[1] + "/shoppingList";
                String messageString = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, messageString, "0", "false");
            }

            post.executePost(myUrl);
            return post.getHttpResponse();

        } catch (Exception e) {
            return "Warning: did not publish";
        }

    }

    @Override
    protected void onPostExecute(String result) {

    }
}