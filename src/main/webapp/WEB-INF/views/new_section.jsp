<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<link rel="stylesheet"
      href="/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Forum Title</title>

</head>
<body>
<div class="container">
    <!--header-->
    <nav class="navbar navbar-dark bg-dark nav-fill text-light">
        <span>${section.getTitle()}</span>
        <span>
        <a>Login</a>&nbsp;/&nbsp;
        <a>Register</a>
        </span>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-primary my-2 my-sm-0" type="submit">Search</button>
        </form>
    </nav>
    <!--body-->
    <div class="row">
        <div class="col-sm-12">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <c:set var="maxIndex" scope="page" value="${parentSection.getElementPathAsc().size()}"/>
                    <c:if test="${parentSection.getElementPathAsc().isEmpty()}">
                        <li class="breadcrumb-item"><a href="/forum">Home page</a></li>
                    </c:if>
                    <c:forEach var="path" items="${parentSection.getElementPathAsc()}">
                        <c:set var="elementIndex" scope="page" value="${parentSection.getElementPathAsc().indexOf(path)}"/>
                        <c:if test="${elementIndex == 0}">
                            <li class="breadcrumb-item">
                                <a href="/forum">Home page</a>
                            </li>
                        </c:if>

                        <c:if test="${elementIndex > 0 && elementIndex < maxIndex}">
                            <li class="breadcrumb-item">
                                <a href="/section/${path.pathElementId}">${path.pathElementName}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                    <li class="breadcrumb-item">
                        <a href="/section/${parentSection.id}">${parentSection.title}</a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">Add new section</li>
                </ol>
            </nav>
        </div>
    </div>

    <form method="post" action="/section/new?parentId=${parentSection.id}" id="newsection">
        Section title:<br>
        <input type="text" name="title" required placeholder="What's this section title?"><br>
        Section description<br>
        <textarea form_id="newsection" required cols="100" rows="5" placeholder="What's the topic of this section?"
                  name="description"></textarea><br>
        <input type="submit">
    </form>


    <!--footer-->
</div>
</body>

</html>