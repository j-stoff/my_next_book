<%--
  Created by IntelliJ IDEA.
  User: j-stoff
  Date: 12/8/17
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Login" scope="session" />
    <c:import url="jsp-snippets/header-tag.jsp"/>
</head>
<body>
    <div class="container-fluid">
        <div class="jumbotron">
            <h2>You have logged out</h2>
        </div>
    </div>

    <div class="container">
        <h4><a href="main">Log back in</a></h4>
    </div>
</body>
</html>
