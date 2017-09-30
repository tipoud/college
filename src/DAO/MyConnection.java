/*package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection{

  private static String url = "jdbc:postgresql://localhost:5432/postgres";
  private static String user = "postgres";
  private static String passwd = "mickey";
  private static Connection connect;
   
  public static Connection getInstance(){
    if(connect == null){
      try {
        connect = DriverManager.getConnection(url, user, passwd);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }      
    return connect;
  }   
}*/

package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection{

  private static String url = "jdbc:mysql://vps321121.ovh.net/collegenew";
  private static String user = "myuser";
  private static String passwd = "rexpoede";
  private static Connection connect;
   
  public static Connection getInstance(){
    if(connect == null){
      try {
    	  Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(url, user, passwd);
      } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }      
    return connect;
  }   
}