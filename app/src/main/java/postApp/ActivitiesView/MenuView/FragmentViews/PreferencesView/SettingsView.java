package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

/**
 * Created by adinH on 2016-10-27.
 */
/*
This is the settings fragments managing all the controlling when pressing the settings buttons.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

import adin.postApp.R;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import postApp.Activities.NavigationActivity.Fragments.QrCode;
import postApp.Activities.NavigationActivity.Fragments.SearchStop;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.DataHandlers.JsonHandler.ParseJson;
import postApp.DataHandlers.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.Vasttrafik.TravelByLoc;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;
import postApp.ActivitiesView.MenuView.NavigationActivity;


public class SettingsView extends Fragment {
    View myView;
    SearchStop newSearch;
    AlertDialog.Builder newsbuilt;
    AlertDialog.Builder busbuilt;
    String auth;
    public EditText UUID;
    public String user;
    private SettingsPresenter presenter;
    public EditText bustext;
    public EditText newstext;
    public EditText weathertext;
    private View.OnClickListener mOriginalListener;

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

        presenter = new SettingsPresenter(this);

        presenter.SetUUID();
        presenter.SetWeather();
        presenter.SetBus();
        presenter.SetTextNews();

        //we build here our builders for news and the stop
        presenter.BuildNews();
        presenter.BuildStop();

        //A QrCode button that has a onclicklistener that changes fragments to the qr fragment, and then change title on the toolbar.
        // the toggleDrawerUse switches from a drawer to a backbutton.
        QrCodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ChangeToQR();
            }
        });

        //when we press news we show the builder built below
        newsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.StartNews();
            }
        });

        //when we press bus button we show the builder built below
        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.StartBus();
            }
        });

        // a onclick listener that uses the library nlopez smartlocation lib that gets the current location one time only.
        weatherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.WeatherOnLoc();
                presenter.SetWeather();
            }
        });

        return myView;
    }

    public void SetNews(){
        newstext.setText(((NavigationActivity) getActivity()).getNews());

    }
    public void SetUUID(){
        UUID.setText(((NavigationActivity) getActivity()).getMirror());

    }
    public void SetBus(){
        bustext.setText(((NavigationActivity) getActivity()).getBus());

    }
    public void SetWeather(){
        weathertext.setText(((NavigationActivity) getActivity()).getWeather());
    }
    public void ChangeToQR(){
        mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror ID");
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCode()).commit();
    }
    public void ShowBus(){
            busbuilt.show();
    }
    public void ShowNews(){
        newsbuilt.show();
    }

    public void displaySuccPub(String S){
        Toast.makeText(getActivity(), S, Toast.LENGTH_SHORT).show();

    }
    public void NoMirrorChosen(){
        // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }
    public void ChangeToSearch(){
        //here we save the toolbarnavigationlistener because we want a back button now instead of a drawer.
        mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
        //we set the drawer to false and it becomes a back button
        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
        //sets title
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Search for your stop");
        //switches fragment
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SearchStop()).commit();
    }

    // this is used to build a AlertDialog that displays newsoptions.
    public void Buildnews() {
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
                                presenter.SetNewsCNN();
                                presenter.SetTextNews();
                                break;
                            case 1:
                                presenter.SetNewsBBC();
                                presenter.SetTextNews();
                                break;
                            case 2:
                                presenter.SetNewsDailyMail();
                                presenter.SetTextNews();
                                break;

                        }
                        presenter.PublishNews(((NavigationActivity) getActivity()).getNews(), ((NavigationActivity) getActivity()).getMirror());
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
        busbuilt = new AlertDialog.Builder(getActivity());
        //We give the usser two options by location or by search.
        busbuilt.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if by location is chosen we use the SmartLocation lib once again to get the fixed location
                        presenter.BusByLoc();
                        presenter.PublishBus(((NavigationActivity) getActivity()).getBus(), ((NavigationActivity) getActivity()).getMirror());
                    }
                })
                .setNegativeButton("By Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.ChangeToSearch();

                    }
                });
        //creates builder
        busbuilt.create();
    }
}