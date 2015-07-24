package test;
import java.sql.*;
public class Jdbc{
    private static String url = "jdbc:mysql://localhost:3306/xjb";
    private static String user = "root";
    private static String password = "root";
    // 获取连接
    public static Connection getconnection() {
        Connection conn = null;
        try {
        	try{
            // 1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
        	}catch(ClassNotFoundException ce){
        		System.err.println("A ClassNotFoundException was caught: " + ce.getMessage());
        	    ce.printStackTrace();
        	}
            // 2.获取连接
            conn = DriverManager.getConnection(url, user, password);
            // 3.获得statement对象
            Statement statement = conn.createStatement();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    // 7.关闭资源
    public static void close(Statement statement, Connection connection) {
        {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}