package com.zr.framework;


import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 2:02
 */
public class DataSourceUtil {
    private static Properties properties =new Properties();
    private static Connection connection = null;
    static {
        try {
            properties.load(DataSourceUtil.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            DataSource druidDataSource = getDruidDataSource();
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static DataSource getDruidDataSource() {
        DruidDataSource dds = new DruidDataSource();
        dds.configFromPropety(properties);
        return dds;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
