package postApp.DataHandlers.AppCommons.Settings;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Created by adinH on 2016-11-30.
 */

public class StoreSettings extends Observable implements Observer {
    private DBConnection conn;
    Connection c;
    private String user;
    private String[] settings;
    private boolean sett;
    String news;
    String bus;
    String weather;

    public StoreSettings(String User, String news, String bus, String weather) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.news = news;
            this.bus = bus;
            this.weather = weather;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        fetchSettings set;
        set = new fetchSettings();
        set.execute();
    }

    private class fetchSettings extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {
            settings = new String[3];
            try {

                String query = "UPDATE Users SET BusConfig = '" + bus + "', WeatherConfig = '" + weather + "', `NewsFeedConfig` = '" + news + "' WHERE `UserID` = '" + user +"'";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.executeUpdate();
                pstSettings.close();
                sett = true;
            } catch (Exception e) {
                e.printStackTrace();
                sett = false;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    public void NotObserver() {
        setChanged();
        notifyObservers();
    }

    public Boolean getSettings() {
        return sett;
    }
}

