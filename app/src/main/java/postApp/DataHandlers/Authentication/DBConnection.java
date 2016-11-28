package postApp.DataHandlers.Authentication;
import android.os.AsyncTask;

import java.sql.*;
import java.util.Observable;

public class DBConnection extends Observable {

    String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7143433";
    Connection Conn;

    public DBConnection() {
        runDB r = new runDB();
        r.execute();
    }

    /**
     * Returns a connection to the database.
     *
     * @return Connection
     */
    public Connection getConn() {
        return Conn;

    }

    private class runDB extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... args) {
            try {
                System.err.println("Loading driver...");
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                System.err.println("Driver loaded...");
                String Username = "sql7143433";
                String Password = "CSqnX957Xb";
                Conn = DriverManager.getConnection(URL, Username, Password);
                System.err.println("Connected!");
            } catch (ClassNotFoundException ex) {
                System.err.println("\n" + "Could not load driver..." + "\n");
                System.err.println(ex);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
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
}