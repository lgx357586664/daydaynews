package com.zr.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: LiGX
 * @Date: 2019/3/11 0011
 */
public class JdbcUtils {
    private static String driverClassName="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql:///newsdb?serverTimezone=UTC";
    private static String user="root";
    private static String password="1234";
    private static Connection connection=null;

    public static Connection getConnection(){
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void close(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
