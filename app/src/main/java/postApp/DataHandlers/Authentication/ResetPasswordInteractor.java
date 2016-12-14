
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
            if(pass.length() > 5){
                ResetPasswordPresenter.loading();
                OnReset(user, pass);
            }
            else{
                ResetPasswordPresenter.tooShortPassword();
            }
        } else {
            ResetPasswordPresenter.PasswordNoMatch();
        }
    }

    /**
     * Method that calls the resetpassword class
     * @param User username
     * @param Pass password
     */
    private void OnReset(String User, String Pass) {
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
