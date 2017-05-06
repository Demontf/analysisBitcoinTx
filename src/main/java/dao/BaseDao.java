package dao;

import util.ResourceUtils;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by demontf on 17/3/28.
 */
public class BaseDao {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * 写一个连接数据库的方法
     */
    public Connection getConnection(){

//        String url="jdbc:mysql://localhost:3306/txanalysis";
//        String userName="root";
//        String password="";

        Properties properties = null;
        try {

            properties = ResourceUtils.getNodeConfig();
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到驱动！");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = (String) properties.get("db.mysql.url");
        String userName = (String) properties.get("db.mysql.username");
        String password = (String) properties.get("db.mysql.password");
        try {
            conn = DriverManager.getConnection(url, userName, password);
//            if (conn != null) {
//                System.out.println("connection successful");
//            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
