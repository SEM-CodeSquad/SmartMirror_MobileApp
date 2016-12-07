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

public class EditPostit extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean edited;
    private String text;


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

    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        EditPost editPost;
        editPost = new EditPostit.EditPost();
        editPost.execute();
    }

    private class EditPost extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0)
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

            return edited;
        }

    }

    public Boolean getEditedStatus(){
        return edited;
    }

}
