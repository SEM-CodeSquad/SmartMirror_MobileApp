package postApp.DataHandlers.AppCommons.Postits;

import android.os.AsyncTask;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.DBConnection.DBConnection;

/**
 * @author Emanuel on 21/11/2016.
 */

public class ReadPostits extends Observable implements Observer {
    private DBConnection conn;
    private Connection c;
    private String user;
    private JSONArray postArray;
    private JSONObject postitJson;

    public ReadPostits(String user) {
        try {
            conn = new DBConnection();
            conn.addObserver(this);
            this.user = user;
        } catch (Exception v) {
            System.out.println(v);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        c = conn.getConn();
        fetchPostits fetch;
        fetch = new fetchPostits();
        fetch.execute();
    }

    private class fetchPostits extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                postArray = new JSONArray();
                String query = "select PostID, Color, Postit from Postits where UserID=?";
                PreparedStatement pstSettings = c.prepareStatement(query);
                pstSettings.setString(1, user);
                ResultSet rs = pstSettings.executeQuery();

                int count = 0;
                while (rs.next()) {
                    postitJson = new JSONObject();
                    String postID = rs.getString("PostID");
                    postitJson.put("PostitID", postID);
                    String color = rs.getString("Color");
                    postitJson.put("Color", color);
                    String text = rs.getString("Postit");
                    postitJson.put("Text", text);
                    postArray.add(count, postitJson);
                    count++;


                }

            } catch (Exception e) {
                e.printStackTrace();
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

    public JSONArray getPostitArray(){
        return postArray;
    }

}
