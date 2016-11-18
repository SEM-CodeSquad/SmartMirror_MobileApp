package postApp.Activities.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.Activities.RegisterActivity;
import postApp.Activities.SecretQActivity;
import postApp.DataHandlers.Network.DataBase.Login;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginPresenter {
    private LoginActivity LoginActivity;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginActivity LoginActivity) {
        this.LoginActivity = LoginActivity;
        this.loginInteractor = new LoginInteractor(LoginActivity);
    }

    /*
Switches to navigationactivity if a user correctly logs in
 */
    protected void OnLogin(String User, String Pass){
        loginInteractor.OnLogin(User,Pass);
    }
    //starts the register intent
    protected void onRegister(){
        loginInteractor.onRegister();
    }
    //starts the forgotten pass intent
    protected void onForgotten(){
        loginInteractor.onForgotten();
    }

    protected void hideKeyboard(View view) {
        loginInteractor.hideKeyboard(view);
    }
}
