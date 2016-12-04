package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import java.util.ArrayList;


public class ShoppingList {
    String listTitle = "";
    ArrayList<String> itemList;
    String clientID = "";

    public ShoppingList(String listTitle, ArrayList<String> itemList, String clientID){
        this.itemList = new ArrayList<>();
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
    public ArrayList<String> getItemList(){
        return this.itemList;
    }


}
