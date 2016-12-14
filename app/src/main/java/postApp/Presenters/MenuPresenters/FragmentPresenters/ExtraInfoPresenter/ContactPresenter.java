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

package postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter;

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.ContactView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExtraInfoHandler.ContactHandler;

/**
 * Class for interacting between the contact view and the contact handler
 */

public class ContactPresenter {

    private ContactView ContactView;
    private ContactHandler ContactHandler;

    /**
     * The constructor that sets the view and instantiates a handler with this clas.
     * @param ContactView the view
     */
    public ContactPresenter(ContactView ContactView){
        this.ContactView = ContactView;
        ContactHandler = new ContactHandler(this);
    }

    /**
     * Calls the views method SentEmail()
     */
    public void SentEmail() {
        ContactView.SentEmail();
    }

    /**
     * Calls the views method Loading()
     */
    public void Loading() {
        ContactView.ShowProgress();
    }


    /**
     * Calls the handlers method SendEmail()
     * @param recep The recepient of the email
     * @param Subject The subject of the email
     * @param Text The text of the email
     */
    public void SendEmail(String recep, String Subject, String Text) {
        ContactHandler.SendEmail(recep, Subject, Text);
    }

    /**
     * Calls the views method NotCorrect
     * @param S the string S that should be shown
     */
    public void NotCorrect(String S) {
        ContactView.NotCorrect(S);
    }

    /**
     * Calls the view method for hiding keyboard
     * @param v the view
     */
    public void hideKeyboard(View v) {
        ContactView.HideKeyboard(v);
    }
}
