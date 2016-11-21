package postApp.Presenters.AuthenticationPresenters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.ActivitiesView.AuthenticationView.RegisterActivity;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;
import postApp.DataHandlers.Authentication.LoginInteractor;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;

/**
 * @author adinH on 2016-11-18.
 */

public class LoginPresenter {
    private LoginInteractor loginInteractor;
    private LoginActivity LoginActivity;

    public LoginPresenter(LoginActivity LoginActivity) {
        this.LoginActivity = LoginActivity;
        this.loginInteractor = new LoginInteractor(this);
    }

    /*
Switches to navigationactivity if a user correctly logs in
 */
    public void OnLogin(String User, String Pass){

        loginInteractor.OnLogin(User,Pass);
    }

    //starts the register intent
    public void onRegister(){
        LoginActivity.OnRegister();
    }
    //starts the forgotten pass intent
    public void onForgotten(){
        LoginActivity.onForgotten();
    }

    public void hideKeyboard(View view) {
        LoginActivity.HideKeyboard(view);
    }

    public void SuccessfulLogin(String User){
        LoginActivity.SuccessfulLogin(User);
    }

    public void UnsuccessfulLogin(){
        LoginActivity.UnsuccessfulLogin();
    }
}
