package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.RegisterInteractor;
import postApp.ActivitiesView.AuthenticationView.RegisterActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class RegisterPresenter {
    private postApp.ActivitiesView.AuthenticationView.RegisterActivity RegisterActivity;
    private postApp.DataHandlers.Authentication.RegisterInteractor RegisterInteractor;

    public RegisterPresenter(RegisterActivity RegisterActivity) {
        this.RegisterActivity = RegisterActivity;
        this.RegisterInteractor = new RegisterInteractor(this);
    }

    /*
Switches to navigationactivity if a user correctly logs in
 */
    public void OnRegister(String User, String Pass, String Secret){
        RegisterInteractor.OnRegister(User,Pass, Secret);
    }


    //starts the register intent
    public void OnCancel(){
        RegisterActivity.onCancel();
    }

    public void SuccessfulRegister(){
        RegisterActivity.SuccessfulRegister();
    }
    public void UnSuccessfulRegister(){
        RegisterActivity.UnsuccessfulRegister();
    }
    public void hideKeyboard(View view) {
        RegisterActivity.HideKeyboard(view);
    }
    public void NotEmail(){
        RegisterActivity.NotEmail();
    }
}
