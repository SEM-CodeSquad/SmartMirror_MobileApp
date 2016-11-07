package postApp.Network.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Emanuel on 07/11/2016.
 */

public class Settings {
    private DBConnection conn;
    Connection c;
    private String Setting;
    private String Change;
    private String User;
    private boolean Updated;
    private String[] settings = new String[3];


    public Settings(String Setting, String Change, String User){
        this.Setting = Setting;
        this.Change = Change;


    }

    public boolean UpdateSettings(){
        try {
            String query = "rand "; //update this later update table where "Setting"
            PreparedStatement pstlogin = (PreparedStatement) c.prepareStatement(query);
            pstlogin.setString(1, Change);

            ResultSet rs = pstlogin.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
               this.Updated = true;
            } else {
                this.Updated = false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return this.Updated;
    }

    public String[] getSettings(){
        try {
            String query = "rand "; //update this later retrieve "Settings"
            PreparedStatement pstlogin = (PreparedStatement) c.prepareStatement(query);
            pstlogin.setString(1, Change);

            ResultSet rs = pstlogin.executeQuery();

            int count = 0;
            while (rs.next()) {
                this.settings[count] = rs.toString();
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return settings;
    }



}
