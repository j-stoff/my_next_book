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
    <c:import url="jsp-snippets/banner-tag.jsp" />
    <c:import url="jsp-snippets/navbar-tag.jsp" />
    <div class="container">
        <h2>Search the database</h2>
        <form action="results" method="get" class="form-inline">
            <select name="selectCriteria" id="selectCriteria">
                <option value="title">Title</option>
                <option value="author">Author</option>
                <option value="genre">Genre</option>
            </select>
            <input type="text" id="query" name="query"/>
            <input type="submit">
        </form>
    </div>
</body>
</html>
