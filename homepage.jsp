<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>

<%
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
%>

<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="tickets booking" />
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
<!-- fonts 字体导致加载会慢，考虑是否需要-->
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

	<!-- banner -->
	<div class="banner">
		<!-- container -->
		<div class="container">
		<!-- 左边图像展示 -->
		<div class="col-md-4 banner-left">
		<section class="slider2">
		<div class="flexslider">
		<ul class="slides">
		<li>
		<div class="slider-info">
		<img src="images/1.jpg" alt="">
		</div>
		</li>
		<li>
		<div class="slider-info">
		<img src="images/2.jpg" alt="">
		</div>
		</li>
		<li>
		<div class="slider-info">
		<img src="images/3.jpg" alt="">
		</div>
		</li>
		<li>
		<div class="slider-info">
		<img src="images/4.jpg" alt="">
		</div>
		</li>
		<li>
		<div class="slider-info">
		<img src="images/2.jpg" alt="">
		</div>
		</li>
		</ul>
		</div>
		</section>
		</div>
		<!-- 右边机票查询 -->
		<div class="col-md-8 banner-right">
		<div class="sap_tabs">
			<div class="booking-info" style="color:#fff"><h1><b>机票查询</b></h1></div>
			<div id="horizontalTab" style="display: block; width: 100%; margin: 0px;">
			<div class="resp-tabs-container">
			<div style="display:block">
			<div class="facts">
				<div class="booking-form">
					<!---strat-date-piker---->
					<script>
						$(function() {
							$( "#datepicker,#datepicker1" ).datepicker();
						});
					</script>
					<!---/End-date-piker---->
					<!-- Set here the key for your domain in order to hide the watermark on the web server -->
					<div class="online_reservation">
					<div class="b_room">
					<div class="booking_room">
					<div class="reservation">
						<ul>
							<li  class="span1_of_1 desti">
								<h5>出发地</h5>
								<div class="book_date">
								<form>
								<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
								<input type="text" placeholder="Type Departure City" class="typeahead1 input-md form-control tt-input" id="srcPlace" required="">
								</form>
								</div>
							</li>
							<li  class="span1_of_1 left desti">
								<h5>到达地</h5>
								<div class="book_date">
								<form>
								<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
								<input type="text" placeholder="Type Destination City" class="typeahead1 input-md form-control tt-input" id="dstPlace" required="">
								</form>
								</div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
					<div class="reservation">
						<ul>
							<li  class="span1_of_1">
								<h5>出发日期</h5>
								<div class="book_date">
								<form>
								<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
								<input type="date" value="Select date" id="srcDate" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Select date';}">
								</form>
								</div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
					<div class="reservation">
						<ul>
							<li class="span1_of_3">
								<div class="date_btn">
								<form>
								<input type="submit" value="查 询" id="submit" />
								</form>
								</div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
					</div>
					<div class="clearfix"></div>
					</div>
					</div>
					</div>
					</div>
					</div>
					</div>
					</div>
				</div>
			</div>
			<div class="clearfix"> </div>
		</div>
		<!-- //container -->
	</div>
	<!-- //banner -->
	<script defer src="js/jquery.flexslider.js"></script>
	<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
	<script src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
	<script src="js/DisplayIcon.js"></script>
	<script type="text/javascript" src="scripts/login.js"></script>
	<script type="text/javascript">
		$(function(){
			SyntaxHighlighter.all();
		});
		$(window).load(function(){
		    $('.flexslider').flexslider({
				animation: "slide",
				start: function(slider){
					$('body').removeClass('loading');
				}
		    });
		});
	</script>
	<script>
        $("#submit").click(function(){ //点击查询
            var srcPlace=$("#srcPlace").val();
            var dstPlace=$("#dstPlace").val();
            var srcDate=$("#srcDate").val();
            $.ajax({
                type:"post",
                url:'flight_file_servlet_action',
                data:{srcPlace:srcPlace,dstPlace:dstPlace,srcDate:srcDate},
                dataType:'json',
                success:function () {
                }
            });
            window.open("tickets_list.jsp");
            localStorage.srcPlace = srcPlace;
            localStorage.dstPlace = dstPlace;
            localStorage.srcDate = srcDate;
        });
	</script>
</body>
</html>
