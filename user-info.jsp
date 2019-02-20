<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    session = request.getSession();
    String existResultset= request.getParameter("exist_resultset");
    if((existResultset==null) ||(existResultset.equals("null") || existResultset.isEmpty())) existResultset="0";
    String userID=session.getAttribute("userID")==null?null:(String)session.getAttribute("userID");
    String userName = session.getAttribute("userName")==null?null:(String)session.getAttribute("userName");
    String userAge =session.getAttribute("userAge")==null?null:(String)session.getAttribute("userAge");
    String userPlace = session.getAttribute("userPlace")==null?null:(String)session.getAttribute("userPlace");
%>
<!DOCTYPE html>
<html>
<head>
    <title>个人中心</title>
    <!-- Custom Theme files -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Govihar Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
	Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- //Custom Theme files -->
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
    <link href="css/style.css" type="text/css" rel="stylesheet" media="all">
    <link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" />
    <link type="text/css" rel="stylesheet" href="css/JFFormStyle-1.css" />
    <!-- js -->
    <script src="js/jquery.min.js"></script>
    <script src="js/modernizr.custom.js"></script>
    <!-- //js -->
    <!-- fonts -->
    <!-- //fonts -->
    <script type="text/javascript">
        $(document).ready(function () {
            $('#horizontalTab').easyResponsiveTabs({
                type: 'default', //Types: default, vertical, accordion
                width: 'auto', //auto or any width like 600px
                fit: true   // 100% fit in a container
            });
        });
    </script>
    <!--pop-up-->
    <script src="js/menu_jquery.js"></script>
    <!--//pop-up-->
</head>
<body>
<!--header-->
<div class="header">
    <div class="container">
        <div class="header-grids">
            <!--logo-->
            <div class="logo">
                <h1><a  href="homepage.html"><span>机票</span>SALE</a></h1>
            </div>
            <!--login icon-->
            <div class="dropdown-grids">
                <div id="loginContainer"><a href="#" id="loginButton"><span>登录/注册</span></a>
                    <!--login box-->
                    <div id="loginBox">
                        <form id="loginForm">
                            <div class="login-grids">
                                <div class="login-grid-left">
                                    <fieldset id="body">
                                        <fieldset>
                                            <label for="email">Email Address</label>
                                            <input type="text" name="email" id="email">
                                        </fieldset>
                                        <fieldset>
                                            <label for="password">Password</label>
                                            <input type="password" name="password" id="password">
                                        </fieldset>
                                        <input type="submit" id="login" value="Sign in">
                                        <label for="checkbox"><input type="checkbox" id="checkbox"> <i>Remember me</i></label>
                                    </fieldset>
                                    <!--此处链接应改为修改密码界面-->
                                    <span><a href="forget-password.html">Forgot your password?</a></span>
                                    <p>New account? <a href="signup.html"><u>Signup</u></a></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix"> </div>
    </div>
</div>
<!--//header-->
<!-- banner-bottom -->
<div class="banner-bottom" style="height:91vh;">
    <!-- container -->
    <div class="container" style="margin-top:5vh">
        <div class="faqs-top-grids">
            <div class="book-grids">
                <div class="col-md-6 book-left">
                    <div style="color:#868686">
                        <h3>个人中心</h3>
                    </div>
                    <div class="book-left-form">
                        <form>
                            <p>您的姓名</p>
                            <input id="userName" value=<%=userName%> type="text" readonly="readonly" style="font-size: 15px;border:none" ></input>
                            <p>您的手机号码</p>
                            <input id="userID" value=<%=userID%> type="text" readonly="readonly" style="font-size: 15px;border:none" ></input>
                            <p>您的年龄</p>
                            <input id="userAge" value=<%=userAge%> type="text" readonly="readonly" style="font-size: 15px;border:none"></input>
                            <p>您的所在地</p>
                            <input id="userPlace" value=<%=userPlace%> type="text" readonly="readonly" style="font-size: 15px;border:none"></input>
                            <input type="button" id="change_info" value="修改信息">
                            <input type="button" id="change_psd" value="修改密码" onclick='window.open("modify-password.html")'>
                        </form>
                    </div>
                </div>
                <div class="col-md-6 book-left book-right">
                    <div style="color: #565656">
                        <h3>欢迎回家</h3><br/>
                        <h5>我们将给予您最贴心的服务！</h5><br/>
                        <a style="display: block" href="signup.html" ><u>退出登录</u></a><br/¥>
                        <a href="user-purchase.html" ><u>查看个人订单</u></a><br/>
                    </div>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>
    </div>
    <!-- //container -->
</div>

<script defer src="js/jquery.flexslider.js"></script>
<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<script>
    function modify(s){
        $("#"+s).attr("readonly",false);
        $("#"+s).css("border","1px solid #BDBDBD");
    }
    function ensure(s){
        $("#"+s).attr("readonly",true);
        $("#"+s).css("border","none");
    }
    $("#change_info").click(function(){
        modify("userName");
        modify("userID");
        modify("userAge");
        modify("userPlace");
        //按钮文字改为：确认修改；变红
        this.value="确认修改";
        $("#change_info").css({"background-color":"red","border-color":"red"});

        //点击确认修改后，边框消失
        $("#change_info").click(function(){
            ensure("userName");
            ensure("userID");
            ensure("userAge");
            ensure("userPlace");
            this.value="修改信息";
            $("#change_info").css({"background-color":"#6fd508","border-color":"#6fd508"});
            var userName=$("#userName").val();
            var userID=$("#userID").val();
            var userAge=$("#userAge").val();
            var userPlace=$("#userPlace").val();
            var src = "user-info";
            $.ajax({
                type:"post",
                url:'common_login_servlet',
                data:{userName:userName,userID:userID,userAge:userAge,userPlace:userPlace,source:src},
                dataType:'json',
                success:function () {
                    alert("修改成功");
                }
            });
        });
    });
</script>
</body>
</html>

