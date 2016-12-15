/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.DataHandlers.AppCommons.JsonHandler;

import android.os.AsyncTask;

import com.vdurmont.emoji.EmojiParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Date;

import postApp.DataHandlers.MqTTHandler.HttpRequestSender;

/**
 * Class for used for building a json object and then starting a httprequestparser with this fully build json message and url.
 */
@SuppressWarnings("unchecked")
public class JsonBuilder extends AsyncTask<String, Void, String> {
    private HttpRequestSender post;

    /**
     * The following function contains the JSONBuilder for different functions needed for both mirror and the Shopping list
     * @param args arguments being passed to the parser
     * @return return the respond of the HTTP
     */
    @Override
    protected String doInBackground(String... args) {
        try {
            //We get the current timestamp here
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 5); // Adding 5 days
            long timestamp = c.getTimeInMillis() / 1000;

            //If args[1] is postit we know we are supposed to publish a postit so we adjust all the args[] to that.
            String topic;
            if (args[1].equals("postit")) {
                JSONObject sendthis = new JSONObject();
                JSONArray jArray = new JSONArray();
                JSONObject item = new JSONObject();
                sendthis.put("messageFrom", args[6]);
                sendthis.put("timestamp", Long.toString(timestamp));
                sendthis.put("contentType", "post-it");
                item.put("postItID", args[5]);
                String text = EmojiParser.parseToAliases(args[2]);
                System.out.println(text);
                item.put("body", text);
                item.put("senderStyle", args[3]);
                item.put("expiresAt", args[4]);
                jArray.add(0, item);
                sendthis.put("content", jArray);
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                String messagestring = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, messagestring, "0", "false");
            }
            //If args[1] is postit we know we are supposed to publish the settings so we adjust all the args[] to that.
            else if (args[1].equals("config")) {
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                sendthis.put("contentType", "settings");

                topic = "dit029/SmartMirror/" + args[0] + "/settings";
                JSONArray jArray = new JSONArray();
                JSONObject jOBJ = new JSONObject();
                //Which type of settings to publish
                if (args[3].equals("newsedit")) {
                    jOBJ.put("news", args[4]);
                }
                if (args[3].equals("busedit")) {
                    jOBJ.put("busStop", args[4]);
                } else if (args[3].equals("weatheredit")) {
                    jOBJ.put("weather", args[4]);
                }

                jArray.add(jOBJ);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");

            }

            //If args[1] is pairing we know we are supposed to publish pairing message so we adjust all the args[] to that.
            else if (args[1].equals("pairing")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/" + args[1];
                sendthis.put("contentType", "pairing");

                JSONArray jArray = new JSONArray();
                JSONObject theobj = new JSONObject();
                theobj.put("ClientID", args[2]);
                jArray.add(theobj);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            }
            //If args[1] is Postit ACtion we know we are supposed to publish a edit or delete so we adjust all the args[] to that.
            else if (args[1].equals("postIt action")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[5]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/postit";
                sendthis.put("contentType", "post-it action");

                JSONArray jArray = new JSONArray();
                JSONObject theobj = new JSONObject();
                theobj.put("postItID", args[2]);
                theobj.put("action", args[3]);
                String text = EmojiParser.parseToAliases(args[4]);
                System.out.println(text);
                theobj.put("modification", text);
                jArray.add(theobj);
                sendthis.put("content", jArray);

                String message = sendthis.toJSONString();

                post = new HttpRequestSender("54.154.153.243", topic, message, "0", "false");
            }
            //If args[1] is preferences we know we are supposed to publish the preferences so we adjust all the args[] we put in the object to that to that.
            else if (args[1].equals("preferences")) {
                //TODO the actual content should be variables and put it in the sendthis object.
                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[2]);
                sendthis.put("timestamp", Long.toString(timestamp));
                topic = "dit029/SmartMirror/" + args[0] + "/preferences";
                sendthis.put("contentType", args[1]);

                JSONArray jArray = new JSONArray();
                JSONObject theobj = new JSONObject();
                theobj.put("news", args[3]);
                theobj.put("bus", args[4]);
                theobj.put("weather", args[5]);
                theobj.put("clock", args[6]);
                theobj.put("device", args[7]);
                theobj.put("greetings", args[8]);
                theobj.put("postits", args[9]);
                theobj.put("shoppinglist", args[10]);
                jArray.add(theobj);
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
            }
            //If args[0] equals shoppinglist we make a string instead of a jsonobject since the group we are cooperating with
            //wanted the order in a specific order
            else if (args[0].equals("shoppinglist")) {
                String send = "";
                if (args.length == 3) {
                    send = "{\"client_id\":\"" + args[1] + "\"," +
                            "\"list\":" + "\"SmartMirror Shopping list\"" + "," +
                            "\"request\":\"" + args[2] + "\"}";
                } else if (!(args[3] == null)) {

                    send = "{\"client_id\":\"" + args[1] + "\"," +
                            "\"list\":" + "\"SmartMirror Shopping list\"" + "," +
                            "\"request\":\"" + args[2] + "\"," +
                            "\"data\":{\"item\":\"" + args[3] + "\"}}";
                }

                topic = "Gro/" + args[1];

                post = new HttpRequestSender("54.154.153.243", topic, send, "1", "false");

            }
            //If the args[0] equals spltomirror we construct a json object that is sent to the smartmirror
            else if (args[0].equals("SPLToMirror")) {

                JSONObject sendthis = new JSONObject();
                sendthis.put("messageFrom", args[1]);
                sendthis.put("timestamp", args[2]);


                if ((!(args[3].equals("0")))&&(!(args[3].equals("-1")))){
                    sendthis.put("contentType", "shoppingList");
                    JSONObject item = new JSONObject();
                    JSONArray jArray = new JSONArray();
                    String tmp = args[4];
                    for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                        item.put("item" + i, tmp.substring(0, tmp.indexOf(",")));
                        tmp = tmp.substring(tmp.indexOf(",") + 1);
                    }
                    jArray.add(0, item);
                    sendthis.put("content", jArray);
                } else if (args[3].equals("0")) {
                    sendthis.put("contentType", "shoppingList");
                    JSONObject item = new JSONObject();
                    JSONArray jArray = new JSONArray();
                    item.put("item", "empty");
                    jArray.add(0, item);
                    sendthis.put("content", jArray);
                }else if(args[3].equals("-1")){
                    sendthis.put("contentType", "Create list");
                    sendthis.put("content", "Create new list "+args[1]);
                }else if(args[3].equals("-2")){
                    sendthis.put("contentType","echo");
                    sendthis.put("content","alive");
                }

                topic = "dit029/SmartMirror/" + args[1] + "/shoppingList";
                String messageString = sendthis.toJSONString();
                post = new HttpRequestSender("54.154.153.243", topic, messageString, "0", "false");
            }

            String myUrl = "http://codehigh.ddns.me:8080/";
            post.executePost(myUrl);
            return post.getHttpResponse();

        } catch (Exception e) {
            return "Warning: did not publish";
        }

    }
}