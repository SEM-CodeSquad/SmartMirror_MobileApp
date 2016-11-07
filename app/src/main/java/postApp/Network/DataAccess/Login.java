package postApp.Network.DataAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Login {
    private DBConnection conn;
    private String User;
    private String Password;
    boolean logedIn;
    Connection c;

    public Login(String User, String Password) {
        this.conn = new DBConnection();
        this.c = conn.getConn();
        this.User = User;
        this.Password = Password;
        Authenticate();
    }

    private void Authenticate() {
        try {
            String query = "select from User where name=? and password=? ";
            PreparedStatement pstlogin = (PreparedStatement) c.prepareStatement(query);
            pstlogin.setString(1, User);
            pstlogin.setString(2, Password);
            ResultSet rs = pstlogin.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                logedIn = true;
            } else {
                //display an error message
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public boolean getStatus(){
     return this.logedIn;
    }


}

