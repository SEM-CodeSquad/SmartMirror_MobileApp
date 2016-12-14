/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

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
