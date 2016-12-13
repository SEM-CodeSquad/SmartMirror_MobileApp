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

    private BusStopSearcherView BusStopSearcherView;
    private BusStopSearcherHandler BusStopSearcherHandler;

    /**
     * The constructor for this class that injects this presenter into the handler and sets the view
     * @param BusStopSearcherView the view
     */
    public BusStopSearcherPresenter(BusStopSearcherView BusStopSearcherView){
        this.BusStopSearcherView = BusStopSearcherView;
        this.BusStopSearcherHandler = new BusStopSearcherHandler(this);
    }

    /**
     * To get all stops we call the handlers function
     * @param s The stop name we search for
     */
    public void GetStops(String s){
        BusStopSearcherHandler.GetStops(s);
    }

    /**
     * To empty list we call the handlers fucntion to emptylist
     */
    public void EmptyList(){
        BusStopSearcherHandler.EmptyList();
    }

    /**
     * When clicked a bus we call the views method for the bus clicked
     * @param bus the bus
     */
    public void OnBusClick(String bus){
        BusStopSearcherView.OnBusClick(bus);
    }

    /**
     * To hide the views keyboard
     * @param V the view
     */
    public void HideKeyboard(View V){
        BusStopSearcherView.hideKeyboard(V);
    }

    /**
     * To show that no mirror is chosen we call this views class
     */
    public void NoMirror(){
        BusStopSearcherView.NoMirror();
    }

    /**
     * To get the busID of the busstop
     * @param s the busstop name
     * @return the id string
     */
    public String getBusID(String s){
        return BusStopSearcherHandler.getStopID(s);
    }

    /**
     * To populate the listview with the adapter
     * @param list the list used in the adapter
     */
    public void setListview(ArrayList<String> list ) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopSearcherView.getActivity(),
                R.layout.listitem, R.id.txtitem, list);
        BusStopSearcherView.listView.setAdapter(adapter);
    }
}
