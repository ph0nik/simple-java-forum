<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Forum Title</title>

    </head>
    <body>
        <form method="post" action="/root/create" id="root">
            Section title:<br>
            <input type="text" name="title" required placeholder="What's the forum title?"><br>
            Section description<br>
            <textarea form_id="root" required cols="100" rows="5" placeholder="What's the description of this forum" name="description"></textarea><br>
            <input type="submit">
        </form>



    </body>

</html>