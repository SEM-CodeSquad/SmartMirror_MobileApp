package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;

/**
 * Created by adinH on 2016-11-28.
 */

public class ShoppingHandler {
    ShoppingPresenter ShoppingPresenter;
    ShoppingView ShoppingView;

    public ShoppingHandler(ShoppingView ShoppingView, ShoppingPresenter ShoppingPresenter) {
        this.ShoppingView = ShoppingView;
        this.ShoppingPresenter = ShoppingPresenter;

    }


}
