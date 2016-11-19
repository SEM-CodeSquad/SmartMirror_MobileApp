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
import postApp.Presenters.AuthenticationPresenters.LoginPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginInteractor {
    LoginPresenter LoginPresenter;


    public LoginInteractor(LoginPresenter LoginPresenter){
        this.LoginPresenter = LoginPresenter;
    }

    public void OnLogin(String User, String Pass){
        Login log = new Login(User, Pass);
        if(log.getStatus() == true){
            LoginPresenter.SuccessfulLogin(User);
        }
        else{
            LoginPresenter.UnsuccessfulLogin();
        }
    }
}
