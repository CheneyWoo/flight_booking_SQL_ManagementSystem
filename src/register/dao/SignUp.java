package register.dao;

public class SignUp {
    //数据库字段
    private String userName;
    private String userPsd;
    private String userID;
    private String userCard;
    private String userAge;
    private String userPlace;

    //向数据库传数据
    private String action;
    private String dbName="FlightBooking";
    private String tableName;
    private String type="";
    private String userType="";

    public SignUp(){ }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPsd() { return userPsd; }
    public void setUserPsd(String userPsd) {
        this.userPsd = userPsd;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserCard() {
        return userCard;
    }
    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getUserAge() {
        return userAge;
    }
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserPlace() {
        return userPlace;
    }
    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
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

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

}
