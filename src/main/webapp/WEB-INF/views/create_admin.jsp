<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib  prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Forum Title</title>

</head>
<body>
<p>Administrator registration page</p>
<p>${message}</p>
<form method="post" action="/admin/create" id="user">
    Admin Name<br>
    <input type="text" name="username" required><br>
    Admin Password<br>
    <input type="password" name="pass" required><br>
    Admin email<br>
    <input type="text" name="email" required><br>
    <input type="submit">
</form>
<div>${errorMessage}</div>


</body>

</html>