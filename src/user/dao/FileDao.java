package user.dao;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dao.FB_db;

public class FileDao {
	/*
	 * 功能：返回结果集
	 */
	public JSONObject getRecord(UserFile query) throws SQLException, IOException, JSONException {
		//开始查询数据库
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			FB_db query_db = new FB_db(query.getDbName());
			//构造sql语句，根据传递过来的查询条件参数
			String sql="select * from order_info inner join ticket_info where order_info.flightID=ticket_info.flightID and order_info.srcDate=ticket_info.srcDate and userID=" + query.getUserID();
			int count=0;
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("userID"));
				list.add(rs.getString("orderID"));
				list.add(rs.getString("flightID"));
				list.add(rs.getString("srcDate"));
				list.add(rs.getString("srcTime"));
				list.add(rs.getString("valid"));
				list.add(rs.getString("used"));
				list.add(rs.getString("price"));
				list.add(count);
				count=count+1;	//做上下记录导航用
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//jsonList.clear();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}

	public JSONObject deleteRecord(String dbName,String orderID) throws JSONException, SQLException{
		String tableName="order_info";
		FB_db query_db = new FB_db(dbName);
		String sql="update "+tableName+" set valid=0 where orderID="+orderID;
		int result = query_db.executeUpdate(sql);
		System.out.println(sql);
		query_db.close();
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("result_msg","OK");//如果发生错误就设置成"error"等
		jsonObj.put("result_code",result);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
}
