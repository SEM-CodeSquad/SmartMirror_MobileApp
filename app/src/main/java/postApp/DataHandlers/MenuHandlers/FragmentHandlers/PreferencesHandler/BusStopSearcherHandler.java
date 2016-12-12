package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.AppCommons.JsonHandler.ParseJson;
import postApp.DataHandlers.AppCommons.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.AppCommons.Vasttrafik.TravelBySearch;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.BusStopSearcherPresenter;


public class BusStopSearcherHandler {
    private String auth;
    private String[] items;
    private ArrayList<String> listItems;
    private String emptylist[] = new String[0];
    private ParseJson js;
    private BusStopSearcherPresenter BusStopSearcherPresenter;
    public BusStopSearcherHandler(BusStopSearcherPresenter BusStopSearcherPresenter){
        this.BusStopSearcherPresenter = BusStopSearcherPresenter;
        // here we use the generateaccesscode class to get a new access code for the api
        GenerateAccessCode gen = new GenerateAccessCode();
        //we set the access to auth
        auth = gen.getAccess();
    }


    public void GetStops(String s){
        try {
            TravelBySearch trav = new TravelBySearch();
            //we execute the async task
            trav.execute(auth, s);
            //json parser for search
            js = new ParseJson();
            //we parse the json data with the data we get from vässtrafik and the string that was searched for
            BusStopSearcherPresenter.setListview(initList(js.parseSearch(trav.get(), s)));
        } catch (Exception v) {
            System.out.println(v);
        }
    }
    public String getStopID(String S){
        return js.getBusIDfromSearch(S);
    }

    public void EmptyList(){
        initList(emptylist);
    }
    private ArrayList<String>  initList(String[] vastitems){
        items= vastitems;
        listItems=new ArrayList<>(Arrays.asList(items));
        return listItems;
    }
}
