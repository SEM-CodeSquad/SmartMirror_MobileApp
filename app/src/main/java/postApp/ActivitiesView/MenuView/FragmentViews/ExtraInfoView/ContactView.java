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

package postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.ContactPresenter;

/**
 * Class responsible for the contact layout view and instantiating all the buttons and onclicklistners
 */

public class ContactView extends Fragment {

    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, sub, msg;
    View myView;
    ContactPresenter presenter;

    /**
     * This method is ran on create of the view which instantiates the buttons and sets onclick listeners.
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance
     * @return the view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contact, container, false);

        context = getActivity();
        Button sendemail = (Button) myView.findViewById(R.id.emailsend);
        reciep = (EditText) myView.findViewById(R.id.receip);
        reciep.setText(((NavigationActivity) getActivity()).getUser());
        sub = (EditText) myView.findViewById(R.id.emtitle);
        msg = (EditText) myView.findViewById(R.id.emtext);
        presenter = new ContactPresenter(this);
        /**
         * Sets a onclick lister to the button send email that calls the presenters function to send email
         */
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.SendEmail(reciep.getText().toString(), sub.getText().toString(),  msg.getText().toString());
            }
        });

        /**
         * if the sub edittext has no focus hide it!
         */
        sub.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    presenter.hideKeyboard(v);
                }
            }
        });
        /**
         * if the msg edittext has no focus hide it!
         */
        msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    presenter.hideKeyboard(v);
                }
            }
        });
        /**
         * if the receip has no focus hide it!
         */
        reciep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    presenter.hideKeyboard(v);
                }
            }
        });

        return myView;
    }

    /**
     * Method that onResume sets actionbar title
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Contact Us");
    }

    public void ShowProgress(){
        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);
    }

    /**
     * Method for reseting the email fields and making a toast
     */
    public void SentEmail() {
        pdialog.dismiss();
        msg.setText("");
        sub.setText("");
        Toast.makeText(getActivity(), "We hope to answer your Email within 24 hours", Toast.LENGTH_LONG).show();
    }

    /**
     * Method that hides keyboard when textfied not in focus
     * @param view
     * view its hiding keyboard from
     */
    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Method to display a alertdialog with the Message S
     * @param s the message
     */
    public void NotCorrect(String s) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(s)
                    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();


    }
}