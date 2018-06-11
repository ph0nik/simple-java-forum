<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib  prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Forum Title</title>

    </head>
    <body>
    <!-- list of children sections-->
        <c:forEach var="section" items="${forumSection}">
            <!-- element path -->
            <table>
<!-- sections list -->
                <!-- posts list -->

            </table>

        </c:forEach>
        <div></div>


    </body>

</html>