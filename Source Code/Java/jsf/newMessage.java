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

public class newMessage implements Serializable{
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    public int messageid;
    public String messagesender;
    public String messagereceiver;
    public String messagecontent;

    public newMessage(){
    
    }

    public newMessage(int messageid, String messagesender, String messagereceiver, String messagecontent) {
        this.messageid = messageid;
        this.messagesender = messagesender;
        this.messagereceiver = messagereceiver;
        this.messagecontent = messagecontent;
    }

    public int getMessageid() {
        return messageid;
    }

    public String getMessagesender() {
        return messagesender;
    }

    public String getMessagereceiver() {
        return messagereceiver;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public void setMessagesender(String messagesender) {
        this.messagesender = messagesender;
    }

    public void setMessagereceiver(String messagereceiver) {
        this.messagereceiver = messagereceiver;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
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
    public static String createMessage(newMessage pos) throws ClassNotFoundException, SQLException{
        
        pos.setMessagesender(SessionUtils.getUserName());
        int saveResult = 0;
        String navigationResult = "";

        try {     

            pstmt = getConnection().prepareStatement("insert into Message (messagesender, messagereceiver, messagecontent) values (?, ?, ?)");        

            pstmt.setString(1, pos.getMessagesender());
            pstmt.setString(2, pos.getMessagereceiver());
            pstmt.setString(3, pos.getMessagecontent());

            saveResult = pstmt.executeUpdate();

            connObj.close();
        } 
        
        catch(Exception sqlException) {
            sqlException.printStackTrace();
        }

        if(saveResult !=0) {
            pos.setMessagecontent("");
            navigationResult = "dashboard";

        } else {

            navigationResult = "dashboard";

        }

        return navigationResult;
    }
    
    public String saveMessage(newMessage pos) throws ClassNotFoundException, SQLException {

        return newMessage.createMessage(pos);

    }
}