package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.ResetPasswordInteractor;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class ResetPasswordPresenter {

    ResetPasswordInteractor ResetPasswordInteractor;
    ResetPasswordActivity ResetPasswordActivity;
    public ResetPasswordPresenter(ResetPasswordActivity ResetPasswordActivity) {
        this.ResetPasswordActivity = ResetPasswordActivity;
        this.ResetPasswordInteractor = new ResetPasswordInteractor(this);
    }

    public void CheckPasswords(String user, String pass, String confpass){
        ResetPasswordInteractor.CheckPasswords(user, pass, confpass);
    }
    public void PasswordReset(){
        ResetPasswordActivity.PasswordReset();
    }
    public void PasswordNoMatch() {
        ResetPasswordActivity.PasswordNoMatch();
    }
    public void OnCancel(){
        ResetPasswordActivity.OnCancel();
    }
    public void hideKeyboard(View view) {
        ResetPasswordActivity.HideKeyboard(view);
    }
}
