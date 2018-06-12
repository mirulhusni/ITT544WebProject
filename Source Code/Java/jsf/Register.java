package jsf;

import static java.awt.SystemColor.window;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;

@ManagedBean

public class Register implements Serializable{
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
	
    private static String username;
    private String password;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
    public static String registerUser(Register reg) throws ClassNotFoundException, SQLException{
        int saveResult = 0;

        String navigationResult = "";

        try {     

            pstmt = getConnection().prepareStatement("insert into Users (username, password, firstname, lastname, gender, email, age) values (?, ?, ?, ?, ?, ?, ?)");        

            pstmt.setString(1, reg.getUsername());

            pstmt.setString(2, reg.getPassword());

            pstmt.setString(3, reg.getFirstname());

            pstmt.setString(4, reg.getLastname());

            pstmt.setString(5, reg.getGender());
            pstmt.setString(6, reg.getEmail());
            pstmt.setInt(7, reg.getAge());

            saveResult = pstmt.executeUpdate();

            connObj.close();

        } catch(Exception sqlException) {

            sqlException.printStackTrace();

        }

        if(saveResult !=0) {

            //HtmlCommandLink popupLink = null;
            //FacesContext fc = FacesContext.getCurrentInstance();
            //fc.addMessage(popupLink.getClientId(), new FacesMessage("The link could not be redirected."));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Register successful."));
            navigationResult = "login";

        } else {

            navigationResult = "register";

        }

        return navigationResult;
    }
    
    public String saveDetails(Register reg) throws ClassNotFoundException, SQLException {

        return Register.registerUser(reg);

    }
    
    
}
