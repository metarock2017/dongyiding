package org.redrock.util;

import javax.print.attribute.standard.MediaSize;
import java.sql.*;

public class dbUtil {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/java";
    private static final String NAME = "root";
    private static final String PASSWORD = "";

    private static Connection conn = null;


    {
        try {
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //数据库连接
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //数据库连接
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        //数据库操作
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT id FROM users");
        while (result.next()) {
            System.out.println(result.getInt("id"));
        }
    }
}
