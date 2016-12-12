package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;


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
        this.mqttClient = new MQTTClient("tcp://prata.technocreatives.com",this.clientID+"@codehigh.com",persistence);
        this.view = ShoppingView;
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();
        if(this.clientID != "No mirror chosen"){
            listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
            listenSubscription("Gro/" + this.clientID + "@smartmirror.com/fetch");
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID + "@smartmirror.com","fetch");
        }
    }

    public void parseMessage(String message) {

        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);

            if (json.containsKey("reply")) {
                this.reply = json.get("reply").toString();
                if(reply.equalsIgnoreCase("done")){
                    System.out.println("got done boy");
                    if(json.get("data")!=null){
                        this.SPLList.clear();
                        parseItem(message);
                    } else {
                        if(tempType == "add"){
                            System.out.println("Adding boy");
                            SPLList.add(tempItem);
                            this.value = true;
                            System.out.println(SPLList.toString());
                        }
                        else if (tempType == "delete"){
                            SPLList.remove(tempItem);
                            this.value = true;
                        }
                        else if (tempType == "delete-list"){
                            SPLList.clear();
                            this.value = true;
                        }
                    }
                } else if (reply.equalsIgnoreCase("error")){
                    makeToast("Error updating list");
                }
            }else if(json.containsKey("client_id")){
                //this.replyID=json.get("client-id").toString();
                System.out.println("hey man it works");

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void parseItem(String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonOBJ = (JSONObject) parser.parse(json);
            if(jsonOBJ.get("data").equals("[]")){

            }else if(!(jsonOBJ.get("data").equals("[]"))) {

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

    public void addClassObserver(MQTTSub sub){
        sub.addObserver(this);
    }

    public void listenSubscription(final String topic) {
        try {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    MQTTSub subscriber = new MQTTSub(mqttClient,topic);
                    addClassObserver(subscriber);
            }
        });
            thread.start();
            System.out.println("listening to this topic " + topic);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateList(String requestType, String item){
        Long timestamp = System.currentTimeMillis() / 1000L;

        if (requestType == "add"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID + "@smartmirror.com",requestType,item);
            tempType = requestType; tempItem = item;
            JsonBuilder builderMirror = new JsonBuilder();
            LinkedList<String> copyList = new LinkedList<>();
            for (int i = 0; i<this.SPLList.size();i++){
                copyList.add(SPLList.get(i).toString());
            }
            copyList.add(item);
            builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp),Integer.toString(SPLList.size()),copyList.toString());

        } else if (requestType == "delete"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID + "@smartmirror.com",requestType,item); // The client id here is the one we should be using.
            tempType = requestType; tempItem = item;
            JsonBuilder builderMirror = new JsonBuilder();
            if (SPLList.size() == 1) {
                builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp));
            }
            else {
                LinkedList<String> copyList = new LinkedList<>();
                for (int i = 0; i<this.SPLList.size();i++){
                    copyList.add(SPLList.get(i).toString());
                }
                copyList.remove(item);
                builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp),Integer.toString(SPLList.size()),copyList.toString());
            }

        }else if (requestType == "delete-list"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID + "@smartmirror.com",requestType);
            tempType = requestType;
            JsonBuilder builderMirror = new JsonBuilder();
            builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp));
        }
    }

    public LinkedList<String> getShoppingList(){
        return this.SPLList;
    }
    public String getReply(){
        return reply;
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

    public void makeToast(String message){
        presenter.makeToast(message);
    }

    public boolean getBoolean(){
        return this.value;
    }
    public void setBooleanFalse(){
        this.value = false;
    }

}