package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Observable;
import java.util.Observer;

import postApp.ActivitiesView.AuthenticationView.LoginActivity;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;
import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * A model for the ResetPassword view
 */

public class ResetPasswordInteractor implements Observer {

    ResetPassword  reg;
    ResetPasswordPresenter ResetPasswordPresenter;

    /**
     * Constructor that sets the presenter
     * @param ResetPasswordPresenter
     */
    public ResetPasswordInteractor(ResetPasswordPresenter ResetPasswordPresenter){
        this.ResetPasswordPresenter = ResetPasswordPresenter;
    }

    /**
     * Method that checks if passwords are equal if they are they start on reset else,
     * they call the presenters method
     * @param user Username
     * @param pass Password
     * @param confpass Confirmed password(2nd typing of same password
     */
    public void CheckPasswords(String user, String pass, String confpass){
        if(pass.equals(confpass)){
            OnReset(user, pass);
        }
        else{
            ResetPasswordPresenter.PasswordNoMatch();
        }
    }

    /**
     * Method that calls the resetpassword class
     * @param User
     * @param Pass
     */
    public void OnReset(String User, String Pass){
        reg = new ResetPassword(User, Pass);
        reg.addObserver(this);

    }

    /**
     * Gets a update from the resetpassword class when its notified. Calls presenter class, when outcomes are different
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if(reg.getPasswordResetStatus()){
            ResetPasswordPresenter.PasswordReset();
        }
        else{
            ResetPasswordPresenter.NoPasswordReset();
        }
    }
}
