<%--
  Created by IntelliJ IDEA.
  User: jake-python
  Date: 10/18/17
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:set var="pageTitle" value="History" scope="session" />
    <c:import url="jsp-snippets/header-tag.jsp" />
</head>
<body>
    <c:import url="jsp-snippets/banner-tag.jsp" />
    <c:import url="jsp-snippets/navbar-tag.jsp" />

    <c:set var="bookList" value="${currentUser.booksForUser}" scope="session" />
    <div class="container">

    <!-- TODO test this results page -->
    <c:choose>
        <c:when test="${empty bookList}">
            <h2>You don't have any books in your list.</h2>
            <a href="recommendABook"><h3>Add Some</h3></a>
        </c:when>

        <c:when test="${not empty bookList}">
            <table id="userReadingHistory">
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Rating</th>
                    <th>Number of Ratings</th>
                    <th>Number of Reviews</th>
                    <th>Genres</th>
                    <th>ISBN</th>
                </tr>
                <c:forEach var="book" items="${bookList}">
                    <tr id="${book.id}" class="dataRow">
                        <td>${book.title}</td>
                        <td>${book.authorName}</td>
                        <td>${book.rating}</td>
                        <td>${book.numberOfRatings}</td>
                        <td>${book.numberOfReviews}</td>
                        <td>Genres eventually</td>
                        <td>${book.isbn}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>
    </div>

</body>
</html>