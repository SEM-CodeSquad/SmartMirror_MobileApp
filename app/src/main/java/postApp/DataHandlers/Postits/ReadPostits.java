package postApp.DataHandlers.Postits;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.Authentication.DBConnection;

/**
 * @author Emanuel on 21/11/2016.
 */

public class ReadPostits {
    private DBConnection conn;
    private Connection c;
    private String user;
    private JSONArray postArray;
    private JSONObject postitJson;

    public ReadPostits(String user) {
        try {
            conn = new DBConnection();
            conn.execute();
            c = conn.get();
            this.user = user;
        } catch (Exception v) {
            System.out.println(v);
        }
    }


    private class fetchPostits extends AsyncTask<Void, Void, JSONArray> {

        protected JSONArray doInBackground(Void... arg0){
        postArray = new JSONArray();
        try {

            String query = "select PostID, Color, Postit from Postits where UserID=?";
            PreparedStatement pstSettings = c.prepareStatement(query);
            pstSettings.setString(1, user);
            ResultSet rs = pstSettings.executeQuery();

            int count= 0;
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
        return postArray;
    }

    }

    public JSONArray getPostitArray(){
        ReadPostits.fetchPostits fetch;
        try {
            fetch = new ReadPostits.fetchPostits();
            fetch.execute();
            this.postArray = fetch.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return postArray;
    }

}
