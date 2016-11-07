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
    private String setting;
    private String change;
    private String user;
    private boolean updated;
    private String[] settings = new String[3];


    public Settings(String setting, String change, String user){
        this.setting = setting;
        this.change = change;


    }

    public boolean updateSettings(){
        try {
            String settings = "update Users set " + setting +"=? where User.UserID= '" + user + "' ";
            PreparedStatement pstUpdate = c.prepareStatement(settings);
            pstUpdate.setString(1, change);

            ResultSet rs = pstUpdate.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
               this.updated = true;
            } else {
                this.updated = false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return this.updated;
    }

    public String[] getSettings(){
        try {
            String query = "select BusConfig, WeatherConfig, NewsFeedConfig from Users where UserID=?";
            PreparedStatement pstlogin = c.prepareStatement(query);
            pstlogin.setString(1, user);
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
