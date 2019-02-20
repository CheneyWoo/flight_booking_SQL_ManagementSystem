package register.file;
import dao.FB_db;
import dao.FB_id;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.DefaultCategoryDataset;
import org.jfree.data.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utility.LogEvent;
import utility.MD5Util;
import register.dao.SignUp;
public class ServletAction extends HttpServlet {
    SignUp su = new SignUp();
    public String module = "flight";
    public String sub = "file";
    public LogEvent flightLog = new LogEvent();
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            getRecord(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*用户注册信息*/
        String userName = request.getParameter("userName");
        String userPsd = request.getParameter("userPsd");
        String userID = request.getParameter("userID");
        String userCard = request.getParameter("userCard");
        String userAge = request.getParameter("userAge");
        String userPlace = request.getParameter("userPlace");
        su.setUserName(userName);
        su.setUserPsd(userPsd);
        su.setUserID(userID);
        su.setUserCard(userCard);
        su.setUserAge(userAge);
        su.setUserPlace(userPlace);
        System.out.println(userName);
        System.out.println(userPsd);
        System.out.println(userID);
        System.out.println(userCard);
        System.out.println(userAge);
        System.out.println(userPlace);
        System.out.println(su.getDbName());
        FB_db insert_db = new FB_db(su.getDbName()); //建立与数据库交互变量
        String sql = "insert into user_info(userID,username,userPsd,userAge,userPlace,userCard,userRole) " +
                "values('"+su.getUserID()+"','"+su.getUserName()+"','"+su.getUserPsd()+"','"+su.getUserAge()+
                "','"+ su.getUserPlace()+"','"+su.getUserCard()+"','normal')";
        int result = insert_db.executeUpdate(sql);
        try {
            JSONObject jsonObj=new JSONObject();
            try {
                jsonObj.put("result",result);
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
        insert_db.close();
        //response.sendRedirect("homepage.jsp");
    }


}