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

/**
 * Class acting as the handler for the busstopsearcher view and interacts with the presenter
 */
public class BusStopSearcherHandler {
    private String auth;
    private String emptylist[] = new String[0];
    private ParseJson js;
    private BusStopSearcherPresenter BusStopSearcherPresenter;

    /**
     * Constructor that generates a access codes and sets auth to it. Instantiates a ParseJson class aswell
     * @param BusStopSearcherPresenter the presenter
     */
    public BusStopSearcherHandler(BusStopSearcherPresenter BusStopSearcherPresenter){
        this.BusStopSearcherPresenter = BusStopSearcherPresenter;
        // here we use the generateaccesscode class to get a new access code for the api
        GenerateAccessCode gen = new GenerateAccessCode();
        //we set the access to auth
        try {
            auth = gen.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //json parser for search
        js = new ParseJson();
    }

    /**
     * Function that creates a new TravelBySearch and executes it with the String S that is what the user searched for
     * This method also calls the presenter method for settings the list view but first parses it with ParseJson class.
     * @param s the user search
     */
    public void GetStops(String s){
        try {
            TravelBySearch trav = new TravelBySearch();
            //we execute the async task
            trav.execute(auth, s);
            //we parse the json data with the data we get from vässtrafik and the string that was searched for
            BusStopSearcherPresenter.setListview(initList(js.parseSearch(trav.get(), s)));
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * Gets a bus stop unique ID from the jsonbuilder class
     * @param S
     * @return the busID
     */
    public String getStopID(String S){
        return js.getBusIDfromSearch(S);
    }

    /**
     * To empty the arraylist we call this class
     */
    public void EmptyList(){
        BusStopSearcherPresenter.setListview(initList(emptylist));
    }

    /**
     * Converts a String[] into a arraylist
     * @param vastitems the String[] of bus stop names
     * @return the ArrayList of the vastitems string
     */
    private ArrayList<String>  initList(String[] vastitems){
        return new ArrayList<>(Arrays.asList(vastitems));
    }
}
