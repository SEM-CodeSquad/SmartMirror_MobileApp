package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.MqTTHandler.MQTTClient;
import postApp.DataHandlers.MqTTHandler.MQTTSub;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;


/*
 * This class is the handler for the ShoppingList component. This is where the logic part of the component
 * takes place (addition,deletion,parsing etc.).
 */
public class ShoppingHandler implements Observer {
    ShoppingPresenter presenter;
    ShoppingView view;

    private static Activity parent;

    private String message;
    private String reply;
    private String replyID;
    private String preData;
    private LinkedList SPLList;
    private String clientID;
    private MQTTClient mqttClient;
    private String tempType = "";
    private String tempItem = "";
    private boolean value;

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter, String clientID) {
        this.value = false;
        MemoryPersistence persistence = new MemoryPersistence();
        this.clientID = clientID;
        this.mqttClient = new MQTTClient("tcp://prata.technocreatives.com", this.clientID + "@codehigh.com", persistence);
        this.view = ShoppingView;
        parent = view.getActivity();
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();


        if (this.clientID != "No mirror chosen") {
            listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
            listenSubscription("Gro/" + this.clientID + "@smartmirror.com/fetch");
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", "fetch");
        }
    }


    /**
     * Method that take a MQTT message(String) and parse it according to the content of the message.
     *
     * @param message A String containing the content of the MQTT message from the broker.
     */
    public void parseMessage(String message) {

        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);

            if (json.containsKey("reply")) {
                this.reply = json.get("reply").toString();
                if (reply.equalsIgnoreCase("done")) {
                    if (json.get("data") != null) {
                        this.SPLList.clear();
                        parseItem(message);
                    } else {
                        if (tempType == "add") {
                            SPLList.add(tempItem);
                            this.value = true;
                            this.updateMirrorList();
                        } else if (tempType == "delete") {
                            SPLList.remove(tempItem);
                            this.value = true;
                            this.updateMirrorList();
                        } else if (tempType == "delete-list") {
                            SPLList.clear();
                            this.value = true;
                            this.updateMirrorList();
                        }
                    }
                } else if (reply.equalsIgnoreCase("error")) {
                    toastMessage("Error updating List");
                }
            } else if (json.containsKey("client_id")) {
                // TODO Can be used for something.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will parse the content of the message(Json), get the result and put them into the SPLList.
     *
     * @param json containing the JsonObject from the broker as string.
     */
    private void parseItem(String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonOBJ = (JSONObject) parser.parse(json);
            if (jsonOBJ.get("data").equals("[]")) {

            } else if (!(jsonOBJ.get("data").equals("[]"))) {

                JSONArray jary = (JSONArray) jsonOBJ.get("data");
                System.out.println(jary);
                for (Object o : jary) {
                    if (o instanceof JSONObject) {
                        System.out.println(((JSONObject) o).get("item"));
                        this.SPLList.addLast(((JSONObject) o).get("item"));

                    }

                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void addClassObserver(MQTTSub sub) {
        sub.addObserver(this);
    }

    public void listenSubscription(final String topic) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MQTTSub subscriber = new MQTTSub(mqttClient, topic);
                    addClassObserver(subscriber);
                }
            });
            thread.start();
            System.out.println("listening to this topic " + topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMirrorList() {
        final Long timestamp = System.currentTimeMillis() / 1000L;
        final JsonBuilder builderMirror = new JsonBuilder();
        if (this.SPLList.isEmpty()) {
            System.out.println("alright empty list");
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), Integer.toString(0));

            JsonBuilder builder = new JsonBuilder();
            builder.execute("SPLToMirror", clientID, Long.toString(timestamp), "-1");

        } else {
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), Integer.toString(this.SPLList.size()), mirrorList(this.SPLList));
        }
    }

    public void updateList(String requestType, String item) {
        if (requestType == "add") {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType, item);
            tempType = requestType;
            tempItem = item;
        } else if (requestType == "delete") {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType, item); // The client id here is the one we should be using.
            tempType = requestType;
            tempItem = item;
        } else if (requestType == "delete-list") {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType);
            tempType = requestType;
        }
    }

    @Override
    public void update(Observable observable, final Object obj) {
        if (obj instanceof MqttMessage) {
            Thread thread = new Thread(new Runnable() {
                Object o = obj;

                @Override
                public void run() {
                    String str = o.toString();
                    System.out.println("The received message is " + str);

                    parseMessage(str);
                }
            });
            thread.start();
        }
    }

    public String mirrorList(LinkedList shoppingList) {
        String list = "";
        for (int i = 0; i < shoppingList.size(); i++) {
            list += shoppingList.get(i).toString() + ",";
        }
        System.out.println("the mirror list is " + list);
        return list;
    }

    public void toastMessage(final String msg) {
        parent.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(parent.getBaseContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean getBoolean() {
        return this.value;
    }

    public void setBooleanFalse() {
        this.value = false;
    }

    public LinkedList<String> getShoppingList() {
        return this.SPLList;
    }

}