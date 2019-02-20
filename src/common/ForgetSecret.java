package common;

import org.json.JSONException;
import org.json.JSONObject;

import dao.FB_db;
import utility.LogEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ForgetSecret extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            getRecord(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        /*用户注册信息*/
        String resultMsg="OK";
        int resultCode=0;
        System.out.println("获取用户ID");
        String userID = request.getParameter("userID");
        String userCard = request.getParameter("userCard");
        String userPsd = request.getParameter("userPsd");
        try {
            FB_db query_db = new FB_db("FlightBooking"); //建立与数据库交互变量
            String sql = "select userID from user_info where userID='"+ userID + "' and userCard='"+ userCard+"'";
            String sql2 = "UPDATE user_info SET userPsd='"+userPsd+"' WHERE userID='"+userID+"'";
            ResultSet rs = query_db.executeQuery(sql);
            if(rs.next()){
                System.out.println(userID);
                System.out.println(rs.getString("userID"));
                int result = query_db.executeUpdate(sql2);
                try {
                    JSONObject jsonObj=new JSONObject();
                    try {
                        jsonObj.put("flag",result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    response.setContentType("application/json; charset=UTF-8");
                    PrintWriter out;
                    out = response.getWriter();
                    out.print(jsonObj);
                    System.out.println(jsonObj.toString());
                    System.out.println("写入成功");
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*SmsBase msg =  new SmsBase();
                msg.setMoblie(userID);
                msg.setValidID("123456");
                msg.sendMsg();*/
            }
            rs.close();
            query_db.close();
        }catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode=10;
            resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
        }

    }



}
