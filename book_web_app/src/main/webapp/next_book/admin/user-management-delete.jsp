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
    <c:set var="pageTitle" value="User Management" scope="session" />
    <head>
        <title>${pageTitle}</title>
        <meta http-equiv="content-type" content="text/html; charset=windows-1252" charset="UTF-8"/>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="../js/jquery.validate.min.js"></script>
        <script src="../js/signupValidation.js"></script>
    </head>
<body>
    <h3><a href="logout"> Logout</a></h3>
    <h3><a href="addUser">Add Users</a></h3>
    <c:choose>
        <c:when test="${not empty userList}">
            <table>
                <tr>
                    <th>User Id</th>
                    <th>Username</th>
                    <th>User email</th>
                    <th>Roles</th>
                    <th>Delete</th>
                </tr>
                <c:forEach var="user" items="${userList}">
                    <tr id="${user.user_id}">
                        <td>${user.user_id}</td>
                        <td>${user.user_name}</td>
                        <td>${user.email}</td>
                        <td>${user.roles}</td>
                        <td><input type="button" value="Delete" class="deleteUser"/></td>
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
