package postApp.DataHandlers.Authentication;

import java.util.Observable;
import java.util.Observer;

import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * A model for the ResetPassword view
 */

public class ResetPasswordInteractor implements Observer {

    private ResetPassword reset;
    private ResetPasswordPresenter ResetPasswordPresenter;

    /**
     * Constructor that sets the presenter
     * @param ResetPasswordPresenter the presenter we interact with
     */
    public ResetPasswordInteractor(ResetPasswordPresenter ResetPasswordPresenter) {
        this.ResetPasswordPresenter = ResetPasswordPresenter;
    }

    /**
     * Method that checks if passwords are equal if they are they start on reset else,
     * they call the presenters method
     *
     * @param user     Username
     * @param pass     Password
     * @param confpass Confirmed password(2nd typing of same password
     */
    public void CheckPasswords(String user, String pass, String confpass) {
        if (pass.equals(confpass)) {
            OnReset(user, pass);
        } else {
            ResetPasswordPresenter.PasswordNoMatch();
        }
    }

    /**
     * Method that calls the resetpassword class
     * @param User username
     * @param Pass password
     */
    public void OnReset(String User, String Pass) {
        reset = new ResetPassword(User, Pass);
        reset.addObserver(this);

    }

    /**
     * Gets a update from the resetpassword class when its notified, it calls the presenters methods depending on outcome of getPasswordResetStatus()
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
        if (reset.getPasswordResetStatus()) {
            ResetPasswordPresenter.PasswordReset();
            ResetPasswordPresenter.DoneLoading();
        } else {
            ResetPasswordPresenter.NoPasswordReset();
            ResetPasswordPresenter.DoneLoading();
        }
    }
}
