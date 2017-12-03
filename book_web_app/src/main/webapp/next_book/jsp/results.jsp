<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/23/17
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="Search Results" scope="session"/>
    <c:import url="jsp-snippets/header-tag.jsp"/>
</head>
<body>

    <c:import url="jsp-snippets/navbar-tag.jsp" />
    <c:choose>
        <c:when test="${not empty resultList}" >
            <h1>Here are the results of the search</h1>
            <table>
                <tr>
                    <th>Book Title:</th>
                    <th>Author:</th>
                    <th>Rating:</th>
                    <th>Number of Reviews:</th>
                    <th>Number of Ratings:</th>
                    <th>Genres:</th>
                    <th>ISBN:</th>
                </tr>
                <c:forEach var="book" items="${resultList}">
                    <tr>
                        <td>${book.title}</td>
                        <td>${book.authorName}</td>
                        <td>${book.rating}</td>
                        <td>${book.numberOfRatings}</td>
                        <td>${book.numberOfReviews}</td>
                        <td>${book.genre}</td>
                        <td>${book.isbn}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>

        <c:otherwise>
            <h1>No Results found</h1>
        </c:otherwise>

    </c:choose>

    <!--
    <c:forEach var="book" items="${resultList}">
        ${book.title}
    </c:forEach>
    -->

</body>
</html>
