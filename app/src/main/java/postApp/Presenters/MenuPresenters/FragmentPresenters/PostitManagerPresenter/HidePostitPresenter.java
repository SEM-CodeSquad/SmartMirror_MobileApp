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

package postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.HidePostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.HidePostitHandler;

/**
 * The class responsible for presenting the HidePostit view and interacting with the HidePostit Handler
 */

public class HidePostitPresenter {

    private HidePostitHandler HidePostitHandler;
    private HidePostitView HidePostitView;

    /**
     * We instantiate a HidePostitHandler with this presenter and the topic name
     * @param HidePostitView the view
     * @param topic the topic
     * @param user the user
     */
    public HidePostitPresenter(HidePostitView HidePostitView, String topic, String user){
        this.HidePostitView = HidePostitView;
        this.HidePostitHandler = new HidePostitHandler(this, topic, user);
    }

    /**
     * Call the HidePostit handlers function to filter postits
     * @param topic topic to publish to
     * @param user the user
     * @param yellow color
     * @param blue color
     * @param orange color
     * @param pink color
     * @param green color
     * @param purple color
     */
    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple){
        HidePostitHandler.FilterPost(topic, user, yellow, blue, orange, pink, green, purple);
    }

    /**
     * Call the views function for no mirror chosen
     */
    public void NoMirror(){
        HidePostitView.NoMirrorChosen();
    }

    /**
     * Call the views function for the Loading screen
     */
    public void Loading() {
        HidePostitView.Loading();
    }

    /**
     * Call the views function when we receive no echo
     */
    public void NoEcho(){
        HidePostitView.UnsuccessfulPublish();
    }

    /**
     * Call the views function when we are done loading
     */
    public void DoneLoading() {
        HidePostitView.DoneLoading();
    }

}
