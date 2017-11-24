<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/18/17
  Time: 12:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Sign up" scope="session"/>
    <c:import url="../jsp/jsp-snippets/header-tag.jsp" />
    <script src="next_book/js/jquery.js"></script>
    <script src="next_book/js/jquery-3.1.1.js"></script>
    <script src="next_book/js/jquery.validate.min.js"></script>
    <script src="next_book/js/signupValidation.js"></script>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Sign up to this awesome service</h1>
    </div>
    <div class="col-sm-2"></div>
    <form method="POST" action="" class="form row col-sm-10" id="signupForm" novalidate="novalidate">
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

        <input type="submit" value="Submit" class="btn btn-default"/>
    </form>
</div>
</body>
</html>
