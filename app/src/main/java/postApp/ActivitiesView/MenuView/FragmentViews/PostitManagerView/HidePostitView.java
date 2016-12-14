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

package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView;

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
import android.widget.RadioButton;
import android.widget.Toast;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.HidePostitPresenter;

/**
 * Class used responsible for the HidePostitView
 */
public class HidePostitView extends Fragment {
    View myView;
    HidePostitPresenter presenter;
    Button publish;
    RadioButton blue;
    RadioButton yellow;
    RadioButton green;
    RadioButton purple;
    RadioButton pink;
    RadioButton orange;
    RadioButton all;
    ProgressDialog progress;

    /**
     * Method that is started when this fragment created
     * @param inflater The inflater
     * @param container The container
     * @param savedInstanceState the saved Instance
     * @return the view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.hidepostit, container, false);

        //Instantiate the progress dialog, button and radiobuttons. Instantiate the presenter with this view, and the mirror and the user.
        progress = new ProgressDialog(getActivity());
        publish = (Button) myView.findViewById(R.id.updatecheck);
        presenter = new HidePostitPresenter(this, ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());
        blue = (RadioButton)  myView.findViewById(R.id.bluecheck);
        yellow = (RadioButton)  myView.findViewById(R.id.yellowcheck);
        green = (RadioButton)  myView.findViewById(R.id.greencheck);
        purple = (RadioButton)  myView.findViewById(R.id.purplecheck);
        pink = (RadioButton)  myView.findViewById(R.id.pinkcheck);
        orange = (RadioButton)  myView.findViewById(R.id.orangecheck);
        all = (RadioButton)  myView.findViewById(R.id.allcheck);

        /**
         * Onclicklistener that when clicked calles the presenters function to FilterPostits
         */
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.FilterPost(((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(),
                        Boolean.toString(yellow.isChecked()), Boolean.toString(blue.isChecked()), Boolean.toString(orange.isChecked()),
                        Boolean.toString(pink.isChecked()), Boolean.toString(green.isChecked()), Boolean.toString(purple.isChecked()));
            }
        });

        return myView;
    }

    /**
     * Sets the supportAction bar title
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Filter Postits");
    }

    /**
     * if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
     */
    public void NoMirrorChosen(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }
    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Filtering postits");
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
     * Method that shows a alertdialog that says fail to filter postits.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to filter postits")
                .setMessage("No answer received")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }



}
