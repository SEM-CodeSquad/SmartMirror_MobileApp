package postApp.Network.DataAccess;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emanuel on 07/11/2016.
 */

public class Settings {
    private DBConnection conn;
    Connection c;
    private String user;
    private String[] settings;


    public Settings(String User) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = User;
        } catch (Exception v) {
            System.out.println(v);
        }


    }

    private class fetchSettings extends AsyncTask<Void, Void, String[]> {
        private String[] settings = new String[3];

        protected String[] doInBackground(Void... arg0) {
            try {
                String query = "select BusConfig, WeatherConfig, NewsFeedConfig from Users where UserID=?";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.setString(1, user);
                ResultSet rs = pstSettings.executeQuery();

                int count = 0;
                while (rs.next()) {
                    this.settings[count] = rs.toString();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return settings;
        }
    }

    public String[] getSettings(){
        fetchSettings set;
        try {
            set = new fetchSettings();
            set.execute();
            this.settings = set.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return settings;
    }
}