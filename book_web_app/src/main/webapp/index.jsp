<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <c:set var="pageTitle" value="Next Book App" scope="session" />
    <c:import url="/next_book/jsp/jsp-snippets/index-header-tag.jsp"/>
    <link rel="stylesheet" type="text/css" href="index.css" />
<body>
<div class="container-fluid">
<h2 class="jumbotron">Next Book App - For those who don't know what to read next</h2>
    <div class="col-md-2"></div>
    <a href="main">
        <input type="button" value="Login" />
    </a>
    <div class="col-sm-2"></div>
    <a href="signuptoday">
        <input type="button" value="Sign up"/>
    </a>
</div>
</body>
</html>
