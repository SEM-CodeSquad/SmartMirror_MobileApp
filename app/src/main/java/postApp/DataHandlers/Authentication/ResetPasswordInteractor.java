package postApp.DataHandlers.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.ActivitiesView.LoginActivity;
import postApp.ActivitiesView.ResetPasswordActivity;
import postApp.DataHandlers.Authentication.ResetPassword;

/**
 * Created by adinH on 2016-11-18.
 */

public class ResetPasswordInteractor {


    postApp.ActivitiesView.ResetPasswordActivity ResetPasswordActivity;


    public ResetPasswordInteractor(ResetPasswordActivity ResetPasswordActivity){
        this.ResetPasswordActivity = ResetPasswordActivity;
    }

    public void CheckPasswords(String user, String pass, String confpass){
        if(pass.equals(confpass)){
            OnReset(user, pass);
        }
        else{
            new AlertDialog.Builder(ResetPasswordActivity)
                    .setTitle("Passwords not matching")
                    .setMessage("Please type again the passwords")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }
    public void OnReset(String User, String Pass){
        ResetPassword reg = new ResetPassword(User, Pass);
        if(reg.getPasswordResetStatus()){
            Intent intent = new Intent(ResetPasswordActivity, LoginActivity.class);
            ResetPasswordActivity.startActivity(intent);
        }
        else{
            new AlertDialog.Builder(ResetPasswordActivity)
                    .setMessage("Some error")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();

        }
    }
    public void OnCancel(){
        Intent intent = new Intent(ResetPasswordActivity, LoginActivity.class);
        ResetPasswordActivity.startActivity(intent);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)ResetPasswordActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
