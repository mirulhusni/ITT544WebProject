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
import javaclass.Comments;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CommentsBean implements Serializable {
    
    private static final long serialVersionUID = 6081417964063918994L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;

    public List<Comments> getComments(int postid1) throws ClassNotFoundException, SQLException {

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
            
            PreparedStatement pstmt1 = connect.prepareStatement("select * from Comments where post_id=?");
            pstmt1.setInt(1, postid1);
            ResultSet rs = pstmt1.executeQuery();
            
            List<Comments> comms = new ArrayList<Comments>();

            while (rs.next()) {

                Comments comm = new Comments();
                comm.setCommentid(rs.getInt("comment_id"));
                comm.setCommentcontent(rs.getString("commentcontent"));
                comm.setPostid(rs.getInt("post_id"));
                comm.setUsername(rs.getString("username"));
                comms.add(comm);

            }

            rs.close();
            pstmt1.close();
            connect.close();

            return comms;
    }
    
    public String fetchComment(int commentid) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("select * from Comments where comment_id=?");
        pstmt1.setInt(1,commentid);
        ResultSet rs = pstmt1.executeQuery();

        Comments comments = new Comments();

        while (rs.next()) {

                comments.setCommentid(rs.getInt("comment_id"));
                comments.setCommentcontent(rs.getString("commentcontent"));
                comments.setPostid(rs.getInt("post_id"));
                comments.setUsername(rs.getString("username"));

        }

        sessionMapObj.put("comments1", comments);
        rs.close();
        pstmt1.close();
        connect.close();

        return "editComment";
    }
    
    public static String editComments(Comments comments1) throws ClassNotFoundException, SQLException{

        String navigationResult = "";

        String url = "jdbc:derby://localhost:1527/blog";

        String user = "blog";
        String pass = "blog";
        
        try {     
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connObj = DriverManager.getConnection(url, user, pass);
            pstmt = connObj.prepareStatement("update Comments set commentcontent=? where comment_id=?");        

            pstmt.setString(1, comments1.getCommentcontent());
            pstmt.setInt(2, comments1.getCommentid());

            pstmt.executeUpdate();
            
            connObj.commit();
            connObj.close();
            navigationResult = "dashboard";
            
        } catch(Exception sqlException) {

            sqlException.printStackTrace();

        }

        return navigationResult;
    }
    

    public String saveComment(Comments reg) throws ClassNotFoundException, SQLException {
        
        return CommentsBean.editComments(reg);

    }
    
    public String deleteComment(int commentid) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("delete from Comments where comment_id=?");
        pstmt1.setInt(1,commentid);
        pstmt1.executeUpdate();

        pstmt1.close();
        connect.close();

        return "dashboard";
    }
}
