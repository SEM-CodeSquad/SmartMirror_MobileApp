package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class Registration {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private String answer;
    private boolean inUse = true;
    private String userID;



    public Registration (String user, String password, String answer) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = user;
            this.password = password;
            this.answer = answer;
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    private class RegisterAcc extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0) {
            try {
                String query = "select UserID from Users where UserID=?";
                PreparedStatement pstReg = c.prepareStatement(query);
                pstReg.setString(1, user);
                ResultSet rs = pstReg.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    return true;

                } else {

                    String register = "insert into Users (UserID, Password, Answer, BusConfig, WeatherConfig, NewsFeedConfig)" + "VALUES('" + user
                            + "', '" + password + "', '" + answer + "', 'No bus stop selected', 'No city selected', 'No feed selected');";
                    PreparedStatement ps = c.prepareStatement(register);
                    ps.executeUpdate();
                    ps.close();
                }

            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return false;
        }
    }

    public boolean getInUse(){
        try {
            RegisterAcc register = new RegisterAcc();
            register.execute();
            return register.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}