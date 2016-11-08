package postApp.Controllers.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.logic.MqTTHandler.Retrievedata;
import postApp.logic.vasttrafik.GenerateAccessCode;
import postApp.logic.vasttrafik.TravelBySearch;
import postApp.Controllers.NavigationActivity;


public class SearchStop extends Fragment {
    View myView;
    String auth;
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    String selectedfromlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.searchstop, container, false);

        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();
        System.out.println(auth);

        listView = (ListView) myView.findViewById(R.id.listview);
        editText = (EditText) myView.findViewById(R.id.txtsearch);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {


            }


            @Override
            public void afterTextChanged(Editable s) {
                String emptylist[] = new String[0];
                if(s.toString().equals("")){

                }
                else if (s.length() <= 2) {
                    initList(emptylist);
                } else {
                    if(s.length() > 2)
                        try {
                            TravelBySearch trav = new TravelBySearch();
                            trav.execute(auth, s.toString());
                            initList(parsejson(trav.get(), s.toString()));
                        } catch (Exception v) {
                            System.out.println(v);
                        }

                }
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                selectedfromlist = (listView.getItemAtPosition(i).toString());
                publishBus();
                ((NavigationActivity) getActivity()).setBus(selectedfromlist);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new Settings()).commit();
            }
        });


        return myView;

    }

    public void publishBus(){
        Retrievedata R = new Retrievedata();
        String S;
        String topic = ((NavigationActivity) getActivity()).getMirror();
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
            Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
        }
    }

    public String[] parsejson(String json, String search) throws ParseException {
        JSONParser parser = new JSONParser();
        String newlist[] = new String[20];
        System.out.println(json);
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONArray jsarr = (JSONArray) obj.get("StopLocation");
        for (int i = 0; i < jsarr.size(); i++) {

            JSONObject finalobj = (JSONObject) jsarr.get(i);
            Object name = finalobj.get("name");
            if (search.toLowerCase().equals(name.toString().substring(0,search.length()).toLowerCase())){
                newlist[i] =  name.toString();
            }
        }
        return clean(newlist);


    }
    public static String[] clean(final String[] v) {
        List<String> list = new ArrayList<String>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

        public void initList(String[] vastitems){
            items= vastitems;
            listItems=new ArrayList<>(Arrays.asList(items));
            adapter=new ArrayAdapter<String>(getActivity(),
                    R.layout.listitem, R.id.txtitem, listItems);
            listView.setAdapter(adapter);

        }

    }

