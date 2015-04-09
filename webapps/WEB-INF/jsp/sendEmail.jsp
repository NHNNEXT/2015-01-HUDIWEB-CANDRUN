<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/sendEmail.cdr" method="post">
	<input name="email_address">
	<input name="requestor" value="sendEmail.jsp">
	<input name="goal_id" value="1">	
	<input type="submit" value="제출">
</form>
</body>
</html>