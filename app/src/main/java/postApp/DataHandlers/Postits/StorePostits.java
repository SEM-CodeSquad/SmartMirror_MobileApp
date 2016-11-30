package postApp.DataHandlers.Postits;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.Authentication.DBConnection;


/**
 * @author Emanuel on 21/11/2016.
 */

public class StorePostits extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String postit;
    private String iD;
    private Boolean stored;
    private String color;

    public StorePostits(String user,String idOne, String color, String text) {
        try {
            this.user = user;
            this.iD = idOne;
            this.color = color;
            this.postit = text;
            conn = new DBConnection();
            conn.addObserver(this);
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
            c = conn.getConn();
            SavePostits psS;
            psS = new SavePostits();
            psS.execute();
    }

    private class SavePostits extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0)
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

    public Boolean getStoreStatus(){
        return stored;
    }


}
