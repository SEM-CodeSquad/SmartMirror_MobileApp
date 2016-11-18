package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.ResetPasswordInteractor;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class ResetPasswordPresenter {

    private postApp.DataHandlers.Authentication.ResetPasswordInteractor ResetPasswordInteractor;

    public ResetPasswordPresenter(ResetPasswordActivity ResetPasswordActivity) {
        this.ResetPasswordInteractor = new ResetPasswordInteractor(ResetPasswordActivity);
    }

    public void CheckPasswords(String user, String pass, String confpass){
        ResetPasswordInteractor.CheckPasswords(user, pass, confpass);
    }
    protected void OnReset(String User, String Pass){
        ResetPasswordInteractor.OnReset(User,Pass);
    }
    public void OnCancel(){
        ResetPasswordInteractor.OnCancel();
    }
    public void hideKeyboard(View view) {
        ResetPasswordInteractor.hideKeyboard(view);
    }
}
