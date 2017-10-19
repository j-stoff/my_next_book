<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <c:set var="pageTitle" value="Next Book App" scope="session" />
    <c:import url="jsp-snippets/header-tag.jsp"/>

<body>
<div class="container-fluid">
<h2 class="jumbotron">Next Book App - For those who don't know what to read next</h2>
    <div class="col-md-2"></div>
    <form method="GET" action="main.jsp" class="col-sm-4">
        <div class="row">
        <label for="username">Username:</label>
        <input type="text" placeholder="Username or Email"/>
        </div>

        <div class="row">
        <label for="password">Password:</label>
        <input type="text" placeholder="Your password"/>
        </div>


        <input type="submit" value="Login" class="col-sm-3"/>
        <div class="col-sm-2"></div>
        <a href="signup.jsp">
        <input type="button" value="Sign up" class="link col-sm-3"/>
        </a>
    </form>
    <!--
    <a href="login.jsp">Login</a>
    <hr/>
    -->
    <!-- For secure login tests
    <a href="edit.jsp">Edit a page</a>
    -->
    <!--
    <hr/>
    <a href="signup.jsp">Sign Up</a>

    <br/>
    -->
    <!-- For going to the main page
    <a href="main.jsp">Main Page</a>
    -->
</div>
</body>
</html>
