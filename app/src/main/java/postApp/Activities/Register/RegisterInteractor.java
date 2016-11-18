package postApp.Activities.Register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import postApp.Activities.Login.LoginActivity;
import postApp.DataHandlers.Network.DataBase.Registration;

/**
 * Created by adinH on 2016-11-18.
 */

public class RegisterInteractor {

    RegisterActivity RegisterActivity;


    public RegisterInteractor(RegisterActivity RegisterActivity){
        this.RegisterActivity = RegisterActivity;
    }
    //on register that takes a user, pass and a secret answer
    protected void OnRegister(String User, String Pass, String Secret){
        Registration reg = new Registration(User, Pass, Secret);
        //if we return that the username is not in use we switch to login class since we know the account making was succesfull
        if(reg.getInUse() == false){
            Intent intent = new Intent(RegisterActivity, LoginActivity.class);
            RegisterActivity.startActivity(intent);
        }
        //a alertdialog displaying it is already chosen
        else{
            new AlertDialog.Builder(RegisterActivity)
                    .setMessage("Username already chosen")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();

        }
    }
    //canceling should switchback to loginactivity
    protected void OnCancel(){
        Intent intent = new Intent(RegisterActivity, LoginActivity.class);
        RegisterActivity.startActivity(intent);
    }
    protected void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)RegisterActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
