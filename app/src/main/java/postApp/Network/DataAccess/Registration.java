package postApp.Network.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration {
    private DBConnection conn;
    private Connection c;
    private String user;
    private String password;
    private String answer;
    private boolean inUse;
    private String userID;



    public Registration (String user, String password, String answer) {
        this.conn = new DBConnection();
        this.c = conn.getConn();
        this.user = user;
        this.password = password;
        register();

    }

    private void register(){
        try {
                String query = "select UserID from Users where UserID=?";
                PreparedStatement pstReg = c.prepareStatement(query);
                pstReg.setString(1, user);
                ResultSet rs = pstReg.executeQuery();

                while (rs.next()) {
                    userID = rs.getString("UserID");
                }
                if (userID == user) {
                    this.inUse = true;

                }else{

                    String register = "insert into Users (UserID, Password, Answer)" + "VALUES('" + user
                            + "', '" + password + "', '" + answer + "');";
                    PreparedStatement ps =  c.prepareStatement(register);
                    ps.executeUpdate();
                    ps.close();
                    c.commit();
                }

        }
        catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public boolean getInUse(){
        return this.inUse;
    }
}
