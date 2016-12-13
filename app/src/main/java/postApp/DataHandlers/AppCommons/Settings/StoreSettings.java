package postApp.DataHandlers.AppCommons.Settings;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class for storing settings in the db
 */

public class StoreSettings extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private boolean sett;
    private String news;
    private String bus;
    private String weather;

    /**
     * Constructor for storesettings that starts a db connection
     * @param User The user
     * @param news The news
     * @param bus The bus
     * @param weather The weather
     */
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

    /**
     * When we get a update from the databaseconnection class we execute fetchsettings
     * @param observable The Observable
     * @param o The object
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        fetchSettings set;
        set = new fetchSettings();
        set.execute();
    }

    /**
     * Async task that stores settings and sets sett to either true or false
     */
    private class fetchSettings extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... arg0) {
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

        /**
         * Notify obs on postexecute
         * @param unused n
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    /**
     * Used for notifying observers
     */
    public void NotObserver() {
        setChanged();
        notifyObservers();
    }

    /**
     * @return If settings are stored or not (true or false)
     */
    public Boolean getSettings() {
        return sett;
    }
}

