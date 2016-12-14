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

package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that is used for resetting password
 */

class ResetPassword extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private boolean reseted;

    /**
     * Constructor for class that makes this class into a observable and start a dbconnection
     *
     * @param User     username for the db
     * @param Password pass for the db
     */
    ResetPassword(String User, String Password) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.password = Password;
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * Just a method waiting for a update from the dbconnection that then start a resetpass asynctask and executes it
     *
     * @param observable The observable
     * @param o          the object
     */
    @Override
    public void update(Observable observable, Object o) {
        this.c = conn.getConn();
        resetPass rp = new resetPass();
        rp.execute();
    }

    /**
     * Just called when wanting to notify observers
     */
    private void NotObserver() {
        setChanged();
        notifyObservers();
    }

    /**
     * @return reseted, which is a boolean that says if it was successful or not
     */
    boolean getPasswordResetStatus() {
        return reseted;
    }

    /**
     * Class that communicates with the DB, and extends async task
     */
    private class resetPass extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            try {
                String resetPass = "update Users set Password=? where UserID= '" + user + "' ";
                PreparedStatement psReset = c.prepareStatement(resetPass);
                psReset.setString(1, password);
                psReset.executeUpdate();
                reseted = true;
                psReset.close();
                c.close();
            } catch (Exception e) {
                reseted = false;
                e.printStackTrace();
            }

            return null;
        }
    }
}

