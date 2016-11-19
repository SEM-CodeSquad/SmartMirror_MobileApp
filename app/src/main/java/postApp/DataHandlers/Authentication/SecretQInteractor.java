package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.ActivitiesView.AuthenticationView.LoginActivity;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;
import postApp.ActivitiesView.AuthenticationView.SecretQActivity;
import postApp.DataHandlers.Authentication.MatchAnswer;
import postApp.Presenters.AuthenticationPresenters.SecretQPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQInteractor {

    SecretQPresenter SecretQPresenter;


    public SecretQInteractor(SecretQPresenter SecretQPresenter){
        this.SecretQPresenter = SecretQPresenter;
    }
    public void OnSecret(String User, String Secret){
        MatchAnswer reg = new MatchAnswer(User, Secret);
        if(reg.getAnswerMatch()){
            SecretQPresenter.CorrectCredentials(User);
        }
        else{
            SecretQPresenter.WrongCredentials();
        }
    }

}
