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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


import adin.postApp.R;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import postApp.Controllers.NavigationActivity;
import postApp.Network.MqTTHandler.Retrievedata;
import postApp.Network.vasttrafik.GenerateAccessCode;
import postApp.Network.vasttrafik.TravelByLoc;


public class Settings extends Fragment {
    View myView;
    QrCode newQr;
    SearchStop newSearch;
    public EditText UUID;
    String auth;
    private View.OnClickListener mOriginalListener;
    Thread one;
    AlertDialog.Builder build1;
    AlertDialog.Builder build2;
    EditText bustext;
    EditText newstext;
    EditText weathertext;
    private String m_Text = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.settings, container, false);
        Button QrCodebtn = (Button) myView.findViewById(R.id.mirrorbtn);
        Button busbutton = (Button) myView.findViewById(R.id.busbtn);
        Button newsbutton = (Button) myView.findViewById(R.id.newsbtn);
        Button weatherbutton = (Button) myView.findViewById(R.id.weatherchange);
        Button confirmbutton = (Button) myView.findViewById(R.id.confirmbtn);


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
                build1.show();
            }
        });

        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                build2.show();

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
                            }
                        });
            }
        });




        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String bus = ((NavigationActivity) getActivity()).getBus();
                String news = ((NavigationActivity) getActivity()).getNews();
                String weather = ((NavigationActivity) getActivity()).getWeather();;
                Retrievedata R = new Retrievedata();
                String S = null;
                if (topic != "No mirror chosen") {
                    try {
                        S = R.execute(topic, "config", bus, news, weather).get();
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
        UUID = (EditText) myView.findViewById(R.id.mirrortext);
        bustext = (EditText) myView.findViewById(R.id.bustext);
        newstext = (EditText) myView.findViewById(R.id.newstext);
        weathertext = (EditText) myView.findViewById(R.id.citytext);
        UUID.setText(((NavigationActivity) getActivity()).getMirror());
        bustext.setText(((NavigationActivity) getActivity()).getBus());
        newstext.setText(((NavigationActivity) getActivity()).getNews());
        weathertext.setText(((NavigationActivity) getActivity()).getWeather());
        return myView;
    }

    private void Buildnews() {
        build1 = new AlertDialog.Builder(getActivity());
        build1.setTitle("Choose News");
        build1.setItems(new CharSequence[]
                        {"CNN", "BBC", "Daily Mail"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                System.out.println("case0");
                                ((NavigationActivity) getActivity()).setNews("CNN");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;
                            case 1:
                                System.out.println("case1");
                                ((NavigationActivity) getActivity()).setNews("BBC");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;
                            case 2:
                                System.out.println("case1");
                                ((NavigationActivity) getActivity()).setNews("Daily Mail");
                                newstext.setText(((NavigationActivity) getActivity()).getNews());
                                break;

                        }
                    }
                });
        build1.create();


    }

    private void Buildstop() {
        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();
        build2 = new AlertDialog.Builder(getActivity());

        build2.setMessage("Choose One Option")
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

        build2.create();
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
