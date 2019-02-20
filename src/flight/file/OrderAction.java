package flight.file;
import dao.FB_db;
import dao.FB_id;
import flight.dao.FlightFile;
import org.json.JSONException;
import org.json.JSONObject;
import utility.LogEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderAction extends HttpServlet {
    FlightFile flightFile=new FlightFile();
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            getRecord(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String userID=session.getAttribute("userID")==null?null:(String)session.getAttribute("userID");
        if(userID == null){
            try {
                JSONObject jsonObj=new JSONObject();
                try {
                    jsonObj.put("flag",0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter out;
                out = response.getWriter();
                out.print(jsonObj);
                System.out.println(jsonObj.toString());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String orderID=request.getParameter("orderID");
            String flightID=request.getParameter("flightID");
            String srcDate=request.getParameter("srcDate");
            String srcTime=request.getParameter("srcTime");
            String valid=request.getParameter("valid");
            String used=request.getParameter("used");
            flightFile.setUserID(userID);
            flightFile.setDbName("FlightBooking");
            flightFile.setOrderID(orderID);
            flightFile.setFlightID(flightID);
            flightFile.setSrcDate(srcDate);
            flightFile.setSrcTime(srcTime);
            flightFile.setValid(valid);
            flightFile.setUsed(used);
            System.out.println(userID);
            System.out.println(orderID);
            System.out.println(flightID);
            System.out.println(srcDate);
            System.out.println(srcTime);
            System.out.println(valid);
            System.out.println(used);
            FB_db insert_db = new FB_db(flightFile.getDbName()); //建立与数据库交互变量
            String sql = "insert into order_info(userID,orderID,flightID,srcDate,srcTime,valid,used) " +
                    "values('"+flightFile.getUserID()+"','"+flightFile.getOrderID()+"','"+flightFile.getFlightID()+"','" +flightFile.getSrcDate()+
                    "','"+ flightFile.getSrcTime()+"','"+flightFile.getValid()+"','"+flightFile.getUsed()+"')";
            int result = insert_db.executeUpdate(sql);
            insert_db.close();

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
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
