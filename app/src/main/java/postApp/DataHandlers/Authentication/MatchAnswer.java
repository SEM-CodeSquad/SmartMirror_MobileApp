package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Created by Emanuel on 08/11/2016.
 */

public class MatchAnswer extends Observable implements Observer {
    private DBConnection conn;
    private String user;
    private String answer;
    private Connection c;
    private boolean match;


    public MatchAnswer(String User, String Answer) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.answer = Answer;
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        matchAnswer ma = new matchAnswer();
        ma.execute();
    }

    private class matchAnswer extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try {
                String answerQuery = "select UserID, Answer from Users where Answer=? and Answer=? ";
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
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    public void NotObserver(){
        setChanged();
        notifyObservers();
    }

    public boolean getAnswerMatch(){
        return match;
    }
}
