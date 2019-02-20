package common;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String userID;
	String userPsd;
	String userName;
	String userAge;
	String userPlace;
	String userRole;
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			System.out.println("请求");
			request.setCharacterEncoding("utf-8");
			String src = "sign-in";
			//String src = request.getParameter("source");
			System.out.println(src);
			if(src.equals("sign-in")){//如果是用于登陆的请求
				doPost(request,response);
				System.out.println("登陆请求");
			}
			else if(src.equals("user-info")){//如果是用户信息修改的请求
				modify_info(request,response);
				System.out.println("修改用户信息请求");
			}
			//doPost(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 该方法用户验证用户名和密码
	public int check(String userid, String userpass) {
		String URL = "jdbc:mysql://localhost:3306/FLightBooking";
		String NAME = "root";
		String PASS = "woaizhoujielun";
		String SQL = "select userPsd from user_info where userID='"+ userid + "'";

		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接对象
			Connection con = DriverManager.getConnection(URL, NAME, PASS);
			// 语句对象
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			// 得到该用户名的密码
			String tmpPass = "";
			if (rs.next()) {
				tmpPass = rs.getString("userPsd");
			}

			// 关闭对象，释放资源
			rs.close();
			stmt.close();
			con.close();

			// 判断密码是否正确
			if (tmpPass.equals(userpass)) {
				return 1;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public int saveInfo(String userID, HttpSession session) {
		String URL = "jdbc:mysql://localhost:3306/FLightBooking";
		String NAME = "root";
		String PASS = "woaizhoujielun";
		String sql="select userName,userAge,userPlace,userRole from user_info where userID='"+ userID + "'";

		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接对象
			Connection con = DriverManager.getConnection(URL, NAME, PASS);
			// 语句对象
			Statement stmt = con.createStatement();
			//获取显示数据
			ResultSet rs = stmt.executeQuery(sql);
			// 得到该用户的信息存入session
			if (rs.next()) {
				userName = rs.getString("userName");
				userAge = rs.getString("userAge");
				userPlace = rs.getString("userPlace");
				userRole = rs.getString("userRole");
				session.setAttribute("userName", userName);
				session.setAttribute("userAge", userAge);
				session.setAttribute("userPlace", userPlace);
				session.setAttribute("userRole", userRole);
			}
			rs.close();
			// 关闭对象，释放资源
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public void modify_info(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String URL = "jdbc:mysql://localhost:3306/FLightBooking";
		String NAME = "root";
		String PASS = "woaizhoujielun";
		String userID_new = request.getParameter("userID");//新手机号
		userName = request.getParameter("userName");
		userAge = request.getParameter("userAge");
		userPlace = request.getParameter("userPlace");
		String sql="UPDATE user_info SET userName='"+userName+"',userAge='"+userAge+"',userPlace='"+userPlace+
				"',userID='" +userID_new+ "' WHERE userID='"+userID+"'";
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接对象
			Connection con = DriverManager.getConnection(URL, NAME, PASS);
			// 语句对象
			Statement stmt = con.createStatement();
			//获取显示数据
			int rs = stmt.executeUpdate(sql);
			userID = userID_new;
			// 得到该用户的信息存入session
			if(rs!=0){
				session.setAttribute("userID", userID);
				session.setAttribute("userName", userName);
				session.setAttribute("userAge", userAge);
				session.setAttribute("userPlace", userPlace);
			}
			// 关闭对象，释放资源
			stmt.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获取用户的用户名和口令
		request.setCharacterEncoding("utf-8");
		userID = request.getParameter("userID");
		userPsd = request.getParameter("userPsd");
		String source = request.getParameter("source");
		System.out.println(source);
		System.out.println("[LoginServlet-doGet]userid=" + userID+ "  userpass=" + userPsd);
		int result = check(userID, userPsd);

		// 如果登录成功，就把用户名写入session中，并且转向success.jsp，
		// 否则转向failure.jsp
		System.out.println("[LoginServlet-doGet]result=" + result);
		if (result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);//账号名
			session.setAttribute("login_status",1);//是否登陆
			saveInfo(userID,session);//将其他信息保存到SESSION
			//response.sendRedirect("index.jsp");
		}
		HttpSession session = request.getSession();
		int login_status = (int)session.getAttribute("login_status");
		try {
			JSONObject jsonObj=new JSONObject();
			try {
				jsonObj.put("flag",result);
				jsonObj.put("login_status",login_status);
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

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
