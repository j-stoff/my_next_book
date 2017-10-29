<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/23/17
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Search the database" scope="session"/>
    <c:import url="jsp-snippets/header-tag.jsp"/>
</head>
<body>
<h2>Search the database</h2>
<h4>Enter only one parameter, processed from top to bottom</h4>
<form action="results" method="get">
    <label for="bookTitle">Book Title:</label>
    <input type="text" name="bookTitle" id="bookTitle"/>
    <br/>

    <label for="bookId">Book ID:</label>
    <input type="text" name="bookId" id="bookId"/>
    <br/>

    <input type="submit">
</form>
</body>
</html>
