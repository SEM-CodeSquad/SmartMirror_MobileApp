package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * @author Emanuel on 23/11/2016.
 */

public class DeletePostit extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean deleted;


    public DeletePostit(String iD){
        try {
            this.iD = iD;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }
    }
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        DeletePost deletePost;
        deletePost = new DeletePost();
        deletePost.execute();
    }
    private class DeletePost extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{
                System.out.println(iD);
                String query = "delete from Postits where PostID= '" + iD + "' ";
                PreparedStatement psPost = c.prepareStatement(query);
                psPost.executeUpdate();
                psPost.close();
                deleted = true;
            } catch (Exception e) {
                e.printStackTrace();
                deleted = false;
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
    public Boolean getDeletedStatus(){
        return deleted;
    }
}
