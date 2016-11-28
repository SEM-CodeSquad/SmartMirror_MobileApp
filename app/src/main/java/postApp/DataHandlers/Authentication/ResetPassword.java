package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emanuel on 08/11/2016.
 */

public class ResetPassword extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private boolean reseted;

    public ResetPassword(String User, String Password){
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = User;
            this.password = Password;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        resetPass rp = new resetPass();
        rp.execute();
    }


    private class resetPass extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            try {
                String resetPass = "update Users set Password=? where UserID= '" + user + "' ";
                PreparedStatement psReset = c.prepareStatement(resetPass);
                psReset.setString(1, password);
                psReset.executeUpdate();
                reseted = true;
                psReset.close();
            } catch (Exception e) {
                reseted = false;
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

    public boolean getPasswordResetStatus(){
            return reseted;
    }
}

