package jsf;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.bean.ManagedBean;

@ManagedBean

public class newComments implements Serializable{
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    public int postid;
    public int commentid;
    public String commentcontent;
    public String username;

    public newComments(){
    
    }

    public newComments(int postid, int commentid, String commentcontent, String username) {
        this.postid = postid;
        this.commentid = commentid;
        this.commentcontent = commentcontent;
        this.username = username;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setComment_id(int commentid) {
        this.commentid = commentid;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public static Connection getConnection(){ 

        try{ 

            Class.forName("org.apache.derby.jdbc.ClientDriver");    

            String db_url ="jdbc:derby://localhost:1527/blog",

                    db_userName = "blog",

                    db_password = "blog";

            connObj = DriverManager.getConnection(db_url,db_userName,db_password); 

        } catch(Exception sqlException) { 

            sqlException.printStackTrace();

        } 

        return connObj;

    }
    public static String createComment(newComments pos, int postid) throws ClassNotFoundException, SQLException{
        
        pos.setUsername(SessionUtils.getUserName());
        pos.setPostid(postid);
        int saveResult = 0;
        String navigationResult = "";

        try {     

            pstmt = getConnection().prepareStatement("insert into Comments (commentcontent, post_id, username) values (?, ?, ?)");        

            pstmt.setString(1, pos.getCommentcontent());
            pstmt.setInt(2, pos.getPostid());
            pstmt.setString(3, pos.getUsername());

            saveResult = pstmt.executeUpdate();

            connObj.close();
        } 
        
        catch(Exception sqlException) {
            sqlException.printStackTrace();
        }

        if(saveResult !=0) {
            pos.setCommentcontent("");
            navigationResult = "dashboard";

        } else {

            navigationResult = "dashboard";

        }

        return navigationResult;
    }
    
    public String saveComment(newComments pos, int postid) throws ClassNotFoundException, SQLException {

        return newComments.createComment(pos, postid);

    }
}