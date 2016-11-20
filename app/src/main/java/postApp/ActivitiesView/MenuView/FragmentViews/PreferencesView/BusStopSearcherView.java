package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.DataHandlers.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.Vasttrafik.TravelBySearch;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.ParseJson;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.BusStopSearcherPresenter;

/*
    Class for searching a stop using västtrafiks api
 */
public class BusStopSearcherView extends Fragment {
    View myView;
    public ListView listView;
    EditText SearchBox;
    BusStopSearcherPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.searchstop, container, false);
        //initilize the views
        listView = (ListView) myView.findViewById(R.id.listview);
        SearchBox = (EditText) myView.findViewById(R.id.txtsearch);
        presenter = new BusStopSearcherPresenter(this);

        //set a textchanged listener to searchbox
        SearchBox.addTextChangedListener(new TextWatcher() {

            //not used for now but have to be implemented
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            //not used for now but have to be implemented
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
            }

            //after the text is changed we use this
            @Override
            public void afterTextChanged(Editable s) {
                //do nothing if empty
                if(s.toString().equals("")){
                }
                //show nothing if not a lot of letters
                else if (s.length() <= 2) {
                    presenter.EmptyList();
                }
                // if more than 2 letters are typed we start a travelbysearch which fetches västtrafiks stops.
                else {
                    if(s.length() > 2){
                        presenter.GetStops(s.toString());
                    }
                }
            }

        });

        //if we chose a item on the list then we publish that one.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                presenter.PublishBus((listView.getItemAtPosition(i).toString()));
                presenter.OnBusClick();
            }
        });

        //if the searchbox has no focus hide it!
        SearchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });


        return myView;

    }

    public void OnBusClick(){
        //set it back to a drawer instead of backbutton
        ((NavigationActivity) getActivity()).toggleDrawerUse(true);
        //switch back to settings
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsView()).commit();
    }
    public void ShowMessage(String S){
            Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
        }
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();

    }
    /*
   Hides keyboard
    */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

