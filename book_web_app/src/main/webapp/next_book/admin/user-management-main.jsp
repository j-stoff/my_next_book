<%--
  Created by IntelliJ IDEA.
  User: j-stoff
  Date: 12/11/17
  Time: 2:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="User Management" scope="session" />
</head>
<body>
    <h2>What operations would you like to preform?</h2>
    <h3><a href="addUser">Add Users</a></h3>
    <h3><a href="deleteUser">Delete Users</a></h3>

</body>
</html>
