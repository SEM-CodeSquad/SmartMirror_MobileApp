package postApp.Network.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class GetPostits {
    private DBConnection conn;
    Connection c;
    private String User;
    private List postits;

    public GetPostits(String User){
        this.User = User;
        FetchPostits();
    }

    private List FetchPostits(){
        try {
            String query = "rand "; //update this later update table where "Setting"
            PreparedStatement pstlogin = (PreparedStatement) c.prepareStatement(query);
            pstlogin.setString(1, User);

            ResultSet rs = pstlogin.executeQuery();


            while (rs.next()) {
                String postit = rs.getString("Postit");

                postits.add(postit);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    return postits;


    }
}
