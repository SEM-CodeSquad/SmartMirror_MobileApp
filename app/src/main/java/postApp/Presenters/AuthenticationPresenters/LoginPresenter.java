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
 * Class that is used as a presenter that controls the View of login and handles the logic
 * On the interactor
 */

public class LoginPresenter {
    private LoginInteractor loginInteractor;
    private LoginActivity LoginActivity;

    /**
     * Constructor
     * @param LoginActivity when instansiated pass the LoginActivity it presents.
     */
    public LoginPresenter(LoginActivity LoginActivity) {
        this.LoginActivity = LoginActivity;
        this.loginInteractor = new LoginInteractor(this);
    }

    /**
     * Method that calls the logic in the logininteractor which is to login
     * @param User The username we want to try the database with
     * @param Pass The password we want to try the database with
     */
    public void OnLogin(String User, String Pass){
        Loading();
        loginInteractor.OnLogin(User,Pass);
    }

    /**
     * Calls the views DoneLoading
     */
    public void DoneLoading(){
        LoginActivity.DoneLoading();
    }

    /**
     * Calls the views Loading progressbar
     */
    public void Loading(){
        LoginActivity.Loading();
    }

    /**
     * Calls the activities onregister function
     */
    public void onRegister(){
        LoginActivity.OnRegister();
    }

    /**
     * Call the activities onforgotten function
     */
    public void onForgotten(){
        LoginActivity.onForgotten();
    }

    /**
     * Calls the activities hidekeyboard function
     * @param view The view we want to hide the keyboard from
     */
    public void hideKeyboard(View view) {
        LoginActivity.HideKeyboard(view);
    }

    /**
     * When sucussesfully login in with the database call this method
     * @param User User that we used to fetch settings
     * @param bus Bus that we fetched
     * @param weather Bus that we fetched
     * @param news Bus that we fetched
     */
    public void SuccessfulLogin(String User, String bus, String weather, String news){
        LoginActivity.SuccessfulLogin(User, bus, weather , news);
    }

    /**
     * Just calls the unsuccessful login method
     */
    public void UnsuccessfulLogin(){
        LoginActivity.UnsuccessfulLogin();
    }
}
