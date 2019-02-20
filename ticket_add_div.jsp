<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<!DOCTYPE html>
<html>
<head>
    <title>管理员添加页面</title>
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
                <h1><a  href="homepage.jsp"><span>机票</span>SALE</a></h1>
            </div>
            <!--login icon-->
            <div class="dropdown-grids">
                <div id="usericon" style="display:none"><a href="user_list.jsp"><img src="images/user.png" style="height:40px;width:40px"></a></div>
                <div id="loginContainer" style="display:inline-block"><a href="#" id="loginButton"><span>登录/注册</span></a>
                    <!--login box-->
                    <div id="loginBox">
                        <form id="loginForm">
                            <div class="login-grids">
                                <div class="login-grid-left">
                                    <fieldset id="body">
                                        <fieldset>
                                            <label for="phone">Mobile Phone Number</label>
                                            <input type="text" name="phone" id="phone">
                                        </fieldset>
                                        <fieldset>
                                            <label for="password">Password</label>
                                            <input type="password" name="password" id="password">
                                        </fieldset>
                                        <input type="button" id="login" value="Sign in" onclick="signin()">
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
<script type="text/javascript">
    var flightID = localStorage["flightID"];
    var srcDate = localStorage["srcDate"];
    var dstDate = localStorage["dstDate"];
    var price = localStorage["price"];
    var srcTime = localStorage["srcTime"];
    var dstTime = localStorage["dstTime"];
    var company = localStorage["company"];
    var srcPlace = localStorage["srcPlace"];
    var dstPlace = localStorage["dstPlace"];
    var srcAirport = localStorage["srcAirport"];
    var dstAirport = localStorage["dstAirport"];
</script>
<div class="banner-bottom">
    <!-- container -->
    <div class="container">
        <div class="faqs-top-grids">
            <div class="book-grids">
                <div class="col-md-6 book-left">
                    <div style="color:#868686">
                        <h3>添加机票</h3>
                    </div>
                    <div class="book-left-form">
                        <form>
                            <p>航班号</p>
                            <input class="inputClass" type="text" name="flightID" id="flightID" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>出发日期</p>
                            <p id="srcDate"></p>
                            <script type="text/javascript">document.getElementById("srcDate").innerHTML = srcDate;</script>
                            <p>到达日期</p>
                            <input class="inputClass" type="text" name="dstDate" id="dstDate" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>价格</p>
                            <input class="inputClass" type="text" name="price" id="price" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>出发时间</p>
                            <input class="inputClass" type="text" name="srcTime" id="srcTime" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>到达时间</p>
                            <input class="inputClass" type="text" name="dstTime" id="dstTime" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>航空公司</p>
                            <input class="inputClass" type="text" name="company" id="company" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>出发地</p>
                            <p id="srcPlace"></p>
                            <script type="text/javascript">document.getElementById("srcPlace").innerHTML = srcPlace;</script>
                            <p>到达地</p>
                            <p id="dstPlace"></p>
                            <script type="text/javascript">document.getElementById("dstPlace").innerHTML = dstPlace;</script>
                            <p>出发机场</p>
                            <input class="inputClass" type="text" name="srcAirport" id="srcAirport" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <p>到达机场</p>
                            <input class="inputClass" type="text" name="dstAirport" id="dstAirport" onfocus="this.value='';" onblur="if (this.value == '') {this.value ='';}">
                            <label for="checkbox">
                                <input type="button" id="add_button" value="确认添加">
                        </form>
                    </div>
                </div>
                <div class="col-md-6 book-left book-right">
                    <div style="color: #868686">
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
<script src="js/DisplayIcon.js"></script>
<script type="text/javascript" src="scripts/login.js"></script>
<script type="text/javascript">
    $("#add_button").click(function(){
        var flightID = $("#flightID").val();
        var srcDate = document.getElementById("srcDate").innerHTML;
        //srcDate = "2018-12-25";
        var dstDate = $("#dstDate").val();
        var price = $("#price").val();
        var srcTime = $("#srcTime").val();
        var dstTime = $("#dstTime").val();
        var company = $("#company").val();
        //srcPlace = "成都";
        //dstPlace = "武汉";
        var srcPlace = document.getElementById("srcPlace").innerHTML;
        var dstPlace = document.getElementById("dstPlace").innerHTML;
        var srcAirport = $("#srcAirport").val();
        var dstAirport = $("#dstAirport").val();
        if(confirm("您确定要添加此记录吗？")){
            var url="flight_file_servlet_action?action=add_record&flightID="+flightID+"&srcDate="+srcDate+"&dstDate="+dstDate+
                "&price="+price+"&srcTime="+srcTime+"&dstTime="+dstTime+"&company="+company+"&srcPlace="+srcPlace+"&dstPlace="+dstPlace+"&srcAirport="+srcAirport+"&dstAirport="+dstAirport;
            $.ajax({
                type:"post",
                url:url,
                dataType:'json',
                success:function (json) {
                    if(json.result_code == 1){
                        alert("添加成功！");
                    }
                }
            });
        }
    });
</script>
</body>
</html>
