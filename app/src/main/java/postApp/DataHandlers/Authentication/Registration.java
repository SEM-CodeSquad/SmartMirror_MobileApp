package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Database class for registration
 */
class Registration extends Observable implements Observer {

    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private String answer;
    private boolean inUse = true;


    /**
     * Constructor for the registration class, that makes this class a observable
     * and sets username password and answer
     * @param user The username typed in
     * @param password The password typed in
     * @param answer The scret question answer
     */
    Registration(String user, String password, String answer) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = user;
            this.password = password;
            this.answer = answer;
        } catch (Exception v) {
            v.printStackTrace();
        }

    }

    /**
     * A observable update that just confirms that we have eastablished a db connection
     * then you call the register acc asynctask to start
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
        this.c = conn.getConn();
        RegisterAcc register = new RegisterAcc();
        register.execute();
    }

    /**
     * Class that extends a async task for communicating with db
     */
    private class RegisterAcc extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
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
                    inUse = true;

                } else {

                    String register = "insert into Users (UserID, Password, Answer, BusConfig, WeatherConfig, NewsFeedConfig)" + "VALUES('" + user
                            + "', '" + password + "', '" + answer + "', 'No bus stop selected', 'No city selected', 'No feed selected');";
                    PreparedStatement ps = c.prepareStatement(register);
                    ps.executeUpdate();
                    ps.close();
                    c.close();
                }

            } catch (SQLException e) {
                    e.printStackTrace();
            }
            inUse = false;
            return null;
        }

        /**
         * On postexecute on the async task we want to notify the observer.
         * @param unused unused
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    /**
     * Just called when wanting to notify observers
     */
    public void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * method that returns inUse
     * @return inUse is either true or false where its either a successfull registration or not
     */
    boolean getInUse(){
        return inUse;
    }
}