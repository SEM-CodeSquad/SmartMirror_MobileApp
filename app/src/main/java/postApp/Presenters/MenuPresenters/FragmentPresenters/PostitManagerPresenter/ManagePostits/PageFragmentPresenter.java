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

import android.view.View;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.PageFragment;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits.PageFragmentHandler;

/**
 * The presenter for the pagefragment and the interactor with the pagefragmenthandler
 */

public class PageFragmentPresenter {
    private PageFragment PageFragment;
    private PageFragmentHandler PageFragmentHandler;

    /**
     * We instatiate the PageFragmentHandler and injects this presenter. We build two alertdialogs and we set the color of the postits
     * @param PageFragment The view passed
     * @param color The color to set
     */
    public PageFragmentPresenter(PageFragment PageFragment, String color, String topic, String user){
        this.PageFragment = PageFragment;
        this.PageFragmentHandler = new PageFragmentHandler(this, topic, user);
        PageFragment.buildDelete();
        PageFragment.buildEdit();
        SetColor(color);
    }

    /**
     * Call the view to show a message
     * @param S The message
     */
    public void loading(String S){
        PageFragment.Loading(S);
    }

    /**
     * Method for calling the views NoMirror function
     */
    public void NoMirror(){
        PageFragment.NoMirror();
    }

    /**
     * Tell the handler to delete a postit
     * @param topic Pass the topic
     * @param ID PAss the ID
     */
    public void DeletePostit(String topic, String ID, String user){
        PageFragmentHandler.DeletePostit(topic, ID, user);
    }

    /**
     * Tell the handler to edit a Postit
     * @param topic The topic we are publishing to
     * @param id The Id of the postit we want to edit
     * @param Text The text we want to change
     */
    public void EditPostit(String topic, String id, String Text, String user){
        PageFragmentHandler.EditPostit(topic, id, Text, user);
    }

    /**
     * Method to call the views Color function
     */
    public void BlueClick(){
        PageFragment.ColorBlue();
    }
    /**
     * Method to call the views Color function
     */
    public void PinkClick(){
        PageFragment.ColorPink();
    }
    /**
     * Method to call the views Color function
     */
    public void PurpleClick(){
        PageFragment.ColorPurple();
    }
    /**
     * Method to call the views Color function
     */
    public void YellowClick(){
        PageFragment.ColorYellow();
    }
    /**
     * Method to call the views Color function
     */
    public void GreenClick(){
        PageFragment.ColorGreen();
    }
    /**
     * Method to call the views Color function
     */
    public void OrangeClick(){
        PageFragment.ColorOrange();
    }
    /**
     * Call the handlers function to setColor
     * @param S the string with the color name
     */
    private void SetColor(String S){
        PageFragmentHandler.SetColor(S);

    }

    /**
     * Call the views function to hide the keyboard
     * @param V The view passed
     */
    public void HideKeyboard(View V){
        PageFragment.hideKeyboard(V);
    }

    /**
     * Call the views function to reloadscreen
     */
    public void ReloadScreen(){
        PageFragment.ReloadScreen();
    }

    /**
     * Call the views for function for no echo
     * @param s the string to show
     */
    public void NoEcho(String s){
        PageFragment.UnsuccessfulPublish(s);
    }

    /**
     * Call the views DoneLoading function
     */
    public void DoneLoading() {
        PageFragment.DoneLoading();
    }

    public void showEdit() {
        PageFragment.ShowEdit();
    }
    public void showDelete() {
        PageFragment.ShowDelete();
    }
}
