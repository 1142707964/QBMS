package com.qbms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载的时候就初始化了
    static {
        //通过类加载器读取对应的资源
        Properties properties = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库的连接
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }

    //编写查询公共方法
    public static ResultSet execute(Connection conn,PreparedStatement prst,String sql,Object[] params,ResultSet rs) throws SQLException {
        //预编译的SQL，后面直接执行即可，不用传递参数
        prst = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject的占位符是从1开始，而数组是从0开始
            prst.setObject(i+1,params[i]);
        }
        rs = prst.executeQuery();
        return rs;
    }

    //编写增删改公共方法
    public static int execute(Connection conn,PreparedStatement prst,String sql,Object[] params,int updateRows) throws SQLException {
        //预编译的SQL，后面直接执行即可，不用传递参数
        prst = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject()的占位符是从1开始，而数组是从0开始
            prst.setObject(i+1,params[i]);
        }
        updateRows = prst.executeUpdate();
        return updateRows;
    }

    //关闭连接，释放资源
    public static boolean closeConn(Connection conn,PreparedStatement prst,ResultSet rs){
        boolean flag = true;
        if (rs != null){
            try {
                rs.close();
                //GC回收
                rs = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        if (prst != null){
            try {
                prst.close();
                //GC回收
                prst = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        if (conn != null){
            try {
                conn.close();
                //GC回收
                conn = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }

}
