package postApp.Network.DataAccess;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class Login {
    private DBConnection conn;
    private String user;
    private String password;
    private boolean logedIn;
    private Connection c;
    private String answerMatch;
    private boolean match;


    public Login(String User, String Password) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = User;
            this.password = Password;
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    private class authenticate extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0)
        {
            try {
                String query = "select UserID, Password from Users where UserID=? and Password=? ";
                PreparedStatement psLogin = c.prepareStatement(query);
                psLogin.setString(1, user);
                psLogin.setString(2, password);
                ResultSet rs = psLogin.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    logedIn = true;

                } else {
                    logedIn = false;
                }

                psLogin.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return logedIn;
        }

    }
    public void matchAnswer(String answer){
        try {

            String answerQuery = "select Answer from Users where Answer=? ";
            PreparedStatement psAnswer = c.prepareStatement(answerQuery);
            psAnswer.setString(1, answer);
            ResultSet rs = psAnswer.executeQuery();

            while (rs.next()) {
                answerMatch = rs.getString("Answer");
            }
            if (answerMatch == answer){
                this.match= true;
            }else{
                this.match = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void resetPassword(String user, String newPassword){
        try {
                String resetPass = "update Users set Password=? where User.UserID= '" + user + "' ";
                PreparedStatement psReset = c.prepareStatement(resetPass);
                psReset.setString(1, newPassword);
                psReset.executeUpdate();
                psReset.close();
                c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean getStatus(){
        try {
            authenticate au = new authenticate();
            au.execute();
            return au.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getMatch(){
        return this.match;
    }


}

