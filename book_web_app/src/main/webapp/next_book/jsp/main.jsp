<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/18/17
  Time: 12:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Home" scope="session"/>
    <c:import url="jsp-snippets/header-tag.jsp" />
</head>
<body>
    <c:import url="jsp-snippets/banner-tag.jsp" />
    <c:import url="jsp-snippets/navbar-tag.jsp" />
    <h2>Get a book recommendation</h2>
    <!-- TODO Navigation bar snippet -->

    <!-- TODO links are broken -->
    <a href="search"><h3>Search the database</h3></a>
    

    <h3>Recommend me a good book</h3>
    <input type="button" value="Get a book to read - does not work"/>
    <p>Based on your preferences, get a book to your liking.</p>


    <h3>Reading history</h3>
    <p>A comprehensive listing of books you have read and the associated ratings</p>
    <a href="history">
        <input type="button" value="My History"/>
    </a>

    <h3>My information</h3>
    <p>Modify your account settings, personal preferences for books here.</p>
    <a href="userInfo">
        <input type="button" value="My Information"/>
    </a>
</body>
</html>
