package postApp.DataHandlers.Authentication;

import java.util.Observable;
import java.util.Observer;

import postApp.Presenters.AuthenticationPresenters.SecretQPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQInteractor implements Observer {

    SecretQPresenter SecretQPresenter;
    MatchAnswer matchAnswer;
    private String user;

    public SecretQInteractor(SecretQPresenter SecretQPresenter) {
        this.SecretQPresenter = SecretQPresenter;
    }

    public void OnSecret(String User, String Secret) {
        this.user = User;
        matchAnswer = new MatchAnswer(User, Secret);
        matchAnswer.addObserver(this);
    }

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
