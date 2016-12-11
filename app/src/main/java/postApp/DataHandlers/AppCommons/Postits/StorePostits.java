package postApp.DataHandlers.AppCommons.Postits;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;


/**
 * Class that stores the postits in the database
 */

public class StorePostits extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String postit;
    private String iD;
    private Boolean stored;
    private String color;
    private long timeStamp;

    /**
     * Establish a connection with the database and set all the Passed parameters
     * @param user the user
     * @param idOne The id of the postit
     * @param color The color of the postit
     * @param text The text of the postit
     * @param timeStamp The time of the postit
     */
    public StorePostits(String user,String idOne, String color, String text, long timeStamp) {
        try {
            this.user = user;
            this.iD = idOne;
            this.color = color;
            this.postit = text;
            this.timeStamp = timeStamp;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    /**
     * When we get a update from the dbconnection that its done we execute a SavePostits class
     * @param observable o
     * @param o o
     */
    @Override
    public void update(Observable observable, Object o) {
            c = conn.getConn();
            SavePostits psS;
            psS = new SavePostits();
            psS.execute();
    }

    /**
     * Class that extends async task that adds a postit in the database
     */
    private class SavePostits extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{

                String query = "insert into Postits (UserID, PostID, Color, Postit, Timestamp)" + "VALUES('" + user
                        + "', '" + iD + "', '" + color + "', '" + postit +"', '" + timeStamp +"');";
                PreparedStatement psPost = c.prepareStatement(query);
                psPost.executeUpdate();
                psPost.close();

                stored = true;

            } catch (Exception e) {
                e.printStackTrace();
                stored = false;

            }
            return null;
        }

        /**
         * When the async task is done we notify the observers
         * @param unused u
         */
        @Override
        protected void onPostExecute(Void unused) {
            NotObserver();
        }

    }

    /**
     * Notify the observers
     */
    public void NotObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * @return stored that is either true or false.
     */
    public Boolean getStoreStatus(){
        return stored;
    }

}
