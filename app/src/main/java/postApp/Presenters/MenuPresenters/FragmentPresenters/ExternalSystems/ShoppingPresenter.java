package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


import java.util.ArrayList;
import java.util.Collections;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems.ShoppingHandler;

public class ShoppingPresenter {
    ShoppingHandler handler;
    ShoppingView view;
    public ShoppingPresenter(ShoppingView shoppingView){
        this.view = shoppingView;
        this.handler = new ShoppingHandler(shoppingView, this);
    }

    public void addItem(String item){
        handler.addItemToList(item);
    }
    public void updateList(){
        Collections.sort(handler.getShoppingList()); // Maybe should be done in handler
        view.getListView().setAdapter(view.getAdapter());
        view.setTitle(handler.getTitle());
    }
    public void setTitle(){
        view.setTitle(handler.getTitle());
    }
    public void clearList(){
        handler.clearShoppingList();
    }
    public void removeElement(int position){
        handler.removeItemFromList(position);
    }

    public ArrayList<String> getShoppingList(){
        return handler.getShoppingList();
    }
    public String getTitle(){
        return handler.getTitle();
    }

}
