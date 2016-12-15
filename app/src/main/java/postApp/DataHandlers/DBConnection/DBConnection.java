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

package postApp.DataHandlers.DBConnection;
import android.os.AsyncTask;

import java.sql.*;
import java.util.Observable;

/**
 * Class used for establish a connection to the database
 */
public class DBConnection extends Observable {

    private Connection Conn;

    /**
     * Constructor that executes the runDB inner class
     */
    public DBConnection() {
        runDB r = new runDB();
        r.execute();
    }

    /**
     * Returns a connection to the database.
     *
     * @return Connection
     */
    public Connection getConn() {
        return Conn;

    }

    /**
     * Async task that connects to the database
     */
    private class runDB extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... args) {
            try {
                System.err.println("Loading driver...");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                System.err.println("Driver loaded...");
                String Username = "sql7143433";
                String Password = "CSqnX957Xb";
                String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7143433";
                Conn = DriverManager.getConnection(URL, Username, Password);
                System.err.println("Connected!");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    private void NotObserver() {
        setChanged();
        notifyObservers();
    }
}