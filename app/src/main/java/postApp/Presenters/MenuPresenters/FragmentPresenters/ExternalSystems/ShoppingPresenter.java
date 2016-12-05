package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


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
        this.handler = new ShoppingHandler(shoppingView, this,uuid);
    }

    public void addItem(String item){
        handler.addItemToList(item);
    }
    public void updateList(){
        Collections.sort(handler.getShoppingList()); // Maybe should be done in handler
        view.getListView().setAdapter(view.getAdapter());
    }
    public void clearList(){
        handler.clearShoppingList();
    }
    public void removeElement(int position){
        handler.removeItemFromList(position);
    }
    /*public void saveTitle(String title){
        handler.saveTitle(title);
    }
    public String fetchTitle(){
        return handler.fetchTitle();
    }*/

    public LinkedList<String> getShoppingList(){
        return handler.getShoppingList();
    }

}
