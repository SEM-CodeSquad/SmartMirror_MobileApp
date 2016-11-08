package postApp.Network.DataAccess;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emanuel on 07/11/2016.
 */

public class Settings {
    private DBConnection conn;
    Connection c;
    private String user;
    private List settings;


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

    private class fetchSettings extends AsyncTask<Void, Void, List> {
        private List settingsList;

        protected List doInBackground(Void... arg0) {
            try {
                String query = "select BusConfig, WeatherConfig, NewsFeedConfig from Users where UserID=?";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.setString(1, user);
                ResultSet rs = pstSettings.executeQuery();


                while (rs.next()) {
                    String bus = rs.getString("BusConfig");
                    settingsList.add(bus);
                    String news = rs.getString("NewsFeedConfig");
                    settingsList.add(news);
                    String weather = rs.getString("WeatherConfig");
                    settingsList.add(weather);

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return settingsList;
        }
    }

    public List getSettings(){
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
        return this.settings;
    }
}