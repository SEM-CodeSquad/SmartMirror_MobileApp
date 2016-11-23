package postApp.DataHandlers.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.Authentication.DBConnection;

/**
 * @author Emanuel on 23/11/2016.
 */

public class EditPostit {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean edited;
    private String text;


    public EditPostit(String text, String iD){
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.text = text;
            this.iD = iD;
        } catch (Exception v) {
            System.out.println(v);
        }
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
        EditPostit.EditPost editPost;
        try {
            editPost = new EditPostit.EditPost();
            editPost.execute();
            return editPost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
