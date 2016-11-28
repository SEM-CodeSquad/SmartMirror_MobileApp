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
import postApp.ActivitiesView.AuthenticationView.RegisterActivity;
import postApp.Presenters.AuthenticationPresenters.RegisterPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class RegisterInteractor implements Observer {

    RegisterPresenter RegisterPresenter;
    Registration reg;

    public RegisterInteractor(RegisterPresenter RegisterPresenter){
        this.RegisterPresenter = RegisterPresenter;
    }
    //on register that takes a user, pass and a secret answer
    public void OnRegister(String User, String Pass, String Secret){
        if(isEmailValid(User)) {
            reg = new Registration(User.toLowerCase(), Pass, Secret);
        }
        else{
            RegisterPresenter.NotEmail();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void update(Observable observable, Object data) {
        //if we return that the username is not in use we switch to login class since we know the account making was succesfull
        if (reg.getInUse() == false) {
            RegisterPresenter.SuccessfulRegister();
        }
        //a alertdialog displaying it is already chosen
        else {
            RegisterPresenter.UnSuccessfulRegister();
        }
    }
}
