<%@page contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8" />
<%
    session.removeAttribute("userID");
    session.setAttribute("login_status",0);
%>
<script>
    window.location="homepage.jsp";
</script>
