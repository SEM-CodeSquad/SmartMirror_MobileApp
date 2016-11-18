package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.LoginInteractor;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginPresenter {
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginActivity LoginActivity) {
        this.loginInteractor = new LoginInteractor(LoginActivity);
    }

    /*
Switches to navigationactivity if a user correctly logs in
 */
    public void OnLogin(String User, String Pass){
        loginInteractor.OnLogin(User,Pass);
    }
    //starts the register intent
    public void onRegister(){
        loginInteractor.onRegister();
    }
    //starts the forgotten pass intent
    public void onForgotten(){
        loginInteractor.onForgotten();
    }

    public void hideKeyboard(View view) {
        loginInteractor.hideKeyboard(view);
    }
}
