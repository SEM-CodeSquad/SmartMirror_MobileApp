package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.RegisterInteractor;
import postApp.ActivitiesView.AuthenticationView.RegisterActivity;

/**
 * Class used as a presenter between the register activity and interactor
 */

public class RegisterPresenter {
    private RegisterActivity RegisterActivity;
    private RegisterInteractor RegisterInteractor;

    /**
     * Constructor for the class that sets the activity and injects the presenter into the interactor
     * @param RegisterActivity The activity
    */
    public RegisterPresenter(RegisterActivity RegisterActivity) {
        this.RegisterActivity = RegisterActivity;
        this.RegisterInteractor = new RegisterInteractor(this);
    }

    /**
     * Calls the interactors on register method
     * @param User the username
     * @param Pass the password
     * @param Secret the secret question answer
     */
    public void OnRegister(String User, String Pass, String Secret){
        RegisterInteractor.OnRegister(User, Pass, Secret);
    }

    /**
     * Method that calls the activities oncancel method
     */
    public void OnCancel(){
        RegisterActivity.onCancel();
    }

    /**
     * Method that calls the activities loading method
     */
    public void loading(){
        RegisterActivity.Loading();
    }
    /**
     * Method that calls the activities doneloading method
     */
    public void DoneLoading(){
        RegisterActivity.DoneLoading();
    }
    /**
     * Method that calls the activities successfulregister method
     */
    public void SuccessfulRegister(){
        RegisterActivity.SuccessfulRegister();
    }
    /**
     * Method that calls the activities UnSuccessfulRegister method
     */
    public void UnSuccessfulRegister(){
        RegisterActivity.UnsuccessfulRegister();
    }
    /**
     * Method that calls the activities hidekeyboard method
     * @param view the view
     */
    public void hideKeyboard(View view) {
        RegisterActivity.HideKeyboard(view);
    }

    /**
     * Method that calls the activies notcorrect method
     * @param s A string to display in the activity
     */
    public void NotCorrect(String s){
        RegisterActivity.NotCorrect(s);
    }

}
