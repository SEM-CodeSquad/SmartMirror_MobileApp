package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

/**
 * Created by adinH on 2016-10-27.
 */
/*
This is the settings fragments managing all the controlling when pressing the settings buttons.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import adin.postApp.R;

import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;
import postApp.ActivitiesView.MenuView.NavigationActivity;


public class SettingsView extends Fragment {
    View myView;

    public EditText UUID;
    public String user;
    private SettingsPresenter presenter;
    public EditText bustext;
    public EditText newstext;
    public EditText weathertext;

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

        //we build here our builders for news and the stop
        presenter.BuildNews();
        presenter.BuildStop();

        //A QrCode button that has a onclicklistener that changes fragments to the qr fragment, and then change title on the toolbar.
        // the toggleDrawerUse switches from a drawer to a backbutton.
        QrCodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ChangeToQr();
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
            }
        });


        // Here we finish off the onCreate with setting the UUID, bus, news,weather to the one in our activity.
        UUID.setText(((NavigationActivity) getActivity()).getMirror());
        bustext.setText(((NavigationActivity) getActivity()).getBus());
        newstext.setText(((NavigationActivity) getActivity()).getNews());
        weathertext.setText(((NavigationActivity) getActivity()).getWeather());


        return myView;
    }

}
