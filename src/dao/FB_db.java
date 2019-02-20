package dao;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//链接到指定数据库
public class FB_db {
    private Connection a;
    private Statement statement;
    private String drivername;
    private String database;
    private String url1;
    private String url2;
    private Integer debugLevel=1;

    public FB_db(String s) {//传数据库名
        getProperty(); //传入参数，给url1, url2, drivername赋值
        database = s;
        String s1 = url1 + s + "?" + url2;
        //jdbc:mysql://localhost:3306/FlightBooking?user=root&password=woaizhoujielun&useUnicode=true&characterEncoding=UTF-8
        String s2 = drivername;
        //com.mysql.jdbc.Driver
        try {
            Class.forName(s2);
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        try {
            a = DriverManager.getConnection(s1);//连接数据库
            statement = a.createStatement();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }

    //关闭数据库
    public void close() {
        try {
            statement.close();
            a.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }

    //执行sql查询操作
    public ResultSet executeQuery(String s) {
        ResultSet resultset = null;
        try {
            if(debugLevel>0){
                System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]"+"FB_db executeQuery:" + s);
            }
            resultset = statement.executeQuery(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return resultset;
    }

    //执行sql更新操作
    public int executeUpdate(String s) {
        int resultset = 0;
        try {
            if(debugLevel>0){
                System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]"+"FB_db executeUpdate:" + s);
            }
            resultset = statement.executeUpdate(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return resultset;
    }

    //获取db.properties和dbip.properties定义内容，传入数据库操作类FB_db的变量中
    //获取与数据库的连接
    public void getProperty() {
        Properties properties = new Properties();
        Properties properties1 = new Properties();
        try {
            InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/conf/db.properties");
            InputStream inputstream1 = getClass().getClassLoader().getResourceAsStream("/conf/dbip.properties");
            properties.load(inputstream);
            properties1.load(inputstream1);
            if (inputstream != null)
                inputstream.close();
            inputstream1.close();
        } catch (IOException ex) {
            System.err.println("Open Propety File Error");
        }
        drivername = properties.getProperty("DRIVER");
        url1 = properties.getProperty("URL1") + properties1.getProperty("IP") + ":3306/";
        url2 = properties.getProperty("URL2");
        //调试级别
        String level = properties.getProperty("debuglevel");
        if(level!=null){
            debugLevel=Integer.parseInt(level);
        }else{
            debugLevel=0;
        }
    }

    public String getTable() {
        return database;
    }
    public void setTable(String s) {
        database = s;
    }
}
