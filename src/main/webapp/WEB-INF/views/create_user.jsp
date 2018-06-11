<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib  prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Forum Title</title>

</head>
<body>
<form method="post" action="/user/create" id="user">
    User Name<br>
    <input type="text" name="username" required><br>
    User Password<br>
    <input type="password" name="pass" required><br>
    User email<br>
    <input type="text" name="email" required><br>
    <input type="submit">
</form>


</body>

</html>