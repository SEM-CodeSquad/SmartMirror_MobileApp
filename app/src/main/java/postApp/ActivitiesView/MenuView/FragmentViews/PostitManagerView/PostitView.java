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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;

/**
 Class responsible for the PostitView. Handles user clicks, instantiates views. Changes postit colors
 */
public class PostitView extends Fragment {


    Button datebutton;
    EditText typedtext;
    View myView;
    PostitPresenter presenter;
    AlertDialog.Builder builder;
    ImageView PostitImage;
    ProgressDialog progress;

    /**
     * When we change to this fragment OnCreateView is started which gets buttons, builds alertdialogs and everything required for this view.
     * @param inflater used for inflating the layout
     * @param container the shared viewgroup of the fragments
     * @param savedInstanceState and the saved instance
     * @return the view for the frament
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.postit, container, false);
        //instantiate the views
        final ImageButton colorbutton = (ImageButton)myView.findViewById(R.id.colorbutton);
        final ImageButton checkmark = (ImageButton)myView.findViewById(R.id.checkmark);
        datebutton = (Button) myView.findViewById(R.id.datebutton);
        PostitImage = (ImageView)myView.findViewById(R.id.ImageView);
        typedtext = (EditText)myView.findViewById(R.id.typedText);
        builder = new AlertDialog.Builder(getActivity());
        presenter = new PostitPresenter(this, ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser());
        progress = new ProgressDialog(getActivity());


        /**
         * Set a onclicklistener for the datebutton that shows a datepicker by calling the presenters function ShowCalender
         */
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ShowCalender();
            }
        });


        /**
         * When colorbutton is pressed in the UI we show the color builder by calling the presenter function for showing colors
         */
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ShowColors();
            }
        });

        /**
         * This is used for posting a postit with starting the Retrieve data class with all the args we need.
         */
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!datebutton.getText().equals("Chose Expiry Date")){
                    presenter.PublishPostit(typedtext.getText().toString(),  ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(), datebutton.getText().toString());
                }
               else{
                    presenter.PublishPostit(typedtext.getText().toString(),  ((NavigationActivity) getActivity()).getMirror(), ((NavigationActivity) getActivity()).getUser(), "standard");
                }

            }
        });


        /**
         * Remove focus from typedtext when clicked on a diffrent place meaning we will hide keyboard when not typing
         */
        typedtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });

        return myView;
    }


    /**
     * When we resume to this activity we call the superclass function and we set the title of the actionbar to the publish postit
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Publish Postit");
    }

    /**
     *  Method used for making a datepickerdialog and showing it.
     */
    public void ShowCalender(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();
    }
    /**
     * A alertdialog builder displaying a choice of colors and calling the presenter to change colors according to this
     */
    public void BuildColorChoice(){

        //Set the title to chose color
        builder.setTitle("Chose color");
        //Building the alertdialog with 6 options
        builder.setItems(new CharSequence[]
                        {"Blue", "Green", "Yellow", "Orange", "Purple", "Pink"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("blue");
                                presenter.BlueClick();
                                break;
                            case 1:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("green");
                                presenter.GreenClick();
                                break;
                            case 2:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("yellow");
                                presenter.YellowClick();
                                break;
                            case 3:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("orange");
                                presenter.OrangeClick();
                                break;
                            case 4:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("purple");
                                presenter.PurpleClick();
                                break;
                            case 5:
                                //We change color, and change the image color of the picture
                                presenter.SetColor("pink");
                                presenter.PinkClick();
                                break;
                        }
                    }
                });
        builder.create();
    }


    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorBlue(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_blue));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorPink(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_pink));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorPurple(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_purple));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorGreen(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_green));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorOrange(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_orange));
    }
    /**
     * Change the picture color to match the switch chosen
     */
    public void ColorYellow(){
        PostitImage.setImageDrawable(getResources().getDrawable(R.mipmap.post_it_yellow));
    }

    /**
     * IF no mirror ID is chosen display a toast
     */
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();

    }

    public void ShowColors(){
        builder.show();
    }
    /*
    Hides keyboard
    @param view The view passed to hide keyboard
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Adding Postit");
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
     * Method that shows a alertdialog that says failed to add postit
     */
    public void UnsuccessfulPublish(String st){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to add Postit")
                .setMessage(st)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * A innerclass for a datepickerdialog
     */
    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        /**
         * When we pick date we set text on the datebutton witth the current date
         * @param view the view it's in
         * @param year the year
         * @param monthOfYear the month
         * @param dayOfMonth the day
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // getCalender();
            datebutton.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(monthOfYear + 1).append("/").append(dayOfMonth).append("/")
                    .append(year).append(" "));
        }
    }

}
