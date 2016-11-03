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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import adin.postApp.R;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import postApp.Controllers.NavigationActivity;
import postApp.MqTTHandler.Retrievedata;


public class Settings extends Fragment {
    View myView;
    QrCode newQr;
    public EditText UUID;
    double Latitude;
    double Longitude;
    String auth;
    private View.OnClickListener mOriginalListener;
    String Tramstation = "Scandinavium";
    Thread one;
    AlertDialog.Builder build1;
    AlertDialog.Builder build2;
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

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uuid = ((NavigationActivity) getActivity()).getUUID();
                String topic = ((NavigationActivity) getActivity()).getMirror();
                String bus = ((NavigationActivity) getActivity()).getBus();
                String news = ((NavigationActivity) getActivity()).getNews();
                String weather = "Göteborg";

                Retrievedata R = new Retrievedata();
                String S = null;
                if (topic != "No mirror chosen") {
                    try {
                        S = R.execute(topic, "config", bus, news, weather, uuid).get();
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
        weathertext.setText("Göteborg");
        return myView;
    }

    private void Buildnews(){
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

    private void Buildstop(){
        build2 = new AlertDialog.Builder(getActivity());

        //if (title != null) builder.setTitle(title);
        /*one = new Thread() {
            public void run() {
                try {
                    GenerateAccessCode gen = new GenerateAccessCode();
                    auth = gen.getAccess();
                    TravelByLoc trav = new TravelByLoc();
                    trav.execute(auth, String.valueOf(Latitude), String.valueOf(Longitude));
                } catch(Exception v) {
                    System.out.println(v);
                }
            }
        };*/

        build2.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                        SmartLocation.with(getActivity()).location()
                                .oneFix()
                                .start(new OnLocationUpdatedListener() {
                                    @Override
                                    public void onLocationUpdated(Location location) {
                                        Latitude = location.getLatitude();
                                        Longitude = location.getLongitude();
                                    }

                                });


                        one.start();*/
                        ((NavigationActivity) getActivity()).setBus(Tramstation);
                        bustext.setText(((NavigationActivity) getActivity()).getBus());

                    }
                })
                .setNegativeButton("By Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((NavigationActivity) getActivity()).setBus(Tramstation);
                        bustext.setText(((NavigationActivity) getActivity()).getBus());
                    }
                });

        build2.create();
    }

}

