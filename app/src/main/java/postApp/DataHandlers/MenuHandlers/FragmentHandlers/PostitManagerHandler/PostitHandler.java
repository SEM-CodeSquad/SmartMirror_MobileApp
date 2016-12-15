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

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.os.Handler;

import com.vdurmont.emoji.EmojiParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.AppCommons.Pairing.UUIDGenerator;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.AppCommons.Postits.StorePostits;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;

/**
 * Class used as a Handler for the Postit view and presenter. Handles the logic part of the View.
 */

public class PostitHandler implements Observer {
    private String color;
    private PostitPresenter PostitPresenter;
    private String text;
    private String idOne;
    private Echo echo;
    private String user;
    private long timestamp;
    private boolean stored = false;

    /**
     * We set the postitpresenter, set the topic we are posting too. Instantiate a Echo Class that we observe
     * @param PostitPresenter the presenter
     * @param mirror the mirror id
     */
    public PostitHandler(PostitPresenter PostitPresenter, String mirror, String user) {
        this.PostitPresenter = PostitPresenter;
        String echotopic = "dit029/SmartMirror/" + mirror + "/echo";
        echo = new Echo(echotopic, user);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * We set color with this method
     * @param color the color
     */
    public void SetColor(String color) {
        this.color = color;
    }

    /**
     * We publish postits witht his method.
     * @param text the text of the postit
     * @param topic the topic we are posting to
     * @param user the user
     * @param date the date
     */
    public void PublishPostit(String text, String topic, String user, String date) {
        //Check if a mirror is paired
        if (!topic.equals("No mirror chosen")) {
            // set a loading screen
            PostitPresenter.Loading();
            //await a echo
            AwaitEcho();
            this.user = user;
            this.text = text;
            Locale loc = new Locale("GERMANY");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", loc);
            //we make a date here to UNIX time, if its standard
            if (date.equals("standard")) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, 5); // Adding 5 days
                this.timestamp = c.getTimeInMillis() / 1000;
            }
            //else the date is chosen already by the user and
            else {
                Date dateTemp;
                try {
                    dateTemp = sdf.parse(date);
                    this.timestamp = (dateTemp.getTime()) / 1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //A random UUID for the postit
            UUIDGenerator uuid = new UUIDGenerator();
            this.idOne = uuid.getUUID();
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "postit", text, color, Long.toString(timestamp), idOne, user).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // if theres no mirror we display that through the presenter
        else {
            PostitPresenter.NoMirror();
        }
    }

    /**
     * Method used for awaiting a echo. if stored is true after 2 seconds we know we got a echo
     */
    private void AwaitEcho() {
        echo.connect();
        echo.addObserver(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (stored) {
                    PostitPresenter.DoneLoading();
                    stored = false;
                } else {
                    PostitPresenter.NoEcho();
                    PostitPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    private void Disc(){
        echo.disconnect();
    }
    /**
     * Method for storing postits in the database
     */
    private void StorePost() {
        String string = EmojiParser.parseToAliases(text);
        new StorePostits(user, idOne, color, string, timestamp);
    }

    /**
     * Just a observable that waits for a notification from the echo class. If it gets a update, we store a postit and sets stored to true
     * @param observable The observable
     * @param data THe object
     */
    @Override
    public void update(Observable observable, Object data) {
        stored = true;
        StorePost();
        echo.deleteObserver(this);

    }
}
