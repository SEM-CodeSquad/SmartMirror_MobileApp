package postApp.Network.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration {
    private DBConnection conn;
    Connection c;
    private String User;
    private String Password;
    private String Answer;
    private boolean InUse;


public Registration (String User, String Password, String Answer) {
        this.conn = new DBConnection();
        this.c = conn.getConn();
        this.User = User;
        this.Password = Password;
        Register();
    }

    private void Register(){

        try {
            String query = "select from User where name=?";
            PreparedStatement pstlogin = (PreparedStatement) c.prepareStatement(query);
            pstlogin.setString(1, User);
            pstlogin.setString(2, Password);
            ResultSet rs = pstlogin.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 0) {
                this.InUse= true;
            } else {
                this.InUse = false;
                String resetPass = null; // "update User set password=? where User.answer= '" + answer + "' and User.name= '" + name + "' "
                PreparedStatement ps = (PreparedStatement) c.prepareStatement(resetPass);
                ps.setString(1, User);
                ps.setString(2, Password);
                ps.setString(3, Answer);
                ps.executeUpdate();

                // close the connection
                ps.close();
                c.commit();
            };

        }
        catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public boolean getRegistrationStatus(){
        return this.InUse;
    }

}
