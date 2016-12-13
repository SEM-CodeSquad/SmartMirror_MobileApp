package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems.ShoppingHandler;


/*
 * The shopping presenter acts as a controller for the view class as well as handing over data to
 * the ShoppingHandler class.
 */
public class ShoppingPresenter {
    ShoppingHandler handler;
    ShoppingView view;
    String uuid;
    public ShoppingPresenter(ShoppingView shoppingView, String uuid){
        this.uuid = uuid;
        this.view = shoppingView;
        this.handler = new ShoppingHandler(this.view, this,uuid);
    }
    public void updateList(String requestType, String item){
        handler.updateList(requestType, item);
    }
    public LinkedList<String> getShoppingList(){
        return handler.getShoppingList();
    }
    public void makeToast(String message){
        view.makeToast(message);
    }
    public boolean getBoolean(){
       return handler.getBoolean();
    }
    public void setBooleanFalse(){
        handler.setBooleanFalse();
    }

}
