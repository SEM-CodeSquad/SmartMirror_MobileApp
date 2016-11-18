package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.ActivitiesView.AuthenticationView.RegisterActivity;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginInteractor {
    postApp.ActivitiesView.AuthenticationView.LoginActivity LoginActivity;


    public LoginInteractor(LoginActivity LoginActivity){
        this.LoginActivity = LoginActivity;
    }

    public void OnLogin(String User, String Pass){
        Login log = new Login(User, Pass);
        if(log.getStatus() == true){
            //if we log in we swithc to navigationActivity
            Intent intent = new Intent(LoginActivity, NavigationActivity.class);
            //we add in a fetchable user when we start the activity
            intent.putExtra("user", User);
            LoginActivity.startActivity(intent);
        }
        else{
            //if user types wrong login we show a alertdialog some text
            new AlertDialog.Builder(LoginActivity)
                    .setTitle("Access denied")
                    .setMessage("Wrong username or password")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }
    //starts the register intent
    public void onRegister(){
        Intent intent = new Intent(LoginActivity, RegisterActivity.class);
        LoginActivity.startActivity(intent);
    }
    //starts the forgotten pass intent
    public void onForgotten(){
        Intent intent = new Intent(LoginActivity, SecretQActivity.class);
        LoginActivity.startActivity(intent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)LoginActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
