package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.SecretQInteractor;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;

/**
 * Class used as a presenter between the SecretQ activity and interactor
 */


public class SecretQPresenter {
    private SecretQActivity SecretQActivity;
    private SecretQInteractor SecretQInteractor;

    /**
     * Constructor for the class that sets the activity and injects the presenter into the interactor
     * @param SecretQActivity the activity
     */
    public SecretQPresenter(SecretQActivity SecretQActivity) {
        this.SecretQActivity = SecretQActivity;
        this.SecretQInteractor = new SecretQInteractor(this);
    }

    /**
     * Calls the interactors onsecret method and calls loading()
     * @param User the username
     * @param Secret the password
     */
    public void OnSecret(String User, String Secret){
        SecretQInteractor.OnSecret(User,Secret);
        loading();
    }

    public void OnCancel(){
        SecretQActivity.OnCancel();
    }
    /**
     * Method that calls the activities hidekeyboard method
     * @param view the view
     */
    public void hideKeyboard(View view) {
        SecretQActivity.HideKeyboard(view);
    }

    /**
     * This method calls the activities method for correctcredentials
     * @param User the user the correct credentials were for
     */
    public void CorrectCredentials(String User){
        SecretQActivity.CorrectCredentials(User);
    }
    /**
     * Method that calls the activities wrongcredentials method
     */
    public void WrongCredentials(){
        SecretQActivity.WrongCredentials();
    }
    /**
     * Method that calls the activities loading method
     */
    public void loading(){
        SecretQActivity.Loading();
    }
    /**
     * Method that calls the activities successfulregister method
     */
    public void DoneLoading(){
        SecretQActivity.DoneLoading();
    }
}
