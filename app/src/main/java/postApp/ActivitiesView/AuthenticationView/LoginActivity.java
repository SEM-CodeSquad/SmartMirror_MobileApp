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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.AuthenticationPresenters.LoginPresenter;

/**
 * Class that is the Activity for the login screen and takes user inputs
 */
public class LoginActivity extends AppCompatActivity {

    EditText usrname;
    TextView passwrd;
    TextView regi;
    TextView forgot;
    private LoginPresenter presenter;
    ProgressDialog progress;
    private Boolean exit = false;
    /**
     * This method is ran when activity is created. We set onclicklisteners and
     * Initialize the views
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);


        progress = new ProgressDialog(this);
        //initilize the views
        Button login = (Button)findViewById(R.id.login);
        usrname = (EditText)findViewById(R.id.loginuser);
        passwrd = (TextView)findViewById(R.id.loginpassword);
        forgot = (TextView)findViewById(R.id.fgtpass);
        regi = (TextView)findViewById(R.id.regibtn);
        presenter = new LoginPresenter(this);

        Intent intent = getIntent();
        if(intent.hasExtra("user")){
            usrname.setText(intent.getExtras().getString("user"));
        }
        //set a onclicklistener to the register button that calls OnRegister
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegister();
            }
        });

        //set a onclicklistener to the login button that calls OnLogin with the username and password typed by the user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnLogin(usrname.getText().toString(), passwrd.getText().toString());
            }
        });
        //set a onclicklistener to the forgot pass button that calls onForgotten
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onForgotten();
            }
        });

        //if the usrname has no focus hide it!
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
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

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    /**
     * @param menu
     * Creates option menu when the activity is created
     * @return
     * true that it's created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Method that start the secretQ activity and switches screen
     */
    public void onForgotten() {
        Intent intent = new Intent(this, SecretQActivity.class);
        startActivity(intent);
    }

    /**
     *  Method that start the Register activity and switches screen
     */
    public void OnRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Method that hides keyboard when textfied not in focus
     * @param view
     * view its hiding keyboard from
     */
    public void HideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Logging In");
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
     * Method that switches activity to NavigationActivity
     *
     * @param User Puts in the intent user which is the User String
     * @param bus Puts in the intent bus which is the Bus String
     * @param weather Puts in the intent weather which is the weather String
     * @param news Puts in the intent news which is the news String
     */
    public void SuccessfulLogin(String User, String bus, String busid, String news, String weather){
        //if we log in we swithc to navigationActivity
        Intent intent = new Intent(this, NavigationActivity.class);
        //we add in a fetchable user when we start the activity
        intent.putExtra("user", User);
        intent.putExtra("bus", bus);
        intent.putExtra("busid", busid);
        intent.putExtra("weather", weather);
        intent.putExtra("news", news);
        startActivity(intent);
    }

    /**
     * Method that shows a alertdialog that says wrong username or password.
     */
    public void UnsuccessfulLogin(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(this)
                .setTitle("Access denied")
                .setMessage("Wrong username or password")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
