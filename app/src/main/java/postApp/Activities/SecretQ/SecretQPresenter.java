package postApp.Activities.SecretQ;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.Activities.Login.LoginActivity;
import postApp.Activities.ResetPassword.ResetPasswordActivity;
import postApp.DataHandlers.Network.DataBase.MatchAnswer;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQPresenter {
    private SecretQActivity SecretQActivity;
    private SecretQInteractor SecretQInteractor;

    public SecretQPresenter(SecretQActivity SecretQActivity) {
        this.SecretQActivity = SecretQActivity;
        this.SecretQInteractor = new SecretQInteractor(SecretQActivity);
    }

    protected void OnSecret(String User, String Secret){
        SecretQInteractor.OnSecret(User,Secret);
    }
    protected void OnCancel(){
        SecretQInteractor.OnCancel();
    }
    protected void hideKeyboard(View view) {
        SecretQInteractor.hideKeyboard(view);
    }
}
