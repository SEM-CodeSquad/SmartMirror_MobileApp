package postApp.Activities.NavigationActivity.Fragments;

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
import postApp.Activities.NavigationActivity.Fragments.SettingsFrag.SettingsFrag;
import postApp.DataHandlers.MqTTHandler.Retrievedata;
import postApp.DataHandlers.Authentication.VastTrafik.GenerateAccessCode;
import postApp.DataHandlers.Authentication.VastTrafik.TravelBySearch;
import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.DataHandlers.JsonParser.ParseJson;

/*
    Class for searching a stop using västtrafiks api
 */
public class SearchStop extends Fragment {
    View myView;
    String auth;
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText SearchBox;
    String selectedfromlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.searchstop, container, false);
        // here we use the generateaccesscode class to get a new access code for the api
        GenerateAccessCode gen = new GenerateAccessCode();
        //we set the access to auth
        auth = gen.getAccess();
        //initilize the views
        listView = (ListView) myView.findViewById(R.id.listview);
        SearchBox = (EditText) myView.findViewById(R.id.txtsearch);

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
                //set a emptylist to use if lenght is less then 2 or equal
                String emptylist[] = new String[0];
                //do nothing if empty
                if(s.toString().equals("")){
                }
                //show nothing if not a lot of letters
                else if (s.length() <= 2) {
                    initList(emptylist);
                }
                // if more than 2 letters are typed we start a travelbysearch which fetches västtrafiks stops.
                else {
                    if(s.length() > 2)
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
            }

        });

        //if we chose a item on the list then we publish that one.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //get the item
                selectedfromlist = (listView.getItemAtPosition(i).toString());
                //publish it
                publishBus();
                //set it to the busstation
                ((NavigationActivity) getActivity()).setBus(selectedfromlist);
                //set it back to a drawer instead of backbutton
                ((NavigationActivity) getActivity()).toggleDrawerUse(true);
                //switch back to settings
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
            }
        });

        //if the searchbox has no focus hide it!
        SearchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        return myView;

    }
    //pusblish the bus using class retrievedata
    public void publishBus(){
        Retrievedata R = new Retrievedata();
        //we make a toast of this string later on
        String S;
        //set the publishing broker topic
        String topic = ((NavigationActivity) getActivity()).getMirror();
        //if there is a topic we try to publish
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "config", selectedfromlist, "buschange").get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                S = "Warning: Did Not Publish";
            }
            //show the message got
            Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
        } else {
            //else chose a mirror first
            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
        }
    }

    //initilizes the list view with the items from the json parser and vässtrafiks api using a adapter and a list and array
        public void initList(String[] vastitems){
            items= vastitems;
            listItems=new ArrayList<>(Arrays.asList(items));
            adapter=new ArrayAdapter<String>(getActivity(),
                    R.layout.listitem, R.id.txtitem, listItems);
            listView.setAdapter(adapter);

        }
    /*
   Hides keyboard
    */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}

