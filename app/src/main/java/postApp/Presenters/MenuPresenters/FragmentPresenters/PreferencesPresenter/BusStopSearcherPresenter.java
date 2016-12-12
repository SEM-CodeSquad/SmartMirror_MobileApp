package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;


import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.BusStopSearcherHandler;

/**
 * Class for interacting with the busstopsearcherview and the busstopsearcherhandler
 */
public class BusStopSearcherPresenter {


    private BusStopSearcherView BusStopSearcherView;
    private BusStopSearcherHandler BusStopSearcherHandler;

    /**
     *
     * @param BusStopSearcherView
     */
    public BusStopSearcherPresenter(BusStopSearcherView BusStopSearcherView){
        this.BusStopSearcherView = BusStopSearcherView;
        this.BusStopSearcherHandler = new BusStopSearcherHandler(BusStopSearcherView, this);
    }

    public void GetStops(String s){
        BusStopSearcherHandler.GetStops(s);
    }
    public void EmptyList(){
        BusStopSearcherHandler.EmptyList();
    }
    public void OnBusClick(String bus){
        BusStopSearcherView.OnBusClick(bus);
    }
    public void HideKeyboard(View V){
        BusStopSearcherView.hideKeyboard(V);
    }
    public void NoMirror(){
        BusStopSearcherView.NoMirror();
    }
    public String getBusID(String s){
        return BusStopSearcherHandler.getStopID(s);
    }

}
