/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

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
import android.widget.TextView;
import android.widget.Toast;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;
import postApp.ActivitiesView.MenuView.NavigationActivity;

/**
 * This class is responsible for the Settings view fragment and the views like buttons and textview
 */
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
    ProgressDialog progress;
    private TextView username;

    /**
     * Method that is done on create
     * @param inflater the inflater
     * @param container the viewgroup container
     * @param savedInstanceState the saved instance
     * @return the view we inflate
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
        username = (TextView) myView.findViewById(R.id.usernameSett);

        user =  (((NavigationActivity) getActivity()).getUser());
        //initialize the progressdialog progress
        progress = new ProgressDialog(getActivity());
        //the presenter is initialize with this view and mirror id and user
        presenter = new SettingsPresenter(this, ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());

        //A QrCodeView button that has a onclicklistener that changes fragments to the qr fragment, and then change title on the toolbar.
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
                presenter.ShowNews();
            }
        });

        //when we press bus button we show the builder built below
        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ShowBus();
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

    /**
     * On resume we set the title to settings
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Settings");
    }
    /**
     * Sets the news textview
     * @param news the news source
     */
    public void SetNews(String news){
        newstext.setText(news);
    }
    /**
     * Sets the uuid textview which is the mirror UUID
     * @param id the ID
     */
    public void SetUUID(String id){
        UUID.setText(id);
    }

    /**
     * Sets the username textview
     * @param user the usernamme
     */
    public void SetUser(String user){
        username.setText(user);
    }

    /**
     * Sets the bustext textview
     * @param bus the bustation
     */
    public void SetBus(String bus){
        bustext.setText(bus);
    }

    /**
     * Sets the weathertext textview
     * @param weather the weather
     */
    public void SetWeather(String weather){
        weathertext.setText(weather);
    }

    /**
     * Method that changes to QR fragment and changes the drawer to a back button and sets title of the actionbar
     */
    public void ChangeToQR(){
        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror Code");
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView(), "QRFRAG").addToBackStack(null).commit();
    }

    /**
     * Method the shows the bus dialog
     */
    public void ShowBus(){
            busbuilt.show();
    }

    /**
     * Method the shows the news dialog
     */
    public void ShowNews(){
        newsbuilt.show();
    }

    /**
     * Method that shows a alertdialog that says not all settings are chosen.
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

    /**
     * This function is used for changing screen to the BusStopSearcher Fragment view
     */
    public void ChangeToSearch(){

        //we set the drawer to false and it becomes a back button
        ((NavigationActivity) getActivity()).toggleDrawerUse(false);
        //sets title
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Search for your stop");
        //switches fragment
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new BusStopSearcherView(), "BUSFRAG").addToBackStack(null).commit();
    }

    /**
     * This is used to build a AlertDialog that displays newsoptions.
     */
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

    /**
     * Method to build a alertdialog for chosing location or search
     */
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
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    /**
     * method that dismisses the progressbar
     */
    public void DoneLoading(){
        progress.dismiss();
    }

    /**
     * Method that shows a alert dialog that says fail to edit settings.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alert dialog some text
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
