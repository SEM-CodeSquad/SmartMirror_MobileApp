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

package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits;

import org.json.simple.JSONArray;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.ManagePostitsView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.ManagePostitsHandler;

/**
 * A class for interacting with the handler and presenting the view of managing posits
 */

public class ManagePostitsPresenter {
    private ManagePostitsView ManagePostitsView;
    private ManagePostitsHandler ManagePostitsHandler;

    /**
     * Constructor that injects the presenter into the handler and gets the view. It also starts a loading screen when instantiated
     * @param ManagePostitsView the view
     */
    public ManagePostitsPresenter(ManagePostitsView ManagePostitsView){
        this.ManagePostitsView = ManagePostitsView;
        this.ManagePostitsHandler = new ManagePostitsHandler(this);
        Loading();
    }

    /**
     * We call the handlers function the fetch the postits
     * @param User the user that we fetch for
     */
    public void FetchPost(String User){
        ManagePostitsHandler.ReadPost(User);
    }

    /**
     * Tell the view to we are done with loading
     */
    public void DoneLoading(){
        ManagePostitsView.DoneLoading();
    }

    /**
     * Tell the view to start a progressdialog showing that we are loading
     */
    public void Loading(){
        ManagePostitsView.Loading();
    }

    /**
     * Tell the view to start loading the postit
     * @param PostIts pass a array of postits
     */
    public void StartLoadingPost(JSONArray PostIts){
        ManagePostitsView.UpdateGuiPost(PostIts);
    }
}
