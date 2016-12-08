package postApp.DataHandlers.Authentication;

import java.util.Observable;
import java.util.Observer;

import postApp.Presenters.AuthenticationPresenters.ResetPasswordPresenter;

/**
 * A model for the ResetPassword view
 */

public class ResetPasswordInteractor implements Observer {

    ResetPassword reset;
    ResetPasswordPresenter ResetPasswordPresenter;

    /**
     * Constructor that sets the presenter
     *
     * @param ResetPasswordPresenter
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
     *
     * @param User
     * @param Pass
     */
    public void OnReset(String User, String Pass) {
        reset = new ResetPassword(User, Pass);
        reset.addObserver(this);

    }

    /**
     * Gets a update from the resetpassword class when its notified. Calls presenter class, when outcomes are different
     *
     * @param observable
     * @param o
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
