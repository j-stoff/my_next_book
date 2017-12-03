package com.nextBook.servlet.databaseEndpoints;

import com.nextBook.database.entity.Book;
import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.UserDAO;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
@WebServlet(
    name = "AddUserBook", 
    urlPatterns = "/next_book/addUserBook")
public class AddUserBook extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Get from the addUserBook");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Note this does not go anywhere, never direct a link here. Only for updating the database.

        String title = request.getParameter("title");
        String authorName = request.getParameter("authorName");

        double rating = Double.parseDouble(request.getParameter("rating"));
        int numberOfRatings = Integer.parseInt(request.getParameter("numberOfRatings"));
        int numberOfReviews = Integer.parseInt(request.getParameter("numberOfReviews"));
        String genreString = request.getParameter("genres");
        String isbn = request.getParameter("isbn");

        log.info("Title: " + title + ", Author: " + authorName + ", rating: " + rating + ", Ratings Count: "
                + numberOfRatings + ", Review Count: " + numberOfReviews + ", Genres: " + genreString + ", ISBN: " + isbn);

        /*
        UserDAO userDAO = new UserDAO();

        Users userInfo = (Users) request.getAttribute("currentUser");

        // Title
        // Author Name
        // Rating
        // Number of ratings
        // Number of reviews
        // genres? -- to be continued
        // isbn

        List<String> emptyList = new ArrayList<>();

        Book bookToAdd = new Book(title, authorName, rating, emptyList, isbn);
        bookToAdd.setFk_id_author(null);
        bookToAdd.setNumberOfRatings(numberOfRatings);
        bookToAdd.setNumberOfReviews(numberOfReviews);

        userInfo.getBooksForUser().add(bookToAdd);

        userDAO.updateUser(userInfo);


        response.getWriter().write("Hello from the servlet");
        response.getWriter().write("Title: " + title + ", Author: " + authorName);

        log.info(" BANANZA :::::  Title: " + title);
        */

        response.getWriter().write("Hello from the servlet");

        String url = "/book_app/next_book/recommendABook";

        response.sendRedirect(url);
    }
        
}