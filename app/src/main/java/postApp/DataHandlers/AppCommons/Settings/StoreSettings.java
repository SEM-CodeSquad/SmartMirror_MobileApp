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

package postApp.DataHandlers.AppCommons.Settings;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class for storing settings in the db
 */

public class StoreSettings implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String news;
    private String bus;
    private String weather;

    /**
     * Constructor for storesettings that starts a db connection
     * @param User The user
     * @param news The news
     * @param bus The bus
     * @param weather The weather
     */
    public StoreSettings(String User, String news, String bus, String weather) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.news = news;
            this.bus = bus;
            this.weather = weather;
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * When we get a update from the databaseconnection class we execute storesettings
     * @param observable The Observable
     * @param o The object
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        storesettings set;
        set = new storesettings();
        set.execute();
    }

    /**
     * Async task that stores settings and sets sett to either true or false
     */
    private class storesettings extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {
            try {
                String query = "UPDATE Users SET BusConfig = '" + bus + "', WeatherConfig = '" + weather + "', `NewsFeedConfig` = '" + news + "' WHERE `UserID` = '" + user +"'";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.executeUpdate();
                pstSettings.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

    }
}

