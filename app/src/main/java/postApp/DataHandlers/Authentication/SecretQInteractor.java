package postApp.DataHandlers.Authentication;

import postApp.Presenters.AuthenticationPresenters.SecretQPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class SecretQInteractor {

    SecretQPresenter SecretQPresenter;


    public SecretQInteractor(SecretQPresenter SecretQPresenter) {
        this.SecretQPresenter = SecretQPresenter;
    }

    public void OnSecret(String User, String Secret) {
        MatchAnswer reg = new MatchAnswer(User, Secret);
        if (reg.getAnswerMatch()) {
            SecretQPresenter.CorrectCredentials(User);
        } else {
            SecretQPresenter.WrongCredentials();
        }
    }

}
