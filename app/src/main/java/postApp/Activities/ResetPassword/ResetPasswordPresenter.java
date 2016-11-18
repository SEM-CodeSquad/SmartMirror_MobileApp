package postApp.Activities.ResetPassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.Activities.Login.LoginActivity;
import postApp.DataHandlers.Network.DataBase.ResetPassword;

/**
 * Created by adinH on 2016-11-18.
 */

public class ResetPasswordPresenter {

    private ResetPasswordInteractor ResetPasswordInteractor;

    public ResetPasswordPresenter(ResetPasswordActivity ResetPasswordActivity) {
        this.ResetPasswordInteractor = new ResetPasswordInteractor(ResetPasswordActivity);
    }

    protected void CheckPasswords(String user, String pass, String confpass){
        ResetPasswordInteractor.CheckPasswords(user, pass, confpass);
    }
    protected void OnReset(String User, String Pass){
        ResetPasswordInteractor.OnReset(User,Pass);
    }
    protected void OnCancel(){
        ResetPasswordInteractor.OnCancel();
    }
    protected void hideKeyboard(View view) {
        ResetPasswordInteractor.hideKeyboard(view);
    }
}
