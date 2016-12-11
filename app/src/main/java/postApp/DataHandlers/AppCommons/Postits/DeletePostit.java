package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class used for deleting postits
 */
public class DeletePostit extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean deleted;

    /**
     * Constructor that sets up a DBconnection
     * @param iD The id of the postit we want to delete
     */
    public DeletePostit(String iD){
        try {
            this.iD = iD;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    /**
     * When its updated we start a DeletePost async task
     * @param observable o
     * @param o o
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        DeletePost deletePost;
        deletePost = new DeletePost();
        deletePost.execute();
    }

    /**
     * Class that extends async task that tries to delete a postit from DB
     */
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

        /**
         * When done notify the observers
         * @param unused o
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }
    }

    /**
     * Notifies the observers
     */
    public void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /*
     * @return A boolean that is either true or false if its deleted
     */
    public Boolean getDeletedStatus(){
        return deleted;
    }
}
