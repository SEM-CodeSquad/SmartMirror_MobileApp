package postApp.Activities.NavigationActivity.Fragments;

/**
 * Created by adinH on 2016-10-27.
 */
/*
This is the settings fragments managing all the controlling when pressing the settings buttons.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


import adin.postApp.R;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.DataHandlers.Network.DataBase.Settings;
import postApp.DataHandlers.Network.MqTTHandler.Retrievedata;
import postApp.DataHandlers.Network.VastTrafik.GenerateAccessCode;
import postApp.DataHandlers.Network.VastTrafik.TravelByLoc;


public class SettingsFrag extends Fragment {
    View myView;
    QrCode newQr;
    SearchStop newSearch;
    public EditText UUID;
    String auth;
    String user;
    private View.OnClickListener mOriginalListener;
    AlertDialog.Builder newsbuilt;
    AlertDialog.Builder busbuilt;
    EditText bustext;
    EditText newstext;
    EditText weathertext;

/*
This is created when the fragment is started.
 */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings, container, false);

        //These for line below just locate the buttons for the mirror, bus, news and weather so we don't get null pointer later when working with them.
        Button QrCodebtn = (Button) myView.findViewById(R.id.mirrorbtn);
        Button busbutton = (Button) myView.findViewById(R.id.busbtn);
        Button newsbutton = (Button) myView.findViewById(R.id.newsbtn);
        Button weatherbutton = (Button) myView.findViewById(R.id.weatherchange);

        //These four line below just locate the edittext for the mirror, bus, news and weather so we don't get null pointer later when working.
        UUID = (EditText) myView.findViewById(R.id.mirrortext);
        bustext = (EditText) myView.findViewById(R.id.bustext);
        newstext = (EditText) myView.findViewById(R.id.newstext);
        weathertext = (EditText) myView.findViewById(R.id.citytext);
        user =  (((NavigationActivity) getActivity()).getUser());

        //we build here our builders for news and the stop
        Buildnews();
        Buildstop();

        //A QrCode button that has a onclicklistener that changes fragments to the qr fragment, and then change title on the toolbar.
        // the toggleDrawerUse switches from a drawer to a backbutton.
        QrCodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQr = new QrCode();
                mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
                ((NavigationActivity) getActivity()).toggleDrawerUse(false);
                ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror ID");
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newQr).commit();
            }
        });

        //when we press news we show the builder built below
        newsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsbuilt.show();
            }
        });

        //when we press bus button we show the builder built below
        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                busbuilt.show();

            }
        });

        // a onclick listener that uses the library nlopez smartlocation lib that gets the current location one time only.
        weatherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using lib smartlocation
                SmartLocation.with(getActivity()).location()
                        //used for getting location just ones
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                //we get the city after lat and long
                                String city = weathercity(location.getLatitude(), location.getLongitude());
                                //set the weather to the city
                                ((NavigationActivity) getActivity()).setWeather(city);
                                //set text to city
                                weathertext.setText(city);
                                //then we start a retrieve data that publishes the new weather as a config.
                                Retrievedata R = new Retrievedata();
                                String S;
                                String topic = ((NavigationActivity) getActivity()).getMirror();
                                if (topic != "No mirror chosen") {
                                    try {
                                        S = R.execute(topic, "config", city, "weatherchange").get();
                                    } catch (InterruptedException e) {
                                        S = "Did not publish";
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                        S = "Warning: Did Not Publish";
                                    }
                                    //displays if it was succesful or not
                                    Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                                } else {
                                    // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                                    Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        //Connecting to our database and getting the settings for this user, then we set the bus, weather and news to the users chosen settings from before.
        Settings set = new Settings(user);
        String[] db = set.getSettings();
        ((NavigationActivity) getActivity()).setBus(db[0]);
        ((NavigationActivity) getActivity()).setWeather(db[1]);
        ((NavigationActivity) getActivity()).setNews(db[2]);

        // Here we finish off the onCreate with setting the UUID, bus, news,weather to the one in our activity.
        UUID.setText(((NavigationActivity) getActivity()).getMirror());
        bustext.setText(((NavigationActivity) getActivity()).getBus());
        newstext.setText(((NavigationActivity) getActivity()).getNews());
        weathertext.setText(((NavigationActivity) getActivity()).getWeather());


        return myView;
    }

    // this is used to build a AlertDialog that displays newsoptions.
    private void Buildnews() {
        newsbuilt = new AlertDialog.Builder(getActivity());
        //set the title
        newsbuilt.setTitle("Choose News");
        //three options
        newsbuilt.setItems(new CharSequence[]
                        {"CNN", "BBC", "Daily Mail"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // A switch with a onlick that sets text in activity based on what you choose
                        switch (which) {
                            case 0:
                                ((NavigationActivity) getActivity()).setNews("CNN");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;
                            case 1:
                                ((NavigationActivity) getActivity()).setNews("BBC");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;
                            case 2:
                                ((NavigationActivity) getActivity()).setNews("Daily Mail");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;

                        }
                        //Publish this chosen news to the broker.
                        Retrievedata R = new Retrievedata();
                        String S;
                        String topic = ((NavigationActivity) getActivity()).getMirror();
                        if (topic != "No mirror chosen") {
                            try {
                                S = R.execute(topic, "config", ((NavigationActivity) getActivity()).getNews(), "newschange").get();
                            } catch (InterruptedException e) {
                                S = "Did not publish";
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                S = "Warning: Did Not Publish";
                            }
                            //displays if it was succesful or not
                            Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                        } else {
                            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //and we create it with all the above options.
        newsbuilt.create();


    }

    private void Buildstop() {
        //the first two lines generete a authorization key for the Västtrafik API.
        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();

        //we build our bus alertdialog
        busbuilt = new AlertDialog.Builder(getActivity());
        //We give the usser two options by location or by search.
        busbuilt.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if by location is chosen we use the SmartLocation lib once again to get the fixed location
                        SmartLocation.with(getActivity()).location()
                                .oneFix()
                                .start(new OnLocationUpdatedListener() {
                                    @Override
                                    public void onLocationUpdated(Location location) {
                                        Double Latitude = location.getLatitude();
                                        Double Longitude = location.getLongitude();
                                        try {
                                            //when we got lat and long we execute the TravelByLoc with the parameters.
                                            TravelByLoc trav = new TravelByLoc();
                                            trav.execute(auth, String.valueOf(Latitude), String.valueOf(Longitude));
                                            //our stop the is what we get() from the travelbyloc
                                            String stop = parsejson(trav.get());
                                            //we set the activities stop
                                            ((NavigationActivity) getActivity()).setBus(stop);
                                            //and the we set the bustext in the app to stop
                                            bustext.setText(stop);
                                            //after this we publish to the smartmirror the buschange
                                            Retrievedata R = new Retrievedata();
                                            String S;
                                            String topic = ((NavigationActivity) getActivity()).getMirror();
                                            if (topic != "No mirror chosen") {
                                                try {
                                                    S = R.execute(topic, "config", bustext.toString(), "buschange").get();
                                                } catch (InterruptedException e) {
                                                    S = "Did not publish";
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                    S = "Warning: Did Not Publish";
                                                }
                                                //displays if it was succesful or not
                                                Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                                            } else {
                                                // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                                                Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception v) {
                                            System.out.println(v);
                                        }
                                    }

                                });
                    }
                })
                .setNegativeButton("By Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //if user choses by search we switch fragment to SearchStop fragment.
                        newSearch = new SearchStop();
                        //here we save the toolbarnavigationlistener because we want a back button now instead of a drawer.
                        mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
                        //we set the drawer to false and it becomes a back button
                        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
                        //sets title
                        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Search for your stop");
                        //switches fragment
                        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newSearch).commit();

                    }
                });
        //creates builder
        busbuilt.create();
    }

    //parses json for the closest stop location return a string with a name of a stop
    public String parsejson(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONObject obj2 = (JSONObject) obj.get("StopLocation");
        Object name = obj2.get("name");
        return name.toString();
    }

    //here we just get the city for the weather
    public String weathercity(double Lat, double Long){
        //will be final adress
        String finalAddress = "";
        //android lib geocoder that gets the current location
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            // addresses with city, country, street etc in a list
            List<Address> address = geoCoder.getFromLocation(Lat, Long, 1);
            //extract adress from list
            Address myadd = address.get(0);
            //we get locality which is city only
            finalAddress = myadd.getLocality() + ", " + myadd.getCountryName();
        } catch (IOException e) {}
        //returns final adress
        return finalAddress;
    }
}
