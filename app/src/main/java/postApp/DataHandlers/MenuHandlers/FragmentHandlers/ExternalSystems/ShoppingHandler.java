package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.MqTTHandler.MQTTClient;
import postApp.DataHandlers.MqTTHandler.MQTTSub;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;


/**
 * This class is the handler for the ShoppingList component. This is where the logic part of the component
 * takes place (addition,deletion,parsing etc.).
 */

public class ShoppingHandler implements Observer {
    private ShoppingPresenter presenter;
    private ShoppingView view;
    private static Activity parent;          /* Parent activity of the Shopping List Fragment*/
    private LinkedList SPLList;
    private String clientID;
    private MQTTClient mqttClient;
    private String tempType = "";           /* The last request type is stored in this variable*/
    private String tempItem = "";           /* The last item updated to the list is stored in this variable*/
    private boolean value;                  /* The boolean used to identify whether an update to the list has been done since it being called*/
    private boolean connected;              /* The boolean used to identify whether the mirror is online and connected to the phone.

    /**
     * Constructor for the shoppinghandler class, that initiates various variables
     * starts an mqtt client and checks whether the mirror is connected with the phone and is online
     *
     * @param ShoppingView      The Shopping List view
     * @param ShoppingPresenter The Shopping List presenter
     * @param clientID          The UUID of the Smart Mirror which will be mentioned as the Client ID.
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

    /**
     * A method used to publish an echo message to the Smart Mirror. This method is used as check to ensure
     * updates to the shopping list are not made without the connected SmartMirror having any knowledge about it.
     * After publishing an echo message, the method waits for 5 seconds for the reply, and upon no received echo
     * reply, prompts the user whether or not he wants to retest the connection between the phone and the mirror
     * or not.
     */
    public void publishEchoMessage() {
        this.connected = false;
        final Long timestamp = System.currentTimeMillis() / 1000L;
        final Echo echo = new Echo("dit029/SmartMirror/" + this.clientID + "/echo", "smartMirror");
        echo.addObserver(this);
        JsonBuilder echoBuilder = new JsonBuilder();
        echoBuilder.execute("SPLToMirror", this.clientID, Long.toString(timestamp), "-2");

        final ProgressDialog dialog = new ProgressDialog(parent);
        dialog.setTitle("Shopping List");
        dialog.setMessage("Checking for your SmartMirror...");
        dialog.setCancelable(false);
        dialog.show();
        final Thread echoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (connected) {
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
                                    FragmentManager fragment = view.getFragmentManager();
                                    fragment.beginTransaction().replace(R.id.content_frame, new PostitView()).addToBackStack(null).commit();
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
     * The method here makes 2 threads that listen to a topic required for the Shopping List feature.
     * Along with listening to the mentioned topics, this method also executes a "fetch" request to
     * the Shopping List system
     */
    public void initList() {
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com/fetch");
        JsonBuilder builder = new JsonBuilder();
        builder.execute("shoppinglist", this.clientID + "@smartmirror.com", "fetch");
    }

    /**
     * Method that according to the requesttype executes a JSON Builder HTTP request to the Shopping List server.
     *
     * @param requestType The type of request to the Shopping List Server.
     * @param item        The item that needs to be updated (added or removed)
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
     * A method to update the Shopping List displayed on the mirror.
     * Creates a json builder that executes a HttpRequest with the updated list.
     */
    private void updateMirrorList() {
        final Long timestamp = System.currentTimeMillis() / 1000L;
        final JsonBuilder builderMirror = new JsonBuilder();
        if (this.SPLList.isEmpty()) {
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), "0");
            JsonBuilder builder = new JsonBuilder();
            builder.execute("SPLToMirror", this.clientID, Long.toString(timestamp), "-1");
        } else {
            builderMirror.execute("SPLToMirror", this.clientID, Long.toString(timestamp), Integer.toString(this.SPLList.size()), mirrorList(this.SPLList));
        }
    }

    /**
     * An overridden method made when a MQTT subscriber is made and an observer is added to the subscriber.
     * Upon receiving a message on the topic as stated in the parameters of a subscriber this method
     * starts a new thread to parse the received message.
     *
     * @param observable the observable
     * @param obj        The object received by the Observer (in this case this class).
     */
    @Override
    public void update(Observable observable, Object obj) {
        if (obj instanceof MqttMessage) {
            final MqttMessage mqttMessage = (MqttMessage) obj;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String str = mqttMessage.toString();
                    parseMessage(str);
                }
            });
            thread.start();
        }
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
                } else if (reply.equalsIgnoreCase("error")) {
                    toastMessage("Error updating List");
                }
            } else if (json.containsKey("client_id")) {
            } else if (json.containsKey("content")) {
                String content = json.get("content").toString();
                JSONParser jsonParser = new JSONParser();
                JSONObject object = (JSONObject) jsonParser.parse(content);
                if (object.containsKey("reply")){
                    if (object.get("reply").toString().equals("alive")) {
                        System.err.println("ALIVE");
                        this.connected = true;
                    }
                }
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
                       // System.out.println(((JSONObject) o).get("item"));
                        this.SPLList.addLast(((JSONObject) o).get("item"));
                    }
                }
            }
           updateListView();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to make an MQTT subscriber that listens to the specified topic
     *
     * @param topic is the topic that a subscriber needs to subscribe to.
     */
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
     * Method to add this class as an observer to a subscriber.
     *
     * @param sub the Mqtt subscriber we add an observer to.
     */
    private void addClassObserver(MQTTSub sub) {
        sub.addObserver(this);
    }


    /**
     * This method used to return a string containing all the items that are currently present
     * in the shopping list. This return method is then used when sending the list items to the
     * SmartMirror.
     *
     * @param shoppingList The LinkedList from which elements are taken and stored in a string.
     * @return the String containing all elements in shoppingList separated by commas.
     */
    private String mirrorList(LinkedList shoppingList) {
        String list = "";
        for (int i = 0; i < shoppingList.size(); i++) {
            list += shoppingList.get(i).toString() + ",";
        }
        return list;
    }

    /**
     * A method used for making toast messages on the parent activity of the ShoppingView Fragment.
     *
     * @param msg the message to be displayed in the toast.
     */
    private void toastMessage(final String msg) {
        parent.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(parent.getBaseContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Method that returns the boolean 'Value'.
     *
     * @return the boolean that signifies whether an update to the list has been done or not since the request being made.
     */
    public boolean getBoolean() {
        return this.value;
    }

    /**
     * Sets 'value' to false which is called by an external class.
     */
    public void setBooleanFalse() {
        this.value = false;
    }

    /**
     * Method used to communicate with the ShoppingView to update its list view to show the updated shopping list.
     */
    public void updateListView() {
        presenter.updateListView();
    }

    /**
     * Method that returns the shopping list.
     *
     * @return The linked list shoppinglist
     */
    public LinkedList<String> getShoppingList() {
        return this.SPLList;
    }

}
