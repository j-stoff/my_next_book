<%--
  Created by IntelliJ IDEA.
  User: j-stoff
  Date: 12/2/17
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Recommendations" scope="session" />
    <c:import url="jsp-snippets/header-tag.jsp" />
    <script src="../js/recommendation.js"></script>
</head>
<body>
    <c:import url="jsp-snippets/banner-tag.jsp" />
    <c:import url="jsp-snippets/navbar-tag.jsp" />
    <h1>Hi</h1>
    <form method="post" action="addUserBook">
        <label>Title:</label>
        <input type="text" name="title" />

        <label>Author:</label>
        <input type="text" name="authorName"/>

        <label>Rating</label>
        <input type="text" name="rating"/>

        <label>Number of Ratings</label>
        <input type="text" name="numberOfRatings"/>

        <label>Number of Reviews</label>
        <input type="text" name="numberOfReviews"/>

        <label>Genre</label>
        <input type="text" name="genres"/>

        <label>ISBN</label>
        <input type="text" name="isbn"/>

        <input type="submit" value="Go"/>
    </form>
</body>
</html>
