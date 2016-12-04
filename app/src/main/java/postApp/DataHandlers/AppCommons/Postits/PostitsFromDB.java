package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.DBConnection.DBConnection;

public class PostitsFromDB  extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private List postits;


    public PostitsFromDB(String User) {
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

    }

    private class fetchPostits extends AsyncTask<Void, Void, List> {
        private List postitsList;

        protected List doInBackground(Void... arg0) {
            try {
                String query = "select PostitView from PostitsFromDB where UserID=?";
                PreparedStatement pstlogin = c.prepareStatement(query);
                pstlogin.setString(1, user);

                ResultSet rs = pstlogin.executeQuery();

                while (rs.next()) {
                    String postit = rs.getString("PostitView");

                    postitsList.add(postit);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return postitsList;
        }

    }

    public List getSettings(){
        fetchPostits pst;
        try {
            pst = new fetchPostits();
            pst.execute();
            this.postits = pst.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return this.postits;
    }
}