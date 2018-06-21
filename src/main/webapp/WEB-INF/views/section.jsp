<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib  prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item"><a href="#">Library</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Data</li>
                </ol>
            </nav>
        </div>
    </div>
    <!--section list-->
    <c:forEach var="section" items="${section.sectionsList}">
        <div class="row border">

            <div class="col-sm-2 border-right">icon</div>
            <div class="col">
                <a>${section.title}</a>
                <p>${section.description}</p>
            </div>
            <div class="col-sm-1">
                <a class="btn btn-secondary btn-sm" href="#" role="button">Edit</a>
            </div>
            <div class="col-sm-1">
                <a class="btn btn-secondary btn-sm" href="#" role="button">Delete</a>
            </div>
        </div>
    </c:forEach>
    <!--post lists-->
    <c:forEach var="post" items="${section.postsList}">
        <div class="row border">

            <div class="col-sm-2 border-right">icon</div>
            <div class="col">
                <a>${post.title}</a>
            </div>
            <div class="col-sm-1">
                <a class="btn btn-secondary btn-sm" href="#" role="button">Edit</a>
            </div>
            <div class="col-sm-1">
                <a class="btn btn-secondary btn-sm" href="#" role="button">Delete</a>
            </div>
        </div>
    </c:forEach>
    <!--administration buttons-->
    <div class="row align-items-start">
        <div class="col-sm-10">
            <c:url var="new_section_link" value="/section/new">
                <c:param name="parent" value="${section.id}" />
            </c:url>
            <a class="btn btn-secondary btn-sm" href="${new_section_link}" role="button">Add new section</a>
        </div>
    </div>


    <!--footer-->
</div>

<script src="/webjars/bootstrap/4.1.0/js/bootstrap.min.js"
        type="text/javascript"></script>
<script src="/webjars/jquery/3.0.0/jquery.min.js"
        type="text/javascript"></script>
</body>

</html>