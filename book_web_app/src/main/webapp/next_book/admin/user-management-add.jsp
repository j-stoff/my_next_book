<%--
  Created by IntelliJ IDEA.
  User: j-stoff
  Date: 12/11/17
  Time: 3:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <c:set var="pageTitle" value="Add Users" scope="session" />
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
    <h3><a href="logout">Logout</a></h3>
    <h3><a href="deleteUser">Delete Users</a></h3>
    <h2>Add Users</h2>
    <form method="POST" action="adminAddUser" class="form row col-sm-10" id="signupForm" novalidate="novalidate">
        <div class="form-group row">
            <label for="username" class="col-sm-2 control-label">Username:</label>
            <div class="col-sm-4">
                <input type="text" id="username" class="form-control" placeholder="Username" name="username" required=""/>
            </div>
        </div>

        <div class="form-group row">
            <label for="email" class="col-sm-2 control-label">Email:</label>
            <div class="col-sm-6">
                <input type="text" id="email" class="form-control" placeholder="Email" name="email" required=""/>
            </div>
        </div>

        <div class="form-group row">
            <label for="emailConfirmation" class="col-sm-2 control-label">Email Confirmation:</label>
            <div class="col-sm-6">
                <input type="text" id="emailConfirmation" class="form-control" placeholder="Email Confirmation" name="emailConfirmation" required=""/>
            </div>
        </div>

        <div class="form-group row">
            <label for="password" class="col-sm-2 control-label">Password:</label>
            <div class="col-sm-4">
                <input type="text" id="password" class="form-control" placeholder="Password" name="password" required=""/>
            </div>
        </div>

        <div class="form-group row">
            <label for="passwordConfirmation" class="col-sm-2 control-label">Password Confirmation:</label>
            <div class="col-sm-4">
                <input type="text" id="passwordConfirmation" class="form-control" placeholder="Password Confirmation" name="passwordConfirmation" required=""/>
            </div>
        </div>

        <input type="submit" value="Add User" class="btn btn-default"/>
    </form>
    <div class="isUserAdded"></div>
</body>
</html>
