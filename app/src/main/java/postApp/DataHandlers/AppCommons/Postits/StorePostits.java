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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that stores the postits in the database
 */

public class StorePostits implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String postit;
    private String iD;
    private String color;
    private long timeStamp;

    /**
     * Establish a connection with the database and set all the Passed parameters
     * @param user the user
     * @param idOne The id of the postit
     * @param color The color of the postit
     * @param text The text of the postit
     * @param timeStamp The time of the postit
     */
    public StorePostits(String user,String idOne, String color, String text, long timeStamp) {
        try {
            this.user = user;
            this.iD = idOne;
            this.color = color;
            this.postit = text;
            this.timeStamp = timeStamp;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * When we get a update from the dbconnection that its done we execute a SavePostits class
     * @param observable o
     * @param o o
     */
    @Override
    public void update(Observable observable, Object o) {
            c = conn.getConn();
            SavePostits psS;
            psS = new SavePostits();
            psS.execute();
    }

    /**
     * Class that extends async task that adds a postit in the database
     */
    private class SavePostits extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{

                String query = "insert into Postits (UserID, PostID, Color, Postit, Timestamp)" + "VALUES('" + user
                        + "', '" + iD + "', '" + color + "', '" + postit +"', '" + timeStamp +"');";
                PreparedStatement psPost = c.prepareStatement(query);
                psPost.executeUpdate();
                psPost.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

    }
}
