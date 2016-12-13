package postApp.Presenters.AuthenticationPresenters;


import android.view.View;
import postApp.DataHandlers.Authentication.LoginInteractor;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;

/**
 * Class that is used as a presenter between the view and handler
 */

public class LoginPresenter {
    private LoginInteractor loginInteractor;
    private LoginActivity LoginActivity;

    /**
     * Constructor that instantiates the loging interactor and sets login activity
     * @param LoginActivity the activity
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
    public void SuccessfulLogin(String User, String bus, String busid, String weather, String news){
        LoginActivity.SuccessfulLogin(User, bus, busid, weather , news);
    }

    /**
     * Just calls the unsuccessful login method
     */
    public void UnsuccessfulLogin(){
        LoginActivity.UnsuccessfulLogin();
    }
}
