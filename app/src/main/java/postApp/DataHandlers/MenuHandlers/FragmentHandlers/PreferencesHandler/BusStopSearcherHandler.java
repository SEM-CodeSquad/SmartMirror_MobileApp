package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.DataHandlers.JsonHandler.ParseJson;
import postApp.DataHandlers.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.Vasttrafik.TravelBySearch;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.BusStopSearcherPresenter;


public class BusStopSearcherHandler {
    String auth;
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    BusStopSearcherView BusStopSearcherView;
    String emptylist[] = new String[0];
    BusStopSearcherPresenter BusStopSearcherPresenter;
    public BusStopSearcherHandler(BusStopSearcherView BusStopSearcherView, BusStopSearcherPresenter BusStopSearcherPresenter){
        this.BusStopSearcherView = BusStopSearcherView;
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
            trav.execute(auth, s.toString());
            //json parser for search
            ParseJson js = new ParseJson();
            //we parse the json data with the data we get from vässtrafik and the string that was searched for
            initList(js.parseSearch(trav.get(), s.toString()));
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    public void EmptyList(){
        initList(emptylist);
    }
    public void initList(String[] vastitems){
        items= vastitems;
        listItems=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(BusStopSearcherView.getActivity(),
                R.layout.listitem, R.id.txtitem, listItems);
        BusStopSearcherView.listView.setAdapter(adapter);

    }
    public void publishBus(String BusStop){
        JsonBuilder R = new JsonBuilder();
        //we make a toast of this string later on
        String S;
        //set it to the busstation
        ((NavigationActivity) BusStopSearcherView.getActivity()).setBus(BusStop);
        //set the publishing broker topic
        String topic = ((NavigationActivity) BusStopSearcherView.getActivity()).getMirror();
        //if there is a topic we try to publish
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "config", BusStop, "buschange").get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                S = "Warning: Did Not Publish";
            }
            //show the message got
            BusStopSearcherPresenter.ShowMessage(S);
        } else {
            //else chose a mirror first
            BusStopSearcherPresenter.NoMirror();
        }
    }
}
