package postApp.Activities.Register;

import android.view.View;

/**
 * Created by adinH on 2016-11-18.
 */

public class RegisterPresenter {
    private RegisterActivity RegisterActivity;
    private RegisterInteractor RegisterInteractor;

    public RegisterPresenter(RegisterActivity RegisterActivity) {
        this.RegisterActivity = RegisterActivity;
        this.RegisterInteractor = new RegisterInteractor(RegisterActivity);
    }

    /*
Switches to navigationactivity if a user correctly logs in
 */
    protected void OnRegister(String User, String Pass, String Secret){
        RegisterInteractor.OnRegister(User,Pass, Secret);
    }
    //starts the register intent
    protected void OnCancel(){
        RegisterInteractor.OnCancel();
    }

    protected void hideKeyboard(View view) {
        RegisterInteractor.hideKeyboard(view);
    }
}
