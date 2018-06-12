package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javaclass.Users;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jsf.SessionUtils;

@ManagedBean
@SessionScoped
public class UsersBean implements Serializable {
    
    private static final long serialVersionUID = 6081417964063918994L;

    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
                
    public String getUser() throws ClassNotFoundException, SQLException {

            Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            String uname = SessionUtils.getUserName();
            Connection connect = null;

            String url = "jdbc:derby://localhost:1527/blog";

            String username = "blog";
            String password = "blog";

            try {

                    Class.forName("org.apache.derby.jdbc.ClientDriver");

                    connect = DriverManager.getConnection(url, username, password);
                    // System.out.println("Connection established"+connect);

            } catch (SQLException ex) {
                    System.out.println("in exec");
                    System.out.println(ex.getMessage());
            }
            
            PreparedStatement pstmt1 = connect.prepareStatement("select * from Users where username=?");
            pstmt1.setString(1,uname);
            ResultSet rs = pstmt1.executeQuery();
            
            Users users = new Users();

            while (rs.next()) {

                    users.setUsername(rs.getString("username"));
                    users.setPassword(rs.getString("password"));
                    users.setFirstname(rs.getString("firstname"));
                    users.setLastname(rs.getString("lastname"));
                    users.setGender(rs.getString("gender"));
                    users.setEmail(rs.getString("email"));
                    users.setAge(rs.getInt("age"));

            }

            sessionMapObj.put("users1", users);
            rs.close();
            pstmt1.close();
            connect.close();

            return "editProfile";
    }
    
    public static String editUser(Users users1) throws ClassNotFoundException, SQLException{
        String uname = SessionUtils.getUserName();

        String navigationResult = "";

        String url = "jdbc:derby://localhost:1527/blog";

        String user = "blog";
        String pass = "blog";
        
        try {     
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connObj = DriverManager.getConnection(url, user, pass);
            pstmt = connObj.prepareStatement("update Users set password=?, firstname=?, lastname=?, gender=?, email=?, age=? where username=?");        

            pstmt.setString(1, users1.getPassword());
            pstmt.setString(2, users1.getFirstname());
            pstmt.setString(3, users1.getLastname());
            pstmt.setString(4, users1.getGender());
            pstmt.setString(5, users1.getEmail());
            pstmt.setInt(6, users1.getAge());
            pstmt.setString(7, uname);

            pstmt.executeUpdate();
            
            connObj.commit();
            connObj.close();
            navigationResult = "dashboard";
            
        } catch(Exception sqlException) {

            sqlException.printStackTrace();

        }

        return navigationResult;
    }
    
    public String saveUser(Users reg) throws ClassNotFoundException, SQLException {
        
        return UsersBean.editUser(reg);

    }
    
    public String getUser2(String uname) throws ClassNotFoundException, SQLException {

            Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Connection connect = null;

            String url = "jdbc:derby://localhost:1527/blog";

            String username = "blog";
            String password = "blog";

            try {

                    Class.forName("org.apache.derby.jdbc.ClientDriver");

                    connect = DriverManager.getConnection(url, username, password);
                    // System.out.println("Connection established"+connect);

            } catch (SQLException ex) {
                    System.out.println("in exec");
                    System.out.println(ex.getMessage());
            }
            
            PreparedStatement pstmt1 = connect.prepareStatement("select * from Users where username=?");
            pstmt1.setString(1,uname);
            ResultSet rs = pstmt1.executeQuery();
            
            Users users = new Users();

            while (rs.next()) {

                    users.setUsername(rs.getString("username"));
                    users.setPassword(rs.getString("password"));
                    users.setFirstname(rs.getString("firstname"));
                    users.setLastname(rs.getString("lastname"));
                    users.setGender(rs.getString("gender"));
                    users.setEmail(rs.getString("email"));
                    users.setAge(rs.getInt("age"));

            }

            sessionMapObj.put("users2", users);
            rs.close();
            pstmt1.close();
            connect.close();

            return "viewProfile";
    }
}
