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
    <title>Search the database</title>
</head>
<body>
<h2>Search the database</h2>
<h4>Enter only one parameter, processed from top to bottom</h4>
<form action="results" method="get">
    <label for="bookTitle">Book Title:</label>
    <input type="text" name="bookTitle"/>
    <br/>

    <label for="bookId">Book ID:</label>
    <input type="text" name="bookId"/>
    <br/>

    <input type="submit">
</form>
</body>
</html>
