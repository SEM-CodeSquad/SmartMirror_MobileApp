package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.SecretQInteractor;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQPresenter {
    private postApp.ActivitiesView.AuthenticationView.SecretQActivity SecretQActivity;
    private postApp.DataHandlers.Authentication.SecretQInteractor SecretQInteractor;

    public SecretQPresenter(SecretQActivity SecretQActivity) {
        this.SecretQActivity = SecretQActivity;
        this.SecretQInteractor = new SecretQInteractor(this);
    }
    public void OnSecret(String User, String Secret){
        SecretQInteractor.OnSecret(User,Secret);
    }
    public void OnCancel(){
        SecretQActivity.OnCancel();
    }
    public void hideKeyboard(View view) {
        SecretQActivity.HideKeyboard(view);
    }
    public void CorrectCredentials(String User){
        SecretQActivity.CorrectCredentials(User);
    }
    public void WrongCredentials(){
        SecretQActivity.WrongCredentials();
    }
}
