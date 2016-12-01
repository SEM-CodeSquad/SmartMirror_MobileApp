package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import android.bluetooth.BluetoothClass;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedList;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;


public class ShoppingHandler {
    ShoppingPresenter ShoppingPresenter;
    ShoppingView ShoppingView;
    private String message;
    private String contentType;
    private String content;
    private String messageFrom;
    private String timestamp;
    private LinkedList<BluetoothClass.Device> deviceList;

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter) {
        this.ShoppingView = ShoppingView;
        this.ShoppingPresenter = ShoppingPresenter;

    }
    public void parseMessage(String message) {

        try {
            this.message = message;
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(this.message);
            this.messageFrom = json.get("messageFrom").toString();
            this.timestamp = json.get("timestamp").toString();
            this.contentType = json.get("contentType").toString();
            this.content = json.get("content").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
