package postApp.DataHandlers.Authentication;

import android.os.Handler;

import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.AppCommons.Settings.FetchSettings;
import postApp.Presenters.AuthenticationPresenters.LoginPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class LoginInteractor implements Observer {
    private LoginPresenter LoginPresenter;
    private Login log;
    private String User;
    private String Bus;
    private String BusID;
    private String News;
    private String Weather;
    private FetchSettings set;

    /**
     * Constructor for the interactor
     * @param LoginPresenter Presenter it calls methods from
     */
    public LoginInteractor(LoginPresenter LoginPresenter){
        this.LoginPresenter = LoginPresenter;
    }

    /**
     * Calls the Login db class
     * @param User User that wants to log in
     * @param Pass Password typed
     */
    public void OnLogin(String User, String Pass){
        this.User = User;
        log = new Login(User, Pass);
        log.addObserver(this);

    }

    /**
     * This method is called from the observables and we check first if its a instanceof login. If it is we check if
     * We succesfully log in, if we do we call UpdateSettings(). Else we call the presenters function Unsuccessfull login.
     * If the object is a instance of FetchSettings we get the settings and the set the bus to diffrent positions in the String array
     * And then we use methods to be done with loading
     * @param observable The observable
     * @param obj The object
     */
    @Override
    public void update(Observable observable, Object obj) {
        if (obj instanceof Login) {
            if (log.getStatus()) {
                UpdateSettings();
            } else {
                LoginPresenter.UnsuccessfulLogin();
                LoginPresenter.DoneLoading();
            }
        }
        else if (obj instanceof FetchSettings) {
            String[] db = set.getSettings();
            if(db[0].equals("No bus stop selected")){
                Bus = db[0];
                BusID = db[0];
            }
            else if(db[0].contains(":")){
                Bus = (db[0].substring(0, db[0].indexOf(":")));
                BusID = (db[0].substring(db[0].indexOf(":")+ 1, db[0].length()));
            }
            else {
                Bus = db[0];
                BusID = db[0];
            }
            News = (db[1]);
            Weather = (db[2]);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    LoginPresenter.DoneLoading();
                    LoginPresenter.SuccessfulLogin(User, Bus, BusID, Weather, News);
                }
            }, 1000); // 1000 milliseconds delay
        }
    }

    /**
     * Calls settings db class to get the new settings with a observer
     * to know when the async task is doen
     */
    public void UpdateSettings(){
        set = new FetchSettings(User);
        set.addObserver(this);
    }
}
