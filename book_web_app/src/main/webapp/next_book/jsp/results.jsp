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
    <script src="../js/addBook.js"></script>
</head>
<body>

    <c:import url="jsp-snippets/navbar-tag.jsp" />
    <div class="container">
    <c:choose>
        <c:when test="${not empty bookList}" >
            <h1>Here are the results of the search</h1>
            <table id="bookResults">
                <tr>
                    <th>Book Title:</th>
                    <th>Author:</th>
                    <th>Rating:</th>
                    <th>Number of Reviews:</th>
                    <th>Number of Ratings:</th>
                    <th>Genres:</th>
                    <th>ISBN:</th>
                    <th></th>
                </tr>
                <c:forEach var="book" items="${bookList}">
                    <tr id="${book.id}">
                        <td class="bookInfo">${book.title}</td>
                        <td class="bookInfo">${book.authorName}</td>
                        <td class="bookInfo">${book.rating}</td>
                        <td class="bookInfo">${book.numberOfRatings}</td>
                        <td class="bookInfo">${book.numberOfReviews}</td>
                        <td class="bookInfo">${book.genre}</td>
                        <td class="bookInfo">${book.isbn}</td>
                        <td><input type="button" class="addToUserBooks" value="Add Book"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>

        <c:otherwise>
            <h1>No Results found</h1>
        </c:otherwise>

    </c:choose>

    </div>
    <!--
    <c:forEach var="book" items="${bookList}">
        ${book.title}
    </c:forEach>
    -->

</body>
</html>
