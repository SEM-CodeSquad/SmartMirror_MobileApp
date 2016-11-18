package postApp.Activities.Login;

import android.view.View;

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
