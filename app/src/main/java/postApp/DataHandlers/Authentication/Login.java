package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class used for making a db connection and checking if the username and password are correct when logging in
 */
public class Login extends Observable implements Observer {
    private DBConnection conn;
    private String user;
    private String password;
    private boolean logedIn;
    private Connection c;

    /**
     * Here we set the user and password and we make a db connection and add a observer.
     * @param User the username
     * @param Password the password
     */
    public Login(String User, String Password) {
        try {
            this.user = User;
            this.password = Password;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }

    }

    /**
     * We receive when the db connecton is done and start the authentication async task.
     * We set C to conn.getconn since we know the connection is done
     * @param observable q
     * @param o q
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        authenticate au = new authenticate();
        au.execute();
    }

    /**
     * Function used for notifying observer
     */
    private void NotObserver() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Returns the status if we succesfully logged in or not
     * @return
     */
    public boolean getStatus() {
        return logedIn;
    }

    /**
     * Async task that tries to query a login. On postexecute calls the method NotObserver. We also set the boolean to either
     * true of false depending on the response from dp
     */
    private class authenticate extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            try {
                String query = "select UserID, Password from Users where UserID=? and Password=? ";
                PreparedStatement psLogin = c.prepareStatement(query);
                psLogin.setString(1, user);
                psLogin.setString(2, password);
                ResultSet rs = psLogin.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    logedIn = true;

                } else {
                    logedIn = false;
                }

                psLogin.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        /**
         * Call the method to notify observer when we are done
         * @param unused q
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }
}

