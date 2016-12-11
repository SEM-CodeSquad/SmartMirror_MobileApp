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

public class MatchAnswer extends Observable implements Observer {
    private DBConnection conn;
    private String user;
    private String answer;
    private Connection c;
    private boolean match;

    /**
     * Constructor that start a db connection and sets observer
     * @param User
     * @param Answer
     */
    public MatchAnswer(String User, String Answer) {
        try {
            this.user = User;
            this.answer = Answer;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    /**
     * When db connection is done the update is called, this starts a new Matchanswer async class
     * @param observable
     * @param o
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
    public void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * Get the answer match
     * @return true if they match else false
     */
    public boolean getAnswerMatch(){
        return match;
    }
}
