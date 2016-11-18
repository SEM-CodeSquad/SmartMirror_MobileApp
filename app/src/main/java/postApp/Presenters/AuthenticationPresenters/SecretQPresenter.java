package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Pairing.SecretQInteractor;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQPresenter {
    private postApp.ActivitiesView.AuthenticationView.SecretQActivity SecretQActivity;
    private postApp.DataHandlers.Pairing.SecretQInteractor SecretQInteractor;

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
