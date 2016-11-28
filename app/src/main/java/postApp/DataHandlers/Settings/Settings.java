package postApp.DataHandlers.Settings;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.Authentication.DBConnection;

/**
 * Created by Emanuel on 07/11/2016.
 */

public class Settings  extends Observable implements Observer {
    private DBConnection conn;
    Connection c;
    private String user;
    private String[] settings;


    public Settings(String User) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        fetchSettings set;
        set = new fetchSettings();
        set.execute();
    }

    private class fetchSettings extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {
            settings = new String[3];
            try {
                String query = "select BusConfig, WeatherConfig, NewsFeedConfig from Users where UserID=?";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.setString(1, user);
                ResultSet rs = pstSettings.executeQuery();


                while (rs.next()) {
                    String bus = rs.getString("BusConfig");
                    settings[0] = bus;
                    String weather = rs.getString("WeatherConfig");
                    settings[1] = weather;
                    String news = rs.getString("NewsFeedConfig");
                    settings[2] = news;


                }
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
    public String[] getSettings(){
        return settings;
    }
}