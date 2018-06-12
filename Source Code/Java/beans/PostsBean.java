package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javaclass.Posts;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PostsBean implements Serializable {
    
    private static final long serialVersionUID = 6081417964063918994L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;

    public List<Posts> getPosts() throws ClassNotFoundException, SQLException {

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
            
            PreparedStatement pstmt = connect.prepareStatement("select post_id, posttitle, postcontent, username from Posts order by post_id desc");
            ResultSet rs = pstmt.executeQuery();
            
            List<Posts> posts = new ArrayList<Posts>();

            while (rs.next()) {

                Posts post = new Posts();
                post.setPostid(rs.getInt("post_id"));
                post.setPosttitle(rs.getString("posttitle"));
                post.setPostcontent(rs.getString("postcontent"));
                post.setUsername(rs.getString("username"));
                posts.add(post);

            }

            rs.close();
            pstmt.close();
            connect.close();

            return posts;
    }
    
    public List<Posts> getYourPosts(String user) throws ClassNotFoundException, SQLException {

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
            
            PreparedStatement pstmt = connect.prepareStatement("select post_id, posttitle, postcontent, username from Posts where username=? order by post_id desc");
            pstmt.setString(1,user);
            ResultSet rs = pstmt.executeQuery();
            
            List<Posts> posts = new ArrayList<Posts>();

            while (rs.next()) {

                Posts post = new Posts();
                post.setPostid(rs.getInt("post_id"));
                post.setPosttitle(rs.getString("posttitle"));
                post.setPostcontent(rs.getString("postcontent"));
                post.setUsername(rs.getString("username"));
                posts.add(post);

            }

            rs.close();
            pstmt.close();
            connect.close();

            return posts;
    }
    
    public String fetchPost(int postid) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("select * from Posts where post_id=?");
        pstmt1.setInt(1,postid);
        ResultSet rs = pstmt1.executeQuery();

        Posts posts = new Posts();

        while (rs.next()) {

                posts.setPostid(rs.getInt("post_id"));
                posts.setPosttitle(rs.getString("posttitle"));
                posts.setPostcontent(rs.getString("postcontent"));
                posts.setUsername(rs.getString("username"));

        }

        sessionMapObj.put("posts1", posts);
        rs.close();
        pstmt1.close();
        connect.close();

        return "editPost";
    }
    
    public static String editPosts(Posts posts1) throws ClassNotFoundException, SQLException{

        String navigationResult = "";

        String url = "jdbc:derby://localhost:1527/blog";

        String user = "blog";
        String pass = "blog";
        
        try {     
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connObj = DriverManager.getConnection(url, user, pass);
            pstmt = connObj.prepareStatement("update Posts set posttitle=?, postcontent=? where post_id=?");        

            pstmt.setString(1, posts1.getPosttitle());
            pstmt.setString(2, posts1.getPostcontent());
            pstmt.setInt(3, posts1.getPostid());

            pstmt.executeUpdate();
            
            connObj.commit();
            connObj.close();
            navigationResult = "dashboard";
            
        } catch(Exception sqlException) {

            sqlException.printStackTrace();

        }

        return navigationResult;
    }
    

    public String savePost(Posts reg) throws ClassNotFoundException, SQLException {
        
        return PostsBean.editPosts(reg);

    }
    
    public String deletePost(int postid) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("delete from Posts where post_id=?");
        pstmt1.setInt(1,postid);
        pstmt1.executeUpdate();

        pstmt1.close();
        connect.close();

        return "dashboard";
    }
}
