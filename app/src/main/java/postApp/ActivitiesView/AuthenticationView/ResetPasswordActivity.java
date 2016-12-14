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

package postApp.ActivitiesView.AuthenticationView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import adin.postApp.R;
import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * Class that is the view for the resetpassword activity, instantiates the buttons and other views
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText passwrd;
    private EditText confpass;
    private String user;
    private ResetPasswordPresenter presenter;
    private ProgressDialog progress;

    /**
     * When we switch to this screen this method happens automatically
     * @param savedInstanceState the savedinstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        //Checks if anything was passed when starting this activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
            //The key argument here must match that used in the other activity
        }
        //Changes layout
        setContentView(R.layout.resetpass);
        //Instantiates the buttons
        Button reset = (Button)findViewById(R.id.confresetbtn);
        Button cancel = (Button)findViewById(R.id.cancelresetbtn);
        passwrd = (EditText)findViewById(R.id.newpassrest);
        confpass = (EditText)findViewById(R.id.confirmreset);
        //starts the presenter passing this view
        presenter = new ResetPasswordPresenter(this);


        //Onclick listener for the reset button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    presenter.CheckPasswords(user, passwrd.getText().toString(), confpass.getText().toString());
            }
        });
        //Onclick listener for the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnCancel();
            }
        });
        //if the usrname has no focus hide it!
        confpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
        //if the passwrd has no focus hide it!
        passwrd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.hideKeyboard(v);
                }
            }
        });
    }

    /**
     * When press backbutton on the phone calls the superclass backpressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * CreatingOptionsMenu, SuperClassMethod
     * @param menu the menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Call this method to switch activity
     */
    public void PasswordReset() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    /**
     * Call this method make a alertdialog builder that tells the user that password do not match
     */
    public void PasswordNoMatch(){
        new AlertDialog.Builder(this)
                .setTitle("Passwords not matching")
                .setMessage("Please type again the passwords")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    /**
     * Call this method to hide keyboard
     * @param view the view
     */
    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Call this method to cancel password reset and returns Loginactivity
     */
    public void OnCancel(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Reseting Password");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
     * Makes a builder that tells the user that password reset failed
     */
    public void NoResetPassword() {
        new AlertDialog.Builder(this)
                .setTitle("Password Reset Failed")
                .setMessage("Please try again")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    /**
     * Shows a dialog saying password is shorter then 6 chars
     */
    public void TooShortPassword() {
        new AlertDialog.Builder(this)
                .setTitle("Password length is not larger than 5 numbers")
                .setMessage("Please type again the passwords")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }
}
