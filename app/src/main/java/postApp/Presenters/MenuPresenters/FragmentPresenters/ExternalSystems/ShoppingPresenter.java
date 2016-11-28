package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems.ShoppingHandler;

public class ShoppingPresenter {
    ShoppingHandler ShoppingHandler;
    ShoppingView ShoppingView;
    public ShoppingPresenter(ShoppingView ShoppingView){
        this.ShoppingView = ShoppingView;
        this.ShoppingHandler = new ShoppingHandler(ShoppingView, this);
    }
}
