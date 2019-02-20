package flight.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import dao.FB_db;

//从数据库取所需数据存入列表，返回jsonList
public class FileDao {
    //返回结果集
    public JSONObject getRecord(FlightFile query) throws SQLException, IOException, JSONException {
        String tableName1="ticket_info";
        String tableName2="seat_info";
        String tableName3="flight_info";
        //开始查询数据库
        String resultMsg="OK";
        int resultCode=0;
        List jsonList = new ArrayList();
        try {
            FB_db query_db = new FB_db(query.getDbName()); //建立与数据库交互变量
            String sql="SELECT * FROM ticket_info JOIN flight_info " +
                    "ON ticket_info.flightID = flight_info.flightID" +
                    " WHERE " +
                    "srcPlace = " + "'" + query.getSrcPlace() + "'" + " AND " +
                    "dstPlace = " + "'" + query.getDstPlace() + "'" +
                    " AND ticket_info.srcDate = " + "'" + query.getSrcDate() + "'";
            int count=0;
            ResultSet rs = query_db.executeQuery(sql); //由自定义的sql语句执行数据库查询操作
            while (rs.next()) {
                List list = new ArrayList(); //创建列表对象存入数据库表数据
                list.add(rs.getString("flightID"));
                list.add(rs.getString("srcDate"));
                list.add(rs.getString("dstDate"));
                list.add(rs.getString("price"));
                list.add(rs.getString("srcTime"));
                list.add(rs.getString("dstTime"));
                list.add(rs.getString("company"));
                list.add(rs.getString("srcPlace"));
                list.add(rs.getString("dstPlace"));
                list.add(rs.getString("srcAirport"));
                list.add(rs.getString("dstAirport"));
                //list.add(rs.getString("isFull"));
                //list.add(rs.getString("passengerNum"));
                //list.add(rs.getString("seatNum"));
                //权限设置
                /*
                if(query.getUserID() != null && query.getUserID().equals(rs.getString("userID"))){
                    list.add("1");
                }else{
                    list.add("0");
                }
                */
                list.add(count);
                count=count+1;	//做上下记录导航用
                System.out.println(list);
                jsonList.add(list); //传入jsonList
            }
            rs.close();
            query_db.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode=10;
            resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
        }
        //数据库查询完毕，得到了json数组jsonList
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("aaData",jsonList);
        jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        //System.out.println(jsonObj);
        return jsonObj;
    }

    public JSONObject deleteRecord(String flightID,String srcDate,String srcTime) throws JSONException, SQLException{
        String tableName="ticket_info";
        FB_db query_db = new FB_db("FlightBooking");
        String sql="delete from "+tableName+" where flightID="+flightID+" and srcDate=" + srcDate + " and srcTime=" + srcTime;
        int result = query_db.executeUpdate(sql);
        System.out.println(sql);
        query_db.close();
        //下面开始构建返回的json
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("result_msg","OK");//如果发生错误就设置成"error"等
        jsonObj.put("result_code",result);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    public JSONObject modifyRecord(FlightFile flightFile,String flightID,String srcDate,String SrcTime) throws JSONException, SQLException{
        FB_db query_db = new FB_db("FlightBooking");
        String sql1="UPDATE ticket_info SET flightID='"+flightFile.getFlightID()+"', srcDate='"+flightFile.getSrcDate() +
                "', dstDate='" + flightFile.getDstDate() + "', price='" + flightFile.getPrice() +
                "', srcTime='" + flightFile.getSrcTime() + "', dstTime='" + flightFile.getDstTime() +
                "' where flightID='" + flightID + "'";
        int result1 = query_db.executeUpdate(sql1);
        System.out.println(sql1);
        String sql2="UPDATE flight_info SET company="+flightFile.getCompany()+"', flightID='"+flightFile.getFlightID() +
                "', srcPlace='" + flightFile.getSrcPlace() + "', dstPlace='" + flightFile.getDstPlace() +
                "', srcAirport='" + flightFile.getSrcAirport() + "', dstAirport='" + flightFile.getDstAirport() +
                "' where flightID='" + flightID + "' and srcDate='" + srcDate + "' and srcTime='" + SrcTime + "'";
        int result2 = query_db.executeUpdate(sql2);
        System.out.println(sql2);
        query_db.close();

        //下面开始构建返回的json
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("result_msg","OK");//如果发生错误就设置成"error"等
        jsonObj.put("result_code",result1);//返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code",result2);
        return jsonObj;
    }

    public JSONObject addRecord(FlightFile flightFile) throws JSONException, SQLException{
        FB_db query_db = new FB_db("FlightBooking");
        String sql1="INSERT INTO ticket_info(flightID,srcDate,dstDate,price,srcTime,dstTime) "+ "values('"+flightFile.getFlightID()+"','"+flightFile.getSrcDate() +
                "','"+ flightFile.getDstDate() +"','"+ flightFile.getPrice() +"','"+ flightFile.getSrcTime() +"','"+ flightFile.getDstTime()+"')";
        int result1 = query_db.executeUpdate(sql1);
        System.out.println(sql1);
        String sql2="INSERT INTO flight_info(company,flightID,srcPlace,dstPlace,srcAirport,dstAirport) "+ "values('"+flightFile.getCompany()+"','"+flightFile.getFlightID() +
                "','"+ flightFile.getSrcPlace() +"','"+ flightFile.getDstPlace() +"','"+ flightFile.getSrcAirport() +"','"+ flightFile.getDstAirport()+"')";
        int result2 = query_db.executeUpdate(sql2);
        System.out.println(sql2);
        query_db.close();

        //下面开始构建返回的json
        JSONObject jsonObj=new JSONObject();
        jsonObj.put("result_msg","OK");//如果发生错误就设置成"error"等
        jsonObj.put("result_code",result1);//返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code",result2);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    private String createGetRecordSql(FlightFile query) {
        String sql = "";
        String where = "";
        sql="select * from "+query.getTableName();
        return sql;
    }

}
