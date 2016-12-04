package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import java.util.ArrayList;
import java.util.LinkedList;


public class ShoppingList {
    String listTitle = "";
    LinkedList<String> itemList;
    String clientID = "";

    public ShoppingList(String listTitle, LinkedList<String> itemList, String clientID){
        this.itemList = new LinkedList<>();
        this.listTitle = listTitle;
        this.itemList = itemList;
        this.clientID = clientID;
    }
    public void setClientID(String clientID){
        this.clientID = clientID;
    }
    public void setListTitle(String listTitle){
        this.listTitle = listTitle;
    }

    public String getClientID(){
        return this.clientID;
    }
    public String getListTitle(){
        return this.listTitle;
    }
    public LinkedList<String> getItemList(){
        return this.itemList;
    }


}
