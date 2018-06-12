package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaclass.Likes;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsf.SessionUtils;

@ManagedBean
@SessionScoped
public class LikesBean implements Serializable {
    
    private static final long serialVersionUID = 6081417964063918994L;
    
    public List<Likes> getLikes(int postid1) throws ClassNotFoundException, SQLException {

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
            
            PreparedStatement pstmt1 = connect.prepareStatement("select * from Likes where post_id=?");
            pstmt1.setInt(1, postid1);
            ResultSet rs = pstmt1.executeQuery();
            
            List<Likes> likes = new ArrayList<Likes>();

            while (rs.next()) {

                Likes like = new Likes();
                like.setLikeid(rs.getInt("like_id"));
                like.setPostid(rs.getInt("post_id"));
                like.setUsername(rs.getString("username"));
                likes.add(like);

            }

            rs.close();
            pstmt1.close();
            connect.close();

            return likes;
    }
    
    public static String createLike(int postid1) throws ClassNotFoundException, SQLException{
        
        Likes like = new Likes();
        like.setUsername(SessionUtils.getUserName());
        int saveResult = 0;
        String navigationResult = "";
        Connection connect = null;
        String url = "jdbc:derby://localhost:1527/blog";
        String username = "blog";
        String password = "blog";

        try {     
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            connect = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connect.prepareStatement("insert into Likes (post_id, username) values (?, ?)");        

            pstmt.setInt(1, postid1);
            pstmt.setString(2, like.getUsername());

            saveResult = pstmt.executeUpdate();

            connect.close();
        } 
        
        catch(Exception sqlException) {
            sqlException.printStackTrace();
        }

        if(saveResult !=0) {
            navigationResult = "dashboard";

        } else {

            navigationResult = "dashboard";

        }

        return navigationResult;
    }
    
    public String addLikes(int pos) throws ClassNotFoundException, SQLException {

        return LikesBean.createLike(pos);

    }
    
    public String deleteLikes(int postid, String user) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("delete from Likes where post_id=? and username=?");
        pstmt1.setInt(1,postid);
        pstmt1.setString(2,user);
        pstmt1.executeUpdate();

        pstmt1.close();
        connect.close();

        return "dashboard";
    }
    
    public boolean checkUser(int postid) throws ClassNotFoundException, SQLException {
        Connection connect = null;
        String user = SessionUtils.getUserName();
        String url = "jdbc:derby://localhost:1527/blog";
        boolean ret = false;
        String username = "blog";
        String password = "blog";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connect = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt1 = connect.prepareStatement("select * from Likes where post_id=? and username=?");
            pstmt1.setInt(1, postid);
            pstmt1.setString(2, user);
            ResultSet rs = pstmt1.executeQuery();

                while (rs.next()) {
                    //result found, means valid inputs
                    ret = true;
                }
            rs.close();
            pstmt1.close();
            connect.close();
            
        } catch (SQLException ex) {
                return false;
        }
        return ret;
    }
}
