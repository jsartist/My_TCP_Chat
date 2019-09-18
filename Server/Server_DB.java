package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server_DB {
 private String driver = "org.mariadb.jdbc.Driver";
 private String url = "jdbc:mariadb://localhost:3306/clog";
 private String id = "root";
 private String pw = "your_password";
 private String time;
 private String nick;
 private String chat;
 Connection con;
 PreparedStatement pre;

 public Server_DB() {
  try {
   Class.forName(driver);
   con = DriverManager.getConnection(url, id, pw);
  } catch (ClassNotFoundException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }

 public void dbclose() {
  try {
   if (pre != null)
    pre.close();
  } catch (Exception e) {
   System.out.println(e + "=> dbClose fail");
  }
 }

 public void setting(String time, String nick, String chat) {
  this.time = time;
  this.nick = nick;
  this.chat = chat;
  Clog();
 }

 public void Clog() {
  String sql = "insert into clog values(?, ?, ?)";
  try {
   pre = con.prepareStatement(sql);
   pre.setString(1, time);
   pre.setString(2, nick);
   pre.setString(3, chat);
   pre.executeUpdate();
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   dbclose();
  }
 }

 public static void main(String[] args) {
  Server_DB js = new Server_DB();
 }
}