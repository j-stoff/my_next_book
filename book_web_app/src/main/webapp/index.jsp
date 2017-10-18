<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <c:set var="pageTitle" value="Next Book App" scope="session" />
    <c:import url="jsp-snippets/header-tag.jsp"/>
<body>
<h2>Next Book App - For those who don't know what to read next</h2>

    <form action="GET">
        <label for="username">Username:</label>
        <input type="text" placeholder="Username or Email"/>
        <br/>

        <label for="password">Password:</label>
        <input type="text" placeholder="Your password"/>
        <br/>

        <input type="submit" value="Login"/>
        <a href="signup.jsp">
        <input type="button" value="Sign up" class="link"/>
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
</body>
</html>
