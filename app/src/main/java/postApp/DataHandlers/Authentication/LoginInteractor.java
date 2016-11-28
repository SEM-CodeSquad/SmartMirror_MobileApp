package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Observable;
import java.util.Observer;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.ActivitiesView.AuthenticationView.RegisterActivity;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;
import postApp.DataHandlers.Settings.Settings;
import postApp.Presenters.AuthenticationPresenters.LoginPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginInteractor implements Observer {
    LoginPresenter LoginPresenter;
    Login log;
    String User;
    String Bus;
    String News;
    String Weather;
    Boolean sett = false;
    Settings set;

    public LoginInteractor(LoginPresenter LoginPresenter){
        this.LoginPresenter = LoginPresenter;
    }

    public void OnLogin(String User, String Pass){
        this.User = User;
        log = new Login(User, Pass);
        log.addObserver(this);

    }

    @Override
    public void update(Observable observable, Object Settings) {
        if(log.getStatus() == true){
            if(sett == true) {
                String[] db = set.getSettings();
                Bus = (db[0]);
                News = (db[1]);
                Weather = (db[2]);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        LoginPresenter.DoneLoading();
                        LoginPresenter.SuccessfulLogin(User, Bus, Weather, News);
                    }
                }, 2000); // 3000 milliseconds delay
            }
            else{
                sett = true;
                UpdateSettings();
            }
        }
        else{
            LoginPresenter.UnsuccessfulLogin();
            LoginPresenter.DoneLoading();
        }
    }

    public void UpdateSettings(){
        set = new Settings(User);
        set.addObserver(this);
    }
}
