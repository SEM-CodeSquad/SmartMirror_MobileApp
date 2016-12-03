package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


import java.util.ArrayList;
import java.util.Collections;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems.ShoppingHandler;

public class ShoppingPresenter {
    ShoppingHandler shoppingHandler;
    ShoppingView shoppingView;
    public ShoppingPresenter(ShoppingView shoppingView){
        this.shoppingView = shoppingView;
        this.shoppingHandler = new ShoppingHandler(shoppingView, this);
    }

    public void addItem(String item){
        shoppingHandler.addItemToList(item);
    }

    public void updateList(){
        Collections.sort(shoppingHandler.getShoppingList()); // Maybe should be done in handler
        shoppingView.getListView().setAdapter(shoppingView.getAdapter());
    }
    public void setTitle(){
        shoppingView.setTitle(shoppingHandler.getTitle());
    }

    public ArrayList<String> getShoppingList(){
        return shoppingHandler.getShoppingList();
    }
    public String getTitle(){
        return shoppingHandler.getTitle();
    }
    public void clearList(){
        shoppingHandler.clearShoppingList();
    }
    public void removeElement(int position){
        shoppingHandler.removeItemFromList(position);
    }
}
