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
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item"><a href="#">Library</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Data</li>
                </ol>
            </nav>
        </div>
    </div>

    <form method="post" action="/section/new" id="newsection">
        Section title:<br>
        <input type="text" name="title" required placeholder="What's this section title?"><br>
        Section description<br>
        <input type="hidden" name="parent" value="${parentSection}">
        <textarea form_id="newsection" required cols="100" rows="5" placeholder="What's the topic of this section?"
                  name="description"></textarea><br>
        <input type="submit">
    </form>


    <!--footer-->
</div>
</body>

</html>