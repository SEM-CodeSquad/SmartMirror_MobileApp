package postApp.Presenters;

import android.view.View;

import postApp.DataHandlers.Authentication.SecretQInteractor;
import postApp.ActivitiesView.SecretQActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQPresenter {
    private postApp.ActivitiesView.SecretQActivity SecretQActivity;
    private postApp.DataHandlers.Authentication.SecretQInteractor SecretQInteractor;

    public SecretQPresenter(SecretQActivity SecretQActivity) {
        this.SecretQActivity = SecretQActivity;
        this.SecretQInteractor = new SecretQInteractor(SecretQActivity);
    }

    public void OnSecret(String User, String Secret){
        SecretQInteractor.OnSecret(User,Secret);
    }
    public void OnCancel(){
        SecretQInteractor.OnCancel();
    }
    public void hideKeyboard(View view) {
        SecretQInteractor.hideKeyboard(view);
    }
}
