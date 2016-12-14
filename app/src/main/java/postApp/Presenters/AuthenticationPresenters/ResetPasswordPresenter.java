package postApp.Presenters.AuthenticationPresenters;

import android.view.View;

import postApp.DataHandlers.Authentication.ResetPasswordInteractor;
import postApp.ActivitiesView.AuthenticationView.ResetPasswordActivity;

/**
 * Class that acts as a presenter between the Resetpasswordview and interactor
 */

public class ResetPasswordPresenter {

    /**
     * Constructor that sets the views and starts a new interactor and injecting this presenter
     */
    private ResetPasswordInteractor ResetPasswordInteractor;
    private ResetPasswordActivity ResetPasswordActivity;
    public ResetPasswordPresenter(ResetPasswordActivity ResetPasswordActivity) {
        this.ResetPasswordActivity = ResetPasswordActivity;
        this.ResetPasswordInteractor = new ResetPasswordInteractor(this);
    }

    /**
     * Method that calls the interactors method for checking passwords
     * @param user the username
     * @param pass the password
     * @param confpass the confirmed password
     */
    public void CheckPasswords(String user, String pass, String confpass){
        ResetPasswordInteractor.CheckPasswords(user, pass, confpass);

    }

    /**
     * Method that calls the views method passwordreset
     */
    public void PasswordReset(){
        ResetPasswordActivity.PasswordReset();
    }

    /**
     * When passwords dont match calls the views method for no mathcing passwords
     */
    public void PasswordNoMatch() {
        ResetPasswordActivity.PasswordNoMatch();
    }

    /**
     * Calls the views method oncancel
     */
    public void OnCancel(){
        ResetPasswordActivity.OnCancel();
    }

    /**
     * Calls the views method to hide keyboard
     * @param view the view
     */
    public void hideKeyboard(View view) {
        ResetPasswordActivity.HideKeyboard(view);
    }

    /**
     * When password reset fails calls the views method NoResetPassword
     */
    public void NoPasswordReset() {
        ResetPasswordActivity.NoResetPassword();
    }

    /**
     * Calls the activities method to show a loading dialog
     */
    public void loading(){
        ResetPasswordActivity.Loading();
    }
    /**
     * Calls the activities method to show a doneloading dialog
     */
    public void DoneLoading(){
        ResetPasswordActivity.DoneLoading();
    }
    /**
     * Calls the activities method to show a too short password dialog
     */
    public void tooShortPassword() {
        ResetPasswordActivity.TooShortPassword();
    }
}
