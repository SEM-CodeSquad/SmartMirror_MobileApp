package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that is used for resetting password
 */

public class ResetPassword extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private boolean reseted;

    /**
     * Constructor for class that makes this class into a observable and start a dbconnection
     *
     * @param User     username for the db
     * @param Password pass for the db
     */
    public ResetPassword(String User, String Password) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.password = Password;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    /**
     * Just a update that start a resetpass asynctask
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        this.c = conn.getConn();
        resetPass rp = new resetPass();
        rp.execute();
    }

    /**
     * Just called when wanting to notify observers
     */
    public void NotObserver() {
        setChanged();
        notifyObservers();
    }

    /**
     * @return reseted, which is a boolean that says if it was successful or not
     */
    public boolean getPasswordResetStatus() {
        return reseted;
    }

    /**
     * Class that communicates with the DB, and extends async task
     */
    private class resetPass extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            try {
                String resetPass = "update Users set Password=? where UserID= '" + user + "' ";
                PreparedStatement psReset = c.prepareStatement(resetPass);
                psReset.setString(1, password);
                psReset.executeUpdate();
                reseted = true;
                psReset.close();
                c.close();
            } catch (Exception e) {
                reseted = false;
                e.printStackTrace();
            }

            return null;
        }

        /**
         * When async task is doen we notify the observers
         *
         * @param unused
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }
}

