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

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.PostitHandler;

/**
 * Class responsible as acting as a presenter between the PostitView and handler
 */

public class PostitPresenter {

    private PostitView PostitView;
    private PostitHandler PostitHandler;

    /**
     * The constructor that instantiates a postithandler with this view and mirrorid and calls the BuildColorChoice methods
     * And the setcolor method, with "yellow" the iniatial color
     * @param PostitView the view
     * @param mirrorid the mirror id
     * @param user the user
     */
    public PostitPresenter(PostitView PostitView, String mirrorid, String user){
        this.PostitView = PostitView;
        this.PostitHandler = new PostitHandler(this, mirrorid, user);
        PostitView.BuildColorChoice();
        SetColor("yellow");
    }

    /**
     * Method that calls the PostitViews method NoMirror when no mirror is chosen
     */
    public void NoMirror(){
        PostitView.NoMirror();
    }

    /**
     * Method that calls the handlers method to publish postit
     * @param text the text of the postit
     * @param topic the topic we publish to
     * @param user the username
     * @param date the date
     */
    public void PublishPostit(String text, String topic, String user, String date){
        PostitHandler.PublishPostit(text,topic, user, date);
    }

    /**
     * Method that calls the views blue click
     */
    public void BlueClick(){
        PostitView.ColorBlue();
    }
    /**
     * Method that calls the views Pink click
     */
    public void PinkClick(){
        PostitView.ColorPink();
    }
    /**
     * Method that calls the views purple click
     */
    public void PurpleClick(){
        PostitView.ColorPurple();
    }
    /**
     * Method that calls the views yellow click
     */
    public void YellowClick(){
        PostitView.ColorYellow();
    }
    /**
     * Method that calls the views green click
     */
    public void GreenClick(){
        PostitView.ColorGreen();
    }
    /**
     * Method that calls the views orange click
     */
    public void OrangeClick(){
        PostitView.ColorOrange();
    }
    /**
     * Method that calls the views showcolors method
     */
    public void ShowColors(){
        PostitView.ShowColors();
    }

    /**
     * Method to set color in the postit handler
     * @param S the color
     */
    public void SetColor(String S){
        PostitHandler.SetColor(S);
    }

    /**
     * Method that calls the views hidekeyboard method
     * @param V the view
     */
    public void HideKeyboard(View V){
        PostitView.hideKeyboard(V);
    }

    /**
     * Method that calls the views UnsuccessfulPublish method
     */
    public void NoEcho(String st){
        PostitView.UnsuccessfulPublish(st);
    }

    /**
     * Method that calls the views loading method
     */
    public void Loading() {
        PostitView.Loading();
    }

    /**
     * Method that calls the views DoneLoading function
     */
    public void DoneLoading() {
        PostitView.DoneLoading();
    }

    public void ShowCalender() {
        PostitView.ShowCalender();
    }
}
