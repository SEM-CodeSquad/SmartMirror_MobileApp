package postApp.Controllers.Fragments;

/**
 * Created by adinH on 2016-10-27.
 */
/*
Work in progress atm will comment when done.
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


import adin.postApp.R;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import postApp.Controllers.NavigationActivity;
import postApp.Network.DataAccess.Settings;
import postApp.logic.MqTTHandler.Retrievedata;
import postApp.logic.vasttrafik.GenerateAccessCode;
import postApp.logic.vasttrafik.TravelByLoc;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.settings, container, false);
        Button QrCodebtn = (Button) myView.findViewById(R.id.mirrorbtn);
        Button busbutton = (Button) myView.findViewById(R.id.busbtn);
        Button newsbutton = (Button) myView.findViewById(R.id.newsbtn);
        Button weatherbutton = (Button) myView.findViewById(R.id.weatherchange);
        UUID = (EditText) myView.findViewById(R.id.mirrortext);
        bustext = (EditText) myView.findViewById(R.id.bustext);
        newstext = (EditText) myView.findViewById(R.id.newstext);
        weathertext = (EditText) myView.findViewById(R.id.citytext);
        user =  (((NavigationActivity) getActivity()).getUser());
        Settings set = new Settings(user);
        System.out.println(Arrays.toString(set.getSettings()));

        Buildnews();
        Buildstop();

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

        newsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsbuilt.show();
            }
        });

        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                busbuilt.show();

            }
        });

        weatherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmartLocation.with(getActivity()).location()
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                String city = weathercity(location.getLatitude(), location.getLongitude());
                                ((NavigationActivity) getActivity()).setWeather(city);
                                weathertext.setText(city);
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
                                    Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        UUID.setText(((NavigationActivity) getActivity()).getMirror());
        bustext.setText(((NavigationActivity) getActivity()).getBus());
        newstext.setText(((NavigationActivity) getActivity()).getNews());
        weathertext.setText(((NavigationActivity) getActivity()).getWeather());


        return myView;
    }

    private void Buildnews() {
        newsbuilt = new AlertDialog.Builder(getActivity());
        newsbuilt.setTitle("Choose News");
        newsbuilt.setItems(new CharSequence[]
                        {"CNN", "BBC", "Daily Mail"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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
                            Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        newsbuilt.create();


    }

    private void Buildstop() {
        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();
        busbuilt = new AlertDialog.Builder(getActivity());

        busbuilt.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SmartLocation.with(getActivity()).location()
                                .oneFix()
                                .start(new OnLocationUpdatedListener() {
                                    @Override
                                    public void onLocationUpdated(Location location) {
                                        Double Latitude = location.getLatitude();
                                        Double Longitude = location.getLongitude();
                                        try {
                                            TravelByLoc trav = new TravelByLoc();
                                            trav.execute(auth, String.valueOf(Latitude), String.valueOf(Longitude));
                                            String stop = parsejson(trav.get());
                                            ((NavigationActivity) getActivity()).setBus(stop);
                                            bustext.setText(stop);
                                            Retrievedata R = new Retrievedata();
                                            String S = null;
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
                                                Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();
                                            } else {
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
                        newSearch = new SearchStop();
                        mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
                        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
                        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Search for your stop");
                        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newSearch).commit();

                    }
                });

        busbuilt.create();
    }

    public String parsejson(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONObject obj2 = (JSONObject) obj.get("StopLocation");
        Object name = obj2.get("name");
        return name.toString();
    }

    public String weathercity(double Lat, double Long){
        String finalAddress = "";
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(Lat, Long, 1);

            Address myadd = address.get(0);
            finalAddress = myadd.getLocality();
        } catch (IOException e) {}
        catch (NullPointerException e) {}

        return finalAddress;
    }
}
