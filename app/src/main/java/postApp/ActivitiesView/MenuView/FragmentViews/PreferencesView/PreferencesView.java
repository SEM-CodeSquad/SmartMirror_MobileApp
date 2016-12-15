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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;

/**
 * This class is responsible for the preferences fragment, handles all the views (buttons, switches) and the user clicks and input..
 */

public class PreferencesView extends Fragment {
        View myView;
    Switch disableswitch;
    Switch busswitch;
    Switch newsswitch;
    Switch clockswitch;
    Switch deviceswitch;
    Switch weatherswitch;
    Switch postitswitch;
    Switch greetingsswitch;
    Switch shoppingswitch;
    PreferencesPresenter presenter;
    Button publish;
    ProgressDialog progress;

    /**
     * This method is called onCreate.
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view inflated
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preferences, container, false);

        /*
        instantiate all the bswitches and buttons
         */
        disableswitch = (Switch) myView.findViewById(R.id.disableswitch);
        busswitch = (Switch) myView.findViewById(R.id.busswitch);
        newsswitch = (Switch) myView.findViewById(R.id.newsswitch);
        clockswitch = (Switch) myView.findViewById(R.id.clockswitch);
        deviceswitch = (Switch) myView.findViewById(R.id.deviceswitch);
        weatherswitch = (Switch) myView.findViewById(R.id.weatherswitch);
        postitswitch = (Switch) myView.findViewById(R.id.postswitch);
        greetingsswitch = (Switch) myView.findViewById(R.id.greetingsswitch);
        shoppingswitch = (Switch) myView.findViewById(R.id.shoppingswitch);
        publish = (Button) myView.findViewById(R.id.prefpub);
        //Instantiate the presenter with this class and mirror and user
        presenter = new PreferencesPresenter(this,  ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());
        //instantiate the progressdialog
        progress = new ProgressDialog(getActivity());

        /**
         * Calls the presenter function for prefBtn when clcked
         */
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PrefBtn();
            }
        });
        /**
         * Calls the presenter function DisBtnTrue or false depending on if the switch is checked or not
         */
        disableswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    presenter.DisBtnTrue();
                } else {
                    presenter.DisBtnFalse();
                }
            }
        });
        /**
         * Calls the presenter function ShoppingFalse if its checked, which is used to disable the shoppingswitch when busswitch is checked
         */
        busswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    presenter.ShoppingFalse();
                }
            }
        });
        /*
        * Calls the presenter function BusFalse if its checked, which is used to disable the Busswitch when shoppingswitch is checked
        */
        shoppingswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    presenter.BusFalse();
                }
            }
        });

        return myView;
        }

    /**
     * OnResume sets title to preferences
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Preferences");
    }

    /**
     * Displays a toast to chose mirror first
     */
    public void NoMirrorChosen(){
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets all the switches to true
     */
    public void DisBtnTrue(){
        busswitch.setChecked(true);
        newsswitch.setChecked(true);
        postitswitch.setChecked(true);
        greetingsswitch.setChecked(true);
        clockswitch.setChecked(true);
        deviceswitch.setChecked(true);
        weatherswitch.setChecked(true);
    }
    /**
     * Sets all the switches to false
     */
    public void DisBtnFalse(){
        busswitch.setChecked(false);
        newsswitch.setChecked(false);
        postitswitch.setChecked(false);
        greetingsswitch.setChecked(false);
        clockswitch.setChecked(false);
        deviceswitch.setChecked(false);
        weatherswitch.setChecked(false);
        shoppingswitch.setChecked(false);
    }
    /**
     * Calls the presenters method to publish preferences
     */
    public void PublishPrefs(){
        presenter.PublishPrefs(((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(), Boolean.toString(newsswitch.isChecked()),
                Boolean.toString(busswitch.isChecked()),
                Boolean.toString(weatherswitch.isChecked()), Boolean.toString(clockswitch.isChecked()),  Boolean.toString(deviceswitch.isChecked()),
                Boolean.toString(greetingsswitch.isChecked()), Boolean.toString(postitswitch.isChecked()), Boolean.toString(shoppingswitch.isChecked()));
    }

    /**
     * Called to set busswitch to false
     */
    public void BusFalse(){
        busswitch.setChecked(false);
    }
    /**
     * Called to set shoppingswitch to false
     */
    public void ShoppingFalse(){
        shoppingswitch.setChecked(false);
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
     * Method that shows a alertdialog that says fail.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to update preferences")
                .setMessage("No answer received, please try to pair with the mirror again")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
