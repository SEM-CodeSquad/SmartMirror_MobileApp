package postApp.DataHandlers.Authentication;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

public class Login extends Observable implements Observer {
    private DBConnection conn;
    private String user;
    private String password;
    private boolean logedIn;
    private Connection c;



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

    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        authenticate au = new authenticate();
        au.execute();
    }

    private class authenticate extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try {
                String query = "select UserID, Password from Users where UserID=? and Password=? ";
                PreparedStatement psLogin = conn.getConn().prepareStatement(query);
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

    public boolean getStatus(){
        return logedIn;
    }
}

