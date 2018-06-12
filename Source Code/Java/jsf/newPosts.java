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

public class newPosts implements Serializable{
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    public int post_id;
    public String posttitle;
    public String postcontent;
    public String username;

    public newPosts(){
    
    }
    
    public newPosts(int post_id, String posttitle, String postcontent, String username) {
        this.post_id = post_id;
        this.posttitle = posttitle;
        this.postcontent = postcontent;
        this.username = username;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
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
    public static String createPost(newPosts pos) throws ClassNotFoundException, SQLException{
        
        pos.setUsername(SessionUtils.getUserName());
        int saveResult = 0;
        String navigationResult = "";

        try {     

            pstmt = getConnection().prepareStatement("insert into Posts (posttitle, postcontent, username) values (?, ?, ?)");        

            pstmt.setString(1, pos.getPosttitle());
            pstmt.setString(2, pos.getPostcontent());
            pstmt.setString(3, pos.getUsername());

            saveResult = pstmt.executeUpdate();

            connObj.close();
        } 
        
        catch(Exception sqlException) {
            sqlException.printStackTrace();
        }

        if(saveResult !=0) {
            navigationResult = "dashboard";

        } else {

            navigationResult = "newPost";

        }

        return navigationResult;
    }
    
    public String savePost(newPosts pos) throws ClassNotFoundException, SQLException {

        return newPosts.createPost(pos);

    }
    
    
}
