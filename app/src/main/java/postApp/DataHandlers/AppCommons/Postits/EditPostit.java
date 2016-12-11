package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * Class that is used for editing a postit
 */

public class EditPostit extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean edited;
    private String text;


    /**
     * Constructor that start a db conn and sets the id and text
     * @param text text of the postit
     * @param iD id of the postit
     */
    public EditPostit(String text, String iD){
        try {
            this.text = text;
            this.iD = iD;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    /**
     * When we get a update that DB connection is done we execute the async class editpost
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        EditPost editPost;
        editPost = new EditPostit.EditPost();
        editPost.execute();
    }

    /**
     * Class that extends async task that tries to edit a postit from DB
     */
    private class EditPost extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
        {
            try{
                String query = "update Postits set Postit=? where PostID= '" + iD + "' ";
                PreparedStatement psEdit = c.prepareStatement(query);
                psEdit.setString(1, text);
                psEdit.executeUpdate();
                psEdit.close();
                edited = true;
            } catch (Exception e) {
                e.printStackTrace();
                edited = false;
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
    public Boolean getEditedStatus(){
        return edited;
    }

}
