package postApp.DataHandlers.AppCommons.Settings;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that connects to database and gets fettings by user typed in.
 */

public class FetchSettings extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String[] settings;

    /**
     * contructor that creates a db connection and the makes this class a observer.
     * @param User
     */
    public FetchSettings(String User) {
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
        c = conn.getConn();
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