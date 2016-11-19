package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import postApp.Activities.NavigationActivity.Fragments.QrCode;
import postApp.Activities.NavigationActivity.Fragments.SearchStop;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.DataHandlers.JsonHandler.ParseJson;
import postApp.DataHandlers.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.Vasttrafik.TravelByLoc;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsHandler {
    QrCode newQr;
    SearchStop newSearch;
    private View.OnClickListener mOriginalListener;
    AlertDialog.Builder newsbuilt;
    AlertDialog.Builder busbuilt;
    String auth;

    postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView SettingsView;
    public SettingsHandler(SettingsView SettingsView) {
        this.SettingsView = SettingsView;
    }

    public void StartBus(){
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {
            busbuilt.show();
        }
        else
        {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
        }
    }
    public void StartNews(){
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {
            newsbuilt.show();
        }
        else
        {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
        }
    }
    public void ChangeToQr(){
        newQr = new QrCode();
        mOriginalListener = ((NavigationActivity) SettingsView.getActivity()).toggle.getToolbarNavigationClickListener();
        ((NavigationActivity) SettingsView.getActivity()).toggleDrawerUse(false);
        ((NavigationActivity) SettingsView.getActivity()).getSupportActionBar().setTitle("Mirror ID");
        SettingsView.getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newQr).commit();
    }
    public void WeatherOnLoc(){
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {

            //using lib smartlocation
            SmartLocation.with(SettingsView.getActivity()).location()
                    //used for getting location just ones
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            //we get the city after lat and long
                            String city = weathercity(location.getLatitude(), location.getLongitude());
                            //set the weather to the city
                            ((NavigationActivity) SettingsView.getActivity()).setWeather(city);
                            //set text to city
                            SettingsView.weathertext.setText(city);
                            //then we start a retrieve data that publishes the new weather as a config.
                            JsonBuilder R = new JsonBuilder();
                            String S;
                            String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
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
                                Toast.makeText(SettingsView.getActivity(), S, Toast.LENGTH_SHORT).show();
                            } else {
                                // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                                Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
        }
    }
    // this is used to build a AlertDialog that displays newsoptions.
    public void Buildnews() {
        newsbuilt = new AlertDialog.Builder(SettingsView.getActivity());
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
                                ((NavigationActivity) SettingsView.getActivity()).setNews("CNN");
                                SettingsView.newstext.setText(((NavigationActivity) SettingsView.getActivity()).getNews());
                                break;
                            case 1:
                                ((NavigationActivity) SettingsView.getActivity()).setNews("BBC");
                                SettingsView.newstext.setText(((NavigationActivity) SettingsView.getActivity()).getNews());
                                break;
                            case 2:
                                ((NavigationActivity) SettingsView.getActivity()).setNews("Daily Mail");
                                SettingsView.newstext.setText(((NavigationActivity) SettingsView.getActivity()).getNews());
                                break;

                        }
                        //Publish this chosen news to the broker.
                        JsonBuilder R = new JsonBuilder();
                        String S;
                        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
                        if (topic != "No mirror chosen") {
                            try {
                                S = R.execute(topic, "config", ((NavigationActivity) SettingsView.getActivity()).getNews(), "newschange").get();
                            } catch (InterruptedException e) {
                                S = "Did not publish";
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                S = "Warning: Did Not Publish";
                            }
                            //displays if it was succesful or not
                            Toast.makeText(SettingsView.getActivity(), S, Toast.LENGTH_SHORT).show();
                        } else {
                            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                            Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //and we create it with all the above options.
        newsbuilt.create();


    }

    public void Buildstop() {
        //the first two lines generete a authorization key for the Västtrafik API.
        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();

        //we build our bus alertdialog
        busbuilt = new AlertDialog.Builder(SettingsView.getActivity());
        //We give the usser two options by location or by search.
        busbuilt.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if by location is chosen we use the SmartLocation lib once again to get the fixed location
                        SmartLocation.with(SettingsView.getActivity()).location()
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
                                            ParseJson js = new ParseJson();
                                            String stop = js.parseLoc(trav.get());
                                            //we set the activities stop
                                            ((NavigationActivity) SettingsView.getActivity()).setBus(stop);
                                            //and the we set the bustext in the app to stop
                                            SettingsView.bustext.setText(stop);
                                            //after this we publish to the smartmirror the buschange
                                            JsonBuilder R = new JsonBuilder();
                                            String S;
                                            String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
                                            if (topic != "No mirror chosen") {
                                                try {
                                                    S = R.execute(topic, "config", SettingsView.bustext.toString(), "buschange").get();
                                                } catch (InterruptedException e) {
                                                    S = "Did not publish";
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                    S = "Warning: Did Not Publish";
                                                }
                                                //displays if it was succesful or not
                                                Toast.makeText(SettingsView.getActivity(), S, Toast.LENGTH_SHORT).show();
                                            } else {
                                                // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
                                                Toast.makeText(SettingsView.getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
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
                        mOriginalListener = ((NavigationActivity) SettingsView.getActivity()).toggle.getToolbarNavigationClickListener();
                        //we set the drawer to false and it becomes a back button
                        ((NavigationActivity) SettingsView.getActivity()).toggleDrawerUse(false);
                        //sets title
                        ((NavigationActivity) SettingsView.getActivity()).getSupportActionBar().setTitle("Search for your stop");
                        //switches fragment
                        SettingsView.getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newSearch).commit();

                    }
                });
        //creates builder
        busbuilt.create();
    }



    //here we just get the city for the weather
    public String weathercity(double Lat, double Long){
        //will be final adress
        String finalAddress = "";
        //android lib geocoder that gets the current location
        Locale loc = new Locale("en");
        Geocoder geoCoder = new Geocoder(SettingsView.getActivity(), loc);
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
