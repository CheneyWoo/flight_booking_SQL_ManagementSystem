package flight.dao;

//定义所有所需数据库数据的类
public class FlightFile {
    //数据库字段
    private String flightID;
    private String srcDate;
    private String dstDate;
    private String price;
    private String srcTime;
    private String dstTime;
    private String company;
    private String srcPlace;
    private String dstPlace;
    private String srcAirport;
    private String dstAirport;



    private String valid;
    private String used;
    private String orderID;
    private String userID;
    private String userName;
    private String userRole;
    private String userPsd;
    private String userAge;
    private String userPlace;
    private String userCard;

    //传递条件查询用
    private String action;
    private String dbName;
    private String tableName="";
    private String type="";
    private String timeFrom="";
    private String timeTo="";
    private String timeSelect="";
    private String groupId="";
    private String groupSelect="";
    private String timeInterval="";
    private String userType="";

    public FlightFile() {
    }

    public String getFlightID() {
        return flightID;
    }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getSrcDate() {
        return srcDate;
    }
    public void setSrcDate(String srcDate) {
        this.srcDate = srcDate;
    }

    public String getDstDate() {
        return dstDate;
    }
    public void setDstDate(String dstDate) {
        this.dstDate = dstDate;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getSrcTime() {
        return srcTime;
    }
    public void setSrcTime(String srcTime) {
        this.srcTime = srcTime;
    }

    public String getDstTime() {
        return dstTime;
    }
    public void setDstTime(String dstTime) {
        this.dstTime = dstTime;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getSrcPlace() {
        return srcPlace;
    }
    public void setSrcPlace(String srcPlace) {
        this.srcPlace = srcPlace;
    }

    public String getDstPlace() {
        return dstPlace;
    }
    public void setDstPlace(String dstPlace) {
        this.dstPlace = dstPlace;
    }

    public String getSrcAirport() {
        return srcAirport;
    }
    public void setSrcAirport(String srcAirport) {
        this.srcAirport = srcAirport;
    }

    public String getDstAirport() {
        return dstAirport;
    }
    public void setDstAirport(String dstAirport) {
        this.dstAirport = dstAirport;
    }

    public String getValid() { return valid; }
    public void setValid(String valid) { this.valid = valid; }

    public String getUsed() { return used; }
    public void setUsed(String used) { this.used = used; }

    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserPsd() { return userPsd; }
    public void setUserPsd(String userPsd) {
        this.userPsd = userPsd;
    }

    public String getUserPlace() {
        return userPlace;
    }
    public void setUserPlace(String userAge) {
        this.userPlace = userPlace;
    }

    public String getUserAge() {
        return userAge;
    }
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserCard() {
        return userCard;
    }
    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getDbName() {
        return dbName;
    }
    public void setDbName(String dbName) {  this.dbName = dbName; }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {  this.action = action; }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }

    public String getTimeFrom() {
        return timeFrom;
    }
    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }
    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getTimeSelect() {
        return timeSelect;
    }
    public void setTimeSelect(String timeSelect) {
        this.timeSelect = timeSelect;
    }

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupSelect() {
        return groupSelect;
    }
    public void setGroupSelect(String groupSelect) {
        this.groupSelect = groupSelect;
    }

    public String getTimeInterval() {
        return timeInterval;
    }
    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

}
