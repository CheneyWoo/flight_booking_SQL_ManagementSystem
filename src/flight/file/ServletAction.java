package flight.file;

import dao.FB_db;
import dao.FB_id;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import flight.dao.FlightFile;
import flight.dao.FileDao;

import utility.LogEvent;
import utility.MD5Util;

//根据前端发来的action指令采取对应措施
//将数据库取出的数据jsonList传到前端
public class ServletAction extends HttpServlet {
    public String module = "flight";
    public String sub = "file";

    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public LogEvent flightLog = new LogEvent();

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            processAction(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        showDebug("processAction收到的action是："+action);
        if (action.equals("get_record")){
            try {
                getRecord(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("delete_record")) {
            try {
                deleteRecord(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("modify_record")) {
            try {
                modifyRecord(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("add_record")) {
            try {
                addRecord(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processError(HttpServletRequest request, HttpServletResponse response,int errorNo,String errorMsg) throws JSONException, IOException{
        String action = request.getParameter("action");
        //errorNo=0->没有错误
        //errorNo=1->action是空值
        //errorNo=2->没有对应的处理该action的函数
        //errorNo=3->session超时
        showDebug("错误信息："+errorMsg+"，代码："+errorNo);
        JSONObject jsonObj=new JSONObject();
        boolean isAjax=true;
        if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
        if(isAjax){
            jsonObj.put("result_code",errorNo);
            jsonObj.put("result_msg",errorMsg);
            jsonObj.put("action",action);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(jsonObj);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            errorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
            String url = errorMsg;
            showDebug(url);
            response.sendRedirect(url);
        }
    }

    public void showDebug(String msg){
        System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/ServletAction]"+msg);
    }

    //从前端获取数据存入变量中，用以调用数据库其他数据
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String existResultset= request.getParameter("exist_resultset");
        if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
        //获取userID和username
        String userId=session.getAttribute("userID")==null?null:(String)session.getAttribute("userID");
        String srcPlace=request.getParameter("srcPlace");
        String dstPlace=request.getParameter("dstPlace");
        String srcDate=request.getParameter("srcDate");
        System.out.println(srcPlace);
        System.out.println(dstPlace);
        System.out.println(srcDate);
        //数据获取完毕，开始和数据库交互
        FlightFile query=new FlightFile();
        query.setDbName("FlightBooking");
        query.setUserID(userId);
        query.setSrcPlace(srcPlace);
        query.setDstPlace(dstPlace);
        query.setSrcDate(srcDate);
        JSONObject jsonObj=null;
        if(existResultset.equals("1")){
            //要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if(session.getAttribute(module+"_"+sub+"_get_record_result")!=null){
                jsonObj=(JSONObject)session.getAttribute(module+"_"+sub+"_get_record_result");
            }else{
                //没有就报错
                jsonObj=new JSONObject();
                jsonObj.put("result_code",10);
                jsonObj.put("result_msg","exist_resultset参数不当，服务器当前没有结果数据,请重新设置！");
            }
        }else{
            //如果是新查询
            FileDao fileDao=new FileDao();
            jsonObj = fileDao.getRecord(query);
            session.setAttribute(module+"_"+sub+"_get_record_result",jsonObj);
        }
        jsonObj.put("userID",userId);
        //数据查询完毕，根据交互方式返回数据
        boolean isAjax=true;
        if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
        if(isAjax){
            response.setContentType("application/json; charset=UTF-8");
            try {
                PrintWriter out = response.getWriter();
                out.print(jsonObj); //数据写入缓冲区
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
            int resultCode=0;
            resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
            //String url = redirectPath+"/"+redirectUrl;
            showDebug(resultMsg);
            //response.sendRedirect(url);
        }
    }

    public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flightID = request.getParameter("flightID");
        String srcDate = request.getParameter("srcDate");
        String srcTime = request.getParameter("srcTime");
        JSONObject jsonObj=null;
        if(flightID!=null){
            FileDao fileDao=new FileDao();
            jsonObj=fileDao.deleteRecord(flightID, srcDate, srcTime);
        }
        boolean isAjax=true;
        if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
        if(isAjax){
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(jsonObj);
                out.flush();
                out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
            resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
            showDebug(resultMsg);
        }
    }

    public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flightID = request.getParameter("flightID");
        String srcDate = request.getParameter("srcDate");
        String dstDate = request.getParameter("dstDate");
        String price = request.getParameter("price");
        String srcTime = request.getParameter("srcTime");
        String dstTime = request.getParameter("dstTime");
        String company = request.getParameter("company");
        String srcPlace = request.getParameter("srcPlace");
        String dstPlace = request.getParameter("dstPlace");
        String srcAirport = request.getParameter("srcAirport");
        String dstAirport = request.getParameter("dstAirport");
        FlightFile flightFile = new FlightFile();
        flightFile.setDbName("FlightBooking");
        flightFile.setFlightID(flightID);
        flightFile.setSrcDate(srcDate);
        flightFile.setDstDate(dstDate);
        flightFile.setPrice(price);
        flightFile.setSrcTime(srcTime);
        flightFile.setDstTime(dstTime);
        flightFile.setCompany(company);
        flightFile.setSrcPlace(srcPlace);
        flightFile.setDstPlace(dstPlace);
        flightFile.setSrcAirport(srcAirport);
        flightFile.setDstAirport(dstAirport);
        JSONObject jsonObj=null;
        if(flightID!=null){
            FileDao fileDao=new FileDao();
            jsonObj=fileDao.modifyRecord(flightFile, flightID, srcDate, srcTime);
        }
        boolean isAjax=true;
        if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
        if(isAjax){
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(jsonObj);
                out.flush();
                out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
            resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
            showDebug(resultMsg);
        }
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String flightID = request.getParameter("flightID");
        String srcDate = request.getParameter("srcDate");
        String dstDate = request.getParameter("dstDate");
        String price = request.getParameter("price");
        String srcTime = request.getParameter("srcTime");
        String dstTime = request.getParameter("dstTime");
        String company = request.getParameter("company");
        String srcPlace = request.getParameter("srcPlace");
        String dstPlace = request.getParameter("dstPlace");
        String srcAirport = request.getParameter("srcAirport");
        String dstAirport = request.getParameter("dstAirport");
        FlightFile flightFile = new FlightFile();
        flightFile.setDbName("FlightBooking");
        flightFile.setFlightID(flightID);
        flightFile.setSrcDate(srcDate);
        flightFile.setDstDate(dstDate);
        flightFile.setPrice(price);
        flightFile.setSrcTime(srcTime);
        flightFile.setDstTime(dstTime);
        flightFile.setCompany(company);
        flightFile.setSrcPlace(srcPlace);
        flightFile.setDstPlace(dstPlace);
        flightFile.setSrcAirport(srcAirport);
        flightFile.setDstAirport(dstAirport);
        JSONObject jsonObj=null;
        if(flightID!=null){
            FileDao fileDao=new FileDao();
            jsonObj=fileDao.addRecord(flightFile);
        }
        boolean isAjax=true;
        if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
        if(isAjax){
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(jsonObj);
                out.flush();
                out.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }else{
            String resultMsg="操作已经执行，请按返回按钮返回列表页面！";
            resultMsg=java.net.URLEncoder.encode(resultMsg, "UTF-8");
            showDebug(resultMsg);
        }
    }

    private String getRecordIndexFromId(String id,JSONObject json) throws JSONException{
        String index="-1";
        JSONArray jsonArr=(JSONArray)json.getJSONArray("aaData");
        for(int i=0;i<jsonArr.length();i++){
            ArrayList list=(ArrayList)jsonArr.get(i);
            if(id.equals(list.get(0)+"")){
                index=list.get(11)+"";
                break;
            }
        }
        return index;
    };
}
