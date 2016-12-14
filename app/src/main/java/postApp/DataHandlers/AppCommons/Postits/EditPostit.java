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
 * Class that is used for editing a postit and communicating with the Database
 */

public class EditPostit implements Observer {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private String text;


    /**
     * Constructor that start a db conn and sets the id and text
     * @param text text of the postit
     * @param iD id of the postit
     */
    public EditPostit(String text, String iD){
        try {
            this.text = text;
            this.iD = iD;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * When we get a update that DB connection is done we execute the async class editpost
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        EditPost editPost;
        editPost = new EditPostit.EditPost();
        editPost.execute();
    }

    /**
     * Class that extends async task that tries to edit a postit from DB
     */
    private class EditPost extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{
                String query = "update Postits set Postit=? where PostID= '" + iD + "' ";
                PreparedStatement psEdit = c.prepareStatement(query);
                psEdit.setString(1, text);
                psEdit.executeUpdate();
                psEdit.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}
