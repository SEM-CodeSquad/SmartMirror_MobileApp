package postApp.DataHandlers.Postits;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.Authentication.DBConnection;

/**
 * @author Emanuel on 23/11/2016.
 */

public class DeletePostit {
    private DBConnection conn;
    private Connection c;
    private String iD;
    private Boolean deleted;


    public DeletePostit(String iD){
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.iD = iD;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    private class DeletePost extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0)
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

            return deleted;
        }

    }

    public Boolean getDeletedStatus(){
        DeletePostit.DeletePost deletePost;
        try {
            deletePost = new DeletePostit.DeletePost();
            deletePost.execute();
            return deletePost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


}
