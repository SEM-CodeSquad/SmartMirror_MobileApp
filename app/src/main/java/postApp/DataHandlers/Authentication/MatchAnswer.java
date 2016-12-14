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
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that starts a dbconnection and checks if secret answers match
 */

class MatchAnswer extends Observable implements Observer {
    private DBConnection conn;
    private String user;
    private String answer;
    private Connection c;
    private boolean match;

    /**
     * Constructor that start a db connection and sets observer
     * @param User the user
     * @param Answer the answer
     */
    MatchAnswer(String User, String Answer) {
        try {
            this.user = User;
            this.answer = Answer;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            v.printStackTrace();
        }

    }

    /**
     * When db connection is done the update is called, this starts a new Matchanswer async class
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {

        this.c = conn.getConn();
        matchAnswer ma = new matchAnswer();
        ma.execute();
    }

    /**
     * Class that extends Async task and communicates with the DataBase
     */
    private class matchAnswer extends AsyncTask<Void, Void, Void> {

        /**
         * async task that checks if secret question answer is correct
         * @param arg0 not used
         * @return null
         */
        protected Void doInBackground(Void... arg0)
        {
            try {
                String answerQuery = "select UserID, Answer from Users where UserID=? and Answer=? ";
                PreparedStatement psAnswer = c.prepareStatement(answerQuery);
                psAnswer.setString(1, user);
                psAnswer.setString(2, answer);
                ResultSet rs = psAnswer.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    match = true;

                } else {
                    match = false;
                }
                psAnswer.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        /**
         * OnPostexecute of async task we notify the observers
         * @param unused n
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    /**
     * Notify the observers
     */
    private void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * Get the answer match
     * @return true if they match else false
     */
    boolean getAnswerMatch(){
        return match;
    }
}
