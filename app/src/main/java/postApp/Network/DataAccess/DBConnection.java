package postApp.Network.DataAccess;
import android.os.AsyncTask;

import java.sql.*;

public class DBConnection extends AsyncTask<String, Void, Connection> {

    String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7143433";

    /**
     * Constructor.
     */
    protected Connection doInBackground(String... args) {
        try {
            System.err.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.err.println("Driver loaded...");
        } catch (ClassNotFoundException ex) {
            System.err.println("\n" + "Could not load driver..." + "\n");
            System.err.println(ex);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
            return getConn();
    }

    /**
     * Returns a connection to the database.
     *
     * @return Connection
     */
    public Connection getConn() {

        Connection conn = null;

        try {

            String Username = "sql7143433";
            String Password = "CSqnX957Xb";
            conn = DriverManager.getConnection(URL, Username, Password);
            System.err.println("Connected!");
        } catch (SQLException e) {
            System.err.println("\n" + "Could not connect..." + "\n");
            System.err.println(e);
        }
        return conn;

    }
}