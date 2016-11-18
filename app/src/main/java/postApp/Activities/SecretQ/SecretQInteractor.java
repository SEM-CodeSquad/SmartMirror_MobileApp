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

public class SecretQInteractor {

    SecretQActivity SecretQActivity;


    public SecretQInteractor(SecretQActivity SecretQActivity){
        this.SecretQActivity = SecretQActivity;
    }
    protected void OnSecret(String User, String Secret){
        MatchAnswer reg = new MatchAnswer(User, Secret);
        if(reg.getAnswerMatch()){
            Intent intent = new Intent(SecretQActivity, ResetPasswordActivity.class);
            intent.putExtra("user", User);
            SecretQActivity.startActivity(intent);
        }
        else{
            new AlertDialog.Builder(SecretQActivity)
                    .setMessage("Wrong Username or Question Answer")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();

        }
    }
    protected void OnCancel(){
        Intent intent = new Intent(SecretQActivity, LoginActivity.class);
        SecretQActivity.startActivity(intent);
    }
    protected void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)SecretQActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
