package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.LinkedList;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;


public class ShoppingHandler {
    ShoppingPresenter ShoppingPresenter;
    ShoppingView ShoppingView;

    private String message;
    private String reply;
    private String preData;
    private LinkedList<ShoppingList> SPLList;

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter) {
        this.ShoppingView = ShoppingView;
        this.ShoppingPresenter = ShoppingPresenter;

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
    public String getReply(){
        return reply;
    }


}