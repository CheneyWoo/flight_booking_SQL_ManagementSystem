<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>

<%
    String id = request.getParameter("id");
    String existResultset = request.getParameter("exist_resultset");
    String userRole = (String)session.getAttribute("userRole");
%>

<!DOCTYPE html>
<html>
<head>
<title>查询列表</title>
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
	<div class="banner-bottom">
		<!-- container -->
		<div class="container">
			<!-- 顶部筛选框 -->
			<div class="faqs-top-grids">
				<!--single-page-->
				<div class="single-page">
					<div class="booking_room">
						<div class="reservation">
							<ul>
								<li  class="span1_of_1 desti">
									<h5 style="color: #000;">出发地</h5>
									<div class="book_date">
									<form>
									<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
									<input type="text" placeholder="Type Departure City" class="typeahead1 input-md form-control tt-input" id="srcPlace" required="">
									</form>
									</div>
								</li>
								<li  class="span1_of_1 left desti">
									<h5 style="color: #000;">到达地</h5>
									<div class="book_date">
									<form>
									<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
									<input type="text" placeholder="Type Destination City" class="typeahead1 input-md form-control tt-input" id="dstPlace" required="">
									</form>
									</div>
								</li>
								<li  class="span1_of_1 left">
									<h5 style="color: #000;">出发日期</h5>
									<div class="book_date">
									<form>
									<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
									<input type="date" value="Select date" id="srcDate" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Select date';}">

									</form>
									</div>
								</li>
								<li class="span1_of_3 left">
									<form>
										<div class="book-button-column" id="search-button">
											<input type="submit" id="submit" value="查询" />
										</div>
									</form>
								</li>
								<div class="clearfix"></div>
							</ul>
						</div>
					</div>

					<input type="hidden" id="id" name="id" value="<%=id%>" />
					<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset%>" />
					<div class="clearfix"></div>
				</div>
				<!--//single-page-->
			</div>
			<!--机票列表-->
			<div class="c-rooms">
				<div class="form-group">
					<input type="hidden" id="userRole" name="userRole" value="<%=userRole%>" />
					<label id="#record_list_tip" class="col-mod-12">
					</label>
				</div>
				<div class="form-group">
					<div id="record_list_div"></div>
					<div id="add"></div>
				</div>
			</div>
		</div>
		<!-- //container -->
	</div>

	<script defer src="js/jquery.flexslider.js"></script>
	<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
	<script src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
	<script type="text/javascript" src="scripts/ticket_list.js"></script>
	<script src="js/DisplayIcon.js"></script>
	<script type="text/javascript" src="scripts/login.js"></script>
</body>
</html>
