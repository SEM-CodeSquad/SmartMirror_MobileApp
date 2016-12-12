package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;


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

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter, String clientID) {
        MemoryPersistence persistence = new MemoryPersistence();
        this.clientID = clientID;
        this.mqttClient = new MQTTClient("tcp://prata.technocreatives.com",this.clientID+"@codehigh.com",persistence);
        this.view = ShoppingView;
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
        listenSubscription("Gro/" + this.clientID + "@smartmirror.com");
        JsonBuilder builder = new JsonBuilder();
        builder.execute("shoppinglist",this.clientID + "@smartmirror.com","fetch");
    }

    public void parseMessage(String message) {
        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);

            if (json.containsKey("reply")) {
                this.reply = json.get("reply").toString();
                if(reply.equalsIgnoreCase("done")){
                    System.out.println("got done boy");
                    if(json.get("data")!=null){
                        this.SPLList.clear();
                        parseItem(json.get("data").toString());
                    } else {
                        if(tempType == "add"){
                            System.out.println("Adding boy");
                            SPLList.add(tempItem);
                            updateListView();
                            System.out.println(SPLList.toString());
                        }
                        else if (tempType == "delete"){
                            SPLList.remove(tempItem);
                            updateListView();
                        }
                        else if (tempType == "delete-list"){
                            SPLList.clear();
                            updateListView();
                        }
                    }
                } else if (reply.equalsIgnoreCase("error")){
                    Toast.makeText(view.getActivity().getApplicationContext(),"Error updating list",Toast.LENGTH_LONG).show();
                }
            }else if(json.containsKey("client-id")){
                //this.replyID=json.get("client-id").toString();
                System.out.println("hey man it works");
                //TODO Nimish do your done checker here!
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
            builder.execute("shoppinglist",this.clientID + "@smartmirror.com",requestType,item);
            tempType = requestType; tempItem = item;
           // JsonBuilder builderMirror = new JsonBuilder();
           // builderMirror.execute("SPLToMirror", this.clientID,Long.toString(timestamp),Integer.toString(SPLList.size()),SPLList.toString());

        } else if (requestType == "delete"){
            JsonBuilder builder = new JsonBuilder();
            builder.execute("shoppinglist",this.clientID+"@smartmirror.com",requestType,item); // The client id here is the one we should be using.
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

    public void updateListView(){
        presenter.updateListView();
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


}