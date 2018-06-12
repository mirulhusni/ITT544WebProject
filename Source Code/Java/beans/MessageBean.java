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
import javaclass.Message;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jsf.SessionUtils;

@ManagedBean
@SessionScoped
public class MessageBean implements Serializable {
    
    private static final long serialVersionUID = 6081417964063918994L;
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;

    public List<Message> getMessage() throws ClassNotFoundException, SQLException {

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
            
            PreparedStatement pstmt = connect.prepareStatement("select message_id, MessageSender, MessageReceiver, messagecontent from Message where messagereceiver=? order by message_id desc");
            pstmt.setString(1,SessionUtils.getUserName());
            ResultSet rs = pstmt.executeQuery();
            
            List<Message> messages = new ArrayList<Message>();

            while (rs.next()) {

                Message message = new Message();
                message.setMessageID(rs.getInt("message_id"));
                message.setMessageSender(rs.getString("MessageSender"));
                message.setMessageReceiver(rs.getString("MessageReceiver"));
                message.setMessageContent(rs.getString("messagecontent"));
                messages.add(message);

            }

            rs.close();
            pstmt.close();
            connect.close();

            return messages;
    }
    
    public String deleteMessage(int messageid) throws ClassNotFoundException, SQLException {

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

        PreparedStatement pstmt1 = connect.prepareStatement("delete from Message where message_id=?");
        pstmt1.setInt(1,messageid);
        pstmt1.executeUpdate();

        pstmt1.close();
        connect.close();

        return "inbox";
    }
}
