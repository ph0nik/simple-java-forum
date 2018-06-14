<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Login</title>

</head>
<body>
<p>
    User Login Page
</p>
<p>
    ${message}
    registered user: ${userName}
</p>
<form method="post" action="/user/login" id="user">
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