<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	String orderID = request.getParameter("orderID");
	String existResultset = request.getParameter("exist_resultset");
%>


<!DOCTYPE html>
<html>
<head>
<title>用户订单</title>
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="tickets booking" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //Custom Theme files -->
<link href="../../css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
<link href="../../css/style.css" type="text/css" rel="stylesheet" media="all">
<link rel="stylesheet" href="../../css/flexslider.css" type="text/css" media="screen" />
<link type="text/css" rel="stylesheet" href="../../css/JFFormStyle-1.css" />
<!-- js -->
<script src="../../js/jquery.min.js"></script>
<script src="../../js/modernizr.custom.js"></script>
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
<script src="../../js/menu_jquery.js"></script>
<!--//pop-up-->	
<style>
	.book-button-column a{
		background: #f00;
	}
	.avg-rate{
		padding:2em;
	}
	.book-button-column{
		padding:3em 2em 0;
	}
</style>
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
					<div id="loginContainer" style="display:inline-block"><a href="logout.jsp" id="logoutButton"><span>退出登陆</span></a></div>
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
			
			<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form" action="#">
				<input type="hidden" id="action" name="action" value="get_record" />
				<input type="hidden" id="orderID" name="orderID" value="<%=orderID%>" />
				<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset%>" />
				<div class="c-rooms">
					<div class="form-group">
						<div class="col-md-12">
							<div id="record_list_div"></div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- //container -->
	</div>
	<!-- //banner-bottom -->
	<!-- footer -->
	
	<!-- //footer -->
	
	</div>
	<script defer src="../../js/jquery.flexslider.js"></script>
	<script src="../../js/easyResponsiveTabs.js" type="text/javascript"></script>
	<script src="../../js/jquery-ui.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>
	<script type="text/javascript" src="scripts/user_list.js"></script>

</body>
</html>