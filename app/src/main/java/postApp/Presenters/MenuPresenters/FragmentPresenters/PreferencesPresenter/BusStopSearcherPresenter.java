package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;


import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.BusStopSearcherHandler;

public class BusStopSearcherPresenter {


    BusStopSearcherView BusStopSearcherView;
    BusStopSearcherHandler BusStopSearcherHandler;
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
    public void ShowMessage(String S){
        BusStopSearcherView.ShowMessage(S);
    }
    public void NoMirror(){
        BusStopSearcherView.NoMirror();
    }

}
