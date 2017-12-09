<%--
  Created by IntelliJ IDEA.
  User: j-stoff
  Date: 12/8/17
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="History" scope="session" />
</head>
<body>
    <h3><a href="logout"> Logout</a></h3>
    <c:choose>
        <c:when test="${not empty userList}">
            <table>
                <tr>
                    <th>User Id</th>
                    <th>Username</th>
                    <th>User email</th>
                    <th>Roles</th>
                    <th>Delete</th>
                    <th>Edit</th>
                </tr>
                <c:forEach var="user" items="${userList}">
                    <tr id="${user.user_id}">
                        <td>${user.user_id}</td>
                        <td>${user.user_name}</td>
                        <td>${user.email}</td>
                        <td>${user.roles}</td>
                        <td><input type="button" value="Delete"/></td>
                        <td><input type="button" value="Edit"></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h1>No users to edit.</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>
