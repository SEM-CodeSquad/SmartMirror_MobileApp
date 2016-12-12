package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

/**
 * Created by adinH on 2016-10-27.
 */
/*
This is the settings fragments managing all the controlling when pressing the settings buttons.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import adin.postApp.R;

import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.DataHandlers.AppCommons.Vasttrafik.GenerateAccessCode;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;
import postApp.ActivitiesView.MenuView.NavigationActivity;


public class SettingsView extends Fragment {
    View myView;
    AlertDialog.Builder newsbuilt;
    AlertDialog.Builder busbuilt;
    public EditText UUID;
    public String user;
    private SettingsPresenter presenter;
    public EditText bustext;
    public EditText newstext;
    public EditText weathertext;
    private View.OnClickListener mOriginalListener;
    ProgressDialog progress;

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
        final Button StoreButton = (Button) myView.findViewById(R.id.storeindb);
        //These four line below just locate the edittext for the mirror, bus, news and weather so we don't get null pointer later when working.
        UUID = (EditText) myView.findViewById(R.id.mirrortext);
        bustext = (EditText) myView.findViewById(R.id.bustext);
        newstext = (EditText) myView.findViewById(R.id.newstext);
        weathertext = (EditText) myView.findViewById(R.id.citytext);
        user =  (((NavigationActivity) getActivity()).getUser());
        progress = new ProgressDialog(getActivity());

        presenter = new SettingsPresenter(this, ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());

        //A QrCodeView2 button that has a onclicklistener that changes fragments to the qr fragment, and then change title on the toolbar.
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

        //when we press storebutton
        StoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PublishAll(((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(), ((NavigationActivity) getActivity()).getNews(),
                        ((NavigationActivity) getActivity()).getWeather(), ((NavigationActivity) getActivity()).GetBusID(), ((NavigationActivity) getActivity()).getBus());
            }
        });
        // a onclick listener that uses the library nlopez smartlocation lib that gets the current location one time only.
        weatherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.WeatherOnLoc();
            }
        });

        return myView;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Settings");
    }

    public void SetNews(String news){
        newstext.setText(news);
    }
    public void SetUUID(String id){
        UUID.setText(id);
    }
    public void SetBus(String bus){
        bustext.setText(bus);
    }
    public void SetWeather(String weather){
        weathertext.setText(weather);
    }

    public void ChangeToQR(){
        mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror ID");
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView(), "QRFRAG").addToBackStack(null).commit();
    }
    public void ShowBus(){
            busbuilt.show();
    }
    public void ShowNews(){
        newsbuilt.show();
    }


    /**
     * Method that shows a alertdialog that says fail.
     */
    public void ChoseAllSettings(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Not all settings are chosen")
                .setMessage("Please chose all settings first")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
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
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new BusStopSearcherView(), "BUSFRAG").addToBackStack(null).commit();
    }

    // this is used to build a AlertDialog that displays newsoptions.
    public void Buildnews() {
        newsbuilt = new AlertDialog.Builder(getActivity());
        //set the title
        newsbuilt.setTitle("Choose News");
        //three options
        newsbuilt.setItems(new CharSequence[]
                        {"CNN", "GOOGLE", "DN", "SVT", "EXPRESSEN", "ABC"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // A switch with a onlick that sets text in activity based on what you choose
                        switch (which) {
                            case 0:
                                presenter.SetTextField("CNN");
                                presenter.SetNews("CNN");
                                break;
                            case 1:
                                presenter.SetTextField("GOOGLE");
                                presenter.SetNews("GOOGLE");
                                break;
                            case 2:
                                presenter.SetTextField("DN");
                                presenter.SetNews("DN");
                                break;
                            case 3:
                                presenter.SetTextField("SVT");
                                presenter.SetNews("SVT");
                                break;
                            case 4:
                                presenter.SetTextField("EXPRESSEN");
                                presenter.SetNews("EXPRESSEN");
                                break;
                            case 5:
                                presenter.SetTextField("ABC");
                                presenter.SetNews("ABC");
                                break;
                        }
     }
                });
        //and we create it with all the above options.
        newsbuilt.create();
    }

    public void Buildstop() {

        //we build our bus alertdialog
        busbuilt = new AlertDialog.Builder(getActivity());
        //We give the usser two options by location or by search.
        busbuilt.setMessage("Choose One Option")
                .setPositiveButton("By Location", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if by location is chosen we use the SmartLocation lib once again to get the fixed location
                        presenter.BusByLoc();
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

    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Updating settings");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    /**
     * method that dismisses the progressbar
     */
    public void DoneLoading(){
        progress.dismiss();
    }

    /**
     * Method that shows a alertdialog that says fail.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to edit settings")
                .setMessage("No answer received")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}
