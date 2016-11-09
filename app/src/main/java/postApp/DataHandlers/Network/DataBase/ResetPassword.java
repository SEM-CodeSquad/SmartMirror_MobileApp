package postApp.DataHandlers.Network.DataBase;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emanuel on 08/11/2016.
 */

public class ResetPassword {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private boolean reseted;

    public ResetPassword(String User, String Password){
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = User;
            this.password = Password;
        } catch (Exception v) {
            System.out.println(v);
        }
    }


    private class resetPass extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0) {
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

            return reseted;
        }
    }

    public boolean getPasswordResetStatus(){
        try {
            resetPass rp = new resetPass();
            rp.execute();
            return rp.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }



}

