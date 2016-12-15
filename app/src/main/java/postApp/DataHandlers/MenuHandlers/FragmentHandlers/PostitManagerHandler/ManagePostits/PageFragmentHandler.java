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

import android.os.Handler;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.AppCommons.Postits.EditPostit;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.AppCommons.Postits.DeletePostit;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.PageFragmentPresenter;

/**
 * This is a handler for the pagefragment
 */

public class PageFragmentHandler implements Observer {
    private PageFragmentPresenter PageFragmentPresenter;
    private String idOne;
    private String text;
    private Boolean echoed = false;
    private Echo echo;
    private String editordelete;
    private String message;

    /**
     * Constructor for the handler that injects the presenter and start a echo with a topic
     *
     * @param PageFragmentPresenter the presenter
     */
    public PageFragmentHandler(PageFragmentPresenter PageFragmentPresenter, String topic, String user) {
        this.PageFragmentPresenter = PageFragmentPresenter;
        String topic123 = "dit029/SmartMirror/" + topic + "/echo";
        echo = new Echo(topic123, user);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * Call the presenter to change different colors
     * @param color according to which color is in this string we change
     */
    public void SetColor(String color) {
        switch (color) {
            case "yellow":
                PageFragmentPresenter.YellowClick();
                break;
            case "purple":
                PageFragmentPresenter.PurpleClick();
                break;
            case "pink":
                PageFragmentPresenter.PinkClick();
                break;
            case "blue":
                PageFragmentPresenter.BlueClick();
                break;
            case "green":
                PageFragmentPresenter.GreenClick();
                break;
            case "orange":
                PageFragmentPresenter.OrangeClick();
                break;
        }
    }

    /**
     * Method That publishes a edit to server
     *
     * @param topic the topic to publish to
     * @param id    The id of the postit
     * @param Text  The text of postit to change
     */
    public void EditPostit(String topic, String id, String Text, String user) {
        if (!topic.equals("No mirror chosen")) {
            message = "Could not edit postit";
            PageFragmentPresenter.loading("Editing postit");
            editordelete = "edit";
            AwaitEcho();
            idOne = id;
            text = Text;
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "post-it action", id, "Modify", Text, user).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }

    /**
     * Method That publishes a delete to server
     *  @param topic the topic to publish to
     * @param UUID  the uuid of the postit
     * @param user the user
     */
    public void DeletePostit(String topic, String UUID, String user) {
        if (!topic.equals("No mirror chosen")) {
            AwaitEcho();
            message = "Could not delete postit";
            PageFragmentPresenter.loading("Deleting postit");
            editordelete = "delete";
            idOne = UUID;
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "post-it action", UUID, "Delete", "none", user).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }

    /**
     * method for removing post
     */
    private void RemovePost() {
        new DeletePostit(idOne);
    }

    /**
     * Method for editing post
     */
    private void EditPost() {
        new EditPostit(text, idOne);
    }

    /**
     * Method used for awaiting a echo. if echoed is true after 2 seconds we know we got a echo else its false
     */
    private void AwaitEcho() {
        echo.connect();
        echo.addObserver(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    PageFragmentPresenter.DoneLoading();
                    PageFragmentPresenter.ReloadScreen();
                    echoed = false;
                } else {
                    PageFragmentPresenter.NoEcho(message);
                    PageFragmentPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    private void Disc() {
        echo.disconnect();
    }

    /**
     * Method that fetches the observers update and if we first receive a echo we start a edit or delete based on function
     * If the data is instanceof editpost we check if getedited status, if its true then we know everything went well
     * If the data is instanceof deletepostit we check if getedited status, if its true then we know everything went well
     * @param observable the observable
     * @param data the data object
     */
    @Override
    public void update(Observable observable, Object data) {
            if(editordelete.equals("edit")){
                echoed = true;
                EditPost();
                echo.deleteObserver(this);
            }
            else if(editordelete.equals("delete")){
                echoed = true;
                RemovePost();
                echo.deleteObserver(this);
            }
    }
}
