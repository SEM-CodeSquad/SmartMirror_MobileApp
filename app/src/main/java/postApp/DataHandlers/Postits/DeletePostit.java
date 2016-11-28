package postApp.DataHandlers.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.Authentication.DBConnection;

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
        } catch (Exception v) {
            System.out.println(v);
        }
    }
    @Override
    public void update(Observable observable, Object o) {
        DeletePostit.DeletePost deletePost;
        deletePost = new DeletePostit.DeletePost();
        deletePost.execute();
    }
    private class DeletePost extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{
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
            System.out.println("HI");
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
