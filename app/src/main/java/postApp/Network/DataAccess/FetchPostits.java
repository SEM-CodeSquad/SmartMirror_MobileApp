package postApp.Network.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class FetchPostits {
    private DBConnection conn;
    Connection c;
    private String User;
    private List postits;

    public FetchPostits(String User){
        this.User = User;
        fetchPostits();
    }

    private void fetchPostits(){
        try {
            String query = "select Postit from Postits where UserID=?";
            PreparedStatement pstlogin = c.prepareStatement(query);
            pstlogin.setString(1, User);

            ResultSet rs = pstlogin.executeQuery();


            while (rs.next()) {
                String postit = rs.getString("Postit");

                this.postits.add(postit);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public List getPostits(){
        return this.postits;
    }
}
