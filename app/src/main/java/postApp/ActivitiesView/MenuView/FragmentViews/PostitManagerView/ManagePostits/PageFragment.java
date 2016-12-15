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

package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.PageFragmentPresenter;

/**
 * Class that is responsible for the PageFragment view. Handling the views and onclicklisteners
 */

public class PageFragment extends Fragment {
    String message;
    String color;
    String id;
    String expireat;
    ImageView imageView;
    TextView textview;
    TextView expiresat;
    AlertDialog.Builder builderDelete;
    AlertDialog.Builder builderEdit;
    PageFragmentPresenter presenter;
    ProgressDialog progress;

    /**
     * OnCreate method called when created
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mirror_postit_fragment_view, container, false);
        //Instantiate all the buttons, textviews, and alertdialog
        builderDelete = new AlertDialog.Builder(getActivity());
        builderEdit = new AlertDialog.Builder(getActivity());
        expiresat = (TextView)view.findViewById(R.id.expiresat);
        textview = (TextView)view.findViewById(R.id.editTypedtext);
        imageView = (ImageView)view.findViewById(R.id.manageImageview);
        Button EditPostit = (Button)view.findViewById(R.id.editcheckmark);
        Button DeletePostit = (Button)view.findViewById(R.id.deletepostitbutton);

        //gets the passed argument from the class that switched fragment
        Bundle bundle = getArguments();
        expireat = bundle.getString("Timestamp");
        message = bundle.getString("Text");
        id = bundle.getString("ID");
        color = bundle.getString("Color");
        //insantiates the progress dialog
        progress = new ProgressDialog(getActivity());
        //sets the textview with a message
        textview.setText(message);
        //sets the expiresat textview with the expiration date
        expiresat.setText(expireat);
        presenter = new PageFragmentPresenter(this,color,((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());

        /**
         * Calls the presenters function ShowEdit()
         */
        DeletePostit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDelete();
            }
        });
        /**
         * Calls the presenters function ShowEdit()
         */
        EditPostit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showEdit();
            }
        });

        /**
         * Call the presenters function for hideKeyboard if theres no focus on the keyboard
         */
        textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });

        return view;
    }
    /**
     * Creates the alertdialog builder for deleting postits
     */
    public void buildDelete(){
        builderDelete.setTitle("Confirm");
        builderDelete.setMessage("Are you sure you want to delete this postit?");

        builderDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //If pressed yes we call the presenters function DeketePostit and then dismiss the dialog
                presenter.DeletePostit(((NavigationActivity) getActivity()).getMirror(), id, ((NavigationActivity) getActivity()).getUser());

                dialog.dismiss();
            }
        });

        builderDelete.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderDelete.create();
    }

    /**
     * Creates the alertdialog builder for editing postits
     */
    public void buildEdit(){
        builderEdit.setTitle("Confirm");
        builderEdit.setMessage("Are you sure you want to edit this postit?");

        builderEdit.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //If pressed yes we call the presenters function EditPostit and then dismiss the dialog
                presenter.EditPostit(((NavigationActivity) getActivity()).getMirror(), id, textview.getText().toString(),  ((NavigationActivity) getActivity()).getUser());

                dialog.dismiss();
            }
        });

        builderEdit.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderEdit.create();
    }

    /**
     * Shows the Delete alertdialog
     */
    public void ShowDelete(){
        builderDelete.show();
    }
    /**
     * Shows the edit alertdialog
     */
    public void ShowEdit(){
        builderEdit.show();
    }
    /**
     * Displays a toast with the message to chose a mirror first
     */
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorBlue(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorPink(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorPurple(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorGreen(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorOrange(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorYellow(){
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
    }

    /**
     * Method used for reloading fragment
     */
    public void ReloadScreen(){
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new ManagePostitsView()).addToBackStack(null).commit();
    }
    /**
    * Hides keyboard
    * @param view The view passed to hide keyboard
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(String message){
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    /**
     * method that dismisses the progressbar
     */
    public void DoneLoading(){
        progress.dismiss();
    }

    /**
     * Method that shows a alertdialog that says fail.
     * @param s the string we show
     */
    public void UnsuccessfulPublish(String s){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle(s)
                .setMessage("No answer received, please try to pair with the mirror again")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}

