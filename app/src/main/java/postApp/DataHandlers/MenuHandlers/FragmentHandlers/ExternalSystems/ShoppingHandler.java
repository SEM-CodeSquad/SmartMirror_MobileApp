package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import android.os.AsyncTask;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.MQTTClient;
import postApp.DataHandlers.MqTTHandler.MQTTSub;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;

import static android.content.Context.MODE_PRIVATE;

/*
 * This class is the handler for the ShoppingList component. This is where the logic part of the component
 * takes place (addition,deletion,parsing etc.).
 */
public class ShoppingHandler implements Observer {
    ShoppingPresenter presenter;
    ShoppingView view;

    private ShoppingList shoppingList;
    private String message;
    private String reply;
    private String replyID;
    private String preData;
    private LinkedList<String> SPLList;
    private String clientID;
    private MQTTClient mqttClient;
    private String tempType = "";
    private String tempItem = "";

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter, String clientID) {
        MemoryPersistence persistence = new MemoryPersistence();
        this.clientID = clientID;
        this.mqttClient = new MQTTClient("tcp://prata.technocreatives.com",this.clientID+"@codehigh.com",persistence);
        this.view = ShoppingView;
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();
        listenSubscription("Gro/smartmirror@codehigh.com");
        listenSubscription("Gro/smartmirror@codehigh.com/fetch");
        JsonBuilder builder = new JsonBuilder();
        builder.execute("shoppinglist",this.clientID,"fetch");
        shoppingList = new ShoppingList("ShoppingList",SPLList,this.clientID); // We need to initialize the ShoppingList here with the client ID
    }

    public void parseMessage(String message) {
        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);
            this.reply = json.get("reply").toString();
            this.replyID=json.get("client-ID").toString();
            if(reply.equalsIgnoreCase("done")){
                //Parse the "data" field if there's any
                if(json.get("data")!=null){
                    this.SPLList.clear();
                    parseArray(this.SPLList, "data");
                } else {
                    if(tempType == "add")SPLList.add(tempItem);
                    if (tempType == "delete") SPLList.remove(tempItem);
                    if (tempType == "delete-list") SPLList.clear();
                }
            } else if (reply.equalsIgnoreCase("error")){
                Toast.makeText(view.getActivity().getApplicationContext(),"Error updating list",Toast.LENGTH_LONG).show();
            } else if(replyID.equals(this.clientID)){
                //TODO Nimish, add pop up window for done
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void parseArray(LinkedList linkedList, String type) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(this.preData);
            String s = jsonArray.get(0).toString();
            JSONObject jso = (JSONObject) parser.parse(s);
            ArrayList<String> arrayList = new ArrayList<>(jso.keySet());
            String item;
            for (String anArrayList : arrayList) {
                String value = jso.get(anArrayList).toString();
                switch (type) {
                    case "item":
                        item = value;
                        linkedList.add(item);
                        break;
                    default:
                        System.out.println("Could Not be Parsed!");
                        break;
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
        /*Long timestamp = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        Date dateTemp;
        try {
            dateTemp = sdf.parse(date.toString());
            long unixTime = (dateTemp.getTime()) / 1000;
            timestamp = unixTime;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }*/

        if (requestType == "add"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist","smartmirror@codehigh.com", requestType, item);
            tempType = requestType; tempItem = item;
            JsonBuilder builderMirror = new JsonBuilder();
            //builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp),Integer.toString(SPLList.size()),SPLList.toString());

        } else if (requestType == "delete"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID+"@smartmirror.com",requestType,item);
            tempType = requestType; tempItem = item;
            JsonBuilder builderMirror = new JsonBuilder();
            if (SPLList.size() == 1) {
               // builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp));
            }
            else {
               // builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp),Integer.toString(SPLList.size()),SPLList.toString());
            }

        }else if (requestType == "delete-list"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID+"@smartmirror.com",requestType);
            tempType = "delete-list";
            JsonBuilder builderMirror = new JsonBuilder();
            //builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp));
        }
    }

    public LinkedList<String> getShoppingList(){
        return this.SPLList;
    }
    public String getTitle(){
        return this.shoppingList.getListTitle();
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


}