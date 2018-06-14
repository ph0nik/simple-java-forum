<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib  prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Forum Title</title>

    </head>
    <body>
    ${root.getElementType()} | ${root.getTitle()} | ${root.getDescription()}
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