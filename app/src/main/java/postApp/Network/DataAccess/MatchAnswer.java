package postApp.Network.DataAccess;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emanuel on 08/11/2016.
 */

public class MatchAnswer {
    private DBConnection conn;
    private String user;
    private String answer;
    private Connection c;
    private boolean match;


    public MatchAnswer(String User, String Answer) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = User;
            this.answer = Answer;
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    private class matchAnswer extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0)
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
            return match;
        }

    }


    public boolean getAnswerMatch(){
        try {
            matchAnswer ma = new matchAnswer();
            ma.execute();
            return ma.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


}
