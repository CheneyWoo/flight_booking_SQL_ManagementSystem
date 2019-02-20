package utility;

/*
 * 调用步骤：
 * 1.import security.include.LogEvent;
 * 2.LogEvent log=new LogEvent(session);
 * 3.log.log("某某信息");
 * 4.或者：log.log("某某信息","某某操作");
 * 5.或者：log.log("某某信息","某某操作",1);
 */
import java.text.SimpleDateFormat;
import dao.FB_db;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class LogEvent {
	HttpSession session = null;
	String userId="";
	String time="";
	String ip="";

	public LogEvent() {
	}

	public LogEvent(HttpSession session) {
		this.session = session;
		getClientInformation();
	}
	public void setSession(HttpSession session) throws Exception {
		try {
			this.session = session;
			getClientInformation();
		} catch (Exception e) {
			System.out.println("初始化Bean出现错误！" + e.getMessage());
		}
	}
	public void getClientInformation(){
		ip=(String)session.getAttribute("ip");
		userId=(String)session.getAttribute("user_name");
		time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	}
	public void log(String msg) {
		try {
			log(msg, "未定义操作");
		} catch (Exception e) {
			System.out.println("log出现错误！" + e.getMessage());
		}
	}

	public void log(String msg, String operation) {
		log(msg, operation,"security");
	}
	public void log(String msg, String operation,String module) {
		String time = "";
		String operator = (String) session.getAttribute("user_id");
		java.util.Date now = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = formatter.format(now);
		int type=0;
		try {
			ServletContext dbApplication = session.getServletContext();
			FB_db log_db = new FB_db((String) session.getAttribute("unit_db_name"));
			String sql = "insert into public_log(colTime,colType,colContent,colOperation,colUserId,colModule) values(" + "'" + time + "'" + "," + type + "" + ",'" + msg + "'" + ",'" + operation + "'" + ",'" + operator + "'" + ",'"+module+"')";
			int result = log_db.executeUpdate(sql);
			log_db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
