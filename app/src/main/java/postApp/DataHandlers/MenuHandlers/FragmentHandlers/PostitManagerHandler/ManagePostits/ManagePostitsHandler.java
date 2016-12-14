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

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import java.util.Observable;
import java.util.Observer;
import android.os.Handler;
import postApp.DataHandlers.AppCommons.Postits.ReadPostits;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.ManagePostitsPresenter;


/**
 * Class used as a handler for the ManagePostits presenter and ManagePostits view
 */
public class ManagePostitsHandler implements Observer {

    private ReadPostits readPostits;
    private ManagePostitsPresenter ManagePostitsPresenter;

    /**
     * Sets the presenter
     * @param ManagePostitsPresenter the presenter
     */
    public ManagePostitsHandler(ManagePostitsPresenter ManagePostitsPresenter){
        this.ManagePostitsPresenter = ManagePostitsPresenter;
    }

    /**
     * Starts a ReadPostits class
     * @param user The user we want to read postits from
     */
    public void ReadPost(String user){
        readPostits = new ReadPostits(user);
        readPostits.addObserver(this);
    }

    /**
     * On update we use the presenters method for doneloading and StartLoadingPost with the postitArray we get from readpostits class
     * @param observable the observable
     * @param data the data
     */
    @Override
    public void update(Observable observable, Object data) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ManagePostitsPresenter.DoneLoading();
            }
        }, 1000); // 3000 milliseconds delay
        ManagePostitsPresenter.StartLoadingPost(readPostits.getPostitArray());
    }
}
