package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
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
    private String preData;
    private LinkedList<String> SPLList;
    private String clientID;
    //private MQTTClient mqttClient;

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter, String clientID) {
        MemoryPersistence persistence = new MemoryPersistence();
        this.clientID = clientID;
        //this.mqttClient = new MQTTClient("prata server",this.clientID,persistence);
        this.view = ShoppingView;
        this.presenter = ShoppingPresenter;
        this.SPLList = new LinkedList<>();
        //listenSubscription("Gro/" + clientID);
        shoppingList = new ShoppingList(this.clientID,SPLList,"clientID goes here"); // We need to initialize the ShoppingList here with the client ID
    }

    public void parseMessage(String message) {
        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);
            this.reply = json.get("reply").toString();
            this.preData = json.get("item").toString();
            this.SPLList = new LinkedList<>();
            parseArray(this.SPLList, "data");


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //TODO unsure if it's needed.
//    public void parseContent() {
//        try {
//            JSONParser parser = new JSONParser();
//            JSONArray jsonArray;
//            JSONObject jso;
//
//            switch (getReply()) {
//                case "Done":
//                    jsonArray = (JSONArray) parser.parse(this.preData);
//                    jso = (JSONObject) parser.parse(jsonArray.get(0).toString());
//                    preData = jso.get("item").toString();
//
//                    break;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void addClassObserver(MQTTSub sub){
        sub.addObserver(this);
    }

    /*private void listenSubscription(final String topic) {
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
    }*/


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

            //ShoppingList spl = new ShoppingList(this.getTitle(),linkedList,);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void addItemToList(String item){
        shoppingList.getItemList().add(item);
    }
    public void removeItemFromList(int position){
        shoppingList.getItemList().remove(position);
    }
    public void clearShoppingList(){
        shoppingList.getItemList().clear();
        shoppingList.setListTitle("");
    }

    /*
     * The saveTitle(title) method saves the title of a shopping list internally on a phone. It only
     * saves the last String used in the saveTitle parameters. This method is used because upon
     * creation of the ShoppingView, if the user already has a created shopping list ,
     * the user needs to be presented with it. The items of the shopping list can be received from a fetch call to
     *
     */
    /*public void saveTitle(String title){
        String file_name = "List Title";
        try {
            FileOutputStream fileOutputStream = view.getActivity().openFileOutput(file_name,MODE_PRIVATE);
            fileOutputStream.write(title.getBytes());
            fileOutputStream.close();
            Toast.makeText(view.getActivity().getApplicationContext(),"Message Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    /*public String fetchTitle(){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String title;
            FileInputStream fileInputStream = view.getActivity().openFileInput("List Title");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                while ((title = bufferedReader.readLine())!=null){
                        stringBuffer.append(title + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }*/

    public LinkedList<String> getShoppingList(){
        return this.shoppingList.getItemList();
    }
    public String getTitle(){
        return this.shoppingList.getListTitle();
    }
    public String getReply(){
        return reply;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof MqttMessage){
            // TODO GEOFF code here regarding how to handle the object
        }
    }
}