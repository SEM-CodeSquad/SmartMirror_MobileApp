package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.ActivitiesView.AuthenticationView.LoginActivity;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;
import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class ResetPasswordInteractor {


    ResetPasswordPresenter ResetPasswordPresenter;


    public ResetPasswordInteractor(ResetPasswordPresenter ResetPasswordPresenter){
        this.ResetPasswordPresenter = ResetPasswordPresenter;
    }

    public void CheckPasswords(String user, String pass, String confpass){
        if(pass.equals(confpass)){
            OnReset(user, pass);
        }
        else{
            ResetPasswordPresenter.PasswordNoMatch();
        }
    }
    public void OnReset(String User, String Pass){
        ResetPassword reg = new ResetPassword(User, Pass);
        if(reg.getPasswordResetStatus()){
            ResetPasswordPresenter.PasswordReset();
        }
        else{

        }
    }
}
