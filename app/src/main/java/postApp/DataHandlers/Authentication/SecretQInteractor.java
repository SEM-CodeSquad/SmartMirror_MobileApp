package postApp.DataHandlers.Authentication;

import java.util.Observable;
import java.util.Observer;

import postApp.Presenters.AuthenticationPresenters.SecretQPresenter;

/**
 * Class used for the secretQ fragment and presenter
 */

public class SecretQInteractor implements Observer {

    private SecretQPresenter SecretQPresenter;
    private MatchAnswer matchAnswer;
    private String user;

    /**
     * Sets the SecretQPresenter
     * @param SecretQPresenter the presenter
     */
    public SecretQInteractor(SecretQPresenter SecretQPresenter) {
        this.SecretQPresenter = SecretQPresenter;
    }

    /**
     * Method that start a matchanswer class and adds a observer
     * @param User the username
     * @param Secret the secret answer
     */
    public void OnSecret(String User, String Secret) {
        this.user = User;
        matchAnswer = new MatchAnswer(User, Secret);
        matchAnswer.addObserver(this);
    }

    /**
     * Depending on outcomes of getAnswerMatch of the matchanswer class, we call methods in the presenter.
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
        if (matchAnswer.getAnswerMatch()) {
            SecretQPresenter.CorrectCredentials(user);
            SecretQPresenter.DoneLoading();
        } else {
            SecretQPresenter.WrongCredentials();
            SecretQPresenter.DoneLoading();
        }
    }
}
