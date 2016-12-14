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

package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that fetches all postits from the database
 */

public class ReadPostits extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private JSONArray postArray;

    /**
     * Starts a dn connection
     * @param user the user that wants to read the postits
     */
    public ReadPostits(String user) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = user;
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * When we get a update from the db connection we execute the fetchpostits class
     * @param observable o
     * @param o o
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        fetchPostits fetch;
        fetch = new fetchPostits();
        fetch.execute();
    }

    /**
     * Class that extends asynctask that gets all the postits for a specific user and puts them in a jsonobject
     */
    private class fetchPostits extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                postArray = new JSONArray();
                String query = "select PostID, Color, Postit, Timestamp from Postits where UserID=?";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.setString(1, user);
                ResultSet rs = pstSettings.executeQuery();

                int count = 0;
                while (rs.next()) {
                    JSONObject postitJson = new JSONObject();
                    String postID = rs.getString("PostID");
                    postitJson.put("PostitID", postID);
                    String color = rs.getString("Color");
                    postitJson.put("Color", color);
                    String text = rs.getString("Postit");
                    postitJson.put("Text", text);
                    String timeStamp = rs.getString("Timestamp");
                    postitJson.put("Timestamp", timeStamp);
                    postArray.add(count, postitJson);
                    count++;


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Notify observers that async task is done onPostExecute
         * @param unused u
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    /**
     * Notifies the observers
     */
    private void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * @return a postit array with all the postits
     */
    public JSONArray getPostitArray(){
        return postArray;
    }

}
