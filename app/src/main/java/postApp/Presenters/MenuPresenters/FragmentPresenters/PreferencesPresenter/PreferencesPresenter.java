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

package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.PreferencesView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.PreferencesHandler;

/**
 * Class that presents the view to the handler and interacts with both of them
 */

public class PreferencesPresenter {

    private PreferencesView PreferencesView;
    private PreferencesHandler PreferencesHandler;

    /**
     * Constructor that injects this presenter into the handler and the topic
     * @param PreferencesView The view we get from Preferences
     * @param topic The topic
     * @param user The user
     */
    public PreferencesPresenter(PreferencesView PreferencesView, String topic, String user){
        this.PreferencesView = PreferencesView;
        this.PreferencesHandler = new PreferencesHandler(this, topic, user);
    }

    /**
     * Called when wanting to call the handlers method for publishing preferences
     * @param topic the topic
     * @param user the user
     * @param news the news
     * @param bus the bus
     * @param weather the weather
     * @param device the device
     * @param clock the clock
     * @param calender the calender
     * @param external the external system
     */
    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String device, String clock, String calender ,String external, String ShoppingList){
        PreferencesHandler.PublishPrefs(topic, user, news, bus, weather, device, clock, calender, external, ShoppingList);
    }

    /**
     * Call the views method when publish preferences button is pressed
     */
    public void PrefBtn(){
        PreferencesView.PublishPrefs();
    }
    /**
     * Call the views method when enable all is turned to true
     */
    public void DisBtnTrue(){
        PreferencesView.DisBtnTrue();
    }
    /**
     * Call the views method when enable all is turned to false
     */
    public void DisBtnFalse(){
        PreferencesView.DisBtnFalse();
    }
    /**
     * Call the views method when when theres no mirror chosen
     */
    public void NoMirror(){
        PreferencesView.NoMirrorChosen();
    }
    /**
     * Call the views method when we want to show a loading bar
     */
    public void Loading() {
        PreferencesView.Loading();
    }
    /**
     * Call the views method when its a unsuccessfull publish
     */
    public void NoEcho(){
        PreferencesView.UnsuccessfulPublish();
    }
    /**
     * Call the views method when we are done loading
     */
    public void DoneLoading() {
        PreferencesView.DoneLoading();
    }
    /**
     * Call the views method to set the busswitch to false
     */
    public void BusFalse(){
        PreferencesView.BusFalse();
    }
    /**
     * Call the views method to set the shoppingswitch to false
     */
    public void ShoppingFalse(){
        PreferencesView.ShoppingFalse();
    }

}
