package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import java.util.ArrayList;


public class ShoppingList {
    String listTitle =" ";
    ArrayList<String> itemList = null;
    String clientID;

    public ShoppingList(String listTitle, ArrayList<String> itemList, String clientID){
        this.listTitle = listTitle;
        this.itemList = itemList;
        this.clientID = clientID;
        itemList = new ArrayList<>();
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

    public void setClientID(String clientID){
        this.clientID = clientID;
    }
    public void setListTitle(String listTitle){
        this.listTitle = listTitle;
    }
}
