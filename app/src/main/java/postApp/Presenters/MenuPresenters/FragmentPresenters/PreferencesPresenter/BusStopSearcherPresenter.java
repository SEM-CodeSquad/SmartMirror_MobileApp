package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;


import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.BusStopSearcherHandler;

/**
 * Class for interacting with the busstopsearcherview and the busstopsearcherhandler
 */
public class BusStopSearcherPresenter {

    private ArrayAdapter<String> adapter;
    private BusStopSearcherView BusStopSearcherView;
    private BusStopSearcherHandler BusStopSearcherHandler;

    /**
     *
     * @param BusStopSearcherView
     */
    public BusStopSearcherPresenter(BusStopSearcherView BusStopSearcherView){
        this.BusStopSearcherView = BusStopSearcherView;
        this.BusStopSearcherHandler = new BusStopSearcherHandler(this);
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

    public void setListview(ArrayList<String> list ) {
        adapter = new ArrayAdapter<>(BusStopSearcherView.getActivity(),
                R.layout.listitem, R.id.txtitem, list);
        BusStopSearcherView.listView.setAdapter(adapter);
    }
}
