package postApp.DataHandlers.Authentication;

import java.util.Observable;
import java.util.Observer;

import postApp.Presenters.AuthenticationPresenters.RegisterPresenter;

/**
 * This class handles the logic for the RegisterView
 */

public class RegisterInteractor implements Observer {

    private RegisterPresenter RegisterPresenter;
    private Registration reg;
    /**
     * Constructor for class
     *
     * @param RegisterPresenter is passed as a presenter
     */
    public RegisterInteractor(RegisterPresenter RegisterPresenter) {
        this.RegisterPresenter = RegisterPresenter;
    }

    /**
     * method that is called when registring, checks if its a email, the password is bigger then 5 letter,
     * and if the secret question is not emty
     *
     * @param User   Username
     * @param Pass   Password
     * @param Secret Secret answer to question
     */
    public void OnRegister(String User, String Pass, String Secret) {
        if (isEmailValid(User)) {
            if(Pass.length() > 5) {
                if(Secret.length() > 0) {
                    RegisterPresenter.loading();
                    reg = new Registration(User.toLowerCase(), Pass, Secret);
                    reg.addObserver(this);
                }
                else{
                    RegisterPresenter.NotCorrect("Secret answer is empty");
                }
            }
            else{
                RegisterPresenter.NotCorrect("Password must be atleast 6 characters");
            }
        } else {
            RegisterPresenter.NotCorrect("Username must be a valid Email");
        }
    }

    /**
     * Function that checks if its a email
     *
     * @param email the Charsequence that needs to be checked
     * @return true or false
     */
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * This class is a observable and on update we check if the username is in
     * Use or not. Outcomes call the presenterclasses methods
     *
     * @param observable the observable
     * @param data the data
     */
    @Override
    public void update(Observable observable, Object data) {
        //if we return that the username is not in use we switch to login class since we know the account making was succesfull
        if (!reg.getInUse()) {
            RegisterPresenter.DoneLoading();
            RegisterPresenter.SuccessfulRegister();
        }
        //a alertdialog displaying it is already chosen
        else {
            RegisterPresenter.DoneLoading();
            RegisterPresenter.UnSuccessfulRegister();
        }
    }
}
