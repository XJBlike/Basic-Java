package test;
import java.sql.*;
public class Jdbc{
    private static String url = "jdbc:mysql://localhost:3306/xjb";
    private static String user = "root";
    private static String password = "root";
    // ��ȡ����
    public static Connection getconnection() {
        Connection conn = null;
        try {
        	try{
            // 1.ע������
            Class.forName("com.mysql.jdbc.Driver");
        	}catch(ClassNotFoundException ce){
        		System.err.println("A ClassNotFoundException was caught: " + ce.getMessage());
        	    ce.printStackTrace();
        	}
            // 2.��ȡ����
            conn = DriverManager.getConnection(url, user, password);
            // 3.���statement����
            Statement statement = conn.createStatement();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }
    // 7.�ر���Դ
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