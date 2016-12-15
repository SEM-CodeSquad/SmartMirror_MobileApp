package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
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
    private ShoppingPresenter presenter;
    private ShoppingView view;
    private static Activity parent;
    private LinkedList SPLList;
    private String clientID;
    private MQTTClient mqttClient;
    private String tempType = "";
    private String tempItem = "";
    private boolean value;
    private boolean connected;

    /**
     * Constructor for the shoppinghandler class, that start a mqtt client, listens to a subscription. The presenter, view and clientID is set in this method.
     *
     * @param ShoppingView      The view
     * @param ShoppingPresenter The presenter
     * @param clientID          The clientID
     */
    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter, String clientID) {
        this.value = false;
        this.connected = false;
        this.clientID = clientID;
        this.view = ShoppingView;
        parent = view.getActivity();
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();
        MemoryPersistence persistence = new MemoryPersistence();
        this.mqttClient = new MQTTClient("tcp://prata.technocreatives.com", this.clientID + "@codehigh.com", persistence);
        publishEchoMessage();
    }

    public void initList(){
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com/fetch");
        JsonBuilder builder = new JsonBuilder();
        builder.execute("shoppinglist", this.clientID + "@smartmirror.com", "fetch");
    }


    /**
     * Method that take a MQTT message(String) and parse it according to the content of the message.
     *
     * @param message A String containing the content of the MQTT message from the broker.
     */
    private void parseMessage(String message) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);

            if (json.containsKey("reply")) {
                String reply = json.get("reply").toString();
                if (reply.equalsIgnoreCase("done")) {
                    if (json.get("data") != null) {
                        this.SPLList.clear();
                        parseItem(message);
                    } else {
                        if (tempType.equals("add")) {
                            SPLList.add(tempItem);
                            this.value = true;
                            this.updateMirrorList();
                        } else if (tempType.equals("delete")) {
                            SPLList.remove(tempItem);
                            this.value = true;
                            this.updateMirrorList();
                        } else if (tempType.equals("delete-list")) {
                            SPLList.clear();
                            this.value = true;
                            this.updateMirrorList();
                        }
                    }
                } else if (reply.equalsIgnoreCase("alive")) {
                    this.connected = true;
                } else if (reply.equalsIgnoreCase("error")) {
                    toastMessage("Error updating List");
                }
            } else if (json.containsKey("client_id")) {
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
                for (Object o : jary) {
                    if (o instanceof JSONObject) {
                        System.out.println(((JSONObject) o).get("item"));
                        this.SPLList.addLast(((JSONObject) o).get("item"));
                    }
                }
            }
            presenter.updateListView();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a observer
     *
     * @param sub the Mqtt sub we add a observer to
     */
    private void addClassObserver(MQTTSub sub) {
        sub.addObserver(this);
    }

    private void listenSubscription(final String topic) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MQTTSub subscriber = new MQTTSub(mqttClient, topic);
                    addClassObserver(subscriber);
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * a method to update the mirror list. Creates a json builder that executes a HttpRequest with the updated list
     */
    private void updateMirrorList() {
        final Long timestamp = System.currentTimeMillis() / 1000L;
        final JsonBuilder builderMirror = new JsonBuilder();
        if (this.SPLList.isEmpty()) {
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), "0");
            JsonBuilder builder = new JsonBuilder();
            builder.execute("SPLToMirror", clientID, Long.toString(timestamp), "-1");
        } else {
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), Integer.toString(this.SPLList.size()), mirrorList(this.SPLList));
        }
    }

    /**
     * Depending on requesttype we execute the jsonbuilder with a diffrent values.
     *
     * @param requestType The requesttype
     * @param item        The item
     */
    public void updateList(String requestType, String item) {
        if (requestType.equals("add")) {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType, item);
            tempType = requestType;
            tempItem = item;
        } else if (requestType.equals("delete")) {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType, item); // The client id here is the one we should be using.
            tempType = requestType;
            tempItem = item;
        } else if (requestType.equals("delete-list")) {
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist", this.clientID + "@smartmirror.com", requestType);
            tempType = requestType;
            tempItem = item;
        }
    }

    /**
     * Method to that listens to a class and then if we receive a message we start a new thread to parse the message
     *
     * @param observable the observable
     * @param obj        the object, in this case we are checking for a MqttMessage
     */
    @Override
    public void update(Observable observable, final Object obj) {
        if (obj instanceof MqttMessage) {
            Thread thread = new Thread(new Runnable() {
                Object o = obj;

                @Override
                public void run() {
                    String str = o.toString();
                    parseMessage(str);
                }
            });
            thread.start();
        }
    }

    /**
     * In this method each element in the LinkedList to the string list.
     *
     * @param shoppingList the shoppinglist we take each element from and add it to the string list
     * @return the list
     */
    private String mirrorList(LinkedList shoppingList) {
        String list = "";
        for (int i = 0; i < shoppingList.size(); i++) {
            list += shoppingList.get(i).toString() + ",";
        }
        return list;
    }

    /**
     * A method for making a Ui thread that makes a toast with a message.
     *
     * @param msg the message
     */
    private void toastMessage(final String msg) {
        parent.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(parent.getBaseContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void publishEchoMessage() {
        this.connected = false;
        final Long timeStamp = System.currentTimeMillis() / 1000L;
        final Echo echo = new Echo("dit029/SmartMirror/" + this.clientID + "/shoppingList", "smartMirror");
        JsonBuilder echoBuilder = new JsonBuilder();
        echoBuilder.execute("SPLToMirror", this.clientID, Long.toString(timeStamp), "-2");

        final ProgressDialog dialog = new ProgressDialog(parent);
        dialog.setTitle("Shopping List");
        dialog.setMessage("Checking for your SmartMirror...");
        dialog.setCancelable(false);
        dialog.show();
       final Thread echoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (connected){
                        break;
                    }
                }
                dialog.cancel();
                echo.disconnect();
                if (!connected) {
                    parent.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                            builder.setTitle("Problem Occurred");
                            builder.setMessage("Retry searching for mirror?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    publishEchoMessage();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    });
                } else if (connected) {
                    initList();
                    toastMessage("Connected");
                }
            }
        });
        echoThread.start();
    }

        /**
         * @return the value
         */

    public boolean getBoolean() {
        return this.value;
    }
    public boolean getConnectedBoolean(){
        return this.connected;
    }

    /**
     * Sets value to false
     */
    public void setBooleanFalse() {
        this.value = false;
    }

    public void updateListView(){
        presenter.updateListView();
    }

    /**
     * @return The linked list shoppinglist
     */
    public LinkedList<String> getShoppingList() {
        return this.SPLList;
    }

}
