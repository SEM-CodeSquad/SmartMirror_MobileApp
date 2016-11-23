package postApp.DataHandlers.Postits;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.Authentication.DBConnection;


/**
 * @author Emanuel on 21/11/2016.
 */

public class StorePostits {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String postit;
    private String iD;
    private Boolean stored;
    private String color;

    public StorePostits(String user,String idOne, String color, String text) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = user;
            this.iD = idOne;
            this.color = color;
            this.postit = text;


        } catch (Exception v) {
            System.out.println(v);
        }
    }

    private class SavePostits extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... arg0)
        {
            try{
                String query = "insert into Postits (UserID, PostID, Color, Postit)" + "VALUES('" + user
                        + "', '" + iD + "', '" + color + "', '" + postit +"');";
                PreparedStatement psPost = c.prepareStatement(query);
                psPost.executeUpdate();
                psPost.close();

                stored = true;
            } catch (Exception e) {
                e.printStackTrace();
                stored = false;

            }

            return stored;
        }

    }

    public Boolean getStoreStatus(){
        StorePostits.SavePostits psS;
        try {
            psS = new StorePostits.SavePostits();
            psS.execute();
            return psS.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


}
